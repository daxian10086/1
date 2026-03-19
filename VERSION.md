# 大仙周易 - 安卓版 版本信息

## 当前版本：v2.0.1
- versionCode: 3
- versionName: "2.0.1"

## 更新日期：2026-03-19

## 版本更新内容：

### v2.0.1 (2026-03-19)
- ✅ 更新了完整的64卦数据（384条爻辞记录）
- ✅ 所有数据从Excel导入，与小程序数据保持一致
- ✅ 数据从图片识别并校验，确保准确性
- ✅ 去除所有句号，格式统一
- ✅ 修正第3卦和第23-30卦的数据格式
- ✅ 补充第5-11卦的缺失数据
- ✅ 修复第4卦和第12卦的不完整数据
- ✅ 所有爻数据按照正确顺序排列（初爻→上爻）

### 数据文件说明
- HexagramFortunes.kt: 64卦运势详解数据
  - 包含运势、财运、家庭、健康四个维度
  - 使用Kotlin data class LineFortune
  - 通过getFortune(hexagram, line)方法获取数据

### 构建信息
- 编译SDK: 34
- 最小SDK: 24
- 目标SDK: 34
- Kotlin版本: 1.8

### 依赖项
- androidx.core:core-ktx:1.12.0
- androidx.appcompat:appcompat:1.6.1
- com.google.android.material:material:1.11.0
- androidx.constraintlayout:constraintlayout:2.1.4
