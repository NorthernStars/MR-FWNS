package core;

import static org.mockito.Mockito.*;
import static org.awaitility.Awaitility.*;
import static org.awaitility.Duration.*;
import static java.util.concurrent.TimeUnit.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

@RunWith(PowerMockRunner.class)
@PrepareForTest({Core.class})
@PowerMockIgnore({"javax.management.*"})
public class ReloadAiManagementTest {

	Core mCoreMock = mock(Core.class);
	Logger mLoggerMock = mock(Logger.class);
	
	ArtificialIntelligence mArtificialIntelligenceMock = mock(ArtificialIntelligence.class);
	
	ReloadAiManagement mSUT;
	
	@Before
	public void setUp() throws Exception {
		PowerMockito.mockStatic(Core.class);
		when(Core.getInstance()).thenReturn(mCoreMock);
		when(Core.getLogger()).thenReturn(mLoggerMock);
		
		mSUT = new ReloadAiManagement();
	}

	@After
	public void tearDown() throws Exception {
		if(mSUT != null){
			mSUT.stopManagement();
		}
	}

	@Test
	public void testStartManagementWhileNotAlive() {
		when(mCoreMock.getAI()).thenReturn( null );
		assertThat(mSUT.isAlive()).isFalse();
		
		mSUT.startManagement();
		
		assertThat(mSUT.isAlive()).isTrue();
		verify(mLoggerMock).info("RestartAiServerManagement started.");
			
	}

	@Test
	public void testStartManagementWhileAlive() {
		when(mCoreMock.getAI()).thenReturn( null );
		
		mSUT.startManagement();
		assertThat(mSUT.isAlive()).isTrue();
		
		mSUT.startManagement();
		assertThat(mSUT.isAlive()).isTrue();
		verify(mLoggerMock, times(1)).info("RestartAiServerManagement started.");
			
	}

	@Test
	public void testStartWhileNotAlive() {
		when(mCoreMock.getAI()).thenReturn( null );
		assertThat(mSUT.isAlive()).isFalse();
		
		mSUT.start();
		
		assertThat(mSUT.isAlive()).isTrue();
		verify(mLoggerMock).info("RestartAiServerManagement started.");
			
	}

	@Test
	public void testStartWhileAlive() {
		when(mCoreMock.getAI()).thenReturn( null );
		
		mSUT.start();
		assertThat(mSUT.isAlive()).isTrue();
		
		mSUT.start();
		assertThat(mSUT.isAlive()).isTrue();
		verify(mLoggerMock, times(1)).info("RestartAiServerManagement started.");
			
	}

	@Test
	public void testNameOfThread() {
		assertThat(mSUT.getName()).isEqualToIgnoringCase("RestartAiManagement");
	}

	@Test
	public void testGetInstance() {
		ReloadAiManagement vSaveToCompare = ReloadAiManagement.getInstance();
		
		assertThat(vSaveToCompare).isInstanceOf(ReloadAiManagement.class);
		assertThat(vSaveToCompare).isEqualTo(ReloadAiManagement.getInstance());
	}

}
