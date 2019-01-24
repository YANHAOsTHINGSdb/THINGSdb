package stage3.REL.io.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import stage3.REL.io.MyFileWriter;

public class FileWriter_BufferedWriter implements MyFileWriter {
	
	BufferedWriter bw = null;
	FileWriter fw = null;
	
	public void write(String sFullPath, List<String> context) {
		try {
			File file = new File(sFullPath);

			if (checkBeforeWritefile(file)) {
				bw = new BufferedWriter((Writer) new FileWriter(file));
				for(String sContext : (List<String>)context) {
					bw.write(sContext);
				}
				bw.close();
			} else {
				System.out.println("ファイルに書き込めません");
			}
		} catch (IOException e) {
			System.out.println(e);
		} finally {
			try {
				if (bw != null)
					bw.close();

				if (fw != null)
					fw.close();

			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	private static boolean checkBeforeWritefile(File file) {
		if (file.exists()) {
			if (file.isFile() && file.canWrite()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void write(String sFullPath, Object context) {
		if(context instanceof String) {
			write(sFullPath, new ArrayList().add(context));
		}else if(context instanceof List) {
			write(sFullPath, (List<String>)context);
		}

	}
}
