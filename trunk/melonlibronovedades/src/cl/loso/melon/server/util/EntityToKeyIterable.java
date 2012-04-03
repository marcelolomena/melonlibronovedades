package cl.loso.melon.server.util;

import java.util.Iterator;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

public class EntityToKeyIterable implements Iterable<Key> {
	Iterable<Entity> iterable;

	public EntityToKeyIterable(Iterable<Entity> iterable) {
		this.iterable = iterable;
	}

	@Override
	public Iterator<Key> iterator() {
		return new EntityToKeyIterator(iterable.iterator());
	}

	protected class EntityToKeyIterator implements Iterator<Key> {
		Iterator<Entity> it;

		public EntityToKeyIterator(Iterator<Entity> it) {
			this.it = it;
		}

		@Override
		public boolean hasNext() {
			return it.hasNext();
		}

		@Override
		public Key next() {
			return it.next().getKey();
		}

		@Override
		public void remove() {
			it.remove();
		}
	}
}