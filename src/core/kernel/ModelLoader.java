package core.kernel;

import java.io.File;
import java.io.FilenameFilter;
import java.util.HashMap;

import core.model.Model;
import core.utils.objloader.OBJLoader;

public class ModelLoader {

	private static HashMap<String,Model> models = new HashMap<>();
	
	public static void load() {
		try {
		File file  = new File("./res/models");
		for(File f : file.listFiles()) {
			if(f.isDirectory()) {
				File obj = f.listFiles(new FilenameFilter() {

					@Override public boolean accept(File arg0, String arg1) {
						return arg1.endsWith(".obj");
					}
					
				})[0];
				File mtl = f.listFiles(new FilenameFilter() {

					@Override public boolean accept(File arg0, String arg1) {
						return arg1.endsWith(".mtl");
					}
					
				})[0];
				Model[] models = new OBJLoader().load(f.getAbsolutePath(), obj.getName(), mtl.getName());
				int i = 0;
				for(Model m : models) {
					ModelLoader.models.put(obj.getName().replaceAll(".obj", "") + i++, m);
				}
			}
		}
		}catch(ArrayIndexOutOfBoundsException e) {
			System.out.println("Emty folder in resources folder");
		}
	}
	
	public static Model get(String name) {
		return models.get(name);
	}

	public static Model get(String string, int i) {
		return models.get(string+i);
	}
	
}
