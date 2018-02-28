package com.savor.operation.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.common.api.widget.pulltorefresh.library.PullToRefreshBase;
import com.common.api.widget.pulltorefresh.library.PullToRefreshListView;
import com.savor.operation.R;
import com.savor.operation.adapter.NetworkTestListAdapter;

import java.io.Serializable;

/**
 * 网络测评fragment
 * @author hezd create on 2018?0228
 */
public class NetworkTestFragment extends BaseFragment implements PullToRefreshBase.OnRefreshListener{

    private PullToRefreshListView mNetworkTestPlv;
    private NetworkTestType type;
    private View dividerView;

    /**
     * 网络测评类型
     */
    public enum NetworkTestType implements Serializable{
        /**已完成*/
        TYPE_COMPLETE,
        /**未完成*/
        TYPE_UNCOMPLETE,
    }

    @SuppressWarnings("unused")
    public static NetworkTestFragment newInstance(NetworkTestType type) {
        NetworkTestFragment fragment = new NetworkTestFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("type",type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getViews(view);
        setViews();
        setListeners();
    }

    private void getViews(View view) {
        mNetworkTestPlv = (PullToRefreshListView) view.findViewById(R.id.plv_network_test);
        dividerView = View.inflate(getContext(), R.layout.divider_title,null);
    }

    @Override
    public void setViews() {
        mNetworkTestPlv.getRefreshableView().addHeaderView(dividerView);
        mNetworkTestPlv.setOnRefreshListener(this);

        NetworkTestListAdapter mListAdapter = new NetworkTestListAdapter(getContext());
        mNetworkTestPlv.setAdapter(mListAdapter);
    }

    @Override
    public void setListeners() {

    }

    @Override
    public void onRefresh(PullToRefreshBase refreshView) {

    }
}
