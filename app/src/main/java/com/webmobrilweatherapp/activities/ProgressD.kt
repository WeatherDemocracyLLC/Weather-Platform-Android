package com.webmobrilweatherapp.activities

import android.widget.TextView
import android.widget.RelativeLayout
import android.view.WindowManager.BadTokenException
import android.app.Dialog
import android.content.Context
import android.view.View
import com.webmobrilweatherapp.R
import java.lang.Exception

class ProgressD : Dialog {
    constructor(context: Context?) : super(context!!) {}
    constructor(context: Context?, theme: Int) : super(context!!, theme) {}

    fun setMessage(message: CharSequence?) {
        if (message != null && message.length > 0) {
            findViewById<View>(R.id.message).visibility = View.VISIBLE
            val txt = findViewById<TextView>(R.id.message)
            txt.text = message
            txt.invalidate()
        }
    }
    companion object {
        var dialog: ProgressD? = null
        fun showLoading(context: Context?, message: CharSequence?): ProgressD? {
            dialog = ProgressD(context, R.style.Progressd)
            dialog!!.setCanceledOnTouchOutside(true)
            dialog!!.setTitle("")
            dialog!!.setContentView(R.layout.layout_progress)
            if (message == null || message.length == 0) {
                dialog!!.findViewById<View>(R.id.message).visibility =
                    View.GONE
            } else {
                val txt = dialog!!.findViewById<TextView>(R.id.message)
                txt.text = message
            }
            dialog!!.setCancelable(true)
            dialog!!.setCanceledOnTouchOutside(true)
            dialog!!.show()
            val window = dialog!!.window!!
            window.setLayout(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
            )
            return dialog
        }

        fun hideProgressDialog() {
            try {
                if (dialog != null) dialog!!.dismiss()
            } catch (e: BadTokenException) {
                e.printStackTrace()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}