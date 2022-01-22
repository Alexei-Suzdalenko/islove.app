package islove.app.assets.api
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import islove.app.MainActivity
import islove.app.R
import islove.app.SettingsActivity
import islove.app.assets.classes.App
import islove.app.assets.classes.User
import kotlinx.android.synthetic.main.activity_settings.*

class SaveNewUserData {
    val id = FirebaseAuth.getInstance().currentUser!!.uid
    val rootRef = FirebaseDatabase.getInstance().reference.child("users").child(id)
    val storage = FirebaseStorage.getInstance().reference.child("profile_image").child("$id.jpg")

    fun saveNewUser(email: String, password: String, phone: String){
        val id = FirebaseAuth.getInstance().currentUser!!.uid.toString()
        val profile = HashMap<String, Any>()
             profile["id"] = id
             profile["email"] = email
             profile["password"] = password
             profile["phone"] = phone
        rootRef.setValue(profile)
    }

    fun updateUserNameStatus(name: String, status: String, c: Context){
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
        FirebaseDatabase.getInstance().reference.child("users").child(id).addListenerForSingleValueEvent(object: ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {}
                override fun onDataChange(data: DataSnapshot) {
                    if(data.exists() && data.hasChild("name")) {
                        val userName = data.child("name").value.toString()
                        val userStatus = data.child("status").value.toString()
                        var image = ""
                        if (data.hasChild("image")) { image = data.child("image").value.toString(); }
                        val user = User("", userName, "", "", userStatus, image)
                        onComplete(user)
                    } else if(settingsActivity != null){ App.showToast(settingsActivity, R.string.setUpdateProfileInformation); }
                }
        })
    }

    fun saveUserImage(uri: Uri, onComplete: (result : String) -> Unit){
        storage.putFile(uri).addOnCompleteListener { task ->
            if(task.isSuccessful) {
                storage.downloadUrl.addOnCompleteListener { it ->
                    val profile = HashMap<String, Any>()
                    profile["image"] = it.result.toString()
                    rootRef.updateChildren(profile)
                    onComplete( it.result.toString() )
                }
            } else onComplete("Error")
        }
    }




}