package com.example.registeration;

public class Schedule {

    private String monday[]=new String[14];
    private String tuesday[]=new String[14];
    private String wednesday[]=new String[14];
    private String thursday[]=new String[14];
    private String friday[]=new String[14];

    public Schedule() {
        for (int i = 0; i < 14; i++) {
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
    }
}
