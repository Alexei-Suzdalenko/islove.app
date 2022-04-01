package islove.app
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import islove.app.R
import islove.app.assets.classes.App
import kotlinx.android.synthetic.main.activity_show_images.*
class ShowImages : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_images)
        title = App.otherUserData?.name.toString()
        nameTextView.text = App.otherUserData?.name.toString()

        if(intent.getStringExtra("image").toString() == "back") {
            if (App.otherUserData?.back.toString().length > 11) Glide.with(this).load(App.otherUserData?.back.toString()).into(showImage)
        } else {
            if (App.otherUserData?.image.toString().length > 11) Glide.with(this).load(App.otherUserData?.image.toString()).into(showImage)
        }

    }
}