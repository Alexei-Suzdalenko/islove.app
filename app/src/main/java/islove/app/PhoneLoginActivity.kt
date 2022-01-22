package islove.app
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import islove.app.assets.api.SaveNewUserData
import islove.app.assets.classes.App
import kotlinx.android.synthetic.main.activity_phone_login.*
import java.util.concurrent.TimeUnit
class PhoneLoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private var storedVerificationId: String? = ""
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private lateinit var loadingBar: ProgressDialog
    var userNumber = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_login)
        auth = Firebase.auth
        loadingBar = ProgressDialog(this)

        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                signInWithPhoneAuthCredential(credential)
            }
            override fun onVerificationFailed(e: FirebaseException) {
                loadingBar.dismiss()
                pnoneNumberEditText.visibility = View.VISIBLE
                sendVerCodeButton.visibility = View.VISIBLE
                verificationCode.visibility = View.GONE
                sendVerify.visibility = View.GONE
                Toast.makeText(applicationContext, e.message.toString(), Toast.LENGTH_LONG).show()
            }
            override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                storedVerificationId = verificationId
                resendToken = token
                loadingBar.dismiss()
                App.showToast(this@PhoneLoginActivity, R.string.codeSent)
                pnoneNumberEditText.visibility = View.GONE
                sendVerCodeButton.visibility = View.GONE
                verificationCode.visibility = View.VISIBLE
                sendVerify.visibility = View.VISIBLE
            }
        }

        sendVerCodeButton.setOnClickListener {
            userNumber = pnoneNumberEditText.text.toString().trim()
            if(userNumber.isEmpty()) App.showToast(this, R.string.insertNumberPhone)
            else {
                loadingBar.setTitle(resources.getString(R.string.phoneVerification))
                loadingBar.setMessage(resources.getString(R.string.pleaseWait))
                loadingBar.setCanceledOnTouchOutside(false)
                loadingBar.show()
                PhoneAuthProvider.verifyPhoneNumber(
                    PhoneAuthOptions.newBuilder(auth).setPhoneNumber(userNumber).setTimeout(60L, TimeUnit.SECONDS).setActivity(this).setCallbacks(callbacks).build())
            }
        }

        sendVerify.setOnClickListener {
            val verificationCode = verificationCode.text.toString().trim()
            if(verificationCode.isEmpty()) App.showToast(this, R.string.insertVerificationCode)
            else {
                loadingBar.setTitle(resources.getString(R.string.codeVerification))
                loadingBar.setMessage(resources.getString(R.string.pleaseWait))
                loadingBar.setCanceledOnTouchOutside(false)
                loadingBar.show()
                signInWithPhoneAuthCredential(PhoneAuthProvider.getCredential(storedVerificationId!!, verificationCode))
            }
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    SaveNewUserData().saveNewUser("", "", userNumber)
                    startActivity(Intent(this, MainActivity::class.java)); finish()
                } else {
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(this, task.exception.toString(), Toast.LENGTH_LONG).show()
                    }
                    startActivity(Intent(this, PhoneLoginActivity::class.java)); finish()
                }
            }
    }

   override fun onStart() {
       super.onStart()
       val currentUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser
       if(currentUser != null){ startActivity(Intent(this, MainActivity::class.java)); finish(); }
   }
}




















/*

        sendVerCodeButton.setOnClickListener {
            val userNuber = pnoneNumberEditText.text.toString().trim()
            if(userNuber.isEmpty()) App.showToast(this, R.string.insertNumberPhone)
            else {
                loadingBar.setTitle(resources.getString(R.string.phoneVerification))
                loadingBar.setMessage(resources.getString(R.string.pleaseWait))
                loadingBar.setCanceledOnTouchOutside(false)
                loadingBar.show()
                // PhoneAuthProvider.getInstance().verifyPhoneNumber(userNuber, 60, TimeUnit.SECONDS, this, callbacks)
            }
        }

        sendVerify.setOnClickListener {
            val verificationCode = verificationCode.text.toString().trim()
            if(verificationCode.isEmpty()) App.showToast(this, R.string.insertVerificationCode)
            else {
                loadingBar.setTitle(resources.getString(R.string.codeVerification))
                loadingBar.setMessage(resources.getString(R.string.pleaseWait))
                loadingBar.setCanceledOnTouchOutside(false)
                loadingBar.show()
                // val credential = PhoneAuthProvider.getCredential(storedVerificationId, verificationCode)
                // signInWithPhoneAuthCredential(credential)
            }
        }

    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    App.showToast(this, R.string.conglarationsYou)
                    val user = task.result?.user
                    startActivity(Intent(this, MainActivity::class.java)); finish()
                } else {
                    if (task.exception is FirebaseAuthInvalidCredentialsException) { Toast.makeText(this, task.exception.toString(), Toast.LENGTH_LONG).show(); }
                }
                loadingBar.dismiss()
            }
    }
}
*/
