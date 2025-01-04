package com.roadjourney

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.roadjourney.databinding.FragmentFriendBinding


class FriendFragment : Fragment() {
    lateinit var binding: FragmentFriendBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentFriendBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val typeCategories = listOf("최근 접속", "가나다 순", "달성 목표 수")
        setupSpinner(binding.spSort, typeCategories)

    }

    private fun setupSpinner(spSort: Spinner, typeCategories: List<String>) {
        val adapter = object : ArrayAdapter<String>(requireContext(), R.layout.spinner_item, typeCategories) {

            override fun getView(position: Int, converView: View?, parent: ViewGroup): View {
                val view = super.getView(position, converView, parent) as TextView
                view.setBackgroundResource(R.drawable.spinner_background)
                view.textAlignment = View.TEXT_ALIGNMENT_TEXT_START
                view.setTextColor(resources.getColor(R.color.white, null))
                view.textSize = 15f
                view.typeface = resources.getFont(R.font.samliphopangche)
                return view
            }

            override fun getDropDownView(
                position: Int,
                converView: View?,
                parent: ViewGroup
            ) : View {
                val view = super.getDropDownView(position, converView, parent) as TextView

                when (position) {
                    0 -> view.setBackgroundResource(R.drawable.spinner_dropdown_background_top)
                    count - 1 -> view.setBackgroundResource(R.drawable.spinner_dropdown_background_bottom)
                }

                view.textAlignment = View.TEXT_ALIGNMENT_CENTER
                view.setTextColor(resources.getColor(R.color.white, null))
                view.textSize = 15f
                view.typeface = resources.getFont(R.font.samliphopangche)
                return view
            }

        }

        spSort.adapter = adapter
        spSort.setSelection(0)
    }
}