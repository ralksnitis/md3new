// 211RDB381, Ralfs Alksnītis, 1.grupa

import java.util.Scanner;
import java.util.*;
import java.text.*;
import java.io.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.Serializable;

class Journey implements Serializable {
    public int id, days;
    public String city, date, vehicle;
    double price;
    static final DecimalFormat priceformat = new DecimalFormat("0.00");

    public Journey(int id, String city, String date, int days, double price, String vehicle) {
        this.id = id;
        this.city = city;
        this.date = date;
        this.days = days;
        this.price = price;
        this.vehicle = vehicle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getString() {
        return id + ";" + city + ";" + date + ";" + days + ";" + price + ";" + vehicle;
    }
}

public class Main {
    static Scanner sc = new Scanner(System.in);
    static String path = "db.csv";
    static String tempFile = "dbtemp.csv";
    static final DecimalFormat priceformat = new DecimalFormat("0.00");

    public static void main(String[] args) throws IOException {
        String inp = "";
        while (!inp.equals("exit")) {
            System.out.print("\ncommand: ");
            inp = (String) sc.nextLine();
            String[] inpm = inp.split(" ");
            switch (inpm[0]) {
            case "print":
                print();
                break;

            case "add":
                if (inpm.length == 1) {
                    System.out.println("wrong field count");
                    break;
                }
                add(inpm);
                break;

            case "del":
                if (inpm.length == 1) {
                    System.out.println("wrong id");
                    break;
                }
                del(inpm[1]);
                break;

            case "edit":
                if (inpm.length == 1) {
                    System.out.println("wrong field count");
                    break;
                }
                edit(inpm);
                break;
            case "sort":
                sort();
                break;

            case "find":
                if (inpm.length == 1) {
                    System.out.print("wrong field count");
                    break;
                }
                find(inpm[1]);
                break;

            case "avg":
                avg();
                break;

            case "exit":
                break;

            default:
                System.out.println("wrong command");
                break;
            }
        }
        sc.close();
    }

