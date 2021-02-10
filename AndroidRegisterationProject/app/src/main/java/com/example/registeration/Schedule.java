package com.example.registeration;

import android.content.Context;
import android.widget.TextView;

public class Schedule {

    private String monday[] = new String[10];
    private String tuesday[] = new String[10];
    private String wednesday[] = new String[10];
    private String thursday[] = new String[10];
    private String friday[] = new String[10];

    public Schedule() {
        for (int i = 0; i < 10; i++) {
            monday[i] = "";
            tuesday[i] = "";
            wednesday[i] = "";
            thursday[i] = "";
            friday[i] = "";
        }
    }

    public void addSchedule(String scheduleText) { //schedule 정보를 담는 특정 데이터를 파싱해서 강의정보 배열에 넣어줌
        int temp;
        //월:[3][4][5] 화:[4][5]
        if ((temp = scheduleText.indexOf("월")) > -1) { //월이라는 단어가 포함되어 있다면 위치값을 반환
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;
            for (int i = temp; i < scheduleText.length() && scheduleText.charAt(i) != ':'; i++) {
                if (scheduleText.charAt(i) == '[') {
                    startPoint = i;
                }

                if (scheduleText.charAt(i) == ']') {
                    endPoint = i;
                    monday[Integer.parseInt(scheduleText.substring(startPoint + 1, endPoint))] = "수업";
                }
            }
        }

        if ((temp = scheduleText.indexOf("화")) > -1) {
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;
            for (int i = temp; i < scheduleText.length() && scheduleText.charAt(i) != ':'; i++) {
                if (scheduleText.charAt(i) == '[') {
                    startPoint = i;
                }

                if (scheduleText.charAt(i) == ']') {
                    endPoint = i;
                    tuesday[Integer.parseInt(scheduleText.substring(startPoint + 1, endPoint))] = "수업";
                }
            }
        }

        if ((temp = scheduleText.indexOf("수")) > -1) {
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;
            for (int i = temp; i < scheduleText.length() && scheduleText.charAt(i) != ':'; i++) {
                if (scheduleText.charAt(i) == '[') {
                    startPoint = i;
                }

                if (scheduleText.charAt(i) == ']') {
                    endPoint = i;
                    wednesday[Integer.parseInt(scheduleText.substring(startPoint + 1, endPoint))] = "수업";
                }
            }
        }

        if ((temp = scheduleText.indexOf("목")) > -1) {
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;
            for (int i = temp; i < scheduleText.length() && scheduleText.charAt(i) != ':'; i++) {
                if (scheduleText.charAt(i) == '[') {
                    startPoint = i;
                }

                if (scheduleText.charAt(i) == ']') {
                    endPoint = i;
                    thursday[Integer.parseInt(scheduleText.substring(startPoint + 1, endPoint))] = "수업";
                }
            }
        }

        if ((temp = scheduleText.indexOf("금")) > -1) {
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;
            for (int i = temp; i < scheduleText.length() && scheduleText.charAt(i) != ':'; i++) {
                if (scheduleText.charAt(i) == '[') {
                    startPoint = i;
                }

                if (scheduleText.charAt(i) == ']') {
                    endPoint = i;
                    friday[Integer.parseInt(scheduleText.substring(startPoint + 1, endPoint))] = "수업";
                }
            }
        }
    }

