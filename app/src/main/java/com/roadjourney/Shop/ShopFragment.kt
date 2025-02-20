package com.roadjourney.Shop

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.roadjourney.R
import com.roadjourney.SharedViewModel
import com.roadjourney.databinding.FragmentShopBinding
import kotlinx.coroutines.launch

class ShopFragment : Fragment() {
    private lateinit var binding: FragmentShopBinding
    private lateinit var shopAdapter: ShopAdapter
    private var allItems: List<ShopItem> = listOf()

    private val baseUrl = "http://52.78.84.107:8080"
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentShopBinding.inflate(inflater, container, false)

        val typeCategories = listOf("전체", "배경", "장식품", "캐릭터")
        setupSpinner(binding.spShop, typeCategories)
        setupRecyclerView()

        binding.ivShopEvent.setOnClickListener {
            val intent = Intent(requireContext(), ShopActivity::class.java)
            intent.putExtra("availableGold", binding.tvShopCoin.text.toString().toInt())
            sharedViewModel.accessToken.value?.let { token ->
                intent.putExtra("accessToken", token)
            }
            startActivity(intent)
        }

        sharedViewModel.accessToken.observe(viewLifecycleOwner) { token ->
            if (token.isNotEmpty()) {
                fetchShopItems("all", token)
            }
        }

        return binding.root
    }

    private fun setupRecyclerView() {
        shopAdapter = ShopAdapter(
            listOf(),
            requireContext(),
            sharedViewModel,
            ::updateShopItems
        )
        binding.rvShop.adapter = shopAdapter
    }

    private fun updateShopItems() {
        sharedViewModel.accessToken.value?.let { token ->
            if (token.isNotEmpty()) {
                fetchShopItems("all", token)
            }
        }
    }

    private fun setupSpinner(spinner: Spinner, categories: List<String>) {
        val adapter = object : ArrayAdapter<String>(requireContext(), R.layout.spinner_item, categories) {

            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent) as TextView
                view.setBackgroundResource(R.drawable.spinner_background)
                view.textAlignment = View.TEXT_ALIGNMENT_CENTER
                view.setTextColor(resources.getColor(R.color.white, null))
                view.textSize = 20f
                view.setPadding(0, 0, 0, 0)
                view.typeface = resources.getFont(R.font.samliphopangche)
                return view
            }

            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getDropDownView(position, convertView, parent) as TextView

                when (position) {
                    0 -> view.setBackgroundResource(R.drawable.spinner_dropdown_background_top)
                    count - 1 -> view.setBackgroundResource(R.drawable.spinner_dropdown_background_bottom)
                }

                view.textAlignment = View.TEXT_ALIGNMENT_CENTER
                view.setTextColor(resources.getColor(R.color.white, null))
                view.textSize = 20f
                view.typeface = resources.getFont(R.font.samliphopangche)

                return view
            }
        }

        spinner.adapter = adapter
        spinner.setSelection(0)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedCategory = when (categories[position]) {
                    "배경" -> "wallpaper"
                    "장식품" -> "ornament"
                    "캐릭터" -> "character"
                    else -> "all"
                }

                sharedViewModel.accessToken.value?.let { token ->
                    fetchShopItems(selectedCategory, token)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun fetchShopItems(category: String, token: String) {
        lifecycleScope.launch {
            try {
                val response = ShopApi.getInstance(baseUrl, token).getShopItems(category, "Bearer $token")

                allItems = response.items.map { item ->
                    item.copy(
                        category = changeCategory(item.category),
                        imageRes = changeImageResource(item.itemName)
                    )
                }

                binding.tvShopCoin.text = (response.availableGold.toLong()).toString()

                shopAdapter.updateItems(allItems)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

private fun changeCategory(apiCategory: String): String {
    return when (apiCategory) {
        "wallpaper" -> "배경"
        "ornament" -> "장식품"
        "character" -> "캐릭터"
        else -> "전체"
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
