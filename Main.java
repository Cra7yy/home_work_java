import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class InputDataException extends Exception {
    public InputDataException(String message) {
        super(message);
    }
}

class Person {
    private String surname;
    private String name;
    private String patronymic;
    private String birthDate;
    private long phoneNumber;
    private char gender;

    public Person(String surname, String name, String patronymic, String birthDate, long phoneNumber, char gender) {
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
    }

    public String getSurname() {
        return surname;
    }

    public String toString() {
        return surname + " " + name + " " + patronymic + " " + birthDate + " " + phoneNumber + " " + gender;
    }
}

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Введите данные (Фамилия Имя Отчество ДатаРождения НомерТелефона Пол):");
            String input = scanner.nextLine();

            try {
                processInput(input);
            } catch (InputDataException e) {
                System.out.println("Ошибка ввода данных: " + e.getMessage());
            }

        } catch (IOException e) {
            System.out.println("Ошибка при работе с файлом: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void processInput(String input) throws InputDataException, IOException {
        String[] data = input.split("\\s+");

        if (data.length != 6) {
            throw new InputDataException("Неверное количество данных. Ожидается 6 значений.");
        }

        String surname = data[0];
        String name = data[1];
        String patronymic = data[2];
        String birthDate = data[3];
        long phoneNumber;

        try {
            phoneNumber = Long.parseLong(data[4]);
        } catch (NumberFormatException e) {
            throw new InputDataException("Неверный формат номера телефона.");
        }

        char gender = data[5].charAt(0);
        if (gender != 'f' && gender != 'm') {
            throw new InputDataException("Неверный формат пола. Допустимые значения: f, m.");
        }

        Person person = new Person(surname, name, patronymic, birthDate, phoneNumber, gender);
        writeToOutputFile(person);
    }

    private static void writeToOutputFile(Person person) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(person.getSurname() + ".txt", true))) {
            writer.write(person.toString());
            writer.newLine();
        }
    }
}
