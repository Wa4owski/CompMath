import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;


public class Program {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome! Это программа для решения систем линейных уравнений.");
        int readWay = 0;
        while(true){
            System.out.println("Выберите, как вы хотите задать СЛАУ:");
            System.out.println("С клавиатуры - нажмите 1");
            System.out.println("Из файла - нажмите 2");
            try{
                readWay = Integer.parseInt(scanner.next());
                if (readWay != 1 && readWay != 2){
                    throw new IllegalArgumentException();
                }
            }
            catch (Exception e){
                System.out.println("Непредвиденный ввод");
                continue;
            }
            break;
        }

        if(readWay == 1){
            while(true){
                try {
                    int N = Integer.parseInt(scanner.next());
                    List<Double> arr[] = new ArrayList[N];
                    scanner.nextLine();//fucking Java
                    for(int i = 0; i < N; i++){
                        //System.out.print("i: " + i + scanner.nextLine());
                        arr[i] = (Arrays.stream(scanner.nextLine().split(" "))
                                .mapToDouble(x -> Double.parseDouble(x))).boxed().collect(Collectors.toList());
                        if(arr[i].size() != N+1){
                            throw new IllegalArgumentException();
                        }
                    }
                    System.out.println(N);
                    SimpleIteration simpleIteration = new SimpleIteration(N, arr);
                    simpleIteration.diagonal();
                    simpleIteration.form();
                    System.out.println("Введите требуемую погрешность:");
                    simpleIteration.setEps(Double.parseDouble(scanner.next()));
                    simpleIteration.iteration();
                } catch (Exception e){
                    e.printStackTrace();
                    System.out.println("Непредвиденный ввод");
                    continue;
                }
                break;
            }
        }
        scanner.close();
        String src = "C:\\Users\\user\\Desktop\\Учеба\\ВычМат\\ЛР1\\Lab1\\input.txt";
        if(readWay == 2){

                try {
                    scanner = new Scanner(Paths.get(src));
                    int N = Integer.parseInt(scanner.next());
                    List<Double> arr[] = new ArrayList[N];
                    scanner.nextLine();
                    for(int i = 0; i < N; i++){
                        //System.out.print("i: " + i + scanner.nextLine());
                        arr[i] = (Arrays.stream(scanner.nextLine().split(" "))
                                .mapToDouble(x -> Double.parseDouble(x))).boxed().collect(Collectors.toList());
                        if(arr[i].size() != N+1){
                            throw new IllegalArgumentException();
                        }
                    }
                    SimpleIteration simpleIteration = new SimpleIteration(N, arr);
                    simpleIteration.diagonal();
                    simpleIteration.form();
                   // System.out.println("Введите требуемую погрешность:");
                    //Scanner sc = new Scanner(System.in);
                    //System.out.println(sc.nextLine());
                    double eps = Double.parseDouble(scanner.next());
                    //System.out.println(eps);
                    simpleIteration.setEps(eps);
                    simpleIteration.iteration();
                }
//                } catch (FileNotFoundException ex){
//                    System.out.println("Что с файлом?");
//                    break;
//                }
                  catch (Exception e) {
                      System.out.println(e.getClass().toString());
                      System.out.println("Непредвиденный ввод");
                  }

        }

    }
}
