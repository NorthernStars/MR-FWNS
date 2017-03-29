package essentials.communication.worlddata_server2008;

import javax.xml.bind.annotation.XmlTransient;

public class BallPosition extends ReferencePoint {
	
    public BallPosition() {
        //mPointName = ReferencePointName.Ball;
    }
    
    public BallPosition( double aFirstValue, double aSecondValue, boolean aPolarcoordinates ) throws IllegalArgumentException{
        super(aFirstValue, aSecondValue, aPolarcoordinates);
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