package idv.funnybrain.test;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.hkfdt.fdtexpandablelistview.FDTExpandableListViewAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.ExpandableListItemAdapter;
import com.nhaarman.listviewanimations.swinginadapters.prepared.AlphaInAnimationAdapter;

import com.hkfdt.fdtexpandablelistview.FDTExpandableListView;

/**
 * Created by FDT-14014Mac on 2014/6/13.
 */
public class Fragment_Market extends Fragment
{
    private static final String TAG = "Fragment_Market";
    private MarketListAdapter mExpandableListItemAdapter;
    private List<String> defaultList;


    static Fragment newInstance()
    {
        Fragment_Market f = new Fragment_Market();

        Bundle args = new Bundle();
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        String[] defaultData = getResources().getStringArray(R.array.defalut_currency);
        defaultList = new ArrayList<String>(Arrays.asList(defaultData));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_market, container, false);
        FDTExpandableListView listView = (FDTExpandableListView) view.findViewById(R.id.listview_market);


        // setup the list title
        listView.setHeaderTitles(Arrays.asList(getResources().getStringArray(R.array.market_list_title)));

        ListView test_listView = new ListView(getActivity());
        mExpandableListItemAdapter = new MarketListAdapter(getActivity(), defaultList);
        AlphaInAnimationAdapter alphaInAnimationAdapter = new AlphaInAnimationAdapter(mExpandableListItemAdapter);
        alphaInAnimationAdapter.setAbsListView(test_listView);
        alphaInAnimationAdapter.setInitialDelayMillis(500);
        test_listView.setAdapter(mExpandableListItemAdapter);
        listView.addListView(test_listView);

        return view;
    }

    private static class MarketListAdapter extends FDTExpandableListViewAdapter<String>
    {
        private final Context mContext;
        private final LayoutInflater mLayoutInflater;
        private List<String> mItems;

        private MarketListAdapter(final Context context, final List<String> items)
        {
            super(context, R.layout.expandablelist_market, R.id.expandablelist_title, R.id.expandablelist_content, items);
            mContext = context;
            mLayoutInflater = LayoutInflater.from(context);
            mItems = items;
        }

        @Override
        public View getTitleView(final int position, final View convertView, final ViewGroup parent)
        {
            TextView tv = (TextView) convertView;
            if(tv == null)
            {
                tv = new TextView(mContext);
            }
            tv.setText(mItems.get(position));

            return tv;
        }

        @Override
        public View getContentView(final int position, final View convertView, final ViewGroup parent)
        {
            TextView tv = (TextView) convertView;
            if(tv == null)
            {
                tv = new TextView(mContext);
            }
            tv.setTextSize(30.0f);
            tv.setText("content");

            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "you click content", Toast.LENGTH_SHORT).show();
                }
            });
            return tv;
        }
    }

    // only for test
    private static ArrayList<String> getItems()
    {
        ArrayList<String> items = new ArrayList<String>();

        for (int x = 0; x < 1000; x++)
        {
            items.add(String.valueOf(x));
        }


        return items;
    }
}