package elev;

public interface ElevatorObject {
	public static final int floor_num=10;
	public static final double INTERVAL=0.005;
	public static final int STATE_INITIAL=0;
	public static final int STATE_UP=1;
	public static final int STATE_DOWN=2;
	public static final int STATE_OPENING=3;
	public static final int STATE_WAITING=4;
	public static final int STATE_CLOSING=5;
	public static final int STATE_READY=6;
	public static final int DEFAULT_WIDTH=700;
    public static final int DEFAULT_HEIGHT=700;
	//1:up ���� 2: down ���� 0:initial ��̬
	//3:opening ���ڿ��� 4:waiting ���˽���
	//5:closing ���ڹ��� 6:ready ���ѹأ�����
}
