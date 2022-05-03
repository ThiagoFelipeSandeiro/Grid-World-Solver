package COS30019;

import java.util.*;

/**
 * @author James Sanders
 *
 */

public class Search {
	
    /**
     * @param args - [0] <filename>
     *             - [1]  <method>
     */
	public static void main(String[] args) {
		//if console != null, while args < 1, ask for proper input.
		if(System.console() != null) {
			while(args.length<1) {
				System.out.println("Please add <filename> <method>");
				String lArgs = System.console().readLine();
				args = lArgs.split(" ");
			}//end while
		}else {
			if(args.length < 1) {
				System.out.println("Please add <filename> <method>");
				return;
			}//end if
		}//end else
		
		//local fields
		List<String> fDirections = new ArrayList<String>();
		boolean fAlgoSelected = false;
		String fMethod = "";
		int fNumberOfNodes = 0;
		List<Block> fPath;
		Maze fMaze = new Maze(args[0]); //construct new maze
		
		//build maze structure
		fMaze.buildNeighbours();
		fMaze.buildDistance();		
		
		if(args.length > 1) {
			switch(args[1]) {
				case "DFS":
					DFS fDFS = new DFS(fMaze.getStart()); //construct search
					fPath = fDFS.getSolution(); //get solution, solution is list of nodes.
					fDirections = getDirections(fPath); //get directions, directions are calculated from list of nodes.
					fNumberOfNodes = fDirections.size(); //get count of directions
					fMethod = "Depth First Search"; //full name of search method
					fAlgoSelected = true; //current method selected
					break;
				case "BFS":
					BFS fBFS = new BFS(fMaze.getStart());
					fPath = fBFS.getSolution();
					fDirections = getDirections(fPath);
					fNumberOfNodes = fDirections.size();
					fMethod = "Breadth First Search";
					fAlgoSelected = true;
					break;
				case "GBFS":
					GBFS fGBFS = new GBFS(fMaze.getStart());
					fPath = fGBFS.getSolution();
					fDirections = getDirections(fPath);
					fNumberOfNodes = fDirections.size();
					fMethod = "Greedy Best-First Search";
					fAlgoSelected = true;
					break;
				case "AS":
					AS fAStar = new AS(fMaze.getStart());
					fPath = fAStar.getSolution();
					fDirections = getDirections(fPath);
					fNumberOfNodes = fDirections.size();
					fMethod = "A* Search";
					fAlgoSelected = true;
					break;
				case "CUS1":
					IDDFS fCus1 = new IDDFS(fMaze.getStart());
					fPath = fCus1.getSolution();
					fDirections = getDirections(fPath);
					fNumberOfNodes = fDirections.size();
					fMethod = "Iterative Deepening Depth-First Search (IDDFS)";
					fAlgoSelected = true;
					break;
				case "CUS2":
					IDAStar fIDAStar = new IDAStar(fMaze.getStart());
					fPath = fIDAStar.getSolution();
					fDirections = getDirections(fPath);
					fNumberOfNodes = fDirections.size();
					fMethod = "Iterative Deeping A* Search";
					fAlgoSelected = true;
					break;
				default:
					fPath = null;
					break;
			}
		}else {
			fPath = null;
		}
		//if correct method selected, print directions.
		if(fAlgoSelected) {
			System.out.println(args[0]+" "+fMethod+" "+fNumberOfNodes);
			for(String path : fDirections) {
				System.out.print(path + ' ');				
			}//end for
			
			//uncomment code below for node descriptions.
			//if correct method selected, print list of nodes.
			/*for(Block path:fPath) {
				System.out.println(path.getStatus()+" " +path.getCoords().getfX()+','+path.getCoords().getfY()+'\t' + path.getMHDistance() +'\t'+path.getPathCost() +'\t'+ (path.getPathCost()+path.getMHDistance()) );
			}//end for*/
			
		}//end if
		else {
			System.out.println("Please enter the currect method.\nE.g. DFS, BFS, GBFS, AS, CUS1, or CUS2.");
		}//end else
		
	}//end main

	//procedure to get directions, getDirections(list of nodes) returns list of strings as directions. 
	public static List<String> getDirections(List<Block> aPath){
		//local fields
		List<String> lDirections = new ArrayList<String>();
		Block lNode, lNodeNext;
		
		//for each node in list of nodes, find direction to next node.
		for(int i = 0; i < aPath.size()-1; i++) {
			lNode = aPath.get(i);
			lNodeNext = aPath.get(i+1);
			int lX = lNodeNext.getCoords().getfX() - lNode.getCoords().getfX();
			int lY = lNodeNext.getCoords().getfY() - lNode.getCoords().getfY();
			switch(lX) {
				case 1:
					lDirections.add("Right;");
					break;
				case -1:
					lDirections.add("Left;");
					break;
			}
			switch(lY) {
			case 1:
				lDirections.add("Down;");
				break;
			case -1:
				lDirections.add("Up;");
				break;
			}
		}//end for
		return lDirections;
	}//end function
}
