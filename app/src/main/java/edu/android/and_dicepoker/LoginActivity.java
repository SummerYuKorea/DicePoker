package edu.android.and_dicepoker;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
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


public class LoginActivity extends AppCompatActivity {

    private static final String TYPEFACE_NAME = "BMJUA_ttf.ttf";
    private Typeface typeface = null;

    private ImageView loginButton;
    private ImageView createintBox;
    private EditText editTextId;
    private EditText editTextPw;

    public static final String USERVO ="userVO";
    private UserVO vo;

    String TAG="edu.android.DicePoker";


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
        setContentView(R.layout.activity_login);

        editTextId = (EditText) findViewById(R.id.editTextId);
        editTextPw = (EditText) findViewById(R.id.editTextPw);

        loginButton = (ImageView) findViewById(R.id.imageView5);
        createintBox = (ImageView) findViewById(R.id.createintBox);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectTask st = new selectTask();
                st.execute(editTextId.getText().toString(), editTextPw.getText().toString());
//                if(true){
//                    Toast.makeText(LoginActivity.this, "Login 성공", Toast.LENGTH_SHORT).show();
//                    activityChangeMain();
//
//                }else {
//                    Toast.makeText(LoginActivity.this, "Login 실패", Toast.LENGTH_SHORT).show();
//                }


            }
        });
        createintBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityChangeCreate();

            }
        });

    }

    class selectTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String result = SendByHttp(params[0], params[1]);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Gson gson = new Gson();
            vo = gson.fromJson(s, UserVO.class);
            Toast.makeText(LoginActivity.this, "소지 골드:" + vo.getGold(), Toast.LENGTH_SHORT).show();

            activityChangeMain();// 메인 액티비티로 이동
            finish();
        }
    }//class selectTask

    public String SendByHttp(String userId, String passWd) {
        BufferedReader reader = null;
        String URL = "http://localhost:8181/Java00-Contact/select.jsp";
        DefaultHttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(URL + "?id=" + userId + "&pw=" + passWd);
        String result = "";
        System.out.println("잉");
        try {
            HttpResponse response = client.execute(post);
            System.out.println("잉잉");
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

    public void activityChangeMain() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra(USERVO, vo);
        startActivity(intent);
    }
    public void activityChangeCreate() {
        Intent intent = new Intent(LoginActivity.this, CreateActivity.class);
        startActivity(intent);
    }

    public void onClickBtnExitLogin(View view) {
        //TODO: destoy에서 저장
        finish();
    }
}