package islove.app.assets.notification
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import islove.app.R
import islove.app.assets.classes.App
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ServiceNotification {
    val miId = FirebaseAuth.getInstance().currentUser!!.uid
    fun sentNotification(channelId: String, receiver: String, token: String, messageText: String, image: String, name: String){

        val apiService = Client.Client.getClient("https://fcm.googleapis.com/")!!.create(APIService::class.java)
        val data = Data( channelId, miId,   receiver,  messageText , name,  image, App.otherUserData!!.age, App.sharedPreferences.getString("token" ,"").toString())

        val sender1 = Sender( data, token )

        apiService.sendNotification( sender1 ).enqueue(object:
            Callback<MyResponse> { override fun onFailure(call: Call<MyResponse>, t: Throwable) {}
            override fun onResponse(call: Call<MyResponse>, response: Response<MyResponse>) {
                if( response.code() == 200 ){ if( response.body()?.success != 1 ){ } }
            }
        })
    }
}