package core;

import essentials.core.BotInformation;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TeamConverterTest
{
    TeamConverter teamConverter = new TeamConverter();

    @Test
    public void testConvertWithValueIsGelb() throws Exception
    {
        TeamConverter teamConverter = new TeamConverter();
        BotInformation.Teams result = teamConverter.convert("gelb");
        assertThat(result).isEqualTo(BotInformation.Teams.Yellow);
    }

    @Test
    public void testConvertWithValueIsYellow() throws Exception
    {
        TeamConverter teamConverter = new TeamConverter();
        BotInformation.Teams result = teamConverter.convert("yellow");
        assertThat(result).isEqualTo(BotInformation.Teams.Yellow);
    }

    @Test
    public void testConvertWithValueIsG() throws Exception
    {
        TeamConverter teamConverter = new TeamConverter();
        BotInformation.Teams result = teamConverter.convert("g");
        assertThat(result).isEqualTo(BotInformation.Teams.Yellow);
    }

    @Test
    public void testConvertWithValueIsY() throws Exception
    {
        TeamConverter teamConverter = new TeamConverter();
        BotInformation.Teams result = teamConverter.convert("y");
        assertThat(result).isEqualTo(BotInformation.Teams.Yellow);
    }

    @Test
    public void testConvertWithValueIsBlau() throws Exception
    {
        TeamConverter teamConverter = new TeamConverter();
        BotInformation.Teams result = teamConverter.convert("blau");
        assertThat(result).isEqualTo(BotInformation.Teams.Blue);
    }

    @Test
    public void testConvertWithValueIsBlue() throws Exception
    {
        TeamConverter teamConverter = new TeamConverter();
        BotInformation.Teams result = teamConverter.convert("blue");
        assertThat(result).isEqualTo(BotInformation.Teams.Blue);
    }

    @Test
    public void testConvertWithValueIsB() throws Exception
    {
        TeamConverter teamConverter = new TeamConverter();
        BotInformation.Teams result = teamConverter.convert("b");
        assertThat(result).isEqualTo(BotInformation.Teams.Blue);
    }
}