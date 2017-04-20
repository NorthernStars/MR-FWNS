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

/**
 * Klasse zum parsen und weiterverarbeiten der Commandline. Nutzt Apache-Cli. 
 * 
 * @author Eike Petersen
 * @since 0.4
 * @version 0.9
 *
 */
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
    private Option mRemoteStartOption = null;
    private Option mAiArchiveOption = null;
    private Option mAiClassnameOption = null;
    private Option mAiArgsOption = null;
    
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
        this.setRemoteStartOption();
        this.setAiArchiveOption();
        this.setAiClassnameOption();
        this.setAiArgsOption();
        
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
        
        OptionBuilder .withDescription(  "Diese Hilfe" );
		mHelpOption = OptionBuilder
                                    .create( "h" );
        mHelpOption.setLongOpt( "help" );
        
        mOptions.addOption( mHelpOption );
        
    }
    
    public Option getHelpOption(){
        
        return mHelpOption;
        
    }
    
    @SuppressWarnings("static-access")
    public void setBotNameOption(){
        
        OptionBuilder  .withArgName( "name" );
		OptionBuilder
                                        .hasArg();
		OptionBuilder
                                        .withDescription(  "Der Name des Bots" );
		mBotNameOption = OptionBuilder
                                        .create( "bn" );
        mBotNameOption.setLongOpt( "botname" );
        
        mOptions.addOption( mBotNameOption );
        
    }
    
    public Option getBotNameOption(){
        
        return mBotNameOption;
        
    }
    
    @SuppressWarnings("static-access")
    public void setRcAndVtIdOption(){
        
        OptionBuilder.withArgName( "RcId:VtId" ) //TODO: Vc oder Vt
;
		OptionBuilder
                                        .hasArgs();
		OptionBuilder
                                        .withValueSeparator(':');
		OptionBuilder
                                        .withDescription( "Die Rc- und VtId des Bots.\n" +
                                                          "Wenn die RcId gleich der VtId ist (RcId=VcId)\n" +
                                                          "kann nur ein Wert als Argument übergeben werden.\n" +
                                                          "Bsp: \n" +
                                                          "RcId = 1 und VcId = 4 -> -ids 1:4\n" +
                                                          "RcId = 3 und VcId = 3 -> -ids 3:3 oder -ids 3\n");
		mRcAndVtIdOption = OptionBuilder
                                        .create( "ids" );
        mRcAndVtIdOption.setLongOpt( "rc_and_vt_id" );
        
        mOptions.addOption( mRcAndVtIdOption );
        
    }
    
    public Option getRcAndVtIdOption(){
        
        return mRcAndVtIdOption;
        
    }
    
    @SuppressWarnings("static-access")
    public void setServerAddressOption(){
        
        OptionBuilder .withArgName( "IP:Port" );
		OptionBuilder
                                            .hasArgs();
		OptionBuilder
                                            .withValueSeparator(':');
		OptionBuilder
                                            .withDescription( "Die Ip- und Team-Portadresse des Servers [IP:Port]" );
		mServerAddressOption = OptionBuilder
                                            .create( "s" );
        mServerAddressOption.setLongOpt( "server" );
        
        mOptions.addOption( mServerAddressOption );
        
    }
    
    public Option getServerAddressOption(){
        
        return mServerAddressOption;
        
    }

    @SuppressWarnings("static-access")
    public void setBotPortOption(){
        
        OptionBuilder .withArgName( "Port" );
		OptionBuilder
                                            .hasArg();
		OptionBuilder
                                            .withValueSeparator(':');
		OptionBuilder
                                            .withDescription( "Der Port des Bots. [Port]\n" +
                                                              "Wenn kein Port angegeben wird, wird ein freier ausgewählt." );
		mBotPortOption = OptionBuilder
                                            .create( "bp" );
        mBotPortOption.setLongOpt( "botport" );
        
        mOptions.addOption( mBotPortOption );
        
    }
    
    public Option getBotPortOption(){
        
        return mBotPortOption;
        
    }
    
    @SuppressWarnings("static-access")
    public void setReconnectOption(){
        
        OptionBuilder.withDescription( "Ob ein handschake mit dem Server ausgeführt werden soll " +
        		"und eine unterbroche Verbindug wieder augenommen wird. Nur in Verbindung mit einem Botport nutzbar.\n" +
        		"Bsp:\n" +
        		"-bp 4444 -rc -> nutzt den Port 4444 um sich von dort ohne Handschake zum Server zu verbinden." );
		mReconnectOption = OptionBuilder
                                        .create( "rc" );
        mReconnectOption.setLongOpt( "reconnect" );
        
        mOptions.addOption( mReconnectOption );
        
    }
    
    public Option getReconnectOption(){
        
        return mReconnectOption;
        
    }
    
    @SuppressWarnings("static-access")
    public void setTeamOption(){
        
        OptionBuilder .withArgName( "Team" );
		OptionBuilder
                                    .hasArg();
		OptionBuilder
                                    .withDescription( "Das Team des Bots [gelb,g,yellow,y,blau,b,blue]" );
		mTeamOption = OptionBuilder
                                    .create( "t" );
        mTeamOption.setLongOpt( "team" );
        
        mOptions.addOption( mTeamOption );
        
    }
    
    public Option getTeamOption(){
        
        return mTeamOption;
        
    }
    
    @SuppressWarnings("static-access")
    public void setGamevaluesOptions(){
        
        OptionBuilder   .withArgName( "Gamevalues" );
		OptionBuilder
                                            .hasArgs();
		OptionBuilder
                                            .withValueSeparator(':');
		OptionBuilder
                                            .withDescription( "Spezielle Werte des Spielservers die zum angepassten Spielen notwendig sind." +
                                                              "Momentane mögliche einstellbare Werte: " + GamevalueNames.getAllGamevalueNamesAsAString());
		mGamevaluesOption = OptionBuilder
                                            .create( "gv" );
        mGamevaluesOption.setLongOpt( "gamevalues" );
        
        mOptions.addOption( mGamevaluesOption );
        
    }
    
    public Option getGamevaluesOptions(){
        
        return mGamevaluesOption;
        
    }
    
    @SuppressWarnings("static-access")
    public void setTeamNameOption(){
        
        OptionBuilder.withArgName( "Teamname" );
		OptionBuilder
                                    .hasArg();
		OptionBuilder
                                    .withDescription( "Der Name des Botteams." );
		mTeamNameOption = OptionBuilder
                                    .create( "tn" );
        mTeamNameOption.setLongOpt( "teamname" );
        
        mOptions.addOption( mTeamNameOption );
        
    }
    
    public Option getTeamNameOption(){
        
        return mTeamNameOption;
        
    }
    
    @SuppressWarnings("static-access")
    public void setRemoteStartOption(){
        
        OptionBuilder.withDescription( " Verhindert das automatische Starten des Bots." +
                                                                    " Die Verbindung zum Bot wird ueber die Bot-IP und" +
                                                                    " den Namen zusammen mit der RcID und VcID hergestellt." +
                                                                    " Bsp.: 127.0.0.1/MyBot-0-0 fuer den Bot MyBot mir RcId = 0 " +
                                                                    " und VcId = 0") //TODO: Ueberpruefen 
;
		this.mRemoteStartOption = OptionBuilder
                                        .create( "rs" );
        this.mRemoteStartOption.setLongOpt( "remote-start" );
        
        this.mOptions.addOption( mRemoteStartOption );
        
    }
    
    public Option getRemoteStartOption(){
        
        return this.mRemoteStartOption;
        
    }    

    @SuppressWarnings("static-access")
    public void setAiArchiveOption(){
        
        OptionBuilder  .withArgName( "Ai Archiv" );
		OptionBuilder
                                            .hasArg();
		OptionBuilder
                                            .withDescription( "Die die AI enthaltende Datei mit absolutem oder relativem Pfad" );
		this.mAiArchiveOption = OptionBuilder
                                            .create( "aiarc" );
        this.mAiArchiveOption.setLongOpt( "aiarchive" );
        
        this.mOptions.addOption( mAiArchiveOption );
        
    }
    
    public Option getAiArchiveOption(){
        
        return this.mAiArchiveOption;
        
    }
    
    @SuppressWarnings("static-access")
    public void setAiClassnameOption(){
        
        OptionBuilder  .withArgName( "Ai Klassenname" );
		OptionBuilder
                                            .hasArg();
		OptionBuilder
                                            .withDescription( "Der Klassenname der AI" );
		this.mAiClassnameOption = OptionBuilder
                                            .create( "aicl" );
        this.mAiClassnameOption.setLongOpt( "aiclassname" );
        
        this.mOptions.addOption( mAiClassnameOption );
        
    }
    
    public Option getAiClassnameOption(){
        
        return this.mAiClassnameOption;
        
    }
    
    @SuppressWarnings("static-access")
    public void setAiArgsOption(){
        
        OptionBuilder  .withArgName( "Ai Argumente" );
		OptionBuilder
                                            .hasArg();
		OptionBuilder
                                            .withDescription( "Der AI zu übergebende Argumente" );
		this.mAiArgsOption = OptionBuilder
                                            .create( "aiarg" );
        this.mAiArgsOption.setLongOpt( "aiarguments" );
        
        this.mOptions.addOption( mAiArgsOption );
        
    }
    
    public Option getAiArgsOption(){
        
        return this.mAiArgsOption;
        
    }
    
    /**
     * Verarbeitet die einzelnen Commandlineargumente. Dabei wird auch ueberprueft ob alle
     * essentiellen Argumente vorhanden sind. Die einzelnen argumete wurden vorher in ihren
     * respectiven Optionen definiert.
     * 
     * @since 0.4
     * @param aArgumente die Commandline als Stringarray
     * 
     * @return ob der Bot direkt starten oder auf den Startbefehl warten soll
     */
    static boolean parseCommandLineArguments( String[] aArguments ) {

        Core.getLogger().trace( "Parsing commandline." );
        CommandLineOptions vCommandLineOptions = new CommandLineOptions();

        try {

            CommandLine cmd = vCommandLineOptions.parseOptions( aArguments );

            vCommandLineOptions.parseAndShowHelp( cmd );

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
            
            return vCommandLineOptions.checkForAndSetRemoteStart( cmd );

        } catch ( MissingOptionException vMissingOptionExceptions ) {

            Options vMissingCommandLineOptions = new Options();

            for ( Object s : vMissingOptionExceptions.getMissingOptions().toArray() ) {

                vMissingCommandLineOptions.addOption( vCommandLineOptions.getOptions().getOption( (String) s ) );

            }

            vCommandLineOptions.showCommandlineHelp( "Folgende benötigte Argumente wurden nicht übergeben:", vMissingCommandLineOptions );

        } catch ( ParseException vParseExceptions ) {
            
            Core.getLogger().error( "Fehler beim Parsen der CommandLine", vParseExceptions );

        } catch ( Exception vAllExceptions ) {

            Core.getLogger().error( "Unerwarteter Fehler", vAllExceptions );

        }
        
        Core.getInstance().close();
        return false;

    }

    private boolean checkForAndSetRemoteStart( CommandLine aCommandLine ) throws Exception {
        // Remotestart
        if ( aCommandLine.hasOption( getRemoteStartOption().getOpt() ) ) {

            return true;
            
        }
        
        return false;
        
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
            if ( !aCommandLine.hasOption( getRcAndVtIdOption().getOpt() ) && 
                 !(aCommandLine.hasOption( getRemoteStartOption().getOpt() ) || 
                   (aCommandLine.hasOption( getServerAddressOption().getOpt() )
                    && aCommandLine.hasOption( getAiArchiveOption().getOpt() ))
                    && aCommandLine.hasOption( getAiClassnameOption().getOpt() )
                  ) 
               ) {

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

        @SuppressWarnings("rawtypes")
		@Override
        protected void processOption(final String aArgument, final ListIterator aIter) throws ParseException {
            boolean hasOption = getOptions().hasOption( aArgument );

            if ( hasOption || !mIgnoreUnrecognizedOption ) {
                super.processOption( aArgument, aIter );
            }
        }

    }
    
}
