import java.io.Serializable;
import java.util.*;

public class BinaryTrie implements Serializable{
    private Node root;
    private Set<Character> allchars;
    private class Node implements Serializable, Comparable<Node> {
        char data;
        int freq;
        Node left, right;
        public Node(char c, int f, Node l, Node r){
            data = c;
            freq = f;
            left = l;
            right = r;
        }

        public boolean is_leaf(){
            return left == null && right == null;
        }
        @Override
        public int compareTo(Node o){
            return freq - o.freq;
        }
    }

    public BinaryTrie(Map<Character, Integer> frequencyTable){
        PriorityQueue<Node> pq = new PriorityQueue<>();
        allchars = new HashSet<>();
        for (Character c : frequencyTable.keySet()){
            Node toAdd = new Node(c, frequencyTable.get(c), null, null);
            pq.add(toAdd);
            allchars.add(c);
        }
        while (pq.size() > 1){
            Node n1 = pq.remove(), n2 = pq.remove();
            Node merge = new Node('\0', n1.freq + n2.freq, n1, n2);
            pq.add(merge);
        }
        root = pq.remove();
    }
    public Match longestPrefixMatch(BitSequence querySequence){
        Node target = root;
        BitSequence sofar = new BitSequence();
        for (int i = 0; i < querySequence.length(); i++){
            if (querySequence.bitAt(i) == 0){
                target = target.left;
                sofar = sofar.appended(0);
            }
            else{
                target = target.right;
                sofar = sofar.appended(1);
            }
            if (target.is_leaf())
                break;
        }
        return  new Match(sofar, target.data);
    }

    private void helper(Map<Character, BitSequence> res, Node node, BitSequence b){
        if (node.is_leaf()){
            res.put(node.data, new BitSequence(b));
            return;
        }
        BitSequence b1 = new BitSequence(b);
        b1 = b1.appended(0);
        helper(res, node.left, b1);
        BitSequence b2 = new BitSequence(b);
        b2 = b2.appended(1);
        helper(res, node.right, b2);
    }
    public Map<Character, BitSequence> buildLookupTable(){
        Map<Character, BitSequence> res = new HashMap<>();
        helper(res, root, new BitSequence());
        return res;
    }
}