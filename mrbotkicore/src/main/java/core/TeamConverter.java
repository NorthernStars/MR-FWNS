package core;

import essentials.core.BotInformation;
import picocli.CommandLine;

import static essentials.core.BotInformation.Teams.*;

public class TeamConverter implements CommandLine.ITypeConverter<BotInformation.Teams>
{

    @Override
    public BotInformation.Teams convert(String value) throws Exception
    {
        if (value == "gelb" || value == "yellow" || value == "g" || value == "y")
            return Yellow;
        else if (value == "blau" || value == "blue" || value == "b")
            return Blue;
        else
            return NotSpecified;
    }


}
