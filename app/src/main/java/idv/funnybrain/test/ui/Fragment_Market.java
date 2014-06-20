package idv.funnybrain.test.ui;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.hkfdt.android.widget.FDTExListViewBaseAdapter;
import com.nhaarman.listviewanimations.swinginadapters.prepared.AlphaInAnimationAdapter;

import idv.funnybrain.test.adapter.MarketListAdapter;
import idv.funnybrain.test.R;

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
        View view = inflater.inflate(R.layout.listview_market, container, false);
        ListView listView = (ListView) view.findViewById(R.id.listview);

        mExpandableListItemAdapter = new
                MarketListAdapter(getActivity(), R.layout.expandablelist_market,
                R.id.expandablelist_title,
                R.id.expandablelist_content,
                defaultList, randomValues);
        AlphaInAnimationAdapter alphaInAnimationAdapter = new AlphaInAnimationAdapter(mExpandableListItemAdapter);
        alphaInAnimationAdapter.setAbsListView(listView);
        alphaInAnimationAdapter.setInitialDelayMillis(500);
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

}