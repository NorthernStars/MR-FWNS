package essentials.communication;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Arrays;

@XmlRootElement(name="Position")
public class Position implements WorldData {

    @XmlElement(name="position")
	public int[][] posi = {{-1, 0,-1},
					{ 0, 1, 0},
					{-1, 0,-1}};
	@XmlElement(name="xpositionintile")
	public double mXPositionInTile;
	@XmlElement(name="ypositionintile")
	public double mYPositionInTile;

	@XmlElement(name="orientation")
	public double mOrientation;


	@XmlElement(name="tilewidth")
	public double mTileWidth;
	@XmlElement(name="tileweight")
	public double mTileHeight;
}
