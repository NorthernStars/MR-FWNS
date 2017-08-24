package essentials.communication.action_server2008;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class KickTest
{
    @Test
    public void getXMLString() throws Exception
    {
        Kick kick = new Kick(90.1, (float) 21.0);
        assertThat(kick.getXMLString()).isEqualTo("<command> <kick> <angle>90.1</angle> <force>21.0</force> </kick> </command>");

    }

}