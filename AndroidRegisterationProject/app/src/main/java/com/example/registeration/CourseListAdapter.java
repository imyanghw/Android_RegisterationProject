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
        private String userID = MainActivity.userID; //메인 Activity에 public 형태의 userID 값을 가져옴
        private Schedule schedule = new Schedule();
        private List<Integer> courseIDList;

        public CourseListAdapter(Context context, List<Course> courseList, Fragment parent) {
            this.context = context;
            this.courseList = courseList;
            this.parent = parent;
            schedule = new Schedule();
            courseIDList = new ArrayList<Integer>();
            new BackgroundTask().execute();
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

            if(courseList.get(i).getCourseGrade().equals("제한 없음") || courseList.get(i).getCourseGrade().equals(""))
            {
                courseGrade.setText("모든 학년");
            }
            else
            {
                courseGrade.setText(courseList.get(i).getCourseGrade());
            }
            courseTitle.setText(courseList.get(i).getCourseTitle());
            courseDivide.setText(courseList.get(i).getCourseDivide()+"분반");

            if(courseList.get(i).getCoursePersonnel() == 0)
            {
                coursePersonnel.setText("인원 제한 없음");
            }
            else
            {
                coursePersonnel.setText("제한 인원 : " + courseList.get(i).getCoursePersonnel()+"명");
            }
            courseProfessor.setText(courseList.get(i).getCourseProfessor() + "교수");
            courseTime.setText(courseList.get(i).getCourseTime() + "");

            v.setTag(courseList.get(i).getCourseID());

            Button addButton = (Button)v.findViewById(R.id.addButton); //addButton 레이아웃을 가져옴
            addButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    boolean validate = false;
                    validate = schedule.validate(courseList.get(i).getCourseTime()); //현재 시간표에 강의를 넣음으로써 유효성을 검증
                    if (!alreadyIn(courseIDList, courseList.get(i).getCourseID())) { //만약 자기가 이미 신청한 강의 ID 값이면
                        AlertDialog.Builder builder = new AlertDialog.Builder(parent.getActivity());
                        AlertDialog dialog = builder.setMessage("이미 추가한 강의입니다.")
                                .setPositiveButton("다시 시도", null)
                                .create();
                        dialog.show();
                    }
                    else if (validate == false)
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(parent.getActivity());
                        AlertDialog dialog = builder.setMessage("시간표가 중복됩니다.")
                                .setPositiveButton("다시 시도", null)
                                .create();
                        dialog.show();
                    }
                    else {
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    boolean success = jsonResponse.getBoolean("success");
                                    if (success) {
                                        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(parent.getActivity());
                                        //아이디가 빈공간일때 예외를 처리해 다시 작성하게끔 해줌
                                        AlertDialog dialog = builder.setMessage("강의가 추가되었습니다.")
                                                .setPositiveButton("확인", null)
                                                .create();
                                        dialog.show();
                                        courseIDList.add(courseList.get(i).getCourseID());
                                        schedule.addSchedule(courseList.get(i).getCourseTime());
                                    } else {
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
                        AddRequest addRequest = new AddRequest(userID, courseList.get(i).getCourseID() + "", responseListener);
                        RequestQueue queue = Volley.newRequestQueue(parent.getActivity());
                        queue.add(addRequest);
                    }
                }
            });
            return v;
        }

    class BackgroundTask extends AsyncTask<Void, Void, String>
    {
        String target;

        @Override
        protected void onPreExecute(){
            try {
                target = "https://imyang3163.cafe24.com/ScheduleList.php?userID=" + URLEncoder.encode(userID, "UTF-8");
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            try{
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while((temp = bufferedReader.readLine())!=null)
                {
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            }catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onProgressUpdate(Void... values){
            super.onProgressUpdate();
        }

        @Override
        public void onPostExecute(String result)
        {
            try{
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count = 0;
                String courseProfessor, courseTime;
                int courseID;
                while(count < jsonArray.length()){
                    JSONObject object = jsonArray.getJSONObject(count);
                    courseID = object.getInt("courseID");
                    courseProfessor = object.getString("courseProfessor");
                    courseTime = object.getString("courseTime");
                    courseIDList.add(courseID); //현재 해당 사용자가 가지고 있는 모든 시간표 데이터에 있는 강의 ID 값이 담김
                    schedule.addSchedule(courseTime);
                    count++;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

        public boolean alreadyIn(List<Integer> courseIDList, int item) { //현재 해당하는 courseID 값이 이미 들어가 있는 상태라면
            for(int i=0; i<courseIDList.size(); i++)
            {
                if(courseIDList.get(i) == item){ //현재 추가하려는 id값의 원소가 하나라도 존재한다면
                    return false;
                }
            }
            return true; //그렇지 않다면 해당 데이터를 추가해주도록
        }
    }

