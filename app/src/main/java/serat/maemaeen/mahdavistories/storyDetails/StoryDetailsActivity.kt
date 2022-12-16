package serat.maemaeen.mahdavistories.storyDetails

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.*
import android.graphics.drawable.Drawable
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.android.material.slider.Slider
import kotlinx.coroutines.*
import serat.maemaeen.mahdavistories.PlayService
import serat.maemaeen.mahdavistories.R
import serat.maemaeen.mahdavistories.base.BaseActivity
import serat.maemaeen.mahdavistories.databinding.ActivityStoryDetailsBinding
import serat.maemaeen.mahdavistories.main.MainActivity
import serat.maemaeen.mahdavistories.util.GlideLoading
import serat.maemaeen.mahdavistories.util.NetworkChecker
import serat.maemaeen.mahdavistories.util.myWindow
import java.io.File
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL
import java.util.*
import javax.inject.Inject



class StoryDetailsActivity : BaseActivity<ActivityStoryDetailsBinding, StoryDetailsViewModel>() {
    @Inject
    lateinit var factory: StoryDetailsViewModel.Factory
    lateinit var onComplete: BroadcastReceiver
     var player: MediaPlayer? = null
    lateinit var musicPath: String
    var isUserChanging = false
    var playPauseSc = true

    @Inject
    lateinit var glideLoading: GlideLoading
    lateinit var urlMusic: String
    private lateinit var broadcastReceiver: BroadcastReceiver
    var checkNetValue: Boolean = true
    var playPause = true
    var timer: Timer? = null
    var isMute = false
    val SDCardRoot = Environment.getExternalStorageDirectory()
        .toString()
    private var job = Job()
    private var scopeForSaving = CoroutineScope(job + Dispatchers.Main)
    override fun inflateViewBinding(inflater: LayoutInflater): ActivityStoryDetailsBinding =
        ActivityStoryDetailsBinding.inflate(inflater)

