package edu.android.and_dicepoker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static edu.android.and_dicepoker.BettingActivity.STAKE;
import static edu.android.and_dicepoker.MainActivity.GAMESKIN;
import static edu.android.and_dicepoker.MainActivity.vo;

public class GameActivity extends AppCompatActivity {

    public final static String TAG="edu.android.DicePoker";

    private static final String TYPEFACE_NAME = "BMJUA_ttf.ttf";
    private Typeface typeface = null;


    public static String userId;
    public static int win ;
    public static int lose ;
    public static int totalGame;
    public static int gold ;
    public static String title;
    public static int gameOn;
    public static int diceSkin;
    public static int backSkin;
    public static int voice;

    public static final String VOICE="voice";

    public static String userId_com;
    public static int win_com ;
    public static int lose_com ;
    public static int totalGame_com;
    public static int gold_com ;
    public static String title_com;
    public static int gameOn_com;
    public static int diceSkin_com;
    public static int backSkin_com;
    public static int voice_com;

    public void setMyInfo(UserVO vo) {
        this.userId = vo.getUserId();
        this.win = vo.getWin();
        this.lose = vo.getLose();
        this.totalGame=vo.getTotalGame();
        this.gold = vo.getGold();
        this.title = vo.getTitle();
        this.gameOn=vo.getGameOn();
        this.diceSkin=vo.getDiceSkin();
        this.backSkin=vo.getBackSkin();
        this.voice= vo.getVoice();

    }

    public void setUserVO(UserVO vo){//여기 매개변수에 me 넣는것
        vo.setGold(gold);
        vo.setWin(win);
        vo.setLose(lose);
        vo.setTotalGame(totalGame);//일단 이정도 바꾸면 될 듯

        vo.setTitle(title);
    }

    public void setComVO(UserVO vo){
        vo.setGold(gold_com);
        vo.setWin(win_com);
        vo.setLose(lose_com);
        vo.setTotalGame(totalGame_com);
    }

    public void setComInfo(UserVO vo){
        this.userId_com = vo.getUserId();
        this.win_com = vo.getWin();
        this.lose_com = vo.getLose();
        this.totalGame_com=vo.getTotalGame();
        this.gold_com = vo.getGold();
        this.title_com = vo.getTitle();
        this.gameOn_com=vo.getGameOn();
        this.diceSkin_com=vo.getDiceSkin();
        this.backSkin_com=vo.getBackSkin();
        this.voice_com= vo.getVoice();
    }

    private UserVO me, comVO;


    private int countDice = -1;
    private String handType = "";

    public int[] indexCom = new int[5];
    public int[] indexMy = new int[5];
    public int points = 0;
    public int pointsCom = 0;


    public boolean boss=true; //내가 보스야

    private String betting; //하프, 쿼터, 체크(콜), (따당, 삥), 다이
    private int stake=0;

    private static int onGoingGamer=0;
    private static final int HOW_MANY_PLAYERS=2;
    private boolean btnThrowClicked=false;
    private boolean btnThrowClickedCom=false;//이건 DB받아오면 없어도 되는 TODO
    // private TextView[] bettingView=new TextView[HOW_MANY_PLAYERS];  //이거 초기화 질문
    private Thread thread1, thread2;  //TODO
    private Thread thread3, thread4; //던지는것기다리기위해
    private Thread clickMeThread;
    //private Thread[] threads=new Thread[HOW_MANY_PLAYERS];


    public static int[] IMAGE_ID = {
            R.drawable.dice1,
            R.drawable.dice2,
            R.drawable.dice3,
            R.drawable.dice4,
            R.drawable.dice5,
            R.drawable.dice6

    };
    private final int REQ_CODE1=1;
    private final int REQ_CODE2=2;

    private ImageView imageView1,imageView2, imageView3, imageView4, imageView5, imageView6,
            imageView7, imageView8, imageView9,imageView10,  imageProfile2, imageProfile1;
    private CheckBox checkBox1, checkBox2, checkBox3, checkBox4, checkBox5, checkBox6, checkBox7,
            checkBox8, checkBox9, checkBox10;
    private TextView textId1, textId2, textRecord1, textRecord2, textGold1, textGold2;
    private TextView textView1, textView2;
    private TextView textCount1, textCount2;
    private TextView textStake;
    private TextView textTitleHandRanking;
    private ImageView btnThrow2;
    private ImageView btnExit2;
    private ImageView imageTitleInGame;
    private RecyclerView recyclerView;
    HandRankingAdapter adapter;

    private RelativeLayout gameLayout;

    //호영xml에서 추가된것
    private TextView textBtnThrow, textBtnExit;

    //private static final String TAG="edu.android.DicePoker";


    /////////////////////////////////////////////////////////////////////////////////////


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadTypeface();
        //setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_game);

        imageProfile1=(ImageView)findViewById(R.id.imageProfile1);
        imageProfile2=(ImageView)findViewById(R.id.imageProfile2);

        imageView1=(ImageView)findViewById(R.id.imageView1);
        imageView2=(ImageView)findViewById(R.id.imageView2);
        imageView3=(ImageView)findViewById(R.id.imageView3);
        imageView4=(ImageView)findViewById(R.id.imageView4);
        imageView5=(ImageView)findViewById(R.id.imageView5);
        imageView6=(ImageView)findViewById(R.id.imageView6);
        imageView7=(ImageView)findViewById(R.id.imageView7);
        imageView8=(ImageView)findViewById(R.id.imageView8);
        imageView9=(ImageView)findViewById(R.id.imageView9);
        imageView10=(ImageView)findViewById(R.id.imageView10);

        imageSound=(ImageView)findViewById(R.id.imageSound);

        imageTitleInGame=(ImageView)findViewById(R.id.imageTitleInGame);

        checkBox1=(CheckBox)findViewById(R.id.checkBox1);
        checkBox2=(CheckBox)findViewById(R.id.checkBox2);
        checkBox3=(CheckBox)findViewById(R.id.checkBox3);
        checkBox4=(CheckBox)findViewById(R.id.checkBox4);
        checkBox5=(CheckBox)findViewById(R.id.checkBox5);
        checkBox6=(CheckBox)findViewById(R.id.checkBox6);
        checkBox7=(CheckBox)findViewById(R.id.checkBox7);
        checkBox8=(CheckBox)findViewById(R.id.checkBox8);
        checkBox9=(CheckBox)findViewById(R.id.checkBox9);
        checkBox10=(CheckBox)findViewById(R.id.checkBox10);

        textView1=(TextView)findViewById(R.id.textView1);
        textView2=(TextView)findViewById(R.id.textTitleAchievement);

        textCount1=(TextView)findViewById(R.id.textCount1);
        textCount2=(TextView)findViewById(R.id.textCount2);

        textStake=(TextView)findViewById(R.id.textStake);

        //btnThrow2=(Button)findViewById(R.id.btnThrow2);
        btnThrow2=(ImageView)findViewById(R.id.btnThrow2);  //호영xml에서 imageView로 바뀜
        btnExit2=(ImageView) findViewById(R.id.btnExit2);

