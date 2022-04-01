package islove.app.assets.api
import android.annotation.SuppressLint
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import islove.app.assets.classes.MessageGroup
import islove.app.assets.classes.User
import java.util.*

class SaveGropsMessage(val groupName: String?) {
    val userId = FirebaseAuth.getInstance().currentUser!!.uid
    private val groupNameR = groupName ?: ""
    val refFirestoreGroupChat = FirebaseFirestore.getInstance().collection("groups").document("groups").collection(groupNameR)
        // .orderBy("time", Query.Direction.ASCENDING)


    @SuppressLint("SimpleDateFormat")
    fun saveMessage(user: User, message: String){

        val messageInfoMap = HashMap<String, Any>()
             messageInfoMap["name"] = user.name
             messageInfoMap["message"] = message
             messageInfoMap["time"] = System.currentTimeMillis()
             messageInfoMap["userid"] = userId
        refFirestoreGroupChat.document().set(messageInfoMap)
    }


        fun getMessages( onComplete:(message: MessageGroup) -> Unit) {
            refFirestoreGroupChat.orderBy("time", Query.Direction.ASCENDING).addSnapshotListener { snapshot, e ->
                if(snapshot!!.documents.size > 0) {
                    for (snaps in snapshot.documents) {
                        onComplete(
                            MessageGroup(
                                snaps.get("message").toString(), snaps.get("name").toString(), snaps.get("time") as Long, snaps.get("userid").toString()
                            )
                        )
                    }
                }
            }
        }

}