package agencija;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.sql.*;

public class Agencija {
	private String naziv;
	protected List<Ponuda> ponuda;
	protected static Connection connection;
	protected static String konstring="jdbc:sqlite:C:\\Agencija.db";

	public Agencija(String naziv){
		this.naziv = naziv;

	}
	public static void connect() throws SQLException {
		disconnect();
		connection = DriverManager.getConnection(konstring);
	}
	
	public static void disconnect() throws SQLException {
		if (connection != null && !connection.isClosed()) {
			connection.close();
		}
	}

	

	public List<Ponuda> pretrazi(String drzava) throws SQLException {
		List<Ponuda> l = new LinkedList<Ponuda>();
		connect();
		PreparedStatement stm = connection.prepareStatement(konstring);
		String upit = "SELECT IdTer, Naziv, Drzava, Od, Do, PreostaloMesta, Cena "
					+ "FROM Termin, Skijaliste WHERE Termin.IdSki = Skijaliste.IdSki GROUP BY Drzava "
					+ "HAVING Skijaliste.Drzava = ?";
		stm.setString(1, drzava);
		ResultSet result = stm.executeQuery(upit);

			while (result.next()) {
				Ponuda p = new Ponuda(result.getInt("IdTer"),result.getString("Naziv"), result.getString("Drzava"), result.getInt("Od"), result.getInt("Do"), result.getInt("PreostaloMesta"), result.getInt("Cena"));
				l.add(p);
			}
			stm.close();
			disconnect();
			return l;
	  }
	

	public LinkedList<Ponuda> pretrazi(int maxCena) throws SQLException {
		LinkedList<Ponuda> l = new LinkedList<Ponuda>();
		connect();
		PreparedStatement stm = connection.prepareStatement(konstring); 
    	String upit = "SELECT IdTer, Naziv, Drzava, Od, Do, PreostaloMesta, Cena FROM Skijaliste, Termin WHERE Cena < ?";
    	stm.setInt(1, maxCena);
		ResultSet result = stm.executeQuery(upit);

			while (result.next()) {
				Ponuda p = new Ponuda(result.getInt("IdTer"),result.getString("Naziv"), result.getString("Drzava"), result.getInt("Od"), result.getInt("Do"), result.getInt("PreostaloMesta"), result.getInt("Cena"));
				l.add(p);
				}
			stm.close();
			disconnect();
			return l;

		}

	

	public AdminPanel login(String username, String password) throws SQLException {
		if (username == "admin" && password == "admin") {
			AdminPanel a = new AdminPanel("admin", "admin"); 
			return a;
				}
		else return null;
	}
	
	public String getNaziv() {
		return naziv;
	}
	
	public KorisnickiPanel loginKor(String username, String password) throws SQLException {
		connect();
		PreparedStatement stm = connection.prepareStatement(konstring); 
    	String upit =("SELECT idOsoba FROM Korisnik WHERE KorIme = ? AND Lozinka = ?");
    	stm.setString(1, username);
    	stm.setString(2, password);
    	ResultSet r =stm.executeQuery();
    	KorisnickiPanel k= new KorisnickiPanel(r.getInt("idOsoba"));
    	disconnect();
    	return k;
	}
	
}
