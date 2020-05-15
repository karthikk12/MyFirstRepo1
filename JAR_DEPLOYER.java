package com.demo.JAR_DEPLOYER_NOTIFY;

import java.awt.Color;
import java.awt.EventQueue;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.TimeZone;

import javax.swing.JFrame;
import javax.swing.JTextField;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class JAR_DEPLOYER extends MyScriptFrame {
	final static String url = "https://artifact.intuit.com/artifactory/IBP.Intuit-Releases/com/intuit/cto/wi/";
	static StringBuilder sbs = new StringBuilder();
	static ArrayList<String> generator = new ArrayList<String>();
	static ArrayList<String> generatorurl = new ArrayList<String>();
	static ArrayList<String> cleanlist = new ArrayList<String>();
	static ArrayList<Integer> jarcounts = new ArrayList<Integer>();
	static StringBuffer sb = new StringBuffer();
	static StringBuilder slashapp = new StringBuilder();
	static CloseableHttpClient client = HttpClients.createDefault();
	static CloseableHttpClient clients = HttpClients.createDefault();
	static CloseableHttpClient clientjar = HttpClients.createDefault();
	static HashSet<String> filter = new HashSet<String>();
	static ArrayList<String> content_jar_list = new ArrayList<String>();
	static Scanner sc_in = new Scanner(System.in);
	static SimpleDateFormat timesdf = new SimpleDateFormat("HH:mm:ss");
	static Date timeconv = new Date();
	static Date pre_res_time;
	static Date now = new Date();
	static TimeZone zone = TimeZone.getTimeZone("PST");
	static SimpleDateFormat zone_conv = new SimpleDateFormat("HH:mm:ss");
	static int origin_counter = 1633;
	static int count = 0;
	private JFrame frame;
	private static JTextField namevalue;
	private static JTextField Statuscolor;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JAR_DEPLOYER window = new JAR_DEPLOYER();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private static void status_jar(String temp, String input) throws IOException, Exception {

		// date conv
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		Date mydate = new Date();
		String dateconv = "";
		dateconv = sdf.format(mydate);

		// fixed time conv(def)
		String dep_conv = timesdf.format(timeconv);
		pre_res_time = timesdf.parse(dep_conv);

		// fixed time zone conversion
		zone_conv.setTimeZone(zone);
		String zonetime = zone_conv.format(now);
		Date after_parse_pst = zone_conv.parse(zonetime);

		for (int counter = 0; counter < 100; counter++) {
			if (counter == 50) {
				Statuscolor.setText("Your Jar is not yet deployed......Please Check here :" + url);
				Statuscolor.setBackground(Color.RED);
				System.out.println("Your Jar is not yet deployed......Please Check here :" + url);
				System.exit(0);
			}
			System.out.println("Please wait......");
			content_jar_list.clear();
			HttpGet getjar = new HttpGet(temp);
			HttpResponse responsejar = clientjar.execute(getjar);
			int rescode_jar = responsejar.getStatusLine().getStatusCode();
			if (rescode_jar == 200) {
				Scanner jar_conrtent = new Scanner(responsejar.getEntity().getContent());
				while (jar_conrtent.hasNext()) {
					jar_contents(jar_conrtent.nextLine(), temp, dateconv, after_parse_pst, input);
				}
			} else {
				System.out.println("Not loaded properly" + rescode_jar);
			}
			Thread.sleep(20000);
		}
	}

	private static void call(Scanner sc, String input) throws Exception {
		int k = 1;
		while (sc.hasNext()) {
			calls(sc.nextLine(), input);
		}

	}

	private static void finder(String result, String input) throws Exception {
		String temp = result;
		slashapp.delete(0, slashapp.length());
		int count = 1;
		for (int i = 0; i < cleanlist.size(); i++) {
			if (StringUtils.equalsIgnoreCase(result, cleanlist.get(i))) {
				count++;
				System.out.println("Searched Link : " + cleanlist.get(i));
				System.out.println("");
				status_jar(temp, input);
			}

		}
		if (count == 1) {
			System.out.println("Invalid Jar Name...Please try again..Example:regions,usaa,fifththird");
			System.out.println("");
			user_input(input);
		}

	}

	/**
	 * Create the application.
	 * 
	 * @throws Exception
	 * @throws IOException
	 */
	public JAR_DEPLOYER() throws IOException, Exception {
		initialize();
	}

	private static void jar_contents(String contents, String temp, String dateconv, Date after_parse_pst, String input)
			throws Exception {
		String jar_page = myXpathEvaluationObject(contents, "/html/body");
		content_jar_list.add(jar_page);
		if (jar_page.isEmpty()) {
			final_content(content_jar_list, temp, dateconv, after_parse_pst);
		}

	}

	private static void final_content(List<String> content_jar_list, String temp, String dateconv, Date after_parse_pst)
			throws Exception {
		int b = 1;

		// post deploy time conv
		Date afterDate = new Date();
		Date post_res_time;
		Date tod = new Date();
		SimpleDateFormat timesdfs = new SimpleDateFormat("HH:mm:ss");
		String post_dep_conv = timesdfs.format(afterDate);
		post_res_time = timesdfs.parse(post_dep_conv);

		// post deploy zone time conv
		Date post_jar_zone = new Date();
		TimeZone post_zone = TimeZone.getTimeZone("PST");
		SimpleDateFormat format1 = new SimpleDateFormat("HH:mm:ss");
		format1.setTimeZone(post_zone);
		String zone_post = format1.format(tod);
		Date final_parse = format1.parse(zone_post);

		boolean test = after_parse_pst.before(final_parse);
		String date_result = String.valueOf(test);

		int m = 1;
		for (int i = 0; i < content_jar_list.size(); i++) {
			if (content_jar_list.get(i).isEmpty() || content_jar_list.get(i).startsWith("Index")
					|| content_jar_list.get(i).startsWith("..") || content_jar_list.get(i).startsWith("Name")) {
				content_jar_list.remove(content_jar_list.get(i));
				continue;
			}
//			else if (StringUtils.containsIgnoreCase(content_jar_list.get(i), dateconv)
//					&& date_result.equalsIgnoreCase("true")) {
			
				else if (StringUtils.containsIgnoreCase(content_jar_list.get(i), "12-Mar-2020")) {
				System.out.println("");
				Statuscolor.setText(content_jar_list.get(i));
				Statuscolor.setBackground(Color.GREEN);
				System.out.println("New Jar : " + content_jar_list.get(i) + "--------------> Jar Deployed ");
				count++;
				//System.exit(0);
			}

			else {
				// System.out.println(".");
				// System.out.println(m++ + ")" + content_jar_list.get(i));
			}
		}

		System.out.println("");
		jarcounts.add(m);

	}

	private static void calls(String sc, String input) throws Exception {
		String line = sc;
		String pagenav2 = myXpathEvaluationObject(sc, "//a/@href");
		generator.add(pagenav2);
		if (generator.size() == origin_counter) {
			generateUrl(input);
		}
	}

	private static void pre_evaluate(String result, String input) throws Exception {
		System.out.println("");
		String temp_url = result;
		finder(temp_url, input);

	}

	private static void begin(String input) throws Exception, IOException {

		HttpGet htget = new HttpGet(url);
		HttpResponse response = client.execute(htget);
		int rescode = response.getStatusLine().getStatusCode();
		if (rescode == 200) {
			System.out.println("------------------------------------> Connected : " + 200);
			Scanner sc = new Scanner(response.getEntity().getContent());
			call(sc, input);
		} else {
			System.out.println("Not loaded properly" + rescode);

		}
	}

	private static void user_input(String input) throws Exception {
		String name = input;
		slashapp.append(url);
		slashapp.append(name).append("/");
		String result = slashapp.toString();
		pre_evaluate(result, input);
	}

	public static void generateUrl(String input) throws Exception {
		int k = 1;
		for (int i = 0; i < generator.size(); i++) {
			String rem_url = generator.get(i);
			sb.append(url);
			sb.append(rem_url);
			generatorurl.add(sb.toString());
			sb.delete(0, sb.length());
		}
		allignment(generatorurl, input);

	}

	private static void allignment(List<String> generatorurl, String input) throws Exception {
		int j = 1;
		for (int i = 0; i < generatorurl.size(); i++) {
			filter.add(generatorurl.get(i));
		}
		cleanlist.addAll(filter);
		user_input(input);
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @throws Exception
	 * @throws IOException
	 */
	private void initialize() throws IOException, Exception {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

	     namevalue = new JTextField();
		namevalue.setBounds(204, 28, 86, 20);
		frame.getContentPane().add(namevalue);
		namevalue.setColumns(10);

		JLabel Name = new JLabel("New label");
		Name.setBounds(148, 31, 46, 14);
		frame.getContentPane().add(Name);

		Statuscolor = new JTextField();
		Statuscolor.setBounds(149, 123, 191, 20);
		frame.getContentPane().add(Statuscolor);
		Statuscolor.setColumns(10);

		JButton btnSubmit = new JButton("submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (StringUtils.isNotEmpty(namevalue.getText())) {
					try {
						begin(namevalue.getText());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			}
			}
		});
		btnSubmit.setBounds(306, 27, 89, 23);
		frame.getContentPane().add(btnSubmit);
	
			
	}
}
