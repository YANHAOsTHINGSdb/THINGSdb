package stage3.things.file.io.impl;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import stage3.things.file.文件記録;
import stage3.things.file.io.FileIO;
import sun.misc.Cleaner;

public class MappedByteBufferExampleOld  implements FileIO{

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

        File file = new File(s対象文件全路径);
        // long len = file.length();
        byte[] ds = new byte[(int) i単位記録長度];
        String sResult = null;

        //补丁20180803
        if(l開始地址 < 0){
        	l開始地址 = 0;
        }

        RandomAccessFile randomAccessFile = null;
        FileChannel fileChannel = null;
        try {
        	// 创建从中读取和向其中写入（可选）的随机访问文件流，
        	// 该文件由 File 参数指定。将创建一个新的 FileDescriptor 对象来表示此文件的连接。
        	randomAccessFile = new RandomAccessFile(file, "r");
        	fileChannel = randomAccessFile.getChannel();

            if(l開始地址 >= randomAccessFile.length()){
            	return sResult;
            }
        	// 映射的字节缓冲区是通过 FileChannel.map 方法创建的。
        	// 此类用特定于内存映射文件区域的操作扩展 ByteBuffer 类。
            MappedByteBuffer mappedByteBuffer = fileChannel.map(
            		FileChannel.MapMode.READ_ONLY,
            		0,
            		randomAccessFile.length()
            		);

            int i=0;
            for (long offset = l開始地址;
            		offset < l開始地址 + i単位記録長度 && offset<randomAccessFile.length();
            		offset++,i++){
            	byte b = mappedByteBuffer.get((int)offset);
            	ds[i] = b;
            }
            sResult = new String(ds);

            // 回收缓冲区
            // 每次都会回收缓冲区，虽然更加安全了，但也大大降低了速度。
            // 需要一个回收的方案
            Cleaner cleaner = ((sun.nio.ch.DirectBuffer) mappedByteBuffer).cleaner();
            if(cleaner != null) {
            	cleaner.clean();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	try {
				if(fileChannel != null) {fileChannel.close();}
				if(randomAccessFile != null) {randomAccessFile.close();}
//				System.out.println("File Closed");
			} catch (IOException e) {
				System.out.println("我擦，尝试关闭文件时，出了点问题！");
				e.printStackTrace();
			}
        }
		return sResult;
	}

}
