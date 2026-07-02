package nl.klimdanick.E2.Core.Rendering;

public abstract class Batch {
	public int priority = 0;
	abstract void begin();
	abstract void end();
	protected abstract void flush();
	protected abstract void init();
}
