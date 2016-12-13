package xml;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.StringWriter;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class XmlUtli {

	public static void  createXml() {
		
		Document doc = DocumentHelper.createDocument();
		//Root
		Element root = doc.addElement("root");
		
		 // Parameter
        Element parameterEle = root.addElement("parameter");
        // Name
        Element nameEle = parameterEle.addElement("name");
        // Value
        Element valueEle = parameterEle.addElement("value");
        nameEle.setText("ksiadj");
        valueEle.setText("145");
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("UTF-8");
        
        StringWriter result = new StringWriter();
        try {
        	BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File("E:\\test.xml")));
            XMLWriter writer = new XMLWriter(bos, format); //// (result, format);
            writer.setWriter(result);
            // writer.setOutputStream(new StringWriter());
            writer.write(doc.getRootElement());
          
           
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(result.toString());
	}
	
	public static void main(String[] args) {
		
		createXml();
	}

}
