package com.roadjourney.MyPage

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.roadjourney.R
import com.roadjourney.databinding.ActivityItemBinding

class ItemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityItemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSpinner()
        setupClickListeners()
    }

    private fun setupSpinner() {
        val categories = listOf("전체", "배경", "장식품", "캐릭터")

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories).apply {
            setDropDownViewResource(R.layout.spinner_item)
        }
        binding.spItem.adapter = adapter

        // 초기 값으로 "전체" 선택
        binding.spItem.setSelection(0)
    }

    private fun setupClickListeners() {
        binding.ivItemBack.setOnClickListener {
            // 뒤로가기 버튼 클릭 시 동작
            finish()
        }
    }
}
