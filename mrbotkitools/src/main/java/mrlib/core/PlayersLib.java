package mrlib.core;

import java.util.ArrayList;
import java.util.List;

import essentials.communication.worlddata_server2008.BallPosition;
import essentials.communication.worlddata_server2008.FellowPlayer;
import essentials.communication.worlddata_server2008.RawWorldData;
import essentials.communication.worlddata_server2008.ReferencePoint;
import essentials.core.BotInformation;
import essentials.core.BotInformation.GamevalueNames;

/**
 * Includes static functions to get or calculate positions of players or the ball on the playing field.
 * @version 1.0
 *  
 * @author Louis Jorswieck
 * 
 */

public class PlayersLib {

	 /**
     * Returns nearest Teammate, doesn't check if something is inbetween or not.
     * @param vWorldState 	{@link RawWorldData} from the Server
     * @param mSelf 		{@link BotInformation} of the agent
     * @return 				{@link FellowPlayer} with the shortest distance to oneself
     */
	public static FellowPlayer getNearestMate(RawWorldData vWorldState, BotInformation mSelf){
        List<FellowPlayer> vTeamMates = vWorldState.getListOfTeamMates();
        
		FellowPlayer nearestMate = null;
    	for( FellowPlayer a : vTeamMates){
			if( nearestMate == null || a.getDistanceToPlayer() < nearestMate.getDistanceToPlayer()){
				nearestMate = a;
			}                    				
		
		}

		return nearestMate;
	}
	
	 /**
     * Returns nearest Opponent, doesn't check if something is inbetween or not.
     * @param vWorldState 	{@link RawWorldData} from the Server
     * @return 				{@link FellowPlayer} with the shortest distance to oneself
     */
	public static FellowPlayer getNearestOpponent(RawWorldData vWorldState){
        List<FellowPlayer> vOpponents = vWorldState.getListOfOpponents();
        
		FellowPlayer nearestOpponent = null;
    	for( FellowPlayer a : vOpponents){
			if( nearestOpponent == null || a.getDistanceToPlayer() < nearestOpponent.getDistanceToPlayer()){
				nearestOpponent = a;
			}                    				
		
		}

		return nearestOpponent;
	}
	
	
	
	 /**
     * Check if Enemy is within 2 x kick range of the bot
     * @param vWorldState	{@link RawWorldData} from the Server
     * @param mSelf			{@link BotInformation} of the agent
     * @return 				{@code true } if there is an enemy in 2x kickrange, {@code false} othwise.
     */
	public static boolean isEnemyAround(RawWorldData vWorldState, BotInformation mSelf){
		List<FellowPlayer> vOpponents = vWorldState.getListOfOpponents();
		for( FellowPlayer p : vOpponents){
			if(p.getDistanceToPlayer() <= 2*mSelf.getGamevalue( GamevalueNames.KickRange )){
				return true;
			}
		}
	
		return false;
	}
	 /**
     * Check if Enemy is within a specific range
     * @param vWorldState 	{@link RawWorldData} from the Server
     * @param mSelf			{@link BotInformation} of the agent
     * @param range			{@link Double} range in mm
     * @return 				{@code true} if there is an enemy in a specific range around the agent,
     * 						{@code false} othwerise
     */
	public static boolean isEnemyAround(RawWorldData vWorldState, BotInformation mSelf, double range){
		List<FellowPlayer> vOpponents = vWorldState.getListOfOpponents();
		for( FellowPlayer p : vOpponents){
			if(p.getDistanceToPlayer() <= range ){
				return true;
			}
		}
	
		return false;
	}
	
	/**
     * Check if Enemy is around a specific teammate in range of 2x kick range.
     * @param vWorldState	{@link RawWorldData} from the Server
     * @param mSelf			{@link BotInformation} of the agent
     * @param teamMate		{@link FellowPlayer} that should be checked upon
     * @return 				{@code true} if there is an enemy in range around the agent,
     * 						{@code false} otherwise
     */
	public static boolean isEnemyAroundMate(RawWorldData vWorldState, BotInformation mSelf, FellowPlayer teamMate){
		List<FellowPlayer> vOpponents = vWorldState.getListOfOpponents();
		for( FellowPlayer p : vOpponents){
			if(PlayersLib.getDistanceBetweenTwoPlayers(p, teamMate) <= 2*mSelf.getGamevalue( GamevalueNames.KickRange ) ){
				return true;
			}
		}
		return false;
	}
	
