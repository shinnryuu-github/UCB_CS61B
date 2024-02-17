import java.util.HashMap;
import java.util.Map;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {

    public Rasterer() {
        // YOUR CODE HERE
    }
    private static int find_depth(double LDPP0){
        int res = 0;
        double LDPP = 0.00034332275390625;
        while (res < 7 && LDPP >LDPP0){
            res++;
            LDPP /= 2;
        }
        return res;
    }

    private static double ullo(int d, int x, int y){
        return MapServer.ROOT_ULLON + x * (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / Math.pow(2, d);
    }

    private static double ulla(int d, int x, int y){
        return MapServer.ROOT_ULLAT - y * (MapServer.ROOT_ULLAT - MapServer.ROOT_LRLAT) / Math.pow(2, d);
    }

    private static double lrlo(int d, int x, int y){
        return MapServer.ROOT_ULLON + (x + 1) * (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / Math.pow(2, d);
    }

    private static double lrla(int d, int x, int y){
        return MapServer.ROOT_ULLAT - (y + 1) * (MapServer.ROOT_ULLAT - MapServer.ROOT_LRLAT) / Math.pow(2, d);
    }

    private static int find_ulx(int depth, double ullon){
        int res = (int) Math.pow(2,depth) - 1;
        double lon = ullo(depth, res, 0);
        while (res > 0 && lon > ullon){
            res--;
            lon = ullo(depth, res, 0);
        }
        return res;
    }

    private static int find_uly(int depth, double ulla){
        int res = (int) Math.pow(2,depth) - 1;
        double la = ulla(depth, 0, res);
        while (res > 0 && la < ulla){
            res--;
            la = ulla(depth, 0, res);
        }
        return res;
    }

    private static int find_lrx(int depth, double lrlon){
        int res = 0;
        double lon = lrlo(depth, res, 0);
        while (res < Math.pow(2,depth) - 1 && lon < lrlon){
            res++;
            lon = lrlo(depth, res, 0);
        }
        return res;
    }

    private static int find_lry(int depth, double lrlat){
        int res = 0;
        double lat = lrla(depth, 0, res);
        while (res < Math.pow(2,depth) - 1 && lat > lrlat){
            res++;
            lat = lrla(depth, 0, res);
        }
        return res;
    }
    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        // System.out.println(params);
        Map<String, Object> results = new HashMap<>();
        results.put("query_success", true);
        double LDPP0 = (params.get("lrlon") - params.get("ullon")) / params.get("w");
        int depth = find_depth(LDPP0);
        results.put("depth", depth);
        int ulx = find_ulx(depth, params.get("ullon"));
        int uly = find_uly(depth, params.get("ullat"));
        int lrx = find_lrx(depth, params.get("lrlon"));
        int lry = find_lry(depth, params.get("lrlat"));
        results.put("raster_ul_lon", ullo(depth, ulx, uly));
        results.put("raster_ul_lat", ulla(depth, ulx, uly));
        results.put("raster_lr_lon", lrlo(depth, lrx, lry));
        results.put("raster_lr_lat", lrla(depth, lrx, lry));
        String[][] render_grid = new String[lry - uly + 1][lrx - ulx + 1];
        for (int i = ulx; i <= lrx; i++){
            for (int j = uly; j <= lry; j++){
                render_grid[j - uly][i - ulx] = "d" + depth + "_x" + i + "_y" + j + ".png";
            }
        }
        results.put("render_grid", render_grid);
        return results;
    }

//    public static void main(String[] args){
//        Map<String, Double> params = new HashMap<>();
//        params.put("lrlon", -122.2104604264636);
//        params.put("ullon", -122.30410170759153);
//        params.put("w", 1091.0);
//        params.put("h", 566.0);
//        params.put("ullat", 37.870213571328854);
//        params.put("lrlat", 37.8318576119893);
//        Rasterer r = new Rasterer();
//        System.out.println(r.getMapRaster(params));
//    }
}
