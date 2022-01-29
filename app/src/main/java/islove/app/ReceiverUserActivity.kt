package islove.app
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_profile_visit.*

class ReceiverUserActivity : AppCompatActivity() {
    var miId = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_visit)

        miId = FirebaseAuth.getInstance().currentUser!!.uid
        val visitUserId = intent.getStringExtra("visit_user_id").toString()
        visitNameTextView.text =  intent.getStringExtra("visit_name").toString()
        visitStateTextView.text =  intent.getStringExtra("visit_status").toString()
        if( intent.getStringExtra("visit_image").toString().length > 22 ) Glide.with(this).load(intent.getStringExtra("visit_image").toString()).into(visitProfileImage)

        sentMessageButton.setOnClickListener {
            val intentChat = Intent(this, ChatConversartionActivity::class.java)
                 intentChat.putExtra("sender", miId)
                 intentChat.putExtra("receiver", visitUserId)
                 intentChat.putExtra("name", intent.getStringExtra("visit_name").toString())
                 if( intent.getStringExtra("visit_image").toString().length > 22 ){
                    intentChat.putExtra("image", intent.getStringExtra("visit_image").toString())
                 }

            startActivity(intentChat)
        }
    }
}