        textTitleHandRanking=(TextView)findViewById(R.id.textTitleHandRanking);

        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);

        textBtnThrow=(TextView)findViewById(R.id.textBtnThrow);
        textBtnExit=(TextView)findViewById(R.id.textBtnExit);

        textId1=(TextView)findViewById(R.id.textId1);
        textId2=(TextView)findViewById(R.id.textId2);
        textRecord1=(TextView)findViewById(R.id.textRecord1);
        textRecord2=(TextView)findViewById(R.id.textRecord2);
        textGold1=(TextView)findViewById(R.id.textGold1);
        textGold2=(TextView)findViewById(R.id.textGold2);


        //typeFace주기!!
        textId1.setTypeface(typeface);
        textId2.setTypeface(typeface);
        textRecord1.setTypeface(typeface);
        textRecord2.setTypeface(typeface);
        textGold1.setTypeface(typeface);
        textGold2.setTypeface(typeface);

        textCount1.setTypeface(typeface);
        textCount2.setTypeface(typeface);
        textView1.setTypeface(typeface);
        textView2.setTypeface(typeface);

        textBtnThrow.setTypeface(typeface);
        textBtnExit.setTypeface(typeface);
        textTitleHandRanking.setTypeface(typeface);

        imageTitleInGame.setImageResource(MainActivity.titleImageId);

        imageProfile1.setImageResource(R.drawable.i3);
        imageProfile2.setImageResource(MainActivity.profileImageId);

        gameLayout=(RelativeLayout)findViewById(R.id.gameLayout);

        if(backSkin!=0) {  //backSkin=0이면 하늘색 default. 다른 거면 gameSkin(backSkin에 대응대는 drawable내에 id값. 이용하여 변경)
            int gameSkin = getIntent().getExtras().getInt(GAMESKIN);
            gameLayout.setBackgroundResource(gameSkin);
        }

//        bettingView=new TextView[]{textCount1, textCount2};
//
//        thread1 = new Thread(runnable1);
//        thread2 = new Thread(runnable2);
//        threads = new Thread[]{thread1,thread2};

        checkBox1.setVisibility(View.INVISIBLE);
        checkBox2.setVisibility(View.INVISIBLE);
        checkBox3.setVisibility(View.INVISIBLE);
        checkBox4.setVisibility(View.INVISIBLE);
        checkBox5.setVisibility(View.INVISIBLE);
        checkBox6.setVisibility(View.INVISIBLE);
        checkBox7.setVisibility(View.INVISIBLE);
        checkBox8.setVisibility(View.INVISIBLE);
        checkBox9.setVisibility(View.INVISIBLE);
        checkBox10.setVisibility(View.INVISIBLE);

        initDice();


        //RecyclerView구현
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new HandRankingAdapter();
        recyclerView.setAdapter(adapter);

//        clickMeThread=new Thread(clickMeRunnable);
//        clickMeThread.start();

