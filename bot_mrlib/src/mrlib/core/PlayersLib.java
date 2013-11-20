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
     * Returns nearest teammate
     * @param RawWorldData vWorldState
     * @param BotInformation mSelf
     * @return FellowPlayer nearestMate
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
     * Checks if Enemy is within 2 x KickRange
     * @param RawWorldData vWorldState
     * @param BotInformation mSelf
     * @return true or false
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
     * @param RawWorldData vWorldState
     * @param BotInformation mSelf
     * @param double range
     * @return true or false
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
	
	public static boolean isEnemyAroundMate(RawWorldData vWorldState, BotInformation mSelf, FellowPlayer teamMate){
		List<FellowPlayer> vOpponents = vWorldState.getListOfOpponents();
		for( FellowPlayer p : vOpponents){
			if(PlayersLib.getDistanceBetweenTwoPlayers(p, teamMate) < 2*mSelf.getGamevalue( GamevalueNames.KickRange ) ){
				return true;
			}
		}
		return false;
	}
	
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
	
	private static double getDistanceBetweenPlayersAndBall(FellowPlayer p, BallPosition ballPos) {
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
	
	public static FellowPlayer getNearestMateWithoutEnemyAround(RawWorldData vWorldState, BotInformation mSelf){
        List<FellowPlayer> vOpponents = vWorldState.getListOfOpponents();
        List<FellowPlayer> vTeamMates = vWorldState.getListOfTeamMates();
		FellowPlayer nearestMate = null;
		
    	for( FellowPlayer p : vTeamMates){
    		if( nearestMate == null || ( vOpponents.size() > 0 && p.getDistanceToPlayer() < nearestMate.getDistanceToPlayer() )){
    			if(PlayersLib.isEnemyAroundMate(vWorldState, mSelf, p))
    				nearestMate = p;
    		}
    	}
		return nearestMate;
	}
	
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
	
	public static boolean amINearestToBall(RawWorldData vWorldState, BallPosition ballPos, BotInformation mSelf){
		List<FellowPlayer> vTeamMates = vWorldState.getListOfTeamMates();
		vTeamMates.add(new FellowPlayer(mSelf.getVtId(), mSelf.getBotname(), true, 0, 0, 0) );
		FellowPlayer closest_player = null;
		for ( FellowPlayer a: vTeamMates){
			if(closest_player == null || PlayersLib.getDistanceBetweenPlayersAndBall(a, ballPos) < closest_player.getDistanceToPlayer()){
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
	
	/*
	 * TODO: Write function to check if an enemy is on a line from me to a ReferencePoint
	 * */
	public static boolean isEnemyInOnLineToRefPoint(RawWorldData aWorldState, ReferencePoint RefPoint){
		
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

}
