package com.ningxun.tools;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

/**
 *<li>技术支持:河北凝讯科技有限公司<br/>
 *<li>项目名称: GenerateMP<br/>
 *<li>文件名: PicUtil.java<br/>
 *<li>创建时间: 2017-8-20 下午5:07:16<br/>
 *
 *@author zyt
 *@version [v1.00]
 */
public class PicUtil {
	
	// 网络图片
	public static final int NET_IMG = 1;
	// 本地图片
	public static final int LOCAL_IMG = 2;
	// 将图片裁剪为矩形
	public static final int RECTANGLE = 3;
	// 将图片裁剪成
	public static final int ROUND = 4;

	/**
	 * 
	* 描述: 小图贴到大图上<br/>
	* x,y:小图片定位位置
	* a,b:小图片的压缩宽高
	* type:类型，1，矩形，2，圆形
	* @return void 返回类型 
	* 创建时间：2017-7-27/下午6:32:37<br/>
	* @author Administrator  
	* @version V1.0
	 */
	public static final BufferedImage overlapImage(BufferedImage bigImage, String smallPath,
			int type, int img_type, int x, int y, int a, int b) {
		try {
			BufferedImage small = null;
			if (img_type == NET_IMG) {
				URL url = new URL(smallPath);  
				small = ImageIO.read(url); 
			} else {
				small = ImageIO.read(new File(smallPath));
			}
			// 处理图片将其压缩成正方形的小图  
			BufferedImage  convertImage = scaleByPercentage(small, a, b);
			if (type == RECTANGLE) {
				// 类型是矩形，处理成矩形的，则不做处理
			} else {
				// 裁剪成圆形 （传入的图像必须是正方形的 才会 圆形 如果是长方形的比例则会变成椭圆的）  
	            convertImage = convertCircular(convertImage);
			}
			Graphics2D g = bigImage.createGraphics();
			g.drawImage(convertImage, x, y, convertImage.getWidth(), convertImage.getHeight(), null);
			g.dispose();
			return bigImage;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/** 
	  * 缩小Image，此方法返回源图像按给定宽度、高度限制下缩放后的图像 
	  * @param inputImage 
	  * @param maxWidth：压缩后宽度 
	  * @param maxHeight：压缩后高度 
	  * @throws java.io.IOException 
	  * return  
	  */  
	 private static BufferedImage scaleByPercentage(BufferedImage inputImage, int newWidth, int newHeight) throws Exception {  
	     //获取原始图像透明度类型  
	     int type = inputImage.getColorModel().getTransparency();  
	     int width = inputImage.getWidth();  
	     int height = inputImage.getHeight();  
	     //开启抗锯齿  
	     RenderingHints renderingHints=new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);  
	     //使用高质量压缩  
	     renderingHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);  
	     BufferedImage img = new BufferedImage(newWidth, newHeight, type);  
	     Graphics2D graphics2d =img.createGraphics();  
	     graphics2d.setRenderingHints(renderingHints);          
	     graphics2d.drawImage(inputImage, 0, 0, newWidth, newHeight, 0, 0, width, height, null);  
	     graphics2d.dispose();  
	     return img;  
	 }
	 /** 
	  * 传入的图像必须是正方形的 才会 圆形  如果是长方形的比例则会变成椭圆的 
	  * @param url 用户头像地址   
	  * @return 
	  * @throws IOException 
	  */  
	 private static BufferedImage convertCircular(BufferedImage bi1) throws IOException{  
	    //透明底的图片
	    BufferedImage bi2 = new BufferedImage(bi1.getWidth(), bi1.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);   
	    Ellipse2D.Double shape = new Ellipse2D.Double(0, 0, bi1.getWidth(), bi1.getHeight());  
	    Graphics2D g2 = bi2.createGraphics();
	    g2.setClip(shape);
	    // 使用 setRenderingHint 设置抗锯齿
	    g2.drawImage(bi1,0,0,null);
	    //设置颜色  
	    g2.setBackground(Color.WHITE);
	    g2.dispose();  
	    return bi2;  
	 }
}
