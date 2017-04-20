package core;

import static org.mockito.Mockito.*;
import static org.awaitility.Awaitility.*;
import static org.awaitility.Duration.*;
import static java.util.concurrent.TimeUnit.*;

import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import static org.assertj.core.api.Assertions.*;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito.Then;
import org.mockito.internal.stubbing.answers.ThrowsException;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import essentials.communication.WorldData;
import essentials.communication.worlddata_server2008.RawWorldData;
import essentials.core.ArtificialIntelligence;
import fwns_network.server_2008.NetworkCommunication;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Core.class})
@PowerMockIgnore({"javax.management.*"})
public class FromServerManagementTest {

	Core mCoreMock = mock(Core.class);
	Logger mLoggerMock = mock(Logger.class);
	
	NetworkCommunication mNetworkCommunicationMock = mock(NetworkCommunication.class);
	ArtificialIntelligence mArtificialIntelligenceMock = mock(ArtificialIntelligence.class);
	
	FromServerManagement mSUT;
	
	@Before
	public void setUp() throws Exception {
		PowerMockito.mockStatic(Core.class);
		when(Core.getInstance()).thenReturn(mCoreMock);
		when(Core.getLogger()).thenReturn(mLoggerMock);
		
		mSUT = new FromServerManagement();
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
			assertThat(vExpectedException.getMessage()).isEqualToIgnoringCase( "NetworkCommunication cannot be NULL when starting FromServerManagement." );
		}
	}

	@Test
	public void testStartManagementWithNetworkConnectionAndNotAlive() {
		when(mCoreMock.getServerConnection()).thenReturn( mNetworkCommunicationMock );
		when(mCoreMock.getAI()).thenReturn( null );
		
		mSUT.startManagement();
		
		assertThat(mSUT.isAlive()).isTrue();
		verify(mLoggerMock).info("FromServerManagement started.");
			
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
			assertThat(vExpectedException.getMessage()).isEqualToIgnoringCase( "FromServerManagement can not be started again." );
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
			assertThat(vExpectedException.getMessage()).isEqualToIgnoringCase( "NetworkCommunication cannot be NULL when starting FromServerManagement." );
		}
	}

	@Test
	public void testStartWithNetworkConnectionAndNotAlive() {
		when(mCoreMock.getServerConnection()).thenReturn( mNetworkCommunicationMock );
		when(mCoreMock.getAI()).thenReturn( null );
		
		mSUT.start();
		
		assertThat(mSUT.isAlive()).isTrue();
		verify(mLoggerMock).info("FromServerManagement started.");
			
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
			assertThat(vExpectedException.getMessage()).isEqualToIgnoringCase( "FromServerManagement can not be started again." );
		}
			
	}

	@Test
	public void testNameOfThread() {
		assertThat(mSUT.getName()).isEqualToIgnoringCase("FromServerManagement");
	}

	@Test
	public void testGetInstance() {
		FromServerManagement vSaveToCompare = FromServerManagement.getInstance();
		
		assertThat(vSaveToCompare).isInstanceOf(FromServerManagement.class);
		assertThat(vSaveToCompare).isEqualTo(FromServerManagement.getInstance());
	}
	
	@Test
	public void testStopManagementWhenNotAlive() {
		
		assertThat(mSUT.isAlive()).isFalse();
		mSUT.stopManagement();
		assertThat(mSUT.isAlive()).isFalse();
		verify(mLoggerMock, never()).info("FromServerManagement stopped.");
		
	}
	
	@Test
	public void testStopManagementWhenAlive() {
		when(mCoreMock.getServerConnection()).thenReturn( mNetworkCommunicationMock );
		when(mCoreMock.getAI()).thenReturn( null );
		
		mSUT.startManagement();
		
		mSUT.stopManagement();
		assertThat(mSUT.isAlive()).isFalse();
		verify(mLoggerMock).info("FromServerManagement stopped.");
		
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
		verify(mLoggerMock).info("FromServerManagement stopped.");
		
	}
	
	@Test
	public void testCloseWhenNotAlive() {
		FromServerManagement vSaveToCompare = FromServerManagement.getInstance();
				
		assertThat(vSaveToCompare.isAlive()).isFalse();
		vSaveToCompare.close();
		assertThat(vSaveToCompare.isAlive()).isFalse();
		verify(mLoggerMock).info("FromServerManagement closed.");
		
		assertThat(vSaveToCompare).isNotEqualTo(FromServerManagement.getInstance());
		
	}
	
	@Test
	public void testCloseWhenAlive() {
		when(mCoreMock.getServerConnection()).thenReturn( mNetworkCommunicationMock );
		when(mCoreMock.getAI()).thenReturn( null );

		FromServerManagement vSaveToCompare = FromServerManagement.getInstance();
		
		vSaveToCompare.startManagement();
		
		vSaveToCompare.close();
		assertThat(vSaveToCompare.isAlive()).isFalse();
		verify(mLoggerMock).info("FromServerManagement closed.");
		
		assertThat(vSaveToCompare).isNotEqualTo(FromServerManagement.getInstance());
		
	}
	
	@Test
	public void testCloseWhenNull() {

		FromServerManagement vSaveToCompare = FromServerManagement.getInstance();
		
		FromServerManagement.setInstanceNull();
		
		vSaveToCompare.close();
		assertThat(vSaveToCompare.isAlive()).isFalse();
		
		assertThat(vSaveToCompare).isNotEqualTo(FromServerManagement.getInstance());
		
	}

	@Test
	public void testSuspendWhenNotAlive() {
		
		assertThat(mSUT.isSuspended()).isFalse();

		mSUT.suspendManagement();		
		
		assertThat(mSUT.isSuspended()).isTrue();
		verify(mLoggerMock).info("FromServerManagement suspended.");
		
	}

	@Test
	public void testResumeWhenNotAlive() {

		mSUT.suspendManagement();
		
		assertThat(mSUT.isSuspended()).isTrue();

		mSUT.resumeManagement();		
		
		assertThat(mSUT.isSuspended()).isFalse();
		verify(mLoggerMock).info("FromServerManagement resumed.");
		
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
		verify(mLoggerMock).info("FromServerManagement suspended.");
		
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
		verify(mLoggerMock).info("FromServerManagement resumed.");
		
	}

	@Test
	public void testIsReceivingMessagesWhenNotAlive() {
		assertThat(mSUT.isAlive()).isFalse();
		
		assertThat(mSUT.isReceivingMessages()).isFalse();
	}

	@Test
	public void testIsReceivingMessagesWhenAliveAndNoMessages() {
		when(mCoreMock.getServerConnection()).thenReturn( mNetworkCommunicationMock );
		when(mCoreMock.getAI()).thenReturn( null );
		
		mSUT.startManagement();
		
		assertThat(mSUT.isAlive()).isTrue();
		assertThat(mSUT.isReceivingMessages()).isFalse();
	}

	@Test
	public void testIsReceivingMessagesWhenAliveAndIncomingMessages() throws Exception {
		when(mCoreMock.getServerConnection()).thenReturn( mNetworkCommunicationMock );
		when(mCoreMock.getAI()).thenReturn( mArtificialIntelligenceMock );
		doReturn(new RawWorldData().toXMLString()).when(mNetworkCommunicationMock).getDatagramm(1000);
		
		mSUT.startManagement();
		
		assertThat(mSUT.isAlive()).isTrue();
		await().atMost(2, SECONDS).untilAsserted(()->assertThat(mSUT.isReceivingMessages()).isTrue());
	}

	@Test
	public void testRunWithoutStart() throws Exception {
		assertThat(mSUT.isAlive()).isFalse();
		mSUT.run();
		assertThat(mSUT.isAlive()).isFalse();
		assertThat(mSUT.isReceivingMessages()).isFalse();
	}

	@Test
	public void testRunWhileSuspended() throws Exception {
		when(mCoreMock.getServerConnection()).thenReturn( mNetworkCommunicationMock );
		when(mCoreMock.getAI()).thenReturn( mArtificialIntelligenceMock );
		doReturn(new RawWorldData().toXMLString()).when(mNetworkCommunicationMock).getDatagramm(1000);
		
		mSUT.suspendManagement();
		mSUT.startManagement();
		
		assertThat(mSUT.isAlive()).isTrue();
		await().atMost(2, SECONDS).untilAsserted(()->assertThat(mSUT.isReceivingMessages()).isFalse());
	}

	@Test
	public void testRunWithSocketTimeOut() throws Exception {
		when(mCoreMock.getServerConnection()).thenReturn( mNetworkCommunicationMock );
		when(mCoreMock.getAI()).thenReturn( mArtificialIntelligenceMock );
		
		SocketTimeoutException vTestException = new SocketTimeoutException();
		when(mNetworkCommunicationMock.getDatagramm(1000)).thenThrow(vTestException);
		
		mSUT.startManagement();
		
		assertThat(mSUT.isAlive()).isTrue();
		await().atMost(2, SECONDS).untilAsserted(()->assertThat(mSUT.isReceivingMessages()).isFalse());

		await().atMost(2, SECONDS).untilAsserted(()->verify(mLoggerMock, atLeast(1)).error("Receiving no messages from server null"));
		verify(mLoggerMock, atLeast(1)).catching( Level.ERROR, vTestException);
	}

	@Test
	public void testRunWithGenericException() throws Exception {
		when(mCoreMock.getServerConnection()).thenReturn( mNetworkCommunicationMock );
		when(mCoreMock.getAI()).thenReturn( mArtificialIntelligenceMock );
		
		NullPointerException vTestException = new NullPointerException();
		when(mNetworkCommunicationMock.getDatagramm(1000)).thenThrow(vTestException);
		
		mSUT.startManagement();
		
		assertThat(mSUT.isAlive()).isTrue();
		await().atMost(2, SECONDS).untilAsserted(()->assertThat(mSUT.isReceivingMessages()).isFalse());

		await().atMost(2, SECONDS).untilAsserted(()->verify(mLoggerMock, atLeast(1)).error("Error receiving messages from server null"));
		verify(mLoggerMock, atLeast(1)).catching( Level.ERROR, vTestException);
	}

	@Test
	public void testRecieveMessagesWithoutAI() throws Exception {
		when(mCoreMock.getServerConnection()).thenReturn( mNetworkCommunicationMock );
		when(mCoreMock.getAI()).thenReturn( null );
		
		mSUT.startManagement();
		
		assertThat(mSUT.isAlive()).isTrue();
		await().atMost(2, SECONDS).untilAsserted(()->assertThat(mSUT.isReceivingMessages()).isFalse());

		await().atMost(2, SECONDS).untilAsserted(()->verify(mLoggerMock, atLeast(1)).debug( "Without actual AI all messages from the server will be discarded." ));
	}

	@Test
	public void testRecieveMessagesWithAI() throws Exception {
		when(mCoreMock.getServerConnection()).thenReturn( mNetworkCommunicationMock );
		when(mCoreMock.getAI()).thenReturn( mArtificialIntelligenceMock );
		WorldData vTestData = RawWorldData.createRawWorldDataFromXML("<rawWorldData><time>0</time><agent_id>20</agent_id><nickname>TestBot</nickname><status>found</status></rawWorldData>");
		doReturn(((RawWorldData)vTestData).toXMLString()).when(mNetworkCommunicationMock).getDatagramm(1000);
		
		mSUT.startManagement();
		
		assertThat(mSUT.isAlive()).isTrue();
		await().atMost(2, SECONDS).untilAsserted(()->verify(mArtificialIntelligenceMock, atLeast(1)).putWorldState(vTestData));
	}

	@Test
	public void testLoseServerConnectionWhileRecievingMessages() throws Exception {
		when(mCoreMock.getServerConnection()).thenReturn( mNetworkCommunicationMock );
		when(mCoreMock.getAI()).thenReturn( mArtificialIntelligenceMock );
		doReturn(new RawWorldData().toXMLString()).when(mNetworkCommunicationMock).getDatagramm(1000);
		
		mSUT.startManagement();
		
		assertThat(mSUT.isAlive()).isTrue();
		when(mCoreMock.getServerConnection()).thenReturn( null );
		assertThat(mSUT.isAlive()).isTrue();
		await().atMost(2, SECONDS).untilAsserted(()->verify(mLoggerMock, atLeast(1)).debug( "NetworkCommunication cannot be NULL when running FromServerManagement." ));
	}
	
}
