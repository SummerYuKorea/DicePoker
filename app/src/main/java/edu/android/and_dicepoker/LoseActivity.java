package edu.android.and_dicepoker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import static edu.android.and_dicepoker.BettingActivity.STAKE;
import static edu.android.and_dicepoker.GameActivity.HANDTYPE;
import static edu.android.and_dicepoker.GameActivity.HANDTYPE_COM;
import static edu.android.and_dicepoker.GameActivity.USERID;
import static edu.android.and_dicepoker.GameActivity.USERID_COM;

public class LoseActivity extends AppCompatActivity {
    private ImageView cancel;
    private TextView textStake;

    private TextView textId1, textId2, textHandType1, textHandType2;
    private ImageView imageProfile1, imageProfile2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lose);

        textStake=(TextView)findViewById(R.id.textStake);

        textId1=(TextView)findViewById(R.id.textId1);
        textId2=(TextView)findViewById(R.id.textId2);
        textHandType1=(TextView)findViewById(R.id.textHandType1);
        textHandType2=(TextView)findViewById(R.id.textHandType2);

        imageProfile1=(ImageView)findViewById(R.id.imageProfile1for);
        imageProfile2=(ImageView)findViewById(R.id.imageProfile2for);

        cancel = (ImageView) findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Bundle bundle=getIntent().getExtras();
        int stake=bundle.getInt(STAKE);
        String userId_com=bundle.getString(USERID_COM);
        String userId=bundle.getString(USERID);
        String handType=bundle.getString(HANDTYPE);
        String handType_com=bundle.getString(HANDTYPE_COM);

        Boolean mute=bundle.getBoolean(GameActivity.MUTE);

        textStake.setText("X "+stake+"");
        textId1.setText(userId_com);
        textId2.setText(userId);
        textHandType1.setText(handType_com);
        textHandType2.setText(handType);

        imageProfile1.setImageResource(R.drawable.i3);
        imageProfile2.setImageResource(MainActivity.profileImageId);

        if(mute==false)GameActivity.sp.play(GameActivity.loseSoundId_i[0],1,1,0,0,1);

    }

    // 희석님
    private int[] loseSoundId_0={R.raw.lose1_0, R.raw.lose2_0, R.raw.lose3_0, R.raw.lose4_0};

    // 용욱
    private int[] loseSoundId_1={R.raw.lose_1, R.raw.lose2_1};

    // 호영
    private int[] loseSoundId_2={R.raw.lose1_2, R.raw.lose2_2};

    //for All
    //private int voice=0; //0: 희석, 1: 용욱, 2: 호영, 3: 용선, 4: 한결
    private int[][] loseSoundId= {loseSoundId_0, loseSoundId_1, loseSoundId_2};

    public void onClickBtnMenu(View view) {
        Intent intent=new Intent(this, GameActivity.class);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void onClickBtnRestart(View view) {
        finish();
    }
}
