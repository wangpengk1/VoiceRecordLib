package com.newasia.voicerecorder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.newasia.voicerecorder.utils.AppCache;
import com.newasia.voicerecorder.utils.TimeUtils;
import com.newasia.voicerecordlib.widget.VoiceRecorderView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {



    protected VoiceRecorderView voiceRecorderView;
    protected ListView message_list;
    protected TextView tvRecorder;


    private List<MessageBean> voices;
    EaseMessageAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        voices = new ArrayList<>();
        initView();
    }



    private void initView() {

        message_list = findViewById(R.id.message_list);

        // hold to record voice
        //noinspection ConstantConditions
        voiceRecorderView = (VoiceRecorderView) findViewById(R.id.voice_recorder);

/*        voiceRecorderView.setDrawableAnimation(new Drawable[]{
                getResources().getDrawable(com.ilike.voicerecorder.R.drawable.ease_record_animate_01),
                getResources().getDrawable(com.ilike.voicerecorder.R.drawable.ease_record_animate_02),
                getResources().getDrawable(com.ilike.voicerecorder.R.drawable.ease_record_animate_03),
                getResources().getDrawable(com.ilike.voicerecorder.R.drawable.ease_record_animate_04),
                getResources().getDrawable(com.ilike.voicerecorder.R.drawable.ease_record_animate_05),
                getResources().getDrawable(com.ilike.voicerecorder.R.drawable.ease_record_animate_06),
                getResources().getDrawable(com.ilike.voicerecorder.R.drawable.ease_record_animate_07),
                getResources().getDrawable(com.ilike.voicerecorder.R.drawable.ease_record_animate_08),
                getResources().getDrawable(com.ilike.voicerecorder.R.drawable.ease_record_animate_09),
                getResources().getDrawable(com.ilike.voicerecorder.R.drawable.ease_record_animate_10),
                getResources().getDrawable(com.ilike.voicerecorder.R.drawable.ease_record_animate_11),
                getResources().getDrawable(com.ilike.voicerecorder.R.drawable.ease_record_animate_12),
                getResources().getDrawable(com.ilike.voicerecorder.R.drawable.ease_record_animate_13),
                getResources().getDrawable(com.ilike.voicerecorder.R.drawable.ease_record_animate_14),});*/
        //voiceRecorderView.setCustomNamingFile(true, "语音命名");
        //PathUtil.getInstance().initDirs("voice", this);

//        voiceRecorderView.setShowMoveUpToCancelHint("松开手指，取消发送");
//        voiceRecorderView.setShowReleaseToCancelHint("手指上滑，取消发送");
        tvRecorder = (TextView) findViewById(R.id.tv_touch_recorder);
        voiceRecorderView.setVoiceRecorderCallBack((voiceFilePath, voiceTimeLength) -> {
            Log.e("voiceFilePath=", voiceFilePath + "  time = " + voiceTimeLength);
            //   sendVoiceMessage(voiceFilePath, voiceTimeLength);
            MessageBean bean = new MessageBean();
            bean.path = voiceFilePath;
            bean.msg = "image";
            bean.second = voiceTimeLength;
            bean.time = TimeUtils.getCurrentTimeInLong();
            voices.add(bean);
            adapter.notifyDataSetChanged();
        });
        tvRecorder.setOnTouchListener((v,event)->{
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                voiceRecorderView.stopPlayVoice();
            }
            return voiceRecorderView.onPressToSpeakBtnTouch(v,event);
        });


        adapter = new EaseMessageAdapter(this, voices);
        message_list.setAdapter(adapter);
        adapter.setOnItemClickLister(new EaseMessageAdapter.onItemClickLister() {
            @Override
            public void onItemClick(ImageView imageView, String path, int position) {
                voiceRecorderView.playVoiceFile(imageView,path);
                //播放语音
                //  VoicePlayClickListener voicePlayClickListener = new VoicePlayClickListener(imageView, TestVoiceActivity.this);
               /* voicePlayClickListener.setStopPlayIcon(R.drawable.ease_chatto_voice_playing);
                voicePlayClickListener.setPlayingIconDrawableResoure(R.drawable.voice_to_icon);*/
                //   voicePlayClickListener.playVoice(path);

                // new VoicePlayClickListener(imageView, TestVoiceActivity.this).playUrlVoice("http://img.layuva.com//b96c4bde124a328d9c6edb5b7d51afb2.amr");


//                if (AppCache.getPlayService() != null) {
//                    AppCache.getPlayService().setImageView(imageView);
//                    AppCache.getPlayService().stopPlayVoiceAnimation();
//                  //  AppCache.getPlayService().play("http://img.layuva.com//b96c4bde124a328d9c6edb5b7d51afb2.amr");
//                    AppCache.getPlayService().play(path);
//                }
            }
        });

    }


}