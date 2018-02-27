package core;

import essentials.core.BotInformation;
import org.apache.commons.cli.CommandLine;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class CommandLineOptionsTest
{

    @InjectMocks
    @Spy
    private picocli.CommandLine commandLine = new picocli.CommandLine(new CommandLineOptions());


    @Before
    public void setUp() throws Exception
    {
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void testParseCommandLineArgumentsWithNoExceptionsWithRemoteStart()
    {
        String[] args = {"-bn","3","-tn","Northern Stars","-t","blau","-ids","13","-s","localhost:3310","-aiarc","../mrbotkiexample/bin/exampleai/brain","-aicl","exampleai.brain.Striker","-aiarg","0","-rs"};
        boolean result = CommandLineOptions.parseCommandLineArguments(args);
        assertThat(result).isTrue();
    }

    @Test
    public void testParseCommandLineArgumentsWithNoExceptionsWithoutRemoteStart()
    {
        String[] args = {"-bn","3","-tn","Northern Stars","-t","blau","-ids","13","-s","localhost:3310","-aiarc","../mrbotkiexample/bin/exampleai/brain","-aicl","exampleai.brain.Striker","-aiarg","0"};
        boolean result = CommandLineOptions.parseCommandLineArguments(args);
        assertThat(result).isFalse();
    }

    @Test
    public void testParseCommandLineArgumentsWithNoExceptionsWithMissingArgument()
    {
        String[] args = {"-bn","3","-tn","Northern Stars","-t","blau","-ids","13","-s","localhost:3310","-aiarc","../mrbotkiexample/bin/exampleai/brain","-aicl","exampleai.brain.Striker","-aiarg","0"};
        boolean result = CommandLineOptions.parseCommandLineArguments(args);
        assertThat(result).isFalse();
    }

    @Test
    public void testSetOptions()
    {
        String[] args = {"-bn","3","-tn","Northern Stars","-t","blau","-ids","13","-s","localhost:3310","-aiarc","../mrbotkiexample/bin/exampleai/brain","-aicl","exampleai.brain.Striker","-aiarg","0"};
        CommandLineOptions.parseCommandLineArguments(args);
        BotInformation botInformation = Core.getInstance().getBotinformation();

        assertThat(botInformation.getAIArchive()).isEqualTo("../mrbotkiexample/bin/exampleai/brain");
        assertThat(botInformation.getAIArgs()).isEqualTo("[0]");
        assertThat(botInformation.getAIClassname()).isEqualTo("exampleai.brain.Striker");
        try
        {
            assertThat(botInformation.getServerIP()).isEqualTo(InetAddress.getByName("localhost"));
        }
        catch (UnknownHostException e)
        {
            e.printStackTrace();
        }
        assertThat(botInformation.getServerPort()).isEqualTo(3310);
        assertThat(botInformation.getRcId()).isEqualTo(13);
        assertThat(botInformation.getVtId()).isEqualTo(13);
        assertThat(botInformation.getTeam()).isEqualTo(BotInformation.Teams.Blue);
        assertThat(botInformation.getTeamname()).isEqualTo("Northern Stars");
        assertThat(botInformation.getBotname()).isEqualTo("3");
    }

    @Test
    public void testSetOptionsWithVtAndRcId()
    {
        String[] args = {"-bn","3","-tn","Northern Stars","-t","blau","-ids","13:42","-s","localhost:3310","-aiarc","../mrbotkiexample/bin/exampleai/brain","-aicl","exampleai.brain.Striker","-aiarg","0"};
        CommandLineOptions.parseCommandLineArguments(args);
        BotInformation botInformation = Core.getInstance().getBotinformation();

        assertThat(botInformation.getAIArchive()).isEqualTo("../mrbotkiexample/bin/exampleai/brain");
        assertThat(botInformation.getAIArgs()).isEqualTo("[0]");
        assertThat(botInformation.getAIClassname()).isEqualTo("exampleai.brain.Striker");
        try
        {
            assertThat(botInformation.getServerIP()).isEqualTo(InetAddress.getByName("localhost"));
        }
        catch (UnknownHostException e)
        {
            e.printStackTrace();
        }
        assertThat(botInformation.getServerPort()).isEqualTo(3310);
        assertThat(botInformation.getRcId()).isEqualTo(13);
        assertThat(botInformation.getVtId()).isEqualTo(42);
        assertThat(botInformation.getTeam()).isEqualTo(BotInformation.Teams.Blue);
        assertThat(botInformation.getTeamname()).isEqualTo("Northern Stars");
        assertThat(botInformation.getBotname()).isEqualTo("3");
    }


    //@Test
    //public void testParseCommandLineArgumentsGeneralException()
    //{
    //    String[] args = {"-bn","3","-tn","Northern Stars","-t","blau","-ids","13","-s","localhost:3310","-aiarc","../mrbotkiexample/bin/exampleai/brain","-aicl","exampleai.brain.Striker","-aiarg","0","-rs"};
    //    Mockito.doThrow(new Exception()).when(commandLine).registerConverter(BotInformation.Teams.class, new TeamConverter());
    //    CommandLineOptions.parseCommandLineArguments(args);
    //}

}