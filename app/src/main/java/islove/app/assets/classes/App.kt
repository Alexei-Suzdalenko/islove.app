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

https://youtu.be/uHjR-nJmgAU?list=PLxefhmF0pcPmtdoud8f64EpgapkclCllj&t=491


*/

/*
scrollView auto scroll to botom !!!
scrollView
scrollView.fullScroll ( ScrollView.FOCUS_DOWN )
 */