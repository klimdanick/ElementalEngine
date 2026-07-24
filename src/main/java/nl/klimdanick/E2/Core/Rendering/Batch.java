package nl.klimdanick.E2.Core.Rendering;

public abstract class Batch {
	public int priority = 0;
	protected abstract void begin();
	protected abstract void end();
	protected abstract void flush();
	protected abstract void init();
}
