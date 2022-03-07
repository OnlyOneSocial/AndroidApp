package dev.syorito_hatsuki.onlyone.ui.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.syorito_hatsuki.onlyone.R
import dev.syorito_hatsuki.onlyone.api.user.get_users.response.PostsDataItem
import dev.syorito_hatsuki.onlyone.databinding.ItemPostBinding



class UserProfileWallAdapter(
    private val dialogList: List<PostsDataItem>
) :
    RecyclerView.Adapter<UserProfileWallAdapter.UserViewHolder>() {

    class UserViewHolder(item: View) :
        RecyclerView.ViewHolder(item) {
        val binding = ItemPostBinding.bind(item)

        fun bind(
            username: String,
            text: String,
            time: String,
            Likes: List<Int>?
        ) {

            println(Likes?.size)

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
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = dialogList[position]

        holder.bind(user.author_username, user.text, user.time,user.likes)
    }

    override fun getItemCount(): Int = dialogList.size

}