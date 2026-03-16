package com.fortunetelling.i.ching

data class Trigram(
    val name: String,
    val symbol: String,
    val binary: Int,
    val nature: String
)

data class Hexagram(
    val number: Int,
    val name: String,
    val upperTrigram: Trigram,
    val lowerTrigram: Trigram,
    val lines: Array<String>,
    val lineDescriptions: Array<String>,
    val overallMeaning: String
) {
    fun getFortuneAnalysis(lineIndex: Int): String {
        val lineName = when(lineIndex) {
            0 -> "初爻"
            1 -> "二爻"  
            2 -> "三爻"
            3 -> "四爻"
            4 -> "五爻"
            5 -> "上爻"
            else -> "未知"
        }
        
        val love = generateLoveFortune(lineIndex)
        val career = generateCareerFortune(lineIndex)
        val wealth = generateWealthFortune(lineIndex)
        val health = generateHealthFortune(lineIndex)
        val family = generateFamilyFortune(lineIndex)
        val relationship = generateRelationshipFortune(lineIndex)
        val stock = generateStockFortune(lineIndex)
        
        return """
【$lineName 运势详解】

💕 爱情运势
$love

💼 事业运势  
$career

💰 财运运势
$wealth

🏃 健康运势
$health

🏠 家庭运势
$family

🤝 人际关系
$relationship

📊 投资理财
$stock
        """.trimIndent()
    }
    
    private fun generateLoveFortune(index: Int): String {
        return when(index) {
            0 -> "当前感情处于萌芽期。单身者有机会遇到心仪对象，建议多参加社交活动。有伴侣者感情稳定，适合培养共同兴趣。"
            1 -> "感情正在升温发展。单身者桃花运较旺，主动出击会有收获。有伴侣者关系更进一步，可考虑见家长。"
            2 -> "感情面临考验磨合。单身者可能遇到烂桃花，需要擦亮眼睛。有伴侣者易有矛盾，多沟通理解对方。"
            3 -> "感情出现重要转折。单身者可能遇到命中注定的缘分。有伴侣者关系可能升级或出现变故，理性对待。"
            4 -> "感情达到最佳状态。单身者适合表白或确定关系。有伴侣者感情甜蜜，可考虑谈婚论嫁。"
            5 -> "感情趋于成熟稳定。单身者可能修成正果。有伴侣者婚姻美满，珍惜眼前人，防范第三者。"
            else -> "感情运势平稳，顺其自然。"
        }
    }
    
    private fun generateCareerFortune(index: Int): String {
        return when(index) {
            0 -> "事业刚起步，处于打基础阶段。适合学习新技能，积累经验。不要急于求成，脚踏实地最重要。求职者有机会获得面试机会。"
            1 -> "事业稳步上升期。工作表现得到认可，有升职机会。适合主动承担更多责任，展现能力。创业者可扩大业务范围。"
            2 -> "事业遇到瓶颈挑战。工作压力增大，可能遇到强劲竞争对手。需要保持韧性，突破困难。不宜轻易跳槽或转行。"
            3 -> "事业面临重要抉择。可能出现转岗、跳槽或创业的机会。需谨慎评估，果断决策。风险与机遇并存。"
            4 -> "事业达到高峰状态。工作成果显著，名利双收。适合乘胜追击，争取更大成就。领导者可扩大团队规模。"
            5 -> "事业进入守成阶段。已有成果需要巩固，不宜冒进。适合总结经验，培养接班人。准备退休者可考虑交接工作。"
            else -> "事业运势平稳，按部就班即可。"
        }
    }
    
    private fun generateWealthFortune(index: Int): String {
        return when(index) {
            0 -> "财运刚开始起步。收入有限但开支也不大。适合学习理财知识，为将来打基础。不宜大额投资或借贷。"
            1 -> "财运逐步好转。正财收入稳定增长，可能有意外之财。适合开始定期储蓄，小额投资试水。"
            2 -> "财运受阻需谨慎。可能有意外支出或投资失利。保守理财为宜，控制消费欲望。避免高风险投资和借贷担保。"
            3 -> "财运出现转机。之前的投资可能开始回本，或有新的赚钱机会。把握时机，敢于尝试但要控制风险。"
            4 -> "财运达到顶峰。正财偏财皆旺，收益丰厚。适合多元化投资，但记得分散风险。可考虑购置固定资产。"
            5 -> "财运趋于稳定。收入稳定，没有大的波动。适合稳健理财，守住已有财富。为将来做好财务规划。"
            else -> "财运平稳，收支平衡即可。"
        }
    }
    
    private fun generateHealthFortune(index: Int): String {
        return when(index) {
            0 -> "身体状态一般，免疫力较弱。容易感冒或疲劳。建议规律作息，加强营养。适合开始运动锻炼，增强体质。"
            1 -> "身体状况好转。精力充沛，适合加大运动量。保持现有生活习惯，定期体检。注意劳逸结合。"
            2 -> "健康亮起黄灯。工作压力大可能导致亚健康状态。注意颈椎病、腰椎病等问题。保证充足睡眠，适当放松。"
            3 -> "健康出现转机。如果之前有病痛，此时可能好转。适合调整养生方案，改变不良习惯。"
            4 -> "身体状态极佳。各项指标良好，充满活力。适合挑战高强度运动，保持最佳状态。"
            5 -> "健康状况稳定。维持现有健康水平即可。注意养生保健，预防老年病。保持乐观心态。"
            else -> "健康状况平稳，注意日常保养。"
        }
    }
    
    private fun generateFamilyFortune(index: Int): String {
        return when(index) {
            0 -> "家庭关系初建。新婚夫妇正在磨合期。多包容理解，建立共同的生活目标。备孕夫妇可能有好消息。"
            1 -> "家庭氛围和谐。家庭成员关系融洽，互相支持。适合家庭聚会、出游。子女教育方面进展顺利。"
            2 -> "家庭出现矛盾。可能因琐事产生争执。需要多沟通，换位思考。婆媳关系、亲子关系需要用心经营。"
            3 -> "家庭面临调整。可能有搬家、装修或家庭成员变动。适应变化，共同面对挑战。"
            4 -> "家庭幸福美满。家运昌隆，诸事顺遂。适合添丁进口或置业。长辈身体健康，晚辈孝顺懂事。"
            5 -> "家庭根基稳固。多年经营的家庭关系牢不可破。传承家风，照顾老人。享受天伦之乐。"
            else -> "家庭运势平稳，平平淡淡才是真。"
        }
    }
    
    private fun generateRelationshipFortune(index: Int): String {
        return when(index) {
            0 -> "人缘正在建立。新环境新朋友，主动示好会有收获。容易得到长辈或领导的关照。保持真诚待人。"
            1 -> "人际关系升温。朋友圈扩大，贵人运旺。适合拓展人脉，参加社交活动。同事关系融洽，合作愉快。"
            2 -> "人际出现摩擦。可能遭遇小人或误会。说话做事要谨慎，避免口舌是非。不宜借钱给朋友或为他人担保。"
            3 -> "人际关系转折。可能结识重要人脉，或与某人关系破冰。把握机会，化解恩怨。"
            4 -> "人脉资源丰富。左右逢源，贵人相助。适合寻求合作，互利共赢。口碑良好，声名远播。"
            5 -> "人际圈稳定。多年老友值得信赖。精简社交，注重质量。远离是非之人，保持清誉。"
            else -> "人际关系平稳，与人为善即可。"
        }
    }
    
    private fun generateStockFortune(index: Int): String {
        return when(index) {
            0 -> "股市新手期。趋势不明，建议小额试水。多学习投资知识，不要盲目跟风。关注基本面好的蓝筹股。"
            1 -> "上升趋势形成。适合顺势而为，逐步加仓。但不要追高，逢低吸纳。可考虑定投指数基金。"
            2 -> "震荡调整期。控制仓位在半仓以下。高抛低吸做波段，或空仓观望。避免追涨杀跌。"
            3 -> "趋势转折期。可能是变盘的关键时刻。及时调整持仓结构，换股或减仓。关注政策面和资金面变化。"
            4 -> "收获止盈期。已有盈利可考虑分批减仓。落袋为安，不要贪婪。可适当配置债券等稳健资产。"
            5 -> "观望等待期。市场方向不明，多看少动。保存实力，等待下一轮机会。研究潜力股，为下次布局做准备。"
            else -> "股市平淡，长线持有优质股。"
        }
    }
}
