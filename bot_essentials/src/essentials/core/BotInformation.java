package essentials.core;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import essentials.constants.Default;

// zu tun: final refactor comments singelton

@ThreadSafe
public class BotInformation implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = -7956774328001848340L;

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
        VelocityPerPixel( Default.VelocityPerPixel );
   
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

    public BotInformation() {

        mBotname = "DefaultBot";
        mTeam = Teams.NotSpecified;
        mTeamname = "";

        try {
            mBotIP = InetAddress.getLocalHost();
        } catch ( UnknownHostException e ) {
            e.printStackTrace();
        }
        mBotPort = -1;
        
        try {
            mServerIP = InetAddress.getLocalHost();
        } catch ( UnknownHostException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
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

        String vBotInformationString = "Bot " + mBotname + "(" + mRcId + "/" + mVtId + ")\n";
        vBotInformationString += "with Address: " + mBotIP.toString() + " Port: " + mBotPort;
        vBotInformationString += mReconnect?" (Reconnected)":"" + "\n";
        vBotInformationString += "on Team  " + mTeamname + "(" + mTeam.toString() + ")" + " Port: " + mServerPort
                + "\n";
        vBotInformationString += "on Server: " + mServerIP.toString() + "\n";
        vBotInformationString += "with Values:\n";
        for ( GamevalueNames aGamevalueName : GamevalueNames.values() ) {

            vBotInformationString += aGamevalueName.toString() + " = " + aGamevalueName.getValue() + "\n"; // ordinal siehe getter

        }
        vBotInformationString += "Die AI " + mAIClassname + " startet von " + mAIArchive + " mit den Argumenten: \n";
        vBotInformationString += mAIArgs + "\n";

        return vBotInformationString;

    }
    
}
