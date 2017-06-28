/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classificacao2;

import weka.associations.Apriori;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

/**
 *
 * @author root
 */
public class TesteWeka3 {

    public static void main(String[] args) throws Exception {

        DataSource ds = new DataSource("src/classificacao2/Respostas-28-07.arff");
        Instances data = ds.getDataSet();
        //System.out.println(data.toString());

        data.setClassIndex(data.numAttributes() - 1);

        Apriori apriori = new Apriori();
        apriori.setClassIndex(data.classIndex());
        apriori.buildAssociations(data);

        System.out.println(apriori);
        
        data.deleteAttributeAt(0);
        data.deleteAttributeAt(1);
        data.deleteAttributeAt(2);
        data.deleteAttributeAt(3);
        data.deleteAttributeAt(4);
        System.out.println(data.numAttributes());

        data.setClassIndex(data.numAttributes() - 1);

        Apriori apriori2 = new Apriori();
        apriori2.setClassIndex(data.classIndex());
        apriori2.buildAssociations(data);

        System.out.println(apriori2);
        
        //System.out.println(apriori.getNumRules());
        //System.out.println(apriori.getAssociationRules().getRules().get(0));

        /*List<AssociationRule> lista = apriori.getAssociationRules().getRules();
        System.out.println(lista.get(0).getPremise());
        System.out.println(lista.get(0).getConsequence());
        Collections.sort(lista);

        for (AssociationRule a : lista) {
            System.out.println(a);
        }*/

        /*ArrayList<Atributo> atributos = new ArrayList();

        for (int i = 0; i < data.numAttributes(); i++) {
            Atributo a = new Atributo();
            a.setNome(data.attribute(i).name());
            ArrayList<String> valores = new ArrayList();
            for (int j = 0; j < data.attribute(i).numValues(); j++) {
                valores.add(data.attribute(i).value(j));
            }
            a.setValores(valores);
            atributos.add(a);
        }

        for (Atributo a : atributos) {
            System.out.println(a.getNome() + ": " + a.getValores());
        }*/
        
        
    }


}
