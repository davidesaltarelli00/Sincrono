package it.sincrono.controllers;

import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import it.sincrono.beans.Esito;
import it.sincrono.repositories.dto.AnagraficaDto;
import it.sincrono.requests.AnagraficaRequestDto;
import it.sincrono.requests.ExcelDtoRequest;
import it.sincrono.responses.AnagraficaDtoListResponse;
import it.sincrono.responses.AnagraficaDtoResponse;
import it.sincrono.responses.GenericResponse;
import it.sincrono.services.AnagraficaService;
import it.sincrono.services.UtenteService;
import it.sincrono.services.exceptions.ServiceException;

@RestController
@CrossOrigin
public class AnagraficaController {
	private static final Logger LOGGER = LogManager.getLogger(AnagraficaController.class);

	@Autowired
	private AnagraficaService anagraficaService;

	@Autowired
	private UtenteService utenteService;
//
//	@GetMapping("/anagrafica/{id}")
//	public @ResponseBody HttpEntity<AnagraficaResponse> getById(@PathVariable Integer id) {
//
//		HttpEntity<AnagraficaResponse> httpEntity;
//
//		AnagraficaResponse anagraficaResponse = new AnagraficaResponse();
//
//		try {
//
//			Anagrafica anagrafica = anagraficaService.getById(id);
//
//			anagraficaResponse.setEsito(new Esito());
//			anagraficaResponse.setAnagrafica(anagrafica);
//
//			httpEntity = new HttpEntity<AnagraficaResponse>(anagraficaResponse);
//
//
//		} catch (ServiceException e) {
//			anagraficaResponse.setEsito(new Esito(e.getCode(), e.getMessage(), null));
//			httpEntity = new HttpEntity<AnagraficaResponse>(anagraficaResponse);
//		}
//
//		return httpEntity;
//	}

//	@GetMapping("/anagrafica-list")
//	public @ResponseBody HttpEntity<AnagraficaListResponse> getAll() {
//
//		HttpEntity<AnagraficaListResponse> httpEntity = null;
//
//		AnagraficaListResponse anagraficaListResponse = new AnagraficaListResponse();
//
//		try {
//
//			List<Anagrafica> anagrafiche = anagraficaService.list();
//
//			anagraficaListResponse.setList(anagrafiche);
//			anagraficaListResponse.setEsito(new Esito());
//
//			httpEntity = new HttpEntity<AnagraficaListResponse>(anagraficaListResponse);
//
//
//		} catch (ServiceException e) {
//			anagraficaListResponse.setEsito(new Esito(e.getCode(), e.getMessage(), null));
//			httpEntity = new HttpEntity<AnagraficaListResponse>(anagraficaListResponse);
//		}
//
//		return httpEntity;
//	}

//	@PostMapping("/anagrafica")
//	public @ResponseBody HttpEntity<GenericResponse> insert(@RequestBody AnagraficaRequest anagraficaRequest) {
//
//		HttpEntity<GenericResponse> httpEntity = null;
//
//		GenericResponse genericResponse = new GenericResponse();
//
//		try {
//
//			anagraficaService.insert(anagraficaRequest.getAnagrafica());
//
//			genericResponse.setEsito(new Esito());
//
//			httpEntity = new HttpEntity<GenericResponse>(genericResponse);
//
//
//		} catch (ServiceException e) {
//			genericResponse.setEsito(new Esito(e.getCode(), e.getMessage(), null));
//			httpEntity = new HttpEntity<GenericResponse>(genericResponse);
//		}
//
//		return httpEntity;
//	}

//	@PutMapping("/anagrafica")
//	public @ResponseBody HttpEntity<GenericResponse> update(@RequestBody AnagraficaRequest anagraficaRequest) {
//
//		HttpEntity<GenericResponse> httpEntity = null;
//
//		GenericResponse genericResponse = new GenericResponse();
//
//		try {
//
//			anagraficaService.update(anagraficaRequest.getAnagrafica());
//
//			genericResponse.setEsito(new Esito());
//
//			httpEntity = new HttpEntity<GenericResponse>(genericResponse);
//
//
//		} catch (ServiceException e) {
//			genericResponse.setEsito(new Esito(e.getCode(), e.getMessage(), null));
//			httpEntity = new HttpEntity<GenericResponse>(genericResponse);
//		}
//
//		return httpEntity;
//	}

//	@DeleteMapping("/anagrafica/{id}")
//	public @ResponseBody HttpEntity<GenericResponse> delete(@PathVariable("id") Integer ID) {
//
//		HttpEntity<GenericResponse> httpEntity = null;
//
//		GenericResponse genericResponse = new GenericResponse();
//
//		try {
//
//			anagraficaService.delete(ID);
//
//			genericResponse.setEsito(new Esito());
//
//			httpEntity = new HttpEntity<GenericResponse>(genericResponse);
//
//
//		} catch (ServiceException e) {
//			genericResponse.setEsito(new Esito(e.getCode(), e.getMessage(), null));
//			httpEntity = new HttpEntity<GenericResponse>(genericResponse);
//		}
//
//		return httpEntity;
//	}

