package islove.app.assets.api
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import islove.app.MainActivity
import islove.app.assets.classes.App
import java.net.URL

object ShowAlertForGoogle {
    fun showAlert(activity: MainActivity){
        val thread = Thread{
            try{
                val googlePersonViewAlert = App.sharedPreferences.getBoolean("view", false)
                val resStr = URL("https://alexei-suzdalenko.github.io/r-radio/islove-google.txt").readText()
                activity.runOnUiThread{
                    if(!googlePersonViewAlert && resStr.contains("ok")) showAlertDialogReal(activity)
                }
            } catch (e: Exception){}
        }
        thread.start()
    }

    fun showAlertDialogReal(activity: MainActivity){
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("hello tester")
        builder.setMessage("In the database of this application I have less than 5 users, if the screen shows more users it is because it is the cache of your phone. \n\n Thank you for understanding")

        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
           App.editor.putBoolean("view", true).apply()
            dialog.dismiss()
        }

        builder.show()
    }
}