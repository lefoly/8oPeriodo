/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testeweka;

import java.util.ArrayList;
import java.util.List;
import weka.associations.Apriori;
import weka.associations.AssociationRule;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

/**
 *
 * @author Leandro
 */
public class TesteWeka2 {

    public static void main(String[] args) throws Exception {

        DataSource ds = new DataSource("src/testeweka/produtos.arff");
        Instances ins = ds.getDataSet();
        //System.out.println(ins.toString());

        ins.setClassIndex(ins.numAttributes() - 1);

        // build associator
        Apriori apriori = new Apriori();
        apriori.setClassIndex(ins.classIndex());
        apriori.buildAssociations(ins);

        // output associator
        //System.out.println(apriori);
        /*System.out.println();
        System.out.println("------------------------");
        System.out.println(apriori.getAssociationRules().getRules().get(0));

        List<AssociationRule> lista = apriori.getAssociationRules().getRules();
        System.out.println(lista.get(0).getPremise());
        System.out.println(lista.get(0).getConsequence());

        System.out.println(apriori.getNumRules());
         */
        //System.out.println(ins.attribute(0).value(0));
        //System.out.println(ins.attribute(0).value(1));
        ArrayList<Atributo> atributos = new ArrayList();

        for (int i = 0; i < ins.numAttributes(); i++) {
            Atributo a = new Atributo();
            a.setNome(ins.attribute(i).name());
            ArrayList<String> valores = new ArrayList();
            for (int j = 0; j < ins.attribute(i).numValues(); j++) {
                valores.add(ins.attribute(i).value(j));
            }
            a.setValores(valores);
            atributos.add(a);
        }

        for (Atributo a : atributos) {
            System.out.println(a);
        }

    }

}
