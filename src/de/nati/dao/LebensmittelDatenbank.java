/**
 * @version 1.0.0.0
 * @author Natascha Torres Ripoll
 * @datum 2012-03-19 bis 2012-04-05
 */
package de.nati.dao;

import java.util.ArrayList;
import java.util.List;

import de.nati.model.Lebensmittel;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LebensmittelDatenbank extends SQLiteOpenHelper {
	public static final String DB_NAME = "Lebensmittel";
	protected Context context;
	 
	// Konstruktor
	public LebensmittelDatenbank(Context context) {
		super(context, DB_NAME, null, 1);
		this.context = context;
		// Lebensmittel-Datens�tze in die Datenbank schreiben
		insert(new Lebensmittel(1, "Apfel", 1, "St�ck", 0, ""));
		insert(new Lebensmittel(2, "Banane", 1, "St�ck", 0, ""));
		insert(new Lebensmittel(3, "Apfelsaft", 200, "ml", 2, ""));
		insert(new Lebensmittel(4, "Salatgurke", 1, "St�ck", 0, ""));
		insert(new Lebensmittel(5, "Br�tchen", 1, "St�ck", 4, ""));
		insert(new Lebensmittel(6, "Lachs roh", 125, "g", 7, ""));
		insert(new Lebensmittel(7, "Cola", 200, "ml", 2, "54491229"));
		insert(new Lebensmittel(8, "Mezzo Mix Zero", 200, "ml", 0, "5449000143129"));
		insert(new Lebensmittel(9, "Kaffee", 250, "ml", 0, ""));
		insert(new Lebensmittel(10, "Joghurt bis 0,5% Fett", 125, "g", 1, ""));
		insert(new Lebensmittel(11, "Joghurt bis 1,8% Fett", 125, "g", 2, ""));
		insert(new Lebensmittel(12, "Joghurt bis 3,8% Fett", 125, "g", 2, ""));
		insert(new Lebensmittel(13, "Putenfleisch roh", 120, "g", 3, ""));
		insert(new Lebensmittel(14, "Hackfleisch gemischt roh", 30, "g", 2, ""));
		insert(new Lebensmittel(15, "Milch 0,3% Fett", 250, "ml", 2, ""));
		insert(new Lebensmittel(16, "Milch 1,5% Fett", 250, "ml", 3, ""));
		insert(new Lebensmittel(17, "Milch 3,5% Fett", 250, "ml", 4, ""));
		insert(new Lebensmittel(18, "Butter", 5, "g", 1, ""));
		insert(new Lebensmittel(19, "Halbfettmargarine", 10, "g", 1, ""));
		insert(new Lebensmittel(20, "Milchspeiseeis", 50, "g", 3, ""));
		insert(new Lebensmittel(21, "D�ner", 1, "St�ck", 13, ""));
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// Datenbank anlegen, falls diese noch nicht existiert
		db.execSQL("create table if not exists " + DB_NAME + " (id Integer primary key, bezeichnung varchar, " +
    			"menge Integer, einheit varchar, punktzahl Integer, barcode varchar)");
	}
	
	/** Die Methode onUpgrade l�scht die existierende Datenbank bei einer Neuinstallation. */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Falls die Datenbank extstiert, wird diese gel�scht
		db.execSQL("drop table if exists " + DB_NAME);
		onCreate(db);
	}
	
	/** Die Methode insert bildet ein SQLite-Statement, mit dem dann Datens�tze in die Datenbank geschrieben werden k�nnen. */
	public void insert(Lebensmittel l) {
		// SQLite Statement f�r das Anlegen von Lebensmittel-Datens�tze zusammenbauen
		StringBuilder sB = new StringBuilder();
		sB.append("insert or ignore into ");
		sB.append(DB_NAME);
		if(l.getId() == 0) {
			sB.append(" (bezeichnung, menge, einheit, punktzahl, barcode) values (");
		} else {
			sB.append(" (id, bezeichnung, menge, einheit, punktzahl, barcode) values (");
			sB.append(l.getId());
			sB.append(", ");
		}
		sB.append("'");
		sB.append(l.getBezeichnung());
		sB.append("', ");
		sB.append(l.getMenge());
		sB.append(", '");
		sB.append(l.getEinheit());
		sB.append("', ");
		sB.append(l.getPunktzahl());
		sB.append(", '");
		sB.append(l.getBarcode());
		sB.append("')");
		getWritableDatabase().execSQL(sB.toString());
	}
	
	/** Die Methode sucheBezeichnung() sucht ein Lebensmittel mit Hilfe der Bezeichnung aus der Datenbank und erstellt aus
	 *  den gefundenen Datens�tzen eine Liste. */
	public List<Lebensmittel> sucheBezeichnung(String suchBegriff) {
		
		Cursor cursor = getWritableDatabase().rawQuery("SELECT * FROM " + DB_NAME + " WHERE bezeichnung like ?", 
				new String[]{"%"+suchBegriff+"%"});
		
		// Lebensmittel-Liste erstellen
		ArrayList<Lebensmittel> ergebnisListe = new ArrayList<Lebensmittel>();
		// Solange es noch weitere Eintr�ge gibt,...
		while(cursor.moveToNext()) {
			// ergebnisListe mit Daten der DB f�llen
			int id = cursor.getInt(cursor.getColumnIndex("id"));
			String bezeichnung = cursor.getString(cursor.getColumnIndex("bezeichnung"));
			int menge = cursor.getInt(cursor.getColumnIndex("menge"));
			String einheit = cursor.getString(cursor.getColumnIndex("einheit"));
			int punktzahl = cursor.getInt(cursor.getColumnIndex("punktzahl"));
			String barcode = cursor.getString(cursor.getColumnIndex("barcode"));
			
			Lebensmittel lebensmittel = new Lebensmittel(bezeichnung, menge, einheit, punktzahl, barcode);
			lebensmittel.setId(id);
			
			ergebnisListe.add(lebensmittel);
		}
		return ergebnisListe;
	}
	
	/** Die Methode sucheBarcode() sucht ein Lebensmittel mit Hilfe Barcodes aus der Datenbank und gibt ein Lebensmittel zur�ck */
	public Lebensmittel sucheBarcode(String barcode) {
		Cursor cursor = getWritableDatabase().rawQuery("SELECT * FROM " + DB_NAME + " WHERE barcode = ? ", 
				new String[]{barcode});
		
		// Solange es noch weitere Eintr�ge gibt,...
		if (cursor.moveToNext()) {
			// ergebnisListe mit Daten der DB f�llen
			int id = cursor.getInt(cursor.getColumnIndex("id"));
			String bezeichnung = cursor.getString(cursor.getColumnIndex("bezeichnung"));
			int menge = cursor.getInt(cursor.getColumnIndex("menge"));
			String einheit = cursor.getString(cursor.getColumnIndex("einheit"));
			int punktzahl = cursor.getInt(cursor.getColumnIndex("punktzahl"));
			
			Lebensmittel lebensmittel = new Lebensmittel(bezeichnung, menge, einheit, punktzahl, barcode);
			lebensmittel.setId(id);
			return lebensmittel;
		} else {
			return null;
		}
	}
	
}
