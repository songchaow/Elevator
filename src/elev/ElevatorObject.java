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
	//1:up 上行 2: down 下行 0:initial 初态
	//3:opening 正在开门 4:waiting 等人进出
	//5:closing 正在关门 6:ready 门已关，待命
}
