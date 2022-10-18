import java.util.ArrayList;


public class Network
{
    private int n;
    ArrayList<Node> allNodes;

    Node srcNode;

    public static void main(String args[])
    {
        Network network = new Network(7);
        Node nodeA = network.addNode("A");
        Node nodeB = network.addNode("B");
        Node nodeC = network.addNode("C");
        Node nodeD = network.addNode("D");
        Node nodeE = network.addNode("E");
        Node nodeF = network.addNode("F");
        Node nodeG = network.addNode("G");


        nodeA.link(nodeB, 0.32);
        nodeA.link(nodeC, 0.32);
        nodeB.link(nodeD, 1.00);
        nodeB.link(nodeE, 0.71);
        nodeC.link(nodeE, 0.34);
        nodeC.link(nodeG, 0.53);
        nodeD.link(nodeF, 0.33);
        nodeE.link(nodeG, 0.34);
        nodeF.link(nodeG, 0.43);



      /*  nodeA.link(nodeC, 0.7);
        nodeA.link(nodeB, 0.4);
        nodeC.link(nodeE, 0.3);
        nodeB.link(nodeE, 0.6);
*/


        System.out.println((double)network.sendPackages(nodeA, nodeG, 100000)/100000);
    }
    public Network(int n)
    {
        this.n = n;
        allNodes = new ArrayList<>();
    }

    public Node addNode(String name)
    {
        Node node = new Node(name);
        allNodes.add(node);
        return node;
    }

    private boolean sendPackage(Node src, Node dst)
    {

        src.setVisit(true);

        if(src.equals(dst))
            return true;

        for(int i = 0; i < src.linkedNodes.length; i++)
        {
            Node linkedNode = src.linkedNodes[i];

            if(linkedNode != null && !(linkedNode.visit))
                if(Math.random() > src.linkFailures[i])
                    if(sendPackage(linkedNode, dst) == false)
                    {
                        // try different route
                    }else
                    {
                        return true;
                    }



        }

        return false;


    }

    public int sendPackages(Node src, Node dst, int T)
    {
        int receivedPackage = 0;

        for(int i = 0; i < T; i++)
        {
            for (int j = 0; j < allNodes.size(); j++)
                allNodes.get(j).setVisit(false);

            if(sendPackage(src, dst))
                receivedPackage++;
        }

        return receivedPackage;
    }



    private class Node
    {
        Node[] linkedNodes;
        double[] linkFailures;
        String name;
        boolean visit;

        public Node(String name)
        {
            this.name = name;
            visit = false;
            linkedNodes = new Node[n-1];
            linkFailures = new double[n-1];
        }

        public void link(Node other, double Pe)
        {
            for(int i = 0; i < linkedNodes.length; i++)
            {
                if (linkedNodes[i] == null)
                {
                    linkedNodes[i] = other;
                    linkFailures[i] = Pe; // ---------- hm
                    other.link(this, Pe);
                    return;
                }
                if(linkedNodes[i].equals(other) || linkedNodes[i].equals(this)) // TODO Would not work all cases.
                    return;
            }
        }

        public void setVisit(boolean v)
        {this.visit = v;}
        public boolean isVisited(){return visit;}

        public String toString()
        {
            return this.name;
        }

    }




}
