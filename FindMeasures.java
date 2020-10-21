import java.util.*;
public class FindMeasures {
	
	static Scanner read= new Scanner(System.in);

	Localidade localidade;

	public FindMeasures(Localidade localidade, int flag) {
		this.localidade = localidade;
		if(flag ==1) findSequence();
        if(flag ==2) findAllSequences();
	}

	public void findSequence() {
		BloomFilter b= new BloomFilter(100000,3);


        System.out.print("Qual o mes a estudar? [1-Janeiro <-------> 12-Dezembro]\n");
        System.out.print(">: ");
        int mes= read.nextInt();

        System.out.print("Qual o dia a estudar\n");
        System.out.print(">: ");
		int dia= read.nextInt();

        int[]tempDiarias = localidade.getTemp().getTempDiaria(mes-1, dia-1); //array com as temperaturas do dia
	
        System.out.print("Qual o tamanho das Sequencias a detetar?\n");
        System.out.print(">: ");
        int seqSize= read.nextInt();
        String garbage = read.nextLine(); //ficar com o \n resultante do ultimo input
         /*
        Inserir no bloom as sequencias de temperaturas do dia pretendido
        */
        insertInBloom(b,tempDiarias,seqSize);

        String[] ctrl; //controlo do input do utilizador
        String sequence;
        do{
        System.out.print("Qual a sequencia a detetar | Ex: \"23/24/25\"?\n");
        System.out.print(">: ");
        sequence= read.nextLine();
        ctrl = sequence.split("/");
        sequence = sequence +"/";
        }while(seqSize != ctrl.length);
        
        boolean check = checkIfBloom(b,sequence);

		System.out.printf("A sequencia %s\n", check ? "pertence." : "não pertence.");
		
        System.out.println("Pretende ver as mediçoes do dia escolhido? (1)Sim (2)Não");
        System.out.print(">: ");
		int op=read.nextInt();
		if(op==1){
			for(int i =0;i<tempDiarias.length;i++){
				System.out.print(tempDiarias[i]);
				System.out.print(" | ");
			}
			System.out.println("");
		}
		if(op==2) System.out.println("Retornando...");
    }

    public void findAllSequences(){
        //com o numero de hashfunctions em 2, diminui-se os falsos positivos
        BloomFilter b= new BloomFilter(100000,2);

        System.out.print("Qual o mes a estudar? [1-Janeiro <-------> 12-Dezembro]\n");
        System.out.print(">: ");
        int mes= read.nextInt();

        System.out.print("Qual o dia a estudar\n");
        System.out.print(">: ");
        int dia= read.nextInt();
        
        int[]tempDiarias = localidade.getTemp().getTempDiaria(mes-1, dia-1); //array com as temperaturas do dia
        insertAllSeqInBloom(b,tempDiarias);
        checkSeqInBloom(b,mes-1);

        System.out.println("\nPretende ver as mediçoes do dia escolhido? (1)Sim (2)Não");
        System.out.print(">: ");
		int op=read.nextInt();
		if(op==1){
			for(int i =0;i<tempDiarias.length;i++){
				System.out.print(tempDiarias[i]);
				System.out.print(" | ");
			}
			System.out.println("");
		}
		if(op==2) System.out.println("Retornando...");


    }
	public static void insertInBloom(BloomFilter b,int[] tempDiarias,int seqSize){
        /* 
        //string com as sequencias das temperaturas, de tamanho pedido ao utilizador
        */
        String splitString = GenerateSeq.Seq(tempDiarias, seqSize); 

        /*
        Passar as sequencias , para um vetor com as varias combinaçoes
        de sequencias, para se testar
        */
        String[] setInBloom = splitString.split(" ");

        /*
        colocar as sequencias no bloom Filter, para se 
        poder testar se as sequencias aconteceram ou nao.
        Tambem é feito o teste para apenas inserir sequencias do tamanho
        pedido, pois quando se divide um array em por exemplo 4, sobram sequencias de
        dimensão 2, que em nada interessam incluir no bloom, visto que o estudo está
        a ser feito sobre sequencias de dimensao 4
         (seqsize*3) pois cada numero e feito 
        */

        for(int i=0; i< setInBloom.length;i++){
            String[] m = setInBloom[i].split("/");
            if(m.length==seqSize){ 
                b.inserir(setInBloom[i]);  //teste para apenas meter as sequencias de tamanho pedido
            }
        }

    }

    public static boolean checkIfBloom(BloomFilter b, String seq){
       return b.pertence(seq);
    }

    public static void insertAllSeqInBloom(BloomFilter b,int[] tempDiarias){
        /*
        Como há 24 medições num dia, a dimensao da maior sequencia 
        será 24, que corresponde as medições de um dia inteiro serem todas
        iguais, fazendo assim uma sequencia de 24, que é altamente improvavel
        de acontecer, e na maior parte, sequencias de dimensoes maiores que 8 
        sao falsos positivos
        */

        for(int i = 2; i<=8;i++){
            // criar as sequencias de varias dimensoes//
            String splitString = GenerateSeq.Seq(tempDiarias, i); 

            //separar a sequencia pelos espaços
            String[] setInBloom = splitString.split(" ");

            //inserir as sequencias de varias dimensoes no bloom
            for(int k=0; k< setInBloom.length;k++){
                b.inserir(setInBloom[k]);
            }
        }
    }

    public static void checkSeqInBloom(BloomFilter b,int m){
        /* As temperaturas deste programa 
        estão definidas conforme a estação do ano, logo nao faz sentido
        calcular uma sequencia de temperaturas de 25 graus num mes do
        Inverno, visto que o programa nunca iria gerar essa sequencia, e só
        iria aumentar os nossos falsos positivos
        */

        //limites de sequencias
        int[] lim = new int[2];
        if(m==0||m==1||m==11){ lim[0] = 0; lim[1] = 15;} // inverno
		if(m==2||m==3||m==4){ lim[0] = 15; lim[1] = 23;} //primavera
		if(m==5||m==6||m==7){ lim[0] = 25; lim[1] = 35;}  //  veroa   
        if(m==8||m==9||m==10){ lim[0] = 19; lim[1] = 25;}// outono
        
        for(int tamanho = 2;tamanho<=8;tamanho++){
            for(int numero = lim[0];numero <=lim[1];numero++){
                //String com um numero repetido aka sequencia
                String teste = GenerateSeq.geraSeq(tamanho, numero);
                if(b.pertence(teste)){
                    /* A sequencia pertence, logo sera apresentada 
                    juntamente com todas as sequencias ocorridas*/
                    System.out.println("A Sequencia: "+ teste + " pertence");

                }
            }

        }
        System.out.println("");
        System.out.println("[Atenção que sequencias muito longas são geralmente falsos positivos]");
    }

}

