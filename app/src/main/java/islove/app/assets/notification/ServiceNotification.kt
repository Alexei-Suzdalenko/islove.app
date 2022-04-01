package islove.app.assets.notification
import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import islove.app.R
import islove.app.assets.classes.App
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ServiceNotification {

    @SuppressLint("RestrictedApi")
    fun sentNotification( chatId: String, sender: String,  receiver: String, userName: String,  message: String, image: String, token: String, age: String, miToken: String){
        val apiService = Client.getClient("https://fcm.googleapis.com/")!!.create(APIService::class.java)
        val data = Data(chatId, sender,  receiver, userName,  message , image, age, miToken)

        val senderA = Sender(data, token)

        apiService
            .sendNotification(senderA)
            .enqueue(object: Callback<MyResponse> {
                override fun onFailure(call: Call<MyResponse>, t: Throwable) {;}
                override fun onResponse(call: Call<MyResponse>, response: Response<MyResponse>) {
                    if( response.code() == 200 ) {
                        if ( response.body()?.success != 1 ) {

                        }
                    }
                }
            })
    }
}