package com.example.wivocabo.database

import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wivocabo.R
class DeviceListAdapter(val devicelist: MutableList<Beacon>) :
    RecyclerView.Adapter<DeviceListAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val macaddress:TextView=view.findViewById(R.id.devicemacaddress)

        fun bindItems(item: Beacon) {
            macaddress.text=item.mac
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int = devicelist.size
}