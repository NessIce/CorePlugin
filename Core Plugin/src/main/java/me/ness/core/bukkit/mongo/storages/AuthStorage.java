package me.ness.core.bukkit.mongo.storages;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import lombok.experimental.UtilityClass;
import me.ness.core.bukkit.account.AccountData;
import me.ness.core.bukkit.mongo.MongoStorage;
import org.bson.Document;

@UtilityClass
public class AuthStorage {

    public MongoCollection<Document> collection = MongoStorage.database.getCollection("auths");

    public AccountData getAuth(String uniqueId){
        Document document = collection.find(Filters.eq("uniqueId", uniqueId)).first();

        return document!=null?
                new Gson().fromJson(document.getString("data"), AccountData.class):
                new AccountData(uniqueId);
    }

    public boolean isRegistered(String uniqueId){
        Document document = collection.find(Filters.eq("uniqueId", uniqueId)).first();
        return document!=null;
    }

    public void setAuth(String uniqueId, String password) {
        Document document = new Document();
        document.put("uniqueId", uniqueId);
        document.put("password", password);
        document.put("email", "");

        collection.replaceOne(Filters.eq("uniqueId", uniqueId), document, new UpdateOptions().upsert(true));
    }
}
