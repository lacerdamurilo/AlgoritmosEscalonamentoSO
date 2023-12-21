package br.edu.ifrs.restinga;

import java.util.ArrayList;
import java.util.List;

public class Escalonamentos {

    static List<Processo> processosAlgoritmos = new ArrayList<>();

    public Escalonamentos(){
        Processo p = new Processo();
        processosAlgoritmos = p.escalonamento();
    }

    public void imprime_stats(){
        for (Processo processo : processosAlgoritmos) {
            System.out.println("processo ["+processo.getProcesso()+"]");
            System.out.println("tempo_chegada = "+processo.getTempo_chegada());
            System.out.println("tempo_execução = "+processo.getTempo_execucao());
            System.out.println("tempo_restante = "+processo.getTempo_restante());
            System.out.println("prioridade = "+processo.getPrioridade() + "\n");
        }
    }

    public void fcfs(){
        int tempo_total = 0;
        int tempo_execucao = 1;
        int tempo_espera = 0;
        double espera_media = 0;

        for (Processo item : processosAlgoritmos) {
            tempo_total += item.getTempo_execucao();
        }
        System.out.println("tempo estimado = "+ tempo_total);

        // FCFS
        for(int i = 0; i < processosAlgoritmos.size(); i++){
            while(processosAlgoritmos.get(i).getTempo_restante() > 0){
                //diminui tempo restante
                processosAlgoritmos.get(i).setTempo_restante(processosAlgoritmos.get(i).getTempo_restante() - 1);

                //mostra
                System.out.println("tempo["+tempo_execucao +"]: processo["+ processosAlgoritmos.get(i).getProcesso()+
                        "] restante = "+ processosAlgoritmos.get(i).getTempo_restante());
                tempo_execucao++;

                //contador de espera em outros processos
                for(int x = i + 1; x <= processosAlgoritmos.size() - 1; x++){
                    processosAlgoritmos.get(x).setTempo_espera(processosAlgoritmos.get(x).getTempo_espera() +1);
                }
                for(int x = 0; x < i; x++){
                    if(processosAlgoritmos.get(x).getTempo_restante() > 0) {
                        processosAlgoritmos.get(x).setTempo_espera(processosAlgoritmos.get(x).getTempo_espera() + 1);
                    }
                }
            }
        }

        //calcula espera total e faz media
        for (Processo value : processosAlgoritmos) {
            tempo_espera += value.getTempo_espera();
        }

        espera_media = (double) tempo_espera /(processosAlgoritmos.size());

        //status de espera_media dos processos
        System.out.println("\n");
        for(Processo pTempo_espera : processosAlgoritmos){
            System.out.println("processo["+pTempo_espera.getProcesso()+"] tempo_espera = "+pTempo_espera.getTempo_espera());
        }
        System.out.println("\ntempo de espera_medio = "+  espera_media);
    }

