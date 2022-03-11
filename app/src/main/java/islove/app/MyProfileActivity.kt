package islove.app
import android.annotation.SuppressLint
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import islove.app.assets.api.SaveNewUserData
import islove.app.assets.classes.App
import kotlinx.android.synthetic.main.activity_settings.*
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import islove.app.assets.api.SaveUserLocationFirestore
import islove.app.assets.classes.User

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
        profileImage.setOnClickListener { pickImages.launch("image/*"); }
        SaveUserLocationFirestore().saveUserLocation(this)

        if(App.sharedPreferences.getString("image", "").toString().length > 11 && App.sharedPreferences.getString("name", "").toString().length > 2){
            val user = User(
                FirebaseAuth.getInstance().currentUser!!.uid,
                App.sharedPreferences.getString("age", "").toString(),
                App.sharedPreferences.getString("country", "").toString(),
                App.sharedPreferences.getString("image", "").toString(),
                App.sharedPreferences.getString("locality", "").toString(),
                App.sharedPreferences.getString("name", "").toString() , "", "",
                App.sharedPreferences.getString("status", "").toString(), "", "",
                App.sharedPreferences.getString("gender", "").toString(),
                App.sharedPreferences.getString("search", "").toString()
            )
            setUserInfo(user)
        } else { SaveNewUserData().getUserInformationProfile(this){ user -> setUserInfo(user); }}


        saveSettings.setOnClickListener {
            val userName = setUserName.text.toString().trim()
            val status = setUserStatus.text.toString().trim()
            val age = setUserAge.text.toString()
            val gender = if(radioIamMan.isChecked) "man"; else "woman"
            val search = if(radioSearchMan.isChecked) "man"; else "woman"
            if(userName.isEmpty() || status.isEmpty()) App.showToast(this, R.string.writeYourNameStatus )
            else SaveNewUserData().updateUserNameStatus(userName, status, age, gender, search, this)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setUserInfo(user: User){
        if(user.image.isNotEmpty() && user.image.length > 11) Glide.with(this).load(user.image).into(profileImage)
        setUserName.setText(user.name)
        setUserStatus.setText(user.status)
        textViewPlace.text = user.locality
        setUserAge.setText(user.age)
        if(user.gender == "man"){ radioIamMan.isChecked = true;      radioIamWoman.isChecked = false; }     else { radioIamMan.isChecked = false;      radioIamWoman.isChecked = true; }
        if(user.search == "man"){ radioSearchMan.isChecked = true; radioSearchWoman.isChecked = false; } else { radioSearchMan.isChecked = false; radioSearchWoman.isChecked = true; }
    }



}