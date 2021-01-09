#include<stdio.h>

typedef struct _customer {
	int id; //����ȣ
	char name[20]; //���̸�
	char ssn[14]; //�ֹε�Ϲ�ȣ
	char phone[12]; //��ȭ��ȣ
	char address[50]; //�ּ�
} CUSTOMER; //������ ����ü

typedef struct _video {
	int id; //���� ��ȣ
	char title[20]; //���� ����
	char genre[10]; //�帣
	int charge; //�뿩��
	char regist_data[9]; //�������
	int is_rented; //1: �뿩��, 0: �뿩���� �ƴ�
	int late_fee; //1�� ��ü��
} VIDEO; //�������� ����ü

typedef struct _rent_info {
	int id; //�뿩 ��ȣ
	int video_id; //���� ��ȣ
	int cust_id; //����ȣ
	int rent_date; //�뿩 ����
	int ret_due_date; //�ݳ����� ����
	int ret_date; //�ݳ� ����
	int is_returned; //1: �ݳ��Ϸ�, 0: �뿩��
	int total_late_fee; //�� ��ü��
	int charge; //�뿩��(�뿩�� �����)
} RENT_INFO; //�뿩, �ݳ� ���� ����ü

int main()
{
	return 0;
}