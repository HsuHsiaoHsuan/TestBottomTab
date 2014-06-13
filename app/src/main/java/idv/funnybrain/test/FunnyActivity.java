package idv.funnybrain.test;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TabHost;

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

        Bundle bundle = new Bundle();
        bundle.putInt("num", 1);
        bundle.putInt("color", Color.WHITE);
        mTabManager.addTab(mTabHost.newTabSpec("simple_1").setIndicator("SIMPLE_1"), TestFragment.class, bundle);

        Bundle bundle_2 = new Bundle();
        bundle_2.putInt("num", 2);
        bundle_2.putInt("color", Color.BLUE);
        mTabManager.addTab(mTabHost.newTabSpec("simple_2").setIndicator("SIMPLE_2"), TestFragment.class, bundle_2);

        Bundle bundle_3 = new Bundle();
        bundle_3.putInt("num", 3);
        bundle_3.putInt("color", Color.CYAN);
        mTabManager.addTab(mTabHost.newTabSpec("simple_3").setIndicator("SIMPLE_3"), TestFragment.class, bundle_3);

        Bundle bundle_4 = new Bundle();
        bundle_4.putInt("num", 4);
        bundle_4.putInt("color", Color.DKGRAY);
        mTabManager.addTab(mTabHost.newTabSpec("simple_4").setIndicator("SIMPLE_4"), TestFragment.class, bundle_4);

        Bundle bundle_5 = new Bundle();
        bundle_5.putInt("num", 5);
        bundle_5.putInt("color", Color.GREEN);
        mTabManager.addTab(mTabHost.newTabSpec("simple_5").setIndicator("SIMPLE_5"), TestFragment.class, bundle_5);

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
