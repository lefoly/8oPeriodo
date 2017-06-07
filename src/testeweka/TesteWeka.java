/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testeweka;

import weka.classifiers.bayes.NaiveBayes;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

/**
 *
 * @author Leandro
 */
public class TesteWeka {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {

       DataSource ds = new DataSource("src/testeweka/vendas.arff");
       Instances ins = ds.getDataSet();
       //System.out.println(ins.toString());

       ins.setClassIndex(3);
       
       NaiveBayes nb = new NaiveBayes();
       nb.buildClassifier(ins);
       
       Instance novo = new DenseInstance(4);
       novo.setDataset(ins);
       novo.setValue(0, "M");
       novo.setValue(1, "20-39");
       novo.setValue(2, "Nao");
       
       double probabilidade[] = nb.distributionForInstance(novo);
       System.out.println("Sim: " + probabilidade[1]);
       System.out.println("Não: " + probabilidade[0]); 
    }
    
}
