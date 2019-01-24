package stage3.REL.io.file;

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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import stage3.REL.io.MyFileWriter;

public class FileWriter_MappedByteBuffer implements MyFileWriter {

    CharBuffer charBuffer = null;
    String charEncoding = null;
    MappedByteBuffer mappedByteBuffer = null;
    private static final Logger logger = Logger.getLogger("FileWriter_MappedByteBuffer");

	private void write(String sFullPath, String context) {
		// Write a file

		Path pathWrite = FileSystems.getDefault().getPath(sFullPath);

		if (Files.notExists(pathWrite, new LinkOption[]{LinkOption.NOFOLLOW_LINKS})) {

		    try {
				Files.createFile(pathWrite);
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}

		    try (FileChannel fileChannel = (FileChannel) Files.newByteChannel(
		    		pathWrite, EnumSet.of(
		    				StandardOpenOption.READ, 
		    				StandardOpenOption.WRITE, 
		    				StandardOpenOption.TRUNCATE_EXISTING))) {
		        mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, charBuffer.length());
		        if (mappedByteBuffer != null) {
		            logger.info("Writing to file...");
		            mappedByteBuffer.put(Charset.forName(charEncoding).encode(charBuffer));
		            logger.info("Done!");
		        }

		    } catch (IOException ioe) {
		        logger.log(Level.SEVERE, ioe.getMessage());
		        ioe.printStackTrace();
		    }
		}
	}

	@Override
	public void write(String sFullPath, Object context) {
		if( context instanceof String) {
			write(sFullPath, (String)context);
		}else if( context instanceof List) {
			write(sFullPath, ((List)context).toString());
		}
		
	}

}
