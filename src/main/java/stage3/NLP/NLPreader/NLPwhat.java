package stage3.NLP.NLPreader;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import lombok.Data;
@Data
public class NLPwhat {

	NLPverb nlpVerb;
	List<NLPwhat> listWhat;
	String 太子;

	/**
	 * 解析对象
	 *
	  └── ROOT
	    └── IP
	        ├── ADVP
	        │   └── AD
	        ├── NP（有效）
	        │   └── NN（有效）
	        ├── VP（有效）
	        │   ├── VP（有效）
	        │   │   ├── VC（有效）
	        │   │   └── NP（有效）
	        │   │       ├── DNP（？）
	        │   │       │   ├── NP（有效）
	        │   │       │   │   ├── NN（有效）
	        │   │       │   │   ├── NN（有效）
	        │   │       │   │   ├── CC
	        │   │       │   │   └── NN（有效）
	        │   │       │   └── DEG
	        │   │       ├── ADJP
	        │   │       │   └── JJ
	        │   │       └── NP（有效）
	        │   │           └── NN（有效）
	        │   ├── PU（无效）
	        │   └── VP（有效）
	        │       ├── VC（有效）
	        │       └── NP（有效）
	        │           ├── DNP（？）
	        │           │   ├── NP（有效）
	        │           │   │   ├── NN（有效）
	        │           │   │   └── NN（有效）
	        │           │   └── DEG
	        │           └── NP（有效）
	        │               └── NN（有效）
	        └── PU（无效）
	 */

	/**
	 * 采用递归方式。解析语句。
	 *   先找NP
	 *   再找VP
	 *   最后确定其他
	 *     |---在此递归
	 */

	/**
	 * 目的是将node的语义存入what
	 * @param what
	 * @param nodeParam
	 * @return
	 */
	public void parse(Node nodeParam) {
//		if(what == null) {
//			return null;
//		}
//
//		if(nodeParam == null) {
//			return null;
//		}

		if(CollectionUtils.isEmpty(nodeParam.sonNodeList)) {
			return ;
		}
	//	NLPwhat what = new NLPwhat();
		this.findNp(nodeParam);    // findNP会在what中设置它的NP项，然后把结果返回给what
		this.findVp(nodeParam);    // findVP会在what中设置它的VP项，然后把结果返回给what
		this.parseOther(nodeParam);// parseOther会在nodeParam中找非VP和NP项。然后把结果返回给what

		if(! this.isParseAnyInThisNode(this)) {// isParseAnyInThisNode会在what中查找是否已经存在VP会NP
			// 如果该层Node不是有效的
			// 就需要进入下一层
			for(Node node : nodeParam.sonNodeList) {
				// 专门给无效层做的准备，例。ROOT、IP。。
				// 下一层处理，然后把结果返回给what
				this.parse(node);
			}
		}
		// return what;
	}

	/**
	 * 目的是为了在子集中找到NP，把它放到listWhat的位置
	 * @param what
	 * @param nodeParam
	 * @return
	 */
	private void  findVp(Node nodeParam) {
		// 如果没有子集，就返回
		if(CollectionUtils.isEmpty(nodeParam.sonNodeList)) {
			return ;
		}
		// 如果没有NN
		for(Node node : nodeParam.sonNodeList) {
			if (StringUtils.equals(node.sTYPE, "VP")) {
				NLPverb newVerb = new NLPverb();
				newVerb = newVerb.parse(newVerb, node);
				this.setNlpVerb(newVerb);
				return ;
			}
		}
		// 说明什么都没有找到
		return ;
	}

