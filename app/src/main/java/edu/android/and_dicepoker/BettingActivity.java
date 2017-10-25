package edu.android.and_dicepoker;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import static edu.android.and_dicepoker.GameActivity.IMAGE_ID;
import static edu.android.and_dicepoker.MainActivity.appliedSkin;
import static edu.android.and_dicepoker.MainActivity.vo;

public class BettingActivity extends AppCompatActivity {

    private ImageView btnStop, btnOkay;
    private ImageView btn10, btn100, btn1000;
    private TextView text10, text100, text1000;//결국 onClick도 얘로 쓰게 됨
    private TextView textOkay, textStop;
    private RelativeLayout layoutBetting;
    private ImageView imageView6, imageView7, imageView8, imageView9, imageView10,
            imageOkay, imageStop, borderOkay, borderStop;
    private ImageView border10, border100, border1000;
    private TextView textResult, textTotalStake;
    public int[] indexMy = new int[5];

    private int i=0;
    private int baseStake=0;
    private int totalStake=0;
    private final int[] MULTIPLE={10, 6, 5, 4, 4, 3, 2, 1, 0};
    private int handRankingIndex;

    public static final String STAKE="stake";

    private Boolean btnStopClicked =false;

    private static final String TYPEFACE_NAME = "BMJUA_ttf.ttf";
    private Typeface typeface = null;
    private void loadTypeface(){
        if(typeface==null)
            typeface = Typeface.createFromAsset(getAssets(), TYPEFACE_NAME);     }

    @Override
    public void setContentView(int viewId) {
        View view = LayoutInflater.from(this).inflate(viewId, null);
        ViewGroup group = (ViewGroup)view;
        int childCnt = group.getChildCount();
        for(int i=0; i<childCnt; i++){
            View v = group.getChildAt(i);
            if(v instanceof TextView){
                ((TextView)v).setTypeface(typeface);
            }
        }
        super.setContentView(view);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadTypeface();
        setContentView(R.layout.activity_betting);

        btn10=(ImageView) findViewById(R.id.btn10);
        btn100=(ImageView) findViewById(R.id.btn100);
        btn1000=(ImageView) findViewById(R.id.btn1000);

        text10=(TextView)findViewById(R.id.text10);
        text100=(TextView)findViewById(R.id.text100);
        text1000=(TextView)findViewById(R.id.text1000);

        border10=(ImageView)findViewById(R.id.border10);
        border100=(ImageView)findViewById(R.id.border100);
        border1000=(ImageView)findViewById(R.id.border1000);

        imageView6=(ImageView)findViewById(R.id.imageView66);
        imageView7=(ImageView)findViewById(R.id.imageView77);
        imageView8=(ImageView)findViewById(R.id.imageView88);
        imageView9=(ImageView)findViewById(R.id.imageView99);
        imageView10=(ImageView)findViewById(R.id.imageView1010);

        imageOkay=(ImageView)findViewById(R.id.imageOkay);
        imageStop=(ImageView)findViewById(R.id.imageStop);
        borderOkay=(ImageView)findViewById(R.id.borderOkay);
        borderStop=(ImageView)findViewById(R.id.borderStop);
        textOkay=(TextView)findViewById(R.id.textOkay);
        textStop=(TextView)findViewById(R.id.textStop);

        btnStop=(ImageView)findViewById(R.id.btnStop);
        btnOkay=(ImageView)findViewById(R.id.btnOkay);

        textResult=(TextView)findViewById(R.id.textResult);
        textTotalStake=(TextView)findViewById(R.id.textTotalStake);

        layoutBetting=(RelativeLayout)findViewById(R.id.relativeLayoutBetting);

        //linearLayout=(LinearLayout)findViewById(R.id.linearLayout6);
        //linearLayout.setVisibility(View.INVISIBLE);
        //호영오빠가 레이아웃으로 안해서 이미지뷰 하나하나 invisible처리로 바꿔야함
        imageView6.setVisibility(View.INVISIBLE);
        imageView7.setVisibility(View.INVISIBLE);
        imageView8.setVisibility(View.INVISIBLE);
        imageView9.setVisibility(View.INVISIBLE);
        imageView10.setVisibility(View.INVISIBLE);
        imageOkay.setVisibility(View.INVISIBLE);
        imageStop.setVisibility(View.INVISIBLE);
        borderStop.setVisibility(View.INVISIBLE);
        borderOkay.setVisibility(View.INVISIBLE);
        textOkay.setVisibility(View.INVISIBLE);
        textStop.setVisibility(View.INVISIBLE);

        btnStop.setVisibility(View.INVISIBLE);
        btnOkay.setVisibility(View.INVISIBLE);

        if(vo.getBackSkin()!=0)layoutBetting.setBackgroundResource(appliedSkin);
    }//onCreate

    public void onClickBtnMoney(View view){
        if((TextView)view==text10){
            //btn10.setBackgroundColor(Color.YELLOW);
            //backgroundcolor대신에 border 교체하는 것으로
            border10.setImageResource(R.drawable.border_checked);
            baseStake=10;
        }else if((TextView)view==text100){
            border100.setImageResource(R.drawable.border_checked);
            baseStake=100;
        }else  if((TextView)view==text1000){
            border1000.setImageResource(R.drawable.border_checked);
            baseStake=1000;
        }
        // linearLayout.setVisibility(View.VISIBLE);
        imageView6.setVisibility(View.VISIBLE);
        imageView7.setVisibility(View.VISIBLE);
        imageView8.setVisibility(View.VISIBLE);
        imageView9.setVisibility(View.VISIBLE);
        imageView10.setVisibility(View.VISIBLE);
        imageStop.setVisibility(View.VISIBLE);
        borderStop.setVisibility(View.VISIBLE);
        textStop.setVisibility(View.VISIBLE);

        btnStop.setVisibility(View.VISIBLE);


        Thread randomThrowThread=new Thread(randomThrowRunnable);
        randomThrowThread.start();
    }//onClickBtnMoney