//        diceSkin=2;
//        voice=1;
        handSoundId_i=new int[7]; // 이거 안한거 같길래. (안하면 안될걸? 응ㅇ안됨) //7=handSoundId[voice].length
        winSoundId_i=new int[4];
        loseSoundId_i=new int[4];
        sp=new SoundPool(10, AudioManager.STREAM_ALARM, 0);
        for(int i=0; i<handSoundId[voice].length;i++){
            handSoundId_i[i]=sp.load(this, handSoundId[voice][i], 1);
        }
        chiriring=sp.load(this, R.raw.chiriring, 0);
        //tang=sp.load(this, R.raw.tang2, 0);

        for(int i=0; i<winSoundId[voice].length; i++){
            winSoundId_i[i]=sp.load(this,winSoundId[voice][i],1);
        }

        for(int i=0; i<loseSoundId[voice].length; i++){
            loseSoundId_i[i]=sp.load(this,loseSoundId[voice][i],1);
        }


        //UserVO me=new UserVO("dealerCheon", "123", 32, 66, 12300, "타이틀", 100, 1, 1, 0, 0);
        me=vo; //아까 mainActivity에서 정의해서 받아온 UserVO 넣는중   //돌아갈때도 intent에 result줘서 넘길 필요 없이 vo에 다시 me!!저당
        setMyInfo(me);
        textId2.setText(userId);
        textRecord2.setText(win+"승/"+totalGame+"전");
        textGold2.setText(gold+"star");


        ComTask ct = new ComTask();
        ct.execute(userId);

        gameOvered=true;

        updateTitle();


    }//onCreate

    private void updateTitle() {
        switch (win) {
            case 0:
                title = "dan1";
                break;
            case 1:
                title = "dan2";
                break;
            case 2:
                title = "dan3";
                break;
            case 50:
                title = "mentor";
                break;
            case 55:
                title = "expert";
                break;
            case 60:
                title = "master";
                break;
            case 61:
                title = "grandmaster";
                break;
            case 62:
                title = "berserker";
                break;
            case 63:
                title = "warrior";
                break;
            case 64:
                title = "destroyer";
                break;
            default:
                break;
        }

        MainActivity.setTitleImageId(title);
        imageTitleInGame.setImageResource(MainActivity.titleImageId);


    }//updateTitle


    // 앱 종료하면 실행할 update 작업
    @Override
    protected void onDestroy() {
        updateTask ut = new updateTask();
        //ut.execute(me);
        ut.execute(comVO);//comVO는 게임이끝날때 업데이트, 내 정보는 앱이 종료될때 업데이트하자

        //vo=me; //바뀐 내 VO정보를 public static UserVO에 저장!
        setUserVO(me);//현재 매개변수 정보를 me에 저장
        setComInfo(comVO);//현재 상대 매개변수 정보를 comVO에 저장

        vo=me;

        Log.i(TAG,"game의 onDestroy에서 me.getGold의 값: "+me.getGold());

        Intent i=new Intent(this, MainActivity.class);
        startActivity(i);
        super.onDestroy();


    }
    // update 작업을 실행할 AsyncTask
    class updateTask extends AsyncTask<UserVO, Void, Void> {
        @Override
        protected Void doInBackground(UserVO... params) {
            SendByHttp(params[0]);
            return null;
        }
    }

    // AsyncTask 에서 사용할 메소드
    public void SendByHttp(UserVO vo) {
        String URL = "http://192.168.11.14:8081/Java00-Contact/update.jsp";
        DefaultHttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(URL + "?title=" + vo.getTitle() + "&win=" + vo.getWin() +
                "&lose=" + vo.getLose() + "&gold=" + vo.getGold() +
                "&totalGame=" + vo.getTotalGame() + "&gameOn=" + vo.getGameOn() +
                "&diceSkin=" + vo.getDiceSkin() + "&backSkin=" + vo.getBackSkin() +
                "&voice=" + vo.getVoice() + "&userId=" + vo.getUserId());
        try {
            client.execute(post);
        } catch (IOException e) {
            e.printStackTrace();
        }
        post.abort();
    }
    //////////////////////////////////////////////////////////////////////////////

    class ComTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String result = SendByHttp(params[0]);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Gson gson = new Gson();
            comVO = gson.fromJson(s, UserVO.class);
            setComInfo(comVO);
            //asyncTask라서(비동기식) 언제 setComInfo가 일어날지 확실하지 않으므로 onCreate에서가 아니고 여기서 setText해준다
            textId1.setText(userId_com);
            textRecord1.setText(win_com+"승/"+totalGame_com+"전");
            textGold1.setText(gold_com+"star");
            Toast.makeText(GameActivity.this, "유저 아이디:" + comVO.getUserId(), Toast.LENGTH_SHORT).show();
        }
    }//class ComTask

    public String SendByHttp(String userId) {
        BufferedReader reader = null;
        String URL = "http://192.168.11.14:8081/Java00-Contact/comSelect.jsp";
        DefaultHttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(URL + "?userId=" + userId);
        String result = "";
        try {
            HttpResponse response = client.execute(post);
            InputStream stream = response.getEntity().getContent();
            reader = new BufferedReader(new InputStreamReader(stream, "utf-8"));
            result = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            post.abort();
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }//SendByHttp


    private void soundPoolPractice(){

       // sp.play(soundId, 1.0F, 1.0F,  0,  0,  1.0F);

//        MediaPlayer player = MediaPlayer.create(this, R.raw.click1);
//        player.start();
    }



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

    /////////////////////////////////////////////////////////////////////////////////////
    public void onClickBtnThrow(View v){
        //clickMeThread.interrupt();
        soundPoolPractice();
        gameOvered=false;

//        Thread dice1TestThread=new Thread(dice3dThrowRunnable);
//         dice1TestThread.start();
        if(countDice==-1) {
            Intent i = new Intent(this, BettingActivity.class);
            startActivityForResult(i, REQ_CODE1);  //이액티비티가 게임시작 눌렀을때 먼저나올것
            textBtnThrow.setText("주사위 던지기");

            textCount1.setText(""); //초기화
            textCount2.setText("");
            textView1.setText("");
            textView2.setText("");


           // initComDice();// 가렸던 주사위 다시 보이도록?


        }else if(countDice==0){ //음 던졌을 때
            if(boss==true) {

                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(diceSkin==0){
                    diceRandomThrow(); // 주사위 던지기
                    setDiceInvisible(true);//컴퓨터 1,5번 주사위 가리기


                    //모든 사용자의 결과를 출력(내화면에&모든사람화면에)(출력(result)은 각자하기)
                    //textView1.setText(result(indexCom) + "    ");

                    //handRankingIndex_Com 설정위하여
                    result(indexCom);
                    handRankingIndex_Com=handRankingIndex;
                    handNum_Com[0]=handNum[0];
                    handNum_Com[1]=handNum[1];
                    //이거랑 handNum_Com=handNum 이랑은 다름. 통째로복사하면 주소값 넣어 버리는 거기 때문에 handNum이 바뀌면 같이 바뀜

                    textView1.setText("");
                    textView2.setText(result(indexMy) + "    ");



                    //족보에 표시하기
                    recyclerView.smoothScrollToPosition(8);
                    recyclerView.smoothScrollToPosition(handRankingIndex);//스크롤을 해당족보타입으로옮김
                    setHandTypeSelected(handRankingIndex);//해당족보타입이 하이라이티드 되도록

                    myCheckBoxVisible(true);  //체크박스 보이기
                    countDice++;

                    // 족보 소리내기
                    if(mute==false && handSoundIndex!=-1)sp.play(handSoundId_i[handSoundIndex],1,1,0,0,1);
                }
                else {  // 주사위 던지고/// 그 후에 1,5번 가리기, 족보 계산,출력, handRankingIndex, 체크박스 보이기 모두.
                    Thread dice1TestThread=new Thread(dice3dThrowRunnable);
                    dice1TestThread.start();
                }

                Log.i(TAG,handSoundIndex+"<-throw에서 스레드 밖 handSoundIndex");

                //모든 사용자의 웨이팅 스레드 시작
                thread3=new Thread(runnable3);
                thread3.start();
                thread4=new Thread(runnable4);
                thread4.start();

                //countDice++;  //그냥 스레드 안에 넣어버리게
            }


        }else if(countDice==1){ // 두번째에 선택후에 던질때
            if(btnThrowClicked==false || btnThrowClickedCom==false) {//TODO 인원늘어나면이것도
//                if ((Button) v == btnThrow1) {
//                    thread3.interrupt();
//                    btnThrowClickedCom = true;
//                    textCount1.setText("던져짐");  //이걸 runnable3의 핸들러로 코드 옮긴다
//                }
                if ((ImageView) v == btnThrow2) { //이미지뷰로 바뀜.
                    thread4.interrupt();
                    btnThrowClicked = true;
                    textCount2.setText("던져짐");
                }
                //TODO: 색깔바꿔서 버튼눌렀음을 표시
            }
            if (btnThrowClicked == true && btnThrowClickedCom == true){ ////전체 멤버들이 다 던졌을때
                //원래 how_many_players_throw써서 HOW_MANY_PLAYERS랑 비교했는데 그냥 위에저거로충분할듯

                //상대 주사위 보여도됨 이제
                setDiceInvisible(false);

                //상대 주사위 자동으로 묶기
                comCheckBoxSetChecked();

                //내 체크박스 안보이기
                myCheckBoxVisible(false);

                // 주사위 랜덤 던지기
                if(diceSkin==0) {
                    Thread slotMachineThread = new Thread(slotMachineRunnable);
                    slotMachineThread.start();
                }else{
                    Thread dice1TestThread=new Thread(dice3dThrowRunnable);
                    dice1TestThread.start();
                }

//                Thread afterSlotMachineThread= new Thread(afterSlotMachineRunnable);
//                afterSlotMachineThread.start();
                //쓰레드두개쓰는거슨 너무나~




            }//던져지는 경우
        }//두번째에 던질떄(countDice=1)



    }//onClickBtnThrow


    private void initDice(){

        if(diceSkin==0) {

            imageView1.setImageResource(R.drawable.dice1);
            imageView2.setImageResource(R.drawable.dice2);
            imageView3.setImageResource(R.drawable.dice3);
            imageView4.setImageResource(R.drawable.dice4);
            imageView5.setImageResource(R.drawable.dice5);
            imageView6.setImageResource(R.drawable.dice1);
            imageView7.setImageResource(R.drawable.dice2);
            imageView8.setImageResource(R.drawable.dice3);
            imageView9.setImageResource(R.drawable.dice4);
            imageView10.setImageResource(R.drawable.dice5);
        }else if(diceSkin==1){//황토색
            imageView1.setImageResource(R.drawable.v1_1);
            imageView2.setImageResource(R.drawable.v2_1);
            imageView3.setImageResource(R.drawable.v3_1);
            imageView4.setImageResource(R.drawable.v4_1);
            imageView5.setImageResource(R.drawable.v5_1);

            imageView6.setImageResource(R.drawable.v1_1);
            imageView7.setImageResource(R.drawable.v2_1);
            imageView8.setImageResource(R.drawable.v3_1);
            imageView9.setImageResource(R.drawable.v4_1);
            imageView10.setImageResource(R.drawable.v5_1);
        }
        else if(diceSkin==2){//블랙
            imageView1.setImageResource(R.drawable.b1_1);
            imageView2.setImageResource(R.drawable.b2_1);
            imageView3.setImageResource(R.drawable.b3_1);
            imageView4.setImageResource(R.drawable.b4_1);
            imageView5.setImageResource(R.drawable.b5_1);
            imageView6.setImageResource(R.drawable.b1_1);
            imageView7.setImageResource(R.drawable.b2_1);
            imageView8.setImageResource(R.drawable.b3_1);
            imageView9.setImageResource(R.drawable.b4_1);
            imageView10.setImageResource(R.drawable.b5_1);
        }
        else if(diceSkin==3){//화이트
            imageView1.setImageResource(R.drawable.w1_1);
            imageView2.setImageResource(R.drawable.w2_1);
            imageView3.setImageResource(R.drawable.w3_1);
            imageView4.setImageResource(R.drawable.w4_1);
            imageView5.setImageResource(R.drawable.w5_1);
            imageView6.setImageResource(R.drawable.w1_1);
            imageView7.setImageResource(R.drawable.w2_1);
            imageView8.setImageResource(R.drawable.w3_1);
            imageView9.setImageResource(R.drawable.w4_1);
            imageView10.setImageResource(R.drawable.w5_1);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==REQ_CODE1 && resultCode==RESULT_OK){
            Bundle bundle= data.getExtras();
            stake=bundle.getInt(STAKE);//판돈 필드변수에 전달
            countDice=0; // 게임시작 가능하게
            textStake.setText("판돈: "+stake+"star");
        }
        if(requestCode==REQ_CODE2 && resultCode==RESULT_OK){

            finish();
        }
    }

    private int [] handNum={0,0};//★★
    private int [] handNum_Com={0,0};//★★
    /////////////////////////////////////////////////////////////////////////////////////////////////
    public String result(int [] indexMy){
        int [] types={0,0,0,0,0,0};
        points = 0;
        handType="Nothing";
        handRankingIndex=8;

        int pairCount = 0;
        String onePairValue="";
        String twoPairValue;
        handSoundIndex=-1;

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
                    handNum[0]= i;
                    handSoundIndex=0 ;//int[] handSoundId_n에서 onePair 소리의 index가 0
                }
                if (pairCount >= 2) {// 투페어 15가지종류
                    twoPairValue=(i+1)+", "+onePairValue;  //큰것부터 적음
                    handType = twoPairValue+" Two Pairs";
                    handRankingIndex=6;

                    points= ((i+1)*4)+ (points * 1);  //9~29
                    //i+1이 기존 points보다 높은 값일 수밖에 없음
                    handNum[1]=i;
                    handSoundIndex=1;

                }


                for(int j=0; j<6; j++) {
                    if (types[j] == 3) {
                        pairCount--;
                        fullHouse=true;
                        handType = (j+1)+", "+(i+1)+" Full House";  //3개인 주사위숫자부터 적음
                        points = 50 + (j * 6) + (i * 1);  //51~84
                        handRankingIndex=2;
                        handNum[1]=j;
                        handSoundIndex=4;
                    }}
            }else if (types[i] == 3 && fullHouse==false) {
                handType = (i+1)+" Triple";
                points =(30+i);
                handRankingIndex=5;
                handNum[0]=i;
                handSoundIndex=2;

            }else if (types[i] == 4) {
                handType = (i+1)+" Four dice";
                points = 90+i;
                handRankingIndex=1;
                handNum[0]=i;
                handSoundIndex=5;

            }else if (types[i] == 5) {
                handType = (i+1)+" Five dice";
                points = 100+i;
                handRankingIndex=0;
                handSoundIndex=6;
            }
        }//for문 over

        if (types[0] == 1 && types[1] == 1 && types[2] == 1 && types[3] == 1 && types[4] == 1) {
            handType = "Low Straight";
            points =40;
            handRankingIndex=4;
            handSoundIndex=3;
        } else if (types[1] == 1 && types[2] == 1 && types[3] == 1 && types[4] == 1 && types[5] == 1) {
            handType = "High Straight";
            points =41;
            handRankingIndex=3;
            handSoundIndex=3;
        }
        return handType;
    }// end result

    /////////////////////////////////////////////////////////////////////////////////////////////////

    public static final String USERID_COM="userId_com";
    public static final String USERID="userId";
    public static final String HANDTYPE_COM="handType_com";
    public static final String HANDTYPE="handType";
