package com.revature.models;

public class MemoryBank {
	private int id;
	private int stackBytesAvailable;
	private int heapBytesAvailable;

	public MemoryBank(int heapBytesAvailable, int stackBytesAvailable) {
		this.heapBytesAvailable = heapBytesAvailable;
		this.stackBytesAvailable = stackBytesAvailable;
	}
	
	public MemoryBank(int heapBytesAvailable, int stackBytesAvailable, int id) {
		super();
		this.id = id;
		this.heapBytesAvailable = heapBytesAvailable;
		this.stackBytesAvailable = stackBytesAvailable;
	}

	@Override
	public String toString() {
		return "MemoryBank [id=" + id + ", stackBytesAvailable=" + stackBytesAvailable + ", heapBytesAvailable="
				+ heapBytesAvailable + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + heapBytesAvailable;
		result = prime * result + id;
		result = prime * result + stackBytesAvailable;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MemoryBank other = (MemoryBank) obj;
		if (heapBytesAvailable != other.heapBytesAvailable)
			return false;
		if (id != other.id)
			return false;
		if (stackBytesAvailable != other.stackBytesAvailable)
			return false;
		return true;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getStackBytesAvailable() {
		return stackBytesAvailable;
	}

	public void setStackBytesAvailable(int stackBytesAvailable) {
		this.stackBytesAvailable = stackBytesAvailable;
	}

	public int getHeapBytesAvailable() {
		return heapBytesAvailable;
	}

	public void setHeapBytesAvailable(int heapBytesAvailable) {
		this.heapBytesAvailable = heapBytesAvailable;
	}
	
}
