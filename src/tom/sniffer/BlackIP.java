package tom.sniffer;

import java.io.Serializable;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;


public class BlackIP implements Serializable{
	public int id;
	public String ip;
	
	static int getmaxID() {
		MongoClient mongo = new MongoClient("localhost", 27017);
    	MongoDatabase database = mongo.getDatabase("BlackIP");
		MongoCollection<Document> indexID = database.getCollection("indexID");
		int count = (int) indexID.count();
		if (count == 0)
			indexID.insertOne(new Document("maxID",0));
		Document cur = indexID.find().first();
		int maxID = cur.getInteger("maxID");
		return maxID;
	}
	
	public BlackIP(int id, String ip) {
		super();
		this.id = id;
		this.ip = ip;
	}
	
}
