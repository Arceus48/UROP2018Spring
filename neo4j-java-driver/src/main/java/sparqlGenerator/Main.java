package sparqlGenerator;

import java.util.ArrayList;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Main {
	private static String curID = "";
	private static ConnToNeo4j conn = null;
	private static Scanner sc = null;
	public static void stage1() {
		System.out.println("Do you want to exit?(1/2)");
		if(sc.nextInt() == 1) {
			sc.close();
			System.exit(0);
		}
		sc.nextLine();
		System.out.println("Which entity do you want to search for? ");
		String source = sc.nextLine();
		String str = HttpGet.searchEntities(source);
		if(!str.contains("search-continue")) {
			System.out.println("This entity does not exist.");
			stage1();
		}
		else {
			JSONObject jsonObject = null;
			try {
				jsonObject = new JSONObject(str);
				int size = jsonObject.getInt("search-continue");
				JSONArray jsonArray = jsonObject.getJSONArray("search");
				System.out.println("All the objects found:");
				for(int i=0;i<size;i++) {
					JSONObject entity = jsonArray.getJSONObject(i);
					
					System.out.println("Entity " + i);
					if(entity.has("id"))
						System.out.println("ID: " + entity.getString("id"));
					if(entity.has("label"))
						System.out.println("Label: " + entity.getString("label"));
					if(entity.has("description"))
						System.out.println("Description: " + entity.getString("description"));
					System.out.println("");			
				}
				System.out.println("Please input the ID of the entity you are interested in: ");
				curID = sc.next();
				stage2();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			
		}
	}
	
	public static void stage2() {
		if(conn.exist(curID)) {
			System.out.println("The entity's information exists in the database!");
		}
		else {
			System.out.println("The entity's information does not exist in the database.");
			System.out.println("Preparing the data...");
			conn.writeIn(curID);
			System.out.println("Finished!");
		}
		System.out.println("Do you want to get back to the beginning or proceed with this entity?(1/2)");
		int flag = sc.nextInt();
		sc.nextLine();
		if(flag == 1)
			stage1();
		else
			stage3();
	}
	
	public static void stage3() {
		System.out.println("All possible relations with other nodes are listed below:");
		conn.getProps(curID);
		System.out.println("Do you want to see related nodes of this entity or search for similar nodes or get back?(1/2/3)");
		int flag = sc.nextInt();
		sc.nextLine();
		switch(flag) {
		case 1:stage4();break;
		case 2:stage5();break;
		case 3:stage1();break;
		}
	}
	
	public static void stage4() {
		System.out.println("In which relation would you want to get the related nodes? Please input the ID(e.g. P27)");
		String pid = sc.next();
		conn.getObjects(curID, pid);
		System.out.println("Do you want to learn more about this entity or get back?(1/2)");
		int flag = sc.nextInt();
		sc.nextLine();
		if(flag == 1) {
			System.out.println("Please input the entity id:");
			curID = sc.next();
			stage2();
		}
		else
			stage3();
	}
	
	public static void stage5() {
		System.out.println("Please input the number of props:");
		int size = sc.nextInt();
		sc.nextLine();
		System.out.println("Please input the relation ID you want the similar items have: (one in one line)");
		ArrayList<String> a = new ArrayList<String>();
		for(int i=0;i<size;i++) {
			a.add(sc.next());
		}
		System.out.println("Searching...");
		String sparql = SparqlGenerator.searchSimilarEntities(curID, a);
		conn.getSimilarObjects(curID, sparql);
		
		System.out.println("Do you want to return or begin from the very beginning or learn more about these nodes?(1/2/3)");
		int flag = sc.nextInt();
		sc.nextLine();
		if(flag == 2)
			stage1();
		else if(flag == 3) {
			System.out.println("Please input the id:");
			curID = sc.nextLine();
			stage2();
		}
		else
			stage3();
	}
	
	public static void main(String args[]) {
		conn = new ConnToNeo4j();
		conn.registerShutdownHook();
		sc = new Scanner(System.in);
		System.out.println("********************");
		System.out.println("WIKIDATA QUERY TOOL USING NEO4J");
		System.out.println("Functions: ");
		System.out.println("\tSearch entities and get related information.");
		System.out.println("\tSearch related entities and get information");
		System.out.println("\tGet similar entities.");
		System.out.println("********************");
		stage1();
	}
}
