package essentials.communication.worlddata_server2008;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

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
    
    @XmlTransient 
    public int getYellowTeam()
    {
        return mYellowTeam;
    }

    public void setYellowTeam(int mYellowTeam)
    {
        this.mYellowTeam = mYellowTeam;
    }
    
    @XmlTransient 
    public int getBlueTeam()
    {
        return mBlueTeam;
    }

    public void setBlueTeam(int mBlueTeam)
    {
        this.mBlueTeam = mBlueTeam;
    }

    @Override
    public String toString() {
        return "Score [mYellowTeam=" + mYellowTeam + ", mBlueTeam=" + mBlueTeam + "]";
    }
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + mBlueTeam;
		result = prime * result + mYellowTeam;
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
		Score other = (Score) obj;
		if (mBlueTeam != other.mBlueTeam)
			return false;
		if (mYellowTeam != other.mYellowTeam)
			return false;
		return true;
	}
	
	
}