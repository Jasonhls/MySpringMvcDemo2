package com.cn.xml;

import org.apache.crimson.tree.XmlDocument;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.FileOutputStream;

/**
 * @description:
 * @author: helisen
 * @create: 2021-02-02 15:32
 **/
public class CreateNewDom {
    public static void createDom() {
        Document doc;
        Element school, student;
        Element name = null;
        Element num = null;
        Element classes = null;
        Element address = null;
        Element tel = null;
        try {
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
                ((XmlDocument)doc).write(new FileOutputStream("src/test/resources/xml/newSchool.xml"));
                System.out.println("文档创建成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        CreateNewDom.createDom();
    }
}
