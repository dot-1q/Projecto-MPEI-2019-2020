import java.util.*;


public class TestBloomFilter{
    public static void main (String args[]){


        BloomFilter b= new BloomFilter(100000,3);

        String temperaturas[] = {"12","13" ,"14", "15", "16" ,"17" ,"18" ,"15", "15", "15", "14", "13", "12", "17", "18" ,"17", "17", "17", "13", "17", "18" ,"19", "12" ,"11","14"};
        //para sequencias de 3 temperaturas(n=3)
        int n=3; 
        String r="";
        for(int j=0; j<n; j++) {//cria as sequencias todas possiveis com 3 valores!
            for(int i=0; i<temperaturas.length; i++) {
        
                if(i%n==0) {
                    r+= " ";	
                }
                if(i+j<temperaturas.length) r+=temperaturas[i+j]+"/";
            }
        }
        System.out.printf("%s\n", r);//---------->Imprime a string
        String splitada[]=r.split(" ");

        for(int i=0; i<splitada.length; i++){//Insere todas as sequencias possiveis no bloom
            String[] m = splitada[i].split("/");
            if(m.length == n){  // so inserimos  as sequencias de tamanho pedido, pois ha alturas em que a dividir o array, se cria sequencias menores
                b.inserir(splitada[i]);
            }
        }
        //varios testes para ver se temperaturas pertencem 
        if(b.pertence("12/13/14/")) System.out.println("pertence");// tem de imprimir esta mensagem
        else System.out.println("Nao pertence");

        if(b.pertence("13/14/15/")) System.out.println("pertence");// tem de imprimir esta mensagem
        else System.out.println("Nao pertence");

        if(b.pertence("12/13/14/")) System.out.println("pertence");// tem de imprimir esta mensagem
        else System.out.println("Nao pertence");

        if(b.pertence("19/12/11/")) System.out.println("pertence");// tem de imprimir esta mensagem
        else System.out.println("Nao pertence");

        if(b.pertence("12/11/15/")) System.out.println("pertence");
        else System.out.println("Nao pertence");// tem de imprimir esta mensagem
        if(b.pertence("11/14/")) System.out.println("pertence");
        else System.out.println("Nao pertence");// tem de imprimir esta mensagem pois apesar de se gerar a sequencia "11/14/", esta não e introduzida
        

    }
    
}