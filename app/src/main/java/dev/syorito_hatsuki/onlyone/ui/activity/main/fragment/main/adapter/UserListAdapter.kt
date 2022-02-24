package dev.syorito_hatsuki.onlyone.ui.activity.main.fragment.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import dev.syorito_hatsuki.onlyone.R
import dev.syorito_hatsuki.onlyone.api.user.get_users.response.User
import dev.syorito_hatsuki.onlyone.databinding.ItemUserBinding

class UserListAdapter(private val userList: List<User>) :
    RecyclerView.Adapter<UserListAdapter.UserViewHolder>() {
    class UserViewHolder(private val item: ItemUserBinding) : RecyclerView.ViewHolder(item.root) {
        fun bind(id: Int, avatar: String, username: String) {
            item.userId.text = id.toString()
            item.userAvatar.load("https://cdn.only-one.su/public/clients/$id/$avatar") {
                error(R.drawable.no_avatar)
                transformations(CircleCropTransformation())
            }
            item.username.text = username
        }
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): UserViewHolder = UserViewHolder(
        ItemUserBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )
    )

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]
        holder.bind(user.id, user.avatar, user.username)
    }

    override fun getItemCount(): Int = userList.size
}