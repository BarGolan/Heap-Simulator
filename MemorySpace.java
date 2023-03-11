/**
 * Represents a managed memory space (also called "heap"). The memory space is
 * managed by three
 * methods: <br>
 * <b> malloc </b> allocates memory blocks, <br>
 * <b> free </b> recycles memory blocks,
 * <br>
 * <b> defrag </b> reorganizes the memory space, for better allocation and
 * rescheduling.
 * <br>
 * (Part of Homework 10 in the Intro to CS course, Efi Arazi School of CS)
 */
public class MemorySpace {

	// A list that keeps track of the memory blocks that are presently allocated
	private List allocatedList;

	// A list that keeps track of the memory blocks that are presently free
	private List freeList;

	static int delay = 0;

	/**
	 * Constructs a managed memory space ("heap") of a given maximal size.
	 * 
	 * @param maxSize The size of the memory space to be managed
	 */
	/*
	 * // Constructs and intilaizes an empty list of allocated memory blocks, and a
	 * // free list containing
	 * // a single memory block which represents the entire memory space. The base
	 * // address of this single
	 * // memory block is zero, and its length is the given memory size (maxSize).
	 */
	public MemorySpace(int maxSize) {

		allocatedList = new List();
		freeList = new List();
		freeList.addLast(new MemBlock(0, maxSize));
	}

	public List getAllocatedList() {
		return allocatedList;
	}

	public List getFreeList() {
		return freeList;
	}

	/**
	 * Allocates a memory block.
	 * If can't find a proper size to allocate tries to defrag once and allocate
	 * again.
	 * 
	 * @param length The length (in words) of the memory block that has to be
	 *               allocated
	 * @return the base address of the allocated block, or -1 if unable to allocate
	 */
	/*
	 * Scans the freeList, looking for the first free memory block whose length
	 * equals at least
	 * the given length. If such a block is found, the method performs the
	 * following
	 * operations:
	 * (1) A new memory block is constructed. The base address of the new block
	 * is set to the base address of the found free block. The length of the new
	 * block is set to the value of the method's length parameter.
	 * (2) The new memory block is appended to the end of the allocatedList.
	 * (3) The base address and the length of the found free block are updated,
	 * to reflect the allocation.
	 * For example, suppose that the requested block length is 17, and suppose
	 * that the base address and length of the the found free block are 250 and 20,
	 * respectively. In such a case, the base address and length of of the allocated
	 * block are set to 250 and 17, respectively, and the base address and length of
	 * the found free block are updated to 267 and 3, respectively.
	 * (4) The base address of the new memory block is returned.
	 * If the length of the found block is exactly the same as the requested length,
	 * then the found block is removed from the freeList, and appended to the
	 * allocatedList.
	 */
	public int malloc(int length) {
		StdDraw.setPenColor(StdDraw.WHITE);
		StdDraw.filledRectangle(0.06, 0.95, 0.05, 0.05);
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.text(0.06, 0.95, "Malloc(" + length + "):");
		DrawList.drawList(this);
		sleep();

		ListIterator iterator = freeList.iterator();
		while (iterator.current != null) {
			MemBlock currentBlock = iterator.next(); // returns the current block and increments the iterator
			if (currentBlock.length >= length) {
				MemBlock allocatedBlock = new MemBlock(currentBlock.baseAddress, length);
				allocatedList.addLast(allocatedBlock);
				currentBlock.length = currentBlock.length - length;
				currentBlock.baseAddress = currentBlock.baseAddress + length;
				if (currentBlock.length == 0) {
					freeList.remove(currentBlock);
				}
				StdDraw.setPenColor(StdDraw.WHITE);
				StdDraw.filledRectangle(0.5, 0.5, 1, 1);
				StdDraw.setPenColor(StdDraw.BLACK);
				DrawList.drawList(this);
				sleep();
				return allocatedBlock.baseAddress;
			}
		}

		StdDraw.setPenColor(StdDraw.RED);
		StdDraw.text(0.06, 0.95, "Malloc(" + length + "):");
		StdDraw.setPenColor(StdDraw.BLACK);
		sleep();

		defrag();

		StdDraw.setPenColor(StdDraw.WHITE);
		StdDraw.filledRectangle(0.06, 0.95, 0.05, 0.05);
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.text(0.06, 0.95, "Malloc(" + length + "):");
		DrawList.drawList(this);
		sleep();

		ListIterator iterator2 = freeList.iterator();
		while (iterator2.current != null) {
			MemBlock currentBlock = iterator2.next(); // returns the current block and increments the iterator
			if (currentBlock.length >= length) {
				MemBlock allocatedBlock = new MemBlock(currentBlock.baseAddress, length);
				allocatedList.addLast(allocatedBlock);
				currentBlock.length = currentBlock.length - length;
				currentBlock.baseAddress = currentBlock.baseAddress + length;
				if (currentBlock.length == 0) {
					freeList.remove(currentBlock);
				}

				StdDraw.setPenColor(StdDraw.WHITE);
				StdDraw.filledRectangle(0.5, 0.5, 1, 1);
				StdDraw.setPenColor(StdDraw.BLACK);
				DrawList.drawList(this);
				sleep();
				return allocatedBlock.baseAddress;
			}
		}
		StdDraw.setPenColor(StdDraw.RED);
		StdDraw.text(0.06, 0.95, "Malloc(" + length + "):");
		StdDraw.setPenColor(StdDraw.BLACK);
		sleep();

		return -1;
	}

