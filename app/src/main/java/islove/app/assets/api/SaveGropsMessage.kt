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
    val refGroupDatabase = FirebaseDatabase.getInstance().reference.child("groups").child(groupNameR)
    val refFirestoreGroupChat = FirebaseFirestore.getInstance().collection("groups").document("groups").collection(groupNameR)
        // .orderBy("time", Query.Direction.ASCENDING)


    @SuppressLint("SimpleDateFormat")
    fun saveMessage(user: User, message: String){

        val messageInfoMap = HashMap<String, Any>()
             messageInfoMap["name"] = user.name
             messageInfoMap["message"] = message
             messageInfoMap["time"] = System.currentTimeMillis()
        // no guardar en database realtime
        /// refGroupDatabase.child(refGroupDatabase.push().key.toString()).updateChildren(messageInfoMap)

        refFirestoreGroupChat.document().set(messageInfoMap)
    }


        fun getMessages( onComplete:(message: MessageGroup) -> Unit) {
            //  refGroupDatabase.addChildEventListener(object: ChildEventListener{
            //      override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
            //         val message = dataSnapshot.getValue<Message>()
            //          if (message != null) {
            //              onComplete(message)
            //          }
            //      }
            //      override fun onChildChanged(p0: DataSnapshot, p1: String?) {}
            //      override fun onChildRemoved(p0: DataSnapshot) {}
            //      override fun onChildMoved(p0: DataSnapshot, p1: String?) {}
            //      override fun onCancelled(p0: DatabaseError) {}
            //  })
            // return all messages, how to get only last message ?¿¿?¿?
            refFirestoreGroupChat.orderBy("time", Query.Direction.ASCENDING).addSnapshotListener { snapshot, e ->
                if(snapshot!!.documents.size > 0) {
                    for (snaps in snapshot.documents) {
                        onComplete(MessageGroup(snaps.get("message").toString(), snaps.get("name").toString(), snaps.get("time") as Long))
                    }
                }
            }
        }

}