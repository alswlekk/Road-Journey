import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.roadjourney.AddGoal.Friend
import com.roadjourney.databinding.DialogFriendSearchBinding
import com.roadjourney.databinding.ItemFriendSearchBinding

class FriendSearchAdapter(
    private var friendList: List<Friend>,
) : RecyclerView.Adapter<FriendSearchAdapter.FriendSearchViewHolder>() {

    inner class FriendSearchViewHolder(val binding: ItemFriendSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Friend) {
            // Friend 객체를 UI에 바인딩
            binding.tvRcFriendId.text = item.id
            binding.tvRcFriendName.text = item.name
            binding.ivFriendRcProfile.setImageResource(item.imageRes)

            binding.tvRcFriendBtn.setOnClickListener {
                val dialogBinding = DialogFriendSearchBinding.inflate(LayoutInflater.from(itemView.context))
                dialogBinding.tvFriendPopupName.text = item.name
                dialogBinding.tvFriendPopupId.text = item.id
                dialogBinding.ivFriendPopupProfile.setImageResource(item.imageRes)

                val dialog = AlertDialog.Builder(itemView.context)
                    .setView(dialogBinding.root)
                    .create()

                dialogBinding.ivFriendPopupClose.setOnClickListener {
                    dialog.dismiss()
                }

                dialogBinding.btnFriendRequest.setOnClickListener {
                    dialogBinding.btnFriendRequest.visibility = View.GONE
                    dialogBinding.tvFriendRequestWait.visibility = View.VISIBLE
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
