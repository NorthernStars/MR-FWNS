package essentials.communication.worlddata_server2008;

import javax.xml.bind.annotation.XmlElement;

public class Score {

    @XmlElement(name="yellow")
	int mYellowTeam;
    
    @XmlElement(name="blue")
	int mBlueTeam;
	
    public int ofYellowTeam() {
        return mYellowTeam;
    }
    public int ofBlueTeam() {
        return mBlueTeam;
    }
    
    @Override
    public String toString() {
        return "Score [mYellowTeam=" + mYellowTeam + ", mBlueTeam=" + mBlueTeam + "]";
    }
	
	
}