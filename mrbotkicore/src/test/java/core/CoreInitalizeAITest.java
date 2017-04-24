package core;

import static org.mockito.Mockito.*;
import static org.awaitility.Awaitility.*;
import static org.awaitility.Duration.*;
import static java.util.concurrent.TimeUnit.*;
import static org.assertj.core.api.Assertions.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import remotecontrol.RemoteControlServer;
import essentials.core.ArtificialIntelligence;
import essentials.core.BotInformation;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ReloadAiManagement.class, RemoteControlServer.class, FromServerManagement.class, ToServerManagement.class, CommandLineOptions.class})
@PowerMockIgnore({"javax.management.*", "essentials.core.*", "core.TestAi"})
public class CoreInitalizeAITest {
	
	Logger mLoggerMock = mock(Logger.class);
	ReloadAiManagement mReloadAiManagementMock = mock(ReloadAiManagement.class);
	RemoteControlServer mRemoteControlServerMock = mock(RemoteControlServer.class);
	FromServerManagement mFromServerManagementMock = mock(FromServerManagement.class);
	ToServerManagement mToServerManagementMock = mock(ToServerManagement.class);
	
	BotInformation mBotInformationMock = new BotInformation(); //(BotInformation.class);

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
	public void testInitalizeAIWithJarAndClass() {

		mBotInformationMock.setAIArchive("src/test/java/core/test.jar");
		mBotInformationMock.setAIClassname("exampleai.brain.Striker");
		
		assertThat(mSUT.initializeAI()).isTrue();

		InOrder inOrder = inOrder(mSUT);
		inOrder.verify(mSUT).suspendServermanagements();
		inOrder.verify(mSUT).resumeServermanagements();

		assertThat(mSUT.getAI()).isInstanceOf(ArtificialIntelligence.class);
		
		verify(mLoggerMock).info("Loaded AI " + mBotInformationMock.getAIClassname() + " from " + mBotInformationMock.getAIArchive());
	}

	@Test
	public void testInitalizeAIWithJarAndClassWithOtherAiThatIsNotRunning() {

		mBotInformationMock.setAIArchive("src/test/java/core/test.jar");
		mBotInformationMock.setAIClassname("exampleai.brain.Striker");
		
		TestAi vTestAi = new TestAi();
		mSUT.setAI(vTestAi);
		
		assertThat(mSUT.initializeAI()).isTrue();

		InOrder inOrder = inOrder(mSUT);
		inOrder.verify(mSUT, never()).disposeAI();
		inOrder.verify(mSUT).suspendServermanagements();
		inOrder.verify(mSUT).resumeServermanagements();
		
		verify(mSUT, never()).disposeAI();

		assertThat(mSUT.getAI()).isInstanceOf(ArtificialIntelligence.class);
		assertThat(mSUT.getAI()).isNotEqualTo(vTestAi);
		
		verify(mLoggerMock).info("Loaded AI " + mBotInformationMock.getAIClassname() + " from " + mBotInformationMock.getAIArchive());
	}

	@Test
	public void testInitalizeAIWithJarAndClassWithOtherAiThatIsRunning() {

		mBotInformationMock.setAIArchive("src/test/java/core/test.jar");
		mBotInformationMock.setAIClassname("exampleai.brain.Striker");
		
		TestAi vTestAi = new TestAi();
		vTestAi.setRunning(true);
		mSUT.setAI(vTestAi);
		
		assertThat(mSUT.initializeAI()).isTrue();

		InOrder inOrder = inOrder(mSUT);
		inOrder.verify(mSUT).disposeAI();
		inOrder.verify(mSUT).suspendServermanagements();
		inOrder.verify(mSUT).resumeServermanagements();

		assertThat(mSUT.getAI()).isInstanceOf(ArtificialIntelligence.class);
		assertThat(mSUT.getAI()).isNotEqualTo(vTestAi);
		
		verify(mLoggerMock).info("Loaded AI " + mBotInformationMock.getAIClassname() + " from " + mBotInformationMock.getAIArchive());
	}

	@Test
	public void testInitalizeAI() {

		RuntimeException vRuntimeExceptionTest = new RuntimeException("TestShit");
		doThrow(vRuntimeExceptionTest).when(mSUT).suspendServermanagements();
		assertThat(mSUT.initializeAI()).isFalse();

		assertThat(mSUT.getAI()).isNull();

		verify(mLoggerMock, never()).info("Loaded AI " + mBotInformationMock.getAIClassname() + " from " + mBotInformationMock.getAIArchive());
		verify(mLoggerMock).error( "Error loading AI " + mBotInformationMock.getAIClassname() + " from " + mBotInformationMock.getAIArchive() + " " + vRuntimeExceptionTest.getLocalizedMessage() );
		verify(mLoggerMock).catching( Level.ERROR, vRuntimeExceptionTest );
	}
	
}
