package modules.pathfinding;
    /**************************************************
    Copyright (C) 2014  Raptis Dimos <raptis.dimos@yahoo.gr>


    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
	**************************************************/


import java.awt.Point;
import java.io.*; 
import java.util.ArrayList;
import java.util.Scanner;


public class A_StarAlgorithm {



	public static void main(String[] args) throws InvalidLetterException,FileNotFoundException,IOException,HeapException{	
	
		if(args.length != 1){
			System.out.println("Usage: java A_StarAlgorithm <filename>");
		}
		else{
			String filename = args[0];

			InputHandler handler = new InputHandler();
			SquareGraph graph = handler.readMap(filename);
			
			ArrayList<Node> path = graph.executeAStar();
			
			if(path == null){
				System.out.println("There is no path to target");
			}
			else{
				System.out.println("The total number of moves from distance to the target are : " + path.size());
				System.out.println("You want to see the whole path to the target ? (y/n) ");
				Scanner scanner = new Scanner(System.in);
				String response = scanner.nextLine();
				if(response.equals("y")){
					System.out.println("--- Path to target ---");
					graph.printPath(path);
				}
			}
		}
	}
	
	
}

