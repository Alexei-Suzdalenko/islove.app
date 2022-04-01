package islove.app
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import islove.app.assets.api.SaveConversationMessage
import kotlinx.android.synthetic.main.activity_chat.*

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import islove.app.assets.adapter.TutorialMessageAdapter
import islove.app.assets.classes.App
import islove.app.assets.classes.App.Companion.otherUserData
import islove.app.assets.classes.User
import islove.app.assets.notification.ServiceNotification
import kotlinx.android.synthetic.main.custom_chat_bar.*

class ChatConversartionActivity : AppCompatActivity() { // otherUserData
    var chatChannelId = ""; private val firebaseUserId = FirebaseAuth.getInstance().currentUser!!.uid
    lateinit var tutorialAdapter: TutorialMessageAdapter
    lateinit var rvChatConversationA: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        title = ""
        setSupportActionBar(toolbarChatActivity)
        customProfileImage.setOnClickListener { startActivity(Intent(this, VisitReceiverActivity::class.java)); finish() }

        if(intent.getStringExtra("chatId").toString().length > 7){
            chatChannelId = intent.getStringExtra("chatId").toString()
            val user = User(
                intent.getStringExtra("receiver").toString(),
                intent.getStringExtra("age").toString(), "",
                intent.getStringExtra("image").toString(), "",
                intent.getStringExtra("userName").toString(), "", "", "",
                intent.getStringExtra("miToken").toString(),
            )
            otherUserData = user
        }

        textViewChat.text = otherUserData!!.name
        Glide.with(this).load( otherUserData!!.image).into(customProfileImage)

        rvChatConversationA = findViewById(R.id.rvChatConversation)
        rvChatConversationA.layoutManager = LinearLayoutManager(this)
        rvChatConversationA.setHasFixedSize(true)


        tutorialAdapter = TutorialMessageAdapter(ArrayList(), this)
        rvChatConversationA.adapter = tutorialAdapter


        SaveConversationMessage().createOrGetCurrentChatChannel(otherUserData!!.id){
                chatId -> chatChannelId = chatId
                getConversationMessages()
        }

        sentMessageChat.setOnClickListener {
            val messageText = inputChatMessage.text.toString().trim()
            if(messageText.isNotEmpty() && chatChannelId.isNotEmpty()) {
               // adapter.clearList()
                SaveConversationMessage().saveNewMessage(messageText, chatChannelId)
                inputChatMessage.setText("")

                ServiceNotification().sentNotification (
                    chatChannelId,
                    otherUserData!!.id,
                    firebaseUserId,
                    otherUserData!!.name,
                    messageText,
                    App.sharedPreferences.getString("image", "").toString(),
                    otherUserData!!.token,
                    otherUserData!!.age,
                    App.sharedPreferences.getString("token", "").toString()
                )
            }
        }
        Toast.makeText(this, "onCreateOptionsMenu", Toast.LENGTH_SHORT).show()
    }

    private fun getConversationMessages() {
        SaveConversationMessage().getMessagesFromConversation(chatChannelId){
            tutorialAdapter.addNewItem(it)
            rvChatConversationA.smoothScrollToPosition(tutorialAdapter.itemCount - 1)
        }
    }

       override fun onCreateOptionsMenu(menu: Menu): Boolean { menuInflater.inflate(R.menu.menu_main_conversation, menu); return true }
       override fun onOptionsItemSelected(item: MenuItem): Boolean {
           return when (item.itemId) {
                R.id.blockUserMenu -> { startActivity(Intent(this, BlockThisUserActivity::class.java).putExtra("action", "block").putExtra("userId", App.otherUserData!!.id) ); return true; }
                R.id.reportUserMenu -> { startActivity(Intent(this, BlockThisUserActivity::class.java).putExtra("userId", App.otherUserData!!.id)); return  true; }
               else -> super.onOptionsItemSelected(item)
           }
       }


}