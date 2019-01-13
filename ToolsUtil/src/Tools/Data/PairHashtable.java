package Tools.Data;

import java.util.AbstractMap.SimpleEntry;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

public class PairHashtable<K1, K2, V> {
	private Hashtable<SimpleEntry<K1, K2>, V> hashtable;

	public PairHashtable(){
		hashtable = new Hashtable<SimpleEntry<K1, K2>, V>();
	}

	public void put(K1 key1, K2 key2, V value){
		hashtable.put(new SimpleEntry<K1, K2>(key1, key2), value);
	}

	public V get(K1 key1, K2 key2){
		return hashtable.get(new SimpleEntry<K1, K2>(key1, key2));
	}

	public V remove(K1 key1, K2 key2){
		return hashtable.remove(new SimpleEntry<K1, K2>(key1, key2));
	}

	public Set<K1> firstKeySet(){
		HashSet<K1> result = new HashSet<K1>();

		for(SimpleEntry<K1, K2> keypair : hashtable.keySet()){
			if(!result.contains(keypair.getKey())){
				result.add(keypair.getKey());
			}
		}

		return result;
	}

	public Set<K2> secondKeySet(){
		HashSet<K2> result = new HashSet<K2>();

		for(SimpleEntry<K1, K2> keypair : hashtable.keySet()){
			if(!result.contains(keypair.getValue())){
				result.add(keypair.getValue());
			}
		}

		return result;
	}
}
