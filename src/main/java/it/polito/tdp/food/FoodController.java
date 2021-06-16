/**
 * Sample Skeleton for 'Food.fxml' Controller Class
 */

package it.polito.tdp.food;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.food.model.Adiacenza;
import it.polito.tdp.food.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FoodController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtCalorie"
    private TextField txtCalorie; // Value injected by FXMLLoader

    @FXML // fx:id="txtPassi"
    private TextField txtPassi; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalisi"
    private Button btnAnalisi; // Value injected by FXMLLoader

    @FXML // fx:id="btnCorrelate"
    private Button btnCorrelate; // Value injected by FXMLLoader

    @FXML // fx:id="btnCammino"
    private Button btnCammino; // Value injected by FXMLLoader

    @FXML // fx:id="boxPorzioni"
    private ComboBox<String> boxPorzioni; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCammino(ActionEvent event) {
    	txtResult.clear();
    	String categoria=this.boxPorzioni.getValue();
    	String Ns=this.txtPassi.getText();
    	Integer N=0;
    	try {
    		N=Integer.parseInt(Ns);
    	}catch(NumberFormatException e)
    	{
    		e.printStackTrace();
    	}
    	List<String> camminoBest=new ArrayList<String>(this.model.camminoBest(categoria, N));
    	txtResult.appendText("IL CAMMINO MASSIMO E' DATO DA:\n");
    	for(String s:camminoBest)
    	{
    		txtResult.appendText(s+"\n");
    	}
    	txtResult.appendText("IL PESO TOTALE E': "+this.model.getpeso(camminoBest));
    }

    @FXML
    void doCorrelate(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Cerco porzioni correlate...\n");
    	String categoria=this.boxPorzioni.getValue();
    	List<Adiacenza> vicini=new ArrayList<Adiacenza>(this.model.correlate(categoria));
    	txtResult.appendText("LE PORZIONI CORRELATE A "+categoria+" SONO:\n");
    	for(Adiacenza a:vicini)
    	{
    		txtResult.appendText(a.getTipo2()+" - "+a.getPeso()+"\n");
    	}
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Creazione grafo...");
    	String Cs=this.txtCalorie.getText();
    	Integer C=0;
    	try {
    		C=Integer.parseInt(Cs);
    	}catch(NumberFormatException e)
    	{
    		e.printStackTrace();
    	}
    	this.model.creaGrafo(C);
    	this.txtResult.appendText("Grafo creato!!\n");
    	this.txtResult.appendText("# archi: "+this.model.getArchi()+"\n");
    	this.txtResult.appendText("# vertici: "+this.model.getVertici()+"\n");
    	this.boxPorzioni.getItems().addAll(this.model.tipiPorzioni(C));
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtCalorie != null : "fx:id=\"txtCalorie\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtPassi != null : "fx:id=\"txtPassi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCorrelate != null : "fx:id=\"btnCorrelate\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCammino != null : "fx:id=\"btnCammino\" was not injected: check your FXML file 'Food.fxml'.";
        assert boxPorzioni != null : "fx:id=\"boxPorzioni\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Food.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
