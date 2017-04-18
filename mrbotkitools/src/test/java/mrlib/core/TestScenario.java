package mrlib.core;

import essentials.communication.worlddata_server2008.BallPosition;
import essentials.communication.worlddata_server2008.FellowPlayer;
import essentials.communication.worlddata_server2008.RawWorldData;
import essentials.communication.worlddata_server2008.ReferencePoint;
import essentials.core.BotInformation;

class TestScenario {
	

	//Test Values for worldModel
	static double fellow1Distance = 200.0;
	static double fellow1Angle = -90.0;
	
	static double fellow2Distance = 300.0;
	static double fellow2Angle = -80.0;
	
	
	static double opponent1Distance = 800.0;
	static double opponent1Angle = 55.0;
	
	static double opponent2Distance = 900.0;
	static double opponent2Angle = 66.0;
	
	
	static double ballDistance = 20.0;
	static double ballAngle = 45.0;
	
	

	static RawWorldData getExampleWorldModel(){
		
		RawWorldData rawWorldData = new RawWorldData();
		FellowPlayer player1 = new FellowPlayer(0, "TestBot", true, fellow1Distance, fellow1Angle, 0.0);
		FellowPlayer player2 = new FellowPlayer(1, "TestBot2", true, fellow2Distance, fellow2Angle, 0.0);
		FellowPlayer oPlayer1 = new FellowPlayer(2, "OpponentBot2", true, opponent1Distance, opponent1Angle, 0.0);
		FellowPlayer oPlayer2 = new FellowPlayer(3, "OpponentBot2", true, opponent2Distance, opponent2Angle, 0.0);
		
		rawWorldData.setFellowPlayer(player1);
		rawWorldData.setFellowPlayer(player2);
		rawWorldData.setOpponentPlayer(oPlayer1);
		rawWorldData.setOpponentPlayer(oPlayer2);
		
		BallPosition ballPos = new BallPosition(ballDistance, ballAngle, true);
		
		rawWorldData.setBallPosition(ballPos);
		
		return rawWorldData;
	}
	
	
}
