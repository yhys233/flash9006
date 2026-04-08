package com.flash9006.stars

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }

    @Composable
    fun MainScreen(vm: MainViewModel = viewModel()) {
        val envOK by vm.envOK.collectAsState()
        val partitions by vm.partitions.collectAsState()
        val log by vm.log.collectAsState()
        val flashing by vm.isFlashing.collectAsState()
        val autoSync by vm.autoSyncEnabled.collectAsState()

        var currentPart by remember { mutableStateOf<Partition?>(null) }

        val filePicker = rememberLauncherForActivityResult(
            ActivityResultContracts.GetContent()
        ) { uri ->
            uri ?: return@rememberLauncherForActivityResult
            val path = FileUriHelper.getRealPath(this, uri)
            if (path == null) {
                // 这里已修好
                vm.appendLog("❌ 无法获取文件路径")
                return@rememberLauncherForActivityResult
            }
            currentPart?.let { vm.flash(it, path) }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "9006 磁盘刷机工具",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary
                )
                TextButton(
                    onClick = {
                        startActivity(Intent(this@MainActivity, AboutActivity::class.java))
                    }
                ) {
                    Text("关于")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "⚠️ 仅操作 9006 模式 sdg* 分区，误刷会直接变砖！\n仅用于已解锁/ROOT 设备，操作前请备份数据",
                    modifier = Modifier.padding(12.dp),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onErrorContainer
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (!envOK) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "❌ 环境不满足要求\n需要：Android 12+ / x86_64/ARM64 / ROOT 权限",
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            } else {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Checkbox(
                        checked = autoSync,
                        onCheckedChange = { vm.autoSyncEnabled.value = it }
                    )
                    Text(
                        text = "写入时自动 oflag=dsync 同步",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                if (!autoSync) {
                    Button(
                        onClick = { vm.manualSync() },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !flashing,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("手动同步磁盘 (sync)")
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = { vm.scanPartitions() },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !flashing,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    if (flashing) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(16.dp),
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text("扫描 sdg* 分区")
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    items(partitions) { part ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
                        ) {
                            Column(
                                modifier = Modifier.padding(14.dp)
                            ) {
                                Text(
                                    text = part.partName,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = part.devPath,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Button(
                                    onClick = {
                                        currentPart = part
                                        filePicker.launch("*/*")
                                    },
                                    enabled = !flashing,
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text("选择镜像并刷写")
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1.2f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "日志输出",
                                style = MaterialTheme.typography.titleMedium
                            )
                            TextButton(
                                onClick = { vm.clearLog() },
                                enabled = !flashing
                            ) {
                                Text("清空日志")
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = log,
                            modifier = Modifier
                                .fillMaxWidth()
                                .verticalScroll(rememberScrollState()),
                            fontFamily = FontFamily.Monospace,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}
