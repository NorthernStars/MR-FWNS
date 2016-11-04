package essentials.communication;

import javax.xml.bind.annotation.XmlRegistry;

import essentials.communication.worlddata_server2008.RawWorldData;

@XmlRegistry
public class ObjectFactory {

    public RawWorldData createRawWorldData() {
        return new RawWorldData();
    }
    
}
