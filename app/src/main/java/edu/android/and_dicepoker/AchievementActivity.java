package edu.android.and_dicepoker;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class AchievementActivity extends AppCompatActivity {

    private TextView textTitleAchievement;

    public static final String TYPEFACE_NAME = "BMJUA_ttf.ttf";
    private Typeface typeface = null;
    private void loadTypeface(){
        if(typeface==null)
            typeface = Typeface.createFromAsset(getAssets(), TYPEFACE_NAME);     }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadTypeface();
        setContentView(R.layout.activity_achievement);

        textTitleAchievement=(TextView)findViewById(R.id.textTitleAchievement);

        textTitleAchievement.setTypeface(typeface);

    }


}
