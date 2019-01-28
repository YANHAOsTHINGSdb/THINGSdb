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
}
