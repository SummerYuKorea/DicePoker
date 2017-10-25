package edu.android.and_dicepoker;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyInfoSetDlgFragment extends DialogFragment {

    public interface OnSetItemListener {
        void onSetItem(int which);
    }

    // Listener 객체
    private OnSetItemListener listener;

    // 선택된 아이템(적용)을 저장하는 리스트
    private ArrayList<String> selections;


    public MyInfoSetDlgFragment() {
        // Required empty public constructor
    }

    public static MyInfoSetDlgFragment newInstance() {
        return new MyInfoSetDlgFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSetItemListener) {
            listener = (OnSetItemListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // 멤버 변수 리스트를 초기화
//        selections = new ArrayList<>();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("아이템 적용");

        final String[] items = getResources().getStringArray(R.array.items);

//        builder.setItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
//                setSelections(which, isChecked);
//
//            }
//        });
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(),
                        items[which],
                        Toast.LENGTH_SHORT).show();
                if (listener != null) {
                    listener.onSetItem(which);
                }
            }
        });



//        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                listener.onSetItem(selections);
//
//            }
//        });
//        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                listener.onSetItem(null);
//
//            }
//        });

        return builder.create();
    }  // end onCreateDialog()

//    private void setSelections(int pos, boolean checked) {
//        String[] items = getResources().getStringArray(R.array.items);
//        if (checked) {
//            // 체크했을 때 -> 리스트에 추가
//            selections.add(items[pos]);
//        } else {
//            //체크 해제했을 때  -> 리스트에서 삭제
//            selections.remove(items[pos]);
//        }
//
//    }
}