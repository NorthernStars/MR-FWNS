package essentials.communication.worlddata_server2008;

import javax.xml.bind.annotation.XmlElement;

public class ReferencePoint{
	
	private ReferencePointName mPointName = ReferencePointName.NoFixedName;
    /** 
     * die Länge des ReferencePoints im polaren Koordinatensystem 
     */
	private double mDistanceToPoint = Double.NaN;
	/** 
     * der Winkel des ReferencePoints im polaren Koordinatensystem
     */
	private double mAngleToPoint = Double.NaN;
    /** 
     * der X-Wert des ReferencePoints im kartesischen Koordinatensystem 
     */
    private double mX = Double.NaN;
    /** 
     * der X-Wert des ReferencePoints im kartesischen Koordinatensystem 
     */
    private double mY = Double.NaN;
    
	/**
     * Der Default-Constructor für einen Referenzpunkt auf dem Spielfeld.
     * 
     * Dieser Constructor ist package-private und sollte nie direkt genutzt werden. Er ist für
     * das dekodieren von XML mit JAXB gedacht.
     * 
     * @since 0.9
     */
    ReferencePoint( ) {}
	
	/**
     * Der Constructor für einen Referenzpunkt auf dem Spielfeld.
     * 
     * Dieser Referenzpunkt wird immer mit dem Namen "NoFixedName" und dem Winkel und der Distanz
     * zu dem Bot erstellt. Dabei ist zu beachten, das der Winkel zwischen -180 und 180 Grad liegt und
     * die Distanz nicht kleiner als 0 sein darf.
     * 
     * @since 0.9
     * @param aDistanceToPoint Die Entfernung des Bots zum Referenzpunkt. Kann nicht kleiner als Null sein.
     * @param aAngleToPoint Der Winkel des Referenzpunktes zum Bot. Muss zwischen -180 und +180 liegen.
     * @exception IllegalArgumentException
     *              Wenn die Parameter nicht in den erlaubten Bereichen liegen.
     *              
     * @deprecated benutz {@link #ReferencePoint(double, double, boolean)}
     */
	@Deprecated
    public ReferencePoint( double aDistanceToPoint, double aAngleToPoint ) throws IllegalArgumentException{
        
        this( aDistanceToPoint, aAngleToPoint, true );
        
    }
    
    /**
     * Der Constructor für einen Referenzpunkt auf dem Spielfeld.
     * 
     * Dieser Referenzpunkt wird immer mit dem Namen "NoFixedName" erstellt.
     * Er kann in Polarkoordinaten oder kartesischen Koordinaten angegeben werden. Der Bot ist dabei immer der Ursprung.
     * 
     * Bei Polarkoordinaten ist zu beachten, das der Winkel zwischen -180 und 180 Grad liegt und
     * die Distanz nicht kleiner als 0 sein darf.
     * 
     * @since 0.9
     * @param aFirstValue Polarkoordinaten: Die Entfernung des Bots zum Referenzpunkt. Darf nicht kleiner als Null sein.
     *                    kartesische Koordinaten: Der X-Wert des Punktes.
     * @param aSecondValue Polarkoordinaten: Der Winkel des Referenzpunktes zum Bot. Muss zwischen -180 und +180 liegen.
     *                     kartesische Koordinaten: Der Y-Wert des Punktes.
     * @param aPolarcoordinates true die übergebenden Werte sind Polarkoordinaten
     *                          false die übergebenden Werte sind kartesische Koordinaten
     * @exception IllegalArgumentException
     *              Wenn die Parameter nicht in den erlaubten Bereichen liegen.
     */
    public ReferencePoint( double aFirstValue, double aSecondValue, boolean aPolarcoordinates ) throws IllegalArgumentException{
        
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
     * Kopierkonstruktor
     * 
     * @param aReferencePoint der zu kopierende Referenzpunkt
     */
    public ReferencePoint ( ReferencePoint aReferencePoint ) {
        
            set( aReferencePoint );
            
    }

    /** 
     * Erstellte eine Kopie diese Referenzpunktes
     * 
     * @return eine Kopie des Referenzpunktes 
     */
    public ReferencePoint copy () {
        
            return new ReferencePoint( this );
            
    }

    /** 
     * Überschreibt die Werte des Referenzpunkts mit denen des übergebenden 
     * 
     * @param aReferencePoint der Referenzpunkt mit den gewünschten Werten
     * 
     * @return dieser Referenzpunkt zum Verknüpfen von Methoden 
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
     * Methode zum setzten der Werte des Referenzpunktes.
     * 
     * Dieser Referenzpunkt wird immer mit dem Namen "NoFixedName" erstellt.
     * Er kann in Polarkoordinaten oder kartesischen Koordinaten angegeben werden. Der Bot ist dabei immer der Ursprung.
     * 
     * Bei Polarkoordinaten ist zu beachten, das der Winkel zwischen -180 und 180 Grad liegt und
     * die Distanz nicht kleiner als 0 sein darf.
     * 
     * @since 0.9
     * @param aFirstValue Polarkoordinaten: Die Entfernung des Bots zum Referenzpunkt. Darf nicht kleiner als Null sein.
     *                    kartesische Koordinaten: Der X-Wert des Punktes.
     * @param aSecondValue Polarkoordinaten: Der Winkel des Referenzpunktes zum Bot. Muss zwischen -180 und +180 liegen.
     *                     kartesische Koordinaten: Der Y-Wert des Punktes.
     * @param aPolarcoordinates true die übergebenden Werte sind Polarkoordinaten
     *                          false die übergebenden Werte sind kartesische Koordinaten
     * @exception IllegalArgumentException
     *              Wenn die Parameter nicht in den erlaubten Bereichen liegen.
     *              
     * @return dieser Referenzpunkt zum Verknüpfen von Methoden
     */
    public ReferencePoint set( double aFirstValue, double aSecondValue, boolean aPolarcoordinates ) throws IllegalArgumentException {
        
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
     * Vergleicht diesen Referenzpunkt mit dem übergebenden Referenzpunkt und 
     * benutzt dabei das Epsilon zum festlegen der gewünschten Schärfe.
     * 
     * @param aReferencePoint der zu vergleichende Referenzpunkt
     * @param aEpsilon die gewünschte Schärfe ( 0 bedeute absolut gleich )
     * 
     * @return ob die Referenzpunkte gleich sind. 
     */
    public boolean epsilonEquals ( ReferencePoint aReferencePoint, double aEpsilon )
    {

        return aReferencePoint != null
                && (Math.abs(aReferencePoint.getXOfPoint() - mX) <= aEpsilon)
                && (Math.abs(aReferencePoint.getYOfPoint() - mY) <= aEpsilon);

    }
    
    /** 
     * Vergleicht diesen Referenzpunkt mit den übergebenden X- und Y-Koordinaten eines Referenzpunkt und 
     * benutzt dabei das Epsilon zum festlegen der gewünschten Schürfe.
     * 
     * @param aX der X-Wert des Referenzpunktes
     * @param aY der Y-Wert des Referenzpunktes
     * @param aEpsilon die gewünschte Schärfe ( 0 bedeute absolut gleich )
     * 
     * @return ob die Referenzpunkte gleich sind. 
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
        return Double.doubleToLongBits(mY) == Double.doubleToLongBits(other.mY);
    }
    
    
    
}