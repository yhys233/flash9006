package com.flash9006.stars

object DdFlasher {
    private const val BS = "4M"

    fun buildCommand(imagePath: String, devPath: String, autoDsync: Boolean): String {
        val dsyncFlag = if (autoDsync) "oflag=dsync" else ""
        return "dd if='$imagePath' of='$devPath' bs=$BS status=progress $dsyncFlag 2>&1"
    }

    fun isSafeDevice(devPath: String): Boolean {
        return devPath.startsWith("/dev/block/sdg")
    }
}
