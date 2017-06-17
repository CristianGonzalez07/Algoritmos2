import java.io.*; 
import java.lang.Runtime;
import java.util.ArrayList;
import java.util.Iterator;
import java.lang.Runtime;


public class Main{	

	private static String[] divideString(String s){
		//res[0]=descripcion | res[1]=valor | res[2]=costo
		String[] res= new String[3];
		//aux[0]=descripcion | aux[1]=valor/costo
		String[] aux=s.split("\\' ");
		res[0]=aux[0].replace("'","");// Eliminacion de la comilla inicial
		String[] aux2=aux[1].split("\\ ");
		res[1]=aux2[0];
		res[2]=aux2[1];
		return res;
	}
	
	public static void creacionArchivo(ArrayList<Historia> res){
		try{
			int size = res.size();
			File archivo=new File("backlogOK.txt");

			// Crea un  objeto FileWriter que sera el que nos ayude a escribir sobre archivo
			FileWriter escribir=new FileWriter(archivo);
			escribir.write("Sprint"+"\n"+"\n");
			escribir.write("\t"+"  Actividad|Prioridad|  Costo|");
			for(int i=0;i<size;i++){
				Historia aux = res.get(i);
				escribir.write("\n"+"\t"+aux.getDescripcion()+"|");
				escribir.write("\t"+"\t"+aux.getPrioridad()+"|");
				escribir.write("\t"+"\t"+aux.getCosto()+"|");
			}
			escribir.close();	
		}catch(Exception e){
			System.out.println("Error al calcular las historias del sprint");
		}
	}


	public static ArrayList<Historia> lecturaArchivo(String nombre){
		ArrayList<Historia> result = new ArrayList<Historia>();
		try{
			FileReader fr = new FileReader(nombre); 
			BufferedReader br = new BufferedReader(fr); 
			String s;
			int cont = 0; 
			while((s = br.readLine()) != null) { 
		  		cont++;
			}
			
			//lectura de las lineas del archivo y creacion de las Triplas
		 	fr = new FileReader("backlog.txt");
	 		br = new BufferedReader(fr); 
			String[] array;
			while((s = br.readLine()) != null) {
				array = divideString(s);
				int prioridad = Integer.parseInt(array[1]);
				int costo = Integer.parseInt(array[2]);
				Historia cp = new Historia(array[0], prioridad, costo);
				result.add(cp);
			}	
			fr.close();
		}catch(Exception e){
			System.out.println("No se pudo leer el archivo.Verifique que este exista");
		}
		return result;
	}
	public static void main(String[] args) throws Exception {
		try{
			ArrayList<Historia> historias = lecturaArchivo("backlog.txt");
	 		int n = historias.size();
	 		Integer[] costos = new Integer[n];
	    	Integer[] prioridades = new Integer[n];
	    	int prioridad = 0; 
	 		int costo = 0;
	 		

			for(int i = 0;i<historias.size();i++){
				prioridad = historias.get(i).getPrioridad();
				costo = historias.get(i).getCosto();
				prioridades[i] = prioridad;
				costos[i] = costo;
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Ingrese el tiempo del sprint");
			String tiempo = br.readLine();
			Integer tiempoSprint = Integer.parseInt(tiempo);
	 
			Sprint sprint = new Sprint(tiempoSprint,costos,prioridades);
			sprint.generarSprint(costos.length,tiempoSprint);
			ArrayList<Integer> res = new ArrayList<Integer>();
			res = sprint.getSprint();
			ArrayList<Historia> result = new ArrayList<Historia>();
			Historia historia;
			int pos = 0;
			
			for(int i = 0;i<res.size();i++){
				pos = res.get(i);
				historia = historias.get(pos-1);
				result.add(historia);
			}

			creacionArchivo(result);
			// Mensaje de que se efectuo correctamente
			System.out.println("Abriendo el archivo backlogOK.txt");

			// Deteccion de sistema operativo para la apertura del editor de texto con el 
			// backlog ordenado.
			String sys = System.getProperty("os.name");
			switch (sys){
				case "Linux": Process linux = Runtime.getRuntime().exec ("subl backlogOK.txt"); 
				break;
				case "Mac OS X": Process mac = Runtime.getRuntime().exec ("sublime backlogOK.txt"); 
				break;  
			}
		}catch(Exception e){
			System.out.println("Error Inesperado,verifique que los datos ingresados son validos");
		}
		
	}
}