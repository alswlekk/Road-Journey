package com.roadjourney.Archive

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.appcompat.app.AppCompatActivity
import com.roadjourney.R
import com.roadjourney.databinding.ActivityArchiveBinding

class ArchiveActivity : AppCompatActivity() {

    private lateinit var binding: ActivityArchiveBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArchiveBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListeners()
        setupRecyclerView()

        val archiveCategories = listOf("전체", "성공", "실패")
        setupSpinner(binding.spArchiveArchieve, archiveCategories, 70)

        val typeCategories = listOf("전체", "반복", "단기", "장기")
        setupSpinner(binding.spArchiveType, typeCategories, 70)

        val goalCategories = listOf("전체", "기본", "단계별", "체크리스트")
        setupSpinner(binding.spArchiveGoal, goalCategories, 70)

        val arrayCategories = listOf("최근 등록", "최근 달성")
        setupSpinner(binding.spArchiveArray, arrayCategories, 12)
    }

    private fun setupClickListeners() {
        binding.ivArchiveBack.setOnClickListener {
            finish()
        }
    }

    private fun setupRecyclerView() {
        val archiveItems = listOf(
            ArchiveItem("Goal 1", "자기소개서 제출 마감", "2024.01.26", 80),
            ArchiveItem("Goal 2", "영어 단어 암기", "2024.01.20", 60),
            ArchiveItem("Goal 3", "체력 훈련", "2024.01.15", 40)
        )

        binding.rvArchive.apply {
            layoutManager = LinearLayoutManager(this@ArchiveActivity)
            adapter = ArchiveAdapter(archiveItems)
        }
    }

    private fun setupSpinner(spinner: Spinner, categories: List<String>, left:Int) {
        val adapter = object : ArrayAdapter<String>(this, R.layout.spinner_item, categories) {

            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent) as TextView
                view.setBackgroundResource(R.drawable.spinner_background)
                view.textAlignment = View.TEXT_ALIGNMENT_TEXT_START
                view.setTextColor(resources.getColor(R.color.white, null))
                view.textSize = 16f
                view.setPadding(left, 0, 0, 0)
                view.typeface = resources.getFont(R.font.samliphopangche)
                return view
            }

            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getDropDownView(position, convertView, parent) as TextView

                when (position) {
                    0 -> view.setBackgroundResource(R.drawable.spinner_dropdown_background_top)
                    count - 1 -> view.setBackgroundResource(R.drawable.spinner_dropdown_background_bottom)
                }

                view.textAlignment = View.TEXT_ALIGNMENT_CENTER
                view.setTextColor(resources.getColor(R.color.white, null))
                view.textSize = 16f
                view.typeface = resources.getFont(R.font.samliphopangche)

                return view
            }
        }
        spinner.adapter = adapter
        spinner.setSelection(0)
    }

}
