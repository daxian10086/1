package com.fortunetelling.i.ching

import kotlin.random.Random

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
) {
    // 根据爻的位置，生成多维度解读
    fun getFortuneAnalysis(lineIndex: Int): String {
        val linePosition = when(lineIndex) {
            0 -> "初爻"
            1 -> "二爻"
            2 -> "三爻"
            3 -> "四爻"
            4 -> "五爻"
            5 -> "上爻"
            else -> "未知"
        }
        
        val loveFortune = generateLoveFortune(lineIndex)
        val careerFortune = generateCareerFortune(lineIndex)
        val wealthFortune = generateWealthFortune(lineIndex)
        val healthFortune = generateHealthFortune(lineIndex)
        val familyFortune = generateFamilyFortune(lineIndex)
        val stockFortune = generateStockFortune(lineIndex)
        
        return """
【$linePosition多维度运势解读】

🌹 爱情运势
$loveFortune

💼 事业运势
$careerFortune

💰 财运运势
$wealthFortune

💪 健康运势
$healthFortune

🏠 家庭运势
$familyFortune

📈 股票运势
$stockFortune
        """.trimIndent()
    }
    
    private fun generateLoveFortune(index: Int): String {
        val pos = when(index) {
            0 -> "起步阶段"
            1 -> "成长阶段"
            2 -> "奋斗阶段"
            3 -> "转折阶段"
            4 -> "成功阶段"
            5 -> "总结阶段"
            else -> "未知"
        }
        
        val basic = when(index) {
            0 -> "感情运刚开始，需要主动出击"
            1 -> "感情运发展顺利，可寻求缘分"
            2 -> "感情运遇坎坷，需要耐心等待"
            3 -> "感情运出现转折，需要理性判断"
            4 -> "感情运达到高峰，可以更进一步"
            5 -> "感情运趋于稳定，珍惜眼前人"
            else -> "感情运势平淡"
        }
        
        val advice = when(index) {
            0 -> "建议多参加社交活动"
            1 -> "把握缘分机会"
            2 -> "保持耐心等待良缘"
            3 -> "慎重考虑关系"
            4 -> "趁热打铁可婚"
            5 -> "经营现有感情"
            else -> "保持平常心"
        }
        
        return "【当前状态】$pos\n【运势分析】$basic\n【建议】$advice"
    }
    
    private fun generateCareerFortune(index: Int): String {
        val pos = when(index) {
            0 -> "创业/求职起步"
            1 -> "事业成长期"
            2 -> "事业攻坚期"
            3 -> "事业转折点"
            4 -> "事业成功期"
            5 -> "事业收获期"
            else -> "事业平稳期"
        }
        
        val basic = when(index) {
            0 -> "事业运刚开始，积累经验最重要"
            1 -> "事业稳步上升，积极进取"
            2 -> "遇到困难，需要突破瓶颈"
            3 -> "事业迎来转机，把握机遇"
            4 -> "事业达到高峰，成果显著"
            5 -> "事业稳固，收获成果"
            else -> "事业运势平平"
        }
        
        val advice = when(index) {
            0 -> "打好基础，学习提升"
            1 -> "积极主动，抓住机会"
            2 -> "保持韧性，突破困难"
            3 -> "果断决策，转型升级"
            4 -> "乘胜追击，扩大影响"
            5 -> "巩固成果，享受收获"
            else -> "保持现状"
        }
        
        return "【当前状态】$pos\n【运势分析】$basic\n【建议】$advice"
    }
    
    private fun generateWealthFortune(index: Int): String {
        val pos = when(index) {
            0 -> "财运启动期"
            1 -> "财运上升期"
            2 -> "财运考验期"
            3 -> "财运转折期"
            4 -> "财运收获期"
            5 -> "财运稳定期"
            else -> "财运平缓"
        }
        
        val basic = when(index) {
            0 -> "财运刚开始，需要积累"
            1 -> "财运逐步好转，收入增加"
            2 -> "财运受阻，需要谨慎投资"
            3 -> "财运出现转机，把握机会"
            4 -> "财运达到顶峰，收益颇丰"
            5 -> "财运稳定，收益稳健"
            else -> "财运平平"
        }
        
        val action = when(index) {
            0 -> "学习投资理财知识"
            1 -> "开源节流，增加收入"
            2 -> "保守投资，保存实力"
            3 -> "敢于投资，抓住机会"
            4 -> "多元化配置，分散风险"
            5 -> "稳健理财，守住成果"
            else -> "保持收支平衡"
        }
        
        return "【当前状态】$pos\n【运势分析】$basic\n【建议】$action"
    }
    
    private fun generateHealthFortune(index: Int): String {
        val pos = when(index) {
            0 -> "健康起步期"
            1 -> "健康改善期"
            2 -> "健康考验期"
            3 -> "健康转折点"
            4 -> "健康巅峰期"
            5 -> "健康维持期"
            else -> "健康平稳期"
        }
        
        val basic = when(index) {
            0 -> "身体一般，需要调养"
            1 -> "身体好转，精力充沛"
            2 -> "身体欠佳，需要调理"
            3 -> "健康出现转机"
            4 -> "健康达到最佳状态"
            5 -> "健康维持良好"
            else -> "健康一般"
        }
        
        val advice = when(index) {
            0 -> "养成良好习惯"
            1 -> "加强锻炼，保持活力"
            2 -> "注意休息，防止过劳"
            3 -> "注重养生，调整作息"
            4 -> "享受健康成果"
            5 -> "保持生活习惯"
            else -> "保持现状"
        }
        
        return "【当前状态】$pos\n【运势分析】$basic\n【建议】$advice"
    }
    
    private fun generateFamilyFortune(index: Int): String {
        val pos = when(index) {
            0 -> "家庭建立期"
            1 -> "家庭和谐期"
            2 -> "家庭考验期"
            3 -> "家庭调整期"
            4 -> "家庭和睦期"
            5 -> "家庭稳定期"
            else -> "家庭平稳期"
        }
        
        val basic = when(index) {
            0 -> "家庭关系刚开始建立"
            1 -> "家庭关系越来越和谐"
            2 -> "家庭关系出现矛盾"
            3 -> "家庭关系面临调整"
            4 -> "家庭关系达到巅峰"
            5 -> "家庭关系稳定和睦"
            else -> "家庭关系平淡"
        }
        
        val suggestion = when(index) {
            0 -> "增进感情交流"
            1 -> "共同规划家庭"
            2 -> "沟通化解矛盾"
            3 -> "适应新变化"
            4 -> "享受家庭幸福"
            5 -> "维护现有关系"
            else -> "多花时间陪伴"
        }
        
        return "【当前状态】$pos\n【运势分析】$basic\n【建议】$suggestion"
    }
    
    private fun generateStockFortune(index: Int): String {
        val pos = when(index) {
            0 -> "投资起步期"
            1 -> "盈利上升期"
            2 -> "调整持仓期"
            3 -> "趋势反转期"
            4 -> "收益兑现期"
            5 -> "持仓观望期"
            else -> "股市平淡期"
        }
        
        val trend = when(index) {
            0 -> "趋势不明，可小额试水"
            1 -> "趋势向上，积极加仓"
            2 -> "震荡调整，控制仓位"
            3 -> "趋势反转，果断换股"
            4 -> "趋势确立，开始减仓"
            5 -> "趋势稳定，观望为主"
            else -> "趋势不明"
        }
        
        val strategy = when(index) {
            0 -> "轻仓试错"
            1 -> "顺势而为"
            2 -> "控制仓位"
            3 -> "果断调仓"
            4 -> "分批止盈"
            5 -> "观望等待"
            else -> "观望为主"
        }
        
        return "【当前状态】$pos\n【趋势分析】$trend\n【操作策略】$strategy"
    }
}
