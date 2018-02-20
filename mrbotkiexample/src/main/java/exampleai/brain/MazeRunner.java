package exampleai.brain;

import mrlib.core.MoveLib;
import mrlib.core.PositionLib;

import java.util.Random;

import essentials.communication.Action;
import essentials.communication.Position;
import essentials.communication.action_server2008.Movement;

/**
 * MazeRunner
 * KI die einen Weg durch ein Labyrinth sucht
 * @author Fynn Grandke, Hauke Richter
 *
 */
public class MazeRunner extends MazeRunnerTemplate {
	
	static int ig = Integer.MAX_VALUE, it = Integer.MAX_VALUE, ir = Integer.MAX_VALUE, il = Integer.MAX_VALUE;
    static int minimum;
    static boolean Finish = false;
    static int fueller = 9; // Wert der unerforschten Felder
	

	/**
	 * Gibt die Aktion zurück, die durchgeführt werden soll
	 */
	@Override
	public Action startWorking( Position aWorldData ){
		Action vBotAction = null;
		
		vBotAction = new Movement(new Random().nextInt(200)-100, new Random().nextInt(200)-100);
		
//		// Werte manuel anpassen, wenn Größe des Arrays verändert wird
//        int groesseX = 10, groesseY = 11;
//        // Koordinaten des Ziel manuell anpassen
//        int ZielX = 0, ZielY = 4;
//        // Rand muss mit 0 gefüllt sein außer Start und Ziel
//        // Ersatz für aWorldData
//        int[][] feld = new int[][] {
//                                    {0,1,0,0,0,0,0,0,0,0},
//                                    {0,1,0,0,0,0,1,1,1,0},
//                                    {0,1,1,0,0,0,1,0,1,0},
//                                    {0,0,1,0,0,0,1,0,1,0},
//                                    {1,1,1,1,1,1,1,1,0,0},
//                                    {0,0,1,0,0,1,0,1,0,0},
//                                    {0,0,1,0,0,0,0,1,0,0},
//                                    {0,0,0,1,1,1,1,1,0,0},
//                                    {0,0,0,1,0,1,0,1,0,0},
//                                    {0,0,0,1,0,0,0,0,1,0},
//                                    {0,0,0,0,0,0,0,0,0,0}
//                                    };
//        
//        // Position des Roboters
//        int posX, posY;
//        // Feld, was das Roboter sieht
//        int[][] feldSucher = new int[groesseY][groesseX];
//        
//        // Alle Werte auf Unerforscht setzen
//        for(int i = 0; i < groesseY; i++){
//            for(int j = 0; j < groesseX; j++){
//                System.out.print(feld[i][j] + " ");
//                feldSucher[i][j] = fueller;
//            }
//            System.out.println();
//        }
//        
//        // Startposition des Roboters
//        posX = 1;
//        posY = 0;
//        // Ground Top Right Left
//        int G = -1, T = -1, R = -1, L = -1;
//        	
//
//        
//        if( aWorldData != null ){
//            // Algorithmus starten und Winkel zurückbekommen
//            int newPos = suche( posX, posY, ZielX, ZielY, G, T, R, L, groesseX, groesseY, feldSucher, feld, 2 );
//        	
//        	// Nimm die Position des Roboters
//        	RoboterPosition robPos = aWorldData.getReferencePoint( aName );
//        	
//        	vBotAction = MoveLib.runTo( robPos + newPos) ;
//        	
//        }       
        
        
        return vBotAction;
    	
    }
	

