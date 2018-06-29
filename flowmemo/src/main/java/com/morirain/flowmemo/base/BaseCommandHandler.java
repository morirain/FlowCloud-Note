package com.morirain.flowmemo.base;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.morirain.flowmemo.ui.activity.ContainerActivity;

/**
 * @author morirain
 * @email morirain.dev@outlook.com
 * @since 2018/6/16
 */


public abstract class BaseCommandHandler {

    protected int getPosition(View view) {
        if (view.getTag() instanceof RecyclerView.ViewHolder) {
            BaseAdapter.ViewHolder viewHolder = (BaseAdapter.ViewHolder) view.getTag();
            return (int) viewHolder.itemView.getTag();
        }
        return (int) view.getTag();
    }

    protected void pageJump(Activity from, BaseFragment to) {
        Intent intent = new Intent(from, ContainerActivity.class);
        intent.putExtra("fragment", to);
        from.startActivity(intent);
        //from.overridePendingTransition(R.anim.slide_in_up, R.anim.anim_hold);
    }
}
