package com.flash9006.stars

import java.io.File

object PartitionScanner {
    private const val SYS_BLOCK = "/sys/class/block"

    fun scan(): List<Partition> {
        val blockDir = File("/dev/block/")
        if (!blockDir.exists()) return emptyList()

        return blockDir.listFiles()?.filter {
            it.name.startsWith("sdg") && it.name.matches(Regex("sdg\\d+"))
        }?.mapNotNull { dev ->
            val uevent = File("/sys/class/block/${dev.name}/uevent")
            if (!uevent.exists()) return@mapNotNull null

            val partName = uevent.readLines().firstOrNull {
                it.startsWith("PARTNAME=")
            }?.substringAfter("PARTNAME=")?.trim() ?: dev.name

            Partition(
                devName = dev.name,
                devPath = "/dev/block/${dev.name}",
                partName = partName
            )
        } ?: emptyList()
    }
}
