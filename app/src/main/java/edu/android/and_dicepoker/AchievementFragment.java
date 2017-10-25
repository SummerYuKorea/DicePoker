package edu.android.and_dicepoker;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class AchievementFragment extends Fragment {
    private OnAchievementSelectedListener listener;

    public AchievementFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnAchievementSelectedListener) {
            listener = (OnAchievementSelectedListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.achievement_list, container, false);


        // 생성된 View 객체에서 RecyclerView를 찾아서
        // LayoutManager와 Adapter를 설정
        RecyclerView rv = (RecyclerView) view.findViewById(R.id.achievement_recycler);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(new DicePokerAdapter());

        return view;
    }

    // RecyclerView.ViewHolder를 상속받는 클래스 정의
    class DicePokerViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView16;
        private TextView textView200, textView300;

        public DicePokerViewHolder(View itemView) {
            super(itemView);
            MyInfoFragment.setGlobalFont(getContext(),itemView);
            imageView16 = (ImageView) itemView.findViewById(R.id.imageView16);
            textView200 = (TextView) itemView.findViewById(R.id.textView200);
            textView300 = (TextView) itemView.findViewById(R.id.textView300);

            // itemView를 클릭했을 때 할 기능 - 리스너 등록
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        listener.onAchievementSelected(position);
//                        Toast.makeText(getContext(), position + "클릭", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    } // end class DicePokerViewHolder

    // RecyclerView.Adapter를 상속받는 클래스 정의
    class DicePokerAdapter
            extends RecyclerView.Adapter<DicePokerViewHolder> {

        @Override
        public DicePokerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View itemView = inflater.inflate(R.layout.achievement_item, parent, false);

            return new DicePokerViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(DicePokerViewHolder holder, int position) {
            DicePoker d = DicePokerLab.getInstance().getDicePokerList().get(position);
            holder.imageView16.setImageResource(d.getPhotoId());
            holder.textView200.setText(d.getAaa());
            holder.textView300.setText(d.getBbb());


        }

        @Override
        public int getItemCount() {
            return DicePokerLab.getInstance().getDicePokerList().size();
        }
    }

    public interface OnAchievementSelectedListener {
        public abstract void onAchievementSelected(int position);
    }


} // end AchievementFragment