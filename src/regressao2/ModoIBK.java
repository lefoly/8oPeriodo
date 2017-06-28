/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package regressao2;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.JOptionPane;
import weka.classifiers.Evaluation;
import weka.classifiers.lazy.IBk;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
;
import weka.core.converters.ConverterUtils.DataSource;

/**
 *
 * @author lefoly
 */


public class ModoIBK {

    //método para treinar o algoritmo
    public void treinar(String localArquivo) {

        try {

            //carrega o arquivo csv com os dados de treinamento e coloca no objeto instancias
            DataSource source = new DataSource(localArquivo);

            Instances instancias = source.getDataSet();

            //deleta a primeira coluna da base de dados.(Geralmente é um id ou codigo identificador)
            instancias.deleteAttributeAt(0);

            //ativa a ultima coluna como a classe à ser classificada
            if (instancias.classIndex() == -1) {
                instancias.setClassIndex(instancias.numAttributes() - 1);
            }

            //testa se existem mais de 1 classe
            if (instancias.numClasses() <= 1) {

                JOptionPane.showMessageDialog(null, "Erro. Atenção a última coluna com a classe precisa ter ao menos 2 classes distintas.");

            } else {

                //cria o objeto arvore que será a instancia do classificador ibk
                IBk arvore = new IBk(3);

                //treina o classificador com as instancias carregadas
                arvore.buildClassifier(instancias);

                //salva o algoritmo classificador em um arquivo na pasta raiz do nosso programa
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("classificador-IBK.nnet"));

                oos.writeObject(arvore);

                oos.close();

                //classe para avaliar o modelo aprendido
                Evaluation eval = new Evaluation(instancias);

                eval.evaluateModel(arvore, instancias);

                            String texto = "";

            //for (int c = 0; c < arvore.getNumTraining(); c++) {
            //    texto += arvore.get + " foram classificadas como '" + instancias.classAttribute().value(c) + "'\n";
           // }
                System.out.println("eval.confusionMatrix(): "  + eval.confusionMatrix()[0][0]);
                System.out.println("eval.confusionMatrix(): "  + eval.confusionMatrix()[1][1]);
                System.out.println("eval.toSummaryString(): " + eval.toSummaryString());
                
                System.out.println("eval.getRevision(): " + eval.getRevision());
                System.out.println("arvore.getNumTraining(): " + arvore.getNumTraining());
                //exibe um resumo do modelo aprendido
                JOptionPane.showMessageDialog(null, "Resumo do Treinamento\n de " + instancias.numInstances() + " instâncias.\n" + eval.getRevision() + "\n\n" + arvore.toString());
            }

        } catch (Exception erro) {
            erro.printStackTrace();
            JOptionPane.showMessageDialog(null, "Ocorreu um erro!");
        }

    }

    public void gravarArquivo(String localArquivo) {
        try {

            //lê o arquivo csv dos dados
            FileReader ler = new FileReader(localArquivo);
            BufferedReader br = new BufferedReader(ler);

            String nomeArquivo = "Base_Previsao.csv";

            //cria o arquivo de destino já com os dados classificados ou previstos
            File arquivoDestinatario = new File(System.getProperty("user.dir"), nomeArquivo);

            FileWriter escrever = new FileWriter(arquivoDestinatario, false);
            BufferedWriter bw = new BufferedWriter(escrever);

            //lê o arquivo do classificador salvo durante o processo de treinamento
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("classificador-IBK.nnet"));

            IBk arvore = (IBk) ois.readObject();

            //lê as instancias novas, os dados que serão classificados.
            DataSource source = new DataSource(localArquivo);

            Instances instancias = source.getDataSet();

            //deleta a primeira coluna que é o codigo ou id
            instancias.deleteAttributeAt(0);

            //seta a última coluna como a classe
            if (instancias.classIndex() == -1) {
                instancias.setClassIndex(instancias.numAttributes() - 1);
            }

            //lê a primeira linha da base que são os rórulos da nossa base de dados ou seu cabeçalho
            String rotulos = br.readLine() + ";ESTIMATIVA";

            //grava os rótulos no arquivo que será gerado, acrescido da ultima coluna 'ESTIMATIVA', que será a nossa previsão
            bw.write(rotulos);

            //cria um vetor inteiro para contar as quantidades classificadas de cada classe
            int[] conta = new int[instancias.numClasses()];

            //zera o vetor contador
            for (int a = 0; a < instancias.numClasses(); a++) {
                conta[a] = 0;
            }

            //percorre todas as instancias novas classificando cada uma com o classificador arvore
            for (int i = 0; i < instancias.numInstances(); i++) {

                String linha = "";

                Instance instancia = instancias.instance(i);

                //classe recebe um valor inteiro que é o resuldado da classificação            
                int classe = (int) arvore.classifyInstance(instancia);

                //pega o nome do valor correspondente a classe classificada e colocqa em predClasse
                Attribute a = instancias.attribute(instancias.numAttributes() - 1);

                String predClasse = a.value((int) classe);

                //lê a proxima linha da base csv
                linha = br.readLine();

                //linha é acrescido da classe prevista predClasse que ficará na última coluna 'ESTIMATIVA'
                linha += "," + predClasse;

                //grava a linha no arquivo que será salvo
                bw.newLine();
                bw.write(linha);

                //conta mais 1 de acordo com a classe prevista
                conta[classe] += 1;
            }
            
            //fecha os arquivos
            ler.close();
            br.close();

            bw.flush();
            escrever.close();
            bw.close();

            //formata e exibe a mensagem do relatório da tarefa de classificação
            String texto = "";

            for (int c = 0; c < conta.length; c++) {
                texto += conta[c] + " foram classificadas como '" + instancias.classAttribute().value(c) + "'\n";
            }

            JOptionPane.showMessageDialog(null, "Resumo da Atividade\n\n De " + instancias.numInstances() + " instâncias:\n\n" + texto + "\n\n" + arvore.toString());

            //abre automaticamente o arquivo csv gerado após concluido o salvamento
            Desktop desktop = Desktop.getDesktop();
            desktop.open(arquivoDestinatario);

        } catch (OutOfMemoryError erro) {
            JOptionPane.showMessageDialog(null, "Não existe memória disponível para executar esta tarefa!\n Tente com menos dados.");
        } catch (Exception erro) {
            erro.printStackTrace();
            JOptionPane.showMessageDialog(null, "Ocorreu um Erro!");
        }

    }

}
