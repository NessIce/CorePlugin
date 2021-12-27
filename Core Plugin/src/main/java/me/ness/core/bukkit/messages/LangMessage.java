package me.ness.core.bukkit.messages;

import me.ness.core.bukkit.account.Account;

import java.util.HashMap;
import java.util.Map;

public class LangMessage {

    public enum Language{
        PT_BR, EN_US;
    }

    private final Map<Language, String> messages;

    public LangMessage(String PT_BR, String EN_US){
        messages = new HashMap<>();

        messages.put(Language.PT_BR, PT_BR);
        messages.put(Language.EN_US, EN_US);
    }

    public String get(Account account){
        return messages.get(account.getLanguage());
    }
}
