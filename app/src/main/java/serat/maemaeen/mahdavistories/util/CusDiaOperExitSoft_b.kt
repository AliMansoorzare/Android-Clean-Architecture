package com.serat.maemaeen73.util

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import androidx.core.app.ActivityCompat.finishAffinity

import serat.maemaeen.mahdavistories.R
import serat.maemaeen.mahdavistories.util.CusDia
import serat.maemaeen.mahdavistories.util.CustomDialog
import kotlin.system.exitProcess

class CusDiaOperExitSoft_b(
    context: Context,
    title: String,
    message: String,
    yes: String,
    no: String
) : CusDia {

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
                finishAffinity(this.customDialog.context as Activity)
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