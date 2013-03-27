package core;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.MissingOptionException;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import essentials.core.BotInformation.GamevalueNames;
import essentials.core.BotInformation.Teams;
import gui.CoreWindow;

class CommandLineOptions {

    private Options mOptions = null;

    private Option mHelpOption = null;
    private Option mBotNameOption = null;
    private Option mRcAndVtIdOption = null;
    private Option mServerAddressOption = null;
    private Option mBotPortOption = null;
    private Option mReconnectOption = null;
    private Option mTeamOption = null;
    private Option mGamevaluesOption = null;
    private Option mTeamNameOption = null;
    private Option mGuiOption = null;
    private Option mAiArchiveOption = null;
    private Option mAiClassnameOption = null;
    private Option mAiArgsOption = null;
    private Option mLoggingLevelOption = null;
    private Option mLoggingConsoleOption = null;
    private Option mLoggingFileOption = null;
    private Option mLoggingNetworkOption = null;
    
    public CommandLineOptions(){
        
        this.mOptions = new Options();
        this.setOptions();
        
    }
    
    private void setOptions(){

        this.setHelpOption();

        this.setBotNameOption();
        this.setBotPortOption();
        this.setReconnectOption();
        this.setTeamNameOption();
        this.setTeamOption();
        this.setRcAndVtIdOption();
        this.setServerAddressOption();
        
        this.setGamevaluesOptions();
        this.setGuiOption();
        this.setAiArchiveOption();
        this.setAiClassnameOption();
        this.setAiArgsOption();

        this.setLoggingLevelOption();
        this.setLoggingConsoleOption();
        this.setLoggingFileOption();
        this.setLoggingNetworkOption();
        
    }
    
    public Options getOptions(){
        
        return this.mOptions;
        
    }
    
    public CommandLine parseOptions( String[] aArguments ) throws ParseException {
                
        CommandLineParser vParser = new ExtendedGnuParser( true );
        return vParser.parse( mOptions, aArguments, false);
        
    }
    
    @SuppressWarnings("static-access")
    public void setHelpOption(){
        
        mHelpOption = OptionBuilder .withDescription(  "Diese Hilfe" )
                                    .create( "h" );
        mHelpOption.setLongOpt( "help" );
        
        mOptions.addOption( mHelpOption );
        
    }
    
    public Option getHelpOption(){
        
        return mHelpOption;
        
    }
    
    @SuppressWarnings("static-access")
    public void setBotNameOption(){
        
        mBotNameOption = OptionBuilder  .withArgName( "name" )
                                        .hasArg()
                                        .withDescription(  "Der Name des Bots" )
                                        .create( "bn" );
        mBotNameOption.setLongOpt( "botname" );
        
        mOptions.addOption( mBotNameOption );
        
    }
    
    public Option getBotNameOption(){
        
        return mBotNameOption;
        
    }
    
    @SuppressWarnings("static-access")
    public void setRcAndVtIdOption(){
        
        mRcAndVtIdOption = OptionBuilder.withArgName( "RcId:VtId" )
                                        .hasArgs()
                                        .withValueSeparator(':')
                                        .withDescription( "Die Rc- und VtId des Bots.\n" +
                                                          "Wenn die RcId gleich der VtId ist (RcId=VcId)\n" +
                                                          "kann nur ein Wert als Argument übergeben werden.\n" +
                                                          "Bsp: \n" +
                                                          "RcId = 1 und VcId = 4 -> -ids 1:4\n" +
                                                          "RcId = 3 und VcId = 3 -> -ids 3:3 oder -ids 3\n")
                                        .create( "ids" );
        mRcAndVtIdOption.setLongOpt( "rc_and_vt_id" );
        
        mOptions.addOption( mRcAndVtIdOption );
        
    }
    
    public Option getRcAndVtIdOption(){
        
        return mRcAndVtIdOption;
        
    }
    
