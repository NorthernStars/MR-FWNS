package essentials.communication.action_server2008;

import essentials.communication.Action;


/**
 * 
 * Represents a kick {@link action} sent to the server
 * @author lape
 */
public class Kick implements Action {

	private final double mAngle;
	private final float mForce;
	
	
	public Kick( double aAngle, float aForce){
		
		mAngle = aAngle;
		mForce = aForce;
		
	}
	
	@Override
	public String getXMLString() {
		return "<command> <kick> <angle>" + mAngle + "</angle> <force>" + mForce + "</force> </kick> </command>";
	}

	public double getAngle() {
		return mAngle;
	}

	public float getForce() {
		return mForce;
	}
	
	

}
