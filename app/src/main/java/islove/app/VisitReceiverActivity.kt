package islove.app
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import islove.app.assets.classes.Addss
import islove.app.assets.classes.App
import islove.app.assets.classes.App.Companion.otherUserData
import kotlinx.android.synthetic.main.activity_profile_visit.*

class VisitReceiverActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_visit)

        if(otherUserData  == null) finish()
        if(otherUserData?.name.toString()  == "null") finish()

        visitNameTextView.text = App.otherUserData?.name.toString() + " " + App.otherUserData?.age.toString()
        visitStateTextView.text =  App.otherUserData?.status.toString()
        if( otherUserData?.image.toString().length > 11 ) Glide.with(this).load(otherUserData?.image.toString()).into(visitProfileImage)
        if( otherUserData?.back.toString().length > 11 )   Glide.with(this).load(otherUserData?.back.toString()).into(backImageUserVisit)

        visitProfileImage.setOnClickListener { Addss.start(this); startActivity(Intent(this, ShowImages::class.java).putExtra("image", "image") ) }
        backImageUserVisit.setOnClickListener { Addss.start(this); startActivity(Intent(this, ShowImages::class.java).putExtra("image", "back") ) }

        sentMessageButton.setOnClickListener { startActivity( Intent(this, ChatConversartionActivity::class.java)); }

        blockThisUser.setOnClickListener {
            val intent = Intent(this, BlockThisUserActivity::class.java).putExtra("action", "block")
                  intent.putExtra("userId", App.otherUserData!!.id)
            startActivity(intent)
        }

        reportUserBehavior.setOnClickListener {
            val intent = Intent(this, BlockThisUserActivity::class.java)
            intent.putExtra("userId", App.otherUserData!!.id)
            startActivity(intent)
        }

    }
}