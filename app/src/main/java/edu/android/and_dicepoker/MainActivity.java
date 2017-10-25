package edu.android.and_dicepoker;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

import static edu.android.and_dicepoker.GameActivity.TAG;
import static edu.android.and_dicepoker.GameActivity.gold;
import static edu.android.and_dicepoker.LoginActivity.USERVO;
import static edu.android.and_dicepoker.MyInfoFragment.variableForAchievement;

public class MainActivity extends AppCompatActivity
        implements AchievementFragment.OnAchievementSelectedListener,
             StoreFragment.OnStoreSelectedListener, StoreDlgFragment.OnAddItemListener,
             GameTreeFragment.OnGameTreeSelectedListener,MyInfoSetDlgFragment.OnSetItemListener,
             MyInfoFragment.MyInfoChangeSkinListener {

    //private TextView textView;
    private static final String TYPEFACE_NAME = "BMJUA_ttf.ttf";
    private Typeface typeface = null;
//    private ImageView imageStart;
//    private boolean LoginTrue = false;

//    private ImageView imageView2;
//    private Fragment fragment;

    //아이템 리스트에서 리사이클러뷰의 포지션 저장
    private int itemPosition=0;

    public static int price=0;

    public static int appliedSkin;  //게임 activity에 넘길 때쓰려고 public
    private int appliedSkinTab;
    public static final String GAMESKIN="gameSkin";
    private final int REQ_CODE3=3; //음 이것도 게임액티비티?


    private RelativeLayout activity_achievement;
    private RelativeLayout fragmentStore;
    private TextView textTitleAchievement;
    private TextView textTitleStore;
    private TextView textStoreStar;



    public static UserVO vo=new UserVO();
    public static int titleImageId, profileImageId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadTypeface();
        //Login에서 넘겨준 vo 정보 받아오는중
        //TODO:주석 풀기/묶기
        if(getIntent().getExtras()!=null){
            vo=(UserVO)(getIntent().getExtras().getSerializable(USERVO));
        }

        setTitleImageId(vo.getTitle());//왜구지메소드로 넘겼냐면 나중에 게임중에 타이틀 업그레이드 될때 저코드 또 쓰려고


        switch (vo.getGameOn()){
            case 1: profileImageId=R.drawable.profile_by;break;
            case 2: profileImageId=R.drawable.profile_gy;break;
            case 3: profileImageId=R.drawable.profile_jy;break;
            case 4: profileImageId=R.drawable.profile_sh;break;
            case 5: profileImageId=R.drawable.profile_sl;break;
            case 6: profileImageId=R.drawable.profile_sz;break;
            default: profileImageId=R.drawable.i3;break;
        }

        setContentView(R.layout.activity_main);


       // imageView2=((MyInfoFragment)fragment).item3;

        /** TabHost ID */
        final TabHost tabHost = (TabHost)findViewById(android.R.id.tabhost);
        tabHost.setup();//


        /** 새로운 탭을 추가하기 위한 TabSpect */
        TabSpec firstTabSpec = tabHost.newTabSpec("tid1");
        TabSpec secondTabSpec = tabHost.newTabSpec("tid1");
        TabSpec thirdTabSpec = tabHost.newTabSpec("tid1");
        TabSpec myInfoTabSpec = tabHost.newTabSpec("tid1");


        firstTabSpec.setIndicator("", getResources().getDrawable(R.drawable.icon_1024_2_01));
        firstTabSpec.setContent(R.id.tab1);
        secondTabSpec.setIndicator("", getResources().getDrawable(R.drawable.icon_1024_2_07));
        secondTabSpec.setContent(R.id.tab2);
        thirdTabSpec.setIndicator("",getResources().getDrawable(R.drawable.icon_1024_2_06));
        thirdTabSpec.setContent(R.id.tab3);
        myInfoTabSpec.setIndicator("",getResources().getDrawable(R.drawable.icon_120_04));
        myInfoTabSpec.setContent(R.id.tab4);



        /** 탭을 TabHost 에 추가한다 */
        tabHost.addTab(firstTabSpec);
        tabHost.addTab(secondTabSpec);
        tabHost.addTab(thirdTabSpec);
        tabHost.addTab(myInfoTabSpec);

        // TabHost 에 포함된 Tab의 색깔을 모두 바꾼다, 개별적용
//        tabHost.getTabWidget().getChildAt(0).setBackgroundColor(Color.parseColor("#534512"));
//        tabHost.getTabWidget().getChildAt(1).setBackgroundColor(Color.parseColor("#4E4E9C"));

        // 탭의 선택
        tabHost.getTabWidget().setCurrentTab(0);

//        fragment=null;



        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {

            @Override
            public void onTabChanged(String arg0) {

                setTabColor(tabHost);
            }
        });

        setTabColor(tabHost);

        // 스킨 변경 위한 찾아오기
        activity_achievement = (RelativeLayout) findViewById(R.id.activity_achievement);
        fragmentStore = (RelativeLayout) findViewById(R.id.fragmentStore);

        textTitleStore=(TextView)findViewById(R.id.textViewSb2);
        textTitleStore.setTypeface(typeface);

        textTitleAchievement=(TextView)findViewById(R.id.textViewSt2);
        textTitleAchievement.setTypeface(typeface);

        textStoreStar = (TextView)findViewById(R.id.textStoreStar);
        textStoreStar.setTypeface(typeface);
        textStoreStar.setText("X "+vo.getGold());



        if((vo.getBackSkin())!=0) {
            changeSkin(vo.getBackSkin()); //0일 경우만 디폴트로 하늘색.안바뀜
            setTabColor(tabHost);
        }

        ImageView imageStart = (ImageView)findViewById(R.id.imageStartInGameStart);
        imageStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {    //ohmygod fragment에서 이게됨???!?!?!?
                Intent intent = new Intent(MainActivity.this,GameActivity.class);
                intent.putExtra(GAMESKIN, appliedSkin);
                startActivityForResult(intent, REQ_CODE3);
                finish();

            }
        });// 게임액티비티로 넘어가는 인텐트

        Log.i(TAG,"메인의 onCreate이불리는중");

    }//onCreate

    public static void setTitleImageId(String title){
        switch (title){
            case "dan1" : titleImageId= R.drawable.dan1;break;
            case "dan2" : titleImageId= R.drawable.dan2;break;
            case "dan3" : titleImageId= R.drawable.dan3;break;
            case "mentor" : titleImageId= R.drawable.mentor;break;
            case "expert" : titleImageId= R.drawable.expert;break;
            case "master" : titleImageId= R.drawable.master;break;
            case "grandmaster" : titleImageId= R.drawable.grandmaster;break;

            case "berserker" : titleImageId= R.drawable.berserker;break;
            case "warrior" : titleImageId= R.drawable.warrior;break;
            case "destroyer" : titleImageId= R.drawable.destroyer;break;
            default:titleImageId=R.drawable.berserker;break;
        }

    }//setTitleImageId

    @Override
    protected void onDestroy() {
        updateTask ut = new updateTask();
        ut.execute(vo);
        //comVO는 게임이끝날때 업데이트, 내 정보는 앱이 종료될때 업데이트하자
        super.onDestroy();


    }
    ///////////////////////////////////////////////////////////////

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
    /////////////////////////////////////////////////////////////////////////

    //Change The Backgournd Color of Tabs
    public void setTabColor(TabHost tabhost) {

        for (int i = 0; i < tabhost.getTabWidget().getChildCount(); i++)
            tabhost.getTabWidget().getChildAt(i).setBackgroundColor(0xFFC0C0C0); //unselected

        if (appliedSkin != 0)
            tabhost.getTabWidget().getChildAt(tabhost.getCurrentTab()).setBackgroundResource(appliedSkinTab);
        else
            tabhost.getTabWidget().getChildAt(tabhost.getCurrentTab()).setBackgroundColor(0xFF87CEFA); //selected

    }

    private void loadTypeface(){
        if(typeface==null)
            typeface = Typeface.createFromAsset(getAssets(), TYPEFACE_NAME);     }



    @Override
    public void onAchievementSelected(int position) {


        if(position==0 && variableForAchievement==1){
            vo.setGold(vo.getGold()+100);
            textStoreStar.setText("X "+vo.getGold());

            MyInfoFragment.setTextGoldInMyInfo();
            GameStartFragment.setTextGoldInGameStart();

            Toast.makeText(this, "★100 star 증정★", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onStoreSelected(int position) {

        switch (position){

            //보이스 0,1,2,3,4
            case 0: price=1000;break;
            case 1: price=1100;break;
            case 2: price=1200;break;
            case 3: price=1300;break;
            case 4: price=1400;break;
            //스킨 0,1,2,3
            case 5: price=0;break;
            case 6: price=2000;break;
            case 7: price=3000;break;
            case 8: price=4000;break;
            //추가스킨 4,5,6,7
            case 9: price=5000;break;
            case 10: price=6000;break;
            case 11: price=7000;break;
            case 12: price=8000;break;
            //다이스스킨 1,2,3,4
            case 13: price=5000;break;
            case 14: price=6000;break;
            case 15: price=7000;break;
            case 16: price=8000;break;


        }

        if(price>vo.getGold()) {
            Log.i(TAG, "  price, gold : " + price + "  " + gold);
            Toast.makeText(this, "돈이 부족해 살 수 없습니다.", Toast.LENGTH_SHORT).show();
        }
        else {
            Log.i(TAG, "  price, gold : " + price + "  " + gold);
           // Toast.makeText(this, "스토어 " + (position + 1) + " 클릭", Toast.LENGTH_SHORT).show();
            StoreDlgFragment dlg = new StoreDlgFragment();
            dlg.show(getSupportFragmentManager(), "dlgStore");



            //아이템의 위치를 저장
            itemPosition = position;
        }

    }//onStoreSelected(StoreFragment의 callBack Interface내의 메소드)

    @Override
    public void onAddItem() {//다이알로그에서 yes를 눌렀을때
        FragmentManager fm=getSupportFragmentManager();
        Fragment fragment=fm.findFragmentById(R.id.fragment5);
        if (fragment != null && fragment instanceof MyInfoFragment) {
            ((MyInfoFragment) fragment).changeImage(itemPosition);
        }
        //구매할 경우 내 골드가 바뀜
        vo.setGold(vo.getGold()-price);
        textStoreStar.setText("X "+vo.getGold());

        MyInfoFragment.setTextGoldInMyInfo();
        GameStartFragment.setTextGoldInGameStart();
    }

    @Override
    public void onGameTreeSelected(int position) {

    }

    @Override
    public void onSetItem(int which) {
        switch (which) {
            case 0:
                FragmentManager fm=getSupportFragmentManager();
                Fragment fragment=fm.findFragmentById(R.id.fragment5);
                if (fragment != null && fragment instanceof MyInfoFragment) {
                    ((MyInfoFragment) fragment).changeImage(which);
                }
                //Toast.makeText(this, "1", Toast.LENGTH_SHORT).show();
                break;
            case 1:
                //Toast.makeText(this, "2", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                //Toast.makeText(this, "3", Toast.LENGTH_SHORT).show();
                break;
        }

    }//onSetItem

    @Override
    public void changeSkin(int skin) {

        switch (skin) {
            case 0:
                appliedSkin = R.drawable.backskin0;
                appliedSkinTab = R.drawable.backskin0;
                break;
            case 1:
                appliedSkin = R.drawable.backskin1;
                appliedSkinTab = R.drawable.tabskin1;
                break;
            case 2:
                appliedSkin = R.drawable.backskin2;
                appliedSkinTab = R.drawable.tabskin2;
                break;
            case 3:
                appliedSkin = R.drawable.backskin3;
                appliedSkinTab = R.drawable.tabskin3;
                break;
            case 4:
                appliedSkin = R.drawable.backskin4;
                appliedSkinTab = R.drawable.tabskin4;
                break;

            case 5:
                appliedSkin = R.drawable.backskin5;
                appliedSkinTab = R.drawable.tabskin5;
                break;
            case 6:
                appliedSkin = R.drawable.backskin6;
                appliedSkinTab = R.drawable.tabskin6;
                break;
            case 7:
                appliedSkin = R.drawable.backskin7;
                appliedSkinTab = R.drawable.tabskin7;
                break;
            default: appliedSkin=0;
                appliedSkinTab = 0;


        }

        //4번 탭
        FragmentManager fm=getSupportFragmentManager();
        Fragment fragment=fm.findFragmentById(R.id.fragment5);//탭4
        if (fragment != null && fragment instanceof MyInfoFragment) {
            //((MyInfoFragment) fragment).changeSkin(skin);
            ((MyInfoFragment) fragment).myInfoFrameLayout.setBackgroundResource(appliedSkin);

        }

        //3번 탭
        activity_achievement.setBackgroundResource(appliedSkin);

        //2번 탭
        fragmentStore.setBackgroundResource(appliedSkin);

        //1번 탭
        Fragment fragment1=fm.findFragmentById(R.id.fragment6); //탭1
        if (fragment1 != null && fragment1 instanceof GameStartFragment) {
            ((GameStartFragment) fragment1).gameStartLayout.setBackgroundResource(appliedSkin);
            Log.i("tag", "들어왔다");


        }


    }//changeSkin

//    public void onClickItem2(View view) {
//
//        AlertDialog.Builder builder =
//                new AlertDialog.Builder(this);
//        builder.setTitle("아이템 적용");
//        builder.setMessage("아이템을 적용하시겠습니까?");
//        builder.setPositiveButton("응", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(MainActivity.this,
//                        "적용됨!", Toast.LENGTH_SHORT).show();
//            }
//        });
//        builder.setNegativeButton("아니", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(MainActivity.this,
//                        "적용 취소...", Toast.LENGTH_SHORT).show();
//            }
//        });
////        builder.show();
//        AlertDialog dlg = builder.create();
//        dlg.show();
//
////        Toast.makeText(this, "된다!", Toast.LENGTH_SHORT).show();
//    }
//
//    private Boolean mute=false;
//    private ImageView imageSound;


}

