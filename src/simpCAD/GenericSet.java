package simpCAD;

import java.util.HashSet;

public class GenericSet<E> extends HashSet<E> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7274077337145392021L;
	
	private Class<E> genericType;

	public GenericSet(Class<E> c){
		super();
		this.genericType = c;
	}

	public Class<E> getGenericType(){
		return genericType;
	}
}
