package com.example.wetherforcasting.Utils

import android.app.Activity
import android.app.Service
import android.content.Context
import android.graphics.Point
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatImageView
import com.example.wetherforcasting.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Locale


object CommonUtils {

    var progressDialog: AlertDialog? = null
    lateinit var wmlp: WindowManager.LayoutParams

    enum class ApiStatus {
        LOADING,
        SUCCESS,
        FAILURE
    }

    /**
     * Converts an object of type any to a provided class object for now
     * @param map An object of any type
     * @return an object of supplied type
     */
    inline fun <reified L> convertLinkedTreeMapToClass(map: Any): L {
        Gson().apply { return this.fromJson(this.toJsonTree(map).asJsonObject, L::class.java) }
    }

    fun showProgressDialog(mContext: Context) {
        progressDialog?.let {
            dismissProgressDialog()
        }
        val inflater = (mContext as Activity).layoutInflater
        val view = inflater.inflate(R.layout.layout_reload, null)
        var progressDialogBuilder = AlertDialog.Builder(
            mContext,
            android.R.style.Theme_Material_Light_NoActionBar_Fullscreen
        )
        imageViewAnimation(view.findViewById<AppCompatImageView>(R.id.imgBtnReload))
        progressDialog = progressDialogBuilder.setView(view).create()
        wmlp = progressDialog!!.window!!.attributes
        wmlp.gravity = Gravity.CENTER
        val win_manager = mContext.getSystemService(Service.WINDOW_SERVICE) as WindowManager
        val display = win_manager.defaultDisplay // getting the screen size of device
        val size = Point()
        display.getSize(size)
        val newwidth = (mContext.resources.displayMetrics.widthPixels * 0.99).toInt()
        val newheight = (mContext.resources.displayMetrics.heightPixels * 0.99).toInt()

        progressDialog?.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setCancelable(false)
            window!!.setLayout(newwidth, newheight)
            window!!.attributes = wmlp
            setCanceledOnTouchOutside(false)
            show()
        }
    }

    fun dismissProgressDialog() {
        try {
            progressDialog?.let {
                if (progressDialog!!.isShowing) {
                    progressDialog!!.dismiss()
                    progressDialog = null
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun imageViewAnimation(image: AppCompatImageView) {
        image.clearAnimation()
        val rotate = RotateAnimation(
            0f,
            180f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        rotate.repeatCount = Animation.INFINITE;
        rotate.duration = 1400
        rotate.interpolator = LinearInterpolator()
        image.startAnimation(rotate)
    }

    fun bottomSheetDialog(mContext: Context, onDone: (BottomSheetDialog) -> Unit) {
        val bottomSheetDialog = BottomSheetDialog(mContext, R.style.SheetDialog)
        bottomSheetDialog.setContentView(R.layout.layout_for_after_four_days_forcast)
        onDone(bottomSheetDialog)
        val bottomSheetBehavior = bottomSheetDialog.behavior
        bottomSheetBehavior.isDraggable = false
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        bottomSheetDialog.show()
    }

    fun changeKalvinToCelcious(kelvin: Float): Double {
        return roundOffDecimal((kelvin.minus(273.15f)).toDouble()) ?: 0.0
    }

    private fun roundOffDecimal(number: Double): Double? {
        val df = DecimalFormat("#.#")
        df.roundingMode = RoundingMode.CEILING
        return df.format(number).toDouble()
    }

    fun convertDateTimeToDayOfWeek(dateTimeString: String): String {
        val dateTimeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

        try {
            val dateTime = dateTimeFormat.parse(dateTimeString)
            val dayOfWeekFormat = SimpleDateFormat("EEEE", Locale.getDefault())
            return dayOfWeekFormat.format(dateTime)
        } catch (e: Exception) {
            // Handle parsing or other exceptions
            e.printStackTrace()
            return "Error" // Return an error message
            Log.e("Tag fslse", "Error")
        }
    }

    fun showSnakebarPopUp(view: ViewGroup, title: String, onDone: () -> Unit) {
        Snackbar.make(view, title, Snackbar.LENGTH_INDEFINITE).setAction(
            "Retry"
        ) {
            onDone()
        }.show()
    }

}