package essentials.core;

import org.assertj.core.data.Percentage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.withinPercentage;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
public class BotInformationTest
{
    InetAddress mockInetAdress = mock(InetAddress.class);

    @Before
    public void setUp() throws Exception
    {
    }

    @After
    public void tearDown() throws Exception
    {
    }

    /*
    Tests Teams.getTeamsAsStringArray
     */
    @Test
    public void testTeamsGetTeamsAsStringArray()
    {
        String[] teams = {"Yellow","Blue","NotSpecified"};
        assertThat(BotInformation.Teams.getTeamsAsStringArray()).isEqualTo(teams);
    }

    /*
    Tests GamevalueNames.getAllGamevalueNamesAsAString
     */
    @Test
    public void testGetAllGamevalueNamesAsAString()
    {
        String gvString = new String();
        for ( BotInformation.GamevalueNames vGamevalueName : BotInformation.GamevalueNames.values() )
        {
            gvString += vGamevalueName.toString() + " ";
        }
        assertThat(BotInformation.GamevalueNames.getAllGamevalueNamesAsAString()).isEqualTo(gvString);
    }

    /*
    Tests GamevalueNames.getGamevalueNamesAsStringArray
     */
    @Test
    public void testGetGamevalueNamesAsStringArray()
    {
        int i = 0;
        String[] gvStrings= new String[BotInformation.GamevalueNames.values().length];
        for ( BotInformation.GamevalueNames vGamevalueName : BotInformation.GamevalueNames.values() )
        {
            gvStrings[i] = vGamevalueName.toString();
            i++;
        }
        assertThat(BotInformation.GamevalueNames.getGamevalueNamesAsStringArray()).isEqualTo(gvStrings);
    }

    /*
    Tests BotInformation.BotInformation
     */
    @Test
    public void testBotInformationWithoutErrors()
    {
        BotInformation botInformation = new BotInformation();
        assertThat(botInformation).isInstanceOf(BotInformation.class);
        assertThat(botInformation.getBotname()).isEqualTo("DefaultBot");
        assertThat(botInformation.getTeam()).isEqualTo(BotInformation.Teams.NotSpecified);
        assertThat(botInformation.getTeamname()).isEqualTo("");
        try
        {
            assertThat(botInformation.getBotIP()).isEqualTo(InetAddress.getLocalHost());
            assertThat(botInformation.getServerIP()).isEqualTo(InetAddress.getLocalHost());
        }
        catch (UnknownHostException e)
        {
            e.printStackTrace();
        }
        assertThat(botInformation.getBotPort()).isEqualTo(-1);
        assertThat(botInformation.getAIArchive()).isEqualTo("");
        assertThat(botInformation.getAIClassname()).isEqualTo("");
        assertThat(botInformation.getAIArgs()).isEqualTo("");
        assertThat(botInformation.getBotMemory()).isNull();
    }

    @Test
    public void testBotInformationWithErrors()
    {
        /*
        TODO: Somehow make InetAdress throw an Exception
         */
        fail("Not implemented yet");
    }
}