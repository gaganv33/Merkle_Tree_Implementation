package treeNode;

import hash.Hash;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.DoubleAdder;

public class TreeConstruction {
    public static TreeNode constructTreeNode(Map<String, String> data) {
        List<TreeNode> nodes = getLeafNodes(data);

        while (nodes.size() != 1) {
            int n = nodes.size();
            if ((n % 2 )!= 0) {
                nodes.add(nodes.get(n - 1));
                n++;
            }
            List<TreeNode> nextNodes = new ArrayList<>();

            for (int i = 0; i < n; i += 2) {
                TreeNode right = nodes.get(i);
                TreeNode left = nodes.get(i + 1);

                String rightHash = right.hash;
                String leftHash = left.hash;
                String hash = Hash.getHashForRightAndLeftSubtreeHash(rightHash, leftHash);

                nextNodes.add(new TreeNode(false, hash, rightHash, leftHash, right, left));
            }
            nodes = nextNodes;
        }

        return nodes.getFirst();
    }

    private static List<TreeNode> getLeafNodes(Map<String, String> data) {
        List<TreeNode> leafNodes = new ArrayList<>();

        for (var entry : data.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            String hash = Hash.getHashForKeyAndValue(key, value);
            leafNodes.add(new LeafNode(key, value, true, hash, null, null, null, null));
        }
        return leafNodes;
    }
}