    @SuppressWarnings("static-access")
    public void setServerAddressOption(){
        
        mServerAddressOption = OptionBuilder .withArgName( "IP:Port" )
                                            .hasArgs()
                                            .withValueSeparator(':')
                                            .withDescription( "Die Ip- und Team-Portadresse des Servers [IP:Port]" )
                                            .create( "s" );
        mServerAddressOption.setLongOpt( "server" );
        
        mOptions.addOption( mServerAddressOption );
        
    }
    
    public Option getServerAddressOption(){
        
        return mServerAddressOption;
        
    }

    @SuppressWarnings("static-access")
    public void setBotPortOption(){
        
        mBotPortOption = OptionBuilder .withArgName( "Port" )
                                            .hasArg()
                                            .withValueSeparator(':')
                                            .withDescription( "Der Port des Bots. [Port]\n" +
                                                              "Wenn kein Port angegeben wird, wird ein freier ausgewählt." )
                                            .create( "bp" );
        mBotPortOption.setLongOpt( "botport" );
        
        mOptions.addOption( mBotPortOption );
        
    }
    
    public Option getBotPortOption(){
        
        return mBotPortOption;
        
    }
    
    @SuppressWarnings("static-access")
    public void setReconnectOption(){
        
        mReconnectOption = OptionBuilder.withDescription( "Ob ein handschake mit dem Server ausgeführt werden soll " +
        		"und eine unterbroche Verbindug wieder augenommen wird. Nur in Verbindung mit einem Botport nutzbar.\n" +
        		"Bsp:\n" +
        		"-bp 4444 -rc -> nutzt den Port 4444 um sich von dort ohne Handschake zum Server zu verbinden." )
                                        .create( "rc" );
        mReconnectOption.setLongOpt( "reconnect" );
        
        mOptions.addOption( mReconnectOption );
        
    }
    
    public Option getReconnectOption(){
        
        return mReconnectOption;
        
    }
    
    @SuppressWarnings("static-access")
    public void setTeamOption(){
        
        mTeamOption = OptionBuilder .withArgName( "Team" )
                                    .hasArg()
                                    .withDescription( "Das Team des Bots [gelb,g,yellow,y,blau,b,blue]" )
                                    .create( "t" );
        mTeamOption.setLongOpt( "team" );
        
        mOptions.addOption( mTeamOption );
        
    }
    
    public Option getTeamOption(){
        
        return mTeamOption;
        
    }
    
    @SuppressWarnings("static-access")
    public void setGamevaluesOptions(){
        
        mGamevaluesOption = OptionBuilder   .withArgName( "Gamevalues" )
                                            .hasArgs()
                                            .withValueSeparator(':')
                                            .withDescription( "Spezielle Werte des Spielservers die zum angepassten Spielen notwendig sind." +
                                                              "Momentane mögliche einstellbare Werte: " + GamevalueNames.getAllGamevalueNamesAsAString())
                                            .create( "gv" );
        mGamevaluesOption.setLongOpt( "gamevalues" );
        
        mOptions.addOption( mGamevaluesOption );
        
    }
    
    public Option getGamevaluesOptions(){
        
        return mGamevaluesOption;
        
    }
    
    @SuppressWarnings("static-access")
    public void setTeamNameOption(){
        
        mTeamNameOption = OptionBuilder.withArgName( "Teamname" )
                                    .hasArg()
                                    .withDescription( "Der Name des Botteams." )
                                    .create( "tn" );
        mTeamNameOption.setLongOpt( "teamname" );
        
        mOptions.addOption( mTeamNameOption );
        
    }
    
    public Option getTeamNameOption(){
        
        return mTeamNameOption;
        
    }
    
    @SuppressWarnings("static-access")
    public void setGuiOption(){
        
        this.mGuiOption = OptionBuilder.withDescription( "Startet den Bot mit graphischem Userinterface." )
                                        .create( "gui" );
        this.mGuiOption.setLongOpt( "graphical-userinterface" );
        
        this.mOptions.addOption( mGuiOption );
        
    }
    
    public Option getGuiOption(){
        
        return this.mGuiOption;
        
    }    

