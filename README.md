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

## 理论支持机型（仅供参考，不保证一定可用）
本工具无足够真机样本完成全面测试，因此不承诺任何机型一定可正常使用，以下仅为理论兼容范围：

### 一、理论上更易适配的机型
- OPPO Find 7（X9006，骁龙801 MSM8974AC）——工具名称由来
- 三星 Galaxy Note3（SM-N9006，骁龙800 MSM8974）
- 小米3 联通/电信版、小米4、红米 Note 4G（骁龙400/801）
- 一加 1、一加 2（骁龙801/810）
- 早期 vivo / OPPO 机型：X9007、Find 7a、R7 系列、A37/A53 等（早期骁龙4系/6系）

### 二、理论上可能兼容（需进入 9008 模式）
- 高通骁龙 200 / 400 / 600 / 800 / 801 / 805 / 808 / 810 平台机型
- 大致年代：2013–2016 年高通安卓设备

### 三、理论上基本不支持
- 骁龙 820 及后续平台（9008 签名/加密严格加强）
- 非高通平台：联发科 MTK、华为麒麟、三星 Exynos、展讯等
- 2017 年后主流旗舰：小米6/8/9、一加5/6、OPPO R11 及以后、vivo X20 及以后、三星 S8 及以后
- Android 8+ 且深度锁 BL、9008 端口被厂商屏蔽或强加密的设备

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
    app/build/outputs/apk/release/app-release.apk
    ```

## 使用说明

1. 设备获取ROOT权限后，安装编译生成的APK

2. 打开工具，确认设备环境兼容（系统、架构、ROOT权限均满足）

3. 点击「扫描sdg*分区」，获取可操作分区列表

4. 选择目标分区，点击「选择镜像并刷写」，选取本地刷机镜像

5. 可选择开启/关闭自动dsync同步，也可手动执行同步操作

6. 查看实时日志，刷写完成后建议手动同步确保数据写入完成

## 相关链接

- 作者酷安主页：[https://www.coolapk.com/u/2277160](sslocal://flow/file_open?url=https%3A%2F%2Fwww.coolapk.com%2Fu%2F2277160&flow_extra=eyJsaW5rX3R5cGUiOiJjb2RlX2ludGVycHJldGVyIn0=)
- 项目GitHub仓库：[https://github.com/yhys233/flash9006](sslocal://flow/file_open?url=https%3A%2F%2Fgithub.com%2Fyhys233%2Fflash9006&flow_extra=eyJsaW5rX3R5cGUiOiJjb2RlX2ludGVycHJldGVyIn0=)

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


> （注：文档部分内容可能由 AI 生成）