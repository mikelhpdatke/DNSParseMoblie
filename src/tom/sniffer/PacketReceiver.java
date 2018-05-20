package tom.sniffer;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

public class PacketReceiver {
    public static void main(String[] args) throws IOException{
    	//
    	
    	MongoClient mongo = new MongoClient("localhost", 27017);
    	MongoDatabase database = mongo.getDatabase("DNSParser");
    	// database.createCollection("sampleCollection");
    	//System.out.println("Collection created successfully");
    	MongoCollection<Document> collection = database.getCollection("Collection_BlackIP");
    	MongoCollection<Document> FoundIP = database.getCollection("Collection_FoundIP");
    	//MongoCollection<Document> indexFoundID = database.getCollection("Collection_indexFoundID");
  
    	//
    	InputStreamReader isReader = new InputStreamReader(System.in);
		BufferedReader bufReader = new BufferedReader(isReader);
		File log = new File("/home/luong/JavaProjects/CSDL/src/tom/sniffer/log");
		//System.out.println(log.exists());
		//FileWriter  fo= new FileWriter(System.out);
		//JSONObject jo= new JSONObject("{Hello World:123}");
		//System.out.println(jo.toString()+"WTF");
		//List<JSONObject> mlist = new ArrayList<JSONObject>();
		while(true){
    		try {
        		String inputStr = null;
        		if((inputStr=bufReader.readLine()) != null) {
        			if (inputStr.contains("ip.dst")) {
        				inputStr = inputStr.replaceAll(" ", "");
        				inputStr = inputStr.replaceAll(",", "");
        				//System.out.println(inputStr+"clgtWTFFFFFFFFFFF");
        				String[] keyValue = inputStr.split("\":\"");
        				//System.out.println(keyValue[1]);
        				String ip = keyValue[1].replaceAll("\"", "");
        				//System.out.println(ip);
        				//long count = collection.count(Filters.text(ip));
        				Document myDoc = collection.find(Filters.eq("ip", ip)).first();
        				//System.out.println(count+"???");
        				if (myDoc == null) {
        					//System.out.println(ip+"NO");
        				}
            			if (myDoc != null) {
            				System.out.println(ip);
            				Document checkDoc = null;
            				checkDoc = FoundIP.find(Filters.eq("ip", ip)).first();
            				
            				if (checkDoc == null) {
	            				ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
	            				int maxFoundID = (int) FoundIP.count();
	            				String json = ow.writeValueAsString(new BlackIP(++maxFoundID, ip));
	            				FoundIP.insertOne(Document.parse(json));
	            				// update index
	            				//indexFoundID.updateOne(Filters.eq("maxFoundID", maxFoundID - 1), 
	            				//		Updates.set("maxFoundID", maxFoundID)); 
	            				//FoundIP.insertOne(new Document("ip",ip));
	            				System.out.println("ADDED " + ip + " to FOUNDIP Colecction!!");
            				}
            			}
        			}
        		}
        		else {
            		System.out.println("______________________FINISHED__________________________________");
            		break;
        		}
    		}
    		catch (Exception e) {
    			e.printStackTrace();
    		}
		}
		//System.out.println(mlist.size()+"What the fuk");
    }
}
