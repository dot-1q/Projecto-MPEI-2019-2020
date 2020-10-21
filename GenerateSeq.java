
public class GenerateSeq {
	
	public static String Seq(int[] a, int n) {//FAZER A PARTE DE ANDAR UM PARA A FRENTE ATÉ N (como está no caderno)
		String r="";
		
		for(int j=0; j<n; j++) {
			for(int i=0; i<a.length; i++) {
		
				if(i%n==0) {
					r+= " ";	
				}
				if(i+j<a.length) r+= a[i+j]+"/";
			}
		}
		return r;		
	}
	
	public static String geraSeq(int tamanho, int num) {
		String seq= "";
		for(int i=0; i<tamanho; i++) {
			seq+=num+"/";
		}
		return seq;		
	}
}
