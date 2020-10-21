import java.util.*;

public class Temperaturas {//falta fazer o caso dos anos!
	private int[][][] temperaturas;
	
		public Temperaturas(){
		this.temperaturas = new int [12][31][24]; //array com todos os dias do ano, cada dia com 24 temperaturas de hora a hora
		for(int m=0;m<12;m++){
			for(int d = 0;d<31;d++){
				for(int h = 0;h<24;h++){
					if(m==0||m==1||m==11) temperaturas[m][d][h]=(int)(Math.random()*15); //temperaturaseraturas entre 0 e 15 // inverno
					if(m==2||m==3||m==4) temperaturas[m][d][h]=(int)(Math.random()*(23-15)+15); //temperaturaseraturas entre 15 e 23 //primavera
					if(m==5||m==6||m==7) temperaturas[m][d][h]=(int)(Math.random()*(35-25)+25); //temperaturaseraturas entre 35 e 25 //  verao   
					if(m==8||m==9||m==10) temperaturas[m][d][h]=(int)(Math.random()*(25-19)+19);//temperaturaseraturas entre 19 e 25 // outono
					
				}
			}
		}
		makeTempsRealistic(); 
	 }

	 public int[][][] getTemperaturas(){
		 return this.temperaturas;
	 }

	 public String[][][] getTemperaturasString(){
		 //retornar o array de temperaturas em String
		String[][][] temps = new String[12][31][24];
		for(int m=0;m<12;m++){
			for(int d = 0;d<31;d++){
				for(int h = 0;h<24;h++){
					temps[m][d][h]=Integer.toString(this.temperaturas[m][d][h]); //temperaturaseraturas entre 0 e 15 // inverno
				}
			}
		}
		return temps; 
	 }

	 public int[] getTempDiaria(int mes,int dia){
		 int[] temps = new int[24];
		 for(int h = 0;h<24;h++){
			temps[h]=this.temperaturas[mes][dia][h]; //retornar temperaturas diarias
		}
		return temps;
	 }

	 public String[] perfilDiario(int mes,int dia){
		//  retorna o perfil diario de temperaturas
		//  composto pela temperatura e a hora em que foi registada
		//  ex: "22,14" corresponde a temperatura 22 registada as 14h. importante para minHash

		 String[] perfil = new String[24];
		 for(int h=0;h<24;h++){
			 perfil[h] =this.temperaturas[mes][dia][h]+","+h;
		 }
		 return perfil;
	 }


	 public String[] perfilMensal(int mes){
		//  retorna o perfil mensal de temperaturas
		//  composto pelo dia e a sua temperatura media 
		//  ex: "22,14" corresponde a ao dia 22 com uma temperatura media de 14  . importante para minHash
		 String[] perfil = new String[31];
		 int[] temps_med = getMedia(mes); //media das temperaturas diarias desse mes
		 for(int d=0;d<31;d++){
			 perfil[d] = d+","+temps_med[d]; // array do perfil é igual ao dia seguido da temperatura media do dia 
		 }
		 return perfil;
	 }

	 public String[] perfilAnual(){
		 /*
		 retorna o perfil anual de temperaturas, composto por uma
		 etiqueta que representa a media de temperaturas do mes,
		 seguido do mes em questao || Ex: "7,23" corresponde ao mes
		 7, com uma temperatura mensal media de 23
		 */

		String[] perfil = new String[12];
		int[] temps_med_mes;
		int soma;
		for(int mes = 0; mes<12;mes++){
			temps_med_mes = getMedia(mes); //media das temperaturas diarias desse mes
			soma = 0;
			for(int dia=0;dia<31;dia++){
				soma += temps_med_mes[dia];
			}
			perfil[mes] = mes+"," + (soma/31);
		 }
		 return perfil;
	 }
 
	 public int[] getMedia(int mes){
		 //array com as temperaturas medias de todos os dias desse mes
		 int[] media = new int[31];
		 int somatempdia;
		 for(int d = 0;d<31;d++){
			 somatempdia = 0;
			 for(int h = 0;h<24;h++){
				 somatempdia += this.temperaturas[mes][d][h];
		 }
		 media[d] = (int)(somatempdia/24);
	 }
	 return media;
	}

	public void makeTempsRealistic(){
		/* 
		Como as temperaturas são geradas aleatoriamente
		isto iria criar um array completamente aleatorio que em nada
		se assemelha a temperaturas medidas num dia, que iria criar conflito 
		sobre a procura de dias similares, pois seriam todos muito distintos
		uns dos outros 
		*/
		for(int mes=0;mes<12;mes++){
			for(int dia = 0;dia<31;dia++){
				int[]temps =  getTempDiaria(mes, dia);

				/*
				Passar o array de ints para Integer para se poder
				operar sobre ele com a classe Arrays
				*/
				Integer[] tempsObject = new Integer[24];
    			for(int i = 0;i<24;i++){ 
      				tempsObject[i] = Integer.valueOf(temps[i]);
				}


				/* 
				Até meio do array, que corresponde a medição do meio-dia,
				as temperaturas vao aumentando, do meio dia para a frente, o dia vai 
				ficando progressivamente mais frio, daí ordernar-se o array
				de forma contrária 
				*/

				Arrays.sort(tempsObject,0,12);
				Arrays.sort(tempsObject,12,24,Collections.reverseOrder());
				
				/*
				Neste ciclo for, passamos para o dia em questao, o array
				das temperaturas ordenadas por ordem crescente
				e decrescente feito em cima
				*/
				for(int h=0;h<tempsObject.length;h++){
					this.temperaturas[mes][dia][h] = tempsObject[h];
				  }
				
			}
		}


	}

}
