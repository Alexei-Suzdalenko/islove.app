package islove.app
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import islove.app.assets.api.SaveConversationMessage
import kotlinx.android.synthetic.main.activity_chat.*

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import islove.app.assets.adapter.TutorialMessageAdapter
import islove.app.assets.classes.Addss
import islove.app.assets.classes.App
import islove.app.assets.classes.App.Companion.otherUserData
import islove.app.assets.classes.Conversation
import islove.app.assets.classes.User
import islove.app.assets.notification.ServiceNotification
import kotlinx.android.synthetic.main.custom_chat_bar.*

class ChatConversartionActivity : AppCompatActivity() { // otherUserData
    var chatChannelId: String? = null; private val firebaseUserId = FirebaseAuth.getInstance().currentUser!!.uid; var listMesStart: String? = null
    lateinit var tutorialAdapter: TutorialMessageAdapter
    lateinit var rvChatConversationA: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        title = ""
        setSupportActionBar(toolbarChatActivity)
        customProfileImage.setOnClickListener { startActivity(Intent(this, VisitReceiverActivity::class.java)); finish() }

        if(intent.getStringExtra("chatId").toString().length > 7){
            val user = User(intent.getStringExtra("receiver").toString(), intent.getStringExtra("age").toString(), "", intent.getStringExtra("image").toString(), "", intent.getStringExtra("userName").toString(), "", "", "", intent.getStringExtra("miToken").toString(),)
            chatChannelId = intent.getStringExtra("chatId").toString()
            otherUserData = user
        }

        Log.d("otherUserData", "chatId = " + intent.getStringExtra("chatId").toString())
        Log.d("otherUserData", "otherUserData = " + otherUserData.toString())

        textViewChat.text = otherUserData?.name
        Glide.with(this).load( otherUserData!!.image).into(customProfileImage)

        rvChatConversationA = findViewById(R.id.rvChatConversation)
        rvChatConversationA.layoutManager = LinearLayoutManager(this)
        rvChatConversationA.setHasFixedSize(true)
        tutorialAdapter = TutorialMessageAdapter(ArrayList(), this)
        rvChatConversationA.adapter = tutorialAdapter

        // entro desde listado de usuarios o desde listado conversaciones
        if(chatChannelId == null){
            SaveConversationMessage().getConversationChatChannel(otherUserData!!.id){ chatId ->
                if (chatId != null) { getConversationMessages(chatId) }
            }
        } else {
            getConversationMessages(chatChannelId!!); listMesStart = "started"
        }

        sentMessageChat.setOnClickListener {
            val messageText = inputChatMessage.text.toString().trim()
            if(messageText.isNotEmpty() && messageText.isNotBlank()) {
                if(chatChannelId == null){
                    SaveConversationMessage().createOrGetCurrentChatChannel(otherUserData!!.id){ chatChannelId = it
                        SaveConversationMessage().saveNewMessage(messageText, chatChannelId!!)
                        if(listMesStart == null) {listMesStart = "started"; getConversationMessages(chatChannelId!!)}
                        sentNotification(messageText)
                    }
                } else {
                    SaveConversationMessage().saveNewMessage(messageText, chatChannelId!!)
                    if(listMesStart == null) {listMesStart = "started"; getConversationMessages(chatChannelId!!)}
                    sentNotification(messageText)
                    FirebaseFirestore.getInstance().collection("enganched").document(otherUserData!!.id).collection("chat").document(firebaseUserId).set(Conversation(chatChannelId!!))
                }
                inputChatMessage.setText("")
            }
            Addss.startM(this)
        }
    }

    private fun sentNotification(messageText: String){
        ServiceNotification().sentNotification (chatChannelId.toString(), otherUserData!!.id, firebaseUserId, App.sharedPreferences.getString("name", "").toString(), messageText, App.sharedPreferences.getString("image", "").toString(), otherUserData!!.token, App.sharedPreferences.getString("age", "").toString(), App.sharedPreferences.getString("token", "").toString())
    }

    private fun getConversationMessages(chatId: String) {
        SaveConversationMessage().getMessagesFromConversation(chatId){
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