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
    val enganchedMyChat = FirebaseFirestore.getInstance().collection("enganched")
    val firestore = FirebaseFirestore.getInstance().collection("conversation").document("chat")

    fun createOrGetCurrentChatChannel(receiver: String, onComplete:(chatId: String) -> Unit) {
        enganchedMyChat.document( receiver).collection("chat").document(miId).get().addOnSuccessListener { document ->
            if(document.exists()) {
                val conversationId = document["id"].toString()
                enganchedMyChat.document(miId).collection("chat").document(receiver).set(mapOf("id" to conversationId))
                onComplete(conversationId)
            } else {
                val conversId = System.currentTimeMillis().toString()
                enganchedMyChat.document(miId).collection("chat").document(receiver).set(Conversation(conversId))
                enganchedMyChat.document(receiver).collection("chat").document(miId).set(Conversation(conversId)).addOnSuccessListener { onComplete(conversId) }
            }
        }
    }

    // cuando entro desde listado de usuarios o desde listado de conversaciones !!!!
    fun getConversationChatChannel(otherUserId: String, onComplete: (channelId: String?) -> Unit){
        enganchedMyChat.document( miId ).collection("chat").document(otherUserId).get().addOnSuccessListener  {
            if(it.exists()) { val idChat = it["id"].toString(); onComplete( idChat )
            } else onComplete(null)
        }
    }

    fun saveNewMessage(messageText: String, chatChannelId: String){
        val time = System.currentTimeMillis().toString()
        val chatMessage = MessageChat(time, messageText, miId)
        firestore.collection(chatChannelId).document().set(chatMessage)
    }

    fun getMessagesFromConversation(chatChannelId: String, onComplete: (message: MessageChat) -> Unit){
        val query = firestore.collection(chatChannelId).orderBy("time", Query.Direction.ASCENDING)
        query.addSnapshotListener { snapshot, e ->
            if(snapshot!!.documents.size > 0) {
                for (snaps in snapshot.documents) {
                    onComplete(MessageChat(snaps.get("time").toString(), snaps.get("text").toString(), snaps.get("userid").toString()))
                }
            }
        }
    }
}