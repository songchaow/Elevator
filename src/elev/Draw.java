package elev;

import java.awt.*;
import javax.swing.*;

import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import javax.imageio.*;
import javax.imageio.stream.ImageOutputStream;
public class Draw extends JPanel implements ElevatorObject
{
	elevator machine=new elevator();
	People crew=new People();
	final int ELEVATOR_WIDTH=150;
	final int ELEVATOR_HEIGHT=50;
	final int PERSON_WIDTH=18; //人的大小
	final int PERSON_INTERVAL=20; //排队人的间距
	final int BUTTON_INTERVAL=25;
	int BUTTON_Y=30;
	int PEOPLE_X()
	{
		return getWidth()*2/5;
	}
	int ELEVATOR_X()
	{
		return getWidth()*3/5;
	}
	int calYPosition(double position)
	{
		return((int)((floor_num-position-1)*getHeight()/(floor_num+1)+getHeight()/(floor_num+1)));
	}
	void upDate(int t)
	{
		machine.upDate(t);
		crew.upDate(t,machine);
		if(machine.getState()==STATE_WAITING)
		{
			if(crew.getOff(machine.current_floor,t))
				if(crew.getOn(machine.current_floor,t,machine))
				machine.Start();
			
		}
			
	}
	public void paintElevator(Graphics2D g, int y)
	{
		g.setColor(Color.BLACK);
		g.drawRect(ELEVATOR_X(), y-ELEVATOR_HEIGHT, ELEVATOR_WIDTH, ELEVATOR_HEIGHT);
		String str=new String();
		if(machine.getState()==STATE_OPENING) 
			str="Opening...";
		if(machine.getState()==STATE_CLOSING)
			str="Closing...";
		if(machine.getState()==STATE_WAITING)
			str="Waiting...";
		if(machine.getState()==STATE_UP)
			str="Going up";
		if(machine.getState()==STATE_DOWN)
			str="Going down";
		if(machine.getState()==STATE_INITIAL)
			str="Initial State";
		g.drawString(str, ELEVATOR_X(), y-ELEVATOR_HEIGHT-5);
	}
	public void paintPeople(Graphics2D g)
	{
		g.setFont(new Font("Calibri",Font.PLAIN,16));
		ArrayList<Person> p=crew.getPeople();
		int[] coord_x=new int[floor_num];
		int coord_x2=ELEVATOR_X();
		for(int i=0;i<=floor_num-1;i++)
		{
			coord_x[i]=ELEVATOR_X()-2*PERSON_WIDTH;
		}
		for(int i=0;i<=p.size()-1;i++)
		{
			if(!p.get(i).inelevator) //画在外等待的人 用红色画不耐烦的人 蓝色画正常等待的人 黄色画正在上电梯的人
			{
				if(p.get(i).bored) g.setColor(Color.RED);
				else if(p.get(i).boarding) g.setColor(Color.YELLOW);
				else g.setColor(Color.BLUE);
				g.fillOval(coord_x[p.get(i).floor0], calYPosition(p.get(i).floor0)-PERSON_WIDTH, PERSON_WIDTH, PERSON_WIDTH);
				g.setColor(Color.GRAY);
				g.drawString(String.valueOf(p.get(i).identity), coord_x[p.get(i).floor0], calYPosition(p.get(i).floor0)-PERSON_WIDTH);
				coord_x[p.get(i).floor0]-=PERSON_INTERVAL;
			}
			else //画正在坐电梯的人 用橙色
			{
				if(!p.get(i).outelevator) 
				{
					g.setColor(Color.ORANGE);
					g.fillOval(coord_x2,calYPosition(machine.getPosition())-PERSON_WIDTH,PERSON_WIDTH, PERSON_WIDTH);
					g.setColor(Color.GRAY);
					g.drawString(String.valueOf(p.get(i).identity), coord_x2, calYPosition(machine.getPosition())-PERSON_WIDTH);
					coord_x2+=PERSON_INTERVAL;
				}
				else //已经下电梯的人 用绿色
				{
					g.setColor(Color.GREEN);
					g.fillOval(coord_x[p.get(i).d_floor], calYPosition(p.get(i).d_floor)-PERSON_WIDTH, PERSON_WIDTH, PERSON_WIDTH);
					g.setColor(Color.GRAY);
					g.drawString(String.valueOf(p.get(i).identity), coord_x[p.get(i).d_floor], calYPosition(p.get(i).d_floor)-PERSON_WIDTH);
					coord_x[p.get(i).d_floor]-=PERSON_INTERVAL;
				}
				
			}
		}
	}
	public void paintButtons(Graphics2D g)
	{
		g.setFont(new Font("Calibri",Font.BOLD,30));
		for(int i=0;i<=floor_num-1;i++)
		{
			if(machine.button[i]) g.setColor(Color.BLACK);
			else g.setColor(Color.GRAY);
			g.drawString(String.valueOf(i), getWidth()/(2*floor_num)*i, BUTTON_Y);
			if(machine.ex_button[i]) g.setColor(Color.BLACK);
			else g.setColor(Color.GRAY);
			g.drawString(String.valueOf(i), getWidth()/(2*floor_num)*i, BUTTON_Y+BUTTON_INTERVAL);
		}
		g.setFont(new Font("等线",Font.PLAIN,20));
		g.setColor(Color.BLACK);
		g.drawString("内部按钮", getWidth()/2, BUTTON_Y);
		g.drawString("外部按钮", getWidth()/2, BUTTON_Y+BUTTON_INTERVAL);
		g.setFont(new Font("Calibri",Font.BOLD,20));
		g.setColor(Color.GRAY);
		for(int i=0;i<=floor_num-1;i++)
			g.drawString(String.valueOf(i), ELEVATOR_X()+160, calYPosition(i));
		
	}
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
			g2d.setColor(Color.RED);
		paintElevator(g2d,calYPosition(machine.getPosition()));
		paintPeople(g2d);
		paintButtons(g2d);
	}

}

