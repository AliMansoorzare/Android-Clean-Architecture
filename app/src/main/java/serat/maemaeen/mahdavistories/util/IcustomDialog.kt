package serat.maemaeen.mahdavistories.util

import android.view.View
import androidx.appcompat.app.AlertDialog

interface IcustomDialog {
     val mDialog: AlertDialog?
         get() = null

    fun showDialog():View
}