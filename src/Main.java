import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            MemorySpace.delay = 2000;
        } else {
            try {
                int delay = Integer.parseInt(args[0]);
                MemorySpace.delay = delay;
            } catch (IllegalArgumentException e) {
                System.out.println("arument must be an integer");
                return;
            }
        }

        Scanner scanner = new Scanner(System.in);

        String input = "";
        int size = 0;
        boolean isVaild = false;
        while (!isVaild) {
            System.out.println("Enter is the size in words of the Heap you would like to simulate:");
            input = scanner.nextLine();
            try {
                size = Integer.parseInt(input);
                isVaild = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input: the second part must be a number.\ntry again:");
            }
        }

        MemorySpace memory = new MemorySpace(size);
        StdDraw.setCanvasSize(800, 600);

        while (true) {

            // Ask for user input
            System.out.println("Enter a command (malloc / free / defrag) and than a number:");
            input = scanner.nextLine();

            // Split the input into two parts
            String[] parts = input.split(" ");
            // Validate the first part is malloc / free / defrag
            if (((parts[0].equals("malloc") || parts[0].equals("free")) && parts.length == 2)
                    || (parts[0].equals("defrag") && parts.length == 1)) {
                try {
                    if (parts.length == 2) {
                        int num = Integer.parseInt(parts[1]);
                        invoke(memory, parts[0], num);
                    } else {
                        invokeDefrag(memory, parts[0]);
                    }

                } catch (NumberFormatException e) {
                    System.out.println("Invalid input: the second part must be a number.");
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (SecurityException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println(
                        "Invalid input: the first word must be 'malloc / free / defrag' and the second a number");
            }
        }
    }

    public static void invoke(MemorySpace memory, String methodName, int num)
            throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException {
        Method method = MemorySpace.class.getMethod(methodName, int.class);
        System.out.println("Performing: " + methodName + " (" + num + ")");
        method.invoke(memory, num);

    }

    public static void invokeDefrag(MemorySpace memory, String methodName)
            throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException {
        Method method = MemorySpace.class.getMethod(methodName);
        System.out.println("Performing: " + methodName);
        method.invoke(memory);
    }
}
