package org.hongxi.summer.common;

/**
 * future task state
 * 
 * Created by shenhongxi on 2020/8/23.
 *
 */
public enum FutureState {
	/** the task is doing **/
	DOING(0),
	/** the task is done **/
	DONE(1),
	/** ths task is cancelled **/
	CANCELLED(2);

	public final int value;

	FutureState(int value) {
		this.value = value;
	}

	public boolean isCancelledState() {
		return this == CANCELLED;
	}

	public boolean isDoneState() {
		return this == DONE;
	}

	public boolean isDoingState() {
		return this == DOING;
	}
}
