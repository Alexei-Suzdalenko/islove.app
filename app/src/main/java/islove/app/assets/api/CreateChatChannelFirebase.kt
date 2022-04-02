package islove.app.assets.api

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore

class CreateChatChannelFirebase {
    private val miId = FirebaseAuth.getInstance().currentUser?.uid.toString()


    fun deleteUserConversation(otherUserId: String, onComplete: (res: String) -> Unit){
        FirebaseFirestore.getInstance().collection("enganched").document(miId).collection("chat").document(otherUserId)
            .delete().addOnSuccessListener {
                onComplete("ok") }
    }
}