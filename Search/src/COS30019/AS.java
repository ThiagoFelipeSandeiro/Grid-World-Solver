/**
 * 
 */
package COS30019;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * @author James Sanders
 * reference: Wikipedia, 2022. A* Search Algorithm. [Online] 
 * Available at: https://en.wikipedia.org/wiki/A*_search_algorithm
 * [Accessed 29 April 2022].
 */
public class AS {
	private List<Block> fTotal_path;
	private Block fCurrent;
	private PriorityQueue<Block> fOpenSet;
	private Map<Block, Block> fCameFrom;
	private Map<Block, Integer> fGScore;
	private Map<Block, Integer> fFScore;
	// A* finds a path from start to goal.
	// h is the heuristic function. h(n) estimates the cost to reach goal from node n.
	//function A_Star(start, goal, h)
	/**
	 * @param aNode
	 */
	public AS(Block aNode) {
	    // The set of discovered nodes that may need to be (re-)expanded.
	    // Initially, only the start node is known.
	    // This is usually implemented as a min-heap or priority queue rather than a hash-set.
		fOpenSet = new PriorityQueue<>(fComparator);
		//openSet := {start}
		fOpenSet.add(aNode);

	    // For node n, cameFrom[n] is the node immediately preceding it on the cheapest path from start
	    // to n currently known.
	    //cameFrom := an empty map
		fCameFrom = new HashMap<>();

	    // For node n, gScore[n] is the cost of the cheapest path from start to n currently known.
	    //gScore := map with default value of Infinity
		fGScore = new HashMap<>();
	    //gScore[start] := 0
		fGScore.put(aNode, aNode.getPathCost());
		

	    // For node n, fScore[n] := gScore[n] + h(n). fScore[n] represents our current best guess as to
	    // how short a path from start to finish can be if it goes through n.
	    //fScore := map with default value of Infinity
		fFScore = new HashMap<>();
	    //fScore[start] := h(start)
		fFScore.put(aNode, aNode.getMHDistance());

	    //while openSet is not empty
		while(!fOpenSet.isEmpty()) {
	        // This operation can occur in O(Log(N)) time if openSet is a min-heap or a priority queue
	        //current := the node in openSet having the lowest fScore[] value
			fCurrent = fOpenSet.peek();
	        //if current = goal
			if(fCurrent.isGoal()) {
	            reconstruct_path(fCameFrom, fCurrent);
				break;
			}
	        //openSet.Remove(current)
			fOpenSet.remove(fCurrent);
			
			//for each neighbor of current
			for(Block neighbor:expand(fCurrent)) {
				neighbor.setPathCost(aNode);
	            // d(current,neighbor) is the weight of the edge from current to neighbor
	            // tentative_gScore is the distance from start to the neighbor through current
	            //tentative_gScore := gScore[current] + d(current, neighbor)
				int lTentative_gScore = fGScore.get(fCurrent) + neighbor.getPathCost();
				fGScore.putIfAbsent(neighbor, Integer.MAX_VALUE);
	            //if tentative_gScore < gScore[neighbor]
				if(lTentative_gScore < fGScore.get(neighbor)) {
	                // This path to neighbor is better than any previous one. Record it!
	                //cameFrom[neighbor] := current
					fCameFrom.putIfAbsent(neighbor, fCurrent);
					//gScore[neighbor] := tentative_gScore
					fGScore.put(neighbor, lTentative_gScore);
	                //fScore[neighbor] := tentative_gScore + h(neighbor)
					fFScore.put(neighbor, lTentative_gScore + neighbor.getMHDistance());
	                //if neighbor not in openSet
					if(!fOpenSet.contains(neighbor)) {
						//openSet.add(neighbor)
						fOpenSet.add(neighbor);
					}
				}//end if
			}//end for
		}//end while
	    // Open set is empty but goal was never reached
	    //return failure
	}//end procedure
	/**
	 * @param aNode
	 * @return List<Block>
	 */
	private List<Block> expand(Block aNode) {
		List<Block> lSuccResult = new ArrayList<Block>();
		List<Block> lSuccTemp = new ArrayList<Block>();
		lSuccTemp.add(aNode.getNorth());
		lSuccTemp.add(aNode.getWest());
		lSuccTemp.add(aNode.getSouth());
		lSuccTemp.add(aNode.getEast());
		for(Block succ:lSuccTemp) {
			if(!succ.isWall()) {
				lSuccResult.add(succ);
			}
		}
		return lSuccResult;
	}
	
	//function reconstruct_path(cameFrom, current)
	public void reconstruct_path(Map<Block, Block> aCameFrom, Block aCurrent){
		fTotal_path = new ArrayList<Block>();
	    //total_path := {current}
		//lTotal_path.add(aCurrent);
		//while current in cameFrom.Keys:
		Block lTempBlock;
		while(aCameFrom.containsKey(aCurrent)) {
			lTempBlock = aCurrent;
			//total_path.prepend(current)
			fTotal_path.add(aCurrent);
			//current := cameFrom[current]
			aCurrent = aCameFrom.get(aCurrent);
			aCameFrom.remove(lTempBlock);
		}
		fTotal_path.add(aCurrent);
		//return total_path
		Collections.reverse(fTotal_path);
	}
	
	public List<Block> getSolution(){
		return fTotal_path;
	}
	
	public int length(){
		return fTotal_path.size();
	}
	
    private Comparator<Block> fComparator = new Comparator<Block>() {
    	
        @Override
    	public int compare(Block aLHSBlock, Block aRHSBlock) {

    		return (aLHSBlock.getMHDistance() + aLHSBlock.getPathCost()) - (aRHSBlock.getMHDistance() + aRHSBlock.getPathCost());
    	}
        
    };
}
