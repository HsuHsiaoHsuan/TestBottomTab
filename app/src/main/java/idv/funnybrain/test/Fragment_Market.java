package idv.funnybrain.test;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.hkfdt.fdtexpandablelistview.FDTExListView;
import com.hkfdt.fdtexpandablelistview.FDTExListViewAdapter;

import com.nhaarman.listviewanimations.itemmanipulation.ExpandableListItemAdapter;
import com.nhaarman.listviewanimations.swinginadapters.prepared.AlphaInAnimationAdapter;

/**
 * Created by FDT-14014Mac on 2014/6/13.
 */
public class Fragment_Market extends Fragment
{
    private static final String TAG = "Fragment_Market";
    private MarketListAdapter mExpandableListItemAdapter;
    private List<String> defaultList;
    private List<Double> randomValues;


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
        randomValues = new ArrayList<Double>();
        for (int x = 0; x < defaultList.size(); x++)
        {
            randomValues.add(0.0);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        //View view = inflater.inflate(R.layout.fragment_market, container, false);
        //FDTExpandableListView listView = (FDTExpandableListView) view.findViewById(R.id.listview_market);
        View view = inflater.inflate(R.layout.listview_market, container, false);
        ListView listView = (ListView) view.findViewById(R.id.listview);


        // setup the list title
        //listView.setHeaderTitles(Arrays.asList(getResources().getStringArray(R.array.market_list_title)));

        ListView test_listView = (ListView) inflater.inflate(R.layout.listview_market, null, false).findViewById(R.id.listview);
        //listView.addListView(test_listView);

        mExpandableListItemAdapter = new
        (getActivity(), R.layout.expandablelist_market,
                R.id.expandablelist_title,
                R.id.expandablelist_content,
                defaultList, randomValues);
        AlphaInAnimationAdapter alphaInAnimationAdapter = new AlphaInAnimationAdapter(mExpandableListItemAdapter);
        //alphaInAnimationAdapter.setAbsListView(test_listView);
        alphaInAnimationAdapter.setAbsListView(listView);
        alphaInAnimationAdapter.setInitialDelayMillis(500);
        //test_listView.setAdapter(mExpandableListItemAdapter);
        listView.setAdapter(mExpandableListItemAdapter);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true)
                {
                    for (int x = 0; x < randomValues.size()-1; x++)
                    {
                        randomValues.set(x, (Double) (Math.random() * 100) + 1);
                    }
                    mHandler.sendMessage(new Message());

                    try {
                        Thread.sleep(700);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        return view;
    }

    private android.os.Handler mHandler = new android.os.Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mExpandableListItemAdapter.notifyDataSetChanged();
        }
    };

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