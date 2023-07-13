package it.sincrono.services;

import java.util.List;

import it.sincrono.entities.Profilo;
import it.sincrono.entities.Utente;
import it.sincrono.services.exceptions.ServiceException;

public interface UtenteService {

	public List<Utente> list() throws ServiceException;

	public Utente getById(Integer ID) throws ServiceException;

	public void updateUtente(Utente utente) throws ServiceException;

}