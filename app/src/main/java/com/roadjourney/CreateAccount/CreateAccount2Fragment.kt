package com.roadjourney.CreateAccount

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.roadjourney.R
import com.roadjourney.databinding.ActivityCreateAccountBinding
import com.roadjourney.databinding.FragmentCreateAccount2Binding
import com.roadjourney.databinding.FragmentShopBinding

class CreateAccount2Fragment : Fragment() {

    lateinit var binding: FragmentCreateAccount2Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateAccount2Binding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val isAllFilled = binding.etCreateAccountName.text.isNotEmpty()

                if (isAllFilled) {
                    binding.btnNextFilled.visibility = View.VISIBLE
                    binding.btnNextUnfilled.visibility = View.GONE
                } else {
                    binding.btnNextFilled.visibility = View.GONE
                    binding.btnNextUnfilled.visibility = View.VISIBLE
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        }

        binding.etCreateAccountName.addTextChangedListener(textWatcher)

        binding.btnNextFilled.setOnClickListener {
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.fcv_create_account, CreateAccount3Fragment())
                .addToBackStack(null)
                .commit()
        }
    }
}