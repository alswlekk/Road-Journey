package com.roadjourney.CreateAccount

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import com.roadjourney.Login.LoginActivity
import com.roadjourney.Login.Model.SignupData
import com.roadjourney.Login.Model.SignupResponseData
import com.roadjourney.R
import com.roadjourney.Retrofit.RetrofitObject
import com.roadjourney.Retrofit.Service.LoginService
import com.roadjourney.databinding.DialogSuccessCreateAccountBinding
import com.roadjourney.databinding.FragmentCreateAccount3Binding
import retrofit2.Call
import retrofit2.Response

class CreateAccount3Fragment : Fragment() {
    private var _binding: FragmentCreateAccount3Binding? = null
    private val binding get() = _binding!!

    private var selectedImageUri: Uri? = null

    private val imagePickerLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            selectedImageUri = it
            binding.ivProfile.setImageURI(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateAccount3Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAddPhoto.setOnClickListener {
            imagePickerLauncher.launch("image/*")
        }

        binding.btnFinish.setOnClickListener {
            val name = arguments?.getString("name").toString()
            val stateMessage = binding.etStateMessage.text.toString()

            // 선택한 이미지가 있을 경우 Uri 저장
            selectedImageUri?.let { uri ->
                saveProfileImageUri(requireContext(), uri.toString())
            }

            val nextFragment = CreateAccount1Fragment()
            val bundle = Bundle()
            bundle.putString("name", name)
            bundle.putString("stateMessage", stateMessage)
            nextFragment.arguments = bundle

            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.fcv_create_account, nextFragment)
                .addToBackStack(null)
                .commit()
        }
    }

    private fun saveProfileImageUri(context: Context, uriString: String) {
        val sharedPref = context.getSharedPreferences("UserProfile", Context.MODE_PRIVATE)
        sharedPref.edit().putString("profileImageUri", uriString).apply()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
