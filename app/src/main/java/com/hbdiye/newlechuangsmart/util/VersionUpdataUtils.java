package com.hbdiye.newlechuangsmart.util;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.bean.VersionBean;
import com.hbdiye.newlechuangsmart.global.InterfaceManager;
import com.hbdiye.newlechuangsmart.view.MyDailog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

import static com.hbdiye.newlechuangsmart.global.InterfaceManager.UPDATEVERSION;

//import org.xutils.common.Callback;
//import android.util.Log;
//import com.zhy.http.okhttp.OkHttpUtils;
//import com.zhy.http.okhttp.callback.FileCallBack;

/**
 * 版本更新工具类
 *
 * @author Administrator
 */
public class VersionUpdataUtils {
    /**
     * 服务器读到的版本更新信息
     */
    private String jsonstr = "";

    private int newVersion;
    /**
     * apk下载路径
     */
    private String apkURL = "";
    /**
     * 手机上的旧版本
     */
    private int localVersion;
    /**
     * 手机上的旧版本name
     */
    private String localVersionName;
    /**
     * apk名称
     */
    private String filename = "lc.apk";
    private String noticeMessage = "";
    private String file_path = Environment.getExternalStorageDirectory() + "/download/";
    //    private String file_path ;
    private Context context;
    /* 是否取消更新 */
    private boolean cancelUpdate = false;
    private ProgressBar mProgress;
    private Dialog mDownloadDialog;
    private Dialog noticeDialog;
    private VersionBean versionBean;
    private int type = 0;
//    private int Compulsory = 0;// 0 不强制  1 强制
//	private Callback.Cancelable cancelable = null;//暂停下载使用
//	private int PoolSize = 3;
//	private final Executor exec = new PriorityExecutor(PoolSize, true);//线程池

    private static String[] PERMISSIONS_STORAGE = {//权限数组
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private Activity activity = null;

    public VersionUpdataUtils(Context context, Activity activity, int ftype) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.activity = activity;
        this.type = ftype;
//		loadingDialogShow=new LoadingDialogShow(context);
        if (!Utils.isNetworkAvailable(context)) {
            SmartToast.show("请打开网络连接");
            return;
        }
//        file_path= context.getCacheDir()+ "/download/";
        requestCheckUpdateJson();
    }
//    /**
//     * 首页实例化更新
//     * */
//    public VersionUpdataUtils(Context context, Activity activity ,Version version,int ftype){
//        this.context = context;
//        this.activity = activity;
//        this.type = ftype;
//        if (!Utils.isNetworkAvailable(context)) {
//            SmartToast.show(context, "请打开网络连接");
//            return;
//        }
//        ceshi111(version);
//    }
//
//    public void ceshi111(Version version){
//        Version.MsgBean msgBean=version.getMsg();
//        newVersion = msgBean.getVersion_code();//后台版本号
//        filename = msgBean.getVersion_name() + ".apk";//软件名字
//        apkURL = msgBean.getDown();//下载路径
//        noticeMessage = msgBean.getLog();//更新内容
//        Compulsory = version.getMsg().getFlag();//是否强制更新
//        int localv = getNowVersion();
//        Message msg = new Message();
//        if (msgBean.getVersion_code() > localv) {
//            msg.obj = apkURL;
//            msg.what = 8001;//开始更新
//        } else {
//            msg.what = 8002;//不用更新
//        }
//        myHandler.sendMessage(msg);
//    }

    /**
     * 弹出提示版本更新
     *
     * @author: wrb
     * @Createtime: 2014年08月31日
     */
    public void alertVersion(final String apkurl, String noticeMsg) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_version, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialog);
        final Dialog noticeDialog = builder.create();
        noticeDialog.show();
        noticeDialog.setCancelable(false);
        noticeDialog.getWindow().setContentView(view);
        TextView tv_confirm = (TextView) view.findViewById(R.id.tv_confirm);
        TextView tv_cancle = (TextView) view.findViewById(R.id.tv_cancle);
        TextView tv_content = (TextView) view.findViewById(R.id.tv_content);
        tv_content.setText(noticeMsg.replace("\\n", "\n").replace("\\r", "\r") + "");
//        if (Compulsory == 1) {
//            tv_cancle.setText("关闭");
//        } else {
        tv_cancle.setText("取消");
//        }
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // 显示下载对话框    判断是否有sd卡的读取权限
                int permission = ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);//sd卡读写权限
                if (permission != PackageManager.PERMISSION_GRANTED) {//没有授权
                    ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);//进行授权
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        boolean haveInstallPermission;
                        haveInstallPermission = context.getPackageManager().canRequestPackageInstalls();
                        if (haveInstallPermission) {//已经授权
//                        进行下载
                            noticeDialog.dismiss();
                            showDownloadDialog(apkurl);
                        } else {
                            openQuanxian();
                        }
                    } else {
                        noticeDialog.dismiss();
                        showDownloadDialog(apkurl);
                    }
                }

            }
        });
        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
