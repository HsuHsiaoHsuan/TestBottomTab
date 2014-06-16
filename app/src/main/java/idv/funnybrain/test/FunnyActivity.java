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
//    ViewPager mViewPager;
//    TabsAdapter mTabsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

//        setContentView(R.layout.fragment_tabs_pager);
        setContentView(R.layout.fragment_tabs);
        mTabHost = (TabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup();

        mTabManager = new TabManager(this, mTabHost, R.id.realtabcontent);
        for (int x = 1; x <= 5; x++)
        {
            Bundle bundle = new Bundle();
            bundle.putInt("num", x);
            mTabManager.addTab(createIconTab(x), TestFragment.class, bundle);
        }

        if (savedInstanceState != null)
        {
            mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab"));
        }

//        mViewPager = (ViewPager) findViewById(R.id.pager);

//        mTabsAdapter = new TabsAdapter(this, mTabHost, mViewPager);
//
//        mTabsAdapter.addTab(mTabHost.newTabSpec("simple").setIndicator("Simple"),
//                            TestFragment.class, null);
//        mTabsAdapter.addTab(mTabHost.newTabSpec("simple2").setIndicator("Simple2"),
//                            TestFragment.class, null);
    }

    private TabHost.TabSpec createIconTab(int position)
    {
        Bundle bundle = new Bundle();

        View tab = getLayoutInflater().inflate(R.layout.tab_layout, null);

        switch (position)
        {
            case 1:
                bundle.putInt("num", 1);
                bundle.putInt("color", Color.WHITE);
                tab.findViewById(R.id.tab_icon).setBackgroundResource(R.drawable.tab_leaderboard_selector);
                ((TextView) tab.findViewById(R.id.tab_text)).setText("Leaderboard");
                return mTabHost.newTabSpec("simple_1").setIndicator(tab);
            case 2:
                bundle.putInt("num", 2);
                bundle.putInt("color", Color.BLUE);
                tab.findViewById(R.id.tab_icon).setBackgroundResource(R.drawable.tab_notification_selector);
                ((TextView) tab.findViewById(R.id.tab_text)).setText("Notification");
                return mTabHost.newTabSpec("simple_2").setIndicator(tab);
            case 3:
                bundle.putInt("num", 3);
                bundle.putInt("color", Color.CYAN);
                tab.findViewById(R.id.tab_icon).setBackgroundResource(R.drawable.tab_market_selector);
                ((TextView) tab.findViewById(R.id.tab_text)).setText("Market");
                return mTabHost.newTabSpec("simple_3").setIndicator(tab);
            case 4:
                bundle.putInt("num", 4);
                bundle.putInt("color", Color.DKGRAY);
                tab.findViewById(R.id.tab_icon).setBackgroundResource(R.drawable.tab_social_selector);
                ((TextView) tab.findViewById(R.id.tab_text)).setText("Social");
                return mTabHost.newTabSpec("simple_4").setIndicator(tab);
            case 5:
                bundle.putInt("num", 5);
                bundle.putInt("color", Color.GREEN);
                tab.findViewById(R.id.tab_icon).setBackgroundResource(R.drawable.tab_account_selector);
                ((TextView) tab.findViewById(R.id.tab_text)).setText("Account");
                return mTabHost.newTabSpec("simple_5").setIndicator(tab);
        }

        return mTabHost.newTabSpec("null").setIndicator(tab);
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

    //    public static class TabsAdapter extends FragmentPagerAdapter
//                                    implements TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {
//        private final Context mContext;
//        private final TabHost mTabHost;
//        private final ViewPager mViewPager;
//        private final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();
//
//        static final class TabInfo {
//            private final String tag;
//            private final Class<?> clss;
//            private final Bundle args;
//
//            TabInfo(String _tag, Class<?> _class, Bundle _args) {
//                tag = _tag;
//                clss = _class;
//                args = _args;
//            }
//        }
//
//        static class DummyTabFactory implements TabHost.TabContentFactory {
//            private final Context mContext;
//
//            private DummyTabFactory(Context context) {
//                mContext = context;
//            }
//
//            @Override
//            public View createTabContent(String tag) {
//                View v = new View(mContext);
//                v.setMinimumWidth(0);
//                v.setMinimumHeight(0);
//                return v;
//            }
//        }
//
//        public TabsAdapter(FragmentActivity activity, TabHost tabHost, ViewPager pager) {
//            super(activity.getSupportFragmentManager());
//            mContext = activity;
//            mTabHost = tabHost;
//            mViewPager = pager;
//            mTabHost.setOnTabChangedListener(this);
//            mViewPager.setAdapter(this);
//            mViewPager.setOnPageChangeListener(this);
//        }
//
//        public void addTab(TabHost.TabSpec tabSpec, Class<?> clss, Bundle args) {
//            tabSpec.setContent(new DummyTabFactory(mContext));
//            String tag = tabSpec.getTag();
//
//            TabInfo info = new TabInfo(tag, clss, args);
//            mTabs.add(info);
//            mTabHost.addTab(tabSpec);
//            notifyDataSetChanged();
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            TabInfo info = mTabs.get(position);
//            return Fragment.instantiate(mContext, info.clss.getName(), info.args);
//        }
//
//        @Override
//        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//        }
//
//        @Override
//        public void onPageSelected(int position) {
//            TabWidget widget = mTabHost.getTabWidget();
//            int oldFocusability = widget.getDescendantFocusability();
//            widget.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
//            mTabHost.setCurrentTab(position);
//            widget.setDescendantFocusability(oldFocusability);
//        }
//
//        @Override
//        public void onPageScrollStateChanged(int state) {
//        }
//
//        @Override
//        public void onTabChanged(String tabId) {
//            int position = mTabHost.getCurrentTab();
//            mViewPager.setCurrentItem(position);
//        }
//
//        @Override
//        public int getCount() {
//            return mTabs.size();
//        }
//    }
}
