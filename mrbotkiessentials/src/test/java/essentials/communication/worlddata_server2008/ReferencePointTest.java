package essentials.communication.worlddata_server2008;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.withinPercentage;
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
        ReferencePoint referencePoint = new ReferencePoint(100.0,85.6,true);
        referencePoint.setDistanceToPoint(65.3);
        assertThat(referencePoint.getDistanceToPoint()).isCloseTo(65.3,withinPercentage(1));
    }

    @Test
    public void testSetDistanceToPointWithSetAngle()
    {
        ReferencePoint referencePoint = new ReferencePoint();
        referencePoint.setAngleToPoint(55.3);
        referencePoint.setDistanceToPoint(100.0);
        assertThat(referencePoint.getDistanceToPoint()).isCloseTo(100.0,withinPercentage(1));
    }

    @Test
    public void testSetDistanceToPointWithNothingSet()
    {
        ReferencePoint referencePoint = new ReferencePoint();
        referencePoint.setDistanceToPoint(100.0);
        assertThat(referencePoint.getDistanceToPoint()).isCloseTo(100.0,withinPercentage(1));
    }

}