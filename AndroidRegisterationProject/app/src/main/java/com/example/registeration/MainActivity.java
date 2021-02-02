package com.example.registeration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

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
        noticeList.add(new Notice("공지사항입니다.", "Hello world", "2020-02-02"));
        noticeList.add(new Notice("공지사항입니다.", "Hello world", "2020-02-02"));
        noticeList.add(new Notice("공지사항입니다.", "Hello world", "2020-02-02"));
        noticeList.add(new Notice("공지사항입니다.", "Hello world", "2020-02-02"));
        noticeList.add(new Notice("공지사항입니다.", "Hello world", "2020-02-02"));
        noticeList.add(new Notice("공지사항입니다.", "Hello world", "2020-02-02"));
        noticeList.add(new Notice("공지사항입니다.", "Hello world", "2020-02-02"));
        noticeList.add(new Notice("공지사항입니다.", "Hello world", "2020-02-02"));
        noticeList.add(new Notice("공지사항입니다.", "Hello world", "2020-02-02"));
        noticeList.add(new Notice("공지사항입니다.", "Hello world", "2020-02-02"));

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

    }
}