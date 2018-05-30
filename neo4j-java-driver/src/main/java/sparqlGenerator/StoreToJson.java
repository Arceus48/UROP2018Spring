package sparqlGenerator;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class StoreToJson {
	private static int index = 0;
	public static String saveToFile(String content) {
		Date time = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY:MM:DD:HH:mm:ss");
		String dateString = sdf.format(time);
		String[] str = dateString.split(":");
		String n = "";
		for(String s : str) {
			n += s;
		}
		String name = n + ".json";
		index++;
		System.out.println(index);
		File file = new File(name);
		PrintWriter pw = null;
		try {
			 pw = new PrintWriter(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pw.write(content);
		pw.close();
		
		
		return name;
	}
}
