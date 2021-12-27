package me.ness.core.bukkit.account.friends;

import java.util.HashMap;
import java.util.Map;

public class MineFriend {

    public String uniqueId;
    public Map<String, FriendStatus> friends;

    public MineFriend(String uniqueId){
        this.uniqueId = uniqueId;
        this.friends = new HashMap<>();
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public Map<String, FriendStatus> getFriends() {
        return friends;
    }

    public void setFriends(Map<String, FriendStatus> friends) {
        this.friends = friends;
    }

    public void addFriend(String uniqueId, FriendStatus status){
        this.friends.put(uniqueId, status);
    }

    public void removeFriend(String uniqueId){
        this.friends.remove(uniqueId);
    }

    public FriendStatus getFriend(String uniqueId){
        return this.friends.get(uniqueId);
    }

    public boolean hasFriend(String uniqueId){
        return this.friends.containsKey(uniqueId);
    }
}
