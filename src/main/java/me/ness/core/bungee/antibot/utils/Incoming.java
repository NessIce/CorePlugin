package me.ness.core.bungee.antibot.utils;

public class Incoming {

    private int pps;
    private int cps;
    private int jps;

    public Incoming(int pps, int cps, int jps) {
        this.pps = pps;
        this.cps = cps;
        this.jps = jps;
    }

    public Incoming() {
        this(0, 0, 0);
    }

    public void reset() {
        pps = 0;
        cps = 0;
        jps = 0;
    }

    public void addPPS() {
        pps++;
    }

    public void addCPS() {
        cps++;
    }

    public void addJPS() {
        jps++;
    }

    public int getPPS() {
        return pps;
    }

    public int getCPS() {
        return cps;
    }

    public int getJPS() {
        return jps;
    }

	public boolean isGreater(Incoming incoming) {
		return pps >= incoming.getPPS() && cps >= incoming.getCPS() && jps >= incoming.getJPS();
	}

	public boolean hasGreater(Incoming incoming) {
		return pps >= incoming.getPPS() || cps >= incoming.getCPS() || jps >= incoming.getJPS();
	}
}
