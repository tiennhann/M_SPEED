package M_SPEED;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Tree {

	public TreeNode root;
	public Tree() {
		// Marking root event as ' ' empty
		this.root = new TreeNode(' ');
	} //end Tree
	
	public void addEvents(String events) {
		//System.out.println("Adding event :" + events);
		TreeNode cur_node = this.root;
		for(char event : events.toCharArray()) {
			if(!cur_node.children.containsKey(event)) 
				cur_node.children.put(event, new TreeNode(event));
			//System.out.println("The children of "+cur_node.ToString() +" are/is " +cur_node.getChildren(cur_node.event));
			cur_node = cur_node.children.get(event);
			//cur_node.frequency++;
			//cur_node.getFreq(cur_node);
		}
		cur_node.frequency++;
	} //end addEvents

	public void getFreqevents(String events){
		TreeNode cur_node = this.root;
		System.out.println("The frequencies for "+ events);
		for(char event : events.toCharArray()) {
			cur_node = cur_node.children.get(event);
			if (cur_node == null) {
				System.out.println("The Frequency of "+event+" is 0");
				break;
			} //end if
			cur_node.getFreq(cur_node);
		} //end for
	} //end getFreqevents

} //end class




