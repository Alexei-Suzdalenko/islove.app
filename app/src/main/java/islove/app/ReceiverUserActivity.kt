package islove.app
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import islove.app.assets.classes.App
import kotlinx.android.synthetic.main.activity_profile_visit.*

class ReceiverUserActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_visit)

        visitNameTextView.text = App.otherUserData?.name.toString() + " " + App.otherUserData?.age.toString()
        visitStateTextView.text =  App.otherUserData?.status.toString()
        if( App.otherUserData?.image.toString().length > 11 ) Glide.with(this).load(App.otherUserData?.image.toString()).into(visitProfileImage)

        sentMessageButton.setOnClickListener { startActivity( Intent(this, ChatConversartionActivity::class.java)); }

        blockThisUser.setOnClickListener {
            val intent = Intent(this, BlockThisUserActivity::class.java)
                  intent.putExtra("userId", App.otherUserData!!.id)
            startActivity(intent)
        }

    }
}