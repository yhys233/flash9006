# README.md

# 9006 磁盘刷机工具

## 中文 | English

---

### 中文版本

# 9006 磁盘刷机工具

专为9006磁盘模式救砖场景打造的Android刷机工具，极简可控，安全高效

## 项目简介

本工具专注于9006磁盘模式下的设备救砖与分区刷写，仅支持操作`/dev/block/sdg*`分区，避免误操作导致设备变砖，适配Android 12至Android 16系统，仅支持arm64-v8a与x86_64两种架构，需设备获取ROOT权限后使用。

## 核心特性

- 🔒 **安全可控**：仅限定sdg*分区操作，无批量刷写，降低变砖风险

- 📱 **系统适配**：支持Android 12 - Android 16（API 32 - 36）

- 🎯 **架构支持**：仅适配arm64-v8a、x86_64架构

- ⚡ **同步优化**：支持自动dsync同步与手动sync同步，保障刷写稳定性

- 🧹 **日志管理**：支持日志实时查看与一键清空，方便排查问题

## 环境要求

- Linux编译环境：Gradle 8.9、NDK、SDK完整配置

- 运行设备：Android 12+、已ROOT、已解锁Bootloader

- 设备架构：arm64-v8a / x86_64

## Linux编译步骤

1. 进入项目**根目录**（包含`gradlew`、`settings.gradle.kts`的目录）

2. 赋予编译脚本执行权限：

    ```Bash
    
    chmod +x gradlew
    ```

3. 执行编译命令：

    ```Bash
    
    ./gradlew clean assembleRelease
    ```

4. 编译完成后，APK输出路径：

    ```Plain Text
    
    app/build/outputs/apk/release/app-release-unsigned.apk
    ```

## 使用说明

1. 设备获取ROOT权限后，安装编译生成的APK

2. 打开工具，确认设备环境兼容（系统、架构、ROOT权限均满足）

3. 点击「扫描sdg*分区」，获取可操作分区列表

4. 选择目标分区，点击「选择镜像并刷写」，选取本地刷机镜像

5. 可选择开启/关闭自动dsync同步，也可手动执行同步操作

6. 查看实时日志，刷写完成后建议手动同步确保数据写入完成

## 相关链接

- 作者酷安主页：[https://www.coolapk.com/u/2277160](https://www.coolapk.com/u/2277160)

- 项目GitHub仓库：[https://github.com/yhys233/flash9006](https://github.com/yhys233/flash9006)

## 免责声明

- 本工具仅适用于专业玩机、设备救砖场景，操作前请务必备份设备数据

- 因误操作、刷入错误镜像导致的设备变砖、数据丢失等问题，作者不承担任何责任

- 请在法律法规允许范围内使用本工具，禁止用于非法用途

## 项目结构

```Plain Text

项目根目录/
├── app/
│   ├── build.gradle.kts
│   └── proguard-rules.pro
├── src/
│   └── main/
│       ├── AndroidManifest.xml
│       ├── java/com/flash9006/stars/
│       └── res/
├── build.gradle.kts
├── settings.gradle.kts
├── gradlew
└── gradle/
```

---

### English Version

# 9006 Disk Flashing Tool

An Android flashing tool designed for 9006 disk mode unbricking, with minimal and controllable operations for security and efficiency

## Project Introduction

This tool is dedicated to device unbricking and partition flashing in 9006 disk mode, only supporting operations on `/dev/block/sdg*` partitions to avoid device bricking caused by misoperation. It is compatible with Android 12 to Android 16, only supports arm64-v8a and x86_64 architectures, and requires ROOT access on the device.

## Core Features

- 🔒 **Secure & Controllable**: Only operate sdg* partitions, no batch flashing, reducing bricking risk

- 📱 **System Compatibility**: Supports Android 12 - Android 16 (API 32 - 36)

- 🎯 **Architecture Support**: Only adapted for arm64-v8a and x86_64

- ⚡ **Sync Optimization**: Supports automatic dsync sync and manual sync, ensuring flashing stability

- 🧹 **Log Management**: Real-time log viewing and one-click clear for easy troubleshooting

## Environment Requirements

- Linux Compilation Environment: Gradle 8.9, complete NDK and SDK configuration

- Running Device: Android 12+, rooted, bootloader unlocked

- Device Architecture: arm64-v8a / x86_64

## Linux Compilation Steps

1. Enter the project **root directory** (directory containing `gradlew` and `settings.gradle.kts`)

2. Grant execution permission to the compilation script:

    ```Bash
    
    chmod +x gradlew
    ```

3. Execute the compilation command:

    ```Bash
    
    ./gradlew clean assembleRelease
    ```

4. After compilation, the APK output path:

    ```Plain Text
    
    app/build/outputs/apk/release/app-release-unsigned.apk
    ```

## Usage Instructions

1. After rooting the device, install the compiled APK

2. Open the tool and confirm the device environment is compatible (system, architecture, and ROOT permission are all satisfied)

3. Click *Scan sdg Partitions** to get the list of operable partitions

4. Select the target partition, click **Select Image and Flash**, and choose a local flashing image

5. You can toggle automatic dsync sync on or off, or perform manual synchronization

6. View real-time logs, and it is recommended to perform manual sync after flashing to ensure data is fully written

## Related Links

- Author Coolapk Homepage: [https://www.coolapk.com/u/2277160](https://www.coolapk.com/u/2277160)

- Project GitHub Repository: [https://github.com/yhys233/flash9006](https://github.com/yhys233/flash9006)

## Disclaimer

- This tool is only for professional device tinkering and unbricking scenarios. Please back up device data before operation

- The author is not responsible for device bricking, data loss or other problems caused by misoperation or flashing incorrect images

- Please use this tool within the scope permitted by laws and regulations, and prohibit illegal use

## Project Structure

```Plain Text

Project Root/
├── app/
│   ├── build.gradle.kts
│   └── proguard-rules.pro
├── src/
│   └── main/
│       ├── AndroidManifest.xml
│       ├── java/com/flash9006/stars/
│       └── res/
├── build.gradle.kts
├── settings.gradle.kts
├── gradlew
└── gradle/
```
> （注：文档部分内容可能由 AI 生成）