package essentials.core;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import essentials.constants.Default;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import picocli.CommandLine;
import picocli.CommandLine.ITypeConverter;

// TODO: final refactor comments singelton


/**
 * Speichert alle wichtigen Daten des Bots.
 * Enthaelt zusaetzlich ein Objekt-"Speicherplatz" um die Kommunikation zwischen AIs zu erleichtern
 * Das Serialisieren ignoriert den Objekt-"Speicherplatz".
 *
 * Beachte das die Serialisation ueber einen SerialisationsProxy geschieht, der bei Aenderungen an der
 * Ursprungsklasse auch angepasst werden muss.
 *
 * @author Eike Petersen
 * @since 0.1
 * @version 0.9
 *
 */
@ThreadSafe
public class BotInformation implements Serializable{

    private static Logger sBOTINFORMATIONLOGGER = LogManager.getLogger("BOTINFORMATION");

    public static synchronized Logger getLogger(){

        return sBOTINFORMATIONLOGGER;

    }

    static synchronized void setLogger( Logger aLogger ){

        sBOTINFORMATIONLOGGER = aLogger;

    }

    @GuardedBy("this") private String mBotname;

    @GuardedBy("this") private int mRcId;
    @GuardedBy("this") private int mVtId;

    @GuardedBy("this") private InetAddress mBotIP;
    @GuardedBy("this") private int mBotPort;
    @GuardedBy("this") private boolean mReconnect = false;

    @GuardedBy("this") private InetAddress mServerIP;
    @GuardedBy("this") private int mServerPort;

    public enum Teams {
        
        Yellow, Blue, NotSpecified;
        
        public static String[] getTeamsAsStringArray() {

            String[] vTeamsStringArray = new String[Teams.values().length];
            int vPosition = 0;

            for ( Teams vTeams : Teams.values() ) {

                vTeamsStringArray[vPosition++] = vTeams.toString();

            }

            return vTeamsStringArray;

        }
        
    }

    @GuardedBy("this") private Teams mTeam;
    @GuardedBy("this") private String mTeamname;


    @GuardedBy("this") private String mAIArchive;
    @GuardedBy("this") private String mAIClassname;
    @GuardedBy("this") private String mAIArgs;

    @GuardedBy("this") private Object mBotMemory;

    public enum GamevalueNames {

        KickRange( Default.KickRange ),
        MaximumKickTravelDistance( Default.MaximumKickTravelDistance ),
        Halftime( Default.Halftime ),
        BotWidth( Default.BotWidth ),
        MaxSpeed( Default.MaxSpeed ),
        MaxRate( Default.MaxRate ),
        Attrition( Default.Attrition ),
        VelocityPerPixel( Default.VelocityPerPixel ),
        MaxFieldLength(Default.MaxFieldLength);

        @GuardedBy("this") private float mValue;

        private GamevalueNames( float aValue ) {
            this.mValue = aValue;
        }

        public synchronized void setValue( float aValue ){

            mValue = aValue;

        }

        public synchronized float getValue(){

            return mValue;

        }

        public static String getAllGamevalueNamesAsAString() {

            String vGamevalueNamesString = "";

            for ( GamevalueNames vGamevalueName : GamevalueNames.values() ) {

                vGamevalueNamesString += vGamevalueName.toString() + " ";

            }

            return vGamevalueNamesString;

        }

        public static String[] getGamevalueNamesAsStringArray() {

            String[] vGamevalueNamesString = new String[GamevalueNames.values().length];
            int vPosition = 0;

            for ( GamevalueNames vGamevalueName : GamevalueNames.values() ) {

                vGamevalueNamesString[vPosition++] = vGamevalueName.toString();

            }

            return vGamevalueNamesString;

        }
    }

    public BotInformation(){

        mBotname = "DefaultBot";
        mTeam = Teams.NotSpecified;
        mTeamname = "";

        try {
            mBotIP = InetAddress.getLocalHost();
        } catch ( UnknownHostException vException ) {
            getLogger().error( "Error in BotInformation.BotInformation()", vException );
        }
        mBotPort = -1;

        try {
            mServerIP = InetAddress.getLocalHost();
        } catch ( UnknownHostException vException ) {
            getLogger().error( "Error in BotInformation.BotInformation()", vException );
        }

        mAIArchive = "";
        mAIClassname = "";
        mAIArgs = "";

        mBotMemory = null;

    }

    public synchronized String getBotname() {

        return mBotname;

    }

    public synchronized int getRcId() {

        return mRcId;

    }

    public synchronized int getVtId() {

        return mVtId;

    }

    public synchronized InetAddress getBotIP() {

        return mBotIP;

    }

    public synchronized int getBotPort() {

        return mBotPort;

    }

    public synchronized InetAddress getServerIP() {

        return mServerIP;

    }

    public synchronized int getServerPort() {

        return mServerPort;

    }

    public synchronized Teams getTeam() {

        return mTeam;

    }

    public synchronized String getTeamname() {

        return mTeamname;

    }

    public synchronized float getGamevalue( GamevalueNames aGamevalueName ) {

        return aGamevalueName.getValue();

    }

    public synchronized boolean getReconnect() {

        return mReconnect;

    }

    public synchronized String getAIArgs() {

        return mAIArgs;

    }

    public synchronized void setAIArgs( String aAiArgs ) {

        mAIArgs = aAiArgs;

    }

