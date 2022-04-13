package islove.app.assets.api
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import islove.app.assets.classes.User

class DeleteUsers {
    val miId = FirebaseAuth.getInstance().currentUser!!.uid
    val storageRef = FirebaseStorage.getInstance()
    val mesSecond: Long = 86400 * 45 * 1000;
    val currentTime = System.currentTimeMillis().toLong()
    val newTimeRes = currentTime - mesSecond
    val engRef =  Firebase.firestore.collection("enganched")

    fun startDeleting(listUsers: MutableList<User>){
        deleteImages(listUsers)
        deleteEnganchedChats(listUsers)
        deleteConversarion()
    }

    fun deleteImages(listUsers: MutableList<User>){
        for(item in listUsers){
            if(!(item.email.toString() == "alexei.saron@gmail.com" || item.email.toString() == "saron.alexei@gmail.com" || item.id == miId)) {
                    try{
                        if(item.online.toString().toLong() < newTimeRes.toLong()){
                            if(item.image.toString() != "null"){ storageRef.getReferenceFromUrl(item.image).delete() }
                            if(item.back.toString() != "null"){   storageRef.getReferenceFromUrl(item.back).delete() }
                        }
                    } catch (e: Exception){}
                if(item.online.toString()  == "null"  ){
                    if(item.image.toString() != "null"){ storageRef.getReferenceFromUrl(item.image).delete() }
                    if(item.back.toString() != "null"){   storageRef.getReferenceFromUrl(item.back).delete() }
                }
            }
        }
    }

    fun deleteEnganchedChats(listUsers: MutableList<User>){
        for(item in listUsers){
            try {
                if (item.online.toLong() < newTimeRes.toLong()) {
                    val mutableIdList: MutableList<String> = mutableListOf()
                    engRef.document(item.id).collection("chat").get().addOnSuccessListener {
                        for (doc in it.documents) { mutableIdList.add(doc.id) }
                        if (mutableIdList.size > 0) {
                            for (otherId in mutableIdList) {
                                engRef.document(item.id).collection("chat").document(otherId).delete()
                                engRef.document(otherId).collection("chat").document(item.id).delete()
                            }
                        }
                    }
                }
            } catch (e: Exception){}
        }
    }

    fun deleteConversarion(){

    }

}