package core;

import static org.mockito.Mockito.*;
import static org.awaitility.Awaitility.*;
import static org.awaitility.Duration.*;
import static java.util.concurrent.TimeUnit.*;
import static org.assertj.core.api.Assertions.*;

import java.net.SocketTimeoutException;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MoveAction;

import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import essentials.communication.Action;
import essentials.communication.action_server2008.Movement;
import essentials.communication.worlddata_server2008.RawWorldData;
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

	@Test
	public void testStartWithoutNetworkConnection() {
		when(mCoreMock.getServerConnection()).thenReturn( null );
		
		try{
			mSUT.start();
			fail("Expected Nullpointerexception");
		} catch( Exception vExpectedException ) {
			assertThat(vExpectedException).isInstanceOf(NullPointerException.class);
			assertThat(vExpectedException.getMessage()).isEqualToIgnoringCase( "NetworkCommunication cannot be NULL when starting ToServerManagement." );
		}
	}

	@Test
	public void testStartWithNetworkConnectionAndNotAlive() {
		when(mCoreMock.getServerConnection()).thenReturn( mNetworkCommunicationMock );
		when(mCoreMock.getAI()).thenReturn( null );
		
		mSUT.start();
		
		assertThat(mSUT.isAlive()).isTrue();
		verify(mLoggerMock).info("ToServerManagement started.");
			
	}

	@Test
	public void testStartWithNetworkConnectionAndAlive() {
		when(mCoreMock.getServerConnection()).thenReturn( mNetworkCommunicationMock );
		when(mCoreMock.getAI()).thenReturn( null );
		
		mSUT.start();
		
		try{
			mSUT.startManagement();
			fail("Expected IllegalThreadStateException");
		} catch( Exception vExpectedException ) {
			assertThat(vExpectedException).isInstanceOf(IllegalThreadStateException.class);
			assertThat(vExpectedException.getMessage()).isEqualToIgnoringCase( "ToServerManagement can not be started again." );
		}
			
	}

	@Test
	public void testNameOfThread() {
		assertThat(mSUT.getName()).isEqualToIgnoringCase("ToServerManagement");
	}

	@Test
	public void testGetInstance() {
		ToServerManagement vSaveToCompare = ToServerManagement.getInstance();
		
		assertThat(vSaveToCompare).isInstanceOf(ToServerManagement.class);
		assertThat(vSaveToCompare).isEqualTo(ToServerManagement.getInstance());
	}
	
	@Test
	public void testStopManagementWhenNotAlive() {
		
		assertThat(mSUT.isAlive()).isFalse();
		mSUT.stopManagement();
		assertThat(mSUT.isAlive()).isFalse();
		verify(mLoggerMock, never()).info("ToServerManagement stopped.");
		
	}
	
	@Test
	public void testStopManagementWhenAlive() {
		when(mCoreMock.getServerConnection()).thenReturn( mNetworkCommunicationMock );
		when(mCoreMock.getAI()).thenReturn( null );
		
		mSUT.startManagement();
		assertThat(mSUT.isAlive()).isTrue();
		
		mSUT.stopManagement();
		assertThat(mSUT.isAlive()).isFalse();
		verify(mLoggerMock).info("ToServerManagement stopped.");
		
	}
	
	@Test
	public void testStopManagementWhenAliveAndSuspended() {
		when(mCoreMock.getServerConnection()).thenReturn( mNetworkCommunicationMock );
		when(mCoreMock.getAI()).thenReturn( null );
		
		mSUT.suspendManagement();
		mSUT.startManagement();
		assertThat(mSUT.isAlive()).isTrue();
		
		mSUT.stopManagement();
		assertThat(mSUT.isAlive()).isFalse();
		verify(mLoggerMock).info("ToServerManagement stopped.");
		
	}
	
	@Test
	public void testCloseWhenNotAlive() {
		ToServerManagement vSaveToCompare = ToServerManagement.getInstance();
				
		assertThat(vSaveToCompare.isAlive()).isFalse();
		vSaveToCompare.close();
		assertThat(vSaveToCompare.isAlive()).isFalse();
		verify(mLoggerMock).info("ToServerManagement closed.");
		
		assertThat(vSaveToCompare).isNotEqualTo(ToServerManagement.getInstance());
		
	}
	
	@Test
	public void testCloseWhenAlive() {
		when(mCoreMock.getServerConnection()).thenReturn( mNetworkCommunicationMock );
		when(mCoreMock.getAI()).thenReturn( null );

		ToServerManagement vSaveToCompare = ToServerManagement.getInstance();
		
		vSaveToCompare.startManagement();
		
		vSaveToCompare.close();
		assertThat(vSaveToCompare.isAlive()).isFalse();
		verify(mLoggerMock).info("ToServerManagement closed.");
		
		assertThat(vSaveToCompare).isNotEqualTo(ToServerManagement.getInstance());
		
	}
	
	@Test
	public void testCloseWhenNull() {

		ToServerManagement vSaveToCompare = ToServerManagement.getInstance();
		
		ToServerManagement.setInstanceNull();
		
		vSaveToCompare.close();
		assertThat(vSaveToCompare.isAlive()).isFalse();
		
		assertThat(vSaveToCompare).isNotEqualTo(ToServerManagement.getInstance());
		
	}

	@Test
	public void testSuspendWhenNotAlive() {
		
		assertThat(mSUT.isSuspended()).isFalse();

		mSUT.suspendManagement();		
		
		assertThat(mSUT.isSuspended()).isTrue();
		verify(mLoggerMock).info("ToServerManagement suspended.");
		
	}

	@Test
	public void testResumeWhenNotAlive() {

		mSUT.suspendManagement();
		
		assertThat(mSUT.isSuspended()).isTrue();

		mSUT.resumeManagement();		
		
		assertThat(mSUT.isSuspended()).isFalse();
		verify(mLoggerMock).info("ToServerManagement resumed.");
		
	}

	@Test
	public void testSuspendWhenAlive() {
		when(mCoreMock.getServerConnection()).thenReturn( mNetworkCommunicationMock );
		when(mCoreMock.getAI()).thenReturn( null );
		
		mSUT.startManagement();
		
		assertThat(mSUT.isSuspended()).isFalse();

		mSUT.suspendManagement();		
		
		assertThat(mSUT.isSuspended()).isTrue();
		assertThat(mSUT.isAlive()).isTrue();
		verify(mLoggerMock).info("ToServerManagement suspended.");
		
	}

	@Test
	public void testResumeWhenAlive() {
		when(mCoreMock.getServerConnection()).thenReturn( mNetworkCommunicationMock );
		when(mCoreMock.getAI()).thenReturn( null );
		
		mSUT.startManagement();

		mSUT.suspendManagement();
		
		assertThat(mSUT.isSuspended()).isTrue();

		mSUT.resumeManagement();		
		
		assertThat(mSUT.isSuspended()).isFalse();
		assertThat(mSUT.isAlive()).isTrue();
		verify(mLoggerMock).info("ToServerManagement resumed.");
		
	}

	@Test
	public void testIsSendingMessagesWhenNotAlive() {
		assertThat(mSUT.isAlive()).isFalse();
		
		assertThat(mSUT.isSendingMessages()).isFalse();
	}

	@Test
	public void testIsSendingMessagesWhenAliveAndHasNoAction() {
		when(mCoreMock.getServerConnection()).thenReturn( mNetworkCommunicationMock );
		when(mCoreMock.getAI()).thenReturn( mArtificialIntelligenceMock );
		when(mArtificialIntelligenceMock.getAction()).thenAnswer(new Answer<Action>() {
			@Override
			public Action answer(InvocationOnMock invocation) throws Throwable {
				return new Movement((int)(Math.random()*100), (int)(Math.random()*100));
			}
		});
		
		mSUT.startManagement();
		
		assertThat(mSUT.isAlive()).isTrue();
		await().atMost(2, SECONDS).untilAsserted(()->assertThat(mSUT.isSendingMessages()).isTrue());
		when(mArtificialIntelligenceMock.getAction()).thenReturn(Movement.NO_MOVEMENT);
		
		assertThat(mSUT.isAlive()).isTrue();
		await().atMost(2, SECONDS).untilAsserted(()->assertThat(mSUT.isSendingMessages()).isFalse());
	}

	@Test
	public void testIsSendingMessagesWhenAliveAndHasMessages() throws Exception {
		when(mCoreMock.getServerConnection()).thenReturn( mNetworkCommunicationMock );
		when(mCoreMock.getAI()).thenReturn( mArtificialIntelligenceMock );
		when(mArtificialIntelligenceMock.getAction()).thenAnswer(new Answer<Action>() {
			@Override
			public Action answer(InvocationOnMock invocation) throws Throwable {
				return new Movement((int)(Math.random()*100), (int)(Math.random()*100));
			}
		});
		
		mSUT.startManagement();
		
		assertThat(mSUT.isAlive()).isTrue();
		await().atMost(2, SECONDS).untilAsserted(()->assertThat(mSUT.isSendingMessages()).isTrue());
	}

}