	private static String suche(int posX, int posY, int ZielX, int ZielY, int G, int T, int R, int L, int groesseX, int groesseY, int feldSucher[][], int feld[][], int gelaufen) throws InterruptedException{
        // Neue Position wird in Grad gerechnet
		int newPos = 360;
		
        //Ziel erreicht
        if(posX == ZielX && posY == ZielY){
            Finish = true;
            System.out.println("Ziel erreicht");
            System.exit(0);
        }
        
        //Bestimmen in welche Richtung Fahrzeug fahren soll
        setMinimum();
        if(G > 0 && isMinimum(ig)){
        	// Unten
            posY++;
            newPos = -90;
        }
        else if(T > 0 && isMinimum(it)){
            // Oben
        	posY--;
        	newPos = 90;
        }
        else if(R > 0 && isMinimum(ir)){
        	// Rechts
            posX++;
            newPos = 0;
        }
        else if(L > 0 && isMinimum(il)){
        	// Links
            posX--;
            newPos = 180;
        }
        else if(G == 0 && T == 0 && L == 0 && R == 0){
            // Sackgasse
            // Erneut "starten", nur dieses mal dürfen zuletzt befahrende Wege auch wieder befahren werden
            suche(posX, posY, ZielX, ZielY, -1, -1, -1, -1, groesseX, groesseY, feldSucher, feld, gelaufen+1);
        }
        // Wegmöglichkeiten zurücksetzen
        G = 0;
        T = 0;
        R = 0;
        L = 0;
        
        // Aktuelle Position als besucht setzen
        feldSucher[posY][posX] = gelaufen;
        // Feld links vom Fahrzeug
        if(posX > 0 && feldSucher[posY][posX-1] != gelaufen){
            // Wenn Feld links unerforscht, dann erforsche es
            if(feldSucher[posY][posX-1] == fueller)
                feldSucher[posY][posX-1] = feld[posY][posX-1];
            // Wenn Feld links keine Wand, dann setze Wegmöglichkeit
            if(feldSucher[posY][posX-1] >= 1){
                L = 1;
            }
        }
        // Feld rechts vom Fahrzeug
        if(posX < (groesseX-1) && feldSucher[posY][posX+1] != gelaufen){
            // Wenn Feld rechts unerforscht, dann erforsche es
            if(feldSucher[posY][posX+1] == fueller)
                feldSucher[posY][posX+1] = feld[posY][posX+1];
            // Wenn Feld rechts keine Wand, dann setze Wegmöglichkeit
            if(feldSucher[posY][posX+1] >= 1){
                R = 1;
            }
        }
        // Feld oberhalb vom Fahrzeug
        if(posY > 0 && feldSucher[posY-1][posX] != gelaufen){
            // Wenn Feld oberhalb unerforscht, dann erforsche es
            if(feldSucher[posY-1][posX] == fueller)
                feldSucher[posY-1][posX] = feld[posY-1][posX];
            // Wenn Feld oberhalb keine Wand, dann setze Wegmöglichkeit
            if(feldSucher[posY-1][posX] >= 1){
                T = 1;
            }
        }
        // Feld unterhalb vom Fahrzeug
        if(posY < (groesseY-1) && feldSucher[posY+1][posX] != gelaufen){
            // Wenn Feld unterhalb unerforscht, dann erforsche es
            if(feldSucher[posY+1][posX] == fueller)
                feldSucher[posY+1][posX] = feld[posY+1][posX];
            // Wenn Feld unterhalb keine Wand, dann setze Wegmöglichkeit
            if(feldSucher[posY+1][posX] >= 1){
                G = 1;
            }
        }
        // Werte von den 4 umliegenden Felder setzen
        if(posY > 0)
            it = feldSucher[posY-1][posX];
        if(posY < (groesseY-1))
            ig = feldSucher[posY+1][posX];
        if(posX < (groesseX-1))
            ir = feldSucher[posY][posX+1];
        if(posX > 0)
            il = feldSucher[posY][posX-1];
        
        return "";//newPos;
    }
	
	static void setMinimum(){
        minimum = Integer.MAX_VALUE;
        if(minimum > it && it > 0)
            minimum = it;
        if(minimum > ig && ig > 0)
            minimum = ig;
        if(minimum > ir && ir > 0)
            minimum = ir;
        if(minimum > il && il > 0)
            minimum = il;
    }
	
    static boolean isMinimum(int wert){
        if(wert <= minimum)
            return true;
        else
            return false;
    }
}
