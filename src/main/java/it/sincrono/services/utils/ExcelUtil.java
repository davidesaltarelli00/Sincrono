package it.sincrono.services.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import it.sincrono.entities.Rapportino;
import it.sincrono.services.costants.ServiceMessages;
import it.sincrono.services.exceptions.ServiceException;
import it.sincrono.services.impl.RapportinoServiceImpl;

@Component
public class ExcelUtil {

	private static final Logger LOGGER = LogManager.getLogger(RapportinoServiceImpl.class);

	public int toExcel(List<Rapportino> rapportini, int rowNum, boolean append, String EXCELPATH)
			throws ServiceException {
		try {
			FileInputStream fileInputStream = null;
			if (append) {
				fileInputStream = new FileInputStream(EXCELPATH);
			}
			Workbook workbook = (append ? new XSSFWorkbook(fileInputStream) : new XSSFWorkbook());
			Sheet sheet = (append ? workbook.getSheet("Dati Excel") : workbook.createSheet("Dati Excel"));
			RichTextString str = null;
			int giornoprecedente=0;
			int giornofinaleprecendnte=0;
			int conttotalesenzafeste=0;
			/** SETUP CALENDAR PER I CONTROLLI SU SABATI E DOMENICHE **/
			Calendar calendar = Calendar.getInstance();
			int anno = rapportini.get(0).getAnno();
			int mese = rapportini.get(0).getMese();
			calendar.set(anno, mese - 1, 1);
			int numeroGiorniNelMese = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

			/** SETUP PER LE VARIABILI DEI CALCOLI PER OGNI RAPPORTINO **/
			int contvuoto = 0;
			int contpieno = 0;
			double totale = 0.0;
			double tot = 0.0;
			int ferie = 0;
			int malattie = 0;
			int ex_fs = 0;
			double rol = 0;
			double f1_f2_f3 = 0.0;
			double straordinariSum = 0.0;

			/** SETUP DEGLI GLI OGGETTI PER LA COSTRUZIONE DELL'EXCEL **/
			sheet.setColumnWidth(0, 30 * 256);
			for (int i = 1; i <= 31; i++) {
				sheet.setColumnWidth(i, 5 * 256);
			}
			for (int i = 33; i <= 37; i++) {
				sheet.setColumnWidth(i, 8 * 256);
			}
			Row row = null;
			Cell cell = null;

			/** DICHIARAZIONE PER PIU STILI PER LE CELLE CON VARI COLORI **/
			CellStyle cellStyleDefault = createCellStyle(workbook, IndexedColors.WHITE, false);
			CellStyle cellStyleAnnoMese = createCellStyle(workbook, IndexedColors.GOLD, false);
			CellStyle cellStyleGiorni = createCellStyle(workbook, IndexedColors.GREEN, false);
			CellStyle cellStyleGiorniFinali = createCellStyle(workbook, IndexedColors.GREY_80_PERCENT, false);
			//CellStyle cellStyleGiorniFestivi = createCellStyle(workbook, IndexedColors.RED, false);
			CellStyle cellStyleFerieLabel = createCellStyle(workbook, IndexedColors.YELLOW, false);
			//cellStyleFerieLabel.setRotation((short) -45);
			CellStyle cellStyleTotaleLabel = createCellStyle(workbook, IndexedColors.YELLOW, false);
		//	cellStyleTotaleLabel.setRotation((short) -45);
			CellStyle cellStyleMalattiaLabel = createCellStyle(workbook, IndexedColors.LIGHT_GREEN, false);
			//cellStyleMalattiaLabel.setRotation((short) -45);
			CellStyle cellStylePermessiLabel = createCellStyle(workbook, IndexedColors.LIGHT_ORANGE, false);
			//cellStylePermessiLabel.setRotation((short) -45);
			CellStyle cellStyleRolLabel = createCellStyle(workbook, IndexedColors.LIGHT_TURQUOISE, false);
			//cellStyleRolLabel.setRotation((short) -45);

			CellStyle cellStyleFerie = createCellStyle(workbook, IndexedColors.YELLOW, false);
			CellStyle cellStyleTotale = createCellStyle(workbook, IndexedColors.YELLOW, false);
			CellStyle cellStyleMalattia = createCellStyle(workbook, IndexedColors.LIGHT_GREEN, false);
//			CellStyle cellStylePermessi = createCellStyle(workbook, IndexedColors.LIGHT_ORANGE, false);
//			CellStyle cellStyleRol = createCellStyle(workbook, IndexedColors.LIGHT_TURQUOISE, false);

			CellStyle cellStyleSABDOM = createCellStyle(workbook, IndexedColors.RED, false);
			CellStyle cellStyleNODAY = createCellStyle(workbook, IndexedColors.LIGHT_CORNFLOWER_BLUE, false);

			int cellNum = 0;
			String nomeCognomePrecedente = "", nomeCognomeAttuale = "";
			row = sheet.createRow(rowNum);
			row.setHeightInPoints(18);
			cell = row.createCell(0);
			cell.setCellStyle(cellStyleAnnoMese);
			cell.setCellValue(rapportini.get(0).getAnno());

			cell = row.createCell(1);
			DateFormatSymbols symbols = new DateFormatSymbols();
			cell.setCellValue(symbols.getMonths()[rapportini.get(0).getMese() - 1]);
			cell.setCellStyle(cellStyleAnnoMese);

			for (int i = 2; i < 31; i++) {
				cell = row.createCell(i);
			}
			cell = row.createCell(32);
			cell.setCellStyle(cellStylePermessiLabel);
			cell.setCellValue("TOT");

			cell = row.createCell(33);
			cell.setCellStyle(cellStyleFerieLabel);
			cell.setCellValue("FERIE");

			cell = row.createCell(34);
			cell.setCellStyle(cellStyleMalattiaLabel);
			cell.setCellValue("MALATTIE");
		
			
			cell = row.createCell(35);
			cell.setCellStyle(cellStylePermessiLabel);
			cell.setCellValue("EX-FS");

			cell = row.createCell(36);
			cell.setCellStyle(cellStyleRolLabel);
			cell.setCellValue("ROL");

			sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 1, 31));

