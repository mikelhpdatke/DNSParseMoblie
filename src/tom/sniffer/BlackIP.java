package tom.sniffer;

import java.io.Serializable;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;


public class BlackIP implements Serializable{
	public int id;
	public String ip;
	/*
	static int getmaxBlackID() {
		MongoClient mongo = new MongoClient("localhost", 27017);
    	MongoDatabase database = mongo.getDatabase("DNSParser");
		MongoCollection<Document> indexID = database.getCollection("Collection_indexBlackID");
		int count = (int) indexID.count();
		if (count == 0)
			indexID.insertOne(new Document("maxBlackID",0));
		Document cur = indexID.find().first();
		int maxID = cur.getInteger("maxBlackID");
		return maxID;
	}
	
	static int getmaxFoundID() {
		MongoClient mongo = new MongoClient("localhost", 27017);
    	MongoDatabase database = mongo.getDatabase("DNSParser");
		MongoCollection<Document> indexFoundID = database.getCollection("Collection_indexFoundID");
		int count = (int) indexFoundID.count();
		if (count == 0)
			indexFoundID.insertOne(new Document("maxFoundID",0));
		Document cur = indexFoundID.find().first();
		int maxID = cur.getInteger("maxFoundID");
		return maxID;
	}
	*/
	public BlackIP(int id, String ip) {
		super();
		this.id = id;
		this.ip = ip;
	}
	
}
