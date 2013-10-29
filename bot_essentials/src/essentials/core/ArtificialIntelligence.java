package essentials.core;

public interface ArtificialIntelligence {

    /**
     * Function to initialize the AI. The BotInformation-parameter 
     * is the most recent with all possible Values. 
     * 
     * @param aBotInformation The full information about the Bot
     */
    public void initializeAI( BotInformation aBotInformation );
    
    /**
     * Function to start the AI. 
     * 
     */
    public void startAI();
    
    /**
     * Function to pause the AI. All execution of logic should be stopped 
     * 
     */
    public void pauseAI();
    
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
	 * @param aWorldData The Worldstate as an RawWorldDataobject
	 */
	public void putWorldState( essentials.communication.worlddata_server2008.RawWorldData aWorldData );

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
     * For diagnostic and debugging purposes the AI can get commands in form of strings
     * 
     * @param aCommandString the command for the AI
     * 
     */
	public void executeCommand( String aCommandString );
	

}
