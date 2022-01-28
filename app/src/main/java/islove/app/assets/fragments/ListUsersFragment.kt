package islove.app.assets.fragments
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import islove.app.R
import islove.app.assets.adapter.ListUsersAdapter
import islove.app.assets.api.SaveNewUserData
import islove.app.assets.classes.User
class ListUsersFragment : Fragment() {
    lateinit var listUsersAdapter: ListUsersAdapter
    lateinit var rvListUsersRecycler:  RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.list_users_fragment, container, false)
        listUsersAdapter =  ListUsersAdapter(ArrayList(), requireContext())
        rvListUsersRecycler = view.findViewById(R.id.rvListUsers)
        rvListUsersRecycler.layoutManager = LinearLayoutManager(context)
        rvListUsersRecycler.adapter = listUsersAdapter

        SaveNewUserData().getListUsers {
            listUsersAdapter.addItem(User(it.id, it.name, "", "", it.status, it.image))
        }

        rvListUsersRecycler

        return view
    }


}