package com.demo.Overall_Optimus;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import net.sf.cglib.beans.FixedKeySet;

import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Window.Type;

public class Overall_optimus_c5 extends MyScriptFrame {
	static CloseableHttpClient clients = HttpClients.createDefault();

	static StringBuffer buffer = new StringBuffer();

	static StringBuffer totaltickets = new StringBuffer();

	static List<String> sepline = new ArrayList<String>();

	static List<String> hrefvalues = new ArrayList<String>();

	static List<String> ahreftag = new ArrayList<String>();

	static List<String> demotcks = new ArrayList<String>();

	static List<String> FI_ID = new ArrayList<String>();

	static List<String> tickets = new ArrayList<String>();

	static List<String> script_name = new ArrayList<String>();

	static List<String> teammembers = new ArrayList<String>();

	static List<String> KnownIssue = new ArrayList<String>();

	static StringBuffer tempbuffer = new StringBuffer();

	static List<String> errorcode = new ArrayList<String>();

	static List<Integer> Grade = new ArrayList<Integer>();

	static List<String> headings = new ArrayList<String>();

	static List<String> React_PRo_status = new ArrayList<String>();

	static List<String> Mint_QBO_Status = new ArrayList<String>();

	static List<String> Countcalc = new ArrayList<String>();

	static List<String> Created_status = new ArrayList<String>();

	static List<Integer> montana_grade = new ArrayList<Integer>();

	static List<String> montana_errcode = new ArrayList<String>();

	static List<String> montana_team_memb = new ArrayList<String>();

	static List<String> mv_count = new ArrayList<String>();

	static List<String> TT_type = new ArrayList<String>();

	static List<String> typo_def = new ArrayList<String>();

	static List<Integer> Global_grade = new ArrayList<Integer>();

	static List<String> Global_errcode = new ArrayList<String>();

	static List<String> Global_team_memb = new ArrayList<String>();

	static List<String> assignee_name = new ArrayList<String>();

	static List<String> add_known_issue = new ArrayList<String>();

	static List<String> extra_copy = new ArrayList<String>();

	static final String MAIN_URL = "http://172.22.106.43:8087/tasks";

	static final String INNER_URL = "http://172.22.106.43:8087/tasks/internal";

	static Map<Integer, String> mymap = new HashMap<Integer, String>();

	static List<String> scriptstore = new ArrayList<String>();

	static String[] myarray = new String[500];

	static String letter_definer = "";

	static int retrycount = 1;

	static int other_cnt = 0;

	static boolean mark;

	static int rem_value;

	static String successmessage = "FILE CREATED SUCCESSFULLY";

	public static JFrame frame;

	public static JTextField Ticketfield;

	public static JTextField Otherfield;

	public static JTextField Workfield;

	public static JTextField statusfield;

	public static JTextField pathfield;

	public static JTextField messagefield;

	static Montana_Write_process montan = new Montana_Write_process();

	static Global_Write_process global = new Global_Write_process();

	static Error_Thrower.Error_displayer err_display = new Error_Thrower.Error_displayer();
	private static JTextField Task_textField;

	protected static void initialise(String manager, String developer) throws Exception {
		try {
			HttpGet get = new HttpGet("http://172.22.106.43:8087/tasks");
			CloseableHttpResponse closeableHttpResponse = clients.execute((HttpUriRequest) get);
			int rescode = closeableHttpResponse.getStatusLine().getStatusCode();
			if (rescode == 200) {
				Scanner sc = new Scanner(closeableHttpResponse.getEntity().getContent());
				while (sc.hasNext()) {
					buffer.append(sc.nextLine());
					buffer.append(System.lineSeparator());
				}
			} else {

				statusfield.setText("Something went wrong");
				statusfield.setBackground(Color.RED);
				if (retrycount == 2) {
					System.out.println("Please check the Main URL....");
					System.exit(0);
				}
				System.out.println("Retrying count : " + retrycount++);
				initialise(manager, developer);
			}

			buffer.delete(0, buffer.length());
			postdatas(rescode, manager, developer);
		} catch (SocketException e) {
			String err_names = "Not_connected";
			err_display.errorcatcher(err_names);
		}

	}

	private static void filter_tcks(String local_copy, Map mymap, List<String> KnownIssue, String manager,
			String developer) throws ParserConfigurationException, Exception {
		String refvalue = "";
		String cutter = StringUtils.substringBetween(local_copy, "select-checkbox", "</body>");
		buffer.append("<html>").append(System.lineSeparator()).append("<body>").append(System.lineSeparator())
				.append(cutter).append(System.lineSeparator()).append("</body>").append(System.lineSeparator())
				.append("</html>");
		String tck_copy = buffer.toString();
		buffer.delete(0, buffer.length());
		Scanner scs4 = new Scanner(tck_copy);
		while (scs4.hasNext()) {
			String lines = scs4.nextLine();
			if (StringUtils.containsIgnoreCase(lines, "<a href")) {
				buffer.append(lines).append(System.lineSeparator());
				String passer = buffer.toString();
				hrefvalues.add(passer);
				buffer.delete(0, buffer.length());
			}
		}
		buffer.delete(0, buffer.length());
		tckt_seperator(hrefvalues, KnownIssue, mymap, local_copy, manager, developer);
	}

	private static void separate_it(String getlines, String local_copy, String manager, String developer)
			throws ParserConfigurationException, Exception {
		int count = 0;
		int counter = 0;
		int i = 0;
		Scanner scs1 = new Scanner(getlines);
		while (scs1.hasNext()) {
			String lines = scs1.nextLine();
			if (!lines.isEmpty())
				count++;
		}
		buffer.delete(0, buffer.length());
		Scanner scs2 = new Scanner(getlines);
		while (scs2.hasNext()) {
			boolean check = scs2.hasNext();
			String element = scs2.nextLine();
			if (check) {
				buffer.append(element).append(System.lineSeparator());
				if (StringUtils.equalsIgnoreCase(element, "open")
						|| StringUtils.containsIgnoreCase(element, "in Progress")
						|| StringUtils.containsIgnoreCase(element, "Closed")
						|| StringUtils.containsIgnoreCase(element, "Done")) {
					myarray[counter++] = buffer.toString();
					buffer.delete(0, buffer.length());
				}
			}
		}
		for (int j = 0; j < myarray.length - 1; j++) {
			String linechck = myarray[j];
			if (!StringUtils.isEmpty(linechck))
				sepline.add(linechck);
		}
		buffer.delete(0, buffer.length());
		for (int ctr = 0; ctr < sepline.size(); ctr++) {
			tempbuffer.delete(0, tempbuffer.length());
			int counts = 0;
			Scanner scs3 = new Scanner(sepline.get(ctr));
			while (scs3.hasNext()) {
				String eachline = scs3.nextLine();
				counts++;
				if ((!manager.equalsIgnoreCase("Nir") || (!developer.equalsIgnoreCase("Global_support")
						&& (!manager.equalsIgnoreCase("Nir") || !developer.equalsIgnoreCase("All")))) && counts == 16
						&& !StringUtils.equalsIgnoreCase(eachline, "USA")
						&& !StringUtils.equalsIgnoreCase(eachline, "Israel"))
					while (!StringUtils.equalsIgnoreCase(eachline, "Usa")
							&& !StringUtils.equalsIgnoreCase(eachline, "Israel")) {
						tempbuffer.append(eachline).append(",");
						eachline = scs3.nextLine();
					}

				if (counts == 2 || counts == 5 || counts == 10 || counts == 12) {
					tempbuffer.append(eachline).append("-");
					buffer.append(eachline).append("-");
				}
			}
			KnownIssue.add(tempbuffer.toString());
			mymap.put(Integer.valueOf(ctr), buffer.toString());
			buffer.delete(0, buffer.length());
		}
		Iterator<Integer> mapit = mymap.keySet().iterator();
		while (mapit.hasNext()) {
			int index = ((Integer) mapit.next()).intValue();
			String values = mymap.get(Integer.valueOf(index));
			scriptstore.add(values);
		}
		buffer.delete(0, buffer.length());
		filter_tcks(local_copy, mymap, KnownIssue, manager, developer);
	}

