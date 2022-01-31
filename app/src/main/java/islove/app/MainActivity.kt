package islove.app
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessaging
import islove.app.assets.adapter.TabsPagerAdapter
import islove.app.assets.api.CreateNewAccount
import islove.app.assets.api.CreateNewGroup
import islove.app.assets.classes.App
import islove.app.assets.notification.*
import islove.app.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var currentUser: FirebaseUser? = null
    var apiService: APIService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarMain)

        val toolbar: Toolbar = findViewById(R.id.toolbarMain)
        setSupportActionBar(toolbar)
        supportActionBar?.title = ""

        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        val viewPager = findViewById<ViewPager>(R.id.viewPager)
        viewPager.adapter = TabsPagerAdapter(this, supportFragmentManager)
        tabLayout.setupWithViewPager(viewPager)

   //     apiService = Client.Client.getClient("https://fcm.googleapis.com/")!!.create(APIService::class.java)
   //     val ref = FirebaseDatabase.getInstance().reference.child("tokens")
   //     val query = ref.orderByKey().equalTo("receiverId")                                                    // instert receiver id
//
   //     // post request to https://fcm.googleapis.com/fcm/send
   //     val miIdUser = FirebaseAuth.getInstance().currentUser!!.uid
   //  //   val token: Token? = child.getValue(Token::class.java)
   //     val data = Data( miIdUser,  "hello this is new message in telephone", "jqFlVKv8oTegIuLH47ffsbLMyii1" )
   //    // val sender = Sender( data, token!!.token )
   //     val sender = Sender( data, "cXZ6Vic2QPqptTH2qN71Q0:APA91bEP-GeIvUIsEGhQQZPDG3wLcKdDwGLuICpXXf57VCkF1JSSNosCUeHUp6qNUa0BtX4X1E21CwHFmDzwhNAZ0of_y1HxqESBIW1k4Me64nfCFbvG7Dux4DbOnJiHmWh1s4g2rMxA" )
   //   apiService?.sendNotification( sender )?.enqueue(object:
   //      Callback<MyResponse> { override fun onFailure(call: Call<MyResponse>, t: Throwable) {}
   //      override fun onResponse(call: Call<MyResponse>, response: Response<MyResponse>) {
   //          if( response.code() == 200 ){
   //              if( response.body()?.success != 1 ){
   //                  Toast.makeText(this@MainActivity, "Failed sendNotification function", Toast.LENGTH_LONG).show()
   //              }
   //              Toast.makeText(this@MainActivity, response.body()?.success.toString() +"request in", Toast.LENGTH_LONG).show()
   //          }
   //      }
   //  })

        NotificationWork().saveUserToken()

    }

    override fun onStart() {
        super.onStart()
        currentUser = FirebaseAuth.getInstance().currentUser
        if(currentUser == null){
            startActivity(Intent(this, LoginActivity::class.java)); finish()
        } else {
            CreateNewAccount().virifyUserInstance(this)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu); return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            // R.id.findFrends -> { startActivity(Intent(this, FindFrendsActivity::class.java)); return true; }
            R.id.createGroup -> { RequestNewGroup(); return  true; }
            R.id.settingsOption -> { startActivity(Intent(this, SettingsActivity::class.java)); return true; }
            R.id.logoutOption -> { FirebaseAuth.getInstance().signOut(); startActivity(Intent(this, LoginActivity::class.java)); finish(); return true; }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun RequestNewGroup(){
        val alert = AlertDialog.Builder(this)
              alert.setTitle(resources.getString(R.string.enterGroputName))
        val editText = EditText(this)
              editText.setHint(resources.getString(R.string.appNameGroup))
              alert.setView(editText)
              alert.setPositiveButton(resources.getString(R.string.save)) { dialog, _ ->
                  val groupName = editText.text.toString().trim()
                  if( groupName.isEmpty() ) { App.showToast(this, R.string.pleaseWriteNameGroup)
                  } else {
                      CreateNewGroup().create(groupName){ result ->
                            App.showToast(this, R.string.groupCreated)
                          startActivity(Intent(this, MainActivity::class.java)); finish()
                      }
                  }
              }
              alert.setNegativeButton(resources.getString(R.string.cancel)) { dialog, _ -> dialog.dismiss(); }
              alert.show()
    }



}




































