package islove.app.assets.api
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

import islove.app.MainActivity
import islove.app.R
import islove.app.MyProfileActivity

import islove.app.assets.classes.App

import islove.app.assets.classes.User
class SaveNewUserData {
    val id = FirebaseAuth.getInstance().currentUser!!.uid
    val rootRef = FirebaseDatabase.getInstance().reference.child("users").child(id)
    val storage = FirebaseStorage.getInstance().reference.child("profile_image").child("$id.jpg")
    val refChatId = FirebaseDatabase.getInstance().reference.child("chats").child(id)

    val refUserInfo = FirebaseFirestore.getInstance().collection("user").document(id)
    val refListUsers = FirebaseFirestore.getInstance().collection("user")

    fun intentGetUserDataIfExsistEnDataBase(onComplete:(user: User? ) -> Unit){
        var user: User? = null
        refUserInfo.get().addOnSuccessListener {
            if(it.exists()){
                user = User("", it["age"].toString(), it["country"].toString(), it["image"].toString(), it["locality"].toString(), it["name"].toString(), it["online"].toString(), it["postal"].toString(), it["status"].toString(), "", it["backImage"].toString()      )
            }
            onComplete(user)
        }
    }

    fun saveNewUser(email: String, password: String, phone: String){
        if(email.isEmpty() || email.isBlank()){ App.editor.putString("email", phone); App.editor.apply()
        } else  App.editor.putString("email", email); App.editor.apply()

        intentGetUserDataIfExsistEnDataBase {  user ->
            val profile = HashMap<String, Any>()
            profile["id"] = id
            if(user != null) refUserInfo.update(profile)
            else refUserInfo.set(profile)
        }
    }

    fun updateUserNameStatus(name: String, status: String, c: Context){
        val profile = HashMap<String, Any>()
              profile["id"] = id
              profile["name"] = name
              profile["status"] = status
        rootRef
            .updateChildren( profile )
            .addOnCompleteListener { it ->
                if(it.isSuccessful()){
                    App.showToast(c, R.string.profileUpdatedSuccessfuly)
                    c.startActivity(Intent(c, MainActivity::class.java))
                } else {
                    Toast.makeText(c, "Error:" +  it.exception.toString(), Toast.LENGTH_LONG).show()
                }
        }
    }

    fun getUserInformationProfile(settingsActivity: MyProfileActivity?, onComplete: (user: User) -> Unit){
        FirebaseDatabase.getInstance().reference.child("users").child(id).addListenerForSingleValueEvent(object: ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {}
                override fun onDataChange(data: DataSnapshot) {
                    if(data.exists() && data.hasChild("name")) {
                        val userName = data.child("name").value.toString()
                        val userStatus = data.child("status").value.toString()
                        var image = ""
                        if (data.hasChild("image")) { image = data.child("image").value.toString(); }
                        val user = User("", userName, "", "", userStatus, image)
                        onComplete(user)
                    } else if(settingsActivity != null){ App.showToast(settingsActivity, R.string.setUpdateProfileInformation); }
                }
        })
    }

    fun saveUserImage(uri: Uri, onComplete: (result : String) -> Unit){
        storage.putFile(uri).addOnCompleteListener { task ->
            if(task.isSuccessful) {
                storage.downloadUrl.addOnCompleteListener { it ->
                    val profile = HashMap<String, Any>()
                    profile["image"] = it.result.toString()
                    rootRef.updateChildren(profile)
                    onComplete( it.result.toString() )
                }
            } else onComplete("Error")
        }
    }

    fun getListUsers( onComplete: (result: User) -> Unit){
        refListUsers.get().addOnSuccessListener  { documents ->
            for (it in documents) {
              val  user = User("", it["age"].toString(), it["country"].toString(), it["image"].toString(), it["locality"].toString(), it["name"].toString(), it["online"].toString(), it["postal"].toString(), it["status"].toString(), "", it["backImage"].toString() );
                onComplete(user)
            }
        }
    }


    fun getListIdsContacts( onComplete: (result: User) -> Unit ){
        val listChatsIdsContact = mutableListOf<String>()
      // refChatId.addListenerForSingleValueEvent(object :ValueEventListener{
      //     override fun onDataChange(snapshot: DataSnapshot) {                            Log.d("listChatsId", "miId == " + id)
      //         for(snaps in snapshot.children){
      //             val chatId = snaps.key.toString()
      //            if(id != chatId) { listChatsIdsContact.add(chatId); }
      //         }
      //         for(index in listChatsIdsContact.indices){
      //             refListUsers.child(listChatsIdsContact[index]).addListenerForSingleValueEvent(object : ValueEventListener{
      //                 override fun onDataChange(snapshot: DataSnapshot) {
      //                    val userId = snapshot.child("id").value.toString()
      //                    val name  = snapshot.child("name").value.toString()
      //                    val status = snapshot.child("status").value.toString()
      //                    val image = snapshot.child("image").value.toString()
      //                     val token = snapshot.child("token").value.toString()
      //                     onComplete(User(userId, name, "", "", status, image, token))
      //                 }
      //                 override fun onCancelled(error: DatabaseError) {}
      //             })
      //         }
      //     }
      //     override fun onCancelled(error: DatabaseError) {}
      // })
    }




}