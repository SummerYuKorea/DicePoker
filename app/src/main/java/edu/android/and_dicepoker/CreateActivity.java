package edu.android.and_dicepoker;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CreateActivity extends AppCompatActivity {

    private EditText editId, editPw, editPwCheck;
    private ImageView imageProfile;

    private Uri mImageCaptureUri;
    private String absolutePath;

    String TAG="edu.android.DicePoker";

    //request Code
    private static final int PICK_FROM_CAMERA=0;  //사진 촬영& 찍힌 이미지 처리
    private static final int PICK_FROM_ALBUM=1;     //앨범에서 사진 고르고 이미지 처리
    private static final int CROP_FROM_IMAGE=2;     // 이미지를 크롭!


    private static final String TYPEFACE_NAME = "BMJUA_ttf.ttf";
    private Typeface typeface = null;

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
        setContentView(R.layout.activity_create);

        editId=(EditText)findViewById(R.id.editId);
        editPw=(EditText)findViewById(R.id.editPw);
        editPwCheck=(EditText)findViewById(R.id.editPwCheck);
        imageProfile=(ImageView)findViewById(R.id.imageProfile);

        editId.requestFocus();
    }//onCreate

    public void onClickBtnRegister(View view) {

        if(editId.getText().toString().equals("")) {
            Toast.makeText(this, "아이디를 입력하세요", Toast.LENGTH_SHORT).show();
            editId.requestFocus();
            return;
        }
        if(editPw.getText().toString().equals("")) {
            Toast.makeText(this, "비밀번호를 입력하세요", Toast.LENGTH_SHORT).show();
            editPw.requestFocus();
            return;
        }
        if(editPwCheck.getText().toString().equals("")) {
            Toast.makeText(this, "비밀번호를 입력하세요", Toast.LENGTH_SHORT).show();
            editPwCheck.requestFocus();
            return;
        }

        if(!editPw.getText().toString().equals(editPwCheck.getText().toString())) { //패스워드 같은지 확인

            Toast.makeText(this, "비밀번호가 서로 다릅니다", Toast.LENGTH_SHORT).show();
            editPw.requestFocus();

        }else{  //비밀번호까지 통과하면 insertVO 부르기
            String id= editId.getText().toString();
            String pw= editPw.getText().toString();
            Log.i(TAG, id+"  : "+pw);

           // UserVO vo=new UserVO(id,pw);
            insertVO(new UserVO(id,pw));

        }

    }//onClickBtnRegister

    public void onClickBtnCamera(View view) {
        //리스너 3개 만들어 볼까//오뎁따신기해 .지역변수는 쓰고나서 정의하면 못알아봄 . 먼저 적어야지(순차적)
        DialogInterface.OnClickListener cameraListener =new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                doTakePhotoAction();
            }
        };

        DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                doTakeAlbumAction();
            }
        };

        DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();  // 이거 안해도 똑같
            }
        };

        new AlertDialog.Builder(this)
                .setTitle("업로드할 이미지 선택")
                .setPositiveButton("앨범에서 선택", albumListener)
                .setNeutralButton("사진 촬영", cameraListener)
                .setNegativeButton("취소", cancelListener)
                .show();


    }//onClickBtnCamera

    ///////////// 카메라를 통해 사진 찍기
    public void doTakePhotoAction(){
        // 암시적 인텐트를 사용하여 카메라 앱 실행
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        //임시로 사용할 파일의 경로를 생성
        String url="tmp_"+String.valueOf(System.currentTimeMillis())+".jpg";
        mImageCaptureUri= Uri.fromFile(new File(Environment.getExternalStorageDirectory(),url));

        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);//Parcelable 라는 거래 Uri가. 여기 다시

        if(intent.resolveActivity(getPackageManager())!=null)//인텐트를 샐행시킬 수 있는 앱이 있는지 검사
            startActivityForResult(intent, PICK_FROM_CAMERA);

    }//doTakePhotoAction


    //////////////// 앨범에서 이미지 가져오기
    public void doTakeAlbumAction(){
        // 앨범 호출
        Intent intent=new Intent(Intent.ACTION_PICK);//매개변수 String action ! 흠~
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE); //★ 뭐하자는??
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }//doTakeAlbumAction


    //////////////위메소드들은 startActivityForResult를  호출중
    //그래서 보냈다 돌아왔을때 받아오는 메소드->


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode!=RESULT_OK) return;

        switch (requestCode){
            case PICK_FROM_ALBUM:
                // 이후의 처리가 카메라와 같으므로 일단 break없이 진행(camera case로 가도록)
                // 실제 코드에서는 좀더 합리적인 방법을 선택하시기 바랍니다?
                mImageCaptureUri = data.getData(); //인텐트에서 bundle 빼내는 중
                Log.d("SmartWheel", mImageCaptureUri.getPath().toString()); //d는 뭐죠?

            case PICK_FROM_CAMERA:
                // 이미지를 가져온 이후의 리사이즈할 이미지 크기를 결정한다.
                // 이후에 이미지 크롭 어플리케이션을 호출하게 된다
                Intent intent= new Intent("com.android.camera.action.CROP"); //오호라
                intent.setDataAndType(mImageCaptureUri,"image/*");//데이타(Uri)랑 타입(String)을 지정한다. 별게다있군

                //CROP할 이미지를 200*200 크기로 저장
                intent.putExtra("outputX",200);  //crop한 이미지의 x축 크기
                intent.putExtra("outputY", 200);  //crop한 이미지의 y축 크기
                intent.putExtra("aspectX",1);   //crop 박스의 x축 비율
                intent.putExtra("aspectY",1);   //crop 박스의 y축 비율
                intent.putExtra("scale",true);
                intent.putExtra("return-data", true);
                startActivityForResult(intent, CROP_FROM_IMAGE); //CROP case로 이동(잉)

            case CROP_FROM_IMAGE:
                //크롭이 된 이후의 이미지를 넘겨 받는다
                // 이미지뷰에 이미지를 보여준다거나 부가적인 작업 이후에 임시파일을 삭제한다.
                if(resultCode!= RESULT_OK) return;

                final Bundle extras=data.getExtras();

                //CROP된 이미지를 저장하기 위한 FILE 경로
                String filePath= Environment.getExternalStorageDirectory().getAbsolutePath()
                        +"/SmartWheel/"+System.currentTimeMillis()+".jpg";
                if(extras!=null){
                    Bitmap photo=extras.getParcelable("data");  //CROP된 BITMAP
                    imageProfile.setImageBitmap(photo);    // 레이아웃의 이미지칸에 CROP된 BITMAP을 보여줌

                    storeCropImage(photo, filePath);  //CROP된 이미지를 외부저장소, 앨범에 저장한다
                    absolutePath = filePath ;
                    break;

                }

                //임시파일 삭제
                File f=new File(mImageCaptureUri.getPath());
                if(f.exists()){
                    f.delete();
                }

        }
    }//onActivityResult

    /////////// 외부 저장소(앨범)에 크롭된 이미지를 저장하는 함수
    private void storeCropImage(Bitmap bitmap, String filePath){
        String dirPath=Environment.getExternalStorageDirectory().getAbsolutePath()+"/SmartWheel";
        File directory_SmartWheel =new File(dirPath);
        if(!directory_SmartWheel.exists())  //SmartWheel에 디렉터리 폴더가 없다면(새로 이미지를 저장해야하는경우)
            directory_SmartWheel.mkdir();

        File copyFile = new File(filePath);
        BufferedOutputStream out = null;

        try {
            copyFile.createNewFile();
            out=new BufferedOutputStream(new FileOutputStream(copyFile));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out); //잉?

            // 무슨 영문인지 모르는 중
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                    Uri.fromFile(copyFile)));

            out.flush(); // 이거뭐더라. 비우는 거인가
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }//storeCropImage





    private void insertVO(UserVO vo){
        //용선오빠 메소드
        ////vo를 db에 넣는다.
        insertTask it=new insertTask();
        Log.i(TAG, vo.getUserId()+"  : "+vo.getPassWd());
        it.execute(vo.getUserId(), vo.getPassWd());

        finish();//인서트 완료하면 종료
    }


    public void onClickBtnCancel(View view) {

        finish();
    }

    class insertTask extends AsyncTask<String, Object, String> {
        @Override
        protected String doInBackground(String... params) {
            String toast = SendByHttp(params[0], params[1]);
            return toast;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(CreateActivity.this, s, Toast.LENGTH_SHORT).show();
        }//onPostExecute
    }//insertTask


    public String SendByHttp(String userId, String passWd) {
        String toast = "";
        String URL = "http://192.168.11.14:8081/Java00-Contact/overlap.jsp";
        DefaultHttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(URL + "?userId=" + userId + "&passWd=" + passWd);
        try {
            HttpResponse response = client.execute(post);
            InputStream stream = response.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "utf-8"));
            String result = reader.readLine();
            Log.i("result:", result);
            if (result.equals("empty")) {
                URL = "http://192.168.11.14:8081/Java00-Contact/insert.jsp";
                post = new HttpPost(URL + "?userId=" + userId + "&passWd=" + passWd);
                try {
                    client.execute(post);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                toast = "가입이 완료되었습니다";
            } else {
                toast = "동일한 ID가 존재합니다";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        post.abort();
        return toast;
    }//SendByHttp
}
