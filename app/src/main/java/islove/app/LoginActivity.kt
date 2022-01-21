package islove.app
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import islove.app.assets.api.LoginInApp
import islove.app.assets.classes.App
import kotlinx.android.synthetic.main.login_activity.*

class LoginActivity : AppCompatActivity() {
    private var currentUser: FirebaseUser? = null
    lateinit var  loadingBar: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        loadingBar = ProgressDialog(this)
        needNewAccount.setOnClickListener{ startActivity(Intent(this, RegisterActivity::class.java)); finish(); }
        buttonLogin.setOnClickListener {
            val email: String = emailLogin.text.toString().trim()
            val password: String = passwordLogin.text.toString().trim()
            if(email.isEmpty() || password.isEmpty()){
                App.showToast(this,  R.string.pleaseEnterEmailPassword)
            } else {
                loadingBar.setTitle(resources.getString(R.string.loginIn))
                loadingBar.setMessage(resources.getString(R.string.pleaseWait))
                loadingBar.setCanceledOnTouchOutside(true)
                loadingBar.show()
                LoginInApp(this).loginIn(email, password, loadingBar)
            }
        }
    }

    private fun InitializeFields(){
        buttonLogin
        phoneLogin
        emailLogin
        passwordLogin
        forgetPasswordLogin
        orLoginUsingYour
    }

    override fun onStart() {
        super.onStart()
        currentUser = FirebaseAuth.getInstance().currentUser
        if(currentUser != null){ SendUserToMainActivity(); Toast.makeText(this, "User login " + currentUser!!.uid, Toast.LENGTH_LONG).show(); }
    }

    fun SendUserToMainActivity(){
        val mainIntent = Intent(this, MainActivity::class.java)
             mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK )
             mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(mainIntent)
        finish()
        finish()
    }
}