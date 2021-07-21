package M_SPEED;

import java.awt.*;
import java.util.*;
import java.lang.Character;

public class M_SPEED {
	public Tree tree;
	public ArrayList<String> EpisodeList = new ArrayList<String>();
	public M_SPEED () {
		tree = new Tree();
	}
	public static void main(String[] args) {
		M_SPEED m_speed = new M_SPEED();
		//speed.Read("ABbDCcaBCbdcADaBAdab", 0);
		//m_speed.Read("AdCBb2D3a12B4c7C1A2d8a1b8c3B3A11D4a2b3 ")`
		m_speed.run("AdCBb2D3a12B4c7C1A2d8a1b8c3B3A11D4a2b3");
		int root_freq = 0;
		for(char c : m_speed.tree.root.children.keySet()) 
			root_freq += m_speed.tree.root.children.get(c).frequency;
		System.out.println(root_freq);
		m_speed.tree.getFreqevents("Adac");
		m_speed.CalcProb("b", 'c');
	}

	public void run(String seq){
		int max_window_length = 1;
		String Window = "";
		String Timestorage = "";
		String Storage = "";
		String Episode = "Not found";
		char E;
		int num;
		String str_num = "";
		
		System.out.println("Speed starts!");
		for(int i = 0; i < seq.length(); i++){
			//System.out.println("Episode: " + Episode);
			char e = seq.charAt(i);
			Window += e;
			if(e>='A' && e<='Z')
				E = Character.toLowerCase(e); //if it is true ,display upper case
			else
				E = Character.toUpperCase(e); //if it is true ,display lower case
			if (i+1 < seq.length() && Character.isDigit(seq.charAt(i+1))) {
				str_num = "";
				while (i+1 < seq.length() && Character.isDigit(seq.charAt(i+1))) {
					Storage += seq.charAt(i+1);
					i++;
				}
			}
			num = Integer.parseInt(str_num);
				
			
			// Episode extraction: find the episode
			for(int j = 0; j < Storage.length(); j++) {
				char[] StorageList = Storage.toCharArray();
				if (StorageList[j] == E) {
					Episode = Window.substring(j, Window.length());
					if (Episode.length() > max_window_length)
						max_window_length = Episode.length();
					
					Window = Window.substring(Window.length() - max_window_length, Window.length());
					
					Read(Episode, 0);
					System.out.print("All possible contexts : ");
					for(String context: EpisodeList) {
						if(context.length() <= max_window_length) {
							System.out.print(context + ",");
							tree.addEvents(context);
						}
					}
					System.out.println();
					EpisodeList.clear();
					break;
				}
			}

			System.out.println("Window after episode extraction : " + Window);
			
		}
	}

	public void CalcProb(String window, char c){
		TreeNode cur_node = tree.root;
		char[] WindList = window.toCharArray();
		float generalp = tree.root.children.get(c).frequency;
		float Calc = generalp/38;
		float nullc = cur_node.children.get(WindList[0]).frequency;
		nullc = 1/nullc;
		Calc = nullc * Calc;
		cur_node = cur_node.children.get(WindList[0]);
		TreeNode p = cur_node.children.get(c);
		float pfreq;
		if (p == null){
			pfreq = 0;
		} else{
			pfreq = p.frequency;
		}
		pfreq = pfreq*nullc;
		Calc += pfreq;
		for(int i = 1; i < window.length(); i++) {
			cur_node = cur_node.children.get(WindList[i]);
			nullc = cur_node.frequency;
			nullc = 1/nullc;
			Calc = nullc * Calc;
			p = cur_node.children.get(c);
			if (p == null){
				pfreq = 0;
			} else{
				pfreq = p.frequency;
			}
			pfreq = pfreq/cur_node.frequency;
			Calc += pfreq;

		}
		System.out.println(Calc);
	}

	public void Read(String Episode, int i){
		if (i == Episode.length())
			return;
		// consider each substring `S[i, j]`
		for (int j = Episode.length() - 1; j >= i; j--){

			EpisodeList.add(Episode.substring(i,j+1));
			// append the substring to the result and recur with an index of
			// the next character to be processed and the result string
			Read(Episode, j + 1);
		}
		Set<String> set = new HashSet<>(EpisodeList);
		EpisodeList.clear();
		EpisodeList.addAll(set);
	}
}

