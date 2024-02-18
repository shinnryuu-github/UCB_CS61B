import edu.princeton.cs.algs4.Picture;

public class SeamCarver {
    private final Picture picture;
    private double[][] energy, buffer;
    private int width, height;

    private static double abs(double d){
        return (d >= 0)? d : -d;
    }
    public SeamCarver(Picture picture){
        this.picture = new Picture(picture);
        this.width = picture.width();
        this.height = picture.height();
        this.energy = new double[width][height];
        this.buffer = new double[width][height];
        for (int x = 0; x < width; x++){
            for (int y = 0; y < height; y++){
                double Rx, Gx, Bx, Ry, Gy, By;
                if (x == 0 && x == width() - 1) {
                    Rx = 0;
                    Gx = 0;
                    Bx = 0;
                }
                else if (x == 0){
                    Rx = abs(picture.get(1,y).getRed() - picture.get(width() - 1, y).getRed());
                    Gx = abs(picture.get(1,y).getGreen() - picture.get(width() - 1, y).getGreen());
                    Bx = abs(picture.get(1,y).getBlue() - picture.get(width() - 1, y).getBlue());
                }
                else if (x == width() - 1){
                    Rx = abs(picture.get(0,y).getRed() - picture.get(width() - 2, y).getRed());
                    Gx = abs(picture.get(0,y).getGreen() - picture.get(width() - 2, y).getGreen());
                    Bx = abs(picture.get(0,y).getBlue() - picture.get(width() - 2, y).getBlue());
                }
                else{
                    Rx = abs(picture.get(x - 1,y).getRed() - picture.get(x + 1, y).getRed());
                    Gx = abs(picture.get(x - 1,y).getGreen() - picture.get(x + 1, y).getGreen());
                    Bx = abs(picture.get(x - 1,y).getBlue() - picture.get(x + 1, y).getBlue());
                }

                if (y == 0 && y == height() - 1){
                    Ry = 0;
                    Gy = 0;
                    By = 0;
                }
                else if (y == 0){
                    Ry = abs(picture.get(x,1).getRed() - picture.get(x, height() - 1).getRed());
                    Gy = abs(picture.get(x,1).getGreen() - picture.get(x, height() - 1).getGreen());
                    By = abs(picture.get(x,1).getBlue() - picture.get(x, height() - 1).getBlue());
                }
                else if (y == height() - 1){
                    Ry = abs(picture.get(x,0).getRed() - picture.get(x, height() - 2).getRed());
                    Gy = abs(picture.get(x,0).getGreen() - picture.get(x, height() - 2).getGreen());
                    By = abs(picture.get(x,0).getBlue() - picture.get(x, height() - 2).getBlue());
                }
                else{
                    Ry = abs(picture.get(x, y - 1).getRed() - picture.get(x, y + 1).getRed());
                    Gy = abs(picture.get(x, y - 1).getGreen() - picture.get(x, y + 1).getGreen());
                    By = abs(picture.get(x, y - 1).getBlue() - picture.get(x, y + 1).getBlue());
                }
                energy[x][y] = Rx * Rx + Gx * Gx + Bx * Bx + Ry * Ry + Gy * Gy + By * By;
                buffer[x][y] = energy[x][y];
            }
        }
    }
    public Picture picture(){
        return new Picture(picture);
    }
    public int width(){
        return width;
    }
    public int height(){
        return height;
    }
    public double energy(int x, int y){
        if (x < 0 || x > width - 1 || y < 0 || y > height - 1)
            throw new java.lang.IndexOutOfBoundsException();
        return energy[x][y];
    }

    private void rotate(){
        energy = new double[height][width];
        for (int y = 0; y < height; y++){
            for (int x = 0; x < width; x++)
                energy[y][x] = buffer[x][y];
        }
        buffer = energy;
        int tmp = height;
        height = width;
        width = tmp;
    }
    public int[] findHorizontalSeam(){
        rotate();
        int[] res = findVerticalSeam();
        rotate();
        return res;
    }
    public int[] findVerticalSeam(){
        int[] res = new int[height()];
        double[][] M = new double[width()][height()];
        int[][] prev = new int[width()][height()];
        for (int i = 0; i < width(); i++){
            M[i][0] = energy(i, 0);
            prev[i][0] = -1;
        }
        for (int y = 1; y < height(); y++){
            for (int x = 0; x < width(); x++){
                if (x == 0 && x == width() - 1){
                    M[x][y] = M[x][y - 1] + energy(x, y);
                    prev[x][y] = x;
                }
                else if (x == 0){
                    if (M[x][y - 1] <= M[x + 1][y - 1]){
                        prev[x][y] = x;
                        M[x][y] = M[x][y - 1] + energy(x, y);
                    }
                    else {
                        prev[x][y] = x + 1;
                        M[x][y] = M[x + 1][y - 1] + energy(x, y);
                    }
                }
                else if (x == width() - 1){
                    if (M[x][y - 1] <= M[x - 1][y - 1]){
                        prev[x][y] = x;
                        M[x][y] = M[x][y - 1] + energy(x, y);
                    }
                    else {
                        prev[x][y] = x - 1;
                        M[x][y] = M[x - 1][y - 1] + energy(x, y);
                    }
                }
                else{
                    if (M[x][y - 1] <= M[x - 1][y - 1]){
                        if (M[x][y - 1] <= M[x + 1][y - 1]){
                            prev[x][y] = x;
                            M[x][y] = M[x][y - 1] + energy(x, y);
                        }
                        else{
                            prev[x][y] = x + 1;
                            M[x][y] = M[x + 1][y - 1] + energy(x, y);
                        }
                    }
                    else {
                        if (M[x - 1][y - 1] <= M[x + 1][y - 1]){
                            prev[x][y] = x - 1;
                            M[x][y] = M[x - 1][y - 1] + energy(x, y);
                        }
                        else{
                            prev[x][y] = x + 1;
                            M[x][y] = M[x + 1][y - 1] + energy(x, y);
                        }
                    }
                }
            }
        }
        int last = 0;
        double min = Double.POSITIVE_INFINITY;
        for (int i = 0; i < width(); i++){
            if (M[i][height() - 1] < min){
                min = M[i][height() - 1];
                last = i;
            }
        }
        res[height() - 1] = last;
        for (int i = height() - 2; i >= 0; i--){
            res[i] = prev[last][i + 1];
            last = prev[last][i + 1];
        }
        return res;
    }
    public void removeHorizontalSeam(int[] seam){
        SeamRemover.removeHorizontalSeam(picture(), seam);
    }
    public void removeVerticalSeam(int[] seam){
        SeamRemover.removeVerticalSeam(picture(), seam);
    }
}
