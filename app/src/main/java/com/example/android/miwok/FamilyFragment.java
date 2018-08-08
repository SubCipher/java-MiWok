package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


public class FamilyFragment extends Fragment {

    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;

    //create audioManger listener to manage playback request

    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {

        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            } else if
                    (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                mMediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                releaseMediaPlayer();
            }
        }
    };

    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };

    public FamilyFragment(){
        //empty public constructor(required)

    }

    @Override
    public  View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.activity_family, container, false);

        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        // Create a list of words
        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word(getString(R.string.family_father), getString(R.string.miwok_family_father),
                R.drawable.family_father, R.raw.family_father));
        words.add(new Word(getString(R.string.family_mother), getString(R.string.miwok_family_mother),
                R.drawable.family_mother, R.raw.family_mother));
        words.add(new Word(getString(R.string.family_son), getString(R.string.miwok_family_son),
                R.drawable.family_son, R.raw.family_son));
        words.add(new Word(getString(R.string.family_daughter), getString(R.string.miwok_family_daughter),
                R.drawable.family_daughter, R.raw.family_daughter));
        words.add(new Word(getString(R.string.family_older_brother), getString(R.string.miwok_family_older_brother),
                R.drawable.family_older_brother, R.raw.family_older_brother));
        words.add(new Word( getString(R.string.family_younger_brother), getString(R.string.miwok_family_younger_brother),
                R.drawable.family_younger_brother, R.raw.family_younger_brother));
        words.add(new Word(getString(R.string.family_older_sister), getString(R.string.miwok_family_older_sister),
                R.drawable.family_older_sister, R.raw.family_older_sister));
        words.add(new Word(getString(R.string.family_younger_sister), getString(R.string.miwok_family_younger_sister),
                R.drawable.family_younger_sister, R.raw.family_younger_sister));
        words.add(new Word(getString(R.string.family_grandmother),getString(R.string.miwok_family_grandmother),
                R.drawable.family_grandmother, R.raw.family_grandmother));
        words.add(new Word(getString(R.string.family_grandfather), getString(R.string.miwok_family_grandfather),
                R.drawable.family_grandfather, R.raw.family_grandfather));

        WordAdapter adapter = new WordAdapter(getActivity(), words, R.color.category_family);
        ListView familyListView = (ListView) rootView.findViewById(R.id.familyActivityView);

        familyListView.setAdapter(adapter);
        familyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                releaseMediaPlayer();
                Word w = words.get(position);

                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mMediaPlayer = MediaPlayer.create(getActivity(), w.getmSoundResourceId());
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
       releaseMediaPlayer();
    }

    private void releaseMediaPlayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();

            mMediaPlayer = null;
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);

        }
    }
}