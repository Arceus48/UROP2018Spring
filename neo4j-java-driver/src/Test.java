import java.util.Map;
import java.util.Map.Entry;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;
import org.neo4j.driver.v1.TransactionWork;
public class Test {
	    private final Driver driver;

	    public Test( String uri, String user, String password )
	    {
	        driver = GraphDatabase.driver( uri, AuthTokens.basic( user, password ) );
	    }

	    public void close() throws Exception
	    {
	        driver.close();
	    }
	    
	    public static void main(String args[]) {
	    	Test tt = new Test("bolt://localhost:7687", "neo4j", "8660841157pyjz");
//	    	Session session = tt.driver.session();
//	    	session.run("MERGE (n:Person{name:'hehe'})");
//	    	session.run( "MERGE (a:Book {name:'helloworld'})" );
//	    	Result result = session.run("Match(n:Person{name:'hehe'}) match(p:Book{name:'helloworld'}) Create (n)-[:Read]->(p) return (n)-[]-(p)");
//	    	session.writeTransaction(new TransactionWork<String>(){
//	    		public String execute(Transaction tx) {
//	    			
//	    		}
//	    	});
	//    	session.close();
	    	try(Session session = tt.driver.session()){
	    		StatementResult result = session.run("match(n:Entity) where right(n.id,4) = \"Q976\"\r\n" + 
	    				"match(n)-[p]->() return p");
	    		System.out.println(result.hasNext());
	    	
	    		while(result.hasNext()) {
	    			Record record = result.next();
	    			System.out.println(record.get(0).get("id").asString() + "\n" + record.get(0).get("label").asString() + "\n" +
	    					record.get(0).get("Des").asString());
	    		}
//	    		Record record = result.next();
//	    		Map<String, Object> map = record.asMap();
//	    		System.out.println(map.size());
//	    		for(Entry<String, Object>column:map.entrySet()) {
//	    			Object o = column.getValue();
//	    			o.
//	    		}
//	    	}
	    	}
	    	try {
				tt.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	
}
