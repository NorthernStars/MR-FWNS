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
        if( mAngleToPoint != Double.NaN && mDistanceToPoint != Double.NaN ){
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
            if( mAngleToPoint < -180 || mAngleToPoint > 180 ){
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
//
//    /** Adds the given components to this ReferencePoint
//     * @param x The x-component
//     * @param y The y-component
//     * @return This ReferencePoint for chaining */
//    public ReferencePoint add (double x, double y) {
//            this.mX += x;
//            this.mY += y;
//            return this;
//    }
//    
//    /** Normalizes this ReferencePoint. Does nothing if it is zero.
//     * @return This ReferencePoint for chaining */
//    public ReferencePoint nor () {
//            double len = len();
//            if (len != 0) {
//                    mX /= len;
//                    mY /= len;
//            }
//            return this;
//    }
//
//    /** @param v The other ReferencePoint
//     * @return The dot product between this and the other ReferencePoint */
//    public double dot (ReferencePoint v) {
//            return mX * v.mX + mY * v.mY;
//    }
//
    /** Multiplies this ReferencePoint by a scalar
     * @param scalar The scalar
     * @return This ReferencePoint for chaining */
    public ReferencePoint multiply( double scalar ) {
            mX *= scalar;
            mY *= scalar;
            return set( mX, mY, false );
    }
//
//    /** Multiplies this ReferencePoint by a scalar
//     * @return This ReferencePoint for chaining */
//    public ReferencePoint scl (double x, double y) {
//            this.mX *= x;
//            this.mY *= y;
//            return this;
//    }
//    
//    /** Multiplies this ReferencePoint by a ReferencePoint
//     * @return This ReferencePoint for chaining */
//    public ReferencePoint scl (ReferencePoint v) {
//            this.mX *= v.mX;
//            this.mY *= v.mY;
//            return this;
//    }
//
//    public ReferencePoint div (double value) {
//            return this.scl(1 / value);
//    }
//
//    public ReferencePoint div (double vx, double vy) {
//            return this.scl(1 / vx, 1 / vy);
//    }
//
//    public ReferencePoint div (ReferencePoint other) {
//            return this.scl(1 / other.mX, 1 / other.mY);
//    }
//
//    /** @param v The other ReferencePoint
//     * @return the distance between this and the other ReferencePoint */
//    public double dst (ReferencePoint v) {
//            final double x_d = v.mX - mX;
//            final double y_d = v.mY - mY;
//            return (double)Math.sqrt(x_d * x_d + y_d * y_d);
//    }
//
//    /** @param x The x-component of the other ReferencePoint
//     * @param y The y-component of the other ReferencePoint
//     * @return the distance between this and the other ReferencePoint */
//    public double dst (double x, double y) {
//            final double x_d = x - this.mX;
//            final double y_d = y - this.mY;
//            return (double)Math.sqrt(x_d * x_d + y_d * y_d);
//    }
//
//    /** @param v The other ReferencePoint
//     * @return the squared distance between this and the other ReferencePoint */
//    public double dst2 (ReferencePoint v) {
//            final double x_d = v.mX - mX;
//            final double y_d = v.mY - mY;
//            return x_d * x_d + y_d * y_d;
//    }
//
//    /** @param x The x-component of the other ReferencePoint
//     * @param y The y-component of the other ReferencePoint
//     * @return the squared distance between this and the other ReferencePoint */
//    public double dst2 (double x, double y) {
//            final double x_d = x - this.mX;
//            final double y_d = y - this.mY;
//            return x_d * x_d + y_d * y_d;
//    }
//    
//    /** Limits this ReferencePoint's length to given value
//     * @param limit Max length
//     * @return This ReferencePoint for chaining */
//    public ReferencePoint limit (double limit) {
//            if (len2() > limit * limit) {
//                    nor();
//                    scl(limit);
//            }
//            return this;
//    }
//    
//    /** Clamps this ReferencePoint's length to given value
//     * @param min Min length
//     * @param max Max length
//     * @return This ReferencePoint for chaining */
//    public ReferencePoint clamp (double min, double max) {
//            final double l2 = len2();
//            if (l2 == 0f)
//                    return this;
//            if (l2 > max * max)
//                    return nor().scl(max);
//            if (l2 < min * min)
//                    return nor().scl(min);
//            return this;
//    }
//
//    public String toString () {
//            return "[" + mX + ":" + mY + "]";
//    }
//
//    /** Substracts the other ReferencePoint from this ReferencePoint.
//     * @param x The x-component of the other ReferencePoint
//     * @param y The y-component of the other ReferencePoint
//     * @return This ReferencePoint for chaining */
//    public ReferencePoint sub (double x, double y) {
//            this.mX -= x;
//            this.mY -= y;
//            return this;
//    }
//
//    /** Calculates the 2D cross product between this and the given ReferencePoint.
//     * @param v the other ReferencePoint
//     * @return the cross product */
//    public double crs (ReferencePoint v) {
//            return this.mX * v.mY - this.mY * v.mX;
//    }
//
//    /** Calculates the 2D cross product between this and the given ReferencePoint.
//     * @param x the x-coordinate of the other ReferencePoint
//     * @param y the y-coordinate of the other ReferencePoint
//     * @return the cross product */
//    public double crs (double x, double y) {
//            return this.mX * y - this.mY * x;
//    }
//
//    /** @return the angle in degrees of this ReferencePoint (point) relative to the x-axis.
//     * Angles are towards the positive y-axis (typically counter-clockwise) and between 0 and 360. */
//    public double angle () {
//            double angle = Math.toDegrees( (double)Math.atan2(mY, mX) );
//            return angle;
//    }
//
//    /** Sets the angle of the ReferencePoint in degrees relative to the x-axis, towards the positive y-axis (typically counter-clockwise).
//     * @param degrees The angle to set. */
//    public ReferencePoint setAngle ( double degrees ) {
//            this.set(len(), 0f, false);
//            this.rotate(degrees);
//
//            return this;
//    }
//
//    /** Rotates the ReferencePoint2 by the given angle, counter-clockwise assuming the y-axis points up.
//     * @param degrees the angle in degrees */
//    public ReferencePoint rotate (double degrees) {
//            double rad = Math.toRadians( degrees );
//            double cos = (double)Math.cos(rad);
//            double sin = (double)Math.sin(rad);
//
//            double newX = this.mX * cos - this.mY * sin;
//            double newY = this.mX * sin + this.mY * cos;
//
//            this.mX = newX;
//            this.mY = newY;
//
//            return this;
//    }
//
//    /** Linearly interpolates between this ReferencePoint and the target ReferencePoint by alpha which is in the range [0,1]. The result is stored
//     * in this ReferencePoint.
//     *
//     * @param target The target ReferencePoint
//     * @param alpha The interpolation coefficient
//     * @return This ReferencePoint for chaining. */
//    public ReferencePoint lerp (ReferencePoint target, double alpha) {
//            final double invAlpha = 1.0f - alpha;
//            this.mX = (mX * invAlpha) + (target.mX * alpha);
//            this.mY = (mY * invAlpha) + (target.mY * alpha);
//            return this;
//    }
    
    /** 
     * Vergleicht diesen Referenzpunkt mit dem übergebenden Referenzpunkt und 
     * benutzt dabei das Epsilon zum festlegen der gewünschten Schärfe.
     * 
     * @param aReferencePoint der zu vergleichende Referenzpunkt
     * @param aEpsilon die gewünschte Schärfe ( 0 bedeute absolut gleich )
     * 
     * @return ob die Referenzpunkte gleich sind. 
     */
    public boolean epsilonEquals ( ReferencePoint aReferencePoint, double aEpsilon ) {
        
            if ( aReferencePoint == null ){
                return false;
            }
            
            if ( Math.abs( aReferencePoint.getXOfPoint() - mX ) > aEpsilon ){
                return false;
            }
            
            if ( Math.abs( aReferencePoint.getYOfPoint() - mY ) > aEpsilon ){
                return false;
            }
            
            return true;
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
    public boolean epsilonEquals ( double aX, double aY, double aEpsilon ) {
       
        if ( Math.abs( aX - mX ) > aEpsilon ){
            return false;
        }
        
        if ( Math.abs( aY - mY ) > aEpsilon ){
            return false;
        }
        
        return true;
    }

    @Override
    public String toString() {
        return "ReferencePoint [mPointName=" + mPointName + ", mDistanceToPoint=" + mDistanceToPoint
                + ", mAngleToPoint=" + mAngleToPoint + ", mX=" + mX + ", mY=" + mY + "]";
    }
    
}