// 231RDB234 Germans Veidemans 3
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    static Scanner sc;
    public static void print() {
        System.out.println("\n------------------------------------------------------------");
        System.out.printf("%-4s%-21s%-11s  %-4s     %s %-7s", "ID", "City", "Date", "Days", "Price", "Vehicle");
        System.out.print("\n------------------------------------------------------------");

        try (BufferedReader reader = new BufferedReader(new FileReader("db.csv"))){
            String line;
            while((line = reader.readLine()) != null){
                String[] linesp = line.split(";");
                
                System.out.printf("\n%-4s%-21s%-11s%6s%10s %-7s", linesp[0], linesp[1], linesp[2], linesp[3], linesp[4], linesp[5]);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        System.out.println("\n------------------------------------------------------------");
        check();
    }

    public static boolean check_ID(String command,String ID){
        if (command == "add"){
            if (ID.length() == 3){
                try (BufferedReader reader = new BufferedReader(new FileReader("db.csv"))){
                    String line;
                    while((line = reader.readLine()) != null){
                        String[] linesp = line.split(";");
                        try {
                            int IDBase = Integer.parseInt(linesp[0]);
                            if (IDBase == Integer.parseInt(ID)){
                                System.out.println("wrong id");
                                return false;
                            }
                        }
                        catch(Exception e) {
                            System.out.println("wrong id");
                            return false;
                        } 
                    }
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            } else {
                System.out.println("wrong id");
                return false;
            }
            return true;
        } else {
            if (ID.length() == 3){
                try (BufferedReader reader = new BufferedReader(new FileReader("db.csv"))){
                    String line;
                    while((line = reader.readLine()) != null){
                        String[] linesp = line.split(";");
                        try {
                            int IDBase = Integer.parseInt(linesp[0]);
                            if (IDBase == Integer.parseInt(ID)){
                                return true;
                            }

                        }
                        catch(Exception e) {
                            System.out.println("wrong id");
                            return false;
                        } 
                    }
                }
                catch (IOException e){
                    System.out.println("wrong id");
                    e.printStackTrace();
                }
            } else {
                System.out.println("wrong id");
                return false;
            }
            return false;
        }
    }

    public static void add(String info) {
        String[] parts = info.split(";");
        if (parts.length == 6){
            String ID = parts[0];

            boolean check = check_ID("add", ID);
            if (check == true){
                try (FileWriter fileWriter = new FileWriter("db.csv", true)) {
                    //  Lielas pirmais burts
                    char letter = Character.toUpperCase(parts[1].charAt(0));
                    parts[1] = letter + parts[1].toLowerCase().substring(1); 
                    // datuma pārbaude
                    SimpleDateFormat formatdate = new SimpleDateFormat("dd/MM/yyyy");
                    formatdate.setLenient(false);
                    try {
                        formatdate.parse(parts[2]);
                        String[] date_parts = parts[2].split("/");

                        if (date_parts[0].length() != 2 || date_parts[1].length() != 2){
                            System.out.println("wrong date");
                            check();
                        }
                    } catch (ParseException e){
                        System.out.println("wrong date");
                        check();
                    }
                    // dienas pārbāude
                    try {
                        int number = Integer.parseInt(parts[3]);
                        if (number <= 0) {
                            System.out.println("wrong day count");
                            check();
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("wrong day count");
                        check();
                    }
                    //  cenas pārbāude  
                    if (Character.isLetter(parts[4].charAt(parts[4].length() - 1))){
                        System.out.println("wrong price");
                        check();
                    }
                    boolean price_check = false;
                    try {
                        double number_double = Double.parseDouble(parts[4]);
                        if (number_double <=0) {
                            System.out.println("wrong price");
                            check();
                        } else {
                            price_check = true;
                        }
                    } catch (NumberFormatException e){
                        System.out.println("wrong price");
                        check();
                    }
                    if (price_check == false) {
                        try {
                            int number_int = Integer.parseInt(parts[4]);
                            if (number_int <=0) {
                                System.out.println("wrong price");
                                check();
                            } else {
                                price_check = true;
                            }
                        } catch (NumberFormatException e){
                            System.out.println("wrong price");
                            check();
                        }
                    }
                    // transportlidzekļa koda pārbāude
                    parts[5] = parts[5].toUpperCase();
                    String[] transport_options = {"TRAIN", "PLANE", "BUS", "BOAT"};
                    boolean transport_check = false;
                    for (int i = 0; i<transport_options.length; i++){
                        if (parts[5].equals(transport_options[i])){
                            transport_check = true;
                        }
                    }
                    if (transport_check == false){
                        System.out.println("wrong vehicle");
                    }
                    if (transport_check == true && price_check == true){ 
                        for (int i = 0; i<parts.length; i++){
                            fileWriter.append(parts[i]);
                            if (i != parts.length-1){
                                fileWriter.append(";");
                            }
                        }
                        fileWriter.append("\n");
                        System.out.println("added");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("wrong field count");

        }
        check();
    }
    public static void del(String ID) {
        boolean check = check_ID("del", ID);
        if (check == false){
            System.out.println("wrong id");
            check();
        }
        List<String> lines = new ArrayList<String>();
        try (BufferedReader reader = new BufferedReader(new FileReader("db.csv"))){
            String line;
            while((line = reader.readLine()) != null) {
                String[] linesp = line.split(";");
                int currentID = Integer.parseInt(linesp[0]);
                if (currentID != Integer.parseInt(ID)){
                    lines.add(line);
                }
            }
        } catch (IOException e){
            e.printStackTrace();
            check();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("db.csv"))){
            for(String line : lines) {
                writer.write(line);
                writer.newLine();
            }
            System.out.println("deleted");
        } catch (IOException e){
            e.printStackTrace();
        }
        check();
    }
    public static void edit(String info) {
        String[] parts = info.split(";");
        if (parts.length>6){
            System.out.println("wrong field count");
            check();
        }
        String ID = parts[0];
        boolean check = check_ID("edit", ID);
        if (check == true){
            try (BufferedReader reader = new BufferedReader(new FileReader("db.csv"))) {
                //  Lielas pirmais burts
                if (parts[1].length() != 0){
                    char letter = Character.toUpperCase(parts[1].charAt(0));
                    parts[1] = letter + parts[1].toLowerCase().substring(1);
                }
                // datuma pārbaude
                if (parts[2].length() != 0){
                    SimpleDateFormat formatdate = new SimpleDateFormat("dd/MM/yyyy");
                    formatdate.setLenient(false);
                    try {
                        formatdate.parse(parts[2]);
                        String[] date_parts = parts[2].split("/");

                        if (date_parts[0].length() != 2 || date_parts[1].length() != 2){
                            System.out.println("wrong date");
                            check();
                        }
                    } catch (ParseException e){
                        System.out.println("wrong date");
                        check();
                    }
                }
                // dienas pārbāude
                if (parts[3].length() != 0){
                    try {
                        int number = Integer.parseInt(parts[3]);
                        if (number <= 0) {
                            System.out.println("wrong day count");
                            check();
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("wrong day count");
                        check();
                    }
                }
                //  cenas pārbāude
                if (parts[4].length() != 0){
                    if (Character.isLetter(parts[4].charAt(parts[4].length() - 1))){
                        System.out.println("wrong price");
                        check();
                    }
                    boolean price_check = false;
                    try {
                        double number_double = Double.parseDouble(parts[4]);
                        if (number_double <=0) {
                            System.out.println("wrong price");
                            check();
                        } else {
                            price_check = true;
                        }
                    } catch (NumberFormatException e){
                        System.out.println("wrong price");
                        check();
                    }
                    if (price_check == false) {
                        try {
                            int number_int = Integer.parseInt(parts[4]);
                            if (number_int <=0) {
                                System.out.println("wrong price");
                                check();
                            } else {
                                price_check = true;
                            }
                        } catch (NumberFormatException e){
                            System.out.println("wrong price");
                            check();
                        }
                    }
                }
                // transportlidzekļa koda pārbāude
                if (parts[5].length() != 0){
                    parts[5] = parts[5].toUpperCase();
                    String[] transport_options = {"TRAIN", "PLANE", "BUS", "BOAT"};
                    boolean transport_check = false;
                    for (int i = 0; i<transport_options.length; i++){
                        if (parts[5].equals(transport_options[i])){
                            transport_check = true;
                        }
                    }
                    if (transport_check == false){
                        System.out.println("wrong vehicle");
                        check();
                    }
                }
                List<String> lines = new ArrayList<String>();
                String line;
                while((line = reader.readLine()) != null) {
                    String[] linesp = line.split(";");
                    int currentID = Integer.parseInt(linesp[0]);
                    if (currentID != Integer.parseInt(ID)){
                        lines.add(line);
                    } else {
                        String newline = "";
                        for (int i=0; i<=5; i++){
                            if (parts[i].length()!=0){
                                newline = newline + parts[i] + ";";
                            } else {
                                newline = newline + linesp[i] + ";";
                            }
                        }
                        lines.add(newline);
                        
                    }
                }
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("db.csv"))){
                    for(String nline : lines) {
                        writer.write(nline);
                        writer.newLine();
                    }
                    System.out.println("changed");
                } catch (IOException e){
                    e.printStackTrace();
                }
                check();
            } catch (IOException e){
                e.printStackTrace();
            }
        } else {
            System.out.println("wrong id");
        }
        check();
    }
    public static void sort() {
        String line;
        List<String[]> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("db.csv"))) {
            while((line = reader.readLine()) != null) {
                String[] linesp = line.split(";");
                lines.add(linesp);
            }
                        Collections.sort(lines, (row1, row2) -> {
                LocalDate date1 = LocalDate.parse(row1[2].trim(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                LocalDate date2 = LocalDate.parse(row2[2].trim(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                return date1.compareTo(date2);
            });

            // Write sorted data back to the original CSV file
            try (BufferedWriter bw = new BufferedWriter(new FileWriter("db.csv"))) {
                for (String[] row : lines) {
                    bw.write(String.join(";", row));
                    bw.newLine();
                }
            }
        } catch(IOException e){
            e.printStackTrace();
        }
        check();
        
    }
    public static void find(String info){
        try (BufferedReader reader = new BufferedReader(new FileReader("db.csv"))) {
            //  cenas pārbāude
            if (info.length() != 0){
                if (Character.isLetter(info.charAt(info.length() - 1))){
                    System.out.println("wrong price");
                    check();
                }
                boolean price_check = false;
                try {
                    double number_double = Double.parseDouble(info);
                    if (number_double <=0) {
                        System.out.println("wrong price");
                        check();
                    } else {
                        price_check = true;
                    }
                } catch (NumberFormatException e){
                    System.out.println("wrong price");
                    check();
                }
                if (price_check == false) {
                    try {
                        int number_int = Integer.parseInt(info);
                        if (number_int <=0) {
                            System.out.println("wrong price");
                            check();
                        } else {
                            price_check = true;
                        }
                    } catch (NumberFormatException e){
                        System.out.println("wrong price");
                        check();
                    }
                }
                List<String> lines = new ArrayList<String>();
                String line;
                double user_price = Double.parseDouble(info);
                while((line = reader.readLine()) != null) {
                    String[] linesp = line.split(";");
                    double price = Double.parseDouble(linesp[4]);
                    if (user_price > price){
                        lines.add(line);                    
                    }
                }
                System.out.println("\n------------------------------------------------------------");
                System.out.printf("%-4s%-21s%-11s  %-4s     %s %-7s", "ID", "City", "Date", "Days", "Price", "Vehicle");
                System.out.print("\n------------------------------------------------------------");

                for(String linen : lines){
                    String[] linesp = linen.split(";");
                    System.out.printf("\n%-4s%-21s%-11s%6s%10s %-7s", linesp[0], linesp[1], linesp[2], linesp[3], linesp[4], linesp[5]);
                }
                System.out.println("\n------------------------------------------------------------");
            }
 
        } catch(IOException e){
            e.printStackTrace();
        } 
        check();
    }
    public static void avg(){
        try (BufferedReader reader = new BufferedReader(new FileReader("db.csv"))) {
            String line;
            double price;
            double prices_sum = 0.0;
            int i = 0;
            while((line = reader.readLine()) != null) {
                String[] linesp = line.split(";");
                price = Double.parseDouble(linesp[4]);
                prices_sum = prices_sum + price;
                i++;       
            }
            System.out.printf("average=%.2f%n", prices_sum/i);
        } catch(IOException e){
            e.printStackTrace();
        }
        check();
    }

    public static void check(){
        sc = new Scanner(System.in);
        String user_command = sc.next();
        String user_info = new String();
        switch(user_command){
            case "print":
                print();
                break;
            case "add":
                user_info = sc.next();
                add(user_info);
                break;
            case "del":
                user_info = sc.next();
                del(user_info);
                break;
            case "edit":
                user_info = sc.next();
                edit(user_info);
                break;
            case "sort":
                sort();
                break;
            case "find":
                user_info = sc.next();
                find(user_info);
                break;
            case "avg":
                avg();
                break;
            case "exit":
                System.exit(0);
                default:
                System.out.println("wrong command");
                check();
        }
        sc.close();
    }

    public static void main(String[] args) {
        System.out.println("Hello!");
        check();
    }
}
// edit 104;Dubai;;9;30.01;plane
// add 105;Riga;01/01/2011;3;0;BoAT