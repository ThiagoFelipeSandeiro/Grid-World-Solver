/**
 * 
 */
package COS30019;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * @author James Sanders
 * reference: Wikipedia, 2022. Best-First Search. [Online] 
 * Available at: https://en.wikipedia.org/wiki/Best-first_search
 * [Accessed 29 April 2022].
 */
public class GBFS {
	private List<Block> fPath;
	private PriorityQueue<Block> fFrontier;

	//procedure GBS(start, target) is:
	public GBFS(Block aNode) {
		fPath = new ArrayList<Block>();
		fFrontier = new PriorityQueue<>(fMHDComparator);
		//mark start as visited
		aNode.setVisted(true);
		//add start to queue
		fFrontier.add(aNode);
		//while queue is not empty do:
		while(!fFrontier.isEmpty() && !aNode.isGoal()) {
		    //current_node = vertex of queue with min distance to target
			//remove current_node from queue
			aNode = fFrontier.poll();
			fPath.add(aNode);
		    //foreach neighbor n of current_node do:
			for(Block succ:expand(aNode)) {
				//if n not in visited then:
				if(!succ.getVisted()) {
					//mark n as visited
					succ.setVisted(true);
					//add n to queue
					fFrontier.add(succ);
				}//end if
			}//end for
		}//end while
	}//end procedure
	
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
	
	public List<Block> getSolution(){
		return fPath;
	}
	
	public int length(){
		return fPath.size();
	}
	
    private Comparator<Block> fMHDComparator = new Comparator<Block>() {
    	
        @Override
    	public int compare(Block aLHSBlock, Block aRHSBlock) {

    		return aLHSBlock.getMHDistance() - aRHSBlock.getMHDistance();
    	}
        
    };
}
