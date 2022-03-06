package dev.syorito_hatsuki.onlyone.ui.dialogs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import coil.Coil
import coil.ImageLoader
import coil.load
import coil.request.CachePolicy
import coil.transform.CircleCropTransformation
import dev.syorito_hatsuki.onlyone.R
import dev.syorito_hatsuki.onlyone.api.user.get_users.response.Dialog
import dev.syorito_hatsuki.onlyone.databinding.ItemDialogBinding

typealias OnItemClickListener = (Int) -> Unit

class DialogListAdapter(
    private val dialogList: List<Dialog>,
    private val imageLoader: ImageLoader
) :
    RecyclerView.Adapter<DialogListAdapter.UserViewHolder>() {

    lateinit var onItemClickListener: OnItemClickListener

    class UserViewHolder(item: View, listener: OnItemClickListener) :
        RecyclerView.ViewHolder(item) {
        val binding = ItemDialogBinding.bind(item)

        init {
            item.setOnClickListener {
                listener(adapterPosition)
            }
        }

        fun bind(
            userid: Int,
            username: String,
            time: String,
            text: String,
            avatar: String,
            imgLoader: ImageLoader
        ) {
            Coil.setImageLoader(imgLoader)

            binding.apply {
                Username.text = username
                MessageTime.text = time
                MessageText.text = text
            }

            binding.Image.load("https://cdn.only-one.su/public/clients/$userid/100-$avatar") {
                error(R.drawable.no_avatar)
                transformations(CircleCropTransformation())
                memoryCachePolicy(CachePolicy.ENABLED)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_dialog, parent, false)
        return UserViewHolder(view, onItemClickListener)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = dialogList[position]
        print(user)
        holder.bind(user.sendto, user.username, user.time, user.text, user.avatar, imageLoader)
    }

    override fun getItemCount(): Int = dialogList.size

}