	/**
	 * Frees the memory block whose base address equals the given address
	 * 
	 * @param address The base address of the memory block to free
	 */
	public void free(int address) {
		StdDraw.setPenColor(StdDraw.WHITE);
		StdDraw.filledRectangle(0.06, 0.95, 0.05, 0.05);
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.text(0.06, 0.95, "Free(:" + address + ")");
		DrawList.drawList(this);

		sleep();

		ListIterator iterator = allocatedList.iterator();
		while (iterator.current != null) {
			MemBlock currentBlock = iterator.next();
			if (currentBlock.baseAddress == address) {
				MemBlock freeBlock = new MemBlock(currentBlock.baseAddress, currentBlock.length);
				allocatedList.remove(currentBlock);
				freeList.addLast(freeBlock);

			}
		}

		StdDraw.setPenColor(StdDraw.WHITE);
		StdDraw.filledRectangle(0.5, 0.5, 1, 1);
		StdDraw.setPenColor(StdDraw.BLACK);
		DrawList.drawList(this);
		sleep();
	}

	/**
	 * A textual representation of this memory space
	 * 
	 * @return a string representation of this memory space.
	 */
	public String toString() {
		StringBuilder s = new StringBuilder("");
		s.append("Free List: " + freeList.toString() + "\n");
		s.append("Allocated List: " + allocatedList.toString() + "\n");
		return s.toString();

	}

	/**
	 * Performs a defragmantation of the memory space.
	 * Can be called periodically, or by malloc, when it fails to find a memory
	 * block of the requested size.
	 * defrags in each iteration up tp 5 fragments per memory block. for example:
	 * if the free list is: { (208,8), (100,6), (250,7), (106,8), (114,2) } when the
	 * turtle itterator will reach (100,6) the program will defrag all the fragments
	 * relevant for it. meaning in one iteration of the rabbit it will pick up both
	 * (106,8) and (114,2). for now this is limited to 5 fragments for one function
	 * call. it is possible to enlrage it by enlarging the "removals" array.
	 */
	public void defrag() {
		StdDraw.setPenColor(StdDraw.WHITE);
		StdDraw.filledRectangle(0.06, 0.95, 0.05, 0.05);
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.text(0.06, 0.95, "Defrag:");
		DrawList.drawList(this);
		sleep();

		ListIterator turtle = freeList.iterator();
		ListIterator rabbit = freeList.iterator();
		MemBlock[] removals = { new MemBlock(-1, 0), new MemBlock(-1, 0),
				new MemBlock(-1, 0), new MemBlock(-1, 0), new MemBlock(-1, 0) };

		while (turtle.current != null) {
			int turtleSum = turtle.current.block.baseAddress + turtle.current.block.length;
			int i = 0;
			while (rabbit.current != null && removals[removals.length - 1].baseAddress == -1) {
				if (rabbit.current.block.baseAddress == turtleSum) {
					turtle.current.block.length += rabbit.current.block.length; // updates the length of turtles block
					turtleSum += rabbit.current.block.length; // updates sum for further search in same loop
					removals[i] = rabbit.current.block; // stores the current block
					i++;
				}
				rabbit.next();
			}
			int j = 0;
			while (j < i) {
				freeList.remove(removals[j]);
				j++;
			}
			turtle.next();
			rabbit.current = freeList.getFirst().next;
		}

		StdDraw.setPenColor(StdDraw.WHITE);
		StdDraw.filledRectangle(0.5, 0.5, 1, 1);
		StdDraw.setPenColor(StdDraw.BLACK);
		DrawList.drawList(this);
		sleep();
	}

	public static void sleep() {
		try {
			Thread.sleep(delay); // wait for 2 second
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
