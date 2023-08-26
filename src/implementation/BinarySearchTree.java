package implementation;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

//import trees.BinarySearchTree.Node;

public class BinarySearchTree<K, V> {
	/*
	 * You may not modify the Node class and may not add any instance nor static
	 * variables to the BinarySearchTree class.
	 */
	private class Node {
		private K key;
		private V value;
		private Node left, right;

		private Node(K key, V value) {
			this.key = key;
			this.value = value;
		}
	}

	private Node root;
	private int treeSize, maxEntries;
	private Comparator<K> comparator;
	Map<K,V> map=new HashMap<K,V>();

	public BinarySearchTree(Comparator<K> comparator, int maxEntries) {
		this.comparator=comparator;
		this.maxEntries=maxEntries;
	}

	public BinarySearchTree<K, V> add(K key, V value) throws TreeIsFullException {
		treeSize++;
		if (root == null) {
			map.put(key, value);
			Node node=new Node(key,value);
			root=node;
			return this;
		}else {
			return addAux(key, value, root);
		}
	}
	private BinarySearchTree<K, V> addAux(K key, V data, Node rootAux) throws TreeIsFullException {

		if (comparator.compare(key, rootAux.key)==0) { 
			rootAux.value = data;
			return this;
		} else if (comparator.compare(key, rootAux.key)<0) {
			if (rootAux.left == null) {
				if(treeSize<maxEntries) {
					map.put(key, data);
					rootAux.left = new Node(key, data);
					return this;
				}
				throw new TreeIsFullException("Tree is empty");
			} else {
				return addAux(key, data, rootAux.left);
			}
		} else {
			if (rootAux.right == null) {
				if(treeSize<maxEntries) {
					map.put(key, data);
					rootAux.right = new Node(key, data);
					return this;
				}
				throw new TreeIsFullException("Tree is empty");
			} else {
				return addAux(key, data, rootAux.right);
			}
		}
	}
	public KeyValuePair<K, V> find(K key) {
		return find(key,root);
	}

	private KeyValuePair<K, V> find(K key,Node rootAux) {
		if (rootAux == null) {
			return null;
		} else {
			KeyValuePair<K,V> kv=new KeyValuePair<K,V>(key,rootAux.value);
			if (comparator.compare(kv.getKey(), rootAux.key)==0) {
				return kv;
			} else if (comparator.compare(kv.getKey(), rootAux.key)<0) {
				return find(key, rootAux.left);
			} else {
				return find(key, rootAux.right);
			}
		}

	}
	public String toString() {
		if(root==null) {
			return("EMPTY TREE");
		}
		return toString(root);
	}
	private String toString(Node rootAux) {
		return rootAux == null ? ""
				: toString(rootAux.left) + "{" + rootAux.key + ":" + rootAux.value + "}" + toString(rootAux.right);
	}
	public boolean isEmpty() {
		
		return root == null;
	}

	public int size() {
		return treeSize;
	}

	
	public boolean isFull() {
		return treeSize == maxEntries;
	}

	public KeyValuePair<K, V> getMinimumKeyValue() throws TreeIsEmptyException {
		if(root==null) {
			throw new TreeIsEmptyException("Tree is empty");
		}
		return min(root);
	}
	private KeyValuePair<K, V> min(Node rootAux) {
		if(rootAux.left==null) {
			KeyValuePair<K,V> kv=new KeyValuePair<K,V>(rootAux.key,rootAux.value);
			return kv;
		}else {
			return min(rootAux.left);
		}
	}
	public KeyValuePair<K, V> getMaximumKeyValue() throws TreeIsEmptyException {
		if(root==null) {
			throw new TreeIsEmptyException("Tree is empty");
		}
		return max(root);
	}
	private KeyValuePair<K, V> max(Node rootAux) {
		if(rootAux.right==null) {
			KeyValuePair<K,V> kv=new KeyValuePair<K,V>(rootAux.key,rootAux.value);
			return kv;
		}else {
			return max(rootAux.right);
		}
	}
	public BinarySearchTree<K, V> delete(K key) throws TreeIsEmptyException {
		if(root==null) {
			throw new TreeIsEmptyException("Tree is empty");
		}
		treeSize--;
		root = deleteRec(root, key);
		return this;
	}
	private Node deleteRec(Node rootAux, K key) {
		if(rootAux==null) {
			return null;
		}
        if (comparator.compare(key, rootAux.key)<0) {
            rootAux.left = deleteRec(rootAux.left, key);
        }
        else if (comparator.compare(key, rootAux.key)>0) {
            rootAux.right = deleteRec(rootAux.right, key);
        }
        else {
            
            if (rootAux.left == null)
                return rootAux.right;
            else if (rootAux.right == null)
                return rootAux.left;
 
            
            KeyValuePair<K,V> kv= min(rootAux.right);
            rootAux=new Node(kv.getKey(),kv.getValue());
            
            rootAux.right = deleteRec(rootAux.right, rootAux.key);
        }
 
        return rootAux;
	}
	public void processInorder(Callback<K, V> callback) {
		if(callback==null) {
			throw new IllegalArgumentException("illegal argument");
		}
		processInorder(root,callback);
	}
	private void processInorder(Node root,Callback<K, V> callback) {
		if(root==null) {
			return;
		}
		processInorder(root.left,callback);
		callback.process(root.key,root.value);
		processInorder(root.right,callback);
	}
	public BinarySearchTree<K, V> subTree(K lowerLimit, K upperLimit) {
		if(lowerLimit==null || upperLimit==null || comparator.compare(lowerLimit, upperLimit)>0) {
			throw new IllegalArgumentException("illegal argument");
		}
		BinarySearchTree<K, V> bst=new BinarySearchTree<K, V>(comparator, maxEntries);
		subTree(lowerLimit,upperLimit,root,bst);
		return bst;
	}

	private BinarySearchTree<K, V> subTree(K lowerLimit, K upperLimit, Node rootAux, BinarySearchTree<K, V> bst) {
		if (rootAux == null) {
			return null;
		}
		if (comparator.compare(lowerLimit, rootAux.key) < 0) {
			subTree(lowerLimit, upperLimit, rootAux.left, bst);
		}
		if (comparator.compare(lowerLimit, rootAux.key) <= 0 && comparator.compare(upperLimit, rootAux.key) >= 0) {
			try {
				bst.add(rootAux.key, rootAux.value);
			} catch (TreeIsFullException e) {
				e.printStackTrace();
			}
		}
		subTree(lowerLimit, upperLimit, rootAux.right, bst);
		return bst;
	}
	public TreeSet<V> getLeavesValues() {
		TreeSet<V> set=new TreeSet<V>();
		getLeavesValues(root,set);
		return set;
	}
	private void getLeavesValues(Node rootAux,TreeSet<V> set) {
		if(rootAux==null) {
			return;
		}
		else if(rootAux.left==null && rootAux.right==null) {
			set.add(rootAux.value);	
		}
		getLeavesValues(rootAux.left,set);
		getLeavesValues(rootAux.right,set);
	}
}
