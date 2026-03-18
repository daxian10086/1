package com.fortunetelling.i.ching

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AlphaAnimation
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.android.material.button.MaterialButton
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var btnCastHexagram: MaterialButton
    private lateinit var cardResult: CardView
    private lateinit var tvHexagramNumber: TextView
    private lateinit var tvHexagramName: TextView
    private lateinit var tvSelectedLineName: TextView
    private lateinit var tvUpperTrigram: TextView
    private lateinit var tvLowerTrigram: TextView
    private lateinit var tvOverallMeaning: TextView
    private lateinit var layoutLines: LinearLayout
    private lateinit var tvSelectedLine: TextView
    private lateinit var tvShaking: TextView
    private lateinit var ivShaking: ImageView
    private lateinit var ivStickOut: ImageView
    private lateinit var layoutShaking: FrameLayout
    private lateinit var layoutTitle: LinearLayout

    // 选项页
    private lateinit var tabLines: TextView
    private lateinit var tabSelected: TextView
    private lateinit var tabFortune: TextView
    private lateinit var pageLines: LinearLayout
    private lateinit var pageSelected: LinearLayout
    private lateinit var pageFortune: LinearLayout

    // 运势卡片
    private lateinit var tvLoveContent: TextView
    private lateinit var tvCareerContent: TextView
    private lateinit var tvWealthContent: TextView
    private lateinit var tvHealthContent: TextView
    private lateinit var tvFamilyContent: TextView
    private lateinit var tvRelationContent: TextView
    private lateinit var tvStockContent: TextView

    private var isAnimating = false
    private var currentTab = 0 // 0=六爻详解, 1=选中爻, 2=运势解读

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        setupClickListeners()
    }

    private fun initViews() {
        btnCastHexagram = findViewById(R.id.btnCastHexagram)
        cardResult = findViewById(R.id.cardResult)
        tvHexagramNumber = findViewById(R.id.tvHexagramNumber)
        tvHexagramName = findViewById(R.id.tvHexagramName)
        tvSelectedLineName = findViewById(R.id.tvSelectedLineName)
        tvUpperTrigram = findViewById(R.id.tvUpperTrigram)
        tvLowerTrigram = findViewById(R.id.tvLowerTrigram)
        tvOverallMeaning = findViewById(R.id.tvOverallMeaning)
        layoutLines = findViewById(R.id.layoutLines)
        tvSelectedLine = findViewById(R.id.tvSelectedLine)
        tvShaking = findViewById(R.id.tvShaking)
        ivShaking = findViewById(R.id.ivShaking)
        ivStickOut = findViewById(R.id.ivStickOut)
        layoutShaking = findViewById(R.id.layoutShaking)
        layoutTitle = findViewById(R.id.layoutTitle)

        // 选项页
        tabLines = findViewById(R.id.tabLines)
        tabSelected = findViewById(R.id.tabSelected)
        tabFortune = findViewById(R.id.tabFortune)
        pageLines = findViewById(R.id.pageLines)
        pageSelected = findViewById(R.id.pageSelected)
        pageFortune = findViewById(R.id.pageFortune)

        // 运势内容
        tvLoveContent = findViewById(R.id.tvLoveContent)
        tvCareerContent = findViewById(R.id.tvCareerContent)
        tvWealthContent = findViewById(R.id.tvWealthContent)
        tvHealthContent = findViewById(R.id.tvHealthContent)
        tvFamilyContent = findViewById(R.id.tvFamilyContent)
        tvRelationContent = findViewById(R.id.tvRelationContent)
        tvStockContent = findViewById(R.id.tvStockContent)
    }

    private fun setupClickListeners() {
        btnCastHexagram.setOnClickListener {
            if (!isAnimating) {
                castHexagram()
            }
        }

        // 选项页切换
        tabLines.setOnClickListener { switchTab(0) }
        tabSelected.setOnClickListener { switchTab(1) }
        tabFortune.setOnClickListener { switchTab(2) }

        // 设置运势卡片点击折叠/展开
        setupFoldableCard(R.id.cardLove, R.id.tvLoveTitle, R.id.tvLoveContent)
        setupFoldableCard(R.id.cardCareer, R.id.tvCareerTitle, R.id.tvCareerContent)
        setupFoldableCard(R.id.cardWealth, R.id.tvWealthTitle, R.id.tvWealthContent)
        setupFoldableCard(R.id.cardHealth, R.id.tvHealthTitle, R.id.tvHealthContent)
        setupFoldableCard(R.id.cardFamily, R.id.tvFamilyTitle, R.id.tvFamilyContent)
        setupFoldableCard(R.id.cardRelation, R.id.tvRelationTitle, R.id.tvRelationContent)
        setupFoldableCard(R.id.cardStock, R.id.tvStockTitle, R.id.tvStockContent)
    }

    private fun switchTab(tab: Int) {
        currentTab = tab
        // 重置所有tab样式
        tabLines.setBackgroundColor(Color.parseColor("#F5F5DC"))
        tabLines.setTextColor(Color.parseColor("#666666"))
        tabSelected.setBackgroundColor(Color.parseColor("#F5F5DC"))
        tabSelected.setTextColor(Color.parseColor("#666666"))
        tabFortune.setBackgroundColor(Color.parseColor("#F5F5DC"))
        tabFortune.setTextColor(Color.parseColor("#666666"))
        
        // 隐藏所有页面
        pageLines.visibility = View.GONE
        pageSelected.visibility = View.GONE
        pageFortune.visibility = View.GONE
        
        when(tab) {
            0 -> {
                // 六爻详解
                tabLines.setBackgroundColor(Color.parseColor("#FFECB3"))
                tabLines.setTextColor(Color.parseColor("#8B4513"))
                pageLines.visibility = View.VISIBLE
            }
            1 -> {
                // 选中爻
                tabSelected.setBackgroundColor(Color.parseColor("#FFECB3"))
                tabSelected.setTextColor(Color.parseColor("#8B4513"))
                pageSelected.visibility = View.VISIBLE
            }
            2 -> {
                // 运势解读
                tabFortune.setBackgroundColor(Color.parseColor("#FFECB3"))
                tabFortune.setTextColor(Color.parseColor("#8B4513"))
                pageFortune.visibility = View.VISIBLE
            }
        }
    }

    private fun setupFoldableCard(cardId: Int, titleId: Int, contentId: Int) {
        val card = findViewById<LinearLayout>(cardId)
        val title = findViewById<TextView>(titleId)
        val content = findViewById<TextView>(contentId)
        
        card.setOnClickListener {
            if (content.visibility == View.VISIBLE) {
                content.visibility = View.GONE
                title.text = title.text.toString().replace("▼", "▶")
            } else {
                content.visibility = View.VISIBLE
                title.text = title.text.toString().replace("▶", "▼")
            }
        }
    }

    private fun castHexagram() {
        isAnimating = true
        btnCastHexagram.isEnabled = false
        
        // 隐藏之前的结果
        cardResult.visibility = View.GONE
        
        // 恢复摇卦动画区域高度
        layoutShaking.layoutParams.height = 100
        layoutShaking.requestLayout()
        
        // 显示摇卦动画
        showShakingAnimation()
    }

    private fun showShakingAnimation() {
        // 显示摇桶
        ivShaking.visibility = View.VISIBLE
        ivStickOut.visibility = View.GONE
        tvShaking.visibility = View.VISIBLE
        tvShaking.text = "摇卦中..."
        
        // 摇晃动画
        val shakeAnimator = AnimatorSet()
        
        val rotate1 = ObjectAnimator.ofFloat(ivShaking, "rotation", 0f, -10f, 10f, -10f, 10f, -10f, 10f, 0f)
        val translateX1 = ObjectAnimator.ofFloat(ivShaking, "translationX", 0f, 15f, -15f, 15f, -15f, 15f, 0f)
        
        shakeAnimator.playTogether(rotate1, translateX1)
        shakeAnimator.duration = 1500
        shakeAnimator.interpolator = AccelerateDecelerateInterpolator()
        
        shakeAnimator.addListener(object : android.animation.Animator.AnimatorListener {
            override fun onAnimationStart(animation: android.animation.Animator) {}
            override fun onAnimationEnd(animation: android.animation.Animator) {
                showStickFalling()
            }
            override fun onAnimationCancel(animation: android.animation.Animator) {}
            override fun onAnimationRepeat(animation: android.animation.Animator) {}
        })
        
        shakeAnimator.start()
        
        // 文字闪烁效果
        val blinkAnimator = AlphaAnimation(1f, 0.3f).apply {
            duration = 150
            repeatMode = android.view.animation.Animation.REVERSE
            repeatCount = 9
        }
        tvShaking.startAnimation(blinkAnimator)
    }

    private fun showStickFalling() {
        // 隐藏摇桶，显示签掉出
        ivShaking.visibility = View.GONE
        ivStickOut.visibility = View.VISIBLE
        
        // 签掉出动画
        val dropAnimator = ObjectAnimator.ofFloat(ivStickOut, "translationY", -50f, 0f)
        dropAnimator.duration = 500
        dropAnimator.interpolator = AccelerateDecelerateInterpolator()
        
        dropAnimator.addListener(object : android.animation.Animator.AnimatorListener {
            override fun onAnimationStart(animation: android.animation.Animator) {}
            override fun onAnimationEnd(animation: android.animation.Animator) {
                Handler(Looper.getMainLooper()).postDelayed({
                    displayResult()
                }, 500)
            }
            override fun onAnimationCancel(animation: android.animation.Animator) {}
            override fun onAnimationRepeat(animation: android.animation.Animator) {}
        })
        
        dropAnimator.start()
    }

    private fun displayResult() {
        // 隐藏摇卦提示
        tvShaking.visibility = View.GONE
        ivShaking.visibility = View.GONE
        ivStickOut.visibility = View.GONE
        tvShaking.clearAnimation()
        
        // 缩回摇卦动画区域
        layoutShaking.layoutParams.height = 0
        layoutShaking.requestLayout()
        
        // 随机生成卦象
        val hexagram = HexagramGenerator.randomHexagram()

        // 随机选择六爻中的一爻
        val lineIndex = Random.nextInt(6)
        val lineNames = arrayOf("初爻", "二爻", "三爻", "四爻", "五爻", "上爻")
        val selectedLineName = lineNames[lineIndex]
        val selectedLineText = hexagram.lines[lineIndex]
        val selectedLineDesc = hexagram.lineDescriptions[lineIndex]
        
        // 智能评分 - 根据爻辞质量分析返回1-5星
        val stars = analyzeLineQuality(selectedLineText, selectedLineDesc)

        // 显示结果
        displayHexagram(hexagram, selectedLineName, selectedLineText, selectedLineDesc, stars)
        
        isAnimating = false
        btnCastHexagram.isEnabled = true
    }

    // 分析爻辞质量（返回1-5星）
    private fun analyzeLineQuality(lineText: String, lineDesc: String): String {
        val fullText = (lineText + " " + lineDesc).lowercase()
        
        // 吉祥关键词
        val goodWords = listOf("吉", "利", "亨", "通", "大吉", "元吉", "贞吉", "安", "喜", "福", "庆", "誉", "无咎", "无不利", "有终")
        // 凶险关键词
        val badWords = listOf("凶", "厉", "悔", "吝", "灾", "害", "死", "败", "损", "亡", "穷", "病", "忧", "咎", "寇", "血", "战")
        
        var goodScore = 0
        var badScore = 0
        
        goodWords.forEach { word ->
            if (fullText.contains(word)) goodScore++
        }
        
        badWords.forEach { word ->
            if (fullText.contains(word)) badScore++
        }
        
        val total = goodScore + badScore
        if (total == 0) return "⭐⭐⭐☆☆"
        
        val ratio = goodScore.toFloat() / total
        
        return when {
            ratio >= 0.8f && goodScore >= 3 -> "⭐⭐⭐⭐⭐"
            ratio >= 0.6f && goodScore >= 2 -> "⭐⭐⭐⭐☆"
            ratio >= 0.4f && goodScore >= 1 -> "⭐⭐⭐☆☆"
            ratio >= 0.2f -> "⭐⭐☆☆☆"
            else -> "⭐☆☆☆☆"
        }
    }

    private fun displayHexagram(hexagram: Hexagram, selectedLineName: String, selectedLineText: String, selectedLineDesc: String, stars: String) {
        // 更新卦象信息
        tvHexagramNumber.text = "第${hexagram.number}卦"
        tvHexagramName.text = hexagram.name
        tvSelectedLineName.text = "·$selectedLineName"

        // 显示上下卦
        tvUpperTrigram.text = "${hexagram.upperTrigram.symbol} ${hexagram.upperTrigram.name}（${hexagram.upperTrigram.nature}）"
        tvLowerTrigram.text = "${hexagram.lowerTrigram.symbol} ${hexagram.lowerTrigram.name}（${hexagram.lowerTrigram.nature}）"

        // 显示整体卦象含义
        tvOverallMeaning.text = hexagram.overallMeaning

        // 显示六爻
        displayLines(hexagram)

        // 显示选中的爻 (放到选中爻页面)
        tvSelectedLine.text = "【解卦】$selectedLineName：$selectedLineText\n\n$selectedLineDesc"

        // 显示多维度运势解读
        displayFortuneAnalysis(hexagram, selectedLineName)

        // 重置到选中爻页面
        switchTab(1)

        // 添加动画效果
        val fadeIn = AlphaAnimation(0f, 1f).apply {
            duration = 500
            layoutLines.getChildAt(0)?.startAnimation(this)
        }

        // 显示结果卡片
        showResultCard()
    }

    private fun displayFortuneAnalysis(hexagram: Hexagram, lineName: String) {
        // 根据爻名获取索引
        val lineIndex = when(lineName) {
            "初爻" -> 0
            "二爻" -> 1
            "三爻" -> 2
            "四爻" -> 3
            "五爻" -> 4
            "上爻" -> 5
            else -> 0
        }
        
        // 获取HexagramFortunes数据（运势、财运、家庭、健康）
        val fortuneData = HexagramFortunes.getLineFortune(hexagram.number, lineIndex)
        
        // 设置各维度运势内容（使用HexagramFortunes数据）
        tvCareerContent.text = fortuneData?.运势 ?: "暂无数据"
        tvWealthContent.text = fortuneData?.财运 ?: "暂无数据"
        tvHealthContent.text = fortuneData?.健康 ?: "暂无数据"
        tvFamilyContent.text = fortuneData?.家庭 ?: "暂无数据"
        
        // 其他卡片保持原样
        tvLoveContent.text = hexagram.getLoveFortune(lineIndex)
        tvRelationContent.text = hexagram.getRelationFortune(lineIndex)
        tvStockContent.text = hexagram.getStockFortune(lineIndex)
        
        // 默认折叠所有卡片
        tvLoveContent.visibility = View.GONE
        tvCareerContent.visibility = View.GONE
        tvWealthContent.visibility = View.GONE
        tvHealthContent.visibility = View.GONE
        tvFamilyContent.visibility = View.GONE
        tvRelationContent.visibility = View.GONE
        tvStockContent.visibility = View.GONE
        
        // 更新标题显示状态
        findViewById<TextView>(R.id.tvCareerTitle).text = "📊 运势详情"  // 事业运势 → 运势详情
        findViewById<TextView>(R.id.tvLoveTitle).text = "💕 爱情运势 ▶"
        findViewById<TextView>(R.id.tvWealthTitle).text = "💰 财运运势 ▶"
        findViewById<TextView>(R.id.tvHealthTitle).text = "🏃 健康运势 ▶"
        findViewById<TextView>(R.id.tvFamilyTitle).text = "🏠 家庭运势 ▶"
        findViewById<TextView>(R.id.tvRelationTitle).text = "🤝 人际关系 ▶"
        findViewById<TextView>(R.id.tvStockTitle).text = "📈 投资理财 ▶"
    }

    private fun displayLines(hexagram: Hexagram) {
        layoutLines.removeAllViews()

        val lineNames = arrayOf("初爻", "二爻", "三爻", "四爻", "五爻", "上爻")

        for (i in 0 until 6) {
            val lineName = lineNames[i]
            val lineText = hexagram.lines[i]
            val description = hexagram.lineDescriptions[i]

            val lineInfo = TextView(this)
            lineInfo.text = "$lineName：$lineText"
            lineInfo.textSize = 16f
            lineInfo.setTextColor(android.graphics.Color.BLACK)

            val descView = TextView(this)
            descView.text = description
            descView.textSize = 14f
            descView.setTextColor(android.graphics.Color.DKGRAY)
            descView.setPadding(0, 6, 0, 0)

            val container = LinearLayout(this)
            container.orientation = LinearLayout.VERTICAL
            container.setPadding(0, 12, 0, 12)
            container.addView(lineInfo)
            container.addView(descView)

            layoutLines.addView(container)
        }
    }

    private fun showResultCard() {
        if (cardResult.visibility == View.GONE) {
            cardResult.visibility = View.VISIBLE
            cardResult.alpha = 0f
            cardResult.animate().alpha(1f).setDuration(300).start()
        }
    }
}
