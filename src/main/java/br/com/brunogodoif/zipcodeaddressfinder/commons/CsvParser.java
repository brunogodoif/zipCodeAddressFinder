package br.com.brunogodoif.zipcodeaddressfinder.commons;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CsvParser {
    public static List<Map<String, String>> parse(String filePath) throws IOException {
        try (Reader reader = new FileReader(filePath)) {
            CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader());
            List<Map<String, String>> records = new ArrayList<>();
            for (CSVRecord record : parser) {
                records.add(record.toMap());
            }
            return records;
        }
    }
}