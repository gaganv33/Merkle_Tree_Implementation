package treeNode;

import databaseNode.Node;

public class TreeSynchronization {
    public static void treeSynchronization(TreeNode leaderTreeNode, Node replicaNode,
                                           TreeNode replicaTreeNode) {
        if (leaderTreeNode == null) {
            System.out.println("The leader node is empty, nothing to sync with replica node");
            return;
        }
        if (replicaTreeNode == null) {
            System.out.println("The replica node is empty, so syncing the leader node with replica node");
            copyLeaderDataToReplica(leaderTreeNode, replicaNode);
            return;
        }
        System.out.println("Syncing data of leader node with replica node");
        syncData(leaderTreeNode, replicaTreeNode, replicaNode);
    }

    private static void copyLeaderDataToReplica(TreeNode leaderTreeNode, Node replicaNode) {
        if (leaderTreeNode.isLeaf) {
            LeafNode leafNode = (LeafNode) leaderTreeNode;
            replicaNode.addRecord(leafNode.key, leafNode.value);
            return;
        }

        if (leaderTreeNode.right != null) {
            copyLeaderDataToReplica(leaderTreeNode.right, replicaNode);
        }
        if (leaderTreeNode.left != null) {
            copyLeaderDataToReplica(leaderTreeNode.left, replicaNode);
        }
    }

    private static void syncData(TreeNode leaderTreeNode, TreeNode replicaTreeNode, Node replicaNode) {
        if (leaderTreeNode.isLeaf && replicaTreeNode.isLeaf) {
            if (!leaderTreeNode.hash.equals(replicaTreeNode.hash)) {
                System.out.println("The leader node data is not matching with replica node, so syncing data");
                LeafNode leafNode = (LeafNode) leaderTreeNode;
                replicaNode.addRecord(leafNode.key, leafNode.value);
            } else {
                System.out.println("The leaf node of leader node is matching with the leaf node of replica node");
            }
        } else if (replicaTreeNode.isLeaf) {
            if (leaderTreeNode.right != null) {
                syncData(leaderTreeNode.right, replicaTreeNode, replicaNode);
            }
            if (leaderTreeNode.left != null) {
                syncData(leaderTreeNode.left, replicaTreeNode, replicaNode);
            }
        } else {
            if (leaderTreeNode.hash.equals(replicaTreeNode.hash)) {
                System.out.println("The leader node subtree matches with the replica node subtree");
            } else {
                String leaderRightHash = leaderTreeNode.rightHash;
                String leaderLeftHash = leaderTreeNode.leftHash;
                String replicaRightHash = replicaTreeNode.rightHash;
                String replicaLeftHash = replicaTreeNode.leftHash;

                if (leaderRightHash.equals(replicaRightHash)) {
                    System.out.println("The leader node right subtree matches with the replica node right subtree");
                } else {
                    syncData(leaderTreeNode.right, replicaTreeNode.right, replicaNode);
                }

                if (leaderLeftHash.equals(replicaLeftHash)) {
                    System.out.println("The leader node left subtree matches with the replica node left subtree");
                } else {
                    syncData(leaderTreeNode.left, replicaTreeNode.left, replicaNode);
                }
            }
        }
    }
}
