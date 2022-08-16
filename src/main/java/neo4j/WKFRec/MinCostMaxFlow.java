package neo4j.WKFRec;
import java.util.ArrayList;
import java.util.List;

public class MinCostMaxFlow {
	
	private List<List<Integer>> minCostPaths = new ArrayList<List<Integer>>();
	private List<Double> minCosts = new ArrayList<Double>();
	private List<Integer> maxFlows = new ArrayList<Integer>();
	private List<Integer> minCostPath = new ArrayList<Integer>();
	private double minCost = Double.MAX_VALUE;
	private int source = 1;
	private int target = 2;
	private int maxId = 2;
	private int[][] flows;
	private double[][] costs;
	
	public MinCostMaxFlow(int source, int target, int maxId, int[][] flows,
			double[][] costs) {
		super();
		this.source = source;
		this.target = target;
		this.maxId = maxId;
		this.flows = flows;
		this.costs = costs;
	}
	
	public List<List<Integer>> getMinCostPaths() {
		return minCostPaths;
	}
	public List<Double> getMinCosts() {
		return minCosts;
	}
	public List<Integer> getMaxFlows() {
		return maxFlows;
	}
	
	public void setMaxFlows(List<Integer> maxFlows) {
		this.maxFlows = maxFlows;
	}

	private boolean findMinCostPath(List<Integer> path, double cost) {
		// TODO Auto-generated method stub
		//System.out.println(cost);
		int node = path.get(path.size()-1);
		boolean isFound = false;
		for (int i = 1; i <= maxId; i++) {
			int next = i;
			if (path.contains(next)||flows[node-1][next-1]<=0||cost+costs[node-1][next-1]>=minCost) {
				continue;
			}else {
				//System.out.println("next="+i);
				List<Integer> newPath = new ArrayList<Integer>(); 
				newPath.addAll(path);
				newPath.add(next);
				double newCost = cost + costs[node-1][next-1];
				if (next!=target) {
					isFound = findMinCostPath(newPath,newCost)?true:isFound;
				}else {
					minCostPath.clear();
					minCostPath.addAll(newPath);
					minCost = newCost;
					isFound = true;
				}
			}
		}
		return isFound;
	}
	
	public double findMaxFlowPath() {
		// TODO Auto-generated method stub
		List<Integer> path = new ArrayList<Integer>();
		path.add(source);
		//boolean flag = findMinCostPath(path, 0);
		//System.out.println(flag);
		while (findMinCostPath(path, 0)) {
			int minFlow= Integer.MAX_VALUE;
			for (int i = 0; i < minCostPath.size()-1; i++) {
				if (flows[minCostPath.get(i)-1][minCostPath.get(i+1)-1]<minFlow) {
					minFlow = flows[minCostPath.get(i)-1][minCostPath.get(i+1)-1];
					//System.out.println(minFlow+" "+(minCostPath.get(i)-1)+" "+(minCostPath.get(i+1)-1));
				}
			}
			//System.out.println(minFlow);
			for (int i = 0; i < minCostPath.size()-1; i++) {
				flows[minCostPath.get(i)-1][minCostPath.get(i+1)-1] -= minFlow;
			}
			maxFlows.add(minFlow);
			minCosts.add(minCost);
			minCostPaths.add(minCostPath);
			
			path = new ArrayList<Integer>();
			path.add(source);
			minCost = Double.MAX_VALUE;
		}
		double cost = 0;
		for (int i = 0; i < maxFlows.size(); i++) {
			cost += maxFlows.get(i)*minCosts.get(i);
			//System.out.println(cost);
		}
		return cost;
	}
	
