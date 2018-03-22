package com.example.ivan.markmanager.ProjectRecyclerPackage;

/**
 * Created by Ivan on 21.03.2018.
 */

public interface ItemTouchHelperAdapter {
    boolean onItemMove(int from, int to);
    void onItemRemove(int pos);
}
