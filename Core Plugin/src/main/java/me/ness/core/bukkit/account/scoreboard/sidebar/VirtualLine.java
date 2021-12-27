package me.ness.core.bukkit.account.scoreboard.sidebar;

public class VirtualLine {

    private final String line;
    private final Object value;

    public VirtualLine(String line, Object value){
        this.line = line;
        this.value = value;
    }

    public String getLine() {
        return line;
    }

    public Object getValue() {
        return value;
    }
}