    public void sjfNaoPreemptivo() {
        int tempo_total = 0;
        int tempo_execucao = 1;
        int tempo_espera = 0;
        double espera_media = 0;

        for (int i = 0; i < processosAlgoritmos.size(); i++) {
            tempo_total += processosAlgoritmos.get(i).getTempo_execucao();
        }
        System.out.println("tempo estimado = "+tempo_total);

        Processo p1 = new Processo();
        p1 = (p1.buscaTempo_chegada());//busca processo no tempo_chegada

        //mostra tempo enquanto o processo n chega (saida exemplo)
        while(!p1.mostraTempo_execucaoVazio(p1, tempo_execucao)){
            System.out.println("tempo["+tempo_execucao+"]: nenhum processo esta pronto" );
            tempo_execucao++;
        }

        //SJF - nao preemptivo
        while (p1.getTempo_restante() > 0) {
            //diminui e tempo restante
            p1.setTempo_restante(p1.getTempo_restante() - 1);

            //mostra info
            System.out.println("tempo["+tempo_execucao+"]: processo["+p1.getProcesso()+
                    "] restante = "+p1.getTempo_restante());

            tempo_execucao++;
            p1.attTempo_restante(p1);//ajusta tempo restante na classe Processo


            //ajusta tempo_restante na classe Escalonamentos (array)
            for(int i = 0; i <  processosAlgoritmos.size(); i++){
                if(p1.getProcesso() == i){
                    processosAlgoritmos.get(i).setTempo_restante(processosAlgoritmos.get(i).getTempo_restante() - 1);
                }
            }


            //aumenta o tempo de espera dos outros processos
            for(int i = 0; i <  processosAlgoritmos.size(); i++){
                if(p1.getProcesso() == i){
                    for(int x = i + 1; x <= processosAlgoritmos.size() - 1; x++){
                        processosAlgoritmos.get(x).setTempo_espera(processosAlgoritmos.get(x).getTempo_espera() +1);
                    }
                    for(int x = 0; x < i; x++){
                        if(processosAlgoritmos.get(x).getTempo_restante() > 0) {
                            processosAlgoritmos.get(x).setTempo_espera(processosAlgoritmos.get(x).getTempo_espera() + 1);
                        }
                    }
                }
            }

        }

        //apos a chegada de processos escolhe com o menor tempo
        for(int i = 0; i < processosAlgoritmos.size(); i++){
            Processo sortProcess = new Processo();

            sortProcess = (sortProcess.buscaProcesso_execucao());

            while(!sortProcess.mostraTempo_execucaoVazio(sortProcess, tempo_execucao)){
                System.out.println("tempo["+tempo_execucao+"] nenhum processo esta pronto" );
                tempo_execucao++;

                //add tempo_espera p processos
                for(int x = 0; x < processosAlgoritmos.size(); x++){
                    processosAlgoritmos.get(x).setTempo_espera(processosAlgoritmos.get(x).getTempo_espera() + 1);
                }
            }

            while (sortProcess.getTempo_restante() > 0) {
                sortProcess.setTempo_restante(sortProcess.getTempo_restante() - 1);

                System.out.println("tempo["+tempo_execucao+"]: processo["+ sortProcess.getProcesso()+
                        "] restante = " + sortProcess.getTempo_restante());

                tempo_execucao++;
                sortProcess.attTempo_restante(sortProcess);

                //ajusta tempo_restante na classe Escalonamentos (array)
                for(int x = 0; x <  processosAlgoritmos.size(); x++){
                    if(sortProcess.getProcesso() == x){
                        processosAlgoritmos.get(x).setTempo_restante(processosAlgoritmos.get(x).getTempo_restante() - 1);
                    }
                }

                //contador de espera em outros processos
                for(int k = 0; k <  processosAlgoritmos.size(); k++){
                    if(sortProcess.getProcesso() == k){
                        for(int x = k + 1; x <= processosAlgoritmos.size() - 1; x++){
                            processosAlgoritmos.get(x).setTempo_espera(processosAlgoritmos.get(x).getTempo_espera() +1);
                        }
                        for(int x = 0; x < k; x++){
                            if(processosAlgoritmos.get(x).getTempo_restante() > 0) {
                                processosAlgoritmos.get(x).setTempo_espera(processosAlgoritmos.get(x).getTempo_espera() + 1);
                            }
                        }
                    }
                }
            }
        }

        //calcula espera total e faz media
        for(int i = 0; i < processosAlgoritmos.size(); i++){
            tempo_espera  += processosAlgoritmos.get(i).getTempo_espera();
        }

        espera_media = (double) tempo_espera/(processosAlgoritmos.size());

        System.out.println("\n");
        for(Processo pTempo_espera : processosAlgoritmos){
            System.out.println("processo["+pTempo_espera.getProcesso()+"] tempo_espera = "+pTempo_espera.getTempo_espera());
        }

        System.out.println("\ntempo de espera_medio = "+  espera_media);

        //ajusta tempo_restante na classe Processo para execucao
        for(int i = 0; i < processosAlgoritmos.size(); i++){
            processosAlgoritmos.get(i).resetTempo_restante(processosAlgoritmos.get(i));
        }
    }


