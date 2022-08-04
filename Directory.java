import java.util.ArrayList;

class Directory {
	
	String name;
	
	ArrayList<Document> docs;
	
	public Directory (String p_Name) {
		name = p_Name;
		docs = new ArrayList<Document>();
	}
	
	public void addDoc(Document p_Document) {
		docs.add(p_Document);
	}
	
	public void delDoc(Document p_Document) {
		for (int i = 0; i < docs.size(); i++) {
			if (p_Document == docs.get(i)) {
				docs.remove(p_Document);
			}
		}
	}
	
	public void printDocs() {
		for (int i = 0; i < docs.size(); i++) {
			System.out.print(docs.get(i).attributes.get("number") + ", ");			
		}
		System.out.println();
	}
	
	public boolean findDoc(Document p_Document) {
		for (int i = 0; i < docs.size(); i++) {
			
			if (docs.get(i) == p_Document) {
				return true;
			}
		}
		return false;
	}
}