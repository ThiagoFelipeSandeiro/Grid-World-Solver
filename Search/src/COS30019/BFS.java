/**
 * 
 */
package COS30019;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author James Sanders
 * reference: Wikipedia, 2022. Breadth-First Search. [Online] 
 * Available at: https://en.wikipedia.org/wiki/Breadth-first_search
 * [Accessed 28 April 2022].
 */
public class BFS {
	private Block fNode;
	private List<Block> fPath;
	private Deque<Block> fDeque;
	private Map<Block, Block> fCameFrom;
	
	public BFS(Block aNode) {
		fCameFrom = new HashMap<>();
		fNode = aNode;
		fPath = new ArrayList<Block>();
		 //2      let Q be a queue
		fDeque = new LinkedList<Block>();
		 //3      label root as explored
		fNode.setVisted(true);
		 //4      Q.enqueue(root)
		fDeque.push(fNode);
		 //5      while Q is not empty do
		while(!fDeque.isEmpty()) {
			 //6          v := Q.dequeue()
			fNode = fDeque.pop();
			 //7          if v is the goal then
			if(fNode.isGoal()) {
				//8              return v			
	            reconstruct_path(fCameFrom, fNode);
				break;
			}
			fPath.add(fNode);
			//9          for all edges from v to w in G.adjacentEdges(v) do
			for(Block succ : successors(fNode)) {
				//10              if w is not labeled as explored then
				if(!succ.getVisted()) {
					//11                  label w as explored
					succ.setVisted(true);
					//12                  Q.enqueue(w)
					fDeque.add(succ);
					fCameFrom.putIfAbsent(succ, fNode);
				}//end if
			}//end for
		}
	}//end of BFS Constructor
	
	//function reconstruct_path(cameFrom, current)
	public void reconstruct_path(Map<Block, Block> aCameFrom, Block aCurrent){
		fPath = new ArrayList<Block>();
	    //total_path := {current}
		//lTotal_path.add(aCurrent);
		//while current in cameFrom.Keys:
		Block lTempBlock;
		while(aCameFrom.containsKey(aCurrent)) {
			lTempBlock = aCurrent;
			//total_path.prepend(current)
			fPath.add(aCurrent);
			//current := cameFrom[current]
			aCurrent = aCameFrom.get(aCurrent);
			aCameFrom.remove(lTempBlock);
		}
		fPath.add(aCurrent);
		//return total_path
		Collections.reverse(fPath);
	}
	
	private List<Block> successors(Block aNode) {
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
	
	public List<Block> getSolution(){
		return fPath;
	}
	
	public int length(){
		return fPath.size();
	}
}
