package stage3.NLP.NLPreader;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import lombok.Data;

@Data
public class Node {

	String sTYPE;
	String sVar;
	Node fatherNode;
	List<Node> sonNodeList;
    Node(){
    	sTYPE=null;
    	sVar=null;
    	fatherNode=null;
    	sonNodeList=new ArrayList();
    }

    boolean isEmpty(){

    	if(sTYPE !=null) {
    		return false;
    	}
    	if(sVar !=null) {
    		return false;
    	}
    	if(fatherNode !=null) {
    		return false;
    	}
    	if(! CollectionUtils.isEmpty(sonNodeList)) {
    		return false;
    	}

    	return true;
    }

    public void print(String prefix, boolean isTail) {
        System.out.println(prefix + (isTail ? "└── " : "├── ") + sTYPE);
        for (int i = 0; i < sonNodeList.size() - 1; i++) {
        	sonNodeList.get(i).print(prefix + (isTail ? "    " : "│   "), false);
        }
        if (sonNodeList.size() > 0) {
        	sonNodeList.get(sonNodeList.size() - 1)
                    .print(prefix + (isTail ?"    " : "│   "), true);
        }
    }
}
