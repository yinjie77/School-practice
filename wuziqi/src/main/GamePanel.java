package main;

import java.awt.Font;
import java.awt.Graphics;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.ArrayList;


import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import common.ImageValue;
//画布类

public class GamePanel extends JPanel implements ActionListener{
	private JMenuBar jmb=null;
	private GameFrame mainFrame=null;
	private GamePanel panel=null;
	
	public final int ROWS=15;
	public final int COWS=15;
	
	private String gameFlag="start";//游戏状态
	
	//初始化指示器二维数组
	public Pointer [][] pointers=new Pointer [ROWS][COWS];
	//存棋子的集合
	public List<Qizi> qizis=new ArrayList<Qizi>();
	
	//构造方法
	public GamePanel(GameFrame mainFrame) {
		this.setLayout(null);
		this.setOpaque(false);//防止背景消失
		this.mainFrame=mainFrame;
		this.panel=this;
		//图片加载
		ImageValue.init();
		//创建菜单
		createMenu();
		//创建鼠标监听
		createMouseListener();
		//创建数组内容
		createPointers();
	}
	//创建数组内容
	private void createPointers() {
		int x=0;
		int y=0;
		int start=26;
		Pointer pointer;
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COWS; j++) {
				x=j*40+start;
				y=i*40+start;
				pointer=new Pointer(i, j, x, y);
				pointers[i][j]=pointer;
				
			}
		}
	}
	//清理之前电脑最后一步的标记
	private void clearAILast() {
		Qizi qizi;
		for (int i = 0; i < qizis.size(); i++) {
			qizi=qizis.get(i);
			if(qizi!=null&&qizi.getType()==1) {
				qizi.setLast(false);
			}
		}
		
	}
	private void createMouseListener() {
		MouseAdapter mouseAdapter=new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!"start".equals(gameFlag))return ;
				//获取鼠标的坐标
				int x=e.getX();
				int y=e.getY();
				//循环指示器的二维数组
				Pointer pointer;
				for (int i = 0; i < ROWS; i++) {
					for (int j = 0; j < COWS; j++) {
						pointer=pointers[i][j];
						if(pointer.isPoint(x, y)&&pointer.getType()==0) {
							Qizi qizi=new Qizi(pointer.getX(), pointer.getY(), 2);
							qizis.add(qizi);
							pointer.setType(2);
							
							clearAILast();
							//重绘画
							repaint();
						//电脑进行下一步下棋
							if(AI.has5(pointer, panel)) {//你已经成功了
								gameWin();
							}
							else {
								AI.next(panel);
							}
							
							return;
						}
					}
				
				}
			}
			
			@Override
			public void mouseMoved(MouseEvent e) {
				if(!"start".equals(gameFlag))return ;
				//获取鼠标的坐标
				int x=e.getX();
				int y=e.getY();
				//循环指示器的二维数组
				Pointer pointer;
				for (int i = 0; i < ROWS; i++) {
					for (int j = 0; j < COWS; j++) {
						pointer=pointers[i][j];
						if(pointer.isPoint(x, y)&&pointer.getType()==0) {
							pointer.setShow(true);
						}else {
							pointer.setShow(false);
						}
					}
					//重绘画
					repaint();
					
				}
			}
		};
		addMouseMotionListener(mouseAdapter);
		addMouseListener(mouseAdapter);
	}
	public void paint(Graphics g) {
		super.paint(g);
		//绘制网格
		drawGrid(g);
		//绘制5个小黑点
		draw5Point(g);
		//绘制指示器
		drawPointer(g);
		//绘制棋子
		drawQizi(g);
	}
	private void drawQizi(Graphics g) {
		Qizi qizi;
		for (int i = 0; i < qizis.size(); i++) {
			qizi=qizis.get(i);
			qizi.draw(g);
		}
		
	}
	private void drawPointer(Graphics g) {
		Pointer pointer;
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COWS; j++) {
				pointer = pointers[i][j];
				if(pointer!=null)
					pointer.draw(g);
			}
		}
		
	}
	//绘制五个黑点
	private void draw5Point(Graphics g) {
		//左上点
		int x=142;
		int y=142;
		g.fillArc(x, y, 8, 8, 0, 360);
		//右上
		x=462;
		g.fillArc(x, y, 8, 8, 0, 360);
		//左下
		x=142;
		y=462;
		g.fillArc(x, y, 8, 8, 0, 360);
		//右下
		x=462;
		g.fillArc(x, y, 8, 8, 0, 360);
		//中心
		x=302;
		y=302;
		g.fillArc(x, y, 8, 8, 0, 360);
	}
	//绘制网格
	private void drawGrid(Graphics g) {
		int start=26;
		int x1=26;
		int y1=26;
		int x2=586;
		int y2=26;
		int dis=40;
		//绘制横线
		for(int i=0;i<ROWS;i++)
		{
			y1=i*dis+start;
			y2=y1;
			g.drawLine(x1, y1, x2, y2);
		}
		//绘制竖线
		y1=26;
		y2=586;
		for(int i=0;i<COWS;i++)
		{
			x1=i*dis+start;
			x2=x1;
			g.drawLine(x1, y1, x2, y2);
		}
		
	}
	private Font createFont() {
		return new Font("思源宋体",Font.BOLD,18);
	}
	//创建菜单
	private void createMenu() {
		jmb=new JMenuBar();
		//取得字体
		Font tFont=createFont();
		//创建游戏选项
		JMenu jMenu1 =new JMenu("游戏");
		jMenu1.setFont(tFont);
		//创建帮助选项
		JMenu jMenu2 =new JMenu("帮助");
		jMenu2.setFont(tFont);
		
		//游戏子菜单
		JMenuItem jmi1=new JMenuItem("新游戏");
		jmi1.setFont(tFont);
		JMenuItem jmi2=new JMenuItem("退出");
		jmi2.setFont(tFont);
		jMenu1.add(jmi1);
		jMenu1.add(jmi2);
		
		//帮助子菜单
		JMenuItem jmi3=new JMenuItem("操作帮助");
		jmi3.setFont(tFont);
		JMenuItem jmi4=new JMenuItem("胜利条件");
		jmi4.setFont(tFont);
		jMenu2.add(jmi3);
		jMenu2.add(jmi4);
		
		jmb.add(jMenu1);
		jmb.add(jMenu2);
		
		mainFrame.setJMenuBar(jmb);
		
		//添加监听
		jmi1.addActionListener(this);
		jmi2.addActionListener(this);
		jmi3.addActionListener(this);
		jmi4.addActionListener(this);
		//设置指令
		jmi1.setActionCommand("restart");
		jmi2.setActionCommand("exit");
		jmi3.setActionCommand("help");
		jmi4.setActionCommand("win");
	}
	//重写
	public void actionPerformed(ActionEvent e) {
	 String command = e.getActionCommand();
	 System.out.println(command);
	 UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("思源宋体", Font.ITALIC, 18)));
	 UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("思源宋体", Font.ITALIC, 18)));
	 if ("exit".equals(command)) {
			Object[] options = { "确定", "取消" };
			int response = JOptionPane.showOptionDialog(this, "您确认要退出吗", "",
					JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null,
					options, options[0]);
			if (response == 0) {
				System.exit(0);
			} 
		}else if("restart".equals(command)){
			if(!"end".equals(gameFlag)){
				JOptionPane.showMessageDialog(null, "正在游戏中无法重新开始！",
						"提示！", JOptionPane.INFORMATION_MESSAGE); 
			}else{
				restart();
			}
		}else if("help".equals(command)){
			JOptionPane.showMessageDialog(null, "鼠标在指示器位置点下，则落子！",
					"提示！", JOptionPane.INFORMATION_MESSAGE);
		}
		else if("win".equals(command)){
			JOptionPane.showMessageDialog(null, "五子连珠方获得胜利！",
					"提示！", JOptionPane.INFORMATION_MESSAGE);
		}	
	}
	//重新开始
	private void restart() {
		//参数重置 1 游戏状态 2 指示器 3 棋子
		
		//游戏状态
		gameFlag="start";
		//指示器
		Pointer pointer;
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COWS; j++) {
				pointer=pointers[i][j];
				pointer.setType(0);
				pointer.setShow(false);
				
			}
		}
		//棋子
		qizis.clear();
		
	}
	//游戏胜利
	public void gameWin() {
		gameFlag="end";
		//弹出结果显示
		UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("思源宋体", Font.ITALIC, 18)));
		UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("思源宋体", Font.ITALIC, 18)));
		JOptionPane.showMessageDialog(mainFrame, "你胜利了");
	}
	//游戏结束
	public void gameOver() {
		gameFlag="end";
		//弹出结果显示
		UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("思源宋体", Font.ITALIC, 18)));
		UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("思源宋体", Font.ITALIC, 18)));
		JOptionPane.showMessageDialog(mainFrame, "你失败了");
	}
}
