package stage3.NLP.NLPreader;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

public class NLPwhat {

	NLPverb nlpVerb;
	List<NLPwhat> listWhat;
	String 太子;

	/**
	 * 目的是将node的语义存入what
	 * @param what
	 * @param nodeParam
	 * @return
	 */
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

		what = findNp(what, nodeParam);// findNP会在what中设置它的NP项，然后把结果返回给what
		what = findVp(what, nodeParam);// findVP会在what中设置它的VP项，然后把结果返回给what
		what = parseOther(what, nodeParam);// parseOther会在nodeParam中找非VP和NP项。然后把结果返回给what

		if(! isParseAnyInThisNode(what)) {// isParseAnyInThisNode会在what中查找是否已经存在VP会NP
			// 如果该层Node不是有效的
			// 就需要进入下一层
			for(Node node : nodeParam.sonNodeList) {
				// 专门给无效层做的准备，例。ROOT、IP。。
				// 下一层处理，然后把结果返回给what
				what = what.parse(what, node);
			}
		}
		return what;
	}

	/**
	 * 目的是为了在子集中找到NP，把它放到listWhat的位置
	 * @param what
	 * @param nodeParam
	 * @return
	 */
	private NLPwhat findVp(NLPwhat what, Node nodeParam) {
		// 如果没有子集，就返回
		if(CollectionUtils.isEmpty(nodeParam.sonNodeList)) {
			return what;
		}
		// 如果没有NN
		for(Node node : nodeParam.sonNodeList) {
			if (StringUtils.equals(node.sTYPE, "VP")) {
				NLPverb newVerb = new NLPverb();
				newVerb = newVerb.parse(newVerb, node);
				what.nlpVerb = newVerb;
				return what;
			}
		}
		// 说明什么都没有找到
		return what;
	}

	/**
	 * 目的是为了在子集中找到NP，把它放到listWhat的位置
	 * @param what
	 * @param nodeParam
	 * @return
	 */
	private NLPwhat findNp(NLPwhat what, Node nodeParam) {
		// 如果没有子集，就返回
		if(CollectionUtils.isEmpty(nodeParam.sonNodeList)) {
			return what;
		}
		// 如果没有NN，说明需要发现NP，需要再次递归。
		for(Node node : nodeParam.sonNodeList) {
			// 在子集中发现了【NP】这样的node
			if (StringUtils.equals(node.sTYPE, "NP")) {
				NLPwhat newWhat = new NLPwhat();
				// VP---VC-NP---DNP-ADJP-NP
				// 因为NP之下有可能不是NN，而还是VP、所以只能递归，不可挖掘
				newWhat = newWhat.parse(newWhat, node);
				what.太子 = newWhat.太子;
				what.listWhat = newWhat.listWhat;
				return what;
			}
		}
		// 说明什么都没有找到
		return what;
	}

	/**
	 * 在子集中，除了NP与VP之外，其他项目该如何定义，作为NP的辅臣吧
	 * @param what
	 * @param nodeParam
	 * @return
	 */
	private NLPwhat parseOther(NLPwhat what, Node nodeParam) {
		if(what == null) {
			return null;
		}
		// 先将非NP/VP的项找到
		// 如果没有子集，就返回吧。
		if(CollectionUtils.isEmpty(nodeParam.sonNodeList)) {
			return what;
		}
		boolean bNNisOver = false; // 有没有被处理过的flg
		for(Node node : nodeParam.sonNodeList) {
			if(StringUtils.isEmpty(nodeParam.sTYPE)) {
				continue;
			}
			// 在子集中发现了【NP】【VP】这样的node，就放过吧。
			switch(node.sTYPE) {
				case "NP":break;
				case "VP":break;
				case "AD":
				case "NN":
					// 所有的NN会被一次性处理掉，但上面却是在遍历，所以弄了个flag
					if(!bNNisOver) {
						what = findNN(what, nodeParam.sonNodeList);
						bNNisOver = true;
					}
					break;
				case "ADVP":
				case "DNP":
//					System.out.println("DNP");
				default:
					NLPwhat subWhat = new NLPwhat();
					subWhat = subWhat.parse(subWhat, node);
					what.listWhat = what.listWhat == null ? new ArrayList() : what.listWhat;
					// 把她加到辅臣
					if(! subWhat.isEmpty()) what.listWhat.add(subWhat);
			}
		} // end-for

		// 如果什么都没找到，就算了
		return what;
	}

	/**
	 * 目的是在子集中处理 NN-NN-CC-NN的情况
	 * @param what
	 * @param sonNodeList
	 * @return
	 */
	private NLPwhat findNN(NLPwhat what, List<Node> sonNodeList) {
		//
		Integer countNN = getCountOfNN(sonNodeList);
		if(countNN == 1) return parse太子(what, sonNodeList);
		Integer iLocCC = getLocCC(sonNodeList);
		what = beforeCC(what, sonNodeList, iLocCC);
		if(iLocCC != -1) what = afterCC(what, sonNodeList, iLocCC);
		return what;
	}

	/**
	 * 目的是处理第一个CC之后的部分
	 * @param what
	 * @param sonNodeList
	 * @param iLocCC
	 * @return
	 */
	private NLPwhat afterCC(NLPwhat what, List<Node> sonNodeList, Integer iLocCC) {
		List<Node> anther_nodeList = new ArrayList();
		// 如果CC为【与】，可以考虑共享辅臣
		anther_nodeList = shareByCC(anther_nodeList, sonNodeList, iLocCC);

		// 定向复制
		for(int i=iLocCC +1; i<sonNodeList.size(); i++) {
			anther_nodeList.add(sonNodeList.get(i));
		}

		// 【人类 交流-与-思维 和 记录 还有 学习】
		// 这才是最难解析的
		NLPwhat anther_newWhat = new NLPwhat();
		anther_newWhat = anther_newWhat.findNN(anther_newWhat, anther_nodeList);
		what.listWhat = what.listWhat == null ? new ArrayList() : what.listWhat;
		what.listWhat.add(anther_newWhat);
		return what;
	}

	/**
	 * 目的是根据接续符，判断是否需要共享辅臣
	 * @param anther_nodeList
	 * @param sonNodeList
	 * @param iLocCC
	 * @return
	 */
	private List<Node> shareByCC(List<Node> anther_nodeList, List<Node> sonNodeList, Integer iLocCC) {
		switch(sonNodeList.get(iLocCC).sVar) {
		case "与":
			anther_nodeList.add(sonNodeList.get(0));
			break;
		default:
			// 什么也不做
		}
		return anther_nodeList;
	}

	/**
	 * 目的是处理第一个CC之前的部分
	 * @param what
	 * @param sonNodeList
	 * @param iLocCC
	 * @return
	 */
	private NLPwhat beforeCC(NLPwhat what, List<Node> sonNodeList, Integer iLocCC) {
		List<Node> nodeList = new ArrayList();
		// 以下不是【1】就是【2】
		// 【1】
		if(iLocCC == -1) {
			nodeList = sonNodeList;
		}
		// 【2】
		for(int i=0; i<iLocCC ; i++) {
			nodeList.add(sonNodeList.get(i));
		}

		switch(nodeList.size()) {
		case 1:
			what.parse太子(what, sonNodeList.get(0));
			return what;
		case 2:
			NLPwhat nlpWhat = new NLPwhat();
			nlpWhat = nlpWhat.parseNN_NN(nlpWhat, nodeList);
			what.listWhat = what.listWhat == null ? new ArrayList() : what.listWhat;
			what.listWhat.add(nlpWhat);
		default:
			// 还没想好怎么处理三个以上的NN 一起来的。
		}
		return what;
	}

	/**
	 * 目的是针对NN NN这样的作出变形处理
	 * 然后在交给what重新洗白
	 * 具体方案是， 前一个NN 是辅臣
	 *            后一个NN 是主上
	 * @param nlpWhat
	 * @param nodeList
	 * @return
	 */
	private NLPwhat parseNN_NN(NLPwhat what, List<Node> sonNodeList) {
		Integer countNN = getCountOfNN(sonNodeList);
		if(countNN != 2) {
			// 还没想好怎么处理三个以上的NN 一起来的。
			// 真的来了，我也不知道咋办
			return null;
		}
		Node node =  new Node();
		if(countNN == 2) {
			what.太子 = sonNodeList.get(1).sVar;     // 后一个NN 是主上
			node.sTYPE = "NP";
			node.sonNodeList = new ArrayList();
			node.sonNodeList.add(sonNodeList.get(0));// 前一个NN 是辅臣
		}
		// 开始洗白
		NLPwhat nlpWhat = new NLPwhat();
		nlpWhat = nlpWhat.parse(nlpWhat, node);
		what.listWhat = what.listWhat == null ? new ArrayList() : what.listWhat;
		what.listWhat.add(nlpWhat);
		return what;
	}

	/**
	 * 取得子集中CC的位置
	 * @param sonNodeList
	 * @return
	 */
	private Integer getLocCC(List<Node> sonNodeList) {
		int iLocOfCC = -1;
		int iLoc=0;
		for(Node node : sonNodeList) {
			if(StringUtils.equals(node.sTYPE, "CC")) {
				iLocOfCC = iLoc;
			}
			iLoc ++;
		}
		return iLocOfCC;
	}

	/**
	 * 目的是取得子集中的NN节点，并将其作为太子
	 * 前提是子集中有且只有一个NN节点
	 * @param what
	 * @param node
	 * @return
	 */
	private NLPwhat parse太子(NLPwhat what, List<Node> sonNodeList) {
		for(Node node : sonNodeList) {
			if(StringUtils.equals(node.sTYPE, "NN")) {
				what = what.parse太子(what, node);
			}
		}
		return what;
	}

	/**
	 * 目的是取得子集中NN节点的个数
	 * @param sonNodeList
	 * @return
	 */
	private Integer getCountOfNN(List<Node> sonNodeList) {
		int countOfNN = 0;
		for(Node node : sonNodeList) {
			if(StringUtils.equals(node.sTYPE, "NN")) {
				countOfNN ++;
			}
		}
		return countOfNN;
	}

	/**
	 * 目的是判断当前what是不是空/无效
	 * @return
	 */
	private boolean isEmpty() {
		if (nlpVerb == null
				&& CollectionUtils.isEmpty(listWhat)
				&& StringUtils.isEmpty(太子)) {
			return true;
		}
		return false;

	}

	/**
	 * 目的是取得子集中的NN节点，并将其作为太子
	 * 前提是子集中有且只有一个NN节点
	 * @param what
	 * @param node
	 * @return
	 */
	private NLPwhat parse太子(NLPwhat what, Node node) {
		if (! StringUtils.isEmpty(node.sVar)) {
			what.太子 = node.sVar;
			return what;
		}
		return what;
	}

	/**
	 * 目的是查看当前NODE是否是有效的节点。例，ROOT，IP，就是无效的跟节点。
	 * 万一不是就往下一层走。
	 * @param what
	 * @return
	 */
	private boolean isParseAnyInThisNode(NLPwhat what) {
		// 评判标准就是，如果nlpVer与listWhat都为空
		// 就说明这个NLPWhat还不合格
		if (what.nlpVerb == null
				&& CollectionUtils.isEmpty(what.listWhat)
				&& StringUtils.isEmpty(what.太子)) {
			return false;
		}
		return true;
	}

    public void print(String prefix, boolean isTail) {
        System.out.println(prefix + (isTail ? "└── " : "├── ") + 太子);

        if(listWhat == null ) return ;
        for (int i = 0; i < listWhat.size() - 1; i++) {
        	listWhat.get(i).print(prefix + (isTail ? "    " : "│   "), false);
        }
        if ( listWhat.size() > 0) {
        	listWhat.get(listWhat.size() - 1)
                    .print(prefix + (isTail ?"    " : "│   "), true);
        }

        if(nlpVerb != null) nlpVerb.print(prefix, isTail);
    }

}
