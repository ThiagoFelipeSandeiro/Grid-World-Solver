/**
 * 
 */
package COS30019;

/**
 * @author James Sanders
 *
 */
public class Vector {

    private int fX;
    private int fY;
	
	/**
	 * @param Default constructor
	 */
    public Vector() {
    	this.fX = 0;
    	this.fY = 0;
    }
    
    /**
	 * @param aX
	 * @param aY
	 */
	public Vector(int aX, int aY) {
		this.fX = aX;
		this.fY = aY;
	}
	
	/**
	 * @return the fX
	 */
	public int getfX() {
		return fX;
	}
	
	/**
	 * @param aX the fX to set
	 */
	public void setfX(int aX) {
		this.fX = aX;
	}
	
	/**
	 * @return the fY
	 */
	public int getfY() {
		return fY;
	}
	
	/**
	 * @param aY the fY to set
	 */
	public void setfY(int aY) {
		this.fY = aY;
	}
    
    
}
