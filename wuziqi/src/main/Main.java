package main;
//Main类
public class Main {
	public static void main(String[] args) {
		GameFrame frame=new GameFrame();//窗口
		GamePanel panel=new GamePanel(frame);//菜单
		frame.add(panel);
		
		frame.setVisible(true);//设置显示窗体
	}
}
