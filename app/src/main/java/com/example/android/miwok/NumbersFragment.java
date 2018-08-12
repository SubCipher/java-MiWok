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
       final View rootView = inflater.inflate(R.layout.word_list,container,false);

        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word(R.string.number_one,R.string.miwok_number_one, R.mipmap.ic_launcher,R.raw.number_one));
        words.add(new Word(R.string.number_two,R.string.miwok_number_two, R.mipmap.ic_launcher,R.raw.number_two));
        words.add(new Word(R.string.number_three,R.string.miwok_number_three, R.mipmap.ic_launcher,R.raw.number_three));
        words.add(new Word(R.string.number_four,R.string.miwok_number_four, R.mipmap.ic_launcher,R.raw.number_four));
        words.add(new Word(R.string.number_five,R.string.miwok_number_five, R.mipmap.ic_launcher,R.raw.number_five));
        words.add(new Word(R.string.number_six,R.string.miwok_number_six, R.mipmap.ic_launcher,R.raw.number_six));
        words.add(new Word(R.string.number_seven,R.string.miwok_number_seven, R.mipmap.ic_launcher,R.raw.number_seven));
        words.add(new Word(R.string.number_eight,R.string.miwok_number_eight, R.mipmap.ic_launcher,R.raw.number_eight));
        words.add(new Word(R.string.number_nine,R.string.miwok_number_nine, R.mipmap.ic_launcher,R.raw.number_nine));
        words.add(new Word(R.string.number_ten,R.string.miwok_number_ten, R.mipmap.ic_launcher,R.raw.number_ten));

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
