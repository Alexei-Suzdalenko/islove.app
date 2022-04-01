package islove.app.assets.notification
import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.Ringtone
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import islove.app.ChatConversartionActivity
import islove.app.R
import islove.app.assets.classes.App
import islove.app.assets.classes.User
import java.lang.Math.random
import kotlin.system.exitProcess

class MyFirebaseMessaging: FirebaseMessagingService() {
    var notId:         Int = 0
    var chatId:       String = ""
    var sender:      String = ""
    var receiver:    String = ""
    var userName: String = ""
    var message:   String = ""
    var image:       String = ""
    var age:           String = ""
    var miToken:    String = ""
    lateinit var intent: Intent
    var firebaseUser: FirebaseUser? = null
    var usersBlockedMe = ""

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        firebaseUser = FirebaseAuth.getInstance().currentUser
        intent = Intent(applicationContext, ChatConversartionActivity::class.java); intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

        chatId = remoteMessage.data["chatId"].toString()
        sender = remoteMessage.data["sender"].toString()
        receiver = remoteMessage.data["receiver"].toString()
        userName = remoteMessage.data["userName"].toString()
        image = remoteMessage.data["image"].toString()
        message = remoteMessage.data["message"].toString()
        age = remoteMessage.data["age"].toString()
        miToken =  remoteMessage.data["miToken"].toString()


        if(firebaseUser!!.uid == sender && chatId == "block" && message == "block") { exitProcess(0)
          //         val miId = FirebaseAuth.getInstance().currentUser!!.uid
          //         FirebaseDatabase.getInstance().reference.child("block/$miId").addListenerForSingleValueEvent(object : ValueEventListener{
          //             override fun onDataChange(snapshot: DataSnapshot) {
          //                 for( snaps in snapshot.children ){ val valueData = snaps.value; usersBlockedMe += ", $valueData" }
          //                 App.editor.putString("block", usersBlockedMe).apply()
          //                 Log.d("usersBlockedMe", "usersBlockedMe1=" + usersBlockedMe.toString())
          //                 val usersBlocked = App.sharedPreferences.getString("block", "").toString()
          //                 Log.d("usersBlockedMe", "usersBlockedMe result=" + usersBlocked.toString())
          //             }
          //             override fun onCancelled(error: DatabaseError) {}
          //         })
          //         FirebaseDatabase.getInstance().reference.child("block/$miId").addValueEventListener(object:
          //             ValueEventListener {
          //             override fun onCancelled(error: DatabaseError) {}
          //             override fun onDataChange(snapshot: DataSnapshot) {
          //                 for( snaps in snapshot.children ){ val valueData = snaps.value; usersBlockedMe += ", $valueData" }
          //                 App.editor.putString("block", usersBlockedMe).apply()
          //                 Log.d("usersBlockedMe", "usersBlockedMe2=" + usersBlockedMe.toString())
          //                 val usersBlocked = App.sharedPreferences.getString("block", "").toString()
          //                 Log.d("usersBlockedMe", "usersBlockedMe result=" + usersBlocked.toString())
          //                 exitProcess(0)
          //             }
          //         })
        }

        intent.putExtra("chatId", chatId)
        intent.putExtra("sender", sender)
        intent.putExtra("receiver", receiver)
        intent.putExtra("userName", userName)
        intent.putExtra("image", image)
        intent.putExtra("age", age)
        intent.putExtra("miToken", miToken)

        notId = App.getIdNoification(sender)

        if( firebaseUser != null && sender == firebaseUser!!.uid && message != "block") {
            if( Build.VERSION.SDK_INT > Build.VERSION_CODES.O) sendOreoNotificatioin(remoteMessage)
            else sendNotificatioin(remoteMessage)
        }
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun sendNotificatioin(remoteMessage: RemoteMessage) {
        val pendingIntent = PendingIntent.getActivity(this, notId, intent, PendingIntent.FLAG_ONE_SHOT)
        val builder = NotificationCompat.Builder(this)
            .setContentIntent(pendingIntent)
            .setContentTitle(userName)
            .setContentText(message)
            .setSmallIcon(R.drawable.message_icon)
            .setAutoCancel(true)
        val noti = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        noti.notify(notId, builder.build())
    }


    @SuppressLint("UnspecifiedImmutableFlag")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun sendOreoNotificatioin(remoteMessage: RemoteMessage) {
        val pendingIntent = PendingIntent.getActivity(this, notId, intent, PendingIntent.FLAG_ONE_SHOT)
        val oreoNotification = Oreonotification(this)
        val builder: Notification.Builder = oreoNotification.getOreoNotification(userName, message, pendingIntent  )

        oreoNotification.getManager!!.notify(notId, builder.build())
    }

    override fun onNewToken(token: String) {
        if(FirebaseAuth.getInstance().currentUser != null) {
            NotificationWork().saveUserToken()
        }
    }


}
























