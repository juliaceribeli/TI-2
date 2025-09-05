package exercicio01;

import java.util.Scanner;

public class SomaDoisNumeros {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Digite o primeiro número: ");
        double numero1 = scanner.nextDouble();
        
        System.out.print("Digite o segundo número: ");
        double numero2 = scanner.nextDouble();
        
        double soma = numero1 + numero2;
        
        System.out.println("A soma de " + numero1 + " + " + numero2 + " = " + soma);
        
        scanner.close();
    }
}