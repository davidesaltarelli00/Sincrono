package it.sincrono.services.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;

import it.sincrono.repositories.dto.DuplicazioniGiornoDto;
import it.sincrono.repositories.dto.GiornoDto;
import it.sincrono.repositories.dto.MeseDto;
import it.sincrono.repositories.dto.RapportinoDto;
import it.sincrono.requests.RapportinoRequestDto;
import it.sincrono.responses.DocumentResponse;
import it.sincrono.services.costants.ServiceMessages;
import it.sincrono.services.exceptions.ServiceException;

@Component
public class FileUtil {
	private static final Logger LOGGER = LogManager.getLogger(FileUtil.class);

	public RapportinoDto readFile(String percorso) throws Exception {

		RapportinoDto rapportinoDto = new RapportinoDto();

		creatFolder(percorso);

		try (BufferedReader reader = new BufferedReader(new FileReader(percorso))) {
			StringBuilder fileStringBuilder = new StringBuilder();
			String linea = null;

			// fileStringBuilder.append(reader.readLine()).append("\n");
			// String fileString = fileStringBuilder.toString();

			rapportinoDto = covertStringInRapportinoDto(reader.readLine());

			rapportinoDto.setNote(reader.readLine());

		} catch (Exception e) {
			LOGGER.log(Level.ERROR, e.getMessage());
			throw new Exception(e);
		}

		return rapportinoDto;
	}

	private RapportinoDto covertStringInRapportinoDto(String fileString) {

		RapportinoDto rapportinoDto = new RapportinoDto();

		rapportinoDto.setMese(new MeseDto());

		List<GiornoDto> mese = new ArrayList<GiornoDto>();

		if (fileString != null && !fileString.isEmpty()) {

			fileString = fileString.replaceAll("\n", "");

			for (String giornoNotSplit : fileString.split(";")) {

				List<String> cliente = new ArrayList<String>();

				List<Double> oreOrdinarie = new ArrayList<Double>();

				GiornoDto giornoDto = new GiornoDto();

				giornoDto.setDuplicazioniGiornoDto(new ArrayList<DuplicazioniGiornoDto>());

				DuplicazioniGiornoDto duplicazioniGiornoDto = new DuplicazioniGiornoDto();

				String[] giornoSplit = giornoNotSplit.split(",");

				for (String elem : giornoSplit[1].split("/")) {

					if (elem != null && !elem.equals("null")) {

						if (giornoSplit[0] != null && !giornoSplit[0].isEmpty() && !giornoSplit[0].equals("null"))
							duplicazioniGiornoDto.setGiorno(Integer.parseInt(giornoSplit[0]));

						if (elem.split("-")[0] != null && !elem.split("-")[0].isEmpty()
								&& !elem.split("-")[0].equals("null"))

							duplicazioniGiornoDto.setCliente(elem.split("-")[0]);

						if (elem.split("-")[1] != null && !elem.split("-")[1].isEmpty()
								&& !elem.split("-")[1].equals("null"))

							duplicazioniGiornoDto.setOreOrdinarie(Double.parseDouble(elem.split("-")[1]));

						if (elem.split("-")[2] != null && !elem.split("-")[2].isEmpty()
								&& !elem.split("-")[2].equals("null"))

							duplicazioniGiornoDto.setFascia1(Double.parseDouble(elem.split("-")[2]));

						if (elem.split("-")[3] != null && !elem.split("-")[3].isEmpty()
								&& !elem.split("-")[3].equals("null"))

							duplicazioniGiornoDto.setFascia2(Double.parseDouble(elem.split("-")[3]));

						if (elem.split("-")[4] != null && !elem.split("-")[4].isEmpty()
								&& !elem.split("-")[4].equals("null"))

							duplicazioniGiornoDto.setFascia3(Double.parseDouble(elem.split("-")[4]));

						/*
						 * straordinari.add(straordinario.getFascia1() != null ||
						 * straordinario.getFascia2() != null || straordinario.getFascia3() != null ?
						 * straordinario : null);
						 */

						/*
						 * if(straordinario.getFascia1() != null || straordinario.getFascia2() != null
						 * || straordinario.getFascia3() != null) {
						 * 
						 * straordinari.add(straordinario);
						 * 
						 * }else {
						 * 
						 * straordinari=null; }
						 */

						giornoDto.getDuplicazioniGiornoDto().add(duplicazioniGiornoDto);

						duplicazioniGiornoDto = new DuplicazioniGiornoDto();

					}

				}

				if (giornoSplit[2] != null && !giornoSplit[2].isEmpty() && !giornoSplit[2].equals("null"))
					giornoDto.setFerie(Boolean.parseBoolean(giornoSplit[2]));

				if (giornoSplit[3] != null && !giornoSplit[3].isEmpty() && !giornoSplit[3].equals("null"))
					giornoDto.setMalattie(Boolean.parseBoolean(giornoSplit[3]));

				if (giornoSplit[4] != null && !giornoSplit[4].isEmpty() && !giornoSplit[4].equals("null"))
					giornoDto.setPermessi(Double.parseDouble(giornoSplit[4]));

				if (giornoSplit[5] != null && !giornoSplit[5].isEmpty() && !giornoSplit[5].equals("null"))
					giornoDto.setNote(giornoSplit[5]);

				mese.add(giornoDto);

			}

			rapportinoDto.getMese().setGiorni(mese);

		} else {

			createRapportinoForMonth(rapportinoDto);
		}

		return rapportinoDto;

	}

