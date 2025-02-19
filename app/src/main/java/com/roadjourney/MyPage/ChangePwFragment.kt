package com.roadjourney.MyPage

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.roadjourney.Login.LoginActivity
import com.roadjourney.R
import com.roadjourney.databinding.DialogSuccessChangeBinding
import com.roadjourney.databinding.FragmentChangePwBinding

class ChangePwFragment : Fragment() {
    lateinit var binding: FragmentChangePwBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChangePwBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                val isAllFilled = binding.etChangeAccountPw.text.isNotEmpty() &&
                        binding.etChangeAccountPwCheck.text.isNotEmpty()

                if (isAllFilled) {
                    binding.btnCompleteFilled.visibility = View.VISIBLE
                    binding.btnCompleteUnfilled.visibility = View.GONE
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        }
        binding.etChangeAccountPw.addTextChangedListener(textWatcher)
        binding.etChangeAccountPwCheck.addTextChangedListener(textWatcher)
        binding.btnCompleteFilled.setOnClickListener {
            if (binding.etChangeAccountPw.text.toString().equals(binding.etChangeAccountPwCheck.text.toString())) {
                val intent = Intent(requireContext(), LoginActivity::class.java)
                startActivity(intent)
            } else {
                binding.llChangeAccountPwCheck.visibility = View.VISIBLE
            }
        }

        binding.ivChangePwBack.setOnClickListener {
            requireActivity().finish()
        }

        binding.btnCompleteFilled.setOnClickListener {
            addChangePwDialog()
        }
    }

    private fun addChangePwDialog() {
        val dialogSuccessChangeBinding  = DialogSuccessChangeBinding.inflate(LayoutInflater.from(requireContext()))

        val successChangeDialog = AlertDialog
            .Builder(requireContext())
            .setView(dialogSuccessChangeBinding.root)
            .create()

        dialogSuccessChangeBinding.btnFinish.setOnClickListener {
            successChangeDialog.dismiss()
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
        }
        successChangeDialog.show()
    }
}