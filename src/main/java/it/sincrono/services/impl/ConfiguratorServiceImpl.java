package it.sincrono.services.impl;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;

import it.sincrono.entities.Anagrafica;
import it.sincrono.entities.Commessa;
import it.sincrono.entities.Configurator;
import it.sincrono.entities.Contratto;
import it.sincrono.entities.Profilo;
import it.sincrono.entities.Ruolo;
import it.sincrono.entities.StoricoCommesse;
import it.sincrono.entities.StoricoContratti;
import it.sincrono.entities.TipoCcnl;
import it.sincrono.entities.TipoLivelloContratto;
import it.sincrono.entities.Utente;
import it.sincrono.repositories.AnagraficaRepository;
import it.sincrono.repositories.CommessaRepository;
import it.sincrono.repositories.ConfiguratorRepository;
import it.sincrono.repositories.ContrattoRepository;
import it.sincrono.repositories.ProfiloRepository;
import it.sincrono.repositories.StoricoCommesseRepository;
import it.sincrono.repositories.StoricoContrattiRepository;
import it.sincrono.repositories.TipologicheContrattoRepository;
import it.sincrono.repositories.UtenteRepository;
import it.sincrono.repositories.dto.AnagraficaDto;
import it.sincrono.requests.AnagraficaRequestDto;
import it.sincrono.services.AnagraficaService;
import it.sincrono.services.ConfiguratorService;
import it.sincrono.services.EmailService;
import it.sincrono.services.costants.ServiceMessages;
import it.sincrono.services.exceptions.ServiceException;
import it.sincrono.services.utils.FileUtil;
import it.sincrono.services.utils.FilterCustom;
import it.sincrono.services.utils.MapperCustom;
import it.sincrono.services.utils.ObjectCompare;
import it.sincrono.services.utils.TokenGenerator;
import it.sincrono.services.validator.AnagraficaValidator;
import it.sincrono.services.validator.CommessaValidator;
import it.sincrono.services.validator.CommessaValidatorList;
import it.sincrono.services.validator.ContrattoValidator;
import it.sincrono.services.validator.RuoloValidator;
import jakarta.transaction.Transactional;

@Service
public class ConfiguratorServiceImpl extends BaseServiceImpl implements ConfiguratorService {

	private static final Logger LOGGER = LogManager.getLogger(ConfiguratorServiceImpl.class);

	@Autowired
	private ConfiguratorRepository configuratorRepository;


	@Override
	public List<Configurator> listConfigurator() throws ServiceException {

		List<Configurator> list = null;

		try {
			
			list= configuratorRepository.listConfigurator();
	
		} catch (Exception e) {
			LOGGER.log(Level.ERROR, e.getMessage());
			throw new ServiceException(ServiceMessages.ERRORE_GENERICO);
		}

		return list;
	}


}