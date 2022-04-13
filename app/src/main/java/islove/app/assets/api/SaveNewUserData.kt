package islove.app.assets.api
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

import islove.app.MainActivity
import islove.app.R
import islove.app.MyProfileActivity

import islove.app.assets.classes.App
import islove.app.assets.classes.App.Companion.editor
import islove.app.assets.classes.Conversation

import islove.app.assets.classes.User
import kotlinx.android.synthetic.main.activity_settings.*

class SaveNewUserData {
    val id           = FirebaseAuth.getInstance().currentUser!!.uid
    val email = App.sharedPreferences.getString("email", "").toString()
    val rootRef   = FirebaseFirestore.getInstance().collection("user").document(id)
    val storage   = FirebaseStorage.getInstance().reference.child("profile")
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
               val user = User(document["id"].toString(), document["age"].toString(), document["country"].toString(), document["image"].toString(), document["locality"].toString(), document["name"].toString(), document["online"].toString(), document["postal"].toString(), document["status"].toString(), document["token"].toString(), document["back"].toString(), document["gender"].toString(), document["search"].toString() )
               onComplete(user)
           } else if(settingsActivity != null){ App.showToast(settingsActivity, R.string.setUpdateProfileInformation); }
        }
    }

    fun saveUserImage(imageUri: Uri?, context: Context, fragment: MyProfileActivity) {
        fragment.progressBarProfile.visibility = View.VISIBLE
        val fileRef = FirebaseStorage.getInstance().reference.child("perfil").child(email +App.imageType+".jpg")
        if (imageUri != null) {
            fileRef.putFile(imageUri).continueWithTask { task ->
                if (!task.isSuccessful) { task.exception?.let {
                    fragment.progressBarProfile.visibility = View.GONE
                    Toast.makeText(context, "ERROR FILE UPLOAD", Toast.LENGTH_LONG).show() } }
                fileRef.downloadUrl }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result.toString()
                    val map = HashMap<String, Any>()
                    map[App.imageType] = downloadUri
                    editor.putString(App.imageType, downloadUri).apply()
                    if(App.imageType == "image") Glide.with( context ).load( downloadUri ).into( fragment.profileImage )
                    else Glide.with( context ).load( downloadUri ).into( fragment.backImageMyProfile )
                    FirebaseFirestore.getInstance().collection("user").document(Firebase.auth.currentUser!!.uid).update(map).addOnCompleteListener { fragment.progressBarProfile.visibility = View.GONE }
                } else { Toast.makeText(context, "ERROR FILE UPLOAD", Toast.LENGTH_LONG).show() }
            }
        }
    }

    fun getListUsers( onComplete: (resultUsers: MutableList<User>) -> Unit){
        val resultUsers: MutableList<User> = mutableListOf()
        val usersBlocked = App.sharedPreferences.getString("block", "").toString()
        val country = App.sharedPreferences.getString("country", "es").toString()
        val gender  = App.sharedPreferences.getString("gender", "man").toString()
        val search  = App.sharedPreferences.getString("search", "man").toString()

        refListUsers
            // .whereEqualTo("country", country)
            // .whereEqualTo("gender", search)
            // .whereEqualTo("search", gender)
            .get().addOnSuccessListener  { documents ->
            for (it in documents) {
              val  user = User(it["id"].toString(), it["age"].toString(), it["country"].toString(), it["image"].toString(), it["locality"].toString(), it["name"].toString(), it["online"].toString(), it["postal"].toString(), it["status"].toString(), it["token"].toString(), it["back"].toString(), "", "", it["email"].toString())
                App.listAllUsers.add(user)
                if( id != user.id && ! usersBlocked.contains(user.id, ignoreCase = true) &&  user.name != "null" && user.image != "null"){ resultUsers.add(user) }
            }
                onComplete(resultUsers)
        }
    }


    fun getListIdsContacts( onComplete: (result: User) -> Unit ){
        val usersBlocked = App.sharedPreferences.getString("block", "").toString()
        val listChatsIdsContact = mutableListOf <String>()
        FirebaseFirestore.getInstance().collection("enganched").document(id).collection("chat").get().addOnSuccessListener { documents ->
            for(doc in documents.documents) {
                if(! usersBlocked.contains(doc.id, ignoreCase = true)) listChatsIdsContact.add(doc.id)
            }
            for(index in listChatsIdsContact.indices) {
                 refListUsers.document(listChatsIdsContact[index]).get().addOnSuccessListener { userF ->
                     val user = userF.toObject<User>()
                     if (user != null) { onComplete(user) }
                 }
             }
        }
    }




}