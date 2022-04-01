package it.polito.tdp.lab04.model;

import java.util.List;

import it.polito.tdp.lab04.DAO.CorsoDAO;
import it.polito.tdp.lab04.DAO.StudenteDAO;

public class Model {
	
	// ATTRIBUTI
	private CorsoDAO corsoDao;
	private StudenteDAO studenteDao;
	
	// COSTRUTTORE
	public Model()
	{
		this.corsoDao = new CorsoDAO();
		this.studenteDao = new StudenteDAO();
	}
	
	// METODI
	public List<Corso> getTuttiICorsi() 
	{
		return this.corsoDao.getTuttiICorsi();
	}
	
	public Studente getStudenteByMatricola(Integer matricola)
	{
		return this.studenteDao.getStudenteByMatricola(matricola);
	}
	
	public List<Studente> getStudentiIscrittiAlCorso(Corso corso) 
	{
		return this.corsoDao.getStudentiIscrittiAlCorso(corso);
	}
	
	public Corso getCorso(String codins) 
	{
		return this.corsoDao.getCorso(codins);
	}
	
	public List<Corso> getCorsiIscrittoStudente(Integer matricola)
	{
		return this.studenteDao.getCorsiIscrittoStudente(matricola);
	}
	
	public boolean isIscritto(Integer matricola, String codins)
	{
		return this.studenteDao.isIscritto(matricola, codins);
	}
		


}
