package com.flash9006.stars

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _envOK = MutableStateFlow(false)
    val envOK: StateFlow<Boolean> = _envOK.asStateFlow()

    private val _partitions = MutableStateFlow(emptyList<Partition>())
    val partitions: StateFlow<List<Partition>> = _partitions.asStateFlow()

    private val _log = MutableStateFlow("")
    val log: StateFlow<String> = _log.asStateFlow()

    private val _isFlashing = MutableStateFlow(false)
    val isFlashing: StateFlow<Boolean> = _isFlashing.asStateFlow()

    val autoSyncEnabled = MutableStateFlow(true)

    init {
        checkEnv()
    }

    private fun checkEnv() {
        _envOK.value = EnvChecker.isCompatible() && EnvChecker.hasRoot()
    }

    fun scanPartitions() {
        viewModelScope.launch(Dispatchers.IO) {
            val list = PartitionScanner.scan()
            _partitions.value = list
            appendLog("扫描完成：找到 ${list.size} 个 sdg* 分区")
        }
    }

    fun flash(partition: Partition, imagePath: String) {
        if (!DdFlasher.isSafeDevice(partition.devPath)) {
            appendLog("❌ 禁止操作非 sdg* 分区，操作已终止")
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            _isFlashing.value = true
            appendLog("\n> 开始刷写：${partition.partName}")
            appendLog("→ 设备路径：${partition.devPath}")

            val cmd = DdFlasher.buildCommand(imagePath, partition.devPath, autoSyncEnabled.value)
            appendLog("执行命令：$cmd")

            Shell.exec(cmd) { line ->
                appendLog(line)
            }

            appendLog("> 刷写完成\n")
            _isFlashing.value = false
        }
    }

    fun manualSync() {
        viewModelScope.launch(Dispatchers.IO) {
            appendLog("\n> 执行手动同步 (sync) ...")
            SyncUtils.runSync { appendLog(it) }
        }
    }

    fun clearLog() {
        _log.value = ""
        appendLog("日志已清空")
    }

    // 公开方法，给外部调用
    fun appendLog(msg: String) {
        _log.update { it + "\n$msg" }
    }
}
