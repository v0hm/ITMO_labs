import java.util.*;
import java.util.Map.Entry;
import java.io.*;
import com.google.gson.*;
import com.google.gson.stream.*;

import java.time.LocalDate;
import java.io.FileOutputStream;

/**
 * Программа для работы с коллекциями
 * @author Владимир Дмитриев и Егор Еловский
 * @version 1.0
 */

public class main {
  /** Свойство - ввод */
  static Scanner input = new Scanner(System.in);
  /** Задание новой коллекции */
  static HashMap<Long, LabWork> collection = new HashMap<Long, LabWork>();
  /** Задание изначальной команды */
  static String command = "";
  /** Задание текущей даты */
  static java.time.LocalDate Date_of_initialization = LocalDate.now();
  /** Ключ элемента коллекции */
  static Long key;
  /** id элемента коллекции */
  static Long id;
  /** difficulty элемента коллекции */
  static String difficulty = null;
  /** Задание изначального элемента */
  static String element = "";
  /** Создание gson */
  static Gson gson = new Gson();
  /** Имя файла */
  static String file_name;
  /** Задание изначального файла */
  static String file = "";
  /** Задание изначальной строки */
  static String str = null;
  /** Задание изначального автора */
  static String author_str = null;
  /** Задание изначального файла со скриптом */
  static String file_name_for_script = null;
  /** Задание дефолтного ввода */
  static InputStream defInput = System.in;
  /** Состояние чтения файла */
  static boolean isFile = false;

