public class treeNode {
    private int degree;
    // the frequency is stored by data;
    private datafield data;
    public treeNode oneOfchilds;
    public treeNode leftSibling;
    public treeNode rightSibling;
    public treeNode parent;
    public boolean ChildCut;

    treeNode(String theNode, int amount) {
        data = new datafield(theNode, amount);
        degree = 0;
        oneOfchilds = null;
        leftSibling = this;
        rightSibling = this;
        parent = null;
        ChildCut = false;
    }
    public int getAmount() {
        return data.getAmount();
    }
    public String getHashtag() {
        return data.getHashtag();
    }
    public int getDegree() {
        return degree;
    }
    public void addAmount(int amount) {
        data.addAmount(amount);
    }
    public void minusDegree() {
        degree--;
    }
    public void addDegree(int amount) {
        degree += amount;
    }
}
class datafield {
    private int amount;
    private String hashtag;

    datafield(String hashtag, int amount) {
        this.amount = amount;
        this.hashtag = hashtag;
    }

    public void addAmount(int amount) {
        this.amount += amount;
    }
    public String getHashtag() {
        return hashtag;
    }
    public int getAmount() {
        return amount;
    }
}

