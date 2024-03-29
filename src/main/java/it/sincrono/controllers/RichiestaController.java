package it.sincrono.controllers;

import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import it.sincrono.beans.Esito;
import it.sincrono.repositories.dto.RichiestaDto;
import it.sincrono.requests.RichiestaRequest;
import it.sincrono.responses.GenericResponse;
import it.sincrono.responses.RichiestaCheckResponse;
import it.sincrono.responses.RichiestaResponse;
import it.sincrono.responses.RichiesteDtoListResponse;
import it.sincrono.services.RichiestaService;
import it.sincrono.services.exceptions.ServiceException;

@RestController
@CrossOrigin
public class RichiestaController {

	private static final Logger LOGGER = LogManager.getLogger(RichiestaController.class);

	@Autowired
	RichiestaService richiestaService;

	@GetMapping("/get-richiesta/{id}")
	public @ResponseBody HttpEntity<RichiestaResponse> getRichiesta(@PathVariable("id") Integer id) {

		HttpEntity<RichiestaResponse> httpEntity = null;

		RichiestaResponse richiestaResponse = new RichiestaResponse();
		try {
			LOGGER.log(Level.INFO, "Inizio chiamata al meotodo getRichiesta");

			RichiestaDto richiestaDto = richiestaService.getRichiesta(id);

			richiestaResponse.setRichiestaDto(richiestaDto);
			richiestaResponse.setEsito(new Esito());

			httpEntity = new HttpEntity<RichiestaResponse>(richiestaResponse);

		} catch (ServiceException e) {
			LOGGER.log(Level.ERROR, e.getMessage());
			richiestaResponse.setEsito(new Esito(e.getCode(), e.getMessage(), null));
			httpEntity = new HttpEntity<RichiestaResponse>(richiestaResponse);
		}
		LOGGER.log(Level.INFO, "Fine chiamata al meotodo getRapportino\n");

		return httpEntity;
	}

	@PostMapping("/inserisci-richiesta")
	public @ResponseBody HttpEntity<GenericResponse> insertRichiesta(@RequestBody RichiestaRequest richiestaRequest) {

		HttpEntity<GenericResponse> httpEntity = null;

		GenericResponse genericResponse = new GenericResponse();
		try {
			LOGGER.log(Level.INFO, "Inizio chiamata al metodo insertRichiesta");

			richiestaService.insertRichiesta(richiestaRequest.getRichiestaDto());

			genericResponse.setEsito(new Esito());

			httpEntity = new HttpEntity<GenericResponse>(genericResponse);

		} catch (ServiceException e) {
			LOGGER.log(Level.ERROR, e.getMessage());
			genericResponse.setEsito(new Esito(e.getCode(), e.getMessage(), null));
			httpEntity = new HttpEntity<GenericResponse>(genericResponse);
		}
		LOGGER.log(Level.INFO, "Fine chiamata al metodo insertRichiesta\n");

		return httpEntity;
	}

	@PostMapping("/list-richieste")
	public @ResponseBody HttpEntity<RichiesteDtoListResponse> listRichiesteDto(
			@RequestBody RichiestaRequest richiestaRequest) {

		HttpEntity<RichiesteDtoListResponse> httpEntity = null;

		RichiesteDtoListResponse richiesteDtoListResponse = new RichiesteDtoListResponse();
		try {
			LOGGER.log(Level.INFO, "Inizio chiamata al meotodo listRichiesteDto");

			List<RichiestaDto> richiesteDto = richiestaService.listRichiesteDto(richiestaRequest.getRichiestaDto());

			richiesteDtoListResponse.setList(richiesteDto);
			richiesteDtoListResponse.setEsito(new Esito());

			httpEntity = new HttpEntity<RichiesteDtoListResponse>(richiesteDtoListResponse);

		} catch (ServiceException e) {
			LOGGER.log(Level.ERROR, e.getMessage());
			richiesteDtoListResponse.setEsito(new Esito(e.getCode(), e.getMessage(), null));
			httpEntity = new HttpEntity<RichiesteDtoListResponse>(richiesteDtoListResponse);
		}
		LOGGER.log(Level.INFO, "Fine chiamata al meotodo listRichiesteDto\n");

		return httpEntity;
	}

