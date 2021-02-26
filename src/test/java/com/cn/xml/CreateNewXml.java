package com.cn.xml;

import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.helpers.AttributesImpl;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: 解析或生成xml文件的四种方式：Dom方式，Dom4j方式（需要引入dom4j包），jDom方式（需要引入jdom包），Sax方式
 * @author: helisen
 * @create: 2021-02-02 15:32
 **/
public class CreateNewXml {

    /**
     * 方式一：Dom写入
     */
    @Test
    public void testCreateXmlByDom() {
        Long start = System.currentTimeMillis();
        createXmlByDom();
        System.out.println("运行时间：" + (System.currentTimeMillis() - start));
    }

    /**
     * 方式二：Dom4j写入
     */
    @Test
    public void testCreateXmlByDom4j() {
        createXmlByDom4j();
    }

    /**
     * jDom写入
     */
    @Test
    public void testCreateXmlByJDom() {
        createXmlByJDom();
    }

    /**
     * 方法四：Sax写入
     */
    @Test
    public void testCreateXmlBySax() {
        List<Student> list = new ArrayList<>();
        Student s = new Student();
        s.setExamId("23");
        s.setName("香香");
        s.setNum("1006010066");
        s.setClasses("眼视光5");
        s.setAddress("浙江温州");
        s.setTel("123890");
        list.add(s);
        createXmlBySax(list);
    }

