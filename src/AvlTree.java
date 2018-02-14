/**
 * Created by baraa on 02/02/2018.
 */
public class AvlTree<T extends Comparable<T>> implements ITree<T> {

    private Node<T> root;

    private int height(Node<T> node){
        if(node == null){
            return -1;
        }
        return node.getHeight();
    }
    @Override
    public void traverse() {
        if(root == null)
            return;
        innerTraverse(root);
    }


    private void innerTraverse(Node<T> node){
        if(node.getLeftNode() != null)
            innerTraverse(node.getLeftNode());
        System.out.println(node);
        if (node.getRightNode() !=null)
            innerTraverse(node.getRightNode());
    }
    private int getBalance(Node<T> node){
        if (node == null){
            return 0;
        }
        return height(node.getLeftNode()) - height(node.getRightNode());
    }

    private Node<T> rightRotation(Node<T> node){
        System.out.println("Right rotation on node ::: "+node);
        Node<T> newRoot = node.getLeftNode();
        Node<T> childRoot = newRoot.getRightNode();
        newRoot.setRightNode(node);
        node.setLeftNode(childRoot);
        node.setHeight(Math.max(height(node.getLeftNode()),height(node.getRightNode()))+1);
        newRoot.setHeight(Math.max(height(newRoot.getLeftNode()),height(newRoot.getRightNode()))+1);
        return newRoot;
    }
    private Node<T> leftRotation(Node<T> node){
        System.out.println("Left rotation on node ::: "+node);
        Node<T> newRoot = node.getRightNode();
        Node<T> childRoot = newRoot.getLeftNode();
        newRoot.setLeftNode(node);
        node.setRightNode(childRoot);
        node.setHeight(Math.max(height(node.getRightNode()),height(node.getLeftNode()))+1);
        newRoot.setHeight(Math.max(height(newRoot.getLeftNode()),height(newRoot.getRightNode()))+1);
        return newRoot;
    }

    @Override
    public void insert(T data){
            this.root = innerInsert(root,data);
    }

    private Node<T> innerInsert(Node<T> node, T data){
        if (node == null){
            return new Node<T>(data);
        }

        if(data.compareTo(node.getData()) > 0){
            node.setRightNode(innerInsert(node.getRightNode(),data));
        }else {
            node.setLeftNode(innerInsert(node.getLeftNode(),data));
        }

        node.setHeight(Math.max(height(node.getLeftNode()),height(node.getRightNode()))+1);

        return settleViolation(node,data);
    }

    private Node<T> settleViolation(Node<T> node, T data) {
        int balance = getBalance(node);
        if (balance > 1 && data.compareTo(node.getLeftNode().getData()) < 0){
            return rightRotation(node);
        }else if(balance < -1 && data.compareTo(node.getRightNode().getData())>0){
            return leftRotation(node);
        }else if(balance >1  && data.compareTo(node.getLeftNode().getData())>0){
            Node<T> temp = leftRotation(node.getLeftNode());
            node.setLeftNode(temp);
            return rightRotation(node);
        }else if(balance < -1 && data.compareTo(node.getRightNode().getData())<0){
            Node<T> temp = rightRotation(node.getRightNode());
            node.setRightNode(temp);
            return leftRotation(node);
        }

        return node;
    }
    @Override
    public void delete(T data){

        innerDelete(this.root,data);

    }
    private Node<T> innerDelete(Node<T> node,T data){
        if(node == null){
            return node;
        }
        if(data.compareTo(node.getData())<0){
            node.setLeftNode(innerDelete(node.getLeftNode(),data));
        }else if(data.compareTo(node.getData())>0){
            node.setRightNode(innerDelete(node.getRightNode(),data));
        }else {
            if(node.getLeftNode() == null &&  node.getRightNode()==null){
                return null;
            }else if(node.getLeftNode() == null){
                Node<T> temp = node.getRightNode();
                node = null;
                return temp;
            }else if(node.getRightNode() == null){
                Node<T> temp= node.getLeftNode();
                node = null;
                return temp;
            }
            Node<T> predNode = getPredecessor(node.getLeftNode());
            node.setData(predNode.getData());
            node.setLeftNode(innerDelete(node.getLeftNode(),predNode.getData()));
        }
        node.setHeight(Math.max(height(node.getLeftNode()),height(node.getRightNode()))+1);
        return settleDeleteViolation(node);
    }

    private Node<T> settleDeleteViolation(Node<T> node){
        int balance = getBalance(node);
        if(balance > 1 && getBalance(node.getLeftNode()) > 0){
            return rightRotation(node);
        }else if(balance > 1 && getBalance(node.getLeftNode()) > 0){
            Node<T>temp = leftRotation(node.getRightNode());
            node.setLeftNode(temp);
            return rightRotation(node);
        } else if(balance < -1 && getBalance(node.getRightNode() )< 0){
            return leftRotation(node);
        }else if(balance < -1 && getBalance(node.getRightNode() )>0){
            Node<T> temp = rightRotation(node.getLeftNode());
            node.setRightNode(temp);
            return leftRotation(node);
        }
        return node;
    }


    private Node<T> getPredecessor(Node<T> node){
        if(node.getRightNode() != null){
            return getPredecessor(node.getRightNode());
        }
        return node;
    }

}