//    public static final String IMAGEPROFILE_COM="imageProfile_com";
//    public static final String IMAGEPROFILE="imageProfile";

    public void comparePoint(){
        totalGame++;
        totalGame_com++;

        if(points>pointsCom){  //이겼을 경우
            
            gold +=stake; 
            gold_com-=stake;
            win++;
            lose_com++;

            intentToResultActivity(WinActivity.class);

        } else if(points<pointsCom){
           
            gold -=stake;
            gold_com +=stake;
            lose++;
            win_com++;

            intentToResultActivity(LoseActivity.class);

        } else if(points==pointsCom){
            Toast.makeText(this, "DRAW", Toast.LENGTH_SHORT).show();
            gameOvered=true;
        }
    }// end result point

    private void intentToResultActivity(Class<?> cls){

        Intent intent=new Intent(this, cls);
        intent.putExtra(STAKE, stake);
        intent.putExtra(USERID_COM, userId_com);
        intent.putExtra(USERID, userId);
        intent.putExtra(HANDTYPE_COM, HAND_RANKING[handRankingIndex_Com]);
        intent.putExtra(HANDTYPE, HAND_RANKING[handRankingIndex]);

        intent.putExtra(MUTE,mute);
        intent.putExtra(VOICE, voice);

        startActivityForResult(intent, REQ_CODE2);
        gameOvered=true;

    }//intentToResultActivity

    private CheckBox[] comCheckBoxes={checkBox1, checkBox2, checkBox3, checkBox4, checkBox5};

    private void comCheckBoxSetChecked(){
        //index배열 이용하는 것보다 그냥 새로 만드는게속편할듯
        switch (handRankingIndex_Com){
            case 0: //five of a kind
            case 3: //high straight
            case 4: //low straight
//                    for(int i=0;i<comCheckBoxes.length;i++)
//                        comCheckBoxes[i].setChecked(true);
                checkBox1.setChecked(true);
                checkBox2.setChecked(true);
                checkBox3.setChecked(true);
                checkBox4.setChecked(true);
                checkBox5.setChecked(true);
                break;
            case 1: //four of a kind
            case 5: //tripple
            case 7: //one pair
//                    for(int i=0;i<comCheckBoxes.length;i++)
//                        if(indexCom[i]==handNum_Com[0]  )
//                            comCheckBoxes[i].setChecked(true);
                if(indexCom[0]==handNum_Com[0]  ) checkBox1.setChecked(true);
                if(indexCom[1]==handNum_Com[0]  ) checkBox2.setChecked(true);
                if(indexCom[2]==handNum_Com[0]  ) checkBox3.setChecked(true);
                if(indexCom[3]==handNum_Com[0]  ) checkBox4.setChecked(true);
                if(indexCom[4]==handNum_Com[0]  ) checkBox5.setChecked(true);
                break;

            case 6: //two pair
            case 2: //full house
//                    for(int i=0;i<comCheckBoxes.length;i++)
//                        if(indexCom[i]==handNum_Com[0]  ||indexCom[i]==handNum_Com[1])
//                            comCheckBoxes[i].setChecked(true);
                if(indexCom[0]==handNum_Com[0]  || indexCom[0]==handNum_Com[1]) checkBox1.setChecked(true);
                if(indexCom[1]==handNum_Com[0]  || indexCom[1]==handNum_Com[1]) checkBox2.setChecked(true);
                if(indexCom[2]==handNum_Com[0]  || indexCom[2]==handNum_Com[1]) checkBox3.setChecked(true);
                if(indexCom[3]==handNum_Com[0]  || indexCom[3]==handNum_Com[1]) checkBox4.setChecked(true);
                if(indexCom[4]==handNum_Com[0]  || indexCom[4]==handNum_Com[1]) checkBox5.setChecked(true);
                break;
            case 8:
                break;


        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////
//    public void onClickBtnDie(View v){
//
//        betting="다이";
//        bettingResult(betting);
//        gameOver();
//    }
//
//    public void onClickBtnCall(View v){
//        betting="콜!";
//        bettingResult(betting);
//    }
//
//    public void onClickBtnQuarter(View v){
//        betting="쿼터";
//        bettingResult(betting);
//    }
//
//    public void onClickBtnHalf(View v){
//        betting="하프";
//        bettingResult(betting);
//    }

    public void onClickBtnExit(View v){
        //finishRunnable
        textBtnExit.setText("나가기 예약");
        Thread exitThread=new Thread(exitRunnble);
        exitThread.start();

    }

    private void bettingResult(String betting){

        for(int i=0; i<HOW_MANY_PLAYERS; i++){ //TODO:나중에 숫자 늘리려고 for문으로 처리하는중
            if(i==onGoingGamer){
                if(onGoingGamer==0) {
                    textCount1.setText(betting);
                    thread1.interrupt();
//                    bettingView[0].setText(betting);
//                    threads[0].interrupt();  ///잉의문
                }else if(onGoingGamer==1){
                    textCount2.setText(betting);
                    thread2.interrupt();
                }
            }
        }//for문
    }//bettingResult

    private void gameOver(){

        //TODO: 여러명을 비교해야지

        //layout 흑백처리(졌을경우), (근데얘는 중간에 다이했을때도 해야됨. 여러명되면.TODO)
        //이겼다는 표시. 족보 표시!(주사위에)
        //얼마 얻었는지. 잃었는지.
        // 주사위 초기화

        //게임새로 시작하기 전에 finish 검사
        gameOvered=true;
    }//gameOver





    //////////////////////////////////////////////////////////////////////////////////////////////////

    public void onClickBtnStartBetting(View v) {
        thread1=new Thread(runnable1);  // 객체 새로 생성안하고 다시 start만 부르면 죽어버림(잉.?)
        thread1.start();



    }

    private Handler clickMeHandler1_btnThrow=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            textBtnThrow.setTextColor(Color.BLACK);
            textBtnThrow.setTextSize(25);
        }
    };
    private Handler clickMeHandler2_btnThrow=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            textBtnThrow.setTextColor(Color.BLACK);
            textBtnThrow.setTextSize(18);
        }
    };

    Runnable clickMeRunnable =new Runnable() {
        @Override
        public void run() {
            while(true){

                try {
                    Thread.sleep(500);
                    clickMeHandler1_btnThrow.sendEmptyMessage(0);
                    Thread.sleep(500);
                    clickMeHandler2_btnThrow.sendEmptyMessage(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }
    };

    //뜬금없이 멤버변수 뙇
    private Handler handler1=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle=msg.getData();
            String str=bundle.getString("key_cnt");
            textCount1.setText(str);
        }
    };

    private Handler handler2=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle=msg.getData();
            String str=bundle.getString("key_cnt");
            textCount2.setText(str);
        }
    };

    Runnable runnable1=new Runnable() {
        @Override
        public void run() {
            onGoingGamer=0;  //현재 1번 플레이어가 베팅중

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //Message message =handler.obtainMessage();
            Bundle bundle=new Bundle();
            int i=8;
            while(true) {

                Message message = handler1.obtainMessage(); //얘를 밖에다 놓으면 안돌아가 //메세지를 넣을 때마다 새로 객체생성해줘야한단소리
                //Looper가 (메세지큐에서) sendMessage를 하면 Message객체도 반납해버리는 식인듯?
                bundle.putString("key_cnt", i + "");
                message.setData(bundle);
                handler1.sendMessage(message);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    thread2=new Thread(runnable2);
                    thread2.start();
                    break;
                }
                i--;

                if (i == 0) {
                    betting = "Die";
                    message = handler1.obtainMessage();
                    bundle.putString("key_cnt", betting);//cnt로그냥적음...
                    message.setData(bundle);
                    handler1.sendMessage(message);

                    //TODO: 인원늘어나면 if문으로 걸러서 그다음 thread불러야
                    thread2=new Thread(runnable2);
                    thread2.start();

                    break;
                }
            }// while문

        }//run
    };//Runnable1


    Runnable runnable2=new Runnable() { //아니면 나중에 핸들러에서 멤버변수(int a 같은거 )정해놓고 a가 1일때는 textView1을 변화시키는 방식으로.
        @Override
        public void run() {
            onGoingGamer=1;  //현재 2번 플레이어가 베팅중

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Bundle bundle = new Bundle();
            int i = 8;
            while (true) {

                Message message = handler2.obtainMessage();
                bundle.putString("key_cnt", i + "");
                message.setData(bundle);
                handler2.sendMessage(message);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }

                i--;

                if (i == 0) {
                    betting = "Die";
                    message = handler2.obtainMessage();
                    bundle.putString("key_cnt", betting);
                    message.setData(bundle);
                    handler2.sendMessage(message);
                    //TODO: 인원늘어나면 if문으로 걸러서 그다음 thread불러야


                    break; //while문나가기->thread종료
                }//if문
            }//while 문
        }//run


    };//Runnable2
