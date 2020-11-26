package org.hongxi.java.util.concurrent;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shenhongxi 2019/8/12
 */
public class DeadLockTest {

    public static void main(String[] args) {
        final TreeNode parent = new TreeNode();
        final TreeNode child = new TreeNode();
        // dead lock exists, maybe happens
        new Thread(() -> parent.addChild(child)).start();
        new Thread(() -> child.setParent(parent)).start();
    }

    static class TreeNode {
        TreeNode parent;
        List<TreeNode> children = new ArrayList<>();

        public synchronized void addChild(TreeNode child) {
            if (!this.children.contains(child)) {
                this.children.add(child);
                child.setParentOnly(this);
            }
        }

        public synchronized void addChildOnly(TreeNode child) {
            if (!this.children.contains(child)) {
                this.children.add(child);
            }
        }

        public synchronized void setParent(TreeNode parent) {
            this.parent = parent;
            parent.addChildOnly(this);
        }

        public synchronized void setParentOnly(TreeNode parent) {
            this.parent = parent;
        }
    }
}
