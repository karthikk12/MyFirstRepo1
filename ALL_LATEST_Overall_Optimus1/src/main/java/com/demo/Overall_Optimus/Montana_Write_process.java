package com.demo.Overall_Optimus;

import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JTextField;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import Error_Thrower.Error_displayer;

public class Montana_Write_process extends MyScriptFrame {
	static StringBuffer totaltickets = new StringBuffer();

	static List<String> sepline = new ArrayList<String>();

	static List<String> hrefvalues = new ArrayList<String>();

	static List<String> ahreftag = new ArrayList<String>();

	static List<String> demotcks = new ArrayList<String>();

	static List<String> tickets = new ArrayList<String>();

	static List<String> script_name = new ArrayList<String>();

	static List<String> teammembers = new ArrayList<String>();

	static List<String> errorcode = new ArrayList<String>();

	static List<Integer> Grade = new ArrayList<Integer>();

	static List<String> headings = new ArrayList<String>();

	static List<String> Countcalc = new ArrayList<String>();

	static List<String> Created_status = new ArrayList<String>();

	static List<String> assignee_name = new ArrayList<String>();

	static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");

	static SimpleDateFormat logtime = new SimpleDateFormat("HH:mm a");

	static SimpleDateFormat logdate = new SimpleDateFormat("dd/MM/yy");

	static Date dates = new Date();

	static int retrycount = 1;

	static int other_cnt = 0;

	static int rem_value;

	static String letter_definer = "";

	private JFrame frame;

	static JTextField Ticketfield;

	static JTextField Otherfield;

	static JTextField Workfield;

	static JTextField statusfield;

	static JTextField pathfield;

	static JTextField messagefield;

	static Overall_optimus_c5 overall = new Overall_optimus_c5();

	static Error_displayer err_display = new Error_displayer();

