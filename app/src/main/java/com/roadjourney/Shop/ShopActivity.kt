package com.roadjourney.Shop

import android.app.Dialog
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.roadjourney.R
import com.roadjourney.SharedViewModel
import com.roadjourney.databinding.ActivityShopBinding
import com.roadjourney.databinding.DialogBuyFailBinding
import com.roadjourney.databinding.DialogItemListBinding
import com.roadjourney.databinding.DialogItemSpecialBinding
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

        binding.ivShopIvent.setOnClickListener {
            purchaseSpecialItem()
        }
    }

    private fun purchaseSpecialItem() {
        sharedViewModel.accessToken.value?.let { token ->
            if (token.isNotEmpty()) {
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val response = ShopApi.getInstance(baseUrl, token).orderSpecialItem("Bearer $token")
                        withContext(Dispatchers.Main) {
                            if (response.isSuccessful) {
                                val specialOrderResponse = response.body()

                                specialOrderResponse?.let {
                                    binding.tvShopCoin.text = it.availableGold.toString()

                                    if (it.status == "success") {
                                        showSpecialItemDialog(it)
                                    } else if (it.status == "failed" && it.message == "골드가 부족합니다.") {
                                        showBuyFailDialog(it)
                                    }
                                }
                            }
                        }
                    } catch (e: Exception) {
                    }
                }
            }
        }
    }

    private fun showBuyFailDialog(orderResponse: SpecialOrderResponse) {
        val dialog = Dialog(this)
        val dialogBinding = DialogBuyFailBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)

        binding.tvShopCoin.text = orderResponse.availableGold.toString()

        dialogBinding.tvSaveBtn.setOnClickListener {
            dialog.dismiss()
        }

        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.show()
    }

    private fun showSpecialItemDialog(orderResponse: SpecialOrderResponse) {
        val dialog = Dialog(this)
        val dialogBinding = DialogItemSpecialBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)

        val itemName = orderResponse.itemName
        val imageRes = changeImageResource(itemName)

        dialogBinding.ivShop.setImageResource(imageRes)
        dialogBinding.tvItemName.text = orderResponse.itemName
        dialogBinding.tvItemDetail.text = orderResponse.description

        when (orderResponse.message) {
            "새로운 특별 아이템이 지급되었습니다." -> {
                dialogBinding.tvItem.text = "축하합니다!"
                dialogBinding.tvItem.setTextColor(ContextCompat.getColor(this, R.color.blue2))
                dialogBinding.tvItemSpecial.text = "새로운 아이템입니다"
                dialogBinding.tvItemSpecial.setTextColor(ContextCompat.getColor(this, R.color.blue6))
            }
            "이미 보유한 아이템이 선택되었습니다. 골드만 차감됩니다." -> {
                dialogBinding.tvItem.text = "이런!"
                dialogBinding.tvItem.setTextColor(ContextCompat.getColor(this, R.color.red3))
                dialogBinding.tvItemSpecial.text = "이미 보유한 아이템입니다"
                dialogBinding.tvItemSpecial.setTextColor(ContextCompat.getColor(this, R.color.red4))
            }
        }

        binding.tvShopCoin.text = orderResponse.availableGold.toString()

        dialogBinding.ivItemClose.setOnClickListener {
            dialog.dismiss()
        }

        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.show()
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

private fun changeImageResource(itemName: String): Int {
    return when (itemName) {
        "밤하늘" -> R.drawable.img_sky
        "기본 방" -> R.drawable.img_normal
        "바다" -> R.drawable.img_ocean
        "에펠탑" -> R.drawable.img_tower
        "숲" -> R.drawable.img_forest
        "벚꽃길" -> R.drawable.img_flower
        "설원" -> R.drawable.img_snow
        "음료수" -> R.drawable.img_juice
        "화분" -> R.drawable.img_pot
        "게임기" -> R.drawable.img_game
        "스마트폰" -> R.drawable.img_phone
        "산타 양말" -> R.drawable.img_socks
        "책" -> R.drawable.img_book
        "베개" -> R.drawable.img_pillow
        "강아지" -> R.drawable.img_dog
        "다람쥐" -> R.drawable.img_squirrel
        "여우" -> R.drawable.img_fox
        "펭귄" -> R.drawable.img_penguin
        else -> R.drawable.img_normal
    }
}
