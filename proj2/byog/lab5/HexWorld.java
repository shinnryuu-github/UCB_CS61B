package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private int Height, Width;
    private static final long SEED = 287326;
    private static final Random RANDOM = new Random(SEED);

    public class Posision{
        int xpos, ypos;
        public Posision(int x, int y){
            xpos = x;
            ypos = y;
        }
    }
    public static int get_single_width(int sidelenth, int height){
        if (height <= sidelenth){
            return sidelenth + 2 * height - 2;
        }
        return 3 * sidelenth - 2 * (height - sidelenth);
    }

    private static void addHexagon(TETile[][] world, Posision p, int sidelenth, TETile add){
        for (int i = 1; i <= sidelenth; i++){
            for (int j = 1; j <= get_single_width(sidelenth, i); j++){
                world[p.xpos + j - i][p.ypos - 1 + i] = TETile.colorVariant(add, 75, 75, 75, RANDOM);
            }
        }
        for (int i = sidelenth + 1; i <= 2 * sidelenth; i++){
            for (int j = 1; j <= get_single_width(sidelenth, i); j++){
                world[p.xpos + j + i - 2 * sidelenth - 1][p.ypos - 1 + i] = TETile.colorVariant(add, 75, 75, 75, RANDOM);
            }
        }
    }

    public static int get_Width(int sidelenth){
        return 2 * sidelenth + 3 * get_single_width(sidelenth, sidelenth) + 2;
    }

    private static int get_Height(int sidelenth){
        return 10 * sidelenth + 2;
    }

    private static TERenderer build_world(int Height, int Width, TETile[][] world){
        TERenderer ter = new TERenderer();
        ter.initialize(Width, Height);
        for (int x = 0; x < Width; x += 1) {
            for (int y = 0; y < Height; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        return ter;
    }

    private Posision get_Posision(int sidelenth, int row, int col){
        Posision res;
        int x, y;
        if (row == 1){
            res = new Posision( 2 * sidelenth + get_single_width(sidelenth, sidelenth),1);
        } else if (row == 9) {
            res = new Posision(2 * sidelenth + get_single_width(sidelenth, sidelenth),Height - 2 * sidelenth - 1);
        } else if (row % 2 == 0) {
            y = sidelenth + 2 * sidelenth * (row / 2 - 1);
            x = get_single_width(sidelenth, sidelenth) + 1 + (col - 1) * (sidelenth + get_single_width(sidelenth, sidelenth));
            res = new Posision(x , y + 1);
        } else {
            y = 2 * sidelenth + 2 * sidelenth * ((row - 1) / 2 - 1);
            x = sidelenth + (col - 1) * (sidelenth + get_single_width(sidelenth, sidelenth));
            res = new Posision(x, y + 1);
        }
        return res;
    }

    private TETile get_random_tetile(){
        int tileNum = RANDOM.nextInt(8);
        switch (tileNum) {
            case 0:
                return Tileset.WALL;
            case 1:
                return Tileset.FLOOR;
            case 2:
                return Tileset.GRASS;
            case 3:
                return Tileset.WATER;
            case 4:
                return Tileset.FLOWER;
            case 5:
                return Tileset.SAND;
            case 6:
                return Tileset.MOUNTAIN;
            case 7:
                return Tileset.TREE;
                default:return Tileset.MOUNTAIN;
        }
    }

    private static void HexagonWorld(int sidelenth){
        int Height = get_Height(sidelenth), Width = get_Width(sidelenth);
        TETile[][] world = new TETile[Width][Height];
        TERenderer ter = build_world(Height, Width, world);
        int[] shape = new int[]{1,2,3,2,3,2,3,2,1};
        HexWorld w = new HexWorld();
        w.Height = Height;
        w.Width = Width;
        for (int i = 0; i < 9; i++){
            for (int j = 1; j <= shape[i]; j++){
                Posision p = w.get_Posision(sidelenth, i + 1, j);
                TETile add = w.get_random_tetile();
                addHexagon(world,p,sidelenth,add);
            }
        }
        ter.renderFrame(world);
    }

    public static void main(String[] args){
        HexagonWorld(5);
    }
}
