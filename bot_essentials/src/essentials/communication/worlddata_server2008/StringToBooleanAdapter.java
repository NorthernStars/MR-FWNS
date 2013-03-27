package essentials.communication.worlddata_server2008;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class StringToBooleanAdapter extends XmlAdapter<String, Boolean> {
    
    @Override
    public Boolean unmarshal( String aStatus ) throws Exception {
        return aStatus.equals( "found" );
    }

    @Override
    public String marshal( Boolean aStatus ) throws Exception {
        if( aStatus ) {
            return "found";
        }
        return "not found";
    }
    
}
