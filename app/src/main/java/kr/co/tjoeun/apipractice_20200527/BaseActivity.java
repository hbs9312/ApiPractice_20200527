package kr.co.tjoeun.apipractice_20200527;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    public Context mContext = this;

    public abstract void setupEvent();
    public abstract void setValues();

}
