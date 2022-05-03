package COS30019;

/**
 * @author James Sanders
 *
 */
public class Block {

	private Status fStatus;
    private Vector fCoords;
    private Neighbor fNeighbor;
    private boolean fVisted = false;
    private int fPathCost;
    private int fMHD;
    
	/**
	 * @param Default constructor
	 */
    public Block() {
    	this.fStatus = Status.Path;
    	this.fCoords = new Vector();
    	this.fNeighbor = null;
    	this.fVisted = false;
    	this.fPathCost = 0;
    }
    
    /**
     * @param constructor
	 * @param aStatus
	 * @param aCoords
	 * @param aNeighbor
	 */
	public Block(Status aStatus, Vector aCoords, Neighbor aNeighbor) {
		this.fStatus = aStatus;
		this.fCoords = aCoords;
		this.fNeighbor = aNeighbor;
		this.fVisted = false;
		this.fPathCost = 0;
	}
	
	public Block(Block aBlock) {
		this.fStatus = aBlock.getStatus();
		this.fCoords = aBlock.getCoords();
		this.fNeighbor = aBlock.getNeighbor();
		this.fVisted = aBlock.getVisted();
		this.fPathCost = aBlock.getPathCost();
		this.fMHD = aBlock.getMHDistance();
	}
	
	public int getPathCost() {
		return this.fPathCost;
	}
	
	public void setPathCost(Block aParentNode) {
		this.fPathCost = Math.abs( 
										(this.fCoords.getfX() - aParentNode.fCoords.getfX() ) + 
										(this.fCoords.getfY() - aParentNode.fCoords.getfY() )
									);
	}
	
	public int getMHDistance() {
		return this.fMHD;
	}
	
	public void setMHDistance(int aDistance) {
		this.fMHD = aDistance;
	}
	
	public boolean isStart() {		
		return this.getStatus().equals(Status.Start);
	}
	
	public boolean isWall() {		
		return this.getStatus().equals(Status.Wall);
	}
	
	public boolean isPath() {		
		return this.getStatus().equals(Status.Path);
	}
	
	public boolean isGoal() {		
		return this.getStatus().equals(Status.Goal);
	}	
	
	public void setVisted(boolean aBoolean) {
		this.fVisted = aBoolean;
	}
	
	public boolean getVisted() {
		return this.fVisted;
	}	
	
	/**
	 * @return the fStatus
	 */
	public Status getStatus() {
		return fStatus;
	}
	
	/**
	 * @param aStatus the fStatus to set
	 */
	public void setStatus(Status aStatus) {
		this.fStatus = aStatus;
	}
	
	/**
	 * @return the fCoords
	 */
	public Vector getCoords() {
		return fCoords;
	}

	/**
	 * @param aCoords the fCoords to set
	 */
	public void setCoords(Vector aCoords) {
		this.fCoords = aCoords;
	}

	/**
	 * @return the fNeighbor
	 */
	public Neighbor getNeighbor() {
		return fNeighbor;
	}

	/**
	 * @param aNeighbor the fNeighbor to set
	 */
	public void setNeighbor(Neighbor aNeighbor) {
		this.fNeighbor = aNeighbor;
	}	

	public void setNorth(Block aBlock) {
		this.fNeighbor.setNorth(aBlock);
	}
	
	public void setEast(Block aBlock) {
		this.fNeighbor.setEast(aBlock);
	}
	
	public void setSouth(Block aBlock) {
		this.fNeighbor.setSouth(aBlock);
	}
	
	public void setWest(Block aBlock) {
		this.fNeighbor.setWest(aBlock);
	}
	
	public Block getNorth() {
		return this.fNeighbor.getNorth();
	}
	
	public Block getEast() {
		return this.fNeighbor.getEast();
	}
	
	public Block getSouth() {
		return this.fNeighbor.getSouth();
	}
	
	public Block getWest() {
		return this.fNeighbor.getWest();
	}
	
}
