/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classificacao;

import java.util.ArrayList;

/**
 *
 * @author root
 */
public class Atributo {
    
   private String nome;
   private ArrayList<String> valores;

    public Atributo() {
    }

    public Atributo(String nome, ArrayList<String> valores) {
        this.nome = nome;
        this.valores = valores;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ArrayList<String> getValores() {
        return valores;
    }

    public void setValores(ArrayList<String> valores) {
        this.valores = valores;
    }

    @Override
    public String toString() {
        String valores = "";
        for (String valor : this.valores) {
            valores = valores + valor + " | ";
        }            
        return "Atributo{ nome=" + nome + " | Valores: " + valores + "}";
    }
  
   
    
}
