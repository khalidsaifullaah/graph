import java.util.*;

class Pair{
    int destination,weight;
    public Pair(int destination, int weight) {
        this.destination = destination;
        this.weight = weight;
    }
}

public class Main {
    static ArrayList<String> vertexNames=new ArrayList<>();
    static HashMap<String,Integer> vertexNumber=new HashMap<>();
    static int[] distance;
    static String[] color;
    static String[] parent;
    static int[] visitTime;
    static int[] finishTime;
    static int time=0;
    static LinkedList<Integer> TopologicalSortedList;

    public static void main(String[] args) {
        Scanner scan=new Scanner(System.in);
        System.out.println("Is the graph directed? (yes/no): ");
        String isDirected=scan.nextLine(); isDirected.toLowerCase();
        int numOfVertices=scan.nextInt();
        int numOfEdges=scan.nextInt();
        scan.nextLine();

        ArrayList<Pair>[] graph=new ArrayList[numOfVertices];
        // initializing ArrayList
        for (int i = 0; i < numOfVertices; i++) {
            graph[i] = new ArrayList<Pair>();
        }
        //initializing vertex names
        for(int i=0;i<numOfVertices;i++){
            String temp=scan.nextLine();
            vertexNames.add(temp);
            vertexNumber.put(temp,i);
        }
        //initializing edges
        for(int i=0;i<numOfEdges;i++){
            //for directed graph
            if(isDirected.contains("yes")){
                String source=scan.next();
                String destination=scan.next();
                Integer src=vertexNumber.get(source);
                int weight=scan.nextInt();
                scan.nextLine();
                Pair Adjacent=new Pair(vertexNumber.get(destination),weight);
                graph[src].add(Adjacent);
            }
            //for undirected graph
            else{
                String source=scan.next();
                String destination=scan.next();
                Integer src=vertexNumber.get(source);
                int weight=scan.nextInt();
                scan.nextLine();
                Pair Adjacent=new Pair(vertexNumber.get(destination),weight);
                graph[src].add(Adjacent);
                Pair Adjacent2=new Pair(src,weight);
                graph[vertexNumber.get(destination)].add(Adjacent2);
            }
        }

        while (true){
            System.out.println("Enter the number of your desired operation: ");
            System.out.println("1. BFS & Shortest Path");
            System.out.println("2. DFS & Topological Sort");
            System.out.println("3. Print Graph");
            System.out.println("4. Close Program");
            int n=scan.nextInt();
            scan.nextLine();
            if(n==1){
                System.out.print("Enter Shortest path's source and destination: ");
                String source=scan.next();
                String destination=scan.next();
                BFS(graph,source,numOfVertices);
                PrintShortestPath(destination);
                System.out.println();
            }
            else if(n==2){
                TopologicalSortedList=new LinkedList<>();
                DFS(graph,numOfVertices);
                PrintTopologicalSortedList();
                System.out.println();
            }
            else if(n==3){
                print(graph,numOfVertices);
                System.out.println();

            }
            else {
                break;
            }
        }
        scan.close();

    }

    public static void BFS(ArrayList<Pair>[] graph,String source,int n){
        distance=new int[n];
        color=new String[n];
        parent=new String[n];
        //initializing
        for(int i=0;i<n;i++){
            color[i]="white";
            distance[i]=Integer.MAX_VALUE;
            parent[i]="NIL";
        }
        //For disconnected graph
//      for(int i=0;i<n;i++){
//         f(color[i]=="white")
//         BFS_Visit(graph,i);
//      }
        int i=vertexNumber.get(source);
        BFS_Visit(graph,i);
    }

    public static void BFS_Visit(ArrayList<Pair>[] graph,int u){
        color[u]="grey";
        distance[u]=0;
        parent[u]="NIL";
        Queue<Integer> Q=new LinkedList<>();
        Q.add(u);
        while (Q.size()>0){
            u=Q.remove();
            for(int i=0;i<graph[u].size();i++){
                Pair Adjacent=graph[u].get(i);
                if(color[Adjacent.destination]=="white"){
                    color[Adjacent.destination]="grey";
                    distance[Adjacent.destination]=distance[u]+1;
                    parent[Adjacent.destination]=vertexNames.get(u);
                    Q.add(Adjacent.destination);
                }
            }
            color[u]="black";
        }
    }

    public static void PrintShortestPath(String destination){
        if(parent[vertexNumber.get(destination)].contains("NIL")){
            System.out.print(vertexNames.get(vertexNumber.get(destination)));
            return;
        }
        else {
            PrintShortestPath(parent[vertexNumber.get(destination)]);
            System.out.print("-->"+vertexNames.get(vertexNumber.get(destination)));
        }
    }

    public static void DFS(ArrayList<Pair>[] graph,int n){
        color=new String[n];
        visitTime=new int[n];
        finishTime=new int[n];
        for (int i=0;i<n;i++){
            color[i]="white";
        }
        for(int i=0;i<n;i++) {
            if (color[i] == "white")
                DFS_Visit(graph, i);
        }
    }

    public static void DFS_Visit(ArrayList<Pair>[] graph,int u){
        color[u]="grey";
        time++;
        visitTime[u]=time;
        for(int i=0;i<graph[u].size();i++){
            Pair Adjacent=graph[u].get(i);
            if(color[Adjacent.destination]=="white")
                DFS_Visit(graph,Adjacent.destination);
        }
        color[u]="black";
        time++;
        finishTime[u]=time;
        TopologicalSortedList.addFirst(u);
    }

    public static void PrintTopologicalSortedList(){
        System.out.print("Topological sorted order of the vertices: ");
        for (Integer x:TopologicalSortedList){
            System.out.print(vertexNames.get(x)+" ");
        }
        System.out.println();
    }
    
    public static void print(ArrayList<Pair>[] graph,int n){
        for(int i=0;i<n;i++){
            System.out.print(vertexNames.get(i)+"--> ");
            for(int j=0;j<graph[i].size();j++){
                Pair Adjacent=graph[i].get(j);
                System.out.print(vertexNames.get(Adjacent.destination)+"-"+Adjacent.weight+" ");
            }
            System.out.println();
        }
    }

}
