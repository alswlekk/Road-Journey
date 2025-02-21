package com.roadjourney.MyPage

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.roadjourney.R
import com.roadjourney.databinding.ActivityAchievementBinding

class AchievementActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAchievementBinding
    private lateinit var achievementGoalAdapter: AchievementGoalAdapter
    private var goals = ArrayList<AchievementData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAchievementBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val typeCategories = listOf("목표", "전체", "캐릭터", "아이템", "기타")
        setupSpinner(binding.spAchievementCategory, typeCategories, 110)

        clickBackButton()

        initAdapter()
        initGoalData()

    }

    private fun initAdapter() {
        achievementGoalAdapter = AchievementGoalAdapter(goals)
        binding.rvAchievementGoal.apply {
            adapter = achievementGoalAdapter
            layoutManager = LinearLayoutManager(this@AchievementActivity)
        }
    }

    private fun initGoalData() {
        goals.addAll(
            arrayListOf(
                AchievementData(
                    "천리 길도 한 걸음부터",
                    "목표를 1개 달성하세요.",
                    "성장 포인트 +5 골드 + 100",
                    "goal"
                ),
                AchievementData(
                    "세상에 틀린 길은 없다",
                    "목표를 10개 달성하세요.",
                    "성장 포인트 + 10 골드 + 300",
                    "goal"
                ),
                AchievementData(
                    "Road Journey!",
                    "목표를 100개 달성하세요.",
                    "성장 포인트 + 10 골드 + 500",
                    "goal"
                ),
                AchievementData(
                    "No Pain, No Gain",
                    "난이도 5의 목표를 달성하세요.",
                    "성장 포인트 + 20 골드 + 1000",
                    "goal"
                ),

                AchievementData(
                    "No Pain, No Gain",
                    "난이도 5의 목표를 달성하세요.",
                    "성장 포인트 + 20 골드 + 1000",
                    "goal"
                ),

                AchievementData(
                    "뉴 페이스",
                    "캐릭터 1개 해금",
                    "성장 포인트 +20 골드 +100"
                    ,"character"
                ),
                AchievementData(
                    "캐릭터 수집가",
                    "캐릭터 3개 해금",
                    "성장 포인트 +20 골드 +100",
                    "character"
                ),
                AchievementData(
                    "특별한 만남",
                    "특별상점에서만 얻을 수 있는 캐릭터 해금",
                    "성장 포인트 +30 골드 +200",
                    "character"
                ),
                AchievementData("동물 애호가",
                    "모든 캐릭터 해금",
                    "성장 포인트 +100 골드 +1000",
                    "character"),
                AchievementData("성장의 끝에서",
                    "캐릭터 1개의 성장도 성장도 4단계 달성",
                    "성장 포잍으 +50 골드 +500",
                    "character"),

                AchievementData("초보 소비자",
                    "일반 상점에서 아이템 1개 구매",
                    "성장 포인트 +20 골드 +100",
                    "item"),
                AchievementData("포토그래퍼",
                    "일반 상점에서 '배경' 카테고리의 모든 아이템 구매",
                    "성장 포인트 +70 골드 +700",
                    "item"),
                AchievementData("인테리어 디자이너",
                    "일반 상점에서 '장식품' 카테고리의 모든 아이템 구매",
                    "성장 포인트 +50 골드 +500",
                    "item"),
                AchievementData("랜덤 박스",
                    "특별 상점에서 아이템 1개 구매",
                    "성장 포인트 +10 골드 +50",
                    "item"),

                AchievementData("황금 고블린","100000골드 이상 보유하기","성장 포인트 +200 골드 +2000","etc"),
                AchievementData("대부호","1000000골드 이상 보유하기","성장 포인트 +500 골드 +5000","etc"),
                AchievementData("너 내 친구가 되어라","친구 1명 추가하기","성장 포인트 +20 골드 +100","etc"),
                AchievementData("나, 너, 우리","친구 10명 추가하기","성장 포인트 +50 골드 +500","etc"),

                )
        )
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
            "목표" -> goals.filter { it.category == "goal" }
            "전체" -> goals
            "캐릭터" -> goals.filter { it.category == "character" }
            "아이템" -> goals.filter { it.category == "item" }
            "기타" -> goals.filter { it.category == "etc" }
            else -> goals.filter { it.category == "goal" }
        }
        updateRecyclerViewData(filteredItems)
    }

    private fun updateRecyclerViewData(data: List<AchievementData>) {
        achievementGoalAdapter.updateData(data)
    }

    private fun clickBackButton() {
        binding.ivAchievementBack.setOnClickListener {
            finish()
        }
    }
}