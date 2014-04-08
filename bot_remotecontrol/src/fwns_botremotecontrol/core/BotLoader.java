package fwns_botremotecontrol.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
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
	private File mCoreJarFile;
	private Process mProcess;
	private BufferedReader mReader;
	private boolean active = false;
	private List<String> mMessages = new ArrayList<String>();

	/**
	 * Consctructor
	 * @param aBot			{@link BotInformation} about bot to start
	 * @param aCoreJarFile	{@link File} of bot core
	 */
	public BotLoader(BotInformation aBot, File aCoreJarFile) {
		setBot(aBot);
		mCoreJarFile = aCoreJarFile;
	}

	/**
	 * Starts bot as new process.
	 * @return {@code true} if bot started successfull, {@code false} otherwise.
	 */
	public boolean startBot() {
		try {
			
			if (mBot != null) {
				
				// get relative paths
				Path basePath = Paths.get( mCoreJarFile.getPath() );
				Path aiFilePath = Paths.get( mBot.getAIArchive() );
				String relativeAiPath = basePath.relativize(aiFilePath).toString().replace("../", "");
				
				boolean isLinux = false;
				String cpSeperator = ";";
				
				String os = System.getProperty("os.name").toLowerCase();
				if( os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0 || os.indexOf("aix") > 0 ){
					cpSeperator = ":";
					isLinux = true;
				}
				
				// set command
				String cmd = "java -cp \"libraries/*" + cpSeperator + mCoreJarFile.getName() + "\" core.Main"
						+ ( mBot.getBotname().trim().length() > 0 ? " -bn " + mBot.getBotname().trim() : "" )
						+ ( mBot.getTeamname().trim().length() > 0 ? " -tn " + mBot.getTeamname().trim() : "" )
						+ " -t " + mBot.getTeam().name().toLowerCase()
						+ " -ids " + mBot.getRcId() + ":" + mBot.getVtId()
						+ " -s " + mBot.getServerIP().getHostAddress() + ":" + mBot.getServerPort()
						+ " -aiarc \"" + relativeAiPath + "\""
						+ " -aicl \"" + mBot.getAIClassname() + "\"";
				
				// check for linux
				if( isLinux ){
					cmd = "./bot_remotecontrol_linux_starter.sh " + cmd + "";
				}
				System.out.println("CMD: " + cmd);
				
				// create processbuilder
				ProcessBuilder processBuilder = new ProcessBuilder( cmd.split(" ") );
				processBuilder.redirectErrorStream(true);
				if( mCoreJarFile.getParent() != null ){
					processBuilder.directory( new File(mCoreJarFile.getParent()) );
				} else{
					processBuilder.directory( new File(".") );
				}
				
				// start process
				mMessages.clear();
				mProcess = processBuilder.start();
				runningProcesses.add(mProcess);
				Core.getLogger().info("Started bot " + mBot.getBotname() + "(" + mBot.getVtId() + ") "
				+ processBuilder.directory().getPath());
				
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
		Core.getLogger().debug("Stopping running processes.");
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
				if( mReader.ready()){
					String line = mReader.readLine();
					if( line != null ){
						// Uncomment to see process output
//						System.out.println( "# " + line );
					}
				}
			}

			if( mReader != null ){
				mReader.close();
			}
		
		}catch (IOException e){
			e.printStackTrace();
		}
	}

}
