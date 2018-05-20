package tom.sniffer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.bson.Document;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.sun.org.apache.bcel.internal.generic.NEW;

public class Publisher {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File file = new File("/home/luong/Documents/BlackipNgoc");
		MongoClient mongo = new MongoClient("localhost", 27017);
    	MongoDatabase database = mongo.getDatabase("DNSParser");
    	// database.createCollection("sampleCollection");
    	//System.out.println("Collection created successfully");
    	MongoCollection<Document> collection = database.getCollection("Collection_BlackIP");
    	//MongoCollection<Document> indexID = database.getCollection("Collection_indexBlackID");
		try {
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				String ip = scanner.nextLine();
				ip.replaceAll(" ", "");
				//
				/// push black ip to db
				ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
				int maxID = (int) collection.count();
				String json = ow.writeValueAsString(new BlackIP(++maxID, ip));
				collection.insertOne(Document.parse(json));
				//update index 
				//indexID.updateOne(Filters.eq("maxBlackID", maxID - 1), Updates.set("maxBlackID", maxID)); 
			}
		} 
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}	
	}
}
