package com.example.registeration;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    private ArrayAdapter adapter;
    private Spinner spinner;
    private String userID;
    private String userPassword;
    private String userGender;
    private String userMajor;
    private String userEmail;
    private AlertDialog dialog;
    private boolean validate = false; //회원 아이디인지 체크

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //화면을 세로로 고정시켜줌

        final Spinner mSpinner = findViewById(R.id.majorSpinner);
        String[] models = getResources().getStringArray(R.array.major);
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(getBaseContext(), R.layout.support_simple_spinner_dropdown_item, models);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter); //spinner로 전공 목록을 나열해줌

        /*mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/

        final EditText idText = (EditText) findViewById(R.id.idText);
        final EditText passwordText = (EditText) findViewById(R.id.passwordText);
        final EditText emailText = (EditText) findViewById(R.id.emailText);
        RadioGroup genderGroup = (RadioGroup) findViewById(R.id.genderGroup);

        int genderGroupID = genderGroup.getCheckedRadioButtonId(); //현재 선택되고 있는 Gender의 ID값
        userGender = ((RadioButton) findViewById(genderGroupID)).getText().toString(); //현재 선택된 Gender의 ID를 매칭
        //결국 남자인지 여자인지에 대해 값이 userGender에 들어감

        genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton genderButton = (RadioButton)findViewById(i); //현재 선택되어 있는 라디오 버튼을 찾은 다음에
                userGender = genderButton.getText().toString(); //userGender의 값을 변경
            }
        }); //라디오 버튼이 클리되었을때에 대한 이벤트 처리

        final Button validateButton = (Button) findViewById(R.id.validateButton); //회원 중복체크 버튼
        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userID = idText.getText().toString();
                if(validate) {
                    return; //validate가 체크가 이루어진 상황이라면 종료
                }
                if(userID.equals("")) { //체크가 되어있지 않은 상태라면(userID값이 아무런내용이 없다면)
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    //아이디가 빈공간일때 예외를 처리해 다시 작성하게끔 해줌
                    dialog = builder.setMessage("아이디를 입력해주세요.")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                //아이디가 빈공간일때 예외를 처리해 다시 작성하게끔 해줌
                                dialog = builder.setMessage("사용할 수 있는 아이디입니다.")
                                        .setPositiveButton("확인", null)
                                        .create();
                                dialog.show();
                                idText.setEnabled(false); //아이디 값을 바꿀수 없도록 고정시켜줌
                                validate= true; //체크가 완료됨
                                idText.setBackgroundColor(getResources().getColor(R.color.colorGray));
                                validateButton.setBackgroundColor(getResources().getColor(R.color.colorGray));
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                //아이디가 빈공간일때 예외를 처리해 다시 작성하게끔 해줌
                                dialog = builder.setMessage("사용할 수 없는 아이디입니다.")
                                        .setNegativeButton("확인", null)
                                        .create();
                                dialog.show();
                            } //중복체크에 실패했다면(사용할 수 없는 아이디라면)
                        } catch (Exception e) {
                            e.printStackTrace();
                        } //response를 다시 받을 수 있도록
                    }
                };
                ValidateRequest validateRequest = new ValidateRequest(userID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(validateRequest);
            }
        }); //버튼을 클릭했을때 중복체크를 진행함

        Button registerButton = (Button) findViewById(R.id.registerButton); //회원가입 버튼
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userID = idText.getText().toString();
                String userPassword = passwordText.getText().toString();
                String userMajor = mSpinner.getSelectedItem().toString();
                String userEmail = emailText.getText().toString();
                if(!validate) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    //아이디가 빈공간일때 예외를 처리해 다시 작성하게끔 해줌
                    dialog = builder.setMessage("먼저 중복체크를 해주세요.")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                } //중복체크가 되어있지 않다면
                if(userID.equals("") || userPassword.equals("")
                        || userMajor.equals("") || userEmail.equals("") || userGender.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    //아이디가 빈공간일때 예외를 처리해 다시 작성하게끔 해줌
                    dialog = builder.setMessage("빈 칸 없이 입력해주세요.")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                } //하나라도 빈공간이 있다면 정삭적으로 회원가입이 등록될수가 없음
                //*아무런 오류가 없다면 정상적으로 회원가입을 진행*
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                //아이디가 빈공간일때 예외를 처리해 다시 작성하게끔 해줌
                                dialog = builder.setMessage("회원 등록에 성공했습니다.")
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent=new Intent(RegisterActivity.this,
                                                        LoginActivity.class);
                                                RegisterActivity.this.startActivity(intent);
                                            } //다이얼로그 메시지 확인을 누르게되면 로그인창으로 이동함
                                        })
                                        .create();
                                dialog.show();
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                //아이디가 빈공간일때 예외를 처리해 다시 작성하게끔 해줌
                                dialog = builder.setMessage("회원 등록에 실패했습니다.")
                                        .setNegativeButton("확인", null)
                                        .create();
                                dialog.show();
                            } //중복체크에 실패했다면(사용할 수 없는 아이디라면)
                        } catch (Exception e) {
                            e.printStackTrace();
                        } //response를 다시 받을 수 있도록
                    }
                };
                RegisterRequest registerRequest = new RegisterRequest(userID, userPassword, userGender,
                        userMajor, userEmail, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest); //Volley 라이브러리를 이용해서 실제 서버와 통신을 구현하는 부분
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(dialog != null) {
            dialog.dismiss();
            dialog=null;
        }
    } //회원 등록이 이루어진 이후에 꺼지게되는 창
}