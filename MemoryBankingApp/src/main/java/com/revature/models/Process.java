package com.revature.models;

public class Process {
	private int id;
	private int currentBytesHeld;
	private String processName;
	private String checksum;
	
	public Process(String processName, String checksum) {
		this.processName = processName;
		this.checksum = checksum;
	}

	public Process(int currentBytesHeld, String processName, String checksum) {
		this.currentBytesHeld = currentBytesHeld;
		this.processName = processName;
		this.checksum = checksum;
	}
	
	public Process(int currentBytesHeld, String processName, String checksum, int id) {
		super();
		this.currentBytesHeld = currentBytesHeld;
		this.processName = processName;
		this.checksum = checksum;
		this.id = id;
	}
	

	@Override
	public String toString() {
		return "Process [id=" + id + ", currentBytesHeld=" + currentBytesHeld + ", processName=" + processName
				+ ", checksum=" + checksum + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((checksum == null) ? 0 : checksum.hashCode());
		result = prime * result + currentBytesHeld;
		result = prime * result + id;
		result = prime * result + ((processName == null) ? 0 : processName.hashCode());
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
		Process other = (Process) obj;
		if (checksum == null) {
			if (other.checksum != null)
				return false;
		} else if (!checksum.equals(other.checksum))
			return false;
		if (currentBytesHeld != other.currentBytesHeld)
			return false;
		if (id != other.id)
			return false;
		if (processName == null) {
			if (other.processName != null)
				return false;
		} else if (!processName.equals(other.processName))
			return false;
		return true;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getCurrentBytesHeld() {
		return currentBytesHeld;
	}

	public void setCurrentBytesHeld(int currentBytesHeld) {
		this.currentBytesHeld = currentBytesHeld;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public String getChecksum() {
		return checksum;
	}

	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}

}
