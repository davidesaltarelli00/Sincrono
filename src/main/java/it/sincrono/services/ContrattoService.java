package it.sincrono.services;

import java.util.List;

import it.sincrono.repositories.dto.OrganicoDto;
import it.sincrono.services.exceptions.ServiceException;

public interface ContrattoService {

	// List<Contratto> listContratto() throws ServiceException;

	// public Contratto getContrattoById(Integer id) throws ServiceException;

	// public void insert(Contratto contratto) throws ServiceException;

	// public void update(Contratto contratto) throws ServiceException;

	// public void delete(Integer id) throws ServiceException;

	public List<OrganicoDto> organico() throws ServiceException;
}