//                SafePreference.save(context, "isCloseUp", true);//不出现更新提示框
//                if (Compulsory == 1) {
//                    noticeDialog.dismiss();
//                    exit();
//                } else {
                noticeDialog.dismiss();
//                }
            }
        });
    }

//    private void exit() {
//        try {
//            SafePreference.save(context, "isCloseUp", false);//不出现更新提示框
//            AppManager.getAppManager().finishAllActivity();
//            ActivityManager activityMgr = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
//            activityMgr.killBackgroundProcesses(context.getPackageName());
//            System.exit(0);
//        } catch (Exception e) {
//            System.exit(0);
//        }
//    }

    /**
     * 显示软件下载对话框
     */
    public void showDownloadDialog(String apkurl) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        View view = inflater.inflate(R.layout.softupdate_progress, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        noticeDialog = builder.create();
        noticeDialog.setCancelable(false);
        try {
            noticeDialog.show();
            // 记得activity记得改变一下
            WindowManager m = ((Activity) activity).getWindowManager();
            Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
            // 以下代码设置对话框 的布局参数
            WindowManager.LayoutParams params = noticeDialog.getWindow()
                    .getAttributes();
            params.gravity = Gravity.CENTER;
            // 设置dialog的宽和高
            params.width = (int) (d.getWidth() * 0.6); // 宽度设置为屏幕的0.6
            params.height = (int) (d.getHeight() * 0.3); // 宽度设置为屏幕的0.6
            // 设置对话框的布局参数为居中
            noticeDialog.getWindow().setAttributes(params);
            // 参数true指出此对话框类似于非模态对话框
            // wordDialog.setCancelable(false);
            // 设置对话框对象所使用的布局
            noticeDialog.getWindow().setContentView(view);

        } catch (Exception e) {
            e.printStackTrace();
        }
        mProgress = (ProgressBar) view.findViewById(R.id.update_progress);
        mProgress.setMax(100);
        TextView tv_cancle = (TextView) view.findViewById(R.id.tv_cancle);
        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                noticeDialog.dismiss();
                // 设置取消状态
                cancelUpdate = true;
//                if (type==2 && Compulsory==1){//首页过来的强制更新
//                    exit();
//                }
            }
        });
        downFile(apkurl);
    }

    /**
     * 下载最新apk
     *
     * @author: 位瑞彬
     * @Createtime: 2014年08月31日
     */
    @SuppressWarnings("unchecked")
    public void downFile(final String apkurl) {
        OkHttpUtils
                .get()
                .url(apkurl)
                .build()
                .execute(new FileCallBack(file_path, filename) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        noticeDialog.dismiss();
                        SmartToast.show("版本下载失败");
                    }

                    @Override
                    public void inProgress(float progress, long total, int id) {
                        super.inProgress(progress, total, id);
                        mProgress.setProgress(Math.abs((int) (progress * 100)));
                    }

                    @Override
                    public void onResponse(File response, int id) {
                        noticeDialog.dismiss();
                        if (!cancelUpdate) {
                            boolean haveInstallPermission;
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                haveInstallPermission = context.getPackageManager().canRequestPackageInstalls();
                                if (haveInstallPermission) {
//                                    myHandler.postDelayed(new Runnable() {
//                                        @Override
//                                        public void run() {
                                            installApk();//安装apk
//                                        }
//                                    },2000);

                                } else {
                                    openQuanxian();
//                                    SafePreference.save(context,"isCloseUp",false);
                                }
                            } else {
                                installApk();//安装apk
                            }
                        }
                    }
                });
    }


    MyDailog dailog = null;


    //权限弹窗
    public void openQuanxian() {
        dailog = new MyDailog(context, dailogClicer);
        dailog.Create("温馨提示", "安装应用需要打开未知来源权限，\n请去设置中开启权限", "确定", "取消");
    }

    private View.OnClickListener dailogClicer = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_confirm:
                    dailog.dissmis();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {//Android 系统8.0以上
                        startInstallPermissionSettingActivity();
                    }
                    break;
                case R.id.tv_cancle:
                    dailog.dissmis();
                    break;
            }
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startInstallPermissionSettingActivity() {
//注意这个是8.0新API
        Uri packageURI = Uri.parse("package:" + context.getPackageName());
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI);
        activity.startActivityForResult(intent, 10086);
    }

    /**
     * 安装APK文件
     */
    public void installApk() {
        File apkfile = new File(file_path, filename);
        if (!apkfile.exists()) {
            SmartToast.show("APP安装文件不存在或已损坏");
            return;
        }
        // 通过Intent安装APK文件
        final Intent i = new Intent(Intent.ACTION_VIEW);

//        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        if (Build.VERSION.SDK_INT >= 24) { //判读版本是否在7.0以上
            //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
            Uri apkUri = FileProvider.getUriForFile(activity, "com.hbdiye.newlechuangsmart.fileprovider", apkfile);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            i.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            i.setDataAndType(Uri.fromFile(apkfile), "application/vnd.android.package-archive");
        }
