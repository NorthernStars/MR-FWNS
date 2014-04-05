package fwns_botremotecontrol.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;

import essentials.core.BotInformation;

/**
 * Class for loading a bot.
 * @author Hannes Eilers
 *
 */
public class BotLoader implements Runnable {
	
	private static List<Process> runningProcesses = new ArrayList<Process>();

	private BotInformation mBot;
	private String workingDir = ".";
	private Process mProcess;
	private BufferedReader mReader;
	private boolean active = false;
	private List<String> mMessages = new ArrayList<String>();

	/**
	 * Consctructor
	 * @param aBot			{@link BotInformation} about bot to start
	 * @param coreJarFile	{@link File} of bot core
	 */
	public BotLoader(BotInformation aBot, File coreJarFile) {
		setBot(aBot);
		workingDir = coreJarFile.getParent();
	}

	/**
	 * Starts bot as new process.
	 * @return {@code true} if bot started successfull, {@code false} otherwise.
	 */
	public boolean startBot() {
		try {

			if (mBot != null) {

				String cmd = "java -cp \"libraries/*;bot_mr.jar\" core.Main"
						+ " -bn " + mBot.getBotname()
						+ " -tn " + mBot.getTeamname()
						+ " -t " + mBot.getTeam().name().toLowerCase()
						+ " -ids " + mBot.getRcId() + ":" + mBot.getVtId()
						+ " -s " + mBot.getServerIP() + ":" + mBot.getServerPort()
						+ " -aiarc \"" + mBot.getAIArchive() + "\""
						+ " -aicl \"" + mBot.getAIClassname() + "\"";
				
				// create processbuilder
				ProcessBuilder processBuilder = new ProcessBuilder( cmd.split(" ") );
				processBuilder.redirectErrorStream(true);
				processBuilder.directory( new File(workingDir) );
				
				// start process
				mMessages.clear();
				mProcess = processBuilder.start();
				runningProcesses.add(mProcess);
				Core.getLogger().info("Started bot " + mBot.getBotname() + "(" + mBot.getVtId() + ")");
				
				// get process inputstream reader
				mReader = new BufferedReader(new InputStreamReader(mProcess.getInputStream()));

				// start output reading thread
				active = true;
				(new Thread(this)).start();
				
				return true;
				
			}

		} catch (Exception vException) {
			Core.getLogger().error(
					"Error loading FWNS core "
							+ vException.getLocalizedMessage());
			Core.getLogger().catching(Level.ERROR, vException);
		}

		return false;
	}
	
	/**
	 * Stops bot process.
	 * @return {@code true} if bot stopped, {@code false} otherwise.
	 */
	public boolean stopBot(){		
		active = false;
		if( mProcess != null ){
			mProcess.destroy();
			runningProcesses.remove(mProcess);
			return true;
		}
		
		return false;
	}
	
	/**
	 * Stops all running processes.
	 */
	public static void stopRunningProcesses(){
		Core.getLogger().info("Stopping running processes.");
		for( Process p : runningProcesses ){
			p.destroy();
		}
	}

	/**
	 * @return {@link BotInformation} of this bot
	 */
	public BotInformation getBot() {
		return mBot;
	}

	/**
	 * @param mBot {@link BotInformation} to set
	 */
	public void setBot(BotInformation mBot) {
		this.mBot = mBot;
	}

	/**
	 * @return {@code true} if bot is currently active, {@code false} otherwise.
	 */
	public boolean isActive() {
		return active;
	}
	
	
	@Override
	public void run() {
		try{
			
			// while bot is active show output of bot process
			while( isActive() && mReader != null ){
				String line = mReader.readLine();
				if( line != null ){
					System.out.println( line );
				}
			}

			if( mReader != null ){
				mReader.close();
				mReader = null;
			}
		
		}catch (IOException e){
			e.printStackTrace();
		}
	}

}
