package com.savor.operation.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.savor.operation.R;

/**
 * 系统状态
 * @author hezd
 */
public class SystemStatusFragment extends Fragment {
    private static final String ARG_CITY_ID = "params_city_id";

    private String cityId;


    public SystemStatusFragment() {
        // Required empty public constructor
    }

    public static SystemStatusFragment newInstance(String cityId) {
        SystemStatusFragment fragment = new SystemStatusFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CITY_ID, cityId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cityId = getArguments().getString(ARG_CITY_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_system_status, container, false);
    }

}
