package essentials.core;

import org.assertj.core.data.Percentage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.withinPercentage;

import static org.junit.Assert.*;

@RunWith(PowerMockRunner.class)
public class BotInformationTest
{
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
}