package essentials.communication.worlddata_server2008;

import javax.xml.bind.annotation.XmlElement;

public class ReferencePoint{
	
	private ReferencePointName mPointName = ReferencePointName.NoFixedName;

    /**
     * The length of ReferencePoints in polar coordinate system
     */
	private double mDistanceToPoint = Double.NaN;

	/**
     * The angle of the ReferencePoints in polar coordinate system
     */
	private double mAngleToPoint = Double.NaN;

    /**
     * The X-Value of ReferencePoints in cartesian coordinate system
     */
    private double mX = Double.NaN;

    /**
     * The X-Value of ReferencePoints in cartesian coordinate system
     */
    private double mY = Double.NaN;
    
	/**
     * Useless?
     * The default constructor for a ReferencePoint on a matchfield.
     * 
     * Dieser Constructor ist package-private und sollte nie direkt genutzt werden. Er ist für
     * das dekodieren von XML mit JAXB gedacht.
     * 
     * @since 0.9
     */
    ReferencePoint( ) {}
	
	/**
     * The construtor for a ReferencePoint on a matchfield.
     *
     * This ReferencePoint will allways get the name "NoFixedName". Additionally the angle and the distance
     * to the bot will be setted. Be sure the angle is between -180 and 180 degree and the distance is not less
     * than 0!
     *
     * @since 0.9
     * @param aDistanceToPoint The distance from bot to ReferencePoint. Can not be less than 0!
     * @param aAngleToPoint The angle between the Reference and bot. Be sure its between -180 and 180 degree!
     * @exception IllegalArgumentException
     *              If one of the parameter is not in a legal range.
     *              
     * @deprecated use {@link #ReferencePoint(double, double, boolean)}
     */
	@Deprecated
    public ReferencePoint( double aDistanceToPoint, double aAngleToPoint ){
        
        this( aDistanceToPoint, aAngleToPoint, true );
        
    }
    
    /**
     * The construtor for a ReferencePoint on a matchfield.
     * 
     * This ReferencePoint will allways get the name "NoFixedName".
     * You can set the ReferencePoint in polar or cartesian coordinate system. The bot will always be the point of origin.
     * 
     * Be sure the angle is between -180 and 180 degree and the distance is not less than 0!
     * 
     * @since 0.9
     * @param aFirstValue Polar coordinate system: The distance between ReferencePoint and the bot. Can not be less than 0!
     *                    Cartesian coordinate system: The X-value of the ReferencePoint.
     * @param aSecondValue Polar coordinate system: Der Winkel des Referenzpunktes zum Bot. Muss zwischen -180 und +180 liegen.
     *                     Cartesian coordinate system: The Y-value of the ReferencePoint.
     * @param aPolarcoordinates true parameters are polar coordinates
     *                          false parameters are in cartesian coordinates
     * @exception IllegalArgumentException
     *              If parameters are not in a legal range
     */
    public ReferencePoint( double aFirstValue, double aSecondValue, boolean aPolarcoordinates ){
        
        set( aFirstValue, aSecondValue, aPolarcoordinates );
        
    }
	
    @XmlElement(name="id")
    public ReferencePointName getPointName() {
	    
        return mPointName;
        
    }
    
    void setPointName( ReferencePointName aPointName ){
        
        mPointName = aPointName;
        
    }
    
    @XmlElement(name="dist")
    public double getDistanceToPoint() {

        return mDistanceToPoint;
        
    }
    
    public void setDistanceToPoint( double aDistance ) {

        mDistanceToPoint = aDistance;
        if( getAngleToPoint() != Double.NaN && getDistanceToPoint() != Double.NaN ){
            set( mDistanceToPoint, mAngleToPoint, true );
        }
        
    }
    
    @XmlElement(name="angle")
    public double getAngleToPoint() {
        
        return mAngleToPoint;
        
    }
    
    public void setAngleToPoint( double aAngle ) {

        mAngleToPoint = aAngle;
        if( mDistanceToPoint != Double.NaN && mAngleToPoint != Double.NaN ){
            set( mDistanceToPoint, mAngleToPoint, true );
        }
        
    }
    
    @XmlElement(name="xvalue")
    public double getXOfPoint() {
        
        return mX;
        
    }
    
    public void setXOfPoint( double aXValue ) {

        mX = aXValue;
        set( mX, mY, false );
        
    }
    
    @XmlElement(name="yvalue")
    public double getYOfPoint() {
        
        return mY;
        
    }
    
    public void setYOfPoint( double aYValue ) {

        mY = aYValue;
        set( mX, mY, false );
        
    }
    
    /** 
     * Copy constructor
     * 
     * @param aReferencePoint the ReferencePoint to copy
     */
    public ReferencePoint ( ReferencePoint aReferencePoint ) {
        
            set( aReferencePoint );
            
    }

    /** 
     * Is making a copy of a ReferencePoint
     * 
     * @return A copy of the ReferencePoint
     */
    public ReferencePoint copy () {
        
            return new ReferencePoint( this );
            
    }

    /**
     * Overwrites the values of the ReferencePoint with the given ones.
     * 
     * @param aReferencePoint The ReferencePoint with the values which should be set.
     * 
     * @return This ReferencePoint with the set values
     */
    public ReferencePoint set ( ReferencePoint aReferencePoint ) {

        mDistanceToPoint = aReferencePoint.getDistanceToPoint();
        mAngleToPoint = aReferencePoint.getAngleToPoint();
        
        mX = aReferencePoint.getXOfPoint();
        mY = aReferencePoint.getYOfPoint();
        
        mPointName = ReferencePointName.NoFixedName;
        
        return this;
            
    }

