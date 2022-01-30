package islove.app.assets.adapter
import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import islove.app.R
import islove.app.assets.classes.MessageChat
class TutorialMessageAdapter(private val list:  ArrayList<MessageChat>, private val c: Context) : RecyclerView.Adapter<TutorialMessageAdapter.ViewHolderA>() {
    val miId = FirebaseAuth.getInstance().currentUser!!.uid.toString()
    val arrayIdMessages = ArrayList<String>()

    class ViewHolderA(view: View): RecyclerView.ViewHolder(view)  {
        val receiverMessageText: TextView? = view.findViewById(R.id.chatTextViewItemLeft)
        val senderMessageText: TextView? = view.findViewById(R.id.chatTextViewItemRight)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderA {
        val view: View = if(viewType == 0) LayoutInflater.from(c).inflate(R.layout.layout_chat_item_left, parent, false)
        else LayoutInflater.from(c).inflate(R.layout.layout_chat_item_right, parent, false)
        return ViewHolderA(view)
    }

    override fun onBindViewHolder(holder: ViewHolderA, position: Int) {
        holder.receiverMessageText?.text = list[position].text
        holder.senderMessageText?.text = list[position].text
    }

  override fun getItemViewType(position: Int): Int {
      return if(list[position].userid == miId) 1
      else 0
  }

    override fun getItemCount(): Int = list.size

    @SuppressLint("NotifyDataSetChanged")                         // una solucion muy mala para que no se repitan los mensajes en el recyclerView
    fun addNewItem(message: MessageChat){                                  Log.d("arrayIdMessages", "arrayIdMessages: " + arrayIdMessages.toString())
        if(!arrayIdMessages.contains(message.time)) {
            arrayIdMessages.add(message.time)
            list.add(message)
            notifyDataSetChanged()
        }
    }
}