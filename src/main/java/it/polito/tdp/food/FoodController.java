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
    	String partenza=this.boxPorzioni.getValue();
    	String NS=this.txtPassi.getText();
    	Integer N=0;
    	try {
    		N=Integer.parseInt(NS);
    	}catch(NumberFormatException e)
    	{
    		e.printStackTrace();
    	}
    	if(partenza==null || NS==null)
    	{
    		this.txtResult.appendText("inserire un valore!\n");
    	}else
    	{
    		List<String> best=new ArrayList<String>(this.model.best(partenza, N));
    		for(String s:best)
    		{
    			txtResult.appendText(s+"\n");
    		}
    	}
    }

    @FXML
    void doCorrelate(ActionEvent event) {
    	txtResult.clear();
    	String porzione=this.boxPorzioni.getValue();
    	if(porzione==null)
    	{
    		txtResult.appendText("scegliere un valore");
    	}else
    	{
    		List<Adiacenza> corr=new ArrayList<Adiacenza>(this.model.correlate(porzione));
    		txtResult.appendText("i vertici correlati a "+porzione+" sono:\n");
    		for(Adiacenza a:corr)
    		{
    			txtResult.appendText(a.getS2()+" - "+a.getPeso()+"\n");
    		}
    	}
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Creazione grafo...");
    	String calorieS=this.txtCalorie.getText();
    	Integer calorie=0;
    	try {
    		calorie=Integer.parseInt(calorieS);
    	}catch(NumberFormatException e)
    	{
    		e.printStackTrace();
    	}
    	if(calorieS==null)
    	{
    		txtResult.appendText("inserire un valore\n");
    	}else
    	{
    		this.model.creaGrafo(calorie);
    		txtResult.appendText("grafo creato!\n");
    		txtResult.appendText("# archi: "+this.model.getArco()+"\n");
    		txtResult.appendText("# vertici: "+this.model.getVertici()+"\n");
    		this.boxPorzioni.getItems().addAll(this.model.vertici());
    	}
    	
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
