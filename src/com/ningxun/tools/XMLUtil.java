package com.ningxun.tools;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;


public class XMLUtil {

	private Document document = null;
	
	public XMLUtil(String xmlStr) {
		try {
			document = DocumentHelper.parseText(xmlStr);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 遍历出递归节点
	 * @param targetNode  目标节点
	 */
	public String getValueByNote(String targetNode) {
		try {
			Element root = document.getRootElement();
			return getChildrenNodes(root, targetNode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 根据父节点获取第一个匹配的节点
	 */
	@SuppressWarnings("unchecked")
	public String getChildrenNodes(Element parentNode, String targetNode) {
		String value = null;
		boolean isFind = false;
		try {
			//递归遍历所有节点
			List<Element> elementList = parentNode.elements();  //获取父节点的子节点
			for (Element element : elementList) {
				if (isFind) break;   //一旦返现目标不在执行递归直接跳出递归
				if (element.getName() == targetNode) {
					isFind = true;    //找到目标节点
					value = element.getTextTrim();
				}
				if (!isFind) {  //没有找到继续执行
					this.getChildrenNodes(element, targetNode);   //递归遍历其子节点
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return value;
	}
	
	

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}
	
}
