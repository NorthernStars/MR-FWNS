package essentials.communication.worlddata_server2008;

import org.assertj.core.data.Percentage;
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

    /*
    Tests setDistanceToPoint()
     */
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

    /*
    Tests setAngleToPoint()
     */
    @Test
    public void testSetAngleToPointWithSetValues()
    {
        ReferencePoint referencePoint = new ReferencePoint(100.0,85.6,true);
        referencePoint.setAngleToPoint(65.3);
        assertThat(referencePoint.getAngleToPoint()).isCloseTo(65.3,withinPercentage(1));
    }

    @Test
    public void testSetAngleToPointWithSetDistance()
    {
        ReferencePoint referencePoint = new ReferencePoint();
        referencePoint.setDistanceToPoint(55.3);
        referencePoint.setAngleToPoint(100.0);
        assertThat(referencePoint.getAngleToPoint()).isCloseTo(100.0,withinPercentage(1));
    }

    @Test
    public void testSetAngleToPointWithNothingSet()
    {
        ReferencePoint referencePoint = new ReferencePoint();
        referencePoint.setAngleToPoint(100.0);
        assertThat(referencePoint.getAngleToPoint()).isCloseTo(100.0,withinPercentage(1));
    }

    /*
    Tests set
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSetWithPolarcoordinatesWithNegativeDistance()
    {
        ReferencePoint referencePoint = new ReferencePoint();
        referencePoint.set(-5,25,true);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetWithPolarcoordinatesWithToSmallAngle()
    {
        ReferencePoint referencePoint = new ReferencePoint();
        referencePoint.set(13,-190,true);
    }

    @Test
    public void testSetWithPolarCoordinates()
    {
        ReferencePoint referencePoint = new ReferencePoint();
        referencePoint.set(50,20,true);
        assertThat(referencePoint.getAngleToPoint()).isCloseTo(20, Percentage.withPercentage(1));
        assertThat(referencePoint.getDistanceToPoint()).isCloseTo(50,Percentage.withPercentage(1));
    }

    @Test
    public void testSetWithCartesianCoordinates()
    {
        ReferencePoint referencePoint = new ReferencePoint();
        referencePoint.set(6,7,false);
        assertThat(referencePoint.getAngleToPoint()).isCloseTo(49.398, Percentage.withPercentage(2));
        assertThat(referencePoint.getDistanceToPoint()).isCloseTo(9.22, Percentage.withPercentage(1));
    }



}