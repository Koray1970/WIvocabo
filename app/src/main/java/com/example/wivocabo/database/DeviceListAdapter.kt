package com.example.wivocabo.database

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.wivocabo.R
import com.google.android.material.behavior.SwipeDismissBehavior
import com.google.android.material.card.MaterialCardView
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DeviceListAdapter(val devicelist: MutableList<Beacon>, val context: Context) :
    RecyclerView.Adapter<DeviceListAdapter.ViewHolder>() {
    private val gson=Gson()
    private val TAG = DeviceListAdapter::class.java.simpleName
    private lateinit var parentContentLayout: CoordinatorLayout

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val gson=Gson()
        val macaddress: TextView = view.findViewById(R.id.swipecard_context_macaddress)
        val devicename: TextView = view.findViewById(R.id.swipecard_context_name)
        var devicedata:TextView=view.findViewById(R.id.swipecard_context_data)
        fun bindItems(item: Beacon) {
            macaddress.text = item.mac
            devicename.text = item.name
            devicedata.text=gson.toJson(item)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.fragment_card_swipe, parent, false)
        val swipeDismissBehavior = SwipeDismissBehavior<View>()
        swipeDismissBehavior.setSwipeDirection(SwipeDismissBehavior.SWIPE_DIRECTION_START_TO_END)

        Log.v(TAG, "Parent:" + parent.accessibilityClassName)

        parentContentLayout = view.findViewById<CoordinatorLayout>(R.id.card_container)
        val cardContentLayout = view.findViewById<MaterialCardView>(R.id.swipecard_content_layout)
        val coordinatorlayoutParams =
            cardContentLayout.layoutParams as CoordinatorLayout.LayoutParams
        coordinatorlayoutParams.behavior = swipeDismissBehavior

        swipeDismissBehavior.listener = object : SwipeDismissBehavior.OnDismissListener {
            override fun onDismiss(view: View) {
                val appDatabase by lazy {
                    Room.databaseBuilder(
                        context,
                        AppDatabase::class.java,
                        "ivocabodb.db"
                    ).fallbackToDestructiveMigration().build()
                }
                var eDataItem=parentContentLayout.findViewById<TextView>(R.id.swipecard_context_data).text.toString()
                val itemdata= gson.fromJson(eDataItem,Beacon::class.java)
                val beaconsDao=appDatabase.beaconsDao()
                val parseEvents=ParseEvents()
                parseEvents.DeleteBeacon(itemdata.mac.toString())

                CoroutineScope(Dispatchers.IO).launch {
                    beaconsDao.deleteBeacon(itemdata)
                    resetCard(view as MaterialCardView)
                    Snackbar.make(
                        parent.rootView!!,
                        R.string.cat_card_dismissed,
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onDragStateChanged(state: Int) {
                onDragStateChanged(state, cardContentLayout, parentContentLayout)
            }
        }
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(devicelist.get(position))
    }

    override fun getItemCount(): Int = devicelist.size

    private fun onDragStateChanged(
        state: Int,
        cardContentLayout: MaterialCardView,
        parent: CoordinatorLayout
    ) {

        when (state) {
            SwipeDismissBehavior.STATE_DRAGGING, SwipeDismissBehavior.STATE_SETTLING -> {
                cardContentLayout.isDragged = true
            }

            SwipeDismissBehavior.STATE_IDLE -> {
                cardContentLayout.isDragged = false
            }
        }
    }

    private fun resetCard(cardContentLayout: MaterialCardView) {
        val params = cardContentLayout.layoutParams as CoordinatorLayout.LayoutParams
        params.setMargins(0, 0, 0, 0)
        cardContentLayout.alpha = 0.0f
        cardContentLayout.requestLayout()
        var parentt = cardContentLayout.parent as CoordinatorLayout
        parentt.visibility = View.INVISIBLE
    }

}