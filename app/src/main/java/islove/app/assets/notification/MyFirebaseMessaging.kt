package islove.app.assets.notification
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
import java.lang.Math.random

class MyFirebaseMessaging: FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        NotificationWork().saveUserToken()
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

   //     if( firebaseUser != null && sented == firebaseUser.uid ){
   //         if( currentOnlineUser != user ){
                if( Build.VERSION.SDK_INT > Build.VERSION_CODES.O){
                    sendOreoNotificatioin(remoteMessage)
                } else {
                    sendNotificatioin(remoteMessage)
                }
   //         }
   //     }
        
    }

    private fun sendNotificatioin(remoteMessage: RemoteMessage) {
        val intent = Intent(applicationContext, ChatConversartionActivity::class.java)
        intent.putExtra("sender",  remoteMessage.data["sender"].toString() )
        intent.putExtra("receiver", remoteMessage.data["receiver"].toString() )
        intent.putExtra("name", remoteMessage.data["name"].toString() )
        val title = remoteMessage.data["body"].toString()
        intent.putExtra("image", remoteMessage.data["image"].toString())


        val rand: Int = (1..999999999).random()
        val pendingIntent = PendingIntent.getActivity(this, rand, intent, PendingIntent.FLAG_ONE_SHOT)
        val builder = NotificationCompat.Builder(this)
               .setContentIntent(pendingIntent)
               .setContentTitle(title)
               .setContentText("Is Love App")
               .setSmallIcon(R.drawable.email.toInt())
               .setAutoCancel(true)
        val noti = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        noti.notify(rand, builder.build())
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun sendOreoNotificatioin(remoteMessage: RemoteMessage) {
        val intent = Intent(applicationContext, ChatConversartionActivity::class.java)
        intent.putExtra("sender",  remoteMessage.data["sender"].toString() )
        intent.putExtra("receiver", remoteMessage.data["receiver"].toString() )
        intent.putExtra("name", remoteMessage.data["name"].toString() )
        val title = remoteMessage.data["body"].toString()
        intent.putExtra("image", remoteMessage.data["image"].toString())

        val rand: Int = (1..999999999).random()

        val pendingIntent = PendingIntent.getActivity(this, rand, intent, PendingIntent.FLAG_ONE_SHOT)
        val defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val oreoNotification = Oreonotification(this)
        val builder: Notification.Builder = oreoNotification.getOreoNotification(title,"Is Love App", pendingIntent, defaultSound,  R.drawable.email.toString())

        oreoNotification.getManager!!.notify(rand, builder.build())
    }


}



































