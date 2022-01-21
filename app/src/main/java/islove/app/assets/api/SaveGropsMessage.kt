package islove.app.assets.api
import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue
import islove.app.assets.classes.Message
import islove.app.assets.classes.User
import java.text.SimpleDateFormat
import java.util.*

class SaveGropsMessage(val groupName: String?) {
    val userId = FirebaseAuth.getInstance().currentUser!!.uid
    private val groupNameR = groupName ?: ""
    val refGroupDatabase = FirebaseDatabase.getInstance().reference.child("groups").child(groupNameR)


    @SuppressLint("SimpleDateFormat")
    fun saveMessage(user: User, message: String){

        val cal = Calendar.getInstance()
        val formatDate = SimpleDateFormat("dd/MM/yyyy")
        val currentDate = formatDate.format(cal.time)

        val formatTime = SimpleDateFormat("hh:mm:ss a")
        val currentTime = formatTime.format(cal.time)

        val messageInfoMap = HashMap<String, Any>()
             messageInfoMap["name"] = user.name
             messageInfoMap["message"] = message
             messageInfoMap["date"] =currentDate
             messageInfoMap["time"] = currentTime

        refGroupDatabase.child(refGroupDatabase.push().key.toString()).updateChildren(messageInfoMap)
    }


        fun getMessages( onComplete:(message: Message) -> Unit){
            refGroupDatabase.addChildEventListener(object: ChildEventListener{
                override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
                   val message = dataSnapshot.getValue<Message>()
                    if (message != null) {
                        onComplete(message)
                    }
                }
                override fun onChildChanged(p0: DataSnapshot, p1: String?) {}
                override fun onChildRemoved(p0: DataSnapshot) {}
                override fun onChildMoved(p0: DataSnapshot, p1: String?) {}
                override fun onCancelled(p0: DatabaseError) {}
            })
        }

}