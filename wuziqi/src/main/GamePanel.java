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
//������

public class GamePanel extends JPanel implements ActionListener{
	private JMenuBar jmb=null;
	private GameFrame mainFrame=null;
	private GamePanel panel=null;
	
	public final int ROWS=15;
	public final int COWS=15;
	
	private String gameFlag="start";//��Ϸ״̬
	
	//��ʼ��ָʾ����ά����
	public Pointer [][] pointers=new Pointer [ROWS][COWS];
	//�����ӵļ���
	public List<Qizi> qizis=new ArrayList<Qizi>();
	
	//���췽��
	public GamePanel(GameFrame mainFrame) {
		this.setLayout(null);
		this.setOpaque(false);//��ֹ������ʧ
		this.mainFrame=mainFrame;
		this.panel=this;
		//ͼƬ����
		ImageValue.init();
		//�����˵�
		createMenu();
		//����������
		createMouseListener();
		//������������
		createPointers();
	}
	//������������
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
	//����֮ǰ�������һ���ı��
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
				//��ȡ��������
				int x=e.getX();
				int y=e.getY();
				//ѭ��ָʾ���Ķ�ά����
				Pointer pointer;
				for (int i = 0; i < ROWS; i++) {
					for (int j = 0; j < COWS; j++) {
						pointer=pointers[i][j];
						if(pointer.isPoint(x, y)&&pointer.getType()==0) {
							Qizi qizi=new Qizi(pointer.getX(), pointer.getY(), 2);
							qizis.add(qizi);
							pointer.setType(2);
							
							clearAILast();
							//�ػ滭
							repaint();
						//���Խ�����һ������
							if(AI.has5(pointer, panel)) {//���Ѿ��ɹ���
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
				//��ȡ��������
				int x=e.getX();
				int y=e.getY();
				//ѭ��ָʾ���Ķ�ά����
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
					//�ػ滭
					repaint();
					
				}
			}
		};
		addMouseMotionListener(mouseAdapter);
		addMouseListener(mouseAdapter);
	}
	public void paint(Graphics g) {
		super.paint(g);
		//��������
		drawGrid(g);
		//����5��С�ڵ�
		draw5Point(g);
		//����ָʾ��
		drawPointer(g);
		//��������
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
	//��������ڵ�
	private void draw5Point(Graphics g) {
		//���ϵ�
		int x=142;
		int y=142;
		g.fillArc(x, y, 8, 8, 0, 360);
		//����
		x=462;
		g.fillArc(x, y, 8, 8, 0, 360);
		//����
		x=142;
		y=462;
		g.fillArc(x, y, 8, 8, 0, 360);
		//����
		x=462;
		g.fillArc(x, y, 8, 8, 0, 360);
		//����
		x=302;
		y=302;
		g.fillArc(x, y, 8, 8, 0, 360);
	}
	//��������
	private void drawGrid(Graphics g) {
		int start=26;
		int x1=26;
		int y1=26;
		int x2=586;
		int y2=26;
		int dis=40;
		//���ƺ���
		for(int i=0;i<ROWS;i++)
		{
			y1=i*dis+start;
			y2=y1;
			g.drawLine(x1, y1, x2, y2);
		}
		//��������
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
		return new Font("˼Դ����",Font.BOLD,18);
	}
	//�����˵�
	private void createMenu() {
		jmb=new JMenuBar();
		//ȡ������
		Font tFont=createFont();
		//������Ϸѡ��
		JMenu jMenu1 =new JMenu("��Ϸ");
		jMenu1.setFont(tFont);
		//��������ѡ��
		JMenu jMenu2 =new JMenu("����");
		jMenu2.setFont(tFont);
		
		//��Ϸ�Ӳ˵�
		JMenuItem jmi1=new JMenuItem("����Ϸ");
		jmi1.setFont(tFont);
		JMenuItem jmi2=new JMenuItem("�˳�");
		jmi2.setFont(tFont);
		jMenu1.add(jmi1);
		jMenu1.add(jmi2);
		
		//�����Ӳ˵�
		JMenuItem jmi3=new JMenuItem("��������");
		jmi3.setFont(tFont);
		JMenuItem jmi4=new JMenuItem("ʤ������");
		jmi4.setFont(tFont);
		jMenu2.add(jmi3);
		jMenu2.add(jmi4);
		
		jmb.add(jMenu1);
		jmb.add(jMenu2);
		
		mainFrame.setJMenuBar(jmb);
		
		//��Ӽ���
		jmi1.addActionListener(this);
		jmi2.addActionListener(this);
		jmi3.addActionListener(this);
		jmi4.addActionListener(this);
		//����ָ��
		jmi1.setActionCommand("restart");
		jmi2.setActionCommand("exit");
		jmi3.setActionCommand("help");
		jmi4.setActionCommand("win");
	}
	//��д
	public void actionPerformed(ActionEvent e) {
	 String command = e.getActionCommand();
	 System.out.println(command);
	 UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("˼Դ����", Font.ITALIC, 18)));
	 UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("˼Դ����", Font.ITALIC, 18)));
	 if ("exit".equals(command)) {
			Object[] options = { "ȷ��", "ȡ��" };
			int response = JOptionPane.showOptionDialog(this, "��ȷ��Ҫ�˳���", "",
					JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null,
					options, options[0]);
			if (response == 0) {
				System.exit(0);
			} 
		}else if("restart".equals(command)){
			if(!"end".equals(gameFlag)){
				JOptionPane.showMessageDialog(null, "������Ϸ���޷����¿�ʼ��",
						"��ʾ��", JOptionPane.INFORMATION_MESSAGE); 
			}else{
				restart();
			}
		}else if("help".equals(command)){
			JOptionPane.showMessageDialog(null, "�����ָʾ��λ�õ��£������ӣ�",
					"��ʾ��", JOptionPane.INFORMATION_MESSAGE);
		}
		else if("win".equals(command)){
			JOptionPane.showMessageDialog(null, "�������鷽���ʤ����",
					"��ʾ��", JOptionPane.INFORMATION_MESSAGE);
		}	
	}
	//���¿�ʼ
	private void restart() {
		//�������� 1 ��Ϸ״̬ 2 ָʾ�� 3 ����
		
		//��Ϸ״̬
		gameFlag="start";
		//ָʾ��
		Pointer pointer;
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COWS; j++) {
				pointer=pointers[i][j];
				pointer.setType(0);
				pointer.setShow(false);
				
			}
		}
		//����
		qizis.clear();
		
	}
	//��Ϸʤ��
	public void gameWin() {
		gameFlag="end";
		//���������ʾ
		UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("˼Դ����", Font.ITALIC, 18)));
		UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("˼Դ����", Font.ITALIC, 18)));
		JOptionPane.showMessageDialog(mainFrame, "��ʤ����");
	}
	//��Ϸ����
	public void gameOver() {
		gameFlag="end";
		//���������ʾ
		UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("˼Դ����", Font.ITALIC, 18)));
		UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("˼Դ����", Font.ITALIC, 18)));
		JOptionPane.showMessageDialog(mainFrame, "��ʧ����");
	}
}
