package islove.app.assets.api

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore

class CreateChatChannelFirebase {
    private val firestore = FirebaseFirestore.getInstance().collection("enganched_chat")
    private val firebaseReference = FirebaseDatabase.getInstance().reference
    private val miId = FirebaseAuth.getInstance().currentUser?.uid.toString()

    fun createOrGetChatChannle(otherUserId: String, onComplete: (channelId: String) -> Unit) {
        firebaseReference.child("enganched_chat/$otherUserId/$otherUserId").get().addOnSuccessListener  {
            if(it.exists()) {
                val idChat = it.child("id").value.toString()
                onComplete( idChat )
            } else {
                // 1. create new chatChannel
                val newChatId = System.currentTimeMillis().toString()
                // firebaseReference.child("chats/$newChatId").setValue(ChatChannel(mutableListOf(miId, otherUserId)))

                // 2. save channel Id en mi user and in other user
                firebaseReference.child("enganched_chat/$miId/$otherUserId").setValue(mapOf("id" to newChatId))
                firebaseReference.child("enganched_chat/$otherUserId/$miId").setValue(mapOf("id" to newChatId))
                onComplete(newChatId)
            }
        }
    }

    fun deleteUserConversation(otherUserId: String, onComplete: (res: String) -> Unit){
        FirebaseFirestore.getInstance().collection("enganched_chat").document(miId).collection("channel").document(otherUserId)
            .delete().addOnSuccessListener { onComplete("ok") }
    }
}