
import java.util.*;

public class CyclicTreeDetectionAndSerializer implements TreeSerializer {
  private static final String SPLITTER = ",";
  private static final String NN = "#";

  @Override
  public String serialize(Node root) {
    Set<Node> visited = new HashSet<>();    // creating visited set to keep track of visited nodes for cycle detection
    if (root == null) {
      return null;
    }
    Stack<Node> s = new Stack<>();
    s.push(root);

    List<String> l = new ArrayList<>();
    while (!s.isEmpty()) {
      Node t = s.pop();
      if (visited.contains(t)) {
        l.add(t + ":" + t.num);
        System.out.println("Cycle Detected!");
        continue;
        // throw new RuntimeException("Cyclic Tree Detected");   // enable this line to throw runtime exception when cycle is found
      }

      // If current node is NULL, store marker
      if (t == null) {
        l.add(NN);
      } else {
        visited.add(t);
        // Else, store current node
        // and recur for its children
        l.add(t + ":" + t.num);
        s.push(t.right);
        s.push(t.left);
      }
    }
    return String.join(SPLITTER, l);
  }

  static int t = 0;

  @Override
  public Node deserialize(String data) {
    if (data == null)
      return null;
    t = 0;
    String[] arr = data.split(SPLITTER);
    Map<String, Node> visited = new HashMap<>();
    return buildTree(arr, visited);
  }

  private Node buildTree(String[] arr, Map<String, Node> visited) {
    if (arr[t].equals(NN))
      return null;

    String[] tokens = arr[t].split(":");
    // Create node with this item
    // and recur for children
    if (visited.containsKey(tokens[0])) {
      return visited.get(tokens[0]);
    }
    Node node = new Node();
    node.num = Integer.parseInt(tokens[1]);
    t++;
    visited.put(tokens[0], node);
    node.left = buildTree(arr, visited);
    t++;
    node.right = buildTree(arr, visited);
    return node;
  }

  public void preOrderTraversal(Node root) {     // just a preorder traversal to check fina tree output after deserialization
    Set<Node> visited = new HashSet<>();
    preOrder(root, visited);
  }

  private Set<Node> preOrder(Node node, Set<Node> visited) {
    if (node != null) {
      if (visited.contains(node)) {
        System.out.println("\t" + node.num);
      } else {

        // Print the current node's data
        System.out.println(node.num);
        visited.add(node);

        // Traverse the left subtree
        visited = preOrder(node.left, visited);

        // Traverse the right subtree
        visited = preOrder(node.right, visited);
      }
    }
    return visited;
  }

  public static void main(String[] args) {

    try {

      CyclicTreeDetectionAndSerializer x = new CyclicTreeDetectionAndSerializer();
      Node t = new Node(5);             // creating a tree to run it as a test case
      Node root = new Node(
          1,
          new Node(2,
              new Node(4),
              t),
          new Node(
              3,
              new Node(6),
              new Node(7)));
      /* Below is to create cycle for cyclic tree detection during serialization! */
      t.right = root;
      String output = x.serialize(root);
      System.out.println("serialized output => \t" + output);
      x.preOrderTraversal(x.deserialize(output));      // checking final output after deserialization

    } catch (Exception e) {
      System.out.println("Error");
      e.printStackTrace();
    }

  }
}