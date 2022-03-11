package islove.app
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import islove.app.assets.api.CreateNewAccount
import islove.app.assets.classes.App
import kotlinx.android.synthetic.main.activity_location_permision.*
import kotlinx.android.synthetic.main.register_activity.*
import kotlinx.android.synthetic.main.register_activity.termAndCond

class RegisterActivity : AppCompatActivity() {
    lateinit var mAuth: FirebaseAuth
    lateinit var  loadingBar: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_activity)

        mAuth = FirebaseAuth.getInstance()

        loadingBar = ProgressDialog(this)
        alredyHaveAccoutRegister.setOnClickListener { startActivity(Intent(this, LoginActivity::class.java)); finish(); }
        buttonCreateAccount.setOnClickListener { CreateNewAccount(); }
        termAndCond.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://santa-maria-de-cayon.github.io/contact.messager/terms-and-conditions.html")))
        }
    }

    private fun CreateNewAccount(){
        val email: String = emailRegister.text.toString().trim()
        val password = passwordRegister.text.toString().trim()
        if(email.isEmpty() || password.isEmpty()){
            App.showToast(this,  R.string.pleaseEnterEmailPassword); return
        } else {
            loadingBar.setTitle(resources.getString(R.string.creatingNewAccount))
            loadingBar.setMessage(resources.getString(R.string.pleaseWait))
            loadingBar.setCanceledOnTouchOutside(true)
            loadingBar.show()
            CreateNewAccount(this).createNewAccount(email, password, loadingBar)
        }
    }


    override fun onStart() {
        super.onStart()
        val currentUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser
        if(currentUser != null){ SendUserToMainActivity(); }
    }
    private fun SendUserToMainActivity(){ startActivity(Intent(this, MainActivity::class.java)); finish(); }
}