	private static void tckt_seperator(List<String> hrefvalues, List<String> KnownIssue, Map mymap, String local_copy,
			String manager, String developer) throws ParserConfigurationException, Exception {
		String values = "";
		for (int index = 0; index < hrefvalues.size(); index++) {
			String getvalues = hrefvalues.get(index);
			ahreftag.add(getvalues);
			values = myXpathEvaluationObject(getvalues, "//*[@href]");
			tickets.add(values);
		}
		category(tickets, KnownIssue, hrefvalues, mymap, local_copy, ahreftag, manager, developer);
	}

	private static void list_it(String response, int count, String local_copy, String manager, String developer)
			throws ParserConfigurationException, Exception {
		buffer.delete(0, buffer.length());
		buffer.append("<html>").append(System.lineSeparator()).append("<body>").append(System.lineSeparator())
				.append(response).append(System.lineSeparator()).append("</body>").append(System.lineSeparator())
				.append("</html>");
		String res = buffer.toString();
		buffer.delete(0, buffer.length());
		String result = myXpathEvaluationObject(res, "/html/body");
		Scanner scs = new Scanner(result);
		while (scs.hasNext()) {
			String lines = scs.nextLine();
			if (!lines.isEmpty()) {
				buffer.append(lines);
				buffer.append(System.lineSeparator());
			}
		}
		separate_it(buffer.toString(), local_copy, manager, developer);
	}

	private static void looper_err_scr(String local_copy, List tickets, List hrefvalues, Map mymap, List ahreftag,
			List created_status, List assigne_name, List<String> FI_ID, List<String> typo_def, String manager,
			String developer) throws Exception {
		String teamnames[], scriptname = "";
		String error_code = "";
		String react_counts = "";
		String pro_counts = "";
		int gradeno = 0;
		int tercounter = 0;
		String issues = null;
		if ((StringUtils.containsIgnoreCase(manager, "Tammy")
				&& StringUtils.containsIgnoreCase(developer, "Cognizant-Montana"))
				|| (StringUtils.containsIgnoreCase(manager, "Tammy")
						&& StringUtils.containsIgnoreCase(developer, "All"))) {
			teamnames = new String[5];
			teamnames[0] = "Asha";
			teamnames[1] = "Ganga";
			teamnames[2] = "Priyadarshini";
			teamnames[3] = "Vignesh";
			teamnames[4] = "Suganya";
		} else if ((StringUtils.containsIgnoreCase(manager, "Nir")
				&& StringUtils.containsIgnoreCase(developer, "Global_support"))
				|| (StringUtils.containsIgnoreCase(manager, "Nir")
						&& StringUtils.containsIgnoreCase(developer, "All"))) {
			teamnames = new String[5];
			teamnames[0] = "Gopi";
			teamnames[1] = "Soundar";
			teamnames[2] = "Yoga";
			teamnames[3] = "Praveen";
			teamnames[4] = "Subash";
		} else {
			teamnames = new String[4];
			teamnames[0] = "Mohan";
			teamnames[1] = "Balaji";
			teamnames[2] = "Karthik";
			teamnames[3] = "Vignesh";
		}
		if (teamnames.length == 5) {
			for (int itr = 0; itr < scriptstore.size(); itr++) {
				tercounter++;
				if (tercounter == 5)
					tercounter = 0;
				gradeno++;
				String names = scriptstore.get(itr);
				StringTokenizer tokenize = new StringTokenizer(names, "-");
				while (tokenize.hasMoreTokens()) {
					scriptname = tokenize.nextToken();
					error_code = tokenize.nextToken();
					pro_counts = tokenize.nextToken();
					react_counts = tokenize.nextToken();
				}
				int pro_number_count = converttoValidNumber(pro_counts);
				int react_number_count = converttoValidNumber(react_counts);
				if (react_number_count == 0) {
					for (int ctr = 0; ctr < pro_number_count; ctr++) {
						script_name.add(scriptname);
						errorcode.add(error_code);
						Grade.add(Integer.valueOf(gradeno));
						teammembers.add(teamnames[tercounter]);
					}
				} else if (pro_number_count == 0) {
					for (int ctr = 0; ctr < react_number_count; ctr++) {
						script_name.add(scriptname);
						errorcode.add(error_code);
						Grade.add(Integer.valueOf(gradeno));
						teammembers.add(teamnames[tercounter]);
					}
				} else {
					for (int proctr = 0; proctr < pro_number_count; proctr++) {
						script_name.add(scriptname);
						errorcode.add(error_code);
						Grade.add(Integer.valueOf(gradeno));
						teammembers.add(teamnames[tercounter]);
					}
					for (int reactr = 0; reactr < react_number_count; reactr++) {
						script_name.add(scriptname);
						errorcode.add(error_code);
						Grade.add(Integer.valueOf(gradeno));
						teammembers.add(teamnames[tercounter]);
					}
				}
			}
		} else if (teamnames.length == 4) {
			for (int itr = 0; itr < scriptstore.size(); itr++) {
				tercounter++;
				if (tercounter == 4)
					tercounter = 0;
				gradeno++;
				String names = scriptstore.get(itr);
				StringTokenizer tokenize = new StringTokenizer(names, "-");
				while (tokenize.hasMoreTokens()) {
					scriptname = tokenize.nextToken();
					error_code = tokenize.nextToken();
					pro_counts = tokenize.nextToken();
					react_counts = tokenize.nextToken();
				}
				int pro_number_count = converttoValidNumber(pro_counts);
				int react_number_count = converttoValidNumber(react_counts);
				if (react_number_count == 0) {
					for (int ctr = 0; ctr < pro_number_count; ctr++) {
						script_name.add(scriptname);
						errorcode.add(error_code);
						Grade.add(Integer.valueOf(gradeno));
						teammembers.add(teamnames[tercounter]);
					}
				} else if (pro_number_count == 0) {
					for (int ctr = 0; ctr < react_number_count; ctr++) {
						script_name.add(scriptname);
						errorcode.add(error_code);
						Grade.add(Integer.valueOf(gradeno));
						teammembers.add(teamnames[tercounter]);
					}
				} else {
					for (int proctr = 0; proctr < pro_number_count; proctr++) {
						script_name.add(scriptname);
						errorcode.add(error_code);
						Grade.add(Integer.valueOf(gradeno));
						teammembers.add(teamnames[tercounter]);
					}
					for (int reactr = 0; reactr < react_number_count; reactr++) {
						script_name.add(scriptname);
						errorcode.add(error_code);
						Grade.add(Integer.valueOf(gradeno));
						teammembers.add(teamnames[tercounter]);
					}
				}
			}
		}
		for (int i = 0; i < KnownIssue.size(); i++) {
			String known = KnownIssue.get(i);
			StringTokenizer tokens = new StringTokenizer(known, "-");
			int tok_length = tokens.countTokens();
			if (tok_length == 5) {
				while (tokens.hasMoreTokens()) {
					String scr_name = tokens.nextToken();
					String errcode = tokens.nextToken();
					pro_counts = tokens.nextToken();
					react_counts = tokens.nextToken();
					issues = tokens.nextToken();
				}
			} else {
				while (tokens.hasMoreTokens()) {
					String scr_name = tokens.nextToken();
					String errcode = tokens.nextToken();
					pro_counts = tokens.nextToken();
					react_counts = tokens.nextToken();
					issues = " ";
				}
			}
			int pro_number_count = converttoValidNumber(pro_counts);
			int react_number_count = converttoValidNumber(react_counts);
			if (react_number_count == 0) {
				for (int ctr = 0; ctr < pro_number_count; ctr++)
					add_known_issue.add(issues);
			} else if (pro_number_count == 0) {
				for (int ctr = 0; ctr < react_number_count; ctr++)
					add_known_issue.add(issues);
			} else {
				for (int proctr = 0; proctr < pro_number_count; proctr++)
					add_known_issue.add(issues);
				for (int reactr = 0; reactr < react_number_count; reactr++)
					add_known_issue.add(issues);
			}
		}
		for (Integer mongradeno : Grade)
			montana_grade.add(mongradeno);
		for (String mon_errcode : errorcode)
			montana_errcode.add(mon_errcode);
		for (String mon_team_memb : teammembers)
			montana_team_memb.add(mon_team_memb);
		for (Integer globalgradeno : Grade)
			Global_grade.add(globalgradeno);
		for (String global_errcode : errorcode)
			Global_errcode.add(global_errcode);
		for (String global_tem_memb : teammembers)
			Global_team_memb.add(global_tem_memb);
		write_process(local_copy, tickets, hrefvalues, mymap, ahreftag, FI_ID, typo_def, Created_status, assignee_name,
				script_name, add_known_issue, montana_grade, montana_errcode, montana_team_memb, Global_grade,
				Global_errcode, Global_team_memb, error_code, manager, developer);
	}

