package essentials.communication.action_server2008;

import essentials.communication.Action;

public class Movement implements Action {

	public static final Movement NO_MOVEMENT = new Movement( 0, 0);

	private int mRightWheelVelocity = 0;
	private int mLeftWheelVelocity = 0;
	
	public Movement( int aRightWheelVelocity, int aLeftWheelVelocity ) {
		
		mLeftWheelVelocity = aLeftWheelVelocity;
		mRightWheelVelocity = aRightWheelVelocity;

	}
	
	@Override
	public String getXMLString() {
		return "<command> <wheel_velocities> <right>" + mRightWheelVelocity + "</right> <left>" + mLeftWheelVelocity + "</left> </wheel_velocities> </command>";
	}

}
