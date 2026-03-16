package com.fortunetelling.i.ching

data class Trigram(
    val name: String,           // 八卦名称
    val symbol: String,         // 符号（如 ☰）
    val binary: Int,            // 二进制值 (0-7)
    val nature: String          // 自然属性
)

data class Hexagram(
    val number: Int,            // 卦序 (1-64)
    val name: String,           // 卦名
    val upperTrigram: Trigram,  // 上卦
    val lowerTrigram: Trigram,  // 下卦
    val lines: Array<String>,   // 六爻 (从下到上，6条)
    val lineDescriptions: Array<String>,  // 爻词解说
    val overallMeaning: String  // 整体卦象含义
)
