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

import java.util.ArrayList;

public class NumbersFragment extends Fragment {

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


    public NumbersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       final View rootView = inflater.inflate(R.layout.activity_numbers,container,false);

        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("one","lutti", R.mipmap.ic_launcher,R.raw.kitten6));
        words.add(new Word("two","otiiko", R.mipmap.ic_launcher,R.raw.kitten6));
        words.add(new Word("three","tolookosu", R.mipmap.ic_launcher,R.raw.kitten6));
        words.add(new Word("four","oyyisa", R.mipmap.ic_launcher,R.raw.kitten6));
        words.add(new Word("five","massokka", R.mipmap.ic_launcher,R.raw.kitten6));
        words.add(new Word("six","temmokka", R.mipmap.ic_launcher,R.raw.kitten6));
        words.add(new Word("seven","kenekaku", R.mipmap.ic_launcher,R.raw.kitten6));
        words.add(new Word("eight","kawinta", R.mipmap.ic_launcher,R.raw.kitten6));
        words.add(new Word("nine","wo'e", R.mipmap.ic_launcher,R.raw.kitten6));
        words.add(new Word("ten","na'aacha", R.mipmap.ic_launcher,R.raw.kitten6));

        WordAdapter itemsAdapter = new WordAdapter(getActivity(), words, R.color.category_numbers);
        ListView numberlistView = (ListView) rootView.findViewById(R.id.list);
        numberlistView.setAdapter(itemsAdapter);

        numberlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Word word = words.get(position);

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