	@PostMapping("/filter")
	public @ResponseBody HttpEntity<AnagraficaDtoListResponse> filterListAnagraficaDto(
			@RequestBody AnagraficaRequestDto anagraficaRequestDto) {

		HttpEntity<AnagraficaDtoListResponse> httpEntity = null;

		AnagraficaDtoListResponse anagraficaDtoListResponse = new AnagraficaDtoListResponse();
		try {
			LOGGER.log(Level.INFO, "Inizio chiamata al meotodo filterListAnagraficaDto");

			List<AnagraficaDto> anagrafiche = anagraficaService.filterListAnagraficaDto(anagraficaRequestDto);

			anagraficaDtoListResponse.setList(anagrafiche);
			anagraficaDtoListResponse.setEsito(new Esito());

			httpEntity = new HttpEntity<AnagraficaDtoListResponse>(anagraficaDtoListResponse);

		} catch (ServiceException e) {
			LOGGER.log(Level.ERROR, e.getMessage());
			anagraficaDtoListResponse.setEsito(new Esito(e.getCode(), e.getMessage(), null));
			httpEntity = new HttpEntity<AnagraficaDtoListResponse>(anagraficaDtoListResponse);
		}
		LOGGER.log(Level.INFO, "Fine chiamata al meotodo filterListAnagraficaDto\n");

		return httpEntity;
	}

	@GetMapping("/list")
	public @ResponseBody HttpEntity<AnagraficaDtoListResponse> listAnagraficaDto() {

		HttpEntity<AnagraficaDtoListResponse> httpEntity = null;

		AnagraficaDtoListResponse anagraficaDtoListResponse = new AnagraficaDtoListResponse();
		try {
			LOGGER.log(Level.INFO, "Inizio chiamata al meotodo listAnagraficaDto");

			List<AnagraficaDto> anagrafiche = anagraficaService.listAnagraficaDto();

			anagraficaDtoListResponse.setList(anagrafiche);
			anagraficaDtoListResponse.setEsito(new Esito());

			httpEntity = new HttpEntity<AnagraficaDtoListResponse>(anagraficaDtoListResponse);

		} catch (ServiceException e) {
			LOGGER.log(Level.ERROR, e.getMessage());
			anagraficaDtoListResponse.setEsito(new Esito(e.getCode(), e.getMessage(), null));
			httpEntity = new HttpEntity<AnagraficaDtoListResponse>(anagraficaDtoListResponse);
		}
		LOGGER.log(Level.INFO, "Fine chiamata al meotodo listAnagraficaDto\n");

		return httpEntity;
	}

	@GetMapping("/dettaglio/{id}")
	public @ResponseBody HttpEntity<AnagraficaDtoResponse> dettaglioAnagraficaDto(@PathVariable("id") Integer id) {
		HttpEntity<AnagraficaDtoResponse> httpEntity = null;
		AnagraficaDtoResponse anagraficaDtoResponse = new AnagraficaDtoResponse();
		try {
			LOGGER.log(Level.INFO, "Inizio chiamata al meotodo dettaglioAnagraficaDto");
			AnagraficaDto anagraficaDto = anagraficaService.getAnagraficaDto(id);
			anagraficaDtoResponse.setAnagraficaDto(anagraficaDto);
			anagraficaDtoResponse.setEsito(new Esito());

			httpEntity = new HttpEntity<AnagraficaDtoResponse>(anagraficaDtoResponse);

		} catch (ServiceException e) {
			LOGGER.log(Level.ERROR, e.getMessage());
			anagraficaDtoResponse.setEsito(new Esito(e.getCode(), e.getMessage(), null));
			httpEntity = new HttpEntity<AnagraficaDtoResponse>(anagraficaDtoResponse);
		}
		LOGGER.log(Level.INFO, "Fine chiamata al meotodo dettaglioAnagraficaDto\n");
		return httpEntity;
	}

	@PostMapping("/inserisci")
	public @ResponseBody HttpEntity<GenericResponse> insertAnagraficaDto(
			@RequestBody AnagraficaRequestDto anagraficaRequestDto) {

		HttpEntity<GenericResponse> httpEntity = null;

		GenericResponse genericResponse = new GenericResponse();
		try {
			LOGGER.log(Level.INFO, "Inizio chiamata al metodo insertAnagrafica");

			anagraficaService.insertAnagraficaDto(anagraficaRequestDto.getAnagraficaDto());

			genericResponse.setEsito(new Esito());

			httpEntity = new HttpEntity<GenericResponse>(genericResponse);

		} catch (ServiceException e) {
			LOGGER.log(Level.ERROR, e.getMessage());
			genericResponse.setEsito(new Esito(e.getCode(), e.getMessage(), null));
			httpEntity = new HttpEntity<GenericResponse>(genericResponse);
		}
		LOGGER.log(Level.INFO, "Fine chiamata al metodo inserAnagrafica\n");

		return httpEntity;
	}

