package islove.app
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.*
import islove.app.assets.api.SaveNewMessage
import islove.app.assets.classes.ChatMessage
import islove.app.assets.adapter.FindFrensViewHolder
import islove.app.assets.classes.User
import kotlinx.android.synthetic.main.activity_chat.*
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import islove.app.assets.adapter.ChatsViewHolder

import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_group_chat.*
import java.lang.Exception

class ChatActivity : AppCompatActivity() {
    var sender = ""; var receiver = ""; var chatChannelId = ""
    lateinit var adapter: FirestoreRecyclerAdapter<ChatMessage, ChatsViewHolder>
    lateinit var rvChatConversationA: RecyclerView; var changes = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        rvChatConversationA = findViewById(R.id.rvChatConversation)
        rvChatConversationA.setHasFixedSize(true)
        rvChatConversationA.layoutManager = LinearLayoutManager(this)
        sender = intent.getStringExtra("sender").toString()
        receiver =  intent.getStringExtra("receiver").toString()

        SaveNewMessage().createOrGetCurrentChatChannel(sender, receiver){
                chatId -> chatChannelId = chatId
                getListMessages()
        }

        sentMessageChat.setOnClickListener {
            val messageText = inputChatMessage.text.toString().trim()
            if(messageText.isNotEmpty() && chatChannelId.isNotEmpty()){
                SaveNewMessage().saveNewMessage(messageText, chatChannelId)
                inputChatMessage.setText("")
                // rvChatConversationA.smoothScrollToPosition(rvChatConversationA.adapter!!.itemCount )
                changes = true
            }
        }
    }

    private fun getListMessages() {
        val query = FirebaseFirestore.getInstance().collection("conversation").document("chats").collection(chatChannelId).orderBy("time", Query.Direction.ASCENDING)
        val options = FirestoreRecyclerOptions.Builder<ChatMessage>().setQuery(query, ChatMessage::class.java).build()
      adapter = object : FirestoreRecyclerAdapter<ChatMessage, ChatsViewHolder>(options) {
          override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatsViewHolder {
              return ChatsViewHolder(LayoutInflater.from(this@ChatActivity).inflate(R.layout.layout_chat_item, parent, false))
          }
          override fun onBindViewHolder(holder: ChatsViewHolder, p1: Int, model: ChatMessage) {
              holder.bind(model, this@ChatActivity)
              if(changes) { rvChatConversationA.smoothScrollToPosition(rvChatConversationA.adapter!!.itemCount -1 ); changes = false }
          }
      }
      rvChatConversationA.adapter = adapter
      adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening();
    }


}