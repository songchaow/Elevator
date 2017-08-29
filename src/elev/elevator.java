package elev;
import java.lang.reflect.Array;
import java.util.ArrayList;
public class elevator implements ElevatorObject {
	int current_floor;
	int t0=0; //用于记录开门时间
	private double position; //用小数表示floor，记录运行位置
	boolean button[]=new boolean[floor_num];
	boolean ex_button[]=new boolean[floor_num]; //外部按钮
	final double speed=0.4; //决定每秒钟position的改变
	final double ACCURACY=0.01;
	final int doortime=12;
	final int DEFAULT_FLOOR=1; //电梯闲置时停下的楼层
	private int state; 
	private int state0=STATE_INITIAL;
	//1:up 上行 2: down 下行 0:initial 初态
	//3:opening 正在开门 4:waiting 等人进出
	//5:closing 正在关门 6:ready 门已关，待命
	int getState()
	{
		return state;
	}
	void Start()
	{
		if(state==STATE_WAITING)
		{
			state=STATE_CLOSING; //使电梯关门
			button[current_floor]=false;
			ex_button[current_floor]=false;
		}
	}
	double getPosition()
	{
		return position;
	}
	public elevator()
	{
		current_floor=1;
		position=1;
		state=STATE_INITIAL;
		for(int i=0;i<=floor_num-1;i++)
			{
			button[i]=false; //初始时外面按钮都没按
			ex_button[i]=false;
			}
	}
	boolean isEmpty()
	{
		for(int i=0;i<=button.length-1;i++)
			if(button[i]||ex_button[i]) return false;
		return true;
	}
	boolean upperRequest() //电梯上方仍有按钮按下
	{
		if(current_floor==floor_num-1) return false;
		else
		for(int i=current_floor+1;i<=floor_num-1;i++)
			if(button[i]||ex_button[i]) return true;
		return false;
	}
	boolean lowerRequest() //电梯下方仍有按钮按下
	{
		if(current_floor==0) return false;
		else
		for(int i=current_floor-1;i>=0;i--)
			if(button[i]||ex_button[i]) return true;
		return false;
	}
	void move(boolean up)
	{
		if(up)
			position+=INTERVAL*speed;
		else
			position-=INTERVAL*speed;
	}
	void upDate(int t)
	{
		//TODO: 由外部确定有哪些button被按？done.
		
		
		//TODO: 电梯，根据button确定运动状态，安排开关门
		if(state==STATE_UP)
		{
			state0=STATE_UP;
			if((Math.abs((int)position-position)<ACCURACY)||(Math.abs((int)position+1-position)<ACCURACY))
			{
				int passby=Math.abs((int)position-position)<Math.abs((int)position+1-position)?(int)position:(int)position+1;
				if(passby==1&&isEmpty()) state=STATE_INITIAL;
				else if(button[passby]||ex_button[passby])  //经过的这层楼被按下按钮，则停下
				{
					state=STATE_OPENING;
					button[passby]=false;
					ex_button[passby]=false;
				}
				else move(true);
				current_floor=passby;
			}
			else move(true);
		}
		if(state==STATE_DOWN)
		{
			state0=STATE_DOWN;
			if((Math.abs((int)position-position)<ACCURACY)||(Math.abs((int)position+1-position)<ACCURACY))
			{
				int passby=Math.abs((int)position-position)<Math.abs((int)position+1-position)?(int)position:(int)position+1;
				if(passby==1&&isEmpty()) state=STATE_INITIAL;
				else if(button[passby]||ex_button[passby])  //经过的这层楼被按下按钮，则停下
				{
					state=STATE_OPENING;
					button[passby]=false;
					ex_button[passby]=false;
				}
				else move(false);
				current_floor=passby;
			}
			else move(false);
		}
		if(state==STATE_OPENING)
		{
			if(t0==0) t0=t;
			if(t==t0+doortime) //仅当t变化时才执行一次
			{
				state=STATE_WAITING;
				t0=0;
			}
		}
		if(state==STATE_CLOSING)
		{
			if(t0==0) t0=t;
			if(t==t0+doortime) //仅当t变化时才执行一次
			{
				state=STATE_READY;
				t0=0;
			}
		}
		if(state==STATE_INITIAL)
		{
			if(upperRequest()) state=STATE_UP;
			else if(lowerRequest()) state=STATE_DOWN;
			else if(button[current_floor]||ex_button[current_floor]) state=STATE_OPENING; //当前楼层被按下就开门
		}
		if(state==STATE_READY)
		{
			if(!isEmpty()) //有按钮被按
			{
				if(state0==STATE_UP)
					if(!upperRequest()) state=STATE_DOWN;
					else state=STATE_UP;
				else if(state0==STATE_DOWN)
					if(!lowerRequest()) state=STATE_UP;
					else state=STATE_DOWN;
				else if(state0==STATE_INITIAL) //电梯在初始时就有人上的情况
				{
					if(upperRequest()) state=STATE_UP;
					else if(lowerRequest()) state=STATE_DOWN;
				}
			}
			else //没按钮被按
			{
				if(current_floor>DEFAULT_FLOOR) state=STATE_DOWN;
				else if(current_floor<DEFAULT_FLOOR) state=STATE_UP;
				else state=STATE_INITIAL;
			}
			
		}
	}
}
