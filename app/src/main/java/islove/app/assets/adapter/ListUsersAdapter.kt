package islove.app.assets.adapter
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import islove.app.R
import islove.app.ReceiverUserActivity
import islove.app.assets.classes.User

class ListUsersAdapter(private val list: ArrayList<User>, val c: Context):  RecyclerView.Adapter<ListUsersAdapter.InnerClassDescription>() {
    class InnerClassDescription(view: View) : RecyclerView.ViewHolder(view) {
        val image = view.findViewById<CircleImageView>(R.id.imageListUser)
        val textName = view.findViewById<TextView>(R.id.nameListUser)
        val textStatus = view.findViewById<TextView>(R.id.statusListUser)
        val imageOnline = view.findViewById<ImageView>(R.id.onlineListUser)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerClassDescription {
        return InnerClassDescription(LayoutInflater.from(parent.context).inflate(R.layout.list_users_layout, parent, false))
    }

    override fun onBindViewHolder(holder: InnerClassDescription, position: Int) {
        val user = list[position]
        if(user.image.length > 22) Glide.with(c).load(user.image).into(holder.image)
        holder.textName.text = user.name
        holder.textStatus.text = user.status
        holder.itemView.setOnClickListener {
            val visitIntent = Intent(c, ReceiverUserActivity::class.java); visitIntent.putExtra("visit_user_id", user.id)
            visitIntent.putExtra("visit_name", user.name);visitIntent.putExtra("visit_status", user.status); visitIntent.putExtra("visit_image", user.image)
            c.startActivity(visitIntent)
        }
    }

    override fun getItemCount(): Int = list.size

    @SuppressLint("NotifyDataSetChanged")
    fun addItem(user: User) { list.add(user); notifyDataSetChanged(); }
}