	public static void montana_writing(String local_copy, List<String> tickets2, List hrefvalues2, List<String> FI_ID,
			List<String> typo_def, Map mymap2, List ahreftag2, List<String> created_status2,
			List<String> assignee_name2, List<String> script_name2, List<String> add_known_isue,
			List<Integer> montana_grade, List<String> montana_errcode, List<String> montana_team_memb,
			String error_code, String manager, String developer) throws Exception {
		String datenow = "";
		String filename = "C:\\Tickets\\Assignment ";

		SimpleDateFormat sdf = new SimpleDateFormat("MMM-dd");
		SimpleDateFormat sdf1 = new SimpleDateFormat("HH:ss");
		Date date1 = new Date();
		Date date2 = new Date();
		datenow = sdf.format(date1);
		String secnow = sdf1.format(date2);
		letter_definer = Overall_optimus_c5.filenamechanger(filename, datenow, manager, developer);
		String a_name = "avarghese";
		String g_name = "gmuthusamy1";
		String pd_name = "panbalagam";
		String np_name = "vbalakrishn";
		String s_name = "skatamreddy";
		String a_ori_name = "Asha";
		String g_ori_name = "Ganga";
		String pd_ori_name = "PriyaDharshini";
		String np_ori_name = "Vignesh";
		String s_ori_name = "Suganya";
		int acount = 0;
		int gcount = 0;
		int pdcount = 0;
		int npcount = 0;
		int scount = 0;
		String successmessage = "FILE CREATED SUCCESSFULLY";
		String default_name = "System";
		int ticket_size = tickets2.size();
		int errcode_size = montana_errcode.size();
		int cr_status = created_status2.size();
		int assignestatus_size = assignee_name2.size();
		int grade_size = montana_grade.size();
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
				xSSFCellStyle7.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
				xSSFCellStyle7.setFillPattern((short) 1);
				xSSFCell.setCellStyle((CellStyle) xSSFCellStyle7);
				font.setBoldweight((short) 700);
				xSSFCellStyle7.setFont((Font) font);
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
			if (ticket_size == montana_errcode.size() && ticket_size == created_status2.size()
					&& ticket_size == assignee_name2.size() && ticket_size == montana_grade.size()) {
				for (int i = 0; i < tickets2.size()-1; i++) {
					row = sheet.createRow(i + 1);
					for (int j = 0; j < 10; j++) {
						XSSFCell xSSFCell = row.createCell(j);
						if (xSSFCell.getColumnIndex() == 0) {
							xSSFCell.setCellValue(tickets2.get(i));
							xSSFCell.setCellStyle((CellStyle) style);
						}
						if (xSSFCell.getColumnIndex() == 1) {
							xSSFCell.setCellValue(script_name2.get(i));
							xSSFCell.setCellStyle((CellStyle) style);
						}
						if (xSSFCell.getColumnIndex() == 2) {
							xSSFCell.setCellValue(montana_errcode.get(i));
							xSSFCell.setCellStyle((CellStyle) style);
						}
						if (xSSFCell.getColumnIndex() == 3) {
							xSSFCell.setCellValue(FI_ID.get(i));
							xSSFCell.setCellStyle((CellStyle) style);
						}

						if (xSSFCell.getColumnIndex() == 4) {
							xSSFCell.setCellValue(created_status2.get(i));
							xSSFCell.setCellStyle((CellStyle) style);
						}
						if (xSSFCell.getColumnIndex() == 5) {
							xSSFCell.setCellValue(((Integer) montana_grade.get(i)).intValue());
							xSSFCell.setCellStyle((CellStyle) style);
						}
						if (xSSFCell.getColumnIndex() == 6) {
							xSSFCell.setCellValue(assignee_name2.get(i));
							xSSFCell.setCellStyle((CellStyle) style);
						}
						if (xSSFCell.getColumnIndex() == 6)
							if (StringUtils.containsIgnoreCase(assignee_name2.get(i), np_name)) {
								npcount++;
								xSSFCell.setCellValue(np_ori_name);
								xSSFCell.setCellStyle((CellStyle) style);
							} else if (StringUtils.containsIgnoreCase(assignee_name2.get(i), pd_name)) {
								pdcount++;
								xSSFCell.setCellValue(pd_ori_name);
								xSSFCell.setCellStyle((CellStyle) style);
							} else if (StringUtils.containsIgnoreCase(assignee_name2.get(i), g_name)) {
								gcount++;
								xSSFCell.setCellValue(g_ori_name);
								xSSFCell.setCellStyle((CellStyle) style);
							} else if (StringUtils.containsIgnoreCase(assignee_name2.get(i), s_name)) {
								scount++;
								xSSFCell.setCellValue(s_ori_name);
								xSSFCell.setCellStyle((CellStyle) style);
							} else if (StringUtils.containsIgnoreCase(created_status2.get(i), "In progress")
									&& StringUtils.containsIgnoreCase(assignee_name2.get(i), g_name)) {
								gcount++;
								xSSFCell.setCellValue(g_ori_name);
								xSSFCell.setCellStyle((CellStyle) style);
							} else if (StringUtils.containsIgnoreCase(created_status2.get(i), "In progress")
									&& StringUtils.containsIgnoreCase(assignee_name2.get(i), a_name)) {
								acount++;
								xSSFCell.setCellValue(a_ori_name);
								xSSFCell.setCellStyle((CellStyle) style);
							} else if (StringUtils.containsIgnoreCase(created_status2.get(i), "In progress")
									&& StringUtils.containsIgnoreCase(assignee_name2.get(i), np_name)) {
								npcount++;
								xSSFCell.setCellValue(np_ori_name);
								xSSFCell.setCellStyle((CellStyle) style);
							} else if (StringUtils.containsIgnoreCase(created_status2.get(i), "In progress")
									&& StringUtils.containsIgnoreCase(assignee_name2.get(i), s_name)) {
								scount++;
								xSSFCell.setCellValue(np_ori_name);
								xSSFCell.setCellStyle((CellStyle) style);
							} else if (StringUtils.containsIgnoreCase(assignee_name2.get(i), "System")) {
								xSSFCell.setCellValue(montana_team_memb.get(i));
								xSSFCell.setCellStyle((CellStyle) style);
							}
						if (Overall_optimus_c5.mark && xSSFCell.getColumnIndex() == 8) {
							int length = ((String) add_known_isue.get(i)).length() - 1;
							String overalls = ((String) add_known_isue.get(i)).substring(0,
									((String) add_known_isue.get(i)).length() - 1);
							xSSFCell.setCellValue(overalls);
							xSSFCell.setCellStyle((CellStyle) style);
						}
						if (xSSFCell.getColumnIndex() == 7)
							if (!StringUtils.equalsIgnoreCase(assignee_name2.get(i), a_name)
									&& !StringUtils.equalsIgnoreCase(assignee_name2.get(i), s_name)
									&& !StringUtils.equalsIgnoreCase(assignee_name2.get(i), np_name)
									&& !StringUtils.equalsIgnoreCase(assignee_name2.get(i), g_name)
									&& !StringUtils.equalsIgnoreCase(assignee_name2.get(i), pd_name)
									&& !StringUtils.equalsIgnoreCase(assignee_name2.get(i), default_name)) {
								other_cnt++;
								xSSFCell.setCellValue("Assigned By IL Team");
								xSSFCell.setCellStyle((CellStyle) style);
							}

							else if (StringUtils.containsIgnoreCase(script_name2.get(i), "Hint")) {
								other_cnt++;
								xSSFCell.setCellValue("Hint Script");
								xSSFCell.setCellStyle((CellStyle) style);
							} else {
								xSSFCell.setCellStyle((CellStyle) style);
							}
						if (overall.mark == false) {
							if (xSSFCell.getColumnIndex() == 8) {
								xSSFCell.setCellValue(Overall_optimus_c5.typo_def.get(i));
								xSSFCell.setCellStyle((CellStyle) style);
							}
						}
						if (overall.mark == true) {
							if (xSSFCell.getColumnIndex() == 9) {
								xSSFCell.setCellValue(Overall_optimus_c5.typo_def.get(i));
								xSSFCell.setCellStyle((CellStyle) style);
							}
						}
					}
				}

				row = sheet.createRow(tickets2.size() + 5);
				XSSFCell xSSFCell1 = row.createCell(0);
				xSSFCell1.setCellStyle((CellStyle) style);
				xSSFCell1.setCellValue("Total Tickets : ");
				xSSFCellStyle1.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
				xSSFCellStyle1.setFillPattern((short) 1);
				xSSFCell1.setCellStyle((CellStyle) xSSFCellStyle1);
				font.setBoldweight((short) 700);
				xSSFCellStyle1.setFont((Font) font);
				xSSFCell1.setCellStyle((CellStyle) style);
				xSSFCell1.setCellStyle((CellStyle) xSSFCellStyle1);
				XSSFCell xSSFCell2 = row.createCell(1);
				xSSFCell2.setCellValue(tickets2.size());
				xSSFCell2.setCellStyle((CellStyle) style);
				String value1 = String.valueOf(tickets2.size());
				Overall_optimus_c5.Ticketfield.setText(value1);
				Overall_optimus_c5.Ticketfield.setBackground(Color.lightGray);
				row = sheet.createRow(tickets2.size() + 6);
				xSSFCell1 = row.createCell(0);
				xSSFCell1.setCellStyle((CellStyle) style);
				xSSFCell1.setCellValue("Others : ");
				xSSFCellStyle2.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
				xSSFCellStyle2.setFillPattern((short) 1);
				xSSFCell1.setCellStyle((CellStyle) xSSFCellStyle1);
				font.setBoldweight((short) 700);
				xSSFCellStyle2.setFont((Font) font);
				xSSFCell1.setCellStyle((CellStyle) style);
				xSSFCell1.setCellStyle((CellStyle) xSSFCellStyle2);
				xSSFCell2 = row.createCell(1);
				xSSFCell2.setCellValue(other_cnt);
				xSSFCell2.setCellStyle((CellStyle) style);
				String value2 = String.valueOf(other_cnt);
				Overall_optimus_c5.Otherfield.setText(value2);
				Overall_optimus_c5.Otherfield.setBackground(Color.YELLOW);
				row = sheet.createRow(tickets2.size() + 7);
				xSSFCell1 = row.createCell(0);
				xSSFCell1.setCellStyle((CellStyle) style);
				rem_value = ticket_size - other_cnt;
				xSSFCell1.setCellValue("Need to Work : ");
				xSSFCellStyle3.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
				xSSFCellStyle3.setFillPattern((short) 1);
				xSSFCell1.setCellStyle((CellStyle) xSSFCellStyle1);
				font.setBoldweight((short) 700);
				xSSFCellStyle3.setFont((Font) font);
				xSSFCell1.setCellStyle((CellStyle) style);
				xSSFCell1.setCellStyle((CellStyle) xSSFCellStyle3);
				String value3 = String.valueOf(rem_value);
				Overall_optimus_c5.Workfield.setText(value3);
				Overall_optimus_c5.Workfield.setBackground(Color.green);
				xSSFCell2 = row.createCell(1);
				xSSFCell2.setCellValue(rem_value);
				xSSFCell2.setCellStyle((CellStyle) style);
				row = sheet.createRow(tickets2.size() + 9);
				xSSFCell1 = row.createCell(0);
				xSSFCell1.setCellStyle((CellStyle) style);
				xSSFCell1.setCellValue("Manager : ");
				xSSFCellStyle1.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
				xSSFCellStyle1.setFillPattern((short) 1);
				xSSFCell1.setCellStyle((CellStyle) xSSFCellStyle1);
				font.setBoldweight((short) 700);
				xSSFCellStyle1.setFont((Font) font);
				xSSFCell1.setCellStyle((CellStyle) style);
				xSSFCell1.setCellStyle((CellStyle) xSSFCellStyle1);
				xSSFCell2 = row.createCell(1);
				xSSFCell2.setCellValue(manager);
				xSSFCell2.setCellStyle((CellStyle) style);
				row = sheet.createRow(tickets2.size() + 10);
				xSSFCell1 = row.createCell(0);
				xSSFCell1.setCellStyle((CellStyle) style);
				xSSFCell1.setCellValue("Developer : ");
				xSSFCellStyle1.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
				xSSFCellStyle1.setFillPattern((short) 1);
				xSSFCell1.setCellStyle((CellStyle) xSSFCellStyle1);
				font.setBoldweight((short) 700);
				xSSFCellStyle1.setFont((Font) font);
				xSSFCell1.setCellStyle((CellStyle) style);
				xSSFCell1.setCellStyle((CellStyle) xSSFCellStyle1);
				xSSFCell2 = row.createCell(1);
				xSSFCell2.setCellValue(developer);
				xSSFCell2.setCellStyle((CellStyle) style);
				for (int allign = 0; allign < total; allign++) {
					sheet.autoSizeColumn(allign);
				}
				FileOutputStream out = new FileOutputStream(new File(letter_definer));
				book.write(out);
				Overall_optimus_c5.backup_creater(letter_definer);
				System.out.println("Created Path : " + letter_definer);
				String displayname = StringUtils.substringBefore(letter_definer, ".");
				Overall_optimus_c5.pathfield.setText(displayname);
				Overall_optimus_c5.pathfield.setBackground(Color.GREEN);
				System.out.println("");
				System.out.println("!!!!...............FILE CREATED SUCCESSFULLY.................!!!");
				Overall_optimus_c5.messagefield.setText(" !...FILE CREATED SUCCESSFULLY....!");
				Overall_optimus_c5.messagefield.setBackground(Color.GREEN);
				log_files(local_copy, tickets2, hrefvalues2, mymap2, ahreftag2, created_status2, assignee_name2,
						script_name2, montana_grade, montana_errcode, montana_team_memb, error_code, manager,
						developer);
			} else {
				String err_names = "Sizeerror";
				err_display.errorcatcher(err_names);
				System.out.println(" # Sizes are not equal............Please check");
				System.out.println("");
				size_err_detector(tickets2, created_status2, assignee_name2, script_name2, montana_grade);
			}
		} catch (FileNotFoundException e) {
			Overall_optimus_c5.messagefield.setText("File is already Open..Please close it");
			Overall_optimus_c5.messagefield.setBackground(Color.RED);
			System.out.println("ERROR ::--->" + e);
			System.out.println(
					"Message ::---->The File is already in Open state.....Please close it and run the program");
		}
	}

	public static void file_opener() throws IOException {
		Desktop desk = Desktop.getDesktop();
		File fileOpen = new File(letter_definer);
		if (letter_definer.isEmpty()) {
			overall.file_opener();
		}
		desk.open(fileOpen);
	}

	public static void log_files(String local_copy, List<String> tickets2, List hrefvalues2, Map mymap2, List ahreftag2,
			List<String> created_status2, List<String> assignee_name2, List<String> script_name2,
			List<Integer> montana_grade, List<String> montana_errcode, List<String> montana_team_memb,
			String error_code, String manager, String developer) throws IOException {
		Date date = new Date();
		String logging_time = logtime.format(Long.valueOf(date.getTime()));
		String logging_date = logdate.format(date);
		try {
			String username;
			File directory = new File("C:\\LogDirectory");
			if (!directory.exists())
				directory.mkdir();
			InetAddress localhost = InetAddress.getLocalHost();
			String conv_address = String.valueOf(InetAddress.getLocalHost());
			if (StringUtils.containsIgnoreCase(conv_address, "PCIN473750")) {
				username = "Karthikeyan";
			} else {
				username = "Unknown";
			}
			String ASSET_ID = StringUtils.substringBefore(conv_address, "/");
			String ip_valid_address = StringUtils.substringAfter(conv_address, "/");
			String datesnow = String.valueOf(sdf.format(dates));
			File logfile = new File("C:\\LogDirectory\\" + datesnow + ".txt");
			if (!logfile.exists())
				logfile.createNewFile();
			PrintWriter writer = new PrintWriter(logfile, "UTF-8");
			writer.print("PC Number :" + ASSET_ID);
			writer.print(System.lineSeparator());
			writer.print("UserName :" + username);
			writer.print(System.lineSeparator());
			writer.print("Ip_Address :" + ip_valid_address);
			writer.print(System.lineSeparator());
			writer.println("Date of Logging :" + logging_date);
			writer.print(System.lineSeparator());
			writer.print("Time of Logging :" + logging_time);
			writer.print(System.lineSeparator());
			writer.print(System.lineSeparator());
			writer.println("------------------------------------------------");
			writer.print("Karthikeyan Printed Logs");
			writer.print(System.lineSeparator());
			writer.println("------------------------------------------------");
			writer.print(System.lineSeparator());
			writer.close();
		} catch (IOException e) {
			System.out.println("# DETECTED FILE OPEN CORRUPTION");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void size_err_detector(List tickets, List Created_status, List assignee_name, List script_name,
			List Grade) {
		System.out.println("Total tickets size : " + tickets.size());
		System.out.println("Script Name size : " + script_name.size());
		System.out.println("Created Status size : " + Created_status.size());
		System.out.println("Assignee Name size : " + assignee_name.size());
		System.out.println("Grade size : " + Grade.size());
		Overall_optimus_c5.pathfield.setText("Something went Wrong");
		Overall_optimus_c5.pathfield.setBackground(Color.RED);
		Overall_optimus_c5.messagefield.setText("Optimus has some Change");
		Overall_optimus_c5.messagefield.setBackground(Color.RED);
	}

	public static List<String> Head_call() {
		headings.add("TICKET CREATED");
		headings.add("SCRIPT NAME");
		headings.add("ERROR CODE");
		headings.add("FI ID");
		headings.add("STATUS");
		headings.add("PRIORITY");
		headings.add("ASSIGNED TO");
		headings.add("COMMENTS");
		if (Overall_optimus_c5.mark) {
			headings.add("Known Issues");
		}
		headings.add("DETAILS");
		return headings;
	}
}
