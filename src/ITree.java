/**
 * Created by baraa on 02/02/2018.
 */
public interface ITree<T extends Comparable<T>> {
    void traverse();
    void insert(T data);
    void delete(T data);
}
