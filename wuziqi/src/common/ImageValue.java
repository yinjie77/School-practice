package common;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

//ͼƬ����
public class ImageValue {
	//�ڰ���ͼƬ
	public static BufferedImage whiteImage=null;
	public static BufferedImage blackImage=null;
	//ͼƬ·��
	private static String path="/images/";
	
	//��ʼ��ͼƬ�ķ���
	public static void init() {
		try {
			whiteImage=ImageIO.read(ImageValue.class.getResource(path+"white.png"));
			blackImage=ImageIO.read(ImageValue.class.getResource(path+"black.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
