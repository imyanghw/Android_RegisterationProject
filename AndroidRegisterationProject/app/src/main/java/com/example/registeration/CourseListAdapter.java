package com.example.registeration;

import android.content.Context;
import android.os.AsyncTask;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class CourseListAdapter extends BaseAdapter {

        private Context context;
        private List<Course> courseList;
        private Fragment parent;

        public CourseListAdapter(Context context, List<Course> courseList, Fragment parent) {
            this.context = context;
            this.courseList = courseList;
            this.parent = parent;
        }

        @Override
        public int getCount() {
            return courseList.size();
        }

        @Override
        public Object getItem(int i) {
            return courseList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            View v = View.inflate(context, R.layout.course, null); //xml 에 씌여져 있는 view 의 정의를 실제 view 객체로 만드는 역할
            TextView courseGrade=(TextView)v.findViewById(R.id.courseGrade);
            TextView courseTitle=(TextView)v.findViewById(R.id.courseTitle);
            TextView courseDivide=(TextView)v.findViewById(R.id.courseDivide);
            TextView coursePersonnel=(TextView)v.findViewById(R.id.coursePersonnel);
            TextView courseProfessor=(TextView)v.findViewById(R.id.courseProfessor);
            TextView courseTime=(TextView)v.findViewById(R.id.courseTime);

            if(courseList.get(i).getCourseGrade().equals("제한 없음")||courseList.get(i).getCourseGrade().equals(""))
            {
                courseGrade.setText("모든 학년");
            }
            else
            {
                courseGrade.setText(courseList.get(i).getCourseGrade());
            }
            courseTitle.setText(courseList.get(i).getCourseTitle());
            courseDivide.setText(courseList.get(i).getCourseDivide()+"분반");

            if(courseList.get(i).getCoursePersonnel()==0)
            {
                coursePersonnel.setText("인원 제한 없음");
            }
            else
            {
                coursePersonnel.setText("제한 인원 : "+courseList.get(i).getCoursePersonnel()+"명");
            }
            courseProfessor.setText(courseList.get(i).getCourseProfessor() + "교수");
            courseTime.setText(courseList.get(i).getCourseTime()+"");

            v.setTag(courseList.get(i).getCourseID());

            Button addButton=(Button)v.findViewById(R.id.addButton); //addButton 레이아웃을 가져옴
            addButton.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    String userID = MainActivity.userID;
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                if(success) {
                                    androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(parent.getActivity());
                                    //아이디가 빈공간일때 예외를 처리해 다시 작성하게끔 해줌
                                    AlertDialog dialog = builder.setMessage("강의가 추가되었습니다.")
                                            .setPositiveButton("확인", null)
                                            .create();
                                    dialog.show();
                                }
                                else {
                                    androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(parent.getActivity());
                                    //아이디가 빈공간일때 예외를 처리해 다시 작성하게끔 해줌
                                    AlertDialog dialog = builder.setMessage("강의 추가에 실패하였습니다.")
                                            .setNegativeButton("확인", null)
                                            .create();
                                    dialog.show();
                                } //중복체크에 실패했다면(사용할 수 없는 아이디라면)
                            } catch (Exception e) {
                                e.printStackTrace();
                            } //response를 다시 받을 수 있도록
                        }
                    };
                    AddRequest addRequest = new  AddRequest(userID, courseList.get(i).getCourseID() + "", responseListener);
                    RequestQueue queue = Volley.newRequestQueue(parent.getActivity());
                    queue.add(addRequest);
                }
            });
            return v;
        }
    }

