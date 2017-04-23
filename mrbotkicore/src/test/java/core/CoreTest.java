package core;

import static org.mockito.Mockito.*;
import static org.awaitility.Awaitility.*;
import static org.awaitility.Duration.*;
import static java.util.concurrent.TimeUnit.*;
import static org.assertj.core.api.Assertions.*;

import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import remotecontrol.RemoteControlServer;
import essentials.core.ArtificialIntelligence;
import essentials.core.BotInformation;
import fwns_network.server_2008.NetworkCommunication;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ReloadAiManagement.class, RemoteControlServer.class, FromServerManagement.class, ToServerManagement.class, CommandLineOptions.class})
@PowerMockIgnore({"javax.management.*"})
public class CoreTest {
		
	Logger mLoggerMock = mock(Logger.class);
	ReloadAiManagement mReloadAiManagementMock = mock(ReloadAiManagement.class);
	RemoteControlServer mRemoteControlServerMock = mock(RemoteControlServer.class);
	FromServerManagement mFromServerManagementMock = mock(FromServerManagement.class);
	ToServerManagement mToServerManagementMock = mock(ToServerManagement.class);
	
	BotInformation mBotInformationMock = mock(BotInformation.class);

	Core mSUT;
	
	@Before
	public void setUp() throws Exception {
		Core.setLogger(mLoggerMock);
		
		PowerMockito.mockStatic(ReloadAiManagement.class);
		when(ReloadAiManagement.getInstance()).thenReturn(mReloadAiManagementMock);
		
		PowerMockito.mockStatic(RemoteControlServer.class);
		when(RemoteControlServer.getInstance()).thenReturn(mRemoteControlServerMock);
		
		PowerMockito.mockStatic(FromServerManagement.class);
		when(FromServerManagement.getInstance()).thenReturn(mFromServerManagementMock);
		
		PowerMockito.mockStatic(ToServerManagement.class);
		when(ToServerManagement.getInstance()).thenReturn(mToServerManagementMock);
		
		mSUT = spy(new Core(mBotInformationMock));
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testConstructorWithBotInformation() {
		mSUT = new Core(mBotInformationMock);
		
		assertThat(mSUT.getBotinformation()).isEqualTo(mBotInformationMock);
	}

	@Test
	public void testGetInstance() {
		mSUT = Core.getInstance();
		
		assertThat(mSUT).isInstanceOf(Core.class);
		assertThat(mSUT).isEqualTo(Core.getInstance());
		assertThat(mSUT.getBotinformation()).isEqualTo(Core.getInstance().getBotinformation());
	}

	@Test
	public void testGetLogger() {
		Logger vLoggerToTest = Core.getLogger();
		
		assertThat(vLoggerToTest).isInstanceOf(Logger.class);
		assertThat(vLoggerToTest).isEqualTo(Core.getLogger());
	}

	@Test
	public void testCloseWithInstance() {
		Core.getInstance();
		when(mBotInformationMock.getBotname()).thenReturn("TestBot");
		when(mBotInformationMock.getRcId()).thenReturn(10);
		when(mBotInformationMock.getVtId()).thenReturn(11);
		
		mSUT.close();
		
		verify(mSUT).disposeAI();
		verify(mSUT).stopServermanagements();
		verify(mSUT).stopServerConnection();
		verify(mReloadAiManagementMock).close();
		verify(mRemoteControlServerMock).close();
		
		verify(mLoggerMock).info( mBotInformationMock.getBotname() + "(" + mBotInformationMock.getRcId() + "/" + mBotInformationMock.getVtId() + ") closed!" );
	}

	@Test
	public void testCloseWithoutInstance() {
		Core.setInstanceNull();
		when(mBotInformationMock.getBotname()).thenReturn("TestBot");
		when(mBotInformationMock.getRcId()).thenReturn(10);
		when(mBotInformationMock.getVtId()).thenReturn(11);
		
		mSUT.close();
		
		verify(mSUT, never()).disposeAI();
		verify(mSUT, never()).stopServermanagements();
		verify(mSUT, never()).stopServerConnection();
		verify(mReloadAiManagementMock, never()).close();
		verify(mRemoteControlServerMock, never()).close();
		
		verify(mLoggerMock, never()).info( mBotInformationMock.getBotname() + "(" + mBotInformationMock.getRcId() + "/" + mBotInformationMock.getVtId() + ") closed!" );
	}

	@Test
	public void testStartBotWithRemoteStart() {
		
		String[] vTestCommandline = new String[0];
		
		PowerMockito.mockStatic(CommandLineOptions.class);
		when(CommandLineOptions.parseCommandLineArguments(vTestCommandline)).thenReturn(true);
		
		mSUT.startBot(vTestCommandline);
		
		verify(mRemoteControlServerMock).startRemoteServer();
		
		verify(mSUT, never()).initializeAI();
		verify(mSUT, never()).startServerConnection( 3 );
		verify(mReloadAiManagementMock, never()).startManagement();
	}

	@Test
	public void testStartBotWithoutRemoteStartAndConnect() {
		doReturn(true).when(mSUT).initializeAI();
		doReturn(true).when(mSUT).startServerConnection( 3 );
		
		
		String[] vTestCommandline = new String[0];
		
		PowerMockito.mockStatic(CommandLineOptions.class);
		when(CommandLineOptions.parseCommandLineArguments(vTestCommandline)).thenReturn(false);
		
		mSUT.startBot(vTestCommandline);
		
		verify(mRemoteControlServerMock).startRemoteServer();
		
		verify(mSUT).initializeAI();
		verify(mSUT).startServerConnection( 3 );
		verify(mReloadAiManagementMock).startManagement();
	}

	@Test
	public void testStartBotWithoutRemoteStartAndNotConnectWithoutAI() {
		doReturn(true).when(mSUT).initializeAI();
		doReturn(false).when(mSUT).startServerConnection( 3 );
		
		String[] vTestCommandline = new String[0];
		
		PowerMockito.mockStatic(CommandLineOptions.class);
		when(CommandLineOptions.parseCommandLineArguments(vTestCommandline)).thenReturn(false);
		
		mSUT.startBot(vTestCommandline);
		
		verify(mRemoteControlServerMock).startRemoteServer();
		
		verify(mSUT).initializeAI();
		verify(mSUT).startServerConnection( 3 );
		verify(mReloadAiManagementMock).startManagement();
		
		assertThat(mSUT.getAI()).isNull();
	}

	@Test
	public void testStartBotWithoutRemoteStartAndNotConnectWithAI() {
		doReturn(true).when(mSUT).initializeAI();
		doReturn(false).when(mSUT).startServerConnection( 3 );
		
		ArtificialIntelligence vArtificialIntelligenceMock = mock(ArtificialIntelligence.class);
		mSUT.setAI(vArtificialIntelligenceMock);
		
		String[] vTestCommandline = new String[0];
		
		PowerMockito.mockStatic(CommandLineOptions.class);
		when(CommandLineOptions.parseCommandLineArguments(vTestCommandline)).thenReturn(false);
		
		mSUT.startBot(vTestCommandline);
		
		verify(mRemoteControlServerMock).startRemoteServer();

		verify(mSUT).initializeAI();
		verify(mSUT).suspendAI();
		verify(mSUT).startServerConnection( 3 );
		verify(mReloadAiManagementMock).startManagement();

		assertThat(mSUT.getAI()).isNotNull();
		assertThat(mSUT.getAI()).isEqualTo(vArtificialIntelligenceMock);
		
		verify(mLoggerMock).error( "Could not connect to Server. AI suspended" );
	}

	@Test
	public void testStartBotWithException() {
		RuntimeException vTestException = new RuntimeException();
		doThrow(vTestException).when(mSUT).startServerConnection( 3 );
		
		String[] vTestCommandline = new String[0];
		
		PowerMockito.mockStatic(CommandLineOptions.class);
		when(CommandLineOptions.parseCommandLineArguments(vTestCommandline)).thenReturn(false);
		
		mSUT.startBot(vTestCommandline);
		
		verify(mLoggerMock).error( "Fehler beim initialisiern der Grundfunktionen", vTestException );
	}

}
