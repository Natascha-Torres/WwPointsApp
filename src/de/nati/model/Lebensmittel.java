/**
 * @version 1.0.0.0
 * @author Natascha Torres Ripoll
 * @datum 2012-03-19 bis 2012-04-05
 */
package de.nati.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Lebensmittel implements Parcelable {
	private int id;
	private String bezeichnung;
	private int menge;
	private String einheit;
	private int punktzahl;
	private String barcode;
	
	public static final Parcelable.Creator<Lebensmittel> CREATOR = new Parcelable.Creator<Lebensmittel>() {

        @Override
        public Lebensmittel createFromParcel(Parcel source) {
            return new Lebensmittel(source);
        }

        @Override
        public Lebensmittel[] newArray(int size) {
            return new Lebensmittel[size];
        }
    };

	
	// Konstruktoren
	public Lebensmittel(String bezeichnung, int menge, String einheit,
			int punktzahl, String barcode) {
		super();
		this.id = 0;
		this.bezeichnung = bezeichnung;
		this.menge = menge;
		this.einheit = einheit;
		this.punktzahl = punktzahl;
		this.barcode = barcode;
	}
	
	public Lebensmittel(int id, String bezeichnung, int menge, String einheit,
			int punktzahl, String barcode) {
		super();
		this.id = id;
		this.bezeichnung = bezeichnung;
		this.menge = menge;
		this.einheit = einheit;
		this.punktzahl = punktzahl;
		this.barcode = barcode;
	}
	
	public Lebensmittel(Parcel source) {
		super();
		this.id = source.readInt();
		this.bezeichnung = source.readString();
		this.menge = source.readInt();
		this.einheit = source.readString();
		this.punktzahl = source.readInt();
		this.barcode = source.readString();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Lebensmittel [id=");
		builder.append(id);
		builder.append(", bezeichnung=");
		builder.append(bezeichnung);
		builder.append(", menge=");
		builder.append(menge);
		builder.append(", einheit=");
		builder.append(einheit);
		builder.append(", punktzahl=");
		builder.append(punktzahl);
		builder.append(", barcode=");
		builder.append(barcode);
		builder.append("]");
		return builder.toString();
	}
	
	// get()- und set()- Methoden
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBezeichnung() {
		return bezeichnung;
	}

	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}

	public int getMenge() {
		return menge;
	}

	public void setMenge(int menge) {
		this.menge = menge;
	}

	public String getEinheit() {
		return einheit;
	}

	public void setEinheit(String einheit) {
		this.einheit = einheit;
	}

	public int getPunktzahl() {
		return punktzahl;
	}

	public void setPunktzahl(int punktzahl) {
		this.punktzahl = punktzahl;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel source, int flags) {
		source.writeInt(id);
		source.writeString(bezeichnung);
		source.writeInt(menge);
		source.writeString(einheit);
		source.writeInt(punktzahl);
		source.writeString(barcode);
	}

}
