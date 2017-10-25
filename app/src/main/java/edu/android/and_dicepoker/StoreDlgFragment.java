package edu.android.and_dicepoker;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

/**
 * DialogFragment를 상속 받음
 * onCreateDialog()를 override
 */
public class StoreDlgFragment extends DialogFragment {

    //메인 액티비티 저장하기 위한 리스너 맴버변수
    private OnAddItemListener listener;

    public interface OnAddItemListener {
        void onAddItem();
    }

    public StoreDlgFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {

            super.onAttach(context);
            if (context instanceof OnAddItemListener) {
                listener = (OnAddItemListener) context;
            }

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity());
        builder.setTitle("아이템을");
        builder.setMessage("구매 하시겠습니까?");
        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(), "구매 성공...", Toast.LENGTH_SHORT).show();

                // ImageFragment의 메소드를 호출해서 이미지 변경
//                FragmentManager fm = getSupportFragmentManager();
//                MyInfoFragment fragment = new MyInfoFragment();
//                if (fragment != null && fragment instanceof MyInfoFragment) {
//                    ((MyInfoFragment) fragment).changeImage(position);
//                }

                listener.onAddItem();

            }
        });
        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(), "구매 취소...", Toast.LENGTH_SHORT).show();
            }
        });

        return builder.create();
    }
}
