package islove.app.assets.api
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import islove.app.assets.classes.ChatMessage
import islove.app.assets.classes.MessageGroup

class SaveConversationMessage() {
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
        val chatMessage = ChatMessage(time, messageText)
        firestore.collection(chatChannelId).document().set(chatMessage)
    }

    fun getMessagesFromConversation(chatChannelId: String, onComplete: (message: ChatMessage) -> Unit){
        val query = FirebaseFirestore.getInstance().collection("conversation").document("chats").collection(chatChannelId).orderBy("time", Query.Direction.ASCENDING)
        query.addSnapshotListener { snapshot, e ->
            if(snapshot!!.documents.size > 0) {
                for (snaps in snapshot.documents) {
                    onComplete(ChatMessage(snaps.get("time").toString(), snaps.get("text").toString()))
                }
            }
        }
    }
}