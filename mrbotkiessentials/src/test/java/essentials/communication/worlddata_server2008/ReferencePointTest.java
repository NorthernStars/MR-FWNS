package essentials.communication.worlddata_server2008;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
public class ReferencePointTest
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
    public void testSetDistanceToPointWithSetValues()
    {
        ReferencePoint referencePoint = new ReferencePoint(100,85.6,true);

    }

}