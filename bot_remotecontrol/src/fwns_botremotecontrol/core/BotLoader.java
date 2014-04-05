package fwns_botremotecontrol.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;

import essentials.core.BotInformation;

public class BotLoader implements Runnable {

	private BotInformation mBotinformation;
	private Process mProcess;
	private BufferedReader mReader;
	private boolean active = false;

	public BotLoader(BotInformation aBot) {
		mBotinformation = aBot;
	}

	public boolean startBot() {
		try {

			if (mBotinformation != null) {

				
				List<String> argList = new ArrayList<String>();
				argList.add("test.bat");
				argList.add("-cp");
				argList.add("\"libraries/*:bot_mr.jar\"");
				argList.add("core.Main");
				
				// create processbuilder
				ProcessBuilder processBuilder = new ProcessBuilder(argList);
				processBuilder.redirectErrorStream(true);
				processBuilder.directory(new File("../MixedRealityBot"));
				
				// start process
				mProcess = processBuilder.start();
				
				// get process inputstream reader
				mReader = new BufferedReader(new InputStreamReader(mProcess.getInputStream()));

				active = true;
				(new Thread(this)).start();
				
				Thread.sleep(5000);
				active = false;
				mProcess.destroy();
				mProcess = null;
				
			}

		} catch (Exception vException) {
			Core.getLogger().error(
					"Error loading FWNS core "
							+ vException.getLocalizedMessage());
			Core.getLogger().catching(Level.ERROR, vException);
		}

		return false;
	}

	@Override
	public void run() {
		try{
			
			while( active && mReader != null ){
				String line = mReader.readLine();
				if( line != null ){
					System.out.println( "# " + line );
				}
			}
			
		}catch (IOException e){
			e.printStackTrace();
		}

	}

}