    public static boolean isnumdouble(String str) {
        if (str == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static boolean isnumint(String str) {
        if (str == null) {
            return false;
        }
        try {
            int i = Integer.parseInt(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static void print() throws IOException {
        String line = "";
        System.out.println("------------------------------------------------------------");
        System.out.printf("%-4s", "ID");
        System.out.printf("%-21s", "City");
        System.out.printf("%-11s", "Date");
        System.out.printf("%6s", "Days");
        System.out.printf("%10s", "Price");
        System.out.printf("%7s", " Vehicle");
        System.out.println("\n------------------------------------------------------------");

        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                System.out.printf("%-4s", values[0]);
                System.out.printf("%-21s", values[1]);
                System.out.printf("%-11s", values[2]);
                System.out.printf("%6s", values[3]);
                System.out.printf("%10s", priceformat.format(Double.parseDouble(values[4])));
                System.out.printf("%7s", values[5]);
                System.out.println();
            }
            System.out.println("------------------------------------------------------------");
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void add(String[] inputmas) throws IOException, FileNotFoundException {
        String city;
        String[] addinfo;
        if (inputmas.length == 2) {
            addinfo = inputmas[1].split(";");
        } else {
            city = inputmas[1];
            for (int j = 2; j < inputmas.length; j++) {
                city = city + " " + inputmas[j];
            }
            addinfo = city.split(";");
        }
        if (addinfo.length != 6) {
            System.out.println("wrong field count");
            return;
        }
        if (!isnumint(addinfo[0]) || addinfo[0].length() != 3) {
            System.out.println("wrong id");
            return;
        }
        String line = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                if (values[0].equals(addinfo[0])) {
                    System.out.println("wrong id");
                    return;
                }
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] splitcity = addinfo[1].split(" ");
        addinfo[1] = "";
        for (int j = 0; j < splitcity.length; j++) {
            splitcity[j] = splitcity[j].toLowerCase();
            splitcity[j] = splitcity[j].substring(0, 1).toUpperCase() + splitcity[j].substring(1);
            addinfo[1] = addinfo[1] + splitcity[j];
            if (j + 1 < splitcity.length) {
                addinfo[1] = addinfo[1] + " ";
            }
        }
        String date[] = addinfo[2].split("/");
        int d = Integer.parseInt(date[0]);
        int m = Integer.parseInt(date[1]);
        String y = date[2];
        if ((d <= 0 || d >= 32) || (m <= 0 || m >= 13) || y.length() != 4) {
            System.out.println("wrong date");
            return;
        }
        if (!isnumint(addinfo[3])) {
            System.out.println("wrong day count");
            return;
        }
        if (!isnumdouble(addinfo[4])) {
            System.out.println("wrong price");
            return;
        }
        String t = addinfo[5];
        t = t.toUpperCase();
        if (!(t.equals("TRAIN") || t.equals("BOAT") || t.equals("PLANE") || t.equals("BUS"))) {
            System.out.println("wrong vehicle");
            return;
        }

        String inpst = "";
        line = "";
        inpst = addinfo[0] + ";" + addinfo[1] + ";" + addinfo[2] + ";" + addinfo[3] + ";" +
            priceformat.format(Double.parseDouble(addinfo[4])) + ";" + addinfo[5];
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            BufferedWriter wr = new BufferedWriter(new FileWriter(tempFile));

            while ((line = br.readLine()) != null) {
                wr.write(line + System.getProperty("line.separator"));
            }
            wr.write(inpst);

            System.out.println("added");
            wr.close();
            br.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        File sourceFile = new File("db.csv");
        File temptFile = new File("dbtemp.csv");
        sourceFile.delete();
        temptFile.renameTo(sourceFile);
        temptFile.delete();

    }

    public static void del(String idstr) throws FileNotFoundException, IOException {
        if (idstr == null) {
            System.out.println("wrong id");
            return;
        }
        try {
            int id = Integer.parseInt(idstr);
        } catch (NumberFormatException e) {
            System.out.println("wrong id");
            return;
        }
        int rowcount = 0;
        int idrow = 0;
        boolean idtest = true;
        String line = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                int fid = Integer.parseInt(values[0]);
                int id = Integer.parseInt(idstr);
                if (fid == id) {
                    idtest = false;
                    idrow = rowcount;
                }
                rowcount++;
            }
            br.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (idtest) {
            System.out.println("wrong id");
            return;
        }

        Journey mas[] = new Journey[rowcount - 1];
        int j = 0;
        int i = 0;
        line = "";

        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            while ((line = br.readLine()) != null) {
                if (idrow != j) {
                    String[] row = line.split(";");
                    Journey journey = new Journey(Integer.parseInt(row[0]), row[1], row[2], Integer.parseInt(row[3]), Double.parseDouble(row[4]), row[5]);
                    mas[i++] = journey;
                }
                j++;
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedWriter wr = new BufferedWriter(new FileWriter(tempFile));
            for (j = 0; j < mas.length; j++) {
                wr.write(mas[j].getString() + System.getProperty("line.separator"));
            }

            System.out.println("deleted");
            wr.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        File sourceFile = new File("db.csv");
        File temptFile = new File("dbtemp.csv");
        sourceFile.delete();
        temptFile.renameTo(sourceFile);
        temptFile.delete();

    }

    public static void edit(String[] inputmas) throws FileNotFoundException, IOException {

        String city;
        String[] addinfo;
        if (inputmas.length == 2) {
            addinfo = inputmas[1].split(";");
        } else {
            city = inputmas[1];
            for (int j = 2; j < inputmas.length; j++) {
                city = city + " " + inputmas[j];
            }
            addinfo = city.split(";");
        }
        if (addinfo.length != 6) {
            System.out.println("wrong field count");
            return;
        }
        if (!isnumint(addinfo[0]) || addinfo[0].length() != 3) {
            System.out.println("wrong id");
            return;
        }
        boolean idis = true;
        String line = "";
        int c = 0;
        int cid = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                if (values[0].equals(addinfo[0])) {
                    idis = false;
                    cid = c;
                }
                c++;
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (idis) {
            System.out.println("wrong id");
            return;
        }

        Journey array[] = new Journey[c];
        int j = 0;
        line = "";

        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            while ((line = br.readLine()) != null) {
                String[] row = line.split(";");
                Journey journey = new Journey(Integer.parseInt(row[0]), row[1], row[2], Integer.parseInt(row[3]),
                    Double.parseDouble(row[4]), row[5]);
                array[j++] = journey;
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!addinfo[1].equals("")) {
            String[] splitcity = addinfo[1].split(" ");
            addinfo[1] = "";
            for (j = 0; j < splitcity.length; j++) {
                splitcity[j] = splitcity[j].toLowerCase();
                splitcity[j] = splitcity[j].substring(0, 1).toUpperCase() + splitcity[j].substring(1);
                addinfo[1] = addinfo[1] + splitcity[j];
                if (j + 1 < splitcity.length) {
                    addinfo[1] = addinfo[1] + " ";
                }
            }
            array[cid].setCity(addinfo[1]);
        }
        if (!addinfo[2].equals("")) {
            String date[] = addinfo[2].split("/");
            int d = Integer.parseInt(date[0]);
            int m = Integer.parseInt(date[1]);
            String y = date[2];
            if ((d <= 0 || d >= 32) || (m <= 0 || m >= 13) || y.length() != 4) {
                System.out.println("wrong date");
                return;
            }
            array[cid].setDate((addinfo[2]));
        }
        if (!addinfo[3].equals("")) {
            if (!isnumint(addinfo[3])) {
                System.out.println("wrong day count");
                return;
            }
            array[cid].setDays(Integer.parseInt(addinfo[3]));
        }
        if (!addinfo[4].equals("")) {
            if (!isnumdouble(addinfo[4])) {
                System.out.println("wrong price");
                return;
            }
            array[cid].setPrice(Double.parseDouble(addinfo[4]));
        }
        if (!addinfo[5].equals("")) {
            String t = addinfo[5];
            t = t.toUpperCase();
            if (!(t.equals("TRAIN") || t.equals("BOAT") || t.equals("PLANE") || t.equals("BUS"))) {
                System.out.println("wrong vehicle");
                return;
            }
            array[cid].setVehicle(t);
        }
        try {

            BufferedWriter wr = new BufferedWriter(new FileWriter(tempFile));
            for (j = 0; j < array.length; j++) {
                wr.write(array[j].getString() + System.getProperty("line.separator"));

            }
            wr.close();
            System.out.println("changed");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        File sourceFile = new File("db.csv");
        File temptFile = new File("dbtemp.csv");
        sourceFile.delete();
        temptFile.renameTo(sourceFile);
        temptFile.delete();

    }
    public static void sort() throws FileNotFoundException, IOException {
        PrintWriter outwr = new PrintWriter(new FileWriter(tempFile));
        int c = 0;
        String line;
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            while ((line = br.readLine()) != null) {
                c++;
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Journey journey[] = new Journey[c];
        int i = 0;
        line = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                Journey infojourney = new Journey(Integer.parseInt(values[0]), values[1], values[2],
                    Integer.parseInt(values[3]), Double.parseDouble(values[4]), values[5]);
                journey[i++] = infojourney;
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList < String > dateList = new ArrayList < > ();
        for (i = 0; i < journey.length; i++) {
            dateList.add(journey[i].getDate());
        }
        Collections.sort(dateList, new Comparator < String > () {
            DateFormat f = new SimpleDateFormat("dd/MM/yyyy");

            public int compare(String d1, String d2) {
                try {
                    return f.parse(d1).compareTo(f.parse(d2));
                } catch (ParseException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        });

        String str;

        try {

            BufferedWriter wr = new BufferedWriter(new FileWriter(tempFile));
            for (String dateStr: dateList) {
                for (i = 0; i <= journey.length; i++) {
                    if (dateStr.equals(journey[i].getDate())) {
                        str = journey[i].getId() + ";" + journey[i].getCity() + ";" + journey[i].getDate() + ";" + journey[i].getDays() + ";" + priceformat.format(journey[i].getPrice()) + ";" + journey[i].getVehicle();
                        wr.write(str);
                        wr.write("\n");
                        break;
                    }
                }
            }
            System.out.println("sorted");
            wr.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        File sourceFile = new File("db.csv");
        File temptFile = new File("dbtemp.csv");
        temptFile.renameTo(sourceFile);
        temptFile.delete();

    }
    public static void find(String findpricestr) throws IOException, FileNotFoundException {
        if (findpricestr == null) {
            System.out.println("wrong price");
            return;
        }
        try {
            double p = Double.parseDouble(findpricestr);
        } catch (NumberFormatException e) {
            System.out.println("wrong price");
            return;
        }

        String line = " ";
        System.out.println("------------------------------------------------------------");
        System.out.printf("%-4s", "ID");
        System.out.printf("%-21s", "City");
        System.out.printf("%-11s", "Date");
        System.out.printf("%6s", "Days");
        System.out.printf("%10s", "Price");
        System.out.printf("%7s", " Vehicle");
        System.out.println("\n------------------------------------------------------------");

        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                String travelprice = values[4];
                double prices = Double.parseDouble(travelprice);
                double findprice = Double.parseDouble(findpricestr);
                if (findprice >= prices) {
                    System.out.printf("%-4s", values[0]);
                    System.out.printf("%-21s", values[1]);
                    System.out.printf("%-11s", values[2]);
                    System.out.printf("%6s", values[3]);
                    System.out.printf("%10s", priceformat.format(Double.parseDouble(values[4])));
                    System.out.printf("%7s", values[5]);
                    System.out.println();
                }
            }
            System.out.println("------------------------------------------------------------");
            br.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void avg() throws IOException, FileNotFoundException {
        String line = "";
        double sum = 0;
        int count = 0;
        double avg;

        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                double price = Double.parseDouble(values[4]);
                sum = sum + price;
                count++;
            }
            avg = sum / count;
            System.out.printf("%.2f", avg);
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}