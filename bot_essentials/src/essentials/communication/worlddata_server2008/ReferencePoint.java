package essentials.communication.worlddata_server2008;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

public class ReferencePoint {
	
    @XmlType(name = "id")
    @XmlEnum
    public enum ReferencePointName {
        
        @XmlEnumValue("bottom_center") NoFixedName( -1 ),
        @XmlEnumValue("bottom_center") CenterLineBottom( 0 ),
        @XmlEnumValue("bottom_left_corner") YellowFieldCornerBottom( 1 ),
        @XmlEnumValue("bottom_left_goal") YellowPenaltyAreaFrontBottom( 2 ),
        @XmlEnumValue("bottom_left_pole") YellowGoalCornerBottom( 3 ),
        @XmlEnumValue("bottom_left_small_area") YellowGoalAreaFrontBottom( 4 ),
        @XmlEnumValue("bottom_right_corner") BlueFieldCornerBottom( 5 ),
        @XmlEnumValue("bottom_right_goal") BluePenaltyAreaFrontBottom( 6 ),
        @XmlEnumValue("bottom_right_pole") BlueGoalCornerBottom( 7 ),
        @XmlEnumValue("bottom_right_small_area") BlueGoalAreaFrontBottom( 8 ),
        @XmlEnumValue("middle_center") FieldCenter( 9 ),
        @XmlEnumValue("top_center") CenterLineTop( 10 ),
        @XmlEnumValue("top_left_corner") YellowFieldCornerTop( 11 ),
        @XmlEnumValue("top_left_goal") YellowPenaltyAreaFrontTop( 12 ),
        @XmlEnumValue("top_left_pole") YellowGoalCornerTop( 13 ),
        @XmlEnumValue("top_left_small_area") YellowGoalAreaFrontTop( 14),
        @XmlEnumValue("top_right_corner") BlueFieldCornerTop( 15 ),
        @XmlEnumValue("top_right_goal") BluePenaltyAreaFrontTop( 16 ),
        @XmlEnumValue("top_right_pole") BlueGoalCornerTop( 17 ),
        @XmlEnumValue("top_right_small_area") BlueGoalAreaFrontTop( 18 );
        
        private final int mPosition;
        
        private ReferencePointName( int aPosition ) {

            mPosition = aPosition;
            
        }

        public int getPosition(){
            
            return mPosition;
            
        }
        
    }
    
	@XmlElement(name="id")
	ReferencePointName mPointName;
	@XmlElement(name="dist")
	double mDistanceToPoint;
	@XmlElement(name="angle")
	double mAngleToPoint;
    
	/**
     * Der Constructor fuer einen Referenzpunkt auf dem Spielfeld.
     * 
     * Dieser Referenzpunkt wird immer mit dem Namen "NoFixedName" und dem Winkel und der Distanz
     * zu dem Bot erstellt. Dabei ist zu beachten, das der Winkel zwischen -180 und 180 Grad liegt und
     * die Distanz nicht kleiner als 0 sein darf.
     * 
     * @since 0.9
     * @param aDistanceToPoint Die Entfernung des Bots zum Refernzpunkt. Kann nicht kleiner als Null sein.
     * @param aAngleToPoint Der Winkel des Referenzpunktes zum Bot. Muss zwischen -180 und +180 liegen.
     * @exception IllegalArgumentException
     *              Wenn die Parameter nicht in den erlaubten Bereichen liegen.
     */
	public ReferencePoint( double aDistanceToPoint, double aAngleToPoint ) throws IllegalArgumentException{
	    
	    this.mPointName = ReferencePointName.NoFixedName;
	    if( aDistanceToPoint < 0 ){
	        throw new IllegalArgumentException(" Distance can not be smaller than 0! Distance:" + aDistanceToPoint + " Angle: " + aAngleToPoint);
	    }
	    this.mDistanceToPoint = aDistanceToPoint;
	    if( mAngleToPoint < -180 || mAngleToPoint > 180 ){
            throw new IllegalArgumentException(" Angle must be between -180 and +180! Distance:" + aDistanceToPoint + " Angle: " + aAngleToPoint);
        }
	    this.mAngleToPoint = aAngleToPoint;
	    
	}
	
    public ReferencePointName getPointName() {
        return mPointName;
    }
    
    public double getDistanceToPoint() {
        return mDistanceToPoint;
    }
    
    public double getAngleToPoint() {
        return mAngleToPoint;
    }
    
    public void setPointName( ReferencePointName aPointName ) {
    	mPointName = aPointName;
    }
    
    public void setDistanceToPoint( double aDistance ) {
    	mDistanceToPoint = aDistance;
    }
    
    public void setAngelToPoint( double aAngle ) {
    	mAngleToPoint = aAngle;
    }
    
	
}