////////////////////////////////////////////////////////////////////////////////////////////

    private Handler handler3=new Handler(){
        @Override
        public void handleMessage(Message msg) {

            textCount1.setText("던져짐!");
            //TODO: 여기서 throw버튼색깔도죽이던가 말던가~
        }
    };

    private Handler handler4=new Handler(){
        @Override
        public void handleMessage(Message msg) {

            textCount2.setText("던져짐!");
            //TODO: 여기서 throw버튼색깔도죽이던가 말던가~
        }
    };

    private Handler clickBtnThrow1Handler= new Handler(){
        @Override
        public void handleMessage(Message msg) {
            btnThrowClickedCom = true;
            onClickBtnThrow(null);  //헐 !! 버튼.퍼폼클릭드 보다 이게 좋음!!★(null대신 딴거넣어봣는데 망가짐)
            textCount1.setText("던져짐");

        }
    };

    private Handler clickBtnThrow2Handler= new Handler(){
        @Override
        public void handleMessage(Message msg) {
            btnThrow2.performClick();
        }
    };

    Runnable runnable3=new Runnable() {
        @Override
        public void run() {

            double r=Math.random();//0~1
            int randomNum=(int)(r*5)+2;//0~5(0,1,2,3,4)+2 -> 2,3,4,5,6중 하나 ;


            Bundle bundle = new Bundle();
            int i = 10;
            while (true) {

                Message message = handler1.obtainMessage();
                bundle.putString("key_cnt", i + "");
                message.setData(bundle);
                handler1.sendMessage(message);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    // handler3.sendEmptyMessage(0); //이게 던져짐!하는애인데 사실그냥 버튼에 설정하면 되지 않을까
                    break;
                }

                i--;

                if (i == randomNum) { //randomNum이 0이면 8초를 다쓴것.
                    clickBtnThrow1Handler.sendEmptyMessage(0);
                    break; //while문나가기->thread종료
                }//if문
            }//while 문


        }
    };//runnable3

    Runnable runnable4=new Runnable() {
        @Override
        public void run() {

            Bundle bundle = new Bundle();
            int i = 10;
            while (true) {

                Message message = handler2.obtainMessage();
                bundle.putString("key_cnt", i + "");
                message.setData(bundle);
                handler2.sendMessage(message);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    // handler4.sendEmptyMessage(0);
                    break;
                }

                i--;

                if (i == 0) {
                    clickBtnThrow2Handler.sendEmptyMessage(0);
                    break; //while문나가기->thread종료
                }//if문
            }//while 문

        }
    };//runnable4

    private Handler slotHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            diceRandomThrow();
        }
    };

    private Handler afterSlotMachineHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {  //초기화 작업 겸용하는 핸들러
            textView1.setText(result(indexCom) + "    ");
            handRankingIndex_Com=handRankingIndex;

            //TODO: point를 DB에 넣어
            pointsCom = points; //비루한 코드 . 바꿔야지 나중에
            textView2.setText(result(indexMy) + "    ");
            // 족보 랭킹에 표시
            recyclerView.smoothScrollToPosition(handRankingIndex);
            setHandTypeSelected(handRankingIndex);

            //TODO: 각자 폰에서 다른 사람 점수 DB에서 받아와서 비교
            comparePoint();



        }
    };

    Handler initHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            countDice = -1;  //초기화
//            textCount1.setText("");
//            textCount2.setText("");
//            textView1.setText("");
//            textView2.setText("");
            checkBoxChecked(false); //체크했던것 release
            myCheckBoxVisible(false);  //체크박스사라짐
            btnThrowClickedCom = false;
            btnThrowClicked = false;