    public boolean validate(String scheduleText) {
        if (scheduleText.equals("")) { //새롭게 추가하려는 수강 신청 날짜의 데이터가 현재 schedule 데이터에 중복되지 않는지 체크           return true;
            return true;
        }
        int temp;
        if ((temp = scheduleText.indexOf("월")) > -1) {
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;
            for (int i = temp; i < scheduleText.length() && scheduleText.charAt(i) != ':'; i++) {
                if (scheduleText.charAt(i) == '[') {
                    startPoint = i;
                }

                if (scheduleText.charAt(i) == ']') {
                    endPoint = i;
                    if (!monday[Integer.parseInt(scheduleText.substring(startPoint + 1, endPoint))].equals("")) {
                        return false;
                    }
                }
            }
        }

        if ((temp = scheduleText.indexOf("화")) > -1) {
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;
            for (int i = temp; i < scheduleText.length() && scheduleText.charAt(i) != ':'; i++) {
                if (scheduleText.charAt(i) == '[') {
                    startPoint = i;
                }

                if (scheduleText.charAt(i) == ']') {
                    endPoint = i;
                    if (!tuesday[Integer.parseInt(scheduleText.substring(startPoint + 1, endPoint))].equals("")) {
                        return false;
                    }
                }
            }
        }

        if ((temp = scheduleText.indexOf("수")) > -1) {
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;
            for (int i = temp; i < scheduleText.length() && scheduleText.charAt(i) != ':'; i++) {
                if (scheduleText.charAt(i) == '[') {
                    startPoint = i;
                }

                if (scheduleText.charAt(i) == ']') {
                    endPoint = i;
                    if (!wednesday[Integer.parseInt(scheduleText.substring(startPoint + 1, endPoint))].equals("")) {
                        return false;
                    }
                }
            }
        }

        if ((temp = scheduleText.indexOf("목")) > -1) {
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;
            for (int i = temp; i < scheduleText.length() && scheduleText.charAt(i) != ':'; i++) {
                if (scheduleText.charAt(i) == '[') {
                    startPoint = i;
                }

                if (scheduleText.charAt(i) == ']') {
                    endPoint = i;
                    if (!thursday[Integer.parseInt(scheduleText.substring(startPoint + 1, endPoint))].equals("")) {
                        return false;
                    }
                }
            }
        }

        if ((temp = scheduleText.indexOf("금")) > -1) {
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;
            for (int i = temp; i < scheduleText.length() && scheduleText.charAt(i) != ':'; i++) {
                if (scheduleText.charAt(i) == '[') {
                    startPoint = i;
                }

                if (scheduleText.charAt(i) == ']') {
                    endPoint = i;
                    if (!friday[Integer.parseInt(scheduleText.substring(startPoint + 1, endPoint))].equals("")) {
                        return false; //공백이 아니라면 어떠한 데이터가 들어가 있다는 소리이기 때문에 해당 시간표는 중복되어서 들어갈 수 없음
                    }
                }
            }
        }
        return true;
    }
    //시간표에 추가되어서 보여지는 부분
    public void addSchedule(String scheduleText, String courseTitle, String courserProfessor) {
        String professor;
        if (courserProfessor.equals("")) {
            professor = "";
        } else {
            professor = "";
        }
        int temp;
        //월:[3][4][5] 화:[4][5]
        if ((temp = scheduleText.indexOf("월")) > -1) {
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;
            for (int i = temp; i < scheduleText.length() && scheduleText.charAt(i) != ':'; i++) {
                if (scheduleText.charAt(i) == '[') {
                    startPoint = i;
                }

                if (scheduleText.charAt(i) == ']') {
                    endPoint = i;
                    monday[Integer.parseInt(scheduleText.substring(startPoint + 1, endPoint))] = courseTitle + professor;
                }
            }
        }

        if ((temp = scheduleText.indexOf("화")) > -1) {
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;
            for (int i = temp; i < scheduleText.length() && scheduleText.charAt(i) != ':'; i++) {
                if (scheduleText.charAt(i) == '[') {
                    startPoint = i;
                }

                if (scheduleText.charAt(i) == ']') {
                    endPoint = i;
                    tuesday[Integer.parseInt(scheduleText.substring(startPoint + 1, endPoint))] = courseTitle + professor;
                }
            }
        }

        if ((temp = scheduleText.indexOf("수")) > -1) {
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;
            for (int i = temp; i < scheduleText.length() && scheduleText.charAt(i) != ':'; i++) {
                if (scheduleText.charAt(i) == '[') {
                    startPoint = i;
                }

                if (scheduleText.charAt(i) == ']') {
                    endPoint = i;
                    wednesday[Integer.parseInt(scheduleText.substring(startPoint + 1, endPoint))] = courseTitle + professor;
                }
            }
        }

        if ((temp = scheduleText.indexOf("목")) > -1) {
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;
            for (int i = temp; i < scheduleText.length() && scheduleText.charAt(i) != ':'; i++) {
                if (scheduleText.charAt(i) == '[') {
                    startPoint = i;
                }

                if (scheduleText.charAt(i) == ']') {
                    endPoint = i;
                    thursday[Integer.parseInt(scheduleText.substring(startPoint + 1, endPoint))] = courseTitle + professor;
                }
            }
        }

        if ((temp = scheduleText.indexOf("금")) > -1) {
            temp += 2;
            int startPoint = temp;
            int endPoint = temp;
            for (int i = temp; i < scheduleText.length() && scheduleText.charAt(i) != ':'; i++) {
                if (scheduleText.charAt(i) == '[') {
                    startPoint = i;
                }

                if (scheduleText.charAt(i) == ']') {
                    endPoint = i;
                    friday[Integer.parseInt(scheduleText.substring(startPoint + 1, endPoint))] = courseTitle + professor;
                }
            }
        }
    }

