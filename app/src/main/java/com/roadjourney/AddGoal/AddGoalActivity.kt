package com.roadjourney.AddGoal

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.LinearLayoutManager
import com.roadjourney.R
import com.roadjourney.databinding.ActivityAddGoalBinding
import com.roadjourney.databinding.DialogAddFriendBinding
import com.roadjourney.databinding.DialogCalendarBinding
import com.roadjourney.databinding.DialogGoalTypeBinding
import com.roadjourney.databinding.DialogRequestBinding
import com.roadjourney.databinding.DialogSaveBinding
import retrofit2.Call
import retrofit2.Response

class AddGoalActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddGoalBinding
    private var accessToken: String = ""
    private var userId: Int = -1
    private val starStates = BooleanArray(5)
    private var isGoalShare = false
    private var isGoalFriend = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddGoalBinding.inflate(layoutInflater)
        setContentView(binding.root)
        accessToken = intent.getStringExtra("accessToken") ?: ""
        userId = intent.getIntExtra("userId", -1)
        selectedFriendAdapter = SelectedFriendAdapter(selectedFriends)
        binding.rvAddGoalFriend.layoutManager = LinearLayoutManager(this)
        binding.rvAddGoalFriend.adapter = selectedFriendAdapter

        setupClickListeners()
        setupTextWatchers()
        setupRecyclerView()
    }

    private fun setupClickListeners() {
        binding.ivAddGoalBack.setOnClickListener {
            finish()
        }

        val stars = listOf(
            binding.ivAddGoalStar1,
            binding.ivAddGoalStar2,
            binding.ivAddGoalStar3,
            binding.ivAddGoalStar4,
            binding.ivAddGoalStar5
        )

        stars.forEachIndexed { index, imageView ->
            imageView.setOnClickListener {
                starStates[index] = !starStates[index]
                updateStarImage(imageView, starStates[index])
            }
        }

        binding.tvAddGoalBtn.setOnClickListener {
            if (binding.tvAddGoalBtn.isEnabled) {
                submitGoal()
                if (!isGoalShare){
                    showSaveDialog()
                }else{
                    showRequestDialog()
                }
            }
        }

        binding.tvAddGoalLong.setOnClickListener {
            goalTypeDialog()
        }

        binding.ivAddGoalShareBtn.setOnClickListener {
            isGoalShare = !isGoalShare
            updateToggleImage(binding.ivAddGoalShareBtn, isGoalShare)
            if (isGoalShare) {
                binding.clAddGoalFriendType.visibility = View.VISIBLE
                binding.rvAddGoalFriend.visibility = View.VISIBLE
            }else{
                binding.clAddGoalFriendType.visibility = View.GONE
                binding.rvAddGoalFriend.visibility = View.GONE
            }
        }
        binding.ivAddGoalFriendPlus.setOnClickListener {
            showAddFriendDialog()
        }

        binding.ivAddGoalFriendBtn.setOnClickListener {
            isGoalFriend = !isGoalFriend
            updateToggleImage(binding.ivAddGoalFriendBtn, isGoalFriend)
        }

        binding.tvAddGoalFriendType.setOnClickListener {
            if (binding.tvAddGoalFriendType.text == "협동") {
                binding.tvAddGoalFriendType.text = "경쟁"
            } else {
                binding.tvAddGoalFriendType.text = "협동"
            }
        }

    }

    private fun setupRecyclerView() {
        binding.rvAddGoal.layoutManager = LinearLayoutManager(this)

        val normalButton = binding.tvAddGoalNormal
        val additionalButton = binding.tvAddGoalAddtional
        val checklistButton = binding.tvAddGoalCheck

        val buttons = listOf(normalButton, additionalButton, checklistButton)
        val recyclerView = binding.rvAddGoal
        val btnPlus = binding.ivAddGoalPlus
        val btnMinus = binding.ivAddGoalMinus

        val additionalGoals = mutableListOf("Step 1", "Step 2")
        val checklistGoals = mutableListOf("Sub Goal 1", "Sub Goal 2", "Sub Goal 3")

        var currentGoals: MutableList<String>? = null
        var currentAdapter: GoalAdapter? = null

        normalButton.setOnClickListener {
            updateButtonStyles(normalButton, buttons)
            recyclerView.visibility = View.GONE
            btnMinus.visibility = View.GONE
            btnPlus.visibility = View.GONE
            currentGoals = null
            currentAdapter = null
            updateLayoutConstraints(false)
        }

        additionalButton.setOnClickListener {
            updateButtonStyles(additionalButton, buttons)
            currentGoals = additionalGoals
            currentAdapter = GoalAdapter(currentGoals!!)
            recyclerView.adapter = currentAdapter
            recyclerView.visibility = View.VISIBLE
            btnMinus.visibility = View.VISIBLE
            btnPlus.visibility = View.VISIBLE
            updateLayoutConstraints(true)
        }

        checklistButton.setOnClickListener {
            updateButtonStyles(checklistButton, buttons)
            currentGoals = checklistGoals
            currentAdapter = GoalAdapter(currentGoals!!)
            recyclerView.adapter = currentAdapter
            recyclerView.visibility = View.VISIBLE
            btnMinus.visibility = View.VISIBLE
            btnPlus.visibility = View.VISIBLE
            updateLayoutConstraints(true)
        }

        btnPlus.setOnClickListener {
            currentGoals?.let { goals ->
                val newGoal = if (goals === additionalGoals) {
                    "Step ${goals.size + 1}"
                } else {
                    "Sub Goal ${goals.size + 1}"
                }
                goals.add(newGoal)
                currentAdapter?.notifyItemInserted(goals.size - 1)
            }
        }

        btnMinus.setOnClickListener {
            currentGoals?.let { goals ->
                if (goals.isNotEmpty()) {
                    val lastIndex = goals.size - 1
                    goals.removeAt(lastIndex)
                    currentAdapter?.notifyItemRemoved(lastIndex)
                }
            }
        }
    }

    private fun updateLayoutConstraints(isRecyclerViewVisible: Boolean) {
        val constraintSet = ConstraintSet()
        constraintSet.clone(binding.clAddGoal)

        if (isRecyclerViewVisible) {
            constraintSet.connect(
                R.id.cl_add_goal_share,
                ConstraintSet.TOP,
                R.id.iv_add_goal_minus,
                ConstraintSet.BOTTOM,
                60
            )
        } else {
            constraintSet.connect(
                R.id.cl_add_goal_share,
                ConstraintSet.TOP,
                R.id.tv_add_goal_normal,
                ConstraintSet.BOTTOM,
                20
            )
        }

        constraintSet.applyTo(binding.clAddGoal)
    }

    private fun updateButtonStyles(selectedButton: TextView, buttons: List<TextView>) {
        buttons.forEach { button ->
            button.setBackgroundResource(
                if (button == selectedButton) R.drawable.shape_fill_blue1_10 else R.drawable.shape_fill_blue8_10
            )
        }
    }

    private fun goalTypeDialog() {
        val dialogBinding = DialogGoalTypeBinding.inflate(LayoutInflater.from(this))
        val dialog = AlertDialog.Builder(this)
            .setView(dialogBinding.root)
            .create()

        dialogBinding.rbRepeated.isChecked = true
        updateGoalTypeVisibility(dialogBinding, R.id.rb_repeated)

        dialogBinding.rgGoalType.setOnCheckedChangeListener { _, checkedId ->
            updateGoalTypeVisibility(dialogBinding, checkedId)
        }

        dialogBinding.tvProfileEditBtn.setOnClickListener {
            val selectedText = when (dialogBinding.rgGoalType.checkedRadioButtonId) {
                R.id.rb_repeated -> dialogBinding.rbRepeated.text.toString()
                R.id.rb_short_term -> dialogBinding.rbShortTerm.text.toString()
                R.id.rb_long_term -> dialogBinding.rbLongTerm.text.toString()
                else -> ""
            }

            binding.tvAddGoalLong.text = selectedText

            when (dialogBinding.rgGoalType.checkedRadioButtonId) {
                R.id.rb_repeated -> {
                    val goalDay = dialogBinding.etAddGoalDay.text.toString().ifEmpty { "1" }
                    val goalHow = dialogBinding.etAddGoalHow.text.toString().ifEmpty { "3" }

                    binding.tvAddGoalDay.text = "${goalDay}일"
                    binding.tvAddGoalEach.text = "마다"
                    binding.tvAddGoalMany.text = "${goalHow}회"
                    binding.tvAddGoalRepeat.text = "반복"
                }
                R.id.rb_short_term, R.id.rb_long_term -> {
                    val startDate = dialogBinding.tvAddGoalDayShort.text.toString()
                    val endDate = dialogBinding.tvAddGoalShort.text.toString()

                    binding.tvAddGoalDay.text = "$startDate"
                    binding.tvAddGoalEach.text = "~"
                    binding.tvAddGoalMany.text = "$endDate"
                    binding.tvAddGoalRepeat.text = ""
                }
            }

            dialog.dismiss()
        }

        dialogBinding.ivGoalTypeCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialogBinding.tvAddGoalDayShort.setOnClickListener {
            showCalendarDialog(dialogBinding.tvAddGoalDayShort, dialogBinding)
        }

        dialogBinding.tvAddGoalShort.setOnClickListener {
            showCalendarDialog(dialogBinding.tvAddGoalShort, dialogBinding)
        }

        dialog.show()
    }

    private fun updateGoalTypeVisibility(dialogBinding: DialogGoalTypeBinding, selectedId: Int) {
        if (selectedId == R.id.rb_repeated) {
            dialogBinding.llGoalRepeat.visibility = View.VISIBLE
            dialogBinding.llGoalHow.visibility = View.VISIBLE
            dialogBinding.llGoalRepeatShort.visibility = View.GONE
            dialogBinding.llGoalHowShort.visibility = View.GONE
        } else {
            dialogBinding.llGoalRepeat.visibility = View.GONE
            dialogBinding.llGoalHow.visibility = View.GONE
            dialogBinding.llGoalRepeatShort.visibility = View.VISIBLE
            dialogBinding.llGoalHowShort.visibility = View.VISIBLE
        }
    }

    private fun showCalendarDialog(targetTextView: TextView, dialogBinding: DialogGoalTypeBinding) {
        val calendarDialogBinding = DialogCalendarBinding.inflate(LayoutInflater.from(targetTextView.context))
        val dialog = AlertDialog.Builder(targetTextView.context)
            .setView(calendarDialogBinding.root)
            .create()
        val calendarView = calendarDialogBinding.calendarView
        val dayDecorator = DayDecorator(targetTextView.context)
        val sundayDecorator = SundayDecorator()
        val saturdayDecorator = SaturdayDecorator()
        calendarView.addDecorators(dayDecorator, sundayDecorator, saturdayDecorator)
        calendarView.setHeaderTextAppearance(R.style.CalendarWidgetHeader)
        calendarView.setTitleFormatter { day ->
            "${day.year}년 ${String.format("%02d", day.month)}월"
        }

        var selectedDate: String? = null

        calendarView.setOnDateChangedListener { _, date, _ ->
            val formattedMonth = String.format("%02d", date.month)
            val formattedDay = String.format("%02d", date.day)
            selectedDate = "${date.year}.$formattedMonth.$formattedDay"
        }

        calendarDialogBinding.tvCalendarBtn.setOnClickListener {
            selectedDate?.let { date ->
                targetTextView.text = date

                val startDateText = dialogBinding.tvAddGoalDayShort.text.toString()
                val endDateText = dialogBinding.tvAddGoalShort.text.toString()

                if (startDateText.isNotEmpty() && endDateText.isNotEmpty()) {
                    val startDate = startDateText.replace(".", "").toInt()
                    val endDate = endDateText.replace(".", "").toInt()

                    if (targetTextView == dialogBinding.tvAddGoalShort && endDate < startDate) {
                        dialogBinding.tvAddGoalShort.text = startDateText
                    }

                    if (targetTextView == dialogBinding.tvAddGoalDayShort && startDate > endDate) {
                        dialogBinding.tvAddGoalDayShort.text = endDateText
                    }
                }
            }
            dialog.dismiss()
        }

        calendarDialogBinding.tvCalendarBtnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun showSaveDialog() {
        val saveDialogBinding = DialogSaveBinding.inflate(LayoutInflater.from(this))
        val saveDialog = AlertDialog.Builder(this)
            .setView(saveDialogBinding.root)
            .setCancelable(false)
            .create()

        saveDialogBinding.tvSaveBtn.setOnClickListener {
            saveDialog.dismiss()
            val intent = Intent()
            intent.putExtra("goalCreated", true)
            setResult(RESULT_OK, intent)
            finish()
        }

        saveDialog.show()
    }

    private fun showRequestDialog() {
        val requestDialogBinding = DialogRequestBinding.inflate(LayoutInflater.from(this))
        val requestDialog = AlertDialog.Builder(this)
            .setView(requestDialogBinding.root)
            .setCancelable(false)
            .create()

        requestDialogBinding.tvRequestBtn.setOnClickListener {
            requestDialog.dismiss()
            val intent = Intent()
            intent.putExtra("goalCreated", true)
            setResult(RESULT_OK, intent)
            finish()
        }

        requestDialog.show()
    }

    private fun setupTextWatchers() {
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                updateButtonState()
            }

            override fun afterTextChanged(s: Editable?) {}
        }

        binding.etAddGoal.addTextChangedListener(textWatcher)
        binding.etAddGoalDetail.addTextChangedListener(textWatcher)
    }

    private fun updateStarImage(imageView: ImageView, isSelected: Boolean) {
        val imageRes = if (isSelected) {
            R.drawable.ic_home_star
        } else {
            R.drawable.ic_add_goal_star
        }
        imageView.setImageResource(imageRes)
    }

    private fun updateButtonState() {
        val isGoalNameFilled = binding.etAddGoal.text.toString().isNotEmpty()
        val isGoalDetailFilled = binding.etAddGoalDetail.text.toString().isNotEmpty()

        val isEnabled = isGoalNameFilled && isGoalDetailFilled
        binding.tvAddGoalBtn.isEnabled = isEnabled

        val backgroundRes = if (isEnabled) {
            R.drawable.shape_fill_blue_25
        } else {
            R.drawable.shape_fill_gray3_25
        }
        binding.tvAddGoalBtn.setBackgroundResource(backgroundRes)
    }

    private fun updateToggleImage(imageView: ImageView, isEnabled: Boolean) {
        val newImageRes = if (isEnabled) {
            R.drawable.ic_toggle_on
        } else {
            R.drawable.ic_toggle_off
        }
        imageView.setImageResource(newImageRes)
    }

    private val selectedFriends = mutableListOf<Friend>()
    private lateinit var selectedFriendAdapter: SelectedFriendAdapter

    private fun showAddFriendDialog() {
        val dialogBinding = DialogAddFriendBinding.inflate(LayoutInflater.from(this))
        val dialog = AlertDialog.Builder(this)
            .setView(dialogBinding.root)
            .create()

        val friendAdapter = FriendAdapter(emptyList()) { friend, isAdded ->
            if (isAdded) {
                selectedFriends.add(friend)
            } else {
                selectedFriends.remove(friend)
            }
            dialogBinding.tvAddFriendBtn.isEnabled = selectedFriends.isNotEmpty()
            dialogBinding.tvAddFriendBtn.setBackgroundResource(
                if (selectedFriends.isNotEmpty()) R.drawable.shape_fill_blue1_25 else R.drawable.shape_fill_gray3_25
            )
        }

        dialogBinding.rvAddFriend.layoutManager = LinearLayoutManager(this)
        dialogBinding.rvAddFriend.adapter = friendAdapter

        dialogBinding.ivAddFriendSearch.setOnClickListener {
            val query = dialogBinding.etAddFriend.text.toString().trim()
            if (query.isNotEmpty()) {
                searchFriendsFromServer(query, friendAdapter, dialogBinding)
            }
        }

        dialogBinding.ivAddFriendCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialogBinding.tvAddFriendBtn.setOnClickListener {
            updateFriendRecyclerView()
            dialog.dismiss()
        }

        dialog.show()
    }


    private fun searchFriendsFromServer(query: String, adapter: FriendAdapter, dialogBinding: DialogAddFriendBinding) {
        val token = "Bearer $accessToken"

        RetrofitClient.instance.searchFriends(token, query).enqueue(object : retrofit2.Callback<FriendResponse> {
            override fun onResponse(call: Call<FriendResponse>, response: Response<FriendResponse>) {
                if (response.isSuccessful) {
                    val friends = response.body()?.users ?: emptyList()
                    friends.forEach { friend ->
                        Log.d("사진 확인", "User: ${friend.nickname}, Image: ${friend.profileImage}")
                    }
                    if (friends.isEmpty()) {
                        dialogBinding.rvAddFriend.visibility = View.GONE
                        dialogBinding.tvAddFriendNo.visibility = View.VISIBLE
                    } else {
                        dialogBinding.rvAddFriend.visibility = View.VISIBLE
                        dialogBinding.tvAddFriendNo.visibility = View.GONE
                        adapter.updateList(friends)
                    }
                }
            }

            override fun onFailure(call: retrofit2.Call<FriendResponse>, t: Throwable) {
            }
        })
    }

    private fun updateFriendRecyclerView() {
        selectedFriendAdapter.updateList(selectedFriends.toList())
        binding.rvAddGoalFriend.visibility = if (selectedFriends.isEmpty()) View.GONE else View.VISIBLE
    }

    private fun submitGoal() {
        val title = binding.etAddGoal.text.toString()
        val difficulty = starStates.count { it }.toFloat()

        val category = when (binding.tvAddGoalLong.text.toString()) {
            "반복" -> "repeated"
            "단기" -> "short-term"
            "장기" -> "long-term"
            else -> "repeated"
        }

        val description = binding.etAddGoalDetail.text.toString()
        val sharedGoal = isGoalShare
        val publicGoal = isGoalFriend

        val sharedGoalType = if (sharedGoal) {
            when (binding.tvAddGoalFriendType.text.toString()) {
                "협동" -> "co-operative"
                "경쟁" -> "competitive"
                else -> null
            }
        } else {
            null
        }

        val subGoalType = when {
            binding.tvAddGoalNormal.background.constantState == resources.getDrawable(R.drawable.shape_fill_blue1_10).constantState -> "normal"
            binding.tvAddGoalAddtional.background.constantState == resources.getDrawable(R.drawable.shape_fill_blue1_10).constantState -> "stepByStep"
            binding.tvAddGoalCheck.background.constantState == resources.getDrawable(R.drawable.shape_fill_blue1_10).constantState -> "checkList"
            else -> "normal"
        }

        val repeatedGoal = category == "repeated"

        val dateInfo = if (repeatedGoal) {
            DateInfo(
                startAt = getCurrentDate(),
                expireAt = getCurrentDate(),
                repetitionPeriod = binding.tvAddGoalDay.text.toString().replace("일", "").toIntOrNull() ?: 0,
                repetitionNumber = binding.tvAddGoalMany.text.toString().replace("회", "").toIntOrNull() ?: 0
            )
        } else {
            DateInfo(
                startAt = binding.tvAddGoalDay.text.toString().replace(".", "-"),
                expireAt = binding.tvAddGoalMany.text.toString().replace(".", "-"),
                repetitionPeriod = 0,
                repetitionNumber = 0
            )
        }

        val friendList = selectedFriends.map { FriendRequest(it.userId) }

        val subGoalList = mutableListOf<SubGoal>()
        for (i in 0 until binding.rvAddGoal.childCount) {
            val viewHolder = binding.rvAddGoal.findViewHolderForAdapterPosition(i) as? GoalAdapter.GoalViewHolder
            if (viewHolder != null) {
                val subGoalDesc = viewHolder.binding.etGoalDetail.text.toString()
                val subGoalDifficulty = viewHolder.starStates.count { it }.toFloat()
                subGoalList.add(SubGoal(index = i, difficulty = subGoalDifficulty, description = subGoalDesc))
            }
        }

        val goalRequest = GoalRequest(
            title = title,
            difficulty = difficulty,
            category = category,
            description = description,
            sharedGoal = sharedGoal,
            sharedGoalType = sharedGoalType,
            publicGoal = publicGoal,
            subGoalType = subGoalType,
            dateInfo = dateInfo,
            friendList = friendList,
            subGoalList = subGoalList,
            repeatedGoal = repeatedGoal
        )

        RetrofitClient.goalService.createGoal("Bearer $accessToken", goalRequest)
            .enqueue(object : retrofit2.Callback<GoalResponse> {
                override fun onResponse(call: Call<GoalResponse>, response: Response<GoalResponse>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                        }
                    }
                }

                override fun onFailure(call: Call<GoalResponse>, t: Throwable) {
                }
            })
    }

    private fun getCurrentDate(): String {
        val sdf = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
        return sdf.format(java.util.Date())
    }
}
