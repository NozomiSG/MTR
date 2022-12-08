import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;


public class TransformGraph {

    public static String[][] mtr = new String[13][50];
    public static HashMap<String, Integer> map = new HashMap<>();
    public static String[] nameList = new String[100];
    public static int index = 0;
    public static boolean[][] adjacencyMatrix;

    public static void main(String[] args) {
        String de;
        String re;
        readFile();
        storeInHashMap();
        adjacencyMatrix = new boolean[index][index];
        generateGraph();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Please enter depart station: ");
            de = scanner.nextLine();
            if (map.containsKey(de))
                break;
            else
                System.out.println("No station found, please enter again!");
        }
        while (true) {
            System.out.print("Please enter arrival station: ");
            re = scanner.nextLine();
            if (map.containsKey(re))
                break;
            else
                System.out.println("No station found, please enter again!");
        }
        BFS(map.get(de), map.get(re));
    }
    public static void readFile() {
        // Read stations by subway lines and store it into mtr array
        try{
            int i = 0;
            String line;
            BufferedReader reader = new BufferedReader(new FileReader("src/mtr_dataset.csv"));
            while ((line=reader.readLine())!=null) {
                mtr[i++] = line.split(",");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void storeInHashMap() {
        for(String[] line : mtr)
            for (String station : line) {
                if (!map.containsKey(station)) {
                    nameList[index] = station;
                    map.put(station, index++);
                }
            }
    }

    public static void generateGraph() {
        int x, y;
        for(String[] line : mtr)
            for (int i=0; i<line.length-1; i++) {
                x = map.get(line[i]);
                y = map.get(line[i+1]);
                adjacencyMatrix[x][y] = true;
                adjacencyMatrix[y][x] = true;
            }
    }

    public static void BFS(int de, int ar) {
        int node;
        int[] edge = new int[index];
        ArrayList<String> path = new ArrayList<>();
        Queue<Integer> queue = new LinkedList<>();
        Queue<Integer> visited = new LinkedList<>();

        queue.offer(de);
        visited.offer(de);

        while (!queue.isEmpty()) {
            node = queue.poll();
            for (int i=0; i<adjacencyMatrix[node].length; i++) {
                if (adjacencyMatrix[node][i] && !visited.contains(i)) {
                    visited.offer(i);
                    edge[i] = node;
                    queue.offer(i);
                    if (i == ar){
                        queue.clear();
                        break;
                    }
                }
            }
        }
        int dest = ar;
        while (dest != de) {
            path.add(nameList[dest]);
            dest = edge[dest];
        }
        path.add(nameList[de]);
        Collections.reverse(path);
        System.out.println(path);
        System.out.println("Total "+(path.size()-1)+" stops!");
    }


}
