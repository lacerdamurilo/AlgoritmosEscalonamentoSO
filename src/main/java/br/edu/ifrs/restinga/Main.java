package br.edu.ifrs.restinga;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        input.useDelimiter("\n");

        int processos = 3;
        int tempo_chegada = 0;
        int tempo_execucao = 0;
        int tempo_restante = 0;
        int prioridade = 0;
        int op = 999;
        int t_intervalo = 0;

        System.out.println("sera aleatorio? " +
                "\n 1 = sim" +
                "\n qualquer outro numero = nao ");
        int opp = input.nextInt();

        if(opp != 1){
            for(int i = 0; i < processos; i++){
                System.out.println("processo ["+i+"] tempo_execucao: ");
                tempo_execucao = input.nextInt();
                System.out.println("processo ["+i+"] tempo_chegada: ");
                tempo_chegada = input.nextInt();
                System.out.println("processo ["+i+"] prioridade: ");
                prioridade = input.nextInt();

                Processo p = new Processo(i, tempo_execucao, tempo_chegada, prioridade);
            }
        } else {
            for (int i = 0; i < processos; i++) {
                Processo p = new Processo(i);
            }
        }

        while(op != 0) {
            Escalonamentos e = new Escalonamentos();
            System.out.println("\n\n1 - FCFS.");
            System.out.println("2 - SJF - nao preemptivo.");
            System.out.println("3 - Prioridade - nao preemptivo.");
            System.out.println("4 - RoundRobin.");
            System.out.println("5 - imprime_stats.");
            System.out.println("0 - Sair.\n");

            op = input.nextInt();

            switch (op){
                case 1:
                    e.fcfs();
                    break;
                case 2:
                    e.sjfNaoPreemptivo();
                    break;
                case 3:
                    e.prioridadeNaoPreemptivo();
                    break;
                case 4:
                    System.out.println("Time-Slice (intervalo de tempo): ");
                    t_intervalo = input.nextInt();

                    e.roundRobin(t_intervalo);
                    break;
                case 5:
                    e.imprime_stats();
                    break;
                case 0:
                    break;
            }
        }

    }
}
