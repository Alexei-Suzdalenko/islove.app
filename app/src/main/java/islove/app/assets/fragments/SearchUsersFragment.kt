package islove.app.assets.fragments
import android.os.Bundle
import android.util.Log
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
import islove.app.assets.api.DeleteUsers
import islove.app.assets.api.SaveNewUserData
import islove.app.assets.classes.App
import islove.app.assets.classes.PageWidth.GetSizesPageItems
import kotlinx.android.synthetic.main.list_users_fragment.*
import kotlinx.android.synthetic.main.list_users_fragment.view.*

class SearchUsersFragment : Fragment() {
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
        App.listAllUsers.clear()

        SaveNewUserData().getListUsers {
            listUsersAdapter.addListUsers(it)
            view.loaderSearchProgressbar.visibility = View.GONE


            // DeleteUsers().startDeleting(App.listAllUsers)
            //  15/04/2022
        }

        return view
    }

}