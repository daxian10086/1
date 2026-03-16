package com.fortunetelling.i.ching

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {

    private lateinit var btnCastHexagram: MaterialButton
    private lateinit var cardResult: androidx.cardview.widget.CardView
    private lateinit var tvHexagramNumber: TextView
    private lateinit var tvHexagramName: TextView
    private lateinit var tvUpperTrigram: TextView
    private lateinit var tvLowerTrigram: TextView
    private lateinit var tvOverallMeaning: TextView
    private lateinit var layoutLines: LinearLayout

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
        tvUpperTrigram = findViewById(R.id.tvUpperTrigram)
        tvLowerTrigram = findViewById(R.id.tvLowerTrigram)
        tvOverallMeaning = findViewById(R.id.tvOverallMeaning)
        layoutLines = findViewById(R.id.layoutLines)
    }

    private fun setupClickListener() {
        btnCastHexagram.setOnClickListener {
            castHexagram()
        }
    }

    private fun castHexagram() {
        // 随机生成卦象
        val hexagram = HexagramGenerator.randomHexagram()

        // 显示结果
        displayHexagram(hexagram)
    }

    private fun displayHexagram(hexagram: Hexagram) {
        // 更新卦象信息
        tvHexagramNumber.text = "第${hexagram.number}卦"
        tvHexagramName.text = hexagram.name

        // 显示上下卦
        tvUpperTrigram.text = "${hexagram.upperTrigram.symbol} ${hexagram.upperTrigram.name}（${hexagram.upperTrigram.nature}）"
        tvLowerTrigram.text = "${hexagram.lowerTrigram.symbol} ${hexagram.lowerTrigram.name}（${hexagram.lowerTrigram.nature}）"

        // 显示整体卦象含义
        tvOverallMeaning.text = hexagram.overallMeaning

        // 显示六爻
        displayLines(hexagram)

        // 显示结果卡片
        showResultCard()
    }

    private fun displayLines(hexagram: Hexagram) {
        layoutLines.removeAllViews()

        // 从下到上显示六爻
        val lineNames = arrayOf("初爻", "二爻", "三爻", "四爻", "五爻", "上爻")

        for (i in 0 until 6) {
            val lineView = createLineView(lineNames[i], hexagram.lines[i], hexagram.lineDescriptions[i])
            layoutLines.addView(lineView)
        }
    }

    private fun createLineView(lineName: String, lineText: String, description: String): View {
        val container = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(0, 12, 0, 12)
            // 添加分隔线
            background = resources.getDrawable(android.R.drawable.divider_horizontal_bright)
        }

        // 爻名和爻词
        val lineInfo = TextView(this).apply {
            text = "$lineName：$lineText"
            textSize = 16f
            setTextColor(resources.getColor(android.R.color.black))
        }

        // 爻词解说
        val descView = TextView(this).apply {
            text = description
            textSize = 14f
            setTextColor(resources.getColor(android.R.color.darker_gray))
            setPadding(0, 6, 0, 0)
            lineSpacingExtra = 2f
        }

        container.addView(lineInfo)
        container.addView(descView)

        return container
    }

    private fun showResultCard() {
        if (cardResult.visibility == View.GONE) {
            cardResult.apply {
                alpha = 0f
                visibility = View.VISIBLE
                animate()
                    .alpha(1f)
                    .setDuration(300)
                    .setListener(null)
                    .start()
            }
        }
    }

    private fun hideResultCard() {
        cardResult.apply {
            animate()
                .alpha(0f)
                .setDuration(300)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        visibility = View.GONE
                    }
                })
                .start()
        }
    }
}
