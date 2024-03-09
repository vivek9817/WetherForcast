package com.example.wetherforcasting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.wetherforcasting.Utils.CommonUtils
import com.example.wetherforcasting.Utils.CommonUtils.dismissProgressDialog
import com.example.wetherforcasting.Utils.CommonUtils.showProgressDialog
import com.example.wetherforcasting.ViewModel.WetherViewModels
import com.example.wetherforcasting.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val wetherViewModel: WetherViewModels by viewModels()

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
                    Log.e("Success", "${it.data}")
                }

                CommonUtils.ApiStatus.FAILURE -> {
                    dismissProgressDialog()
                    Log.e("Failure", "${it.message}")
                }
            }
        })
    }
}