/**
 * 
 */
package COS30019;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author James Sanders
 * reference: Wikipedia, 2021. Iterative Deepening Depth-First Search. [Online] 
 * Available at: https://en.wikipedia.org/wiki/Iterative_deepening_depth-first_search
 * [Accessed 29 April 2022].
 */
public class IDDFS {
	
	private List<Block> fPath;
	private Stack<Block> fStack;
		
	//function IDDFS(root) is
	public IDDFS(Block aNode) {
		fStack = new Stack<Block>();
	    //for depth from 0 to infinite do
		for(int lDepth = 0; lDepth < Integer.MAX_VALUE; lDepth++) {
	        //found, remaining = DLS(root, depth)
			ResponseObject lResponseObject = DLS(aNode, lDepth);
	        //if found != null then
			if(lResponseObject.getNode() != null) {
	            //return found
				break;
			}
	        //else if not remaining then
			else if(!lResponseObject.getBool()) {
	            //return null
				break;
			}
		}//end for
		fStack.add(aNode);
	}//end of IDDFS Constructor
	
	//function DLS(node, depth) is
	private ResponseObject DLS(Block aNode, int aDepth) {
		//if depth == 0 then
		if(aDepth == 0) {
	        //if node is a goal then
			if(aNode.isGoal()) {
	            //return (node, true)
				return new ResponseObject(aNode, true);
			}
	        //else
			else {
				//return (null, true)    (Not found, but may have children)
				return new ResponseObject(null,true);
			}
		}
	    //else if depth > 0 then
		else if(aDepth > 0) {
	        //any_remaining = false
			boolean lBool = false;
	        //for each child of node do
			for(Block succ:successors(aNode)) {
	            //found, remaining = DLS(child, depth-1)
				ResponseObject lResponseObject = DLS(succ, aDepth - 1);
	            //if found != null then
				if(lResponseObject.getNode() != null) {
					fStack.add(succ);
					//return (found, true)
					return new ResponseObject(lResponseObject.getNode(), true);
				}
				//if remaining then
				if(lResponseObject.getBool()) {
					//any_remaining = true    (At least one node found at depth, let IDDFS deepen)
					lBool = true;
				}
			}//end for
			//return (null, any_remaining)
			return new ResponseObject(null, lBool);
		}//end else if
		return new ResponseObject(null, true);
	}// end funtion

	private class ResponseObject {
		private Block fNode;
		private boolean fBool;
		public ResponseObject(Block aNode, boolean aBool) {
			this.fNode = aNode;
			this.fBool = aBool;
		}
		public Block getNode() {
			return this.fNode;
		}
		public boolean getBool() {
			return this.fBool;
		}
	}
	
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
	
	public List<Block> getSolution(){
		fPath = new ArrayList<Block>();
		int lLength = fStack.size();
		for(int i = 0; i < lLength; i++)
			fPath.add(fStack.pop());		
		return fPath;
	}
	
	public int length(){
		return fPath.size();
	}
}