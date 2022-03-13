package islove.app.assets.api
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import islove.app.assets.classes.Conversation
import islove.app.assets.classes.MessageChat

class SaveConversationMessage {
    val miId = FirebaseAuth.getInstance().currentUser!!.uid.toString()
    val enganchedMyChat = FirebaseFirestore.getInstance().collection("enganchedChat").document(miId)
    val firestore = FirebaseFirestore.getInstance().collection("conversation").document("chats")

    fun createOrGetCurrentChatChannel(receiver: String, onComplete:(chatId: String) -> Unit) {
        enganchedMyChat.collection("chat").document(receiver).get().addOnSuccessListener { document ->
            if(document.exists()){ val conversationId = document["id"].toString(); onComplete(conversationId)
            } else {
                val conversId = System.currentTimeMillis().toString()
                enganchedMyChat.collection("chat").document(receiver).set(Conversation(conversId))
                FirebaseFirestore.getInstance().collection("enganchedChat").document(receiver).collection("chat").document(miId).set(Conversation(conversId)).addOnSuccessListener { onComplete(conversId) }
            }
        }
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