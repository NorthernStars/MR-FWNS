package essentials.communication.action_server2008;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class MovementTest
{
    Movement mockMovement;

    @Before
    public void setUp() throws Exception
    {
        mockMovement = mock(Movement.class);
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

        //TODO Mock? getClass Problem

        Movement movement1 = new Movement(51,50);
        Movement movement2 = new Movement(51,49);

        assertThat(movement1.equals(movement2)).isEqualTo(false);
    }

    @Test
    public void testEqualsWithDifferentRightWheelVelocity()
    {
        Movement movement1 = new Movement(51,50);
        Movement movement2 = new Movement(49,50);

        assertThat(movement1.equals(movement2)).isEqualTo(false);
    }

    @Test
    public void testEqualsWithSameValues()
    {
        Movement movement1 = new Movement(50,50);
        Movement movement2 = new Movement(50,50);

        assertThat(movement1.equals(movement2)).isEqualTo(true);
    }
}