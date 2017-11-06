package com.savor.operation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.savor.operation.R;
import com.savor.operation.adapter.FixTaskListAdapter;
import com.savor.operation.bean.FixTask;
import com.savor.operation.bean.Hotel;
import com.savor.operation.enums.SearchHotelOpType;

import java.util.ArrayList;
import java.util.List;

/**
 * 任务详情页面（公共页面）
 */
public class TaskActivity extends BaseActivity implements View.OnClickListener {

    public static final int REQUEST_CODE_SEARCH = 0x1;
    public static final int RESULT_CODE_SEARCH = 0x2;
    private ListView mTaskLv;
    private View mHeadView;
    private ImageView mBackBtn;
    private PublishTaskActivity.TaskType actionType;
    private RelativeLayout mNumLayout;
    private TextView mAddTv;
    private TextView mReduceTv;
    private TextView mBoxNumTv;
    private RelativeLayout mSearchLayout;
    private Hotel hotel;
    private TextView mHotelNameTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        handleIntent();
        getViews();
        setViews();
        setListeners();
    }

    private void handleIntent() {
        Intent intent = getIntent();
        actionType = (PublishTaskActivity.TaskType) intent.getSerializableExtra("type");
    }

    @Override
    public void getViews() {
        mBackBtn = (ImageView) findViewById(R.id.iv_left);
        mTaskLv = (ListView) findViewById(R.id.lv_task_list);

        initHeaderView();

    }

    private void initHeaderView() {
        mHeadView = View.inflate(this,R.layout.header_view_task,null);
        mNumLayout = (RelativeLayout) mHeadView.findViewById(R.id.rl_num);
        mAddTv = (TextView) mHeadView.findViewById(R.id.tv_add);
        mReduceTv = (TextView) mHeadView.findViewById(R.id.tv_reduce);
        mBoxNumTv = (TextView) mHeadView.findViewById(R.id.tv_box_num);
        mHotelNameTv = (TextView) mHeadView.findViewById(R.id.tv_select_hotel);
        mSearchLayout = (RelativeLayout) mHeadView.findViewById(R.id.rl_select_hotel);

        if(actionType == PublishTaskActivity.TaskType.INFO_CHECK||actionType == PublishTaskActivity.TaskType.NETWORK_REMOULD) {
            mNumLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void setViews() {
        List<FixTask> list = new ArrayList<>();
        for(int i = 0;i<10;i++) {
            FixTask fixTask = new FixTask();
            fixTask.setBoxName("V00"+i);
            fixTask.setExceptioinDes("HDMI线损坏");
            fixTask.setUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1509690252876&di=34072403ccc622dbd89479c144c95b63&imgtype=0&src=http%3A%2F%2Fpic.58pic.com%2F58pic%2F13%2F56%2F04%2F69758PICVPU_1024.jpg");
            list.add(fixTask);
        }


        FixTaskListAdapter mTaskAdapter = new FixTaskListAdapter(this);
        mTaskLv.setAdapter(mTaskAdapter);
        mTaskLv.addHeaderView(mHeadView);
        if(actionType == PublishTaskActivity.TaskType.FIX) {
            mTaskAdapter.setData(list);
        }
    }

    @Override
    public void setListeners() {
        mBackBtn.setOnClickListener(this);
        mAddTv.setOnClickListener(this);
        mReduceTv.setOnClickListener(this);
        mSearchLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String num;
        Intent intent;
        switch (v.getId()) {
            case R.id.rl_select_hotel:
                intent = new Intent(this,SearchActivity.class);
                intent.putExtra("type", SearchHotelOpType.PUBLIS_TASK);
                startActivityForResult(intent,REQUEST_CODE_SEARCH);
                break;
            case R.id.tv_add:
                num = mBoxNumTv.getText().toString();
                try{
                    int boxNum = Integer.valueOf(num);
                    boxNum+=1;
                    mBoxNumTv.setText(String.valueOf(boxNum));
                }catch (Exception e){
                    mBoxNumTv.setText("0");
                }
                break;
            case R.id.tv_reduce:
                num = mBoxNumTv.getText().toString();
                try{
                    int boxNum = Integer.valueOf(num);
                    boxNum-=1;
                    if(boxNum<0) {
                        boxNum = 0;
                    }
                    mBoxNumTv.setText(String.valueOf(boxNum));
                }catch (Exception e){
                    mBoxNumTv.setText("0");
                }
                break;
            case R.id.iv_left:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_SEARCH &&resultCode == RESULT_CODE_SEARCH) {
            if(data!=null) {
                hotel = (Hotel) data.getSerializableExtra("hotel");
                if(hotel!=null) {
                    String name = hotel.getName();
                    if(!TextUtils.isEmpty(name)) {
                        mHotelNameTv.setText(name);
                    }
                }
            }
        }
    }
}