	/**
     * Return the distance between two {@link FellowPlayer}
     * 
     * @param p				The {@link FellowPlayer} whose distance to the other {@link FellowPlayer} should be calculated
     * @param otherPlayer	The {@link FellowPlayer} that should be checked upon
     * @return 				Distance from {@code p} to {@code otherPLayer} as {@link Double}
     */
	private static double getDistanceBetweenTwoPlayers(FellowPlayer p,FellowPlayer otherPlayer) {
		double a, b, wa, wb;
        
        if( p.getAngleToPlayer() > otherPlayer.getAngleToPlayer() ){
            
            a = p.getDistanceToPlayer();
            wa = p.getAngleToPlayer();
            b = otherPlayer.getDistanceToPlayer();
            wb = otherPlayer.getAngleToPlayer();
            
        } else {

            a = otherPlayer.getDistanceToPlayer();
            wa = otherPlayer.getAngleToPlayer(); 
            b = p.getDistanceToPlayer();
            wb = p.getAngleToPlayer();
            
        }
        double c = Math.sqrt( a*a + b*b - 2 * a * b * Math.cos(Math.toRadians(Math.abs(wa - wb))));
    	
		return c;
	}
	
	/**
     * Return the distance between specific {@link FellowPlayer} and {@link BallPosition}
     * 
     * @param p			The {@link FellowPlayer} whose distance to the ball should be calculated
     * @param ballPos 	The {@link BallPosition} from the {@link RawWorldData}
     * @return 			Distance from {@code p} to {@code ballPos} as {@link Double}
     */
	public static double getDistanceBetweenPlayerAndBall(FellowPlayer p, BallPosition ballPos) {
		double a, b, wa, wb;
        
        if( p.getAngleToPlayer() > ballPos.getAngleToBall() ){
            
            a = p.getDistanceToPlayer();
            wa = p.getAngleToPlayer();
            b = ballPos.getDistanceToBall();
            wb = ballPos.getAngleToBall();
            
        } else {

            a = ballPos.getDistanceToBall();
            wa = ballPos.getAngleToBall(); 
            b = p.getDistanceToPlayer();
            wb = p.getAngleToPlayer();
            
        }
        double c = Math.sqrt( a*a + b*b - 2 * a * b * Math.cos(Math.toRadians(Math.abs(wa - wb))));
    	
		return c;
	}
	
	/**
     * Return the nearest teammate without enemy around.
     * 
     * @param vWorldState	{@link RawWorldData} from the Server
     * @param mSelf 		{@link BotInformation} of the agent
     * @return 				Teammate {@link FellowPlayer} where no enemy is around in 2x kick range
     */
	public static FellowPlayer getNearestMateWithoutEnemyAround(RawWorldData vWorldState, BotInformation mSelf){
        List<FellowPlayer> vTeamMates = vWorldState.getListOfTeamMates();
		FellowPlayer nearestMate = null;
		
    	for( FellowPlayer p : vTeamMates){
    		if( nearestMate == null || p.getDistanceToPlayer() < nearestMate.getDistanceToPlayer() ){
    			if( !PlayersLib.isEnemyAroundMate(vWorldState, mSelf, p)
    					&& p.getId() != mSelf.getVtId() ) {
    				nearestMate = p;
    			}
    		}
    	}
		return nearestMate;
	}
	
