import java.util.*;

public class Search {

    public static void main(String[] args){
        // Set<int[]> visited = new HashSet<>();
        // int[] a = new int[] {5,8};
        // int[] b = new int[] {2,1};
        // int[] c = new int[] {5,8};
        // visited.add(a);
        // //visited.add(b);
        // System.out.println(visited.contains(a));
        // System.out.println(visited.contains(b));
        // System.out.println(visited.contains(c));
        Location p = new Location(7,8);
        System.out.println(p);
        System.out.println(p.row());
        System.out.println(p.column());
        Set<Location> visited = new HashSet<>();
        visited.add(p);
        Location q = new Location(7,8);
        System.out.println(visited.contains(q));
    }

     /**
     * Finds the location of the nearest reachable cheese from the rat's position.
     * Returns a 2-element int array: [row, col] of the closest 'c'. If there are multiple
     * cheeses that are tied for the same shortest distance to reach, return
     * any one of them.
     * 
     * 'R' - the rat's starting position (exactly one)
     * 'o' - open space the rat can walk on
     * 'w' - wall the rat cannot pass through
     * 'c' - cheese that the rat wants to reach
     * 
     * If no rat is found, throws EscapedRatException.
     * If more than one rat is found, throws CrowdedMazeException.
     * If no cheese is reachable, throws HungryRatException
     *
     * oooocwco
     * woowwcwo
     * ooooRwoo
     * oowwwooo
     * oooocooo
     *
     * The method will return [0,4] as the nearest cheese.
     *
     * @param maze 2D char array representing the maze
     * @return int[] location of the closest cheese in row, column format
     * @throws EscapedRatException if there is no rat in the maze
     * @throws CrowdedMazeException if there is more than one rat in the maze
     * @throws HungryRatException if there is no reachable cheese
     */
    public static int[] nearestCheese(char[][] maze) throws EscapedRatException, CrowdedMazeException, HungryRatException {
        Queue<Location> seen = new ArrayDeque<>();
        Set<Location> visited = new HashSet<>();
        Location start = findRat(maze);
        seen.add(start);
        while(!seen.isEmpty()){
            Location current = seen.remove();
            if(visited.contains(current)) continue;
            visited.add(current);
            if(maze[current.row()][current.column()] == 'c') return new int[] {current.row(), current.column()};
            //seen.addAll(possibleMoves(maze, current));
            for(Location neighbor : possibleMoves(maze, current)) seen.add(neighbor);

        }
        throw new HungryRatException();
    }

    public static List<Location> possibleMoves(char[][] maze, Location current){
        int[][] moves = new int[][] {
            {-1,0},
            {1,0},
            {0,-1},
            {0,1}
        };
        List<Location> validMoves = new ArrayList<>();
        for(int[] move : moves){
            Location possibleMove = new Location(current.row() + move[0], current.column() + move[1]);
            if( possibleMove.row() >= 0 && possibleMove.row() < maze.length && 
                possibleMove.column() >= 0 && possibleMove.column() < maze[0].length && 
                maze[possibleMove.row()][possibleMove.column()] != 'w'){
                    validMoves.add(possibleMove);
               }
        }
        return validMoves;
    }

    public static Location findRat(char[][] maze){
        Location rat = null;
        for(int r = 0; r < maze.length; r++){
            for(int c = 0; c < maze[0].length; c++){
                if(maze[r][c] == 'R'){
                    if(rat != null) throw new CrowdedMazeException();
                    rat = new Location(r,c);
                }
            }
        }
        if(rat == null) throw new EscapedRatException();

        return rat;
        
    }
}