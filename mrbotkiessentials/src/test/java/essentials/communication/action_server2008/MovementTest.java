package essentials.communication.action_server2008;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.MockGateway;

import static org.assertj.core.api.Assertions.assertThat;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;


public class MovementTest
{
    Movement mockMovement;

    @Before
    public void setUp() throws Exception
    {
        MockGateway.MOCK_GET_CLASS_METHOD = true;
        mockMovement = PowerMockito.mock(Movement.class);
    }

    @After
    public void tearDown() throws Exception
    {
    }

    /*
    Tests for getXMLString()
     */
    @Test
    public void testGetXMLString()
    {
        Movement movement = new Movement(51,50);
        assertThat(movement.getXMLString()).isEqualTo("<command> <wheel_velocities> <right>51</right> <left>50</left> </wheel_velocities> </command>");
    }

    /*
    Tests for hashCode
     */
    @Test
    public void testHashCode()
    {
        Movement movement = new Movement(51,50);
        assertThat(movement.hashCode()).isEqualTo(2562);
    }

    /*
    Tests for equals
     */
    @Test
    public void testEqualsWithSameObject()
    {
        Movement movement = new Movement(51,50);
        assertThat(movement.equals(movement)).isEqualTo(true);
    }

    @Test
    public void testEqualsWithObjectIsNull()
    {
        Movement movement = new Movement(51,50);
        assertThat(movement.equals(null)).isEqualTo(false);
    }

    @Test
    public void testEqualsWithDifferentClass()
    {
        Movement movement = new Movement(51,50);
        assertThat(movement.equals(new Integer(12))).isEqualTo(false);
    }

    @Test
    public void testEqualsWithDifferentLeftWheelVelocity()
    {

        /*PowerMockito.when(mockMovement.getmLeftWheelVelocity()).thenReturn(42);

        System.out.println(mockMovement.getClass());

        Movement movement = new Movement(51,50);
        assertThat(movement.equals(mockMovement)).isEqualTo(false);*/
    }

    @Test
    public void testEqualsWithDifferentRightWheelVelocity()
    {
        /*when(mockMovement.getmRightWheelVelocity()).thenReturn(42);
        when(mockMovement.getmLeftWheelVelocity()).thenReturn(50);

        Movement movement = new Movement(51,50);
        assertThat(movement.equals(mockMovement)).isEqualTo(false);*/
    }
}