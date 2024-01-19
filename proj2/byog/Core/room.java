package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class room {
    public Position pos;
    public int width, height;
    private static class Position {
        public int xpos, ypos;
        public Position(int x, int y){
            xpos = x;
            ypos = y;
        }
    }
    public room(int w, int h, int x, int y){
        width = w;
        height = h;
        pos = new Position(x, y);
    }
    public boolean is_out_of_world(int Width, int Height){
        if (pos.xpos + width > Width)
            return true;
        if (pos.ypos + height > Height)
            return true;
        return false;
    }
    public int[][] intersect_part(int[][] picture){
        int Width = picture.length, Height = picture[0].length;
        int[][] res = new int[Width][Height];
        for (int i = 0; i < Width; i++){
            for (int j = 0; j < Height; j++)
                res[i][j] = 0;
        }
        for (int i = 0; i < width; i++){
            for (int j = 0; j < height; j++){
                if (picture[i + this.pos.xpos][j + this.pos.ypos] != 0)
                    res[i + this.pos.xpos][j + this.pos.ypos] = 1;
            }
        }
        for (int i = 0; i < width; i++) {
            if (picture[i + pos.xpos][pos.ypos] == 2)
                res[i + pos.xpos][pos.ypos] = 2;
            if (picture[i + pos.xpos][height - 1 + pos.ypos] == 2)
                res[i + pos.xpos][height - 1 + pos.ypos] = 2;
        }
        for (int j = 0; j < height; j++) {
            if (picture[pos.xpos][j + pos.ypos] == 2)
                res[pos.xpos][j + pos.ypos] = 2;
            if (picture[width - 1 + pos.xpos][j + pos.ypos] == 2)
                res[width - 1 + pos.xpos][j + pos.ypos] = 2;

        }
        return res;
    }

    public void draw(TETile[][] world, int[][] picture){
        int Width = world.length, Height = world[0].length;
        if (!is_out_of_world(Width, Height)) {
            for (int i = 1; i < width - 1; i++) {
                for (int j = 1; j < height - 1; j++) {
                    world[i + pos.xpos][j + pos.ypos] = Tileset.FLOOR;
                    picture[i + pos.xpos][j + pos.ypos] = 1;
                }
            }
            for (int i = 0; i < width; i++) {
                if (picture[i + pos.xpos][pos.ypos] != 1){
                    world[i + pos.xpos][pos.ypos] = Tileset.WALL;
                    picture[i + pos.xpos][pos.ypos] = 2;
                }
                if (picture[i + pos.xpos][height - 1 + pos.ypos] != 1){
                    world[i + pos.xpos][height - 1 + pos.ypos] = Tileset.WALL;
                    picture[i + pos.xpos][height - 1 + pos.ypos] = 2;
                }
            }
            for (int j = 0; j < height; j++) {
                if (picture[pos.xpos][j + pos.ypos] != 1){
                    world[pos.xpos][j + pos.ypos] = Tileset.WALL;
                    picture[pos.xpos][j + pos.ypos] = 2;
                }
                if (picture[width - 1 + pos.xpos][j + pos.ypos] != 1){
                    world[width - 1 + pos.xpos][j + pos.ypos] = Tileset.WALL;
                    picture[width - 1 + pos.xpos][j + pos.ypos] = 2;
                }
            }
            int[][] intersect = this.intersect_part(picture);
            for (int i = 0; i < Width; i++){
                for (int j = 0; j < Height; j++){
                    if (intersect[i][j] == 1){
                        world[i][j] = Tileset.FLOOR;
                        picture[i][j] = 1;
                    }
                    if (intersect[i][j] == 2){
                        world[i][j] = Tileset.WALL;
                        picture[i][j] = 2;
                    }
                }
            }
        }
    }
}
