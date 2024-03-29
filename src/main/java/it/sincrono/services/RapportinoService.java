package it.sincrono.services;

import java.util.List;

import it.sincrono.entities.RapportinoInviato;
import it.sincrono.repositories.dto.AnagraficaDto;
import it.sincrono.repositories.dto.RapportinoDto;
import it.sincrono.requests.AnagraficaFilterRequestDto;
import it.sincrono.requests.RapportinoRequest;
import it.sincrono.requests.RapportinoRequestDto;
import it.sincrono.services.exceptions.ServiceException;

public interface RapportinoService {

	public RapportinoInviato findByData(RapportinoDto rapportinoDto) throws ServiceException;

	public RapportinoDto getRapportino(RapportinoRequestDto rapportinoRequestDto) throws ServiceException;

	
	
	public void updateRapportino(RapportinoRequestDto rapportinoRequestDto) throws ServiceException;

	public void inviaRapportino(RapportinoInviato rapportinoInviato) throws ServiceException;

	public void updateFreeze(RapportinoInviato rapportinoInviato) throws ServiceException;

	public void delete(Integer id) throws ServiceException;

	public Boolean aggiungiNote(RapportinoRequestDto rapportinoRequestDto) throws ServiceException;
	
	public Boolean aggiungiNoteDipendente(RapportinoRequestDto rapportinoRequestDto) throws ServiceException;

	public List<AnagraficaDto> getRapportiniNotFreeze(AnagraficaFilterRequestDto anagraficaFilterRequestDto) throws ServiceException;

	public List<AnagraficaDto> getRapportiniFreeze(AnagraficaFilterRequestDto anagraficaFilterRequestDto) throws ServiceException;

	public List<AnagraficaDto> getRapportiniNotFreezeFilter(AnagraficaFilterRequestDto anagraficaFilterRequestDto)
			throws ServiceException;

	public List<AnagraficaDto> getRapportiniFreezeFilter(AnagraficaFilterRequestDto anagraficaFilterRequestDto)
			throws ServiceException;

	public void addRapportinoInDatabase(RapportinoRequest rapportinoRequest) throws ServiceException;

	public void deleteRapportinoInDatabase(RapportinoRequest rapportinoRequest) throws ServiceException;

	public String getRapportinoB64(Integer anno, Integer mese) throws ServiceException;

	public boolean getCheckRapportinoInviato(RapportinoRequest RapportinoRequest) throws ServiceException;

}
