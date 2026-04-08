package com.flash9006.stars

import android.os.Build
import java.io.File

object EnvChecker {
    /**
     * 检查系统兼容性：Android 12+ (API 32+) + 架构为 arm64-v8a / x86_64
     */
    fun isCompatible(): Boolean {
        // 检查Android版本：Android 12 = API 32
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
            return false
        }

        // 检查CPU架构
        val supportedAbis = Build.SUPPORTED_ABIS
        return supportedAbis.any { it == "arm64-v8a" || it == "x86_64" }
    }

    /**
     * 检查是否拥有Root权限（兼容Magisk/KSU/APatch等所有主流Root方案）
     */
    fun hasRoot(): Boolean {
        // 方法1：检查su二进制文件是否存在（覆盖所有常见路径）
        val suPaths = listOf(
            "/system/bin/su",
            "/system/xbin/su",
            "/sbin/su",
            "/vendor/bin/su",
            "/data/adb/magisk/su",
            "/data/adb/ksu/bin/su",
            "/data/adb/ap/bin/su"
        )
        if (suPaths.any { File(it).exists() }) {
            return true
        }

        // 方法2：尝试执行su命令，通过执行结果判断
        return try {
            val process = Runtime.getRuntime().exec("su -c echo root_check")
            process.waitFor()
            // 退出码0 = 授权成功
            process.exitValue() == 0
        } catch (e: Exception) {
            false
        }
    }
}
