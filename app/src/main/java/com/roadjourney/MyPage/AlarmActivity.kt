package com.roadjourney.MyPage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.roadjourney.databinding.ActivityAlarmBinding

class AlarmActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAlarmBinding
    private lateinit var alarmAdapter: AlarmAdapter
    private val alarmList = mutableListOf(
        AlarmItem("기니디님이 공동 목표를 요청했어요!", "", "2024.02.05 19:04"),
        AlarmItem("가나다님이 목표 달성을 축하했어요!", "", "2024.02.05 18:30"),
        AlarmItem("다음의 목표 마감이 하루 남았어요", "자기소개서 작성", "2024.02.05 17:45")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlarmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupListeners()
    }

    private fun setupRecyclerView() {
        alarmAdapter = AlarmAdapter(alarmList) { position ->
            removeAlarmItem(position)
        }
        binding.rvAlarm.layoutManager = LinearLayoutManager(this)
        binding.rvAlarm.adapter = alarmAdapter
    }

    private fun setupListeners() {
        binding.tvAlarmBtn.setOnClickListener {
            clearAllAlarms()
        }
        binding.ivAlarmBack.setOnClickListener {
            finish()
        }
    }

    private fun removeAlarmItem(position: Int) {
        alarmList.removeAt(position)
        alarmAdapter.notifyItemRemoved(position)
    }

    private fun clearAllAlarms() {
        alarmList.clear()
        alarmAdapter.notifyDataSetChanged()
    }
}
