
import java.io.*;
import java.util.*;

import FINAL.Router.Information;

public class Main {
	 public static WeightedGraph graph;
	 public static ArrayList<Router> routerLinks;
	 public static Queue<Event> queue=new LinkedList<Event>();
	 
	 public static void main(String[] args){

		    System.out.println("Please input the path of directory which contains infile.dat. Example D:\\ABX\\");
			Scanner scanner =new Scanner(System.in);            //C:\Users\pc\Desktop
			String inputPath="";
			String filename="infile.dat";
			File file;
			while(true){
				inputPath=scanner.nextLine();
				if(inputPath.charAt(inputPath.length()-1)!='\\') inputPath=inputPath+'\\';
				inputPath=inputPath+filename;
				System.out.println(inputPath);
				file=new File(inputPath);
				if(file.exists())break;
				else
					System.out.println("Cannot Find infile.dat. Please try again.");
			}
			System.out.println(file);
			
		    //File file=new File("C:\\Users\\pc\\Desktop\\infile.dat");
		 
			graph=new WeightedGraph(5);
			routerLinks=new ArrayList<>();
			Router router = null;
			String line;
			String IPAddress = null;
			int routerNumber = 0;
			int nextJump;
			int cost;
		 	
			try {
				BufferedReader input = new BufferedReader(new FileReader(file));
				
				while (!isEmpty((line=input.readLine()))) {
					if(line.contains(".")){
						String[] eachNumber=line.split(" ");
						routerNumber=Integer.valueOf(eachNumber[0]);
						IPAddress=eachNumber[1];
						router=new Router(IPAddress, routerNumber);
						routerLinks.add(router);
					} else {
						//add routing info
						line= line.trim();
						String[] routingInfo= line.split(" ");
						nextJump=Integer.valueOf(routingInfo[0]);
						cost=Integer.valueOf(routingInfo[1]);
						graph.addEdge(routerNumber, nextJump, cost);
					}
					
				}
				input.close();
			} catch (Exception ex) {
				System.err.println("Error: " + ex.getMessage());
			}
			
		 	Router.updateRouters();
	
		 	String promptString="Would like to continue(enter 'C'),"
					+ "quit (enter 'Q'),"
					+ "print the routing table of a router('P' followed by the router's id number),"
					+ "shut down a router(enter 'S' followed by the id number),"
					+ "start up a router(enter 'T' followed by the id)";
		 	
			while(true){
				System.out.println(promptString);
	
				String input=scanner.nextLine();
				
				int curRouter= -1;
				String command="";
				
				try {
					command= input.substring(0, 1);		
				} catch (Exception e) {
					System.out.println("Input format error, please try again");
					continue;
				}
				
				try {
					curRouter = Integer.valueOf(input.substring(1));
				} catch (Exception e) {
				
				}
	
				if(command.equalsIgnoreCase("C")){
					Router.updateRouters();
						
				}else if(command.equalsIgnoreCase("Q")){
					System.out.println("Quit success.");
					break;
					
				}else if(command.equalsIgnoreCase("P")){
					if(curRouter<0 || curRouter>=routerLinks.size()){
						System.out.println("invalid router id, try again.");
						continue;
					}
				 	routerLinks.get(curRouter).displayRoutingTable();
					
		 	    }else if(command.equalsIgnoreCase("S")){
			 	   	if(curRouter<0 || curRouter>=routerLinks.size()){
						System.out.println("invalid router id, try again.");
						continue;
					}
		 	    	routerLinks.get(curRouter).ON=false;
				 	Router.updateRouters();
		 	
		 	    }else if(command.equalsIgnoreCase("T")){
			 	   	if(curRouter<0 || curRouter>=routerLinks.size()){
						System.out.println("invalid router id, try again.");
						continue;
					}
		 	    	routerLinks.get(curRouter).ON=true;
		 	    	Router.updateRouters();		 	
	 	        }
				
			}
	
	 }
	 
	 public static boolean isEmpty(String s){
			return s==null || "".equals(s);
		}
}

class Event{
	int targetRouter;
	Packet packet;
	public Event(int targetRouter, Packet packet){
		this.targetRouter=targetRouter;
		this.packet=packet;
	}
}

