package com.roadjourney.MyPage

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.roadjourney.R
import com.roadjourney.SharedViewModel
import com.roadjourney.databinding.FragmentCheckPwIdBinding

class CheckPwIdFragment : Fragment() {
    lateinit var binding: FragmentCheckPwIdBinding
    private val sharedViewModel : SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCheckPwIdBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val isAllFilled = binding.etPwCheck.text.isNotEmpty()

                if (isAllFilled) {
                    binding.btnNextFilled.visibility = View.VISIBLE
                    binding.btnNextUnfilled.visibility = View.GONE
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        }
        binding.etPwCheck.addTextChangedListener(textWatcher)
        binding.btnNextFilled.setOnClickListener {
            val id = sharedViewModel.userId.value
            val pw = binding.etPwCheck.text.toString()

            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.fcv_change_account, ChangeIdFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.ivCheckPwBack.setOnClickListener() {
            requireActivity().finish()
        }
    }

}