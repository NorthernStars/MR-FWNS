package mrlib.core;

import essentials.communication.Action;
import essentials.communication.action_server2008.Movement;
import essentials.communication.worlddata_server2008.BallPosition;
import essentials.communication.worlddata_server2008.FellowPlayer;
import essentials.communication.worlddata_server2008.ReferencePoint;

/**
 * Includes static functions for agent movement.
 * @author Hannes Eilers
 * @version 1.0
 *
 */
public class MoveLib {
	
    /**
     * Angle to {@link ReferencePoint}, below that the agent doesn't turn.
     */
    public static final double moveAngleNoTurn = 10.0;

    /**
     * Angle to {@link ReferencePoint}, below that the agent should move and turn,
     * depending on the value of the angle to the {@link ReferencePoint}.
     * This value has to bee higher than {@link #moveAngleNoTurn}.
     */
    public static final double moveAngleTurnAndMove = 60.0;

    /**
     * Angle to {@link ReferencePoint}, below that the agent should turn with 25% speed only.
     */
    public static final double turnAngleSlowSpeed = 25.0;



    /**
     * Running to a {@link ReferencePoint}.
     * @param aRefPoint	{@link ReferencePoint}
     * @return Action	{@link Action}
     */
    public static Action runTo( ReferencePoint aRefPoint ){
            return runTo( aRefPoint.getAngleToPoint() );
    }

    /**
     * Running to a {@link FellowPlayer}.
     * @param aFellowPlayer	{@link FellowPlayer}
     * @return Action		{@link Action}
     */
    public static Action runTo( FellowPlayer aFellowPlayer ) {
            return runTo( aFellowPlayer.getAngleToPlayer() );
    }

    /**
     * Running to a {@link BallPosition}
     * @param aBallPosition	{@link BallPosition}
     * @return Action		{@link Action}
     */
    public static Action runTo( BallPosition aBallPosition ) {
            return runTo( aBallPosition.getAngleToBall() );
    }
	
    /**
     * Let the agent run into an specific direction.
     * @param vAngle	{@link Double} direction angle to run to in degree (-180 to 180)
     * @return Action	{@link Action}
     */
    public static Action runTo( double vAngle ) {

        Action returnedAction = runForward(vAngle);
        if (returnedAction != Movement.NO_MOVEMENT ){
            return returnedAction;
        }

        returnedAction = runBackward(vAngle);
        if (returnedAction != Movement.NO_MOVEMENT){
            return returnedAction;
        }

        returnedAction = runOnPlace(vAngle);
        if (returnedAction != Movement.NO_MOVEMENT){
            return returnedAction;
        }

        return Movement.NO_MOVEMENT;

    }

    private static Action runForward( double vAngle)
    {
        // no turn, moving fwd
        if( vAngle >= -moveAngleNoTurn && vAngle <= moveAngleNoTurn ){
            return new Movement( 100, 100 );
        }

        // turn depending on angle, moving fwd
        if( vAngle > -moveAngleTurnAndMove && vAngle < -moveAngleNoTurn ){
            return new Movement( 100, 100 + (int) vAngle );
        }

        // turn depending on angle, moving fwd
        if( vAngle > moveAngleNoTurn && vAngle < moveAngleTurnAndMove ){
            return new Movement( 100 - (int) vAngle, 100 );
        }

        return Movement.NO_MOVEMENT;
    }

    private static Action runBackward( double vAngle)
    {
        // turn depending on angle, moving bwd
        if( vAngle > -(180-moveAngleNoTurn) && vAngle < -(180-moveAngleTurnAndMove) ){
            return new Movement( -100, 100 + (int) vAngle );
        }

        // turn depending on angle, moving bwd
        if( vAngle > (180-moveAngleTurnAndMove) && vAngle < (180-moveAngleNoTurn) ){
            return new Movement( 100 - (int) vAngle, -100 );
        }

        // no turn, moving bwd
        if( ( vAngle >= (180-moveAngleNoTurn) && vAngle <= 180 )
                || ( vAngle <= -(180-moveAngleNoTurn) && vAngle >= -180 ) ){
            return new Movement( -100, -100 );
        }

        return Movement.NO_MOVEMENT;
    }

