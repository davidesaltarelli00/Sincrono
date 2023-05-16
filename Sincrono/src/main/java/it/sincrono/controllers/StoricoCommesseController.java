package it.sincrono.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import it.sincrono.beans.Esito;
import it.sincrono.entities.StoricoCommesse;
import it.sincrono.requests.StoricoCommesseRequest;
import it.sincrono.responses.GenericResponse;
import it.sincrono.responses.StoricoCommesseListResponse;
import it.sincrono.responses.StoricoCommesseResponse;
import it.sincrono.services.StoricoCommesseService;
import it.sincrono.services.exceptions.ServiceException;

public class StoricoCommesseController {

	@Autowired
	private StoricoCommesseService StoricoCommesseService;

	@GetMapping("/StoricoCommesse")
	public @ResponseBody HttpEntity<StoricoCommesseListResponse> fetchAllStoricoCommesse() {
		HttpEntity<StoricoCommesseListResponse> httpEntity;

		StoricoCommesseListResponse StoricoCommesseListResponse = new StoricoCommesseListResponse();

		try {
			List<StoricoCommesse> commesse = StoricoCommesseService.listStoricoCommesse();

			StoricoCommesseListResponse.setList(commesse);
			StoricoCommesseListResponse.setEsito(new Esito());

			httpEntity = new HttpEntity<StoricoCommesseListResponse>(StoricoCommesseListResponse);
			System.out.println("bau");
		} catch (Exception e) {
			 StoricoCommesseListResponse.setEsito(new Esito(404, e.getMessage(), null));
			httpEntity = new HttpEntity<StoricoCommesseListResponse>(StoricoCommesseListResponse);
		}
		return httpEntity;

	}

	@GetMapping("/StoricoCommesse/{id}")
	public @ResponseBody HttpEntity<StoricoCommesseResponse> getStoricoCommesseById(@PathVariable Integer id) {

		HttpEntity<StoricoCommesseResponse> httpEntity;

		StoricoCommesseResponse StoricoCommesseResponse = new StoricoCommesseResponse();

		try {
			StoricoCommesse StoricoCommesse = StoricoCommesseService.getStoricoCommesseById(id);

			StoricoCommesseResponse.setStoricoCommesse(StoricoCommesse);
			StoricoCommesseResponse.setEsito(new Esito());

			httpEntity = new HttpEntity<StoricoCommesseResponse>(StoricoCommesseResponse);
			System.out.println("ciao");
		} catch (Exception e) {
			StoricoCommesseResponse.setEsito(new Esito(404, e.getMessage(), new String[] { String.valueOf(id) }));
			httpEntity = new HttpEntity<StoricoCommesseResponse>(StoricoCommesseResponse);
		}
		return httpEntity;
	}

	@PostMapping("/StoricoCommesse")
	public @ResponseBody HttpEntity<GenericResponse> saveStoricoCommesse(@RequestBody StoricoCommesseRequest StoricoCommesseRequest) {
		HttpEntity<GenericResponse> httpEntity;

		GenericResponse genericResponse = new GenericResponse();

		try {
			StoricoCommesseService.insert(StoricoCommesseRequest.getStoricoCommesse());
			genericResponse.setEsito(new Esito());

			httpEntity = new HttpEntity<GenericResponse>(genericResponse);
			System.out.println("ciao");
		} catch (Exception e) {
			genericResponse.setEsito(new Esito(404, e.getMessage(), new String[] { null }));
			httpEntity = new HttpEntity<GenericResponse>(genericResponse);
		}
		return httpEntity;
	}

	@PutMapping("/StoricoCommesse")
	public @ResponseBody HttpEntity<GenericResponse> updateStoricoCommesse(@RequestBody StoricoCommesseRequest StoricoCommesseRequest) {
		HttpEntity<GenericResponse> httpEntity;

		GenericResponse genericResponse = new GenericResponse();

		try {
			StoricoCommesseService.update(StoricoCommesseRequest.getStoricoCommesse());
			genericResponse.setEsito(new Esito());

			httpEntity = new HttpEntity<GenericResponse>(genericResponse);
			System.out.println("ciao");
		} catch (Exception e) {
			genericResponse.setEsito(new Esito(404, e.getMessage(), new String[] { null }));
			httpEntity = new HttpEntity<GenericResponse>(genericResponse);
		}
		return httpEntity;
	}

	@DeleteMapping("/StoricoCommesse/{id}")
	public @ResponseBody HttpEntity<GenericResponse> delete(@PathVariable("id") Integer id) {

		HttpEntity<GenericResponse> httpEntity;

		GenericResponse genericResponse = new GenericResponse();

		try {

			StoricoCommesseService.delete(id);

			genericResponse.setEsito(new Esito());

			httpEntity = new HttpEntity<GenericResponse>(genericResponse);

		} catch (ServiceException e) {
			genericResponse.setEsito(new Esito(e.getCode(), e.getMessage(), new String[] { String.valueOf(id) }));
			httpEntity = new HttpEntity<GenericResponse>(genericResponse);
		}

		return httpEntity;
	}
	
}