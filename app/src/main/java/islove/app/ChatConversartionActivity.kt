package islove.app
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.*
import islove.app.assets.api.SaveConversationMessage
import islove.app.assets.classes.ChatMessage
import kotlinx.android.synthetic.main.activity_chat.*
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import islove.app.assets.adapter.ChatConversationAdapter

import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_group_chat.*
import kotlinx.android.synthetic.main.activity_profile_visit.*
import kotlinx.android.synthetic.main.custom_chat_bar.*

class ChatConversartionActivity : AppCompatActivity() {
    var sender = ""; var receiver = ""; var chatChannelId = ""
    lateinit var adapter: ChatConversationAdapter
    lateinit var rvChatConversationA: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        textViewChat.text = intent.getStringExtra("name").toString()
        if( intent.getStringExtra("image").toString().length > 22 ) Glide.with(this).load(intent.getStringExtra("image").toString()).into(customProfileImage)

        rvChatConversationA = findViewById(R.id.rvChatConversation)
        // rvChatConversationA.setHasFixedSize(true)
        rvChatConversationA.layoutManager = LinearLayoutManager(this)
        adapter = ChatConversationAdapter(ArrayList(), this)
        rvChatConversationA.adapter = adapter

        sender = intent.getStringExtra("sender").toString()
        receiver =  intent.getStringExtra("receiver").toString()
        SaveConversationMessage().createOrGetCurrentChatChannel(sender, receiver){
                chatId -> chatChannelId = chatId
                getConversationMessages()
        }

        sentMessageChat.setOnClickListener {
            val messageText = inputChatMessage.text.toString().trim()
            if(messageText.isNotEmpty() && chatChannelId.isNotEmpty()) {
                adapter.clearList()
                SaveConversationMessage().saveNewMessage(messageText, chatChannelId)
                inputChatMessage.setText("")
            }
        }

    }

    private fun getConversationMessages() {
        SaveConversationMessage().getMessagesFromConversation(chatChannelId){
            adapter.addItem(it)
            rvChatConversationA.scrollToPosition(adapter.itemCount - 1)
        }
    }


    override fun onStop() {
        super.onStop()

    }


}