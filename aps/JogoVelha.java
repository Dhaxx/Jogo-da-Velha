package aps;

import java.util.Random;
import java.util.Scanner;

public class JogoVelha {
    private static Random random = new Random();
    private static Scanner input = new Scanner(System.in);
    private static char[][] grade = {
        {' ', ' ', ' '},
        {' ', ' ', ' '},
        {' ', ' ', ' '}
    };
    private static String jogadorX, jogadorO;
    private static char tipoX, tipoO;
    private static String vencedor, transgressor;
    private static char vez = 'X';
    private static String historico = "";
    public static void main(String[] args) {
        inicializa();
        while (vencedor == null && gradeAberta()) {
            desenhaGrade();
            jogar();
            vez = vez == 'X' ? 'O' : 'X';
        }
        finalizar();
    }
    private static void inicializa() {
        System.out.println("Jogo da Velha");
        System.out.println("-------------");
        
        do {
            System.out.print("Jogador X: (H)omem ou (M)aquina? ");     
            tipoX = input.nextLine().trim().toUpperCase().charAt(0);
        } while (tipoX != 'H' && tipoX != 'M'); // tipo tem que ser H ou M, se não repete novamente
        do  {
            System.out.print("Nome do jogador X: ");
            jogadorX = input.nextLine().trim();
                System.out.println();
        } while (jogadorX.length() == 0); // Nome não pode ser vazio
        do {
            System.out.print("Jogador O: (H)omem ou (M)aquina? ");
            tipoO = input.nextLine().trim().toUpperCase().charAt(0);
        } while (tipoO != 'H' && tipoO != 'M'); // tipo tem que ser H ou M, se não repete novamente
        do  {
            System.out.print("Nome do jogador O: ");
            jogadorO = input.nextLine().trim();
                System.out.println();
        } while (jogadorO.length() == 0); // Nome não pode ser vazio
        
    }
    private static void desenhaGrade() {
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_YELLOW = "\u001B[33m";
        final String ANSI_CIAN = "\u001B[36m";
        System.out.print("\033[H\033[2J");
        System.out.println(ANSI_CIAN + "     1   2   3" + ANSI_RESET);
        System.out.println();
        System.out.println(ANSI_CIAN + " 1   " + ANSI_RESET +
                            grade[0][0] + ANSI_YELLOW + " | " +
                            ANSI_RESET + grade[0][1] + ANSI_YELLOW +
                            " | " + ANSI_RESET + grade[0][2]);
        System.out.println(ANSI_YELLOW + "    ---+---+---" + ANSI_RESET);
       
        System.out.println(ANSI_CIAN + " 2   " + ANSI_RESET + 
                            grade[1][0] + ANSI_YELLOW + " | " + 
                            ANSI_RESET + grade[1][1] + ANSI_YELLOW + 
                            " | " + ANSI_RESET + grade[1][2]);         
        System.out.println(ANSI_YELLOW + "    ---+---+---" + ANSI_RESET);

        System.out.println(ANSI_CIAN + " 3   " + ANSI_RESET +
                            grade[2][0] + ANSI_YELLOW + " | " + 
                            ANSI_RESET + grade[2][1] + ANSI_YELLOW + 
                            " | " + ANSI_RESET + grade[2][2]);
        System.out.println();
        System.out.print(historico); 
    }
    private static void jogar() {
        char tipo = vez == 'X' ? tipoX : tipoO;
        String jogador = vez == 'X' ? jogadorX : jogadorO;
        System.out.print("Jogador " + vez + ": " + jogador + ", jogue (linha e coluna)? ");
        int lin, col;
        if (tipo == 'H') {
            lin = input.nextInt();
            col = input.nextInt();
            if (lin < 1 || lin > 3 || col < 1 || col > 3 || grade[lin - 1][col -1] != ' ') {
                transgressor = jogador;
                vencedor = vez == 'X' ? jogadorO : jogadorX;
            } else {
                grade[lin - 1][col -1] = vez;
            }
        } else {
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                
            }
            while (true) {
                lin = random.nextInt(3) + 1;
                col = random.nextInt(3) + 1;
                if (grade[lin -1][col - 1] == ' ') {
                    System.out.println(lin + " " + col);
                    grade[lin - 1][col - 1] = vez;
                    break;
                }
            }
        }
        historico += "Jogador " + vez + ": " + jogador + ", jogue (linha e coluna)? " + lin + " " + col + "\n";
        if (triangulacao()) vencedor = jogador;
    }

    private static boolean gradeAberta() {
        for (int lin = 0; lin < 3; lin++) {
            for (int col = 0; col < 3; col++) {
                if (grade[lin][col] == ' ') return true;
            }
        }
        return false;
    }

    private static boolean triangulacaoLin(int lin) {
        return grade[lin][0] == vez && grade[lin][1] == vez && grade[lin][2] == vez;
    }
    private static boolean triangulacaoCol(int col) {
        return grade[0][col] == vez && grade[1][col] == vez && grade[2][col] == vez;
    }
    private static boolean triangulacaoDiagonal() {
        return (grade[0][0] == vez && grade[1][1] == vez && grade[2][2] == vez) ||
               (grade[0][2] == vez && grade[1][1] == vez && grade[2][0] == vez);
    }
    private static boolean triangulacao() {
        return triangulacaoLin(0) || triangulacaoLin(1) || triangulacaoLin(2) ||
               triangulacaoCol(0) || triangulacaoCol(1) || triangulacaoCol(2) ||
               triangulacaoDiagonal();
    }

    private static void finalizar() {
        desenhaGrade(); 
        if (vencedor == null) {
            System.out.println("Deu velha!");
        } else {
            if (transgressor != null) {
                System.out.println( transgressor + " desclassificado!");
            }
            System.out.println(vencedor + " é o vencedor!");
        }
    }
}