    override fun createViewModel(): StoryDetailsViewModel {
        factory.sId = intent.extras!!.getInt("storyId")
        return ViewModelProvider(this, factory).get()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myWindow()
        observeViewModel()
        progressBar()
        broadcastNetChecker()
        broadcastOnDownloadComplete()
        viewModel.onInitialState()
        setupListeners()
//        Handler(Looper.getMainLooper()).postDelayed({ getPath() }, 1000)
        binding.apply {


            btnPlayPause.setOnClickListener {

                if (playPause) {
                    scopeForSaving.launch { playStory() }

                } else {
                    scopeForSaving.launch { pause() }

                }
            }
            btnDownload.setOnClickListener {
                scopeForSaving.launch {
                    clickedDownloadFun()
                }
            }




//            binding.btnGoAfter.setOnClickListener {
//                goAfterMusic()
//            }
//            binding.btnGoBefore.setOnClickListener {
//                goBeforeMusic()
//            }

            playPauseScreen.setOnClickListener {

                playPauseSc = if (playPauseSc) {
                    stopBackgroundMusic()
                    false
                } else {
                    startBackgroundMusic()
                    true
                }

            }
            binding.sliderMain.addOnChangeListener { slider, value, fromUser ->
                binding.txtLeft.text = convertMillisToString(value.toLong())
                isUserChanging = fromUser
                binding.sliderMain.setLabelFormatter { convertMillisToString(value.toLong()) }
            }
            binding.sliderMain.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {

                @SuppressLint("RestrictedApi")
                override fun onStartTrackingTouch(slider: Slider) {
                }

                @SuppressLint("RestrictedApi")
                override fun onStopTrackingTouch(slider: Slider) {
                    player?.seekTo(slider.value.toInt())
                    binding.sliderMain.value = player?.currentPosition?.toFloat()!!
                }

            })
            binding.btnVolumeOnOff.setOnClickListener { configureVolume() }
        }

    }

    suspend fun clickedDownloadFun() {
        saveToStorage(urlMusic)
        changeViewDetails()
    }

    private suspend fun playStory() {
        binding.apply {
            parentRelative.visibility = View.VISIBLE
            try {
                playMusic(urlMusic)
                playPause = false
            } catch (e: IOException) {
                Log.e("AUDIO PLAYBACK", "prepare() failed")
            }


        }

    }

    private fun pause() {
        binding.apply {
            try {
                stopMusic(urlMusic)
                playPause = true
            } catch (e: IOException) {
                Log.e("AUDIO PLAYBACK", "prepare() failed")
            }
        }


    }


    private fun startBackgroundMusic() {
        startService(Intent(this, PlayService::class.java))
        pause()
    }


    private fun broadcastOnDownloadComplete() {
        onComplete = object : BroadcastReceiver() {
            override fun onReceive(ctxt: Context?, intent: Intent?) {
                tasks()
            }
        }
        registerReceiver(
            onComplete,
            IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        )
    }

    private fun setupListeners() = with(binding) {
        favorite.setOnClickListener {
            viewModel.onFavoriteClicked()
        }
    }

    private fun tasks() {
        getPath()
        Handler(Looper.getMainLooper()).postDelayed({
            hideProgressbar()
            changeIconPpToDownloadComplete()
            downLoadCompleteAlert()
            changeIconPpToDownloading()
        }, 1000)

    }

    private fun downLoadCompleteAlert() {
        val sweetAlertDialog = SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
        sweetAlertDialog.contentText = "اتمام دانلود"
        sweetAlertDialog.confirmText = "باشه"
        sweetAlertDialog.show()
    }

    private fun hideProgressbar() {
        binding.apply {
            progressBar.visibility =
                View.GONE
        }
    }

    private fun changeViewDetails() {
        binding.apply {
            if (NetworkChecker(this@StoryDetailsActivity).isInternetConnected){
                progressBar.visibility =
                    View.VISIBLE
                downloading.visibility = View.VISIBLE
                patient()
            }else{
                checkNet()
            }

        }
    }

    private fun patient() {
        val sweetAlertDialog = SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
        sweetAlertDialog.contentText = "شروع دانلود..."
        sweetAlertDialog.confirmText = "باشه"
        sweetAlertDialog.show()
    }

    private fun changeIconPpToPlay() {
        binding.btnPlayPause.setImageResource(R.drawable.ic_pause)
    }

    private fun chagneIconPpToPause() {
        binding.btnPlayPause.setImageResource(R.drawable.ic_play)

    }


    private fun changeIconPpToDownloading() {
        binding.apply {
            downloading.visibility = View.INVISIBLE

        }

    }

    private fun changeIconPpToDownloadComplete() {
        binding.apply {
            downloading.visibility = View.INVISIBLE

        }

    }

    private fun stopBackgroundMusic() {
        stopService(Intent(this, PlayService::class.java))
    }


    private fun getPath() {

        val audioFilePath = "$SDCardRoot/Download/${File(URL(urlMusic).path).name}"
        Log.i("tt", "getPath: $audioFilePath")
        musicPath = audioFilePath
        Log.i("mp", "getPath: $musicPath")
    }

    private fun bePatient() {
        val sweetAlertDialog = SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
        sweetAlertDialog.contentText = "لطفا چند لحظه تا پخش صبوری کنید."
        sweetAlertDialog.confirmText = "باشه"
        sweetAlertDialog.show()
    }

    private suspend fun playMusic(on: String) {
        binding.apply {


            if (NetworkChecker(this@StoryDetailsActivity).isInternetConnected){
                bePatient()
                linearLayout2.visibility = View.VISIBLE
                btnVolumeOnOff.visibility = View.VISIBLE
                btnGoBefore.visibility = View.VISIBLE
                btnGoAfter.visibility = View.VISIBLE
            }else{
                checkNet()
            }

            withContext(Dispatchers.IO) {
                player = MediaPlayer.create(
                    this@StoryDetailsActivity,
                    Uri.parse(on)
                )
                player?.start()
                timer = Timer()
                timer?.schedule(object : TimerTask() {
                    override fun run() {
                        if (!isUserChanging) {
                            scopeForSaving.launch(Dispatchers.Main) {
                                currentPositionSlider()
                            }
                        }
                    }

                }, 1000, 1000)
            }
            binding.sliderMain.valueTo = player?.duration?.toFloat()!!
            binding.txtRight.text = convertMillisToString(player?.duration!!.toLong())
            changeIconPpToPlay()
            stopBackgroundMusic()
            nowPlay.visibility = View.VISIBLE
            txtTop.text = "در حال پخش"
        }
    }

    private fun checkNet() {
        val sweetAlertDialog = SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
        sweetAlertDialog.contentText = "اتصال اینترنت رو مورد بررسی قرار ده."
        sweetAlertDialog.confirmText = "باشه"
        sweetAlertDialog.show()
    }

    private fun currentPositionSlider() {
        binding.sliderMain.value = player?.currentPosition?.toFloat()!!
    }

    private fun stopMusic(off: String) {
        binding.apply {
//            player.reset()
//            player.setDataSource(applicationContext, Uri.parse(off))
//            player.setAudioStreamType(AudioManager.STREAM_MUSIC)
//            player.prepareAsync()
//      player.setDataSource(off)
//      player.prepare()
            player?.pause()
            chagneIconPpToPause()
            txtTop.text = " "
//            startBackgroundMusic()
            nowPlay.visibility = View.INVISIBLE
        }

    }

    private fun broadcastNetChecker() {
        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (NetworkChecker(this@StoryDetailsActivity).isInternetConnected) {

                    checkNetValue = true
                    binding.apply {
                        connectionState.visibility = View.GONE
                    }
                } else {
                    checkNetValue = false
                    binding.apply {
                        connectionState.visibility = View.VISIBLE
                        btnPlayPause.visibility = View.GONE
                    }

                }
            }
        }
        val intentFilter = IntentFilter("android.net.conn.CONNECTIVITY_CHANGE")
        registerReceiver(broadcastReceiver, intentFilter)
    }

    private fun progressBar() {
        binding.mainImg.animate().alpha(0.25f).duration = 4000
        if (checkNetValue) {
            binding.progressBar.visibility =
                View.GONE
        }
    }


    private fun observeViewModel() = with(viewModel) {

        getStoryDetailsUiStateLiveData().observer { upDateStoryDetails(it) }
        getFavoriteStateLiveData().observer { updateFavoriteDrawable(it.drawable, it.int) }

    }

    private fun upDateStoryDetails(storyDetails: StoryDetailsViewModel.StoryDetailsUiState) =
        with(binding) {
            textView2.text = storyDetails.name
            glideLoading.load(this.imageCover, imageCover, storyDetails.imageUrl)

            urlMusic = storyDetails.musicLink


        }

    private fun updateFavoriteDrawable(drawable: Drawable?, int: Int) = with(binding) {
        favorite.setImageDrawable(drawable)
        if (int == 1) {
            imgDetailFav.visibility = View.VISIBLE
            imgDetailFavBorder.visibility = View.INVISIBLE

        } else {
            imgDetailFav.visibility = View.INVISIBLE
            imgDetailFavBorder.visibility = View.VISIBLE

        }
    }

    private suspend fun saveToStorage(url: String) {
        withContext(Dispatchers.IO) {
            StartDownload(url)
        }
    }

    private fun StartDownload(url: String) {
        val uri = Uri.parse(url)
        val request = DownloadManager.Request(uri)
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE)
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI)
        request.setAllowedOverRoaming(false)
        request.setTitle("Test mp3")
        request.setDescription("داستان مهدوی")
        request.setVisibleInDownloadsUi(true)
        request.setTitle(getFileName(url))
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
        request.setDestinationInExternalPublicDir(
            Environment.DIRECTORY_DOWNLOADS,
            "/" + getFileName(url)
        )
        val manager =
            baseContext?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        manager.enqueue(request)


    }

    private fun getFileName(url: String): String {

        return try {
            File(URL(url).path).name

        } catch (e: MalformedURLException) {
            System.currentTimeMillis().toString()
        }

    }

    private fun configureVolume() {

        val audioManager = getSystemService(AUDIO_SERVICE) as AudioManager
        isMute = if (isMute) {
            audioManager.adjustVolume(AudioManager.ADJUST_UNMUTE, AudioManager.FLAG_SHOW_UI)
            binding.btnVolumeOnOff.setImageResource(R.drawable.ic_volume_on)
            false

        } else {
            audioManager.adjustVolume(AudioManager.ADJUST_MUTE, AudioManager.FLAG_SHOW_UI)
            binding.btnVolumeOnOff.setImageResource(R.drawable.ic_volume_off)
            true
        }

    }


    private fun goAfterMusic() {

        val now = player?.currentPosition
        val newValue = now?.plus(15000)
        if (newValue != null) {
            player?.seekTo(newValue)
        }

    }

    private fun goBeforeMusic() {
        val now = player?.currentPosition
        val newValue = now?.minus(15000)
        if (newValue != null) {
            player?.seekTo(newValue)
        }

    }

    private fun convertMillisToString(duration: Long): String {
        val second = duration / 1000 % 60
        val minute = duration / (1000 * 60) % 60

        return java.lang.String.format(Locale.US, "%02d:%02d", minute, second)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        scopeForSaving.cancel()
        player?.release()
        timer?.purge()
        timer?.cancel()
    }

    override fun onDestroy() {
        super.onDestroy()
        scopeForSaving.cancel()
        player?.release()
        timer?.purge()
        timer?.cancel()
        unregisterReceiver(broadcastReceiver)
        unregisterReceiver(onComplete)
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onStop() {
        super.onStop()
        scopeForSaving.cancel()
        player?.release()
        timer?.purge()
        timer?.cancel()
    }

    override fun onPause() {
        super.onPause()
        scopeForSaving.cancel()
        player?.release()
        timer?.purge()
        timer?.cancel()
    }

}
