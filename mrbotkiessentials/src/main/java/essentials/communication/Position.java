package essentials.communication;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Position")
public class Position implements WorldData {

    @XmlElement(name="position")
	int[][] posi = {{-1, 0,-1},
					{ 0, 1, 0},
					{-1, 0,-1}};
    @XmlElement(name="mXPositionInTile")
	int mXPositionInTile;
    @XmlElement(name="mYPositionInTile")
	int mYPositionInTile;
    @XmlElement(name="tileWidth")
	int tileWidth;
    @XmlElement(name="tileHeight")
	int tileHeight;
}
