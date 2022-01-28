package islove.app.assets.fragments
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerOptions
import islove.app.R
class ContactsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.contacts_fragment, container, false)
        val rvContactsRecyclerView = view.findViewById<RecyclerView>(R.id.rvContactsRecyclerView)
        rvContactsRecyclerView.layoutManager = LinearLayoutManager(context)

        return view
    }

    override fun onStart() {
        super.onStart()
        // val options = FirebaseRecyclerOptions.Builder<>()
    }

}