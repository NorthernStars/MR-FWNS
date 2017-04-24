package core;

import essentials.communication.Action;
import essentials.communication.WorldData;
import essentials.core.ArtificialIntelligence;
import essentials.core.BotInformation;

public class TestAi implements ArtificialIntelligence {

	@Override
	public void initializeAI(BotInformation aBotInformation) {
		
	}

	@Override
	public void resumeAI() {}

	@Override
	public void suspendAI() {}

	@Override
	public void disposeAI() {}

	@Override
	public void putWorldState(WorldData aWorldData) {}

	@Override
	public Action getAction() {
		return null;
	}

	@Override
	public boolean isRunning() {
		return mRunning;
	}

	@Override
	public boolean wantRestart() {
		return false;
	}

	@Override
	public void executeCommand(String aCommandString) {
		// TODO Auto-generated method stub
		
	}
	
	private boolean mRunning = false;
	public void setRunning( boolean aRunning ){
		mRunning = aRunning;
	}
	
}