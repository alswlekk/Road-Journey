package com.roadjourney.MyPage

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.roadjourney.R
import com.roadjourney.SharedViewModel
import com.roadjourney.databinding.ActivityItemBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ItemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityItemBinding
    private lateinit var storageItemAdapter: StorageItemAdapter
    private val baseUrl = "http://52.78.84.107:8080"
    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSpinner()
        setupRecyclerView()
        setupClickListeners()

        val receivedToken = intent.getStringExtra("accessToken") ?: ""
        if (receivedToken.isNotEmpty()) {
            sharedViewModel.setUserData(receivedToken, -1)
        }

        sharedViewModel.accessToken.observe(this) { token ->
            if (!token.isNullOrEmpty()) {
                fetchItems("all", token)
            }
        }
    }

    private fun setupSpinner() {
        val categories = listOf("전체", "배경", "장식품", "캐릭터")

        val adapter = object : ArrayAdapter<String>(this, R.layout.spinner_item, categories) {
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

        binding.spItem.adapter = adapter
        binding.spItem.setSelection(0)

        binding.spItem.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedCategory = when (categories[position]) {
                    "배경" -> "wallpaper"
                    "장식품" -> "ornament"
                    "캐릭터" -> "character"
                    else -> "all"
                }

                sharedViewModel.accessToken.value?.let { token ->
                    if (!token.isNullOrEmpty()) {
                        fetchItems(selectedCategory, token)
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun setupRecyclerView() {
        storageItemAdapter = StorageItemAdapter(listOf())
        binding.rvItem.apply {
            layoutManager = GridLayoutManager(this@ItemActivity, 3)
            adapter = storageItemAdapter
        }
    }

    private fun setupClickListeners() {
        binding.ivItemBack.setOnClickListener {
            finish()
        }
    }

    private fun fetchItems(category: String, token: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = ItemApi.getInstance(baseUrl, token).getStorageItems(category,"Bearer $token")
                withContext(Dispatchers.Main) {
                    if (response.items.isNotEmpty()) {
                        storageItemAdapter.updateItems(response.items)
                    }
                }
            } catch (e: Exception) {
            }
        }
    }
}
