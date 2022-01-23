package islove.app.assets.classes
import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import islove.app.R
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.users_display_layout.view.*

class FindFrensViewHolder(val itemViewA: View) : RecyclerView.ViewHolder(itemViewA) {

    fun bind(user: User, c: Context) {
        itemViewA.userProfileName?.text = user.name
        itemViewA.statusProfileName?.text = user.status
        if(user.image.length > 22) Glide.with(c).load(user.image).into(itemViewA.userProfileImage)

    }


}
