package com.ningxun.card.action;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.struts2.ServletActionContext;

import com.ningxun.common.BaseSupportAction;
import com.ningxun.common.HibernateDAO;
import com.ningxun.security.action.WXUserSS;
import com.ningxun.tools.Config;
import com.ningxun.tools.PicUtil;
import com.ningxun.wxuserinfo.dao.LocationDAO;
import com.ningxun.wxuserinfo.vo.Location;
import com.ningxun.wxuserinfo.vo.WXUser;
import com.opensymphony.xwork2.ActionContext;

/**
 *<li>技术支持:河北凝讯科技有限公司<br/>
 *<li>项目名称: rongqiu<br/>
 *<li>文件名: PersonalCardAction.java<br/>
 *<li>创建时间: 2017-7-27 下午10:38:19<br/>
 *
 *@author zyt
 *@version [v1.00]
 */
@SuppressWarnings("serial")
public class PersonalCardAction extends BaseSupportAction {
	
	// 图片存放路径
	public static final String BASE_IMG_PATH = "img\\";
	// 个人图片背景1
	public static final String IMG_PATH1 = "mb1.jpg";
	// 个人图片背景2
	public static final String IMG_PATH2 = "mb2.jpg";
	// 个人图片背景3
	public static final String IMG_PATH3 = "mb3.jpg";
	// 暂无头像图片
	public static final String IMG_ZANWU = ServletActionContext.getServletContext().getRealPath("/common/") + "\\" +
			BASE_IMG_PATH + "default.png";
	// 背景类型
	private Integer indexNum;
	/**
	 * 
	* 描述: 生成个人名片<br/>
	*
	* @return String 返回类型
	*
	* 创建时间：2017-7-28/上午9:52:26<br/>
	* @author Administrator
	* @version V1.0
	 */
	public void showPersonalCard() {
		try {
			// 获取当前登录对象
			WXUserSS wxUserSS = (WXUserSS) getUser();
			Integer userId = wxUserSS.getId();
			
			WXUser wxUser = (WXUser) HibernateDAO.findById(WXUser.class, userId);
			
			BufferedImage bi = null;
			if (indexNum == 1) {
				bi = writeFontToIcon(
						ServletActionContext.getServletContext().getRealPath("/common/") + "\\" +
						BASE_IMG_PATH + IMG_PATH1, wxUser, indexNum);
			} else if (indexNum == 2) {
				bi = writeFontToIcon(
						ServletActionContext.getServletContext().getRealPath("/common/") + "\\" +
						BASE_IMG_PATH + IMG_PATH2, wxUser, indexNum);
			} else {
				bi = writeFontToIcon(
						ServletActionContext.getServletContext().getRealPath("/common/") + "\\" +
						BASE_IMG_PATH + IMG_PATH3, wxUser, indexNum);
			}
			
			// getRequest().getSession();
			getResponse().setContentType("image/jpg");
			outputImg(bi, getResponse().getOutputStream());
			// getResponse().getOutputStream().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	* 描述: 跳转显示个人信息卡片<br/>
	*
	* @return String 返回类型
	*
	* 创建时间：2017-7-29/上午8:55:18<br/>
	* @author Administrator  
	* @version V1.0
	 */
	public String cardShow() {
		try {
			ActionContext.getContext().getValueStack().set("indexNum", indexNum);
			return SUCCESS;
		} catch (Exception e) {
			return ERROR;
		}
	}
	/**
	 * 
	* 描述: 将字体写入文字，得到图片缓冲流<br/>
	*
	* @return BufferedImage 返回类型
	*
	* 创建时间：2017-7-28/上午9:52:50<br/>
	* @author Administrator  
	* @version V1.0
	 */
	public static BufferedImage writeFontToIcon(String fromPath, WXUser wxUser, Integer indexNum) {
		try {
			// 读取图片文件，得到BufferedImage对象
			BufferedImage bimg = ImageIO.read(new FileInputStream(fromPath));
			// 结果对象
			BufferedImage result = null;
			/******************初始化用户信息*********************/
			String habitFootString = "";
			if (wxUser.getHabitfoot() == 1) {
				habitFootString = "左脚";
			} else if (wxUser.getHabitfoot() == 2) {
				habitFootString = "右脚";
			} else {
				habitFootString = "暂无信息";
			}
			String heightString = "";
			if (wxUser.getHeight() != null && wxUser.getHeight().trim() != "") {
				heightString = wxUser.getHeight() + "cm";
			} else {
				heightString = "暂无信息";
			}
			String weightString = "";
			if (wxUser.getWeight() != null && wxUser.getWeight().trim() != "") {
				weightString = wxUser.getWeight() + "kg";
			} else {
				weightString = "暂无信息";
			}
			// 查询用户常踢位置
			LocationDAO locationDAO = new LocationDAO();
			List<Location> locations = locationDAO.findUserLocation(wxUser.getId());
			String locationString = "";
			if (locations != null && locations.size() > 0) {
				for (int i = 0; i < locations.size(); i++) {
					if (i == locations.size() - 1) {
						locationString += locations.get(i).getLocationName();
					} else {
						locationString += locations.get(i).getLocationName()+ ",";
					}
				}
			} else {
				locationString = "暂无信息";
			}
			// 设置头像
			String headString = "";
			int img_type = 2;
			if (wxUser.getHeadPortraitNew() == null && wxUser.getHeadPortrait() == null) {
				// 用户没有头像的时候，使用默认暂无的图片
				headString = IMG_ZANWU;
				img_type = PicUtil.LOCAL_IMG;
			} else if (wxUser.getHeadPortraitNew().contains("http")) {
				// 用户使用微信默认头像的时候
				headString = wxUser.getHeadPortraitNew();
				img_type = PicUtil.NET_IMG;
			} else {
				// 用户更换了头像，使用本地头像
				headString = Config.getFileUploadPath() + wxUser.getHeadPortraitNew();
				img_type = PicUtil.LOCAL_IMG;
			}
			/********************初始化完成***********************/
			
			// 根据用户选择的不同背景画出不同的模板
			if (indexNum == 1) {
				
				// (名片个人姓名)
				Graphics2D myName = (Graphics2D) bimg.getGraphics();
				
				myName.setStroke(new BasicStroke(59));
				
				// 设置颜色和画笔粗细
				myName.setColor(Color.BLACK);
				myName.setStroke(new BasicStroke(59));
				myName.setFont(new Font("楷体", Font.BOLD, 45));
				
				myName.drawString(wxUser.getNickname(), 550, 640);
				
				// (名片内容画笔)
				Graphics2D content = (Graphics2D) bimg.getGraphics();

				content.setStroke(new BasicStroke(59));
				
				// 设置颜色和画笔粗细
				content.setColor(Color.BLACK);
				content.setStroke(new BasicStroke(59));
				content.setFont(new Font("楷体", Font.BOLD, 30));
				
				content.drawString("擅长脚："+habitFootString, 75, 650);
				
				content.drawString("身高：" + heightString, 75, 700);
				
				content.drawString("体重：" + weightString, 75, 750);
				
				content.drawString("位置：" + locationString, 75, 800);
				
				// 设置头像
				result = PicUtil.overlapImage(bimg, headString, PicUtil.ROUND, img_type, 495, 397, 197, 197);
				
			} else if (indexNum == 2) {
				
				// (名片个人姓名)
				Graphics2D myName = (Graphics2D) bimg.getGraphics();
				
				myName.setStroke(new BasicStroke(59));
				
				// 设置颜色和画笔粗细
				myName.setColor(Color.WHITE);
				myName.setStroke(new BasicStroke(59));
				myName.setFont(new Font("黑体", Font.BOLD, 60));
				
				myName.drawString(wxUser.getNickname(), 550, 320);
				
				// (名片内容画笔)
				Graphics2D content = (Graphics2D) bimg.getGraphics();

				content.setStroke(new BasicStroke(59));
				
				// 设置颜色和画笔粗细
				content.setColor(Color.WHITE);
				content.setStroke(new BasicStroke(59));
				content.setFont(new Font("黑体", Font.BOLD, 30));
				
				content.drawString("擅长脚：" + habitFootString, 50, 650);
				
				content.drawString("身高：" + heightString, 50, 700);
				
				content.drawString("体重：" + weightString, 50, 750);
				
				content.drawString("位置：" + locationString, 50, 800);
				
				// 设置头像
				result = PicUtil.overlapImage(bimg, headString, PicUtil.ROUND, img_type, 545, 50, 197, 197);
			} else {
				// (名片个人姓名)
				Graphics2D myName = (Graphics2D) bimg.getGraphics();
				
				myName.setStroke(new BasicStroke(59));
				
				// 设置颜色和画笔粗细
				myName.setColor(Color.BLACK);
				myName.setStroke(new BasicStroke(59));
				myName.setFont(new Font("隶书", Font.BOLD, 60));
				
				myName.drawString(wxUser.getNickname(), 350, 350);
				
				// (名片内容画笔)
				Graphics2D content = (Graphics2D) bimg.getGraphics();

				content.setStroke(new BasicStroke(59));
				
				// 设置颜色和画笔粗细
				content.setColor(Color.BLACK);
				content.setStroke(new BasicStroke(59));
				content.setFont(new Font("隶书", Font.BOLD, 25));
				
				content.drawString("擅长脚：" + habitFootString, 150, 400);
				
				content.drawString("身高：" + heightString, 150, 450);
				
				content.drawString("体重：" + weightString, 150, 500);
				
				content.drawString("位置：" + locationString, 150, 550);
				// 设置头像
				result = PicUtil.overlapImage(bimg, headString, PicUtil.RECTANGLE, img_type, 120, 150, 197, 197);
			}
			// 保存新图片
			return result;
		} catch (Exception e) {
			return null;
		}
	}
	/**
	 * 
	* 描述: 保存图片到指定的输出流<br/>
	*
	* @return void 返回类型
	*
	* 创建时间：2017-7-28/上午9:53:24<br/>
	* @author Administrator  
	* @version V1.0
	 */
	public static void outputImg(BufferedImage image, OutputStream out) throws Exception {
		ImageIO.write(image, "JPG", out);
	}
	public Integer getIndexNum() {
		return indexNum;
	}
	public void setIndexNum(Integer indexNum) {
		this.indexNum = indexNum;
	}
	
	
}
