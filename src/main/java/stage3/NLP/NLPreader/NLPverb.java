package stage3.NLP.NLPreader;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import lombok.Data;
@Data
public class NLPverb {
	NLPwhat nlpWhat;
	List<NLPverb> brotherNLPverbList;
	String 太子;

	public NLPverb parse(NLPverb newNLPverb, Node nodeParam) {
		if(isVCexisted(nodeParam)) {
			newNLPverb = parse太子(newNLPverb, nodeParam);
		}else {
			newNLPverb = parseVPNodeList(newNLPverb, nodeParam);
		}
		newNLPverb = findNp(newNLPverb, nodeParam);
		return newNLPverb;
	}

	private NLPverb findNp(NLPverb newNLPverb, Node nodeParam) {
		if( CollectionUtils.isEmpty(nodeParam.sonNodeList)) {
			return newNLPverb;
		}
		for(Node node : nodeParam.sonNodeList) {
			if ( StringUtils.equals(node.sTYPE, "NP")) {
				NLPwhat newWhat = new NLPwhat();
				newWhat.parse(node);
				newNLPverb.setNlpWhat(newWhat);
				return newNLPverb;
			}
		}
		return newNLPverb;
	}

	private NLPverb parse太子(NLPverb newNLPverb, Node nodeParam) {
		Node vcNode = getVcNode(nodeParam);
		if(vcNode == null) {
			return newNLPverb;
		}
		if( ! StringUtils.isEmpty(vcNode.sVar)) {
			newNLPverb.太子 = vcNode.sVar;
			return newNLPverb;
		}
		return newNLPverb;
	}

	private Node getVcNode(Node nodeParam) {
		if( nodeParam == null) {
			return null;
		}
		if( CollectionUtils.isEmpty(nodeParam.sonNodeList)) {
			return null;
		}
		for( Node node : nodeParam.sonNodeList) {
			if ( StringUtils.equals(node.sTYPE, "VC")) {
				return node;
			}
		}
		return null;
	}

	private boolean isVCexisted(Node nodeParam) {
		if( CollectionUtils.isEmpty(nodeParam.sonNodeList)) {
			return false;
		}
		for(Node node : nodeParam.sonNodeList) {
			if (StringUtils.equals(node.sTYPE, "VC")) {
				return true;
			}
		}
		return false;
	}

	private NLPverb parseVPNodeList(NLPverb newNLPverb, Node nodeParam) {
		List<Node> vpNodeList = getVPnodeList(nodeParam);
		if(CollectionUtils.isEmpty(vpNodeList)) {
			return newNLPverb;
		}
		for(Node node : vpNodeList) {
			if ( StringUtils.isEmpty(newNLPverb.太子)) {
				newNLPverb = newNLPverb.parse(newNLPverb, node);
			}else {
				NLPverb brotherNLPverb = new  NLPverb();
				brotherNLPverb = brotherNLPverb.parse(brotherNLPverb, node);
				if ( CollectionUtils.isEmpty(newNLPverb.brotherNLPverbList))
					newNLPverb.brotherNLPverbList = new ArrayList<NLPverb>();

				newNLPverb.brotherNLPverbList.add(brotherNLPverb);
			}
		}
		return newNLPverb;
	}

	private List<Node> getVPnodeList(Node nodeParam) {

		if( CollectionUtils.isEmpty(nodeParam.sonNodeList)) {
			return null;
		}
		List<Node> vpNodeList =  new ArrayList<Node>();
		for(Node node : nodeParam.sonNodeList) {
			if ( StringUtils.equals(node.sTYPE, "VP")) {
				vpNodeList.add(node);
			}
		}
		return vpNodeList;
	}








    public void print(String prefix, boolean isTail) {
        System.out.println(prefix + (isTail ? "└── " : "├── ") + 太子);

        nlpWhat.print(prefix, isTail);
        if(brotherNLPverbList == null) return ;
        for (int i = 0; i < brotherNLPverbList.size() - 1; i++) {
        	brotherNLPverbList.get(i).print(prefix + (isTail ? "    " : "│   "), false);
        }
        if (brotherNLPverbList.size() > 0) {
        	brotherNLPverbList.get(brotherNLPverbList.size() - 1)
                    .print(prefix + (isTail ?"    " : "│   "), true);
        }
    }

    /**
     *
     * @return
     */
	public boolean isNLPVerbEmpty() {
		boolean bResult = false;

		if(nlpWhat == null || nlpWhat.isNLPwhatEmpty()) {}{
			bResult = true;
		}
		if(CollectionUtils.isEmpty(this.brotherNLPverbList)) {
			bResult = true;
		}
		if(StringUtils.isEmpty(this.太子)) {
			bResult = true;
		}
		return bResult;
	}
}
