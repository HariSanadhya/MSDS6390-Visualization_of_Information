/* 
 * Enum created to decide the direction of the cylinder with respect of the axis. 
 * If POSITIVE, then the Cylinder will be created upside down
 * If NEGATIVE, then the Cylinder will be created above the origin 
 * if not POSITIVE or NEGATIVE (i.e. if null), then the cylinder will be created with origin in the middle
 */

public enum Direction{
	NEGATIVE, POSITIVE;
}
