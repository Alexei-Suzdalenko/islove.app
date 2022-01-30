package islove.app.assets.notification

import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import islove.app.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ServiceNotification {

    fun sentNotification(sender: String, receiver: String, token: String, messageText: String, image: String, name: String){

        val apiService = Client.Client.getClient("https://fcm.googleapis.com/")!!.create(APIService::class.java)
        val data = Data( sender,  receiver, messageText , name, image)

        // val sender = Sender( data, "cXZ6Vic2QPqptTH2qN71Q0:APA91bEP-GeIvUIsEGhQQZPDG3wLcKdDwGLuICpXXf57VCkF1JSSNosCUeHUp6qNUa0BtX4X1E21CwHFmDzwhNAZ0of_y1HxqESBIW1k4Me64nfCFbvG7Dux4DbOnJiHmWh1s4g2rMxA" )
        val sender = Sender( data, token )
        apiService?.sendNotification( sender )?.enqueue(object:
            Callback<MyResponse> { override fun onFailure(call: Call<MyResponse>, t: Throwable) {}
            override fun onResponse(call: Call<MyResponse>, response: Response<MyResponse>) {
                if( response.code() == 200 ){ if( response.body()?.success != 1 ){ } }
            }
        })
    }
}