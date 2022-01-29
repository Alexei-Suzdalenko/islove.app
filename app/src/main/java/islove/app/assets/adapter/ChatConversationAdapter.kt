package islove.app.assets.adapter
import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import islove.app.R
import islove.app.assets.classes.ChatMessage


class ChatConversationAdapter (private val list: ArrayList<ChatMessage>, private val c: Context) : RecyclerView.Adapter<ChatConversationAdapter.InnerDescription>() {
    class InnerDescription(val view: View) : RecyclerView.ViewHolder(view) {
        val textName = view.findViewById<TextView>(R.id.chatTextViewItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerDescription {
        return InnerDescription(LayoutInflater.from(c).inflate(R.layout.layout_chat_item, parent, false))
    }

    override fun onBindViewHolder(holder: InnerDescription, position: Int) {
        val message = list[position]
       holder.textName.text = message.text
    }

    override fun getItemCount(): Int  { return list.size; }

    @SuppressLint("NotifyDataSetChanged")
    fun addItem(mes: ChatMessage) {
        list.add(mes)
     //   notifyDataSetChanged()
    }
    fun clearList(){
      //  list.clear()
    }
}