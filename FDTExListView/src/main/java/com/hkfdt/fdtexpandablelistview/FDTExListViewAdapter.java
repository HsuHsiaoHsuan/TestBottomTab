package com.hkfdt.fdtexpandablelistview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.nhaarman.listviewanimations.itemmanipulation.ExpandableListItemAdapter;

import java.util.List;

/**
 * Created by FDT-14014Mac on 2014/6/18.
 */
public abstract class FDTExListViewAdapter<T> extends ExpandableListItemAdapter<T>
{
    public FDTExListViewAdapter(Context context) {
        super(context);
    }

    public FDTExListViewAdapter(Context context, List<T> items) {
        super(context, items);
    }

    public FDTExListViewAdapter(Context context, int layoutResId, int titleParentResId, int contentParentResId) {
        super(context, layoutResId, titleParentResId, contentParentResId);
    }

    public FDTExListViewAdapter(Context context, int layoutResId, int titleParentResId, int contentParentResId, List<T> items) {
        super(context, layoutResId, titleParentResId, contentParentResId, items);
    }

    @Override
    public abstract View getTitleView(int position, View view, ViewGroup viewGroup);

    @Override
    public abstract View getContentView(int position, View view, ViewGroup viewGroup);
}