    public void prioridadeNaoPreemptivo(){//logica parecida com sjf n preemptivo
        int tempo_total = 0;
        int tempo_execucao = 1;
        int tempo_espera = 0;
        double espera_media = 0;


        for (int i = 0; i < processosAlgoritmos.size(); i++) {
            tempo_total += processosAlgoritmos.get(i).getTempo_execucao();
        }
        System.out.println("tempo estimado = " + tempo_total);

        Processo p1 = new Processo();
        p1 = (p1.buscaTempo_chegada());//busca processo tempo_chegada

        //conta tempo enquanto processo n "chega"
        while(!p1.mostraTempo_execucaoVazio(p1, tempo_execucao)){
            System.out.println("tempo["+tempo_execucao+"] nenhum processo esta pronto" );
            tempo_execucao++;
        }

        //prioridade - nao preemptivo
        while (p1.getTempo_restante() > 0) {
            //diminui tempo_restante
            p1.setTempo_restante(p1.getTempo_restante() - 1);

            //mostra info
            System.out.println("tempo["+tempo_execucao+"]: processo["+p1.getProcesso()+
                    "] restante = " + p1.getTempo_restante());

            tempo_execucao++;
            p1.attTempo_restante(p1);//ajusta tempo restante na classe Processo

            for(int i = 0; i <  processosAlgoritmos.size(); i++){//diminui tempo_restante(na classe Escalonamentos, como sjf)
                if(p1.getProcesso() == i){
                    processosAlgoritmos.get(i).setTempo_restante(processosAlgoritmos.get(i).getTempo_restante() - 1);
                }
            }

            //contador de espera em outros processos
            for(int i = 0; i <  processosAlgoritmos.size(); i++){
                if(p1.getProcesso() == i){
                    for(int x = i + 1; x <= processosAlgoritmos.size() - 1; x++){
                        processosAlgoritmos.get(x).setTempo_espera(processosAlgoritmos.get(x).getTempo_espera() +1);
                    }
                    for(int x = 0; x < i; x++){
                        if(processosAlgoritmos.get(x).getTempo_restante() > 0) {
                            processosAlgoritmos.get(x).setTempo_espera(processosAlgoritmos.get(x).getTempo_espera() + 1);
                        }
                    }
                }
            }
        }

        //apos a chegada de processos escolhe maior prioridade
        for(int i = 0; i < processosAlgoritmos.size(); i++){

            Processo priorityProcess = new Processo();
            priorityProcess = (priorityProcess.buscaProcesso_prioridade(tempo_execucao));

            while(!priorityProcess.mostraTempo_execucaoVazio(priorityProcess, tempo_execucao)){
                System.out.println("tempo["+tempo_execucao + "] nenhum processo esta pronto" );
                tempo_execucao++;

                for(int k = 0; i < processosAlgoritmos.size(); k++){
                    processosAlgoritmos.get(k).setTempo_espera(processosAlgoritmos.get(k).getTempo_espera() + 1);
                }
            }

            while (priorityProcess.getTempo_restante() > 0) {
                priorityProcess.setTempo_restante(priorityProcess.getTempo_restante() - 1);

                System.out.println("tempo["+tempo_execucao+"]: processo["+priorityProcess.getProcesso()+
                        "] restante = "+priorityProcess.getTempo_restante());

                tempo_execucao++;
                priorityProcess.attTempo_restante(priorityProcess);//ajusta tempo classe Processo

                //tira tempo_espera da lista nessa classe como anteriormente
                for(int x = 0; x <  processosAlgoritmos.size(); x++){
                    if(priorityProcess.getProcesso() == x){
                        processosAlgoritmos.get(x).setTempo_restante(processosAlgoritmos.get(x).getTempo_restante() - 1);
                    }
                }

                //contador de tempo_espera de processos
                for(int k = 0; k <  processosAlgoritmos.size(); k++){
                    if(priorityProcess.getProcesso() == k){
                        for(int x = k + 1; x <= processosAlgoritmos.size() - 1; x++){
                            processosAlgoritmos.get(x).setTempo_espera(processosAlgoritmos.get(x).getTempo_espera() +1);
                        }
                        for(int x = 0; x < k; x++){
                            if(processosAlgoritmos.get(x).getTempo_restante() > 0) {
                                processosAlgoritmos.get(x).setTempo_espera(processosAlgoritmos.get(x).getTempo_espera() + 1);
                            }
                        }
                    }
                }
            }

        }
        //calcula o tempo de espera total
        for (Processo algoritmo : processosAlgoritmos) {
            tempo_espera += algoritmo.getTempo_espera();
        }

        espera_media = (double) tempo_espera/(processosAlgoritmos.size());

        System.out.println("\n");
        for(Processo pTempo_espera : processosAlgoritmos){
            System.out.println("processo["+pTempo_espera.getProcesso()+"] tempo_espera = "+pTempo_espera.getTempo_espera());
        }

        System.out.println("\ntempo de espera_medio = "+  espera_media);

        //reinicia tempo_restante em Processo
        for (Processo processosAlgoritmo : processosAlgoritmos) {
            processosAlgoritmo.resetTempo_restante(processosAlgoritmo);
        }
    }

