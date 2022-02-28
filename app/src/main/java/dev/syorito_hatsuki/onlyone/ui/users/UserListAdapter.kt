package dev.syorito_hatsuki.onlyone.ui.users

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
import dev.syorito_hatsuki.onlyone.api.user.get_users.response.User
import dev.syorito_hatsuki.onlyone.databinding.ItemUserBinding

typealias OnItemClickListener = (Int) -> Unit

class UserListAdapter(private val userList: List<User>,private val imageLoader: ImageLoader) :
    RecyclerView.Adapter<UserListAdapter.UserViewHolder>() {

    lateinit var onItemClickListener: OnItemClickListener

    class UserViewHolder(item: View, listener: OnItemClickListener) :
        RecyclerView.ViewHolder(item) {
        val binding = ItemUserBinding.bind(item)

        init {

            item.setOnClickListener {
                listener(adapterPosition)
            }
        }

        fun bind(id: Int, avatar: String, username: String,imgLoader: ImageLoader) {
            Coil.setImageLoader(imgLoader)

            binding.Image.load("https://cdn.only-one.su/public/clients/$id/100-$avatar") {
                error(R.drawable.no_avatar)
                transformations(CircleCropTransformation())
                memoryCachePolicy(CachePolicy.ENABLED)
            }
            binding.Username.text = username
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view, onItemClickListener )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]
        holder.bind(user.id, user.avatar, user.username,imageLoader)
    }

    override fun getItemCount(): Int = userList.size

}