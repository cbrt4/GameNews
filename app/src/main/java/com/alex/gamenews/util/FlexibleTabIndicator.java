package com.alex.gamenews.util;

import android.support.design.widget.TabLayout;

import com.alex.gamenews.R;

public class FlexibleTabIndicator {

    private static int caretStart;
    private static int caretEnd;
    private static int caretSize;
    private static int count;

    public static void indicate(final TabLayout tabLayout, int caretSize) {
        FlexibleTabIndicator.caretSize = caretSize - 1;

        caretStart = tabLayout.getSelectedTabPosition();
        caretEnd = tabLayout.getSelectedTabPosition() + FlexibleTabIndicator.caretSize;
        count = tabLayout.getTabCount();
        moveCaret(tabLayout, tabLayout.getSelectedTabPosition());

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                moveCaret(tabLayout, tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        updateIndication(tabLayout);
    }

    private static void moveCaret(TabLayout tabLayout,int position) {
        int difference = position > caretEnd ? position - caretEnd :
                (position < caretStart ? position - caretStart : 0);
        caretStart += difference;
        caretEnd += difference;
        updateIndication(tabLayout);
        tabLayout.scrollBy((tabLayout.getWidth()/count) * difference, 0);
    }

    private static void updateIndication(TabLayout tabLayout) {
        for (int i = 0; i < count; i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (i >= caretStart && i <= caretEnd) tab.setIcon(R.drawable.tab_selector);
            else if (i == caretStart - 1 || i == caretEnd + 1) tab.setIcon(R.drawable.dot_medium);
            else if (i == caretStart - 2 || i == caretEnd + 2) tab.setIcon(R.drawable.dot_small);
            else tab.setIcon(R.color.colorTransparent);
        }
    }
}