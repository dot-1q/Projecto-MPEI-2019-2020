
public class BloomFilter{
    private int[] array;
    private int nHash; 

    public BloomFilter(int n,int k){
        this.array = new int[n];
        this.nHash = k;
    }
    public BloomFilter(int n){
        this.array = new int[n];
        this.nHash = 6; //valor defaul do numero de hashes
    }

    private int getHashCode(String temp){
        int hashCode = temp.hashCode();
        int index = Math.abs(hashCode % array.length); //ficar dentro do bloomFilter
        return index;   
    }

    public void inserir(String temp){
        for(int i=0;i<nHash;i++){
            temp += i;
            int index = getHashCode(temp);
            array[index] = 1;
        }
    }

    public boolean pertence(String temp){
        boolean pertence = false;
        for(int i = 0; i < nHash; i++) {
			temp += i;
			int index = getHashCode(temp);
			
			if(array[index] == 1) {
				pertence = true;
				break;
			}
        }
        return pertence;
    }

}