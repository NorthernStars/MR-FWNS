package fwns_botremotecontrol.core;

import java.rmi.RemoteException;

import org.apache.logging.log4j.core.LogEvent;

import fwns_network.botremotecontrol.LogListener;

public class LogListenerImplementation implements LogListener {

	@Override
	public void logEvent(LogEvent aLogEvent) throws RemoteException {
		System.out.println( aLogEvent );
	}

}
