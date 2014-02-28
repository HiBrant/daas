package data.as.a.service.metadata.datamodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import data.as.a.service.metadata.exception.RepeatedVariableNameException;

public class Fields implements Iterable<Field> {
	
	private List<Field> fields;
	private Map<String, Integer> namesAndIndexes;
	
	public Fields() {
		fields = new ArrayList<Field>();
		namesAndIndexes = new HashMap<String, Integer>();
	}
	
	public boolean hasField(String name) {
		return namesAndIndexes.containsKey(name);
	}
	
	public void add(String name, FieldType type) throws RepeatedVariableNameException {
		if (!this.hasField(name)) {
			fields.add(new Field(name, type));
			namesAndIndexes.put(name, fields.size() - 1);
		} else {
			throw new RepeatedVariableNameException(name);
		}
	}
	
	public void remove(String name) {
		if (this.hasField(name)) {
			int index = namesAndIndexes.get(name);
			fields.remove(index);
			namesAndIndexes.remove(name);
		}
	}

	@Override
	public Iterator<Field> iterator() {
		return fields.iterator();
	}
	
	public Field getField(String name) {
		if (!this.hasField(name)) {
			return null;
		}
		
		return fields.get(namesAndIndexes.get(name));
	}

}