    private static Action runOnPlace (double vAngle)
    {
        // turn on place left
        if( vAngle >= -(180-moveAngleTurnAndMove) && vAngle <= -moveAngleTurnAndMove ){
            return new Movement( 100, -100 );
        }

        // turn on place right
        if( vAngle >= moveAngleTurnAndMove && vAngle <= (180-moveAngleTurnAndMove) ){
            return new Movement( -100, 100 );
        }

        return Movement.NO_MOVEMENT;
    }
	
    /**
     * Turn to a {@link ReferencePoint}.
     * @param vRefPoint	{@link ReferencePoint}
     * @return			{@link Action}
     */
    public static Action turnTo(ReferencePoint vRefPoint){
            return turnTo(vRefPoint.getAngleToPoint());
    }

    /**
     * Turn to {@link FellowPlayer}
     * @param vFellowPlayer	{@link FellowPlayer}
     * @return				{@link Action}
     */
    public static Action turnTo(FellowPlayer vFellowPlayer){
            return turnTo(vFellowPlayer.getAngleToPlayer());
    }

    /**
     * Turn run to {@link BallPosition}.
     * @param vBallPos	{@link BallPosition}
     * @return			{@link Action}
     */
    public static Action turnTo(BallPosition vBallPos){
            return turnTo(vBallPos.getAngleToBall());
    }
	
    /**
     * Turn towards an angle (no movement).
     * @param vAngle	{@link Double} angle to turn to. (-180 to 180)
     * @return			{@link Action}
     */
    public static Action turnTo(double vAngle){

        Action returnedAction = turnRight(vAngle);
        if (returnedAction != Movement.NO_MOVEMENT)
        {
            return returnedAction;
        }

        returnedAction = turnLeft(vAngle);
        if (returnedAction != Movement.NO_MOVEMENT)
        {
            return returnedAction;
        }


        return Movement.NO_MOVEMENT;

    }

    private static Action turnRight(double vAngle)
    {
        // 100% speed turn right
        if(vAngle <= -moveAngleTurnAndMove && vAngle >= -(180-moveAngleTurnAndMove)){
            return new Movement(100,-100);
        }

        // 50% speed turn right
        if((vAngle < -turnAngleSlowSpeed && vAngle >= -moveAngleTurnAndMove)
                || vAngle > (180-moveAngleTurnAndMove) && vAngle < (180-turnAngleSlowSpeed)){
            return new Movement(50,-50);
        }

        // 25% speed turn right
        if((vAngle < -moveAngleNoTurn && vAngle >= -turnAngleSlowSpeed)
                || vAngle > (180-turnAngleSlowSpeed) && vAngle < (180-moveAngleNoTurn)){
            return new Movement(25,-25);
        }

        return Movement.NO_MOVEMENT;
    }

    private static Action turnLeft(double vAngle)
    {
        // 100% speed turn left
        if(vAngle >= moveAngleTurnAndMove && vAngle <= (180-moveAngleTurnAndMove)){
            return new Movement(-100,100);
        }

        // 50% speed turn left
        if((vAngle >= turnAngleSlowSpeed && vAngle <= moveAngleTurnAndMove)
                || vAngle < -(180-moveAngleTurnAndMove) && vAngle > -(180-turnAngleSlowSpeed)){
            return new Movement(-50,50);
        }

        // 25% speed turn left
        if((vAngle >= moveAngleNoTurn && vAngle <= turnAngleSlowSpeed)
                || vAngle < -(180-turnAngleSlowSpeed) && vAngle > -(180-moveAngleNoTurn)){
            return new Movement(-25,25);
        }

        return Movement.NO_MOVEMENT;
    }
    
    /**
     * Getter for moveAngleNoTurn
     * 
     * @return moveAngleNoTurn
     */    
    public static double getMoveAngleNoTurn()
    {
        return moveAngleNoTurn;
    }

    /**
     * Getter for moveAngleTurnAndMove
     * 
     * @return moveAngleTurnAndMove
     */
    public static double getMoveAngleTurnAndMove()
    {
        return moveAngleTurnAndMove;
    }

    /**
     * Getter for turnAngleSlowSpeed
     * 
     * @return turnAngleSlowSpeed
     */
    public static double getTurnAngleSlowSpeed()
    {
        return turnAngleSlowSpeed;
    }
	
}
