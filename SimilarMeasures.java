import java.util.*;

public class SimilarMeasures{
    Localidade l1;
    Localidade l2;
    Localidade[] localidades; //caso seja para comparar dias mais exaustivamente, por exemplo todos os dias de todas as localidades

    static Scanner read = new Scanner(System.in);
    public SimilarMeasures(Localidade l1, Localidade l2){
        this.l1 = l1;
        this.l2 = l2;
        compareTwoSites();

    }
    public SimilarMeasures(Localidade[] localidades){
        this.localidades = localidades;
        findSimilarDays();
    }
    public void findSimilarDays(){
        System.out.println("(1) Procurar sobre todos os dias do ano");
        System.out.println("(2) Procurar sobre dias de um determinado mês");
        System.out.print(">: ");
        int op = read.nextInt();
        
        if(op == 1) findAllSimilarDays();
        if(op == 2) findSpecificSimilarity();


    }
    public void compareTwoSites(){
        System.out.println("(1) Medições diárias");
        System.out.println("(2) Medições mensais");
        System.out.println("(3) Medições anuais");
        System.out.print(">: ");
        int op=read.nextInt();

        if(op==1) SimilarDaysSites();
        if(op==2) SimilarMonthsSites();
        if(op==3) SimilarYear();

    }

    public void findSpecificSimilarity(){
        MinHash min = new MinHash(50);
        System.out.println("Escolha a cidade");
            System.out.println("(1)Aveiro   (2)Porto    (3)Leiria   (4)Lisboa   (5)Viseu  (6)Braga    (7)Coimbra  (8)Viana do Castelo");
            System.out.print(">: ");
            int cidade = read.nextInt();
            
            System.out.print("Qual o mes a estudar? [1-Janeiro <-------> 12-Dezembro]\n");
            System.out.print(">: ");
            int mes= read.nextInt();

            System.out.print("Qual o dia a estudar\n");
            System.out.print(">: ");
            int dia= read.nextInt();
            String garbage = read.nextLine(); //colect the "\n"

            System.out.println("Qual a Semelhança de Jaccard?  Ex: 50%");
            System.out.print(">: ");
            String t = read.nextLine();
            t=t.replaceAll("%","");
            double threshold = Double.parseDouble(t)/100;  //passar a percentagem a decimal

            Set<String> perfildiario1 = CreateProfileSet(localidades[cidade-1].getTemp().perfilDiario(mes-1, dia-1));
            List<Integer> diasSimilares = new ArrayList<Integer>(); //array com os perfis similares

            /* Percorrer todos os dias do mes escolhido
               e verificar a distancia de jaccard desse dia 
               com o dia em estudo e guardar num array caso
               sejam semelhantes
            */
            for(dia = 0; dia<31;dia++){
                Set<String> perfildiario2 = CreateProfileSet(localidades[cidade-1].getTemp().perfilDiario(mes-1, dia));
                double jacSim = min.disJaccard(perfildiario1, perfildiario2);
                //caso a jacSim for 1, quer dizer que e o msm dia 
                if(jacSim >= threshold){ 
                    diasSimilares.add(dia);
                }
            }
            System.out.println("Os dias do mês similares ao escolhido foram: ");
            for(Integer d : diasSimilares){
                //d+1 pois o array dos dias começa no 0
                System.out.print((d+1)+",");
            }
            System.out.println("");
            System.out.println("Pretende imprimir todos os dias similares? (1)Sim / (2)Não");
            int op = read.nextInt();
            if(op==1){
                for(Integer d : diasSimilares){
                    int[] dias = localidades[cidade-1].getTemp().getTempDiaria(mes-1, d);
                    for(int c = 0;c<dias.length;c++){
                        System.out.printf("%3d|",dias[c]);
                    } 
                    System.out.println("");               
                }
            }
            if(op==2) System.out.println("Retornando...");
            
    }

