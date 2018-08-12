package com.example.android.miwok;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Word> {
    private int mColorResourceId;

    public WordAdapter(Activity context, ArrayList<Word> word,int colorResourceId){
        super(context,0, word);
        mColorResourceId = colorResourceId;

    }

    @Override
    public View getView(int position, View convertView,ViewGroup parent) {


        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.miwok_item_list,parent,false);
        }

        Word currentWord = getItem(position);

        Log.v("word activity","current word " + currentWord);

        TextView nameTextView = (TextView) listItemView.findViewById(R.id.text_view00);
        nameTextView.setText(currentWord.getMiWokWord());

        TextView translationView = (TextView) listItemView.findViewById(R.id.text_view01);
        translationView.setText(currentWord.getDefaultTranslastion());

        ImageView iconImage = (ImageView) listItemView.findViewById(R.id.listImage);

        if(currentWord.hasImage()){
            iconImage.setImageResource(currentWord.getmImageResourceId());
            iconImage.setVisibility(View.VISIBLE);
        } else {
            iconImage.setVisibility(View.GONE);
        }


        ImageView soundPlayIcon = (ImageView) listItemView.findViewById(R.id.playIcon);
        soundPlayIcon.setImageResource(R.mipmap.baseline_play1_circle_outline_white_18dp);

        View textContainer = listItemView.findViewById(R.id.text_container);
        int color = ContextCompat.getColor(getContext(),mColorResourceId);
        textContainer.setBackgroundColor(color);


        return listItemView;
    }
}
