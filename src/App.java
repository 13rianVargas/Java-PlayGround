import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n---------- Hello World! -----------\n\n\n");



        //continueTest();
        
        //var days = Test.calculate(Test.Day.MONDAY);
        //System.out.println("Remaining work days: " + days);
        //days = Test.calculate(Test.Day.SUNDAY);
        //System.out.println("Remaining work days: " + days);

        //testSwitchMonths();

        //System.out.println(convertToLabel(0));

        System.out.println("\n----------- Bye World! ------------\n"); 
    }

    public static void continueTest() {
        var searchMe = "Look for a substring in me";
        var substring = "sub";
        var foundIt = false; //Se puede usar var para especificar cualquier tipo de dato

        var max = searchMe.length() -
                substring.length();

    test: //label sirven para crear un bloque de codigo y usar break o continue
        for (var i = 0; i <= max; i++) {
            var n = substring.length();
            var j = i;
            var k = 0;
            while (n-- != 0) {
                if (searchMe.charAt(j++) != substring.charAt(k++)) {
                    continue test; //vuelve al label test y no al for
                }
            }
            foundIt = true;
                break test;
        }
        System.out.println(foundIt ? "Found it" : "Didn't find it");
    
    }// continueTest()

    public class Test {
        static enum Day {
            MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
        }
    
        public static int calculate(Day d) {
            return switch (d) {
                    case SATURDAY, SUNDAY -> 0;
                    default -> {
                        var remainingWorkDays = 5 - d.ordinal(); //ordinal devuelve el valor de la posicion del enum
                        yield remainingWorkDays;//para salir del case, no del switch
                    }
                };
        }
    }// Test class

    public static void testSwitchMonths() {
        var month = 8;
        List<String> months = new ArrayList<>();

        switch (month) {
            case 1:  months.add("January");
            case 2:  months.add("February");
            case 3:  months.add("March");
            case 4:  months.add("April");
            case 5:  months.add("May");
            case 6:  months.add("June");
            case 7:  months.add("July");
            case 8:  months.add("August");//Aquí inicia a agregar los meses y solo para hasta el default
            case 9:  months.add("September");
            case 10: months.add("October");
            case 11: months.add("November");
            case 12: months.add("December");
            default: break;
        }
        for (String m : months) {
            System.out.println(m.toString());
        }

        month = 2;
        var year = 2021;
        var numDays = 0;

        switch (month) {
            case 1: case 3: case 5:   // January March May
            case 7: case 8: case 10:  // July August October
            case 12:
                numDays = 31;
                break;
            case 4: case 6:   // April June
            case 9: case 11:  // September November
                numDays = 30;
                break;
            case 2: // February
                if (((year % 4 == 0) && 
                    !(year % 100 == 0))
                    || (year % 400 == 0))
                    numDays = 29;
                else
                    numDays = 28;
                break;
            default:
                System.out.println("Invalid month.");
                break;
        }
        System.out.println("\nNumber of days in month " + month + " of year " + year + " is: " + numDays);

        Test.Day day = Test.Day.SATURDAY; // any day
        int len =
            switch (day) {
                case MONDAY, FRIDAY, SUNDAY -> 6;//tipos de switchs
                case TUESDAY                -> 7;
                case THURSDAY, SATURDAY     -> 8;
                case WEDNESDAY              -> 9;
            };
        System.out.println("\nlen of word = " + len);

    }//testSwitchMonths

    public static String convertToLabel(int quarter) {
        String quarterLabel =
            switch (quarter) {
                case 0  -> {
                    yield "Q1 - Winter"; //devuelve solo al case y no al switch o el metodo.
                }
                default -> "Unknown quarter";
            };
        return quarterLabel;//devuelve al metodo.
    }

}//class App