			row = sheet.createRow(++rowNum);
			row.setHeightInPoints(30);
			for (int i = 1; i < 32; i++) {
				cell = row.createCell(i);
				cell.setCellValue(i);
				calendar.set(anno, mese - 1, i);
				int giornoweek = calendar.get(Calendar.DAY_OF_WEEK);
				if(giornoweek==1 || giornoweek==7 || DateUtil.checkFestivitàNazionaleRapportino(mese,anno,i)) {
				cell.setCellStyle(cellStyleSABDOM);
				}else {
					
					cell.setCellStyle(cellStyleGiorni);
					conttotalesenzafeste++;
				}
				int giornofinale = calendar.get(Calendar.DAY_OF_MONTH);
			
				
				if(giornofinale>giornoprecedente) {
					giornoprecedente=giornofinale;
					
				}else {
					cell.setCellStyle(cellStyleGiorniFinali);
					conttotalesenzafeste--;
					giornofinaleprecendnte=giornoprecedente;
				}
				if (i == 31) {
					cell = null;
					cell = row.createCell(++i);
					cell.setCellStyle(cellStylePermessiLabel);
					cell = row.createCell(++i);
					cell.setCellStyle(cellStyleFerieLabel);
					cell = row.createCell(++i);
					cell.setCellStyle(cellStyleMalattiaLabel);
					cell = row.createCell(++i);
					cell.setCellStyle(cellStylePermessiLabel);
					cell = row.createCell(++i);
					cell.setCellStyle(cellStyleRolLabel);
					sheet.addMergedRegion(new CellRangeAddress(rowNum - 1, rowNum, 32, 32));
					sheet.addMergedRegion(new CellRangeAddress(rowNum - 1, rowNum, 33, 33));
					sheet.addMergedRegion(new CellRangeAddress(rowNum - 1, rowNum, 34, 34));
					sheet.addMergedRegion(new CellRangeAddress(rowNum - 1, rowNum, 35, 35));
					sheet.addMergedRegion(new CellRangeAddress(rowNum - 1, rowNum, 36, 36));
					sheet.addMergedRegion(new CellRangeAddress(rowNum - 1, rowNum, 0, 0));
				}

			}
			for (Rapportino rapportino : rapportini) {

				/** SET DELLA COMBINAZIONE NOME-COGNOME DELLA N ITERAZIONE **/
				nomeCognomeAttuale = rapportino.getAnagrafica().getNome() + " "
						+ rapportino.getAnagrafica().getCognome();

				/**
				 * SE IL NOME VECCHIO E QUELLO DELLA N ITERAZIONE SONO DIVERSI ALLORA E' UNA
				 * NUOVA RIGA
				 **/
				if (!nomeCognomePrecedente.equals(nomeCognomeAttuale)) {
					nomeCognomePrecedente = nomeCognomeAttuale;
					row = sheet.createRow(++rowNum);
					row.setHeightInPoints(15);
					cellNum = 0; // RICOMINCIA A CONTARE DA 0 CON LE CELLE
					cell = row.createCell(cellNum);
					cell.setCellStyle(cellStyleDefault);
					cell.setCellValue(nomeCognomePrecedente);

				}
				
				cell = row.createCell(rapportino.getGiorno());
				
				calendar.set(anno, mese - 1, cellNum);
				int giorno = calendar.get(Calendar.DAY_OF_WEEK);
				/**
				 * CONTROLLO SE IL GIORNO ATTUALE E' PIU GRANDE DELL'ULTIMO GIORNO DEL MESE
				 * SELEZIONATO
				 **/
				if (giorno > numeroGiorniNelMese) {
					// SE CI SONO GIORNI INESISTENTI NEL MESE CORRENTE
					cell.setCellStyle(cellStyleNODAY);
					cell.setCellValue("Q");
				} else {
					if (rapportino.getFascia1() != null) {
						f1_f2_f3 += rapportino.getFascia1();
					}
					if (rapportino.getFascia2() != null) {
						f1_f2_f3 += rapportino.getFascia2();
					}
					if (rapportino.getFascia3() != null) {
						f1_f2_f3 += rapportino.getFascia3();
					}
					
						calendar.set(anno, mese - 1, rapportino.getGiorno());
						int giornoweek = calendar.get(Calendar.DAY_OF_WEEK);
						if(giornoweek==1 || giornoweek==7 || DateUtil.checkFestivitàNazionaleRapportino(mese,anno,rapportino.getGiorno()) ) {
							if(rapportino.getOre() !=null ) {
							cell.setCellValue(rapportino.getOre() );
							}
							if(rapportino.getPermessiRole() !=null) {
								System.out.println("role"+rapportino.getPermessiRole());
								cell.setCellValue(rapportino.getPermessiRole() );
								rol+=rapportino.getPermessiRole();
								cell.setCellStyle(cellStyleRolLabel);
							}
							if(rapportino.getOre() !=null ) {
							f1_f2_f3+=rapportino.getOre();
							}
							cell.setCellStyle(cellStyleSABDOM);
							
						System.out.println("1");
							}else {
								cell.setCellStyle(cellStyleDefault);
								System.out.println("2");
								if(rapportino.getOre() !=null ) {
									cell.setCellValue(rapportino.getOre() );
									System.out.println("3");
									}
								if(rapportino.getPermessiRole() !=null) {
										cell.setCellValue(rapportino.getPermessiRole() );
										rol+=rapportino.getPermessiRole();
										cell.setCellStyle(cellStyleRolLabel);
									
								}
								
								System.out.println("4");
								if(rapportino.getMalattie()!=null) {
								if(rapportino.getMalattie()==true ) {
									malattie++;
								}
								}
								if(rapportino.getFerie()!=null) {
								if(rapportino.getFerie()==true ) {
									ferie++;
								}
								}
								if(rapportino.getPermessiExfestivita()!=null) {
									if(rapportino.getPermessiExfestivita()==true ) {
										ex_fs++;
									}
									}
								if(rapportino.getOre() !=null ) {
								if(rapportino.getOre()<=8) {
								totale+=rapportino.getOre();
								contpieno+=1;
								}
								}
								if(rapportino.getOre()==null) {
								contvuoto+=1;
								}
							}
			
					
				}
					tot=contvuoto +conttotalesenzafeste-(totale/8);
				straordinariSum += f1_f2_f3;
				f1_f2_f3 = 0.0; // RICOMINCIA A CONTARE LE ORE DI STRAORDINARIO DELLA GIORNATA
				contvuoto=0;
				contpieno=0;
			//	if (rapportino.getGiorno() == 31) {
					str = new XSSFRichTextString("Di cui " + straordinariSum + " ore di straordinari.");
					Comment comment = sheet.createDrawingPatriarch().createCellComment(new XSSFClientAnchor());
					comment.setString(str);

					cell = row.createCell(32);
					cell.setCellStyle(cellStyleTotale);
					cell.setCellValue(tot);
					cell.setCellComment(comment);

					cell = row.createCell(33);
					cell.setCellStyle(cellStyleDefault);
					cell.setCellValue(ferie);

					cell = row.createCell(34);
					cell.setCellStyle(cellStyleDefault);
					cell.setCellValue(malattie);

					cell = row.createCell(35);
					cell.setCellStyle(cellStyleDefault);
					cell.setCellValue(ex_fs);

					cell = row.createCell(36);
					cell.setCellStyle(cellStyleDefault);
					cell.setCellValue(rol);

					/** RESET DELLE VARIABILI **/
					cellNum = 0;
					tot = 0.0;
				//	ferie = 0;
					//malattie = 0;
				//ex_fs = 0;
					//rol = 0;
				//}
			/*	cell = row.createCell(32);
				cell.setCellStyle(cellStyleTotale);
				cell.setCellValue(tot);*/
			}
			try (FileOutputStream outputStream = new FileOutputStream(EXCELPATH)) {
				workbook.write(outputStream);
			}
			workbook.close();
			return rowNum;
		} catch (IOException e) {
			LOGGER.log(Level.ERROR, e.getMessage());
			throw new ServiceException(ServiceMessages.IMPOSSIBILE_SCRIVERE_EXCEL);
		} catch (Exception e) {
			LOGGER.log(Level.ERROR, e.getMessage());
			throw new ServiceException(ServiceMessages.ERRORE_GENERICO);

		}
	}

	public static String excelToBase64(String filePath) throws ServiceException {
		File file = new File(filePath);
		FileInputStream fileInputStream;
		try {
			fileInputStream = new FileInputStream(file);

			byte[] bytes = new byte[(int) file.length()];
			fileInputStream.read(bytes);
			fileInputStream.close();

			// Encode the byte array to Base64
			byte[] encodedBytes = Base64.encodeBase64(bytes);

			return new String(encodedBytes);
		} catch (FileNotFoundException e) {
			LOGGER.log(Level.ERROR, e.getMessage());
			throw new ServiceException(ServiceMessages.IMPOSSIBILE_SCRIVERE_EXCEL_CONVERTITO);
		} catch (IOException e) {
			LOGGER.log(Level.ERROR, e.getMessage());
			throw new ServiceException(ServiceMessages.ERRORE_GENERICO);
		}
	}

	private static CellStyle createCellStyle(Workbook workbook, IndexedColors bgColor, boolean checkGiorni) {
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyle.setBorderTop(BorderStyle.MEDIUM);
		cellStyle.setBorderLeft(BorderStyle.MEDIUM);
		cellStyle.setBorderRight(BorderStyle.MEDIUM);
		cellStyle.setBorderBottom(BorderStyle.MEDIUM);

		// Set the background color using IndexedColors
		cellStyle.setFillForegroundColor(bgColor.index);
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		Font fontStyle = workbook.createFont();
		if ((IndexedColors.GREEN.getIndex() == bgColor.index) && checkGiorni)
			fontStyle.setColor(bgColor.index);
		fontStyle.setFontName("Times New Roman");
		cellStyle.setFont(fontStyle);
		return cellStyle;
	}
}
