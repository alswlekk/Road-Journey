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
import com.roadjourney.databinding.FragmentChangeEmailBinding

class ChangeEmailFragment : Fragment() {
    lateinit var binding: FragmentChangeEmailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChangeEmailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val isAllFilled = binding.etEmailChange.text.isNotEmpty()

                if (isAllFilled) {
                    binding.btnCompleteFilled.visibility = View.VISIBLE
                    binding.btnCompleteUnfilled.visibility = View.GONE
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        }
        binding.etEmailChange.addTextChangedListener(textWatcher)
        binding.btnCompleteFilled.setOnClickListener {
            addChangeEmailDialog()
        }

        binding.ivChangeEmailBack.setOnClickListener {
            // 클릭 시 MyPageFragment로 이동
            requireActivity().finish()
        }
    }

    private fun addChangeEmailDialog() {
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