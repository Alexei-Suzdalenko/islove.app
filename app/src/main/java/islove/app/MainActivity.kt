package islove.app
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import islove.app.assets.adapter.TabsPagerAdapter
import islove.app.assets.api.CreateNewAccount
import islove.app.assets.api.CreateNewGroup
import islove.app.assets.classes.Addss
import islove.app.assets.classes.App
import islove.app.assets.notification.*
import islove.app.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding; val miId = FirebaseAuth.getInstance().currentUser!!.uid; var usersBlockedMe = ""
    private var currentUser: FirebaseUser? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // key app : svoboda_key.jks
        // key app folder: C:\_OJO_NEW_ACCOUNT_ANDROID_DEVELOPER\islove.app
        // 13:42 15/04/2022
         App.editor.putString("block", usersBlockedMe).apply()
        FirebaseDatabase.getInstance().reference.child("block/$miId").addValueEventListener(object:
            ValueEventListener {
            override fun onCancelled(error: DatabaseError) {}
            override fun onDataChange(snapshot: DataSnapshot) {
                for( snaps in snapshot.children ){ val valueData = snaps.value; usersBlockedMe += ", $valueData" }
                App.editor.putString("block", usersBlockedMe).apply()
            }
        })


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
        if(App.sharedPreferences.getString("image", "").toString().length > 11) Glide.with(this).load(App.sharedPreferences.getString("image", "").toString()).into(profileImage)
        userName.text = App.sharedPreferences.getString("name" ,"").toString()

        profileImage.setOnClickListener { startActivity(Intent(this, MyProfileActivity::class.java)) }
        userName.setOnClickListener    { startActivity(Intent(this, MyProfileActivity::class.java)) }
        Addss.smartAdd(this)
    }

    override fun onStart() {
        super.onStart()
        currentUser = FirebaseAuth.getInstance().currentUser
        if(currentUser == null) { startActivity(Intent(this, LoginActivity::class.java)); finish()
        } else CreateNewAccount().virifyUserInstance(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean { menuInflater.inflate(R.menu.menu_main, menu); return true }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            // R.id.findFrends -> { startActivity(Intent(this, FindFrendsActivity::class.java)); return true; }
            // R.id.createGroup -> { RequestNewGroup(); return  true; }
            R.id.settingsOption -> { startActivity(Intent(this, MyProfileActivity::class.java)); return true; }
           // R.id.logoutOption -> { FirebaseAuth.getInstance().signOut(); startActivity(Intent(this, LoginActivity::class.java)); finish(); return true; }
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




































