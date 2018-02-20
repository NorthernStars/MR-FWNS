package essentials.communication;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Arrays;

@XmlRootElement(name="Position")
public class Position implements WorldData {

    @XmlElement(name="position")
	int[][] posi = {{-1, 0,-1},
					{ 0, 1, 0},
					{-1, 0,-1}};
	@XmlElement(name="mXPositionInTile")
	double mXPositionInTile;
	@XmlElement(name="mYPositionInTile")
	double mYPositionInTile;

	@XmlElement(name="mOrientation")
	double mOrientation;
}