	public void creatFolder(String percorso) throws Exception {

		File file = new File(percorso);
		if (!file.exists()) {
			try {
				Files.createDirectories(Paths.get(percorso).getParent());
				file.createNewFile();
			} catch (Exception e) {
				LOGGER.log(Level.ERROR, e.getMessage());
				throw new ServiceException(ServiceMessages.ERRORE_GENERICO);
			}
		}
	}

	private int getNumeroGiorniMese(int anno, int mese) {
		YearMonth yearMonth = YearMonth.of(anno, mese);
		return yearMonth.lengthOfMonth();
	}

	private void createRapportinoForMonth(RapportinoDto rapportinoDto) {

		LocalDate oggi = LocalDate.now();

		rapportinoDto.setMese(new MeseDto());

		List<GiornoDto> mese = new ArrayList<GiornoDto>();

		DuplicazioniGiornoDto duplicazioniGiornoDto = new DuplicazioniGiornoDto();

		for (int i = 0; i < getNumeroGiorniMese(oggi.getYear(), oggi.getMonthValue()); i++) {

			GiornoDto giornoDto = new GiornoDto();

			giornoDto.setDuplicazioniGiornoDto(new ArrayList<DuplicazioniGiornoDto>());

			giornoDto.getDuplicazioniGiornoDto().add(duplicazioniGiornoDto);

			mese.add(giornoDto);

		}

		rapportinoDto.getMese().setGiorni(mese);

	}

	public void saveFile(String path, RapportinoRequestDto rapportino) throws Exception {
		if (rapportino != null) {
			try {
				Path filePath = Path.of(path);
				if (Files.exists(filePath))
					Files.delete(filePath);
				String dati = "";
				try (FileWriter writer = new FileWriter(filePath.toFile())) {
					for (GiornoDto giornoDto : rapportino.getRapportinoDto().getMese().getGiorni()) {

						if (giornoDto.getDuplicazioniGiornoDto() != null
								&& giornoDto.getDuplicazioniGiornoDto().get(0) != null
								&& giornoDto.getDuplicazioniGiornoDto().get(0).getGiorno() != null) {
							dati += giornoDto.getDuplicazioniGiornoDto().get(0).getGiorno() + ",";
							for (DuplicazioniGiornoDto giornoDuplicato : giornoDto.getDuplicazioniGiornoDto()) {
								int i = 1;

								if (giornoDuplicato.getCliente() != null) {
									dati += giornoDuplicato.getCliente() + "-";
								} else
									dati += "null-";
								if (giornoDuplicato.getOreOrdinarie() != null) {
									dati += giornoDuplicato.getOreOrdinarie() + "-";
								} else
									dati += "null-";
								if (giornoDuplicato.getFascia1() != null) {
									dati += giornoDuplicato.getFascia1() + "-";
								} else
									dati += "null-";
								if (giornoDuplicato.getFascia2() != null) {
									dati += giornoDuplicato.getFascia2() + "-";
								} else
									dati += "null-";
								if (giornoDuplicato.getFascia3() != null) {
									dati += giornoDuplicato.getFascia3();
								} else
									dati += "null";

								if (i < giornoDto.getDuplicazioniGiornoDto().size())
									dati += "/";
								i++;
							}
						} else
							dati += "null,null-null-null-null-null,";
						if (giornoDto.getFerie() != null) {
							dati += "," + giornoDto.getFerie();
						} else
							dati += ",null";
						if (giornoDto.getMalattie() != null) {
							dati += "," + giornoDto.getMalattie() + ",";
						} else
							dati += ",null";
						if (giornoDto.getPermessi() != null) {
							dati += "," + giornoDto.getPermessi();
						} else
							dati += ",null";
						if (giornoDto.getNote() != null) {
							dati += "," + giornoDto.getNote() + ";";
						} else
							dati += ",null;";

					}
					writer.write(dati);
				}
			} catch (HttpMessageNotReadableException e) {
				LOGGER.log(Level.ERROR, e.getMessage());
				// throw new ServiceException(ServiceMessages.FORMATO_INCORRETTO);
				throw new HttpMessageNotReadableException(e.getMessage());
			} catch (IOException e) {
				LOGGER.log(Level.ERROR, e.getMessage());
				// throw new ServiceException(ServiceMessages.ERRORE_SALVATAGGIO_FILE);
				throw new IOException(e.getMessage());
			} catch (Exception e) {
				LOGGER.log(Level.ERROR, e.getMessage());
				// throw new ServiceException(ServiceMessages.ERRORE_GENERICO);
				throw new Exception(e.getMessage());
			}
		}
		LOGGER.log(Level.INFO, "Rapportino salvato con successo.");

	}