	@PostMapping("/inserisci-excel")
	public @ResponseBody HttpEntity<AnagraficaDtoListResponse> insertAnagraficaDtoExcel(
			@RequestBody ExcelDtoRequest excelDtoRequest) {

		HttpEntity<AnagraficaDtoListResponse> httpEntity = null;

		AnagraficaDtoListResponse anagraficaDtoListResponse = new AnagraficaDtoListResponse();
		try {
			LOGGER.log(Level.INFO, "Inizio chiamata al metodo insertAnagrafica");

			anagraficaDtoListResponse.setList(
					anagraficaService.insertAnagraficaDtoExcel(excelDtoRequest.getBase64()));

			anagraficaDtoListResponse.setEsito(new Esito());

			httpEntity = new HttpEntity<AnagraficaDtoListResponse>(anagraficaDtoListResponse);

		} catch (ServiceException e) {
			LOGGER.log(Level.ERROR, e.getMessage());
			anagraficaDtoListResponse.setEsito(new Esito(e.getCode(), e.getMessage(), null));
			httpEntity = new HttpEntity<AnagraficaDtoListResponse>(anagraficaDtoListResponse);
		}
		LOGGER.log(Level.INFO, "Fine chiamata al metodo inserAnagrafica\n");

		return httpEntity;
	}

	@PutMapping("/modifica")
	public @ResponseBody HttpEntity<GenericResponse> updateAnagraficadto(
			@RequestBody AnagraficaRequestDto anagraficaRequestDto) {

		HttpEntity<GenericResponse> httpEntity = null;

		GenericResponse genericResponse = new GenericResponse();

		try {
			LOGGER.log(Level.INFO, "Inizio chiamata al metodo updateAnagrafica");

			anagraficaService.updateAnagraficaDto(anagraficaRequestDto.getAnagraficaDto());

			genericResponse.setEsito(new Esito());

			httpEntity = new HttpEntity<GenericResponse>(genericResponse);

		} catch (ServiceException e) {
			LOGGER.log(Level.ERROR, e.getMessage());
			genericResponse.setEsito(new Esito(e.getCode(), e.getMessage(), null));
			httpEntity = new HttpEntity<GenericResponse>(genericResponse);
		}
		LOGGER.log(Level.INFO, "Fine chiamata al metodo updateAnagrafica\n");

		return httpEntity;
	}

	/*
	 * @PostMapping("/aggiungi-contratto-commessa") public @ResponseBody
	 * HttpEntity<GenericResponse> insertAnagraficaDtoRelations(
	 * 
	 * @RequestBody AnagraficaRequestDto anagraficaRequestDto) {
	 * 
	 * HttpEntity<GenericResponse> httpEntity = null;
	 * 
	 * GenericResponse genericResponse = new GenericResponse();
	 * 
	 * try { );
	 * 
	 * anagraficaService.insertAnagraficaDtoRelations(anagraficaRequestDto.
	 * getAnagraficaDto());
	 * 
	 * genericResponse.setEsito(new Esito());
	 * 
	 * httpEntity = new HttpEntity<GenericResponse>(genericResponse);
	 * 
	 * 
	 * } catch (ServiceException e) { genericResponse.setEsito(new
	 * Esito(e.getCode(), e.getMessage(), null)); httpEntity = new
	 * HttpEntity<GenericResponse>(genericResponse); }
	 * 
	 * return httpEntity; }
	 */

	@PutMapping("/delete")
	public @ResponseBody HttpEntity<GenericResponse> deleteAnagraficaDto(
			@RequestBody AnagraficaRequestDto anagraficaRequestDto) {

		HttpEntity<GenericResponse> httpEntity = null;

		GenericResponse genericResponse = new GenericResponse();

		try {
			LOGGER.log(Level.INFO, "Inizio chiamata al metodo deleteAnagraficaDto");

			anagraficaService.deleteAnagraficaDto(anagraficaRequestDto.getAnagraficaDto());

			genericResponse.setEsito(new Esito());

			httpEntity = new HttpEntity<GenericResponse>(genericResponse);

		} catch (ServiceException e) {
			LOGGER.log(Level.ERROR, e.getMessage());
			genericResponse.setEsito(new Esito(e.getCode(), e.getMessage(), null));
			httpEntity = new HttpEntity<GenericResponse>(genericResponse);
		}
		LOGGER.log(Level.INFO, "Fine chiamata al metodo deleteAnagraficaDto\n");

		return httpEntity;
	}

