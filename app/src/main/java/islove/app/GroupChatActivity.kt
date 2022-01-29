package islove.app
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import islove.app.assets.adapter.GroupsAdapter
import islove.app.assets.api.SaveGropsMessage
import islove.app.assets.api.SaveNewUserData
import islove.app.assets.classes.User
import kotlinx.android.synthetic.main.activity_group_chat.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class GroupChatActivity : AppCompatActivity() {
    lateinit var user: User
    lateinit var groupsAdapter: GroupsAdapter

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
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

        SaveGropsMessage(groupName).getMessages{ message ->
            val formatter = SimpleDateFormat("hh:mm:ss dd/MM/yyyy")
            val  dateString = formatter.format( Date(message.time))
            groupsAdapter.addItem(message.name + "  "+ dateString + " " + "\n" + message.message)
            rvGroupChat.scrollToPosition(groupsAdapter.itemCount - 1)
        }

    }


}