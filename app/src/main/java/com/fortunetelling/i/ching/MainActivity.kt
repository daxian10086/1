package com.fortunetelling.i.ching

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.TranslateAnimation
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var btnCastHexagram: MaterialButton
    private lateinit var cardResult: androidx.cardview.widget.CardView
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
    private lateinit var layoutTitle: LinearLayout

    private var isAnimating = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        setupClickListener()
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
        layoutTitle = findViewById(R.id.layoutTitle)
    }

    private fun setupClickListener() {
        btnCastHexagram.setOnClickListener {
            if (!isAnimating) {
                castHexagram()
            }
        }
    }

    private fun castHexagram() {
        isAnimating = true
        btnCastHexagram.isEnabled = false
        
        // 隐藏之前的结果
        cardResult.visibility = View.GONE
        
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
        shakeAnimator.duration = 1500 // 1.5秒摇晃
        shakeAnimator.interpolator = AccelerateDecelerateInterpolator()
        
        shakeAnimator.addListener(object : android.animation.Animator.AnimatorListener {
            override fun onAnimationStart(animation: android.animation.Animator) {}
            override fun onAnimationEnd(animation: android.animation.Animator) {
                // 摇晃结束后，显示签掉出
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
                // 动画结束后显示结果
                Handler(mainLooper).postDelayed({
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
        
        // 随机生成卦象
        val hexagram = HexagramGenerator.randomHexagram()

        // 随机选择六爻中的一爻
        val lineIndex = Random.nextInt(6)
        val lineNames = arrayOf("初爻", "二爻", "三爻", "四爻", "五爻", "上爻")
        val selectedLineName = lineNames[lineIndex]
        val selectedLineText = hexagram.lines[lineIndex]
        val selectedLineDesc = hexagram.lineDescriptions[lineIndex]

        // 显示结果
        displayHexagram(hexagram, selectedLineName, selectedLineText, selectedLineDesc)
        
        isAnimating = false
        btnCastHexagram.isEnabled = true
    }

    private fun displayHexagram(hexagram: Hexagram, selectedLineName: String, selectedLineText: String, selectedLineDesc: String) {
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

        // 显示选中的爻
        tvSelectedLine.text = "【解卦】$selectedLineName：$selectedLineText\n\n$selectedLineDesc"
        tvSelectedLine.visibility = View.VISIBLE

        // 添加动画效果
        val fadeIn = AlphaAnimation(0f, 1f).apply {
            duration = 500
            tvSelectedLine.startAnimation(this)
        }

        // 显示结果卡片
        showResultCard()
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
