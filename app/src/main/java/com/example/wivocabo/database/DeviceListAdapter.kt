package com.example.wivocabo.database

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.wivocabo.R
import com.google.android.material.behavior.SwipeDismissBehavior
import com.google.android.material.card.MaterialCardView
import com.google.android.material.snackbar.Snackbar

class DeviceListAdapter(val devicelist: MutableList<Beacon>) :
    RecyclerView.Adapter<DeviceListAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val macaddress:TextView=view.findViewById(R.id.swipecard_context_text)

        fun bindItems(item: Beacon) {
            macaddress.text=item.mac
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.fragment_card_swipe,parent,false)
        val swipeDismissBehavior= SwipeDismissBehavior<View>()
        swipeDismissBehavior.setSwipeDirection(SwipeDismissBehavior.SWIPE_DIRECTION_START_TO_END)
        val cardContentLayout=view.findViewById<MaterialCardView>(R.id.swipecard_content_layout)

        val coordinatorlayoutParams=cardContentLayout.layoutParams as CoordinatorLayout.LayoutParams
        coordinatorlayoutParams.behavior=swipeDismissBehavior

        swipeDismissBehavior.listener = object : SwipeDismissBehavior.OnDismissListener {
            override fun onDismiss(view: View) {
                Snackbar.make(parent.rootView!!, R.string.cat_card_dismissed, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.cat_card_undo) { v ->
                        resetCard(cardContentLayout)
                    }.show()
            }

            override fun onDragStateChanged(state: Int) {
                onDragStateChanged(state, cardContentLayout)
            }
        }



        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(devicelist.get(position))
    }

    override fun getItemCount(): Int = devicelist.size

    private fun onDragStateChanged(state:Int,cardContentLayout:MaterialCardView){
        when(state){
            SwipeDismissBehavior.STATE_DRAGGING,SwipeDismissBehavior.STATE_SETTLING->cardContentLayout.isDragged=true
            SwipeDismissBehavior.STATE_IDLE-> cardContentLayout.isDragged=false
        }
    }
    private fun resetCard(cardContentLayout: MaterialCardView){
        val params=cardContentLayout.layoutParams as CoordinatorLayout.LayoutParams
        params.setMargins(0,0,0,0)
        cardContentLayout.alpha=1.0f
        cardContentLayout.requestLayout()
    }

}