    /**
     * 方式一：Dom写入
     */
    public static void createXmlByDom() {
        Document doc;
        Element school, student;
        Element name = null;
        Element num = null;
        Element classes = null;
        Element address = null;
        Element tel = null;
        try {
            //创建解析工厂
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
            doc = dbBuilder.newDocument();
            if(doc != null) {
                school = doc.createElement("School");
                student = doc.createElement("Student");
                student.setAttribute("examId", "23");
                name = doc.createElement("Name");
                name.appendChild(doc.createTextNode("香香"));
                student.appendChild(name);
                /**
                 * 下面的元素依次加入即可
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

                school.appendChild(student);
                doc.appendChild(school);

                //创建TransformerFactory对象
                TransformerFactory tff = TransformerFactory.newInstance();
                //创建Transformer对象
                Transformer tf = tff.newTransformer();

                //输出内容是否使用换行
                tf.setOutputProperty(OutputKeys.INDENT,  "yes");
                //创建xml文件并写入内容
                tf.transform(new DOMSource(doc), new StreamResult(new File("src/test/resources/xml/newSchool.xml")));
                System.out.println("生成newSchool.xml成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("生成newSchool.xml失败");
        }
    }

    /**
     * 方式二：Dom4j写入
     */
    public static void createXmlByDom4j() {
        try {
            //1.创建document对象
            org.dom4j.Document doc = DocumentHelper.createDocument();
            //2.创建根结点
            org.dom4j.Element school = doc.addElement("School");
            //创建子结点
            org.dom4j.Element student = school.addElement("student");
            //添加属性
            student.addAttribute("examId", "23");

            org.dom4j.Element name = school.addElement("name");
            name.setText("香香");
            org.dom4j.Element num = school.addElement("num");
            num.setText("1006010066");
            org.dom4j.Element classes = school.addElement("classes");
            classes.setText("眼视光5");
            org.dom4j.Element address = school.addElement("address");
            address.setText("浙江温州");
            org.dom4j.Element tel = school.addElement("tel");
            tel.setText("123890");

            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding("utf8");

            //生成xml文件
            File f = new File("src/test/resources/xml/newSchoolThree.xml");
            XMLWriter writer = new XMLWriter(new FileOutputStream(f));
            //设置是否转义，默认使用转义字符
            writer.setEscapeText(false);
            writer.write(doc);
            writer.close();
            System.out.println("生成newSchoolThree.xml成功");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("生成newSchoolThree.xml失败");
        }
    }


    /**
     * jDom写入
     */
    public static void createXmlByJDom() {
        try {
            // 1、生成一个根节点
            org.jdom2.Element school = new org.jdom2.Element("School");
            // 2、生成一个document对象
            org.jdom2.Document doc = new org.jdom2.Document(school);


            org.jdom2.Element student = new org.jdom2.Element("student");
            student.setAttribute("examId", "23");
            school.addContent(student);

            org.jdom2.Element name = new org.jdom2.Element("name");
            name.setText("香香");
            student.addContent(name);

            org.jdom2.Element num = new org.jdom2.Element("num");
            num.setText("1006010066");
            student.addContent(num);

            org.jdom2.Element classes = new org.jdom2.Element("classes");
            classes.setText("眼视光5");
            student.addContent(classes);

            org.jdom2.Element address = new org.jdom2.Element("address");
            address.setText("浙江温州");
            student.addContent(address);

            org.jdom2.Element tel = new org.jdom2.Element("tel");
            tel.setText("123890");
            student.addContent(tel);

            Format format = Format.getCompactFormat();
            // 设置换行Tab或空格
            format.setIndent("	");
            format.setEncoding("UTF-8");

            // 4、创建XMLOutputter的对象
            XMLOutputter outputer = new XMLOutputter(format);
            // 5、利用outputer将document转换成xml文档
            File file = new File("src/test/resources/xml/newSchoolFour.xml");
            outputer.output(doc, new FileOutputStream(file));

            System.out.println("生成newSchoolFour.xml成功");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("生成newSchoolFour.xml失败");
        }
    }


    /**
     * 方法四：Sax写入
     * @param list
     */
    public static void createXmlBySax(List<Student> list) {
        //1.创建一个SAXTransformerFactory类的对象
        SAXTransformerFactory tff = (SAXTransformerFactory)SAXTransformerFactory.newInstance();
        try {
            //2.通过SAXTransformerFactory创建一个TransformerHandler对象
            TransformerHandler handler = tff.newTransformerHandler();
            //3.通过handler创建一个Transformer对象
            Transformer tr = handler.getTransformer();
            //4.通过Transformer对象对生成的xml文件进行设置
            //设置编码
            tr.setOutputProperty(OutputKeys.ENCODING, "utf-8");
            //设置是否换行
            tr.setOutputProperty(OutputKeys.INDENT, "yes");
            //5.创建一个Result对象
            File f = new File("src/test/resources/xml/newSchoolTwo.xml");
            if(!f.exists()) {
                f.createNewFile();
            }
            StreamResult streamResult = new StreamResult(new FileOutputStream(f));
            //6.使Result与handler关联
            handler.setResult(streamResult);

            //打开document
            handler.startDocument();
            AttributesImpl attr = new AttributesImpl();
            handler.startElement("", "", "School", attr);
            attr.clear();
            for (Student s : list) {
                attr.clear();
                attr.addAttribute("", "", "examId", "", s.getExamId());
                handler.startElement("", "", "student", attr);

                //创建name
                attr.clear();
                handler.startElement("", "", "name", attr);
                handler.characters(s.getName().toCharArray(), 0, s.getName().length());
                handler.endElement("", "", "name");

                //创建num
                attr.clear();
                handler.startElement("", "", "num", attr);
                handler.characters(s.getNum().toCharArray(), 0, s.getNum().length());
                handler.endElement("", "", "num");

                //创建classes
                attr.clear();
                handler.startElement("", "", "classes", attr);
                handler.characters(s.getClasses().toCharArray(), 0, s.getClasses().length());
                handler.endElement("", "", "classes");

                //创建address
                attr.clear();
                handler.startElement("", "", "address", attr);
                handler.characters(s.getAddress().toCharArray(), 0, s.getAddress().length());
                handler.endElement("", "", "address");

                //创建tel
                attr.clear();
                handler.startElement("", "", "tel", attr);
                handler.characters(s.getTel().toCharArray(), 0, s.getTel().length());
                handler.endElement("", "", "tel");

                handler.endElement("", "", "student");

            }

            handler.endElement("", "", "School");
            //关闭document
            handler.endDocument();
            System.out.println("生成newSchoolTwo.xml成功");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("生成newSchoolTwo.xml失败");
        }

    }
}
