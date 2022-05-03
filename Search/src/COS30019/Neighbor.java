package COS30019;
/**
 * @author James Sanders
 *
 */
public class Neighbor {

	Block  fNorth;
	Block fEast;
	Block fSouth;
	Block fWest;
	
	/**
	 * @param Default constructor
	 */
	public Neighbor( ) {
		this.fNorth = new Block();
		this.fEast = new Block();
		this.fSouth = new Block();
		this.fWest = new Block();
	}
	
	/**
	 * @param aNorth
	 * @param aEast
	 * @param aSouth
	 * @param aWest
	 */
	public Neighbor(Block aNorth, Block aEast, Block aSouth, Block aWest) {
		this.fNorth = aNorth;
		this.fEast = aEast;
		this.fSouth = aSouth;
		this.fWest = aWest;
	}
	/**
	 * @return the fNorth
	 */
	public Block getNorth() {
		return fNorth;
	}
	/**
	 * @param aNorth the fNorth to set
	 */
	public void setNorth(Block aNorth) {
		this.fNorth = aNorth;
	}
	/**
	 * @return the fEast
	 */
	public Block getEast() {
		return fEast;
	}
	/**
	 * @param aEast the fEast to set
	 */
	public void setEast(Block aEast) {
		this.fEast = aEast;
	}
	/**
	 * @return the fSouth
	 */
	public Block getSouth() {
		return fSouth;
	}
	/**
	 * @param aSouth the fSouth to set
	 */
	public void setSouth(Block aSouth) {
		this.fSouth = aSouth;
	}
	/**
	 * @return the fWest
	 */
	public Block getWest() {
		return fWest;
	}
	/**
	 * @param aWest the fWest to set
	 */
	public void setWest(Block aWest) {
		this.fWest = aWest;
	}
		
}
