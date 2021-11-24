package com.ram.test.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import com.ram.test.model.RecyclerViewData
import com.ram.test.model.Video
import com.ram.test.network.RetroInstance
import com.ram.test.network.RetroService
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecyclerViewModel : ViewModel() {
    var recyclerViewList: MutableLiveData<List<RecyclerViewData>>

    init {
        recyclerViewList = MutableLiveData()

    }

    fun getRecyclerListObserver(): MutableLiveData<List<RecyclerViewData>> {
        return recyclerViewList
    }

    //Get data from API
    fun makeApiCall() {
        val retroGetData = RetroInstance.getRetroInstance().create(RetroService::class.java)
        val call = retroGetData.getDataFromApi()
        call.enqueue(object : Callback<JsonObject> {
            override fun onResponse(
                call: Call<JsonObject>,
                response: Response<JsonObject>
            ) {

                //  Log.d("SucResponse", response.body().toString())
               var jsonObject = response.body()?.getAsJsonObject()

                if (response.code() == 200) {

                        var name:String = jsonObject?.get("name").toString()
                        var number:String = jsonObject?.get("flight_number").toString()
                        var date:String = jsonObject?.get("date_local").toString()
                        var url:String = jsonObject?.getAsJsonObject("links")?.get("webcast").toString()


                    var data = RecyclerViewData(name,number,date,url)

                    recyclerViewList.postValue(listOf(data))
                } else {
                    recyclerViewList.postValue(null)
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                  // Log.d("FalResponse", t.message.toString())
                recyclerViewList.postValue(null)
                //  Toast.makeText(this@MainActivity, "Error in getting the data", Toast.LENGTH_LONG).show()
            }
        })
    }
}




