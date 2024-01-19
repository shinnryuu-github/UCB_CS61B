package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import java.util.Random;
import byog.TileEngine.Tileset;

public class worldgenerator {
    private static int Height = 50, Width = 75;

    public static room random_room1(Random rand){
        int w = RandomUtils.uniform(rand, 3, Width / 5);
        int h = RandomUtils.uniform(rand, 3, Height / 7);
        int x = RandomUtils.uniform(rand, Width);
        int y = RandomUtils.uniform(rand, Height);
        return new room(w, h, x, y);
    }

    public static room random_room2(Random rand){
        int w = RandomUtils.uniform(rand, 3, Width / 7);
        int h = RandomUtils.uniform(rand, 3, Height / 5);
        int x = RandomUtils.uniform(rand, Width);
        int y = RandomUtils.uniform(rand, Height);
        return new room(w, h, x, y);
    }

    public static void modify(TETile[][] world, int[][] picture){
        for (int i = 1; i < Width - 1; i++){
            for (int j = 1; j < Height - 1; j++){
                if (picture[i][j] == 2){
                    if ((picture[i - 1][j] == 1 && picture[i + 1][j] == 1) || (picture[i][j-1] == 1 && picture[i][j+1] == 1)){
                        world[i][j] = Tileset.FLOOR;
                        picture[i][j] = 1;
                    }
                }
            }
        }
    }

    public static void add_door(TETile[][] world, int[][] picture){

    }

    public static TETile[][] generate(int seed){
        Random rand = new Random(seed);

        TETile[][] world = new TETile[Width][Height];
        int[][] picture = new int[Width][Height];

        for (int x = 0; x < Width; x += 1) {
            for (int y = 0; y < Height; y += 1) {
                world[x][y] = Tileset.NOTHING;
                picture[x][y] = 0;
            }
        }

        int cnt = RandomUtils.uniform(rand, 150, 300);
        for (int i = 0; i < cnt; i++){
            room r;
            if (i % 2 == 0)
                r = random_room1(rand);
            else
                r = random_room2(rand);
            r.draw(world, picture);
            modify(world,picture);
        }


        return world;
    }
}
