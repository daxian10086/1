package com.fortunetelling.i.ching

import android.os.Bundle
import android.view.View
import android.view.animation.AlphaAnimation
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
    private lateinit var tvUpperTrigram: TextView
    private lateinit var tvLowerTrigram: TextView
    private lateinit var tvOverallMeaning: TextView
    private lateinit var layoutLines: LinearLayout
    private lateinit var tvSelectedLine: TextView

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
        tvSelectedLine = findViewById(R.id.tvSelectedLine)
    }

    private fun setupClickListener() {
        btnCastHexagram.setOnClickListener {
            castHexagram()
        }
    }

    private fun castHexagram() {
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
    }

    private fun displayHexagram(hexagram: Hexagram, selectedLineName: String, selectedLineText: String, selectedLineDesc: String) {
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
