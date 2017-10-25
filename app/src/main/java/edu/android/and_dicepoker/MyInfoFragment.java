package edu.android.and_dicepoker;


import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;

import static android.view.View.VISIBLE;
import static edu.android.and_dicepoker.GameActivity.TAG;
import static edu.android.and_dicepoker.GameActivity.backSkin;
import static edu.android.and_dicepoker.GameActivity.diceSkin;
import static edu.android.and_dicepoker.GameActivity.gold;
import static edu.android.and_dicepoker.GameActivity.voice;
import static edu.android.and_dicepoker.MainActivity.vo;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyInfoFragment extends Fragment {

    public interface MyInfoChangeSkinListener {
        void changeSkin(int skin);

    }

    private HorizontalScrollView horiScroll;

    public ImageView item1,item2,item3,item4,item5,
            item6,item7,item8,item9,item10,item11,item12,item13,item14,item15,item16,item17;
    private ImageView imageProfileInMyInfo ,imageTitleInMyInfo;

    private TextView textUserIdInMyInfo, textRecordInMyInfo;
    private static TextView textGoldInMyInfo;// 다른 클래스에서도 필요해서(->public static메소드안에서 씀)
//    private static final int[] IMAGE_IDS = {
//            R.drawable.elements_fire, R.drawable.elements_death,
//            R.drawable.elements_energy, R.drawable.elements_ice,
//            R.drawable.equipment_gloves, R.drawable.inventory_book,
//            R.drawable.inventory_bullet, R.drawable.inventory_key,
//            R.drawable.inventory_moneybag, R.drawable.equipment_helmet,
//    };

    public FrameLayout myInfoFrameLayout;



    public MyInfoFragment() {
        // Required empty public constructor
    }

    // factory 메소드 정의
    // 생성자를 대신하는 public static 메소드
    public static MyInfoFragment newInstance() {
        MyInfoFragment instance =  new MyInfoFragment();
        return instance;
    }

    private FragmentManager fm;
    private MyInfoChangeSkinListener listener;

    int x = 0;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fm=((MainActivity)context).getSupportFragmentManager();
        listener=(MyInfoChangeSkinListener)context;
    }

    public static void setGlobalFont(Context context,View view){
        if (view != null) {
            if (view instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) view;
                int len = vg.getChildCount();
                for (int i = 0; i < len; i++) {
                    View v = vg.getChildAt(i);
                    if (v instanceof TextView) {
                        ((TextView) v).setTypeface(Typeface.createFromAsset(context.getAssets(),
                                AchievementActivity.TYPEFACE_NAME));
                    }
                    setGlobalFont(context, v);
                }
            }
        }


    }//setGlobalFont

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_info, container, false);
        setGlobalFont(getContext(), view);

        item1 = (ImageView) view.findViewById(R.id.item1);
        item2 = (ImageView) view.findViewById(R.id.item2);
        item3 = (ImageView) view.findViewById(R.id.item3);
        item4 = (ImageView) view.findViewById(R.id.item4);
        item5 = (ImageView) view.findViewById(R.id.item5);
        item6 = (ImageView) view.findViewById(R.id.item6);
        item7 = (ImageView) view.findViewById(R.id.item7);
        item8 = (ImageView) view.findViewById(R.id.item8);
        item9 = (ImageView) view.findViewById(R.id.item9);
        item10 = (ImageView) view.findViewById(R.id.item10);
        item11 = (ImageView) view.findViewById(R.id.item11);
        item12 = (ImageView) view.findViewById(R.id.item12);
        item13 = (ImageView) view.findViewById(R.id.item13);
        item14 = (ImageView) view.findViewById(R.id.item14);
        item15 = (ImageView) view.findViewById(R.id.item15);
        item16 = (ImageView) view.findViewById(R.id.item16);
        item17 = (ImageView) view.findViewById(R.id.item17);

        horiScroll=(HorizontalScrollView)view.findViewById(R.id.horiScroll);

        myInfoFrameLayout = (FrameLayout)view.findViewById(R.id.myInfoFrameLayout);

        imageProfileInMyInfo=(ImageView) view.findViewById(R.id.imageProfileInMyInfo) ;
        imageTitleInMyInfo = (ImageView) view.findViewById(R.id.imageTitleInMyInfo);

        textUserIdInMyInfo = (TextView)view.findViewById(R.id.textUserIdInMyInfo);
        textRecordInMyInfo=(TextView)view.findViewById(R.id.textRecordInMyInfo);
        textGoldInMyInfo=(TextView)view.findViewById(R.id.textGoldInMyInfo);

        item1.setVisibility(View.INVISIBLE);
        item2.setVisibility(View.INVISIBLE);
        item3.setVisibility(View.INVISIBLE);
        item4.setVisibility(View.INVISIBLE);
        item5.setVisibility(View.INVISIBLE);
        item6.setVisibility(View.INVISIBLE);
        item7.setVisibility(View.INVISIBLE);
        item8.setVisibility(View.INVISIBLE);
        item9.setVisibility(View.INVISIBLE);
        item10.setVisibility(View.INVISIBLE);
        item11.setVisibility(View.INVISIBLE);
        item12.setVisibility(View.INVISIBLE);
        item13.setVisibility(View.INVISIBLE);
        item14.setVisibility(View.INVISIBLE);
        item15.setVisibility(View.INVISIBLE);
        item16.setVisibility(View.INVISIBLE);
        item17.setVisibility(View.INVISIBLE);


