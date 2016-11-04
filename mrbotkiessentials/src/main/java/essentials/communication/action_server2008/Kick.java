package essentials.communication.action_server2008;

import essentials.communication.Action;

public class Kick implements Action {

	private double mAngle;
	private float mForce;
	
	public Kick( double aAngle, float aForce){
		
		mAngle = aAngle;
		mForce = aForce;
		
	}
	
	@Override
	public String getXMLString() {
		return "<command> <kick> <angle>" + mAngle + "</angle> <force>" + mForce + "</force> </kick> </command>";
	}

}