    public void setting(AutoResizeTextView[] monday, AutoResizeTextView[] tuesday, AutoResizeTextView[] wednesday, AutoResizeTextView[] thursday, AutoResizeTextView[] friday, Context context)
    { //해당 강의목록을 보여줄 수 있도록 세팅
        int maxLength=0;
        String maxString=""; //TextView에 들어가는 내용중에서 가장 긴 Text를 골라서 넣어줌
        for(int i=0; i<10; i++)
        {
            if(this.monday[i].length()>maxLength){ //현재의 Text가 maxLength보다 길면
                maxLength = this.monday[i].length();
                maxString = this.monday[i];
            }
            if(this.tuesday[i].length()>maxLength){
                maxLength = this.tuesday[i].length();
                maxString = this.tuesday[i];
            }
            if(this.wednesday[i].length()>maxLength){
                maxLength = this.wednesday[i].length();
                maxString = this.wednesday[i];
            }
            if(this.thursday[i].length()>maxLength){
                maxLength = this.thursday[i].length();
                maxString = this.thursday[i];
            }
            if(this.friday[i].length()>maxLength){
                maxLength = this.friday[i].length();
                maxString = this.friday[i];
            }
        }

        for(int i=0; i<10; i++){
            if(!this.monday[i].equals("")) //특정한 강의가 배열에 들어가있지 않다면
            {
                monday[i].setText(this.monday[i]);
                monday[i].setTextColor(context.getResources().getColor(R.color.colorPrimaryLight));
            }
            else
            {
                monday[i].setText(maxString);
            }

            if(!this.tuesday[i].equals(""))
            {
                tuesday[i].setText(this.tuesday[i]);
                tuesday[i].setTextColor(context.getResources().getColor(R.color.colorPrimaryLight));
            }
            else
            {
                tuesday[i].setText(maxString);
            }

            if(!this.wednesday[i].equals(""))
            {
                wednesday[i].setText(this.wednesday[i]);
                wednesday[i].setTextColor(context.getResources().getColor(R.color.colorPrimaryLight));
            }
            else
            {
                wednesday[i].setText(maxString);
            }

            if(!this.thursday[i].equals(""))
            {
                thursday[i].setText(this.thursday[i]);
                thursday[i].setTextColor(context.getResources().getColor(R.color.colorPrimaryLight));
            }
            else
            {
                thursday[i].setText(maxString);
            }

            if(!this.friday[i].equals(""))
            {
                friday[i].setText(this.friday[i]);
                friday[i].setTextColor(context.getResources().getColor(R.color.colorPrimaryLight));
            }
            else
            {
                friday[i].setText(maxString);
            }

            monday[i].resizeText();
            tuesday[i].resizeText();
            wednesday[i].resizeText();
            thursday[i].resizeText();
            friday[i].resizeText();
        }
    }
}