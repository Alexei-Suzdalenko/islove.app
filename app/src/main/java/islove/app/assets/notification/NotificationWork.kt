package islove.app.assets.notification
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import islove.app.assets.classes.App

class NotificationWork {
    val id = FirebaseAuth.getInstance().currentUser!!.uid.toString()
    val rootRef = FirebaseFirestore.getInstance().collection("user").document(id)


    fun saveUserToken(){
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                if (task.result != null && !TextUtils.isEmpty(task.result)) {
                    val token: String = task.result!!.toString()

                    val tokenUpdate = HashMap<String, Any>()
                          tokenUpdate["time"] = System.currentTimeMillis().toString()
                          tokenUpdate["token"] = token; App.editor.putString("token", token).apply()
                          // tokenUpdate["email"] = token; App.sharedPreferences.getString("email", " ").toString()
                    rootRef.update(tokenUpdate)
                }
            }
        }
    }

/*
comandos para utilizar notifiaciones de Firebase
C:\_OJO_NEW_ACCOUNT_ANDROID_DEVELOPER\islove.app\NotificationFolder
https://youtu.be/I6p-kYOALbE?list=PLxefhmF0pcPmtdoud8f64EpgapkclCllj
firebase init
y
functions
 */















}