    public void findAllSimilarDays(){
        MinHash min = new MinHash(50);
        System.out.println("Escolha a cidade");
        System.out.println("(1)Aveiro   (2)Porto    (3)Leiria   (4)Lisboa   (5)Viseu  (6)Braga    (7)Coimbra  (8)Viana do Castelo");
        System.out.print(">: ");
        int cidade = read.nextInt();
        
        System.out.print("Qual o mes a estudar? [1-Janeiro <-------> 12-Dezembro]\n");
        System.out.print(">: ");
        int mes= read.nextInt();

        System.out.print("Qual o dia a estudar\n");
        System.out.print(">: ");
        int dia= read.nextInt();
        String garbage = read.nextLine(); //colect the "\n"

        System.out.println("Qual a Semelhança de Jaccard?  Ex: 50%");
        System.out.print(">: ");
        String t = read.nextLine();
        t=t.replaceAll("%","");
        double threshold = Double.parseDouble(t)/100;  //passar a percentagem a decimal

        Set<String> perfildiario1 = SimilarMeasures.CreateProfileSet(localidades[cidade-1].getTemp().perfilDiario(mes-1,dia-1));
        List<ArrayList<Integer>> diasSimilares = new ArrayList<>(); //array com os perfis similares

        for(int i = 0;i<12;i++)diasSimilares.add(new ArrayList<Integer>()); //iniciallizar o array de listas

        for(mes=0;mes<12;mes++){
          for(dia=0;dia<31;dia++){
            Set<String> perfildiario2 = SimilarMeasures.CreateProfileSet(localidades[cidade-1].getTemp().perfilDiario(mes,dia));
                double jacSim = min.disJaccard(perfildiario1, perfildiario2);
                //caso a jacSim for 1, quer dizer que e o msm dia 
                if(jacSim >= threshold){ 
                    diasSimilares.get(mes).add(dia);
                }
            }
          }
          int b = 1;
          for(ArrayList<Integer> a : diasSimilares){
            if(a.size()>0){ //so imprimir os meses com os dias similares
            System.out.println("Mes: "+b);
            System.out.print("Dia: ");
            for(Integer i : a){
              System.out.print((i+1)+","); 
            }
            System.out.println("\n------------------------------");
          }
          b++;
          }

    }

    public static Set<String> CreateProfileSet(String[] perfil) { // split de todas as temperaturas
		/* Cria um set de acordo com o perfil dado
		que tanto pode ser diário como 
		mensal */
		Set<String> set = new TreeSet<>();
		
		for(int i = 0; i< perfil.length; i++) {
			set.add(perfil[i]);
		}
		return set;
	}

    public void SimilarDaysSites(){
        MinHash min = new MinHash(50);
        System.out.print("Qual o mes a estudar? [1-Janeiro <-------> 12-Dezembro]\n");
        System.out.print(">: ");
        int mes= read.nextInt();

        System.out.print("Qual o dia a estudar\n");
        System.out.print(">: ");
        int dia= read.nextInt();
        String garbage = read.nextLine(); //colect the "\n"

        System.out.println("Qual a Semelhança de Jaccard?  Ex: 50%");
        System.out.print(">: ");
        String t = read.nextLine();
        t=t.replaceAll("%","");
        double threshold = Double.parseDouble(t)/100;  //passar a percentagem a decimal

        Set<String> perfildiario1 = CreateProfileSet(l1.getTemp().perfilDiario(mes-1, dia-1)); //perfil diario da primeira localidade para comparar
        List<ArrayList<Integer>> diasSimilares = new ArrayList<>(); //array com os perfis diarios da segunda localidade que vao ser guardados 

        for(int i = 0;i<12;i++)diasSimilares.add(new ArrayList<Integer>()); //iniciallizar o array de listas

        boolean flag = false; //Apenas uma variavel de controlo, para assegurarmos que so imprimimos caso haja dias similares
        for(mes=0;mes<12;mes++){
          for(dia=0;dia<31;dia++){
            Set<String> perfildiario2 = SimilarMeasures.CreateProfileSet(l2.getTemp().perfilDiario(mes,dia));
                double jacSim = min.disJaccard(perfildiario1, perfildiario2);
                //caso a jacSim for 1, quer dizer que e o msm dia 
                if(jacSim >= threshold){ 
                    diasSimilares.get(mes).add(dia);
                    flag = true;
                }
            }
          }

          if(flag){ //apenas se imprime caso se encontre pelo menos 1 dia similar
          int b = 1;
          System.out.println(l2.getName() + " tem os seguintes dias semelhantes a "+l1.getName());
          System.out.println("");
          for(ArrayList<Integer> a : diasSimilares){
            if(a.size()>0){ //so imprimir os meses com os dias similares
            System.out.println("Mes: "+b);
            System.out.print("Dia: ");
            for(Integer i : a){
              System.out.print((i+1)+","); 
            }
            System.out.println("\n---------------------------------------");
          }
          b++;
          }
          System.out.println("");
        }else{
            System.out.println("Não há dias similares");
        }
    }

