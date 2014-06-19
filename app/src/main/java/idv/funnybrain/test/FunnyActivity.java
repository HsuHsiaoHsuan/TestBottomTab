package idv.funnybrain.test;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.HashMap;

/**
 * Created by Freeman on 2014/5/18.
 */
public class FunnyActivity extends FragmentActivity
{
    private static final String TAG = "FunnyActivity";

    TabHost mTabHost;
    TabManager mTabManager;

    private static final Class<?>[] frag_list = new Class<?>[]
    {
        TestFragment.class,    // Leaderboard
        TestFragment.class,    // Notification
        Fragment_Market.class, // Market
        TestFragment.class,    // Social
        TestFragment.class     // Account
    };

    private static final int[] tab_icon = new int[]
    {
        R.drawable.tab_leaderboard_selector,
        R.drawable.tab_notification_selector,
        R.drawable.tab_market_selector,
        R.drawable.tab_social_selector,
        R.drawable.tab_account_selector
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_tabs);
        mTabHost = (TabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup();

        mTabManager = new TabManager(this, mTabHost, R.id.realtabcontent);
        String[] tab_title = getResources().getStringArray(R.array.tabs_name);
        for (int x = 0; x < tab_title.length; x++)
        {
            Bundle bundle = new Bundle();
            bundle.putString("name", tab_title[x]);

            View tab = getLayoutInflater().inflate(R.layout.tab_layout, null);
            tab.findViewById(R.id.tab_icon).setBackgroundResource(tab_icon[x]);
            ((TextView) tab.findViewById(R.id.tab_text)).setText(tab_title[x]);

            mTabManager.addTab(mTabHost.newTabSpec(tab_title[x]).setIndicator(tab), frag_list[x], bundle);
        }

        if (savedInstanceState != null)
        {
            mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab"));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putString("tab", mTabHost.getCurrentTabTag());
    }

    public static class TabManager implements TabHost.OnTabChangeListener
    {
        private final FragmentActivity mActivity;
        private final TabHost mTabHost;
        private final int mContainerId;
        private final HashMap<String, TabInfo> mTabs = new HashMap<String, TabInfo>();
        TabInfo mLastTab;

        static final class TabInfo
        {
            private final String tag;
            private final Class<?> clss;
            private final Bundle args;
            private Fragment fragment;

            TabInfo(String _tag, Class<?> _class, Bundle _args) {
                tag = _tag;
                clss = _class;
                args = _args;
            }
        }

        static class DummyTabFactory implements TabHost.TabContentFactory
        {
            private final Context mContext;

            public DummyTabFactory(Context context)
            {
                mContext = context;
            }

            @Override
            public View createTabContent(String tag)
            {
                View v = new View(mContext);
                v.setMinimumWidth(0);
                v.setMinimumHeight(0);

                return v;
            }
        }

        public TabManager(FragmentActivity activity, TabHost tabHost, int containerId)
        {
            mActivity = activity;
            mTabHost = tabHost;
            mContainerId = containerId;
            mTabHost.setOnTabChangedListener(this);
        }

        public void addTab(TabHost.TabSpec tabSpec, Class<?> clss, Bundle args)
        {
            tabSpec.setContent(new DummyTabFactory(mActivity));
            String tag = tabSpec.getTag();

            TabInfo info = new TabInfo(tag, clss, args);
            info.fragment = mActivity.getSupportFragmentManager().findFragmentByTag(tag);
            if (info.fragment != null && !info.fragment.isDetached())
            {
                android.support.v4.app.FragmentTransaction ft = mActivity.getSupportFragmentManager().beginTransaction();
                ft.detach(info.fragment);
                ft.commit();
            }

            mTabs.put(tag, info);
            mTabHost.addTab(tabSpec);
        }

        @Override
        public void onTabChanged(String tabId)
        {
            TabInfo newTab = mTabs.get(tabId);

            if (mLastTab != newTab)
            {
                FragmentTransaction ft = mActivity.getSupportFragmentManager().beginTransaction();
                if (mLastTab != null)
                {
                    if (mLastTab.fragment != null)
                    {
                        ft.detach(mLastTab.fragment);
                    }
                }
                if (newTab != null)
                {
                    if (newTab.fragment == null)
                    {
                        newTab.fragment = Fragment.instantiate(mActivity, newTab.clss.getName(), newTab.args);
                        ft.add(mContainerId, newTab.fragment, newTab.tag);
                    } else
                    {
                        ft.attach(newTab.fragment);
                    }
                }

                mLastTab = newTab;
                ft.commit();
                mActivity.getSupportFragmentManager().executePendingTransactions();
            }
        }
    }
}
