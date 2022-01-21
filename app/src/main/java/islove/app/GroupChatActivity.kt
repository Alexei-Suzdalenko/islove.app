package islove.app
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import islove.app.assets.api.GroupsAdapter
import islove.app.assets.api.SaveGropsMessage
import islove.app.assets.api.SaveNewUserData
import islove.app.assets.classes.User
import kotlinx.android.synthetic.main.activity_group_chat.*
class GroupChatActivity : AppCompatActivity() {
    lateinit var user: User
    lateinit var groupsAdapter: GroupsAdapter

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_chat)

        setSupportActionBar(groupChatBarL as Toolbar?)
        val groupName =  intent.getStringExtra("group").toString()
        supportActionBar!!.title = groupName
        groupsAdapter = GroupsAdapter(ArrayList())
        rvGroupChat.layoutManager = LinearLayoutManager(this)
        rvGroupChat.adapter = groupsAdapter

        SaveNewUserData().getUserInformationProfile(null){ userRes -> user = userRes; }

        sentMessageGroup.setOnClickListener {
            val message = inputGropMessage.text.toString().trim()
            if(message.isNotEmpty()){
                SaveGropsMessage(groupName).saveMessage(user, message)
                inputGropMessage.setText("")
            }
        }

        SaveGropsMessage(groupName).getMessages(){ message ->
            groupsAdapter.addItem(message.message + "\n" + message.date)
            rvGroupChat.scrollToPosition(groupsAdapter.itemCount - 1)
        }

    }


}