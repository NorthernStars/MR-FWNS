package mrlib.core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.withinPercentage;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import essentials.communication.worlddata_server2008.BallPosition;
import essentials.communication.worlddata_server2008.FellowPlayer;
import essentials.communication.worlddata_server2008.RawWorldData;
import essentials.communication.worlddata_server2008.ReferencePoint;
import essentials.constants.Default;
import essentials.core.BotInformation;
import essentials.core.BotInformation.GamevalueNames;

@RunWith(PowerMockRunner.class)
public class PlayersLibTests {

	RawWorldData worldModel;
	BotInformation vBotInformation = new BotInformation();

	final int mOwnId = 20;
	RawWorldData mWorldDataMock = mock(RawWorldData.class);
	BotInformation mSelfMock = mock(BotInformation.class);
	
	@Before
	public void setUp() throws Exception {
		worldModel = TestScenario.getExampleWorldModel();
		vBotInformation.setVtId(100);
		
		when(mWorldDataMock.getAgentId()).thenReturn(mOwnId);
		
		when(mSelfMock.getRcId()).thenReturn(mOwnId);
		when(mSelfMock.getVtId()).thenReturn(mOwnId);
		when(mSelfMock.getGamevalue(GamevalueNames.KickRange)).thenReturn(Default.KickRange);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetNearestMate() {

		//2 calls for different function signatures (legacy included)
		FellowPlayer returnPlayer = PlayersLib.getNearestMate(worldModel, new BotInformation());
		
		assertThat(returnPlayer).isExactlyInstanceOf(FellowPlayer.class);
		assertThat(returnPlayer.getDistanceToPlayer()).isCloseTo(TestScenario.fellow1Distance, withinPercentage(1));
		assertThat(returnPlayer.getAngleToPlayer()).isCloseTo(TestScenario.fellow1Angle, withinPercentage(1));
		
		returnPlayer = PlayersLib.getNearestMate(worldModel);
		
		assertThat(returnPlayer).isExactlyInstanceOf(FellowPlayer.class);
		assertThat(returnPlayer.getDistanceToPlayer()).isCloseTo(TestScenario.fellow1Distance, withinPercentage(1));
		assertThat(returnPlayer.getAngleToPlayer()).isCloseTo(TestScenario.fellow1Angle, withinPercentage(1));
		

		//2 calls for different function signatures (legacy included)
		FellowPlayer nearerPlayer = new FellowPlayer(9,"Homer",true,TestScenario.fellow1Distance-20, TestScenario.fellow1Angle, 0);
		worldModel.setFellowPlayer(nearerPlayer);
		
		returnPlayer = PlayersLib.getNearestMate(worldModel, new BotInformation());
		
		assertThat(returnPlayer).isExactlyInstanceOf(FellowPlayer.class);
		assertThat(returnPlayer.getDistanceToPlayer()).isCloseTo(TestScenario.fellow1Distance-20, withinPercentage(1));
		assertThat(returnPlayer.getAngleToPlayer()).isCloseTo(TestScenario.fellow1Angle, withinPercentage(1));
		
		returnPlayer = PlayersLib.getNearestMate(worldModel);
		
		assertThat(returnPlayer).isExactlyInstanceOf(FellowPlayer.class);
		assertThat(returnPlayer.getDistanceToPlayer()).isCloseTo(TestScenario.fellow1Distance-20, withinPercentage(1));
		assertThat(returnPlayer.getAngleToPlayer()).isCloseTo(TestScenario.fellow1Angle, withinPercentage(1));
		
		worldModel = new RawWorldData();
		returnPlayer = PlayersLib.getNearestMate(worldModel);
		assertThat((returnPlayer)).isEqualTo(null);
				
		worldModel = TestScenario.getExampleWorldModel();
	}

	@Test
	public void testGetNearestOpponent() {
		FellowPlayer returnPlayer = PlayersLib.getNearestOpponent(worldModel);
		
		assertThat(returnPlayer.getDistanceToPlayer()).isCloseTo(TestScenario.opponent1Distance,withinPercentage(1));

		FellowPlayer o1 = new FellowPlayer(0, "NearerOpponent", true, TestScenario.opponent1Distance-300, TestScenario.fellow1Angle, 0.0);
		worldModel.setOpponentPlayer(o1);
		
		returnPlayer = PlayersLib.getNearestOpponent(worldModel);
		assertThat(returnPlayer.getDistanceToPlayer()).isCloseTo(o1.getDistanceToPlayer(),withinPercentage(1));
		
		

		worldModel = new RawWorldData();
		returnPlayer = PlayersLib.getNearestOpponent(worldModel);
		assertThat((returnPlayer)).isEqualTo(null);
				
		worldModel = TestScenario.getExampleWorldModel();
		
		
	}

	@Test
	public void testIsEnemyAroundRawWorldDataBotInformation() {

		
		//Case 1
		boolean testAround = PlayersLib.isEnemyAround(worldModel, vBotInformation);
		assertThat(testAround).isEqualTo(false);
		
		
		//Case 2 (barely out of range)
		FellowPlayer nearestOpponent = PlayersLib.getNearestOpponent(worldModel);
		nearestOpponent.setDistanceToPoint((Default.KickRange)*2+0.01);
		testAround = PlayersLib.isEnemyAround(worldModel, vBotInformation);
		assertThat(testAround).isEqualTo(false);

		
		//Case 3 (exact in range)
		nearestOpponent.setDistanceToPoint((Default.KickRange)*2);
		testAround = PlayersLib.isEnemyAround(worldModel, vBotInformation);
		assertThat(testAround).isEqualTo(true);
		
		//Case 4 (barely in range)
		nearestOpponent.setDistanceToPoint((Default.KickRange)*2-0.01);
		testAround = PlayersLib.isEnemyAround(worldModel, vBotInformation);
		assertThat(testAround).isEqualTo(true);
		
		//Switch Model back to testDefault
		worldModel = TestScenario.getExampleWorldModel();
		
	}

	@Test
	public void testIsEnemyAroundRawWorldDataBotInformationDouble() {

		
		//Case 1
		boolean testAround = PlayersLib.isEnemyAround(worldModel,vBotInformation, 700.0);
		assertThat(testAround).isEqualTo(false);
		
		
		//Case 2: Barely out of range
		testAround = PlayersLib.isEnemyAround(worldModel, vBotInformation, 799.99);
		assertThat(testAround).isEqualTo(false);

		
		//Case 3: Exact in range
		testAround = PlayersLib.isEnemyAround(worldModel, vBotInformation, 800.0);
		assertThat(testAround).isEqualTo(true);
		
		//Case 4: Barely in range
		testAround = PlayersLib.isEnemyAround(worldModel, vBotInformation, 802.0);
		assertThat(testAround).isEqualTo(true);
		
		//Switch Model back to testDefault
		worldModel = TestScenario.getExampleWorldModel();
		
	}
	
	@Test
	public void testIsEnemyAroundRawWorldDataDouble() {

		
		//Case 1
		boolean testAround = PlayersLib.isEnemyAround(worldModel, 700.0);
		assertThat(testAround).isEqualTo(false);
		
		
		//Case 2: Barely out of range
		testAround = PlayersLib.isEnemyAround(worldModel, 799.99);
		assertThat(testAround).isEqualTo(false);

		
		//Case 3: Exact in range
		testAround = PlayersLib.isEnemyAround(worldModel, 800.0);
		assertThat(testAround).isEqualTo(true);
		
		//Case 4: Barely in range
		testAround = PlayersLib.isEnemyAround(worldModel, 802.0);
		assertThat(testAround).isEqualTo(true);
		
		//Switch Model back to testDefault
		worldModel = TestScenario.getExampleWorldModel();
		
	}

	@Test
	public void testIsEnemyAroundMate() {

		FellowPlayer testMate = new FellowPlayer();
		testMate.set(new ReferencePoint(700, 55, true));
		
		//Case 1: 100mm away
		boolean testAround = PlayersLib.isEnemyAroundMate(worldModel, vBotInformation, testMate);
		assertThat(testAround).isEqualTo(false);
		
		//Case 2: 51mm away (barely out of range)
		testMate.setDistanceToPoint(749);
		testAround = PlayersLib.isEnemyAroundMate(worldModel, vBotInformation, testMate);
		assertThat(testAround).isEqualTo(false);

		//Case 2: exact in range
		testMate.setDistanceToPoint(750);
		testAround = PlayersLib.isEnemyAroundMate(worldModel, vBotInformation, testMate);
		assertThat(testAround).isEqualTo(true);

		//Case 2: 49mm away (barely in range)
		testMate.setDistanceToPoint(750);
		testAround = PlayersLib.isEnemyAroundMate(worldModel, vBotInformation, testMate);
		assertThat(testAround).isEqualTo(true);

		//Switch Model back to testDefault
		worldModel = TestScenario.getExampleWorldModel();
	}

	@Test
	public void testGetDistanceBetweenPlayerAndBall() {

		worldModel = TestScenario.getExampleWorldModel();
		
		FellowPlayer P1 = new FellowPlayer();
		P1.set(new ReferencePoint(TestScenario.ballDistance+20, TestScenario.ballAngle, true));
		worldModel.setFellowPlayer(P1);
						
		double testDistance = PlayersLib.getDistanceBetweenPlayerAndBall(P1, worldModel.getBallPosition());
		assertThat(testDistance).isCloseTo(20, withinPercentage(0.1));
		
		P1.setAngleToPoint(-135.0); //opposite angle to 45°
		testDistance = PlayersLib.getDistanceBetweenPlayerAndBall(P1, worldModel.getBallPosition());
		assertThat(testDistance).isCloseTo((2*TestScenario.ballDistance + 20), withinPercentage(0.1));
		
		P1.set(654, 123, true);
		
		double testDistance2 = PositionLib.getDistanceBetweenTwoRefPoints(P1, worldModel.getBallPosition()); 
		testDistance = PlayersLib.getDistanceBetweenPlayerAndBall(P1, worldModel.getBallPosition());
		assertThat(testDistance).isCloseTo((testDistance2), withinPercentage(0.1));
		

		worldModel = TestScenario.getExampleWorldModel();
		
	}

	@Test
	public void testGetNearestMateWithoutEnemyAroundAllAlone(){

		when(mWorldDataMock.getListOfTeamMates()).thenReturn(new ArrayList<>());
		
		assertThat(PlayersLib.getNearestMateWithoutEnemyAround(mWorldDataMock, mSelfMock)).isNull();
		
	}

	@Test
	public void testGetNearestMateWithoutEnemyAroundWithoutMatesWithOpponents(){

		when(mWorldDataMock.getListOfTeamMates()).thenReturn(new ArrayList<>());
		
		List<FellowPlayer> vListOfOpponents = new ArrayList<>();
		vListOfOpponents.add(new FellowPlayer(1, "", true, 200, 0, 0));
		vListOfOpponents.add(new FellowPlayer(2, "", true, 300, 0, 0));
		vListOfOpponents.add(new FellowPlayer(3, "", true, 400, 0, 0));
		
		when(mWorldDataMock.getListOfOpponents()).thenReturn(vListOfOpponents);
		
		assertThat(PlayersLib.getNearestMateWithoutEnemyAround(mWorldDataMock, mSelfMock)).isNull();
		
	}

	@Test
	public void testGetNearestMateWithoutEnemyAroundWithOrderedMatesWithoutOpponents(){

		FellowPlayer vTheMate = new FellowPlayer(1, "", true, 200, 0, 0);
		
		List<FellowPlayer> vListOfMates = new ArrayList<>();
		
		vListOfMates.add(vTheMate);
		vListOfMates.add(new FellowPlayer(2, "", true, 300, 0, 0));
		vListOfMates.add(new FellowPlayer(3, "", true, 400, 0, 0));
		
		when(mWorldDataMock.getListOfTeamMates()).thenReturn(vListOfMates);
		when(mWorldDataMock.getListOfOpponents()).thenReturn(new ArrayList<>());
		
		assertThat(PlayersLib.getNearestMateWithoutEnemyAround(mWorldDataMock, mSelfMock)).isEqualTo(vTheMate);
		
	}

	@Test
	public void testGetNearestMateWithoutEnemyAroundWithUnorderedMatesWithoutOpponents(){

		FellowPlayer vTheMate = new FellowPlayer(1, "", true, 200, 0, 0);
		
		List<FellowPlayer> vListOfMates = new ArrayList<>();
		
		vListOfMates.add(new FellowPlayer(2, "", true, 300, 0, 0));
		vListOfMates.add(new FellowPlayer(3, "", true, 400, 0, 0));
		vListOfMates.add(vTheMate);
		
		when(mWorldDataMock.getListOfTeamMates()).thenReturn(vListOfMates);
		when(mWorldDataMock.getListOfOpponents()).thenReturn(new ArrayList<>());
		
		assertThat(PlayersLib.getNearestMateWithoutEnemyAround(mWorldDataMock, mSelfMock)).isEqualTo(vTheMate);
		
	}

	@Test
	public void testGetNearestMateWithoutEnemyAroundWithouMatesWithoutOpponentsWithMeself(){

		int vOwnId = 20;
		FellowPlayer vMeself = new FellowPlayer(vOwnId, "", true, 0, 0, 0);
		
		List<FellowPlayer> vListOfMates = new ArrayList<>();
		vListOfMates.add(vMeself);
		
		when(mWorldDataMock.getListOfTeamMates()).thenReturn(vListOfMates);
		when(mWorldDataMock.getListOfOpponents()).thenReturn(new ArrayList<>());
		
		assertThat(PlayersLib.getNearestMateWithoutEnemyAround(mWorldDataMock, mSelfMock)).isNull();
		
	}

	@Test
	public void testGetNearestMateWithoutEnemyAroundWithMatesWithOpponentsWithOneMateFree(){

		FellowPlayer vTheMate = new FellowPlayer(1, "", true, 300, 0, 0);
		
		List<FellowPlayer> vListOfMates = new ArrayList<>();
		
		vListOfMates.add(new FellowPlayer(2, "", true, 200, 0, 0));
		vListOfMates.add(vTheMate);
		vListOfMates.add(new FellowPlayer(3, "", true, 400, 0, 0));
		
		when(mWorldDataMock.getListOfTeamMates()).thenReturn(vListOfMates);
		
		List<FellowPlayer> vListOfOpponents = new ArrayList<>();
		
		vListOfOpponents.add(new FellowPlayer(4, "", true, 200, 0, 0));
		vListOfOpponents.add(new FellowPlayer(5, "", true, 400, 0, 0));
		vListOfOpponents.add(new FellowPlayer(6, "", true, 600, 0, 0));
		
		when(mWorldDataMock.getListOfOpponents()).thenReturn(vListOfOpponents);
		
		assertThat(PlayersLib.getNearestMateWithoutEnemyAround(mWorldDataMock, mSelfMock)).isEqualTo(vTheMate);
		
	}

	@Test
	public void testGetNearestMateWithoutEnemyAroundWithMatesWithOpponentsWithTwoMatesFree(){

		FellowPlayer vTheMate = new FellowPlayer(1, "", true, 100, 0, 0);
		
		List<FellowPlayer> vListOfMates = new ArrayList<>();
		
		vListOfMates.add(new FellowPlayer(2, "", true, 300, 0, 0));
		vListOfMates.add(vTheMate);
		vListOfMates.add(new FellowPlayer(3, "", true, 400, 0, 0));
		
		when(mWorldDataMock.getListOfTeamMates()).thenReturn(vListOfMates);
		
		List<FellowPlayer> vListOfOpponents = new ArrayList<>();
		
		vListOfOpponents.add(new FellowPlayer(4, "", true, 200, 0, 0));
		vListOfOpponents.add(new FellowPlayer(5, "", true, 400, 0, 0));
		vListOfOpponents.add(new FellowPlayer(6, "", true, 600, 0, 0));
		
		when(mWorldDataMock.getListOfOpponents()).thenReturn(vListOfOpponents);
		
		assertThat(PlayersLib.getNearestMateWithoutEnemyAround(mWorldDataMock, mSelfMock)).isEqualTo(vTheMate);
		
	}

	@Test
	public void testGetNearestMateWithoutEnemyAroundWithMatesWithOpponentsWithNoMatesFree(){
		
		List<FellowPlayer> vListOfMates = new ArrayList<>();
		
		vListOfMates.add(new FellowPlayer(2, "", true, 200, 0, 0));
		vListOfMates.add(new FellowPlayer(1, "", true, 300, 0, 0));
		vListOfMates.add(new FellowPlayer(3, "", true, 400, 0, 0));
		
		when(mWorldDataMock.getListOfTeamMates()).thenReturn(vListOfMates);
		
		List<FellowPlayer> vListOfOpponents = new ArrayList<>();
		
		vListOfOpponents.add(new FellowPlayer(4, "", true, 200, 0, 0));
		vListOfOpponents.add(new FellowPlayer(5, "", true, 300, 0, 0));
		vListOfOpponents.add(new FellowPlayer(6, "", true, 400, 0, 0));
		
		when(mWorldDataMock.getListOfOpponents()).thenReturn(vListOfOpponents);
		
		assertThat(PlayersLib.getNearestMateWithoutEnemyAround(mWorldDataMock, mSelfMock)).isNull();
		
	}
	
	
	@Test
	public void testGetNearestMateWithoutEnemyAround() {

		
		
		FellowPlayer chosenOne = PlayersLib.getNearestMateWithoutEnemyAround(worldModel, vBotInformation);
		assertThat(chosenOne.getDistanceToPlayer()).isCloseTo(PlayersLib.getNearestMate(worldModel, vBotInformation).getDistanceToPlayer(), withinPercentage(0.1));
		FellowPlayer oldChosenOne = chosenOne;
		
		//P1 should not become nearest as opponent1 is around
		FellowPlayer P1 = new FellowPlayer();
		P1.set(new ReferencePoint(TestScenario.opponent1Distance - 5, TestScenario.opponent1Angle, true));
		chosenOne = PlayersLib.getNearestMateWithoutEnemyAround(worldModel, vBotInformation);
		
		assertThat(chosenOne.getDistanceToPlayer()).isCloseTo(oldChosenOne.getDistanceToPlayer(), withinPercentage(0.1));

	}

	@Test
	public void testGetMateWithEnemyNearButFurthestAway() {

		
		FellowPlayer chosenOne = PlayersLib.getMateWithEnemyNearButFurthestAway(worldModel, vBotInformation);
		assertThat(chosenOne.getDistanceToPlayer()).isCloseTo(TestScenario.fellow2Distance, withinPercentage(0.1));
		

		chosenOne = PlayersLib.getMateWithEnemyNearButFurthestAway(worldModel);
		assertThat(chosenOne.getDistanceToPlayer()).isCloseTo(TestScenario.fellow2Distance, withinPercentage(0.1));

		FellowPlayer fellowFarAway = new FellowPlayer();
		fellowFarAway.set(new ReferencePoint(TestScenario.fellow2Distance + 50, TestScenario.fellow2Angle, true));
		worldModel.setFellowPlayer(fellowFarAway);

		chosenOne = PlayersLib.getMateWithEnemyNearButFurthestAway(worldModel);
		assertThat(chosenOne.getDistanceToPlayer()).isCloseTo(fellowFarAway.getDistanceToPlayer(), withinPercentage(0.1));
		
		chosenOne = PlayersLib.getMateWithEnemyNearButFurthestAway(worldModel, vBotInformation);
		assertThat(chosenOne.getDistanceToPlayer()).isCloseTo(fellowFarAway.getDistanceToPlayer(), withinPercentage(0.1));
		
	}

	@Test
	public void testAmINearestToBall() {
		List<FellowPlayer> allPlayers = worldModel.getListOfTeamMates();
		allPlayers.addAll(worldModel.getListOfOpponents());
		
		Boolean amINear = PlayersLib.amINearestToBall(worldModel, worldModel.getBallPosition(), vBotInformation);
		assertThat(amINear).isEqualTo(true);
		
		worldModel.setBallPosition(new BallPosition(TestScenario.fellow1Distance + 20, TestScenario.fellow1Angle, true));
		amINear = PlayersLib.amINearestToBall(worldModel, worldModel.getBallPosition(), vBotInformation);
		assertThat(amINear).isEqualTo(false);
		
		worldModel = TestScenario.getExampleWorldModel();
		
	}

	@Test
	public void testNearestMateToBall() {
		FellowPlayer mateWithBalls = PlayersLib.nearestMateToBall(worldModel);
		
		assertThat(mateWithBalls.getDistanceToPlayer()).isCloseTo(TestScenario.fellow1Distance, withinPercentage(0.1));
		assertThat(mateWithBalls.getAngleToPlayer()).isCloseTo(TestScenario.fellow1Angle, withinPercentage(0.1));
		
		FellowPlayer P1 = new FellowPlayer();
		P1.set(new ReferencePoint(TestScenario.ballDistance+20, TestScenario.ballAngle, true));
		worldModel.setFellowPlayer(P1);
		
		mateWithBalls = PlayersLib.nearestMateToBall(worldModel);
		
		assertThat(mateWithBalls.getDistanceToPlayer()).isCloseTo(P1.getDistanceToPlayer(), withinPercentage(0.1));
		assertThat(mateWithBalls.getAngleToPlayer()).isCloseTo(P1.getAngleToPlayer(), withinPercentage(0.1));
		
		worldModel = new RawWorldData();
		worldModel.setBallPosition(new BallPosition(TestScenario.ballDistance, TestScenario.ballAngle, true));
		
		mateWithBalls = PlayersLib.nearestMateToBall(worldModel);
		
		assertThat(mateWithBalls==null).isEqualTo(true);
		
		worldModel = TestScenario.getExampleWorldModel();
		
	}

	@Test
	public void testNearestOpponentToBall() {
		FellowPlayer opponentWithBalls = PlayersLib.nearestOpponentToBall(worldModel);
		
		assertThat(opponentWithBalls.getDistanceToPlayer()).isCloseTo(TestScenario.opponent1Distance, withinPercentage(0.1));
		assertThat(opponentWithBalls.getAngleToPlayer()).isCloseTo(TestScenario.opponent1Angle, withinPercentage(0.1));
		
		
		FellowPlayer P1 = new FellowPlayer();
		P1.set(new ReferencePoint(TestScenario.ballDistance+20, TestScenario.ballAngle, true));
		worldModel.setOpponentPlayer(P1);
		
		opponentWithBalls=PlayersLib.nearestOpponentToBall(worldModel);
		
		assertThat(opponentWithBalls.getDistanceToPlayer()).isCloseTo(P1.getDistanceToPlayer(), withinPercentage(0.1));
		assertThat(opponentWithBalls.getAngleToPlayer()).isCloseTo(P1.getAngleToPlayer(), withinPercentage(0.1));
		
		worldModel = new RawWorldData();
		worldModel.setBallPosition(new BallPosition(TestScenario.ballDistance, TestScenario.ballAngle, true));
		opponentWithBalls = PlayersLib.nearestOpponentToBall(worldModel);
		
		assertThat(opponentWithBalls == null).isEqualTo(true);
		
		worldModel = TestScenario.getExampleWorldModel();
	}

	@Test
	public void testNearestPlayerToBall() {
		//Case 1: FellowPlayer 1 is nearest
		FellowPlayer unknownWithBalls = PlayersLib.nearestPlayerToBall(worldModel);
		
		assertThat(unknownWithBalls.getDistanceToPlayer()).isCloseTo(TestScenario.fellow1Distance, withinPercentage(0.1));
		assertThat(unknownWithBalls.getAngleToPlayer()).isCloseTo(TestScenario.fellow1Angle, withinPercentage(0.1));

		//Case 2: New Opponent Player P1 is neareest
		FellowPlayer P1 = new FellowPlayer();
		P1.set(new ReferencePoint(TestScenario.ballDistance+20, TestScenario.ballAngle, true));
		worldModel.setOpponentPlayer(P1);
	
		unknownWithBalls=PlayersLib.nearestPlayerToBall(worldModel);
		
		assertThat(unknownWithBalls.getDistanceToPlayer()).isCloseTo(P1.getDistanceToPlayer(), withinPercentage(0.1));
		assertThat(unknownWithBalls.getAngleToPlayer()).isCloseTo(P1.getAngleToPlayer(), withinPercentage(0.1));
		
		//Case 3: P1 is FellowPlayer with no Opponent Player
		worldModel = new RawWorldData();
		worldModel.setFellowPlayer(P1);
		worldModel.setBallPosition(new BallPosition(TestScenario.ballDistance, TestScenario.ballAngle, true));
		
		unknownWithBalls=PlayersLib.nearestPlayerToBall(worldModel);
		
		assertThat(unknownWithBalls.getDistanceToPlayer()).isCloseTo(P1.getDistanceToPlayer(), withinPercentage(0.1));
		assertThat(unknownWithBalls.getAngleToPlayer()).isCloseTo(P1.getAngleToPlayer(), withinPercentage(0.1));
		worldModel.setBallPosition(new BallPosition(TestScenario.ballDistance, TestScenario.ballAngle, true));

		//Case 3.1: P1 is OpponentPlayer with no FellowPlayer
		worldModel = new RawWorldData();
		worldModel.setOpponentPlayer(P1);
		worldModel.setBallPosition(new BallPosition(TestScenario.ballDistance, TestScenario.ballAngle, true));
		
		unknownWithBalls=PlayersLib.nearestPlayerToBall(worldModel);
		
		assertThat(unknownWithBalls.getDistanceToPlayer()).isCloseTo(P1.getDistanceToPlayer(), withinPercentage(0.1));
		assertThat(unknownWithBalls.getAngleToPlayer()).isCloseTo(P1.getAngleToPlayer(), withinPercentage(0.1));
		
		worldModel = new RawWorldData();
		//Case 4: No player
		
		FellowPlayer hauntedOpponentWithBalls = PlayersLib.nearestPlayerToBall(worldModel);
		
		assertThat(hauntedOpponentWithBalls).isEqualTo(null);
		
		worldModel = TestScenario.getExampleWorldModel();
	}

	@Test
	public void testIsEnemyOnWayToRefPoint() {
		
		//Case 1: Enemy direct between refpoint and Player
		ReferencePoint testRefPoint = new ReferencePoint(TestScenario.opponent1Distance, TestScenario.opponent1Angle, true);
		testRefPoint.setDistanceToPoint(TestScenario.opponent1Distance + 30);
		Boolean testBool = PlayersLib.isEnemyOnWayToRefPoint(worldModel, testRefPoint, 0);
		
		assertThat(testBool).isEqualTo(true);

		//Case 2: Enemy behind refpoint
		testRefPoint.setDistanceToPoint(TestScenario.opponent1Distance - 20);
		testBool = PlayersLib.isEnemyOnWayToRefPoint(worldModel, testRefPoint, 0);
		
		assertThat(testBool).isEqualTo(false);
		
		//Case 3: Enemy slightly in way, function call without tolerance
		testRefPoint.setDistanceToPoint(TestScenario.opponent1Distance + 30);
		testRefPoint.setAngleToPoint(testRefPoint.getAngleToPoint() + 0.5);
		testBool = PlayersLib.isEnemyOnWayToRefPoint(worldModel, testRefPoint, 0);
		
		assertThat(testBool).isEqualTo(false);
		
		//Case 4: Enemy slightly in way, function call without tolerance
		testRefPoint.setAngleToPoint(testRefPoint.getAngleToPoint() + 0.5);
		testBool = PlayersLib.isEnemyOnWayToRefPoint(worldModel, testRefPoint, 1.2);
		
		assertThat(testBool).isEqualTo(true);
		
		//Case 5+: Angle Exceeds maximum (+-180)

		testRefPoint.setAngleToPoint(179.0);
		testBool = PlayersLib.isEnemyOnWayToRefPoint(worldModel, testRefPoint, 1.2);

		assertThat(testBool).isEqualTo(false);		

		testRefPoint.setAngleToPoint(-179.0);
		testBool = PlayersLib.isEnemyOnWayToRefPoint(worldModel, testRefPoint, 1.2);

		assertThat(testBool).isEqualTo(false);		
		
		
		
	}

	@Test
	public void testIsEnemyInCorridorBetweenTwoAnglesSwitchedAngles()
	{

		double angleLeft = TestScenario.opponent1Angle -10;
		double angleRight = TestScenario.opponent1Angle +10;
		when(mWorldDataMock.getListOfOpponents()).thenReturn(new ArrayList<>());
		assertThat(PlayersLib.isEnemyInCorridorBetweenTwoAngles(mWorldDataMock, angleLeft, angleRight)==PlayersLib.isEnemyInCorridorBetweenTwoAngles(mWorldDataMock, angleRight, angleLeft));
		
	}
	
	@Test
	public void testIsEnemyInCorridorBetweenTwoRefPointsNoOpponents()
	{
		ReferencePoint testRefPoint1 = new ReferencePoint(0, TestScenario.opponent1Angle -10, true);
		ReferencePoint testRefPoint2 = new ReferencePoint(0, TestScenario.opponent1Angle +10, true);

		when(mWorldDataMock.getListOfOpponents()).thenReturn(new ArrayList<>());
		assertThat(PlayersLib.isEnemyInCorridorBetweenTwoRefPoints(mWorldDataMock, testRefPoint1, testRefPoint2)).isFalse();
	}
	
	@Test
	public void testIsEnemyInCorridorBetweenTwoAnglesNoOpponents()
	{
		double angleLeft = TestScenario.opponent1Angle -10;
		double angleRight = TestScenario.opponent1Angle +10;
		
		when(mWorldDataMock.getListOfOpponents()).thenReturn(new ArrayList<>());
		assertThat(PlayersLib.isEnemyInCorridorBetweenTwoAngles(mWorldDataMock, angleLeft, angleRight)).isFalse();
	}

	@Test
	public void testIsEnemyInCorridorBetweenTwoRefPointsNoOpponentsInAngle()
	{
		ReferencePoint testRefPoint1 = new ReferencePoint(0, 130, true);
		ReferencePoint testRefPoint2 = new ReferencePoint(0, 150, true);
		
		List<FellowPlayer> vListOfOpponents = new ArrayList<>();
		vListOfOpponents.add(new FellowPlayer(1, "", true, 200, 100, 0));
		vListOfOpponents.add(new FellowPlayer(2, "", true, 300, 90, 0));
		vListOfOpponents.add(new FellowPlayer(3, "", true, 400, 80, 0));
		
		when(mWorldDataMock.getListOfOpponents()).thenReturn(vListOfOpponents);

		assertThat(PlayersLib.isEnemyInCorridorBetweenTwoRefPoints(mWorldDataMock, testRefPoint1, testRefPoint2)).isFalse();
	}

	@Test
	public void testIsEnemyInCorridorBetweenTwoRefPointsNoOpponentsInRBackwards()
	{
		ReferencePoint testRefPoint1 = new ReferencePoint(0, 170, true);
		ReferencePoint testRefPoint2 = new ReferencePoint(0, -150, true);
		
		List<FellowPlayer> vListOfOpponents = new ArrayList<>();
		vListOfOpponents.add(new FellowPlayer(1, "", true, 200, 100, 0));
		vListOfOpponents.add(new FellowPlayer(2, "", true, 300, 90, 0));
		vListOfOpponents.add(new FellowPlayer(3, "", true, 400, 80, 0));
		
		when(mWorldDataMock.getListOfOpponents()).thenReturn(vListOfOpponents);

		assertThat(PlayersLib.isEnemyInCorridorBetweenTwoRefPoints(mWorldDataMock, testRefPoint1, testRefPoint2)).isFalse();
	}
	
	@Test
	public void testIsEnemyInCorridorBetweenTwoAnglesNoOpponentsInAngleBackwards()
	{
		List<FellowPlayer> vListOfOpponents = new ArrayList<>();
		vListOfOpponents.add(new FellowPlayer(1, "", true, 200, 100, 0));
		vListOfOpponents.add(new FellowPlayer(2, "", true, 300, 90, 0));
		vListOfOpponents.add(new FellowPlayer(3, "", true, 400, 80, 0));
		
		when(mWorldDataMock.getListOfOpponents()).thenReturn(vListOfOpponents);

		assertThat(PlayersLib.isEnemyInCorridorBetweenTwoAngles(mWorldDataMock, 170, -150)).isFalse();
	}

	@Test
	public void testIsEnemyInCorridorBetweenTwoAnglesNoOpponentsInAngle()
	{
		List<FellowPlayer> vListOfOpponents = new ArrayList<>();
		vListOfOpponents.add(new FellowPlayer(1, "", true, 200, 100, 0));
		vListOfOpponents.add(new FellowPlayer(2, "", true, 300, 90, 0));
		vListOfOpponents.add(new FellowPlayer(3, "", true, 400, 80, 0));
		
		when(mWorldDataMock.getListOfOpponents()).thenReturn(vListOfOpponents);

		assertThat(PlayersLib.isEnemyInCorridorBetweenTwoAngles(mWorldDataMock, 130, 150)).isFalse();
	}
	
	@Test
	public void testIsEnemyInCorridorBetweenTwoRefPointsNoOpponentButMateInCorridor() 
	{
		ReferencePoint testRefPoint1 = new ReferencePoint(0, 130, true);
		ReferencePoint testRefPoint2 = new ReferencePoint(0, 150, true);
		
		List<FellowPlayer> vListOfOpponents = new ArrayList<>();
		vListOfOpponents.add(new FellowPlayer(1, "", true, 200, 100, 0));
		vListOfOpponents.add(new FellowPlayer(2, "", true, 300, 90, 0));
		vListOfOpponents.add(new FellowPlayer(3, "", true, 400, 80, 0));
		
		when(mWorldDataMock.getListOfOpponents()).thenReturn(vListOfOpponents);
		
		List<FellowPlayer> vListOfTeamMates = new ArrayList<>();
		vListOfTeamMates.add(new FellowPlayer(1, "", true, 200, 140, 0));
		
		when(mWorldDataMock.getListOfTeamMates()).thenReturn(vListOfTeamMates);
	
		assertThat(PlayersLib.isEnemyInCorridorBetweenTwoRefPoints(mWorldDataMock, testRefPoint1, testRefPoint2)).isFalse();
	}
	
	@Test
	public void testIsEnemyInCorridorBetweenTwoAnglesNoOpponentButMateInCorridor() 
	{
		List<FellowPlayer> vListOfOpponents = new ArrayList<>();
		vListOfOpponents.add(new FellowPlayer(1, "", true, 200, 100, 0));
		vListOfOpponents.add(new FellowPlayer(2, "", true, 300, 90, 0));
		vListOfOpponents.add(new FellowPlayer(3, "", true, 400, 160, 0));
		
		when(mWorldDataMock.getListOfOpponents()).thenReturn(vListOfOpponents);
		
		List<FellowPlayer> vListOfTeamMates = new ArrayList<>();
		vListOfTeamMates.add(new FellowPlayer(1, "", true, 200, 140, 0));
		
		when(mWorldDataMock.getListOfTeamMates()).thenReturn(vListOfTeamMates);
		assertThat(PlayersLib.isEnemyInCorridorBetweenTwoAngles(mWorldDataMock, 130, 150)).isFalse();
	}
	
	@Test
	public void testIsEnemyInCorridorBetweenTwoRefPointsWithOpponent()
	{
		ReferencePoint testRefPoint1 = new ReferencePoint(0, 130, true);
		ReferencePoint testRefPoint2 = new ReferencePoint(0, 150, true);
		
		List<FellowPlayer> vListOfOpponents = new ArrayList<>();
		vListOfOpponents.add(new FellowPlayer(1, "", true, 200, 140, 0));
		
		when(mWorldDataMock.getListOfOpponents()).thenReturn(vListOfOpponents);
		
		assertThat(PlayersLib.isEnemyInCorridorBetweenTwoRefPoints(mWorldDataMock, testRefPoint1, testRefPoint2)).isTrue();
	}
	
	@Test
	public void testIsEnemyInCorridorBetweenTwoAnglesWithOpponent()
	{
		
		List<FellowPlayer> vListOfOpponents = new ArrayList<>();
		vListOfOpponents.add(new FellowPlayer(1, "", true, 200, 140, 0));
		
		when(mWorldDataMock.getListOfOpponents()).thenReturn(vListOfOpponents);
		
		assertThat(PlayersLib.isEnemyInCorridorBetweenTwoAngles(mWorldDataMock, 130, 150)).isTrue();
	}
	
	@Test
	public void testIsEnemyInCorridorBetweenTwoRefPointsWithPositiveOpponentBehindPlayer()
	{
		ReferencePoint testRefPoint1 = new ReferencePoint(0, 170, true);
		ReferencePoint testRefPoint2 = new ReferencePoint(0, -160, true);
		
		List<FellowPlayer> vListOfOpponents = new ArrayList<>();
		vListOfOpponents.add(new FellowPlayer(1, "", true, 200, 175, 0));
		
		when(mWorldDataMock.getListOfOpponents()).thenReturn(vListOfOpponents);
		
		assertThat(PlayersLib.isEnemyInCorridorBetweenTwoRefPoints(mWorldDataMock, testRefPoint1, testRefPoint2)).isTrue();
	}
	
	@Test
	public void testIsEnemyInCorridorBetweenTwoAnglesWithPositiveOpponentBehindPlayer()
	{
		List<FellowPlayer> vListOfOpponents = new ArrayList<>();
		vListOfOpponents.add(new FellowPlayer(1, "", true, 200, 175, 0));
		
		when(mWorldDataMock.getListOfOpponents()).thenReturn(vListOfOpponents);
		
		assertThat(PlayersLib.isEnemyInCorridorBetweenTwoAngles(mWorldDataMock, 170, -160)).isTrue();
	}
	
	@Test
	public void testIsEnemyInCorridorBetweenTwoRefPointsWithNegativeOpponentBehindPlayer()
	{
		ReferencePoint testRefPoint1 = new ReferencePoint(0, 170, true);
		ReferencePoint testRefPoint2 = new ReferencePoint(0, -160, true);
		
		List<FellowPlayer> vListOfOpponents = new ArrayList<>();
		vListOfOpponents.add(new FellowPlayer(1, "", true, 200, -175, 0));
		
		when(mWorldDataMock.getListOfOpponents()).thenReturn(vListOfOpponents);
		
		assertThat(PlayersLib.isEnemyInCorridorBetweenTwoRefPoints(mWorldDataMock, testRefPoint1, testRefPoint2)).isTrue();
	}
	
	@Test
	public void testIsEnemyInCorridorBetweenTwoAnglesWithNegativeOpponentBehindPlayer()
	{
		List<FellowPlayer> vListOfOpponents = new ArrayList<>();
		vListOfOpponents.add(new FellowPlayer(1, "", true, 200, -175, 0));
		
		when(mWorldDataMock.getListOfOpponents()).thenReturn(vListOfOpponents);
		
		assertThat(PlayersLib.isEnemyInCorridorBetweenTwoAngles(mWorldDataMock, 170, -160)).isTrue();
	}
	

	@Test
	public void testIsSpecificEnemyInAngleBetweenTwoRefPoints() {
		//Case 1: Player in small angle
		ReferencePoint testRefPoint1 = new ReferencePoint(0, TestScenario.opponent1Angle -10, true);
		ReferencePoint testRefPoint2 = new ReferencePoint(0, TestScenario.opponent1Angle +10, true);
		FellowPlayer o1 = new FellowPlayer();
		o1 = worldModel.getListOfOpponents().get(0);

		Boolean testBool = PlayersLib.isSpecificEnemyInAngleBetweenTwoRefPoints(o1, testRefPoint1.getAngleToPoint(), testRefPoint2.getAngleToPoint());
		assertThat(testBool).isEqualTo(true);
		

		//Case 1.2: Player in small angle (switched RefPoints)
		 testRefPoint1 = new ReferencePoint(0, TestScenario.opponent1Angle -10, true);
		 testRefPoint2 = new ReferencePoint(0, TestScenario.opponent1Angle +10, true);

		testBool = PlayersLib.isSpecificEnemyInAngleBetweenTwoRefPoints(o1, testRefPoint2.getAngleToPoint(), testRefPoint1.getAngleToPoint());
		assertThat(testBool).isEqualTo(true);
		
		//Case 2: Player not in angle
		testRefPoint1.set(new ReferencePoint(0, TestScenario.opponent1Angle -20, true));
		testRefPoint2.set(new ReferencePoint(0, TestScenario.opponent1Angle -30, true));
		
		testBool = PlayersLib.isSpecificEnemyInAngleBetweenTwoRefPoints(o1, testRefPoint1.getAngleToPoint(), testRefPoint2.getAngleToPoint());
		assertThat(testBool).isEqualTo(false);

		//Case 2.2: Player not in angle (other side)
		testRefPoint1.set(new ReferencePoint(0, TestScenario.opponent1Angle +20, true));
		testRefPoint2.set(new ReferencePoint(0, TestScenario.opponent1Angle +30, true));
		
		testBool = PlayersLib.isSpecificEnemyInAngleBetweenTwoRefPoints(o1, testRefPoint1.getAngleToPoint(), testRefPoint2.getAngleToPoint());
		assertThat(testBool).isEqualTo(false);
		
		
		//Case 3: Very large corridor - returns no because function takes the opposite angle
		testRefPoint1 = new ReferencePoint(0, TestScenario.opponent1Angle-100, true);
		testRefPoint2 = new ReferencePoint(0, TestScenario.opponent1Angle+100, true);

		testBool = PlayersLib.isSpecificEnemyInAngleBetweenTwoRefPoints(o1, testRefPoint1.getAngleToPoint(), testRefPoint2.getAngleToPoint());
		assertThat(testBool).isEqualTo(false);
	}

	@Test
	public void testHasMateTheBall() {

		Boolean testTeamBall = PlayersLib.hasMateTheBall(worldModel, vBotInformation);
		assertThat(testTeamBall).isEqualTo(false);
		
		FellowPlayer P1 = new FellowPlayer();
		P1.set(new ReferencePoint(20, 45,true));
		worldModel.setFellowPlayer(P1);
		
		testTeamBall = PlayersLib.hasMateTheBall(worldModel, vBotInformation);
		assertThat(testTeamBall).isEqualTo(true);
		
		BallPosition bPos = null;
		worldModel.setBallPosition(bPos);
		
	}

	@Test
	public void testIsEnemyNearBall() {
		Boolean testEnemyBall = PlayersLib.isEnemyNearBall(worldModel, vBotInformation);
		assertThat(testEnemyBall).isEqualTo(false);
		
		FellowPlayer P1 = new FellowPlayer();
		P1.set(new ReferencePoint(20, 45,true));
		worldModel.setOpponentPlayer(P1);
		
		testEnemyBall = PlayersLib.isEnemyNearBall(worldModel, vBotInformation);
		assertThat(testEnemyBall).isEqualTo(true);
		
	}

	@Test
	public void testGetDistanceBetweenPlayerAndPoint() {
		FellowPlayer P1 = new FellowPlayer();
		P1.set(new ReferencePoint(77,TestScenario.fellow1Angle,true));
		
		ReferencePoint P2 = new ReferencePoint(66.0, TestScenario.fellow1Angle, true);
		
		double testDistance = PlayersLib.getDistanceBetweenPlayerAndPoint(P1, P2);
		assertThat(testDistance).isEqualTo(11.0);
	
		P1 = PlayersLib.getNearestMate(worldModel);
		
		testDistance = PlayersLib.getDistanceBetweenPlayerAndPoint(P1, P2);
		assertThat(testDistance).isEqualTo(P1.getDistanceToPlayer() - 66.0);
		
	}



}
