package agencija;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Ponuda {
	protected int idTerm;
	private String naziv;
	private String drzava;
	private int datumOd;
	private int datumDo;
	private int preostaloMesta;
	private int cena;
	
	public Ponuda(int i, String n, String d, int o, int dd, int prM, int c) {
		idTerm=i;
		naziv=n;
		drzava=d;
		datumOd=o;
		datumDo=dd;
		preostaloMesta=prM;
		cena=c;	
	}

	public int getPreostaloMesta() {
		return preostaloMesta;
	}

	public int getCena() {
		return cena;
	}

	public void setIdTer(int idTer) {
		this.idTerm = idTer;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public void setDrzava(String drzava) {
		this.drzava = drzava;
	}

	public void setDatumOd(int datumOd) {
		this.datumOd = datumOd;
	}

	protected int getIdTerm() {
		return idTerm;
	}

	public void setDatumDo(int datumDo) {
		this.datumDo = datumDo;
	}

	public void setPreostaloMesta(int preostaloMesta) {
		this.preostaloMesta = preostaloMesta;
	}

	public void setCena(int cena) {
		this.cena = cena;
	}

	public String toString() {
		String s = "(" + idTerm + ")" + naziv + "(" + drzava + ")" + datumOd + " - " + datumDo + " /" + cena + " :"
				+ preostaloMesta + " .";
		return s;
	}

	public boolean zakupi(String ime, String prezime, String kontakt) throws SQLException {
		if(this.preostaloMesta>0) {
		Agencija.connect();
		PreparedStatement s=Agencija.connection.prepareStatement(Agencija.konstring);
		String update = "UPDATE Termin SET PreostaloMesta = PreostaloMesta-1 WHERE idTer =?";
		s.setInt(1, this.idTerm);
		s.executeUpdate(update);
		
		update= "INSERT INTO Osoba(Ime, Prezime, Kontakt) VALUES (?, ?, ?)";
		s.setString(1, ime);
		s.setString(2, prezime);
		s.setString(3, kontakt);
		s.executeUpdate(update);
		
		update= "SELECT IdOsobe FROM Osoba WHERE Ime = ? AND Prezime = ? AND Kontakt = ?";
		s.setString(1, ime);
		s.setString(2, prezime);
		s.setString(3, kontakt);
		ResultSet result= s.executeQuery(update);
		update= "INSERT INTO Putuje (idOsobe, idTer) VALUES (?, ?)";
		s.setInt(1, Integer.parseInt(result.toString()));
		s.setInt(2, this.getIdTerm());
		s.executeUpdate(update);
		System.out.println("Uspesan zakup termina.");
		s.close();
		Agencija.disconnect();
		return true;
		}
		else
			System.out.println("Nema slobodnih termina.");
		return false;
	}
	
	public boolean zakupi(int idOsoba) throws SQLException {
		if (this.preostaloMesta > 0) {
			PreparedStatement s=Agencija.connection.prepareStatement(Agencija.konstring);
			String update = "UPDATE Termin SET PreostaloMesta = PreostaloMesta-1 WHERE idTer =?";
			s.setInt(1, this.getIdTerm());
			s.executeUpdate(update);
			update= "INSERT INTO Putuje (idOsobe, idTer) VALUES (?, ?)";
			s.setInt(1, idOsoba);
			s.setInt(2, this.getIdTerm());
			s.executeUpdate(update);
			System.out.println("Uspesan zakup termina.");
			s.close();
			Agencija.disconnect();
			return true;
		}
		else
			System.out.println("Nema slobodnih termina.");
		return false;
	}
	
}
