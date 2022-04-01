package islove.app.assets.api
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import islove.app.assets.classes.App
import islove.app.assets.notification.ServiceNotification

class BlockUserFire {
    val miId = FirebaseAuth.getInstance().currentUser?.uid.toString()

    fun block(blockId: String, onComlete:(res: String)->Unit){
        Log.d("remoteMessagingData", "sent Data Block = "  +
            App.otherUserData!!.id + " ," +
            miId + " ," +
            App.otherUserData!!.name + " ," +
            "block" + " ," +
            "" + " ," +
            App.otherUserData!!.token + " ," +
            "" + " ," +
            "")

        ServiceNotification().sentNotification (
            "block",
            App.otherUserData!!.id,
            miId,
            App.otherUserData!!.name,
            "block",
           "",
            App.otherUserData!!.token,
            "",
            ""
        )


        val dataUser             = HashMap<String, Any>()
        dataUser[miId]     = blockId
        dataUser[blockId] = miId
        FirebaseDatabase.getInstance().reference.child("block/$miId").setValue(dataUser)
        FirebaseDatabase.getInstance().reference.child("block/$blockId").setValue(dataUser).addOnSuccessListener { onComlete("ok") }
    }

}