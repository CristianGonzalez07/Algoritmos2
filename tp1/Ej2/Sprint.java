import java.util.ArrayList;
import java.lang.Math;

public class Sprint {

	private Integer tiempo;				 
	private Integer[][] matrizBeneficio; 
	private Integer[] costos;
	private Integer[] prioridades;
	
  
	public Sprint(Integer t,Integer[] c,Integer[] p){
		this.tiempo = t;
		this.costos = c;
		this.prioridades = p;
		Integer n = c.length;
		Integer i;
		Integer j;

		//Creamos la matriz
		this.matrizBeneficio = new Integer[n+1][tiempo+1];
		
		//Rellenamos la 1ª fila de ceros
		for(i = 0; i <= tiempo; i++){
			matrizBeneficio[0][i] = 0; 
		}

		//Rellenamos la 1ª columna de ceros
		for(i = 0;i <= n;i++){ 
			matrizBeneficio[i][0] = 0;   
		}

		//Rellenamos el resto de la matriz con -1
		for(i = 1;i <= n;i++){  
	    	for(j = 1;j <= tiempo;j++){ 
	    		matrizBeneficio[i][j] = -1;
	    	}
	    } 
	}
  	
  	//variante del problema de la mochila con programacion dinamica 
	public Integer generarSprint(Integer i,Integer j){
		Integer value;
		if (matrizBeneficio[i][j] < 0){
			if(j<costos[i-1]){//costos en i-1 pues i esta reflejado en la matriz que tiene tamaño costos+1
				value = generarSprint(i-1,j);
			}else{
				value = Math.max(generarSprint(i-1,j),prioridades[i-1]+generarSprint(i-1,j-costos[i-1]));
			}
			matrizBeneficio[i][j] = value;
		}
		return matrizBeneficio[i][j];
	}

	//retorna la lista de las historias del sprint maximizando las prioridades
	//el ArrayList de Integer hace referencia a las historias donde cada elemento es un nro de historia
	public  ArrayList<Integer> getSprint(){
		ArrayList<Integer> result = new ArrayList<Integer>();
		int indiceElem = this.costos.length;
		int tiempoSprint = this.tiempo;

		while(indiceElem!=0){
			if (matrizBeneficio[indiceElem][tiempoSprint] > matrizBeneficio[indiceElem-1][tiempoSprint]){
				result.add(indiceElem); 
				tiempoSprint = tiempoSprint - this.costos[indiceElem-1];
			}
			indiceElem--;
		}
		return result;
	}
}
