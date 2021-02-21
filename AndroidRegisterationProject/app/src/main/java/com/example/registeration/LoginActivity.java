package com.example.registeration;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView registerButton = (TextView)findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        }); //LoginActivity 화면에서 회원가입 버튼을 누르면 RegisterActivity를 호출

        final EditText idText = (EditText) findViewById(R.id.idText); //아이디를 입력받는 부분
        final EditText passwordText = (EditText) findViewById(R.id.passwordText); //비밀번호를 입력받는 부분
        final Button loginButton = (Button) findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String userID = idText.getText().toString();
                String userPassword = passwordText.getText().toString();
                //결과를 받아올 수 있도록 한다
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success) { //로그인에 성공했다면
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                //아이디가 빈공간일때 예외를 처리해 다시 작성하게끔 해줌
                                dialog = builder.setMessage("로그인에 성공했습니다.")
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent=new Intent(LoginActivity.this,
                                                        MainActivity.class);
                                                intent.putExtra("userID", userID);
                                                LoginActivity.this.startActivity(intent);
                                            } //다이얼로그 메시지 확인을 누르게되면 메인화면으로 이동함
                                        })
                                        .create();
                                dialog.show();
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                //아이디가 빈공간일때 예외를 처리해 다시 작성하게끔 해줌
                                dialog = builder.setMessage("계정을 다시 확인하세요.")
                                        .setNegativeButton("다시 시도", null)
                                        .create();
                                dialog.show();
                            } //로그인에 실패했다면
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest(userID, userPassword, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest); //Volley 라이브러리를 이용해서 실제 서버와 통신을 구현하는 부분
            }
        });//로그인 버튼을 눌렀을 때 이벤트 처리를 해줌

        TextView information = (TextView)findViewById(R.id.information);
        information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, Pop.class));
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    } //로그인 이후에 꺼지게되는 창
}