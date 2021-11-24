package com.ram.test

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ram.test.adapter.RecyclerViewAdapter
import com.ram.test.viewmodel.RecyclerViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    lateinit var recyclerViewAdapter: RecyclerViewAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRecyclerView()
        displayData()


    }

    private fun setupRecyclerView() {
         recyclerView.apply {
             layoutManager = LinearLayoutManager(this@MainActivity)

            recyclerViewAdapter = RecyclerViewAdapter()
             adapter = recyclerViewAdapter

             val divider =
                 DividerItemDecoration(this@MainActivity, StaggeredGridLayoutManager.VERTICAL)
             addItemDecoration(divider)
         }
    }

    fun displayData() {

        val viewModel = ViewModelProvider(this).get(RecyclerViewModel::class.java)

        //To check internet connection
        if (isOnline()) {
            progressLayout.visibility = View.VISIBLE
            viewModel.makeApiCall()
        } else {
            Toast.makeText(this@MainActivity, "No internet connection!!", Toast.LENGTH_LONG).show()
        }


        // set data to Recycler view
        viewModel.getRecyclerListObserver().observe(this, Observer {
            progressLayout.visibility = View.GONE
            if (it != null) {
                recyclerViewAdapter.setListData(it)

                recyclerViewAdapter.notifyDataSetChanged()


            } else {
                Toast.makeText(this@MainActivity, "Error in getting data", Toast.LENGTH_LONG)
                    .show()
            }

        })

    }

    fun isOnline(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null &&
                cm.activeNetworkInfo!!.isConnectedOrConnecting
    }
}