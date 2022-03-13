package islove.app
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import islove.app.assets.api.BlockUserFire
import islove.app.assets.classes.App
import kotlinx.android.synthetic.main.activity_block_this_user.*

class BlockThisUserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_block_this_user)
        val blockThisId = intent.getStringExtra("userId").toString()

        reportAbuse.setOnClickListener {
                // aqui guardo el id para que a este usuario no le vea yo
                var usersBlocked = App.sharedPreferences.getString("block", "").toString()
                      usersBlocked += " , $blockThisId "
                App.editor.putString("block", usersBlocked).apply()

                BlockUserFire().block(blockThisId)
                showMessageBlockUser()

            Toast.makeText(this, resources.getString(R.string.infoSented), Toast.LENGTH_LONG).show()
        }
    }

    private fun showMessageBlockUser() {
        Thread{
            Thread.sleep(1500)
            runOnUiThread { startActivity(Intent(applicationContext, MainActivity::class.java)); finish(); }
        }.start()
    }
}