package core;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import essentials.communication.Action;
import essentials.communication.action_server2008.Movement;
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

	@Test
	public void testRunWithoutStart() throws Exception {
		when(mCoreMock.getServerConnection()).thenReturn( mNetworkCommunicationMock );
		when(mCoreMock.getAI()).thenReturn( mArtificialIntelligenceMock );
		when(mArtificialIntelligenceMock.getAction()).thenAnswer(new Answer<Action>() {
			@Override
			public Action answer(InvocationOnMock invocation) throws Throwable {
				return new Movement((int)(Math.random()*100), (int)(Math.random()*100));
			}
		});
		
		assertThat(mSUT.isAlive()).isFalse();
		mSUT.run();
		assertThat(mSUT.isAlive()).isFalse();
		assertThat(mSUT.isSendingMessages()).isFalse();

		verifyZeroInteractions(mNetworkCommunicationMock);
		verifyZeroInteractions(mArtificialIntelligenceMock);
		
	}

	@Test
	public void testRunWhileSuspended() throws Exception {
		when(mCoreMock.getServerConnection()).thenReturn( mNetworkCommunicationMock );
		when(mCoreMock.getAI()).thenReturn( mArtificialIntelligenceMock );
		when(mArtificialIntelligenceMock.getAction()).thenAnswer(new Answer<Action>() {
			@Override
			public Action answer(InvocationOnMock invocation) throws Throwable {
				int vI = (int)(Math.random()*100)%2;
				return new Movement(vI, vI);
			}
		});
		
		mSUT.suspendManagement();
		mSUT.startManagement();
		
		assertThat(mSUT.isAlive()).isTrue();
		await().atMost(2, SECONDS).untilAsserted(()->assertThat(mSUT.isSendingMessages()).isFalse());

		verify(mNetworkCommunicationMock, atMost(1)).sendDatagramm(new Movement(0,0));
		verify(mNetworkCommunicationMock, atMost(1)).sendDatagramm(new Movement(1,1));
		
	}

	@Test
	public void testRunWithGenericException() throws Exception {
		when(mCoreMock.getServerConnection()).thenReturn( mNetworkCommunicationMock );
		when(mCoreMock.getAI()).thenReturn( mArtificialIntelligenceMock );
		when(mArtificialIntelligenceMock.getAction()).thenAnswer(new Answer<Action>() {
			@Override
			public Action answer(InvocationOnMock invocation) throws Throwable {
				int vI = (int)(Math.random()*100)%2;
				return new Movement(vI, vI);
			}
		});
		
		IOException vTestException = new IOException();
		doThrow(vTestException).when(mNetworkCommunicationMock).sendDatagramm(new Movement(0,0));
		doThrow(vTestException).when(mNetworkCommunicationMock).sendDatagramm(new Movement(1,1));
		
		mSUT.startManagement();
		
		assertThat(mSUT.isAlive()).isTrue();
		await().atMost(2, SECONDS).untilAsserted(()->assertThat(mSUT.isSendingMessages()).isFalse());

		await().atMost(2, SECONDS).untilAsserted(()->verify(mLoggerMock, atLeast(1)).error("Error sending messages to server null"));
		verify(mLoggerMock, atLeast(1)).catching( Level.ERROR, vTestException);
	}

	@Test
	public void testSendMessagesWithoutAI() throws Exception {
		when(mCoreMock.getServerConnection()).thenReturn( mNetworkCommunicationMock );
		when(mCoreMock.getAI()).thenReturn( null );
		
		mSUT.startManagement();
		
		assertThat(mSUT.isAlive()).isTrue();
		await().atMost(2, SECONDS).untilAsserted(()->assertThat(mSUT.isSendingMessages()).isFalse());

		await().atMost(2, SECONDS).untilAsserted(()->verify(mLoggerMock, atLeast(1)).debug( "Without actual AI only empty messages will be sent to the Server." ));
		
		InOrder inOrder = inOrder(mNetworkCommunicationMock);
		inOrder.verify(mNetworkCommunicationMock, atLeastOnce()).sendDatagramm(Movement.NO_MOVEMENT);
	}

	@Test
	public void testSendMessagesWithAI() throws Exception {
		when(mCoreMock.getServerConnection()).thenReturn( mNetworkCommunicationMock );
		when(mCoreMock.getAI()).thenReturn( mArtificialIntelligenceMock );
		when(mArtificialIntelligenceMock.getAction()).thenAnswer(new Answer<Action>() {
			@Override
			public Action answer(InvocationOnMock invocation) throws Throwable {
				int vI = (int)(Math.random()*100)%2;
				return new Movement(vI, vI);
			}
		});
		
		mSUT.startManagement();
		
		assertThat(mSUT.isAlive()).isTrue();
		await().atMost(2, SECONDS).untilAsserted(()->verify(mNetworkCommunicationMock, atLeast(1)).sendDatagramm(new Movement(0,0)));
		await().atMost(2, SECONDS).untilAsserted(()->verify(mNetworkCommunicationMock, atLeast(1)).sendDatagramm(new Movement(1,1)));
	}

	@Test
	public void testSendNullMessagesWithAI() throws Exception {
		when(mCoreMock.getServerConnection()).thenReturn( mNetworkCommunicationMock );
		when(mCoreMock.getAI()).thenReturn( mArtificialIntelligenceMock );
		when(mArtificialIntelligenceMock.getAction()).thenAnswer(new Answer<Action>() {
			@Override
			public Action answer(InvocationOnMock invocation) throws Throwable {
				int vI = (int)(Math.random()*100)%2;
				return Math.random()>0.5?null:new Movement(vI, vI);
			}
		});
		
		mSUT.startManagement();
		
		assertThat(mSUT.isAlive()).isTrue();
		await().atMost(2, SECONDS).untilAsserted(()->verify(mNetworkCommunicationMock, atLeast(1)).sendDatagramm(new Movement(0,0)));
		await().atMost(2, SECONDS).untilAsserted(()->verify(mNetworkCommunicationMock, atLeast(1)).sendDatagramm(new Movement(1,1)));
		
		verify(mNetworkCommunicationMock, never()).sendDatagramm((Action)null);
	}

	@Test
	public void testLoseServerConnectionWhileSendingMessages() throws Exception {
		when(mCoreMock.getServerConnection()).thenReturn( mNetworkCommunicationMock );
		when(mCoreMock.getAI()).thenReturn( mArtificialIntelligenceMock );
		when(mArtificialIntelligenceMock.getAction()).thenAnswer(new Answer<Action>() {
			@Override
			public Action answer(InvocationOnMock invocation) throws Throwable {
				int vI = (int)(Math.random()*100)%2;
				return new Movement(vI, vI);
			}
		});
		
		mSUT.startManagement();
		
		assertThat(mSUT.isAlive()).isTrue();
		when(mCoreMock.getServerConnection()).thenReturn( null );
		assertThat(mSUT.isAlive()).isTrue();
		await().atMost(2, SECONDS).untilAsserted(()->verify(mLoggerMock, atLeast(1)).debug( "NetworkCommunication cannot be NULL when running ToServerManagement." ));
	}

}
