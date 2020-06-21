package com.mycode.mvvmkotlin.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mycode.mvvmkotlin.R
import com.mycode.mvvmkotlin.Util.CustomProgressDialog
import com.mycode.mvvmkotlin.Util.Util
import com.mycode.mvvmkotlin.databinding.ActivityMainBinding
import com.mycode.mvvmkotlin.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainViewModel
    lateinit var customProgressDialog: CustomProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding.viewModel = viewModel
        customProgressDialog = CustomProgressDialog(this)
        // initObservables()

        binding.btnContinue.setOnClickListener {
            if (Util.isInternetConnected(this@MainActivity)) {
                Util.hideSoftKeyBoard(this@MainActivity, it)
                customProgressDialog.show()
                viewModel.onLogin()?.observe(this, Observer {
                    if (customProgressDialog.isShowing) {
                        customProgressDialog.dismiss()
                    }
                    Toast.makeText(this, "Response $it", Toast.LENGTH_LONG).show()
                })
            } else {
                Toast.makeText(this, "No Internet Connection!", Toast.LENGTH_LONG).show()
            }
        }

        binding.parent.setOnClickListener {
            Util.hideSoftKeyBoard(this, it)
        }
    }

    /*private fun initObservables() {
        viewModel?.progressDialog?.observe(this, Observer {
            if (it!!) {
                customProgressDialog?.show()
            } else customProgressDialog?.dismiss()
        })
    }*/


}