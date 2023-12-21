package br.edu.ifrs.restinga;

import java.util.ArrayList;
import java.util.Random;

public class Processo {
    private int processo;
    private int tempo_chegada;
    private int tempo_execucao;
    private int tempo_restante;
    private int prioridade;
    private int tempo_espera;

    public static ArrayList<Processo> processList = new ArrayList();

    public Processo(){

    }

    //input usuario
    public Processo(int processo, int tempo_execucao, int tempo_chegada, int prioridade){
        this.processo = processo;
        this.tempo_chegada = tempo_chegada;
        this.tempo_execucao = tempo_execucao;
        this.tempo_restante = tempo_execucao;
        this.prioridade = prioridade;
        this.tempo_espera = 0;

        processList.add(this);
    }


    //input random
    public Processo(int processo){
        Random rand = new Random();
        this.processo = processo;
        this.tempo_chegada = rand.nextInt(10) + 1;
        this.tempo_execucao = rand.nextInt(10) + 1;
        this.tempo_restante = tempo_execucao;
        this.prioridade = rand.nextInt(10) + 1;
        this.tempo_espera = 0;

        processList.add(this);
    }

    //método que popula o array de processos da classe Escalonamentos
    public ArrayList<Processo> escalonamento(){

        ArrayList<Processo> processo = new ArrayList<>();

        for (Processo value : processList) {
            Processo p = new Processo();
            p.setProcesso(value.getProcesso());
            p.setPrioridade(value.getPrioridade());
            p.setTempo_chegada(value.getTempo_chegada());
            p.setTempo_execucao(value.getTempo_execucao());
            p.setTempo_restante(value.getTempo_restante());

            processo.add(p);
        }
        return processo;
    }

    //procura o processo que irá chegar primeiro ao processador
    public Processo buscaTempo_chegada() {
        Processo menorTempoChegada = new Processo();
        menorTempoChegada.setTempo_chegada(999);

        for (int i = 0; i < processList.size(); i++) {
            if(processList.get(i).getTempo_chegada() < menorTempoChegada.getTempo_chegada()){
                if(processList.get(i).getTempo_restante() > 0) {
                    menorTempoChegada = processList.get(i);
                }
            }
        }
        return menorTempoChegada;
    }

    public void attTempo_restante(Processo processo){
        for(int i = 0; i < processList.size(); i++){
            if(processList.get(i).equals(processo)){
                processList.get(i).setTempo_restante(processo.getTempo_restante());
            }
        }
    }

    public void resetTempo_restante(Processo processo){
        for(int i = 0; i < processList.size(); i++){
            if(processList.get(i).getProcesso() == processo.getProcesso()){
                processList.get(i).setTempo_restante(processo.getTempo_execucao());
            }
        }
    }

    public Processo buscaProcesso_execucao(){
        Processo menorTempo_execucao = new Processo();
        menorTempo_execucao.setTempo_execucao(999);
        for (int i = 0; i < processList.size(); i++) {
            if(processList.get(i).getTempo_execucao() < menorTempo_execucao.getTempo_execucao()){
                if(processList.get(i).getTempo_restante() > 0) {
                    menorTempo_execucao = processList.get(i);
                }
            }
        }

        return menorTempo_execucao;
    }

    public boolean mostraTempo_execucaoVazio(Processo processo, int tempoExecucaoProcesso){
        if(processo.getTempo_chegada() <= tempoExecucaoProcesso){
            return true;
        } else {
            return false;
        }
    }

    public Processo buscaProcesso_prioridade(int tempoExecucao){
        Processo maiorPrioridade = new Processo();
        maiorPrioridade.setPrioridade(999);
        for (int i = 0; i < processList.size(); i++) {
            if(processList.get(i).getPrioridade() < maiorPrioridade.getPrioridade()){
                if(processList.get(i).getTempo_chegada() <= tempoExecucao) {
                    if (processList.get(i).getTempo_restante() > 0) {
                        maiorPrioridade = processList.get(i);
                    }
                }
            }
        }
        if(maiorPrioridade.getPrioridade() == 999){
            maiorPrioridade.setTempo_restante(0);
            return maiorPrioridade;

        } else {
            return maiorPrioridade;
        }
    }

    public int getProcesso() {
        return processo;
    }

    public void setProcesso(int processo) {
        this.processo = processo;
    }

    public int getTempo_chegada() {
        return tempo_chegada;
    }

    public void setTempo_chegada(int tempo_chegada) {
        this.tempo_chegada = tempo_chegada;
    }

    public int getTempo_execucao() {
        return tempo_execucao;
    }

    public void setTempo_execucao(int tempo_execucao) {
        this.tempo_execucao = tempo_execucao;
    }

    public int getTempo_restante() {
        return tempo_restante;
    }

    public void setTempo_restante(int tempo_restante) {
        this.tempo_restante = tempo_restante;
    }

    public int getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(int prioridade) {
        this.prioridade = prioridade;
    }

    public int getTempo_espera() {
        return tempo_espera;
    }

    public void setTempo_espera(int tempo_espera) {
        this.tempo_espera = tempo_espera;
    }

}