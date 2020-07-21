package com.demo.Overall_Optimus;

import java.io.IOException;
import java.util.Scanner;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.DomSerializer;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.w3c.dom.Document;

public class MyScriptFrame {
  static StringBuffer sb1 = new StringBuffer();
  
  static CloseableHttpClient clients = HttpClients.createDefault();
  
  public static String myXpathEvaluationObject(String contents, String cut_xpath) throws ParserConfigurationException, Exception {
    String content_line = contents;
    TagNode tagNodes = (new HtmlCleaner()).clean(content_line);
    Document docs = (new DomSerializer(new CleanerProperties())).createDOM(tagNodes);
    XPath xpath = XPathFactory.newInstance().newXPath();
    String result = (String)xpath.evaluate(cut_xpath, docs, XPathConstants.STRING);
    return result;
  }
  
  public static int converttoValidNumber(String getvalues) {
    sb1.delete(0, sb1.length());
    int length = getvalues.length();
    for (int i = 0; i < length; i++) {
      char c = getvalues.charAt(i);
      if (Character.isDigit(c) || getvalues.charAt(i) == '.')
        sb1.append(c); 
    } 
    String validamount = sb1.toString();
    int result = Integer.parseInt(validamount);
    return result;
  }
  
  public static String trim(String response) {
    String trim_result = response.trim();
    return trim_result;
  }
  
  public static String myUrlGet(String url) throws IllegalStateException, IOException {
    sb1.delete(0, sb1.length());
    HttpGet get = new HttpGet(url);
    CloseableHttpResponse closeableHttpResponse = clients.execute((HttpUriRequest)get);
    int rescode = closeableHttpResponse.getStatusLine().getStatusCode();
    if (rescode == 200) {
      Scanner reader = new Scanner(closeableHttpResponse.getEntity().getContent());
      while (reader.hasNext())
        sb1.append(reader.nextLine()).append(System.lineSeparator()); 
      return sb1.toString();
    } 
    return "Invalid response code";
  }
}
