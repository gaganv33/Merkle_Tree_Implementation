import databaseNode.Node;
import treeNode.LeafNode;
import treeNode.TreeNode;
import treeNode.TreeSynchronization;

import java.util.LinkedList;
import java.util.Queue;

public class Main {
     public static void main(String[] args) {
         Node leaderNode = new Node("Leader Node");
         Node replicaNode = new Node("Replica Node");

         leaderNode.addRecord("key - 1", "value - 1");
         leaderNode.addRecord("key - 2", "value - 2");
         leaderNode.addRecord("key - 3", "value - 3");
         leaderNode.addRecord("key - 4", "value - 4");
         leaderNode.addRecord("key - 5", "value - 5");
         leaderNode.addRecord("key - 6", "value - 6");
         leaderNode.addRecord("key - 7", "value - 7");
         leaderNode.addRecord("key - 8", "value - 8");
         leaderNode.addRecord("key - 9", "value - 9");
         leaderNode.addRecord("key - 10", "value - 10");
         leaderNode.addRecord("key - 11", "value - 11");
         leaderNode.addRecord("key - 12", "value - 12");
         leaderNode.addRecord("key - 13", "value - 13");
         leaderNode.addRecord("key - 14", "value - 14");
         leaderNode.addRecord("key - 15", "value - 15");
         leaderNode.addRecord("key - 16", "value - 16");
         leaderNode.addRecord("key - 17", "value - 17");
         leaderNode.addRecord("key - 18", "value - 18");

         replicaNode.addRecord("key - 1", "value - 1");
         replicaNode.addRecord("key - 2", "value - 2 replica");
         replicaNode.addRecord("key - 5", "value - 5 replica");
         replicaNode.addRecord("key - 8", "value - 8 replica");
         replicaNode.addRecord("key - 9", "value - 9");
         replicaNode.addRecord("key - 10", "value - 10");

         TreeNode leaderTreeNode = leaderNode.getTreeNode();
         TreeNode replicaTreeNode = replicaNode.getTreeNode();
         printStatusOfTreeNodes(leaderTreeNode, replicaTreeNode);

         TreeSynchronization.treeSynchronization(leaderTreeNode, replicaNode, replicaTreeNode);

         leaderTreeNode = leaderNode.getTreeNode();
         replicaTreeNode = replicaNode.getTreeNode();
         printStatusOfTreeNodes(leaderTreeNode, replicaTreeNode);

         System.out.println("Leader Data");
         for (var entry : leaderNode.getData().entrySet()) {
             System.out.println(entry.getKey() + " " + entry.getValue());
         }
         System.out.println("Replica Data");
         for (var entry : replicaNode.getData().entrySet()) {
             System.out.println(entry.getKey() + " " + entry.getValue());
         }
     }

     private static void printStatusOfTreeNodes(TreeNode leaderTreeNode, TreeNode replicaTreeNode) {
         if (checkIfMatchingTree(leaderTreeNode, replicaTreeNode)) {
             System.out.println("Matching");
         } else {
             System.out.println("Not Matching");
         }
     }

     private static void dfs(TreeNode treeNode) {
         if (treeNode.isLeaf) {
             LeafNode leafNode = (LeafNode) treeNode;
             System.out.println(leafNode.key + " " + leafNode.value);
             return;
         }

         System.out.println("Hash: " + treeNode.hash);
         System.out.println("Right Hash: " + treeNode.rightHash);
         System.out.println("Left Hash: " + treeNode.leftHash);

         dfs(treeNode.right);
         dfs(treeNode.left);
     }

     private static void bfs(TreeNode treeNode) {
         Queue<TreeNode> queue = new LinkedList<>();
         queue.add(treeNode);
         int index = 1;

         while (!queue.isEmpty()) {
            int n = queue.size();
            System.out.println("Layer - " + index++);
            System.out.println(n);

            for (int i = 0; i < n; i++) {
                TreeNode node = queue.poll();
                if (node == null) continue;
                if (node.isLeaf) {
                    LeafNode leafNode = (LeafNode) node;
                    System.out.println(leafNode.key + " " + leafNode.value);
                } else {
                    if (node.right != null) queue.add(node.right);
                    if (node.left != null) queue.add(node.left);
                }
            }
         }
     }

     private static boolean checkIfMatchingTree(TreeNode leaderTreeNode, TreeNode replicaTreeNode) {
         if (leaderTreeNode == null && replicaTreeNode == null) {
             return true;
         } else if (leaderTreeNode == null || replicaTreeNode == null) {
             return false;
         }

         String leaderHash = leaderTreeNode.hash;
         String replicaHash = replicaTreeNode.hash;

         if (leaderTreeNode.isLeaf && replicaTreeNode.isLeaf) {
             return leaderHash.equals(replicaHash);
         } else {
             if (!leaderHash.equals(replicaHash)) return false;
             return checkIfMatchingTree(leaderTreeNode.right, replicaTreeNode.right)
                     && checkIfMatchingTree(leaderTreeNode.left, replicaTreeNode.left);
         }
     }
}
