package edu.android.and_dicepoker;


import android.content.Context;
import android.graphics.Typeface;
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
public class StoreFragment extends Fragment {


    private StoreFragment.OnStoreSelectedListener listener;

    public StoreFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof StoreFragment.OnStoreSelectedListener) {
            listener = (StoreFragment.OnStoreSelectedListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
    private static final String TYPEFACE_NAME = "BMJUA_ttf.ttf";
    private Typeface typeface = null;

    private void loadTypeface(){
        if(typeface==null)
            typeface = Typeface.createFromAsset(getActivity().getAssets(), TYPEFACE_NAME);     }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        loadTypeface();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.store_list, container, false);

        // 생성된 View 객체에서 RecyclerView를 찾아서
        // LayoutManager와 Adapter를 설정
        RecyclerView rv = (RecyclerView) view.findViewById(R.id.store_recycler);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(new StoreAdapter());

        return view;
    }

    // RecyclerView.ViewHolder를 상속받는 클래스 정의
    class StoreViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView4;
        private TextView ribborntext1,textView4;

        public StoreViewHolder(View itemView) {
            super(itemView);
            MyInfoFragment.setGlobalFont(getContext(),itemView);
            imageView4 = (ImageView) itemView.findViewById(R.id.imageView4);
            ribborntext1 = (TextView) itemView.findViewById(R.id.ribborntext1);
            textView4 = (TextView) itemView.findViewById(R.id.textView4);

            // itemView를 클릭했을 때 할 기능 - 리스너 등록
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        listener.onStoreSelected(position);
                    }
                }
            });
        }
    } // end class StoreViewHolder

    // RecyclerView.Adapter를 상속받는 클래스 정의
    class StoreAdapter
            extends RecyclerView.Adapter<StoreFragment.StoreViewHolder> {

        @Override
        public StoreFragment.StoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View itemView = inflater.inflate(R.layout.store_item, parent, false);

            return new StoreFragment.StoreViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(StoreFragment.StoreViewHolder holder, int position) {
            Store d = StoreLab.getInstance().getStoreList().get(position);
            holder.imageView4.setImageResource(d.getPhotoId());
            holder.ribborntext1.setText(d.getAaa());
            holder.textView4.setText(d.getBbb());



        }

        @Override
        public int getItemCount() {
            return StoreLab.getInstance().getStoreList().size();
        }
    }

    public interface OnStoreSelectedListener {
        public abstract void onStoreSelected(int position);
    }

}
