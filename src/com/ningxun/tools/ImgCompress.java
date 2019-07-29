package com.ningxun.tools;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;

import com.ningxun.common.InitListener;
import com.ningxun.common.SysConfig;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * <li>@ClassName: ImgCompress<br/> <li>创建时间：2016-7-28 下午3:38:59<br/>
 * 图片压缩类<br/>
 * 
 * @author lsy
 */
public class ImgCompress {

	private Image img;
	private int width;
	private int height;
	private File imgFile;
	private String path;
	private String type;
	private String picName;

	/**
	 * <p>
	 * Title: 初始化
	 * </p>
	 * <p>
	 * Description: 调用完成之后调用getpicName();方法可以得到压缩后的图片系统无前缀 名(前缀为"max_";"mid_";"min_")
	 * </p>
	 * 
	 * @param imgFile 上传的图片
	 * @param fileName 上传的图片原名
	 * @param path 需要上传的路径
	 */
	public ImgCompress(File imgFile, String fileName, String path) {
		this.imgFile = imgFile;
		this.path = path;
		this.type = getExt(fileName);
		this.init();
	}

	private void init() {
		try {
			img = ImageIO.read(imgFile); // 构造Image对象
			width = img.getWidth(null); // 得到源图宽
			height = img.getHeight(null); // 得到源图长

			Map<String, String> baseDataMap = InitListener.getBaseDataMap();
			int maxSize = 0;
			int midSize = 0;
			int minSize = 0;
			if (baseDataMap == null || baseDataMap.size() == 0) {
				// 没有加载到内存，读取配置文件
				maxSize = Integer.parseInt(SysConfig.getConfigData("ImgCompress", "max"));
				midSize = Integer.parseInt(SysConfig.getConfigData("ImgCompress", "mid"));
				minSize = Integer.parseInt(SysConfig.getConfigData("ImgCompress", "min"));
			} else {
				// 直接内存读取内存
				maxSize = Integer.parseInt(baseDataMap.get("imgMax"));
				midSize = Integer.parseInt(baseDataMap.get("imgMid"));
				minSize = Integer.parseInt(baseDataMap.get("imgMin"));
			}
			picName = UUID.randomUUID().toString() + type;
			this.resizeFix("max_" + picName, maxSize, maxSize);
			this.resizeFix("mid_" + picName, midSize, midSize);
			this.resizeFix("min_" + picName, minSize, minSize);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 保存原图<br/>
	 * 
	 * @return String 返回类型 创建时间：2016-7-29/上午9:04:44<br/>
	 * @author lsy
	 * @version V1.0
	 */
	public String saveOriginalPic() {
		try {
			String realName = UUID.randomUUID().toString() + type;// 保存的文件名称，使用UUID+后缀进行保存
			File target = new File(path, realName);
			FileUtils.copyFile(imgFile, target);// 上传至服务器
			return realName;// 上传成功
		} catch (Exception e) {
			e.printStackTrace();
			return "";// 上传异常
		}
	}

	/**
	 * 获取文件后缀<br/>
	 * 
	 * @return String 返回类型 创建时间：2016-7-29/上午9:28:02<br/>
	 * @author lsy
	 * @version V1.0
	 */
	public String getExt(String fileName) {
		return fileName.substring(fileName.lastIndexOf("."));
	}

	/**
	 * 按照宽度还是高度进行压缩
	 * 
	 * @param tp String 图片前缀
	 * @param w int 最大宽度
	 * @param h int 最大高度
	 * @return 压缩后的图片系统名称
	 */
	public void resizeFix(String tp, int w, int h) {
		try {
			if (width / height > w / h) {
				h = (int) (height * w / width);
				resize(tp, w, h);
			} else {
				w = (int) (width * h / height);
				resize(tp, w, h);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 强制压缩/放大图片到固定的大小
	 * 
	 * @param tp String 图片前缀
	 * @param w int 新宽度
	 * @param h int 新高度
	 */
	@SuppressWarnings("restriction")
	private void resize(String tp, int w, int h) throws IOException {
		// SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢
		BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		image.getGraphics().drawImage(img, 0, 0, w, h, null); // 绘制缩小后的图
		File destFile = new File(path + tp);
		FileOutputStream out = new FileOutputStream(destFile); // 输出到文件流
		// 可以正常实现bmp、png、gif转jpg
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		encoder.encode(image); // JPEG编码
		out.close();
	}

	public String getPicName() {
		return picName;
	}

	public void setPicName(String picName) {
		this.picName = picName;
	}

}
