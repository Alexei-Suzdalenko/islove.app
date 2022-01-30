package islove.app.assets.notification
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessaging

class NotificationWork {
    val id = FirebaseAuth.getInstance().currentUser!!.uid.toString()
    val rootRef = FirebaseDatabase.getInstance().reference.child("users").child(id)
    val notRef = FirebaseDatabase.getInstance().reference.child("notifications")

    fun saveNotification(receiver: String){
        val chatNotification = HashMap<String, Any>()
             chatNotification["from"] = id
             chatNotification["type"] = "request"
        notRef.child(receiver).push().setValue(chatNotification)
    }

    fun saveUserToken(){
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                if (task.result != null && !TextUtils.isEmpty(task.result)) {
                    val token: String = task.result!!.toString()

                    val tokenUpdate = HashMap<String, Any>()
                          tokenUpdate["token"] = token
                    rootRef.updateChildren(tokenUpdate)
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