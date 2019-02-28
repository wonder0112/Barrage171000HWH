package cn.edu.gdpt.xxgcx.barrage171000hwh;

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

import master.flame.danmaku.ui.widget.DanmakuView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private VideoView mVdoViMainVideo;
    private DanmakuView mKuViewMainBarrage;
    private EditText mEdtTxtMainInput;
    private Button mBtnMainSend;
    private LinearLayout mLinlayMainBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        playVideo();
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

        mBtnMainSend.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_main_send:
                submit();
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
    }
}
