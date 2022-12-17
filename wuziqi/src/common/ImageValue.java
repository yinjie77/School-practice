package common;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

//Í¼Æ¬¼ÓÔØ
public class ImageValue {
	//ºÚ°×ÆåÍ¼Æ¬
	public static BufferedImage whiteImage=null;
	public static BufferedImage blackImage=null;
	//Í¼Æ¬Â·¾¶
	private static String path="/images/";
	
	//³õÊ¼»¯Í¼Æ¬µÄ·½·¨
	public static void init() {
		try {
			whiteImage=ImageIO.read(ImageValue.class.getResource(path+"white.png"));
			blackImage=ImageIO.read(ImageValue.class.getResource(path+"black.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
