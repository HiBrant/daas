package data.as.a.service.metadata.datamodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import data.as.a.service.exception.metadata.RepeatedVariableNameException;

public class References implements Iterable<Reference> {
	
	private List<Reference> references;
	private Map<String, Integer> namesAndIndexes;
	
	public References() {
		references = new ArrayList<>();
		namesAndIndexes = new HashMap<>();
	}
	
	public boolean hasReference(String name) {
		return namesAndIndexes.containsKey(name);
	}
	
	public void add(Reference ref) throws RepeatedVariableNameException {
		if (!this.hasReference(ref.getName())) {
			references.add(ref);
			namesAndIndexes.put(ref.getName(), references.size() - 1);
		} else {
			throw new RepeatedVariableNameException(ref.getName());
		}
	}
	
	public void remove(String name) {
		if (this.hasReference(name)) {
			int index = namesAndIndexes.get(name);
			references.remove(index);
			namesAndIndexes.remove(name);
		}
	}

	@Override
	public Iterator<Reference> iterator() {
		return references.iterator();
	}

}
