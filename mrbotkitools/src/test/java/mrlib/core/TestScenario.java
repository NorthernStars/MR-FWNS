package mrlib.core;

import essentials.communication.worlddata_server2008.FellowPlayer;
import essentials.communication.worlddata_server2008.RawWorldData;

class TestScenario {
	

	//Test Values for worldModel
	static double fellow1_Distance = 200.0;
	static double fellow1_Angle = -90.0;
	
	static double fellow2_Distance = 300.0;
	static double fellow2_Angle = -80.0;
	
	
	static double opponent1_Distance = 800.0;
	static double opponent1_Angle = 55.0;
	
	static double opponent2_Distance = 900.0;
	static double opponent2_Angle = 66.0;

	static RawWorldData setScenario(){
		
		RawWorldData rawWorldData = new RawWorldData();
		FellowPlayer player1 = new FellowPlayer(0, "TestBot", true, fellow1_Distance, fellow1_Angle, 0.0);
		FellowPlayer player2 = new FellowPlayer(1, "TestBot2", true, fellow2_Distance, fellow2_Angle, 0.0);
		FellowPlayer oPlayer1 = new FellowPlayer(2, "OpponentBot2", true, opponent1_Distance, opponent1_Angle, 0.0);
		FellowPlayer oPlayer2 = new FellowPlayer(3, "OpponentBot2", true, opponent2_Distance, opponent2_Angle, 0.0);
		
		rawWorldData.setFellowPlayer(player1);
		rawWorldData.setFellowPlayer(player2);
		rawWorldData.setOpponentPlayer(oPlayer1);
		rawWorldData.setOpponentPlayer(oPlayer2);
		
		return rawWorldData;
	}
	
	
}
