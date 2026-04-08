package com.flash9006.stars

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader

object Shell {
    suspend fun exec(cmd: String, onOutput: (String) -> Unit): Int {
        return withContext(Dispatchers.IO) {
            val process = ProcessBuilder("su", "-c", cmd)
                .redirectErrorStream(true)
                .start()

            val reader = BufferedReader(InputStreamReader(process.inputStream))
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                onOutput(line ?: "")
            }

            process.waitFor()
        }
    }
}
