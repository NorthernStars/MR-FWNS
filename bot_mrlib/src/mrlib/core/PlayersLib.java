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
 * !!! Needs verification still not fully tested and not final !!!
 *  
 * @author Louis Jorswieck
 * 
 */

public class PlayersLib {

	 /**
     * Returns nearest Teammate, doesn't check if something is inbetween or not
     * @param RawWorldData vWorldState, the complete Worlddata from the Server
     * @param BotInformation mSelf, the information of the bot
     * @return FellowPlayer nearestMate, Teammate with the shortest distance to oneself
     */
	public static FellowPlayer getNearestMate(RawWorldData vWorldState, BotInformation mSelf){
        List<FellowPlayer> vOpponents = vWorldState.getListOfOpponents();
        List<FellowPlayer> vTeamMates = vWorldState.getListOfTeamMates();
		FellowPlayer nearestMate = null;
    	for( FellowPlayer p : vOpponents){
    		if(p.getDistanceToPlayer() < 2*mSelf.getGamevalue( GamevalueNames.KickRange )){
    			for( FellowPlayer a : vTeamMates){
    				if( nearestMate == null || a.getDistanceToPlayer() < nearestMate.getDistanceToPlayer()){
    					nearestMate = a;
    				}                    				
    			
    			}
    			
    		}
    	}
		return nearestMate;
	}
	 /**
     * Checks if Enemy is within 2 x KickRange of the bot
     * @param RawWorldData vWorldState, the complete Worlddata from the Server
     * @param BotInformation mSelf, the information of the bot
     * @return true or false whether there is an enemy in 2x kickrange or not
     */
	public static boolean isEnemyAround(RawWorldData vWorldState, BotInformation mSelf){
		List<FellowPlayer> vOpponents = vWorldState.getListOfOpponents();
		for( FellowPlayer p : vOpponents){
			if(p.getDistanceToPlayer() < 2*mSelf.getGamevalue( GamevalueNames.KickRange )){
				return true;
			}
		}
	
		return false;
	}
	 /**
     * Checks if Enemy is within a specific range
     * @param RawWorldData vWorldState, the complete Worlddata from the Server
     * @param BotInformation mSelf, the information of the bot
     * @param double range, in mm
     * @return true or false if there is an enemy in a specific range around the bot
     */
	public static boolean isEnemyAround(RawWorldData vWorldState, BotInformation mSelf, double range){
		List<FellowPlayer> vOpponents = vWorldState.getListOfOpponents();
		for( FellowPlayer p : vOpponents){
			if(p.getDistanceToPlayer() < range ){
				return true;
			}
		}
	
		return false;
	}
	
	/**
     * Checks if Enemy is around a specific teammate of the FellowPlayer type in 2x KickRange
     * @param RawWorldData vWorldState, the complete worlddata from the Server
     * @param BotInformation mSelf, the information of the bot
     * @param FellowPlayer teamMate, the teammate that should be checked upon
     * @return true or false, if there is an enemy in a specific range around the bot
     */
	public static boolean isEnemyAroundMate(RawWorldData vWorldState, BotInformation mSelf, FellowPlayer teamMate){
		List<FellowPlayer> vOpponents = vWorldState.getListOfOpponents();
		for( FellowPlayer p : vOpponents){
			if(PlayersLib.getDistanceBetweenTwoPlayers(p, teamMate) < 2*mSelf.getGamevalue( GamevalueNames.KickRange ) ){
				return true;
			}
		}
		return false;
	}
	/**
     * Returns the distance between two players
     * 
     * @param FellowPlayer p, the player whos distance to the teammate should be calculated
     * @param FellowPlayer teamMate, the teammate that should be checked upon
     * @return distance as double
     */
	private static double getDistanceBetweenTwoPlayers(FellowPlayer p,FellowPlayer teamMate) {
		double a, b, wa, wb;
        
        if( p.getAngleToPlayer() > teamMate.getAngleToPlayer() ){
            
            a = p.getDistanceToPlayer();
            wa = p.getAngleToPlayer();
            b = teamMate.getDistanceToPlayer();
            wb = teamMate.getAngleToPlayer();
            
        } else {

            a = teamMate.getDistanceToPlayer();
            wa = teamMate.getAngleToPlayer(); 
            b = p.getDistanceToPlayer();
            wb = p.getAngleToPlayer();
            
        }
        double c = Math.sqrt( a*a + b*b - 2 * a * b * Math.cos(Math.toRadians(Math.abs(wa - wb))));
    	
		return c;
	}
	/**
     * Returns the distance between specific player and ball
     * 
     * @param FellowPlayer p, the player whos distance to the ball should be calculated
     * @param BallPosition ballPos, the ballPosition from the worlddata
     * @return distance as double
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
     * Returns the nearest teammate where no enemy is around
     * 
     * @param RawWorldData vWorldState, the complete worlddata from the Server
     * @param BotInformation mSelf, the information about the bot
     * @return nearest teammate where no enemy is in 2x kickrange
     */
	public static FellowPlayer getNearestMateWithoutEnemyAround(RawWorldData vWorldState, BotInformation mSelf){
        List<FellowPlayer> vTeamMates = vWorldState.getListOfTeamMates();
		FellowPlayer nearestMate = null;
    	for( FellowPlayer p : vTeamMates){
    		if( nearestMate == null || p.getDistanceToPlayer() < nearestMate.getDistanceToPlayer() ){
    			if(!PlayersLib.isEnemyAroundMate(vWorldState, mSelf, p)) {
    				nearestMate = p;
    			}
    		}
    	}
		return nearestMate;
	}
	/**
     * Returns the teammate who the enemys are furthest away of
     * 
     * @param RawWorldData vWorldState, the complete worlddata from the Server
     * @param BotInformation mSelf, the information about the bot
     * @return teammate with a enemy around but the enemy furthest away
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
    		if ( (bestMate == null) || PlayersLib.getDistanceBetweenTwoPlayers(a, nearestEnemys.get(counter)) > PlayersLib.getDistanceBetweenTwoPlayers(bestMate, nearestEnemys.get(bestIndex))){
    			bestMate = a;
    			bestIndex = counter;
    		}
    		counter++;
    	}
		
		return bestMate;
	}
	/**
     * Checks if the bot is the nearest to the ball
     * 
     * @param RawWorldData vWorldState, the complete worlddata from the Server
     * @param BallPosition ballPos, the Position of the ball from the server
     * @param BotInformation mSelf, the information about the bot
     * @return true or false whether bot is the closest to the ball or not
     */
	public static boolean amINearestToBall(RawWorldData vWorldState, BallPosition ballPos, BotInformation mSelf){
		List<FellowPlayer> vTeamMates = vWorldState.getListOfTeamMates();
		vTeamMates.add(new FellowPlayer(mSelf.getVtId(), mSelf.getBotname(), true, 0, 0, 0) );
		FellowPlayer closest_player = null;
		for ( FellowPlayer a: vTeamMates){
			if(closest_player == null || PlayersLib.getDistanceBetweenPlayerAndBall(a, ballPos) < PlayersLib.getDistanceBetweenPlayerAndBall(closest_player, ballPos)){
				closest_player = a;
			}
		}
		if(closest_player.getId() == mSelf.getVtId()){
			return true;
		}else{
			return false;
		}
	}
	
