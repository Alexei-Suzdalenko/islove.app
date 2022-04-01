package islove.app.assets.classes
import android.util.Log
import android.widget.RelativeLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import islove.app.ChatConversartionActivity
import islove.app.MainActivity
import islove.app.R
import islove.app.VisitReceiverActivity
import kotlinx.android.synthetic.main.activity_main.*

object Addss {

    fun start(context: VisitReceiverActivity) {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(context, "ca-app-pub-7286158310312043/5921970972", adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    App.mInterstitialAd = interstitialAd
                    App.mInterstitialAd?.show(context)
                }
                override fun onAdFailedToLoad(loadAdError: LoadAdError) {}
            })
    }


    fun startM(context: ChatConversartionActivity) {
        var countMessage = App.sharedPreferences.getInt("count", 0).toInt(); countMessage++
        App.editor.putInt("count", countMessage).apply()

        if (countMessage > 10) {
            val adRequest = AdRequest.Builder().build()
            InterstitialAd.load(context, "ca-app-pub-7286158310312043/5921970972", adRequest, object : InterstitialAdLoadCallback() {
                    override fun onAdLoaded(interstitialAd: InterstitialAd) {
                        App.mInterstitialAd = interstitialAd
                        App.mInterstitialAd?.show(context)
                    }
                    override fun onAdFailedToLoad(loadAdError: LoadAdError) {}
                })
            App.editor.putInt("count", 0).apply()
        }
    }


    fun smartAdd(activity: MainActivity) {
        MobileAds.initialize(activity) {}
        val params = RelativeLayout.LayoutParams(CoordinatorLayout.LayoutParams.MATCH_PARENT, CoordinatorLayout.LayoutParams.MATCH_PARENT)
        val mAdView: AdView = activity.findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.adListener = object : AdListener() {
            override fun onAdLoaded() {
                var size = 211
                if (mAdView.height > 15) size = mAdView.height
                params.setMargins(0, (size * 1.5).toInt(), 0, (size/1.2).toInt())
                activity.viewPager.layoutParams = params

            }
            override fun onAdFailedToLoad(p0: LoadAdError) {}
        }
        mAdView.loadAd(adRequest)
    }
}


