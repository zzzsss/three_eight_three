package gc;

/**  
 * A reference-counting heap.
 */
public class RefCountHeap extends Heap {
	private static final int SIZE = -1;
	private static final int COUNTER = -2;

	public RefCountHeap(int size) {
		//size must be even
		super(size);
	}

	public void endScope() {
		// TODO decrease counters
		for(Var v : currentScope()){
			if (!v.isNull()){
				decreaseCounter(v.addr);
			}
		}
		super.endScope();
	}

	/**
	 * Allocate memory with 2 extra slots, one for the object size, the other
	 * for the reference counter.
	 */
	public void allocate(Var v, int size) throws InsufficientMemory {
		//Todo ...
		if (!v.isNull()){
			decreaseCounter(v.addr);
		}
		super.allocate(v, size + 2);
		v.addr += 2;
		data[v.addr + SIZE] = size;
		data[v.addr + COUNTER] = 1;
	}

	public void assign(Var v1, Var v2) {
		if (!v1.isNull())
			decreaseCounter(v1.addr);
		super.assign(v1, v2);
		if (!v1.isNull())
			increaseCounter(v1.addr);
	}

	public void readField(Var v1, Var v2, int fieldOffset) {
		//int i=v2.addr+fieldOffset;
		// TODO decrease counter
		if(!v1.isNull())
			decreaseCounter(v1.addr);
		super.readField(v1, v2, fieldOffset);
		// TODO increase counter
		if(!v1.isNull())
			increaseCounter(v1.addr);
	}

	public void writeField(Var v1, int fieldOffset, Var v2) {
		int i=v1.addr+fieldOffset;
		// TODO decrease counter
		if(data[i]>0)
			decreaseCounter(data[i]);
		super.writeField(v1, fieldOffset, v2);
		// TODO increase counter
		if(!v2.isNull())
			increaseCounter(v2.addr);
	}

	private void increaseCounter(int addr) {
		// TODO
		data[addr+COUNTER] ++;
	}

	private void decreaseCounter(int addr) {
		// TODO
		if(--data[addr+COUNTER] == 0){
			for(int i=addr;i<=addr+data[addr + SIZE]-1;i++){
				if(data[i]>0){
					int tmp=data[i];
					data[i]=-1;
					decreaseCounter(tmp);
				}
			}
			freelist.release(addr-2,data[addr+SIZE]+2);
		}
			
	}
}