	public void appendNote(String path, String note) throws Exception {
		if (note != null) {
			try {
				Path filePath = Path.of(path);
				try (FileWriter writer = new FileWriter(filePath.toFile(), true)) {
					writer.append("\n" + note);
				}
			} catch (HttpMessageNotReadableException e) {
				LOGGER.log(Level.ERROR, e.getMessage());
				// throw new ServiceException(ServiceMessages.FORMATO_INCORRETTO);
				throw new HttpMessageNotReadableException(e.getMessage());
			} catch (IOException e) {
				LOGGER.log(Level.ERROR, e.getMessage());
				// throw new ServiceException(ServiceMessages.ERRORE_SALVATAGGIO_FILE);
				throw new IOException(e.getMessage());
			} catch (Exception e) {
				LOGGER.log(Level.ERROR, e.getMessage());
				// throw new ServiceException(ServiceMessages.ERRORE_GENERICO);
				throw new Exception(e.getMessage());
			}
		}
		LOGGER.log(Level.INFO, "Note salvate con successo.");
	}

	// metodi per la lettura e scrittura di un image in base64 nel file

	public void saveFileImage(String percorso, String base64) throws Exception {

		Path filePath = Path.of(percorso);
		try (FileWriter writer = new FileWriter(filePath.toFile())) {
			writer.write(base64);

		} catch (HttpMessageNotReadableException e) {
			LOGGER.log(Level.ERROR, e.getMessage());
			// throw new ServiceException(ServiceMessages.FORMATO_INCORRETTO);
			throw new HttpMessageNotReadableException(e.getMessage());
		} catch (IOException e) {
			LOGGER.log(Level.ERROR, e.getMessage());
			// throw new ServiceException(ServiceMessages.ERRORE_SALVATAGGIO_FILE);
			throw new IOException(e.getMessage());
		} catch (Exception e) {
			LOGGER.log(Level.ERROR, e.getMessage());
			// throw new ServiceException(ServiceMessages.ERRORE_GENERICO);
			throw new Exception(e.getMessage());
		}

		LOGGER.log(Level.INFO, "image salvata con successo.");
	}

	public DocumentResponse readFileImage(String percorso) throws Exception {

		creatFolder(percorso);

		DocumentResponse documentResponse = null;

		try (BufferedReader reader = new BufferedReader(new FileReader(percorso))) {

			documentResponse = covertStringInDocumentResponse(reader.readLine());

		} catch (Exception e) {
			LOGGER.log(Level.ERROR, e.getMessage());
			throw new Exception(e);
		}

		return documentResponse;
	}

	private DocumentResponse covertStringInDocumentResponse(String fileString) {

		return new DocumentResponse(fileString);

	}

}