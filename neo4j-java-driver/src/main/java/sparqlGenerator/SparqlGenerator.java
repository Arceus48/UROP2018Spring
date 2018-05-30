package sparqlGenerator;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class SparqlGenerator {
	//input: root: the id of the entity
	//		 mode:traversal direction: Forward, Reverse, Undirected
	//		 lang: the language of the contents returned eg. en, fr..
	//		 prop: the association used to construct the tree e.g. P157
	//		 limit: the maximum nodes visited
	// 		 iterations: the maximum iterations 
	public static String treeGenerator(String mode, String lang, String root, String prop, String limit, String iterations) {
		String result = "";
		result = String.format(
				"SELECT ?item ?itemLabel ?linkTo{ \n" +
				"      SERVICE gas:service {\r\n" + 
				"          gas:program gas:gasClass \"com.bigdata.rdf.graph.analytics.SSSP\" ;\r\n" + 
				"                      gas:in wd:%s ;\r\n" + 
				"                      gas:traversalDirection \"%s\" ;\r\n" + 
				"                      gas:out ?item ;\r\n" + 
				"                      gas:out1 ?depth ;\r\n" + 
				"                      gas:maxIterations %s ;\r\n" + 
				"                      gas:maxVisited %s ;\r\n" + 
				"                      gas:linkType wdt:%s .\r\n" + 
				"        }" + 
				"		OPTIONAL { ?item wdt:%s ?linkTo } . \n" +
				"       SERVICE wikibase:label {bd:serviceParam wikibase:language \"%s\" }\r\n" + 
				"      }", root, mode, iterations, limit, prop, prop, lang);
		return result;
	}
	
	//input: the id of the entity
	//output: the sparql code
	public static String getDescription(String id) {
		String result = "";
		result =String.format("SELECT ?item ?itemLabel ?itemDes\r\n" + 
				"WHERE{\r\n" + 
				"  VALUES ?item {wd:%s} .\r\n" + 
				"  ?item schema:description ?itemDes .\r\n" + 
				"   SERVICE wikibase:label {bd:serviceParam wikibase:language \"en\" }\r\n" + 
				"  FILTER(LANG(?itemDes) =\"en\")\r\n" + 
				"  }", id) ;
		return result;
	}
	
	//input:the id of the entity e.g. Q956
	//output: the sparql code
	public static String getProp(String id) {
		String result = "";
		result = String.format("SELECT DISTINCT ?Property ?Label ?Description\r\n" + 
				"WHERE{\r\n" + 
				"  VALUES ?item {wd:%s} .\r\n" + 
				"  ?item ?Property ?o .\r\n" + 
				"  ?wd wikibase:directClaim ?Property .\r\n" + 
				"  ?wd schema:description ?Description .\r\n" + 
				"  ?wd rdfs:label ?Label .\r\n" + 
				"  FILTER(LANG(?Label) = \"en\") .\r\n" + 
				"  FILTER(LANG(?Description) = \"en\") .\r\n" + 
				"  }", id);
		return result;
	}
	
	//input: id: the id of the entity. e.g. Q956
	public static String getInfo(String id) {
		String result = "";
		result = String.format("SELECT DISTINCT ?item ?itemLabel ?itemDes ?p ?pLabel ?pDes ?o ?oLabel ?oDes\r\n" + 
				"WHERE{\r\n" + 
				"  VALUES ?item {wd:%s} .\r\n" + 
				"  ?item ?a ?o .\r\n" + 
				"  ?item schema:description ?itemDes .\r\n" + 
				"  ?o schema:description ?oDes .\r\n" + 
				"  ?p wikibase:directClaim ?a .\r\n" + 
				"  ?p schema:description ?pDes .\r\n" + 
				"  FILTER(LANG(?pDes) = \"en\") .\r\n" + 
				"  FILTER(LANG(?oDes) = \"en\")\r\n" + 
				"  FILTER(LANG(?itemDes) = \"en\") .\r\n" + 
				"  SERVICE wikibase:label {bd:serviceParam wikibase:language \"en\" }\r\n" + 
				"  }", id);
		return result;
	}
	
	//input: id: the id of the entity. e.g. Q956
	//		 props: the arraylist containing the association ids.
	public static String searchSimilarEntities(String id, ArrayList<String> props) {
		String result = "";
		result = "SELECT DISTINCT ?o ?oLabel ?oDes\r\n" + 
				"WHERE{ \n";
		int index = 0;
		for(String str : props) {
			result += String.format("wd:%s wdt:%s ?h", id, str) + index + " .\n";
			result += String.format("?o wdt:%s ?h", str) + index + " .\n";
			index++;
		}
		result += String.format("FILTER(?o != wd:%s) .\n", id);
		result += "?o schema:description ?oDes .\n" + "FILTER(LANG(?oDes) = \"en\")\r\n";
		result += "SERVICE wikibase:label {bd:serviceParam wikibase:language \"en\" }\r\n" + 
				"  }";
		return result;
	}
	
	public static String stringToUrl(String target) {
		String str = "";
		str += "https://query.wikidata.org/sparql?format=json&" + "query=";
		try {
			str += URLEncoder.encode(target, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	public static void main(String args[]) {
//		Example 1:
//		String str = SparqlGenerator.treeGenerator("Reverse", "en", "Q11563", "P279", "1000", "5");
		
//		Example 2:
//		String str = getInfo("Q89");
		
//		Example 3:
//		ArrayList<String> a = new ArrayList<String>();
//		a.add("P361");
//		a.add("P131");
//		String str = searchSimilarEntities("Q956", a);
		
//		System.out.println(str);
	}


}
