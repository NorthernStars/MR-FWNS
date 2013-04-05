package essentials.core;

public interface ArtificialIntelligence {
    
    /**
     * Function to initialize and start the AI. The BotInformation-parameter 
     * is the most recent with all possible Values. 
     * 
     * @param BotInformation The full information about the Bot
     */
    public void initializeAI( BotInformation BotInformation );
    
    /**
     * Disables and disposes the AI. Afterward the AI will be garbage-collected.
     */
	public void disposeAI();

	/**
	 * Passes any newly acquired Worldstate from the Botcore to the AI. 
	 * This will happen in the same frequency as messages arrive from the
	 * server, the exact delay is saved in the [TODO: name and position in BotInformation (GameClockCycle) ]
	 * The excecution is not guaranteed, because a new Worldstate can only be generated, if
	 * the server sends the data.
	 * 
	 * @param WorldData The Worldstate as an RawWorldDataobject
	 */
	public void putWorldState( essentials.communication.worlddata_server2008.RawWorldData WorldData );

	/**
	 * Gets an Actionobject to send to the server. This method will 
	 * be executed in the same frequency as the server-clockcycle, as 
	 * its saved in the [TODO: name and position in BotInformation (GameClockCycle) ]
	 * 
	 * @return Action The Action to send to the server.
	 */
	public essentials.communication.Action getAction();
	
	/**
	 * Checks if the AI runs. For diagnose only.
	 * 
	 * @return TRUE if the AI runs. FALSE if not.
	 */
	public boolean isRunning();
	
	/**
	 * If the AI wants t restart itself or load and start an other AI, 
	 * it can indicate this desire by returning TRUE. 
	 * To start another AI in this way the AIArchive and AIClassname in 
	 * BotInformation have to be set pointing at the desired AI. Otherwise
	 * the old AI will be reloaded and start or if no AI is found at the
	 * indicated place, the Bot will terminate.
	 *
	 * @return TRUE to reload and restart the ai, FALSE to do nothing
	 */
	public boolean wantRestart();

	/**
	 * For diagnose- or configuration-purposes the AI can implement its own eclipse.swt based GUI
	 * to be displayed. This method executes when the AIConfiguration-Button in
	 * the CoreAIGui is pressed. As the Parameter it gets the parent Display. 
	 * 
	 * @param Display The parent Display
	 * @return The shell to display
	 */
	public org.eclipse.swt.widgets.Shell getConfigurationWindow( org.eclipse.swt.widgets.Display Display);

}
