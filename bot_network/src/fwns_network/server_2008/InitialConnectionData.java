package fwns_network.server_2008;

import java.io.StringWriter;

import javax.xml.bind.JAXB;
import javax.xml.bind.annotation.XmlRootElement;

import essentials.communication.Action;
import essentials.core.BotInformation;

public class InitialConnectionData implements Action{
	
	@XmlRootElement
	private static class connect{
		
		@SuppressWarnings("unused")
		public String type;
		@SuppressWarnings("unused")
		public double protocol_version;
        @SuppressWarnings("unused")
        public String nickname;
        @SuppressWarnings("unused")
        public String teamname;
		@SuppressWarnings("unused")
		public int rc_id;
		@SuppressWarnings("unused")
		public int vt_id;
		
	}
	
	private connect mConnection = new connect();
	
	public InitialConnectionData( BotInformation aBot ){
		
		mConnection.type = "Client";
		mConnection.protocol_version = 1.1;
        mConnection.nickname = aBot.getBotname();
        mConnection.nickname = aBot.getTeamname();
		mConnection.rc_id = aBot.getRcId();
		mConnection.vt_id = aBot.getVtId();
		
	}
	
	@Override
	public String getXMLString() {
		
		StringWriter vXMLDataStream = new StringWriter();
		JAXB.marshal( mConnection, vXMLDataStream );
		
		String vXMLDataString = vXMLDataStream.toString();
		vXMLDataString = vXMLDataString.substring( vXMLDataString.indexOf('>') + 2 );
		
		return vXMLDataString;
		
	}

}
