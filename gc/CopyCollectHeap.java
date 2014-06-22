package gc;

import java.util.Arrays;  

/**
 * For simplicity, implement Fenichel's algorithm instead of Cheney's algorithm.
 * 
 * Semi-space garbage collection [Fenichel, 1969] is a copying algorithm, which
 * means that reachable objects are relocated from one address to another during
 * a collection. Available memory is divided into two equal-size regions called
 * "from-space" and "to-space".
 * 
 * Allocation is simply a matter of keeping a pointer into to-space which is
 * incremented by the amount of memory requested for each allocation (that is,
 * memory is allocated sequentially out of to-space). When there is insufficient
 * space in to-space to fulfill an allocation, a collection is performed.
 * 
 * A collection consists of swapping the roles of the regions, and copying the
 * live objects from from-space to to-space, leaving a block of free space
 * (corresponding to the memory used by all unreachable objects) at the end of
 * the to-space.
 * 
 * Since objects are moved during a collection, the addresses of all references
 * must be updated. This is done by storing a "forwarding address" for an object
 * when it is copied out of from-space. Like the mark-bit, this forwarding
 * address can be thought of as an additional field of the object, but is
 * usually implemented by temporarily repurposing some space from the object.
 * 
 * The primary benefits of semi-space collection over mark-sweep are that the
 * allocation costs are extremely low (no need to maintain and search the free
 * list), and fragmentation is avoided.
 */
public class CopyCollectHeap extends Heap {
	private static final int SIZE = -1;
	private static final int FORWARD = -2;

	private int toSpace;
	private int fromSpace;
	private int allocPtr;
	private int currentEnd;
	private int scanPtr;

	/**
	 * Though the super constructor is invoked and the free list is initialized,
	 * the free list is not used in the implementation of this copy collector.
	 */
	public CopyCollectHeap(int size) {
		super(size - size % 2);
		size = size - size % 2;
		fromSpace = 0;
		toSpace = size / 2;
		allocPtr = fromSpace;
		currentEnd = size / 2;
	}

	public void allocate(Var v, int size) throws InsufficientMemory {
		// TODO
		try {
			allocateObject(v, size);
		} catch (InsufficientMemory e) {
			collect();
			allocateObject(v, size);
		}
	}
	private void allocateObject(Var v, int size) throws InsufficientMemory {
		if((currentEnd-allocPtr)<size+2)
			throw new InsufficientMemory();
		v.addr = allocPtr + 2;
		data[v.addr + SIZE] = size;
		data[v.addr + FORWARD] = -1;
		allocPtr = allocPtr + 2 + size;
		Arrays.fill(data, v.addr, v.addr + size, -1);
	}
	
	private void collect() {
		// TODO
		allocPtr = toSpace;
		scanPtr = toSpace;
		// first copy the reachable
		for(Var v : reachable){
			if(!v.isNull()){
				if(data[v.addr + FORWARD]==-1){
					v.addr = copy(v.addr);
				}
				else
					v.addr = data[v.addr + FORWARD];
			}
		}
		// next scan the new space
		while(scanPtr != allocPtr){
			scanPtr += 2;
			int size = data[scanPtr+SIZE];
			for(int i=0;i<size;i++){
				int cur_addr = data[scanPtr+i];
				if(cur_addr >= 0){
					if(data[cur_addr+FORWARD]==-1){
						data[scanPtr+i] = copy(cur_addr);
					}
					else
						data[scanPtr+i] = data[cur_addr+FORWARD];
				}
			}
			scanPtr += size;
		}
		//swap the spaces
		if(fromSpace==0){
			currentEnd = 2*toSpace;
			fromSpace = toSpace;
			toSpace = 0;
		}
		else{
			currentEnd = fromSpace;
			toSpace = fromSpace;
			fromSpace = 0;
		}
	}

	private int copy(int addr) {
		// TODO
		allocPtr += 2;
		int tmp = allocPtr;
		int size = data[addr+SIZE];
		data[allocPtr+SIZE] = size;
		data[allocPtr+FORWARD] = -1;
		for(int i=0;i<size;i++)
			data[allocPtr+i] = data[addr+i];
		allocPtr += size;
		data[addr + FORWARD] = tmp;
		return tmp;
	}
}
