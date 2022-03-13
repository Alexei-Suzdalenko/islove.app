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
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import islove.app.ChatConversartionActivity
import islove.app.R
import islove.app.assets.classes.App
import islove.app.assets.classes.User
import java.lang.Math.random

class MyFirebaseMessaging: FirebaseMessagingService() {
    var notId: Int = 0
    var user: User? = null
    var age: String = ""
    var name: String = ""
    var message: String = ""
    var intent: Intent? = null
    override fun onNewToken(token: String) { if (FirebaseAuth.getInstance().currentUser != null) NotificationWork().saveUserToken(); }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage); // Log.d("otherUserDataA", "remoteMessage = " + remoteMessage.data.toString())
        intent = Intent(applicationContext, ChatConversartionActivity::class.java); intent!!.putExtra("message", "message")
        val curU = remoteMessage.data
        name = curU["name"].toString()
        user = User(curU["sender"].toString(), curU["age"].toString(), "", curU["image"].toString(), "", curU["name"].toString(), "", "", curU["status"].toString(), curU["token"].toString())

        App.otherUserData = user
        notId = App.getIdNoification(curU["sender"].toString())
        message = curU["message"].toString()
        if( Build.VERSION.SDK_INT > Build.VERSION_CODES.O) sendOreoNotificatioin() else sendNotificatioin()
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun sendNotificatioin() {

        val pendingIntent = PendingIntent.getActivity(this, notId, intent, PendingIntent.FLAG_ONE_SHOT)
        val builder = NotificationCompat.Builder(this)
               .setContentIntent(pendingIntent)
               .setContentTitle(message)
               .setContentText(name)
               .setSmallIcon(R.drawable.email.toInt())
               .setAutoCancel(true)
        val noti = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        noti.notify(notId, builder.build())
    }


    @SuppressLint("UnspecifiedImmutableFlag")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun sendOreoNotificatioin() {

        val pendingIntent = PendingIntent.getActivity(this, notId, intent, PendingIntent.FLAG_ONE_SHOT)
        val defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val oreoNotification = Oreonotification(this)
        val builder: Notification.Builder = oreoNotification.getOreoNotification(message, name, pendingIntent, defaultSound,  R.drawable.email.toString())
        oreoNotification.getManager!!.notify(notId, builder.build())
    }


}



































