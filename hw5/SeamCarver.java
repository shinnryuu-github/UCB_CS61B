import edu.princeton.cs.algs4.Picture;

public class SeamCarver {
    private Picture picture;
    private double[][] energy, buffer;
    private int width, height;

    private static double abs(double d){
        return (d >= 0)? d : -d;
    }
    public SeamCarver(Picture picture){
        this.picture = picture;
        this.width = picture.width();
        this.height = picture.height();
        this.energy = new double[width][height];
        this.buffer = new double[width][height];
        for (int x = 0; x < width; x++){
            for (int y = 0; y < height; y++){
                double Rx, Gx, Bx, Ry, Gy, By;
                if (x == 0){
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


                if (y == 0){
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
        int i = 0, j = 0, start = 0;
        double min = Double.POSITIVE_INFINITY;
        for (i = 0; i < width(); i++){
            if (energy(i, j) < min){
                start = i;
                min = energy(i,j);
            }
        }
        res[0] = start;
        for (j = 1; j <= height() - 1; j++){
            int x1 = res[j - 1] - 1, x2 = res[j - 1], x3 = res[j - 1] + 1;
            if (x1 < 0){
                double e2 = energy(x2, j), e3 = energy(x3, j);
                res[j] = (e2 <= e3) ? x2 : x3;
            }
            else if (x3 > width() - 1){
                double e1 = energy(x1, j), e2 = energy(x2, j);
                res[j] = (e1 <= e2) ? x1 : x2;
            }
            else {
                double e1 = energy(x1, j), e2 = energy(x2, j), e3 = energy(x3, j);
                if (e1 <= e2) {
                    if (e1 <= e3)
                        res[j] = x1;
                    else
                        res[j] = x3;
                } else {
                    if (e2 <= e3)
                        res[j] = x2;
                    else
                        res[j] = x3;
                }
            }
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
