import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class MaxFibonacciHeap {
    private Map<String, treeNode> hashtable;
    private treeNode MaxElement;

    MaxFibonacciHeap(){
        hashtable = new HashMap<>();
        MaxElement = null;
    }
    public void Insert(String theNode, int theAmout) {
        treeNode n = new treeNode(theNode, theAmout);
        hashtable.put(theNode, n);
        if (MaxElement == null) {
            MaxElement = n;
            return;
        }
        insertIntoList(n, MaxElement);
        if (theAmout > MaxElement.getAmount()) {
            MaxElement = n;
        }
    }
    // insert one node into a list
    public void insertIntoList(treeNode theNode, treeNode theListStarter) {
        theNode.rightSibling = theListStarter;
        theNode.leftSibling = theListStarter.leftSibling;
        theListStarter.leftSibling.rightSibling = theNode;
        theListStarter.leftSibling = theNode;
    }
    //Remove Max based on remove()
    public datafield RemoveMax() {
        if(MaxElement == null) {
            return null;
        }
        datafield ans = remove(MaxElement);
        pairwiseCombine(MaxElement);
        return ans;
    }
    public void pairwiseCombine(treeNode starter) {
        if (starter == null) return;
        if (starter.rightSibling == starter) return;
        // store trees in different degrees
        Map<Integer, treeNode> degreeTable = new HashMap<>();
        treeNode pointer = starter;
        // use a Queue to keep track of all trees in top level may need to be combined
        Queue<treeNode> q = new LinkedList<>();
        do {
            q.offer(pointer);
            pointer = pointer.rightSibling;
        } while (pointer != starter);
        while (!q.isEmpty()) {
            pointer = q.poll();
            treeNode newNode = null;
            if (!degreeTable.containsKey(pointer.getDegree())) {
                degreeTable.put(pointer.getDegree(), pointer);
            }
            else {
                treeNode partner = degreeTable.remove(pointer.getDegree());
                if (partner.getAmount() > pointer.getAmount()) {
                    newNode = AbeSubtreeOfB(pointer, partner);
                }
                else {
                    // when partner.amount == pointer.amount, partner cannot be subtree if it is also the Max for now
                    if (partner.getAmount() == pointer.getAmount() && partner == MaxElement) {
                        newNode = AbeSubtreeOfB(pointer, partner);
                    }
                    else {
                        newNode = AbeSubtreeOfB(partner, pointer);
                    }
                }
            }
            if (newNode != null) {
                q.offer(newNode);
            }
        }
    }
    //Let tree a be one subtree of b
    public treeNode AbeSubtreeOfB(treeNode a, treeNode b) {
        getOutOfCurrentList(a);
        a.parent = b;
        a.ChildCut = false;
        b.addDegree(1);
        if (b.oneOfchilds == null) {
            b.oneOfchilds = a;
            //getOutOfCurrentList didn't reset a's siblings;
            a.leftSibling = a;
            a.rightSibling = a;
            return b;
        }
        insertIntoList(a, b.oneOfchilds);
        return b;
    }
    //find the Max in a top circle from a starter
    public void findtheMax(treeNode starter){
        if (starter == null) {
            return;
        }
        treeNode pointer = starter;
        MaxElement = starter;
        do {
            pointer.parent = null;
            if (pointer.getAmount() > MaxElement.getAmount()) {
                MaxElement = pointer;
            }
            pointer = pointer.rightSibling;
        } while (pointer != starter);
    }

    // Meld two max heaps
    public void meld(treeNode a, treeNode b) {
        combineTwoList(a, b);
        findtheMax(b);
    }
    //combine two lists into one list at top level
    public void combineTwoList(treeNode OneChild, treeNode theMax) {
        if (OneChild == null) {
            return;
        }
        if (theMax == null) {
            treeNode p = OneChild;
            do {
                p.parent = null;
                p = p.rightSibling;
            } while (p != OneChild);
            return;
        }
        // set the parent node of nodes in a list to their new parent
        if (OneChild.parent != null) {
            treeNode p = OneChild;
            do {
                p.parent = null;
                p = p.rightSibling;
            } while (p != OneChild);
        }
        OneChild.rightSibling.leftSibling = theMax.leftSibling;
        theMax.leftSibling.rightSibling = OneChild.rightSibling;
        OneChild.rightSibling = theMax;
        theMax.leftSibling = OneChild;
    }
    //remove theNode from the doubly linked list
    public void getOutOfCurrentList(treeNode theNode) {
        theNode.leftSibling.rightSibling = theNode.rightSibling;
        theNode.rightSibling.leftSibling = theNode.leftSibling;
        theNode.parent = null;
    }
    // Remove arbitrary node
    public datafield remove(treeNode theNode) {
        if (theNode == null) {
            return null;
        }
        hashtable.remove(theNode.getHashtag(), theNode);
        datafield ans = new datafield(theNode.getHashtag(), theNode.getAmount());
        // if theNode is the only item in this level
        if (theNode.rightSibling == theNode) {
            //if theNode is in top level
            if (theNode == MaxElement && theNode.parent == null) {
                MaxElement = null;
            }
            // in lower level
            else {
                theNode.parent.oneOfchilds = null;
                theNode.parent.minusDegree();
                //should be replaced by cascading cut
                cascadingCut(theNode.parent);
                theNode.parent = null;
            }
        }
        //theNode has siblings in this level
        else {
            //if the node is in top level
            if (theNode.parent == null) {
                getOutOfCurrentList(theNode);
                if (theNode == MaxElement) {
                    findtheMax(MaxElement.rightSibling);
                    // getOutOfCurrentList didn't reset theNode's siblings
                    theNode.leftSibling = theNode;
                    theNode.rightSibling = theNode;
                }
                else {
                    theNode.leftSibling = theNode;
                    theNode.rightSibling = theNode;
                }
            }
            else {
                theNode.parent.minusDegree();
                // Set its parent's child to its right sibling;
                if (theNode.parent.oneOfchilds == theNode) {
                    if (theNode.rightSibling == theNode) {
                        theNode.parent.oneOfchilds = null;
                    } else {
                        theNode.parent.oneOfchilds = theNode.rightSibling;
                    }
                }
                treeNode parent = theNode.parent;
                getOutOfCurrentList(theNode);
                theNode.parent = null;
                // getOutOfCurrentList didn't reset theNode's siblings
                theNode.rightSibling = theNode;
                theNode.leftSibling = theNode;
                cascadingCut(parent);
            }
        }
        if (theNode.oneOfchilds != null) {
                /*
                Combine top-level list and children list of theNode; do not pairwise combine equal degree trees.
                */
            combineTwoList(theNode.oneOfchilds, MaxElement);
            findtheMax(theNode.oneOfchilds);
            theNode.oneOfchilds = null;
        }

        return ans;
    }
    public void cascadingCut(treeNode starter) {
        if (starter.ChildCut == false) {
            starter.ChildCut = true;
            return;
        }
        if (starter.parent == null) {
            return;
        }
        treeNode parent = starter.parent;
        starter.parent = null;
        parent.minusDegree();
        if (parent.oneOfchilds == starter) {
            if (starter.rightSibling == starter) {
                parent.oneOfchilds = null;
            } else {
                parent.oneOfchilds = starter.rightSibling;
            }
        }
        getOutOfCurrentList(starter);
        insertIntoList(starter, MaxElement);
        cascadingCut(parent);
    }
    public void IncreaseKey(String theNode, int theAmount) {
        treeNode pointer = hashtable.get(theNode);
        pointer.addAmount(theAmount);
        if (pointer.parent == null) {
            findtheMax(MaxElement);
            return;
        }
        else if ( pointer.parent.getAmount() >= pointer.getAmount()) {
            return;
        }
        treeNode parent = pointer.parent;
        //set childNode
        if (parent.oneOfchilds == pointer) {
            if (pointer.rightSibling == pointer) {
                parent.oneOfchilds = null;
            } else {
                parent.oneOfchilds = pointer.rightSibling;
            }
        }
        getOutOfCurrentList(pointer);
        pointer.parent = null;
        insertIntoList(pointer, MaxElement);
        findtheMax(MaxElement);
        cascadingCut(parent);
    }
    public void addData(String theNode, int theAmount) {
        //If this is the first time this hashtag appears, just do an insert operation
        if (!hashtable.containsKey(theNode)) {
            Insert(theNode,theAmount);
        }
        else {
            IncreaseKey(theNode, theAmount);
        }
    }
}
