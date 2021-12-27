package me.ness.core.bukkit.mongo.storages;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import lombok.experimental.UtilityClass;
import me.ness.core.bukkit.account.friends.MineFriend;
import me.ness.core.bukkit.mongo.MongoStorage;
import org.bson.Document;

@UtilityClass
public class FriendStorage {

    public MongoCollection<Document> collection = MongoStorage.database.getCollection("friends");

    public MineFriend loadMineFriend(String uniqueId){
        Document document = collection.find(Filters.eq("uniqueId", uniqueId)).first();

        return document!=null?
                new Gson().fromJson(document.getString("data"), MineFriend.class):
                new MineFriend(uniqueId);
    }

    public void saveMineFriend(String uniqueId, MineFriend mineFriend) {
        Document document = new Document();
        document.put("uniqueId", uniqueId);
        document.put("data", new Gson().toJson(mineFriend));
        collection.replaceOne(Filters.eq("uniqueId", uniqueId), document, new UpdateOptions().upsert(true));
    }
}
