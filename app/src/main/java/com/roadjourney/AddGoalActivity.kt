package com.roadjourney

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.roadjourney.Home.HomeFragment
import com.roadjourney.databinding.ActivityAddGoalBinding

class AddGoalActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddGoalBinding
    private val starStates = BooleanArray(5)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddGoalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListeners()
        setupTextWatchers()
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
}
