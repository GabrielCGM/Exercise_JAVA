import java.io.*;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        String path = "C:\\Users\\Gabriel\\IdeaProjects\\ProjectStream\\src\\Information";
        List<Employee> list = readFileOfTypeCsvAndReturnList(path);

        System.out.println("Average: ");
        Double average = sc.nextDouble();
        System.out.println("Character: ");
        Character c = sc.next().toUpperCase().charAt(0);

        listEmailPeopleSalaryMoreAverage(list, average);
        sumOfSalaryOfPeopleWithCharacter(list, c);



        writeNewFile(list);



    }

    /**
     * This  method read a file with text at the format CSV and return a List type Employee
     * @param path Pass the path of file for read
     * @return Return a List of tupe Employee
     */
    public static List<Employee> readFileOfTypeCsvAndReturnList (String path){
        List<Employee> list = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(new FileReader(path))){
            String line = br.readLine();

            while(line != null){
                String[] fields = line.split(",");
                list.add(new Employee(fields[0], fields[1], Double.parseDouble(fields[2])));
                line = br.readLine();
            }


        }catch (IOException e){
            System.out.println(e);
        }finally {
            if (list.isEmpty()){
                return null;
            }
            return list;
        }
    }

    /**
     *This method prints the emails of peoples that have the salary more than Average.
     * @param list Pass a list of type Employee
     * @param averagee The parameter averagee will serve as reference for the method verify
     *                the list of peoples that have salary more than the parameter averagee
     */
    public static void listEmailPeopleSalaryMoreAverage(List<Employee> list, Double averagee){

        List<String> listString = list.stream()
                .filter(x -> x.getSalary() > averagee)
                .map(x->x.getEmail())
                .collect(Collectors.toList());

        System.out.println("Email of people where salary is more than " + averagee);
        listString.forEach(System.out::println);
    }

    /**
     * This method prints the sum of the salaries of peoples that start the name with parameter c
     * @param list a List of type Employee
     * @param c A character that will serve as parameter for search the peoples that start the name
     *          with the parameter c
     */
    public static void sumOfSalaryOfPeopleWithCharacter(List<Employee> list, Character c){


        Predicate<Employee> pred = (p->p.getName().charAt(0) == c);

        Double sum = list.stream()
                .filter(pred)
                .map(p->p.getSalary())
                .reduce(0.0,(x,y)->x+y);
        System.out.println("Sum of salary of people whose name starts with '" + c + "' :" + sum);
    }

    /**
     * This method is responsible for receive a list of type Employee and return a SET type Employee with
     * the sorting specific(name | email | salary )
     * @param list List of type Employee
     * @return Set of type Employee with sorting specific
     */
    public static Set<Employee> choiceTheModeOfOrderStart(List<Employee> list) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Modes of order\n" +
                "1-NAME(crescent)\n" +
                "2-NAME(decrescent)\n" +
                "3-Email(crescent)\n" +
                "4-Email(decrescent)\n" +
                "5-Salary(crescent)\n" +
                "6-Salary(decrescent)");

        int choice = sc.nextInt();

        switch (choice) {
            case 1:
                // Set<Employee> setzin = new TreeSet<>((x, y)-> x.getName().compareTo(y.getName()));
                Set<Employee> setCase1 = new TreeSet<>(Comparator.comparing(Employee::getName));
                list.forEach((x) -> setCase1.add(x));
                return setCase1;


            case 2:
                Set<Employee> setCase2 = new TreeSet<>(Comparator.comparing(Employee::getName).reversed());
                list.forEach((x) -> setCase2.add(x));
                return setCase2;


            case 3:
                Set<Employee> setCase3 = new TreeSet<>(Comparator.comparing(Employee::getEmail));
                list.forEach((x) -> setCase3.add(x));
                return setCase3;


            case 4:
                Set<Employee> setCase4 = new TreeSet<>(Comparator.comparing(Employee::getEmail).reversed());
                list.forEach((x) -> setCase4.add(x));
                return setCase4;


            case 5:
                Set<Employee> setCase5 = new TreeSet<>(Comparator.comparing(Employee::getSalary));
                list.forEach((x) -> setCase5.add(x));
                return setCase5;

            case 6:
                Set<Employee> setCase6 = new TreeSet<>(Comparator.comparing(Employee::getSalary).reversed());
                list.forEach((x) -> setCase6.add(x));
                return setCase6;


            default:
                return null;


        }



    }

    /**
     * This method is responsible for writing a new file. Write according the elements of the List.
     * @param list List Type Employee
        return void
     */
    public static void writeNewFile(List<Employee> list){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\Users\\Gabriel\\IdeaProjects\\ProjectStream\\src\\newInformationOrder"))){
            bw.write("NAME | EMAIL | SALARY");
            bw.newLine();
            choiceTheModeOfOrderStart(list).forEach(
                    (x) -> {
                        try {
                            bw.write(x.getName() +
                                    "," + x.getEmail() +
                                    "," + x.getSalary());
                                    bw.newLine();

                        } catch (IOException | NullPointerException e) {
                            throw new RuntimeException(e);
                        }
                    }
            );

        }catch (IOException e){
            System.out.println(e);
        }



    }
}