	private static void category(List tickets, List<String> KnownIssue, List hrefvalues, Map mymap, String local_copy,
			List<String> ahreftag, String manager, String developer) throws ParserConfigurationException, Exception {
		Iterator<Integer> it1 = mymap.keySet().iterator();
		while (it1.hasNext()) {
			int key = ((Integer) it1.next()).intValue();
			String str = (String) mymap.get(Integer.valueOf(key));
		}
		buffer.delete(0, buffer.length());
		assigneeName_Status(local_copy, KnownIssue, tickets, hrefvalues, mymap, ahreftag, manager, developer);
	}

	private static void assigneeName_Status(String local_copy, List<String> KnownIssue, List tickets, List hrefvalues,
			Map mymap, List<String> ahreftag, String manager, String developer)
			throws ParserConfigurationException, Exception {
		int cnt = 0;
		boolean flag = false;
		int main_cnt = 0;
		buffer.delete(0, buffer.length());
		String refvalue = "";
		String cutter = StringUtils.substringBetween(local_copy, "select-checkbox", "</body>");
		buffer.append("<html>").append(System.lineSeparator()).append("<body>").append(System.lineSeparator())
				.append(cutter).append(System.lineSeparator()).append("</body>").append(System.lineSeparator())
				.append("</html>");
		String tck_copys = buffer.toString();
		for (int index = 0; index < ahreftag.size(); index++) {
			String lines = ahreftag.get(index);
			String getlines = lines.trim();
			String cutlines = StringUtils.substringBetween(tck_copys, getlines, "</tr>");
			demotcks.add(cutlines);
		}
		for (int itr = 0; itr < demotcks.size(); itr++) {
			cnt = 0;
			String iterator = demotcks.get(itr);
			if (StringUtils.containsIgnoreCase(iterator, "Manual")) {
				Scanner scs6 = new Scanner(iterator);
				Created_status.add("Manual");
				while (scs6.hasNext()) {
					String eachline = scs6.nextLine();
					cnt++;
					if (cnt == 10) {
						String cutnew = StringUtils.substringBetween(eachline, ">", "</");
						String trim_created_status = cutnew.trim();
						FI_ID.add(trim_created_status);
					}
					if (cnt == 87) {
						String cutnew2 = StringUtils.substringBetween(eachline, ">", "</");
						String trim_assigne = cutnew2.trim();
						assignee_name.add(trim_assigne);
					}
				}
			} else {
				Scanner scs6 = new Scanner(iterator);
				while (scs6.hasNext()) {
					String eachline = scs6.nextLine();
					cnt++;
					if (cnt == 10) {
						String cutnew = StringUtils.substringBetween(eachline, ">", "</");
						String trim_created_status = cutnew.trim();
						FI_ID.add(trim_created_status);
					}
					if (cnt == 17) {
						String cutnew = StringUtils.substringBetween(eachline, ">", "</");
						String trim_created_status = cutnew.trim();
						if (StringUtils.containsIgnoreCase(trim_created_status, "Sf_Reactive")) {
							trim_created_status = "R";
						} else if (StringUtils.containsIgnoreCase(trim_created_status, "Proactive")) {
							trim_created_status = "P";
						} else if (StringUtils.containsIgnoreCase(trim_created_status, "Manual")) {
							trim_created_status = "M";
						} else {
							trim_created_status = trim_created_status;
						}
						TT_type.add(trim_created_status);
					}
					if (cnt == 31) {
						String cutnew = StringUtils.substringBetween(eachline, ">", "</");
						String trim_created_status = cutnew.trim();
						String appender = "-" + trim_created_status;
						mv_count.add(appender);
					}
					if (cnt == 94) {
						String cutnew = StringUtils.substringBetween(eachline, ">", "</");
						String trim_created_status = cutnew.trim();
						Created_status.add(trim_created_status);
					}
					if (cnt == 87) {
						String cutnew2 = StringUtils.substringBetween(eachline, ">", "</");
						String trim_assigne = cutnew2.trim();
						assignee_name.add(trim_assigne);
					}
				}
			}
		}
		for (int i = 0; i < mv_count.size(); i++) {
			typo_def.add(TT_type.get(i) + " " + mv_count.get(i));
		}
		looper_err_scr(local_copy, tickets, hrefvalues, mymap, ahreftag, Created_status, assignee_name, FI_ID, typo_def,
				manager, developer);
	}

	private static void size_err_detector(List tickets, List Created_status, List assignee_name, List script_name,
			List Grade) {
		System.out.println("Total tickets size : " + tickets.size());
		System.out.println("Script Name size : " + script_name.size());
		System.out.println("Created Status size : " + Created_status.size());
		System.out.println("Assignee Name size : " + assignee_name.size());
		System.out.println("Grade size : " + Grade.size());
		pathfield.setText("Something went Wrong");
		pathfield.setBackground(Color.RED);
		messagefield.setText("Optimus has some Change");
		messagefield.setBackground(Color.RED);
	}

	private static void validation(String response, String manager, String developer)
			throws ParserConfigurationException, Exception {
		String local_copy = response;
		String task = Task_finder(local_copy);
		Task_textField.setText(task);
		Task_textField.setBackground(Color.GREEN);
		int count = 0;
		String result = StringUtils.substringBetween(response, "<tbody>", "</tbody>");
		Scanner total_tckts = new Scanner(result);
		while (total_tckts.hasNext()) {
			String no_of_tcs = total_tckts.nextLine();
			String trimres = trim(no_of_tcs);
			buffer.append(trimres).append(System.lineSeparator());
			if (StringUtils.containsIgnoreCase(trimres, "<th>"))
				count++;
		}
		list_it(buffer.toString(), count, local_copy, manager, developer);
	}

	private static String Task_finder(String local_copy) {
		Scanner sc = new Scanner(local_copy);
		while (sc.hasNext()) {
			String eachline = sc.nextLine();
			if (StringUtils.containsIgnoreCase(eachline, "onload=\"loadTasksInternal")
					&& StringUtils.containsIgnoreCase(eachline, "id=\"page-top\"")) {
				String task = StringUtils.substringBetween(eachline, "loadTasksInternal(", ",");
				return task.trim();
			}
		}
		return StringUtils.EMPTY;

	}

