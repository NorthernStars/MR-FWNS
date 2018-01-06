package core;

import essentials.core.BotInformation;
import essentials.core.BotInformation.Teams;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

import picocli.CommandLine;
import picocli.CommandLine.Option;

/**
 * Class to parse the Commandline. Uses picocli.
 * 
 * @author Eike Petersen, Simon Bruhse
 * @since 0.4
 * @version 0.9
 *
 */
class CommandLineOptions{

    @Option(names={"-h", "-help", "--help"}, description="Diese Hilfe", usageHelp=true)
    private boolean help;

    @Option(names={"-bn", "-botname", "--botname"}, description="Der Name des Bots")
    private String botname;

    @Option(names={"-ids","-rc_and_vt_id", "--rc_and_vt_id"},
            description = "Die Rc- und VtId des Bots.\n" +
                            "Wenn die RcId gleich der VtId ist (RcId=VcId) " +
                            "kann nur ein Wert als Argument übergeben werden.\n" +
                            "Bsp: \n" +
                            "RcId = 1 und VcId = 4 -> -ids 1:4\n" +
                            "RcId = 3 und VcId = 3 -> -ids 3:3 oder -ids 3",
            split = ":",
            required = true)
    private int[] rcAndVtIdOption;

    @Option(names={"-s", "-server", "--server"}, description = "Die Ip- und Team-Portadresse des Servers [IP:Port]", required = true, split=":")
    private String[] serverPortAndAddress;

    @Option(names = {"-bp", "-botport", "--botport"}, description = "Der Port des Bots. [Port] Wenn kein Port angegeben wird, wird ein freier ausgewählt.")
    private int botPort = -1;

    @Option(names={"-rc", "-reconnect", "--reconnect"}, description = "Ob ein Handshake mit dem Server ausgeführt werden soll " +
                                                                        "und eine unterbroche Verbindug wieder augenommen wird. Nur in Verbindung mit einem Botport nutzbar.\n" +
                                                                        "Bsp:\n" +
                                                                        "-bp 4444 -rc -> nutzt den Port 4444 um sich von dort ohne Handshake zum Server zu verbinden.")
    private boolean reconnect;


    @Option(names = {"-t", "-team", "--team"}, description = "Das Team des Bots [gelb,g,yellow,y,blau,b,blue]", type = Teams.class)
    BotInformation.Teams team;

    //TODO: GamevalueNames.getAllGamevalueNamesAsAString() am Ende der Description hinzufügen
    @Option(names = {"-gv", "-gamevalues", "--gamevalues"}, description = "Spezielle Werte des Spielservers die zum angepassten Spielen notwendig sind.\n" +
                                                                            "Momentane mögliche einstellbare Werte:  " /*+ GamevalueNames.getAllGamevalueNamesAsAString()*/)
    BotInformation.GamevalueNames gamevalueNames;

    @Option(names={"-tn", "-teamname", "--teamname"}, description = "Der Name des Botteams")
    String teamName;

    @Option(names={"-rs", "-remote-start", "--remote-start"}, description = "Verhindert das automatische Starten des Bots.\n" +
                                                                            "Die Verbindung zum Bot wird ueber die Bot-IP und " +
                                                                            "den Namen zusammen mit der RcID und VcID hergestellt.\n" +
                                                                            "Bsp.:\n 127.0.0.1/MyBot-0-0 fuer den Bot MyBot mir RcId = 0\n" +
                                                                            " und VcId = 0")
    boolean remoteStart;

    @Option(names = {"-aiarc", "-aiarchive", "--aiarchive"}, description = "Die die AI enthaltende Datei mit absolutem oder relativem Pfad")
    String aiArchive;

    @Option(names = {"-aicl", "-aiclassname", "--aiclassname"}, description = "Der Klassenname der AI")
    String aiClassname;

    @Option(names={"-aiarg", "-aiarguments", "--aiarguments"}, description = "Der AI zu übergebende Argumente")
    String[] aiArguments;

    /**
     * Processes the single Commandlineparameters. Checks if everything is correct.
     * 
     * @since 0.4
     * @param aArguments The Commandline as Stringarray
     *
     * @return If the Bot started directly or if the bot should wait for the start command
     *
     */
    static boolean parseCommandLineArguments( String[] aArguments ) {

        Core.getLogger().trace( "Parsing commandline." );

        CommandLineOptions commandLineOptions = new CommandLineOptions();


        try
        {
            CommandLine commandLine = new CommandLine(commandLineOptions)
                        .registerConverter(BotInformation.Teams.class, new TeamConverter());

            commandLine.parse(aArguments);

            if (!commandLineOptions.reconnect && commandLineOptions.botPort != -1)
                throw new CommandLine.MissingParameterException(commandLine, "Wenn der Botport gesetzt wurde, muss auch reconnect gesetzt werden!");

            commandLineOptions.parseAndShowHelp();
            commandLineOptions.setOptions();

            return commandLineOptions.remoteStart;
        }
        catch (CommandLine.ParameterException e)
        {
            Core.getLogger().error(e);
            CommandLine.usage(commandLineOptions, System.out, CommandLine.Help.Ansi.AUTO);
            System.exit( 0 );
        }
        catch (UnknownHostException e)
        {
            Core.getLogger().error(e);
        }


        Core.getInstance().close();
        return false;

    }

    /**
     *
     */
    private void parseAndShowHelp()
    {
        if (help)
        {
            CommandLine.usage(this, System.out, CommandLine.Help.Ansi.AUTO);
            return;
        }
    }

    private void setOptions() throws UnknownHostException
    {
        BotInformation botInformation = Core.getInstance().getBotinformation();

        botInformation.setAIClassname(this.aiClassname);
        botInformation.setAIArchive(this.aiArchive);
        botInformation.setAIArgs(Arrays.toString(this.aiArguments));
        botInformation.setTeamname(this.teamName);
        botInformation.setTeam(this.team);
        botInformation.setBotPort(this.botPort);
        botInformation.setServerIP(InetAddress.getByName(this.serverPortAndAddress[0]));
        botInformation.setServerPort(Integer.parseInt(this.serverPortAndAddress[1]));
        if(this.rcAndVtIdOption.length <= 1)
        {
            botInformation.setRcId(rcAndVtIdOption[0]);
            botInformation.setVtId(rcAndVtIdOption[0]);
        }
        else
        {
            botInformation.setRcId(rcAndVtIdOption[0]);
            botInformation.setVtId(rcAndVtIdOption[1]);
        }
        botInformation.setBotname(botname);

    }

}