    public void onClickBtnStop(View view){
        if(btnStopClicked==true) return;  //이미 눌렸으면 걍 빠져나와

        imageOkay.setVisibility(View.VISIBLE);
        borderOkay.setVisibility(View.VISIBLE);
        btnOkay.setVisibility(View.VISIBLE);
        textOkay.setVisibility(View.VISIBLE);

        btnStopClicked=true;
        i=0;
    }//onClickBtnStop

    public void onClickBtnOkay(View v){
        Intent i=new Intent(this, MainActivity.class);
        i.putExtra(STAKE,totalStake);
        setResult(RESULT_OK, i);
        finish();
    }

    private Handler slotHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            double r6 = Math.random();
            int index6 = (int) (6 * r6);
            imageView6.setImageResource(IMAGE_ID[index6]);
            indexMy[0] = index6;

            double r7 = Math.random();
            int index7 = (int) (6 * r7);
            imageView7.setImageResource(IMAGE_ID[index7]);
            indexMy[1] = index7;

            double r8 = Math.random();
            int index8 = (int) (6 * r8);
            imageView8.setImageResource(IMAGE_ID[index8]);
            indexMy[2] = index8;

            double r9 = Math.random();
            int index9 = (int) (6 * r9);
            imageView9.setImageResource(IMAGE_ID[index9]);
            indexMy[3] = index9;

            double r10 = Math.random();
            int index10 = (int) (6 * r10);
            imageView10.setImageResource(IMAGE_ID[index10]);
            indexMy[4] = index10;

        }
    };

    private Handler setResultHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            textResult.setText("   "+result(indexMy));
        }
    };

    private Handler setTotalStakeHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            totalStake=baseStake*MULTIPLE[handRankingIndex];
            textTotalStake.setText("   판돈 : "+totalStake+"$ = "+baseStake+"$ x "+MULTIPLE[handRankingIndex]+"배");
        }
    };

    Runnable randomThrowRunnable=new Runnable() {
        @Override
        public void run() {

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            while(true){
                slotHandler.sendEmptyMessage(0);
                try {
                    if(i>=0 && i<100000){ //100초
                        Thread.sleep(80);
                        i+=80;}
                    if(i>=100000 || btnStopClicked==true) {

                        if (i >= 0 && i < 750) {
                            Thread.sleep(250);
                            i += 250;
                        } else if (i >= 750 && i < 1550) {
                            Thread.sleep(400);
                            i += 400;
                        } else if (i >= 1550 && i < 2150) {
                            Thread.sleep(600);
                            i += 600;
                        } else if (i >= 2150) break;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }//WHILE

            try {
                Thread.sleep(600);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            setResultHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            setTotalStakeHandler.sendEmptyMessage(0);
        }
    };

    ////////////////////////////////////////////////////////////////////

    public String result(int [] indexMy){
        int [] types={0,0,0,0,0,0};
        int points = 0;
        String handType="Nothing";
        handRankingIndex=8; //Bust, Nothing

        int pairCount = 0;
        String onePairValue="";
        String twoPairValue;

        boolean fullHouse=false;


        // 각 숫자가 몇개인지 types 배열에 정리
        for (int i = 0; i < 5; i++) {
            for(int j=0; j<6; j++)
                if(indexMy[i]==j) types[j]++;
        }



        //족보 GO
        for(int i=0; i < 6; i++){ //i:0~5
            if (types[i] == 2) { //types[i]: i(1~6)가 몇개씩있는지
                pairCount++;
                // Log.i(TAG,"pairCount:"+pairCount);
                if (pairCount == 1) {
                    onePairValue=(i+1)+"";
                    handType = onePairValue+" One Pair";
                    handRankingIndex=7;

                    points= (i+1);//1~6


                }
                if (pairCount >= 2) {// 투페어 15가지종류
                    twoPairValue=(i+1)+", "+onePairValue;  //큰것부터 적음
                    handType = twoPairValue+" Two Pairs";
                    handRankingIndex=6;

                    points= ((i+1)*4)+ (points * 1);  //9~29
                    //i+1이 기존 points보다 높은 값일 수밖에 없음

                }


                for(int j=0; j<6; j++) {
                    if (types[j] == 3) {
                        pairCount--;
                        fullHouse=true;
                        handType = (j+1)+", "+(i+1)+" Full House";  //3개인 주사위숫자부터 적음
                        points = 50 + (j * 6) + (i * 1);  //51~84
                        handRankingIndex=2;
                    }}
            }else if (types[i] == 3 && fullHouse==false) {
                handType = (i+1)+" Tripple";
                points =(30+i);
                handRankingIndex=5;

            }else if (types[i] == 4) {
                handType = (i+1)+" Four of a kind";
                points = 90+i;
                handRankingIndex=1;

            }else if (types[i] == 5) {
                handType = (i+1)+" Five of a Kind";
                points = 100+i;
                handRankingIndex=0;
            }
        }//for문 over

        if (types[0] == 1 && types[1] == 1 && types[2] == 1 && types[3] == 1 && types[4] == 1) {
            handType = "Low Straight";
            points =40;
            handRankingIndex=4;
        } else if (types[1] == 1 && types[2] == 1 && types[3] == 1 && types[4] == 1 && types[5] == 1) {
            handType = "High Straight";
            points =41;
            handRankingIndex=3;
        }
        return handType;
    }// end result
}
