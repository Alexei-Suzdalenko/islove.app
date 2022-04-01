package islove.app.assets.adapter
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import islove.app.R
import islove.app.VisitReceiverActivity
import islove.app.assets.api.CreateChatChannelFirebase
import islove.app.assets.classes.App
import islove.app.assets.classes.User
class ContactUsersAdapter(private val list: ArrayList<User>, val c: Context):  RecyclerView.Adapter<ContactUsersAdapter.InnerClassDescription>() {
    class InnerClassDescription(view: View) : RecyclerView.ViewHolder(view) {
        val image = view.findViewById<CircleImageView>(R.id.imageContact)
       val textName = view.findViewById<TextView>(R.id.nameCont)
       val textStatus = view.findViewById<TextView>(R.id.statusCont)
       val delete = view.findViewById<ImageView>(R.id.deleteConv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerClassDescription {
        return InnerClassDescription(LayoutInflater.from(parent.context).inflate(R.layout.list_contacts_users, parent, false))
    }

    override fun onBindViewHolder(holder: InnerClassDescription, position: Int) {
        val user = list[position]
        if(user.image.length > 22) Glide.with(c).load(user.image).into(holder.image)
         holder.textName.text = user.name
         holder.textStatus.text = user.status
        holder.image.setOnClickListener { App.otherUserData = user; c.startActivity(Intent(c, VisitReceiverActivity::class.java)) }
        holder.delete.setOnClickListener { CreateChatChannelFirebase().deleteUserConversation(user.id){ list.removeAt(position); notifyThis() }}
    }

    override fun getItemCount(): Int = list.size

    @SuppressLint("NotifyDataSetChanged")
    fun addItem(user: User) { list.add(user); notifyDataSetChanged(); }
    @SuppressLint("NotifyDataSetChanged")
    fun notifyThis(){ notifyDataSetChanged() }
}
