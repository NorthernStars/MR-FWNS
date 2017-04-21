package core;

import static org.mockito.Mockito.*;
import static org.awaitility.Awaitility.*;
import static org.awaitility.Duration.*;
import static java.util.concurrent.TimeUnit.*;
import static org.assertj.core.api.Assertions.*;

import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import essentials.core.ArtificialIntelligence;
import fwns_network.server_2008.NetworkCommunication;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Core.class})
@PowerMockIgnore({"javax.management.*"})
public class ToServerManagementTest {

	Core mCoreMock = mock(Core.class);
	Logger mLoggerMock = mock(Logger.class);
	
	NetworkCommunication mNetworkCommunicationMock = mock(NetworkCommunication.class);
	ArtificialIntelligence mArtificialIntelligenceMock = mock(ArtificialIntelligence.class);
	
	ToServerManagement mSUT;
	
	@Before
	public void setUp() throws Exception {
		PowerMockito.mockStatic(Core.class);
		when(Core.getInstance()).thenReturn(mCoreMock);
		when(Core.getLogger()).thenReturn(mLoggerMock);
		
		mSUT = new ToServerManagement();
	}

	@After
	public void tearDown() throws Exception {
		if(mSUT != null){
			mSUT.stopManagement();
		}
	}

	@Test
	public void testStartManagementWithoutNetworkConnection() {
		when(mCoreMock.getServerConnection()).thenReturn( null );
		
		try{
			mSUT.startManagement();
			fail("Expected Nullpointerexception");
		} catch( Exception vExpectedException ) {
			assertThat(vExpectedException).isInstanceOf(NullPointerException.class);
			assertThat(vExpectedException.getMessage()).isEqualToIgnoringCase( "NetworkCommunication cannot be NULL when starting ToServerManagement." );
		}
	}

	@Test
	public void testStartManagementWithNetworkConnectionAndNotAlive() {
		when(mCoreMock.getServerConnection()).thenReturn( mNetworkCommunicationMock );
		when(mCoreMock.getAI()).thenReturn( null );
		
		mSUT.startManagement();
		
		assertThat(mSUT.isAlive()).isTrue();
		verify(mLoggerMock).info("ToServerManagement started.");
			
	}

	@Test
	public void testStartManagementWithNetworkConnectionAndAlive() {
		when(mCoreMock.getServerConnection()).thenReturn( mNetworkCommunicationMock );
		when(mCoreMock.getAI()).thenReturn( null );
		
		mSUT.startManagement();
		
		try{
			mSUT.startManagement();
			fail("Expected IllegalThreadStateException");
		} catch( Exception vExpectedException ) {
			assertThat(vExpectedException).isInstanceOf(IllegalThreadStateException.class);
			assertThat(vExpectedException.getMessage()).isEqualToIgnoringCase( "ToServerManagement can not be started again." );
		}
			
	}

}
