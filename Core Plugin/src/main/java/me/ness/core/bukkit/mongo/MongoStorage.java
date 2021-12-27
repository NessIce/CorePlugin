package me.ness.core.bukkit.mongo;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.BsonDocument;
import org.bson.BsonInt64;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bukkit.Bukkit;

public class MongoStorage {

    public static MongoClient connection;
    public static MongoDatabase database;

    public static boolean connect(){
        ConnectionString connString = new ConnectionString(
                "mongodb+srv://nessice12:senhateste12@cluster0.4bl4q.mongodb.net/myFirstDatabase?retryWrites=true&w=majority"
        );

        try {
            Bukkit.getConsoleSender().sendMessage("§6[Core] §econectando-se ao banco de dados...");

            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(connString)
                    .retryWrites(true)
                    .build();

            connection = MongoClients.create(settings);

            database = connection.getDatabase("minevil");

            Bson command = new BsonDocument("ping", new BsonInt64(1));
            Document commandResult = database.runCommand(command);
            Bukkit.getConsoleSender().sendMessage("§6[Core] §aconectado ao banco de dados!");

            return true;
        } catch (MongoException me) {
            Bukkit.getConsoleSender().sendMessage("§6[Core] §afalha ao conectar ao banco de dados!");
            return false;
        }
    }
}
