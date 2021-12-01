package com.example.batterystatus

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private var TAG = MainActivity::class.simpleName

    private var BatteryStatus : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        BatteryStatus  = findViewById(R.id.battery_status )
        // create intent filter register receiver
        val intentFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        registerReceiver(batteryStatusReceiver,intentFilter)
    }


    private val batteryStatusReceiver : BroadcastReceiver = object : BroadcastReceiver (){
        override fun onReceive(context: Context?, intent: Intent?) {

            if (intent?.action == "android.intent.action.BATTERY_CHANGED"){
                val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL,-1 )
                Log.d(TAG,"onReceive : battery level $level")
                //set the text view
                BatteryStatus?.post {
                   if (level <= 20) {
                       BatteryStatus?.text = level.toString().plus("%").plus("Low")

                   }else(
                     level.toString().plus("%").plus("Good").also { BatteryStatus?.text = it }

                   )
                }

            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(batteryStatusReceiver)
    }

}