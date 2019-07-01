package agencija;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class KorisnickiPanel {

	private int idKor;

	public KorisnickiPanel(int idOsobe) {
		idKor = idOsobe;
	}

	public LinkedList<Ponuda> mojaPutovanja() throws SQLException {
		LinkedList<Ponuda> p = new LinkedList<>();
		Agencija.connect();
		PreparedStatement stm = Agencija.connection.prepareStatement(Agencija.konstring);
		String upit = ("SELECT t.idter, s.naziv, s.drzava, t.datumOd, t.datumDo, t.preostaloMesta, t.cena FROM Termin t, Skijaliste s, Putuje.p WHERE s.idSki=t.idSki AND t.idTer=p.idTer AND p.idOsobe=?");
		stm.setInt(1, this.idKor);
		ResultSet r = stm.executeQuery(upit);
		while (r.next()) {
			p.add(new Ponuda(r.getInt("idTer"), r.getString("naziv"), r.getString("drzava"), r.getInt("datumOd"),
					r.getInt("datumDo"), r.getInt("preostaloMesta"), r.getInt("cena")));
		}
		stm.close();
		Agencija.disconnect();
		return p;

	}

	public void ukloni(Ponuda p) throws SQLException {
		Agencija.connect();
		PreparedStatement stm = Agencija.connection.prepareStatement(Agencija.konstring);
		String del = ("DELETE FROM Putuje WHERE idOsobe = ? AND idTer = ?");
		stm.setInt(1, this.idKor);
		stm.setInt(2, p.getIdTerm());
		stm.executeUpdate(del);
		stm.close();
		Agencija.disconnect();
	}

}
