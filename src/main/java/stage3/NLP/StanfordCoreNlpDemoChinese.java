package stage3.NLP;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.codec.binary.Base64;

import edu.stanford.nlp.coref.CorefCoreAnnotations;
import edu.stanford.nlp.coref.data.CorefChain;
import edu.stanford.nlp.io.IOUtils;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;

/**
 * @author Christopher Manning
 */
public class StanfordCoreNlpDemoChinese {

	private StanfordCoreNlpDemoChinese() {
	} // static main

	public static void main(String[] args) throws IOException {
		// set up optional output files
		PrintWriter out;
		if (args.length > 1) {
			out = new PrintWriter(args[1]);
		} else {
			out = new PrintWriter(System.out);
		}
		Properties props = new Properties();
		// /Users/haoyan/Desktop/history/20190110/SpringRestfulWebServicesCRUDExample/src/main/resources/stanfordnlp/StanfordCoreNLP-chinese.properties
		//InputStream inputSream = PublicName.class.getResourceAsStream("/stanfordnlp/StanfordCoreNLP-chinese.properties");
		//InputStreamReader inputStreamReader = new InputStreamReader(inputSream, "UTF8");
		props.load(IOUtils.readerFromString("StanfordCoreNLP-chinese.properties"));
		//props.load(inputStreamReader);
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

		Annotation document;
		if (args.length > 0) {
			document = new Annotation(IOUtils.slurpFileNoExceptions(args[0]));
		} else {
			// document = new Annotation("克林顿说，华盛顿将逐步落实对韩国的经济援助。金大中对克林顿的讲话报以掌声：克林顿总统在会谈中重申，他坚定地支持韩国摆脱经济危机。");
			document = new Annotation("自然语言是人类思维与交流的主要工具，是人类智慧的结晶");
		}

		pipeline.annotate(document);
		List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);

		int sentNo = 1;
		for (CoreMap sentence : sentences) {
			out.println("Sentence #" + sentNo + " tokens are:");
			for (CoreMap token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
				out.println(token.toShorterString("Text", "CharacterOffsetBegin", "CharacterOffsetEnd", "Index",
						"PartOfSpeech", "NamedEntityTag"));
			}

			out.println("Sentence #" + sentNo + " basic dependencies are:");
			out.println(sentence.get(SemanticGraphCoreAnnotations.BasicDependenciesAnnotation.class)
					.toString(SemanticGraph.OutputFormat.LIST));
			sentNo++;
		}

		// Access coreference.
		out.println("Coreference information");
		Map<Integer, CorefChain> corefChains = document.get(CorefCoreAnnotations.CorefChainAnnotation.class);
		if (corefChains == null) {
			return;
		}
		for (Map.Entry<Integer, CorefChain> entry : corefChains.entrySet()) {
			out.println("Chain " + entry.getKey());
			for (CorefChain.CorefMention m : entry.getValue().getMentionsInTextualOrder()) {
				// We need to subtract one since the indices count from 1 but the Lists start from 0
				List<CoreLabel> tokens = sentences.get(m.sentNum - 1).get(CoreAnnotations.TokensAnnotation.class);
				// We subtract two for end: one for 0-based indexing, and one because we want last token of mention not one following.
				out.println("  " + m + ":[" + tokens.get(m.startIndex - 1).beginPosition() + ", " +
						tokens.get(m.endIndex - 2).endPosition() + ')');
			}
		}
		IOUtils.closeIgnoringExceptions(out);
	}

	public static String compress1(String str) throws IOException {
		if (str == null || str.length() == 0) {
			return str;
		}
		System.out.println("String length : " + str.length());
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		GZIPOutputStream gzip = new GZIPOutputStream(out);
		gzip.write(str.getBytes());
		gzip.close();
		String outStr = out.toString("UTF-8");
		System.out.println("Output String lenght : " + outStr.length());
		System.out.println("Output : " + outStr.toString());
		return outStr;
	}

	public static String decompress1(String str) throws IOException {
		if (str == null || str.length() == 0) {
			return str;
		}
		System.out.println("Input String length : " + str.length());
		GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(str.getBytes("UTF-8")));
		BufferedReader bf = new BufferedReader(new InputStreamReader(gis, "UTF-8"));
		String outStr = "";
		String line;
		while ((line = bf.readLine()) != null) {
			outStr += line;
		}
		System.out.println("Output String lenght : " + outStr.length());
		return outStr;
	}

	public static void main2(String[] args) throws IOException {
		String string = "my data";
		System.out.println("after compress:");
		String compressed = compress(string);
		System.out.println(compressed);
		System.out.println("after decompress:");
		String decomp = decompress(compressed);
		System.out.println(decomp);
	}

	public static String compress(String str) throws IOException {
		if (str == null || str.length() == 0) {
			return str;
		}
		System.out.println("String length : " + str.length());
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		GZIPOutputStream gzip = new GZIPOutputStream(out);
		gzip.write(str.getBytes());
		gzip.close();
		String outStr = new String(Base64.encodeBase64(out.toByteArray()));
		System.out.println("Output String lenght : " + outStr.length());
		System.out.println("Output : " + outStr.toString());
		return outStr;
	}

	public static String decompress(String str) throws IOException {
		if (str == null || str.length() == 0) {
			return str;
		}
		System.out.println("Input String length : " + str.length());
		GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(Base64.decodeBase64(str)));
		String outStr = "";
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buffer = new byte[256];
		int n;
		while ((n = gis.read(buffer)) >= 0) {
			out.write(buffer, 0, n);
		}
		System.out.println("Output String lenght : " + outStr.length());
		return new String(out.toByteArray());
	}

	public static void main1(String[] args) throws IOException {
		String string = "my data";
		System.out.println("after compress:");
		String compressed = compress(string);
		System.out.println(compressed);
		System.out.println("after decompress:");
		String decomp = decompress(compressed);
		System.out.println(decomp);
	}

}
