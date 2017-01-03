package simulator;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

import domain.dao.DAOLane;
import domain.model.Plane;
import domain.model.users.Admin;
import helpers.MD5;
import notification.Notification;

public class ArrivingPlane extends PlaneThread {

	private static final String ADMIN = new Admin().getClass().getSimpleName();

	public ArrivingPlane(Plane plane, AirportController controller, AtomicInteger activePlanesNum) {
		this.plane = plane;
		semControllerPermision = new Semaphore(0, true);
		this.controller = controller;
		this.activePlanes = activePlanesNum;
		this.mode = ARRIVING;
	}

	@Override
	public void run() {
		Notification.sendNotification(MD5.encrypt(ADMIN), "Plane " + plane.getSerial() + " ARRIVING");
		moveToAirport();
		Notification.sendNotification(MD5.encrypt(ADMIN),
				"Plane " + plane.getSerial() + " ASK PERMISSION TO ARRIVE");

		if (!controller.askPermission(this)) {
			Thread waitingThread = new Thread(new MovePlaneInCircles(plane));
			// run?
			try {
				semControllerPermision.acquire();
				waitingThread.interrupt();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				System.out.println("Interrupted plane");
				e.printStackTrace();
			}
		}

		Notification.sendNotification(MD5.encrypt(ADMIN), "Plane " + plane.getSerial() + " LANDED");

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
			Thread.currentThread().interrupt();
			System.out.println("Interrupted plane");
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
