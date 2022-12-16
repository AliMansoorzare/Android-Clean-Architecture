package serat.maemaeen.mahdavistories.util

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import serat.maemaeen.mahdavistories.feed.FeedFragment
import serat.maemaeen.mahdavistories.R

class ChatFragmentDialoge(
    context: Context,
    title: String,
    message: String,
    yes: String,
    no: String
) : CusDia,AppCompatActivity() {

    val customDialog = CustomDialog(context, title, message, yes, no)

    override fun DoIt() {
        val view: View = customDialog.showDialog()
        val cdialog = customDialog.mDialog

        val btnyes = view.findViewById<Button>(R.id.yes)
        val btnno = view.findViewById<Button>(R.id.no)
        btnyes?.setOnClickListener {
            btnyes.animate().rotationX(70f).duration = 1000
            Handler(Looper.getMainLooper()).postDelayed({
                view.animate().alpha(0f).duration = 1000
                cdialog?.dismiss()

                val transaction = (view.context as FragmentActivity).supportFragmentManager.beginTransaction()
                transaction.replace(R.id.fragmentContainerView, FeedFragment())
                transaction.addToBackStack(null)
                transaction.commit()

            }, 1500)

        }
        btnno?.setOnClickListener {
            btnno.animate().alpha(0f).duration = 1000
            Handler(Looper.getMainLooper()).postDelayed({
                view.animate().alpha(0f).duration = 1000

                cdialog?.dismiss()
            }, 1500)
        }
    }


}