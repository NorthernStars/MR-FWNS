package essentials.communication;

/**
 * Dieses Interface muss von allen möglichen Aktionen, zB Schuss und Bewegung, implementiert werden. (Eike Petersen 03/2013)
 * 
 * @author Northern Stars
 *
 */
public interface Action {
	
	/**
	 * Diese Funktion liefert die Aktion als einen korrekten, vollständigen und vom Server verarbeitbaren XML String zurück. (Eike Petersen 11/2012)
	 * 
	 * @return String mit dem XML-Baum der Aktion
	 */
	public String getXMLString();

}
