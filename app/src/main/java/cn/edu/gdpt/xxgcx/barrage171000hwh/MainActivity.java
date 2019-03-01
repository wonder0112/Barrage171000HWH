package cn.edu.gdpt.xxgcx.barrage171000hwh;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.VideoView;

import master.flame.danmaku.controller.DrawHandler;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.ui.widget.DanmakuView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private VideoView mVdoViMainVideo;
    private DanmakuView mKuViewMainBarrage;
    private EditText mEdtTxtMainInput;
    private Button mBtnMainSend;
    private LinearLayout mLinlayMainBottom;

    //1、声明和定义3个相关变量
    private  boolean showDanmaku;//弹幕是否显示
    private DanmakuContext danmakuContext;//弹幕上下文，存放弹幕相关信息，比如文字，大小，颜色等。
    private BaseDanmakuParser parser=new BaseDanmakuParser() {//定义弹幕解析器
        @Override
        protected IDanmakus parse() {
            return new Danmakus();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        playVideo();
        initDanmaku();//3、调用弹幕初始化
    }

    /**
     * 2‘初始化弹幕
     */
    private  void initDanmaku(){
        mKuViewMainBarrage.setCallback(new DrawHandler.Callback() {//4、设置弹幕的回调函数
            @Override
            public void prepared() {//5、设定为显示弹幕，并开启弹幕
                showDanmaku=true;
                mKuViewMainBarrage.start();
            }
            @Override
            public void updateTimer(DanmakuTimer timer) {
            }
            @Override
            public void danmakuShown(BaseDanmaku danmaku) {
            }
            @Override
            public void drawingFinished() {
            }
        });
        danmakuContext=DanmakuContext.create();//创建弹幕上下文对象
        mKuViewMainBarrage.enableDanmakuDrawingCache(true);//提高绘制效率
        mKuViewMainBarrage.prepare(parser,danmakuContext);//参数为解析器和上下文
    }

    /**
     * 播放视频
     */
    private void playVideo(){
        String uri="android.resource://"+getPackageName()+"/"+R.raw.sun;//视频路径
        if(uri!=null){
            mVdoViMainVideo.setVideoURI(Uri.parse(uri));//通过URI解析路径，并赋给VideoView控件
            mVdoViMainVideo.start();//播放视频
        }else {
            mVdoViMainVideo.getBackground().setAlpha(0);//设定背景透明
        }
        //循环播放
        mVdoViMainVideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
                mp.setLooping(true);
            }
        });
    }
    private void initView() {
        mVdoViMainVideo = (VideoView) findViewById(R.id.vdoVi_main_video);
        mKuViewMainBarrage = (DanmakuView) findViewById(R.id.kuView_main_barrage);
        mEdtTxtMainInput = (EditText) findViewById(R.id.edtTxt_main_input);
        mBtnMainSend = (Button) findViewById(R.id.btn_main_send);
        mLinlayMainBottom = (LinearLayout) findViewById(R.id.linlay_main_bottom);
        mLinlayMainBottom.setVisibility(View.GONE);
        mBtnMainSend.setOnClickListener(this);
        mKuViewMainBarrage.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_main_send:
                submit();
                break;
            case R.id.kuView_main_barrage://点击弹幕时触发
                if(mLinlayMainBottom.getVisibility()==View.GONE){//如果当前是隐藏
                    mLinlayMainBottom.setVisibility(View.VISIBLE);//则设置为显示
                }else {
                    mLinlayMainBottom.setVisibility(View.GONE);//否则隐藏输入栏
                }
                break;
        }
    }
    private void submit() {
        // validate
        String input = mEdtTxtMainInput.getText().toString().trim();
        if (TextUtils.isEmpty(input)) {
            Toast.makeText(this, "不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        // TODO validate success, do something
        addDanmaku(input,true);
    }

    /**
     * 添加一条弹幕
     */
    private  void addDanmaku(String context,Boolean border){
        BaseDanmaku danmaku=danmakuContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);
        danmaku.text=context;//文本内容
        danmaku.textSize=30;//字体大小
        danmaku.padding=6;//内边框距离
        danmaku.textColor= Color.WHITE;//文本颜色
        danmaku.setTime(mKuViewMainBarrage.getCurrentTime());//显示时间
        if(border){
            danmaku.borderColor=Color.RED;//边框颜色
        }
        mKuViewMainBarrage.addDanmaku(danmaku);//将弹幕添加到弹幕视图控件中去
    }

}