	/*
	 * TODO: Write function to get the nearest Player to the Ball
	 * */
	public static ReferencePoint whoIsNearestToBall(RawWorldData aWorldState){
		ReferencePoint player = null;
		
		
		return player;
	}
	
	/**
	 * Returns if an enemy is at the same angle as a specific reference point +- 10 degrees
	 * 
	 * @param RawWorldData aWorldState, worlddata from the server
	 * @param ReferencePoint RefPoint, a point with distance and angle
	 * @returns true or false if an enemy is found at the same angle as the reference point relative to the bot
	 * */
	public static boolean isEnemyOnLineToRefPoint(RawWorldData aWorldState, ReferencePoint RefPoint){
		
		List<FellowPlayer> vOpponents = aWorldState.getListOfOpponents();
		for ( FellowPlayer a: vOpponents){
			if(a.getAngleToPlayer() > RefPoint.getAngleToPoint()+10 || a.getAngleToPlayer() < RefPoint.getAngleToPoint()-10){
				return false;
			}
			else{
				return true;
			}
		}
		
		return true;
	}
	/**
	 * Returns if an enemy is in the angle of 2 reference points
	 * 
	 * @param RawWorldData aWorldState, worlddata from the server
	 * @param ReferencePoint refPoint1, a point with distance and angle
	 * @param ReferencePoint refPoint2, a point with distance and angle
	 * @returns true or false if an enemy is found in angle between the reference points
	 * */
	public static boolean isEnemyInAngleBetweenTwoRefPoints(RawWorldData aWorldState, ReferencePoint refPoint1, ReferencePoint refPoint2){
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
	 * Returns if an specific enemy is in the angle of 2 reference points
	 * 
	 * @param FellowPlayer enemy, the player who should be checked upon
	 * @param ReferencePoint refPoint1, a point with distance and angle
	 * @param ReferencePoint refPoint2, a point with distance and angle
	 * @returns true or false if an enemy is found in angle between the reference points
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
	 * Checks if any of the teammates is inside the kick range
	 * 
	 * @param RawWorldData aWorldState
	 * @param BotInformation botInfo, information about the bot
	 * @returns true or false if a teammate is in kick range or not
	 * */
	public static boolean hasMateTheBall(RawWorldData aWorldState, BotInformation botInfo){
		BallPosition ball = aWorldState.getBallPosition();
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
		
		return false;
		
	}
	/**
	 * Checks if any of the opponents is inside the kick range * 2
	 * 
	 * @param RawWorldData aWorldState
	 * @param BotInformation botInfo, information about the bot
	 * @returns true or false if a opponent is in kick range * 2 or not
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
     * Returns the distance between specific player and reference point
     * 
     * @param FellowPlayer p, the player whos distance to the point should be calculated
     * @param ReferencePoint refPoint, the reference point to which the distance of the player should be calculated
     * @return distance as double
     */
	public static double getDistanceBetweenPlayerAndPoint(FellowPlayer player,ReferencePoint refPoint) {
		double a, b, wa, wb;
        
        
        a = player.getDistanceToPoint();
        wa = player.getAngleToPoint();
        b = refPoint.getDistanceToPoint();
        wb = refPoint.getAngleToPoint();
         
       
        double c = Math.sqrt( a*a + b*b - 2 * a * b * Math.cos(Math.toRadians(Math.abs(wa - wb))));
    	
       return c;
	}
}
