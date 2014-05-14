package mrlib.core;

import java.util.ArrayList;

import essentials.communication.Action;
import essentials.communication.action_server2008.Kick;
import essentials.communication.action_server2008.Movement;
import essentials.communication.worlddata_server2008.FellowPlayer;
import essentials.communication.worlddata_server2008.RawWorldData;
import essentials.communication.worlddata_server2008.ReferencePoint;

/**
 * Includes static functions to kick somewhere
 * @author Hannes Eilers
 * @version 1.0
 *
 */
public class KickLib {
	/**
	 * Kick into a direction of an angle with a specific force.
	 * @param aAngle	{@link Double} angle to kick to in degree
	 * @param aForce	{@link Float} force to kick with (0.0f - 1.0f)
	 * @return Action	{@link Action}
	 */
	public static Action kickTo(double aAngle, float aForce) {
		return (Action) new Kick(aAngle, aForce);
	}

	/**
	 * Kick into a direction of an angle with maximum force.
	 * @param aAngle	{@link Double} angle to kick to in degree
	 * @return Action	{@link Action}
	 */
	public static Action kickTo(double aAngle) {
		return kickTo(aAngle, 1.0f);
	}

	/**
	 * Kick to {@link ReferencePoint} with maximum force.
	 * @param aRefPoint	{@link ReferencePoint} to kick to
	 * @return Action		{@link Action}
	 */
	public static Action kickTo(ReferencePoint aRefPoint) {
		return kickTo(aRefPoint, 1.0f);
	}

	/**
	 * Kick to {@link ReferencePoint} with specific force.
	 * @param aRefPoint	{@link ReferencePoint} to kick to
	 * @param aForce		{@link Float} force to kick with (0.0f - 1.0f)
	 * @return			{@link Action}
	 */
	public static Action kickTo(ReferencePoint aRefPoint, float aForce) {
		return kickTo(aRefPoint.getAngleToPoint(), aForce);
	}

	/**
	 * Kick to {@link FellowPlayer} with maxmimum force.
	 * @param aFellowPlayer	{@link FellowPlayer} to kick to
	 * @return Action			{@link Action}
	 */
	public static Action kickTo(FellowPlayer aFellowPlayer) {
		return kickTo(aFellowPlayer, 1.0f);
	}

	/**
	 * Kick to {@link FellowPlayer} with specific force.
	 * @param aFellowPlayer	{@link FellowPlayer} to kick to
	 * @param aForce			{@link Float} force to kick with (0.0f - 1.0f)
	 * @return Action			{@link Action}
	 */
	public static Action kickTo(FellowPlayer aFellowPlayer, float aForce) {
		return kickTo(aFellowPlayer.getAngleToPlayer(), aForce);
	}

	/**
	 * Kicks to nearest teammate.
	 * @param aWorldData	{@link RawWorldData}
	 * @return Action		{@link Action}
	 */
	public static Action kickToNearestTeamMate(RawWorldData aWorldData) {
		return kickToNearest(aWorldData, FellowPlayers.TeamMates);
	}

	/**
	 * Kick to nearest opponent.
	 * @param aWorldData	{@link RawWorldData}
	 * @return Action		{@link Action}
	 */
	public static Action kickToNearestOpponent(RawWorldData aWorldData) {
		return kickToNearest(aWorldData, FellowPlayers.Opponents);
	}

	/**
	 * Kick to nearest player (teammate or opponent).
	 * @param aWorldData	{@link RawWorldData}
	 * @return Action		{@link Action}
	 */
	public static Action kickToNearest(RawWorldData aWorldData) {
		return kickToNearest(aWorldData, FellowPlayers.AllPlayers);
	}

	/**
	 * Kick to specific nearest player type.
	 * @param aWorldData		{@link RawWorldData}
	 * @param aFellowPlayers 	{@link FellowPlayers} type to kick to
	 * @return Action			{@link Action}
	 */
	private static Action kickToNearest(RawWorldData aWorldData, FellowPlayers aFellowPlayers) {
		ArrayList<FellowPlayer> vSpieler = new ArrayList<FellowPlayer>();
		// get a list of players
		if (aWorldData.getListOfOpponents() != null && (aFellowPlayers == FellowPlayers.Opponents || aFellowPlayers ==FellowPlayers.AllPlayers)) {
			vSpieler.addAll(aWorldData.getListOfOpponents());
		}
		if (aWorldData.getListOfTeamMates() != null && (aFellowPlayers == FellowPlayers.TeamMates || aFellowPlayers ==FellowPlayers.AllPlayers)) {
			vSpieler.addAll(aWorldData.getListOfTeamMates());
		}

		FellowPlayer vNearestPlayer = null;
		// get the nearest player
		for (FellowPlayer vAPlayer : vSpieler) {
			if (vNearestPlayer == null || vNearestPlayer.getDistanceToPlayer() > vAPlayer.getDistanceToPlayer()) {
				vNearestPlayer = vAPlayer;
			}
		}
		if (vNearestPlayer != null) {
			return kickTo(vNearestPlayer);
		}

		return (Action) Movement.NO_MOVEMENT;
	}
}