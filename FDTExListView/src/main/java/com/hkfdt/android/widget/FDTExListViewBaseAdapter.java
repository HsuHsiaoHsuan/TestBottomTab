package com.hkfdt.android.widget;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.nhaarman.listviewanimations.itemmanipulation.ExpandableListItemAdapter;

import java.util.List;

/**
 * Created by FDT-14014Mac on 2014/6/18.
 */
public abstract class FDTExListViewBaseAdapter<T> extends ExpandableListItemAdapter<T>
{
    public FDTExListViewBaseAdapter(Context context, int layoutResId, int titleResId, int contentResId, List<T> items) {
        super(context, layoutResId, titleResId, contentResId, items);
    }

    public static class ViewHolder_title
    {
        List<View> views;
    }

    public static class ViewHolder_content
    {
        List<View> views;
    }

    @Override
    public abstract View getTitleView(int position, View view, ViewGroup viewGroup);

    @Override
    public abstract View getContentView(int position, View view, ViewGroup viewGroup);
}
