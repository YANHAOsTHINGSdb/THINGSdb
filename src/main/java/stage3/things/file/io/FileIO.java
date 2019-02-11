package stage3.things.file.io;

public interface FileIO {
	void write(String s写入内容, String 文件全路径);
	String read(String s対象文件全路径, long l開始地址, long i単位記録長度);
}
