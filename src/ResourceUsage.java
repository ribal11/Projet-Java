import java.io.Serializable;

public abstract class ResourceUsage extends MyObservable
		implements Comparable<ResourceUsage>, Serializable, MyObserver {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static int next = 1;
	int id;

	public ResourceUsage() {
		this.id = next++;
	}

	public int compareTo(ResourceUsage r) {
		return Integer.compare(this.id, r.id);
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (!(obj instanceof ResourceUsage))
			return false;

		if (obj instanceof HumanResourceUsage) {
			HumanResourceUsage human = (HumanResourceUsage) obj;
			if (this.id == human.id)
				return true;
		}

		if (obj instanceof MaterialUsage) {
			MaterialUsage material = (MaterialUsage) obj;
			if (this.id == material.id)
				return true;
		}
		return false;

	}

	protected abstract String toComboBoxString();

	public abstract void update();

}
