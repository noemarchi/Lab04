/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.lab04;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Model;
import it.polito.tdp.lab04.model.Studente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;
    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;
    @FXML // fx:id="cmbCorso"
    private ComboBox<String> cmbCorso; // Value injected by FXMLLoader
    @FXML // fx:id="txtCognome"
    private TextField txtCognome; // Value injected by FXMLLoader
    @FXML // fx:id="txtMatricola"
    private TextField txtMatricola; // Value injected by FXMLLoader
    @FXML // fx:id="txtNome"
    private TextField txtNome; // Value injected by FXMLLoader
    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    
    private Model model;

    @FXML
    void cercaCorsiPerStudente(ActionEvent event) {
    	// 1-	Pulizia interfaccia grafica
    	this.cmbCorso.setValue(null);
    	this.txtResult.clear();
    	
    	// 2-	Acquisizione dei dati
    	String mat = this.txtMatricola.getText();
    	
    	// 3-	Controllo dei dati
    	Integer matricola;
    	
    	try 
    	{
    		matricola = Integer.parseInt(mat);
    	}
    	catch(NumberFormatException e)
    	{
    		e.printStackTrace();
    		this.txtResult.setText("ERRORE! Inserisci una matricola numerica");
    		this.txtCognome.clear();
        	this.txtMatricola.clear();
        	this.txtNome.clear();
    		return;
    	}
    	// 4-	Esecuzione dell’operazione --> chiedo al Model di farla
    	// controllo se lo studente è presente nel database
    	Studente studente = this.model.getStudenteByMatricola(matricola);
    	
    	if(studente == null)
    	{
    		this.txtResult.setText("ERRORE! Non esiste uno studente con quella matricola");
    		this.txtCognome.clear();
        	this.txtMatricola.clear();
        	this.txtNome.clear();
    		return;
    	}
    	
    	// lo studente è iscritto a qualche corso?
    	List<Corso> corsi = this.model.getCorsiIscrittoStudente(matricola);
    	
    	if(corsi.size() == 0)
    	{
    		this.txtResult.setText("ERRORE! Lo studente non è iscritto ad alcun corso");
    		this.txtCognome.clear();
        	this.txtMatricola.clear();
        	this.txtNome.clear();
    		return;
    	}
    	
    	// 5-	Visualizzazione del risultato
    	this.txtResult.setText(studente.toString() + "\n");
    	
    	for(Corso c: corsi)
    	{
    		this.txtResult.appendText(c.toString() + "\n");
    	}
    	
    	this.txtCognome.clear();
    	this.txtMatricola.clear();
    	this.txtNome.clear();
    }

    @FXML
    void cercaIscrittiCorso(ActionEvent event) {
    	// 1-	Pulizia interfaccia grafica
    	this.txtCognome.clear();
    	this.txtMatricola.clear();
    	this.txtNome.clear();
    	this.txtResult.clear();
    	
    	// 2-	Acquisizione dei dati
    	String codiceNome = this.cmbCorso.getValue();
    	
    	// 3-	Controllo dei dati
    	if(codiceNome == null || codiceNome.equals(""))
    	{
    		this.txtResult.setText("ERRORE! Seleziona un corso");
    		return;
    	}
    	
    	// 4-	Esecuzione dell’operazione --> chiedo al Model di farla
    	
    	// estraggo il codice del corso dalla stringa 	"codice nome"
    	String[] array = codiceNome.split(" ");
    	String codins = array[0];
    	
    	// il corso esiste o il corso non ha iscritti???
    	// cerco il corso. esiste o no?
    	Corso corso = this.model.getCorso(codins);
    	
    	if(corso == null)
    	{
    		this.txtResult.setText("ERRORE! Il corso selezionato non esiste");
    		return;
    	}
    	
    	// il corso esiste! estraggo la lista di iscritti
    	List<Studente> iscritti = this.model.getStudentiIscrittiAlCorso(corso);
    	
    	// il corso ha iscritti o no?
    	if(iscritti.size() == 0)
    	{
    		this.txtResult.setText("ERRORE! Il corso selezionato non ha studenti iscritti");
    		return;
    	}
    	
    	// 5-	Visualizzazione del risultato
    	this.txtResult.setText(corso.toString() + "\n");
    	
    	for(Studente studente: iscritti)
    	{
    		this.txtResult.appendText(studente.toString() + "\n");
    	}
    	
    	this.cmbCorso.setValue(null);
    }

    @FXML
    void completamentoAutomaticoStudente(ActionEvent event) {
    	
    	// 1-	Pulizia interfaccia grafica
    	this.txtCognome.clear();
    	this.txtNome.clear();
    	this.txtResult.clear();
    	
    	// 2-	Acquisizione dei dati
    	String textM = this.txtMatricola.getText();
    	Integer matricola;
    	
    	// 3-	Controllo dei dati
    	try
    	{
    		matricola = Integer.parseInt(textM);
    	}
    	catch(NumberFormatException e)
    	{
    		System.out.println("Errore formato matricola");
    		e.printStackTrace();
    		this.txtResult.setText("Inserisci una matricola in formato numerico!");
    		return;
    	}
    	// 4-	Esecuzione dell’operazione --> chiedo al Model di farla
    	Studente studente = this.model.getStudenteByMatricola(matricola);
    	
    	// 5-	Visualizzazione del risultato
    	if(studente == null)
    	{
    		this.txtResult.setText("Studente non presente! Inserisci un'altra matricola");
    		return;
    	}
    	
    	this.txtCognome.setText(studente.getCognome());
    	this.txtNome.setText(studente.getNome());
    }

    @FXML
    void iscriviStudenteCorso(ActionEvent event) {
    	// 1-	Pulizia interfaccia grafica
    	// 2-	Acquisizione dei dati
    	// 3-	Controllo dei dati
    	// 4-	Esecuzione dell’operazione  chiedo al Model di farla
    	// 5-	Visualizzazione del risultato

    }
    
    @FXML
    void isIscritto(ActionEvent event) {
    	// 1-	Pulizia interfaccia grafica
    	
    	// 2-	Acquisizione dei dati
    	String codiceNome = this.cmbCorso.getValue();
    	String mat = this.txtMatricola.getText();
    	
    	// 3-	Controllo dei dati
    	// corso
    	if(codiceNome == null || codiceNome.equals(""))
    	{
    		this.txtResult.setText("ERRORE! Seleziona un corso");
    		return;
    	}
    	
    	// studente
    	Integer matricola;
    	
    	try 
    	{
    		matricola = Integer.parseInt(mat);
    	}
    	catch(NumberFormatException e)
    	{
    		e.printStackTrace();
    		this.txtResult.setText("ERRORE! Inserisci una matricola numerica");
    		this.txtCognome.clear();
        	this.txtMatricola.clear();
        	this.txtNome.clear();
    		return;
    	}
    	
    	// 4-	Esecuzione dell’operazione --> chiedo al Model di farla
    	
    	// estraggo il codice del corso dalla stringa 	"codice nome"
    	String[] array = codiceNome.split(" ");
    	String codins = array[0];
    	
    	// il corso esiste?
    	Corso corso = this.model.getCorso(codins);
    	
    	if(corso == null)
    	{
    		this.txtResult.setText("ERRORE! Il corso selezionato non esiste");
    		return;
    	}
    	
    	// controllo se lo studente è presente nel database
    	Studente studente = this.model.getStudenteByMatricola(matricola);
    	
    	if(studente == null)
    	{
    		this.txtResult.setText("ERRORE! Non esiste uno studente con quella matricola");
    		this.txtCognome.clear();
        	this.txtMatricola.clear();
        	this.txtNome.clear();
    		return;
    	}
    	
    	// corso e studente esistono
    	// lo studente è iscritto a quel corso?
    	boolean iscritto = this.model.isIscritto(matricola, codins);
    	
    	// 5-	Visualizzazione del risultato
    	this.txtResult.clear();
    	this.txtResult.appendText(corso.toString() + "\n");
    	this.txtResult.appendText(studente.toString() + "\n");
    	
    	if(iscritto)
    	{
    		this.txtResult.appendText("Studente ISCRITTO al corso");
    	}
    	else
    	{
    		this.txtResult.appendText("Studente NON ISCRITTO al corso");
    	}
    	
    	// 6-   Pulizia interfaccia grafica
    	this.cmbCorso.setValue(null);
    	this.txtCognome.clear();
    	this.txtMatricola.clear();
    	this.txtNome.clear();
    }

    @FXML
    void resetAll(ActionEvent event) {
    	
    	this.cmbCorso.setValue(null);
    	this.txtCognome.clear();
    	this.txtMatricola.clear();
    	this.txtNome.clear();
    	this.txtResult.clear();

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert cmbCorso != null : "fx:id=\"cmbCorso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtCognome != null : "fx:id=\"txtCognome\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMatricola != null : "fx:id=\"txtMatricola\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtNome != null : "fx:id=\"txtNome\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
    }
    

    public void setModel(Model model) 
    {
    	this.model = model;
    	
    	this.cmbCorso.getItems().clear();
        
        // this.cmbCorso.getItems().add(....);
        List<Corso> tuttiCorsi = this.model.getTuttiICorsi();
        
        for(Corso c: tuttiCorsi)
        {
        	this.cmbCorso.getItems().add(c.getCodins() + " " + c.getNome());
        }
        
        this.cmbCorso.getItems().add("");
    }

}
