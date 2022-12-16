package serat.maemaeen.mahdavistories.util

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

import serat.maemaeen.mahdavistories.R

class CustomDialog(
    val context: Context,
    val title:String, val message:String,
    val yes:String,
    val no:String
): IcustomDialog {

    override var mDialog:AlertDialog? = null
    override fun showDialog():View {

            val layoutInflater = LayoutInflater.from(context)
            val view: View = layoutInflater.inflate(R.layout.custom_dialog, null, false)

            val txtmes = view.findViewById<TextView>(R.id.txt_message)
            val txttit = view.findViewById<TextView>(R.id.txt_title)
            val btnyes = view.findViewById<Button>(R.id.yes)
            val btnno = view.findViewById<Button>(R.id.no)
        val mainImg = view.findViewById<ImageView>(R.id.mainImg)

            txtmes.text = message
            txttit.text = title
            btnyes.text = yes
            btnno.text = no
        mainImg.animate().alpha(0.25f).duration = 4000

        val builder = AlertDialog.Builder(context)
            builder.setView(view)
            val dialog: AlertDialog = builder.create()
            dialog.show()

            mDialog = dialog
            return view

    }


}