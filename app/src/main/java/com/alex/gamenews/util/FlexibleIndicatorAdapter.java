package com.alex.gamenews.util;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alex.gamenews.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FlexibleIndicatorAdapter extends RecyclerView.Adapter<FlexibleIndicatorAdapter.FlexibleIndicatorViewHolder> {

    private RecyclerView recyclerView;
    private ViewPager viewPager;
    private int visibleCount;
    private int totalCount;
    private int cursorStart;
    private int cursorEnd;
    private int selectedPosition;

    public FlexibleIndicatorAdapter(RecyclerView recyclerView, ViewPager viewPager) {
        this.recyclerView = recyclerView;
        this.viewPager = viewPager;

        int cursorSize = 2;
        visibleCount = 7;
        totalCount = viewPager.getAdapter().getCount();
        cursorStart = viewPager.getCurrentItem();
        cursorEnd = viewPager.getCurrentItem() + cursorSize;

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                selectedPosition = position;
                moveCursor(selectedPosition);
                notifyDataSetChanged();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                // Stop only scrolling.
                return recyclerView.getScrollState() == RecyclerView.SCROLL_STATE_DRAGGING;
            }
        });

        moveCursor(viewPager.getCurrentItem());
        notifyDataSetChanged();
    }

    @Override
    public FlexibleIndicatorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FlexibleIndicatorViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.indicator_frame, parent, false));
    }

    @Override
    public void onBindViewHolder(final FlexibleIndicatorViewHolder holder, int position) {
        holder.itemView.setSelected(selectedPosition == holder.getAdapterPosition());
        holder.itemView.setOnClickListener(view -> viewPager.setCurrentItem(holder.getAdapterPosition(), true));
        holder.bindData(holder.getAdapterPosition());
    }

    @Override
    public int getItemCount() {
        return totalCount;
    }

    class FlexibleIndicatorViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.indicator)
        ImageView indicator;

        FlexibleIndicatorViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bindData(int index) {
            if (index >= cursorStart && index <= cursorEnd)
                indicator.setImageResource(R.drawable.tab_selector);
            else if (index == cursorStart - 1 || index == cursorEnd + 1)
                indicator.setImageResource(R.drawable.dot_medium);
            else if (index == cursorStart - 2 || index == cursorEnd + 2)
                indicator.setImageResource(R.drawable.dot_small);
            else indicator.setImageResource(R.color.colorTransparent);
        }
    }

    private void moveCursor(int position) {
        int bias = position > cursorEnd ? position - cursorEnd :
                (position < cursorStart ? position - cursorStart : 0);
        if (bias != 0) {
            cursorStart += bias;
            cursorEnd += bias;
            recyclerView.smoothScrollBy((int) Math.round(Double.valueOf(recyclerView.getWidth()) / Double.valueOf(visibleCount)) * bias, 0);
        }
    }
}
