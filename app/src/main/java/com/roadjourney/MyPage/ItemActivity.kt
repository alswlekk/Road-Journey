package com.roadjourney.MyPage

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
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

        val adapter = object : ArrayAdapter<String>(this, R.layout.spinner_item, categories) {

            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent) as TextView
                view.setBackgroundResource(R.drawable.spinner_background)
                view.textAlignment = View.TEXT_ALIGNMENT_CENTER
                view.setTextColor(resources.getColor(R.color.white, null))
                view.textSize = 20f
                view.setPadding(0,0,0,0)
                view.typeface = resources.getFont(R.font.samliphopangche)
                return view
            }

            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getDropDownView(position, convertView, parent) as TextView

                when (position) {
                    0 -> view.setBackgroundResource(R.drawable.spinner_dropdown_background_top) // 맨 위 항목
                    count - 1 -> view.setBackgroundResource(R.drawable.spinner_dropdown_background_bottom) // 맨 아래 항목

                }

                view.textAlignment = View.TEXT_ALIGNMENT_CENTER
                view.setTextColor(resources.getColor(R.color.white, null))
                view.textSize = 20f
                view.typeface = resources.getFont(R.font.samliphopangche)

                return view
            }

        }

        binding.spItem.adapter = adapter
        binding.spItem.setSelection(0)
    }

    private fun setupClickListeners() {
        binding.ivItemBack.setOnClickListener {
            finish()
        }
    }
}
