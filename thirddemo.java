package com.training;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.DomSerializer;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.w3c.dom.Document;

public class thirddemo extends Script {
	static String inline;

	static StringBuilder sb = new StringBuilder();
	static StringBuilder sb1 = new StringBuilder();
	static StringBuilder sbpage = new StringBuilder();
	static StringBuilder sbcounter = new StringBuilder();
	static StringBuilder sbnextpage = new StringBuilder();
	static List<String> countries = new ArrayList<String>();
	static Scanner sc = new Scanner(System.in);
	static Scanner s = new Scanner(System.in);
	static CloseableHttpClient client = HttpClients.createDefault();
	static Map<Integer, String> mapped_countries = new HashMap<Integer, String>();
	static int counter = 2;
	static char alpha;
	static String nxtpage = "";
	static String savepage = "";

	public static void main(String[] args) throws Exception, IOException {

		HttpGet htget = new HttpGet(url);
		HttpResponse response = client.execute(htget);
		int rescode = response.getStatusLine().getStatusCode();
		if (rescode == 200) {
			Scanner sc = new Scanner(response.getEntity().getContent());
			calls(sc);
		} else {
			System.out.println(rescode + "Not loaded properly");
		}
	}

	public static void calls(Scanner sc) throws Exception {
		int j = 1;
		TagNode tagNode = new HtmlCleaner().clean(name);

		org.w3c.dom.Document doc = new DomSerializer(new CleanerProperties()).createDOM(tagNode);

		XPath xpath = (XPath) XPathFactory.newInstance().newXPath();
		for (int i = 1; i <= 100; i++) {
			sb.append(".//tbody/tr[");
			sb.append(i);
			sb.append("]");
			String xpathappender = sb.toString();
			String page1 = (String) xpath.evaluate(xpathappender, doc, XPathConstants.STRING);
			char[] name = page1.toCharArray();
			for (int k = 0; k < name.length; k++) {
				if (Character.isAlphabetic(name[k])) {
					sb1.append(name[k]);
				}
			}

			String countryname = sb1.toString();
			countries.add(countryname);
			sb.delete(0, sb.length());
			sb1.delete(0, sb1.length());
			page1 = null;
			name = null;
		}
		System.out.println(
				"-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

		for (String string : countries) {
			System.out.println(j++ + " ) " + string);
		}
		System.out.println("");
		System.out.println("There are " + j + " countries detected in page 1 Sir......");
		pagecall();
	}

	public static void pagecall() throws Exception {
		System.out.println("");
		System.out.println("Shall i go to page 2 details..........Press Yes or No");
		System.out.println("");
		String res = sc.nextLine();
		if (res.contains("y")) {

			pagenav();
		} else {
			pagecall();
		}

	}

	public static void pagenav() throws Exception, IOException {
		TagNode tagNodes = new HtmlCleaner().clean(page2);

		org.w3c.dom.Document docs = new DomSerializer(new CleanerProperties()).createDOM(tagNodes);

		XPath xpaths = (XPath) XPathFactory.newInstance().newXPath();

		String pagenav2 = (String) xpaths.evaluate("//a[contains(@href,'start-with-b/')]/@href", docs,
				XPathConstants.STRING);
		sb.append("https://www.worldometers.info");
		sb.append(pagenav2);
		String page2append = sb.toString();
		System.out.println("Current Url of  A : " + page2append);
		sb.delete(0, sb.length());
		System.out.println("");
		System.out.println(
				"-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

		HttpGet httget = new HttpGet(page2append);
		HttpResponse responses = client.execute(httget);
		int rescodes = responses.getStatusLine().getStatusCode();

		if (rescodes == 200) {
			sc = new Scanner(responses.getEntity().getContent());
			pagescall(sc);

		} else {
			pagenav();
		}
	}

	public static void pagescall(Scanner sc) throws Exception {
		while (sc.hasNext()) {
			nxtpage = sc.nextLine();
			if (nxtpage.contains("<table")) {
				savepage = nxtpage;
			}

		}

		String tablecontent = StringUtils.substringBetween(savepage,
				"<table class='table table-hover table-condensed'>", "</table>");
		sbpage.append(" <table class='table table-hover table-condensed'>");
		sbpage.append(tablecontent);
		sbpage.append("</table>");
		String sbpagenav = sbpage.toString();
		// System.out.println(sbpagenav);
		pagingcall(sbpagenav);

	}

	public static void pagingcall(String sbpagenav) throws Exception {
		int j = 1;
		TagNode tagNodep = new HtmlCleaner().clean(sbpagenav);

		org.w3c.dom.Document docp = new DomSerializer(new CleanerProperties()).createDOM(tagNodep);

		XPath xpathp = (XPath) XPathFactory.newInstance().newXPath();
		for (int i = 1; i <= 17; i++) {
			sb.append(".//tbody/tr[");
			sb.append(i);
			sb.append("]");
			String xpathappenderp = sb.toString();
			String page1 = (String) xpathp.evaluate(xpathappenderp, docp, XPathConstants.STRING);
			char[] name = page1.toCharArray();
			for (int k = 0; k < name.length; k++) {
				if (Character.isAlphabetic(name[k])) {
					sb1.append(name[k]);
				}
			}

			String countrynamep = sb1.toString();
			countries.add(countrynamep);
			sb.delete(0, sb.length());
			sb1.delete(0, sb1.length());
			sbpage.delete(0, sbpage.length());

		}
		System.out.println(
				"-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

		for (String string : countries) {
			System.out.println(j++ + " ) " + string);
		}
		System.out.println("");
		System.out.println("There are " + j + " countries detected in this page Sir ......");

		name = null;
		tagNodep = null;
		docp = null;
		xpathp = null;
//		char lower = (char) ('z' + 1);
//		System.out.println(lower);
		newlayout();
	}

	public static void SearchBox(String answer) {
		for (int i = 0; i < countries.size(); i++) {
			if (answer.equalsIgnoreCase(countries.get(i))) {
				System.out.println("");
				System.out.println("Your searched country " + countries.get(i) + " is in " + i + "st position sir");
				System.out.println("");
			}
		}
		general();
	}

	public static void newlayout() throws Exception {
		for (int h = 1; h < 25; h++) {
			System.out.println("");
			char lower = (char) ('b' + h);
			String lowerchange = String.valueOf(lower);
			String appages = sbnextpage.toString();
			System.out.println(">>Entering the url of " + lowerchange.toUpperCase());
			sbnextpage.append("<a href=\"/geography/alphabetical-list-of-countries/countries-that-start-with-");
			sbnextpage.append(lower);
			sbnextpage.append("/>C</a>");
			lowerchange = String.valueOf(lower);
			appages = sbnextpage.toString();
			TagNode tagNodesc = new HtmlCleaner().clean(appages);

			org.w3c.dom.Document docsc = new DomSerializer(new CleanerProperties()).createDOM(tagNodesc);

			XPath xpathcc = (XPath) XPathFactory.newInstance().newXPath();
			sbcounter.append("//a[contains(@href,'start-with-");
			sbcounter.append(lower);
			sbcounter.append("/')]/@href");
			String counterappend = sbcounter.toString();

			String pagecc = (String) xpathcc.evaluate(counterappend, docsc, XPathConstants.STRING);
			sb.append("https://www.worldometers.info");
			sb.append(pagecc);
			String page3append = sb.toString();
			System.out.println("");
			System.out.println("");
			System.out.println("Current Url of  " + lowerchange.toUpperCase() + " : " + page3append);
			System.out.println("");
			System.out.println(
					"-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
			CloseableHttpClient clients = HttpClients.createDefault();

			HttpGet getc = new HttpGet(page3append);
			HttpResponse response3 = clients.execute(getc);
			int responsecode3 = response3.getStatusLine().getStatusCode();
			if (responsecode3 == 200) {
				sc = new Scanner(response3.getEntity().getContent());
				while (sc.hasNext()) {
					String line = sc.nextLine();
					if (line.contains("<table")) {
						savepage = line;

					}
				}
				String tablecontent = StringUtils.substringBetween(savepage,
						"<table class='table table-hover table-condensed'>", "</table>");
				sbpage.append(" <table class='table table-hover table-condensed'>");
				sbpage.append(tablecontent);
				sbpage.append("</table>");
				String sbpagenav = sbpage.toString();

				TagNode tagNodep = new HtmlCleaner().clean(sbpagenav);

				Document docp = new DomSerializer(new CleanerProperties()).createDOM(tagNodep);
				sb.delete(0, sb.length());
				sb1.delete(0, sb1.length());
				sbpage.delete(0, sbpage.length());
				XPath xpathp = (XPath) XPathFactory.newInstance().newXPath();
				for (int i = 1; i <= 17; i++) {
					sb.append(".//tbody/tr[");
					sb.append(i);
					sb.append("]");
					String xpathappenderp = sb.toString();
					String pagec = (String) xpathp.evaluate(xpathappenderp, docp, XPathConstants.STRING);
					char[] name = pagec.toCharArray();
					for (int k = 0; k < name.length; k++) {
						if (Character.isAlphabetic(name[k])) {
							sb1.append(name[k]);
						}
					}

					String countrynamel = sb1.toString();
					countries.add(countrynamel);
					sb.delete(0, sb.length());
					sb1.delete(0, sb1.length());
					sbpage.delete(0, sbpage.length());
				}
				System.out.println("");
				System.out.println(
						"-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
				System.out.println("");
				int j = 1;
				for (String string : countries) {
					System.out.println(j++ + " ) " + string);
				}
				System.out.println("");
				System.out.println("There are " + j + " countries detected in this page Sir ......");
				System.out.println("");
				System.out.println(
						".................................................................................Automation SUCCESS..............................................................................................................");
				System.out.println("");
				name = null;
				tagNodep = null;
				docp = null;
				xpathp = null;
				sb.delete(0, sb.length());
				sb1.delete(0, sb1.length());
				sbpage.delete(0, sbpage.length());
				sbcounter.delete(0, sbcounter.length());

			}
		}
		System.out.println("");
		System.out.println("Do you want to sort it out..............Press yes or no");
		String answer = s.nextLine();
		if (StringUtils.containsIgnoreCase(answer, "y")) {
			CountrySort(answer);
		} else {
			System.out.println("");
			System.out.println("Thank you");
		}
	}

	public static void CountrySort(String answer) {
		int z = 1;
		for (int i = 0; i < countries.size(); i++) {
			if (!countries.get(i).isEmpty()) {
				System.out.println(z++ + " ) " + countries.get(i));
			}
		}
		general();
	}

	public static void general() {
		System.out.println("");
		System.out.println("Do you want to take a copy of  a file Sir..............press 1");
		System.out.println("");
		System.out.println("Do you want to search for a specific country Number.................Press 2");
		System.out.println("");
		System.out.println("Do u want to exit .......Press 3");
		String ans = s.nextLine();
		if (StringUtils.containsIgnoreCase(ans, "1")) {
			Print();
		} else if (StringUtils.contains(ans, "2")) {
			System.out.println("");
			System.out.println("Please type the name of the country you want to search ?");
			System.out.println("");
			System.out.println("Waiting for user input : ");
			String resp = s.nextLine();
			SearchBox(resp);
		} else if (StringUtils.contains(ans, "3")) {

			System.out.println("");
			System.out.println("Thank U");
		}
	}

	public static void Print() {
		for (int i = 0; i < countries.size(); i++) {
			mapped_countries.put(i, countries.get(i));
		}
		try {
			PrintWriter writer = new PrintWriter("C:\\Users\\Karthikeyan\\Desktop\\countrynames\\countryname.txt",
					"UTF-8");
			Iterator<Integer> countryit = mapped_countries.keySet().iterator();
			while (countryit.hasNext()) {
				int id = countryit.next();
				String name = mapped_countries.get(id);
				if (!name.isEmpty()) {
					System.out.println(id + " " + name);

					writer.print(id + 1 + ") " + name + System.getProperty("line.separator"));
				}

			}
			writer.close();
			System.out.println("");
			System.out.println("#########################        !!  File Copied Completed !!    ##############");
		} catch (Exception e) {
			System.out.println("Exception Occurred" + e);
		}
		general();
	}
}
