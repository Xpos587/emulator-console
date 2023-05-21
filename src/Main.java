import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    private static final String SHELL = "/bin/bash";
    private static final String VER = "0.1.1";
    private static String currentDir = "/home/container/";
    public static void executeDir(String command, String directory) throws IOException {
        String[] commandParts = new String[]{SHELL, "-c", "cd " + directory + " && " + command};
        Process process = Runtime.getRuntime().exec(commandParts);
        BufferedReader processReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String line;
        while((line = processReader.readLine()) != null) {
            System.out.println(line);
        }

        BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));

        while((line = errorReader.readLine()) != null) {
            System.err.println(line);
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.println("EmulatorConsole (SNAPSHOT-" + VER + ") by An5nym8us7 loaded successful!");

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.out.print(currentDir + " $ ");
            String input = reader.readLine();


            if (input.isEmpty()) continue;

            String[] parts = input.split("\\s+");
            String command = parts[0];
            List<String> arguments = new ArrayList<>();
            if (parts.length > 1) {
                arguments.addAll(Arrays.asList(parts).subList(1, parts.length));
            }

            switch (command) {
                case "cls" -> executeDir("clear", currentDir);
                case "cd" -> {
                    if (arguments.isEmpty()) {
                        System.out.println("Usage: cd <directory>");
                    } else {
                        File dir = new File(currentDir, arguments.get(0)).getCanonicalFile();
                        if (dir.isDirectory()) {
                            currentDir = dir.getAbsolutePath();
                        } else {
                            System.out.println("Directory not found: " + dir.getAbsolutePath());
                        }
                    }
                }
                case "myip" -> executeDir("curl https://ipinfo.io/ip", currentDir);
                case "python-setup" -> {
                    executeDir("curl https://raw.githubusercontent.com/Xpos587/emulator-console/main/python.setup.sh -O python.setup.sh", currentDir);
                    executeDir("chmod +x ./python.setup.sh && ./python.setup.sh", currentDir);
                }
                default -> executeDir(input, currentDir);
            }
        }
    }
}