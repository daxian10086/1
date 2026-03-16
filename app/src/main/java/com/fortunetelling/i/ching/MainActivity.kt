package com.fortunetelling.i.ching

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
        val hexagram = HexagramGenerator.randomHexagram()
        displayHexagram(hexagram)
    }

    private fun displayHexagram(hexagram: Hexagram) {
        tvHexagramNumber.text = "第${hexagram.number}卦"
        tvHexagramName.text = hexagram.name

        tvUpperTrigram.text = "${hexagram.upperTrigram.symbol} ${hexagram.upperTrigram.name}（${hexagram.upperTrigram.nature}）"
        tvLowerTrigram.text = "${hexagram.lowerTrigram.symbol} ${hexagram.lowerTrigram.name}（${hexagram.lowerTrigram.nature}）"

        tvOverallMeaning.text = hexagram.overallMeaning

        displayLines(hexagram)
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
