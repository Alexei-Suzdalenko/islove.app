package islove.app.assets.classes
import android.app.Application
import android.content.Context
import android.widget.Toast

class App: Application() {
    companion object{
        fun showToast(c: Context, message: Int){
            Toast.makeText(c, c.resources.getString(message), Toast.LENGTH_LONG) . show();
        }
    }
}

/*
  google play                    => saron.alexei@gmail.com
  firebase this is love app => desarrollo.web.cantabria.penagos@gmail.com
  svoboda2...

  C:\_OJO_NEW_ACCOUNT_ANDROID_DEVELOPER\svoboda_key.jks

*/

/*
scrollView auto scroll to botom !!!
scrollView
scrollView.fullScroll ( ScrollView.FOCUS_DOWN )
 */