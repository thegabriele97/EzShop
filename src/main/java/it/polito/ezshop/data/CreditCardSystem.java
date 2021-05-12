package it.polito.ezshop.data;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;
import static it.polito.ezshop.data.EZShop.*;

class CreditCardSystem {
    
    private static CreditCardSystem instance;

    private CreditCardSystem() {
        
    }

    static CreditCardSystem getInstance() {
        
        if (instance == null) {
            instance = new CreditCardSystem();
        }

        return instance;
    }

    boolean isValidNumber(String creditCard) {
        
        int sum = 0;
        boolean skip = false;

        for (int i = creditCard.length() - 2; i >= 0; i--, skip ^= true) {

            int curr_digit = Integer.parseInt(creditCard.substring(i, i + 1));
            int mul_2 = !skip ? curr_digit << 1 : curr_digit;
            sum += (mul_2 % 10) + (mul_2 / 10);

        }

        return ((sum * 9) % 10) == Integer.parseInt(creditCard.substring(creditCard.length() - 1));
    }

    boolean isRegistered(String creditCard) {

        if (creditCard == null || creditCard.isEmpty() || !isValidNumber(creditCard)) return false;

        try (Stream<String> stream = Files.lines(Paths.get("./it.polito.ezshop.utils.CreditCards.txt"))) {

            boolean result = stream
                .filter(line -> !line.startsWith("#"))
                .map(line -> line.split(";"))
                .anyMatch(line -> line[0].equals(creditCard));

            stream.close();
            return result;

        } catch (IOException e) {
            return false;
        }

    }

    boolean hasEnoughBalance(String creditCard, double toRemove) {
        
        if (creditCard == null || creditCard.isEmpty() || !isValidNumber(creditCard)) return false;

        try (Stream<String> stream = Files.lines(Paths.get("src/main/java/it/polito/ezshop/utils/CreditCards.txt"))) {

            boolean result = stream
                .filter(line -> !line.startsWith("#"))
                .map(line -> line.split(";"))
                .filter(line -> line[0].equals(creditCard))
                .map(line -> getRightDoublePrecision(line[1]))
                .allMatch(credit -> credit >= getRightDoublePrecision(toRemove));

            stream.close();
            return result;

        } catch (IOException e) {
            return false;
        }

    }

    boolean updateBalance(String creditCard, double toRemove) {
        
        if (creditCard == null || creditCard.isEmpty() || !hasEnoughBalance(creditCard, toRemove)) return false;

        try (Stream<String> stream = Files.lines(Paths.get("src/main/java/it/polito/ezshop/utils/CreditCards.txt"))) {

            List<String> lines = stream
                .map(line -> {
                    if (line.startsWith("#") || !line.startsWith(creditCard + ";")) return line;

                    String[] split = line.split(";");
                    return split[0] + ";" + String.valueOf(getRightDoublePrecision(Double.valueOf(split[1]) - toRemove));
                })
                .collect(toList());

            stream.close();


            FileWriter writer = new FileWriter("src/main/java/it/polito/ezshop/utils/CreditCards.txt");
            boolean write_carriageret = false;
            for (String line : lines) {
                
                if (write_carriageret) {
                    writer.write("\n");
                }

                writer.write(line);
                write_carriageret = true;
            }

            writer.close();

        } catch (IOException e) {
            return false;
        }

        return true;

    }

}
