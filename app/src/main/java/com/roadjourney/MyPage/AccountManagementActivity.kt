package com.roadjourney.MyPage

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import com.roadjourney.R
import com.roadjourney.SharedViewModel
import com.roadjourney.databinding.ActivityAccountManagementBinding

class AccountManagementActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAccountManagementBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAccountManagementBinding.inflate(layoutInflater)
        setContentView(binding.root)

        clickBackButton()
        clickIdChangeButton()
        clickPwChangeButton()
        clickEmailChangeButton()
        clickNameChangeButton()
    }

    private fun clickNameChangeButton() {
        binding.tvAccountManageNameChange.setOnClickListener {
            binding.fcvChangeAccount.visibility = View.VISIBLE
            supportFragmentManager.beginTransaction()
                .replace(R.id.fcv_change_account, CheckPwNameFragment())
                .commit()
        }
    }

    private fun clickEmailChangeButton() {
        binding.tvAccountManageEmailChange.setOnClickListener {
            binding.fcvChangeAccount.visibility = View.VISIBLE
            supportFragmentManager.beginTransaction()
                .replace(R.id.fcv_change_account, CheckPwEmailFragment())
                .commit()
    }
    }

    private fun clickIdChangeButton() {
        binding.tvAccountManageIdChange.setOnClickListener {
            binding.fcvChangeAccount.visibility = View.VISIBLE
            supportFragmentManager.beginTransaction()
                .replace(R.id.fcv_change_account, CheckPwIdFragment())
                .commit()
        }
    }

    private fun clickPwChangeButton() {
        binding.tvAccountManagePwChange.setOnClickListener {
            binding.fcvChangeAccount.visibility = View.VISIBLE
            supportFragmentManager.beginTransaction()
                .replace(R.id.fcv_change_account, CheckPwPwFragment())
                .commit()
        }
    }

    private fun clickBackButton() {
        binding.ivAccountManageBack.setOnClickListener {
            finish()
        }
    }
}