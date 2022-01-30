package islove.app.assets.notification
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface APIService {
    @Headers(
        "Content-Type: application/json",
        "Authorization:key=AAAAkbVKnk0:APA91bHr7gsC6cOYRNobN3oXgDEm8T3WT6PZOFEaLib_JJ6CZuY5ir8idgqMM-40HPLUdeGa8HCMOLj33hEmCqF5h_0PmYjskGHfVICWqOJ0bTXBpSVgsghy8y42DPpPSFV6IbTEjGy7"
    )

    @POST("fcm/send")
    fun sendNotification(@Body body: Sender): Call<MyResponse>
}