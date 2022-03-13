package islove.app
import android.content.Intent
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
import islove.app.assets.classes.App
import islove.app.assets.classes.App.Companion.otherUserData
import islove.app.assets.notification.NotificationWork
import islove.app.assets.notification.ServiceNotification
import kotlinx.android.synthetic.main.custom_chat_bar.*

class ChatConversartionActivity : AppCompatActivity() { // otherUserData
    var chatChannelId = ""
    lateinit var tutorialAdapter: TutorialMessageAdapter
    lateinit var rvChatConversationA: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
      //  Log.d("otherUserDataA", "otherUserData = " + otherUserData.toString())

        textViewChat.text = App.otherUserData!!.name
        Glide.with(this).load( App.otherUserData!!.image).into(customProfileImage)


        rvChatConversationA = findViewById(R.id.rvChatConversation)
        rvChatConversationA.layoutManager = LinearLayoutManager(this)
        rvChatConversationA.setHasFixedSize(true)


        tutorialAdapter = TutorialMessageAdapter(ArrayList(), this)
        rvChatConversationA.adapter = tutorialAdapter


        SaveConversationMessage().createOrGetCurrentChatChannel(App.otherUserData!!.id){
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
                ServiceNotification().sentNotification(chatChannelId, App.otherUserData!!.id, App.otherUserData!!.token, messageText, App.otherUserData!!.image, App.otherUserData!!.name)
            }
        }

    }

    private fun getConversationMessages() {
        SaveConversationMessage().getMessagesFromConversation(chatChannelId){
            tutorialAdapter.addNewItem(it)
            rvChatConversationA.smoothScrollToPosition(tutorialAdapter.itemCount - 1)
        }
    }

    override fun onStop() {
        super.onStop()
        if(intent.getStringExtra("message").toString() == "message"){ startActivity(Intent(this, MainActivity::class.java)); }
    }





}