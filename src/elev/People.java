package elev;
import java.util.ArrayList;

public class People implements ElevatorObject
{
	private ArrayList<Person> crew=new ArrayList<Person>();
	final int ENTERTIME =12;
	private int counter=0,counter2=0;
	void upDate(int t, elevator machine) //�������˵ĵ������ȷ����˵���ȥ ���������ʱɾȥ
	{
		
		if(crew.size()==0) 
			{crew.add(new Person(t));machine.ex_button[crew.get(crew.size()-1).floor0]=true;}
		else 
			for(int i=0;i<=crew.size()-1;i++)
			{
				crew.get(i).upDate(t,machine);
				
				if(crew.get(i).nextone)
				{
					if(!crew.get(i).nextonecome) {crew.add(new Person(t));machine.ex_button[crew.get(crew.size()-1).floor0]=true;crew.get(i).nextonecome=true;}
					//if(crew.get(i).bored) crew.remove(i);  //������˵ȵ�ʱ�������������һλ�Ѿ��������Ϳ����Ƴ���
				}
				if(crew.get(i).inelevator)
				{
					if(crew.get(i).outelevator) 
						if(t-crew.get(i).outtime>=crew.get(i).OUTTIME)
						crew.remove(i);
				}
				if(crew.get(i).bored)
				{ 
					if(t-crew.get(i).boredtime>=crew.get(i).REMAININGTIME)
						crew.remove(i);
				}
			}
		
	}
	public ArrayList<Person> getPeople()
	{
		return crew;
	}
	boolean getOn(int current_floor,int t,elevator machine) //�õ���ͣ�µ�¥���ڵȺ�����ϵ���
	{
		
		for(int i=0;i<=crew.size()-1;)
		{
			if(crew.get(i).floor0==current_floor&&!crew.get(i).bored&&!crew.get(i).inelevator)
				//�������������1.��current_floor 2.��û���� 3.��û������
			{
				if(counter==0) counter=t;
				if(((t-counter)%ENTERTIME==0)&&t!=counter) //ÿENTERTIME����λʱ���ִ��һ���ڲ�����
				{
					counter=t;
					crew.get(i).inelevator=true;
					crew.get(i).boarding=false;
					machine.button[crew.get(i).d_floor]=true; //���ڲ�����ť�Ĳ���
				}
				else crew.get(i).boarding=true;
				return false;
			}
			i++;
		}
		counter=0;return true; //��ʾ���и��ϵ��˶��Ѿ��ϵ�����
	}
	boolean getOff(int current_floor,int t) //�õ���ͣ�µ�¥����Ŀ�ĵص����µ���
	{

		for(int i=0;i<=crew.size()-1;)
		{
			if(crew.get(i).d_floor==current_floor&&crew.get(i).inelevator&&!crew.get(i).outelevator)
				//�������������Ŀ�ĵ���current_floor 2.�Ѿ��ڵ����� 3. ��û������
			{
				if(counter2==0) counter2=t;
				if(((t-counter2)%ENTERTIME==0)&&t!=counter2) //ÿENTERTIME����λʱ���ִ��һ���ڲ�����
				{
					counter2=t;
					crew.get(i).outelevator=true;
					crew.get(i).outtime=t;
					crew.get(i).boarding=false;
					System.out.println("OUT");
				}
				else crew.get(i).boarding=true;
				return false;
			}
			i++;
		}
		counter2=0;return true; //��ʾ���и��µ��˶��Ѿ��µ�����
	}
	void Print()
	{
		for(int i=0;i<=crew.size()-1;i++)
		{
			System.out.println("crew"+i);
			System.out.println("floor0"+crew.get(i).floor0);
			System.out.println("d_floor"+crew.get(i).d_floor);
		}
	}
	
}

