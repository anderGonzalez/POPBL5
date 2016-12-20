package test.dijkstra;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import domain.model.Lane;
import domain.model.Node;
import domain.model.Path;
import helpers.Dijkstra;
import helpers.Graph;

public class TestDijkstra {

	private List<Node> nodes;
	private List<Path> paths;

	@Test
	public void testExcute() {
		nodes = new ArrayList<Node>();
		paths = new ArrayList<Path>();
		for (int i = 0; i < 12; i++) {
			Node positionNode = new Node();
			positionNode.setId(i);
			positionNode.setName("Node_" + i);
			positionNode.setPositionX(i*1.0);
			positionNode.setPositionY(0);
			nodes.add(positionNode);
		}

		addLane("Edge_0", 0, 1);
		addLane("Edge_1", 0, 2);
		addLane("Edge_2", 0, 4);
		addLane("Edge_3", 2, 6);
		addLane("Edge_4", 2, 7);
		addLane("Edge_5", 3, 7);
		addLane("Edge_6", 5, 8);
		addLane("Edge_7", 8, 9);
		addLane("Edge_8", 7, 9);
		addLane("Edge_9", 4, 9);
		addLane("Edge_10", 9, 10);
		addLane("Edge_11", 1, 10);
		addLane("Edge_12", 7, 11);

		// Lets check from location Loc_1 to Loc_10
		Graph graph = new Graph(nodes, paths);
		Dijkstra dijkstra = new Dijkstra(graph);
		dijkstra.execute(nodes.get(0));
		LinkedList<Node> path = dijkstra.getPath(nodes.get(11));

		assertNotNull(path);
		assertTrue(path.size() > 0);

		for (Node vertex : path) {
			System.out.println(vertex);
		}

	}

	private void addLane(String laneId, int sourceLocNo, int destLocNo) {
		Node src = nodes.get(sourceLocNo);
		Node dst = nodes.get(destLocNo);
		
		//Honek berez lane asko euki biharko littuzke baina bueno tt, lane bakarrakin ingou probia
		Lane lane = new Lane();
		lane.setStartNode(src);
		lane.setEndNode(dst);
		
		ArrayList<Lane> laneList = new ArrayList<>();
		laneList.add(lane);
		
		Path path = new Path();
		path.setLaneList(laneList);
		path.setDistance(calculateDistance(src, dst));
		
		paths.add(path);
	}

	private double calculateDistance(Node src, Node dst) {

		return pitagor(src.getPositionX() - dst.getPositionX(), src.getPositionY() - dst.getPositionY());

	}

	private double pitagor(double x, double y) {
		return Math.sqrt((x*x)+(y*y));
	}

}