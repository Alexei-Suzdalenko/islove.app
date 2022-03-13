package islove.app.assets.api
import android.app.ProgressDialog
import android.content.Intent
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import islove.app.*
import islove.app.assets.classes.App
import islove.app.assets.notification.NotificationWork

class CreateNewAccount(val c: RegisterActivity? = null) {

        fun createNewAccount(email: String, password: String, loadingBar: ProgressDialog){
            val mAuth = FirebaseAuth.getInstance()
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { it ->
                if(it.isSuccessful) {
                    SaveNewUserData().saveNewUser(email, password, "")
                    NotificationWork().saveUserToken()
                    SendUserToMainActivity()
                    App.showToast(c!!,  R.string.accountCreatedSuccessfully)
                } else {
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {  it->
                        if(it.isSuccessful){
                            /* save user token */
                                App.editor.putString("email", email).apply()
                            NotificationWork().saveUserToken()
                            SendUserToMainActivity()
                        } else {
                            Toast.makeText(c, "Error: " + it.exception.toString(), Toast.LENGTH_LONG).show()
                        }
                        loadingBar.dismiss()
                    }
                }
                loadingBar.dismiss()
            }
        }

    private fun SendUserToMainActivity(){
        val mainIntent = Intent(c, MainActivity::class.java)
        c!!.startActivity(mainIntent)
        c.finish()
    }

    /*  hay que cambiar esto por unico request no mantener conneccion durante todo el tiempo */
    fun virifyUserInstance(mainActivity: MainActivity){
        NotificationWork().saveUserToken()
        if(App.sharedPreferences.getString("image", "").toString().length < 11 || App.sharedPreferences.getString("name", "").toString().length < 3){
            Toast.makeText(mainActivity, mainActivity.resources.getString(R.string.setUserDataAndUploadUserFoto), Toast.LENGTH_LONG).show()
            mainActivity.startActivity(Intent(mainActivity, MyProfileActivity::class.java))
        }
        BlockUserFire().getListUsersThenBlockedMe()
    }

}
