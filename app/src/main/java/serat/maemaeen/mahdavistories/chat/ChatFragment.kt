package serat.maemaeen.mahdavistories.chat

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import serat.maemaeen.mahdavistories.PlayService
import serat.maemaeen.mahdavistories.R
import serat.maemaeen.mahdavistories.databinding.CustomDialogBinding
import serat.maemaeen.mahdavistories.databinding.FragmentChatBinding
import serat.maemaeen.mahdavistories.databinding.FragmentFeedBinding
import serat.maemaeen.mahdavistories.util.ChatFragmentDialoge
import serat.maemaeen.mahdavistories.util.CusDia


class ChatFragment : Fragment() {
    lateinit var binding: FragmentChatBinding
    lateinit var mediaPlayer: MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
        playChatMusicBack()
        stopBackgroundMusic()
        alert()

    }

    private fun alert() {
        val chatFragmentDialoge: CusDia = ChatFragmentDialoge(
            requireContext(), "سرویس چت",
            "این سرویس فعلا غیر فعال میابشد. انشاءالله در آپدیت های بعدی راه اندازی خواهد شد.",
            "صفحه اصلی",
            "همینجا"
        )
        chatFragmentDialoge.DoIt()
    }

    private fun playChatMusicBack() {
        mediaPlayer = MediaPlayer.create(this.context, R.raw.javadmoghadam)
        mediaPlayer.start()
    }

    private fun setUpViews() {
        binding.mainImg.animate().alpha(0.25f).duration = 4000
    }

    private fun stopBackgroundMusic() {
        activity?.stopService(Intent(this.context, PlayService::class.java))
    }

    override fun onPause() {
        super.onPause()
        mediaPlayer.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }
}