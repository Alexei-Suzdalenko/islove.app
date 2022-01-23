package islove.app.assets.classes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import islove.app.R

class GroupsAdapter(val list: ArrayList<String>):  RecyclerView.Adapter<GroupsAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var textView: TextView = view.findViewById(R.id.teztViewGroupChat)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_with_text,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val message=list[position]
        holder.textView.text = message
    }

    override fun getItemCount(): Int { return list.size; }
    fun addItem(string: String) { list.add(string); }
}