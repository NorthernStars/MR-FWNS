package essentials.core;

import java.net.InetAddress;
import java.net.UnknownHostException;

import essentials.constants.Default;

// zu tun: final refactor comments singelton

public class BotInformation {

    private String mBotname;

    private int mRcId;
    private int mVtId;

    private InetAddress mBotIP;
    private int mBotPort;
    private boolean mReconnect = false;

    private InetAddress mServerIP;
    private int mServerPort;

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

    private Teams mTeam;
    private String mTeamname;

    
    private String mAIArchive;
    private String mAIClassname;
    private String mAIArgs;
    
    public enum GamevalueNames {
        
        KickRange( Default.KickRange ), 
        MaximumKickTravelDistance( Default.MaximumKickTravelDistance ),
        Halftime( Default.Halftime ),
        BotWidth( Default.BotWidth ),
        MaxSpeed( Default.MaxSpeed ),
        MaxRate( Default.MaxRate ),
        Attrition( Default.Attrition ),
        VelocityPerPixel( Default.VelocityPerPixel );
   
        private float mValue;
        
        private GamevalueNames( float aValue ) {
            this.mValue = aValue;
        }
        
        void setValue( float aValue ){
            
            mValue = aValue;
            
        }
        
        float getValue(){
            
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

    }

    public String getBotname() {

        return mBotname;

    }

    public int getRcId() {

        return mRcId;

    }

    public int getVtId() {

        return mVtId;

    }

    public InetAddress getBotIP() {

        return mBotIP;

    }

    public int getBotPort() {

        return mBotPort;

    }

    public InetAddress getServerIP() {

        return mServerIP;

    }

    public int getServerPort() {

        return mServerPort;

    }

    public Teams getTeam() {
        
        return mTeam;

    }

    public String getTeamname() {

        return mTeamname;

    }

    public float getGamevalue( GamevalueNames aGamevalueName ) {

        return aGamevalueName.getValue();
        
    }

    public boolean getReconnect() {

        return mReconnect;
        
    }

    public String getAIArgs() {

        return mAIArgs;
        
    }

    public void setAIArgs( String aAiArgs ) {

        mAIArgs = aAiArgs;

    }
    
    public String getAIArchive() {
        
        return mAIArchive;
        
    }

    public void setAIArchive( String aAIArchive ) {
        
        mAIArchive = aAIArchive;
        
    }

    public String getAIClassname() {
        
        return mAIClassname;
        
    }

    public void setAIClassname( String aAIClassname ) {
        
        mAIClassname = aAIClassname;
        
    }
    
    public void setReconnect( boolean aReconnect) {

        mReconnect = aReconnect;

    }
    
    public void setGamevalue( GamevalueNames aGamevalueName, float aGamevalue ) {

        aGamevalueName.setValue( aGamevalue );

    }

    public void setTeamname( String aTeamname ) {

        mTeamname = aTeamname;

    }

    public void setTeam( Teams aTeam ) {

        mTeam = aTeam;

    }

    public void setServerPort( int aServerPort ) {

        mServerPort = aServerPort;

    }

    public void setServerIP( InetAddress aServerIP ) {

        mServerIP = aServerIP;

    }

    public void setBotPort( int aBotPort ) {

        mBotPort = aBotPort;

    }

    public void setBotIP( InetAddress aBotIP ) {

        mBotIP = aBotIP;

    }

    public void setVtId( int aVtId ) {

        mVtId = aVtId;

    }

    public void setRcId( int aRcId ) {

        mRcId = aRcId;

    }

    public void setBotname( String aBotname ) {

        mBotname = aBotname;

    }

    public String toString() {

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
