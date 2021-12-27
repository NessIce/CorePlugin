package me.ness.core.bungee.antibot.shared.instanceables;

import me.ness.core.bungee.antibot.utils.Incoming;

public class Threshold {

	private final Incoming incoming;

	private final boolean oneMatch;

	public Threshold(Incoming incoming, boolean oneMeeting) {
		this.incoming = incoming;
		this.oneMatch = oneMeeting;
	}

	public boolean meet(Incoming ...incoming1) {
		if (oneMatch) {
			for (Incoming incoming2 : incoming1) {
				if (incoming2.hasGreater(incoming)) {
					return true;
				}
			}
		} else {
			for (Incoming incoming2 : incoming1) {
				if (incoming2.isGreater(incoming)) {
					return true;
				}
			}
		}

		return false;
	}
}
