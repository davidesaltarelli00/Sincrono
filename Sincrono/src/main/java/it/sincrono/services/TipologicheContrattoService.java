package it.sincrono.services;

import java.util.List;

import it.sincrono.entities.Ccnl;
import it.sincrono.entities.LivelloContratto;
import it.sincrono.entities.TipoAzienda;
import it.sincrono.entities.TipoContratto;
import it.sincrono.services.exceptions.ServiceException;

public interface TipologicheContrattoService {

	public List<TipoAzienda> getAziendeMap() throws ServiceException;

	public List<TipoContratto> getTipoContrattoMap() throws ServiceException;

	public List<Ccnl> getCcnlMap() throws ServiceException;

	public List<LivelloContratto> getTipoLivelliContrattualiMap() throws ServiceException;

}