    @SuppressWarnings("static-access")
    public void setAiArchiveOption(){
        
        this.mAiArchiveOption = OptionBuilder  .withArgName( "Ai Archiv" )
                                            .hasArg()
                                            .withDescription( "Die die AI enthaltende Datei mit absolutem oder relativem Pfad" )
                                            .create( "aiarc" );
        this.mAiArchiveOption.setLongOpt( "aiarchive" );
        
        this.mOptions.addOption( mAiArchiveOption );
        
    }
    
    public Option getAiArchiveOption(){
        
        return this.mAiArchiveOption;
        
    }
    
    @SuppressWarnings("static-access")
    public void setAiClassnameOption(){
        
        this.mAiClassnameOption = OptionBuilder  .withArgName( "Ai Klassenname" )
                                            .hasArg()
                                            .withDescription( "Der Klassenname der AI" )
                                            .create( "aicl" );
        this.mAiClassnameOption.setLongOpt( "aiclassname" );
        
        this.mOptions.addOption( mAiClassnameOption );
        
    }
    
    public Option getAiClassnameOption(){
        
        return this.mAiClassnameOption;
        
    }
    
    @SuppressWarnings("static-access")
    public void setAiArgsOption(){
        
        this.mAiArgsOption = OptionBuilder  .withArgName( "Ai Argumente" )
                                            .hasArg()
                                            .withDescription( "Der AI zu übergebende Argumente" )
                                            .create( "aiarg" );
        this.mAiArgsOption.setLongOpt( "aiarguments" );
        
        this.mOptions.addOption( mAiArgsOption );
        
    }
    
    public Option getAiArgsOption(){
        
        return this.mAiArgsOption;
        
    }
    
    @SuppressWarnings("static-access")
    public void setLoggingLevelOption(){
        
        mLoggingLevelOption = OptionBuilder    .withArgName( "Loglevel" )
                                            .hasArg()
                                            .withDescription( "Das LoggingLevel des Bots." + 
                                                    "Dabei wird das java util.logging Packet genutzt und die entsprechenden Level sind:" +
                                                    "[SEVERE, WARNING, INFO, CONFIG, FINE, FINER, FINEST]. Grundeinstellung ist INFO")
                                            .create( "ll" );
        mLoggingLevelOption.setLongOpt( "logginglevel" );
        
        mOptions.addOption( mLoggingLevelOption );
        
    }
    
    public Option getLoggingLevelOption(){
        
        return mLoggingLevelOption;
        
    }
    
    @SuppressWarnings("static-access")
    public void setLoggingConsoleOption(){
        
        mLoggingConsoleOption = OptionBuilder.withDescription( "Zeigt die Logs in der Console an. Normal aus." )
                                             .create( "lc" );
        mLoggingConsoleOption.setLongOpt( "logconsole" );
        
        mOptions.addOption( mLoggingConsoleOption );
        
    }
    
    public Option getLoggingConsoleOption(){
        
        return mLoggingConsoleOption;
        
    }
    
    @SuppressWarnings("static-access")
    public void setLoggingFileOption(){
        
        mLoggingFileOption = OptionBuilder    .withArgName( "LogFile" )
                                            .hasArg()
                                            .withDescription( "File in die geloggt werden soll. Normal wird in keine File geloggt." +
                                                    " Falls kein Argument übergeben wird, wird in eine Datei mit dem aktellen datum und der aktuellen Systemzeit als Filenamen geloggt. [, filename]")
                                            .create( "lf" );
        mLoggingFileOption.setLongOpt( "logfile" );
        
        mOptions.addOption( mLoggingFileOption );
        
    }
    
    public Option getLoggingFileOption(){
        
        return mLoggingFileOption;
        
    }
    
    @SuppressWarnings("static-access")
    public void setLoggingNetworkOption(){

        mLoggingNetworkOption = OptionBuilder    .withArgName( "IP:Port" )
                                                .hasArgs()
                                                .withValueSeparator(':')
                                                .withDescription( "Die Ip- und Portadresse des LogServers [IP:Port]" )
                                                .create( "ls" );
        mLoggingNetworkOption.setLongOpt( "logserver" );
        
        mOptions.addOption( mLoggingNetworkOption );
        
    }
    
