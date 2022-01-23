package islove.app
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ProfileVisitActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_visit)

        val visitUserId = intent.getStringExtra("visit_user_id").toString()
        val visitName = intent.getStringExtra("visit_name").toString()
        val visitStatus = intent.getStringExtra("visit_status").toString()
        val visitImage = intent.getStringExtra("visit_image").toString()
    }
}