    public void roundRobin(int timeSlice) {
        int tempo_total = 0;
        int tempo_execucao = 1;
        int tempo_executando = 0;
        int tempo_espera = 0;
        double espera_media = 0;

        Processo p = new Processo();

        for (Processo algoritmo : processosAlgoritmos) {
            tempo_total += algoritmo.getTempo_execucao();
        }
        System.out.println("tempo estimado = "+ tempo_total);

        for(int i = 0; i < processosAlgoritmos.size(); i++){
            while(processosAlgoritmos.get(i).getTempo_restante() > 0){

                processosAlgoritmos.get(i).setTempo_restante(processosAlgoritmos.get(i).getTempo_restante() - 1);

                tempo_executando++;

                System.out.println("tempo["+tempo_execucao+"]: processo["+processosAlgoritmos.get(i).getProcesso()+
                        "] restante = "+ processosAlgoritmos.get(i).getTempo_restante());

                tempo_execucao++;

                for(int x = i + 1; x <= processosAlgoritmos.size() - 1; x++){
                    if(processosAlgoritmos.get(x).getTempo_restante() > 0) {
                        processosAlgoritmos.get(x).setTempo_espera(processosAlgoritmos.get(x).getTempo_espera() + 1);
                    }
                }
                for(int x = 0; x < i; x++){
                    if(processosAlgoritmos.get(x).getTempo_restante() > 0) {
                        processosAlgoritmos.get(x).setTempo_espera(processosAlgoritmos.get(x).getTempo_espera() + 1);
                    }
                }

                //joga processo para o final da fila (??)
                if(tempo_executando == timeSlice){
                    p = processosAlgoritmos.remove(i);
                    processosAlgoritmos.add(p);

                    tempo_executando = 0;

                }
                if(processosAlgoritmos.get(i).getTempo_restante()  == 0){
                    p = processosAlgoritmos.remove(i);
                    processosAlgoritmos.add(p);
                }
            }
        }
        //calcula o tempo de espera total
        for (Processo processosAlgoritmo : processosAlgoritmos) {
            tempo_espera += processosAlgoritmo.getTempo_espera();
        }

        //reinicia o tempo restante dos processos para que possa ser feito novo algoritmo de escalonamento
        for (Processo processosAlgoritmo : processosAlgoritmos) {
            processosAlgoritmo.resetTempo_restante(processosAlgoritmo);
        }

        espera_media = (double) tempo_espera/(processosAlgoritmos.size());

        System.out.println("\n");
        for(Processo pTempo_espera : processosAlgoritmos){
            System.out.println("processo["+pTempo_espera.getProcesso()+"] tempo_espera = "+pTempo_espera.getTempo_espera());
        }

        System.out.println("\ntempo de espera_medio: "+  espera_media);
    }
}