	public static void write_process(String local_copy, List<String> tickets2, List hrefvalues2, Map mymap2,
			List ahreftag2, List<String> FI_ID, List<String> typo_def, List<String> created_status2,
			List<String> assignee_name2, List<String> script_name2, List<String> add_known_issue,
			List<Integer> montana_grade, List<String> montana_errcode, List<String> montana_team_memb,
			List<Integer> Global_grade, List<String> Global_errcode, List<String> Global_team_memb, String error_code,
			String manager, String developer) throws Exception {

		for (String string : typo_def) {
			System.out.println(string);
		}
		String filename = "C:\\Tickets\\Assignment ";
		String datenow = "";
		String overall = null;
		Create_mainFolder(filename);

		if ((StringUtils.containsIgnoreCase(manager, "Tammy")
				&& StringUtils.containsIgnoreCase(developer, "Cognizant-Montana"))
				|| (StringUtils.containsIgnoreCase(manager, "Tammy")
						&& StringUtils.containsIgnoreCase(developer, "All"))) {
			Montana_Write_process.montana_writing(local_copy, tickets2, hrefvalues2, FI_ID, typo_def, mymap2, ahreftag2,
					created_status2, assignee_name2, script_name2, add_known_issue, montana_grade, montana_errcode,
					montana_team_memb, error_code, manager, developer);
		} else if ((StringUtils.containsIgnoreCase(manager, "Nir")
				&& StringUtils.containsIgnoreCase(developer, "Global_support"))
				|| (StringUtils.containsIgnoreCase(manager, "Nir")
						&& StringUtils.containsIgnoreCase(developer, "All"))) {
			Global_Write_process.Global_Writing(local_copy, tickets2, hrefvalues2, FI_ID, typo_def, mymap2, ahreftag2,
					created_status2, assignee_name2, script_name2, add_known_issue, Global_errcode, Global_grade,
					Global_team_memb, error_code, manager, developer);
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("MMM-dd");
			SimpleDateFormat sdf1 = new SimpleDateFormat("HH:ss");
			Date date1 = new Date();
			Date date2 = new Date();
			datenow = sdf.format(date1);
			String secnow = sdf1.format(date2);
			letter_definer = filenamechanger(filename, datenow, manager, developer);
			String cut_backup = StringUtils.substringBetween(letter_definer, "Tickets\"\"/", ".");
			String s_name = "snagarajan1";
			String k_name = "kbaskaran";
			String v_name = "vbalakrishn";
			String m_name = "mjayaraj";
			String b_name = "ssampathkum";
			String s_ori_name = "Saraswathi";
			String k_ori_name = "Karthik";
			String v_ori_name = "Vignesh";
			String m_ori_name = "Mohan";
			String b_ori_name = "Balaji";
			int kcount = 0;
			int mcount = 0;
			int scount = 0;
			int bcount = 0;
			int vcount = 0;
			String default_name = "System";
			int ticket_size = tickets.size();
			int errcode_size = errorcode.size();
			int cr_status = Created_status.size();
			int assignestatus_size = assignee_name.size();
			int grade_size = Grade.size();
			int total = 14;
			try {
				XSSFWorkbook book = new XSSFWorkbook();
				XSSFSheet sheet = book.createSheet("TICKETS");
				XSSFCellStyle style = book.createCellStyle();
				style.setBorderBottom(BorderStyle.THIN);
				style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
				style.setBorderRight(BorderStyle.THIN);
				style.setRightBorderColor(IndexedColors.BLACK.getIndex());
				style.setBorderTop(BorderStyle.THIN);
				style.setTopBorderColor(IndexedColors.BLACK.getIndex());
				XSSFCellStyle xSSFCellStyle1 = book.createCellStyle();
				XSSFCellStyle xSSFCellStyle7 = book.createCellStyle();
				XSSFCellStyle xSSFCellStyle2 = book.createCellStyle();
				xSSFCellStyle2.setBorderBottom(BorderStyle.THIN);
				xSSFCellStyle2.setBottomBorderColor(IndexedColors.BLACK.getIndex());
				xSSFCellStyle2.setBorderRight(BorderStyle.THIN);
				xSSFCellStyle2.setRightBorderColor(IndexedColors.BLACK.getIndex());
				xSSFCellStyle2.setBorderTop(BorderStyle.THIN);
				style.setTopBorderColor(IndexedColors.BLACK.getIndex());
				XSSFCellStyle xSSFCellStyle3 = book.createCellStyle();
				xSSFCellStyle3.setBorderBottom(BorderStyle.THIN);
				xSSFCellStyle3.setBottomBorderColor(IndexedColors.BLACK.getIndex());
				xSSFCellStyle3.setBorderRight(BorderStyle.THIN);
				xSSFCellStyle3.setRightBorderColor(IndexedColors.BLACK.getIndex());
				xSSFCellStyle3.setBorderTop(BorderStyle.THIN);
				style.setTopBorderColor(IndexedColors.BLACK.getIndex());
				XSSFFont font = book.createFont();
				style.setAlignment((short) 2);
				XSSFRow row = sheet.createRow(0);
				List<String> headings = Head_call();
				for (int mainitr = 0; mainitr < headings.size(); mainitr++) {
					String cellname = "cell" + mainitr;
					XSSFCell xSSFCell = row.createCell(mainitr);
					xSSFCell.setCellValue(headings.get(mainitr));
					xSSFCellStyle7.setFillPattern((short) 1);
					xSSFCell.setCellStyle((CellStyle) xSSFCellStyle1);
					font.setBoldweight((short) 700);
					xSSFCellStyle7.setFont(font);
					xSSFCell.setCellStyle((CellStyle) style);
					xSSFCellStyle7.setBorderBottom(BorderStyle.THIN);
					xSSFCellStyle7.setBottomBorderColor(IndexedColors.BLACK.getIndex());
					xSSFCellStyle7.setBorderRight(BorderStyle.THIN);
					xSSFCellStyle7.setRightBorderColor(IndexedColors.BLACK.getIndex());
					xSSFCellStyle7.setBorderTop(BorderStyle.THIN);
					style.setTopBorderColor(IndexedColors.BLACK.getIndex());
					xSSFCell.setCellStyle((CellStyle) xSSFCellStyle7);
					xSSFCellStyle7.setAlignment((short) 2);
					xSSFCellStyle7.setFillForegroundColor(IndexedColors.LIGHT_TURQUOISE.getIndex());
				}
				System.out.println(String.valueOf(FI_ID.size()) + "   " + errorcode.size());
				if (ticket_size == errorcode.size() && ticket_size == Created_status.size()
						&& ticket_size == assignee_name.size() && ticket_size == Grade.size()) {
					for (int i = 0; i < tickets.size()-1; i++) {
						row = sheet.createRow(i + 1);
						for (int j = 0; j < 10; j++) {
							XSSFCell xSSFCell = row.createCell(j);
							if (xSSFCell.getColumnIndex() == 0) {
								xSSFCell.setCellValue(tickets.get(i));
								xSSFCell.setCellStyle((CellStyle) style);
							}
							if (xSSFCell.getColumnIndex() == 1) {
								xSSFCell.setCellValue(script_name.get(i));
								xSSFCell.setCellStyle((CellStyle) style);
							}
							if (xSSFCell.getColumnIndex() == 2) {
								xSSFCell.setCellValue(errorcode.get(i));
								xSSFCell.setCellStyle((CellStyle) style);
							}
							if (xSSFCell.getColumnIndex() == 3) {
								xSSFCell.setCellValue(FI_ID.get(i));
								xSSFCell.setCellStyle((CellStyle) style);
							}
							if (xSSFCell.getColumnIndex() == 4) {
								xSSFCell.setCellValue(Created_status.get(i));
								xSSFCell.setCellStyle((CellStyle) style);
							}
							if (xSSFCell.getColumnIndex() == 5) {
								xSSFCell.setCellValue(((Integer) Grade.get(i)).intValue());
								xSSFCell.setCellStyle((CellStyle) style);
							}
							if (xSSFCell.getColumnIndex() == 6) {
								xSSFCell.setCellValue(assignee_name.get(i));
								xSSFCell.setCellStyle((CellStyle) style);
							}
							if (xSSFCell.getColumnIndex() == 6)
								if (StringUtils.containsIgnoreCase(assignee_name.get(i), "m_name")) {
									mcount++;
									xSSFCell.setCellValue(m_ori_name);
									xSSFCell.setCellStyle((CellStyle) style);
								} else if (StringUtils.containsIgnoreCase(assignee_name.get(i), "v_name")) {
									vcount++;
									xSSFCell.setCellValue(v_ori_name);
									xSSFCell.setCellStyle((CellStyle) style);
								} else if (StringUtils.containsIgnoreCase(assignee_name.get(i), "k_name")) {
									kcount++;
									xSSFCell.setCellValue(k_ori_name);
									xSSFCell.setCellStyle((CellStyle) style);
								} else if (StringUtils.containsIgnoreCase(assignee_name.get(i), "b_name")) {
									bcount++;
									xSSFCell.setCellValue(b_ori_name);
									xSSFCell.setCellStyle((CellStyle) style);
								} else if (StringUtils.containsIgnoreCase(script_name.get(i), "q2tower_api2.scr")) {
									scount++;
									xSSFCell.setCellValue(s_ori_name);
									xSSFCell.setCellStyle((CellStyle) style);
								} else if (StringUtils.containsIgnoreCase(Created_status.get(i), "In progress")
										&& StringUtils.containsIgnoreCase(assignee_name.get(i), b_name)) {
									bcount++;
									xSSFCell.setCellValue(b_ori_name);
									xSSFCell.setCellStyle((CellStyle) style);
								} else if (StringUtils.containsIgnoreCase(Created_status.get(i), "In progress")
										&& StringUtils.containsIgnoreCase(assignee_name.get(i), k_name)) {
									kcount++;
									xSSFCell.setCellValue(k_ori_name);
									xSSFCell.setCellStyle((CellStyle) style);
								} else if (StringUtils.containsIgnoreCase(Created_status.get(i), "In progress")
										&& StringUtils.containsIgnoreCase(assignee_name.get(i), v_name)) {
									vcount++;
									xSSFCell.setCellValue(b_ori_name);
									xSSFCell.setCellStyle((CellStyle) style);
								} else if (StringUtils.containsIgnoreCase(Created_status.get(i), "In progress")
										&& StringUtils.containsIgnoreCase(assignee_name.get(i), s_name)) {
									scount++;
									xSSFCell.setCellValue(s_ori_name);
									xSSFCell.setCellStyle((CellStyle) style);
								} else if (StringUtils.containsIgnoreCase(Created_status.get(i), "In progress")
										&& StringUtils.containsIgnoreCase(assignee_name.get(i), m_name)) {
									mcount++;
									xSSFCell.setCellValue(m_ori_name);
									xSSFCell.setCellStyle((CellStyle) style);
								} else if (StringUtils.containsIgnoreCase(assignee_name.get(i), "System")
										&& !StringUtils.containsIgnoreCase(script_name.get(i), "q2tower_api2.scr")) {
									xSSFCell.setCellValue(teammembers.get(i));
									xSSFCell.setCellStyle((CellStyle) style);
								}
							if (mark && xSSFCell.getColumnIndex() == 8) {
								int length = ((String) add_known_issue.get(i)).length() - 1;
								overall = ((String) add_known_issue.get(i)).substring(0,
										((String) add_known_issue.get(i)).length() - 1);
								xSSFCell.setCellValue(overall);
								xSSFCell.setCellStyle((CellStyle) style);
							}
							if (xSSFCell.getColumnIndex() == 7)
								if (!StringUtils.equalsIgnoreCase(assignee_name.get(i), s_name)
										&& !StringUtils.equalsIgnoreCase(assignee_name.get(i), m_name)
										&& !StringUtils.equalsIgnoreCase(assignee_name.get(i), k_name)
										&& !StringUtils.equalsIgnoreCase(assignee_name.get(i), v_name)
										&& !StringUtils.equalsIgnoreCase(assignee_name.get(i), b_name)
										&& !StringUtils.equalsIgnoreCase(assignee_name.get(i), default_name)) {
									other_cnt++;
									xSSFCell.setCellValue("Assigned By IL Team");
									xSSFCell.setCellStyle((CellStyle) style);
								} else if (StringUtils.containsIgnoreCase(script_name.get(i), "Hint")) {
									other_cnt++;
									xSSFCell.setCellValue("Hint Script");
									xSSFCell.setCellStyle((CellStyle) style);
								} else {
									xSSFCell.setCellStyle((CellStyle) style);
								}
							if (mark == false) {
								if (xSSFCell.getColumnIndex() == 8) {
									xSSFCell.setCellValue(Overall_optimus_c5.typo_def.get(i));
									xSSFCell.setCellStyle((CellStyle) style);
								}
							}
							if (mark == true) {
								if (xSSFCell.getColumnIndex() == 9) {
									xSSFCell.setCellValue(Overall_optimus_c5.typo_def.get(i));
									xSSFCell.setCellStyle((CellStyle) style);
								}
							}
						}
					}
					row = sheet.createRow(tickets.size() + 5);
					XSSFCell xSSFCell1 = row.createCell(0);
					xSSFCell1.setCellStyle((CellStyle) style);
					xSSFCell1.setCellValue("Total Tickets : ");
					xSSFCellStyle1.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
					xSSFCellStyle1.setFillPattern((short) 1);
					xSSFCell1.setCellStyle((CellStyle) xSSFCellStyle1);
					font.setBoldweight((short) 700);
					xSSFCellStyle1.setFont(font);
					xSSFCell1.setCellStyle((CellStyle) style);
					xSSFCell1.setCellStyle((CellStyle) xSSFCellStyle1);
					XSSFCell xSSFCell2 = row.createCell(1);
					xSSFCell2.setCellValue(tickets.size());
					xSSFCell2.setCellStyle((CellStyle) style);
					String value1 = String.valueOf(tickets.size());
					Ticketfield.setText(value1);
					Ticketfield.setBackground(Color.lightGray);
					row = sheet.createRow(tickets.size() + 6);
					xSSFCell1 = row.createCell(0);
					xSSFCell1.setCellStyle((CellStyle) style);
					xSSFCell1.setCellValue("Others : ");
					xSSFCellStyle2.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
					xSSFCellStyle2.setFillPattern((short) 1);
					xSSFCell1.setCellStyle((CellStyle) xSSFCellStyle1);
					font.setBoldweight((short) 700);
					xSSFCellStyle2.setFont(font);
					xSSFCell1.setCellStyle((CellStyle) style);
					xSSFCell1.setCellStyle((CellStyle) xSSFCellStyle2);
					xSSFCell2 = row.createCell(1);
					xSSFCell2.setCellValue(other_cnt);
					xSSFCell2.setCellStyle((CellStyle) style);
					String value2 = String.valueOf(other_cnt);
					Otherfield.setText(value2);
					Otherfield.setBackground(Color.YELLOW);
					row = sheet.createRow(tickets.size() + 7);
					xSSFCell1 = row.createCell(0);
					xSSFCell1.setCellStyle((CellStyle) style);
					rem_value = ticket_size - other_cnt;
					xSSFCell1.setCellValue("Need to Work : ");
					xSSFCellStyle3.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
					xSSFCellStyle3.setFillPattern((short) 1);
					xSSFCell1.setCellStyle((CellStyle) xSSFCellStyle1);
					font.setBoldweight((short) 700);
					xSSFCellStyle3.setFont(font);
					xSSFCell1.setCellStyle((CellStyle) style);
					xSSFCell1.setCellStyle((CellStyle) xSSFCellStyle3);
					String value3 = String.valueOf(rem_value);
					Workfield.setText(value3);
					Workfield.setBackground(Color.green);
					xSSFCell2 = row.createCell(1);
					xSSFCell2.setCellValue(rem_value);
					xSSFCell2.setCellStyle((CellStyle) style);
					row = sheet.createRow(tickets.size() + 9);
					xSSFCell1 = row.createCell(0);
					xSSFCell1.setCellStyle((CellStyle) style);
					xSSFCell1.setCellValue("Manager : ");
					xSSFCellStyle1.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
					xSSFCellStyle1.setFillPattern((short) 1);
					xSSFCell1.setCellStyle((CellStyle) xSSFCellStyle1);
					font.setBoldweight((short) 700);
					xSSFCellStyle1.setFont(font);
					xSSFCell1.setCellStyle((CellStyle) style);
					xSSFCell1.setCellStyle((CellStyle) xSSFCellStyle1);
					xSSFCell2 = row.createCell(1);
					xSSFCell2.setCellValue(manager);
					xSSFCell2.setCellStyle((CellStyle) style);
					row = sheet.createRow(tickets.size() + 10);
					xSSFCell1 = row.createCell(0);
					xSSFCell1.setCellStyle((CellStyle) style);
					xSSFCell1.setCellValue("Developer : ");
					xSSFCellStyle1.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
					xSSFCellStyle1.setFillPattern((short) 1);
					xSSFCell1.setCellStyle((CellStyle) xSSFCellStyle1);
					font.setBoldweight((short) 700);
					xSSFCellStyle1.setFont(font);
					xSSFCell1.setCellStyle((CellStyle) style);
					xSSFCell1.setCellStyle((CellStyle) xSSFCellStyle1);
					xSSFCell2 = row.createCell(1);
					xSSFCell2.setCellValue(developer);
					xSSFCell2.setCellStyle((CellStyle) style);
					for (int allign = 0; allign < total; allign++) {
						sheet.autoSizeColumn(allign);
					}
					System.out.println(letter_definer);
					FileOutputStream out = new FileOutputStream(new File(letter_definer));

					book.write(out);
					backup_creater(letter_definer);
					System.out.println("Created Path :" + letter_definer);
					String displayname = StringUtils.substringBefore(letter_definer, ".");
					pathfield.setText(displayname);
					pathfield.setBackground(Color.GREEN);
					System.out.println("");
					System.out.println("!!!!...............FILE CREATED SUCCESSFULLY.................!!!");
					messagefield.setText(" !...FILE CREATED SUCCESSFULLY....!");
					messagefield.setBackground(Color.GREEN);
					Montana_Write_process.log_files(local_copy, tickets2, hrefvalues2, mymap2, ahreftag2,
							created_status2, assignee_name2, script_name2, montana_grade, montana_errcode,
							montana_team_memb, error_code, manager, developer);
				} else {
					String err_names = "Sizeerror";
					err_display.errorcatcher(err_names);
					System.out.println("Sizes are not equal............Please check");
					System.out.println("");
					size_err_detector(tickets, Created_status, assignee_name, script_name, Grade);
				}
			} catch (FileNotFoundException e) {
				messagefield.setText("File is already Open..Please close it");
				messagefield.setBackground(Color.RED);
				System.out.println("ERROR ::--->" + e);
				System.out.println(
						"Message ::---->The File is already in Open state.....Please close it and run the program");
			}
		}
	}

