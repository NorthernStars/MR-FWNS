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

        public int getmRightWheelVelocity()
        {
            return mRightWheelVelocity;
        }

        public int getmLeftWheelVelocity()
        {
            return mLeftWheelVelocity;
        }
  
	@Override
	public String getXMLString() {
		return "<command> <wheel_velocities> <right>" + mRightWheelVelocity + "</right> <left>" + mLeftWheelVelocity + "</left> </wheel_velocities> </command>";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + mLeftWheelVelocity;
		result = prime * result + mRightWheelVelocity;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Movement other = (Movement) obj;
		if (mLeftWheelVelocity != other.mLeftWheelVelocity)
			return false;
		if (mRightWheelVelocity != other.mRightWheelVelocity)
			return false;
		return true;
	}

}
