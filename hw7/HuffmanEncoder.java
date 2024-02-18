import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HuffmanEncoder {
    public static Map<Character, Integer> buildFrequencyTable(char[] inputSymbols){
        Map<Character, Integer> res = new HashMap<>();
        for (char c :inputSymbols){
            if (!res.containsKey(c))
                res.put(c, 1);
            else
                res.put(c, res.get(c) + 1);
        }
        return res;
    }
    public static void main(String[] args){
        char[] inputSymbols = FileUtils.readFile(args[0]);
        Map<Character, Integer> frequencyTable = buildFrequencyTable(inputSymbols);
        BinaryTrie trie = new BinaryTrie(frequencyTable);
        ObjectWriter ow = new ObjectWriter(args[0] + ".huf");
        ow.writeObject(trie);
        ow.writeObject(inputSymbols.length);
        Map<Character, BitSequence> LookUpTable = trie.buildLookupTable();
        List<BitSequence> inputSymbolstobit = new ArrayList<>();
        for (int i = 0; i < inputSymbols.length; i++){
            inputSymbolstobit.add(LookUpTable.get(inputSymbols[i]));
        }
        BitSequence hugeBitSequence = BitSequence.assemble(inputSymbolstobit);
        ow.writeObject(hugeBitSequence);
    }
}
