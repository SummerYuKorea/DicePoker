package edu.android.and_dicepoker;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import static edu.android.and_dicepoker.MainActivity.vo;


/**
 *
 * A simple {@link Fragment} subclass.
 */
    // textUserId --> 유저 아이디
    // textGold --> 가지고 있는 골드
    // onClickBtnStart --> 게임 시작 버튼( 이미지)
public class GameStartFragment extends Fragment {

    public RelativeLayout gameStartLayout;
    private ImageView imageTitleInGameStart;
    private TextView textUserIdInGameStart;
    private static TextView textGoldInGameStart;

    public GameStartFragment() {
        // Required empty public constructorㅜ
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_game_start, container, false);

        MyInfoFragment.setGlobalFont(getContext(),view);
//        TextView textStartStar =(TextView) view.findViewById(R.id.textStartStar);
//        textStartStar.setTypeface(Typeface);

        imageTitleInGameStart=(ImageView)view.findViewById(R.id.imageTitleInGameStart);
        textUserIdInGameStart=(TextView)view.findViewById(R.id.textUserIdInGameStart);
        textGoldInGameStart=(TextView)view.findViewById(R.id.textGoldInGameStart);


        imageTitleInGameStart.setImageResource(MainActivity.titleImageId);
        textUserIdInGameStart.setText(vo.getUserId());
        textGoldInGameStart.setText("X "+vo.getGold());


        gameStartLayout = (RelativeLayout)view.findViewById(R.id.gameStartLayout);

        return view;
    }

    public static void setTextGoldInGameStart(){
        textGoldInGameStart.setText("X "+vo.getGold());
    }


}
