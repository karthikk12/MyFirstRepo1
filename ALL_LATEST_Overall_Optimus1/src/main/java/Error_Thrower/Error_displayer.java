package Error_Thrower;

import java.awt.Desktop;
import java.io.File;
import java.io.PrintWriter;
import org.apache.commons.lang3.StringUtils;

public class Error_displayer {
	static Desktop desk = Desktop.getDesktop();

	public static void errorcatcher(String err_names) throws Exception {
		String key = null;
		if (StringUtils.containsIgnoreCase(err_names, "Too much Time")) {
			key = "tooMuchDelay";
		} else if (StringUtils.containsIgnoreCase(err_names, "Not_connected")) {
			key = "Not_connected";
		} else if (StringUtils.containsIgnoreCase(err_names, "Sizeerror")) {
			key = "Size_variation";
		}
		message_passer(err_names, key);
	}

	public static void message_passer(String err_names, String key) throws Exception {
		File filename = new File("C:\\LogDirectory\\error_message.txt");
		filename.createNewFile();
		if (filename.exists()) {
			print_error_display(filename, err_names, key);
		} else {
			filename.createNewFile();
		}
	}

	public static void print_error_display(File filename, String err_names, String key) throws Exception {
		PrintWriter writer = new PrintWriter(filename, "UTF-8");
		writer.println(
				"******************************************************   Hi USER      *************************************************************************");
		writer.println();
		if (StringUtils.equalsIgnoreCase(key, "Not_connected")) {
			writer.println("ERROR DETECTED : I cant reach the Optimus Server ");
			writer.println();
			writer.println("ROOT CAUSE : Please Check your Interner Connection");
			writer.close();
			desk.open(filename);
		} else if (StringUtils.equalsIgnoreCase(key, "tooMuchDelay")) {
			writer.println("ERROR DETECTED : Optimus server is Down......Please check Optimus Site");
			writer.println();
			writer.println("Root Cause : This usually happens sometimes...");
			writer.close();
			desk.open(filename);
		} else if (StringUtils.equalsIgnoreCase(key, "Size_variation")) {
			writer.println("ERROR DETECTED : Size variation error caused so that i cant fullfill your request");
			writer.println();
			writer.println("ROOT CAUSE : This happens Because of  OPTIMUS BUG in Official site.....");
			writer.println();
			writer.println("SUGGESTION : Please check optimus site and take it manally...Sorry for inconvinience");
			writer.close();
			desk.open(filename);
		}
	}
}
