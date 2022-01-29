package islove.app.assets.fragments
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerOptions
import islove.app.R
import islove.app.assets.adapter.ListUsersAdapter
import islove.app.assets.api.SaveNewUserData

class ContactsFragment : Fragment() {
    lateinit var adapter: ListUsersAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.contacts_fragment, container, false)
        val rvContactsRecyclerView = view.findViewById<RecyclerView>(R.id.rvContactsRecyclerView)
        rvContactsRecyclerView.layoutManager = LinearLayoutManager(context)
        rvContactsRecyclerView.setHasFixedSize(true)
        adapter = ListUsersAdapter(ArrayList(), requireContext())
        rvContactsRecyclerView.adapter = adapter

        /*  get list chats ids
        *   get custom user from custom id
        */
        SaveNewUserData().getListIdsContacts{ adapter.addItem(it) }


        return view
    }


}