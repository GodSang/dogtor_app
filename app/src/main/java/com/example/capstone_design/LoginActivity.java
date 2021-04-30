package com.example.capstone_design;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstone_design.bluetooth.BluetoothActivity;
import com.example.capstone_design.login.LoginRetrofitAPI;
import com.example.capstone_design.login.Post;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.GoogleApiAvailabilityCache;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.google.android.gms.common.util.CollectionUtils.listOf;
import static java.lang.Thread.sleep;

public class LoginActivity extends AppCompatActivity {

    TextView retrofit_test;    EditText user_ID;
    EditText user_Password;
    Button login_btn;
    TextView sign_up_tv;
    SignInButton google_login_btn;

    String id;
    String pwd;
    String uid;

    private GoogleSignInOptions gso;
    private GoogleSignInClient mGoogleSignInClient;
    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]
    private FirebaseUser user;

    private LoginRetrofitAPI login_retrofitAPI;

    Intent uid_send_intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //TextView retrofit_test = findViewById(R.id.retrofit_test_tv);
        user_ID = findViewById(R.id.userid_et);
        user_Password = findViewById(R.id.password_et);
        login_btn = findViewById(R.id.login_btn);
        sign_up_tv = findViewById(R.id.sign_up_tv);
        google_login_btn = findViewById(R.id.google_login_btn);

        // 블루투스 검색을 위해 필요한 위치권한 확인(BluetoothActivity에서 필요함)
        String[] permission_list = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

        ActivityCompat.requestPermissions(LoginActivity.this, permission_list, 1);


        // BluetoothActivity에 uid값 전달
        uid_send_intent = new Intent(this, BluetoothActivity.class);

        // Configure Google Sign In
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();


        // okhttp 로그 찍기
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(2, TimeUnit.MINUTES)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .protocols(listOf(Protocol.HTTP_1_1))
                .addInterceptor(loggingInterceptor).build();

        // 서버와 통신시 필요
        Retrofit retrofit = new Retrofit.Builder()
                       .baseUrl("http://13.209.18.94:3000") // 주소값 중에 바뀌지 않는 고정값
                       //.baseUrl("http://172.30.1.7")
                       .addConverterFactory(GsonConverterFactory.create()) // gson으로 converter를 생성 => gson은 JSON을 자바 클래스로 바꾸는데 사용됨
                       //.client(okHttpClient)
                       .build();
                login_retrofitAPI = retrofit.create(LoginRetrofitAPI.class); // 인터페이스 생성

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(user_ID.getText().toString().equals("") || user_ID.getText().toString() == null){
                    Toast.makeText(getApplicationContext(), "아이디를 입력하세요.", Toast.LENGTH_SHORT).show();
                } else if(user_Password.getText().toString().equals("") || user_Password.getText().toString() == null){
                    Toast.makeText(getApplicationContext(), "패스워드를 입력하세요.", Toast.LENGTH_SHORT).show();
                } else if(user_ID.getText().toString().equals("test") && user_Password.getText().toString().equals("test")){
                    Toast.makeText(getApplicationContext(), "가입되어 있지 않은 사용자입니다.", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT).show();
                    //Intent intent_login_info = new Intent(getApplicationContext(), GetDataActivity.class);
                    Intent intent_main = new Intent(getApplicationContext(), MainActivity.class);

                    startActivity(intent_main);

                    id= user_ID.getText().toString();
                    pwd = user_Password.getText().toString();

                    /*intent_login_info.putExtra("id", id);
                    intent_login_info.putExtra("pwd", pwd);

                    System.out.println(user_Password.getText());

                    startActivity(intent_login_info);*/
                }
            }
        });

        // 회원가입 화면 이동
        sign_up_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_sign_up = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent_sign_up);
            }
        });

        // 구글 로그인 버튼
        google_login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("GO", "로그인 시작 버튼 터치");
                signIn();
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
                Log.d("GO", "로그인 onActivityResult 끝");

            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            user = mAuth.getCurrentUser();
                            updateUI(user);
                            Log.d("GO", "로그인 firebaseAuthWithGoogle 끝");
                            nextPage(user);

                            // BluetoothActivity에 uid값 전달
                            uid_send_intent.putExtra("uid", mAuth.getUid());
                            //startActivity(uid_send_intent);


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            updateUI(null);
                        }
                    }
                });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void updateUI(FirebaseUser user) {

    }

    private void nextPage(FirebaseUser user){
        if(user!=null){
            Log.d("GO", "다음 페이지 넘어가기 함수");
                HashMap<String, Object> input = new HashMap<>();
                //input.put("email", mAuth.getCurrentUser());
                Log.d(TAG, "사용자 UID: " + user.getUid() + " " + "사용자 이메일: " + user.getEmail());

                uid = user.getUid();
                input.put("uid", uid);

                Log.d(TAG, "input에 UID 넣긴 함: " + input.get("uid").toString());

                try{
                    Log.d(TAG, "try 들어오긴 함");

                    login_retrofitAPI.postData(input).enqueue(new Callback<Post>() {
                        @Override
                        public void onResponse(Call<Post> call, Response<Post> response) {
                            Log.d(TAG, "RESPONSE 받긴 함");
                            if(response.isSuccessful()){
                                Post data = response.body();

                                Log.d(TAG, data.getNext());

                                if(data.getNext().equals("signup")){

                                    Intent intent_init = new Intent(getApplicationContext(), InitInfo.class);
                                    intent_init.putExtra("uid", uid);
                                    startActivity(intent_init);
                                    finish();

                                }else if(data.getNext().equals("login")){
                                    Intent intent_main = new Intent(getApplicationContext(), MainActivity.class);
                                    intent_main.putExtra("uid", uid);
                                    startActivity(intent_main);
                                    finish();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<Post> call, Throwable t) {
                            Log.d(TAG, "RESPONSE 받기 실패");
                            t.printStackTrace();
                            t.getMessage();
                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }
        }
    }

    // 활동을 초기화할 때 사용자가 현재 로그인되어있는지 확인
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
        // 자동 로그인
        nextPage(currentUser);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}