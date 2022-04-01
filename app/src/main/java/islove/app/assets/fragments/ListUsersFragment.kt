package islove.app.assets.fragments
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import islove.app.R
import islove.app.assets.adapter.ListUsersAdapter
import islove.app.assets.api.SaveNewUserData
import islove.app.assets.classes.App
import islove.app.assets.classes.PageWidth.GetSizesPageItems
import kotlinx.android.synthetic.main.list_users_fragment.*
import kotlinx.android.synthetic.main.list_users_fragment.view.*

class ListUsersFragment : Fragment() {
    lateinit var listUsersAdapter: ListUsersAdapter
    lateinit var rvListUsersRecycler:  RecyclerView
    val miId = FirebaseAuth.getInstance().currentUser?.uid
    val usersBlocked = App.sharedPreferences.getString("block", "").toString()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val items = GetSizesPageItems(requireContext())
        val view = inflater.inflate(R.layout.list_users_fragment, container, false)
        listUsersAdapter =  ListUsersAdapter(ArrayList(), requireContext())
        rvListUsersRecycler = view.findViewById(R.id.rvListUsers)
        rvListUsersRecycler.setHasFixedSize(true)
        rvListUsersRecycler.layoutManager = GridLayoutManager(context, items)
        rvListUsersRecycler.adapter = listUsersAdapter

        view.loaderSearchProgressbar.visibility = View.VISIBLE
        SaveNewUserData().getListUsers {
            if( miId != it.id && ! usersBlocked.contains(it.id, ignoreCase = true) &&  it.name != "null" && it.image != "null") listUsersAdapter.addItem(it)
            view.loaderSearchProgressbar.visibility = View.GONE
        }

        return view
    }

}