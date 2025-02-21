import android.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.gms.common.api.Response
import com.roadjourney.AddGoal.Friend
import com.roadjourney.Friend.SendRequestData
import com.roadjourney.Friend.SendRequestResponse

import com.roadjourney.R
import com.roadjourney.Retrofit.RetrofitObject
import com.roadjourney.Retrofit.Service.FriendManageService
import com.roadjourney.databinding.DialogFriendSearchBinding
import com.roadjourney.databinding.ItemFriendSearchBinding
import retrofit2.Call
import retrofit2.Callback

class FriendSearchAdapter(
    private var friendList: List<Friend>,
    private val token : String?
) : RecyclerView.Adapter<FriendSearchAdapter.FriendSearchViewHolder>() {

    inner class FriendSearchViewHolder(val binding: ItemFriendSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Friend) {
            // Friend 객체를 UI에 바인딩
            binding.tvRcFriendId.text = item.accountId
            binding.tvRcFriendName.text = item.nickname

            Glide.with(binding.ivFriendRcProfile.context)
                .load(item.profileImage)
                .placeholder(R.drawable.img_friend_profile1)
                .error(R.drawable.img_friend_profile1)
                .circleCrop()
                .into(binding.ivFriendRcProfile)

            binding.tvRcFriendBtn.setOnClickListener {
                Log.d("FriendSearchAdapter", "token : $token")
                val dialogBinding = DialogFriendSearchBinding.inflate(LayoutInflater.from(itemView.context))
                dialogBinding.tvFriendPopupName.text = item.nickname
                dialogBinding.tvFriendPopupId.text = item.accountId

                if (item.statusMessage.isNullOrEmpty() || item.statusMessage == "null") {
                    dialogBinding.tvFriendPopupQuote.text = "상태 메시지가 없습니다."
                } else {
                    dialogBinding.tvFriendPopupQuote.text = item.statusMessage
                }

                Glide.with(dialogBinding.ivFriendPopupProfile.context)
                    .load(item.profileImage)
                    .placeholder(R.drawable.img_friend_popup_profile)
                    .error(R.drawable.img_friend_popup_profile)
                    .circleCrop()
                    .into(dialogBinding.ivFriendPopupProfile)

                val dialog = AlertDialog.Builder(itemView.context)
                    .setView(dialogBinding.root)
                    .create()

                dialogBinding.ivFriendPopupClose.setOnClickListener {
                    dialog.dismiss()
                }

                var isFriend = item.friendStatus
                Log.d("FriendSearchAdapter", "isFriend : $isFriend")
                if (isFriend.equals("IS_NOT_FRIEND")) {
                    dialogBinding.btnFriendRequest.visibility = View.VISIBLE
                    dialogBinding.tvFriendRequestWait.visibility = View.GONE
                    dialogBinding.tvFriendAlready.visibility = View.GONE
                } else if (isFriend.equals("PENDING")) {
                    dialogBinding.btnFriendRequest.visibility = View.GONE
                    dialogBinding.tvFriendRequestWait.visibility = View.VISIBLE
                    dialogBinding.tvFriendAlready.visibility = View.GONE
                } else if (isFriend.equals("IS_FREIND")) {
                    dialogBinding.btnFriendRequest.visibility = View.GONE
                    dialogBinding.tvFriendRequestWait.visibility = View.GONE
                    dialogBinding.tvFriendAlready.visibility = View.VISIBLE
                }

                dialogBinding.btnFriendRequest.setOnClickListener {
                    val authToken = "Bearer $token"
                    val service = RetrofitObject.retrofit.create(FriendManageService::class.java)
                    val call = service.postFriendRequest(authToken,SendRequestData(item.userId))
                    call.enqueue(object : Callback<SendRequestResponse> {
                        override fun onResponse(
                            call: Call<SendRequestResponse>,
                            response: retrofit2.Response<SendRequestResponse>
                        ) {
                            if (response.isSuccessful) {
                                dialogBinding.btnFriendRequest.visibility = View.GONE
                                dialogBinding.tvFriendRequestWait.visibility = View.VISIBLE
                                Log.d("FriendSearchAdapter", "친구 요청 성공")
                            } else {
                                Log.d("FriendSearchAdapter", "친구 요청 실패 - 코드: ${response.code()}, 메시지: ${response.errorBody()?.string()}")
                            }
                        }

                        override fun onFailure(call: Call<SendRequestResponse>, t: Throwable) {
                            Log.d("FriendSearchAdapter", "친구 요청 실패<네트워크 오류>")
                        }

                    })
                }

                dialog.show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendSearchViewHolder {
        val binding = ItemFriendSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FriendSearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FriendSearchViewHolder, position: Int) {
        val friend = friendList[position]
        // 데이터를 ViewHolder에 바인딩
        holder.bind(friend)
    }

    override fun getItemCount(): Int = friendList.size

    fun updateList(newList: List<Friend>) {
        friendList = newList
        notifyDataSetChanged()  // 리스트 갱신 후 RecyclerView 업데이트
    }
}
