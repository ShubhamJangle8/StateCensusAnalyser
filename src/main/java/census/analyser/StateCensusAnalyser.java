package census.analyser;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.StreamSupport;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

public class StateCensusAnalyser {

	public int loadIndiaCensusData(String indiaCensusCsvFilePath) throws CensusAnalyserException {
		try {
			Reader reader = Files.newBufferedReader(Paths.get(indiaCensusCsvFilePath));
			CsvToBeanBuilder<CSVStateCensus> csvBuilder = new CsvToBeanBuilder<>(reader);
			csvBuilder.withType(CSVStateCensus.class);
			csvBuilder.withIgnoreLeadingWhiteSpace(true);
			CsvToBean<CSVStateCensus> csvToBean = csvBuilder.build();
			Iterator<CSVStateCensus> censusCsvIterator = csvToBean.iterator();
			Iterable<CSVStateCensus> csvIterable = () -> censusCsvIterator;
			int numberOfEntries = (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
			return numberOfEntries;
		} catch (IOException e) {
			throw new CensusAnalyserException(e.getMessage(),
											  CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
		}
	}

	public static void main(String[] args) {
		System.out.println("Welcome to State Census Analyser");
	}

}
