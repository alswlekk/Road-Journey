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
import com.roadjourney.R
import com.roadjourney.databinding.FragmentShopBinding


class ShopFragment : Fragment() {
    lateinit var binding: FragmentShopBinding
    private lateinit var shopAdapter: ShopAdapter
    private lateinit var allItems: List<ShopItem>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentShopBinding.inflate(layoutInflater)

        val typeCategories = listOf("전체", "배경", "장식품", "캐릭터")
        setupSpinner(binding.spShop, typeCategories, 110)
        setupRecyclerView()

        binding.ivShopEvent.setOnClickListener {
            val intent = Intent(requireContext(), ShopActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }

    private fun setupRecyclerView() {
        allItems = getDummyItems()
        shopAdapter = ShopAdapter(allItems, requireContext())
        binding.rvShop.adapter = shopAdapter
    }

    private fun getDummyItems(): List<ShopItem> {
        return listOf(
            ShopItem(R.drawable.img_sky, "밤하늘", "2500", "배경"),
            ShopItem(R.drawable.img_forest, "숲", "700", "배경"),
            ShopItem(R.drawable.img_flower, "벚꽃길", "1400", "배경"),
            ShopItem(R.drawable.img_sky, "하늘밤", "2500", "장식품"),
            ShopItem(R.drawable.img_flower, "벚꽃길", "3000", "장식품"),
            ShopItem(R.drawable.img_flower, "마법사2", "5000", "캐릭터"),
            ShopItem(R.drawable.img_flower, "마법사1", "4500", "캐릭터")
        )
    }

    private fun setupSpinner(spinner: Spinner, categories: List<String>, left: Int) {
        val adapter =
            object : ArrayAdapter<String>(requireContext(), R.layout.spinner_item, categories) {
                override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                    val view = super.getView(position, convertView, parent) as TextView
                    view.setBackgroundResource(R.drawable.spinner_background)
                    view.textAlignment = View.TEXT_ALIGNMENT_TEXT_START
                    view.setTextColor(resources.getColor(R.color.white, null))
                    view.textSize = 16f
                    view.setPadding(left, 0, 0, 0)
                    view.typeface = resources.getFont(R.font.samliphopangche)
                    return view
                }

                override fun getDropDownView(
                    position: Int,
                    convertView: View?,
                    parent: ViewGroup
                ): View {
                    val view = super.getDropDownView(position, convertView, parent) as TextView
                    when (position) {
                        0 -> view.setBackgroundResource(R.drawable.spinner_dropdown_background_top)
                        count - 1 -> view.setBackgroundResource(R.drawable.spinner_dropdown_background_bottom)
                    }
                    view.textAlignment = View.TEXT_ALIGNMENT_CENTER
                    view.setTextColor(resources.getColor(R.color.white, null))
                    view.textSize = 16f
                    view.typeface = resources.getFont(R.font.samliphopangche)
                    return view
                }
            }
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedCategory = categories[position]
                filterItems(selectedCategory)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun filterItems(category: String) {
        val filteredItems = if (category == "전체") {
            allItems
        } else {
            allItems.filter { it.category == category }
        }
        shopAdapter.updateItems(filteredItems)
    }
}
