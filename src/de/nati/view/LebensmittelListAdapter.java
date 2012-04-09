/**
 * @version 1.0.0.0
 * @author Natascha Torres Ripoll
 * @datum 2012-03-19 bis 2012-04-05
 */
package de.nati.view;

import java.util.List;

import de.nati.controller.R;
import de.nati.model.Lebensmittel;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class LebensmittelListAdapter extends ArrayAdapter<Lebensmittel> {

	private final int RESOURCE;
	private final LayoutInflater INFLATER;
	private final List<Lebensmittel> LEBENSMITTEL_LISTE;
	
	// Konstrucktor zum Erstellen eines neuen LebensmittelListAdapters
	public LebensmittelListAdapter(Context context, int textViewResourceId,
			List<Lebensmittel> lebensmittelListe) {
		super(context, textViewResourceId, lebensmittelListe);
		RESOURCE = textViewResourceId;
		// macht aus der XML datei (RESOURCE) eine Java-Klasse
		INFLATER = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LEBENSMITTEL_LISTE = lebensmittelListe;
	}

	@Override
	public View getView(final int position, final View convertView,
			final ViewGroup parent) {

		// setup view
		View view = convertView;
		if (view == null) {
			view = INFLATER.inflate(RESOURCE, null);
		}
		// sucht die Bezeichnung und die punktzahl aus dem aus der DB ausgewählten Lebensmittel und schreibt beides  
		// in das ListenFeld
		final Lebensmittel lebensmittel = LEBENSMITTEL_LISTE.get(position);
		final TextView bezeichnung = (TextView) view.findViewById(R.id.lebensmittel_bezeichnung);
		bezeichnung.setText(lebensmittel.getBezeichnung());
		final TextView punktzahl = (TextView) view.findViewById(R.id.lebensmittel_punktzahl);
		punktzahl.setText(""+lebensmittel.getPunktzahl());
		
		return view;
	}

}
