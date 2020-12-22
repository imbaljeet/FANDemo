package com.app.fandemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.facebook.ads.*
import kotlinx.android.synthetic.main.activity_main.*

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {
    private lateinit var interstitialAd: InterstitialAd

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG, "onCreate: called")

        interstitialAd = InterstitialAd(this, "YOUR_PLACEMENT_ID")

        val interstitialAdListener: InterstitialAdListener = object : InterstitialAdListener {
            override fun onInterstitialDisplayed(ad: Ad?) {
                // Interstitial ad displayed callback
                Log.e(TAG, "Interstitial ad displayed.")
            }

            override fun onInterstitialDismissed(ad: Ad?) {
                // Interstitial dismissed callback
                Log.e(TAG, "Interstitial ad dismissed.")
            }

            override fun onError(ad: Ad?, adError: AdError) {
                // Ad error callback
                Log.e(TAG, "Interstitial ad failed to load: " + adError.errorMessage)
            }

            override fun onAdLoaded(ad: Ad?) {
                // Interstitial ad is loaded and ready to be displayed
                Log.d(TAG, "Interstitial ad is loaded and ready to be displayed!")
                // Show the ad
                interstitialAd.show()
            }

            override fun onAdClicked(ad: Ad?) {
                // Ad clicked callback
                Log.d(TAG, "Interstitial ad clicked!")
                Toast.makeText(this@MainActivity, "onAdClicked", Toast.LENGTH_SHORT).show()
            }

            override fun onLoggingImpression(ad: Ad?) {
                // Ad impression logged callback
                Log.d(TAG, "Interstitial ad impression logged!")
            }
        }

        button_init?.setOnClickListener {
            AdSettings.setTestMode(true)
            AdSettings.setTestAdType(AdSettings.TestAdType.IMG_16_9_LINK)
            AudienceNetworkAds.initialize(this)
        }

        button_launch?.setOnClickListener {
            interstitialAd.loadAd(
                    interstitialAd.buildLoadAdConfig()?.withAdListener(interstitialAdListener)
                            ?.build()
            )
        }
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy: called")
        interstitialAd.destroy()
        super.onDestroy()
    }
}