import java.util.Scanner;
import java.util.ArrayList;

class DocFinder {
	
	static ArrayList<Document> documents;
	static ArrayList<Directory> directories;
	
	private static void initData() {
		
		documents = new ArrayList<Document>();
		directories = new ArrayList<Directory>();
		
		documents.add(new Document());
		documents.get(0).setAttr("type","passport");
		documents.get(0).setAttr("number","2207 876234");
		documents.get(0).setAttr("name","Василий Гупкин");

		documents.add(new Document());
		documents.get(1).setAttr("type","invoice");
		documents.get(1).setAttr("number","11-2");
		documents.get(1).setAttr("name","Геннадий Покемонов");
		
		directories.add(new Directory("1"));
		directories.get(0).addDoc(documents.get(0));
		directories.get(0).addDoc(documents.get(1));

		documents.add(new Document());
		documents.get(2).setAttr("type","insurance");
		documents.get(2).setAttr("number","10006");
		documents.get(2).setAttr("name","Аристарх Павлов");
		
		directories.add(new Directory("2"));
		directories.get(1).addDoc(documents.get(2));
		
		directories.add(new Directory("3"));
	}
	
	private static Document findDocument(String p_Number) {
		for (int i = 0; i < documents.size(); i++) {
			if (documents.get(i).attributes.get("number").equals(p_Number)) {
				return documents.get(i);
			}
		}
		return null;
	}
	
	private static String getOwner(String p_Number) {
		
		Document doc = findDocument(p_Number);
		
		if (doc != null) {
			return "Document's owner: " + doc.attributes.get("name");
		} else {
			return "Document did not found";
		}
	}
	
	private static String getDirectory(String p_Number) {
		
		Document doc = findDocument(p_Number);
		
		if (doc != null) {
			for (int i = 0; i < directories.size(); i++) {
				if (directories.get(i).findDoc(doc)) {
					return directories.get(i).name;
				}
			}
		} else {
			return "Document did not found";
		}
		return "";
	}
	
	private static void printDocs() {
		for (int i = 0; i < documents.size(); i++) {
			System.out.print("#:" + documents.get(i).attributes.get("number") + ",");
			System.out.print(" type: " + documents.get(i).attributes.get("type") + ",");
			System.out.print(" name: " + documents.get(i).attributes.get("name") + ",");
			System.out.print(" directory: " + getDirectory(documents.get(i).attributes.get("number")));
			System.out.println();
		}
	}
	
	private static String getDirectoryList() {
		String directoryList = "";
		for (int i = 0; i < directories.size(); i++) {
			directoryList = directoryList + directories.get(i).name;
			if (i == directories.size() - 1) {
				directoryList = directoryList + ".";
			} else {
				directoryList = directoryList + ", ";
			}
		}
		return directoryList;
	}
	
	private static void addDirectory(String p_Name) {
		boolean bExists = false;
		for (int i = 0; i < directories.size(); i++) {
			if (directories.get(i).name.equals(p_Name)) {
				bExists = true;
				break;
			}
		}
		if (bExists) {
			System.out.print("Directory exists. ");
		} else {
			directories.add(new Directory(p_Name));
			System.out.print("Directory added. ");
		}
		System.out.println("Current directory list: " + getDirectoryList());
	}
	
	private static void delDirectory (String p_Name) {
		boolean bExists = false;
		for (int i = 0; i < directories.size(); i++) {
			if (directories.get(i).name.equals(p_Name)) {

				if (directories.get(i).docs.isEmpty()) {
					directories.remove(i);
					System.out.print("Directory deleted. ");
				} else {
					System.out.print("Directory has documents, delete them before. ");
				}
					
				bExists = true;
				break;
			}
		}
		if (!bExists) {
			System.out.print("No such directory. ");
		}
		
		System.out.println("Current directory list: " + getDirectoryList());
	}
	
