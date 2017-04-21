package core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.management.ManagementFactory;

import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Core.class})
@PowerMockIgnore({"javax.management.*"})
public class MainTest {

	Core mCoreMock = mock(Core.class);
	Logger mLoggerMock = mock(Logger.class);
	
	
	@Before
	public void setUp() throws Exception {
		PowerMockito.mockStatic(Core.class);
		when(Core.getInstance()).thenReturn(mCoreMock);
		when(Core.getLogger()).thenReturn(mLoggerMock);
	}

	@Test
	public void mainWithEmptyCommandline() {
		String[] vEmptyCommandline = new String[0];
		doNothing().when(mCoreMock).startBot(vEmptyCommandline);
		
		Main.main(vEmptyCommandline);
		
		assertThat(System.getProperty("Bot")).isEqualToIgnoringCase(ManagementFactory.getRuntimeMXBean().getName());
		verify(mLoggerMock).info("Starting Bot(" + ManagementFactory.getRuntimeMXBean().getName() + ")" );
		verify(mLoggerMock).info("Parameters: ");
		
		verify(mCoreMock).startBot(vEmptyCommandline);
	}

	@Test
	public void mainWithACommandline() {
		String[] vCommandline = new String[5];
		vCommandline[0] = "Hello,";
		vCommandline[1] = "this";
		vCommandline[2] = "is";
		vCommandline[3] = "a";
		vCommandline[4] = "test.";
		doNothing().when(mCoreMock).startBot(vCommandline);
		
		Main.main(vCommandline);
		
		assertThat(System.getProperty("Bot")).isEqualToIgnoringCase(ManagementFactory.getRuntimeMXBean().getName());
		verify(mLoggerMock).info("Starting Bot(" + ManagementFactory.getRuntimeMXBean().getName() + ")" );
		verify(mLoggerMock).info("Parameters: Hello, this is a test. ");
		
		verify(mCoreMock).startBot(vCommandline);
	}

	@Test
	public void mainWithFatalError() {
		String[] vEmptyCommandline = new String[0];
		RuntimeException vTestException = new RuntimeException();
		doThrow(vTestException).when(mCoreMock).startBot(vEmptyCommandline);
		
		Main.main(vEmptyCommandline);
		
		verify(mCoreMock).startBot(vEmptyCommandline);
		verify(mLoggerMock).fatal( "Fatal error! Bot terminates. ", vTestException );
	}

}
