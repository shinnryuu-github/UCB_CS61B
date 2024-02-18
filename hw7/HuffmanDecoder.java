public class HuffmanDecoder {
    public static void main(String[] args){
        ObjectReader or = new ObjectReader(args[0]);
        BinaryTrie trie = (BinaryTrie) or.readObject();
        int lenth = (int) or.readObject();
        BitSequence hugeBitSequence = (BitSequence) or.readObject();
        StringBuilder res = new StringBuilder();
        int cnt = 0;
        while (res.length() < lenth){
            Match m = trie.longestPrefixMatch(hugeBitSequence.allButFirstNBits(cnt));
            res.append(m.getSymbol());
            cnt += m.getSequence().length();
        }
        FileUtils.writeCharArray(args[1], res.toString().toCharArray());
    }
}
