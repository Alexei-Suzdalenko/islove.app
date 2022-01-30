package islove.app.assets.api
import android.app.ProgressDialog
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import islove.app.LoginActivity
import islove.app.R
import islove.app.assets.classes.App
import islove.app.assets.notification.NotificationWork

class LoginInApp(val c: LoginActivity) {


    fun loginIn(email: String, password: String, loadingBar: ProgressDialog){
        val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {  it->
            if(it.isSuccessful){
                /* save user token */
                NotificationWork().saveUserToken()

                c.SendUserToMainActivity()
                App.showToast(c, R.string.loggedInSuccessful)
            } else {
                Toast.makeText(c, "Error: " + it.exception.toString(), Toast.LENGTH_LONG).show()
            }
            loadingBar.dismiss()
        }
    }
}