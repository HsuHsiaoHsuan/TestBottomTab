package idv.funnybrain.test.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.hkfdt.android.widget.FDTExListViewBaseAdapter;
import idv.funnybrain.test.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by freeman on 2014/6/20.
 */
public class MarketListAdapter extends FDTExListViewBaseAdapter
{
    private final Context mContext;
    private final LayoutInflater mLayoutInflater;
    private List<String> mItems;
    private List<Double> mValues;

    public MarketListAdapter(Context context, int layoutResId, int titleParentResId, int contentParentResId, List<String> items, List<Double> values) {
        super(context, layoutResId, titleParentResId, contentParentResId, items);
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mItems = items;
        mValues = values;
    }

    @Override
    public int getCount()
    {
        return mItems.size();
    }

    static class ViewHolder_title
    {
        TextView name;
        TextView value;
        TextView sign;
        List<View> views;
    }

    static List<Object> viewHolder_test;

    @Override
    public View getTitleView(final int position, View convertView, ViewGroup parent)
    {
        ViewHolder_title viewHolder;

        if (convertView == null)
        {
            convertView = mLayoutInflater.inflate(R.layout.expandablelist_market, parent, false);
            viewHolder = new ViewHolder_title();
            viewHolder.name = (TextView) convertView.findViewById(R.id.list_title_name);
            viewHolder.value = (TextView) convertView.findViewById(R.id.list_title_value);
            viewHolder.sign = (TextView) convertView.findViewById(R.id.list_title_sign);

            viewHolder.views = new ArrayList<View>();

            //viewHolder.views.add()
            //for (View view: viewHolder.views)

//            viewHolder.name.setOnTouchListener(new View.OnTouchListener() {
//                CountDownTimer c;
//                long time = 5000;
//
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//                    final String tmp = ((TextView) v).getText().toString();
//                    switch (event.getAction())
//                    {
//                        case MotionEvent.ACTION_DOWN:
//                            c = new CountDownTimer(5000, 1000) {
//                                @Override
//                                public void onTick(long millisUntilFinished) {
//                                    time = millisUntilFinished;
//                                }
//
//                                @Override
//                                public void onFinish() {
//                                    Toast.makeText(mContext, "you long press name " + tmp + " ,time=" + time, Toast.LENGTH_SHORT).show();
//                                }
//                            }.start();
//                            break;
//
//                        case MotionEvent.ACTION_UP:
//                            if (time > 0)
//                            {
//                                Toast.makeText(mContext, "you click name " + tmp + " ,time="+time, Toast.LENGTH_SHORT).show();
//                                c.cancel();;
//                            }
//                            break;
//                    }
//                    return false;
//                }
//            });

            viewHolder.name.setOnTouchListener(new View.OnTouchListener()
            {
                @Override
                public boolean onTouch(View v, MotionEvent event)
                {
                    Toast.makeText(mContext, "You click " + ((TextView) v).getText() +
                                             ", on row " + position +
                                             ", has tag " + v.getTag(), Toast.LENGTH_SHORT).show();

                    return false;
                }
            });

            viewHolder.value.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event)
                {
                    String tmp = ((TextView) v).getText().toString();
                    Toast.makeText(mContext, "you click value " + tmp, Toast.LENGTH_SHORT).show();
                    return true;
                }
            });

            viewHolder.sign.setOnTouchListener(new View.OnTouchListener()
            {
                @Override
                public boolean onTouch(View v, MotionEvent event)
                {

                    return false;
                }
            });

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder_title) convertView.getTag();
        }

        viewHolder.name.setText(mItems.get(position));
        viewHolder.value.setText(mValues.get(position).toString());
        viewHolder.sign.setText("XXX");

        return convertView;
    }

    @Override
    public View getContentView(int position, View convertView, ViewGroup parent)
    {
        TextView tv = (TextView) convertView;
        if(tv == null)
        {
            tv = new TextView(mContext);
        }
        tv.setTextSize(30.0f);
        tv.setText("content");

        tv.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(mContext, "you click content", Toast.LENGTH_SHORT).show();
            }
        });
        return tv;
    }
}
