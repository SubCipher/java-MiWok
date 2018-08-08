package com.example.android.miwok;

public class Word {

    private String mMiWokWord;
    private String mDefaultTranslastion;
    private int mImageResourceId;
    private int mSoundResourceId;


    public Word(String miWokWord, String translation, int imageResourceId ,int soundResourceID){
        mMiWokWord = miWokWord;
        mDefaultTranslastion = translation;
        mImageResourceId = imageResourceId;
        mSoundResourceId = soundResourceID;
    }

    public Word(String miWokWord, String translation, int soundResourceId){
       mMiWokWord = miWokWord;
       mDefaultTranslastion = translation;
       mSoundResourceId = soundResourceId;

    }

    public String getMiWokWord() {
        return mMiWokWord;
    }

    public String getDefaultTranslastion(){
        return mDefaultTranslastion;
    }

    public int getmImageResourceId(){return mImageResourceId; }

    public int getmSoundResourceId(){ return mSoundResourceId; }
/*
    @Override
    public String toString() {
        return "Word{" +
                "mDefaultTranslation='" + mDefaultTranslastion + '\'' +
                ",mMiwokTranslation='" + mMiWokWord + '\'' +
                ",mSoundResourceId=" + mSoundResourceId +
                ",mImageResourceId=" + mImageResourceId +
                '}';
    }
    */
}