    public Option getLoggingNetworkOption(){
        
        return mLoggingNetworkOption;
        
    }
    
    static void parseCommandLineArguments( String[] aArguments ) {

        CommandLineOptions vCommandLineOptions = new CommandLineOptions();

        try {

            CommandLine cmd = vCommandLineOptions.parseOptions( aArguments );

            vCommandLineOptions.parseAndShowHelp( cmd );
            
            vCommandLineOptions.checkForAndSetLogging( cmd );

            vCommandLineOptions.checkArgumentsForEssentialOptions( cmd );

            // cmdline verarbeiten
            vCommandLineOptions.checkForAndSetBotname( cmd );
            vCommandLineOptions.checkForAndSetBotIDs( cmd );
            vCommandLineOptions.checkForAndSetTeam( cmd );
            vCommandLineOptions.checkForAndSetTeamname( cmd );
            
            vCommandLineOptions.checkForAndSetServerAddress( cmd );
            vCommandLineOptions.checkForAndSetBotportAndReconnect( cmd );

            vCommandLineOptions.checkForAndSetAIArchive( cmd );
            vCommandLineOptions.checkForAndSetAIClassname( cmd );
            vCommandLineOptions.checkForAndSetAIArguments( cmd );
            
            vCommandLineOptions.checkForAndSetGui( cmd );

        } catch ( MissingOptionException vMissingOptionExceptions ) {

            Options vMissingCommandLineOptions = new Options();

            for ( Object s : vMissingOptionExceptions.getMissingOptions().toArray() ) {

                vMissingCommandLineOptions.addOption( vCommandLineOptions.getOptions().getOption( (String) s ) );

            }

            vCommandLineOptions.showCommandlineHelp( "Folgende benötigte Argumente wurden nicht übergeben:", vMissingCommandLineOptions );

        } catch ( ParseException vParseExceptions ) {
            
            vParseExceptions.printStackTrace();

        } catch ( Exception vAllExceptions ) {

            vAllExceptions.printStackTrace();

        }

    }

    private void checkForAndSetGui( CommandLine aCommandLine ) throws Exception {
        // gui
        if ( aCommandLine.hasOption( getGuiOption().getOpt() ) ) {

            Core.getInstance().setCoreWindow( new CoreWindow() );
            
        }
        
    }

    /**
     * @param aCommandLineOptions
     * @param aCommandLine
     */
    private void checkForAndSetLogging( CommandLine aCommandLine ) {
        // logging
        if( aCommandLine.hasOption( getLoggingFileOption().getOpt() ) ||
            aCommandLine.hasOption( getLoggingNetworkOption().getOpt() ) ||
            aCommandLine.hasOption( getLoggingConsoleOption().getOpt() ) ){
           
            //consolelogging
            if ( aCommandLine.hasOption( getLoggingConsoleOption().getOpt() ) ) {
      
            }
            // networklogging
            if ( aCommandLine.hasOption( getLoggingNetworkOption().getOpt() ) ) {
      
            }
            // filelogging
            if ( aCommandLine.hasOption( getLoggingFileOption().getOpt() ) ) {
      
            }
            
            if ( aCommandLine.hasOption( getLoggingLevelOption().getOpt() ) ) {
                
                // String vLogLevelArg = aCommandLine.getOptionValue( getLoggingLevelOption().getOpt() );
   
 
            } 
            
        }
    }

    /**
     * @param aCommandLineOptions
     * @param aCommandLine
     * @throws Exception
     */
    private void checkForAndSetAIClassname( CommandLine aCommandLine )
            throws Exception {
        // aiclassname
        if ( aCommandLine.hasOption( getAiClassnameOption().getOpt() ) ) {

            Core.getInstance().getBotinformation().setAIClassname( aCommandLine.getOptionValue( getAiClassnameOption().getOpt() ) );

        }
    }
    
