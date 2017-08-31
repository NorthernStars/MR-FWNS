package mrlib.core;

import essentials.communication.worlddata_server2008.BallPosition;
import essentials.communication.worlddata_server2008.FellowPlayer;
import essentials.communication.worlddata_server2008.RawWorldData;
import essentials.communication.worlddata_server2008.ReferencePoint;
import essentials.core.BotInformation;

public class TestScenario {
	

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
        
        static String xmlExampleWorldData = "<rawWorldData>\n" +
                        "    <time>0</time>\n" +
                        "    <agent_id>20</agent_id>\n" +
                        "    <nickname>TestBot</nickname>\n" +
                        "    <status>found</status>\n" +
                        "    <max_agent>22</max_agent>\n" +
                        "	<playMode>kick off</playMode>\n" +
                        "    <score>\n" +
                        "        <yellow>0</yellow>\n" +
                        "        <blue>0</blue>\n" +
                        "    </score>\n" +
                        "    <ball>\n" +
                        "        <dist>264.6</dist>\n" +
                        "        <angle>-8.03823</angle>\n" +
                        "    </ball>\n" +
                        "    <teamMate>\n" +
                        "        <id>0</id>\n" +
                        "        <nickname>TestBot</nickname>\n" +
                        "        <status>found</status>\n" +
                        "        <dist>200.0</dist>\n" +
                        "        <angle>-90.0</angle>\n" +
                        "        <orientation>0.0</orientation>\n" +
                        "    </teamMate>\n" +
                        "    <teamMate>\n" +
                        "        <id>1</id>\n" +
                        "        <nickname>TestBot</nickname>\n" +
                        "        <status>found</status>\n" +
                        "        <dist>200.0</dist>\n" +
                        "        <angle>-90.0</angle>\n" +
                        "        <orientation>0.0</orientation>\n" +
                        "    </teamMate>\n" +
                        "    <teamMate>\n" +
                        "        <id>2</id>\n" +
                        "        <nickname>TestBot</nickname>\n" +
                        "        <status>found</status>\n" +
                        "        <dist>200.0</dist>\n" +
                        "        <angle>-90.0</angle>\n" +
                        "        <orientation>0.0</orientation>\n" +
                        "    </teamMate>\n" +
                        "    <teamMate>\n" +
                        "        <id>3</id>\n" +
                        "        <nickname>TestBot</nickname>\n" +
                        "        <status>found</status>\n" +
                        "        <dist>200.0</dist>\n" +
                        "        <angle>-90.0</angle>\n" +
                        "        <orientation>0.0</orientation>\n" +
                        "    </teamMate>\n" +
                        "    <teamMate>\n" +
                        "        <id>4</id>\n" +
                        "        <nickname>TestBot</nickname>\n" +
                        "        <status>found</status>\n" +
                        "        <dist>200.0</dist>\n" +
                        "        <angle>-90.0</angle>\n" +
                        "        <orientation>0.0</orientation>\n" +
                        "    </teamMate>\n" +
                        "    <teamMate>\n" +
                        "        <id>5</id>\n" +
                        "        <nickname>TestBot</nickname>\n" +
                        "        <status>found</status>\n" +
                        "        <dist>200.0</dist>\n" +
                        "        <angle>-90.0</angle>\n" +
                        "        <orientation>0.0</orientation>\n" +
                        "    </teamMate>\n" +
                        "    <teamMate>\n" +
                        "        <id>6</id>\n" +
                        "        <nickname>TestBot</nickname>\n" +
                        "        <status>found</status>\n" +
                        "        <dist>200.0</dist>\n" +
                        "        <angle>-90.0</angle>\n" +
                        "        <orientation>0.0</orientation>\n" +
                        "    </teamMate>\n" +
                        "    <teamMate>\n" +
                        "        <id>7</id>\n" +
                        "        <nickname>TestBot</nickname>\n" +
                        "        <status>found</status>\n" +
                        "        <dist>200.0</dist>\n" +
                        "        <angle>-90.0</angle>\n" +
                        "        <orientation>0.0</orientation>\n" +
                        "    </teamMate>\n" +
                        "    <teamMate>\n" +
                        "        <id>18</id>\n" +
                        "        <nickname>TestBot</nickname>\n" +
                        "        <status>found</status>\n" +
                        "        <dist>0.0</dist>\n" +
                        "        <angle>0.0</angle>\n" +
                        "        <orientation>0.0</orientation>\n" +
                        "    </teamMate>\n" +
                        "    <teamMate>\n" +
                        "        <id>19</id>\n" +
                        "        <nickname>TestBot</nickname>\n" +
                        "        <status>found</status>\n" +
                        "        <dist>0.0</dist>\n" +
                        "        <angle>0.0</angle>\n" +
                        "        <orientation>0.0</orientation>\n" +
                        "    </teamMate>\n" +
                        "    <opponent>\n" +
                        "        <id>8</id>\n" +
                        "        <nickname>TestBot</nickname>\n" +
                        "        <status>found</status>\n" +
                        "        <dist>200.0</dist>\n" +
                        "        <angle>-90.0</angle>\n" +
                        "        <orientation>0.0</orientation>\n" +
                        "    </opponent>\n" +
                        "    <opponent>\n" +
                        "        <id>9</id>\n" +
                        "        <nickname>TestBot</nickname>\n" +
                        "        <status>found</status>\n" +
                        "        <dist>200.0</dist>\n" +
                        "        <angle>-90.0</angle>\n" +
                        "        <orientation>0.0</orientation>\n" +
                        "    </opponent>\n" +
                        "    <opponent>\n" +
                        "        <id>10</id>\n" +
                        "        <nickname>TestBot</nickname>\n" +
                        "        <status>found</status>\n" +
                        "        <dist>200.0</dist>\n" +
                        "        <angle>-90.0</angle>\n" +
                        "        <orientation>0.0</orientation>\n" +
                        "    </opponent>\n" +
                        "    <opponent>\n" +
                        "        <id>11</id>\n" +
                        "        <nickname>TestBot</nickname>\n" +
                        "        <status>found</status>\n" +
                        "        <dist>200.0</dist>\n" +
                        "        <angle>-90.0</angle>\n" +
                        "        <orientation>0.0</orientation>\n" +
                        "    </opponent>\n" +
                        "    <opponent>\n" +
                        "        <id>12</id>\n" +
                        "        <nickname>TestBot</nickname>\n" +
                        "        <status>found</status>\n" +
                        "        <dist>200.0</dist>\n" +
                        "        <angle>-90.0</angle>\n" +
                        "        <orientation>0.0</orientation>\n" +
                        "    </opponent>\n" +
                        "    <opponent>\n" +
                        "        <id>13</id>\n" +
                        "        <nickname>TestBot</nickname>\n" +
                        "        <status>found</status>\n" +
                        "        <dist>200.0</dist>\n" +
                        "        <angle>-90.0</angle>\n" +
                        "        <orientation>0.0</orientation>\n" +
                        "    </opponent>\n" +
                        "    <opponent>\n" +
                        "        <id>14</id>\n" +
                        "        <nickname>TestBot</nickname>\n" +
                        "        <status>found</status>\n" +
                        "        <dist>200.0</dist>\n" +
                        "        <angle>-90.0</angle>\n" +
                        "        <orientation>0.0</orientation>\n" +
                        "    </opponent>\n" +
                        "    <opponent>\n" +
                        "        <id>15</id>\n" +
                        "        <nickname>TestBot</nickname>\n" +
                        "        <status>found</status>\n" +
                        "        <dist>200.0</dist>\n" +
                        "        <angle>-90.0</angle>\n" +
                        "        <orientation>0.0</orientation>\n" +
                        "    </opponent>\n" +
                        "    <opponent>\n" +
                        "        <id>16</id>\n" +
                        "        <nickname>TestBot</nickname>\n" +
                        "        <status>found</status>\n" +
                        "        <dist>200.0</dist>\n" +
                        "        <angle>-90.0</angle>\n" +
                        "        <orientation>0.0</orientation>\n" +
                        "    </opponent>\n" +
                        "    <opponent>\n" +
                        "        <id>17</id>\n" +
                        "        <nickname>TestBot</nickname>\n" +
                        "        <status>found</status>\n" +
                        "        <dist>509.902</dist>\n" +
                        "        <angle>-11.3099</angle>\n" +
                        "        <orientation>0.0</orientation>\n" +
                        "    </opponent>\n" +
                        "    <flag>\n" +
                        "        <id>bottom_center</id>\n" +
                        "        <dist>335.932</dist>\n" +
                        "        <angle>38.4734</angle>\n" +
                        "    </flag>\n" +
                        "    <flag>\n" +
                        "        <id>bottom_left_corner</id>\n" +
                        "        <dist>245.082</dist>\n" +
                        "        <angle>121.485</angle>\n" +
                        "    </flag>\n" +
                        "    <flag>\n" +
                        "        <id>bottom_left_goal</id>\n" +
                        "        <dist>132.563</dist>\n" +
                        "        <angle>73.3422</angle>\n" +
                        "    </flag>\n" +
                        "    <flag>\n" +
                        "        <id>bottom_left_pole</id>\n" +
                        "        <dist>135.351</dist>\n" +
                        "        <angle>161.03</angle>\n" +
                        "    </flag>\n" +
                        "    <flag>\n" +
                        "        <id>bottom_left_small_area</id>\n" +
                        "        <dist>73.6003</dist>\n" +
                        "        <angle>143.286</angle>\n" +
                        "    </flag>\n" +
                        "    <flag>\n" +
                        "        <id>bottom_right_corner</id>\n" +
                        "        <dist>685.631</dist>\n" +
                        "        <angle>17.7479</angle>\n" +
                        "    </flag>\n" +
                        "    <flag>\n" +
                        "        <id>bottom_right_goal</id>\n" +
                        "        <dist>503.287</dist>\n" +
                        "        <angle>14.6161</angle>\n" +
                        "    </flag>\n" +
                        "    <flag>\n" +
                        "        <id>bottom_right_pole</id>\n" +
                        "        <dist>654.481</dist>\n" +
                        "        <angle>3.85484</angle>\n" +
                        "    </flag>\n" +
                        "    <flag>\n" +
                        "        <id>bottom_right_small_area</id>\n" +
                        "        <dist>585.655</dist>\n" +
                        "        <angle>4.30866</angle>\n" +
                        "    </flag>\n" +
                        "    <flag>\n" +
                        "        <id>middle_center</id>\n" +
                        "        <dist>265.452</dist>\n" +
                        "        <angle>-7.79433</angle>\n" +
                        "    </flag>\n" +
                        "    <flag>\n" +
                        "        <id>top_center</id>\n" +
                        "        <dist>383.419</dist>\n" +
                        "        <angle>-46.6909</angle>\n" +
                        "    </flag>\n" +
                        "    <flag>\n" +
                        "        <id>top_left_corner</id>\n" +
                        "        <dist>306.961</dist>\n" +
                        "        <angle>-114.645</angle>\n" +
                        "    </flag>\n" +
                        "    <flag>\n" +
                        "        <id>top_left_goal</id>\n" +
                        "        <dist>203.578</dist>\n" +
                        "        <angle>-79.242</angle>\n" +
                        "    </flag>\n" +
                        "    <flag>\n" +
                        "        <id>top_left_pole</id>\n" +
                        "        <dist>171.406</dist>\n" +
                        "        <angle>-138.311</angle>\n" +
                        "    </flag>\n" +
                        "    <flag>\n" +
                        "        <id>top_left_small_area</id>\n" +
                        "        <dist>128.363</dist>\n" +
                        "        <angle>-117.364</angle>\n" +
                        "    </flag>\n" +
                        "    <flag>\n" +
                        "        <id>top_right_corner</id>\n" +
                        "        <dist>710.106</dist>\n" +
                        "        <angle>-23.135</angle>\n" +
                        "    </flag>\n" +
                        "    <flag>\n" +
                        "        <id>top_right_goal</id>\n" +
                        "        <dist>526.468</dist>\n" +
                        "        <angle>-22.3269</angle>\n" +
                        "    </flag>\n" +
                        "    <flag>\n" +
                        "        <id>top_right_pole</id>\n" +
                        "        <dist>662.876</dist>\n" +
                        "        <angle>-9.90283</angle>\n" +
                        "    </flag>\n" +
                        "    <flag>\n" +
                        "        <id>top_right_small_area</id>\n" +
                        "        <dist>595.023</dist>\n" +
                        "        <angle>-11.0456</angle>\n" +
                        "    </flag>\n" +
                        "</rawWorldData>";
	
	

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
	
	static RawWorldData getExampleWorldModel(String xmlString)
        {
            return RawWorldData.createRawWorldDataFromXML(xmlString);
        }
}
