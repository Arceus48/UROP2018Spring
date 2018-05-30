package sparqlGenerator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpGet {
	//Helper method to send a HTTPGet request
	public static String sendGet(String urlToRead) throws Exception{
		 StringBuilder result = new StringBuilder();
	     URL url = new URL(urlToRead);
	     HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	     conn.setRequestMethod("GET");
	     BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	     String line;
	     while ((line = rd.readLine()) != null) {
	        result.append(line);
	     }
	     rd.close();
	     return result.toString();
	}
	
	//Send a HTTPGet request to wikidata query service and get back info.
	public static String runQuery(String url) {
		String iri = "";
		try {
			iri = URLEncoder.encode(url, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		String str = "https://query.wikidata.org/sparql?format=json&" + "query=" + iri;
		String target = "";
		try {
			target = sendGet(str);
		} catch (Exception e) {
			
		}
		
		return target;
	}
	
	//Search entities and get back a JSON string.
	public static String searchEntities(String source) {
		String str = "";
		String target = "";
		str = String.format("https://www.wikidata.org/w/api.php?action=wbsearchentities&search=%s&language=en&format=json", source);
		try {
			target = sendGet(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return target;
	}
	
	public static void main(String args[]) {
//      Example 1:
//		String str = "";
//		String url = "";
//		url = SparqlGenerator.getDescription("Q89");
//		str = runQuery(url);
//		System.out.println(str);
		
//		Example 2:
//		ArrayList<String> a = new ArrayList<String>();
//		a.add("P361");
//		a.add("P131");
//		String url = SparqlGenerator.searchSimilarEntities("Q956",a);
//		String str = "";
//		try {
//			str = runQuery(url);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
	}
}