    /**
     * @param aCommandLineOptions
     * @param aCommandLine
     * @throws Exception
     */
    private void checkForAndSetAIArchive( CommandLine aCommandLine )
            throws Exception {
        // aiargumente
        if ( aCommandLine.hasOption( getAiArchiveOption().getOpt() ) ) {

            Core.getInstance().getBotinformation().setAIArchive( aCommandLine.getOptionValue( getAiArchiveOption().getOpt() ) );

        }
    }
    
    /**
     * @param aCommandLineOptions
     * @param aCommandLine
     * @throws Exception
     */
    private void checkForAndSetAIArguments( CommandLine aCommandLine )
            throws Exception {
        // aiargumente
        if ( aCommandLine.hasOption( getAiArgsOption().getOpt() ) ) {

            Core.getInstance().getBotinformation().setAIArgs( aCommandLine.getOptionValue( getAiArgsOption().getOpt() ) );

        }
    }

    /**
     * @param aCommandLineOptions
     * @param aCommandLine
     * @throws Exception
     */
    private void checkForAndSetTeamname( CommandLine aCommandLine )
            throws Exception {
        // teamname
        if ( aCommandLine.hasOption( getTeamNameOption().getOpt() ) ) {

            Core.getInstance().getBotinformation().setTeamname( aCommandLine.getOptionValue( getTeamNameOption().getOpt() ) );

        }
    }

    /**
     * @param aCommandLineOptions
     * @param aCommandLine
     * @throws Exception
     */
    private void checkForAndSetTeam( CommandLine aCommandLine )
            throws Exception {
        //team
        if ( aCommandLine.hasOption( getTeamOption().getOpt() ) ) {

            String vTeamArg = aCommandLine.getOptionValue( getTeamOption().getOpt() );

            if ( vTeamArg.equalsIgnoreCase( "g" ) || vTeamArg.equalsIgnoreCase( "gelb" )
                    || vTeamArg.equalsIgnoreCase( "y" ) || vTeamArg.equalsIgnoreCase( "yellow" ) ) {
                Core.getInstance().getBotinformation().setTeam( Teams.Yellow );
            }
            if ( vTeamArg.equalsIgnoreCase( "b" ) || vTeamArg.equalsIgnoreCase( "blau" )
                    || vTeamArg.equalsIgnoreCase( "blue" ) ) {
                Core.getInstance().getBotinformation().setTeam( Teams.Blue );
            }

        }
    }

    /**
     * @param aCommandLineOptions
     * @param aCommandLine
     * @throws Exception
     */
    private void checkForAndSetBotportAndReconnect( CommandLine aCommandLine )
            throws Exception {
        //botport
        if ( aCommandLine.hasOption( getBotPortOption().getOpt() ) ) {

            Core.getInstance().getBotinformation().setBotPort( Integer.parseInt( aCommandLine.getOptionValue( getBotPortOption().getOpt() ) ) );

            //reconnect kann nur mit festem Botport durchgeführt werden
            if ( aCommandLine.hasOption( getReconnectOption().getOpt() ) ) {

                Core.getInstance().getBotinformation().setReconnect( true );

            }
            
        }
    }

    /**
     * @param aCommandLine
     * @throws Exception
     * @throws UnknownHostException
     */
    private void checkForAndSetServerAddress( CommandLine aCommandLine )
            throws Exception, UnknownHostException {
        // serveradresse
        if ( aCommandLine.hasOption( getServerAddressOption().getOpt() ) ) {

            Core.getInstance().getBotinformation().setServerIP( InetAddress.getByName( aCommandLine.getOptionValues( getServerAddressOption().getOpt() )[0] ) );
            Core.getInstance().getBotinformation().setServerPort( Integer.parseInt( (aCommandLine.getOptionValues( getServerAddressOption().getOpt() )[1]) ) );

        }
    }

