package elev;
import java.lang.Math;
public class Person implements ElevatorObject {
	static int ID=0; //���ڱ�־ÿ����
	int identity;
	int floor0; //��ʼ¥��
	int d_floor; //Ŀ��¥��
	int time0; //��ʼ�ȴ���ʱ��
	int outtime; //�����ݵ�ʱ��
	int boredtime; //��ʼ���ͷ���ʱ��
	int time_interval; //��һ��������ʱ����
	boolean bored=false; //�Ƿ��Ĳ��ͷ�
	boolean nextone=false; //��һ�����Ƿ���
	boolean nextonecome=false; //��һ�����Ƿ����
	boolean inelevator=false; //�Ƿ�������
	boolean outelevator=false; //�Ƿ��Ѿ�������
	boolean boarding=false; //�Ƿ������ϵ���
	boolean going=false; //�Ƿ������뿪 ��������
	final int WAITTIME=300;
	final int OUTTIME=100; //�����ݺ����ʾʱ��
	final int REMAININGTIME=150;
	final int MAXINTERVAL=120;
	final int MININTERVAL=20;
	public Person(int t)
	{
		floor0=(int)(Math.random()*floor_num);
		identity=++ID;
		while((d_floor=(int)(Math.random()*floor_num))==floor0); //d_floorҪȡ��floor��ͬ����
		time0=t;
		while((time_interval=(int)(Math.random()*MAXINTERVAL))<MININTERVAL);
	}
	void upDate(int t, elevator machine)
	{
		if(!inelevator&&bored==false&&!boarding) 
			if(t-time0>=WAITTIME) 
			{
				if(Math.abs(machine.getPosition()-floor0)>=1)
				{bored=true;boredtime=t;}
			}
		if(t-time0>=time_interval) nextone=true;
	}
}
