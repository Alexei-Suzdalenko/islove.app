package islove.app
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_location_permision.*

class LocationPermisionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_permision)
        supportActionBar?.hide()

        titleApp.setOnClickListener { startActivity(Intent(this, LocationPermisionActivity::class.java)); finish() }
        termAndCond.setOnClickListener { startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://santa-maria-de-cayon.github.io/contact.messager/terms-and-conditions.html"))) }
        startAppChat.setOnClickListener { checkLocationPermission() }
        authorApp.setOnClickListener { startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q=alexei+suzdalenko+developer"))) }
        checkLocationPermission()
    }

    fun goToLogin(){
        // startActivity(Intent(this, RegisterActivity::class.java)); finish()
    }

    override fun onResume() {
        super.onResume()
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            startActivity(Intent(this, RegisterActivity::class.java)); finish()
        }
    }


    private fun checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                AlertDialog.Builder(this)
                    .setTitle("Location Permission Needed")
                    .setMessage("This app needs the Location permission, please accept to use location functionality")
                    .setPositiveButton("OK") { _, _ -> requestLocationPermission() }
                    .create()
                    .show()
            } else { requestLocationPermission() }
        }
    }


    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), MY_PERMISSIONS_REQUEST_LOCATION)
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_LOCATION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        // goToLogin()
                        startActivity(Intent(this, RegisterActivity::class.java)); finish()
                    }
                } else {
                    Toast.makeText(this, "Enable location !", Toast.LENGTH_LONG).show()
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                        startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", this.packageName, null)))
                    }
                }
                return
            }
        }
    }

    companion object {
        private const val MY_PERMISSIONS_REQUEST_LOCATION = 99
    }
}