	public static void addDocument(String p_Number, String p_Type, String p_Owner, String p_Directory) {
		
		boolean bExists = false;
		for (int i = 0; i < directories.size(); i++) {
			if (directories.get(i).name.equals(p_Directory)) {
				bExists = true;
				
				Document doc = new Document();
				doc.setAttr("number", p_Number);
				doc.setAttr("type", p_Type);
				doc.setAttr("name", p_Owner);
				documents.add(doc);
				
				directories.get(i).addDoc(doc);
				
				System.out.print("Document added. ");
			}
		}
		if (!bExists) {
			System.out.print("No such directory. Add directory with command 'as'. ");
		}
		
		System.out.println("Curent document list: ");
		printDocs();
	}
	
	public static void delDocument(String p_Number) {
		
		Document doc = findDocument(p_Number);
		
		if (doc != null) {
			for (int i = 0; i < directories.size(); i++) {
				if (directories.get(i).findDoc(doc)) {
					directories.get(i).delDoc(doc);
				}
			}
			documents.remove(doc);
			System.out.println("Document deleted. ");
		} else {
			System.out.print("Document did not found.");
		}
		System.out.println("Curent document list: ");
		printDocs();
	}
	
	public static void printDirectoryDocs(String p_Name) {
		boolean bExists = false;
		for (int i = 0; i < directories.size(); i++) {
			if (directories.get(i).name.equals(p_Name)) {
				directories.get(i).printDocs();
				bExists = true;
			}
		}
		if (!bExists) {
			System.out.println("No such directory.");
		}
	}
	
	public static void move(String p_Number, String p_Directory) {
		Document doc = findDocument(p_Number);
		
		if (doc != null) {
			for (int i = 0; i < directories.size(); i++) {
				if (directories.get(i).findDoc(doc)) {
					directories.get(i).delDoc(doc);
				}
			}
			boolean bExists = false;
			for (int i = 0; i < directories.size(); i++) {
				if (directories.get(i).name.equals(p_Directory)) {
					directories.get(i).addDoc(doc);
					bExists = true;
					System.out.println("Document moved. ");
				}
			}
			if (!bExists) {
				System.out.println("No such directory.");
			}
		} else {
			System.out.print("Document did not found.");
		}
		System.out.println("Curent document list: ");
		printDocs();
	}
	
	public static void main(String [] args) {
		
		String text;
		Scanner in = new Scanner(System.in);
		
		initData();
		
		System.out.println(documents.get(0).attributes);

		directories.get(0).printDocs();
		
		try {
			while (1==1) {
				System.out.print("Input a command: ");
				text = in.nextLine();
				
				if (text.equals("q")) {
					in.close();
					System.out.println("Exit");
					break;
				}
				
				if (text.equals("p")) {
					System.out.println("input document number:");
					text = in.nextLine();
					System.out.println(getOwner(text));
				}
				
				if (text.equals("s")) {
					System.out.println("input document number:");
					text = in.nextLine();
					System.out.println(getDirectory(text));
				}
				
				if (text.equals("l")) {
					printDocs();
				}
				
				if (text.equals("ads")) {
					System.out.println("input directory number:");
					text = in.nextLine();
					addDirectory(text);
				}
				
				if (text.equals("ds")) {
					System.out.println("input directory number:");
					text = in.nextLine();
					delDirectory(text);
				}
				
				if (text.equals("ad")) {
					System.out.println("input document number:");
					String doc_num = in.nextLine();
					System.out.println("input document type:");
					String doc_type = in.nextLine();
					System.out.println("input document owner:");
					String doc_owner = in.nextLine();
					System.out.println("input document directory:");
					String doc_dir = in.nextLine();
					addDocument(doc_num, doc_type, doc_owner, doc_dir);
				}
				
				if (text.equals("d")) {
					System.out.println("input document number:");
					text = in.nextLine();
					delDocument(text);
				}
				
				if (text.equals("m")) {
					System.out.println("input document number:");
					String doc_num = in.nextLine();
					System.out.println("input document directory:");
					String doc_dir = in.nextLine();
					move(doc_num, doc_dir);
				}
				
				if (text.equals("pd")) {
					System.out.println("input directory number:");
					text = in.nextLine();
					printDirectoryDocs(text);
				}
			}
			
		} catch (Exception e) {
			in.close();
		} finally {
			in.close();
		}
	}
}