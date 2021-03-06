package core;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

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
	
	@Test
	public void testStopManagementWhenNotAlive() {
		when(mCoreMock.getAI()).thenReturn( null );
		
		assertThat(mSUT.isAlive()).isFalse();
		mSUT.stopManagement();
		assertThat(mSUT.isAlive()).isFalse();
		verify(mLoggerMock, never()).info("RestartAiServerManagement stopped.");
		
	}
	
	@Test
	public void testStopManagementWhenAlive() {
		when(mCoreMock.getAI()).thenReturn( null );
		
		mSUT.startManagement();
		assertThat(mSUT.isAlive()).isTrue();
		
		mSUT.stopManagement();
		assertThat(mSUT.isAlive()).isFalse();
		verify(mLoggerMock).info("RestartAiServerManagement stopped.");
		
	}
	
	@Test
	public void testCloseWhenNotAlive() {
		ReloadAiManagement vSaveToCompare = ReloadAiManagement.getInstance();
				
		assertThat(vSaveToCompare.isAlive()).isFalse();
		vSaveToCompare.close();
		assertThat(vSaveToCompare.isAlive()).isFalse();
		verify(mLoggerMock).info("RestartAiServerManagement closed.");
		
		assertThat(vSaveToCompare).isNotEqualTo(ReloadAiManagement.getInstance());
		
	}
	
	@Test
	public void testCloseWhenAlive() {
		when(mCoreMock.getAI()).thenReturn( null );

		ReloadAiManagement vSaveToCompare = ReloadAiManagement.getInstance();
		
		vSaveToCompare.startManagement();
		assertThat(vSaveToCompare.isAlive()).isTrue();
		
		vSaveToCompare.close();
		assertThat(vSaveToCompare.isAlive()).isFalse();
		verify(mLoggerMock).info("RestartAiServerManagement closed.");
		
		assertThat(vSaveToCompare).isNotEqualTo(ReloadAiManagement.getInstance());
		
	}
	
	@Test
	public void testCloseWhenNull() {

		ReloadAiManagement vSaveToCompare = ReloadAiManagement.getInstance();
		
		ReloadAiManagement.setInstanceNull();
		
		vSaveToCompare.close();
		assertThat(vSaveToCompare.isAlive()).isFalse();
		
		assertThat(vSaveToCompare).isNotEqualTo(ReloadAiManagement.getInstance());
		
	}

	@Test
	public void testRunWithoutStart() throws Exception {
		when(mCoreMock.getAI()).thenReturn( mArtificialIntelligenceMock );
		
		assertThat(mSUT.isAlive()).isFalse();
		mSUT.run();
		assertThat(mSUT.isAlive()).isFalse();

		verifyZeroInteractions(mArtificialIntelligenceMock);
	}

	@Test
	public void testRunWithoutAI() throws Exception {
		when(mCoreMock.getAI()).thenReturn( null );
		
		mSUT.startManagement();
		assertThat(mSUT.isAlive()).isTrue();
	}

	@Test
	public void testRunWithAIAndNoNeedToRestart() throws Exception {
		when(mCoreMock.getAI()).thenReturn( mArtificialIntelligenceMock );
		when(mArtificialIntelligenceMock.wantRestart()).thenReturn(false);
		
		mSUT.startManagement();
		assertThat(mSUT.isAlive()).isTrue();

		await().atMost(2, SECONDS).untilAsserted(()->verify(mArtificialIntelligenceMock, atLeast(2)).wantRestart());

		verify(mCoreMock, never()).initializeAI();
		verify(mCoreMock, never()).resumeAI();
		
	}

	@Test
	public void testRunWithAIAndNeedToRestart() throws Exception {
		when(mCoreMock.getAI()).thenReturn( mArtificialIntelligenceMock );
		when(mArtificialIntelligenceMock.wantRestart()).thenReturn(true).thenReturn(false);
		
		mSUT.startManagement();
		assertThat(mSUT.isAlive()).isTrue();

		await().atMost(2, SECONDS).untilAsserted(()->verify(mArtificialIntelligenceMock, atLeast(2)).wantRestart());
		
		InOrder inOrder = inOrder(mCoreMock);
		inOrder.verify(mCoreMock).initializeAI();
		inOrder.verify(mCoreMock).resumeAI();
		
	}
	
}
