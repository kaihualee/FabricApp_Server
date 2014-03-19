import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @(#)body.java, 2013-8-6. 
 * 
 */

/**
 * @author likaihua
 */

public class body {

    private s_rgb[] color = { new s_rgb("红", "Red", 255, 0, 0),
        new s_rgb("橙", "Orange", 255, 165, 0),
        new s_rgb("黄", "Yellow", 255, 255, 0),
        new s_rgb("绿", "Green", 0, 0, 255),
        new s_rgb("蓝绿色", "Cyan", 0, 255, 255),
        new s_rgb("蓝", "Blue", 0, 0, 255),
        new s_rgb("紫", "Purple", 160, 32, 240),
        new s_rgb("米黄色", "Beige", 245, 245, 220),
        new s_rgb("黑", "Black", 10, 10, 10),
        new s_rgb("灰色", "Gray", 180, 180, 180),
        new s_rgb("白", "White", 251, 251, 251),
        new s_rgb("蓝", "White", -16711681),
        new s_rgb("绿", "White", -16711936),
        new s_rgb("紫", "White", -8388353),
        new s_rgb("黑", "White", -16777216),
        new s_rgb("白", "White", -1)};

    class s_rgb {
        private int r, g, b;

        private String name;

        private int hex;

        private String chs;

        public s_rgb(String chs, String name, int r, int g, int b) {
            this.chs = chs;
            this.name = name;
            this.r = r;
            this.g = g;
            this.b = b;
            Color tmp = new Color(this.r, g, b);
            this.hex = tmp.getRGB();
        }

        public s_rgb(String chs, String name, int hex) {
            this.chs = chs;
            this.name = name;
            this.hex = hex;
            Color tmp = new Color(this.hex);
            this.r = tmp.getRed();
            this.g = tmp.getGreen();
            this.b = tmp.getBlue();
        }

        public void display() {
            System.out.println(this.chs + "|" + this.name + "#" + this.hex
                + ";" + "RGB " + this.r +"," +this.g + "," + this.b);
        }

        /**
         * @return the r
         */
        public int getR() {
            return r;
        }

        /**
         * @param r
         *            the r to set
         */
        public void setR(int r) {
            this.r = r;
        }

        /**
         * @return the g
         */
        public int getG() {
            return g;
        }

        /**
         * @param g
         *            the g to set
         */
        public void setG(int g) {
            this.g = g;
        }

        /**
         * @return the b
         */
        public int getB() {
            return b;
        }

        /**
         * @param b
         *            the b to set
         */
        public void setB(int b) {
            this.b = b;
        }

        /**
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * @return the hex
         */
        public int getHex() {
            return hex;
        }

        /**
         * @param hex
         *            the hex to set
         */
        public void setHex(int hex) {
            this.hex = hex;
        }
    }

    public void compute() {
        int length = color.length;
        for (int nLoop = 0; nLoop < length; ++nLoop) {
            color[nLoop].display();
        }
    }

    public static void main(String[] args) {
        String filename = "hellp.xml";
        UUID uuid = UUID.randomUUID();

        int nCount = 10;
        for (int nLoop = 0; nLoop < nCount; ++nLoop) {
            System.out.println(String.valueOf(nLoop) + " : "
                + UUID.randomUUID().toString());
        }
        Map<Integer, String> map = new HashMap();
        List<String> list = new ArrayList();

        new body().compute();
    }
}
