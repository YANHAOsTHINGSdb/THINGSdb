package stage3.NLP.NLPreader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import lombok.Data;
import stage3.things.multiConditionCalc.CRUDer;

/**
 * 【总结】
 * =======================================
 * 处理的关键是，如何处理括号与括号之间的关系。
 *
 */

/** ------------UI 概要设计-----------------
 *
 * @author haoyan
 *
【文本】

    (ROOT (IP (ADVP (AD 自然)) (NP (NN 语言)) (VP (VP (VC 是) (NP (DNP (NP (NN 人类) (NN 思维) (CC 与) (NN 交流)) (DEG 的)) (ADJP (JJ 主要)) (NP (NN 工具)))) (PU ，) (VP (VC 是) (NP (DNP (NP (NN 人类) (NN 智慧)) (DEG 的)) (NP (NN 结晶))))) (PU 。)))


【解析后】

    ROOT
     |——IP
         |——ADVP——AD——自然
         |——NP    ——NN——语言
         |——VP
                   |-—VP
                            |——VC——是
                            |——NP
                                    |—-DNP
                                           |——NP
                                               |——NN 人类
                                               |——NN 思维
                                               |——CC 与
                                               |——NN 交流
                                           |——DEG
                                     |——ADJP
                                           |——JJ 主要
                                     |——NP
                                           |——NN 工具
                   |—-PU——，
                   |-—VP
                            |——VC——是
                                    |——NP
                                            |—-DNP
                                                   |——NP
                                                      |——NN 人类
                                                      |——NN 智慧
                                                   |——DEG
                                            |——NP
                                                  |——NN 结晶

【多叉树街节点】
1.     class Node{  
2.         int var;   // 具体值的番号. 例,人类:5
3.         int TYPE；  // 父子关系. 例,NP
4.         Node father;  
5.         list<Node> sonList;  
6.     }

【参考】
    https://blog.csdn.net/qfikh/article/details/52782555


【处理该要】
    main
      |—-生成二叉树
            |—逐字读入文本
                  |—如果不是’(’ ‘)’和空格
                       累计
                       继续入
                  |—如果是’(’
                       前面的计入 Node
                       取最后Node为其父
                       将其设置为最后Node
                  |—如果是 ）
                       倒数第二为父节点、
                  |—如果是 空格
                       当前父节点的var为其值

      最后得到一个Node作为多叉树
      一个父堆栈list



【遍历多叉树。完成解析】

    如果父是【IP】
    如果父是【NP】
    如果父是【VP】
    如果子中是有【VP】
    如果子中是有【NP】
    如果子中是有【VC】
    如果子中是有【PU】
 */


/** ------------SS 详细设计-----------------
 *
 * @author haoyan
 *
逐字读入文本
   是不是指定符号【(】【)】【空白】其中之一

   如果不存在。//例，读入“RO”，
                         //下一个值为【O】，
                          //你会把这个值堆完
       累计
       继续读入-->自身递归调用(
                                NODE多叉树，
                                累计读入文本
                             )
   如果是【(】
       做成新Node，TYPE=【累计读入文档】
       清空【累积读入文档】
       现在的我【NODE多叉树】为新Node的父亲
       如果现在的【NODE多叉树】为空，
           那新Node就是根节点。
       否则的话
           将新Node计入到【NODE多叉树】的右手
           【NODE多叉树】计入到新Node的左右
       继续读入-->自身递归调用(
                                NODE多叉树，
                                累计读入文本
                             )

   如果是【)】
       // 例，【自然】之后是【)】
       //.         说明【自然】是当前NODE的Var值
       //          说明当前NODE已经结束，要退到上一层节点
       // 例，【)】之后是【)】
       //          说明当前NODE已经结束，要退到上一层节点

       如果【累计读入文档】不为空
           那么【累计读入文档】就是当前NODE的var值
           【NODE多叉树】.var = 【累计读入文档】

       如果【累计读入文档】不空
            // 什么都不用做
       如果【NODE多叉树】的右手不为空
            // 说明他是有爸爸的
            // 接下来就要看看爸爸的其他子女了
            继续读入-->自身递归调用(
                                NODE多叉树，
                                累计读入文本
                             )
       如果【NODE多叉树】的右手为空
           // 说明他是没有爸爸的
           表示解析已经结束了

     如果是【空白】
          // 例，【AD】之后就是空白
          // 说明这个节点时候具体var的，
          // 那它的TYPE描述已经结束了。
          // （也说明他是没有孩子的）

          做成新NODE， TYPE=【累计读入文档】
          将新NODE计入【NODE多叉树】的右手
          继续读入-->自身递归调用(
                                NODE多叉树，
                                累计读入文本
                             )
 最后返回【NODE多叉树】



 */
@Data
public class NLPreader extends NLPreaderBase {

	private Node nodeResult;

	/**
	 *
	 * @param sParse对象
	 */
	public NLPreader(String sParse对象) {
		super.sParse对象 =  sParse对象 ;
		super.iLoc = 0;
	}

