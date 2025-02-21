package com.roadjourney.Friend

import android.app.AlertDialog
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.roadjourney.R
import com.roadjourney.Retrofit.RetrofitObject
import com.roadjourney.Retrofit.Service.FriendManageService
import com.roadjourney.SharedViewModel
import com.roadjourney.databinding.DialogFriendBinding
import com.roadjourney.databinding.ItemFriendBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.log

class FriendAdapter(var items: FriendsData, private val token: String?) :
    RecyclerView.Adapter<FriendAdapter.FriendViewHolder>() {
    private var itemClickListener: ((FriendData) -> Unit)? = null

    inner class FriendViewHolder(private val binding: ItemFriendBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: FriendData) {
            with(binding) {
                tvFriendRcTime.text = "최근 접속 : ${getTimeAgo(item.lastLoginTime)}"
                tvFriendRcName.text = item.nickname
                tvFriendRcQuote.text = item.statusMessage
                tvFriendRcGoal.text = "달성 목표: ${item.achievementCount}개"

                // 프로필 이미지 로드
                Glide.with(ivFriendRcProfile.context)
                    .load(item.profileImage)
                    .placeholder(R.drawable.img_friend_profile1)
                    .error(R.drawable.img_friend_profile1)
                    .circleCrop()
                    .into(ivFriendRcProfile)
            }
        }

        init {
            itemView.setOnClickListener {
                val item = items.friends[adapterPosition]
                itemClickListener?.invoke(item) ?: showDialog(item)
            }
        }

        private fun showDialog(item: FriendData) {
            val context = itemView.context
            val friendUserId = item.userId

            val service = RetrofitObject.retrofit.create(FriendManageService::class.java)
            val authToken = "Bearer $token"
            Log.d("FriendAdapter", "Auth Token: $authToken")
            Log.d("FriendAdapter", "FriendUserId: $friendUserId")

            val call = service.getEnableViewFriendMain(friendUserId, null, authToken)

            val friendDialogBinding = DialogFriendBinding.inflate(LayoutInflater.from(context))

            friendDialogBinding.apply {
                tvFriendPopupName.text = item.nickname
                tvFriendPopupQuote.text = item.statusMessage

                Glide.with(context)
                    .load(item.profileImage)
                    .error(R.drawable.img_friend_popup_profile)
                    .into(ivFriendPopupProfile)

                Log.d("FriendAdapter", "Profile Image: ${item.profileImage}")

                call.enqueue(object : Callback<EnableFriendMainData> {
                    override fun onResponse(
                        call: Call<EnableFriendMainData>,
                        response: Response<EnableFriendMainData>
                    ) {
                        Log.d("FriendAdapter", "HTTP Response Code: ${response.code()}")
                        Log.d("FriendAdapter", "Response Body: ${response.body()}")

                        if (response.isSuccessful) {
                            val enableResponse = response.body()
                            if (enableResponse != null) {
                                Log.d("FriendAdapter", "Response Data: $enableResponse")

                                if (enableResponse.activeStatus.equals("ENABLED")) {
                                    btnVisit.visibility = android.view.View.VISIBLE
                                    tvNoVisit.visibility = android.view.View.GONE
                                } else {
                                    btnVisit.visibility = android.view.View.GONE
                                    tvNoVisit.visibility = android.view.View.VISIBLE
                                }
                            } else {
                                btnVisit.visibility = android.view.View.GONE
                                tvNoVisit.visibility = android.view.View.VISIBLE
                                Log.d("FriendAdapter", "response.body is null")
                            }
                        } else {
                            btnVisit.visibility = android.view.View.GONE
                            tvNoVisit.visibility = android.view.View.VISIBLE
                            Log.d("FriendAdapter", "response is not successful, error: ${response.errorBody()?.string()}")
                        }
                    }

                    override fun onFailure(call: Call<EnableFriendMainData>, t: Throwable) {
                        btnVisit.visibility = android.view.View.GONE
                        tvNoVisit.visibility = android.view.View.VISIBLE
                        Log.d("FriendAdapter", "onFailure: ${t.message}")
                    }
                })

                val dialog = AlertDialog.Builder(context)
                    .setView(root)
                    .create()

                ivFriendPopupClose.setOnClickListener { dialog.dismiss() }
                btnVisit.setOnClickListener {
                    context.startActivity(Intent(context, FriendProfileActivity::class.java))
                    dialog.dismiss()
                }

                dialog.show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        val binding = ItemFriendBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FriendViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        holder.bind(items.friends[position])
    }

    override fun getItemCount(): Int = items.friends.size

    fun updateData(data: List<FriendData>) {
       items.friends = data
        notifyDataSetChanged()
    }


    // 2025-02-13 17:39:11 -> "1시간 전", "5분 전" 등의 형식으로 변환
    private fun getTimeAgo(lastLoginTime: String): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val lastLogin = LocalDateTime.parse(lastLoginTime, formatter)
        val now = LocalDateTime.now()
        val duration = Duration.between(lastLogin, now)

        return when {
            duration.toMinutes() < 1 -> "방금 전"
            duration.toMinutes() < 60 -> "${duration.toMinutes()}분 전"
            duration.toHours() < 24 -> "${duration.toHours()}시간 전"
            duration.toDays() < 7 -> "${duration.toDays()}일 전"
            duration.toDays() < 14 -> "2주 전"
            duration.toDays() < 21 -> "3주 전"
            duration.toDays() < 28 -> "한 달 전"
            duration.toDays() < 60 -> "2달 전"
            duration.toDays() < 90 -> "3달 전"
            duration.toDays() < 365 -> "${duration.toDays() / 30}달 전"
            else -> "${duration.toDays() / 365}년 전"
        }
    }
}
