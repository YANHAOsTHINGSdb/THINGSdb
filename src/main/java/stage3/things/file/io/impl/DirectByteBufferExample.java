package stage3.things.file.io.impl;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import stage3.things.file.io.FileIO;

public class DirectByteBufferExample implements FileIO{

	public static void main (String [] args)
			throws Exception {

		long startTime = new Date().getTime();

		Path path = Paths.get("testfile");
		FileChannel fileChannel = FileChannel.open(path);
		// 非直接缓冲
		//ByteBuffer buffer = ByteBuffer.allocate(1024 * 10);
		// 直接缓冲
		ByteBuffer buffer = ByteBuffer.allocateDirect(1024 * 10);

		System.out.println("Is a direct buffer: " + buffer.isDirect());
		System.out.println("Buffer has a backing array: " + buffer.hasArray());
		System.out.println("Reading file... ");

		int noOfBytesRead = fileChannel.read(buffer);

		for (int i = 0; i < 25; i++) {

			while (noOfBytesRead != -1) {

				buffer.clear();
				noOfBytesRead = fileChannel.read(buffer);
			}

			buffer.clear();
			fileChannel.position(0);
			noOfBytesRead = fileChannel.read(buffer);
		}

		fileChannel.close();

		long endTime = new Date().getTime();
		System.out.println("");
		System.out.println("Time taken (millis): " + (endTime - startTime));
	}

	@Override
	public void write(String s写入内容, String 文件全路径) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public String read(String s対象文件全路径, long l開始地址, long i単位記録長度) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}
}