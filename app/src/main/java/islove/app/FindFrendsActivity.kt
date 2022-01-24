package islove.app
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import islove.app.assets.adapter.FindFrensViewHolder
import islove.app.assets.classes.User
import kotlinx.android.synthetic.main.activity_find_frens.*

class FindFrendsActivity : AppCompatActivity() {
    lateinit var adapter: FirebaseRecyclerAdapter<User, FindFrensViewHolder>
    var miId = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_frens)

        miId = FirebaseAuth.getInstance().currentUser!!.uid
        setSupportActionBar(findFrenToolbar as Toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = resources.getString(R.string.findFrends)
        rvFindFrens.layoutManager = LinearLayoutManager(this)

    }

    override fun onStart() {
        super.onStart()
        val usersQuery = FirebaseDatabase.getInstance().reference.child("users")
        val options = FirebaseRecyclerOptions.Builder<User>().setQuery(usersQuery, User::class.java).setLifecycleOwner(this).build()

        adapter = object : FirebaseRecyclerAdapter<User, FindFrensViewHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FindFrensViewHolder {
                return FindFrensViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.users_display_layout, parent, false)); }
            protected override fun onBindViewHolder(holder: FindFrensViewHolder, position: Int, model: User) {
                if(miId != model.id){
                    holder.bind(model, this@FindFrendsActivity)
                    holder.itemViewA.setOnClickListener {
                        val visitIntent = Intent(applicationContext, ReceiverUserActivity::class.java); visitIntent.putExtra("visit_user_id",  getRef(position).key)
                        visitIntent.putExtra("visit_name", model.name);visitIntent.putExtra("visit_status", model.status);visitIntent.putExtra("visit_image", model.image);
                        startActivity(visitIntent)
                    }
                }
            }
            override fun onDataChanged() { // mEmptyListMessage.setVisibility(if (itemCount == 0) View.VISIBLE else View.GONE)
            }
        }
        rvFindFrens.adapter = adapter
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening();
    }
}