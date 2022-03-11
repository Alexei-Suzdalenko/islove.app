package islove.app.assets.api
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
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
    val id           = FirebaseAuth.getInstance().currentUser!!.uid
    // val rootRef   = FirebaseDatabase.getInstance().reference.child("users").child(id)
    val rootRef   = FirebaseFirestore.getInstance().collection("user").document(id)
    val email      = App.sharedPreferences.getString("email", "a").toString()
    val storage   = FirebaseStorage.getInstance().reference.child("$email.jpg")
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

    fun updateUserNameStatus(userName: String, status: String, age: String, gender: String, search: String,  c: Context){
        val profile = HashMap<String, Any>()
              profile["id"] = id
              profile["name"] = userName;  App.editor.putString("name", userName).apply()
              profile["status"] = status;      App.editor.putString("status", status).apply()
              profile["age"] = age;              App.editor.putString("age", age).apply()
              profile["gender"] = gender;    App.editor.putString("gender", gender).apply()
              profile["search"] = search;    App.editor.putString("search", search).apply()
        rootRef.update(profile).addOnCompleteListener {
            if(it.isSuccessful) c.startActivity(Intent(c, MainActivity::class.java))
            else Toast.makeText(c, "Error:" +  it.exception.toString(), Toast.LENGTH_LONG).show()
        }
    }

    fun getUserInformationProfile(settingsActivity: MyProfileActivity?, onComplete: (user: User) -> Unit){
        FirebaseFirestore.getInstance().collection("user").document(id).get().addOnSuccessListener  { document ->
           if(document != null){
               val user = User(document["id"].toString(), document["age"].toString(), document["country"].toString(), document["image"].toString(), document["locality"].toString(), document["name"].toString(), document["online"].toString(), document["postal"].toString(), document["status"].toString(), document["token"].toString(), "")
               onComplete(user)
           } else if(settingsActivity != null){ App.showToast(settingsActivity, R.string.setUpdateProfileInformation); }
        }
    }

    fun saveUserImage(uri: Uri, onComplete: (result : String) -> Unit){
        var urlString = ""
        storage.putFile(uri).addOnCompleteListener { task ->
            if(task.isSuccessful) {
                storage.downloadUrl.addOnCompleteListener { it ->
                    urlString = it.result.toString()
                    val profile = HashMap<String, Any>()
                          profile["image"] = urlString
                    FirebaseFirestore.getInstance().collection("user").document(FirebaseAuth.getInstance().currentUser!!.uid).update(profile).addOnCompleteListener {
                        if(it.isSuccessful) App.editor.putString("image", urlString).apply()
                    }
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