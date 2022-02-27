package islove.app
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import islove.app.assets.api.SaveNewUserData
import islove.app.assets.classes.App
import kotlinx.android.synthetic.main.activity_settings.*
import com.bumptech.glide.Glide
import islove.app.assets.api.SaveUserLocationFirestore

class MyProfileActivity : AppCompatActivity() {
    lateinit var pickImages: ActivityResultLauncher<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        pickImages = registerForActivityResult(ActivityResultContracts.GetContent()){ uri: Uri? ->
            if(uri != null){
              SaveNewUserData().saveUserImage(uri){ imageUrl ->
                  if(imageUrl != "Error") Glide.with(this).load(imageUrl).into(profileImage)
                  else App.showToast(this, R.string.error)
              }
            } else App.showToast(this, R.string.error)
        }

        SaveNewUserData().getUserInformationProfile(this){ user ->
            setUserName.setText(user.name)
            setUserStatus.setText(user.status)
            if(user.image.isNotEmpty()) Glide.with(this).load(user.image).into(profileImage)
        }

        saveSettings.setOnClickListener {
            val userName: String = setUserName.text.toString().trim()
            val status: String = setUserStatus.text.toString().trim()
            if(userName.isEmpty() || status.isEmpty()) App.showToast(this, R.string.writeYourNameStatus )
            else SaveNewUserData().updateUserNameStatus(userName, status, this)
        }

        profileImage.setOnClickListener { pickImages.launch("image/*"); }

        SaveUserLocationFirestore().saveUserLocation(this)
    }




}