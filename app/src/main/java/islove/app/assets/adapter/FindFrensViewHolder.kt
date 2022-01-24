package islove.app.assets.adapter
import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import islove.app.assets.classes.User
import kotlinx.android.synthetic.main.users_display_layout.view.*

class FindFrensViewHolder(val itemViewA: View) : RecyclerView.ViewHolder(itemViewA) {

    fun bind(user: User, c: Context) {
        itemViewA.userProfileName?.text = user.name
        itemViewA.statusProfileName?.text = user.status
        if(user.image.length > 22) Glide.with(c).load(user.image).into(itemViewA.userProfileImage)

    }


}
