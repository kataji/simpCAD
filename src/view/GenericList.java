package view;

import java.util.ArrayList;

public class GenericList<E> extends ArrayList<E> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7274077337145392021L;
	
	private Class<E> genericType;

	public GenericList(Class<E> c){
		super();
		this.genericType = c;
	}

	public Class<E> getGenericType(){
		return genericType;
	}

	@Override
	public boolean remove(Object o) {
		boolean contained = false;
		for (int i = 0; i < this.size(); i++) {
			if (this.get(i) == o) {
				contained = true;
				remove(i);
				break;
			}
		}
		return contained;
	}
}
