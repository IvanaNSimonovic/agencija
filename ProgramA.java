package agencija;

import java.sql.SQLException;
import java.util.List;

public class ProgramA {

	public static void main(String[] args) {
		try {
			Agencija agencija = new Agencija("ZimoTurs");
			List<Ponuda> p = agencija.pretrazi("Bugarska");
			p.get(0).zakupi("Marko", "Kraljevic", "064987678");
			p.get(0).zakupi("Jovanka", "Orleanka", "066555666");
			p.get(0).zakupi("Jug", "Bogdan", "063853657");
			AdminPanel panel = agencija.login("admin", "admin");
			panel.stampajOsobe(p.get(0).idTerm);
			} catch (SQLException e) {
			e.printStackTrace();
			}
			}
			

	}


