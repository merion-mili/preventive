package al.mili.preventive.db.model.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.io.UncheckedIOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CsvReader implements Serializable{
	 private static final String SEPARATOR = ";";
	 
	    private final Reader source;
	 
	    public CsvReader(Reader source) {
	        this.source = source;
	    }
	    
	    public List<List<String>> readRecords() {
	        try (BufferedReader reader = new BufferedReader(source)) {
	            return reader.lines()
	                    .map(line -> Arrays.asList(line.split(SEPARATOR)))
	                    .collect(Collectors.toList());
	        } catch (IOException e) {
	            throw new UncheckedIOException(e);
	        }
	    }  
	}

