package com.example.ivan.markmanager.ProjectRecyclerPackage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.example.ivan.markmanager.ProjectDialogs.ProjectEditDialog;
import com.example.ivan.markmanager.ProjectDialogs.ProjectRemoveDialog;
import com.example.ivan.markmanager.R;

import java.util.ArrayList;

/**
 * Created by Ivan on 21.03.2018.
 */

public class ItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private ProjectAdapter adapter;
    private Context context;
    private FragmentManager fragmentManager;
    private ArrayList<Project> projects;

    public ItemTouchHelperCallback(ProjectAdapter adapter, Context context, FragmentManager fragmentManager) {
        this.adapter = adapter;
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    public void setProjects(ArrayList<Project> projects) {
        this.projects = projects;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(
                ItemTouchHelper.DOWN | ItemTouchHelper.UP,
                ItemTouchHelper.START | ItemTouchHelper.END
                );
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        adapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        //adapter.onItemRemove(viewHolder.getAdapterPosition());
        if(direction == ItemTouchHelper.START){
            ProjectRemoveDialog dialog = new ProjectRemoveDialog();
            dialog.setAdapter(adapter, viewHolder.getAdapterPosition());
            dialog.setProjects(projects);
            dialog.show(fragmentManager, "ProjectRemoveDialog");
        }
        if(direction == ItemTouchHelper.END){
            ProjectEditDialog dialog = new ProjectEditDialog();
            dialog.setAdapter(adapter, (ProjectAdapter.ViewHolder)viewHolder);
            dialog.show(fragmentManager, "ProjectEditDialog");
        }
    }

    @Override
    public void onChildDraw(Canvas c,
                            RecyclerView recyclerView,
                            RecyclerView.ViewHolder viewHolder,
                            float dX, float dY, int actionState, boolean isCurrentlyActive) {
        Bitmap icon;
        Paint p = new Paint();
        if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){

            View itemView = viewHolder.itemView;
            float height = (float) itemView.getBottom() - (float) itemView.getTop();
            float width = height / 3;

            if(dX > 0){
                p.setColor(Color.parseColor("#388E3C"));
                RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX,(float) itemView.getBottom());
                c.drawRect(background,p);
                icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_action_item_edit);
                RectF icon_dest = new RectF((float) itemView.getLeft() + width ,(float) itemView.getTop() + width,(float) itemView.getLeft()+ 2*width,(float)itemView.getBottom() - width);
                c.drawBitmap(icon,null,icon_dest,p);
            } else {
                p.setColor(Color.parseColor("#D32F2F"));
                RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(),(float) itemView.getRight(), (float) itemView.getBottom());
                c.drawRect(background,p);
                icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_action_item_delete);
                RectF icon_dest = new RectF((float) itemView.getRight() - 2*width ,(float) itemView.getTop() + width,(float) itemView.getRight() - width,(float)itemView.getBottom() - width);
                c.drawBitmap(icon,null,icon_dest,p);
            }
        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }
}
