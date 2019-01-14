package com.hbdiye.newlechuangsmart.hwactivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.activity.HwBaseActivity;
import com.hbdiye.newlechuangsmart.adapter.SelectSetTopBoxAdapter;
import com.hbdiye.newlechuangsmart.bean.LocationBean;
import com.hbdiye.newlechuangsmart.permission.PermissionListener;
import com.hbdiye.newlechuangsmart.permission.PermissionUtil;
import com.hbdiye.newlechuangsmart.view.TipsDialog;
import com.xzcysoft.wnzhikonglibrary.WNZKConfigure;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

public class SelectSetTopBoxActivity extends HwBaseActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private LocationManager locationManager;
    private StringBuilder stringBuilder = new StringBuilder();
    private String[] PERMISSIONS_STORAGE = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};
    private static final int REQUEST_EXTERNAL_STORAGE = 1000;
    private List<LocationBean.SpList> mSpList = new ArrayList<>();
    private SelectSetTopBoxAdapter selectSetTopBoxAdapter;

    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_set_top_box);
        ButterKnife.bind(this);
        mLocationClient = new LocationClient(getApplicationContext());
        //声明LocationClient类
        LocationClientOption option = new LocationClientOption();

        option.setIsNeedAddress(true);
        //可选，是否需要地址信息，默认为不需要，即参数为false
        //如果开发者需要获得当前点的地址信息，此处必须为true

        mLocationClient.setLocOption(option);
        mLocationClient.registerLocationListener(myListener);
        //注册监听函数
        mLocationClient.start();
        initView();
//        initData();
    }

    private void initData() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            getPermissions();
            return;
        }
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
//       locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

    }

    private void initView() {
        setTitle("运营商");
        recyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        selectSetTopBoxAdapter = new SelectSetTopBoxAdapter(mSpList);
        recyclerView.setAdapter(selectSetTopBoxAdapter);

        selectSetTopBoxAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (position == 0) {
                    Intent intent = new Intent(getApplicationContext(), AddRemoteControlActivity.class);
                    intent.putExtra("hwbDeviceId", getIntent().getStringExtra("hwbDeviceId"));
                    intent.putExtra("deviceName", getIntent().getStringExtra("deviceName"));
                    intent.putExtra("deviceId", getIntent().getStringExtra("deviceId"));
                    intent.putExtra("deviceUrl", getIntent().getIntExtra("deviceUrl", 0));
                    intent.putExtra("spId", mSpList.get(position).spId + "");
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getApplicationContext(), ChooseBrandActivity.class);
                    intent.putExtra("hwbDeviceId", getIntent().getStringExtra("hwbDeviceId"));
                    intent.putExtra("deviceName", getIntent().getStringExtra("deviceName"));
                    intent.putExtra("deviceId", getIntent().getStringExtra("deviceId"));
                    intent.putExtra("deviceUrl", getIntent().getIntExtra("deviceUrl", 0));
                    intent.putExtra("spId", mSpList.get(position).spId + "");
                    startActivity(intent);
                }
            }
        });
    }

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {

            stringBuilder.append("\t").append(location.getLatitude()).append("\n");
            stringBuilder.append("\t").append(location.getLongitude()).append("\n");
            stringBuilder.append("\t").append(location.getProvider()).append("\n");

            Geocoder gc = new Geocoder(getApplicationContext(), Locale.getDefault());
            List<Address> locationList = null;
            try {
                locationList = gc.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Address address = locationList.get(0);//得到Address实例

            if (address == null) {
                return;
            }

            String adminArea = address.getAdminArea();//省
            if (!TextUtils.isEmpty(adminArea)) {
                stringBuilder.append(adminArea);
            }

            String locality = address.getLocality();//得到城市名称
            if (!TextUtils.isEmpty(locality)) {
                stringBuilder.append(locality);
            }

            Log.e("成功", stringBuilder.toString());
            WNZKConfigure.findSpListByProvince(address.getAdminArea(), new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {

                }

                @Override
                public void onResponse(String response, int id) {
                    LocationBean locationBean = new Gson().fromJson(response, LocationBean.class);
                    if (getResultCode(locationBean.errcode)) {
                        List<LocationBean.SpList> spList = locationBean.spList;
                        mSpList.addAll(spList);
                        selectSetTopBoxAdapter.notifyDataSetChanged();

                        locationManager.removeUpdates(locationListener);
                    }
                }
            });

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    private void getPermissions() {
        PermissionUtil permissionUtil = new PermissionUtil(SelectSetTopBoxActivity.this);
        permissionUtil.requestPermissions(PERMISSIONS_STORAGE,
                new PermissionListener() {
                    @Override
                    public void onGranted() {
                        //所有权限都已经授权
                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(),
                                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
//                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                    }

                    @Override
                    public void onDenied(List<String> deniedPermission) {
                        //Toast第一个被拒绝的权限
                        getPermissions();

                    }

                    @Override
                    public void onShouldShowRationale(final List<String> deniedPermission) {
                        //Toast第一个勾选不在提示的权限
                        TipsDialog tipsDialog = new TipsDialog(SelectSetTopBoxActivity.this);
                        tipsDialog.setContent("需要使用您手机基本访问权限,否则将会影响基本使用");
                        tipsDialog.setOnConfrimlickListener("确定", new TipsDialog.OnConfirmClickListener() {
                            @Override
                            public void onConfirmClick() {
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package", getPackageName(), null);
                                intent.setData(uri);
                                startActivityForResult(intent, REQUEST_EXTERNAL_STORAGE);
                            }

                        });
                        tipsDialog.show();

                    }

                });

    }

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location){
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取地址相关的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明

            String addr = location.getAddrStr();    //获取详细地址信息
            String country = location.getCountry();    //获取国家
            String province = location.getProvince();    //获取省份
            String city = location.getCity();    //获取城市
            String district = location.getDistrict();    //获取区县
            String street = location.getStreet();    //获取街道信息
            Log.e("baidu",addr+province+city);
            WNZKConfigure.findSpListByProvince(city, new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {

                }

                @Override
                public void onResponse(String response, int id) {
                    LocationBean locationBean = new Gson().fromJson(response, LocationBean.class);
                    if (getResultCode(locationBean.errcode)) {
                        List<LocationBean.SpList> spList = locationBean.spList;
                        if (mSpList.size()>0){
                            mSpList.clear();
                        }
                        mSpList.addAll(spList);
                        selectSetTopBoxAdapter.notifyDataSetChanged();

//                        locationManager.removeUpdates(locationListener);
                    }
                }
            });
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_EXTERNAL_STORAGE) {
            initData();
        }
    }
}
