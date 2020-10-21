import java.util.*;
public class TestMinhash{
    public static void main(String args[]){
        MinHash min = new MinHash(50);
        String a="18/1/1 19/2/1 19/3/1 18/4/1 19/5/1 20/6/1 21/7/1 22/8/1 17/9/1 19/10/1 18/11/1 20/12/1 15/13/1 19/14/1 19/15/1 16/16/1 17/17/1 18/18/1 21/19/1 19/20/1 22/21/1 22/22/1 21/23/1 20/24/1 ";
        String[] aa= a.split(" ");
         
        String b="5/1/1 9/2/1 19/3/1 12/4/1 19/5/1 30/6/1 21/7/1 22/8/1 23/9/1 19/10/1 1/11/1 20/12/1 15/13/1 19/13/1 1/15/1 15/16/1 17/17/1 12/18/1 21/19/1 19/20/1 22/21/1 22/22/1 21/23/1 20/24/1 ";
        String[] bb= b.split(" ");

        String teste1="10/1/1 13/2/1 14/3/1 18/4/1";
        String teste2="10/1/1 13/2/1 14/3/1 18/4/1";//-------------->São iguais!!!!
        String teste11[] = teste1.split(" ");
        String teste22[] = teste2.split(" ");

        Set<String> setPedida = min.CreateProfileSet(aa); //cria um "set" com as temperaturas(com etiquetas)
        Set<String> setPedida1 = min.CreateProfileSet(bb);

        Set<String> setTeste1 = min.CreateProfileSet(teste11); //cria um "set" com as temperaturas(com etiquetas)
        Set<String> setTeste2 = min.CreateProfileSet(teste22);

        double disteste1 = min.disJaccard(setPedida, setPedida1); // Obter a distancia de jaccard
        double disTeste2 =min.disJaccard(setTeste1, setTeste2);

        System.out.println("--------------------------------------------------------------");
        System.out.println("Primeiro teste");
        if(disteste1>0.7){
            System.out.println("\n\nAs temperaturas são muito semelhantes")    ;
            System.out.printf("Distancia de Jaccard %.2f\n", disteste1);
        }
        else{
            System.out.println("\nAs temperaturas são diferentes");
            System.out.printf("Distancia de Jaccard %.2f\n", disteste1);
        }
        System.out.println("--------------------------------------------------------------");
        System.out.println("Segundo teste\n");
        if(disTeste2==1){
            System.out.println("As temperaturas de teste sao iguais");//--------------> É suposto imprimir este pois sao 100% iguais
            System.out.printf("Distância de Jaccard: %f\n", disTeste2);
        }
        else{
            System.out.println("\n\nAs temperaturas sao diferentes");
            System.out.printf("Distancia de Jaccard %.2f\n", disTeste2);
        }
    }
}