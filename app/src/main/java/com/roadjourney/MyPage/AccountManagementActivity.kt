package com.roadjourney.MyPage

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.roadjourney.R
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
                .replace(R.id.fcv_change_account, CheckPwFragment())
                .commit()
        }
    }

    private fun clickEmailChangeButton() {
        binding.tvAccountManageEmailChange.setOnClickListener {
            binding.fcvChangeAccount.visibility = View.VISIBLE
            supportFragmentManager.beginTransaction()
                .replace(R.id.fcv_change_account, CheckPwFragment())
                .commit()
    }
    }

    private fun clickIdChangeButton() {
        binding.tvAccountManageIdChange.setOnClickListener {
            binding.fcvChangeAccount.visibility = View.VISIBLE
            supportFragmentManager.beginTransaction()
                .replace(R.id.fcv_change_account, CheckPwFragment())
                .commit()
        }
    }

    private fun clickPwChangeButton() {
        binding.tvAccountManagePwChange.setOnClickListener {
            binding.fcvChangeAccount.visibility = View.VISIBLE
            supportFragmentManager.beginTransaction()
                .replace(R.id.fcv_change_account, CheckPwFragment())
                .commit()
        }
    }

    private fun clickBackButton() {
        binding.ivAccountManageBack.setOnClickListener {
            finish()
        }
    }
}