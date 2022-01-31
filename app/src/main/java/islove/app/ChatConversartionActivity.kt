package islove.app
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import islove.app.assets.api.SaveConversationMessage
import kotlinx.android.synthetic.main.activity_chat.*
import islove.app.assets.adapter.ChatConversationAdapter

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import islove.app.assets.adapter.TutorialMessageAdapter
import islove.app.assets.notification.NotificationWork
import islove.app.assets.notification.ServiceNotification
import kotlinx.android.synthetic.main.custom_chat_bar.*

class ChatConversartionActivity : AppCompatActivity() {
    var sender = ""; var receiver = ""; var chatChannelId = ""; var token = "000 000 000 000"; var image = ""; var name = ""
  //  lateinit var adapter: ChatConversationAdapter
    lateinit var tutorialAdapter: TutorialMessageAdapter
    lateinit var rvChatConversationA: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        name = intent.getStringExtra("name").toString()
        textViewChat.text = name
        if( intent.getStringExtra("image").toString().length > 22 ) {
            image = intent.getStringExtra("image").toString()
            Glide.with(this).load(intent.getStringExtra("image").toString()).into(customProfileImage)
        }

        rvChatConversationA = findViewById(R.id.rvChatConversation)
        rvChatConversationA.layoutManager = LinearLayoutManager(this)
        rvChatConversationA.setHasFixedSize(true)
    //     adapter = ChatConversationAdapter(ArrayList(), this)
    //     rvChatConversationA.adapter = adapter

        tutorialAdapter = TutorialMessageAdapter(ArrayList(), this)
        rvChatConversationA.adapter = tutorialAdapter

        sender = intent.getStringExtra("sender").toString()
        receiver =  intent.getStringExtra("receiver").toString()
        token = intent.getStringExtra("token").toString()

        SaveConversationMessage().createOrGetCurrentChatChannel(sender, receiver){
                chatId -> chatChannelId = chatId
                getConversationMessages()
        }

        sentMessageChat.setOnClickListener {
            val messageText = inputChatMessage.text.toString().trim()
            if(messageText.isNotEmpty() && chatChannelId.isNotEmpty()) {
               // adapter.clearList()
                SaveConversationMessage().saveNewMessage(messageText, chatChannelId)
                inputChatMessage.setText("")
                /* create notification  and send */
                ServiceNotification().sentNotification(sender, receiver, token, messageText, image, name)
                Log.d("tokentoken", "token: " + token)
            }
        }

    }

    private fun getConversationMessages() {
        SaveConversationMessage().getMessagesFromConversation(chatChannelId){
      //      adapter.addItem(it)
      //      rvChatConversationA.scrollToPosition(adapter.itemCount - 1)
            tutorialAdapter.addNewItem(it)
            // rvChatConversationA.scrollToPosition(tutorialAdapter.itemCount - 1)
            /* scroll from tutorial chat */
            rvChatConversationA.smoothScrollToPosition(tutorialAdapter.itemCount - 1)
        }
    }





}