package com.hbdiye.newlechuangsmart.activity;

import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.adapter.DragAdapter;
import com.hbdiye.newlechuangsmart.adapter.OtherAdapter;
import com.hbdiye.newlechuangsmart.bean.ChannelItem;
import com.hbdiye.newlechuangsmart.view.DragGrid;
import com.hbdiye.newlechuangsmart.view.OtherGridView;

import java.util.ArrayList;

public class MoreSceneActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    /** 用户栏目的GRIDVIEW */
    private DragGrid userGridView;
    /** 其它栏目的GRIDVIEW */
    private OtherGridView otherGridView;
    /** 用户栏目对应的适配器，可以拖动 */
    DragAdapter userAdapter;
    /** 其它栏目对应的适配器 */
    OtherAdapter otherAdapter;
    /** 其它栏目列表 */
    ArrayList<ChannelItem> otherChannelList = new ArrayList<ChannelItem>();
    /** 用户栏目列表 */
    ArrayList<ChannelItem> userChannelList = new ArrayList<ChannelItem>();
    /** 是否在移动，由于这边是动画结束后才进行的数据更替，设置这个限制为了避免操作太频繁造成的数据错乱。 */
    boolean isMove = false;
    private TextView tv_save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_scene);
        initView();
        initData();
    }
    /** 初始化数据*/
    private void initData() {

        userChannelList.add(new ChannelItem(1, "头条", 1, 1,0));
        userChannelList.add(new ChannelItem(2, "视频", 2, 1,0));
        userChannelList.add(new ChannelItem(3, "娱乐", 3, 1,0));
        userChannelList.add(new ChannelItem(4, "体育", 4, 1,0));
        userChannelList.add(new ChannelItem(5, "北京", 5, 1,0));
        userChannelList.add(new ChannelItem(6, "新时代", 6, 1,0));
        userChannelList.add(new ChannelItem(7, "网易号", 7, 1,0));
        otherChannelList.add(new ChannelItem(8, "段子", 1, 0,0));
        otherChannelList.add(new ChannelItem(9, "冰雪运动", 2, 0,0));
        otherChannelList.add(new ChannelItem(10, "科技", 3, 0,0));
        otherChannelList.add(new ChannelItem(11, "汽车", 4, 0,0));
        otherChannelList.add(new ChannelItem(12, "轻松一刻", 5, 0,0));
        otherChannelList.add(new ChannelItem(13, "时尚", 6, 0,0));
        otherChannelList.add(new ChannelItem(14, "直播", 7, 0,0));
        otherChannelList.add(new ChannelItem(15, "图片", 8, 0,0));
        otherChannelList.add(new ChannelItem(16, "跟帖", 9, 0,0));
        otherChannelList.add(new ChannelItem(17, "NBA", 10, 0,0));
        otherChannelList.add(new ChannelItem(18, "态度公开课", 11, 0,0));

        userAdapter = new DragAdapter(this, userChannelList,userGridView);
        userGridView.setAdapter(userAdapter);
        otherAdapter = new OtherAdapter(this, otherChannelList);
        otherGridView.setAdapter(this.otherAdapter);
        //设置GRIDVIEW的ITEM的点击监听
        otherGridView.setOnItemClickListener(this);
        userGridView.setOnItemClickListener(this);
        userAdapter.setOnDelecteItemListener(new DragAdapter.OnDelecteItemListener() {
            @Override
            public void onDelete(final int position, View v, ViewGroup parent) {
                if (position != -1) {
                    View view = userGridView.getChildAt(position);
                    final ImageView moveImageView = getView(view);
                    if (moveImageView != null) {
                        TextView newTextView = (TextView) view.findViewById(R.id.text_item);
                        final int[] startLocation = new int[2];
                        newTextView.getLocationInWindow(startLocation);
                        final ChannelItem channel = userAdapter.getItem(position);//获取点击的频道内容
                        otherAdapter.setVisible(false);
                        userAdapter.setIsDeleteing(true);
                        //添加到最后一个
                        otherAdapter.addItem(channel);
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                try {
                                    int[] endLocation = new int[2];
                                    //获取终点的坐标
                                    otherGridView.getChildAt(otherGridView.getLastVisiblePosition()).getLocationInWindow(endLocation);
                                    MoveAnim(moveImageView, startLocation , endLocation, channel,userGridView);
                                    userAdapter.setRemove(position);
                                } catch (Exception localException) {
                                }
                            }
                        }, 50L);
                    }
                }
            }
        });

        userAdapter.setOnStartDragingListener(new DragAdapter.OnStartDragingListener() {
            @Override
            public void onStartDraging() {
                tv_save.setVisibility(View.VISIBLE);

                otherAdapter.showDelIcon(true);

            }
        });
    }

    /** 初始化布局*/
    private void initView() {
        userGridView = (DragGrid) findViewById(R.id.userGridView);
        otherGridView = (OtherGridView) findViewById(R.id.otherGridView);
        tv_save = (TextView) findViewById(R.id.tv_save);
        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userAdapter.hideDeleteIcon(true);
                userAdapter.showDeleteIcon(false);
                userAdapter.notifyDataSetChanged();
                tv_save.setVisibility(View.GONE);
                otherAdapter.showDelIcon(false);
            }
        });
    }

    /** GRIDVIEW对应的ITEM点击监听接口  */
    @Override
    public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {
        //如果点击的时候，之前动画还没结束，那么就让点击事件无效
        if(isMove){
            return;
        }
        switch (parent.getId()) {
            case R.id.userGridView:
                //TODO position为 0的不可以进行任何操作
//            Toast.makeText(getBaseContext(),"item点击",Toast.LENGTH_SHORT).show();
                if (position > -1) {
                    final ImageView moveImageView = getView(view);
                    if (moveImageView != null) {
                        TextView newTextView = (TextView) view.findViewById(R.id.text_item);
                        final int[] startLocation = new int[2];
                        newTextView.getLocationInWindow(startLocation);
                        final ChannelItem channel = ((DragAdapter) parent.getAdapter()).getItem(position);//获取点击的频道内容
                        otherAdapter.setVisible(false);
                        //添加到最后一个
                        otherAdapter.addItem(channel);
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                try {
                                    int[] endLocation = new int[2];
                                    //获取终点的坐标
                                    otherGridView.getChildAt(otherGridView.getLastVisiblePosition()).getLocationInWindow(endLocation);
                                    MoveAnim(moveImageView, startLocation , endLocation, channel,userGridView);
                                    userAdapter.setRemove(position);
                                } catch (Exception localException) {
                                }
                            }
                        }, 50L);
                    }
                }
                break;
            case R.id.otherGridView:
                final ImageView moveImageView = getView(view);
                if (moveImageView != null){
                    TextView newTextView = (TextView) view.findViewById(R.id.text_item);
                    final int[] startLocation = new int[2];
                    newTextView.getLocationInWindow(startLocation);
                    final ChannelItem channel = ((OtherAdapter) parent.getAdapter()).getItem(position);
                    userAdapter.setVisible(false);
                    //添加到最后一个
                    channel.setNewItem(1);
                    userAdapter.addItem(channel);
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            try {
                                int[] endLocation = new int[2];
                                //获取终点的坐标
                                userGridView.getChildAt(userGridView.getLastVisiblePosition()).getLocationInWindow(endLocation);
                                MoveAnim(moveImageView, startLocation , endLocation, channel,otherGridView);
                                otherAdapter.setRemove(position);
                            } catch (Exception localException) {
                            }
                        }
                    }, 50L);
                }
                break;
            default:
                break;
        }
    }
    /**
     * 点击ITEM移动动画
     * @param moveView
     * @param startLocation
     * @param endLocation
     * @param moveChannel
     * @param clickGridView
     */
    private void MoveAnim(View moveView, int[] startLocation,int[] endLocation, final ChannelItem moveChannel,
                          final GridView clickGridView) {
        int[] initLocation = new int[2];
        //获取传递过来的VIEW的坐标
        moveView.getLocationInWindow(initLocation);
        //得到要移动的VIEW,并放入对应的容器中
        final ViewGroup moveViewGroup = getMoveViewGroup();
        final View mMoveView = getMoveView(moveViewGroup, moveView, initLocation);
        //创建移动动画
        TranslateAnimation moveAnimation = new TranslateAnimation(
                startLocation[0], endLocation[0], startLocation[1],
                endLocation[1]);
        moveAnimation.setDuration(300L);//动画时间
        //动画配置
        AnimationSet moveAnimationSet = new AnimationSet(true);
        moveAnimationSet.setFillAfter(false);//动画效果执行完毕后，View对象不保留在终止的位置
        moveAnimationSet.addAnimation(moveAnimation);
        mMoveView.startAnimation(moveAnimationSet);
        moveAnimationSet.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                isMove = true;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                moveViewGroup.removeView(mMoveView);
                // instanceof 方法判断2边实例是不是一样，判断点击的是DragGrid还是OtherGridView
                if (clickGridView instanceof DragGrid) {
                    otherAdapter.setVisible(true);
                    otherAdapter.notifyDataSetChanged();
                    userAdapter.remove();
                }else{
                    userAdapter.setVisible(true);
                    userAdapter.notifyDataSetChanged();
                    otherAdapter.remove();
                }
                isMove = false;
            }
        });
    }

    /**
     * 获取移动的VIEW，放入对应ViewGroup布局容器
     * @param viewGroup
     * @param view
     * @param initLocation
     * @return
     */
    private View getMoveView(ViewGroup viewGroup, View view, int[] initLocation) {
        int x = initLocation[0];
        int y = initLocation[1];
        viewGroup.addView(view);
        LinearLayout.LayoutParams mLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mLayoutParams.leftMargin = x;
        mLayoutParams.topMargin = y;
        view.setLayoutParams(mLayoutParams);
        return view;
    }

    /**
     * 创建移动的ITEM对应的ViewGroup布局容器
     */
    private ViewGroup getMoveViewGroup() {
        ViewGroup moveViewGroup = (ViewGroup) getWindow().getDecorView();
        LinearLayout moveLinearLayout = new LinearLayout(this);
        moveLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        moveViewGroup.addView(moveLinearLayout);
        return moveLinearLayout;
    }

    /**
     * 获取点击的Item的对应View，
     * @param view
     * @return
     */
    private ImageView getView(View view) {
        view.destroyDrawingCache();
        view.setDrawingCacheEnabled(true);
        Bitmap cache = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        ImageView iv = new ImageView(this);
        iv.setImageBitmap(cache);
        return iv;
    }

    /** 退出时候保存选择后数据库的设置  */
    private void saveChannel() {
//		ChannelManage.getManage(AppApplication.getApp().getSQLHelper()).deleteAllChannel();
        ArrayList<ChannelItem> items = new ArrayList<ChannelItem>();
        for (ChannelItem item :userAdapter.getChannnelLst()){
            item.setNewItem(0);
            items.add(item);
        }
//		ChannelManage.getManage(AppApplication.getApp().getSQLHelper()).saveUserChannel(items);
//		ChannelManage.getManage(AppApplication.getApp().getSQLHelper()).saveOtherChannel(otherAdapter.getChannnelLst());
    }

    @Override
    public void onBackPressed() {
        saveChannel();
        super.onBackPressed();
    }
}
