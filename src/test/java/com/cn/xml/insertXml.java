package com.cn.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

/**
 * @description:
 * @author: helisen
 * @create: 2021-02-01 13:42
 **/
public class insertXml {
    /**
     * 遍历xml文档
     */
    public static void queryXml() {
        try {
            //得到DOM解析器的工厂实例
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            //从DOM工厂中获得DOM解析器
            DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
            //把要解析的xml文档
            Document doc = dbBuilder.parse("src/test/resources/xml/school.xml");
            //得到文档名称为Student的元素的节点列表
            NodeList nList = doc.getElementsByTagName("Student");
            //遍历该集合，显示结合中的元素及其子元素的名字
            for (int i = 0; i < nList.getLength(); i++) {
                Element node = (Element) nList.item(i);
                System.out.println("Name: " + node.getElementsByTagName("Name").item(0).getFirstChild().getNodeValue());
                System.out.println("Num: " + node.getElementsByTagName("Num").item(0).getFirstChild().getNodeValue());
                System.out.println("Classes: " + node.getElementsByTagName("Classes").item(0).getFirstChild().getNodeValue());
                System.out.println("Address: " + node.getElementsByTagName("Address").item(0).getFirstChild().getNodeValue());
                System.out.println("Tel: " + node.getElementsByTagName("Tel").item(0).getFirstChild().getNodeValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 向已经存在的xml文件中插入元素
     */
    public static void insertXml() {
        Element school = null;
        Element student = null;
        Element name = null;
        Element num = null;
        Element classes = null;
        Element address = null;
        Element tel = null;
        try {
            //得到DOM解析器的工厂实例
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            //从DOM工厂中获得DOM解析器
            DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
            //把要解析的xml文档传入DOM解析器
            Document doc = dbBuilder.parse("src/test/resources/xml/school.xml");

            //得到文档名称为School的元素的节点列表
            NodeList nList = doc.getElementsByTagName("School");
            school = (Element)nList.item(0);
            //创建名称为Name的元素
            student = doc.createElement("Student");
            //设置元素student的属性examId的值为23
            student.setAttribute("examId", "23");
            //创建名称为Name的元素
            name = doc.createElement("Name");
            //创建名称为香香的文本节点并作为子节点添加到name元素中
            name.appendChild(doc.createTextNode("香香"));
            //将name子元素添加到student中
            student.appendChild(name);
            /**
             * 下面元素依次加入即可
             */
            num = doc.createElement("Num");
            num.appendChild(doc.createTextNode("1006010066"));
            student.appendChild(num);

            classes = doc.createElement("Classes");
            classes.appendChild(doc.createTextNode("眼视光5"));
            student.appendChild(classes);

            address = doc.createElement("Address");
            address.appendChild(doc.createTextNode("浙江温州"));
            student.appendChild(address);

            tel = doc.createElement("Tel");
            tel.appendChild(doc.createTextNode("123890"));
            student.appendChild(tel);

            //将student作为子元素添加到树的根节点school
            school.appendChild(student);

            //创建TransformerFactory对象
            TransformerFactory tff = TransformerFactory.newInstance();
            //创建Transformer对象
            Transformer tf = tff.newTransformer();

            //输出内容是否使用换行
            tf.setOutputProperty(OutputKeys.INDENT,  "yes");
            //创建xml文件并写入内容
            tf.transform(new DOMSource(doc), new StreamResult(new File("src/test/resources/xml/insertSchool.xml")));
            System.out.println("生成newSchool.xml成功");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("生成newSchool.xml失败");
        }
    }

    public static void main(String[] args) {
        //读取
        insertXml.queryXml();
        //插入
        insertXml.insertXml();
    }
}
