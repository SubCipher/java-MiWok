package com.example.android.miwok;

public class Word {

    private int mMiWokWord;
    private int mDefaultTranslastion;
    private int mImageResourceId;
    private int mSoundResourceId;
    private static final int NO_IMAGE_PROVIDED = -1;


    public Word(int miWokWord, int translation, int imageResourceId ,int soundResourceID){
        mMiWokWord = miWokWord;
        mDefaultTranslastion = translation;
        mImageResourceId = imageResourceId;
        mSoundResourceId = soundResourceID;
    }

    public Word(int miWokWord, int translation, int soundResourceId){
       mMiWokWord = miWokWord;
       mDefaultTranslastion = translation;
       mSoundResourceId = soundResourceId;

    }

    public int getMiWokWord() {
        return mMiWokWord;
    }

    public int getDefaultTranslastion(){
        return mDefaultTranslastion;
    }

    public int getmImageResourceId(){return mImageResourceId; }

    public int getmSoundResourceId(){ return mSoundResourceId; }

    public boolean hasImage() {
        return mImageResourceId != NO_IMAGE_PROVIDED;
    }

}
