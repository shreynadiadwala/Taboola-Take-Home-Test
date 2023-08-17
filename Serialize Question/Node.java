public class Node {
  Node left;
  Node right;
  Integer num;

  Node() {
  }

  Node(int val) {
    this.num = val;
  }

  Node(int val, Node left, Node right) {
    this.left = left;
    this.right = right;
    this.num = val;
  }
}