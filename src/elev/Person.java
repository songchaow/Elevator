package elev;
import java.lang.Math;
public class Person implements ElevatorObject {
	static int ID=0; //用于标志每个人
	int identity;
	int floor0; //初始楼层
	int d_floor; //目标楼层
	int time0; //开始等待的时刻
	int outtime; //出电梯的时刻
	int boredtime; //开始不耐烦的时刻
	int time_interval; //下一人与他的时间间隔
	boolean bored=false; //是否变的不耐烦
	boolean nextone=false; //下一个人是否到来
	boolean nextonecome=false; //下一个人是否产生
	boolean inelevator=false; //是否进入电梯
	boolean outelevator=false; //是否已经出电梯
	boolean boarding=false; //是否正在上电梯
	boolean going=false; //是否正在离开 后续加入
	final int WAITTIME=300;
	final int OUTTIME=100; //出电梯后的显示时间
	final int REMAININGTIME=150;
	final int MAXINTERVAL=120;
	final int MININTERVAL=20;
	public Person(int t)
	{
		floor0=(int)(Math.random()*floor_num);
		identity=++ID;
		while((d_floor=(int)(Math.random()*floor_num))==floor0); //d_floor要取与floor不同的数
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
