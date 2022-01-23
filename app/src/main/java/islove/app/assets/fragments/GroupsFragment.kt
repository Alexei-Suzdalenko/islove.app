package islove.app.assets.fragments
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import islove.app.GroupChatActivity
import islove.app.R
import islove.app.assets.api.CreateNewGroup

class GroupsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.groups_fragment, container, false)
        val listViewGroup = view.findViewById<ListView>(R.id.listViewGroup)
             CreateNewGroup().getListGroups { listGroups ->
                     listViewGroup.adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, listGroups)
                     listViewGroup.setOnItemClickListener { adapterView, view, i, l ->
                         val currentGroupName = adapterView.getItemAtPosition(i).toString()
                         val groupIntent = Intent(requireContext(), GroupChatActivity::class.java)
                         groupIntent.putExtra("group", currentGroupName)
                         startActivity(groupIntent)
                     }
             }
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) {}

    }
}