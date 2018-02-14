/**
 * Created by baraa on 01/02/2018.
 */
public class Main {
    public static void main(String args[]){
        AvlTree avlTree = new AvlTree();
        avlTree.insert(12);
        avlTree.insert(18);
        avlTree.insert(17);
        avlTree.insert(5);
        avlTree.insert(4);
        avlTree.insert(8);
        avlTree.insert(7);
        avlTree.insert(11);
        avlTree.insert(2);

        avlTree.delete(18);
        avlTree.traverse();

    }

}
