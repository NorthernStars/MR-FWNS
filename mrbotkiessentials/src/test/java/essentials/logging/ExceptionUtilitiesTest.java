package essentials.logging;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ExceptionUtilitiesTest
{
    @Before
    public void setUp() throws Exception
    {
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void testGetStackTraceAsString()
    {
        Exception mockException = mock(Exception.class);
        when(mockException.toString()).thenReturn("Mocked Exception");
        when(mockException.getStackTrace()).thenReturn(new StackTraceElement[]{
                new StackTraceElement("String","toString()","testfile",123),
                new StackTraceElement("Testclass","testMethod()","testfile2",42)
        });

        String expectedResult = "\tMocked Exception\n" +
                "\t\tString.toString()(testfile:123)\n" +
                "\t\tTestclass.testMethod()(testfile2:42)\n";

        assertThat(ExceptionUtilities.getStackTraceAsString(mockException)).isEqualTo(expectedResult);
    }

}