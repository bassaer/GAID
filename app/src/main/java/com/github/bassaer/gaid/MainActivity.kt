package com.github.bassaer.gaid

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.TextView
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import kotlinx.coroutines.experimental.launch
import java.io.IOException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val textView = findViewById<TextView>(R.id.main_text)
        launch {
            try {
                val adInfo = AdvertisingIdClient.getAdvertisingIdInfo(applicationContext)
                textView.text = adInfo.id
                Log.d(javaClass.name, "GAID -> " + adInfo.id)
                Log.d(javaClass.name, "TrackingEnabled -> " + adInfo.isLimitAdTrackingEnabled)
            } catch (e: IOException) {
                Log.d(javaClass.name, "Error!!!")
                textView.text = "Error"
            }
        }
    }
}
