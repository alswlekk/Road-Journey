package com.roadjourney.AddGoal

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.roadjourney.Home.HomeFragment
import com.roadjourney.R
import com.roadjourney.databinding.ActivityAddGoalBinding
import com.roadjourney.databinding.DialogAddFriendBinding
import com.roadjourney.databinding.DialogCalendarBinding
import com.roadjourney.databinding.DialogGoalTypeBinding
import com.roadjourney.databinding.DialogRequestBinding
import com.roadjourney.databinding.DialogSaveBinding

class AddGoalActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddGoalBinding
    private val starStates = BooleanArray(5)
    private var isGoalShare = false
    private var isGoalFriend = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddGoalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        selectedFriendAdapter = SelectedFriendAdapter(selectedFriends)
        binding.rvAddGoalFriend.layoutManager = LinearLayoutManager(this)
        binding.rvAddGoalFriend.adapter = selectedFriendAdapter

        setupClickListeners()
        setupTextWatchers()
        setupRecyclerView()
    }


    private fun setupClickListeners() {
        binding.ivAddGoalBack.setOnClickListener { finish() }

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

        dialogBinding.rbRepeat.isChecked = true
        updateGoalTypeVisibility(dialogBinding, R.id.rb_repeat)

        dialogBinding.rgGoalType.setOnCheckedChangeListener { _, checkedId ->
            updateGoalTypeVisibility(dialogBinding, checkedId)
        }

        dialogBinding.tvProfileEditBtn.setOnClickListener {
            val selectedText = when (dialogBinding.rgGoalType.checkedRadioButtonId) {
                R.id.rb_repeat -> dialogBinding.rbRepeat.text.toString()
                R.id.rb_short -> dialogBinding.rbShort.text.toString()
                R.id.rb_long -> dialogBinding.rbLong.text.toString()
                else -> ""
            }

            binding.tvAddGoalLong.text = selectedText

            when (dialogBinding.rgGoalType.checkedRadioButtonId) {
                R.id.rb_repeat -> {
                    val goalDay = dialogBinding.etAddGoalDay.text.toString().ifEmpty { "1" }
                    val goalHow = dialogBinding.etAddGoalHow.text.toString().ifEmpty { "3" }

                    binding.tvAddGoalDay.text = "${goalDay}일"
                    binding.tvAddGoalEach.text = "마다"
                    binding.tvAddGoalMany.text = "${goalHow}회"
                    binding.tvAddGoalRepeat.text = "반복"
                }
                R.id.rb_short, R.id.rb_long -> {
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
        if (selectedId == R.id.rb_repeat) {
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
            "${day.year}년 ${String.format("%02d", day.month + 1)}월"
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
            moveToFragment(HomeFragment())
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
            moveToFragment(HomeFragment())
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

    private fun moveToFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main, fragment)
            .addToBackStack(null)
            .commit()
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

        val allFriends = listOf(
            Friend(R.drawable.img_friend_profile1, "김철수", "kim123"),
            Friend(R.drawable.img_friend_profile1, "이영희", "lee1456"),
            Friend(R.drawable.img_friend_profile1, "박민수", "park1789"),
            Friend(R.drawable.img_friend_profile1, "정하늘", "skyblue1"),
            Friend(R.drawable.img_friend_profile1, "한지민", "hanji199")
        )

        val tempSelectedFriends = mutableSetOf<Friend>()

        val friendAdapter = FriendAdapter(allFriends) { friend, isAdded ->
            if (isAdded) {
                tempSelectedFriends.add(friend)
                dialogBinding.tvAddFriendBtn.isEnabled = true
                dialogBinding.tvAddFriendBtn.setBackgroundResource(R.drawable.shape_fill_blue1_25)
            } else {
                tempSelectedFriends.remove(friend)
                dialogBinding.tvAddFriendBtn.isEnabled = false
                dialogBinding.tvAddFriendBtn.setBackgroundResource(R.drawable.shape_fill_gray3_25)
            }
        }

        dialogBinding.rvAddFriend.layoutManager = LinearLayoutManager(this)
        dialogBinding.rvAddFriend.adapter = friendAdapter

        dialogBinding.ivAddFriendCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialogBinding.ivAddFriendSearch.setOnClickListener {
            val query = dialogBinding.etAddFriend.text.toString().trim()
            val filteredFriends = allFriends.filter {
                it.name.contains(query, ignoreCase = true) || it.id.contains(query, ignoreCase = true)
            }

            if (filteredFriends.isEmpty()) {
                dialogBinding.rvAddFriend.visibility = View.GONE
                dialogBinding.tvAddFriendNo.visibility = View.VISIBLE
            } else {
                dialogBinding.rvAddFriend.visibility = View.VISIBLE
                dialogBinding.tvAddFriendNo.visibility = View.GONE
                friendAdapter.updateList(filteredFriends)
            }
        }

        dialogBinding.tvAddFriendBtn.setOnClickListener {
            selectedFriends.clear()
            selectedFriends.addAll(tempSelectedFriends)
            updateFriendRecyclerView()
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun updateFriendRecyclerView() {
        selectedFriendAdapter.updateList(selectedFriends.toList())
        binding.rvAddGoalFriend.visibility = if (selectedFriends.isEmpty()) View.GONE else View.VISIBLE
    }

}
