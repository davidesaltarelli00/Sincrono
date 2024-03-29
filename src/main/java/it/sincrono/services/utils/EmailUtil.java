package it.sincrono.services.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import it.sincrono.entities.Anagrafica;
import it.sincrono.entities.Richieste;
import it.sincrono.entities.TipoRichieste;
import it.sincrono.repositories.dto.DuplicazioniRichiestaDto;
import it.sincrono.repositories.dto.RichiestaDto;

@Component
public class EmailUtil {

	@Value("${path-frontend.path}")
	private String pathFrontEnd;

	public String createBodyRichiesta(RichiestaDto richiestaDto, Anagrafica anagrafica) {

		String ferieOrPermesso = richiestaDto.getList().get(0).getFerie() != null
				&& richiestaDto.getList().get(0).getFerie() == true ? "ferie" : "permesso";

		String unoOrPiuGiorni = richiestaDto.getList().size() > 1 ? " è per i giorni: ("
				+ richiestaDto.getList().stream().map(DuplicazioniRichiestaDto::getnGiorno).collect(Collectors.toList())
						.stream().map(Object::toString).collect(Collectors.joining(", "))
				+ ")" : " è per il giorno: " + richiestaDto.getList().get(0).getnGiorno();

		unoOrPiuGiorni += " per il mese: " + richiestaDto.getMese() + " per l'anno: " + richiestaDto.getAnno();

		String link = pathFrontEnd +richiestaDto.getId();

		return "la richiesta di " + ferieOrPermesso + unoOrPiuGiorni + " per visualizzare la richiesta: " + "<a href=\""
				+ link + "\">clicca qui</a>";

	}

	public String createSubjectRichiesta(RichiestaDto richiestaDto, Anagrafica anagrafica) {

		String ferieOrPermesso = richiestaDto.getList().get(0).getFerie() != null
				&& richiestaDto.getList().get(0).getFerie() == true ? "ferie" : "permesso";

		return "richiesta di " + ferieOrPermesso + " dal dipendente: " + anagrafica.getNome() + " "
				+ anagrafica.getCognome() + " codice fiscale: " + anagrafica.getCodiceFiscale();

	}

}
