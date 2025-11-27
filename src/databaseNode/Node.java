package databaseNode;

import exception.DataNotFoundException;
import treeNode.TreeConstruction;
import treeNode.TreeNode;

import java.util.Map;
import java.util.TreeMap;

public class Node {
    private final String nodeName;
    private final Map<String, String> data;

    public Node(String nodeName) {
        this.nodeName = nodeName;
        data = new TreeMap<>();
    }

    public void addRecord(String key, String value) {
        System.out.printf("[%s]: add record\n", this.nodeName);
        data.put(key, value);
    }

    public void deleteRecord(String key) {
        System.out.printf("[%s]: delete record\n", this.nodeName);
        data.remove(key);
    }

    public String getRecord(String key) throws DataNotFoundException {
        System.out.printf("[%s]: get record\n", this.nodeName);
        if (data.containsKey(key)) {
            return data.get(key);
        }
        throw new DataNotFoundException("Data not found for the key: " + key);
    }

    public TreeNode getTreeNode() {
        if (data.isEmpty()) return null;
        return TreeConstruction.constructTreeNode(data);
    }

    public Map<String, String> getData() {
        return data;
    }
}
