package islove.app.assets.adapter
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import islove.app.R
import islove.app.assets.classes.User
/*

dont user there !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

 */
class ContactListaAdapter(private val list: ArrayList<User>, private val c: Context): RecyclerView.Adapter<ContactListaAdapter.InnerClass>()  {
    class InnerClass(view: View): RecyclerView.ViewHolder(view){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerClass {
        return InnerClass(LayoutInflater.from(c).inflate(R.layout.list_users_layout, parent, false))
    }

    override fun onBindViewHolder(holder: InnerClass, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int = list.size
}