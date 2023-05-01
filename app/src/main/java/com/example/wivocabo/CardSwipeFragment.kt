package com.example.wivocabo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout.LayoutParams
import androidx.fragment.app.Fragment
import com.example.wivocabo.databinding.FragmentCardSwipeBinding
import com.google.android.material.behavior.SwipeDismissBehavior
import com.google.android.material.card.MaterialCardView
import com.google.android.material.snackbar.Snackbar

class CardSwipeFragment : Fragment() {
    private lateinit var binding: FragmentCardSwipeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCardSwipeBinding.inflate(layoutInflater)

        /*val swipeDismissBehavior=SwipeDismissBehavior<View>()
        swipeDismissBehavior.setSwipeDirection(SwipeDismissBehavior.SWIPE_DIRECTION_START_TO_END)
        val cardContentLayout=binding.swipecardContentLayout

        val coordinatorlayoutParams=binding.swipecardContentLayout.layoutParams as LayoutParams
        coordinatorlayoutParams.behavior=swipeDismissBehavior

        swipeDismissBehavior.listener = object : SwipeDismissBehavior.OnDismissListener {
            override fun onDismiss(view: View) {
                Snackbar.make(container!!, R.string.cat_card_dismissed, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.cat_card_undo) { v ->
                        resetCard(cardContentLayout)
                    }.show()
            }

            override fun onDragStateChanged(state: Int) {
                onDragStateChanged(state, cardContentLayout)
            }
        }*/


        return binding.swipecardContentLayout
    }
    /*private fun onDragStateChanged(state:Int,cardContentLayout:MaterialCardView){
        when(state){
            SwipeDismissBehavior.STATE_DRAGGING,SwipeDismissBehavior.STATE_SETTLING->cardContentLayout.isDragged=true
            SwipeDismissBehavior.STATE_IDLE-> cardContentLayout.isDragged=false
            card
        }
    }
    private fun resetCard(cardContentLayout: MaterialCardView){
        val params=cardContentLayout.layoutParams as CoordinatorLayout.LayoutParams
        params.setMargins(0,0,0,0)
        cardContentLayout.alpha=1.0f
        cardContentLayout.requestLayout()
    }*/

}