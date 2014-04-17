package data.as.a.service.adaptor.condition;

import java.util.ArrayList;
import java.util.List;

public class Conditions {

	private ConditionNode root;
	private StringBuilder builder = new StringBuilder();
	private List<String> values = new ArrayList<>();

	public ConditionNode getConditionTreeRoot() {
		return root;
	}

	public void setConditionTreeRoot(ConditionNode root) {
		this.root = root;
	}
	
	private void iterate(ConditionNode node) {
		
		if (node != null) {
			iterate(node.getLeft());
			readNode(node);
			iterate(node.getRight());
		}
	}
	
	private void readNode(ConditionNode node) {
		
	}
}
