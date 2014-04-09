package fwns_botremotecontrol.core;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.Level;

import essentials.core.BotInformation;

/**
 * Class for loading a bot.
 * @author Hannes Eilers
 *
 */
public class BotLoader {
	
	private static HashMap<String, BotLoader> runningProcesses = new HashMap<String, BotLoader>();

	private BotInformation mBot;
	private File mCoreJarFile;
	private Process mProcess;
	private String mKey = "";
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
				String relativeAiPath = basePath.relativize(aiFilePath).toString().replace("../", "").replace("..\\", "");
				
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
				mKey = "//localhost:1099/" + mBot.getBotname()
						+ "-" + mBot.getRcId() + "-" + mBot.getVtId();
				mMessages.clear();
				mProcess = processBuilder.start();
				runningProcesses.put(mKey, this);
				Core.getLogger().info("Started bot " + mBot.getBotname() + "(" + mBot.getVtId() + ") "
				+ processBuilder.directory().getPath());
				
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
		if( mProcess != null ){
			mProcess.destroy();
			runningProcesses.remove(mKey);
			return true;
		}
		
		return false;
	}
	
	/**
	 * Tries to get a botloader object by its remote control adress.
	 * @param aKey	{@link String} remote control adress
	 * @return		{@link BotLoader} if found, {@code null} otherwise.
	 */
	public static BotLoader getBotLoaderByKey(String aKey){
		if( runningProcesses.containsKey(aKey) ){
			return runningProcesses.get(aKey);
		}
		
		return null;
	}
	
	/**
	 * Stops all running processes.
	 */
	public static void stopRunningProcesses(){
		Core.getLogger().debug("Stopping running processes.");
		for( String key : runningProcesses.keySet() ){
			runningProcesses.get(key).getProcess().destroy();
		}
		runningProcesses.clear();
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
	 * @return {@link Process} of this {@link BotLoader}.
	 */
	public Process getProcess() {
		return mProcess;
	}

}
