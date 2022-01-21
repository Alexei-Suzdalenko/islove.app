package islove.app
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import islove.app.assets.api.SaveNewUserData
import islove.app.assets.classes.App
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        saveSettings.setOnClickListener {
            val userName: String = setUserName.text.toString().trim()
            val status: String = setUserStatus.text.toString().trim()
            if(userName.isEmpty() || status.isEmpty()){
                App.showToast(this, R.string.writeYourNameStatus )
            } else {
                SaveNewUserData().updateUserNameStatus(userName, status, this)
            }
        }

        SaveNewUserData().getUserInformationProfile(this){ user ->
            setUserName.setText(user.name)
            setUserStatus.setText(user.status)
            if(user.image.isNotEmpty()){

            }
        }
    }




}