    /**
     * @param aCommandLineOptions
     * @param aCommandLine
     * @throws Exception
     */
    private void checkForAndSetBotIDs( CommandLine aCommandLine )
            throws Exception {
        // botids
        if ( aCommandLine.hasOption( getRcAndVtIdOption().getOpt() ) ) {

            if ( aCommandLine.getOptionValues( getRcAndVtIdOption().getOpt() ).length > 1 ) {
                Core.getInstance().getBotinformation().setRcId( Integer.parseInt( (aCommandLine.getOptionValues( getRcAndVtIdOption().getOpt() )[0]) ) );
                Core.getInstance().getBotinformation().setVtId( Integer.parseInt( (aCommandLine.getOptionValues( getRcAndVtIdOption().getOpt() )[1]) ) );
            } else {
                Core.getInstance().getBotinformation().setRcId( Integer.parseInt( aCommandLine.getOptionValue( getRcAndVtIdOption().getOpt() ) ) );
                Core.getInstance().getBotinformation().setVtId( Core.getInstance().getBotinformation().getRcId() );
            }
        }
    }

    /**
     * @param aCommandLineOptions
     * @param aCommandLine
     * @throws Exception
     */
    private void checkForAndSetBotname( CommandLine aCommandLine )
            throws Exception {
        // botname
        if ( aCommandLine.hasOption( getBotNameOption().getOpt() ) ) {

            Core.getInstance().getBotinformation().setBotname( aCommandLine.getOptionValue( getBotNameOption().getOpt() ) );

        }
    }

    /**
     * @param aCommandLineOptions
     * @param aCommandLine
     * @throws MissingOptionException
     */
    private void checkArgumentsForEssentialOptions( CommandLine aCommandLine )
            throws MissingOptionException {
        // essentielle cmdlineargumente überprüfen
        if ( !(aCommandLine.hasOption( getGuiOption().getOpt() ) || 
                (aCommandLine.hasOption( getServerAddressOption().getOpt() ) 
                && aCommandLine.hasOption( getRcAndVtIdOption().getOpt() )
                && aCommandLine.hasOption( getAiArchiveOption().getOpt() ))
                && aCommandLine.hasOption( getAiClassnameOption().getOpt() )) ) {

            List<String> vMissingOptions = new ArrayList<String>();

            if ( !aCommandLine.hasOption( getServerAddressOption().getOpt() ) ) {

                vMissingOptions.add( getServerAddressOption().getOpt() );

            }

            if ( !aCommandLine.hasOption( getRcAndVtIdOption().getOpt() ) ) {

                vMissingOptions.add( getRcAndVtIdOption().getOpt() );

            }

            if ( !aCommandLine.hasOption( getAiArchiveOption().getOpt() ) ) {

                vMissingOptions.add( getAiArchiveOption().getOpt() );

            }

            if ( !aCommandLine.hasOption( getAiClassnameOption().getOpt() ) ) {

                vMissingOptions.add( getAiClassnameOption().getOpt() );

            }

            throw new MissingOptionException( vMissingOptions );

        }
    }

    /**
     * @param aCommandLineOptions
     * @param aCommandLine
     */
    private void parseAndShowHelp( CommandLine aCommandLine ) {
        // Hilfe anzeigen
        if ( aCommandLine.hasOption( getHelpOption().getOpt() ) ) {

            showCommandlineHelp( "FwNS Bot Commandlinehilfe\n Essentielle Argumente sind -ids und -s", getOptions() );

        }
    }

    private void showCommandlineHelp( String aHelpString, Options aCommandLineOptions ) {

        HelpFormatter vHelpFormatter = new HelpFormatter();
        vHelpFormatter.setWidth( 120 );
        vHelpFormatter.setLongOptPrefix( "-" );
        vHelpFormatter.setSyntaxPrefix( "" );
        vHelpFormatter.printHelp( aHelpString, aCommandLineOptions );

        System.exit( 0 );
    }
    
    class ExtendedGnuParser extends GnuParser {

        private boolean mIgnoreUnrecognizedOption;

        public ExtendedGnuParser(final boolean ignoreUnrecognizedOption) {
            mIgnoreUnrecognizedOption = ignoreUnrecognizedOption;
        }

        @Override
        protected void processOption(final String aArgument, final ListIterator aIter) throws ParseException {
            boolean hasOption = getOptions().hasOption( aArgument );

            if ( hasOption || !mIgnoreUnrecognizedOption ) {
                super.processOption( aArgument, aIter );
            }
        }

    }
    
}
