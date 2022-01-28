package islove.app
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import islove.app.assets.adapter.TabsPagerAdapter
import islove.app.assets.api.CreateNewAccount
import islove.app.assets.api.CreateNewGroup
import islove.app.assets.classes.App
import islove.app.databinding.ActivityMainBinding
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var currentUser: FirebaseUser? = null

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
            R.id.findFrends -> { startActivity(Intent(this, FindFrendsActivity::class.java)); return true; }
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




