	@PutMapping("/retain")
	public @ResponseBody HttpEntity<GenericResponse> retainAnagraficaDto(
			@RequestBody AnagraficaRequestDto anagraficaRequestDto) {

		HttpEntity<GenericResponse> httpEntity = null;

		GenericResponse genericResponse = new GenericResponse();

		try {
			LOGGER.log(Level.INFO, "Inizio chiamata al metodo di retainAnagraficaDto");

			anagraficaService.retainAnagraficaDto(anagraficaRequestDto.getAnagraficaDto());

			genericResponse.setEsito(new Esito());

			httpEntity = new HttpEntity<GenericResponse>(genericResponse);

		} catch (ServiceException e) {
			LOGGER.log(Level.ERROR, e.getMessage());
			genericResponse.setEsito(new Esito(e.getCode(), e.getMessage(), null));
			httpEntity = new HttpEntity<GenericResponse>(genericResponse);
		}
		LOGGER.log(Level.INFO, "Fine chiamata al metodo di retainAnagraficaDto\n");

		return httpEntity;
	}

	@GetMapping("/anagrafica-list-contratti")
	public @ResponseBody HttpEntity<AnagraficaDtoListResponse> anagraficaListContratti() {
		HttpEntity<AnagraficaDtoListResponse> httpEntity = null;

		AnagraficaDtoListResponse anagraficaDtoListResponse = new AnagraficaDtoListResponse();
		try {
			LOGGER.log(Level.INFO, "Inizio chiamata al metodo  anagraficaListContratti");
			List<AnagraficaDto> anagrafiche = anagraficaService.anagraficaListContratti();

			anagraficaDtoListResponse.setList(anagrafiche);
			anagraficaDtoListResponse.setEsito(new Esito());

			httpEntity = new HttpEntity<AnagraficaDtoListResponse>(anagraficaDtoListResponse);

		} catch (ServiceException e) {
			LOGGER.log(Level.ERROR, e.getMessage());
			anagraficaDtoListResponse.setEsito(new Esito(e.getCode(), e.getMessage(), null));
			httpEntity = new HttpEntity<AnagraficaDtoListResponse>(anagraficaDtoListResponse);
		}
		LOGGER.log(Level.INFO, "Fine chiamata al metodo anagraficaListContratti\n");
		return httpEntity;
	}

	@DeleteMapping("/anagrafica-Delete-ScattoContratti")
	public @ResponseBody HttpEntity<GenericResponse> deleteScattiContratto() {

		HttpEntity<GenericResponse> httpEntity = null;

		GenericResponse genericResponse = new GenericResponse();

		try {
			LOGGER.log(Level.INFO, "Inizio chiamata al metodo deleteScattiContratto");

			anagraficaService.deleteScattoContratti();

			genericResponse.setEsito(new Esito());

			httpEntity = new HttpEntity<GenericResponse>(genericResponse);

		} catch (ServiceException e) {
			LOGGER.log(Level.ERROR, e.getMessage());
			genericResponse.setEsito(new Esito(e.getCode(), e.getMessage(), null));
			httpEntity = new HttpEntity<GenericResponse>(genericResponse);
		}
		LOGGER.log(Level.INFO, "Fine chiamata al metodo deleteScattiContratto\n");

		return httpEntity;
	}

	@GetMapping("/dettaglio-token/{token}")
	public @ResponseBody HttpEntity<AnagraficaDtoResponse> dettaglioAnagraficaDtoByToken(
			@PathVariable("token") String token) {
		HttpEntity<AnagraficaDtoResponse> httpEntity = null;
		AnagraficaDtoResponse anagraficaDtoResponse = new AnagraficaDtoResponse();
		try {
			LOGGER.log(Level.INFO, "Inizio chiamata al meotodo dettaglioAnagraficaDtoByToken");
			AnagraficaDto anagraficaDto = anagraficaService.getAnagraficaDtoByToken(token);
			anagraficaDtoResponse.setAnagraficaDto(anagraficaDto);
			anagraficaDtoResponse.setEsito(new Esito());

			httpEntity = new HttpEntity<AnagraficaDtoResponse>(anagraficaDtoResponse);

		} catch (ServiceException e) {
			LOGGER.log(Level.ERROR, e.getMessage());
			anagraficaDtoResponse.setEsito(new Esito(e.getCode(), e.getMessage(), null));
			httpEntity = new HttpEntity<AnagraficaDtoResponse>(anagraficaDtoResponse);
		}
		LOGGER.log(Level.INFO, "Fine chiamata al metodo dettaglioAnagraficaDtoByToken\n");

		return httpEntity;
	}

}
