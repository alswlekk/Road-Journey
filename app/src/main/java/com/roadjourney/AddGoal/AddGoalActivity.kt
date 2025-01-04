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
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.roadjourney.Home.HomeFragment
import com.roadjourney.R
import com.roadjourney.databinding.ActivityAddGoalBinding
import com.roadjourney.databinding.DialogGoalTypeBinding
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
                moveToFragment(HomeFragment())
            }
        }

        binding.tvAddGoalLong.setOnClickListener {
            goalTypeDialog()
        }

        binding.ivAddGoalShareBtn.setOnClickListener {
            isGoalShare = !isGoalShare
            updateToggleImage(binding.ivAddGoalShareBtn, isGoalShare)
        }

        binding.ivAddGoalFriendBtn.setOnClickListener {
            isGoalFriend = !isGoalFriend
            updateToggleImage(binding.ivAddGoalFriendBtn, isGoalFriend)
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
        val constraintSet = androidx.constraintlayout.widget.ConstraintSet()
        constraintSet.clone(binding.clAddGoal)

        if (isRecyclerViewVisible) {
            constraintSet.connect(
                R.id.cl_add_goal_share,
                androidx.constraintlayout.widget.ConstraintSet.TOP,
                R.id.iv_add_goal_minus,
                androidx.constraintlayout.widget.ConstraintSet.BOTTOM,
                60
            )
        } else {
            constraintSet.connect(
                R.id.cl_add_goal_share,
                androidx.constraintlayout.widget.ConstraintSet.TOP,
                R.id.tv_add_goal_normal,
                androidx.constraintlayout.widget.ConstraintSet.BOTTOM,
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

        dialogBinding.rgGoalType.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rb_repeat -> dialogBinding.tvAddGoalDate.text = "특정 일수마다 성공 여부에 관계없이\n자동으로 반복되는 목표입니다. 반복\n횟수와 간격을 지정할 수 있습니다.\n"
                R.id.rb_short -> dialogBinding.tvAddGoalDate.text = "단기간에 달성할 수 있거나, 달성해야\n하는 목표입니다. 쉽고 간단한 목표라도\n꾸준히 해나간다면 당신의 성장에 도움\n이 될 것입니다."
                R.id.rb_long -> dialogBinding.tvAddGoalDate.text = "달성하기 위해 오랫동안 노력을 기울여\n야 하는 목표입니다. 그만큼 성공했을\n때의 성취감이 더 크겠죠.\n"
            }
        }

        dialogBinding.tvProfileEditBtn.setOnClickListener {
            val selectedText = when (dialogBinding.rgGoalType.checkedRadioButtonId) {
                R.id.rb_repeat -> dialogBinding.rbRepeat.text.toString()
                R.id.rb_short -> dialogBinding.rbShort.text.toString()
                R.id.rb_long -> dialogBinding.rbLong.text.toString()
                else -> ""
            }
            binding.tvAddGoalLong.text = selectedText
            dialog.dismiss()
            showSaveDialog()
        }

        dialogBinding.ivGoalTypeCancel.setOnClickListener {
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
        }

        saveDialog.show()
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
}
