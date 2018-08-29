package com.github.bassaer.gaid

import android.content.ClipData
import android.content.ClipDescription
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import kotlinx.coroutines.experimental.launch
import java.io.IOException


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var adInfo: AdvertisingIdClient.Info? = null
        val textView = findViewById<TextView>(R.id.main_text)
        launch {
            try {
                adInfo = AdvertisingIdClient.getAdvertisingIdInfo(applicationContext)
                textView.text = adInfo?.id ?: getString(R.string.error)
            } catch (e: IOException) {
                textView.text = getString(R.string.error)
            }
        }
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener { view ->
            if (adInfo == null) {
                return@setOnClickListener
            }
            val item = ClipData.Item(adInfo?.id)
            val mimeType = arrayOfNulls<String>(1)
            mimeType[0] = ClipDescription.MIMETYPE_TEXT_URILIST
            val cd = ClipData(ClipDescription("text_data", mimeType), item)
            val cm = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            cm.primaryClip = cd

            Snackbar.make(view, getString(R.string.copied), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

}
