package simulator;

import java.util.ArrayList;

import domain.dao.HibernateGeneric;
import domain.model.Lane;

public class AirportController implements Runnable {
	private ArrayList<PlaneThread> activePlaneList;
	private ArrayList<Lane> freeLaneList;

	@Override
	public void run() {
		while (true) {
			if (activePlaneList.size() > 0 && (freeLaneList = HibernateGeneric.getFreeLanes()) != null) {
				PlaneThread plane = activePlaneList.get(0);
				plane.setLane(freeLaneList.get(0));
				activePlaneList.remove(plane);
				plane.givePermission();
			}
		}

	}

	public boolean askPermission(PlaneThread plane) {
		boolean ret;
		if (activePlaneList.size() == 0 && (freeLaneList = HibernateGeneric.getFreeLanes()) != null) {
			plane.setLane(freeLaneList.get(0));
			ret = true;
		} else {
			activePlaneList.add(plane);
			ret = false;
		}
		return ret;
	}

}
