package essentials.communication.worlddata_server2008;

import javax.xml.bind.annotation.XmlTransient;

public class BallPosition extends ReferencePoint {
	
    public BallPosition() {
        //mPointName = ReferencePointName.Ball;
    }
    
    @XmlTransient
    public double getDistanceToBall() {
        return getDistanceToPoint();
    }
    
    @XmlTransient
    public double getAngleToBall() {
        return getAngleToPoint();
    }

    @Override
    public String toString() {
        return "BallPosition [" + super.toString() + "]";
    }
	
}