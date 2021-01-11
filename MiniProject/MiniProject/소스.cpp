#define _CRT_SECURE_NO_WARNINGS
#include<stdio.h>
#include<string.h>
#include<stdlib.h>
/*
0. ���� �������̽� ����
1. �ű� �� ���
2. ����ȸ
3. �ű� ���� ���
4. ���� ��ȸ
5. ���� �뿩
6. ���� �ݳ�
7. ���� ��ü ó��
*/
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
	char regist_date[9]; //�������
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

void input_customer();
void input_video();
void new_customer();

CUSTOMER customer_info[200];
int c_idx = 0; //������ ����� ����
VIDEO video_info[200];
int v_idx = 0; //�������� ����� ����

int main()
{
	int i, sel = 1;

	input_customer(); //������ �Է�
	input_video(); //�������� �Է�

	while(sel!=0)
	{
		system("cls");
		printf("<���� �뿩���� ���α׷�>\n");
		printf("1. �ű� �� ���\n");
		printf("2. �� ���� ��ȸ\n");
		printf("3. �ű� ���� ���\n");
		printf("4. ���� ���� ��ȸ\n");
		printf("0. ����\n");
		printf("---------------------\n");
		printf("�׸��� �����ϼ��� : ");
		scanf("%d", &sel);

		switch (sel)
		{
		case 1:
			new_customer();
			break;
		}
	}
	
	for (i = 0; i < c_idx; i++)
	{
		printf("%d, %s, %s, %s, %s\n"
			, customer_info[i].id
			, customer_info[i].name
			, customer_info[i].ssn
			, customer_info[i].phone
			, customer_info[i].address);
	}

	for (i = 0; i < v_idx; i++)
	{
		printf("%d, %s, %s, %d, %s, %d, %d\n"
			, video_info[i].id
			, video_info[i].title
			, video_info[i].genre
			, video_info[i].charge
			, video_info[i].regist_date
			, video_info[i].is_rented
			, video_info[i].late_fee);			
	}
	return 0;
}

//�ű� �������� �Է¹޾Ƽ� ���Ͽ� ����
void new_customer()
{
	FILE *c_fp = fopen("customer_info.txt", "a");

	//����ü �迭�� �� ������ �Է� ����
	printf("����ȣ : ");
	scanf("%d", &customer_info[c_idx].id);
	printf("���̸� : ");
	scanf("%s", &customer_info[c_idx].name);
	printf("�ֹε�Ϲ�ȣ : ");
	scanf("%s", &customer_info[c_idx].ssn);
	printf("��ȭ��ȣ : ");
	scanf("%s", &customer_info[c_idx].phone);
	printf("�ּ� : ");
	scanf("%s", &customer_info[c_idx].address);

	fprintf(c_fp, "\n%d, %s, %s, %s, %s"
		, customer_info[c_idx].id
		, customer_info[c_idx].name
		, customer_info[c_idx].ssn
		, customer_info[c_idx].phone
		, customer_info[c_idx].address);

	c_idx++;
	fclose(c_fp);
}

//�������� ���Ͽ��� �Է¹޴´�.
void input_customer()
{
	FILE *c_fp = fopen("customer_info.txt", "r");
	char line[150];
	char *ptr;
	int word_cnt; //�޸� �������� �ܾ �ڸ�

	while (fscanf(c_fp, "%s", line) > 0)
	{
		word_cnt = 0;
		//printf("%s\n", line);
		ptr = strtok(line, ",");
		while (ptr != NULL)
		{
			word_cnt++;
			switch (word_cnt)
			{
			case 1: //int id
				customer_info[c_idx].id = atoi(ptr);
				break;
			case 2: //char name
				strcpy(customer_info[c_idx].name, ptr);
				break;
			case 3: //char ssn
				strcpy(customer_info[c_idx].ssn, ptr);
				break;
			case 4: //char phone
				strcpy(customer_info[c_idx].phone, ptr);
				break;
			case 5: //char address
				strcpy(customer_info[c_idx].address, ptr);
				break;
			}
			//printf("%s\n", ptr);
			ptr = strtok(NULL, ",");
		}
		c_idx++;
	}
	fclose(c_fp);
}

//���������� ���Ͽ��� �Է¹޴´�.
void input_video()
{
	FILE *v_fp = fopen("video_info.txt", "r");
	char line[150];
	char *ptr;
	int word_cnt; //�޸� �������� �ܾ �ڸ�

	while (fscanf(v_fp, "%s", line) > 0)
	{
		word_cnt = 0;
		//printf("%s\n", line);
		ptr = strtok(line, ",");
		while (ptr != NULL)
		{
			word_cnt++;
			switch (word_cnt)
			{
			case 1: //int id
				video_info[v_idx].id = atoi(ptr); //���ڿ� ���ڷ� ��ȯ
				break;
			case 2: //char title
				strcpy(video_info[v_idx].title, ptr);
				break;
			case 3: //char genre
				strcpy(video_info[v_idx].genre, ptr);
				break;
			case 4: //int charge
				video_info[v_idx].charge = atoi(ptr); //���ڿ� ���ڷ� ��ȯ
				break;
			case 5: //char regist_date
				strcpy(video_info[v_idx].regist_date, ptr);
				break;
			}
			//printf("%s\n", ptr);
			ptr = strtok(NULL, ",");
		}
		v_idx++;
	}
	fclose(v_fp);
}