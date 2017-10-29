package core;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;
import static org.assertj.core.api.Assertions.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;

public class CommandLineOptionsTest
{
    CommandLine mockCommandLine = mock(CommandLine.class);

    @Before
    public void setUp() throws Exception
    {
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void testParseOptions()
    {
        CommandLineOptions commandLineOptions = new CommandLineOptions();
        try
        {
            CommandLine commandLine = commandLineOptions.parseOptions(new String[]{"-bn","3", "-tn",  "\"Northern Stars\""});

            assertThat(commandLine).isInstanceOf(CommandLine.class);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
    }

}