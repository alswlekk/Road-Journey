package com.roadjourney.MyPage

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.setPadding
import com.roadjourney.R
import com.roadjourney.databinding.ActivityAchievementBinding

class AchievementActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAchievementBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAchievementBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val typeCategories = listOf("목표", "전체", "캐릭터", "아이템", "기타")
        setupSpinner(binding.spAchievementCategory, typeCategories, 110)

        clickBackButton()

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fcv_achievement, AchievementGoalFragment())
            .commit()

    }


    private fun setupSpinner(spAchievementCategory: Spinner, typeCategories: List<String>, left: Int) {
        val adapter = object : ArrayAdapter<String>(this, R.layout.spinner_item, typeCategories) {

            override fun getView(position: Int, converView: View?, parent: ViewGroup): View {
                val view = super.getView(position, converView, parent) as TextView
                view.setBackgroundResource(R.drawable.spinner_background)
                view.textAlignment = View.TEXT_ALIGNMENT_TEXT_START
                view.setTextColor(resources.getColor(R.color.white, null))
                view.textSize = 15f
                view.setPadding(left, 0, 0, 0)
                view.typeface = resources.getFont(R.font.samliphopangche)
                return view
            }

            override fun getDropDownView(
                position: Int,
                converView: View?,
                parent: ViewGroup
            ): View {
                val view = super.getDropDownView(position, converView, parent) as TextView

                when (position) {
                    0 -> view.setBackgroundResource(R.drawable.spinner_dropdown_background_top)
                    count - 1 -> view.setBackgroundResource(R.drawable.spinner_dropdown_background_bottom)
                }

                view.textAlignment = View.TEXT_ALIGNMENT_CENTER
                view.setTextColor(resources.getColor(R.color.white, null))
                view.textSize = 15f
                view.typeface = resources.getFont(R.font.samliphopangche)
                return view
            }
        }

        spAchievementCategory.adapter = adapter

        spAchievementCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedCategory = typeCategories[position]
                filterAchievement(selectedCategory)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun filterAchievement(selectedCategory: String) {
        val filteredItems = when (selectedCategory) {
            "목표" -> AchievementGoalFragment()
            "전체" -> AchievementAllFragment()
            "캐릭터" -> AchievementCharacterFragment()
            "아이템" -> AchievementItemFragment()
            "기타" -> AchievementEtcFragment()
            else -> AchievementGoalFragment()
        }

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fcv_achievement, filteredItems)
            .commit()
    }

    private fun clickBackButton() {
        binding.ivAchievementBack.setOnClickListener {
            finish()
        }
    }
}