package com.guoxiaoxing.kitty.ui.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import com.guoxiaoxing.kitty.AppConfig;
import com.guoxiaoxing.kitty.R;
import com.guoxiaoxing.kitty.adapter.MainShoppingAdapter;
import com.guoxiaoxing.kitty.bean.SimpleBackPage;
import com.guoxiaoxing.kitty.tmp.SampleDataboxset;
import com.guoxiaoxing.kitty.ui.base.BaseFragment;
import com.guoxiaoxing.kitty.util.UIHelper;
import com.guoxiaoxing.kitty.util.log.Logger;
import com.guoxiaoxing.kitty.widget.banner.ConvenientBanner;
import com.guoxiaoxing.kitty.widget.banner.holder.CBViewHolderCreator;
import com.guoxiaoxing.kitty.widget.banner.holder.LocalImageHolderView;
import com.guoxiaoxing.kitty.widget.banner.listener.OnItemClickListener;
import com.guoxiaoxing.kitty.widget.banner.transforms.CubeOutTransformer;
import com.guoxiaoxing.kitty.widget.timecounter.CountdownView;
import com.guoxiaoxingv.smartrecyclerview.ItemTouchListenerAdapter;
import com.guoxiaoxingv.smartrecyclerview.ObservableScrollState;
import com.guoxiaoxingv.smartrecyclerview.ObservableScrollViewCallbacks;
import com.guoxiaoxingv.smartrecyclerview.UltimateRecyclerView;
import com.guoxiaoxingv.smartrecyclerview.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;
import com.guoxiaoxingv.smartrecyclerview.uiUtils.ScrollSmoothLineaerLayoutManager;

import java.lang.reflect.Field;
import java.util.ArrayList;

import butterknife.Bind;


