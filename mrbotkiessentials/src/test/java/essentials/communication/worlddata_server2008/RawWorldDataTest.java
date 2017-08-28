package essentials.communication.worlddata_server2008;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.spy;

public class RawWorldDataTest {
	
	RawWorldData rawWorldData = new RawWorldData();
	RawWorldData rawWorldDataSpy = spy(rawWorldData);

	@Before
	public void setUp() throws Exception 
	{
	}


	@Test
	public void testSetOpponentPlayerWhenListEmpty() 
	{
		FellowPlayer fp1 = new FellowPlayer(1, "", true, 200, 100, 0);
		
		when(rawWorldDataSpy.getListOfOpponents()).thenReturn(null);
		rawWorldDataSpy.setOpponentPlayer(fp1);
		
		when(rawWorldDataSpy.getListOfOpponents()).thenCallRealMethod();
		assert(rawWorldDataSpy.getListOfOpponents().get(rawWorldDataSpy.getListOfOpponents().size()-1).equals(fp1));
	}
	
	@Test
	public void testSetOpponentPlayerWhenAlreadyOneOpponent() 
	{
		FellowPlayer fp1 = new FellowPlayer(1, "", true, 200, 100, 0);
		FellowPlayer fp2 = new FellowPlayer(2, "", true, 250, 150, 0);
		
		ArrayList<FellowPlayer> fpList = new ArrayList<>();
		fpList.add(fp1);
		
		when(rawWorldDataSpy.getListOfOpponents()).thenReturn(fpList);
		rawWorldDataSpy.setOpponentPlayer(fp2);
		
		when(rawWorldDataSpy.getListOfOpponents()).thenCallRealMethod();
		assert(rawWorldDataSpy.getListOfOpponents().get(rawWorldDataSpy.getListOfOpponents().size()-1).equals(fp2));
	}
	

	@Test
	public void testSetTeamMatePlayerWhenListEmpty() 
	{
		FellowPlayer fp1 = new FellowPlayer(1, "", true, 200, 100, 0);
		
		when(rawWorldDataSpy.getListOfTeamMates()).thenReturn(null);
		rawWorldDataSpy.setFellowPlayer(fp1);
		
		when(rawWorldDataSpy.getListOfTeamMates()).thenCallRealMethod();
		assert(rawWorldDataSpy.getListOfTeamMates().get(rawWorldDataSpy.getListOfTeamMates().size()-1).equals(fp1));
	}
	
	@Test
	public void testSetTeamMatePlayerWhenAlreadyOneTeamMate() 
	{
		FellowPlayer fp1 = new FellowPlayer(1, "", true, 200, 100, 0);
		FellowPlayer fp2 = new FellowPlayer(2, "", true, 250, 150, 0);
		
		ArrayList<FellowPlayer> fpList = new ArrayList<>();
		fpList.add(fp1);
		
		when(rawWorldDataSpy.getListOfTeamMates()).thenReturn(fpList);
		rawWorldDataSpy.setFellowPlayer(fp2);
		
		when(rawWorldDataSpy.getListOfTeamMates()).thenCallRealMethod();
		assert(rawWorldDataSpy.getListOfTeamMates().get(rawWorldDataSpy.getListOfTeamMates().size()-1).equals(fp2));
	}
	
	@Test
	public void getReferencePointTest_bottom_center()
	{
		ReferencePoint refPointBottomCenter = rawWorldData.getReferencePoint(ReferencePointName.Ball);
		assert(refPointBottomCenter.getPointName().equals(ReferencePointName.Ball));
	}
	
	@Test
	public void setBallPositionTestNull()
	{
		rawWorldDataSpy.setBallPosition(null);
		assert(rawWorldDataSpy.getBallPosition().equals(new BallPosition()));
	}
	
	@Test
	public void setBallPositionTest()
	{
		BallPosition bp = new BallPosition(30, 40, true);
		
		rawWorldDataSpy.setBallPosition(bp);
		assert(rawWorldDataSpy.getBallPosition().equals(bp));
	}
}
