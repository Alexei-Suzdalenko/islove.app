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
import com.google.firebase.auth.FirebaseAuth
import islove.app.R
import islove.app.assets.adapter.ListUsersAdapter
import islove.app.assets.api.SaveNewUserData
import islove.app.assets.classes.User
class ListUsersFragment : Fragment() {
    lateinit var listUsersAdapter: ListUsersAdapter
    lateinit var rvListUsersRecycler:  RecyclerView
    val miId = FirebaseAuth.getInstance().currentUser?.uid

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.list_users_fragment, container, false)
        listUsersAdapter =  ListUsersAdapter(ArrayList(), requireContext())
        rvListUsersRecycler = view.findViewById(R.id.rvListUsers)
        rvListUsersRecycler.setHasFixedSize(true)
        rvListUsersRecycler.layoutManager = LinearLayoutManager(context)
        rvListUsersRecycler.adapter = listUsersAdapter

        SaveNewUserData().getListUsers {
            if( miId != it.id) listUsersAdapter.addItem(it)
        }

        rvListUsersRecycler

        return view
    }


}