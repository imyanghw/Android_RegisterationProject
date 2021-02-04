package com.example.registeration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView noticeListView;
    private NoticeListAdapter adapter;
    private List<Notice> noticeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noticeListView = (ListView)findViewById(R.id.noticeListView);
        noticeList = new ArrayList<Notice>();
        adapter = new NoticeListAdapter(getApplicationContext(), noticeList);
        noticeListView.setAdapter(adapter); //리스트 뷰에 해당 adapter가 매칭이 됨으로써 ListView의 형태로 나타내어줌

        final Button courseButton = (Button)findViewById(R.id.courseButton);
        final Button statisticsButton = (Button)findViewById(R.id.statisticsButton);
        final Button scheduleButton = (Button)findViewById(R.id.scheduleButton);
        final LinearLayout notice = (LinearLayout)findViewById(R.id.notice); //해당 fragment를 눌렀을 때 화면이 바뀌는 부분

        courseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notice.setVisibility(View.GONE); //디폴트값으로 공지사항은 보이지 않도록
                courseButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));
                statisticsButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                scheduleButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, new CourseFragment());
                fragmentTransaction.commit();
            }
        });
        statisticsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notice.setVisibility(View.GONE); //디폴트값으로 공지사항은 보이지 않도록
                courseButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                statisticsButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));
                scheduleButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, new StatisticsFragment());
                fragmentTransaction.commit();
            }
        });
        scheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notice.setVisibility(View.GONE); //디폴트값으로 공지사항은 보이지 않도록
                courseButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                statisticsButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                scheduleButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, new ScheduleFragment());
                fragmentTransaction.commit();
            }
        });

        new BackgroundTask().execute();
    }

    class BackgroundTask extends AsyncTask<Void, Void, String> {

        String target; //접속한 홈페이지 주소가 들어감

        @Override
        protected void onPreExecute() {
            target = "https://imyang3163.cafe24.com/NoticeList.php";
        }

        @Override
        protected String doInBackground(Void... voids) { //실질적으로 데이터를 얻어올 수 있는 부분
            try {
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream(); //넘어오는 결과값들을 그대로 저장함
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream)); //버퍼에 담아서 읽어옴

                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while((temp = bufferedReader.readLine()) != null) { //버퍼에서 받아온 값을 한줄씩 읽으면서
                    stringBuilder.append(temp + "\n"); //한줄씩 추가
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();;
                return stringBuilder.toString().trim(); //문자열들을 반환
            } catch(Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onProgressUpdate(Void...values) {
            super.onProgressUpdate();
        }

        @Override
        public void onPostExecute(String result) { //해당 결과값을 처리
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response"); //response에 공지사항 리스트가 담긴다.

                int count=0;
                String noticeContent, noticeName, noticeDate;
                while(count < jsonArray.length()) {
                    JSONObject object = jsonArray.getJSONObject(count); //현재 배열의 원소값 저장
                    noticeContent = object.getString("noticeContent");
                    noticeName = object.getString("noticeName");
                    noticeDate = object.getString("noticeDate");
                    Notice notice = new Notice(noticeContent, noticeName, noticeDate);
                    noticeList.add(notice); //공지사항이 리스트에 추가됨
                    adapter.notifyDataSetChanged();
                    count++;
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    private long lastTimeBackPressed; //두번 뒤로가기 버튼을 누르면 메인엑티비티 종료

    @Override
    public void onBackPressed() {
        if(System.currentTimeMillis() - lastTimeBackPressed < 1500) {
            finish();
            return;
        } //1.5초이내에 뒤로가기 버튼을 한번더 누르면 종료
        Toast.makeText(this, "'뒤로' 버튼을 한번 더 눌러 종료합니다.", Toast.LENGTH_SHORT).show();
        lastTimeBackPressed = System.currentTimeMillis();
    }

}