//        i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
//                "application/vnd.android.package-archive");
        try {
            activity.startActivity(i);
            android.os.Process.killProcess(android.os.Process.myPid());//显示安装完成页面
        } catch (Exception e) {
            // TODO: handle exception
            SmartToast.show("解析安装包出了问题哦！");
        }
    }

    /**
     * 得到本机的版本
     *
     * @Createtime: 2014年08月31日
     */
    public String getNowVersion() {
        PackageInfo info = null;
        try {
            info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        if (null != info) {
            // 当前应用的版本名称
            localVersionName = info.versionName;
            // 当前版本的版本号
            int versionCode = info.versionCode;
            localVersion = info.versionCode;
            // 当前版本的包名
            String packageNames = info.packageName;
        }
        return localVersionName;
    }

    /**
     * 获取服务器版本号
     *
     * @return
     */
    private HashMap<String, String> items = null;

    public void requestCheckUpdateJson() {
        OkHttpUtils
                .post()
                .url(InterfaceManager.getInstance().getURL(UPDATEVERSION))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        VersionBean versionBean = new Gson().fromJson(response, VersionBean.class);
                        Log.e("version",response);
                        if (versionBean.errcode.equals("0")) {
                            noticeMessage = versionBean.data.content;
                            apkURL = versionBean.data.download;
                            String version = versionBean.data.version;
                            String nowVersionName = getNowVersion();
                            String[] version_local = nowVersionName.trim().split("\\.");
                            String[] version_net = version.trim().split("\\.");
                            for (int i = 0; i < version_net.length; i++) {
                                String net = version_net[i];
                                String local = version_local[i];
                                int i1 = Integer.parseInt(net);
                                int i2 = Integer.parseInt(local);
                                if (i1 > i2) {
                                    Message msg = new Message();
                                    msg.what = 8001;
                                    myHandler.sendMessage(msg);
                                    return;
                                }
                            }

                        }
                    }
                });
//        Map<String, String> paraMap = new HashMap<String, String>();
//        final String X_CONTENT_TYPE = HeaderUtil.setHeader(paraMap, context, false);
//        HttpManager httpManager = HttpManager.getInstance(context, X_CONTENT_TYPE, 0);
////        Version
//        retrofit2.Call<JsonObject> call = httpManager.createApiService().vup();
//        call.enqueue(new Callback<JsonObject>() {
//            @Override
//            public void onResponse(retrofit2.Call<JsonObject> call, Response<JsonObject> response) {
//                Gson gson = new Gson();
//                ParentCode parentCode = gson.fromJson(response.body().toString(), ParentCode.class);
//                if (parentCode.getState().equals("000000")) {
//                    Version version = gson.fromJson(response.body().toString(), Version.class);
//                    newVersion = version.getMsg().getVersion_code();//后台版本编号
//                    filename = version.getMsg().getVersion_name() + ".apk";//软件名字
//                    apkURL = "https://9eae5c7d0599aef8f84d960645cb010b.dd.cdntips.com/imtt.dd.qq.com/16891/2BE3D6FDC450B34AC6C0B732B1CB6755.apk";//下载路径
//                    noticeMessage = "有更新啦";//更新提示
//                    Compulsory = version.getMsg().getFlag();//是否强制更新
//                    // 2.得到手机现在int的版本
//                    int localv = getNowVersion();
//                    Message msg = new Message();
//                    if (newVersion > localv) {
//                        msg.obj = apkURL;
//                        msg.what = 8001;
//                    } else {
//                        msg.what = 8002;
//                    }
//                    myHandler.sendMessage(msg);
//                } else {
//                    SmartToast.show( parentCode.getMsg().toString());
//                }
//            }
//
//            @Override
//            public void onFailure(retrofit2.Call<JsonObject> call, Throwable t) {
//                SmartToast.show("请求失败，请稍后重试");
//            }
//        });
    }


    /**
     * 消息接收类
     */
    public Handler myHandler = new Handler() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 7002:
                    SmartToast.show("请链接网络!");
                    break;
                case 7005:
                    SmartToast.show("版本更新检测失败!");
                    break;
                case 8002:
                    if (type == 1) {//type=1 说明是从设置页面点击更新的  type=0 说明是在个人中心弹窗更新的
                        SmartToast.show("已是最新版本");
                    }
                    break;
                case 8001:
                    if (type == 2) {
//                        if (Compulsory == 1) {//从首页进来，强制更新，才显示
//                            alertVersion(apkURL, noticeMessage);
//                        }
//                    } else {
                        alertVersion(apkURL, noticeMessage);
                    }
                    break;
            }
        }
    };


}
