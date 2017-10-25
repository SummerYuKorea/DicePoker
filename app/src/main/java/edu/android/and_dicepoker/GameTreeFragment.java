package edu.android.and_dicepoker;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class GameTreeFragment extends Fragment {
    private OnGameTreeSelectedListener listener;

    public GameTreeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnGameTreeSelectedListener) {
            listener = (OnGameTreeSelectedListener) context;
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
        View view = inflater.inflate(R.layout.game_tree_list, container, false);

        // 생성된 View 객체에서 RecyclerView를 찾아서
        // LayoutManager와 Adapter를 설정
        RecyclerView rv = (RecyclerView) view.findViewById(R.id.game_tree_recycler);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(new GameTreeAdapter());

        return view;
    }

    // RecyclerView.ViewHolder를 상속받는 클래스 정의
    class GameTreeViewHolder extends RecyclerView.ViewHolder {

//        private ImageView imageView16;
        private TextView textViewTreeItem;

        public GameTreeViewHolder(View itemView) {
            super(itemView);
//            imageView16 = (ImageView) itemView.findViewById(R.id.imageView16);
            textViewTreeItem = (TextView) itemView.findViewById(R.id.textViewTreeItem);


            // itemView를 클릭했을 때 할 기능 - 리스너 등록
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        listener.onGameTreeSelected(position);
//                        Toast.makeText(getContext(), position + "클릭", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    } // end class GameTreeViewHolder

    // RecyclerView.Adapter를 상속받는 클래스 정의
    class GameTreeAdapter
            extends RecyclerView.Adapter<GameTreeViewHolder> {

        @Override
        public GameTreeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View itemView = inflater.inflate(R.layout.game_tree_item, parent, false);

            return new GameTreeViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(GameTreeViewHolder holder, int position) {
            GameTree d = GameTreeLab.getInstance().getGameTreeList().get(position);
//            holder.imageView16.setImageResource(d.getPhotoId());
            holder.textViewTreeItem.setText(d.getTree());
//            holder.textView300.setText(d.getBbb());


        }

        @Override
        public int getItemCount() {
            return GameTreeLab.getInstance().getGameTreeList().size();
        }
    }

    public interface OnGameTreeSelectedListener {
        public abstract void onGameTreeSelected(int position);
    }


}