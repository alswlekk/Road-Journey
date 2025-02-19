package com.roadjourney.Shop

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.roadjourney.SharedViewModel
import com.roadjourney.databinding.ActivityShopBinding
import com.roadjourney.databinding.DialogItemListBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ShopActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShopBinding
    private val baseUrl = "http://52.78.84.107:8080"
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedViewModel = ViewModelProvider(this)[SharedViewModel::class.java]

        val availableGold = intent.getIntExtra("availableGold", 0)
        binding.tvShopCoin.text = availableGold.toString()

        val receivedToken = intent.getStringExtra("accessToken") ?: ""
        if (receivedToken.isNotEmpty()) {
            sharedViewModel.setUserData(receivedToken, -1)
        }

        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.ivShopBack.setOnClickListener {
            finish()
        }

        binding.tvShopBtn.setOnClickListener {
            showItemListDialog()
        }
    }

    private fun showItemListDialog() {
        val dialogBinding = DialogItemListBinding.inflate(layoutInflater)

        val dialog = androidx.appcompat.app.AlertDialog.Builder(this)
            .setView(dialogBinding.root)
            .setCancelable(true)
            .create()

        dialogBinding.ivItemListClose.setOnClickListener {
            dialog.dismiss()
        }

        val backgroundAdapter = ShopEventAdapter()
        val characterAdapter = ShopEventAdapter()
        val ornamentAdapter = ShopEventAdapter()

        dialogBinding.rvItemBackground.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = backgroundAdapter
        }

        dialogBinding.rvItemCharacter.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = characterAdapter
        }

        dialogBinding.rvItemDeco.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = ornamentAdapter
        }

        sharedViewModel.accessToken.observe(this) { token ->
            if (!token.isNullOrEmpty()) {
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val response = ShopApi.getInstance(baseUrl, token).getSpecialShopItems("Bearer $token")

                        withContext(Dispatchers.Main) {
                            if (response != null) {
                                val items = response.specialItems

                                val backgrounds = items.filter { it.category == "wallpaper" }
                                val characters = items.filter { it.category == "character" }
                                val ornaments = items.filter { it.category == "ornament" }

                                backgroundAdapter.updateItems(backgrounds)
                                characterAdapter.updateItems(characters)
                                ornamentAdapter.updateItems(ornaments)
                            }
                        }
                    } catch (e: Exception) {
                    }
                }
            }
        }
        dialog.show()
    }
}