//            gameOvered=true;
            updateTitle(); //타이틀 이미지 바꾸기

            textId2.setText(userId);
            textRecord2.setText(win+"승/"+totalGame+"전");
            textGold2.setText(gold+"star");

            textId1.setText(userId_com);
            textRecord1.setText(win_com+"승/"+totalGame_com+"전");
            textGold1.setText(gold_com+"star");


            //TODO: 결과볼수있게 잠시 기다려 줘야함
            textBtnThrow.setText("게임 시작");
            //gameOvered=false;
        }
    };

    Runnable slotMachineRunnable=new Runnable() {
        @Override
        public void run() {
            int i=0;
            runningOvered=false; //running시작
            while(true){
                slotHandler.sendEmptyMessage(0);
                try {
                    if(i>=0 && i<1000){
                        Thread.sleep(50);
                        i+=50;}
                    else if(i>=1000 && i<1600){
                        Thread.sleep(100);
                        i+=100; }
                    else if(i>=1600 && i<2500){
                        Thread.sleep(200);
                        i+=250;
                    }else if(i>=2500 && i<4001){
                        Thread.sleep(300);
                        i+=500;
                    }else if(i>=4001) break;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }//WHILE

            afterSlotMachineHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            initHandler.sendEmptyMessage(0);
        }
    };// slotMachineRunnable


    private Boolean runningOvered;
    Runnable afterSlotMachineRunnable =new Runnable() {
        @Override
        public void run() {
            while(true) {
                if(runningOvered==true) {
                    afterSlotMachineHandler.sendEmptyMessage(0);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    initHandler.sendEmptyMessage(0);
                    break;
                }
            }
        }
    };



    private Boolean gameOvered=false;

    Runnable exitRunnble =new Runnable() {
        @Override
        public void run() {
            while (true){
                if(gameOvered==true)finish();
            }
        }
    };//finishRunnable




    private void comCheckBoxVisible(Boolean visible){ //나중에 지울 메소드
        if(visible==true){
            checkBox1.setVisibility(View.VISIBLE);
            checkBox2.setVisibility(View.VISIBLE);
            checkBox3.setVisibility(View.VISIBLE);
            checkBox4.setVisibility(View.VISIBLE);
            checkBox5.setVisibility(View.VISIBLE);
        }else if(visible==false){
            checkBox1.setVisibility(View.INVISIBLE);
            checkBox2.setVisibility(View.INVISIBLE);
            checkBox3.setVisibility(View.INVISIBLE);
            checkBox4.setVisibility(View.INVISIBLE);
            checkBox5.setVisibility(View.INVISIBLE);
        }
    }//checkBoxVisible

    private void myCheckBoxVisible(Boolean visible){
        if(visible==true){
            checkBox6.setVisibility(View.VISIBLE);
            checkBox7.setVisibility(View.VISIBLE);
            checkBox8.setVisibility(View.VISIBLE);
            checkBox9.setVisibility(View.VISIBLE);
            checkBox10.setVisibility(View.VISIBLE);
        }else if(visible==false){
            checkBox6.setVisibility(View.INVISIBLE);
            checkBox7.setVisibility(View.INVISIBLE);
            checkBox8.setVisibility(View.INVISIBLE);
            checkBox9.setVisibility(View.INVISIBLE);
            checkBox10.setVisibility(View.INVISIBLE);
        }
    }

    private void checkBoxChecked(Boolean checked){
        if(checked==false){
            checkBox1.setChecked(false);
            checkBox2.setChecked(false);
            checkBox3.setChecked(false);
            checkBox4.setChecked(false);
            checkBox5.setChecked(false);
            checkBox6.setChecked(false);
            checkBox7.setChecked(false);
            checkBox8.setChecked(false);
            checkBox9.setChecked(false);
            checkBox10.setChecked(false);
        }

    }//checkBoxChecked

    public void diceRandomThrow(){
        if(checkBox1.isChecked()){
        } else {
            double r = Math.random();
            int index1 = (int) (6 * r);
            imageView1.setImageResource(IMAGE_ID[index1]);
            indexCom[0] = index1;//index1이 0이면 주사위 값은 1
        }
        if(checkBox2.isChecked()){
        } else {
            double r2 = Math.random();
            int index2 = (int) (6 * r2);
            imageView2.setImageResource(IMAGE_ID[index2]);
            indexCom[1] = index2;
        }
        if(checkBox3.isChecked()){
        } else {
            double r3 = Math.random();
            int index3 = (int) (6 * r3);
            imageView3.setImageResource(IMAGE_ID[index3]);
            indexCom[2] = index3;
        }
        if(checkBox4.isChecked()){
        } else {
            double r4 = Math.random();
            int index4 = (int) (6 * r4);
            imageView4.setImageResource(IMAGE_ID[index4]);
            indexCom[3] = index4;
        }
        if(checkBox5.isChecked()){
        } else {
            double r5 = Math.random();
            int index5 = (int) (6 * r5);
            imageView5.setImageResource(IMAGE_ID[index5]);
            indexCom[4] = index5;
        }
        if(checkBox6.isChecked()){
        } else {
            double r6 = Math.random();
            int index6 = (int) (6 * r6);
            imageView6.setImageResource(IMAGE_ID[index6]);
            indexMy[0] = index6;
        }
        if(checkBox7.isChecked()){
        } else {
            double r7 = Math.random();
            int index7 = (int) (6 * r7);
            imageView7.setImageResource(IMAGE_ID[index7]);
            indexMy[1] = index7;
        }
        if(checkBox8.isChecked()){
        } else {
            double r8 = Math.random();
            int index8 = (int) (6 * r8);
            imageView8.setImageResource(IMAGE_ID[index8]);
            indexMy[2] = index8;
        }
        if(checkBox9.isChecked()){
        } else {
            double r9 = Math.random();
            int index9 = (int) (6 * r9);
            imageView9.setImageResource(IMAGE_ID[index9]);
            indexMy[3] = index9;
        }
        if(checkBox10.isChecked()){
        } else {
            double r10 = Math.random();
            int index10 = (int) (6 * r10);
            imageView10.setImageResource(IMAGE_ID[index10]);
            indexMy[4] = index10;
        }

    }//diceRandomThrow()

    private void setDiceInvisible(boolean invisible){
        if(invisible==true) {
            //컴퓨터의 1번 5번 주사위 안보이게 설정
            imageView1.setImageResource(R.drawable.dice0);
            imageView5.setImageResource(R.drawable.dice0);
        }if(invisible==false) {//다시 보이기
            if(diceSkin==0) {
                imageView1.setImageResource(IMAGE_ID[indexCom[0]]);
                imageView5.setImageResource(IMAGE_ID[indexCom[4]]);
            }else{
                int [][] DICE=new int[][]{};
                if(diceSkin==1)DICE=V;
                else if(diceSkin==2)DICE=B;
                else if(diceSkin==3)DICE=W;
                imageView1.setImageResource(DICE[indexCom[0]][0]);
                imageView5.setImageResource(DICE[indexCom[4]][0]);
            }
        }
    }



    ////////////////////////////////RecyclerView구현////////////////////////

    public static final String[] HAND_RANKING={
            "Five of a kind",
            "Four of a kind",
            "Full House",
            "High Straight",
            "Low Straight",
            "Triple",
            "Two Pair",
            "One Pair",
            "Bust"
    };

    private int handRankingIndex=8;//0~8
    private int handRankingIndex_Com=8;
    private int positionSaved=0;//족보에 타입표시할 때 필요한 것

    class HandRankingViewHolder extends RecyclerView.ViewHolder{
        private  TextView textView;
        public HandRankingViewHolder(View itemView) {
            super(itemView);
            MyInfoFragment.setGlobalFont(GameActivity.this, itemView);
            textView = (TextView) itemView.findViewById(R.id.textHandRanking);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    textView.setBackgroundColor(Color.YELLOW);
                }
            });
        }

    }//class HandRankingViewHolder

    class HandRankingAdapter extends RecyclerView.Adapter<HandRankingViewHolder>{

        private SharedPreferences mPref;
        private SharedPreferences.Editor mEditor;
        private ArrayList<View> viewArrayList=new ArrayList<>();



        @Override
        public HandRankingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view=LayoutInflater.from(GameActivity.this).inflate(R.layout.my_simple_list,parent,false);
            HandRankingViewHolder viewHolder=new HandRankingViewHolder(view);
            viewArrayList.add(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(HandRankingViewHolder holder, int position) {
            holder.textView.setText((position+1)+". "+HAND_RANKING[position]);
        }

        @Override
        public int getItemCount() {
            return HAND_RANKING.length;
        }

    }//class HandRankingAdapter

    private void setHandTypeSelected(int position){
        recyclerView.findViewHolderForAdapterPosition(positionSaved).itemView.setBackgroundColor(Color.WHITE);
        recyclerView.findViewHolderForAdapterPosition(position).itemView.setBackgroundColor(Color.YELLOW);
        positionSaved=position;
    }//setHandTypeSelected

    Runnable dice3dThrowRunnable =new Runnable() {
        double r;
        int index;

        @Override
        public void run() {


            Bundle bundle=new Bundle();


            for(int j=0;j<5;j++) {
                r = Math.random();
                index = (int) (6 * r);
                bundle.putInt("comDice"+j, index);
                //indexCom[j]=index;
            }

            for(int j=0;j<5;j++) {
                r = Math.random();
                index = (int) (6 * r);
                bundle.putInt("myDice"+j, index);
                //indexMy[j]=index;
            }


        //////////////////////////////////////////////////////
            for(int i=0;i<15;i++){
                try {
                    if(i<9)Thread.sleep(30);
                    else if(i<11)Thread.sleep(50);
                    else Thread.sleep(60);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Message message=dice3dThrowHandler.obtainMessage();
                bundle.putInt("key_dice_height",i);  //i만 계속해서 다른 값을 넘겨줌(높이는 바꾸ㅕ야하니까)
                message.setData(bundle);
                dice3dThrowHandler.sendMessage(message);
            }//for

            afterDice3dThrowHandler.sendEmptyMessage(0);


            if(countDice==1){  //2번째 던졌을 경우에만
                afterSlotMachineHandler.sendEmptyMessage(0);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                initHandler.sendEmptyMessage(0);
            }else if(countDice==0) countDice++; //3d주사위 던져졌을때 0일경우만 스레드 안에서 countDice 처리하는중(...)

        }
    };

    Handler dice3dThrowHandler =new Handler(){
        @Override
        public void handleMessage(Message msg) {

            int index=0;
            Bundle bundle=msg.getData();
            int i= bundle.getInt("key_dice_height");

//            if(i==8 || i==10 || i==12)sp.play(tang, 1,1,0,0,1);
//            Log.i(TAG, tang+":  tang");

            int [][] DICE=new int[][]{};
            if(diceSkin==1)DICE=V;
            else if(diceSkin==2)DICE=B;
            else if(diceSkin==3)DICE=W;

            if(checkBox1.isChecked()){
            } else {
                index= bundle.getInt("comDice"+0);
                imageView1.setImageResource(DICE[index][i]);
                indexCom[0] = index;//index1이 0이면 주사위 값은 1
            }
            if(checkBox2.isChecked()){
            } else {
                index= bundle.getInt("comDice"+1);
                imageView2.setImageResource(DICE[index][i]);
                indexCom[1] = index;
            }
            if(checkBox3.isChecked()){
            } else {
                index= bundle.getInt("comDice"+2);
                imageView3.setImageResource(DICE[index][i]);
                indexCom[2] = index;
            }
            if(checkBox4.isChecked()){
            } else {
                index= bundle.getInt("comDice"+3);
                imageView4.setImageResource(DICE[index][i]);
                indexCom[3] = index;
            }
            if(checkBox5.isChecked()){
            } else {
                index= bundle.getInt("comDice"+4);
                imageView5.setImageResource(DICE[index][i]);
                indexCom[4] = index;
            }
            if(checkBox6.isChecked()){
            } else {
                index= bundle.getInt("myDice"+0);
                imageView6.setImageResource(DICE[index][i]);
                indexMy[0] = index;
            }
            if(checkBox7.isChecked()){
            } else {
                index= bundle.getInt("myDice"+1);
                imageView7.setImageResource(DICE[index][i]);
                indexMy[1] = index;
            }
            if(checkBox8.isChecked()){
            } else {
                index= bundle.getInt("myDice"+2);
                imageView8.setImageResource(DICE[index][i]);
                indexMy[2] = index;
            }
            if(checkBox9.isChecked()){
            } else {
                index= bundle.getInt("myDice"+3);
                imageView9.setImageResource(DICE[index][i]);
                indexMy[3] = index;
            }
            if(checkBox10.isChecked()){
            } else {
                index= bundle.getInt("myDice"+4);
                imageView10.setImageResource(DICE[index][i]);
                indexMy[4] = index;
            }

        }

    }; //dice3dThrowHandler

    Handler afterDice3dThrowHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(countDice==0)setDiceInvisible(true);
            else if(countDice==1)setDiceInvisible(false);

            //모든 사용자의 결과를 출력(내화면에&모든사람화면에)(출력(result)은 각자하기)
            //textView1.setText(result(indexCom) + "    ");

            //handRankingIndex_Com 설정위하여
            result(indexCom);
            handRankingIndex_Com=handRankingIndex;
            handNum_Com[0]=handNum[0];
            handNum_Com[1]=handNum[1];
            //이거랑 handNum_Com=handNum 이랑은 다름. 통째로복사하면 주소값 넣어 버리는 거기 때문에 handNum이 바뀌면 같이 바뀜

            textView1.setText("");
            textView2.setText(result(indexMy) + "    ");

            //족보에 표시하기
            recyclerView.smoothScrollToPosition(handRankingIndex);//스크롤을 해당족보타입으로옮김
            setHandTypeSelected(handRankingIndex);//해당족보타입이 하이라이티드 되도록

            myCheckBoxVisible(true);  //체크박스 보이기

            // 족보 소리내기
            if(mute==false && handSoundIndex!=-1)sp.play(handSoundId_i[handSoundIndex],1,1,0,0,1);
        }
    };//afterDice3dThrowHandler



    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //다시... 위에껀아까워서 못지우는중~
    final int[] V1={R.drawable.v1_1, R.drawable.v1_2, R.drawable.v1_3, R.drawable.v1_4, R.drawable.v1_5,
            R.drawable.v1_4, R.drawable.v1_3, R.drawable.v1_2, R.drawable.v1_1,
            R.drawable.v1_2, R.drawable.v1_1, R.drawable.v1_2, R.drawable.v1_1, R.drawable.v1_2, R.drawable.v1_1};

    final int[] V2={R.drawable.v2_1, R.drawable.v2_2, R.drawable.v2_3, R.drawable.v2_4, R.drawable.v2_5,
            R.drawable.v2_4, R.drawable.v2_3, R.drawable.v2_2, R.drawable.v2_1,
            R.drawable.v2_2, R.drawable.v2_1, R.drawable.v2_2, R.drawable.v2_1, R.drawable.v2_2, R.drawable.v2_1};

    final int[] V3={R.drawable.v3_1, R.drawable.v3_2, R.drawable.v3_3, R.drawable.v3_4, R.drawable.v3_5,
            R.drawable.v3_4, R.drawable.v3_3, R.drawable.v3_2, R.drawable.v3_1,
            R.drawable.v3_2, R.drawable.v3_1, R.drawable.v3_2, R.drawable.v3_1, R.drawable.v3_2, R.drawable.v3_1};

    final int[] V4={R.drawable.v4_1, R.drawable.v4_2, R.drawable.v4_3, R.drawable.v4_4, R.drawable.v4_5,
            R.drawable.v4_4, R.drawable.v4_3, R.drawable.v4_2, R.drawable.v4_1,
            R.drawable.v4_2, R.drawable.v4_1, R.drawable.v4_2, R.drawable.v4_1, R.drawable.v4_2, R.drawable.v4_1};

    final int[] V5={R.drawable.v5_1, R.drawable.v5_2, R.drawable.v5_3, R.drawable.v5_4, R.drawable.v5_5,
            R.drawable.v5_4, R.drawable.v5_3, R.drawable.v5_2, R.drawable.v5_1,
            R.drawable.v5_2, R.drawable.v5_1, R.drawable.v5_2, R.drawable.v5_1, R.drawable.v5_2, R.drawable.v5_1};

    final int[] V6={R.drawable.v6_1, R.drawable.v6_2, R.drawable.v6_3, R.drawable.v6_4, R.drawable.v6_5,
            R.drawable.v6_4, R.drawable.v6_3, R.drawable.v6_2, R.drawable.v6_1,
            R.drawable.v6_2, R.drawable.v6_1, R.drawable.v6_2, R.drawable.v6_1, R.drawable.v6_2, R.drawable.v6_1};




    //test
    int [][] V={V1, V2, V3, V4, V5, V6};
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////

    final int [] B1=  // Black 1번주사위
            {R.drawable.b1_1,  R.drawable.b1_2,  R.drawable.b1_3,  R.drawable.b1_4,  R.drawable.b1_5,
                    R.drawable.b1_4, R.drawable.b1_3, R.drawable.b1_2, R.drawable.b1_1,
                    R.drawable.b1_2, R.drawable.b1_1, R.drawable.b1_2, R.drawable.b1_1,R.drawable.b1_2, R.drawable.b1_1};
    final int [] B2=  // Black 2번주사위
            {R.drawable.b2_1,  R.drawable.b2_2,  R.drawable.b2_3,  R.drawable.b2_4,  R.drawable.b2_5,
                    R.drawable.b2_4, R.drawable.b2_3, R.drawable.b2_2, R.drawable.b2_1,
                    R.drawable.b2_2, R.drawable.b2_1, R.drawable.b2_2, R.drawable.b2_1,R.drawable.b2_2, R.drawable.b2_1};
    final int [] B3=  // Black 3번주사위
            {R.drawable.b3_1,  R.drawable.b3_2,  R.drawable.b3_3,  R.drawable.b3_4,  R.drawable.b3_5,
                    R.drawable.b3_4, R.drawable.b3_3, R.drawable.b3_2, R.drawable.b3_1,
                    R.drawable.b3_2, R.drawable.b3_1, R.drawable.b3_2, R.drawable.b3_1, R.drawable.b3_2, R.drawable.b3_1};
    final int [] B4=  // Black 4번주사위
            {R.drawable.b4_1,  R.drawable.b4_2,  R.drawable.b4_3,  R.drawable.b4_4,  R.drawable.b4_5,
                    R.drawable.b4_4, R.drawable.b4_3, R.drawable.b4_2, R.drawable.b4_1,
                    R.drawable.b4_2, R.drawable.b4_1, R.drawable.b4_2, R.drawable.b4_1, R.drawable.b4_2, R.drawable.b4_1};
    final int [] B5=  // Black 5번주사위
            {R.drawable.b5_1,  R.drawable.b5_2,  R.drawable.b5_3,  R.drawable.b5_4,  R.drawable.b5_5,
                    R.drawable.b5_4, R.drawable.b5_3, R.drawable.b5_2, R.drawable.b5_1,
                    R.drawable.b5_2, R.drawable.b5_1, R.drawable.b5_2, R.drawable.b5_1, R.drawable.b5_2, R.drawable.b5_1};
    final int [] B6=  // Black 6번주사위
            {R.drawable.b6_1,  R.drawable.b6_2,  R.drawable.b6_3,  R.drawable.b6_4,  R.drawable.b6_5,
                    R.drawable.b6_4, R.drawable.b6_3, R.drawable.b6_2, R.drawable.b6_1,
                    R.drawable.b6_2, R.drawable.b6_1, R.drawable.b6_2, R.drawable.b6_1, R.drawable.b6_2, R.drawable.b6_1};

    int [][] B={B1, B2, B3, B4, B5, B6};

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////

    final int [] W1=  // White 1번주사위
            {R.drawable.w1_1,  R.drawable.w1_2,  R.drawable.w1_3,  R.drawable.w1_4,  R.drawable.w1_5,
                    R.drawable.w1_4, R.drawable.w1_3, R.drawable.w1_2, R.drawable.w1_1,
                    R.drawable.w1_2, R.drawable.w1_1, R.drawable.w1_2, R.drawable.w1_1, R.drawable.w1_2, R.drawable.w1_1};
    final int [] W2=  // White 2번주사위
            {R.drawable.w2_1,  R.drawable.w2_2,  R.drawable.w2_3,  R.drawable.w2_4,  R.drawable.w2_5,
                    R.drawable.w2_4, R.drawable.w2_3, R.drawable.w2_2, R.drawable.w2_1,
                    R.drawable.w2_2, R.drawable.w2_1, R.drawable.w2_2, R.drawable.w2_1, R.drawable.w2_2, R.drawable.w2_1};
    final int [] W3=  // White 3번주사위
            {R.drawable.w3_1,  R.drawable.w3_2,  R.drawable.w3_3,  R.drawable.w3_4,  R.drawable.w3_5,
                    R.drawable.w3_4, R.drawable.w3_3, R.drawable.w3_2, R.drawable.w3_1,
                    R.drawable.w3_2, R.drawable.w3_1, R.drawable.w3_2, R.drawable.w3_1, R.drawable.w3_2, R.drawable.w3_1};
    final int [] W4=  // White 4번주사위
            {R.drawable.w4_1,  R.drawable.w4_2,  R.drawable.w4_3,  R.drawable.w4_4,  R.drawable.w4_5,
                    R.drawable.w4_4, R.drawable.w4_3, R.drawable.w4_2, R.drawable.w4_1,
                    R.drawable.w4_2, R.drawable.w4_1, R.drawable.w4_2, R.drawable.w4_1, R.drawable.w4_2, R.drawable.w4_1};
    final int [] W5=  // White 5번주사위
            {R.drawable.w5_1,  R.drawable.w5_2,  R.drawable.w5_3,  R.drawable.w5_4,  R.drawable.w5_5,
                    R.drawable.w5_4, R.drawable.w5_3, R.drawable.w5_2, R.drawable.w5_1,
                    R.drawable.w5_2, R.drawable.w5_1, R.drawable.w5_2, R.drawable.w5_1, R.drawable.w5_2, R.drawable.w5_1};
    final int [] W6=  // White 6번주사위
            {R.drawable.w6_1,  R.drawable.w6_2,  R.drawable.w6_3,  R.drawable.w6_4,  R.drawable.w6_5,
                    R.drawable.w6_4, R.drawable.w6_3, R.drawable.w6_2, R.drawable.w6_1,
                    R.drawable.w6_2, R.drawable.w6_1, R.drawable.w6_2, R.drawable.w6_1, R.drawable.w6_2, R.drawable.w6_1};

    int W[][]= {W1, W2, W3, W4, W5, W6};


    ///////////////////////////////////////////////////////////////////////////////////////////////////////


    public static SoundPool sp;
    private int handSoundIndex;  //0:onepair 1:twoPair .....
    private int[] handSoundId_i;
    public static int[] winSoundId_i;
    public static int[] loseSoundId_i;
    private Boolean mute=false;
    public static final String MUTE="mute";
    private ImageView imageSound;
    private int chiriring, tang;

    // 희석님
    private int[] handSoundId_0={R.raw.onepair_0, R.raw.twopair_0, R.raw.triple_0, R.raw.straight_0,
            R.raw.fullhouse_0, R.raw.fourdice_0, R.raw.fivedice_0};
    private int[] winSoundId_0={R.raw.win1_0, R.raw.win2_0, R.raw.win3_0};
    private int[] loseSoundId_0={R.raw.lose1_0, R.raw.lose2_0, R.raw.lose3_0, R.raw.lose4_0};

    // 용욱
    private int[] handSoundId_1={R.raw.onepair_1, R.raw.twopair_1, R.raw.triple_1, R.raw.straight_1, R.raw.fullhouse_1,
            R.raw.fourdice_1, R.raw.fivedice_1};
    private int[] winSoundId_1={R.raw.win1_1, R.raw.win2_1, R.raw.win3_1};
    private int[] loseSoundId_1={R.raw.lose_1, R.raw.lose2_1};

    // 호영
    private int[] handSoundId_2={ R.raw.onepair_2, R.raw.twopair_2, R.raw.triple_2, R.raw.straight_2,
            R.raw.fullhouse_2, R.raw.fourdice_2, R.raw.fivedice_2};
    private int[] winSoundId_2={R.raw.win1_2, R.raw.win2_2, R.raw.win3_2};
    private int[] loseSoundId_2={R.raw.lose1_2, R.raw.lose2_2};

    //for All
    //private int voice=0; //0: 희석, 1: 용욱, 2: 호영, 3: 용선, 4: 한결
    private int[][] handSoundId= {handSoundId_0, handSoundId_1, handSoundId_2};
    private int[][] winSoundId= {winSoundId_0, winSoundId_1, winSoundId_2};
    private int[][] loseSoundId= {loseSoundId_0, loseSoundId_1, loseSoundId_2};

    public void onClickImageSound(View view) {

        if(mute==false){
            imageSound.setImageResource(R.drawable.icon_1024_3_04);
            mute=true;
        }else if(mute==true){
            imageSound.setImageResource(R.drawable.icon_1024_3_03);
            mute=false;
        }
    }

}
