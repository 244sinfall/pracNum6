import java.util.Scanner;

public class Main {
    public static String selectedType;
    public static String receiveStringValue(String name) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.printf("Введите параметр: '%s' (Строка)\n", name);
            String newVal = scanner.nextLine();
            if (newVal.isEmpty()) {
                System.out.println("Пустая строка.");
                continue;
            }
            return newVal;
        }
    }
    @SuppressWarnings("unchecked")
    public static <T> T getValue() {
        return switch (selectedType) {
            case "String" -> (T) receiveStringValue("значение для сохранения в списке");
            case "Integer" -> (T) (Integer) receiveIntValue("значение для сохранения в списке");
            case "Double" -> (T) (Double) receiveDoubleValue("значение для сохранения в списке");
            default -> null;
        };
    }
    public static int receiveIntValue(String name) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.printf("Введите параметр: '%s' (Целое число)\n", name);
            String newVal = scanner.nextLine();
            if(newVal.isEmpty()) continue;
            try {
                return Integer.parseInt(newVal);
            } catch (NumberFormatException e) {
                System.out.println("Неверное значение.");
            }
        }
    }
    public static double receiveDoubleValue(String name) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.printf("Введите параметр: '%s' (Число с плавающей точкой)\n", name);
            String newVal = scanner.nextLine();
            if(newVal.isEmpty()) continue;
            try {
                return Double.parseDouble(newVal);
            } catch (NumberFormatException e) {
                System.out.println("Неверное значение.");
            }
        }
    }
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Кольцевой двусязный список");
        typeSelectionLoop: while(true) {
            System.out.println("Выберите тип данных для взмиодействия");
            System.out.println("1. Строки");
            System.out.println("2. Целые числа");
            System.out.println("3. Числа с плавающей точкой");
            String choice = input.nextLine();
            int selectedMenuItem;
            try {
                selectedMenuItem = Integer.parseInt(choice);
            } catch (NumberFormatException e) {
                System.out.println("Вы ввели некорректный пункт меню!");
                continue;
            }
            switch (selectedMenuItem) {
                case 1 -> {
                    selectedType = "String";
                    break typeSelectionLoop;
                }
                case 2 -> {
                    selectedType = "Integer";
                    break typeSelectionLoop;
                }
                case 3 -> {
                    selectedType = "Double";
                    break typeSelectionLoop;
                }
            }
        }
        RingedLinkedList<Object> list = new RingedLinkedList<>();
        while(true) {
            System.out.println("Выберите пункт меню:");
            System.out.println("1. Проверить на пустоту. (Посмотрреть список)");
            System.out.println("2. Установить указатель в начало списка.");
            System.out.println("3. Установить указатель в конец списка.");
            System.out.println("4. Добавить элемент за указателем.");
            System.out.println("5. Добавить элемент до указателя.");
            System.out.println("6. Удалить элемент за указателем.");
            System.out.println("7. Удалить элемент до указателя.");
            System.out.println("8. Переместить указатель вправо.");
            System.out.println("9. Переместить указатель влево.");
            System.out.println("10. Обменять значения элементов до указателя и за указателем");
            System.out.println("0. Выйти");
            String choice = input.nextLine();
            int selectedMenuItem;
            try {
                selectedMenuItem = Integer.parseInt(choice);
            } catch (NumberFormatException e) {
                System.out.println("Вы ввели некорректный пункт меню!");
                continue;
            }
            switch (selectedMenuItem) {
                case 0:
                    return;
                case 1:
                    String response = list.isEmpty() ? "Список пуст" : list.toString();
                    System.out.println(response);
                    break;
                case 2:
                    String responseMoveStart = list.moveStart() ?
                            "Указатель установлен на первый элемент списка"
                            : "Указатель не перемещен. Вероятно список пуст или в нем один элемент";
                    System.out.println(responseMoveStart);
                    break;
                case 3:
                    String responseMoveTail = list.moveTail() ?
                            "Указатель установлен на последний элемент списка"
                            : "Указатель не перемещен. Вероятно список пуст или в нем один элемент";
                    System.out.println(responseMoveTail);
                    break;
                case 4:
                    list.addAfter(getValue());
                    System.out.println("Значение добавлено в список." + list);
                    break;
                case 5:
                    list.addBefore(getValue());
                    System.out.println("Значение добавлено в список." + list);
                    break;
                case 6:
                    String responseRemoveAfter = list.removeAfter() ? "Удаление прошло успешно" : "Удаление не прошло";
                    System.out.println(responseRemoveAfter);
                    break;
                case 7:
                    String responseRemoveBefore = list.removeBefore() ? "Удаление прошло успешно" : "Удаление не прошло";
                    System.out.println(responseRemoveBefore);
                    break;
                case 8:
                    String shiftNextResponse = list.shiftNext() ? "Указатель перемещен" :
                    "Указатель не перемещен. Вероятно список пуст или в нем всего один элемент";
                    System.out.printf(shiftNextResponse);
                    break;
                case 9:
                    String shiftPrevResponse = list.shiftPrev() ? "Указатель перемещен." :
                            "Указатель не перемещен. Вероятно список пуст или в нем всего один элемент";
                    System.out.printf(shiftPrevResponse);
                    break;
                case 10:
                    String replaceResponse = list.replacePrevAndNext() ? "Элементы до и после указателя успешно обменяны" : "Элементы не обменяны";
                    System.out.println(replaceResponse);
                default:
                    System.out.println("Вы ввели некорректный пункт меню!");
                    break;
            }
        }
    }
}