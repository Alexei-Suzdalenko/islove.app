package islove.app.assets.api
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CreateNewGroup {
    fun create(groupName: String, onComplete:(result: String) -> Unit){
        FirebaseDatabase.getInstance().reference
            .child("groups")
            .child(groupName)
            .setValue("")
            .addOnCompleteListener {
                if(it.isSuccessful()){
                    onComplete("ok")
                }

        }
    }

    fun getListGroups( onComlete: (result: ArrayList<String>) -> Unit){
        val listGroups = ArrayList<String>()
        FirebaseDatabase.getInstance().reference.child("groups").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                val childrenData = p0.children.iterator()
                listGroups.clear()
                while(childrenData.hasNext()){
                    val itemName = childrenData.next().key.toString()
                    listGroups.add(itemName)
                }
                onComlete(listGroups)
            }
        })
    }
}