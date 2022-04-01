package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class StudenteDAO {
	
	public Studente getStudenteByMatricola(Integer matricola)
	{
		String sql = "SELECT * "
				+ "FROM studente "
				+ "WHERE matricola = ?";
		
		Studente studente = null;
		
		try 
		{
			Connection conn = ConnectDB.getConnection();
			
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setInt(1, matricola);
			
			ResultSet rs = st.executeQuery();
			
			while(rs.next())
			{
				studente = new Studente(rs.getInt("matricola"), rs.getString("cognome"), rs.getString("nome"), rs.getString("CDS"));
				//System.out.format("%8d%25s%25s%6s", rs.getInt("matricola"), rs.getString("cognome"), rs.getString("nome"), rs.getString("CDS") );
			}
			
			rs.close();
			st.close();
			conn.close();
			
			return studente;
		}
		catch(SQLException e)
		{
			System.out.println("ERRORE nel DAO");
			e.printStackTrace();
			
			return null;
		}
	}
	

	public List<Corso> getCorsiIscrittoStudente(Integer matricola)
	{
		String sql = "SELECT c.codins, c.crediti, c.nome, c.pd "
				+ "FROM corso c, iscrizione i "
				+ "WHERE c.codins = i.codins "
				+ "AND i.matricola = ?";
		
		List<Corso> corsi = new LinkedList<Corso>();
		
		try 
		{
			Connection conn = ConnectDB.getConnection();
			
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setInt(1, matricola);
			
			ResultSet rs = st.executeQuery();
			
			while(rs.next())
			{
				String codice = rs.getString("codins");
				int numeroCrediti = rs.getInt("crediti");
				String nome = rs.getString("nome");
				int periodoDidattico = rs.getInt("pd");

				Corso corso = new Corso(codice, numeroCrediti, nome, periodoDidattico);
				
				corsi.add(corso);
			}
			
			rs.close();
			st.close();
			conn.close();
			
			return corsi;
		}
		catch(SQLException e)
		{
			System.out.println("ERRORE nel DAO");
			e.printStackTrace();
			
			return null;
		}
	}

	
	public boolean isIscritto(Integer matricola, String codins)
	{
		String sql = "SELECT * "
				+ "FROM iscrizione "
				+ "WHERE codins = ? "
				+ "AND matricola = ?";
		
		boolean iscritto = false;
		
		try 
		{
			Connection conn = ConnectDB.getConnection();
			
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setString(1, codins);
			st.setInt(2, matricola);
			
			ResultSet rs = st.executeQuery();
			
			while(rs.next())
			{
				iscritto = true;
			}
			
			rs.close();
			st.close();
			conn.close();
			
			return iscritto;
		}
		catch(SQLException e)
		{
			System.out.println("ERRORE nel DAO");
			e.printStackTrace();
			
			return false;
		}
	}
}
