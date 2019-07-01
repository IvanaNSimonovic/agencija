package agencija;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminPanel {
	private String username;
	private String password;
	

	public AdminPanel(String u, String p) {
		username=u;
		password=p;
	}


	public void modifikujCene(double procenat) throws SQLException {
		Agencija.connect();
		PreparedStatement s=Agencija.connection.prepareStatement(Agencija.konstring);
		String update = "UPDATE Termin SET Cena = Cena * ?";
		s.setDouble(1, procenat);
		s.executeUpdate(update);
		s.close();
		Agencija.disconnect();

		}

	public void ukloni(int idTer) throws SQLException {
		Agencija.connect();
		PreparedStatement s=Agencija.connection.prepareStatement(Agencija.konstring);
        String delete = "DELETE FROM Termin WHERE idTer=?";
		s.setInt(1, idTer);
		s.executeUpdate(delete);
		s.close();
		Agencija.disconnect();
		}

	public void stampajOsobe(int idTerm) throws SQLException {
		Agencija.connect();
		PreparedStatement s=Agencija.connection.prepareStatement(Agencija.konstring);
		String upit = "SELECT Osoba.IdOsobe, Ime, Prezime, Kontakt, IdTer FROM Osoba, Putuje WHERE Putuje.IdOsobe = Osoba.IdOsobe AND Putuje.IdTer = ?";
		s.setInt(1, idTerm);
		ResultSet result=s.executeQuery(upit);
		while(result.next()) {
		int a = result.getInt("IdOsobe");
		String b = (result.getString("Ime"));
		String c = (result.getString("Prezime"));
		String d = (result.getString("Kontakt"));
		int e = result.getInt("IdTerm");
		System.out.println("IdOsobe:"+a+" Ime: "+b+" Prezime: "+c+" Kontakt: "+d+" IdTermina: "+e);
		}
		System.out.println("Stampanje osoba zavrseno.");
		s.close();
		Agencija.disconnect();
	}
}
