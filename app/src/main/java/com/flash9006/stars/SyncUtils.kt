package com.flash9006.stars

object SyncUtils {
    suspend fun runSync(onOutput: (String) -> Unit) {
        Shell.exec("sync") { line ->
            onOutput(line)
        }
        onOutput("✅ 手动同步完成")
    }
}
