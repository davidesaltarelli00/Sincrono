package it.sincrono.services.impl;

import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.sincrono.repositories.ContrattoRepository;
import it.sincrono.repositories.dto.OrganicoDto;
import it.sincrono.services.ContrattoService;
import it.sincrono.services.costants.ServiceMessages;
import it.sincrono.services.exceptions.ServiceException;

@Service
public class ContrattoServiceImpl implements ContrattoService {
	private static final Logger LOGGER = LogManager.getLogger(ContrattoServiceImpl.class);

	@Autowired
	private ContrattoRepository contrattoRepository;

//	@Override
//	public List<Contratto> listContratto() throws ServiceException {
//		List<Contratto> contratti = contrattoRepository.findAll();
//		return contratti;
//	}
//
//	@Override
//	public Contratto getContrattoById(Integer id) {
//		Contratto contratto = contrattoRepository.findById(id).get();
//		if (!contratto.equals(null)) {
//			return contratto;
//		}
//		return null;
//	}
//
//	@Override
//	public void insert(Contratto contratto) {
//		contrattoRepository.saveAndFlush(contratto);
//	}

//	@Override
//	public void update(Contratto contratto) throws ServiceException {
//
//		try {
//			Contratto currentContratto = contrattoRepository.findById(contratto.getId()).get();
//
//			currentContratto.setAttivo(false);
//			contrattoRepository.saveAndFlush(currentContratto);
//
//		} catch (NoSuchElementException ne) {
//			throw new ServiceException(ServiceMessages.RECORD_NON_TROVATO);
//		} catch (DataIntegrityViolationException de) {
//			throw new ServiceException(ServiceMessages.ERRORE_INTEGRITA_DATI);
//		} catch (Exception e) {
//			throw new ServiceException(ServiceMessages.ERRORE_GENERICO);
//		}
//	}
//
//	@Override
//	public void delete(Integer id) throws ServiceException {
//
//		try {
//			Contratto contratto = contrattoRepository.findById(id).get();
//
//			contrattoRepository.delete(contratto);
//			contrattoRepository.flush();
//
//		} catch (NoSuchElementException ne) {
//			throw new ServiceException(ServiceMessages.RECORD_NON_TROVATO);
//		} catch (DataIntegrityViolationException de) {
//			throw new ServiceException(ServiceMessages.ERRORE_INTEGRITA_DATI);
//		} catch (Exception e) {
//			throw new ServiceException(ServiceMessages.ERRORE_GENERICO);
//		}
//	}

	@Override
	public List<OrganicoDto> organico() throws ServiceException {
		List<OrganicoDto> organico;
		try {
			organico = contrattoRepository.organico();
		} catch (Exception e) {
			LOGGER.log(Level.ERROR, e.getMessage());
			throw new ServiceException(ServiceMessages.ERRORE_GENERICO);
		}
		return organico;
	}

}