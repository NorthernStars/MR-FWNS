package essentials.communication;

import essentials.communication.worlddata_server2008.RawWorldData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class ObjectFactoryTest
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
    public void testCreateRawWorldData()
    {
        ObjectFactory objectFactory = new ObjectFactory();

        assertThat(objectFactory.createRawWorldData()).isInstanceOf(RawWorldData.class);
    }

}