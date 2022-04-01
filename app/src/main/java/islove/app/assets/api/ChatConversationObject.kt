package islove.app.assets.api

import android.content.Context
import android.content.Intent
import android.widget.TextView
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import islove.app.ChatConversartionActivity
import islove.app.R
import islove.app.VisitReceiverActivity
import islove.app.assets.classes.App.Companion.otherUserData
import java.lang.Exception

class ChatConversationObject(val context: Context, val activity: ChatConversartionActivity) {
    val nameChatUser = activity.findViewById<TextView>(R.id.textViewChat)
    // val ageChatUser = activity.findViewById<TextView>(R.id.ageChatUser)
    val userImage = activity.findViewById<CircleImageView>(R.id.customProfileImage)

    fun initChatConversationFunction(){

        try {
            nameChatUser.text = otherUserData?.name
            // ageChatUser.text = userConversation?.age
            if(otherUserData?.image.toString().length < 22) otherUserData!!.image = "https://alexei-suzdalenko.github.io/r-radio/user.png"
            Glide.with( context ).load( otherUserData?.image ).into( userImage )
        } catch (e: Exception){}

        userImage.setOnClickListener {
            context.startActivity(Intent(context, VisitReceiverActivity::class.java))
            activity.finish()
        }

    }
}