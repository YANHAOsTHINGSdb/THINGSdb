package stage3.things.file.io.impl;

import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

import stage3.things.file.文件記録;
import stage3.things.file.io.FileIO;

public class MappedByteBufferExample  implements FileIO{
	private static final int K = 0x400;

	private static final String FILE_PATH = "c:/nio.txt";

	private void close(Closeable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
				closeable = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void testWrite() {
		RandomAccessFile raf = null;
		FileChannel fc = null;
		Charset charset = Charset.defaultCharset();
		CharsetEncoder charsetEncoder = charset.newEncoder();

		try {
			raf = new RandomAccessFile(FILE_PATH, "rw");
			fc = raf.getChannel();
			MappedByteBuffer mbb = fc.map(MapMode.READ_WRITE, 0, K);
			CharBuffer cb = CharBuffer.wrap("A".toCharArray());
			charsetEncoder.encode(cb, mbb, true);
			mbb.flip(); //encode后，mbb的指针不在0的地方，所以要flip
			fc.position(0);//从头开始写，这行代码可能多余，因为position可能一开始就在0
			for (int i = 0; i < K; i++) {
				fc.write(mbb);
				mbb.flip(); //write后，mbb的指针不在0的地方，所以要flip
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			close(fc);
			close(raf);
		}
	}

	public void testRead() {
		RandomAccessFile raf = null;
		FileChannel fc = null;
		Charset charset = Charset.defaultCharset();
		CharsetDecoder charsetDecoder = charset.newDecoder();
		StringBuilder sb = new StringBuilder();

		try {
			raf = new RandomAccessFile(FILE_PATH, "rw");
			fc = raf.getChannel();
			MappedByteBuffer mbb = fc.map(MapMode.READ_WRITE, 0, K);
			fc.position(0);//从头开始读，这行代码可能多余，因为position可能一开始就在0
			while (fc.read(mbb) != -1) {
				mbb.flip();//read后，mbb的指针不在0的地方，所以要flip
				CharBuffer cb = charsetDecoder.decode(mbb);
				mbb.flip();//decode后，mbb的指针不在0的地方，所以要flip
				sb.append(cb);
			}
			System.out.println(sb);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			close(fc);
			close(raf);
		}
	}

	@Override
	public void write(String s写入内容, String 文件全路径) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public String read(String s対象文件全路径, long l開始地址, long i単位記録長度) {

        // 文件不存在的话还是先退出吧
        if (! 文件記録.existsFile(s対象文件全路径)) {
            return null;
        }

        // 如果i単位記録長度是【0】还是先退出吧
        if (i単位記録長度 ==0L) {
            return null;
        }

        //补丁20180803
        if(l開始地址 < 0){
        	l開始地址 = 0;
        }

        BufferedRandomAccessFile raf = null;
		FileChannel fc = null;
		Charset charset = Charset.defaultCharset();
		CharsetDecoder charsetDecoder = charset.newDecoder();
		StringBuilder sb = new StringBuilder();

		try {
			raf = new BufferedRandomAccessFile(s対象文件全路径, "rw");
			fc = raf.getChannel();
			// MappedByteBuffer mbb = fc.map(MapMode.READ_WRITE, 0, K);
			// FileChannel提供了map方法来把文件影射为内存映像文件
			MappedByteBuffer mbb = fc.map(MapMode.READ_WRITE, l開始地址, i単位記録長度);
			ByteBuffer buf = ByteBuffer.allocate(K);
			// 3、将通道中的数据存入缓冲区
			while (fc.read(buf) != -1) {
				buf.flip();
				// 4、将缓冲区中的数据写入通道中
				CharBuffer cb = charsetDecoder.decode(mbb);
				sb.append(cb);
				buf.clear();// 清空缓冲区
			}
			fc.position(0);//从头开始读，这行代码可能多余，因为position可能一开始就在0
//			System.out.println(mbb.toString());
//			long i = 0L;
//			// 直到Buffer中已经没有尚未写入通道的字节。
//			while (fc.read(mbb) != -1) {
//				mbb.flip();//read后，mbb的指针不在0的地方，所以要flip
//				CharBuffer cb = charsetDecoder.decode(mbb);
//				mbb.flip();//decode后，mbb的指针不在0的地方，所以要flip
//				sb.append(cb);
//			}
//	        while (mbb.hasRemaining()) {
//	            System.out.print((char)mbb.get());
//	        }
//			System.out.println(sb);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			close(fc);
			close(raf);
		}
		return sb.toString();
	}

	static int length = 0x8FFFFFF; // 128 Mb

	public static void main(String[] args) throws Exception {
		MappedByteBuffer out = new RandomAccessFile("test.dat", "rw").getChannel()
				.map(FileChannel.MapMode.READ_WRITE, 0, length);
		for (int i = 0; i < length; i++)
			out.put((byte) 'x');
		System.out.println("Finished writing");
		for (int i = length / 2; i < length / 2 + 6; i++)
			System.out.print((char) out.get(i));
	}
}
