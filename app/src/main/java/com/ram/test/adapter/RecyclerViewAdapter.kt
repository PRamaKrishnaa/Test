package com.ram.test.adapter


import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ram.test.R
import com.ram.test.model.RecyclerViewData
import kotlinx.android.synthetic.main.recyclerview_item.view.*


class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {
    var items = mutableListOf<RecyclerViewData>()

    fun setListData(data: List<RecyclerViewData>) {
        this.items = data.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewAdapter.MyViewHolder {
        var inflater =
            LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item, parent, false)
        return MyViewHolder(inflater)

    }

    override fun onBindViewHolder(holder: RecyclerViewAdapter.MyViewHolder, position: Int) {
        holder.bind(items[position])


    }

    override fun getItemCount(): Int {
        return items.size
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {


        var txTitle = view.tvName
        var txNumber = view.tvNumber
        var txDate = view.tvDate
        var video = view.videoView


        fun bind(listData: RecyclerViewData) {
            txTitle.text = listData.name
            txNumber.text = "Flight Number : " + listData.flight_number


            /* val formatter = SimpleDateFormat("dd-MM-yyyy")
             val dateInString = listData.date_local
             val date = formatter.parse(dateInString)
             val formattedDate = formatter.format(date)*/

            txDate.text = "Date : " + listData.date_local

            val url = listData.links


            val uri: Uri = Uri.parse(url)
            video.setVideoURI(uri)
            video.requestFocus()
            video.start()


        }


    }


}