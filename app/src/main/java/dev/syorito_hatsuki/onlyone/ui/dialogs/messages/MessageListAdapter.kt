package dev.syorito_hatsuki.onlyone.ui.dialogs.messages

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.syorito_hatsuki.onlyone.R
import dev.syorito_hatsuki.onlyone.api.user.get_users.response.DialogMessage
import dev.syorito_hatsuki.onlyone.databinding.ItemMessagesBinding

typealias OnItemClickListener = (Int) -> Unit

class MessageListAdapter(
    private val messageList: List<DialogMessage>
) :
    RecyclerView.Adapter<MessageListAdapter.UserViewHolder>() {

    lateinit var onItemClickListener: OnItemClickListener

    class UserViewHolder(item: View, listener: OnItemClickListener) :
        RecyclerView.ViewHolder(item) {
        val binding = ItemMessagesBinding.bind(item)

        init {
            item.setOnClickListener {
                listener(adapterPosition)
            }
        }

        fun bind(
            time: String,
            text: String
        ) {
            binding.apply {
                MessageTime.text = time
                MessageText.text = text
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_messages, parent, false)
        return UserViewHolder(view, onItemClickListener)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = messageList[position]
        print(user)
        holder.bind(user.time, user.text)
    }

    override fun getItemCount(): Int = messageList.size

}