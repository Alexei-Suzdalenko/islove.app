package islove.app.assets.api
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import islove.app.MainActivity
import islove.app.R
import islove.app.SettingsActivity
import islove.app.assets.classes.App
import islove.app.assets.classes.User
import kotlinx.android.synthetic.main.activity_settings.*

class SaveNewUserData {
    val rootRef = FirebaseDatabase.getInstance().reference

    fun saveNewUser(email: String, password: String){
        val id = FirebaseAuth.getInstance().currentUser!!.uid.toString()
        val profile = HashMap<String, Any>()
             profile["id"] = id
             profile["email"] = email
             profile["password"] = password
        rootRef.child("users").child(id).setValue(profile)
    }

    fun updateUserNameStatus(name: String, status: String, c: Context){
        val id = FirebaseAuth.getInstance().currentUser!!.uid
        val rootRef = FirebaseDatabase.getInstance().reference.child("users").child(id)
        val profile = HashMap<String, Any>()
              profile["id"] = id
              profile["name"] = name
              profile["status"] = status
        rootRef
            .updateChildren( profile )
            .addOnCompleteListener { it ->
                if(it.isSuccessful()){
                    App.showToast(c, R.string.profileUpdatedSuccessfuly)
                    c.startActivity(Intent(c, MainActivity::class.java))
                } else {
                    Toast.makeText(c, "Error:" +  it.exception.toString(), Toast.LENGTH_LONG).show()
                }
        }
    }

    fun getUserInformationProfile(settingsActivity: SettingsActivity?, onComplete: (user: User) -> Unit){

        FirebaseDatabase.getInstance().reference.child("users").child(FirebaseAuth.getInstance().currentUser!!.uid).addListenerForSingleValueEvent(object: ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {}
                override fun onDataChange(data: DataSnapshot) {
                    if(data.exists() && data.hasChild("name")) {
                        val userName = data.child("name").value.toString()
                        val userStatus = data.child("status").value.toString()
                        var image = ""
                        if (data.hasChild("image")) { image = data.child("image").value.toString(); }
                        val user = User("", userName, "", "", userStatus, image)
                        onComplete(user)
                    } else {
                        if(settingsActivity != null){
                            App.showToast(settingsActivity, R.string.setUpdateProfileInformation)
                        }
                    }
                }
        })
    }






}