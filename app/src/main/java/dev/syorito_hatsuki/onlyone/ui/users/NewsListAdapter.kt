package dev.syorito_hatsuki.onlyone.ui.users

import android.content.Context
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import coil.Coil
import coil.Coil.enqueue
import coil.ImageLoader
import coil.imageLoader
import coil.load
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import dev.syorito_hatsuki.onlyone.R
import dev.syorito_hatsuki.onlyone.api.user.get_users.response.PostsDataItem
import dev.syorito_hatsuki.onlyone.api.user.get_users.response.User
import dev.syorito_hatsuki.onlyone.databinding.ItemPostBinding
import dev.syorito_hatsuki.onlyone.databinding.ItemUserBinding
import kotlin.coroutines.coroutineContext

class NewsListAdapter(private val userList: List<PostsDataItem>, private val imageLoader: ImageLoader) :
    RecyclerView.Adapter<NewsListAdapter.UserViewHolder>() {

    lateinit var onItemClickListener: OnItemClickListener

    class UserViewHolder(item: View, listener: OnItemClickListener) :
        RecyclerView.ViewHolder(item) {
        var binding = ItemPostBinding.bind(item)

        init {

            item.setOnClickListener {
                listener(adapterPosition)
            }
        }

        fun bind(
            username: String,
            text: String,
            time: String,
            Likes: List<Int>?,
            imgLoader: ImageLoader
        ){
            Coil.setImageLoader(imgLoader)

            binding.apply {
                PostUsername.text = username
                MessageTime.text = time
                MessageText.text = text
                LikesCount.text = Likes?.size.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return UserViewHolder(view, onItemClickListener)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]
        holder.bind(user.author_username, user.text, user.time,user.likes,imageLoader)
    }

    override fun getItemCount(): Int = userList.size

}