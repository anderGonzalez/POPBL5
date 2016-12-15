package simulator;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

import domain.dao.DAOLane;
import domain.model.Plane;

public class ArrivingPlane extends PlaneThread {

	public ArrivingPlane(Plane plane, AirportController controller, AtomicInteger activePlanesNum) {
		this.plane = plane;
		semControllerPermision = new Semaphore(0, true);
		this.controller = controller;
		this.activePlanes = activePlanesNum;
	}

	@Override
	public void run() {
		System.out.println("Plane " + plane.getSerial() + " ARRIVING");
		moveToAirport();
		System.out.println("Plane " + plane.getSerial() + " ASK PERMISSION TO ARRIVE");
		if (!controller.askPermission(this)) {
			Thread waitingThread = new Thread(new MovePlaneInCircles(plane));
			// run?
			try {
				semControllerPermision.acquire();
				waitingThread.interrupt();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		System.out.println("Plane " + plane.getSerial() + " LANDED");
		// goToDestine();
		landPlane();
		// set plane status OnAirport eta NeedRevision
	}

	private void landPlane() {
		lane.setStatus(true);
		try {
			controller.mutex.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DAOLane.updateLane(lane);
		controller.getMutex().release();
		activePlanes.decrementAndGet();

	}

	private void moveToAirport() {
		// TODO Auto-generated method stub

	}

}