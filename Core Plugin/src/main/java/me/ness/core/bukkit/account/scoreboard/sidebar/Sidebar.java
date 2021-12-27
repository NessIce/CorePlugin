package me.ness.core.bukkit.account.scoreboard.sidebar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Sidebar {

    private List<String> title;
    private List<VirtualLine> lines;

    public Sidebar(){
        title = new ArrayList<>();
        lines = new ArrayList<>();
    }

    public void setTitle(String... title){
        this.title = new ArrayList<>(Arrays.asList(title));
    }

    public void setLines(List<VirtualLine> lines) {
        this.lines = lines;
    }

    public List<String> getTitle() {
        return title;
    }

    public List<VirtualLine> getLines() {
        return lines;
    }

    public List<VirtualLine> getReverseLines(){
        List<VirtualLine> linesReverse = new ArrayList<>(lines);
        Collections.reverse(linesReverse);
        return linesReverse;
    }


    public void addLine(String line, Object value){
        this.lines.add(new VirtualLine(line, value));
    }

    public void addLine(String line){
        this.lines.add(new VirtualLine(line, null));
    }
}