  public static void main(String[] args) throws IOException
  {
    /** Функция, добавляющая элементы из файла gson в коллекцию */

    try {
      file_name = args[0];
      JsonReader file_reader = new JsonReader(new BufferedReader(new FileReader(file_name)));
      JsonParser jsonParser = new JsonParser();
      JsonArray labs = jsonParser.parse(file_reader).getAsJsonArray();
      for (JsonElement aLaba : labs) {
        LabWork laba = gson.fromJson(aLaba, LabWork.class);
        collection.put(laba.getId(),laba);
      }
    }
    catch (java.io.FileNotFoundException e) {
      System.out.println("Неверно введён файл");
      System.exit(0);
    }
    catch (java.lang.ArrayIndexOutOfBoundsException e) {
      System.out.println("Не введён файл");
      System.exit(0);
    }
    /** Функция, считывающая команды пользователя */
    while (input.hasNext()) {
      if(isFile && !input.hasNext()){
        isFile = false;
        System.setIn(defInput);
        input = new Scanner(System.in);
      }
      command = input.next();
      command(command);
    }
  }
  /** Метод обработки команд пользователя
   * @param command Введенная команда
   * */
  public static void command (String command) throws IOException {
    String start_of_saving = "[ ";
    String end_of_saving = "]";
    String comma = ", ";
    switch(command) {
      /** Команда, выводящая справку по доступным командам */
      case "help":{
        System.out.println(" help : справка по доступным командам \n info : информация о коллекции \n show : вывод всех элементов коллекции \n insert key {element} : добавить новый элемент с заданным ключом \n update id {element} : обновить значение элемента коллекции по id \n remove_key key : удалить элемент из коллекции по ключу \n clear : очистить коллекцию \n save : сохранить коллекцию в файл \n execute_script file_name : считать и исполнить скрипт из указанного файла \n exit : завершить программу (без сохранения в файл) \n remove_lower {element} : удаление из коллекции всех элементов, меньших, чем заданный \n replace_if_greater key {element} : замена значения по ключу, если новое значение больше старого \n remove_lower_key key : удаление из коллекции всех элементов, ключ которых меньше, чем заданный \n remove_all_by_difficulty difficulty : удаление из коллекции всех элементов, значение поля difficulty которого эквивалентно заданному \n min_by_coordinates : вывод любого объектв из коллекции, значение поля coordinates которого является минимальным \n print_field_ascending_author author : вывод значения поля author в порядке возрастания");
        break;
      }
      /** Выводит в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.) */
      case "info":{
        System.out.println("Тип коллекции: HashMap" + "\n" + "Дата инициализации:" + Date_of_initialization + "\n" + "Колличество элементов коллекции: " + collection.size());
        break;
      }
      /** Выводит в стандартный поток вывода все элементы коллекции в строковом представлении */
      case "show":{
        if(collection.isEmpty())
          System.out.println("Коллекция пуста");
        else
          for(Entry<Long, LabWork> entry : collection.entrySet())
            System.out.println(entry.getValue());
        break;
      }
      /** insert key {element} Добавляет новый элемент с заданным ключом */
      case "insert":{
        try {
          if (collection.containsKey(key = input.nextLong())) {
            System.out.println("Элемент с таким key уже существует");
            input.nextLine();
          } else {
            try {
              element = input.nextLine();
              try {
                collection.put(key, gson.fromJson(element, LabWork.class));
                System.out.println("Был добавлен элемент с key = " + key);
              } catch (JsonSyntaxException e) {
                System.out.println("Неправильно введён элемент");
                input.nextLine();
              }
            }
            catch (java.lang.NullPointerException e) {
              System.out.println("Не введён element");
            }
          }
        }
        catch (java.lang.NullPointerException e) {
          System.out.println("Не введён id");
        }
        catch (java.util.InputMismatchException e) {
          System.out.println("Введён неверный id");
          input.nextLine();
        }
        element = "";
        break;
      }
      /** Обновляет значение элемента коллекции, id которого равен заданному */
      case "update":{
        try {
          if (!collection.containsKey(key = input.nextLong())) {
            System.out.println("Элемента с таким key не существует");
            input.nextLine();
          } else {
            try {
              element = input.nextLine();
              try {
                System.out.println(gson.fromJson(element, LabWork.class));
                collection.remove(key);
                collection.put(key, gson.fromJson(element, LabWork.class));
                System.out.println("Был обновлён элеммент с id = " + key);
              } catch (JsonSyntaxException e) {
                System.out.println("Неправильно введён элемент");
                input.nextLine();
              }
            }
            catch (java.lang.NullPointerException e) {
              System.out.println("Не введён element");
            }
          }
        }
        catch (java.lang.NullPointerException e) {
          System.out.println("Не введён key");
        }
        catch (java.util.InputMismatchException e) {
          System.out.println("Введён неверный key");
          input.nextLine();
        }
        element = "";
        break;
      }
      /** Удаляет элемент из коллекции по его ключу */
      case "remove_key":{
        try {
          if (!collection.containsKey(key = input.nextLong()))
            System.out.println("Элемента с таким key не существует");
          else {
            collection.remove(key);
            System.out.println("Был удалён элемент с key = " + key);
          }
        }
        catch (java.lang.NullPointerException e) {
          System.out.println("Не введён key");
        }
        catch (java.util.InputMismatchException e) {
          System.out.println("Введён неверный key");
          input.nextLine();
        }
        break; //remove_key key   удалить элемент из коллекции по его ключу
      }
      /** Очищает коллекцию */
      case "clear":{
        collection.clear();
        System.out.println("Коллекция была успешно отчищена");
        break; //очистить коллекцию
      }
      /** Сохраняет коллекцию в файл */
      case "save":{
        FileOutputStream writer = new FileOutputStream(file_name);
        writer.write(start_of_saving.getBytes());
        int i = 1;
        if (collection.size() == 0)
          System.out.println("Была записана пустая коллекция");
        else {
          for (Entry<Long, LabWork> entry : collection.entrySet()) {

            writer.write(gson.toJson(entry.getValue()).getBytes());
            if (i != collection.size()) {
              writer.write(comma.getBytes());
              i++;
            }
          }
          System.out.println("Было записано " + i + " эллемента(ов) коллекции");
        }
        writer.write(end_of_saving.getBytes());
        writer.close();
        break; //сохранить коллекцию в файл
      }
      /** Выполняет скрипт из файла */
      case "execute_script": {
        isFile = true;
        try {
          file_name = input.next();
          if (file_name.equals(file_name_for_script))
            System.out.println("В файле присутствует рекурсия выполнение сего файла");
          else {
            file_name_for_script = file_name;
            try {
              FileReader fr = new FileReader(file_name);
              BufferedReader reader = new BufferedReader(fr);
              String line = reader.readLine();
              do {
                InputStream Input_for_System_in = new ByteArrayInputStream(line.getBytes("UTF-8"));
                System.setIn(Input_for_System_in);
                input = new Scanner(System.in);
                command(input.next());
                System.setIn(System.in);
                line = reader.readLine();
              } while (line != null);
            } catch (java.io.FileNotFoundException e)
            {
              System.out.println("Неверно введён файл");
            }
            break;
          }
        }
        catch (java.lang.NullPointerException e){
          System.out.println("Не введён file_name");
        }
        file_name_for_script = null;
        break; //execute_script file_name   считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.
      }
      /** Удаляет из коллекции все элементы, меньшие, чем заданный */
      case "remove_lower": {
        try{
          element = input.nextLine();
          try {
            LabWork labWork = gson.fromJson(element, LabWork.class);
            HashMap<Long, LabWork> acollection = (HashMap<Long, LabWork>) collection.clone();
            for (Entry<Long, LabWork> entry : acollection.entrySet()) {
              if (labWork.compareTo(entry.getValue()) > 0) {
                collection.remove(entry.getKey());
              }
            }
          }
          catch(JsonSyntaxException e)
          {
            System.out.println("Неправильно введён элемент");
            input.nextLine();
          }
        }
        catch (java.lang.NullPointerException e) {
          System.out.println("Не введён element");
        }
        break; //remove_lower {element}   удалить из коллекции все элементы, меньшие, чем заданный
      }
      /** Заменяет значение по ключу, если новое значение больше старого */
      case "replace_if_greater":{
        try {
          if (!collection.containsKey(key = input.nextLong())) {
            System.out.println("Элемента с таким key уже существует");
            input.nextLine();
          } else {
            try {
              element = input.nextLine();
              try {
                LabWork labWork = gson.fromJson(element, LabWork.class);
                if (labWork.compareTo(collection.get(key)) > 0) {
                  collection.put(key, labWork);
                }
              } catch (JsonSyntaxException e) {
                System.out.println("Неправильно введён элемент");
                input.nextLine();
              }
            }
            catch (java.lang.NullPointerException e) {
              System.out.println("Не введён element");
            }
          }
        }
        catch (java.lang.NullPointerException e) {
          System.out.println("Не введён key");
        }
        catch (java.util.InputMismatchException e) {
          System.out.println("Введён неверный key");
          input.nextLine();
        }
        element = "";
        break; //replace_if_greater key {element} заменить значение по ключу, если новое значение больше старого
      }
      /** Удаляет из коллекции все элементы, ключ которых меньше, чем заданный */
      case "remove_lower_key":{
        try {
          HashMap<Long, LabWork> acollection = (HashMap<Long, LabWork>) collection.clone();
          for (Entry<Long, LabWork> entry : acollection.entrySet())
            if (entry.getKey() < key)
              collection.remove(entry.getKey());
          acollection.clear();
        }
        catch (java.lang.NullPointerException e) {
          System.out.println("Не введён key");
        }
        catch (java.util.InputMismatchException e) {
          System.out.println("Введён неверный key");
          input.nextLine();
        }
        break; //remove_lower_key key   удалить из коллекции все элементы, ключ которых меньше, чем заданный
      }
      /** Удаляет из коллекции все элементы, значение поля difficulty которого эквивалентно заданному */
      case "remove_all_by_difficulty":{
        try {
          difficulty = input.next();
          try {
            HashMap<Long, LabWork> acollection = (HashMap<Long, LabWork>) collection.clone();
            for (Entry<Long, LabWork> entry : acollection.entrySet())
              if (entry.getValue().getDifficulty() == Difficulty.valueOf(difficulty))
                collection.remove(entry.getKey());
            acollection.clear();
          } catch (IllegalArgumentException e) {
            System.out.println("Такой сложности не существует");
          }
        }
        catch (java.lang.NullPointerException e) {
          System.out.println("Не введён difficulty");
        }
        break; //remove_all_by_difficulty difficulty   удалить из коллекции все элементы, значение поля difficulty которого эквивалентно заданному
      }
      /** Выводит любой объект из коллекции, значение поля coordinates которого является минимальным */
      case "min_by_coordinates":{
        Coordinates min_coordinates = new Coordinates();
        if (collection.size() == 0)
          System.out.println("Коллекция пуста");
        else
        {
          for (Entry<Long, LabWork> entry : collection.entrySet()) {
            min_coordinates = collection.get(entry.getKey()).getCoordinates();
            break;
          }
          for (Entry<Long, LabWork> entry : collection.entrySet()) {
            if (entry.getValue().getCoordinates().compareTo(min_coordinates) < 0 ) {
              min_coordinates = collection.get(entry.getKey()).getCoordinates();
            }
          }
        }
        System.out.println(min_coordinates);
        break; //вывести любой объект из коллекции, значение поля coordinates которого является минимальным
      }
      /** Выводит значения поля author в порядке возрастания */
      case "print_field_ascending_author":{
        try {
          author_str = input.nextLine();
          Person author = new Person();
          try {
            author = gson.fromJson(author_str, Person.class);
            for (Entry<Long, LabWork> entry : collection.entrySet()) {
              if (collection.get(entry.getKey()).getAuthor() != null && collection.get(entry.getKey()).getAuthor().equals(author))
                System.out.println(collection.get(entry.getKey()));
            }
          } catch (JsonSyntaxException e) {
            System.out.println("Неправильно введён автор");
            input.nextLine();
          }
        }
        catch (java.lang.NullPointerException e) {
          System.out.println("Не введён author");
        }
        break; //print_field_ascending_author author - вывести значения поля author в порядке возрастания
      }
      /** Обработка пустого запроса */
      case "":{

        break;
      }
      /** Завершение работы программы */
      case "exit":{
        System.exit(0);
        break;
      }
      /** Обработка неизвестной команды */
      default:{
        System.out.println("Неизвестная команда");
      }
    }
  }
}
