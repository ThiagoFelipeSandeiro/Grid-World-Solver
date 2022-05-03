/**
 * 
 */
package COS30019;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author James Sanders
 * reference: Wikipedia, 2021. Iterative Deepening A*. [Online] 
 * Available at: https://en.wikipedia.org/wiki/Iterative_deepening_A*
 * [Accessed 29 April 2022].
 */
public class IDAStar {
	private Stack<Block> fPath;
	
	//procedure ida_star(root)
	public IDAStar(Block aNode) {
	    //bound := h(root)
		int bound = aNode.getMHDistance();
	    //path := [root]
		fPath = new Stack<Block>();
		fPath.push(aNode);
	    //loop
		while(true) {
	        //t := search(path, 0, bound)
			int t = search(0, bound);
            if (t == Integer.MAX_VALUE) { //if t = infinite then return NOT_FOUND
                // Node not found and no more nodes to visit
                break;
            } else if (t < 0) { //if t = FOUND then return (path, bound)
                // if we found the node, the function returns the negative distance
                break;
            } else {
                // if it hasn't found the node, it returns the (positive) next-bigger threshold
            	//bound := t
                bound = t;
            }
		}//end loop
	}//end procedure
	
	//function search(path, g, bound)
	private int search(int aG, int aBound) {
	    //node := path.last
		Block lNode = fPath.peek();
	    //f := g + h(node)
		int f = aG + lNode.getMHDistance();
	    //if f > bound then return f
		if(f > aBound) {
			return f;
		}
	    //if is_goal(node) then return FOUND
		if(lNode.isGoal()) {
			return -1;
		}
	    //min := infinite
		int min = Integer.MAX_VALUE;
	    //for succ in successors(node) do	
		for(Block succ:successors(lNode)) {
			//if succ not in path then
			if(!fPath.contains(succ)) {
				//path.push(succ)
				fPath.push(succ);
				//t := search(g + cost(node, succ), bound)
				int t = search(aG + 1, aBound);
				//if t = FOUND then return FOUND
				if(t < 0)
					return t;
				//if t < min then min := t
				if(t < min)
					min = t;
				//path.pop()
				fPath.pop();
			}//end if
		}//end for
		return min;
			
	}//end function
	
	private List<Block> successors(Block aNode) {
		List<Block> lSuccResult = new ArrayList<Block>();
		List<Block> lSuccTemp = new ArrayList<Block>();
		lSuccTemp.add(aNode.getEast());
		lSuccTemp.add(aNode.getSouth());
		lSuccTemp.add(aNode.getWest());
		lSuccTemp.add(aNode.getNorth());
		for(Block succ:lSuccTemp) {
			if(!succ.isWall()) {
				lSuccResult.add(succ);
			}
		}
		return lSuccResult;
	}
	
	public List<Block> getSolution() {
		return fPath;
	}
	
	public int length() {
		return fPath.size();
	}
}