	/**
     * Selects teammate where enemies around are furthest away from teammate.
     * 
     * @param vWorldState	{@link RawWorldData} from the Server
     * @param mSelf			{@link BotInformation} of the agent
     * @return 				Teammate {@link FellowPlayer} with a enemy around but the enemy furthest away
     */
	public static FellowPlayer getMateWithEnemyNearButFurthestAway(RawWorldData vWorldState, BotInformation mSelf){
        List<FellowPlayer> vOpponents = vWorldState.getListOfOpponents();
        List<FellowPlayer> vTeamMates = vWorldState.getListOfTeamMates();
        List<FellowPlayer> nearestEnemys = new ArrayList<FellowPlayer>();
		FellowPlayer bestMate = null;
		FellowPlayer nearestOpponent = null;
		double dist_old = 0;
		int counter=0, bestIndex = 0;
		
		for( FellowPlayer a : vTeamMates){
			for( FellowPlayer p : vOpponents){
    					
    					if (dist_old == 0 || PlayersLib.getDistanceBetweenTwoPlayers(p, a) < dist_old){
    						nearestOpponent = p;
    						dist_old = PlayersLib.getDistanceBetweenTwoPlayers(p, a);
    					}
    		}
    		nearestEnemys.add(nearestOpponent);
			counter++;
    	}
		counter = 0;
    	for ( FellowPlayer a: vTeamMates){
    		if ( (bestMate == null)
    				|| PlayersLib.getDistanceBetweenTwoPlayers(a, nearestEnemys.get(counter)) > PlayersLib.getDistanceBetweenTwoPlayers(bestMate, nearestEnemys.get(bestIndex))){
    			bestMate = a;
    			bestIndex = counter;
    		}
    		counter++;
    	}
		
		return bestMate;
	}
	
	/**
     * Check if the agent is the nearest to the ball.
     * 
     * @param vWorldState	{@link RawWorldData} from the Server
     * @param ballPos 		{@link BallPosition} of the ball
     * @param mSelf			{@link BotInformation} of the agent
     * @return 				{@code true} if bot is the closest to the ball, {@code fale} otherwise.
     */
	public static boolean amINearestToBall(RawWorldData vWorldState, BallPosition ballPos, BotInformation mSelf){
		List<FellowPlayer> vTeamMates = vWorldState.getListOfTeamMates();
		vTeamMates.add(new FellowPlayer(mSelf.getVtId(), mSelf.getBotname(), true, 0, 0, 0) );
		
		FellowPlayer closest_player = null;
		for ( FellowPlayer a: vTeamMates){
			if(closest_player == null
					|| PlayersLib.getDistanceBetweenPlayerAndBall(a, ballPos) < PlayersLib.getDistanceBetweenPlayerAndBall(closest_player, ballPos)){
				closest_player = a;
			}
		}
		
		if(closest_player.getId() == mSelf.getVtId()){
			return true;
		}
		
		return false;
	}
	
	/**
	 * Get {@link FellowPlayer} teammate, that is nearest to ball
	 * @param aWorldState	{@link RawWorldData} from server.
	 * @return				{@link FellowPlayer} that is nearest to ball,
	 * 						{@code null} if no player found.
	 */
	public static FellowPlayer nearestMateToBall(RawWorldData aWorldState){
		FellowPlayer nearestPlayer = null;
		BallPosition ballPos = aWorldState.getBallPosition();
		double distToBall = -1;
		
		// check teammates
		for( FellowPlayer p : aWorldState.getListOfTeamMates() ){
			// calculate distance to from player to ball
			double d = p.sub( ballPos ).getDistanceToPoint();
			
			// check if player if nearest to ball
			if( d < distToBall || distToBall < 0 ){
				distToBall = d;
				nearestPlayer = p;
			}
		}
		
		return nearestPlayer;
	}
	
	/**
	 * Get {@link FellowPlayer} opponent, that is nearest to ball
	 * @param aWorldState	{@link RawWorldData} from server.
	 * @return				{@link FellowPlayer} that is nearest to ball,
	 * 						{@code null} if no player found.
	 */
	public static FellowPlayer nearestOpponentToBall(RawWorldData aWorldState){
		FellowPlayer nearestPlayer = null;
		BallPosition ballPos = aWorldState.getBallPosition();
		double distToBall = -1;
		
		// check teammates
		for( FellowPlayer p : aWorldState.getListOfOpponents() ){
			// calculate distance to from player to ball
			double d = p.sub( ballPos ).getDistanceToPoint();
			
			// check if player if nearest to ball
			if( d < distToBall || distToBall < 0 ){
				distToBall = d;
				nearestPlayer = p;
			}
		}
		
		return nearestPlayer;
	}
	
