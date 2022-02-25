package dev.syorito_hatsuki.onlyone.ui.activity.main.fragment.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import dev.syorito_hatsuki.onlyone.R
import dev.syorito_hatsuki.onlyone.api.user.get_users.response.User
import dev.syorito_hatsuki.onlyone.databinding.ActivityMainBinding
import dev.syorito_hatsuki.onlyone.databinding.ItemUserBinding
import java.text.FieldPosition

class UserListAdapter(private val userList: List<User>) : RecyclerView.Adapter<UserListAdapter.UserViewHolder>() {

    private lateinit var mListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        mListener = listener
    }

    class UserViewHolder(item: View,listener: OnItemClickListener) : RecyclerView.ViewHolder(item) {
        val binding = ItemUserBinding.bind(item)

        init {
            item.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }

        fun bind(id: Int, avatar: String, username: String) {
            //item.userId.text = id.toString()

            binding.Image.load("https://cdn.only-one.su/public/clients/$id/$avatar") {
                error(R.drawable.no_avatar)
                transformations(CircleCropTransformation())
            }
            binding.Username.text = username
        }

    }

   /* override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): UserViewHolder = UserViewHolder(
        ItemUserBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )
    )*/

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user,parent,false)
        return UserViewHolder(view,mListener)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]
        holder.bind(user.id, user.avatar, user.username)
    }

    override fun getItemCount(): Int = userList.size


}