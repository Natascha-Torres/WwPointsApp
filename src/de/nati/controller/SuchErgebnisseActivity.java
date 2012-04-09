/**
 * @version 1.0.0.0
 * @author Natascha Torres Ripoll
 * @datum 2012-03-19 bis 2012-04-05
 */
package de.nati.controller;

import java.util.List;

import de.nati.dao.LebensmittelDatenbank;
import de.nati.model.Lebensmittel;
import de.nati.view.LebensmittelListAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class SuchErgebnisseActivity extends Activity {

	private LebensmittelDatenbank lebensmittelDatenbank;
	private List<Lebensmittel> suchErgebnisListe;
	private LebensmittelListAdapter adapter;
	
	/** Die Methode onCreate der Klasse SuchErgebnisseActivity holt beim Aufruf die Datenbank.
	 *  Wenn ein Suchbegriff übergeben wurde, wird dieser jetzt als Hint-Text für das Feld 'neuesucheFeld' gesetzt.
	 *  Mit diesem Suchbegriff wird die Methode suchen() ausgeführt.
	 *  Ein LebensmittelAdapter wird erstellt, mit dem die Suchergebnisse in einer Liste im Listenfeld ausgegeben werden. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.suche);
		// Tastatur beim Aufruf der Activity immer ausblenden
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		lebensmittelDatenbank = new LebensmittelDatenbank(this);
		// Suchbegriff, der von der WwAppActivity übergeben wurde, annehmen
		String suchBegriff = getIntent().getStringExtra("suchBegriff");
		if (suchBegriff != null) {
			EditText neueSucheFeld = (EditText) findViewById(R.id.neueSucheFeld);
			neueSucheFeld.setHint(suchBegriff);
			// Lebensmittel aus der Datenbank suchen
			suchen(suchBegriff);
		}
		// Ergebnisse in das ListenFeld schreiben
		adapter = new LebensmittelListAdapter(getBaseContext(),
				R.layout.such_ergebnis_item, suchErgebnisListe);
		ListView listView = (ListView) findViewById(R.id.suchErgebnisListe);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new LebensMittelClickListener());

		// Button
		Button neuSuchenButton = (Button) findViewById(R.id.neuSuchenButton);
		neuSuchenButton.setOnClickListener(new NeuSuchenButtonListener());
	} // End onCreate

	// Methoden
	/** Die Methode suchen wird aus onCreate() aufgerufen und sucht mit dem übergebenen 
	 *  Suchbegriff die passenden Datenbankeinträge aus der Datenbank.
	 *  Wird kein Suchbegriff eingegeben, werden alle Datenbankeinträge ausgesucht.
	 *  Wurde ein Artikel nicht gefunden, wird dem User ein Toast mit der Meldung "Artikel nicht gefunden" angezeigt */
	private void suchen(String suchBegriff) {
		suchErgebnisListe = lebensmittelDatenbank.sucheBezeichnung(suchBegriff);
		if (suchErgebnisListe.isEmpty()) {
			Toast.makeText(getBaseContext(), R.string.ArtikelNichtGefunden,
					Toast.LENGTH_SHORT).show();
		}
	}
	
	// Klassen
	/** Die Methode onItemClick der Klasse LebensMittelClickListener wird beim klicken eines Lebensmittels aus der 
	 *  angezeigten Liste aufgerufen. Die Position des Lebensmittel-Eintrages wird genutzt um die Lebensmittel-ID aus der Datenbank zu suchen,
	 *  die dann an die BerechnungActivity weitergegeben wird. */
	private class LebensMittelClickListener implements OnItemClickListener {
		// Wird ausgeführt, wenn eines der Suchergebnisse angeklickt wird:
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			
			final Lebensmittel lebensmittel = suchErgebnisListe.get(position);

			// Ausgewähltes Lebensmittel zur BerechnungActivity weiterleiten
			Intent activityIntent = new Intent(getBaseContext(), BerechnungActivity.class);
			activityIntent.putExtra("ausgewaehltesLebensmittel", lebensmittel);
			startActivity(activityIntent);
		}
	}
	
	/** Die Methode onClick der Klasse NeuSuchenButtonListener wird beim klicken des 'Neu suchen'-Buttons aufgerufen. 
	 *  InputMethodManager imm verbirgt die Tastatur beim Klicken des Buttons. Der neue Suchbegriff wird mit Hilfe der Methode suchen()
	 *  aus der Datenbank gesucht. Der adapter erstellt die neue Ergebnis-Liste. */
	private class NeuSuchenButtonListener implements OnClickListener {
		// Wird ausgeführt, wenn der "neue Suche" - Button geklickt wird:
		public void onClick(View clickedButton) {
			// Tastatur beim Click auf "suchen" verbergen
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			EditText neueSucheFeld = (EditText) findViewById(R.id.neueSucheFeld);
			imm.hideSoftInputFromWindow(neueSucheFeld.getWindowToken(), 0);
			// Lebensmittel aus der Datenbank suchen
			EditText suchFeld;
			suchFeld = (EditText) findViewById(R.id.neueSucheFeld);
			String suchBegriff = suchFeld.getText().toString().trim();
			suchen(suchBegriff);
			// Liste der gefundenen Lebensmittel erstellen und im Listenfeld
			// ausgeben
			adapter = new LebensmittelListAdapter(getBaseContext(),
					R.layout.such_ergebnis_item, suchErgebnisListe);
			ListView listView = (ListView) findViewById(R.id.suchErgebnisListe);
			listView.setAdapter(adapter);
		}
	}

} // End SuchErgebnisseActivity