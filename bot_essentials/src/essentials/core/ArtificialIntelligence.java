package essentials.core;

public interface ArtificialIntelligence {
    
    public void initializeAI( BotInformation aBotInformation );
    
	public void disposeAI();

	public void putWorldState( essentials.communication.worlddata_server2008.RawWorldData aWorldData );

	public essentials.communication.Action getAction();
	
	public boolean isRunning();

	public org.eclipse.swt.widgets.Shell getConfigurationWindow( org.eclipse.swt.widgets.Display aDisplay);

}
