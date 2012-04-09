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

import com.google.zxing.client.android.Intents;

import de.nati.dao.LebensmittelDatenbank;
import de.nati.model.Lebensmittel;

public class WwAppActivity extends Activity {
	private LebensmittelDatenbank lebensmittelDatenbank;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// Tastatur beim Aufruf der Activity immer ausblenden
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		lebensmittelDatenbank = new LebensmittelDatenbank(this);

		// Buttons
		final Button suchenButton = (Button) findViewById(R.id.suchenButton);
		suchenButton.setOnClickListener(new SuchenButtonListener());

		Button zumScannerButton = (Button) findViewById(R.id.zumScannerButton);
		zumScannerButton.setOnClickListener(new ZumScannerButtonListener());
	} // End onCreate

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (requestCode == 0) {
			if (resultCode == RESULT_OK) {
				// Handle successful scan
				String barcode = intent.getStringExtra("SCAN_RESULT");
				Lebensmittel ausgewaehltesLebensmittel = lebensmittelDatenbank
						.sucheBarcode(barcode);
				Intent activityIntent = new Intent(getBaseContext(),
						BerechnungActivity.class);
				activityIntent.putExtra("ausgewaehltesLebensmittel",
						ausgewaehltesLebensmittel);
				startActivity(activityIntent);
			}
		}
	}

	// Klassen
	/**
	 * Die Methode onClick der Klasse SucheButtonListener wird beim klicken des
	 * 'Suchen'-Buttons aufgerufen. Das Suchfeld wird per id gesucht und der
	 * Variablen suchFeld zugeordnet. Leerzeichen werden mit .trim() gelöscht
	 * und der Suchbegriff an die Ziel-Activity gesendet. Die
	 * SuchErgebnisseActivity wird aufgerufen.
	 */
	private class SuchenButtonListener implements OnClickListener {
		// Beim Click auf den suche-Button ausführen:
		@Override
		public void onClick(View clickedButton) {
			// Suchbegriff auslesen, dabei die Leerzeichen entfernen
			EditText suchFeld;
			suchFeld = (EditText) findViewById(R.id.suchFeldStartseite);
			String suchBegriff = suchFeld.getText().toString().trim();
			// Weiterleiten zur SuchErgebnisseActivity, Suchbegriff wird
			// übergeben
			Intent activityIntent = new Intent(getBaseContext(),
					SuchErgebnisseActivity.class);
			activityIntent.putExtra("suchBegriff", suchBegriff);
			startActivity(activityIntent);
		}
	} // End SuchenButton

	/**
	 * Die Methode onClick der Klasse ZumScannerButtonListener wird beim klicken
	 * des 'Zum-Scanner'-Buttons aufgerufen. Der Barcode-Scanner wird
	 * aufgerufen.
	 */
	private class ZumScannerButtonListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(Intents.Scan.ACTION);
			intent.putExtra(Intents.Scan.MODE, Intents.Scan.ONE_D_MODE);
			startActivityForResult(intent, 0);
		}
	} // End ZumScannerButton

} // End wWAppActivity