	/**
	 * Get {@link FellowPlayer} (teammate or opponent), that is nearest to ball
	 * @param aWorldState	{@link RawWorldData} from server.
	 * @return				{@link FellowPlayer} that is nearest to ball,
	 * 						{@code null} if no player found.<br>
	 * 						If opponent and teammate have the same distance to ball, opponent is returned. 
	 */
	public static FellowPlayer nearestPlayerToBall(RawWorldData aWorldState){
		FellowPlayer teammate = nearestMateToBall(aWorldState);
		FellowPlayer opponent = nearestOpponentToBall(aWorldState);
		
		if( teammate == null ){
			return opponent;
		}
		else if( opponent == null ){
			return teammate;
		}
		
		// caluclate distances
		BallPosition ballPos = aWorldState.getBallPosition();
		double dMate = teammate.sub( ballPos ).getDistanceToPoint();
		double dOpponent = opponent.sub( ballPos ).getDistanceToPoint();
		
		return ( dMate < dOpponent ? teammate : opponent );
	}
	
	/**
	 * Return if an enemy has the same angle as a specific {@link ReferencePoint} +- {@code angle} in {@link MoveLib}
	 * 
	 * @param aWorldState	{@link RawWorldData} from the server
	 * @param refPoint		{@link ReferencePoint}
	 * @param angle			{@link Double} angle
	 * @returns 			{@code true} if enemy is in way from agent to {@code refPoint}, {@code false} othweise.
	 * */
	public static boolean isEnemyOnWayToRefPoint(RawWorldData aWorldState, ReferencePoint refPoint, double angle){		
		// prepare angles
		double vAngleLeft = refPoint.getAngleToPoint()-angle;
		double vAngleRight = refPoint.getAngleToPoint()+angle;
		
		// check if angles exceed maximum angles
		
		List<FellowPlayer> vOpponents = aWorldState.getListOfOpponents();
		for ( FellowPlayer a: vOpponents){
			
			double vAngleToPlayer = a.getAngleToPlayer();
			
			if( vAngleToPlayer >= vAngleLeft && vAngleToPlayer <= vAngleRight ){
					return true;
			}
			
			if( vAngleRight > 180.0 && (vAngleToPlayer <= (-360+vAngleRight) || vAngleToPlayer >= vAngleLeft) ){
				return true;
			}
			
			if( vAngleLeft < -180.0 && (vAngleToPlayer >= (360+vAngleLeft) || vAngleToPlayer <= vAngleRight ) ){
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Return if an enemy is in corridor between two {@link ReferencePoint}s.
	 * 
	 * @param aWorldState	{@link RawWorldData} from the server
	 * @param refPoint1		First {@link ReferencePoint}
	 * @param refPoint2		Second {@link ReferencePoint}
	 * @returns 			{@code true} if an enemy is in angle between {@code refPoint1} and {@code RefPoint2}, {@code false} otherwise.
	 * */
	public static boolean isEnemyInCorridorBetweenTwoRefPoints(RawWorldData aWorldState, ReferencePoint refPoint1, ReferencePoint refPoint2){
		double ref1Angle = 0;
		double ref2Angle = 0;
		List<FellowPlayer> vOpponents = aWorldState.getListOfOpponents();
		if(refPoint1.getAngleToPoint() > refPoint2.getAngleToPoint()){
			ref1Angle = refPoint1.getAngleToPoint();
			ref2Angle = refPoint2.getAngleToPoint();
		}
		else {
			ref1Angle = refPoint2.getAngleToPoint();
			ref2Angle = refPoint1.getAngleToPoint();
		}
		if(Math.abs(ref1Angle-ref2Angle) > 180){
			for ( FellowPlayer a: vOpponents){
				if(a.getAngleToPlayer() < ref2Angle && a.getAngleToPlayer() > -180 || a.getAngleToPlayer() > ref1Angle && a.getAngleToPlayer() < 180){
					return true;
				}
				else{
					return false;
				}
			}
		}
		else{
			for ( FellowPlayer a: vOpponents){
				if(a.getAngleToPlayer() > ref2Angle && a.getAngleToPlayer() < ref1Angle){
					return true;
				}
				else{
				return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Return if an specific enemy is corridor of two {@link ReferencePoint}s.
	 * 
	 * @param enemy 	{@link FellowPlayer} who should be checked upon
	 * @param refPoint1	First {@link ReferencePoint}
	 * @param refPoint2 Seconds {@link ReferencePoint}
	 * @returns 		{@code true} if {@code enemy} is in angle between {@code refPoint1} and {@code refPoint2}, {@code false} otherwise.
	 * */
	public static boolean isSpecificEnemyInAngleBetweenTwoRefPoints(FellowPlayer enemy, ReferencePoint refPoint1, ReferencePoint refPoint2){
		double ref1Angle = 0;
		double ref2Angle = 0;
		
		if(refPoint1.getAngleToPoint() > refPoint2.getAngleToPoint()){
			ref1Angle = refPoint1.getAngleToPoint();
			ref2Angle = refPoint2.getAngleToPoint();
		}
		else {
			ref1Angle = refPoint2.getAngleToPoint();
			ref2Angle = refPoint1.getAngleToPoint();
		}
		if(Math.abs(ref1Angle-ref2Angle) > 180){
			
				if(enemy.getAngleToPlayer() < ref2Angle && enemy.getAngleToPlayer() > -180 || enemy.getAngleToPlayer() > ref1Angle && enemy.getAngleToPlayer() < 180){
					return true;
				}
				else{
					return false;
				}
			
		}
		else{
			
				if(enemy.getAngleToPlayer() > ref2Angle && enemy.getAngleToPlayer() < ref1Angle){
					return true;
				}
				else{
				return false;
				}
			
		}
		
	}
	
	/**
	 * Check if any of the teammates is in the kick range around the current {@link BallPosition} of the ball
	 * 
	 * @param aWorldState	{@link RawWorldData} from server
	 * @param botInfo		{@link BotInformation} of the agent
	 * @returns 			{@code true} if a teammate is in kick range of ball, {@code false} otherwise.
	 * */
	public static boolean hasMateTheBall(RawWorldData aWorldState, BotInformation botInfo){
		BallPosition ball = aWorldState.getBallPosition();
		
		if( ball != null ){
			List<FellowPlayer> vTeamMates = aWorldState.getListOfTeamMates();
			double dist = 0;
			
			for ( FellowPlayer a: vTeamMates){
				dist = PlayersLib.getDistanceBetweenPlayerAndBall(a, ball);
				if(dist < botInfo.getGamevalue(GamevalueNames.KickRange)){
					return true;
				}
				else{
					return false;
				}
			}
		}
		
		return false;
		
	}
	
	/**
	 * Check if any opponents is in 2x kick range of current {@link BallPosition} of the ball.
	 * 
	 * @param aWorldState	{@link RawWorldData} from server
	 * @param botInfo		{@link BotInformation} of the agent
	 * @returns 			{@code true} if any opponent is 2x kick range away from ball, {@code false} otherwise.
	 * */
	public static boolean isEnemyNearBall(RawWorldData aWorldState, BotInformation botInfo){
		BallPosition ball = aWorldState.getBallPosition();
		List<FellowPlayer> vOpponents = aWorldState.getListOfOpponents();
		double dist = 0;
		
		for ( FellowPlayer a: vOpponents){
			dist = PlayersLib.getDistanceBetweenPlayerAndBall(a, ball);
			if(dist < botInfo.getGamevalue(GamevalueNames.KickRange)*2){
				return true;
			}
			else{
				return false;
			}
		}
		
		return false;
		
	}
	
	/**
     * Return the distance between {@link FellowPlayer} and {@link ReferencePoint}.
     * 
     * @param p			{@link FellowPlayer} whose distance to the point should be calculated
     * @param refPoint	{@link ReferencePoint} to which the distance of the player should be calculated
     * @return 			Distance between {@code p} and {@code refPoint} as {@link Double}.
     * @deprecated		Use {@code sub(aReferencePoint)} function of {@link ReferencePoint}
     * 					or {@link FellowPlayer} to calculate distance.
     */
	@Deprecated
	public static double getDistanceBetweenPlayerAndPoint(FellowPlayer player,ReferencePoint refPoint) {
//		double a, b, wa, wb;
//        
//        
//        a = player.getDistanceToPoint();
//        wa = player.getAngleToPoint();
//        b = refPoint.getDistanceToPoint();
//        wb = refPoint.getAngleToPoint();
//         
//       
//        double c = Math.sqrt( a*a + b*b - 2 * a * b * Math.cos(Math.toRadians(Math.abs(wa - wb))));
//    	
//       return c;
		return player.sub( refPoint ).getDistanceToPoint();
	}
}
