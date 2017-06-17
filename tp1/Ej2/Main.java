import java.io.*; 
import java.lang.Runtime;
import java.util.ArrayList;
import java.util.Iterator;
import java.lang.Runtime;

public class Main{	

	/**
	 *	Function that removes characters that are not needed from a string
	 * 	@return subset of s deleting unnecessary characters
	 *	@param s it's a string to divide
	 *	@pre.  s != ""
	 *	@post. res = subset of s deleting unnecessary characters
     */  
	private static String[] divideString(String s){
		//res[0]=descripcion | res[1]=valor | res[2]=costo
		String[] res= new String[3];
		//aux[0]=descripcion | aux[1]=valor/costo
		String[] aux=s.split("\\' ");
		res[0]=aux[0].replace("'","");
		String[] aux2=aux[1].split("\\ ");
		res[1]=aux2[0];
		res[2]=aux2[1];
		return res;
	}

	/**
	 *	Function that creates a file and loads it with the data corresponding to the backlog histories
	 *	@param res it's a list with stories to keep
	 *	@pre. res size > 0 
	 *	@post. Create a file called backlogOK.txt
     */  
	public static void createFile(ArrayList<Story> res){
		try{
			int size = res.size();
			File file=new File("backlogOK.txt");
			FileWriter writer = new FileWriter(file);
			writer.write("Sprint"+"\n"+"\n");
			writer.write("\t"+"  Actividad|Prioridad|  Costo|");
			for(int i=0;i<size;i++){
				Story aux = res.get(i);
				writer.write("\n"+"\t"+aux.getDescription()+"|");
				writer.write("\t"+"\t"+aux.getPriority()+"|");
				writer.write("\t"+"\t"+aux.getWeight()+"|");
			}
			writer.close();	
		}catch(Exception e){
			System.out.println("Error al calcular las historias del sprint");
		}
	}

	/**
	 *	A function that reads a file with the data corresponding to the backlog histories
	 *	@return res it's a list with stories
	 *	@param Name is the name of the file to read
	 *	@pre. backlog.txt exists
	 *	@post. a list with stories
     */  
	public static ArrayList<Story> readFile (String name){
		ArrayList<Story> result = new ArrayList<Story>();
		try{
			FileReader fr = new FileReader(name); 
			BufferedReader br = new BufferedReader(fr); 
			String s;
			int cont = 0; 
			while((s = br.readLine()) != null) { 
		  		cont++;
			}
			
		 	fr = new FileReader("backlog.txt");
	 		br = new BufferedReader(fr); 
			String[] array;
			while((s = br.readLine()) != null) {
				array = divideString(s);
				int priority = Integer.parseInt(array[1]);
				int weight = Integer.parseInt(array[2]);
				Story h = new Story(array[0], priority, weight);
				result.add(h);
			}	
			fr.close();
		}catch(Exception e){
			System.out.println("No se pudo leer el archivo.Verifique que este exista");
		}
		return result;
	}


	public static void main(String[] args) throws Exception {
		try{
			ArrayList<Story> stories = readFile("backlog.txt");
	 		int n = stories.size();
	 		Integer[] weights = new Integer[n];
	    	Integer[] priorities = new Integer[n];
	    	int priority = 0; 
	 		int weight = 0;
	 		
			for(int i = 0;i<stories.size();i++){
				priority = stories.get(i).getPriority();
				weight = stories.get(i).getWeight();
				priorities[i] = priority;
				weights[i] = weight;
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Ingrese el tiempo del sprint");
			String time = br.readLine();
			Integer timeSprint = Integer.parseInt(time);
			Sprint sprint = new Sprint(timeSprint,weights,priorities);
			sprint.generateSprint(weights.length,timeSprint);
			ArrayList<Integer> res = new ArrayList<Integer>();
			res = sprint.getSprint();
			ArrayList<Story> result = new ArrayList<Story>();
			Story story;
			int pos = 0;
			for(int i = 0;i<res.size();i++){
				pos = res.get(i);
				story = stories.get(pos-1);
				result.add(story);
			}
			createFile(result);
			System.out.println("Abriendo el archivo backlogOK.txt");
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