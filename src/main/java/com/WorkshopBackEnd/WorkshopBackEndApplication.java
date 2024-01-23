package com.WorkshopBackEnd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


@SpringBootApplication
public class WorkshopBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorkshopBackEndApplication.class, args);
		 Scanner scanner = new Scanner(System.in);

	        // Raccogliere i nomi degli attributi
	        System.out.println("Inserisci il numero di attributi:");
	        int numberOfAttributes = Integer.parseInt(scanner.nextLine());
	        List<String> attributes = new ArrayList<>();
	        for (int i = 0; i < numberOfAttributes; i++) {
	            System.out.println("Inserisci il nome dell'attributo " + (i + 1) + ":");
	            attributes.add(scanner.nextLine());
	        }

	        // Raccogliere i dati per ogni attributo
	        List<List<String>> data = new ArrayList<>();
	        System.out.println("Inserisci i dati per ogni attributo (separati da virgola):");
	        for (String attribute : attributes) {
	            System.out.println("Dati per " + attribute + ":");
	            String[] values = scanner.nextLine().split(",");
	            List<String> dataList = new ArrayList<>();
	            for (String value : values) {
	                dataList.add(value.trim());
	            }
	            data.add(dataList);
	        }

	        // Creare un file Excel
	        try (Workbook workbook = new XSSFWorkbook()) {
	            Sheet sheet = workbook.createSheet("Dati Utente");

	            // Aggiungere i nomi degli attributi come intestazione
	            Row headerRow = sheet.createRow(0);
	            for (int i = 0; i < attributes.size(); i++) {
	                Cell cell = headerRow.createCell(i);
	                cell.setCellValue(attributes.get(i));
	            }

	            // Aggiungere i dati
	            int rowNum = 1;
	            int maxDataLength = data.stream().mapToInt(List::size).max().orElse(0);
	            for (int i = 0; i < maxDataLength; i++) {
	                Row row = sheet.createRow(rowNum++);
	                for (int j = 0; j < data.size(); j++) {
	                    Cell cell = row.createCell(j);
	                    List<String> dataList = data.get(j);
	                    if (i < dataList.size()) {
	                        cell.setCellValue(dataList.get(i));
	                    }
	                }
	            }

	            // Scrivere su file
	            try (FileOutputStream fileOut = new FileOutputStream("dati_utente.xlsx")) {
	                workbook.write(fileOut);
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	}

}



