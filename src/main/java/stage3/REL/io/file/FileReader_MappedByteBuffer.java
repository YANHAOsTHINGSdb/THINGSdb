package stage3.REL.io.file;

import java.io.File;
import java.io.IOException;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.EnumSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import stage3.REL.io.MyFileReader;

public class FileReader_MappedByteBuffer implements MyFileReader {
	
    CharBuffer charBuffer = null;
    String charEncoding = null;
    MappedByteBuffer mappedByteBuffer = null;
    private static final Logger logger = Logger.getLogger("FileReader_MappedByteBuffer");
    
	@Override
	public Object read(String sFullPath) {
		// Read a file

		Path pathRead = null;
		try {
			pathRead = getFileURIFromClasspath(sFullPath);
		} catch (Exception e) {
			
			e.printStackTrace();
			return null;
		}

		if (Files.exists(pathRead, new LinkOption[]{LinkOption.NOFOLLOW_LINKS})) {
		    try (FileChannel fileChannel = (FileChannel) Files.newByteChannel(pathRead, EnumSet.of(StandardOpenOption.READ))) {
		        mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, fileChannel.size());
		        if (mappedByteBuffer != null) {
		            logger.info("Reading file...");
		            charBuffer = Charset.forName(charEncoding).decode(mappedByteBuffer);
		            logger.info("File content: " + charBuffer.toString());
		        }

		    } catch (IOException ioe) {
		        logger.log(Level.SEVERE, ioe.getMessage());
		        ioe.printStackTrace();
		    }

		}
		return charBuffer ;
	}
	
	
	private Path getFileURIFromClasspath(String fileName) throws Exception {
	    Path result = null;
	    String classpath = System.getProperty("java.class.path");
	    result = FileSystems.getDefault().getPath(classpath + File.separator + fileName);
	    return result;
	}
}
