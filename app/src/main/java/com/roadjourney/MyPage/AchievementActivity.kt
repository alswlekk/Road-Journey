package com.roadjourney.MyPage

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
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
        setupSpinner(binding.spAchievementCategory, typeCategories)

        clickBackButton()

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fcv_achievement, AchievementGoalFragment())
            .commit()

    }


    private fun setupSpinner(spAchievementCategory: Spinner, typeCategories: List<String>) {
        val adapter = object : ArrayAdapter<String>(this, R.layout.spinner_item, typeCategories) {


            override fun getView(position: Int, converView: View?, parent: ViewGroup): View {
                val view = super.getView(position, converView, parent) as TextView
                view.setBackgroundResource(R.drawable.spinner_background)
                view.textAlignment = View.TEXT_ALIGNMENT_TEXT_START
                view.setTextColor(resources.getColor(R.color.white, null))
                view.textSize = 15f
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
        spAchievementCategory.setSelection(0)
    }

    private fun clickBackButton() {
        binding.ivAchievementBack.setOnClickListener {
            finish()
        }
    }
}