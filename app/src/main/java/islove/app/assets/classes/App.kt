package islove.app.assets.classes
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import com.google.android.gms.ads.interstitial.InterstitialAd
import java.util.*
import kotlin.system.exitProcess

class App: Application() {
    companion object{
        var listAllUsers: MutableList<User> = mutableListOf()
        var realChannelId: String? = null; var listenerDatabaseChagesActivated = "no"
        lateinit var sharedPreferences: SharedPreferences
        lateinit var editor: SharedPreferences.Editor
        fun showToast(c: Context, message: Int){
            Toast.makeText(c, c.resources.getString(message), Toast.LENGTH_LONG) . show();
        }
        var otherUserData: User? = null
        val textLetters = arrayOf("q","w","e","r","t","y","u","i","o","p","l","k","j","h","g","f","d","s","a","z","x","c","v","b","n","m")
        val numLetters = arrayOf(1,2,3,4,5,9,10,11,12,13, 14,15, 16, 17, 18,19,20,21,22,23,24,25,26,27,28,30,31,32,33,19,22,27,29,37,47,1,2,34,5,6,7,8,8,8)

        fun getIdNoification(userKey: String): Int{
            var result = 0
            var letter = ""
            for(i in userKey.indices){
                letter = userKey[i].toString().lowercase(Locale.getDefault())
                for(chatL in textLetters.indices){
                    if(textLetters[chatL].toString() == letter){
                        result += numLetters[chatL]
                    }
                }
            }
            return result
        }
        var mInterstitialAd: InterstitialAd? = null
        var imageType = ""
    }
    override fun onCreate() {
        super.onCreate()
        sharedPreferences = getSharedPreferences("love app", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
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