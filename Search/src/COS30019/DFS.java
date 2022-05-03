/**
 * 
 */
package COS30019;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * @author James Sanders
 * reference: Wikipedia, 2022. Depth-First Search. [Online] 
 * Available at: https://en.wikipedia.org/wiki/Depth-first_search
 * [Accessed 28 April 2022].
 */
public class DFS {
	private Block fNode;
	private List<Block> fPath;
	private Stack<Block> fStack;
	private Map<Block, Block> fCameFrom;
		
	//procedure DFS_iterative(G.v) is
	public DFS(Block aNode) {
		fNode = aNode;
		//let cameFrom be map 
		fCameFrom = new HashMap<>();
	    //let S be stack
		fStack = new Stack<Block>();
        //S.push( G.v )            //Inserting G.v in stack 
		fStack.push(fNode);
        //while ( S is not empty & G.v in not goal):
		while(!fStack.empty() && !fNode.isGoal()) {
            //Pop a vertex from stack to visit next
            //v  =  S.pop( )
			fNode = fStack.pop();
			//mark v as visited.
			fNode.setVisted(true);
			//Push all the neighbours of v in stack that are not visited   
			//for all neighbours w of v in Graph G:
			for(Block succ : successors(fNode)) {
              //if w is not visited :
				if(!succ.getVisted()) {
					//S.push( w )         
					fStack.push(succ);
					//cameFrom.put( w, v)
					fCameFrom.put(succ, fNode);
				}//end if
			}//end for
		}//end while
		//call reconstruct_path to find path taken.
		reconstruct_path(fCameFrom, fNode);
	}//end of DFS Constructor
	
	//procedure reconstruct_path(cameFrom, goal) is
	public void reconstruct_path(Map<Block, Block> aCameFrom, Block aCurrent){
		//let p be list
		fPath = new ArrayList<Block>();
		//let t be temp node
		Block lTempBlock;
		//while current in cameFrom.Keys:
		while(aCameFrom.containsKey(aCurrent)) {
			//t := {current}
			lTempBlock = aCurrent;
			//p.prepend(current)
			fPath.add(aCurrent);
			//current := cameFrom[current]
			aCurrent = aCameFrom.get(aCurrent);
			//cameFrom.remove[t]
			aCameFrom.remove(lTempBlock);
		}//end while
		//add final node.
		//p.prepend(current)
		fPath.add(aCurrent);
		//return reverse.p
		Collections.reverse(fPath);
	}//end procedure
	
	//procedure successors(v) is
	private List<Block> successors(Block aNode) {
		//let r be list of true successors 
		List<Block> lSuccResult = new ArrayList<Block>();
		//let t be list of all successors
		List<Block> lSuccTemp = new ArrayList<Block>();
		//for all neighbours w of v in Graph G:
		//t.add( w )
		lSuccTemp.add(aNode.getEast());
		lSuccTemp.add(aNode.getSouth());
		lSuccTemp.add(aNode.getWest());
		lSuccTemp.add(aNode.getNorth());
		//for all v of t
		for(Block succ:lSuccTemp) {
			//if v is not a wall 
			if(!succ.isWall()) {
				//r.add( v )
				lSuccResult.add(succ);
			}//end if
		}//end for
		// return r
		return lSuccResult;
	}//end procedure
	
	public List<Block> getSolution(){
		return fPath;
	}
	
	public int length(){
		return fPath.size();
	}
}
