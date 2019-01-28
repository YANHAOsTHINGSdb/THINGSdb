package stage3.NLP.NLPreader;

import org.apache.commons.lang3.StringUtils;

import lombok.Data;

@Data
public class NLPreader extends NLPreaderBase{

	private Node node;
	public NLPreader(String sParse对象) {
		// TODO 自動生成されたコンストラクター・スタブ
	}
	public static void main(String[] args) {
		String sParse对象 = "(ROOT (IP (ADVP (AD 自然)) (NP (NN 语言)) (VP (VP (VC 是) (NP (DNP (NP (NN 人类) (NN 思维) (CC 与) (NN 交流)) (DEG 的)) (ADJP (JJ 主要)) (NP (NN 工具)))) (PU ，) (VP (VC 是) (NP (DNP (NP (NN 人类) (NN 智慧)) (DEG 的)) (NP (NN 结晶))))) (PU 。)))";

		NLPreader nlpReader = new NLPreader(sParse对象);
		nlpReader.node = new Node();
		nlpReader.parse(nlpReader.node, null);

	}

	void parse(Node father, String s累计读入文本){

		super.parse(father, s累计读入文本);
		if(StringUtils.isEmpty(sChar)) return; // 已经到最后，退出
		Node node = father;

		switch(sChar){
		case "(":
			node = createNewNode(father, s累计读入文本, sChar);
		        s累计读入文本 = null;
		        break;
		case ")":
			node = backToFather(father, s累计读入文本);
		        s累计读入文本 = null;
		        break;
		case " ":
			node = addToNewNode(father, s累计读入文本);
		        s累计读入文本 = null;
		        break;
		default:
			// 如果以上都不是
		        // 累计吧
		        s累计读入文本 = s累计读入文本 == null ? sChar  :  s累计读入文本 + sChar;
		}

		if(node == null) return;

		parse(node, s累计读入文本);

		}
	private Node addToNewNode(Node father, String s累计读入文本) {
		if(father.isEmpty()){
		     return null;
		}
		father.sVar = s累计读入文本;

		return father;
	}

	private Node createNewNode(Node father, String s累计读入文本, String sChar) {
		// TODO 自動生成されたメソッド・スタブ

		if( father.isEmpty() && StringUtils.isEmpty(s累计读入文本) && !StringUtils.isEmpty(sChar)) {
			if(sChar.equals("(")) {
				parse(father, s累计读入文本);
				return father;
			}
		}

		Node node = new Node();
		node.sTYPE = s累计读入文本;
		if(father.isEmpty()) {
			father = node;
		}else {
			father.sonNodeList.add(node);
			node.fatherNode = father;
		}
		return node;
	}
	private Node backToFather(Node father, String s累计读入文本) {
		if (father.isEmpty()){
		     return null;
		}
		if(! StringUtils.isEmpty(s累计读入文本)){
		     father.sTYPE = s累计读入文本;
		}

		if(father.fatherNode != null){
		    return father.fatherNode;
		}

		// 表示解析已经结束
		return null;
	}
}
