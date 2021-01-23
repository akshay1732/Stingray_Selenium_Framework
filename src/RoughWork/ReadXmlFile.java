package RoughWork;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class ReadXmlFile {
    public static void main(String[] args)
    {
        System.out.println(ReadFile("TestCases.xml","2"));
    }

            public static String ReadFile(String fileName,String id)
        {

                String testinput = null;
                String projectPath=System.getProperty("user.dir");
                System.out.println(projectPath);
                File fXmlFile = new File(projectPath+File.separator+fileName);
                System.out.println(fXmlFile);
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = null;
            try {
                dBuilder = dbFactory.newDocumentBuilder();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            }
            Document doc= null;
            try {
                doc = dBuilder.parse(fXmlFile);
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
                XPath xPath =  XPathFactory.newInstance().newXPath();
                String Name = String.format("/TestCases/Test[@id='"+id+"']/Name");
                String Module = String.format("/TestCases/Test[@id='"+id+"']/TestModule");
                String TestType = String.format("/TestCases/Test[@id='"+id+"']/TestType");
                System.out.println("Test Name :"+Name+"/nTest Module :"+Module);
            Node node = null;
            try {
                node = (Node) xPath.compile(Name).evaluate(doc, XPathConstants.NODE);
            } catch (XPathExpressionException e) {
                e.printStackTrace();
            }
            testinput = node != null ? (node.getTextContent()) : "cannot read the test case xml file";
                return testinput;

        }
    }