	/**
	 * 目的是为了在子集中找到NP，把它放到listWhat的位置
	 * @param what
	 * @param nodeParam
	 * @return
	 */
	private void findNp(Node nodeParam) {
		// 如果没有子集，就返回
		if(CollectionUtils.isEmpty(nodeParam.sonNodeList)) {
			return;
		}

		// 如果没有NN，说明需要发现NP，需要再次递归。
		for(Node node : nodeParam.sonNodeList) {
			// 在子集中发现了【NP】这样的node
			if (StringUtils.equals(node.sTYPE, "NP")) {
				// VP---VC-NP---DNP-ADJP-NP
				// 因为NP之下有可能不是NN，而还是VP、所以只能递归，不可挖掘
				this.parse(node);
			}
		}
		// 说明什么都没有找到
	}

	/**
	 * 在子集中，除了NP与VP之外，其他项目该如何定义，作为NP的辅臣吧
	 * @param what
	 * @param nodeParam
	 * @return
	 */
	private void parseOther(Node nodeParam) {
//		if(what == null) {
//			return null;
//		}
		// 先将非NP/VP的项找到
		// 如果没有子集，就返回吧。
		if(CollectionUtils.isEmpty(nodeParam.sonNodeList)) {
			return;
		}
		NLPwhat subWhat = null;
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
				case "CC":
				case "NN":
					// 所有的NN会被一次性处理掉，但上面却是在遍历，所以弄了个flag
					if(!bNNisOver) {
						this.findNN(nodeParam.sonNodeList);
						bNNisOver = true;
					}
					break;
				case "IP":
					this.parse(node);
					break;

				case "DNP":
					// 还要继续深入
					subWhat = new NLPwhat();
					// subWhat = subWhat.parse(subWhat, node);
					subWhat.parse(node);
					this.listWhat = this.listWhat == null ? new ArrayList() : this.listWhat;
					// 把她加到辅臣
					if(! subWhat.isEmpty()) this.listWhat.addAll(subWhat.listWhat);

//					System.out.println("DNP");
					break;
				case "ADVP":
				default:
					// 还要继续深入
					subWhat = new NLPwhat();
					// subWhat = subWhat.parse(subWhat, node);
					subWhat.parse(node);
					this.listWhat = this.listWhat == null ? new ArrayList() : this.listWhat;
					// 把她加到辅臣
					if(! subWhat.isEmpty()) this.listWhat.add(subWhat);
			}
		} // end-for

		// 如果什么都没找到，就算了
		return ;
	}

	/**
	 * 目的是在子集中处理 NN-NN-CC-NN的情况
	 * @param what
	 * @param sonNodeList
	 * @return
	 */
	private void findNN(List<Node> sonNodeList) {
		// 目的是取得子集中NN节点的个数
		Integer countNN = this.getCountOfNN(sonNodeList);
		if(countNN == 1) {
			this.parse太子( sonNodeList);
		}
		// 取得子集中CC的位置
		Integer iLocCC = this.getLocCC(sonNodeList);

		// 目的是处理第一个CC之前的部分
		this.beforeCC(sonNodeList, iLocCC);

		// 目的是处理第一个CC之后的部分
		if(iLocCC != -1) this.afterCC(sonNodeList, iLocCC);

	}

	/**
	 * 目的是处理第一个CC之后的部分
	 * @param what
	 * @param sonNodeList
	 * @param iLocCC
	 * @return
	 */
	private void afterCC(List<Node> sonNodeList, Integer iLocCC) {
		List<Node> anther_nodeList = new ArrayList();
		// 如果CC为【与】，可以考虑共享辅臣
		anther_nodeList = this.shareByCC(anther_nodeList, sonNodeList, iLocCC);

		// 定向复制
		for(int i=iLocCC +1; i<sonNodeList.size(); i++) {
			anther_nodeList.add(sonNodeList.get(i));
		}

		// 【人类 交流-与-思维 和 记录 还有 学习】
		// 这才是最难解析的
		NLPwhat anther_newWhat = new NLPwhat();
		// 目的是在子集中处理 NN-NN-CC-NN的情况
		// anther_newWhat.findNN(anther_nodeList);

		// 目的是针对NN NN这样的作出变形处理，然后再交给what重新洗白
		anther_newWhat.parseNN_NN(anther_nodeList);
		this.listWhat = this.listWhat == null ? new ArrayList() : this.listWhat;
		this.listWhat.add(anther_newWhat);
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
	private void beforeCC(List<Node> sonNodeList, Integer iLocCC) {
		List<Node> nodeList = new ArrayList();
		// 以下不是【1】就是【2】
		// 【1】iLocCC == -1
		if(iLocCC == -1) {
			// 如果CC不存在
			nodeList = sonNodeList;
		}
		// 【2】iLocCC != -1
		for(int i=0; i<iLocCC ; i++) {
			// 如果CC存在，
			nodeList.add(sonNodeList.get(i));
		}

		switch(nodeList.size()) {
		case 1:
			// 如果处理对象nodeList，只有一个节点，
			// 那么这个肯定是【太子】
			// 目的是取得子集中的NN节点，并将其作为太子
			this.parse太子(sonNodeList.get(0));
			break;
		case 2:
			// 如果处理对象nodeList，只有两个节点，
			NLPwhat nlpWhat = new NLPwhat();
			// 目的是针对NN NN这样的作出变形处理，然后再交给what重新洗白
			nlpWhat.parseNN_NN( nodeList);
		//	if( !nlpWhat.isNLPwhatEmpty() ) {
				this.listWhat = this.listWhat == null ? new ArrayList() : this.listWhat;
				this.listWhat.add(nlpWhat);
		//	}
		default:
			// 还没想好怎么处理三个以上的NN 一起来的。
		}
		return ;
	}

	/**
	 *
	 * @return
	 */
	public boolean isNLPwhatEmpty() {
		boolean bResult = false;
		if(StringUtils.isEmpty(this.太子)) {
			bResult = true;
		}
		if(CollectionUtils.isEmpty(this.listWhat)) {
			bResult = true;
		}
		if(this.nlpVerb == null || this.nlpVerb.isNLPVerbEmpty()) {
			bResult = true;
		}
		return bResult;
	}

	/**
	 * 目的是针对NN NN这样的作出变形处理
	 * 然后在交给what重新洗白
	 * 具体方案是， 前一个NN 是辅臣
	 *            后一个NN 是主上
	 *
	 * 就是两个并列的NN 也要分出一个主次。
	 * 主为太子
	 * 次为辅臣
	 *   例 人类 交流
	 *      NN0  NN1
	 *   NN0 是修饰 NN1的
	 *   所以 NN0 是辅臣
	 *   所以 NN1 是太子
	 *
	 * @param nlpWhat
	 * @param nodeList
	 * @return
	 */
	private void parseNN_NN(List<Node> sonNodeList) {
		// 目的是取得子集中NN节点的个数
		Integer countNN = this.getCountOfNN(sonNodeList);
		if(countNN != 2) {
			// 还没想好怎么处理三个以上的NN 一起来的。
			// 真的来了，我也不知道咋办
			return ;
		}
		Node node =  new Node();
		if(countNN == 2) {
			this.太子 = sonNodeList.get(1).sVar;     // 后一个NN 是主上
			node.sTYPE = "NP";
			node.sonNodeList = new ArrayList();
			node.sonNodeList.add(sonNodeList.get(0));// 前一个NN 是辅臣
		}
		// 开始洗白
		NLPwhat nlpWhat = new NLPwhat();
		nlpWhat.parse(node);
		this.listWhat = this.listWhat == null ? new ArrayList() : this.listWhat;
		this.listWhat.add(nlpWhat);
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
	private void parse太子(List<Node> sonNodeList) {
		for(Node node : sonNodeList) {
			if(StringUtils.equals(node.sTYPE, "NN")) {
				this.parse太子(node);
			}
		}
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
	private void parse太子(Node node) {
		if (! StringUtils.isEmpty(node.sVar)) {
			this.set太子(node.sVar);
		}
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
