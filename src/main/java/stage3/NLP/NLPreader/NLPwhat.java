package stage3.NLP.NLPreader;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

public class NLPwhat {

	NLPverb nlpVerb;
	List<NLPwhat> listWhat;
	String 太子;

	public NLPwhat parse(NLPwhat what, Node nodeParam) {
		if(what == null) {
			return null;
		}

		if(nodeParam == null) {
			return null;
		}

		if(CollectionUtils.isEmpty(nodeParam.sonNodeList)) {
			return what;
		}

		what = findNp(what, nodeParam);
		what = findVp(what, nodeParam);
		what = parseOther(what, nodeParam);

		if(! isParseAnyInThisNode(what)) {
			for(Node node : nodeParam.sonNodeList) {
				what = what.parse(what, node);
			}
		}
		return what;
	}

	private NLPwhat findVp(NLPwhat what, Node nodeParam) {
		if(CollectionUtils.isEmpty(nodeParam.sonNodeList)) {
			return what;
		}
		for(Node node : nodeParam.sonNodeList) {
			if (StringUtils.equals(node.sTYPE, "VP")) {
				NLPverb newVerb = new NLPverb();
				newVerb = newVerb.parse(newVerb, node);
				what.nlpVerb = newVerb;
				return what;
			}
		}
		return what;
	}

	private NLPwhat findNp(NLPwhat what, Node nodeParam) {
		if(CollectionUtils.isEmpty(nodeParam.sonNodeList)) {
			return what;
		}
		for(Node node : nodeParam.sonNodeList) {
			if (StringUtils.equals(node.sTYPE, "NP")) {
				NLPwhat newWhat = new NLPwhat();
				newWhat = newWhat.parse(newWhat, node);
				what.太子 = newWhat.太子;
				what.listWhat = newWhat.listWhat;
				return what;
			}
		}
		return what;
	}

	private NLPwhat parseOther(NLPwhat what, Node nodeParam) {
		if(what == null) {
			return null;
		}
		if(CollectionUtils.isEmpty(nodeParam.sonNodeList)) {
			return what;
		}
		for(Node node : nodeParam.sonNodeList) {
			if(StringUtils.isEmpty(nodeParam.sTYPE)) {
				continue;
			}
			switch(node.sTYPE) {
				case "NP":break;
				case "VP":break;
				case "AD":
				case "NN":
					what = what.parse太子(what, node);
					break;
				case "ADVP":
				case "DNP":
//					System.out.println("DNP");
				default:
					NLPwhat subWhat = new NLPwhat();
					subWhat = subWhat.parse(subWhat, node);
					what.listWhat = what.listWhat == null ? new ArrayList() : what.listWhat;
					if(! subWhat.isEmpty()) what.listWhat.add(subWhat);
			}
		} // end-for
		return what;
	}

	private boolean isEmpty() {
		if (nlpVerb == null
				&& CollectionUtils.isEmpty(listWhat)
				&& StringUtils.isEmpty(太子)) {
			return true;
		}
		return false;

	}

	private NLPwhat parse太子(NLPwhat what, Node node) {
		if (! StringUtils.isEmpty(node.sVar)) {
			what.太子 = node.sVar;
			return what;
		}
		return what;
	}

	private boolean isParseAnyInThisNode(NLPwhat what) {
		if (what.nlpVerb == null
				&& CollectionUtils.isEmpty(what.listWhat)
				&& StringUtils.isEmpty(what.太子)) {
			return false;
		}
		return true;
	}

    public void print(String prefix, boolean isTail) {
        System.out.println(prefix + (isTail ? "└── " : "├── ") + 太子);
        if(nlpVerb != null) nlpVerb.print(prefix, isTail);
        if(listWhat == null ) return ;
        for (int i = 0; i < listWhat.size() - 1; i++) {
        	listWhat.get(i).print(prefix + (isTail ? "    " : "│   "), false);
        }
        if ( listWhat.size() > 0) {
        	listWhat.get(listWhat.size() - 1)
                    .print(prefix + (isTail ?"    " : "│   "), true);
        }
    }

}
