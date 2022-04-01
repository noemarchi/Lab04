package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class CorsoDAO {
	
	/*
	 * Ottengo tutti i corsi salvati nel Db
	 */
	public List<Corso> getTuttiICorsi() 
	{

		final String sql = "SELECT * FROM corso";

		List<Corso> corsi = new LinkedList<Corso>();

		try 
		{
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) 
			{

				String codins = rs.getString("codins");
				int numeroCrediti = rs.getInt("crediti");
				String nome = rs.getString("nome");
				int periodoDidattico = rs.getInt("pd");

				// System.out.println(codins + " " + numeroCrediti + " " + nome + " " + periodoDidattico);
				
				// Crea un nuovo JAVA Bean Corso
				// Aggiungi il nuovo oggetto Corso alla lista corsi
				Corso corso = new Corso(codins, numeroCrediti, nome, periodoDidattico);
				corsi.add(corso);
			}

			rs.close();
			st.close();
			conn.close();
			
			return corsi;
			

		} 
		catch (SQLException e) 
		{
			// e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
	}
	
	
	/*
	 * Dato un codice insegnamento, ottengo il corso
	 */
	public Corso getCorso(String codins) {
		
		String sql = "SELECT * "
				+ "FROM corso "
				+ "WHERE codins = ?";
		
		Corso corso = null;
		
		try 
		{
			Connection conn = ConnectDB.getConnection();
			
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setString(1, codins);
			
			ResultSet rs = st.executeQuery();
			
			while(rs.next())
			{
				String codice = rs.getString("codins");
				int numeroCrediti = rs.getInt("crediti");
				String nome = rs.getString("nome");
				int periodoDidattico = rs.getInt("pd");

				corso = new Corso(codice, numeroCrediti, nome, periodoDidattico);
			}
			
			rs.close();
			st.close();
			conn.close();
			
			return corso;
		}
		catch(SQLException e)
		{
			System.out.println("ERRORE nel DAO");
			e.printStackTrace();
			
			return null;
		}
	}

	/*
	 * Ottengo tutti gli studenti iscritti al Corso
	 */
	public List<Studente> getStudentiIscrittiAlCorso(Corso corso) {
		
		String sql = "SELECT s.matricola, s.cognome, s.nome, s.CDS "
				+ "FROM studente s, iscrizione i "
				+ "WHERE s.matricola = i.matricola "
				+ "AND i.codins = ?";
		
		List<Studente> result = new ArrayList<Studente>();
		
		try 
		{
			Connection conn = ConnectDB.getConnection();
			
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setString(1, corso.getCodins());
			
			ResultSet rs = st.executeQuery();
			
			while(rs.next())
			{
				Studente s = new Studente(rs.getInt("matricola"), rs.getString("cognome"), rs.getString("nome"), rs.getString("CDS"));
				
				result.add(s);
			}
			
			rs.close();
			st.close();
			conn.close();
			
			return result;
		}
		catch(SQLException e)
		{
			System.out.println("ERRORE nel DAO");
			e.printStackTrace();
			
			return null;
		}
	}

	/*
	 * Data una matricola ed il codice insegnamento, iscrivi lo studente al corso.
	 */
	public boolean inscriviStudenteACorso(Studente studente, Corso corso) {
		// TODO
		// ritorna true se l'iscrizione e' avvenuta con successo
		return false;
	}

}
