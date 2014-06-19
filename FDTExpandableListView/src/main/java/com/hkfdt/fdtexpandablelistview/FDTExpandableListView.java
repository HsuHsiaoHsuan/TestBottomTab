package com.hkfdt.fdtexpandablelistview;

import android.content.Context;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by FDT-14014Mac on 2014/6/18.
 */
public class FDTExpandableListView extends LinearLayout
{
    private LinearLayout headerView;
    private ListView listView;
    private LayoutInflater inflater;
    private Context mContext;

    public FDTExpandableListView(Context context)
    {
        super(context);
        mContext = context;
        inflater = LayoutInflater.from(context);
        init(context);
    }

    public FDTExpandableListView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        mContext = context;
        inflater = LayoutInflater.from(context);
        init(context);
    }

    public void init(Context context)
    {
        //setOrientation(VERTICAL);
        headerView = (LinearLayout) inflater.inflate(R.layout.layout_header, null);
        addView(headerView);
    }

    public void addHeaderView(View view)
    {
        headerView.addView(view);
    }

    public void setHeaderTitles(List<String> titles)
    {
        for (int x = 0; x < titles.size(); x++)
        {
            TextView textView = new TextView(mContext);
            textView.setText(titles.get(x));
            textView.setPadding(10, 7, 10, 7);
            headerView.addView(textView, x);
        }
    }

    public void addListView(ListView view)
    {
        addView(view);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