	/**
	 * 入库代码的测试代码
	 * @param args
	 */
	public static void main(String[] args) {
		//=============sParse对象是斯坦福NLP解析出来的结果========================
		String sParse对象 = "(ROOT (IP (ADVP (AD 自然)) (NP (NN 语言)) (VP (VP (VC 是) (NP(DNP(NP (NN 人类) (NN 思维) (CC 与) (NN 交流)) (DEG 的))(ADJP (JJ 主要)) (NP (NN 工具))))(PU ，) (VP (VC 是) (NP (DNP (NP (NN 人类) (NN 智慧)) (DEG 的)) (NP (NN 结晶))))) (PU 。)))";
		//=============先词法分析========================
		sParse对象 = sParse对象.replace(" (", "(");
		NLPreader nlpReader = new NLPreader(sParse对象); // 这么写是为了防止使用者忘记设置解析对象
		nlpReader.nodeResult = new Node();
		nlpReader.parse(nlpReader.nodeResult, null);
		nlpReader.nodeResult.print("", true);
		//=============再语法分析========================
		NLPwhat nlpWhat = new NLPwhat();
		nlpWhat.parse(nlpReader.nodeResult);
		nlpWhat.print("", true);

		//=============再做成MAP========================
		Map nlp解析结果Json = new HashMap();
		Map CRUDmap = new HashMap();
		CRUDmap.put("操作", "追加");
		CRUDmap.put("目标", "什么");
		//----------前面有两层是不可用的----------
		List<NLPwhat> nlpWhatList1 = nlpWhat.getListWhat();//ROOT
		List<NLPwhat> nlpWhatList2 = nlpWhatList1.get(0).getListWhat();//IP
		//----------要把Bean 转成后 Map----------
//		ObjectMapper m = new ObjectMapper();
//		Map<String,Object> nlpWhatMap = m.convertValue(nlpWhatList1.get(0), Map.class);

//		CRUDmap.put("条件", nlpWhatMap);

		/*
			---------------词条信息Map的构造---------------------
			词条信息Map< key,   value>
			   |-------条件    条件Map< key,   value>
			                   |------什么    NlpWhat
			---------------------------------------------------
		 */
		Map 什么map = new HashMap();
		什么map.put("什么", nlpWhatList1.get(0));
		CRUDmap.put("条件", 什么map);
		nlp解析结果Json.put("CRUD", CRUDmap);

		//=============把MAP放入THINFGSdb========================
		CRUDer cRUDer = new CRUDer("NLPreader.main()");
		cRUDer.add(nlp解析结果Json);

	}

	/**
	 *
	 */
	Node parse(Node father, String s累计读入文本) {

		super.parse(father, s累计读入文本);
		if (StringUtils.isEmpty(sChar))
			return father; // 已经到最后，退出
		Node node = father;

		switch (sChar) {
		case "(":
			node = createNewNode(father, s累计读入文本, sChar);
			s累计读入文本 = null;
			break;
		case ")":
			node = backToFatherNode(father, s累计读入文本);
			s累计读入文本 = null;
			break;
		case " ":
			node = addToNewNode(father, s累计读入文本, sChar);
			s累计读入文本 = null;
			break;
		default:
			// 如果以上都不是
			// 累计吧
			s累计读入文本 = s累计读入文本 == null ? sChar : s累计读入文本 + sChar;
		}

		if (node == null) {
			if(StringUtils.equals(father.sTYPE, "ROOT")) {
				this.nodeResult = father;
			}
			// 回到原点
			return null;
		}

		return parse(node, s累计读入文本);

	}
 /**
  *
  * @param father
  * @param s累计读入文本
  * @param sChar
  * @return
  */
	private Node addToNewNode(Node father, String s累计读入文本, String sParamChar) {
		if (father.isEmpty()) {
			return null;
		}

		super.parse(father, s累计读入文本);
		// father.sVar = s累计读入文本;

		// 原则上sChar都为空的，只有在第一次进来才会带进来值
		if(StringUtils.equals(sParamChar, " ")) {
			Node nodeForVar = createNewNode(father, s累计读入文本, null);
			s累计读入文本 = this.sChar;
			// 递归：在此向下读，直到完了为止
			//      注，当你读到第一个【)】时，就意味着到头了，
			//      此时应该返回你的爸爸
			return addToNewNode(nodeForVar, s累计读入文本, null).fatherNode;
		}

		// 只有递归的时候才会到此
		switch(this.sChar) {
		case ")" :
			father.sVar = s累计读入文本;
			s累计读入文本 = null;
			// 到此为止了
			return father;
		default:
			// 如果每到头
			// 就继续累计吧
			s累计读入文本 = s累计读入文本 == null ? this.sChar : s累计读入文本 + this.sChar;
		}

		return addToNewNode(father, s累计读入文本, null);
	}
/**
 *
 * @param father
 * @param s累计读入文本
 * @param sChar
 * @return
 */
	private Node createNewNode(Node father, String s累计读入文本, String sChar) {
		// 最初时，回到这里
		if (father.isEmpty() && StringUtils.isEmpty(s累计读入文本) && ! StringUtils.isEmpty(sChar)) {
			if (sChar.equals("(")) {
				return parse(father, s累计读入文本);
			}
		}

		// 若果不是第一次，到这里
		if(StringUtils.isEmpty(s累计读入文本)) {
			// IP(  (  )  (  )  )
			// -----------⬆-----
			// 专门真这个箭头这个括号做的对应
			return father;
		}

		// 完成对一个节点的建立
		Node node = new Node();
		node.sTYPE = s累计读入文本;

		// 建立父子关系
		// 怎么证明这个father是空的
		if (father.isEmpty()) {
			father = node;
		} else {
			father.sonNodeList.add(node);
			node.fatherNode = father;
		}
		return node;
	}

/**
 *
 *
 *
 * @param father
 * @param s累计读入文本
 * @return
 */
	private Node backToFatherNode(Node father, String s累计读入文本) {

		if (father.isEmpty()) {
			return null;
		}
		if (!StringUtils.isEmpty(s累计读入文本)) {
			father.sTYPE = s累计读入文本;
		}

		if (father.fatherNode != null) {

			return father.fatherNode;
		}

		// 表示解析已经结束
		return null;
	}
}
