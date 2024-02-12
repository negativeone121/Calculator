package main.java;

import java.util.Locale;
import java.util.Scanner;

public class Calculator {

    public static void main(String[] args) {
        int[] arabianNumbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        String[] romanNumbers = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};

        String firstValueFromInput;
        String secondValueFromInput;

        int firstValue = 0;
        int secondValue = 0;

        boolean isRoman = false;
        System.out.println("введите арефметическое выражение арабскими/римскими числами от 1 до 10");
        System.out.println("для выхода наберите слово \"выход\""); // экранирование - смотри пример на слове "выход"
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String inputString = scanner.nextLine().toUpperCase();

            if (inputString.equals("ВЫХОД")){
                break;
            }
            if (inputString.isBlank()){ // break - останавливает цикл, continue - преход на следующую операцию.
                continue;
            }

            inputString = inputString.replaceAll("\\s", "");

            int symbolIndexFounded = findSymbolIndex(inputString);//
            firstValueFromInput = inputString.substring(0, symbolIndexFounded);
            secondValueFromInput = inputString.substring(symbolIndexFounded + 1);

            if ((isRoman(firstValueFromInput) && isArabian(secondValueFromInput))
                    || (isArabian(firstValueFromInput) && isRoman(secondValueFromInput))) {
                throw new IllegalArgumentException("ошибка - используется арабское/римское числа");
            } else if (isArabian(firstValueFromInput) && isArabian(secondValueFromInput)) {
                firstValue = Integer.parseInt(firstValueFromInput);
                secondValue = Integer.parseInt(secondValueFromInput);
            } else if (isRoman(firstValueFromInput) && isRoman(secondValueFromInput)) {
                int indexFirstNumber = 0;
                int indexSecondNumber = 0;
                for (int i = 0; i < romanNumbers.length; i++) {
                    if (romanNumbers[i].equals(firstValueFromInput)) {
                        indexFirstNumber = i;
                    }
                    if (romanNumbers[i].equals(secondValueFromInput)) {
                        indexSecondNumber = i;
                    }
                }
                firstValue = arabianNumbers[indexFirstNumber];
                secondValue = arabianNumbers[indexSecondNumber];
                isRoman = true;
            }

            if (firstValue <= 0 || firstValue > 10
                    || secondValue <= 0 || secondValue > 10) {
                throw new IllegalArgumentException("ввели число вне диапазона допустимых значений");
            }

            int result = getIntResult(inputString.charAt(symbolIndexFounded), firstValue, secondValue);

            if (isRoman) {
                if (result < 1) {
                    throw new IllegalArgumentException("результат в римских числах меньше единицы");
                }
                System.out.println("Результат: "+getRomanResult(result));
            } else {
                System.out.println("Результат: "+result);
            }
        }
    }

    private static int getIntResult(char symbol, int firstValue, int secondValue) {
        int result = 0;
        switch (symbol) {
            case '+': {
                result = firstValue + secondValue;
                break;
            }
            case '-': {
                result = firstValue - secondValue;
                break;
            }
            case '*': {
                result = firstValue * secondValue;
                break;
            }
            case '/': {
                result = firstValue / secondValue;
                break;
            }
            default: {
                throw new IllegalArgumentException("ошибка - не распознан математический оператор");
            }
        }
        return result;
    }

    private static int findSymbolIndex(String string) {
        if (string.contains("+") || string.contains("-") || string.contains("*") || string.contains("/")) {
            int symbolIndex = 0;
            if (string.contains("+")) {
                symbolIndex = string.indexOf("+");
            }
            if (string.contains("-")) {
                symbolIndex = string.indexOf("-");
            }
            if (string.contains("*")) {
                symbolIndex = string.indexOf("*");
            }
            if (string.contains("/")) {
                symbolIndex = string.indexOf("/");
            }
            return symbolIndex;
        } else {
            throw new IllegalArgumentException("ошибка - не был передан математический оператор");
        }
    }

    private static boolean isRoman(String string) {
        return string.matches("[IVX]+");
    }

    private static boolean isArabian(String string) {
        return string.matches("[0-9]+");
    }

    private static String getRomanResult(int result) {
        String stringResult = "";
        if (result == 100) {
            stringResult += "C";
            result -= 100;
        }
        while (result >= 90) {
            stringResult += "XC";
            result -= 90;
        }
        while (result >= 50) {
            stringResult += "L";
            result -= 50;
        }
        while (result >= 40) {
            stringResult += "XL";
            result -= 40;
        }
        while (result >= 10) {
            stringResult += "X";
            result -= 10;
        }
        while (result >= 9) {
            stringResult += "IX";
            result -= 9;
        }
        while (result >= 5) {
            stringResult += "V";
            result -= 5;
        }
        while (result >= 4) {
            stringResult += "IV";
            result -= 4;
        }
        while (result >= 1) {
            stringResult += "I";
            result -= 1;
        }
        return stringResult;
    }
}