	@PostMapping("/list-richieste-accettate")
	public @ResponseBody HttpEntity<RichiesteDtoListResponse> listRichiesteDtoAccettate(
			@RequestBody RichiestaRequest richiestaRequest) {

		HttpEntity<RichiesteDtoListResponse> httpEntity = null;

		RichiesteDtoListResponse richiesteDtoListResponse = new RichiesteDtoListResponse();
		try {
			LOGGER.log(Level.INFO, "Inizio chiamata al meotodo listRichiesteDto");

			List<RichiestaDto> richiesteDto = richiestaService
					.listRichiesteDtoAccettate(richiestaRequest.getRichiestaDto());

			richiesteDtoListResponse.setList(richiesteDto);
			richiesteDtoListResponse.setEsito(new Esito());

			httpEntity = new HttpEntity<RichiesteDtoListResponse>(richiesteDtoListResponse);

		} catch (ServiceException e) {
			LOGGER.log(Level.ERROR, e.getMessage());
			richiesteDtoListResponse.setEsito(new Esito(e.getCode(), e.getMessage(), null));
			httpEntity = new HttpEntity<RichiesteDtoListResponse>(richiesteDtoListResponse);
		}
		LOGGER.log(Level.INFO, "Fine chiamata al meotodo listRichiesteDto\n");

		return httpEntity;
	}

	@PostMapping("/check-elaborazione")
	public @ResponseBody HttpEntity<RichiestaCheckResponse> checkElaborazione(
			@RequestBody RichiestaRequest richiestaRequest) {

		HttpEntity<RichiestaCheckResponse> httpEntity = null;
		RichiestaCheckResponse richiestaCheckResponse = new RichiestaCheckResponse(null, null);

		try {
			LOGGER.log(Level.INFO, "Inizio chiamata al meotodo checkElaborazione");
			richiestaCheckResponse.setCheckElaborazione(richiestaService.checkElaborazione(richiestaRequest));
			richiestaCheckResponse.setEsito(new Esito());
			httpEntity = new HttpEntity<RichiestaCheckResponse>(richiestaCheckResponse);

		} catch (ServiceException e) {
			LOGGER.log(Level.ERROR, e.getMessage());
			richiestaCheckResponse.setEsito(new Esito(e.getCode(), e.getMessage(), null));
			httpEntity = new HttpEntity<RichiestaCheckResponse>(richiestaCheckResponse);
		}
		LOGGER.log(Level.INFO, "Fine chiamata al meotodo checkElaborazione \n");
		return httpEntity;

	}

	@PutMapping("/cambia-stato")
	public @ResponseBody HttpEntity<GenericResponse> changeStatus(@RequestBody RichiestaRequest richiestaRequest) {

		HttpEntity<GenericResponse> httpEntity = null;

		GenericResponse genericResponse = new GenericResponse();
		try {
			LOGGER.log(Level.INFO, "Inizio chiamata al meotodo cambia stato richiesta");

			richiestaService.changeStato(richiestaRequest.getRichiestaDto());

			genericResponse.setEsito(new Esito());

			httpEntity = new HttpEntity<GenericResponse>(genericResponse);

		} catch (ServiceException e) {
			LOGGER.log(Level.ERROR, e.getMessage());
			genericResponse.setEsito(new Esito(e.getCode(), e.getMessage(), null));
			httpEntity = new HttpEntity<GenericResponse>(genericResponse);
		}
		LOGGER.log(Level.INFO, "Fine chiamata al meotodo cambia stato richiesta \n");

		return httpEntity;
	}
	
	@PutMapping("/modifica-richiesta")
	public @ResponseBody HttpEntity<GenericResponse> updateRichiesta(@RequestBody RichiestaRequest richiestaRequest) {

		HttpEntity<GenericResponse> httpEntity = null;

		GenericResponse genericResponse = new GenericResponse();
		try {
			LOGGER.log(Level.INFO, "Inizio chiamata al metodo modificaRichiesta");

			richiestaService.modificaRichiesta(richiestaRequest);

			genericResponse.setEsito(new Esito());

			httpEntity = new HttpEntity<GenericResponse>(genericResponse);

		} catch (ServiceException e) {
			LOGGER.log(Level.ERROR, e.getMessage());
			genericResponse.setEsito(new Esito(e.getCode(), e.getMessage(), null));
			httpEntity = new HttpEntity<GenericResponse>(genericResponse);
		}
		LOGGER.log(Level.INFO, "Fine chiamata al metodo modificaRichiesta\n");

		return httpEntity;
	}

}
