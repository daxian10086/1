# 周易算卦 APK

一个基于易经64卦的随机算卦Android应用。

## 功能特点

- 随机生成64卦之一
- 显示上卦、下卦
- 显示整体卦象含义
- 详细展示六爻（从下到上）
- 每爻包含爻词和爻词解说
- 中国风UI设计

## 项目结构

```
fortune-teller/
├── app/
│   ├── src/main/
│   │   ├── java/com/fortunetelling/i/ching/
│   │   │   ├── MainActivity.kt              # 主界面
│   │   │   ├── Hexagram.kt                  # 卦象数据类
│   │   │   ├── Trigrams.kt                  # 八卦数据
│   │   │   ├── HexagramGenerator.kt         # 随机生成器
│   │   │   └── HexagramsData.kt             # 64卦完整数据
│   │   ├── res/
│   │   │   ├── layout/
│   │   │   │   └── activity_main.xml        # 主界面布局
│   │   │   └── values/
│   │   │       ├── strings.xml
│   │   │       ├── colors.xml
│   │   │       └── themes.xml
│   │   └── AndroidManifest.xml
│   └── build.gradle
├── build.gradle
├── settings.gradle
└── gradle.properties
```

## 构建APK

### 前置要求

- Android Studio Arctic Fox 或更高版本
- JDK 8 或更高版本
- Android SDK API 34

### 构建步骤

1. 使用Android Studio打开项目：
   ```bash
   open fortune-teller
   ```

2. 等待Gradle同步完成

3. 构建Release版本APK：
   - 菜单: Build → Generate Signed Bundle / APK
   - 选择 APK
   - 创建或选择签名密钥
   - 选择 release 构建类型
   - APK会生成在 `app/release/app-release.apk`

### 或使用命令行构建

```bash
cd fortune-teller

# 构建Debug版本
./gradlew assembleDebug

# 构建Release版本
./gradlew assembleRelease
```

APK位置：
- Debug: `app/build/outputs/apk/debug/app-debug.apk`
- Release: `app/build/outputs/apk/release/app-release.apk`

## 使用说明

1. 安装APK到Android设备
2. 打开应用
3. 点击"算一卦"按钮
4. 查看卦象结果

## 卦象数据

包含完整的64卦数据：
- 卦序（1-64）
- 卦名
- 上卦和下卦
- 六爻爻词（从下到上）
- 爻词解说
- 整体卦象含义

## 技术栈

- Kotlin
- Android SDK
- Material Components
- ConstraintLayout
- CardView

## 许可证

MIT License
