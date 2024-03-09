package com.example.wetherforcasting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.widget.AppCompatTextView
import androidx.compose.ui.text.capitalize
import androidx.lifecycle.Observer
import com.example.wetherforcasting.Utils.CommonUtils
import com.example.wetherforcasting.Utils.CommonUtils.bottomSheetDialog
import com.example.wetherforcasting.Utils.CommonUtils.changeKalvinToCelcious
import com.example.wetherforcasting.Utils.CommonUtils.convertDateTimeToDayOfWeek
import com.example.wetherforcasting.Utils.CommonUtils.dismissProgressDialog
import com.example.wetherforcasting.Utils.CommonUtils.showProgressDialog
import com.example.wetherforcasting.Utils.CommonUtils.showSnakebarPopUp
import com.example.wetherforcasting.ViewModel.WetherViewModels
import com.example.wetherforcasting.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val wetherViewModel: WetherViewModels by viewModels()
    var filteredArray: ArrayList<Pair<String, Float>> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewWetherReports()
    }

    private fun viewWetherReports() {
        wetherViewModel.getCurrentWetherReport().observe(this@MainActivity, Observer {
            when (it.status) {
                CommonUtils.ApiStatus.LOADING -> {
                    showProgressDialog(this)
                }

                CommonUtils.ApiStatus.SUCCESS -> {
                    dismissProgressDialog()
                    it?.data?.let { forCast ->
                        forCast?.main?.let {
                            binding.txtTempture.text =
                                "${changeKalvinToCelcious(it.temp!!.toFloat())} ${getString(R.string.celcious)}"
                            binding.txtPlace.text = "${forCast?.name?.capitalize()}"
                        }
                    }
                    Log.e("Success", "${it.data}")
                    bottomSheetDialog(this@MainActivity) { dialog ->
                        wetherViewModel.getAfterFourYearWetherReport().observe(this@MainActivity,
                            Observer {
                                when (it.status) {
                                    CommonUtils.ApiStatus.LOADING -> {
                                        showProgressDialog(this)
                                        Log.e("Loader", "${it.data}")
                                    }

                                    CommonUtils.ApiStatus.SUCCESS -> {
                                        dismissProgressDialog()
                                        it.data?.let {
                                            it?.list?.forEach { x ->
                                                if (filteredArray.filter { v ->
                                                        v.first.substring(
                                                            0,
                                                            10
                                                        ) == x.dt_txt?.substring(0, 10).toString()
                                                    }.isNullOrEmpty()) {
                                                    filteredArray.add(
                                                        Pair(
                                                            x.dt_txt.toString(),
                                                            x.main?.temp!!.toFloat()
                                                        )
                                                    )
                                                }
                                            }
                                            Log.e("Tag = fa", "$filteredArray")
                                            Log.e(
                                                "Ta",
                                                "${convertDateTimeToDayOfWeek(filteredArray[4].first.toString())}"
                                            )
                                            dialog.apply {
                                                findViewById<AppCompatTextView>(R.id.txt1ForcastDay)?.text =
                                                    "${convertDateTimeToDayOfWeek(filteredArray[1].first.toString())}"
                                                findViewById<AppCompatTextView>(R.id.txt2ForcastDay)?.text =
                                                    "${convertDateTimeToDayOfWeek(filteredArray[2].first.toString())}"
                                                findViewById<AppCompatTextView>(R.id.txt3ForcastDay)?.text =
                                                    "${convertDateTimeToDayOfWeek(filteredArray[3].first.toString())}"
                                                findViewById<AppCompatTextView>(R.id.txt4ForcastDay)?.text =
                                                    "${convertDateTimeToDayOfWeek(filteredArray[4].first.toString())}"

                                                findViewById<AppCompatTextView>(R.id.txt1Day)?.text =
                                                    "${
                                                        changeKalvinToCelcious(
                                                            filteredArray[1].second
                                                        )
                                                    } ${getString(R.string.celcious)}"
                                                findViewById<AppCompatTextView>(R.id.txt2Day)?.text =
                                                    "${
                                                        changeKalvinToCelcious(
                                                            filteredArray[2].second
                                                        )
                                                    } ${getString(R.string.celcious)}"
                                                findViewById<AppCompatTextView>(R.id.txt3Day)?.text =
                                                    "${
                                                        changeKalvinToCelcious(
                                                            filteredArray[3].second
                                                        )
                                                    } ${getString(R.string.celcious)}"
                                                findViewById<AppCompatTextView>(R.id.txt4Day)?.text =
                                                    "${
                                                        changeKalvinToCelcious(
                                                            filteredArray[4].second
                                                        )
                                                    } ${getString(R.string.celcious)}"
                                            }
                                        }
                                        Log.e("Success 1", "${it.data}")
                                    }

                                    CommonUtils.ApiStatus.FAILURE -> {
                                        dismissProgressDialog()
                                        showSnakebarPopUp(
                                            binding.linActivity,
                                            "Something went wrong"
                                        ) {
                                            viewWetherReports()
                                        }
                                        Log.e("Failure1", "${it.message}")
                                    }
                                }
                            })
                    }
                }

                CommonUtils.ApiStatus.FAILURE -> {
                    dismissProgressDialog()
                    showSnakebarPopUp(
                        binding.linActivity,
                        "Something went wrong"
                    ) {
                        viewWetherReports()
                    }
                    Log.e("Failure", "${it.message}")
                }
            }
        })
    }
}