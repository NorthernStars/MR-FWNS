package essentials.communication.worlddata_server2008;

import javax.xml.bind.annotation.XmlElement;

public class BallPosition {

    @XmlElement(name="dist")
	double mDistanceToBall;

    @XmlElement(name="angle")
	double mAngleToBall;
	
    public double getDistanceToBall() {
        return mDistanceToBall;
    }
    public double getAngleToBall() {
        return mAngleToBall;
    }
	
	

}