    public synchronized String getAIArchive() {

        return mAIArchive;

    }

    public synchronized void setAIArchive( String aAIArchive ) {

        mAIArchive = aAIArchive;

    }

    public synchronized String getAIClassname() {

        return mAIClassname;

    }

    public synchronized Object getBotMemory() {

        return mBotMemory;

    }

    public synchronized void setBotMemory( Object aBotMemory ) {

        mBotMemory = aBotMemory;

    }

    public synchronized void setAIClassname( String aAIClassname ) {

        mAIClassname = aAIClassname;

    }

    public synchronized void setReconnect( boolean aReconnect) {

        mReconnect = aReconnect;

    }

    public synchronized void setGamevalue( GamevalueNames aGamevalueName, float aGamevalue ) {

        aGamevalueName.setValue( aGamevalue );

    }

    public synchronized void setTeamname( String aTeamname ) {

        mTeamname = aTeamname;

    }

    public synchronized void setTeam( Teams aTeam ) {

        mTeam = aTeam;

    }

    public synchronized void setServerPort( int aServerPort ) {

        mServerPort = aServerPort;

    }

    public synchronized void setServerIP( InetAddress aServerIP ) {

        mServerIP = aServerIP;

    }

    public synchronized void setBotPort( int aBotPort ) {

        mBotPort = aBotPort;

    }

    public synchronized void setBotIP( InetAddress aBotIP ) {

        mBotIP = aBotIP;

    }

    public synchronized void setVtId( int aVtId ) {

        mVtId = aVtId;

    }

    public synchronized void setRcId( int aRcId ) {

        mRcId = aRcId;

    }

    public synchronized void setBotname( String aBotname ) {

        mBotname = aBotname;

    }

    public synchronized String toString() {

        String vBotInformationString = "Bot " + getBotname() + "(" + getRcId() + "/" + getVtId() + ")\n";
        vBotInformationString += "with Address: " + getBotIP().toString() + " Port: " + getBotPort();
        vBotInformationString += getReconnect()?" (Reconnected)":"" + "\n";
        vBotInformationString += "on Team  " + getTeamname() + "(" + getTeam().toString() + ")" + " Port: " + getServerPort()
                + "\n";
        vBotInformationString += "on Server: " + getServerIP().toString() + "\n";
        vBotInformationString += "with Values:\n";
        for ( GamevalueNames aGamevalueName : GamevalueNames.values() ) {

            vBotInformationString += aGamevalueName.toString() + " = " + aGamevalueName.getValue() + "\n"; // ordinal siehe getter

        }
        vBotInformationString += "The AI " + getAIClassname() + " starts from " + getAIArchive() + " with parameters: \n";
        vBotInformationString += getAIArgs() + "\n";
        vBotInformationString += getBotMemory()!=null?getBotMemory().toString() + "\n":"";

        return vBotInformationString;

    }

    /**
     *  Serialisation
     */
    private static final long serialVersionUID = -7956774328001848340L;

    private Object writeReplace() {

        return new BotInformationSerialisationProxy(this);

    }

    private void readObject( ObjectInputStream aStream ) throws InvalidObjectException{

        throw new InvalidObjectException( " Need a Proxy for Serialisation" );

    }

    private static class BotInformationSerialisationProxy implements Serializable {

        private static final long serialVersionUID = -2371276572775298909L;

        private final String mBotname;

        private final int mRcId;
        private final int mVtId;

        private final InetAddress mBotIP;
        private final int mBotPort;
        private final boolean mReconnect;

        private final InetAddress mServerIP;
        private final int mServerPort;

        private final Teams mTeam;
        private final String mTeamname;

        private final String mAIArchive;
        private final String mAIClassname;
        private final String mAIArgs;

        //No! private Object mBotMemory;

        BotInformationSerialisationProxy( BotInformation aBotInformation ) {

            mBotname = aBotInformation.mBotname;
            mRcId = aBotInformation.mRcId;
            mVtId = aBotInformation.mVtId;
            mBotIP = aBotInformation.mBotIP;
            mBotPort = aBotInformation.mBotPort;
            mReconnect = aBotInformation.mReconnect;
            mServerIP = aBotInformation.mServerIP;
            mServerPort = aBotInformation.mServerPort;
            mTeam = aBotInformation.mTeam;
            mTeamname = aBotInformation.mTeamname;
            mAIArchive = aBotInformation.mAIArchive;
            mAIClassname = aBotInformation.mAIClassname;
            mAIArgs = aBotInformation.mAIArgs;

        }

        private Object readResolve(){

            BotInformation vBotInformation = new BotInformation();

            vBotInformation.mBotname = mBotname;
            vBotInformation.mRcId = mRcId;
            vBotInformation.mVtId = mVtId;
            vBotInformation.mBotIP = mBotIP;
            vBotInformation.mBotPort = mBotPort;
            vBotInformation.mReconnect = mReconnect;
            vBotInformation.mServerIP = mServerIP;
            vBotInformation.mServerPort = mServerPort;
            vBotInformation.mTeam = mTeam;
            vBotInformation.mTeamname = mTeamname;
            vBotInformation.mAIArchive = mAIArchive;
            vBotInformation.mAIClassname = mAIClassname;
            vBotInformation.mAIArgs = mAIArgs;

            return vBotInformation;

        }

    }

}
