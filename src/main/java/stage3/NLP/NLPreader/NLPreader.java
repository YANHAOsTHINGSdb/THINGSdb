package stage3.NLP.NLPreader;

import org.apache.commons.lang3.StringUtils;

import lombok.Data;


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

	private Node node;

	/**
	 *
	 * @param sParse对象
	 */
	public NLPreader(String sParse对象) {
		super.sParse对象 = sParse对象;
		super.iLoc = 0;
	}

	/**
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		String sParse对象 = "(ROOT (IP (ADVP (AD 自然)) (NP (NN 语言)) (VP (VP (VC 是) (NP (DNP (NP (NN 人类) (NN 思维) (CC 与) (NN 交流)) (DEG 的)) (ADJP (JJ 主要)) (NP (NN 工具)))) (PU ，) (VP (VC 是) (NP (DNP (NP (NN 人类) (NN 智慧)) (DEG 的)) (NP (NN 结晶))))) (PU 。)))";

		NLPreader nlpReader = new NLPreader(sParse对象);
		nlpReader.node = new Node();
		nlpReader.parse(nlpReader.node, null);

	}

/**
 *
 */
	void parse(Node father, String s累计读入文本) {

		super.parse(father, s累计读入文本);
		if (StringUtils.isEmpty(sChar))
			return; // 已经到最后，退出
		Node node = father;

		switch (sChar) {
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
			s累计读入文本 = s累计读入文本 == null ? sChar : s累计读入文本 + sChar;
		}

		if (node == null)
			return;

		parse(node, s累计读入文本);

	}
 /**
  *
  * @param father
  * @param s累计读入文本
  * @return
  */
	private Node addToNewNode(Node father, String s累计读入文本) {
		if (father.isEmpty()) {
			return null;
		}
		father.sVar = s累计读入文本;

		return father;
	}
/**
 *
 * @param father
 * @param s累计读入文本
 * @param sChar
 * @return
 */
	private Node createNewNode(Node father, String s累计读入文本, String sChar) {

		if (father.isEmpty() && StringUtils.isEmpty(s累计读入文本) && !StringUtils.isEmpty(sChar)) {
			if (sChar.equals("(")) {
				parse(father, s累计读入文本);
				return father;
			}
		}

		Node node = new Node();
		node.sTYPE = s累计读入文本;
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
 * @param father
 * @param s累计读入文本
 * @return
 */
	private Node backToFather(Node father, String s累计读入文本) {

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
