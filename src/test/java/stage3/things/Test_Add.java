package stage3.things;

import java.util.ArrayList;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import lombok.Data;
import stage3.log.MyLogger;
import stage3.things.json.Json2Map;
@Data
public class Test_Add{
	String sCallPath = "";
	MyLogger myLogger = new MyLogger();

	public static void main(String[] args) throws Exception{
		Test_Add test_Add = new Test_Add("");
		test_Add.myLogger.printCallMessage(test_Add.getSCallPath(),"Add.main()");

		THINGSdb db = new THINGSdb("main()");
		Json2Map json = new Json2Map();

        // 起動時にオプションを指定しなかった場合は、このサンプルデータを使用する。
        String script = "{ \"key1\" : \"val1\", \"key2\" : \"val2\", \"key3\" : { \"ckey1\" : \"cval1\", \"ckey2\" : [ \"cval2-1\", \"cval2-2\" ] } }";
        if (args.length > 0) {
            java.io.File f = new java.io.File(args[0]);
            if (f.exists() && f.isFile()) {
                // 起動時の第１引数がファイルならファイルから読み込み（java 6 対応版なので、try-with-resources すら使えません。実際は、こんな書き方せずにちゃんとエラー処理してください）
                byte[] buf = new byte[new Long(f.length()).intValue()];
                java.io.FileInputStream fin = null; try { fin = new java.io.FileInputStream(f); fin.read(buf); } catch (Exception ex) { throw ex; } finally { if (fin != null) { fin.close(); }}
                script = args.length > 1 ? new String(buf, args[1]) : new String(buf); // ファイルの文字コードがシステムの文字コードと異なる場合は、第２引数で指定。例えば「UTF-8」や「JISAutoDetect」など。
            } else {
                script = args[0]; // ファイルでなければ、第１引数の文字列をそのまま JSON として扱う
            }
        }
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");
        // ScriptEngine の eval に JSON を渡す時は、括弧で囲まないと例外が発生します。eval はセキュリティ的には好ましくないので、安全であることが不明なデータを扱うことは想定していません。
        // 外部ネットワークと連携するプログラムで使用しないでください。
        Object obj = engine.eval(String.format("(%s)", script));
        // Rhino は、jdk1.6,7までの JavaScript エンジン。jdk1.8は「jdk.nashorn.api.scripting.NashornScriptEngine」
        Map<String, Object> 追加処理对象map = json.jsonToMap(obj, engine.getClass().getName().equals("com.sun.script.javascript.RhinoScriptEngine"));

        db.事前準備();
        db.追加_by数据(追加処理对象map, new ArrayList());

	}

	public Test_Add(String sCallPath) {
		this.sCallPath = sCallPath;
	}


}
