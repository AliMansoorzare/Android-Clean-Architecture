package serat.maemaeen.mahdavistories

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import com.preference.provider.PreferenceProvider.context

class PlayService : Service() {
    var mediaPlayer: MediaPlayer? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        play()
        return START_STICKY
    }

    private fun play() {
        mediaPlayer = MediaPlayer.create(context, R.raw.aghabiaa_file)
        mediaPlayer?.start()

    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }


}