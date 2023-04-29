package com.example.wivocabo;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.behavior.SwipeDismissBehavior;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;


public class CardSwipeDismissFragment extends Fragment {

    /*public CardSwipeDismissFragment() {
        // Required empty public constructor
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup,
                             @Nullable Bundle bundle) {
        View view = layoutInflater.inflate(R.layout.fragment_card_swipe_dismiss, viewGroup, false);
        CoordinatorLayout container = view.findViewById(R.id.swipecard_container);
        SwipeDismissBehavior<View> swipeDismissBehavior = new SwipeDismissBehavior<>();
        swipeDismissBehavior.setSwipeDirection(SwipeDismissBehavior.SWIPE_DIRECTION_START_TO_END);

        MaterialCardView cardContentLayout = view.findViewById(R.id.swipecard_content_layout);
        CoordinatorLayout.LayoutParams coordinatorParams =
                (CoordinatorLayout.LayoutParams) cardContentLayout.getLayoutParams();

        coordinatorParams.setBehavior(swipeDismissBehavior);

        swipeDismissBehavior.setListener(new SwipeDismissBehavior.OnDismissListener() {
            @Override
            public void onDismiss(View view) {
                Snackbar.make(container, R.string.cat_card_dismissed, Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.cat_card_undo, v -> resetCard(cardContentLayout)).show();
            }

            @Override
            public void onDragStateChanged(int state) {
                CardSwipeDismissFragment.onDragStateChanged(state, cardContentLayout);
            }
        });

        return view;
    }
    private static void onDragStateChanged(int state, MaterialCardView cardContentLayout) {
        switch (state) {
            case SwipeDismissBehavior.STATE_DRAGGING:
            case SwipeDismissBehavior.STATE_SETTLING:
                cardContentLayout.setDragged(true);
                break;
            case SwipeDismissBehavior.STATE_IDLE:
                cardContentLayout.setDragged(false);
                break;
            default: // fall out
        }
    }

    private static void resetCard(MaterialCardView cardContentLayout) {
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) cardContentLayout
                .getLayoutParams();
        params.setMargins(0, 0, 0, 0);
        cardContentLayout.setAlpha(1.0f);
        cardContentLayout.requestLayout();
    }
}