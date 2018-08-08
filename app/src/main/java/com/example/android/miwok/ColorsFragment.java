package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class ColorsFragment extends Fragment {
    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;

    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int audioFocusChanged) {

            switch (audioFocusChanged){
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                    mMediaPlayer.pause();
                    break;
                case AudioManager.AUDIOFOCUS_GAIN:
                    mMediaPlayer.start();
                    break;
                case AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK:
                    mMediaPlayer.pause();
                    break;
                case AudioManager.AUDIOFOCUS_LOSS:
                    mediaplayerRelease();
                    break;
                default:
                    mMediaPlayer.pause();
            }

        }
    };


    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaplayerRelease();

        }
    };

    public ColorsFragment(){
        //empty public constructor
    }


    @Override
    public View onCreateView (LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_colors,container,false);


        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);


       // setContentView(R.layout.activity_colors);

        final ArrayList<Word> colors = new ArrayList<>();

        colors.add(new Word("red", "wetetti", R.mipmap.ic_launcher, R.raw.kitten6));
        colors.add(new Word("green", "chokokki", R.mipmap.ic_launcher, R.raw.clearday));
        colors.add(new Word("brown", "takaakki", R.mipmap.ic_launcher, R.raw.es1));
        colors.add(new Word("gray", "topoppi", R.mipmap.ic_launcher, R.raw.kitten6));
        colors.add(new Word("black", "kululli", R.mipmap.ic_launcher, R.raw.kitten6));
        colors.add(new Word("white", "kelelli", R.mipmap.ic_launcher, R.raw.kitten6));


        WordAdapter colorDataSource = new WordAdapter(getActivity(), colors, R.color.category_colors);

        ListView colorListVeiw = (ListView) rootView.findViewById(R.id.colorsActivityView);
        colorListVeiw.setAdapter(colorDataSource);

        colorListVeiw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                mediaplayerRelease();

                Word word = colors.get(position);

                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                    mMediaPlayer = MediaPlayer.create(getActivity(), word.getmSoundResourceId());
                    mMediaPlayer.start();
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });
        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        mediaplayerRelease();
    }

    private void mediaplayerRelease() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();

            mMediaPlayer = null;
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }
}