	public static void main(String[] args){
		int[][] flows = new int[7][7];
		double[][] costs = new double[7][7];
		flows[0][1] = 5;
		flows[1][0] = 0;
		flows[0][2] = 6;
		flows[2][0] = 0;
		flows[0][3] = 5;
		flows[3][0] = 0;
		flows[1][2] = 2;
		flows[2][1] = 2;
		flows[1][4] = 3;
		flows[4][1] = 0;
		flows[2][4] = 3;
		flows[4][2] = 0;
		flows[2][3] = 3;
		flows[3][2] = 0;
		flows[2][5] = 7;
		flows[5][2] = 0;
		flows[3][5] = 5;
		flows[5][3] = 0;
		flows[4][5] = 1;
		flows[5][4] = 1;
		flows[4][6] = 8;
		flows[6][4] = 0;
		flows[5][6] = 7;
		flows[6][5] = 0;
		
		costs[0][1] = 4;
		costs[1][0] = 4;
		costs[0][2] = 3;
		costs[2][0] = 3;
		costs[0][3] = 3;
		costs[3][0] = 3;
		costs[1][2] = 2;
		costs[2][1] = 2;
		costs[1][4] = 3;
		costs[4][1] = 3;
		costs[2][4] = 3;
		costs[4][2] = 3;
		costs[2][3] = 2;
		costs[3][2] = 2;
		costs[2][5] = 4;
		costs[5][2] = 4;
		costs[3][5] = 3;
		costs[5][3] = 3;
		costs[4][5] = 2;
		costs[5][4] = 2;
		costs[4][6] = 1;
		costs[6][4] = 1;
		costs[5][6] = 2;
		costs[6][5] = 2;
		MinCostMaxFlow mcmf = new MinCostMaxFlow(1, 7, 7, flows, costs);
		double cost = mcmf.findMaxFlowPath();
		System.out.println(cost);
		
		
		// mcmf
	}
	/*private void findPath1(List<WordNode> path, double cost) {
		// TODO Auto-generated method stub
		WordNode node = path.get(path.size()-1);
		for (int i = 0; i < node.getNeighbors().size(); i++) {
			WordNode next = node.getNeighbors().get(i);
			if (path.contains(next)||node.getFlows().get(i)<=0||cost+node.getCosts().get(i)>=minCost) {
				continue;
			}else {
				List<WordNode> newPath = new ArrayList<WordNode>(); 
				newPath = path;
				newPath.add(next);
				double newCost = cost + node.getCosts().get(i);
				if (next.equals(target)) {
					minCostPath = newPath;
					minCost = newCost;
				}else {
					findPath1(newPath,newCost);
				}
			}
		}
	}
	
	private void findMinCostPath(List<WordNode> words) {
		// TODO Auto-generated method stub
		List<WordNode> path = new ArrayList<WordNode>();
		path.add(source);
		findPath1(path, 0);
		//minCostPath.get
		for (int i = 0; i < words.size(); i++) {
			
		}
	}*/
	
}

/*class Edge{
	int source = 0;
	int target = -1;
	
}

class WordNode {
	int id = 0;
	String name = "";
	List<WordNode> neighbors = new ArrayList<WordNode>();
	List<Double> costs = new ArrayList<Double>();
	List<Integer> flows = new ArrayList<Integer>();

	public WordNode(int id, String name, List<WordNode> neighbors,
			List<Double> costs, List<Integer> flows) {
		super();
		this.id = id;
		this.name = name;
		this.neighbors = neighbors;
		this.costs = costs;
		this.flows = flows;
	}

	public WordNode() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<WordNode> getNeighbors() {
		return neighbors;
	}
	public void setNeighbors(List<WordNode> neighbors) {
		this.neighbors = neighbors;
	}
	public List<Double> getCosts() {
		return costs;
	}
	public void setCosts(List<Double> costs) {
		this.costs = costs;
	}
	public List<Integer> getFlows() {
		return flows;
	}
	public void setFlows(List<Integer> flows) {
		this.flows = flows;
	}
	
	public void addNeighbor(WordNode neighbor, int flow, double cost) {
		this.neighbors.add(neighbor);
		this.flows.add(flow);
		this.costs.add(cost);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WordNode other = (WordNode) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
}
*/