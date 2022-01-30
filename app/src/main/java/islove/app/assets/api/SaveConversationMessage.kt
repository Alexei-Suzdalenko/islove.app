package islove.app.assets.api
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import islove.app.assets.classes.MessageChat

class SaveConversationMessage {
    val miId = FirebaseAuth.getInstance().currentUser!!.uid.toString()
    val firestore = FirebaseFirestore.getInstance().collection("conversation").document("chats")

    fun createOrGetCurrentChatChannel(sender: String, receiver: String, onComplete:(chatId: String) -> Unit) {
        val refSenderChat = FirebaseDatabase.getInstance().reference.child("chats").child(sender).child(receiver)
        val refReceriverChat = FirebaseDatabase.getInstance().reference.child("chats").child(receiver).child( sender)

        refSenderChat.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {}
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    val chatId = snapshot.child("chat_id").value.toString()
                    onComplete(chatId)
                } else  {
                    val chatId = refReceriverChat.push().key.toString()
                    refSenderChat.child("chat_id").setValue(chatId)
                    refReceriverChat.child("chat_id").setValue(chatId).addOnCompleteListener {
                        if (it.isSuccessful){
                            onComplete(chatId)
                        }
                    }
                }
            }
        })
    }

    fun saveNewMessage(messageText: String, chatChannelId: String){
        val time = System.currentTimeMillis().toString()
        val chatMessage = MessageChat(time, messageText, miId)
        firestore.collection(chatChannelId).document().set(chatMessage)
    }

    fun getMessagesFromConversation(chatChannelId: String, onComplete: (message: MessageChat) -> Unit){
        val query = FirebaseFirestore.getInstance().collection("conversation").document("chats").collection(chatChannelId).orderBy("time", Query.Direction.ASCENDING)
        query.addSnapshotListener { snapshot, e ->
            if(snapshot!!.documents.size > 0) {
                for (snaps in snapshot.documents) {
                    onComplete(MessageChat(snaps.get("time").toString(), snaps.get("text").toString(), snaps.get("userid").toString()))
                }
            }
        }
    }
}