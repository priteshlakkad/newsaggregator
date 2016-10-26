/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import java.util.ArrayList;
import model.PublicDiscussionModel;
import org.bson.types.ObjectId;
import service.PublicPostConverter;

/**
 *
 * @author BHAVESH GOYAL
 */
public class PublicPostDAO {
        private DBCollection col;

    public PublicPostDAO(MongoClient mongo) {
        this.col = mongo.getDB("vidico").getCollection("publicpost");
    }

    public PublicDiscussionModel newPost(PublicDiscussionModel pb) {
        DBObject doc = PublicPostConverter.toDBObject(pb);
        this.col.insert(doc);
        pb.setPostId((ObjectId) doc.get("_id"));
        return pb;
    }
    
    public ArrayList<PublicDiscussionModel> showPost()
    { 
        ArrayList<PublicDiscussionModel> arpb=new ArrayList<PublicDiscussionModel>();
             
        try{
        BasicDBObject whereQuery = new BasicDBObject();
        DBObject oneDetails;
   // whereQuery.put("userId", "101");
    DBCursor cursor = this.col.find();
    while(cursor.hasNext()) {
        oneDetails=cursor.next();
        PublicDiscussionModel pb=new PublicDiscussionModel();
     pb=PublicPostConverter.fromDBObject(oneDetails);
       
        arpb.add(pb);
//        System.out.println(pb.getUsertId());
//        System.out.println(pb.getTopic());
//        System.out.println(pb.getContent());
//        System.out.println(pb.getTagsString());
    }
    
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
    return arpb;    }
     public  void updatePost(ArrayList<PublicDiscussionModel> arpb)
    { 
             
        try{
            
        
    DBCursor cursor = this.col.find();
    while(cursor.hasNext()) {
             PublicDiscussionModel pb=new PublicDiscussionModel();
             DBObject doc = PublicPostConverter.toDBObject(pb);
            BasicDBObject whereQuery = new BasicDBObject();
    whereQuery.put("userId",pb.getUsertId());
   
     this.col.update(whereQuery, doc);
        
        
    
    }
    
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
      }

}