import java.util.*;

public class Main {
		
	static Scanner read= new Scanner(System.in);
    
	public static void main(String[] args) {
		int op;   
        Localidade[] localidades = gerarTemps();
        try{
		do {
            System.out.println("\n----------Padrões Climáticos----------\n");
            System.out.println("(1) Procurar Sequencias de medições");
            System.out.println("(2) Procurar Dias com medições Similares");
            System.out.println("(3) Procurar locais com medições similares ");
            System.out.println("(4) Imprimir estatisticas");
            System.out.println("(5) Sair");
			System.out.print(">: ");
            op = read.nextInt();

            if(op==1){
                System.out.println("Escolha a cidade");
				System.out.println("(1)Aveiro   (2)Porto    (3)Leiria   (4)Lisboa   (5)Viseu  (6)Braga    (7)Coimbra  (8)Viana do Castelo");
				System.out.print(">: ");
                int cidade = read.nextInt();
                System.out.println("(1) Procurar uma sequencia especifica");
                System.out.println("(2) Procurar todas as sequencias que ocorreram");
                System.out.print(">: ");
                int flag = read.nextInt();
                if(flag == 1) new FindMeasures(localidades[cidade-1],flag);//cidade-1 pois o array começa no 0 e flag 1, pois e para procurar sequencias
                if(flag == 2) new FindMeasures(localidades[cidade-1],flag);


            }
            if(op==2){
                /* 
                Passar o array de todas as localidades pois a 
                procura de dias pode ser sobre todos os dias
                de todas as localidades
                */
                new SimilarMeasures(localidades);
			}
            if(op==3){
				System.out.println("Escolha as cidades, separando por vírgula | Ex: 2,6");
				System.out.println("(1)Aveiro   (2)Porto    (3)Leiria   (4)Lisboa   (5)Viseu  (6)Braga    (7)Coimbra  (8)Viana do Castelo");
				System.out.print(">: ");
				String[] cidades = read.next().split(","); //separar as cidades
				new SimilarMeasures(localidades[Integer.parseInt(cidades[0])-1],localidades[Integer.parseInt(cidades[1])-1]); // passar as duas cidades ao metodo correspondentee
				
            }
            if(op==4){
                System.out.println("(1) Imprimir dias de um mes");
                System.out.println("(2) Imprimir todos os dias de um mes");
                System.out.println("(3) Imprimir média diária de um mes");
                System.out.println("(4) Imprimir a média mensal do ano");
                System.out.print(">: ");        
                int a = read.nextInt();
                if(a == 1) printDays(localidades);
                if(a == 2) printAllDays(localidades);
                if(a == 3) printMeanMonth(localidades);
                if(a == 4) printMeanYear(localidades);
            }
            
        }while(op>=1 && op <=4 );

    }catch (Exception e){
        System.out.print("\nFoi apanhada uma excepção: ");
        System.out.print(e.getMessage());
        System.out.println("\nRecomeçando o programa");
        main(args);
    }
        		
}
    
    public static Localidade[] gerarTemps(){
        //
        //Criar um conjunto de localidades, todas elas com 
        //a sua temperatura anual caracteristica
        //
        String[] cidades= {"Aveiro", "Porto", "Leiria", "Lisboa", "Viseu", "Braga", "Coimbra","Viana do Castelo"};
        Localidade[] localidades = new Localidade[cidades.length];
        for(int i=0;i<cidades.length;i++){
            localidades[i] = new Localidade(cidades[i]);
        }
        return localidades;
    }

    public static void printDays(Localidade[] localidades){
        System.out.println("Escolha a cidade");
		System.out.println("(1)Aveiro   (2)Porto    (3)Leiria   (4)Lisboa   (5)Viseu  (6)Braga    (7)Coimbra  (8)Viana do Castelo");
		System.out.print(">: ");
        int cidade = read.nextInt();

        System.out.print("Qual o mes? [1-Janeiro <-------> 12-Dezembro]\n");
        System.out.print(">: ");
        int mes= read.nextInt();

        System.out.print("Qual o dia a imprimir\n");
        System.out.print(">: ");
        int dia= read.nextInt();
        
        int[] tempsDiarias = localidades[cidade-1].getTemp().getTempDiaria(mes-1, dia-1);
        for(int i = 0;i<tempsDiarias.length;i++){
            System.out.print(tempsDiarias[i] + " | ");
        }
        System.out.println("");
    }
    public static void printMeanMonth(Localidade[] localidades){
        System.out.println("Escolha a cidade");
		System.out.println("(1)Aveiro   (2)Porto    (3)Leiria   (4)Lisboa   (5)Viseu  (6)Braga    (7)Coimbra  (8)Viana do Castelo");
		System.out.print(">: ");
        int cidade = read.nextInt();

        System.out.print("Qual o mes? [1-Janeiro <-------> 12-Dezembro]\n");
        System.out.print(">: ");
        int mes= read.nextInt();
        
        int[] tempsMensais = localidades[cidade-1].getTemp().getMedia(mes-1);
        for(int i = 0;i<tempsMensais.length;i++){
            System.out.print(tempsMensais[i] + " | ");
        }
        System.out.println("");
    }

    public static void printAllDays(Localidade[] localidades){
        System.out.println("Escolha a cidade");
		System.out.println("(1)Aveiro   (2)Porto    (3)Leiria   (4)Lisboa   (5)Viseu  (6)Braga    (7)Coimbra  (8)Viana do Castelo");
		System.out.print(">: ");
        int cidade = read.nextInt();

        System.out.print("Qual o mes? [1-Janeiro <-------> 12-Dezembro]\n");
        System.out.print(">: ");
        int mes= read.nextInt();

        int[] temps;

        System.out.println("Mes: "+mes+"\n");
        for(int dia=0;dia<31;dia++){
            System.out.printf("Temperaturas do dia %2d: ",(dia+1));
            temps = localidades[cidade-1].getTemp().getTempDiaria(mes-1,dia);
            for(Integer i : temps)System.out.printf("|%2d|",i);
            System.out.println("");

        }
    }
    public static void printMeanYear(Localidade[] localidades){
        int[] tempsAnuais = new int[12];
		int[] temps_med_mes;
        int soma;
        System.out.println("Escolha a cidade");
		System.out.println("(1)Aveiro   (2)Porto    (3)Leiria   (4)Lisboa   (5)Viseu  (6)Braga    (7)Coimbra  (8)Viana do Castelo");
		System.out.print(">: ");
        int cidade = read.nextInt();
		for(int mes = 0; mes<12;mes++){
			temps_med_mes = localidades[cidade-1].getTemp().getMedia(mes); //media das temperaturas diarias desse mes
			soma = 0;
			for(int dia=0;dia<31;dia++){
				soma += temps_med_mes[dia];
			}
			tempsAnuais[mes] = (soma/31);
         }
         
         System.out.println("\nMédia Anual da localidade ["+localidades[cidade-1].getName()+"]\n");
         for(int i=0;i<tempsAnuais.length;i++){
             System.out.printf("Mes %d tem temperatura média %2d\n",i+1,tempsAnuais[i] );
            }

    }
}
