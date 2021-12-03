import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;

public class GetLogsExtension {

	// Reads the file, extracts the 'nm' key for file name and adds it to Set to avoid duplicates
	public static LinkedHashSet<String> processFile(String file) throws Exception {
		LinkedHashSet<String> extFileUniq = new LinkedHashSet<>();
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;

		while ((line = br.readLine()) != null) {
			// Converts JSON String to Map object to retrieve keys easily
			Map<String, String> map = new ObjectMapper().readValue(line, HashMap.class);
			String fileExt = map.get("nm");
			extFileUniq.add(fileExt);
		}

		br.close();

		return extFileUniq;

	}

	// Gets all available extensions
	public static List<String> getAllExtension(List<String> listExtFileUniq) {
		List<String> listExt = new ArrayList<>();

		for (int i = 0; i < listExtFileUniq.size(); i++) {
			String extension = listExtFileUniq.get(i).substring(listExtFileUniq.get(i).indexOf(".") + 1);
			listExt.add(extension);
		}

		return listExt;
	}

	public static void main(String[] args) throws Exception {

		//Directory of the log file
		String file = "C:\\Users\\testuser\\Documents\\Cisco_Interview\\log_file.log";
		
		List<String> listExtFileUniq = new ArrayList<>(processFile(file));
		List<String> listExt = getAllExtension(listExtFileUniq);
		List<String> listFinal = new ArrayList<>();

		// Gets Unique extensions
		List<String> extUnique = listExt.stream().distinct().collect(Collectors.toList());

		// Counts the number of unique filenames extensions and add to List
		for (String ext : extUnique)
			listFinal.add((ext + ": " + Collections.frequency(listExt, ext)));

		// Prints the list of unique filenames extensions and the count
		System.out.println("The final Output is: " + listFinal);

	}
}