	public static void file_opener() throws IOException {
		Desktop desk = Desktop.getDesktop();
		if (letter_definer.isEmpty()) {
			montan.file_opener();
		}
		File fileOpen = new File(letter_definer);

		desk.open(fileOpen);
	}

	private static String Create_mainFolder(String filename) {
		File folder = new File("C:\\Tickets");
		if (!folder.exists()) {
			folder.mkdir();
			return filename;
		}
		return filename;
	}

	protected static void backup_creater(String letter_definer) throws IOException {
		File backup_folder = new File("C:\\Backup");
		String real_file = "C:/Tickets/";
		String copy_file = "C:/Backup/";
		if (!backup_folder.exists())
			backup_folder.mkdir();
		File sourcefile = new File(letter_definer);
		String realname = sourcefile.getName();
		File targer_file = new File(String.valueOf(copy_file) + realname);
		FileUtils.copyFile(sourcefile, targer_file);
	}

	protected static String filenamechanger(String filename, String datenow, String manager, String developer) {
		buffer.delete(0, buffer.length());
		String[] dev_letter = new String[0];
		char m_letter = manager.charAt(0);
		if (StringUtils.containsIgnoreCase(developer, "-")) {
			dev_letter = developer.split("-");
		} else if (StringUtils.containsIgnoreCase(developer, "_")) {
			dev_letter = developer.split("_");
		} else if (!StringUtils.containsIgnoreCase(developer, "-") && !StringUtils.containsIgnoreCase(developer, "_")) {
			char one_letter = developer.charAt(0);
			buffer.append("(").append(m_letter).append(one_letter).append(")").append("-").append(" ");
			String str = String.valueOf(filename) + buffer.toString().toUpperCase() + datenow + ".xlsx";
			return str;
		}
		char first_letter = dev_letter[0].charAt(0);
		char second_letter = dev_letter[1].charAt(0);
		buffer.append("(").append(m_letter).append(first_letter).append(second_letter).append(")").append("-")
				.append(" ");
		String final_file = String.valueOf(filename) + buffer.toString().toUpperCase() + datenow + ".xlsx";
		return final_file;
	}

