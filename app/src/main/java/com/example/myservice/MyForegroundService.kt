package com.example.myservice

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MyForegroundService : Service() {

    companion object {
        internal val TAG = MyForegroundService::class.java.simpleName
    }

    private val serviceJob = Job()
    private val serviceScope = CoroutineScope(Dispatchers.Main + serviceJob)

    override fun onBind(intent: Intent): IBinder? {
        throw UnsupportedOperationException(getString(R.string.info_text))
    }

    @SuppressLint("StringFormatMatches")
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.d(TAG, getString(R.string.log_text))
        serviceScope.launch {
            for (i in 1..50) {
                delay(1000)
                Log.d(TAG, getString(R.string.do_something, i))
            }
            stopSelf()
            Log.d(TAG, getString(R.string.service_dihentikan))
        }
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceJob.cancel()
        Log.d(TAG, getString(R.string.ondestroy_service_dihentikan))
    }
}
}