    public void SimilarMonthsSites(){
        MinHash min = new MinHash(50);
        System.out.print("Qual o mes a estudar? [1-Janeiro <-------> 12-Dezembro]\n");
        System.out.print(">: ");
        int mesE= read.nextInt();
        String garbage = read.nextLine();

        System.out.println("Qual a Semelhança de Jaccard?  Ex: 50%");
        System.out.print(">: ");
        String t = read.nextLine();
        t=t.replaceAll("%","");
        double threshold = Double.parseDouble(t)/100;  //passar a percentagem a decimal

        Set<String> perfilMensal1 = CreateProfileSet(l1.getTemp().perfilMensal(mesE-1)); //perfil mensal da primeira localidade para comparar
        List<Integer> MesesSimilares = new ArrayList<Integer>(); //array com os perfis diarios da segunda localidade que vao ser guardados 

        for(int mes=0;mes<12;mes++){
            Set<String> perfilMensal2 = CreateProfileSet(l2.getTemp().perfilMensal(mes));
            double jacSim = min.disJaccard(perfilMensal1, perfilMensal2);
            //caso a jacSim for 1, quer dizer que e o msm dia 
            if(jacSim >= threshold){ 
                MesesSimilares.add(mes);
                }
            }

        
            //apenas imprime se houver meses semelhantes na lista
          if(MesesSimilares.size() != 0){
            System.out.println(l2.getName() + " tem os seguintes meses semelhantes a "+l1.getName());
            System.out.print("Mes: ");
                for(int m : MesesSimilares){
                System.out.print((m+1)+","); //array dos meses começa em 0
                }
            }else{
                System.out.println("Não há meses semelhantes");
            }
        System.out.println("\nPretende imprimir os perfis mensais? (1) Sim | (2) Não");
        System.out.print(">: ");
        int op = read.nextInt();
        System.out.println("");
        if(op==1){
            System.out.println("Os arrays começam em 0, logo, [0-Primeiro Dia <-------> 30-Ultimo Dia]");
            String[] perfilmensal1 = l1.getTemp().perfilMensal(mesE-1);
            System.out.print(l1.getName()+": ");
            for(String s : perfilmensal1) System.out.print(s + "| ");
            
            System.out.println("");
            for(int m : MesesSimilares){
                System.out.print(l2.getName()+": ");
                String[] perfilmensal2 = l2.getTemp().perfilMensal(m);
                for(String s2 : perfilmensal2) System.out.print(s2 + "| ");
                System.out.println("");
            }
        }else{
        System.out.println("\nRetornando");
        System.out.println("");
        }
    }

    public void SimilarYear(){
        MinHash min = new MinHash(70);
        Localidade[] localidades = Main.gerarTemps();
        String garbage = read.nextLine(); // \n que vem antes de chamar esta função
        System.out.println("Qual a Semelhança de Jaccard?  Ex: 50%");
            System.out.print(">: ");
            String t = read.nextLine();
            t=t.replaceAll("%","");
            double threshold = Double.parseDouble(t)/100;  //passar a percentagem a decimal
    
            Set<String> perfilAnual1 = CreateProfileSet(l1.getTemp().perfilAnual());      
            Set<String> perfilAnual2 = CreateProfileSet(l2.getTemp().perfilAnual());
            double jacSim = min.disJaccard(perfilAnual1, perfilAnual2);
            if(jacSim >= threshold){ 
                System.out.println("O Perfil anual da localidade [" + l1.getName() + "] é similar ao perfil anual da localidade [" + l2.getName()+"]");
              }else{
                  System.out.println("Os Perfis anuais não sao similares");
              }

        System.out.println("Pretende imprimir os dois perfis anuais? (1) Sim | (2) Não");
        System.out.print(">:");
        int op = read.nextInt();
        System.out.println("");
        if(op==1){
            System.out.println("Os arrays começam em 0, logo, [0-Janeiro <-------> 11-Dezembro]");
            String[] perfilAno1 = l1.getTemp().perfilAnual();  
            String[] perfilAno2 = l2.getTemp().perfilAnual();
            System.out.print(l1.getName()+": ");
            for(String s : perfilAno1) System.out.print(s + " | ");
            System.out.println("");
            System.out.print(l2.getName()+": ");
            for(String s2 : perfilAno2) System.out.print(s2 + " | ");
        }else{
        System.out.println("\nRetornando");
        System.out.println("");
        }
    }

}