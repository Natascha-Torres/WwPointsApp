/**
 * @version 1.0.0.0
 * @author Natascha Torres Ripoll
 * @datum 2012-03-19 bis 2012-04-05
 */
package de.nati.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import de.nati.model.Lebensmittel;

public class BerechnungActivity extends Activity {
	
//	private LebensmittelDatenbank lebensmittelDatenbank;
	private Lebensmittel lebensmittel;
	
	/** Die Methode onCreate der Klasse BerechnungActivity bekommt die ID des ausgesuchten Lebensmittels von der
	 *  SuchErgebnisseActivity oder des Barcode-Scanners übergeben.
	 *  Alle wichtigen aten werden aus der Datenbank geholt und in die entsprechenden Textfelder als Text oder hinttext eingesetzt. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.berechnung);
        getWindow().setSoftInputMode(
        	    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        
          // Lebensmittel, das von der SuchErgebnisseActivity übergeben wurde, annehmen
        lebensmittel = getIntent().getParcelableExtra("ausgewaehltesLebensmittel");
        if (lebensmittel != null) {
	        // Artikelbezeichnung ins Textfeld eintragen
	        TextView ausgewaehltBezeichnung = (TextView) findViewById(R.id.ausgewaehltBezeichnung);
	        ausgewaehltBezeichnung.setText(lebensmittel.getBezeichnung());
	        // Artikelmenge als hinttext ins EditText-Feld eintragen
	        EditText ausgewaehltMenge = (EditText) findViewById(R.id.ausgewaehltMenge);
	        ausgewaehltMenge.setHint("" + lebensmittel.getMenge());
	        // Artikeleinheit ins Textfeld eintragen
	        TextView ausgewaehltEinheit = (TextView) findViewById(R.id.ausgewaehltEinheit);
	        ausgewaehltEinheit.setText(lebensmittel.getEinheit());
	        // Artikeleinheit ins Textfeld eintragen
	        TextView ausgewaehltPunktzahl = (TextView) findViewById(R.id.ausgewaehltPunktzahl);
	        ausgewaehltPunktzahl.setText("" + lebensmittel.getPunktzahl());
        }
        
        
        // Buttons
        Button berechnenButton = (Button) findViewById(R.id.berechnenButton);
        berechnenButton.setOnClickListener(new BerechnenButtonListener());
        
        Button zurHauptSeiteButton = (Button) findViewById(R.id.hauptSeiteButton);
        zurHauptSeiteButton.setOnClickListener(new ZurHauptSeiteButtonListener());
        
        
        
    } // End onCreate
    
    // Methoden
    /** Die Methode punktWertBerechnen der Klasse berechnet die individuelle Punktzahl mit der vom 
     *  User eingegebenen Menge und den übrigen Daten aus der Datenbank. */
    public void punkteWertBerechnen() {
    	// Auslesen der Menge
    	EditText mengeEingabeFeld; 
		mengeEingabeFeld = (EditText) findViewById(R.id.ausgewaehltMenge);
		String eingegebeneMenge = mengeEingabeFeld.getText().toString().replace(',', '.');
		try { 
		// Punktewerte aus der datenbank mit der eingegebenen Menge berechnen
			double originalMenge = lebensmittel.getMenge();
	    	double menge =  Double.valueOf(eingegebeneMenge);
	    	double punktzahlOriginal = lebensmittel.getPunktzahl();
	    	double punktzahlTemp = (punktzahlOriginal / originalMenge) * menge;
	    	int punktzahl = (int) Math.round(punktzahlTemp);
	    	TextView ausgewaehltPunktzahl = (TextView) findViewById(R.id.ausgewaehltPunktzahl);
	        ausgewaehltPunktzahl.setText("" + punktzahl);
		} catch (NumberFormatException e) {
//			Toast.makeText(getBaseContext(), R.string.ungueltigeEingabe,
//					Toast.LENGTH_SHORT).show();
		}
    }
    
    /** Die Methode zurHauptSeite leitet zur WwAppActivity weiter. */
    public void zurHauptSeite(View clickedButton) {
    	// Leitet zur Hauptseite, WwAppActivity, weiter
    	Intent activityIntent = new Intent(this, WwAppActivity.class);
    	startActivity(activityIntent);
    }
    
   
    // Klassen
    /** Die Methode onClick der Klasse BerechnenButtonListener wird beim klicken des 'berechnen'-Buttons aufgerufen. 
	 *  Von hier wird dann die Methode punktWertBerechnen aufgerufen, die die Punkte des Users berechnet. */
    private class BerechnenButtonListener implements OnClickListener {
    	// Wird ausgeführt, wenn der "berechnen" - Button geklickt wird: 
		public void onClick(View clickedButton) {		
			punkteWertBerechnen();
		}
    }
    
    /** Die Methode onClick der Klasse ZurHauptSeiteButtonListener wird beim klicken des 'Zur Hauptseite'-Buttons aufgerufen. 
	 *  Hier wird die Methode zurHauptSeite() aufgerufen. */
    private class ZurHauptSeiteButtonListener implements OnClickListener {
    	// Wird ausgeführt, wenn der "zur Hauptseite" - Button geklickt wird: 
		public void onClick(View clickedButton) {
			zurHauptSeite(clickedButton);
		}
    }
    
} // End BerechnenActivity