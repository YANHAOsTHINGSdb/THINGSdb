package stage3.NLP.NLPreader;

import org.apache.commons.lang3.StringUtils;

public class NLPreaderBase {
	String sParse对象;
	Integer iLoc;
	String sChar;

	void parse(Node father, String s累计读入文本) {
		if (StringUtils.isEmpty(s累计读入文本)) {
			return;
		}
		if (iLoc >= sParse对象.length()) {
			sChar = null;
			return;
		}
		sChar = sParse对象.substring(iLoc, iLoc + 1);
		iLoc++;
	}
}
