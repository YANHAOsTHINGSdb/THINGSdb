package stage3.REL.io.file;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import stage3.REL.io.MyFileReader;

public class FileReader_BufferedReader implements MyFileReader {
	BufferedReader br = null;
	FileReader fr = null;
	List<String> resultList = null;

	@Override
	public Object read(String sFullPath) {
		try {
			//br = new BufferedReader(new FileReader(sFullPath));
			fr = new FileReader(sFullPath);
			br = new BufferedReader(fr);
			resultList = new ArrayList();
			String sCurrentLine;

			while ((sCurrentLine = br.readLine()) != null) {
				resultList.add(sCurrentLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
			
		} finally {
			try {
				if (br != null)
					br.close();

				if (fr != null)
					fr.close();

			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return resultList;
	}
}