//        item1.setImageResource(0);
//        item2.setImageResource(0);
//        item3.setImageResource(0);
//        item4.setImageResource(0);
//        item5.setImageResource(0);
//        item6.setImageResource(0);
//        item7.setImageResource(0);
//        item8.setImageResource(0);
//        item9.setImageResource(0);
//        item10.setImageResource(0);

        imageTitleInMyInfo.setImageResource(MainActivity.titleImageId);
        imageProfileInMyInfo.setImageResource(MainActivity.profileImageId);

        textUserIdInMyInfo.setText(vo.getUserId());
        textRecordInMyInfo.setText("전적: "+vo.getWin()+"승/"+vo.getTotalGame()+"전");
        textGoldInMyInfo.setText("X "+vo.getGold());

        return view;
    }

//    public void change2(){
//        item2.setImageResource(R.drawable.elements_death);
//        Log.i("tag","change2에 들어옴");
//    }





    public static int variableForAchievement;
    public static void setTextGoldInMyInfo(){
        textGoldInMyInfo.setText("X "+vo.getGold());
    }


    public void changeImage(int position) {
        switch (position) {
            case 0:   //호영오빠목소리 voice=2
                Log.i(TAG, "  gold : " + "  " + gold);
                item1.setVisibility(VISIBLE);
                voice=2;
                variableForAchievement=1;
                break;
            case 1:   //용욱오빠목소리 voice=1
                item2.setVisibility(VISIBLE);
                voice=1;
                break;
            case 2:   //용선오빠목소리 voice=3
                item3.setVisibility(VISIBLE);
                voice=3;
                break;
            case 3:   //한결목소리 voice=4
                item4.setVisibility(VISIBLE);
                voice=4;
                break;
            case 4:  //희석님 여자목소리 voice=0
                item5.setVisibility(VISIBLE);
                voice=0;
                break;
            //////////////////////////////////////
            case 5://배경 스킨0 스카이블루 디폴트
                item6.setVisibility(VISIBLE);
                backSkin=0;
                listener.changeSkin(0);
                break;
            case 6://배경 스킨1 민트
                item7.setVisibility(VISIBLE);
                backSkin=1;
                listener.changeSkin(1);
                break;
            case 7://배경 스킨2 찐핑크
                item8.setVisibility(VISIBLE);
                backSkin=2;
                listener.changeSkin(2);
                break;
            case 8://배경 스킨3 하늘색카카오
                item9.setVisibility(VISIBLE);
                backSkin=3;
                listener.changeSkin(3);
                break;
            case 9://배경 스킨4
                item10.setVisibility(VISIBLE);
                backSkin=4;
                listener.changeSkin(4);
                break;
            case 10://배경 스킨5
                item11.setVisibility(VISIBLE);
                backSkin=5;
                listener.changeSkin(5);
                x=700;
                break;
            case 11://배경 스킨6
                item12.setVisibility(VISIBLE);
                backSkin=6;
                listener.changeSkin(6);
                x=700;
                break;
            case 12://배경 스킨7
                item13.setVisibility(VISIBLE);
                backSkin=7;
                listener.changeSkin(7);
                x=700;
                break;
            //////////////////////////////////////
            case 13: //주사위 스킨 White diceSkin=3
                item14.setVisibility(VISIBLE);
                diceSkin=3;
                x=700;
                break;
            case 14:  //주사위 스킨 Black diceSkin=2
                item15.setVisibility(VISIBLE);
                diceSkin=1;
                x=1000;
                break;
            case 15: //주사위 스킨 2d diceSkin=0
                item16.setVisibility(VISIBLE);
                diceSkin=0;
                x=1000;
                break;
            case 16:  //주사위 스킨 3d 황토색 diceSkin=1
                item17.setVisibility(VISIBLE);
                diceSkin=1;
                x=1000;
                break;

        }//switch

        // 체계가 부족. GameActivity의 매개변수들에 값 업데이트 했으므로 vo에 다시 넣어주는중
        MainActivity.vo.setBackSkin(backSkin);
        MainActivity.vo.setDiceSkin(diceSkin);
        MainActivity.vo.setVoice(voice);

        horiScroll.smoothScrollTo(x,0);

    }//changeImage


}