    /**
     * Method to set the values of this ReferencePoint.
     *
     *
     * This ReferencePoint will allways get the name "NoFixedName".
     * You can set the ReferencePoint in polar or cartesian coordinate system. The bot will always be the point of origin.
     *
     * Polar coordinates: Be sure the angle is between -180 and 180 degree and the distance is not less than 0!
     * 
     * @since 0.9
     * @param aFirstValue Polar coordinate system: The distance between ReferencePoint and the bot. Can not be less than 0!
     *                    Cartesian coordinate system: The X-value of the ReferencePoint.
     * @param aSecondValue Polar coordinate system: Der Winkel des Referenzpunktes zum Bot. Muss zwischen -180 und +180 liegen.
     *                     Cartesian coordinate system: The Y-value of the ReferencePoint.
     * @param aPolarcoordinates true parameters are polar coordinates
     *                          false parameters are in cartesian coordinates
     * @exception IllegalArgumentException
     *              If parameters are not in a legal range
     *              
     * @return This ReferencePoint with the set values
     */
    public ReferencePoint set( double aFirstValue, double aSecondValue, boolean aPolarcoordinates ) {
        
        if( aPolarcoordinates ) {

            if( aFirstValue < 0 ){
                throw new IllegalArgumentException( " Distance can not be smaller than 0! Distance:" + aFirstValue + " Angle: " + aSecondValue );
            }
            mDistanceToPoint = aFirstValue;
            if( aSecondValue < -180 || aSecondValue > 180 ){
                throw new IllegalArgumentException(" Angle must be between -180 and +180! Distance:" + aFirstValue + " Angle: " + aSecondValue);
            }
            mAngleToPoint = aSecondValue;
            
            this.mX = mDistanceToPoint * Math.cos( Math.toRadians( mAngleToPoint ) );
            this.mY = mDistanceToPoint * Math.sin( Math.toRadians( mAngleToPoint ) );
            
        } else {

            mX = aFirstValue;
            mY = aSecondValue;
            
            mDistanceToPoint = Math.sqrt(mX * mX + mY * mY);
            mAngleToPoint = Math.toDegrees( Math.atan2( mY, mX ) );
            
        }
        
        return this;
            
    }

    /** Subtracts the given double from this ReferencePoint.
     * @param aScalar The double
     * @return dieser Referenzpunkt zum Verknüpfen von Methoden */
    public ReferencePoint sub( double aScalar ) {
            mX -= aScalar;
            mY -= aScalar;
            return set( mX, mY, false );
    }

    /** Adds the given ReferencePoint from this ReferencePoint.
     * @param aScalar The double
     * @return dieser Referenzpunkt zum Verknüpfen von Methoden */
    public ReferencePoint sub( ReferencePoint aReferencePoint ) {
            mX -= aReferencePoint.mX;
            mY -= aReferencePoint.mY;
            return set( mX, mY, false );
    }

    /** Adds the given ReferencePoint to this ReferencePoint
     * @param v The ReferencePoint
     * @return This ReferencePoint for chaining */
    public ReferencePoint add( ReferencePoint aReferencePoint ) {
            mX += aReferencePoint.mX;
            mY += aReferencePoint.mY;
            return set( mX, mY, false );
    }

    /** Multiplies this ReferencePoint by a scalar
     * @param scalar The scalar
     * @return This ReferencePoint for chaining */
    public ReferencePoint multiply( double scalar ) {
            mX *= scalar;
            mY *= scalar;
            return set( mX, mY, false );
    }

    
    /**
     * Compares this ReferencePoint with the ReferencePoint given as a parameter
     * and use the epsilon to set the sharpness.
     * 
     * @param aReferencePoint ReferencePoint to compare
     * @param aEpsilon Sharpness (0 to compare precisely
     * 
     * @return true if the ReferencePoints are equal
     */
    public boolean epsilonEquals ( ReferencePoint aReferencePoint, double aEpsilon )
    {

        return aReferencePoint != null
                && (Math.abs(aReferencePoint.getXOfPoint() - mX) <= aEpsilon)
                && (Math.abs(aReferencePoint.getYOfPoint() - mY) <= aEpsilon);

    }
    
    /**
     * Compares this ReferencePoint with the given X- and Y-coordinates of a ReferencePoint as a parameter
     * and use the epsilon to set the sharpness.
     * 
     * @param aX X-value of ReferencePoint
     * @param aY Y-value of ReferencePoint
     * @param aEpsilon Sharpness (0 to compare precisely
     * 
     * @return true if the ReferencePoints are equal
     */
    public boolean epsilonEquals ( double aX, double aY, double aEpsilon )
    {

        return Math.abs(aX - mX) <= aEpsilon
                && Math.abs(aY - mY) <= aEpsilon;

    }

    @Override
    public String toString() {
        return "ReferencePoint [mPointName=" + mPointName + ", mDistanceToPoint=" + mDistanceToPoint
                + ", mAngleToPoint=" + mAngleToPoint + ", mX=" + mX + ", mY=" + mY + "]";
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(mAngleToPoint);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(mDistanceToPoint);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((mPointName == null) ? 0 : mPointName.hashCode());
		temp = Double.doubleToLongBits(mX);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(mY);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		ReferencePoint other = (ReferencePoint) obj;
		if (Double.doubleToLongBits(mAngleToPoint) != Double.doubleToLongBits(other.mAngleToPoint))
			return false;
		if (Double.doubleToLongBits(mDistanceToPoint) != Double.doubleToLongBits(other.mDistanceToPoint))
			return false;
		if (mPointName != other.mPointName)
			return false;
		if (Double.doubleToLongBits(mX) != Double.doubleToLongBits(other.mX))
			return false;
		if (Double.doubleToLongBits(mY) != Double.doubleToLongBits(other.mY))
			return false;
		return true;
	}
    
    
    
}