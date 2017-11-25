package com.alex.gamenews.util;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alex.gamenews.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FlexibleIndicatorAdapter extends RecyclerView.Adapter<FlexibleIndicatorAdapter.FlexibleIndicatorViewHolder> {

    private RecyclerView recyclerView;
    private ViewPager viewPager;
    private int caretStart;
    private int caretEnd;
    private int caretSize;
    private int count;
    private int selectedPosition;

    public FlexibleIndicatorAdapter(RecyclerView recyclerView, ViewPager viewPager, int caretSize) {
        this.recyclerView = recyclerView;
        this.viewPager = viewPager;
        this.caretSize = caretSize - 1;

        caretStart = viewPager.getCurrentItem();
        caretEnd = viewPager.getCurrentItem() + this.caretSize;
        count = viewPager.getAdapter().getCount();
        moveCaret(viewPager.getCurrentItem());

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                selectedPosition = position;
                moveCaret(selectedPosition);
                notifyDataSetChanged();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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
        return count;
    }

    class FlexibleIndicatorViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.indicator)
        ImageView indicator;

        public FlexibleIndicatorViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bindData(int index) {
            if (index >= caretStart && index <= caretEnd) indicator.setImageResource(R.drawable.tab_selector);
            else if (index == caretStart - 1 || index == caretEnd + 1) indicator.setImageResource(R.drawable.dot_medium);
            else if (index == caretStart - 2 || index == caretEnd + 2) indicator.setImageResource(R.drawable.dot_small);
            else indicator.setImageResource(R.color.colorTransparent);
        }
    }

    private void moveCaret(int position) {
        int difference = position > caretEnd ? position - caretEnd :
                (position < caretStart ? position - caretStart : 0);
        caretStart += difference;
        caretEnd += difference;
        recyclerView.smoothScrollBy(((recyclerView.getWidth() +
                recyclerView.getPaddingStart() +
                recyclerView.getPaddingEnd()) / count) * difference, 0);
    }
}
