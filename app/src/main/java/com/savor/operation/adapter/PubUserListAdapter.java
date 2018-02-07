package com.savor.operation.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.savor.operation.R;
import com.savor.operation.bean.City;
import com.savor.operation.bean.PubUserBean;
import com.savor.operation.core.Session;

import java.util.List;

import static com.savor.operation.activity.CityListActivity.RESULT_CODE_CITY;
import static com.savor.operation.activity.MyHotelActivity.RESULT_CODE_USER;

/**
 * 城市选择适配器
 * Created by bushlee on 2017/11/6.
 */

public class PubUserListAdapter extends RecyclerView.Adapter<PubUserListAdapter.CityHolder> {

    private final Activity mContext;
    private List<PubUserBean> mCityList;
    private Session mSession;

    public PubUserListAdapter(Activity context) {
        this.mContext = context;
    }

    public void setData(List<PubUserBean> cities,Session session) {
        this.mSession = session;
        this.mCityList = cities;
    }

    @Override
    public CityHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CityHolder(LayoutInflater.from(mContext).inflate(R.layout.item_city_list,parent,false)) ;
    }

    @Override
    public void onBindViewHolder(CityHolder holder, int position) {
        final PubUserBean city = mCityList.get(position);
        String region_name = city.getRemark();
        holder.mCity.setText(region_name);

        holder.rl_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(PubUserBean cty: mCityList) {
                    cty.setSelect(false);
                }
                city.setSelect(true);
                mSession.setPubUserBean(city);
                mContext.setResult(RESULT_CODE_USER);
                mContext.finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCityList==null? 0:mCityList.size();
    }

    public class CityHolder extends RecyclerView.ViewHolder {
        public TextView mCity;
        public RelativeLayout rl_parent;
        public CityHolder(View itemView) {
            super(itemView);
            mCity = (TextView) itemView.findViewById(R.id.tv_city);
            rl_parent = (RelativeLayout) itemView.findViewById(R.id.rl_parent);
        }
    }
}
