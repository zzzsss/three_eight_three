package gc;

import java.util.HashSet;  
import java.util.Iterator;
import java.util.Set;

public class MarkSweepHeap extends Heap {
	private static final int SIZE = -1;
	private static final int MARKER = -2;

	private int markTag = 0;
	private Set<Integer> allocatedObjectAddresses = new HashSet<Integer>();

	public MarkSweepHeap(int size) {
		super(size);
	}

	public void allocate(Var v, int size) throws InsufficientMemory {
		try {
			allocateObject(v, size);
		} catch (InsufficientMemory e) {
			markAndSweep();
			allocateObject(v, size);
		}
		// TODO
		allocatedObjectAddresses.add(v.addr);
	}

	/**
	 * Allocate memory with 2 extra slots, one for the object size, the other
	 * for the marker.
	 */
	private void allocateObject(Var v, int size) throws InsufficientMemory {
		super.allocate(v, size + 2);
		// TODO
		v.addr += 2;
		data[v.addr + SIZE] = size;
		data[v.addr + MARKER] = 0;
	}

	private void markAndSweep() {
		// TODO
		for(Var v : reachable){
			if(!v.isNull())
				mark(v.addr);
		}
		
		Set<Integer> to_remove = new HashSet<Integer>();
		for(Integer addr : allocatedObjectAddresses){
			if(sweep((int)addr)==false)
				data[addr + MARKER] = 0;
			else
				to_remove.add(addr);
		}
		allocatedObjectAddresses.removeAll(to_remove);
	}

	private void mark(int addr) {
		// TODO
		if(data[addr + MARKER] != 1){
			data[addr + MARKER] = 1;	//mark it
			for(int i=addr;i<=addr+data[addr + SIZE]-1;i++){
				if(data[i]>=0)
					mark(data[i]);
			}
		}
	}

	private boolean sweep(int addr) {
		// TODO
		if(data[addr+MARKER]==0){
			freelist.release(addr-2,data[addr+SIZE]+2);
			return true;
		}
		return false;
	}
}