	protected static List<String> Head_call() {
		headings.add("TICKET ID");
		headings.add("SCRIPT NAME");
		headings.add("ERROR CODE");
		headings.add("FI ID");
		headings.add("STATUS");
		headings.add("PRIORITY");
		headings.add("ASSIGNED TO");
		headings.add("COMMENTS");
		if (mark) {
			headings.add("Known Issues");
		}
		headings.add("DETAILS");
		return headings;
	}

	protected static void postdatas(int responsecode, String manager, String developer)
			throws ParserConfigurationException, Exception {
		HttpClient httpClient = new HttpClient();
		PostMethod postMethod = new PostMethod("http://172.22.106.43:8087/tasks/internal");
		postMethod.addParameter("selectedManager", manager);
		postMethod.addParameter("SelectedDeveloper", developer);
		httpClient.executeMethod((HttpMethod) postMethod);
		String getresbody = new String(postMethod.getResponseBody());
		if (StringUtils.isEmpty(getresbody)) {
			System.out.println("Reponse body is Empty");
			System.exit(0);
		}
		if (responsecode == 200) {
			statusfield.setText("SUCCESS");
			statusfield.setBackground(Color.GREEN);
			System.out.println("Successfully Connected : ----------> " + responsecode + "%");
			System.out.println("");
			validation(getresbody, manager, developer);
		} else {
			String err_names = "Too much Time";
			err_display.errorcatcher(err_names);
			statusfield.setText("FAILURE");
			statusfield.setBackground(Color.RED);
			messagefield.setText("Connection Failed...");
		}
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Overall_optimus_c5 window = new Overall_optimus_c5();
					Overall_optimus_c5.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Overall_optimus_c5() {
		initialize();
	}

	protected void initialize() {
		frame = new JFrame("STARTING >>>> AUTO LAUNCH");
		frame.setBackground(new Color(51, 153, 204));
		frame.getContentPane().setBackground(new Color(153, 204, 204));
		frame.getContentPane().setForeground(Color.CYAN);
		frame.setBounds(100, 100, 848, 480);
		frame.setDefaultCloseOperation(3);
		frame.getContentPane().setLayout((LayoutManager) null);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().setLayout(null);
		JLabel lblTask = new JLabel("AUTO TASK ASSIGNER #C4");
		lblTask.setBackground(new Color(0, 255, 255));
		lblTask.setForeground(Color.BLACK);
		lblTask.setFont(new Font("Verdana", 1, 15));
		lblTask.setBounds(297, 36, 231, 14);
		frame.getContentPane().add(lblTask);
		JLabel lblManager = new JLabel("Manager :");
		lblManager.setForeground(Color.BLUE);
		lblManager.setFont(new Font("Verdana", 1, 11));
		lblManager.setBounds(297, 81, 98, 20);
		frame.getContentPane().add(lblManager);
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(445, 120, 1, 164);
		frame.getContentPane().add(separator_1);
		JLabel lblDeveloper = new JLabel("Developer :");
		lblDeveloper.setForeground(Color.BLUE);
		lblDeveloper.setFont(new Font("Verdana", 1, 11));
		lblDeveloper.setBounds(297, 133, 86, 18);
		frame.getContentPane().add(lblDeveloper);
		JLabel lblNewLabel = new JLabel("Total Tickets : ");
		lblNewLabel.setFont(new Font("Verdana", 1, 11));
		lblNewLabel.setBounds(10, 306, 113, 14);
		frame.getContentPane().add(lblNewLabel);
		JLabel lblOthers = new JLabel("Others :");
		lblOthers.setFont(new Font("Verdana", 1, 11));
		lblOthers.setBounds(49, 346, 52, 14);
		frame.getContentPane().add(lblOthers);
		JLabel lblNeedToWork = new JLabel("Need To Work :");
		lblNeedToWork.setFont(new Font("Verdana", 1, 11));
		lblNeedToWork.setBounds(10, 393, 106, 14);
		frame.getContentPane().add(lblNeedToWork);
		Ticketfield = new JTextField();
		Ticketfield.setHorizontalAlignment(SwingConstants.CENTER);
		Ticketfield.setBackground(SystemColor.inactiveCaptionBorder);
		Ticketfield.setFont(new Font("Tahoma", 1, 11));
		Ticketfield.setForeground(Color.BLACK);
		Ticketfield.setBounds(123, 304, 86, 20);
		frame.getContentPane().add(Ticketfield);
		Ticketfield.setColumns(10);
		Otherfield = new JTextField();
		Otherfield.setHorizontalAlignment(SwingConstants.CENTER);
		Otherfield.setBackground(SystemColor.inactiveCaptionBorder);
		Otherfield.setFont(new Font("Tahoma", 1, 11));
		Otherfield.setBounds(123, 344, 86, 20);
		frame.getContentPane().add(Otherfield);
		Otherfield.setColumns(10);
		Workfield = new JTextField();
		Workfield.setHorizontalAlignment(SwingConstants.CENTER);
		Workfield.setBackground(SystemColor.inactiveCaptionBorder);
		Workfield.setFont(new Font("Tahoma", 1, 11));
		Workfield.setBounds(123, 391, 86, 20);
		frame.getContentPane().add(Workfield);
		Workfield.setColumns(10);
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(439, 229, 1, 201);
		frame.getContentPane().add(separator_2);
		JLabel lblNewLabel_1 = new JLabel("Conn Status :");
		lblNewLabel_1.setFont(new Font("Verdana", 1, 11));
		lblNewLabel_1.setBounds(496, 306, 97, 14);
		frame.getContentPane().add(lblNewLabel_1);
		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(386, 282, -383, 2);
		frame.getContentPane().add(separator_3);
		JSeparator separator_4 = new JSeparator();
		separator_4.setBounds(10, 258, 832, 23);
		frame.getContentPane().add(separator_4);
		JLabel lblNewLabel_2 = new JLabel("Path :");
		lblNewLabel_2.setFont(new Font("Verdana", 1, 11));
		lblNewLabel_2.setBounds(548, 346, 46, 14);
		frame.getContentPane().add(lblNewLabel_2);
		statusfield = new JTextField();
		statusfield.setHorizontalAlignment(SwingConstants.CENTER);
		statusfield.setBackground(SystemColor.inactiveCaptionBorder);
		statusfield.setFont(new Font("Tahoma", 1, 11));
		statusfield.setBounds(591, 303, 231, 20);
		frame.getContentPane().add(statusfield);
		statusfield.setColumns(10);
		pathfield = new JTextField();
		pathfield.setHorizontalAlignment(SwingConstants.CENTER);
		pathfield.setBackground(SystemColor.inactiveCaptionBorder);
		pathfield.setFont(new Font("Tahoma", 1, 11));
		pathfield.setBounds(591, 340, 231, 20);
		frame.getContentPane().add(pathfield);
		pathfield.setColumns(10);
		JLabel lblMessage = new JLabel("MESSAGE :");
		lblMessage.setFont(new Font("Verdana", 1, 11));
		lblMessage.setBounds(516, 393, 89, 14);
		frame.getContentPane().add(lblMessage);
		messagefield = new JTextField();
		messagefield.setHorizontalAlignment(SwingConstants.CENTER);
		messagefield.setBackground(SystemColor.inactiveCaptionBorder);
		messagefield.setFont(new Font("Tahoma", 1, 11));
		messagefield.setBounds(591, 390, 231, 20);
		frame.getContentPane().add(messagefield);
		messagefield.setColumns(10);
		final JCheckBox KI_checkbos = new JCheckBox("K I");
		KI_checkbos.setFont(new Font("Tahoma", Font.BOLD, 13));
		KI_checkbos.setBackground(SystemColor.controlHighlight);
		KI_checkbos.setForeground(Color.BLACK);
		KI_checkbos.setBounds(779, 106, 64, 23);
		frame.getContentPane().add(KI_checkbos);
		String[] managers = { "Shirel", "Tammy", "Nir" };
		final JComboBox Manager_comboBox_1 = new JComboBox(managers);
		Manager_comboBox_1.setForeground(Color.BLACK);
		Manager_comboBox_1.setBounds(386, 82, 134, 20);
		frame.getContentPane().add(Manager_comboBox_1);
		String[] developer = { "Cognizant-Alaska", "Cognizant-Montana", "Global_support", "SYSTEM", "All" };
		final JComboBox Dev_comboBox_2 = new JComboBox(developer);
		Dev_comboBox_2.setBounds(386, 131, 134, 20);
		frame.getContentPane().add(Dev_comboBox_2);
		JButton btnNewButton = new JButton("Launch");
		btnNewButton.setFont(new Font("Tahoma", 1, 11));
		btnNewButton.setForeground(Color.BLACK);
		btnNewButton.setBounds(373, 209, 89, 23);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Manager_comboBox_1.getSelectedItem() == "Shirel"
						&& Dev_comboBox_2.getSelectedItem() == "Cognizant-Alaska" && !KI_checkbos.isSelected()) {
					String manager = "Shirel";
					String developer = "Cognizant-Alaska";
					try {
						Overall_optimus_c5.initialise(manager, developer);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
				if (Manager_comboBox_1.getSelectedItem() == "Shirel"
						&& Dev_comboBox_2.getSelectedItem() == "Cognizant-Alaska" && KI_checkbos.isSelected()) {
					Overall_optimus_c5.mark = true;
					String manager = "Shirel";
					String developer = "Cognizant-Alaska";
					try {
						Overall_optimus_c5.initialise(manager, developer);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				} else if (Manager_comboBox_1.getSelectedItem() == "Shirel" && Dev_comboBox_2.getSelectedItem() == "All"
						&& !KI_checkbos.isSelected()) {
					String manager = "Shirel";
					String developer = "All";
					try {
						Overall_optimus_c5.initialise(manager, developer);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				} else if (Manager_comboBox_1.getSelectedItem() == "Shirel" && Dev_comboBox_2.getSelectedItem() == "All"
						&& KI_checkbos.isSelected()) {
					Overall_optimus_c5.mark = true;
					String manager = "Shirel";
					String developer = "All";
					try {
						Overall_optimus_c5.initialise(manager, developer);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				} else if (Manager_comboBox_1.getSelectedItem() == "Shirel"
						&& Dev_comboBox_2.getSelectedItem() == "SYSTEM" && !KI_checkbos.isSelected()) {
					String manager = "Shirel";
					String developer = "SYSTEM";
					try {
						Overall_optimus_c5.initialise(manager, developer);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				} else if (Manager_comboBox_1.getSelectedItem() == "Shirel"
						&& Dev_comboBox_2.getSelectedItem() == "SYSTEM" && KI_checkbos.isSelected()) {
					Overall_optimus_c5.mark = true;
					String manager = "Shirel";
					String developer = "SYSTEM";
					try {
						Overall_optimus_c5.initialise(manager, developer);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				} else if (Manager_comboBox_1.getSelectedItem() == "Tammy"
						&& Dev_comboBox_2.getSelectedItem() == "Cognizant-Montana" && !KI_checkbos.isSelected()) {
					String manager = "Tammy";
					String developer = "Cognizant-Montana";
					try {
						Overall_optimus_c5.initialise(manager, developer);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				} else if (Manager_comboBox_1.getSelectedItem() == "Tammy"
						&& Dev_comboBox_2.getSelectedItem() == "Cognizant-Montana" && KI_checkbos.isSelected()) {
					Overall_optimus_c5.mark = true;
					String manager = "Tammy";
					String developer = "Cognizant-Montana";
					try {
						Overall_optimus_c5.initialise(manager, developer);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				} else if (Manager_comboBox_1.getSelectedItem() == "Tammy" && Dev_comboBox_2.getSelectedItem() == "All"
						&& !KI_checkbos.isSelected()) {
					String manager = "Tammy";
					String developer = "All";
					try {
						Overall_optimus_c5.initialise(manager, developer);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				} else if (Manager_comboBox_1.getSelectedItem() == "Tammy" && Dev_comboBox_2.getSelectedItem() == "All"
						&& KI_checkbos.isSelected()) {
					Overall_optimus_c5.mark = true;
					String manager = "Tammy";
					String developer = "All";
					try {
						Overall_optimus_c5.initialise(manager, developer);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				} else if (Manager_comboBox_1.getSelectedItem() == "Tammy"
						&& Dev_comboBox_2.getSelectedItem() == "SYSTEM" && !KI_checkbos.isSelected()) {
					String manager = "Tammy";
					String developer = "SYSTEM";
					try {
						Overall_optimus_c5.initialise(manager, developer);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				} else if (Manager_comboBox_1.getSelectedItem() == "Tammy"
						&& Dev_comboBox_2.getSelectedItem() == "SYSTEM" && KI_checkbos.isSelected()) {
					Overall_optimus_c5.mark = true;
					String manager = "Tammy";
					String developer = "SYSTEM";
					try {
						Overall_optimus_c5.initialise(manager, developer);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				} else if (Manager_comboBox_1.getSelectedItem() == "Nir"
						&& Dev_comboBox_2.getSelectedItem() == "Global_support" && !KI_checkbos.isSelected()) {
					String manager = "Nir";
					String developer = "Global_support";
					try {
						Overall_optimus_c5.initialise(manager, developer);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				} else if (Manager_comboBox_1.getSelectedItem() == "Nir"
						&& Dev_comboBox_2.getSelectedItem() == "Global_support" && KI_checkbos.isSelected()) {
					Overall_optimus_c5.mark = true;
					String manager = "Nir";
					String developer = "Global_support";
					try {
						Overall_optimus_c5.initialise(manager, developer);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				} else if (Manager_comboBox_1.getSelectedItem() == "Nir" && Dev_comboBox_2.getSelectedItem() == "All"
						&& !KI_checkbos.isSelected()) {
					String manager = "Nir";
					String developer = "All";
					try {
						Overall_optimus_c5.initialise(manager, developer);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				} else if (Manager_comboBox_1.getSelectedItem() == "Nir" && Dev_comboBox_2.getSelectedItem() == "All"
						&& KI_checkbos.isSelected()) {
					Overall_optimus_c5.mark = true;
					String manager = "Nir";
					String developer = "All";
					try {
						Overall_optimus_c5.initialise(manager, developer);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				} else if (Manager_comboBox_1.getSelectedItem() == "Nir" && Dev_comboBox_2.getSelectedItem() == "SYSTEM"
						&& !KI_checkbos.isSelected()) {
					String manager = "Nir";
					String developer = "SYSTEM";
					try {
						Overall_optimus_c5.initialise(manager, developer);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				} else if (Manager_comboBox_1.getSelectedItem() == "Nir" && Dev_comboBox_2.getSelectedItem() == "SYSTEM"
						&& !KI_checkbos.isSelected()) {
					Overall_optimus_c5.mark = true;
					String manager = "Nir";
					String developer = "SYSTEM";
					try {
						Overall_optimus_c5.initialise(manager, developer);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				} else if ((Manager_comboBox_1.getSelectedItem() == "Shirel"
						&& Dev_comboBox_2.getSelectedItem() == "Cognizant-Montana")
						|| (Manager_comboBox_1.getSelectedItem() == "Tammy"
								&& Dev_comboBox_2.getSelectedItem() == "Cognizant-Alaska")
						|| (Manager_comboBox_1.getSelectedItem() == "Shirel"
								&& Dev_comboBox_2.getSelectedItem() == "Global_support")
						|| (Manager_comboBox_1.getSelectedItem() == "Tammy"
								&& Dev_comboBox_2.getSelectedItem() == "Global_support")
						|| (Manager_comboBox_1.getSelectedItem() == "Nir"
								&& Dev_comboBox_2.getSelectedItem() == "Cognizant-Alaska")
						|| (Manager_comboBox_1.getSelectedItem() == "Nir"
								&& Dev_comboBox_2.getSelectedItem() == "Cognizant-Montana")) {
					Overall_optimus_c5.messagefield.setText("Please Check Options");
					Overall_optimus_c5.messagefield.setBackground(Color.RED);
				}
			}
		});
		frame.getContentPane().setLayout((LayoutManager) null);
		frame.getContentPane().add(btnNewButton);
		JSeparator separator = new JSeparator();
		separator.setBounds(320, 73, -326, -3);
		frame.getContentPane().add(separator);
		JSeparator separator_5 = new JSeparator();
		separator_5.setBounds(307, 73, -300, -3);
		frame.getContentPane().add(separator_5);

		JButton btnNewButton_1 = new JButton(">");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					file_opener();
				} catch (IOException e) {

					e.printStackTrace();
				}
			}
		});
		btnNewButton_1.setForeground(new Color(0, 0, 0));
		btnNewButton_1.setBackground(SystemColor.inactiveCaption);
		btnNewButton_1.setBounds(474, 209, 46, 20);
		frame.getContentPane().add(btnNewButton_1);

		JLabel lblNewLabel_3 = new JLabel("Tasks ");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setFont(new Font("Verdana", Font.BOLD, 11));
		lblNewLabel_3.setBounds(354, 283, 64, 16);
		frame.getContentPane().add(lblNewLabel_3);

		Task_textField = new JTextField();
		Task_textField.setBackground(SystemColor.inactiveCaptionBorder);
		Task_textField.setFont(new Font("Tahoma", Font.BOLD, 11));
		Task_textField.setHorizontalAlignment(SwingConstants.CENTER);
		Task_textField.setBounds(341, 302, 86, 22);
		frame.getContentPane().add(Task_textField);
		Task_textField.setColumns(10);

	}
}
