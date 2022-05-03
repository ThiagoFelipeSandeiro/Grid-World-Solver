/**
 * 
 */
package COS30019;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author James Sanders
 *
 */
public class Maze {
	private Block[][] fMaze;
	private int fMazeHeight;
	private int fMazeWidth;
	private int fNumberOfGoals;
	private String[][] fGoals;
	
	private FileReader fReader;
	private BufferedReader fFileData;
	
	
	public Maze(String fileName) {
        try
        {
            //create file reading objects
            fReader = new FileReader(fileName);
            fFileData = new BufferedReader(fReader);
            this.setMazeDimensions(fFileData);
            this.generateMaze();
            this.setInitialState(fFileData);
            this.setGoalStates(fFileData);
            this.setWallStates(fFileData);
            //Close the resource, and return the array
            fFileData.close();
        }
        catch(FileNotFoundException ex)
        {
            //The file didn't exist, show an error
            System.out.println("Error: File \"" + fileName + "\" not found.");
            System.out.println("Please check the path to the file.");
            System.exit(1);
        }
        catch(IOException ex)
        {
            //There was an IO error, show and error message
            System.out.println("Error in reading \"" + fileName + "\". Try closing it and programs that may be accessing it.");
            System.out.println("If you're accessing this file over a network, try making a local copy.");
            System.exit(1);
        }
        
        
	}
	
	private void setWallStates(BufferedReader aFileData) throws IOException {
        //Wall Locations
        //extract numbers
        
        //Build Maze
        String lWallStateString;
        while ((lWallStateString = aFileData.readLine()) != null) {
        	
            String lAllDimensions = lWallStateString.substring(1, lWallStateString.length()-1);
            String[] lWallStateDimensions = lAllDimensions.split(",");
            
            //x and y coords
            int lXCoords = Integer.parseInt(lWallStateDimensions[0]);
            int lYCoords = Integer.parseInt(lWallStateDimensions[1]);
            
            //i & j loop         
            for( int i = Integer.parseInt(lWallStateDimensions[3]); i > 0 ; i--) {
                for( int j = Integer.parseInt(lWallStateDimensions[2]) ; j > 0 ; j--) {
                	fMaze[lYCoords+(i-1)][lXCoords+(j-1)].setStatus(Status.Wall);
                }
            }
        	
        	
        }
	}
	
	private void setGoalStates(BufferedReader aFileData) throws IOException {
		//Goal Locations
        //extract numbers
        String goalStateString = aFileData.readLine();
        
        String[] goalCoords = goalStateString.split("\\s{1}\\W{1}\\s{1}");
        fNumberOfGoals = goalCoords.length;
        fGoals = new String[fNumberOfGoals][];
        
        for(int i = 0; i < fNumberOfGoals; i++) {
        	fGoals[i] = goalCoords[i].substring(1, goalCoords[i].length()-1).split(",");
        }
        for(int i = 0; i < fNumberOfGoals; i++) {
        	fMaze[Integer.parseInt(fGoals[i][1])][Integer.parseInt(fGoals[i][0])].setCoords(new Vector(Integer.parseInt(fGoals[i][1]), Integer.parseInt(fGoals[i][0])));
        	fMaze[Integer.parseInt(fGoals[i][1])][Integer.parseInt(fGoals[i][0])].setStatus(Status.Goal);
        }
        
	}
	
	private void setInitialState(BufferedReader aFileData) throws IOException {
		//Start Location
        //extract numbers
        String lStartStateString = aFileData.readLine();
        
        String lBothDimensions = lStartStateString.substring(1, lStartStateString.length()-1);
        String[] startLocation = lBothDimensions.split(",");
        
        fMaze[Integer.parseInt(startLocation[1])][Integer.parseInt(startLocation[0])].setCoords(new Vector(Integer.parseInt(startLocation[1]), Integer.parseInt(startLocation[0])));        
        //assign start location
        fMaze[Integer.parseInt(startLocation[1])][Integer.parseInt(startLocation[0])].setStatus(Status.Start);
        
	}
	
	private void generateMaze() {
        //Create the 2D array, providing the values for the size of the floor as the size each dimension should be
        this.fMaze = new Block[fMazeHeight][fMazeWidth];
        for (int j = 0; j < fMazeHeight; j++) {
        	for (int i = 0; i < fMazeWidth; i++) {
        		fMaze[j][i] = new Block(Status.Path, new Vector(j,i), new Neighbor());
        	}
        }
	}

