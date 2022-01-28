package islove.app.assets.adapter
import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import islove.app.assets.classes.ChatMessage
import kotlinx.android.synthetic.main.layout_chat_item.view.*

class ChatsViewHolder (val itemViewA: View) : RecyclerView.ViewHolder(itemViewA) {
    val list = ArrayList<String>()

    fun bind(chat: ChatMessage, c: Context) {
        itemViewA.chatTextViewItem?.text = chat.text
    }


}