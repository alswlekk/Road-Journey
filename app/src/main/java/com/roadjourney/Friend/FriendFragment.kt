package com.roadjourney.Friend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.roadjourney.R
import com.roadjourney.databinding.FragmentFriendBinding


class FriendFragment : Fragment() {
    lateinit var binding: FragmentFriendBinding
    private lateinit var friendAdapter: FriendAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentFriendBinding.inflate(inflater, container, false)

        friendAdapter = FriendAdapter(emptyList())
        binding.rvFriend.adapter = friendAdapter
        binding.rvFriend.layoutManager = LinearLayoutManager(requireContext())

        updateRecyclerViewData(getFriendData())


        return binding.root
    }

    private fun getFriendData(): List<FriendData> {
        return listOf(
            FriendData("1시간 전", 34, "가나다", R.drawable.img_friend_profile1, "성공은 도전하는 자에게만 찾아온다."),
            FriendData("19시간 전", 34, "거너더", R.drawable.img_friend_profile2, "오늘도 열심히!"),
            FriendData("2일 전", 49, "고노도", R.drawable.img_friend_profile3, "끝까지 가면 내가 다 이겨"),
            FriendData("2주일 전", 3, "구누두", R.drawable.img_friend_profile4, "쉬고 싶다..."),

            )

    }

    private fun updateRecyclerViewData(data: List<FriendData>) {
        friendAdapter.updateData(data)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val typeCategories = listOf("최근 접속", "가나다 순", "달성 목표 수")
        setupSpinner(binding.spSort, typeCategories)

    }

    private fun setupSpinner(spSort: Spinner, typeCategories: List<String>) {
        val adapter = object : ArrayAdapter<String>(
            requireContext(),
            R.layout.spinner_item, typeCategories
        ) {

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
            ): View {
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