package org.max;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;

import org.apache.jasper.tagplugins.jstl.core.Url;
import org.apache.jasper.tagplugins.jstl.core.When;
import org.apache.tomcat.websocket.server.UriTemplate;

public class Test {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		URL url = new URL("http://wenku.baidu.com/link?url=ijkPI_C7CKuGD9Bx5HzMTxc3I5yKJMCblNqxU44-qyQZpiyxYZNAEyYHOB8Y2XIt8KV5t8S7ExdIxGSuIyokFdtPv5GBbPivp7M5r8e7aEW");
		HttpURLConnection connection =  (HttpURLConnection)url.openConnection();
		connection.setDoOutput(true);
		connection.setRequestMethod("POST");
        connection.addRequestProperty("Content-Type", "application/xml; charset=UTF-8");
        connection.addRequestProperty("Accept", "application/xml; charset=UTF-8");
        InputStream out = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"GBK"));
        StringBuffer sbBuffer = new StringBuffer();
        String line;
        while ((line = reader.readLine()) != null) {
			sbBuffer.append(line);
		}
        reader.close();
        connection.disconnect();
        System.out.println(sbBuffer.toString());
	}
	
	public  String readHtml(String context){
	
		return "";
	}
}
