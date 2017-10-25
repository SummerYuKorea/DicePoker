package edu.android.and_dicepoker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import static edu.android.and_dicepoker.BettingActivity.STAKE;
import static edu.android.and_dicepoker.GameActivity.*;

public class WinActivity extends AppCompatActivity {
    private ImageView cancelWin;
    private TextView textStake;

    private TextView textId1, textId2, textHandType1, textHandType2;
    private ImageView imageProfile1for, imageProfile2for;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);

        cancelWin = (ImageView) findViewById(R.id.cancelWin);
        cancelWin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        textStake=(TextView)findViewById(R.id.textStake);

        textId1=(TextView)findViewById(R.id.textId1);
        textId2=(TextView)findViewById(R.id.textId2);
        textHandType1=(TextView)findViewById(R.id.textHandType1);
        textHandType2=(TextView)findViewById(R.id.textHandType2);

        imageProfile1for=(ImageView)findViewById(R.id.imageProfile1for);
        imageProfile2for=(ImageView)findViewById(R.id.imageProfile2for);

        Bundle bundle=getIntent().getExtras();
        int stake=bundle.getInt(STAKE);
        String userId_com=bundle.getString(USERID_COM);
        String userId=bundle.getString(USERID);
        String handType=bundle.getString(HANDTYPE);
        String handType_com=bundle.getString(HANDTYPE_COM);
//        int profileImageId=bundle.getInt(IMAGEPROFILE);

        Boolean mute=bundle.getBoolean(GameActivity.MUTE);

        textStake.setText("X "+stake+"");
        textId1.setText(userId_com);
        textId2.setText(userId);
        textHandType1.setText(handType_com);
        textHandType2.setText(handType);

        imageProfile1for.setImageResource(R.drawable.i3);
        imageProfile2for.setImageResource(MainActivity.profileImageId);

        if(mute==false)GameActivity.sp.play(GameActivity.winSoundId_i[0],1,1,0,0,1);
    }//onCreate

    // 희석님
    private int[] winSoundId_0={R.raw.win1_0, R.raw.win2_0, R.raw.win3_0};

    // 용욱
    private int[] winSoundId_1={R.raw.win1_1, R.raw.win2_1, R.raw.win3_1};
    // 호영
    private int[] winSoundId_2={R.raw.win1_2, R.raw.win2_2, R.raw.win3_2};

    //for All
    //private int voice=0; //0: 희석, 1: 용욱, 2: 호영, 3: 용선, 4: 한결
    private int[][] winSoundId= {winSoundId_0, winSoundId_1, winSoundId_2};

    public void onClickBtnRestart(View view) {
        finish();

    }

    public void onClickBtnMenu(View view) {
        Intent intent=new Intent(this, GameActivity.class);
        setResult(RESULT_OK, intent);
        finish();

    }
}
