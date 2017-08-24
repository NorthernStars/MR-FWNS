package essentials.communication.worlddata_server2008;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

public class FellowPlayer extends ReferencePoint{

    private int mId;
    
	@XmlElement(name="nickname")
	String mNickname;
	
	@XmlElement(name="status")
    @XmlJavaTypeAdapter(StringToBooleanAdapter.class)
	Boolean mStatus;
	
	@XmlElement(name="orientation")
	double mOrientation;
	
	public FellowPlayer() {
	    //mPointName = ReferencePointName.Player;
	}
	
    public FellowPlayer(int aId, String aNickname, Boolean aStatus, double aDistanceToPlayer, double aAngleToPlayer, double aOrientation) {
		super(aDistanceToPlayer, aAngleToPlayer, true);        
        
		mId = aId;
		mNickname = aNickname;
		mStatus = aStatus;
		mOrientation = aOrientation;
		//mPointName = ReferencePointName.Player;
		
	}
    
    @XmlElement(name="id")
	public int getId() {
        return mId;
    }
    
    // Da unser Serverxmldocument scheiße ist, und ich keine schnelle bessere lösung finden konnte isses nu so
    static int IDCOUNTER = 0;
    
    /**
     * Always sets ReferencePointName.Player, ignores the parameter
     * Needs to override the super function, otherwise FellowPlayers PointName could be set to anything other than Player, eg Ball
     * @param aPointName is ignored and only used to override the same signature super function
     */
    @Override
    void setPointName( ReferencePointName aPointName ) {
        
        super.setPointName( ReferencePointName.Player );
        mId = IDCOUNTER++;
        
    }
    
    @XmlTransient
    public String getNickname() {
        return mNickname;
    }
    @XmlTransient
    public Boolean getStatus() {
        return mStatus;
    }
    @XmlTransient
    public double getDistanceToPlayer() {
        return getDistanceToPoint();
    }
    @XmlTransient
    public double getAngleToPlayer() {
        return getAngleToPoint();
    }
    @XmlTransient
    public double getOrientation() {
        return mOrientation;
    }

    @Override
    public String toString() {
        return "FellowPlayer [mId=" + mId + ", mNickname=" + mNickname + ", mStatus=" + mStatus + ", mOrientation="
                + mOrientation + " " + super.toString() + "]";
    }

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		FellowPlayer other = (FellowPlayer) obj;
		if (mId != other.mId)
			return false;
		if (mNickname == null) 
		{
			if (other.mNickname != null)
				return false;
		} else if (!mNickname.equals(other.mNickname))
			return false;
		if (Double.doubleToLongBits(mOrientation) != Double.doubleToLongBits(other.mOrientation))
			return false;
		if (mStatus == null) {
			if (other.mStatus != null)
				return false;
		} else if (!mStatus.equals(other.mStatus))
			return false;
		return true;
	} 
    
}