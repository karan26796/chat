package com.example.karan.chat.utils;

import android.os.Bundle;

/**
 * Interface to handle click events on each item of RecyclerView
 */
public interface OnRecycleViewItemClickListener {

    /**
     * @param bundle to hold the data in item of RecyclerView
     */
    void onItemClicked(Bundle bundle);
}
