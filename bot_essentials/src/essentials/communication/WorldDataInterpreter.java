package essentials.communication;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class WorldDataInterpreter {
	
	private static Unmarshaller UNMARSHALLER = null;
	@SuppressWarnings("rawtypes")
	private static ConcurrentHashMap<Class, Marshaller> MARSHALLER = new ConcurrentHashMap<Class, Marshaller>();
	
	public static String marshall( WorldData aWorldData ) {
		
		StringWriter vWorldDataXMLStream = new StringWriter();
		
		try {
			
			if( !MARSHALLER.containsKey( aWorldData.getClass() ) ){
				
				JAXBContext vJAXBContext = JAXBContext.newInstance( aWorldData.getClass() );
				MARSHALLER.putIfAbsent( aWorldData.getClass(), vJAXBContext.createMarshaller() );
			
			}
			
			MARSHALLER.get( aWorldData.getClass() ).marshal( aWorldData, vWorldDataXMLStream );		
		} catch ( Exception vJAXBException ) {
				
			vJAXBException.printStackTrace();
				
		}
		
		return vWorldDataXMLStream.toString();

    }
	 
	    
	public static WorldData unmarshall( String aWorldDataInXml ) {
		
		StringReader vWorldDataXMLStream = new StringReader( aWorldDataInXml );
		
		try {
			
			if( UNMARSHALLER == null ){
		
				ClassLoader vWorldDataClassLoader = essentials.communication.ObjectFactory.class.getClassLoader();
				
				JAXBContext vJAXBContext = JAXBContext.newInstance( "essentials.communication", vWorldDataClassLoader );
				UNMARSHALLER = vJAXBContext.createUnmarshaller();
		
			}

			return (WorldData) UNMARSHALLER.unmarshal( vWorldDataXMLStream );
			
		} catch ( JAXBException vJAXBException ) {

			vJAXBException.printStackTrace();
			
		}
		
		return null;
		
	}
	

}
