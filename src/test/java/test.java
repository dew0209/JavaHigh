public class test {
    boolean st[][] = new boolean[110][110];
    int check[][] = new int[110][110];
    int dx[] = {-1,1,0,0},dy[] = {0,0,-1,1};
    public int[][] bicycleYard(int[] position, int[][] terrain, int[][] obstacle) {
        int n = terrain.length;
        dfs(position[0],position[1],terrain,obstacle,n,terrain[0].length,1);
        int num = 0;
        for(int i = 0;i < n;i++){
            for(int j = 0;j < terrain[0].length;j++){
                if(st[i][j])num++;
                System.out.print(st[i][j] + " ");
            }
            System.out.println();
        }
        int res[][] = new int[num][];
        int idx = 0;
        for(int i = 0;i < n;i++){
            for(int j = 0;j < terrain[0].length;j++){
                if(st[i][j])res[idx++] = new int[]{i,j};
            }
        }
        return res;
    }

    private void dfs(int x, int y, int[][] terrain, int[][] obstacle,int n,int m,int sp) {
        check[x][y]++;
        System.out.println("debug");
        for(int i = 0;i < 4;i++){
            int x1 = x + dx[i],y1 = y + dy[i];
            //System.out.println(x1 + " " + y1);
            if(x1 >= n || x1 < 0 || y1 >= m || y1 < 0 || st[x1][y1] || check[x1][y1] > 4)continue;

            int obs = terrain[x][y] - terrain[x1][y1] - obstacle[x1][y1];
            System.out.print(obs);
            if(obs <= 0 && sp - Math.abs(obs) <= 0)continue;
            if(obs <= 0 && sp - Math.abs(obs) == 1)st[x1][y1] = true;
            dfs(x1,y1,terrain,obstacle,n,m,sp + obs);
        }
    }
}
