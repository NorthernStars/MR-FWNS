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

    @Test(expected = IllegalArgumentException.class)
    public void testSetWithPolarcoordinatesWithToBigAngle()
    {
        ReferencePoint referencePoint = new ReferencePoint();
        referencePoint.set(13,190,true);
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

    @Test
    public void testSetReferencePoint()
    {
        ReferencePoint referencePoint = new ReferencePoint(50,20,false);
        referencePoint.set(new ReferencePoint(20,30,false));
        assertThat(referencePoint.getXOfPoint()).isCloseTo(20, Percentage.withPercentage(1));
        assertThat(referencePoint.getYOfPoint()).isCloseTo(30, Percentage.withPercentage(1));
    }

    /*
    Tests Sub
     */
    @Test
    public void testSubDouble()
    {
        ReferencePoint referencePoint = new ReferencePoint(50,20,false);
        referencePoint.sub(10);
        assertThat(referencePoint.getXOfPoint()).isCloseTo(40,Percentage.withPercentage(1));
        assertThat(referencePoint.getYOfPoint()).isCloseTo(10, Percentage.withPercentage(1));
    }

    @Test
    public void testSubReferencePoint()
    {
        //Todo: Mock?
        ReferencePoint referencePoint = new ReferencePoint(50,20,false);
        ReferencePoint minusReferencePoint = new ReferencePoint(10,10, false);
        referencePoint.sub(minusReferencePoint);
        assertThat(referencePoint.getXOfPoint()).isCloseTo(40,Percentage.withPercentage(1));
        assertThat(referencePoint.getYOfPoint()).isCloseTo(10, Percentage.withPercentage(1));
    }

    /*
    Tests Add
     */
    @Test
    public void testAdd()
    {
        //Todo: Mock?
        ReferencePoint referencePoint = new ReferencePoint(50,20,false);
        ReferencePoint plusReferencePoint = new ReferencePoint(10,10, false);
        referencePoint.add(plusReferencePoint);
        assertThat(referencePoint.getXOfPoint()).isCloseTo(60,Percentage.withPercentage(1));
        assertThat(referencePoint.getYOfPoint()).isCloseTo(30, Percentage.withPercentage(1));
    }

    /*
    Tests Multiply
     */
    @Test
    public void testMultiply()
    {
        ReferencePoint referencePoint = new ReferencePoint(50,20, false);
        referencePoint.multiply(2.0);
        assertThat(referencePoint.getXOfPoint()).isCloseTo(100, Percentage.withPercentage(1));
        assertThat(referencePoint.getYOfPoint()).isCloseTo(40,Percentage.withPercentage(1));
    }

    /*
    Tests EpsilonEquals
     */
    @Test
    public void testEpsilonEqualsReferencePointExactCorrect()
    {
        ReferencePoint referencePoint = new ReferencePoint(50,20,false);
        Boolean result = referencePoint.epsilonEquals(new ReferencePoint(50,20,false),0);
        assertThat(result).isEqualTo(true);
    }

    @Test
    public void testEpsilonEqualsReferencePointNotExactCorrect()
    {
        ReferencePoint referencePoint = new ReferencePoint(50,20,false);
        Boolean result = referencePoint.epsilonEquals(new ReferencePoint(51,19,false),2);
        assertThat(result).isEqualTo(true);
    }

    @Test
    public void testEpsilonEqualsReferencePointExactNotCorrect()
    {
        ReferencePoint referencePoint = new ReferencePoint(50,20,false);
        Boolean result = referencePoint.epsilonEquals(new ReferencePoint(100, 38, false), 0);
        assertThat(result).isEqualTo(false);
    }

    @Test
    public void testEpsilonEqualsReferencePointNotExactNotCorrect()
    {
        ReferencePoint referencePoint = new ReferencePoint(50,20, false);
        Boolean result = referencePoint.epsilonEquals(new ReferencePoint(100,38,false), 2);
        assertThat(result).isEqualTo(false);
    }

    @Test
    public void testEpsilonEqualsReferencePointWithReferencePointIsNull()
    {
        ReferencePoint referencePoint = new ReferencePoint(50,20,false);
        Boolean result = referencePoint.epsilonEquals(null,0);
        assertThat(result).isEqualTo(false);
    }

    @Test
    public void testEpsilonEqualsReferencePointYSmallerThanEpsilon()
    {
        ReferencePoint referencePoint = new ReferencePoint(50,20,false);
        Boolean result = referencePoint.epsilonEquals(new ReferencePoint(50,0,false),1);
        assertThat(result).isEqualTo(false);
    }

    @Test
    public void testEpsilonEqualsDoubleNotExactIsTrue()
    {
        ReferencePoint referencePoint = new ReferencePoint(50,20,false);
        Boolean result = referencePoint.epsilonEquals(51.0,19.0,2);
        assertThat(result).isEqualTo(true);
    }

    @Test
    public void testEpsilonEqualsDoubleNotExactIsFalse()
    {
        ReferencePoint referencePoint = new ReferencePoint(50,20,false);
        Boolean result = referencePoint.epsilonEquals(50.0,0.0,1);
        assertThat(result).isEqualTo(false);
    }

    @Test
    public void testEpsilonEqualsDoubleExactIsTrue()
    {
        ReferencePoint referencePoint = new ReferencePoint(50,20,false);
        Boolean result = referencePoint.epsilonEquals(50.0,20.0,0);
        assertThat(result).isEqualTo(true);
    }

    @Test
    public void testEpsilonEqualsDoubleExactIsFalse()
    {
        ReferencePoint referencePoint = new ReferencePoint(50,20,false);
        Boolean result = referencePoint.epsilonEquals(138.0,31.0,0);
        assertThat(result).isEqualTo(false);
    }

}