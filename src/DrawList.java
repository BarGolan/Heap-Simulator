
public class DrawList {

    static int counter = 0;
    static double elementWidth = 0;
    static double elementHeight = 0;
    static double alloLocationX = 0.2;
    static double freeLocationX = 0.6;
    static double yLocation = 0.9;
    static String alloTitle = "Allocated List";
    static String freeTitle = "Free List";
    static double width = 0.3, height = 0.95;

    public static void draw(List list, double x, double y, double width, double height, String title) {
        int n = list.getSize();
        if (counter % 2 == 0) {
            elementWidth = width;
            elementHeight = height / (n + (n * 0.15)); // Add 10% margin between each element
        }
        double xPos = x + elementWidth / 2;
        double yPos = y - elementHeight / 2;

        StdDraw.text(xPos, yPos + 0.05 + elementHeight / 2, title);
        for (int i = 0; i < n; i++) {
            MemBlock current = list.getBlock(i);
            String text = current.getBaseAddress() + "   |   " + current.getLength(); // Concatenate the two fields
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.filledRectangle(xPos, yPos, elementWidth / 2, elementHeight / 2);
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.rectangle(xPos, yPos, elementWidth / 2, elementHeight / 2);
            StdDraw.text(xPos, yPos, text); // Display the concatenated string
            yPos -= elementHeight * 1.15; // Add 10% margin between each element
        }

        counter++;

    }

    public static void drawList(MemorySpace memory) {
        List allo = (List) memory.getAllocatedList();
        List free = (List) memory.getFreeList();

        StdDraw.setXscale(0, 1);
        StdDraw.setYscale(-0.1, 1.1);

        int alloSize = allo.getSize();
        int freeSize = free.getSize();

        if (alloSize > freeSize) {
            draw(allo, alloLocationX, yLocation, width, height, alloTitle);
            draw(free, freeLocationX, yLocation, width, height, freeTitle);
        } else {
            draw(free, freeLocationX, yLocation, width, height, freeTitle);
            draw(allo, alloLocationX, yLocation, width, height, alloTitle);
        }
    }
}