public class MainShoppingFragment extends BaseFragment implements AdapterView.OnItemClickListener,
        ViewPager.OnPageChangeListener, OnItemClickListener {

    public static final String TAG = "MainShoppingFragment";

    @Bind(R.id.tb_home_fragment)
    Toolbar mToolbar;
    @Bind(R.id.tv_scan)
    TextView mTvScan;
    @Bind(R.id.tv_notification)
    TextView mTvNotification;
    @Bind(R.id.et_search)
    EditText mEtSearch;
    @Bind(R.id.url_home_content)
    UltimateRecyclerView mUrlHomeContent;
    ConvenientBanner mCbHomeAd;
    ConvenientBanner mCbSaleAd;
    CountdownView mCvSale;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Context mContext;
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    MainShoppingAdapter simpleRecyclerViewAdapter = null;
    LinearLayoutManager linearLayoutManager;
    int moreNum = 2;
    boolean isDrag = true;

    private ArrayList<Integer> localImages = new ArrayList<Integer>();
    private View headerView;

    public MainShoppingFragment() {
    }

    public static MainShoppingFragment newInstance(String param1, String param2) {
        MainShoppingFragment fragment = new MainShoppingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.t(TAG).d("onCreate()");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Logger.t(TAG).d("onAttach()");
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Logger.t(TAG).d("onViewCreated()");

    }

    @Override
    public void onResume() {
        super.onResume();
        Logger.t(TAG).d("onResume()");
        mCbHomeAd.startTurning(AppConfig.VIEWPAGER_TRANSFORM_TIME);
        mCbSaleAd.startTurning(AppConfig.VIEWPAGER_TRANSFORM_TIME);
        long time2 = (long) 30 * 60 * 1000;
        mCvSale.start(time2);
    }

    @Override
    public void onPause() {
        super.onPause();
        Logger.t(TAG).d("onPause()");
        mCbHomeAd.stopTurning();
        mCbSaleAd.stopTurning();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Logger.t(TAG).d("onDestroyView()");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Logger.t(TAG).d("onDetach()");
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onItemClick(int position) {

    }


    @Override
    public void onClick(View v) {

        super.onClick(v);

        switch (v.getId()) {
            //消息通知
            case R.id.tv_notification:
                UIHelper.showSimpleBack(getActivity(), SimpleBackPage.SEARCH);
                break;
            //搜索框
            case R.id.et_search:
                UIHelper.showSimpleBack(getActivity(), SimpleBackPage.SEARCH);
                break;
            case R.id.tv_scan:
                UIHelper.showScanActivity(getActivity());
                break;
            default:
                break;

        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main_shopping;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
    }

    @Override
    public void initView(View view) {
        Logger.t(TAG).d("onCreateView() -- initView()");
        initToolbar();
        initContentView();
    }

    @Override
    public void initData() {
        super.initData();
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onHomeFragmentInteraction(uri);
        }
    }

    public interface OnFragmentInteractionListener {
        void onHomeFragmentInteraction(Uri uri);
    }

    public int getScreenHeight() {
        return getActivity().findViewById(android.R.id.content).getHeight();
    }

    public static int getResId(String variableName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(variableName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    private void initToolbar() {
        mContext = getActivity();
        mEtSearch.setFocusable(false);
        mTvScan.setOnClickListener(this);
        mTvNotification.setOnClickListener(this);
        mEtSearch.setOnClickListener(this);
    }

    private void initHeaderView() {

        //设置HeaderView
        headerView = LayoutInflater.from(mContext).inflate(R.layout.common_recyclerview_header, mUrlHomeContent.mRecyclerView, false);
        mCbHomeAd = (ConvenientBanner) headerView.findViewById(R.id.cb_home_ad);
        mCvSale = (CountdownView) headerView.findViewById(R.id.cv_sale);


        for (int position = 0; position < 7; position++) {
            localImages.add(getResId("ic_test_" + position, R.drawable.class));
        }

        //本地图片例子
        mCbHomeAd.setPages(
                new CBViewHolderCreator<LocalImageHolderView>() {
                    @Override
                    public LocalImageHolderView createHolder() {
                        return new LocalImageHolderView();
                    }
                }, localImages)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})
//                .setOnPageChangeListener(this)//监听翻页事件
                .setOnItemClickListener(this);

//        mCbHomeAd.setManualPageable(false);//设置不能手动影响

        mCbSaleAd = (ConvenientBanner) headerView.findViewById(R.id.cb_sale_ad);
        mCbSaleAd.getViewPager().setPageTransformer(true, new CubeOutTransformer());
        //本地图片例子
        mCbSaleAd.setPages(
                new CBViewHolderCreator<LocalImageHolderView>() {
                    @Override
                    public LocalImageHolderView createHolder() {
                        return new LocalImageHolderView();
                    }
                }, localImages)
//                .setOnPageChangeListener(this)//监听翻页事件
                .setOnItemClickListener(this);

//        mCbHomeAd.setManualPageable(false);//设置不能手动影响


    }

    private void initContentView() {

        mUrlHomeContent.setHasFixedSize(false);
        simpleRecyclerViewAdapter = new MainShoppingAdapter(SampleDataboxset.newList());
        linearLayoutManager = new ScrollSmoothLineaerLayoutManager(mContext, LinearLayoutManager.VERTICAL, false, 300);
        mUrlHomeContent.setLayoutManager(linearLayoutManager);
        mUrlHomeContent.setAdapter(simpleRecyclerViewAdapter);

        //加载更多
        mUrlHomeContent.enableLoadmore();
        simpleRecyclerViewAdapter.setCustomLoadMoreView(LayoutInflater.from(mContext)
                .inflate(R.layout.custom_bottom_progressbar, null, false));

        //添加Header View
        initHeaderView();
        mUrlHomeContent.setNormalHeader(headerView);
        mUrlHomeContent.setOnParallaxScroll(new UltimateRecyclerView.OnParallaxScroll() {
            @Override
            public void onParallaxScroll(float percentage, float offset, View parallax) {
//                Drawable drawable = mToolbar.getBackground();
//                drawable.setAlpha(Math.round(127 + percentage * 128));
//                mToolbar.setBackground(drawable);
            }
        });

        //下拉刷新数据
        mUrlHomeContent.setDefaultOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        simpleRecyclerViewAdapter.insert(moreNum++ + "  Refresh things", 0);
                        mUrlHomeContent.setRefreshing(false);
                        //   ultimateRecyclerView.scrollBy(0, -50);
                        linearLayoutManager.scrollToPosition(0);
//                        ultimateRecyclerView.setAdapter(simpleRecyclerViewAdapter);
//                        simpleRecyclerViewAdapter.notifyDataSetChanged();
                    }
                }, 1000);
            }
        });
        //上拉加载更多
        mUrlHomeContent.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore(int itemsCount, final int maxLastVisiblePosition) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        SampleDataboxset.insertMoreBuy(simpleRecyclerViewAdapter, 10);
                        //  linearLayoutManager.scrollToPositionWithOffset(maxLastVisiblePosition, -1);
                        //  linearLayoutManager.scrollToPosition(maxLastVisiblePosition);
                    }
                }, 2500);
            }
        });

        //滑动监听，可以根据不同的状态来显示和隐藏Toolbar和Floating button
        mUrlHomeContent.setScrollViewCallbacks(new ObservableScrollViewCallbacks() {
            @Override
            public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {

                Logger.t(TAG).d("onDownMotionEvent" + dragging);
            }

            @Override
            public void onDownMotionEvent() {
                Logger.t(TAG).d("onDownMotionEvent");
            }

            @Override
            public void onUpOrCancelMotionEvent(ObservableScrollState observableScrollState) {
                Logger.t(TAG).d("onUpOrCancelMotionEvent");
                if (observableScrollState == ObservableScrollState.UP) {
                    mUrlHomeContent.hideToolbar(mToolbar, mUrlHomeContent, getScreenHeight());
                    mUrlHomeContent.hideFloatingActionMenu();
                } else if (observableScrollState == ObservableScrollState.DOWN) {
                    mUrlHomeContent.showToolbar(mToolbar, mUrlHomeContent, getScreenHeight());
                    mUrlHomeContent.showFloatingActionMenu();
                }
            }
        });

//        mUrlHomeContent.addOnItemTouchListener(new SwipeableRecyclerViewTouchListener(mUrlHomeContent.mRecyclerView,
//                new SwipeableRecyclerViewTouchListener.SwipeListener() {
//                    @Override
//                    public boolean canSwipe(int position) {
//                        if (position > 0)
//                            return true;
//                        else return false;
//                    }
//
//                    @Override
//                    public void onDismissedBySwipeLeft(RecyclerView recyclerView, int[] reverseSortedPositions) {
//
//                    }
//
//                    @Override
//                    public void onDismissedBySwipeRight(RecyclerView recyclerView, int[] reverseSortedPositions) {
//                    }
//                }));


        ItemTouchListenerAdapter itemTouchListenerAdapter = new ItemTouchListenerAdapter(mUrlHomeContent.mRecyclerView,
                new ItemTouchListenerAdapter.RecyclerViewOnItemClickListener() {
                    @Override
                    public void onItemClick(RecyclerView parent, View clickedView, int position) {
                    }

                    @Override
                    public void onItemLongClick(RecyclerView parent, View clickedView, int position) {
                        if (isDrag) {

                        }

                    }
                });
        mUrlHomeContent.mRecyclerView.addOnItemTouchListener(itemTouchListenerAdapter);

        StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(simpleRecyclerViewAdapter);
        mUrlHomeContent.addItemDecoration(headersDecor);
    }

}