	private void setMazeDimensions(BufferedReader aFileData) throws IOException {
        String lMazeData = aFileData.readLine();
        
        //Maze Size
        //extract numbers
        String lBothDimensions = lMazeData.substring(1, lMazeData.length()-1);
        String[] lMazeDimensions = lBothDimensions.split(",");
        
        //work out the "physical" size of the map
        this.fMazeHeight = Integer.parseInt(lMazeDimensions[0]);
        this.fMazeWidth = Integer.parseInt(lMazeDimensions[1]);
	}
	
	public void buildNeighbours() {
        
		for (int row = 0; row < this.fMaze.length; row++) {
			
        	for (int column = 0; column < this.fMaze[row].length; column++) {
        		
        		this.fMaze[row][column].setCoords(new Vector(column, row));
        		
        		//Set Status
        		if(row == 0)
        			this.fMaze[row][column].getNorth().setStatus(Status.Wall);
        		if(column == this.fMaze[row].length-1)
        			this.fMaze[row][column].getEast().setStatus(Status.Wall);
        		if(row == this.fMaze.length-1)
        			this.fMaze[row][column].getSouth().setStatus(Status.Wall);
        		if(column == 0)
        			this.fMaze[row][column].getWest().setStatus(Status.Wall);
        		
        		
        		if(row > 0)
        			this.fMaze[row][column].setNorth(this.fMaze[row-1][column]);
        		if(column < this.fMaze[row].length-1)
        			this.fMaze[row][column].setEast(this.fMaze[row][column+1]);
        		if(row < this.fMaze.length-1)
        			this.fMaze[row][column].setSouth(this.fMaze[row+1][column]);
        		if(column > 0)
        			this.fMaze[row][column].setWest(this.fMaze[row][column-1]);
        	}
        }
	}
	
	//build Manhattan Distance to closest goal for each node.
	public void buildDistance() {
		
		//local fields
		int[] lMinMHD = new int[fNumberOfGoals];
		Block[] lGoalBlocks = new Block[fNumberOfGoals];
		int j = fNumberOfGoals - 1;
		
		//try, or throw exception, no goals in maze.
		try {
			
			for (int row = 0; row < fMaze.length; row++) {
	        	for (int column = 0; column < fMaze[row].length; column++) {
	        		if(fMaze[row][column].isGoal()) {
	        			lGoalBlocks[j] = fMaze[row][column];
	        			j--;
	        		}//end if
	        	}//end for
			}//end for
			
			//reset j
			j = fNumberOfGoals - 1;
			
			for (int row = 0; row < fMaze.length; row++) {
	        	for (int column = 0; column < fMaze[row].length; column++) {
	        		if(!fMaze[row][column].isWall()) {
	        			for(int i = 0; i <= j; i++) {
	        				//calculate Manhattan Distance for each goal.
	        				lMinMHD[i] = (Math.max(lGoalBlocks[i].getCoords().getfX(), fMaze[row][column].getCoords().getfX()) - (Math.min(lGoalBlocks[i].getCoords().getfX(), fMaze[row][column].getCoords().getfX()))) + (Math.max(lGoalBlocks[i].getCoords().getfY(), fMaze[row][column].getCoords().getfY()) - Math.min(lGoalBlocks[i].getCoords().getfY(), fMaze[row][column].getCoords().getfY()));			
	        			}
	        			int lMin = lMinMHD[0];
	        			//find closest goal.
	        			for(int i = 1; i < lMinMHD.length; i++) {
	        				if(lMin > lMinMHD[i])
	        					lMin = lMinMHD[i];
	        			}
	        			//set Manhattan Distance to closest goal, for each node.
	        			fMaze[row][column].setMHDistance(lMin);
	        		}//end if
	        	}//end for
			}//end for
		}//end try
        catch(Exception ex)
        {
            //There was an IO error, show and error message
            System.out.println("Error " + ex);
            System.out.println("No Goal states in Maze problem");
            System.exit(1);
        }//end catch
		
	}//end function
	
	//find start node.
	public Block getStart() {
		for (int row = 0; row < fMaze.length; row++) {
        	for (int column = 0; column < fMaze[row].length; column++) {
        		if(fMaze[row][column].isStart())
        			return fMaze[row][column];
        	}
		}
		return null;
	}
	
}
