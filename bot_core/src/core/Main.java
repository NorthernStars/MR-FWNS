package core;

public class Main {
    
    public static void main( String[] aCommandline ) {
      
        Core Botcore;
        try {
            
            Botcore = Core.getInstance();
            
            Botcore.startBot( aCommandline );
            
        } catch ( Exception e ) {

            e.printStackTrace();
            
        }
       
    }
   
}
