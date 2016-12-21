package helpers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import domain.model.Node;
import domain.model.Path;

public class Dijkstra {

	public static final boolean ARRIVAL_MODE = true;
	public static final boolean DEPARTURE_MODE = false;
	private final List<Path> paths;
	private Set<Node> settledNodes;
	private Set<Node> unSettledNodes;
	private Map<Node, Node> predecessors;
	private Map<Node, Double> distance;
	private boolean executionMode;

	public Dijkstra(List<Path> paths) {
		this.paths = paths;
	}

	public void execute(Node source, boolean mode) {
		settledNodes = new HashSet<Node>();
		unSettledNodes = new HashSet<Node>();
		distance = new HashMap<Node, Double>();
		predecessors = new HashMap<Node, Node>();
		executionMode = mode;
		distance.put(source, 0.0);
		unSettledNodes.add(source);
		while (unSettledNodes.size() > 0) {
			Node node = getMinimum(unSettledNodes);
			settledNodes.add(node);
			unSettledNodes.remove(node);
			findMinimalDistances(node);
		}
	}

	private void findMinimalDistances(Node node) {
		List<Node> adjacentNodes = getNeighbors(node);
		for (Node target : adjacentNodes) {
			if (getShortestDistance(target) > getShortestDistance(node) + getDistance(node, target)) {
				distance.put(target, getShortestDistance(node) + getDistance(node, target));
				predecessors.put(target, node);
				unSettledNodes.add(target);
			}
		}

	}

	private Path getPathFromTwoNodes(Node node, Node target) {

		for (Path path : paths) {
			if (executionMode == ARRIVAL_MODE) {
				if (checkPathExist(node, target, path))
					return path;
			} else {
				if (checkPathExist(target, node, path))
					return path;
			}
		}
		throw new RuntimeException("Should not happen");
	}

	private double getDistance(Node node, Node target) {
		for (Path path : paths) {
			if (executionMode == ARRIVAL_MODE) {
				if (checkPathExist(node, target, path))
					return path.getDistance();

			} else {
				if (checkPathExist(target, node, path))
					return path.getDistance();
			}
		}
		throw new RuntimeException("Should not happen");
	}

	private boolean checkPathExist(Node node, Node target, Path path) {
		boolean checker = false;
		if (path.getLaneList().get(0).getStartNode().getId() == node.getId()) {
			if (path.getLaneList().get(path.getLaneList().size() - 1).getEndNode().getId() == target.getId()) {
				checker = true;
			}
		}
		return checker;
	}

	private List<Node> getNeighbors(Node node) {
		List<Node> neighbors = new ArrayList<Node>();
		for (Path path : paths) {
			if(executionMode == ARRIVAL_MODE){
				if (path.getLaneList().get(0).getStartNode().getId() == node.getId()
						&& !isSettled(path.getLaneList().get(path.getLaneList().size() - 1).getEndNode())) {
					neighbors.add(path.getLaneList().get(path.getLaneList().size() - 1).getEndNode());
				}
			}else{
				if (path.getLaneList().get(0).getEndNode().getId() == node.getId()
						&& !isSettled(path.getLaneList().get(path.getLaneList().size() - 1).getStartNode())) {
					neighbors.add(path.getLaneList().get(path.getLaneList().size() - 1).getStartNode());
				}
			}
			
		}
		return neighbors;
	}

	private Node getMinimum(Set<Node> unSettledNodes2) {
		Node minimum = null;
		for (Node vertex : unSettledNodes2) {
			if (minimum == null) {
				minimum = vertex;
			} else {
				if (getShortestDistance(vertex) < getShortestDistance(minimum)) {
					minimum = vertex;
				}
			}
		}
		return minimum;
	}

	private boolean isSettled(Node vertex) {
		return settledNodes.contains(vertex);
	}

	private Double getShortestDistance(Node vertex) {
		Double d = distance.get(vertex);
		if (d == null) {
			return Double.MAX_VALUE;
		} else {
			return d;
		}
	}

	/*
	 * This method returns the path from the source to the selected target and
	 * NULL if no path exists
	 */
	public LinkedList<Path> getPath(Node target) {
		LinkedList<Node> path = new LinkedList<Node>();
		LinkedList<Path> pathList = new LinkedList<Path>();
		Node step = target;
		Node stepBefore;
		// check if a path exists
		if (predecessors.get(step) == null) {
			return null;
		}
		path.add(step);
		while (predecessors.get(step) != null) {
			stepBefore = step;
			step = predecessors.get(step);
			path.add(step);
			pathList.add(getPathFromTwoNodes(step, stepBefore));
		}
		// Put it into the correct order
		Collections.reverse(path);
		Collections.reverse(pathList);

		return pathList;
	}

}