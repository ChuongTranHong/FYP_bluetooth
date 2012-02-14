package com.piezo.util;

import java.util.Collection;
import java.util.LinkedList;

import com.piezo.screen.RunningScreen;

import ioio.lib.api.IOIO;
import ioio.lib.api.IOIOFactory;
import ioio.lib.api.exception.ConnectionLostException;
import ioio.lib.api.exception.IncompatibilityException;
import ioio.lib.util.IOIOConnectionDiscovery;
//import ioio.lib.util.AbstractIOIOActivity.IOIOThread;
import ioio.lib.util.IOIOConnectionDiscovery.IOIOConnectionSpec;
import android.os.Looper;
import android.util.Log;

public abstract class IOIOThread extends Thread {
	/** Subclasses should use this field for controlling the IOIO. */
	private static final String TAG = "AbstractIOIOActivity";
	protected IOIO ioio_;
	private boolean abort_ = false;
	private boolean connected_ = true;
	protected final IOIOConnectionSpec spec_=RunningScreen.currentSpec_;
	

	/**
	 * Subclasses should override this method for performing operations to
	 * be done once as soon as IOIO communication is established. Typically,
	 * this will include opening pins and modules using the openXXX()
	 * methods of the {@link #ioio_} field.
	 */
	protected void setup() throws ConnectionLostException,
			InterruptedException {
	}

	/**
	 * Subclasses should override this method for performing operations to
	 * be done repetitively as long as IOIO communication persists.
	 * Typically, this will be the main logic of the application, processing
	 * inputs and producing outputs.
	 */
	protected void loop() throws ConnectionLostException,
			InterruptedException {
		sleep(100000);
	}

	/**
	 * Subclasses should override this method for performing operations to
	 * be done once as soon as IOIO communication is lost or closed.
	 * Typically, this will include GUI changes corresponding to the change.
	 * This method will only be called if setup() has been called. The
	 * {@link #ioio_} member must not be used from within this method.
	 */
	protected void disconnected() throws InterruptedException {
	}

	/**
	 * Subclasses should override this method for performing operations to
	 * be done if an incompatible IOIO firmware is detected. The
	 * {@link #ioio_} member must not be used from within this method. This
	 * method will only be called once, until a compatible IOIO is connected
	 * (i.e. {@link #setup()} gets called).
	 */
	protected void incompatible() {
	}

	/** Not relevant to subclasses. */
	@Override
	public final void run() {
		super.run();
		Looper.prepare();
		while (true) {
			try {
				synchronized (this) {
					if (abort_) {
						break;
					}
					ioio_ = IOIOFactory.create(spec_.className, spec_.args);
				}

				ioio_.waitForConnect();

				connected_ = true;
				
				setup();
				while (!abort_) {
					loop();
				}
				ioio_.disconnect();
			} catch (ConnectionLostException e) {
				RunningScreen.piezoSignal = false;
				if (abort_) {
					break;
				}
			} catch (InterruptedException e) {
				
				ioio_.disconnect();
				break;
			} catch (IncompatibilityException e) {
				Log.e(TAG, "Incompatible IOIO firmware", e);
				incompatible();
				// nothing to do - just wait until physical disconnection
				try {
					ioio_.waitForDisconnect();
				} catch (InterruptedException e1) {
					ioio_.disconnect();
				}
			} catch (Exception e) {
				System.out.println("in exception handler");
				Log.e(TAG, "Unexpected exception caught", e);
				ioio_.disconnect();
				break;
			} finally {
				
				try {
					if (ioio_ != null) {
						ioio_.waitForDisconnect();
						if (connected_) {
							disconnected();
						}
					}
				} catch (InterruptedException e) {
				}
			}
		}
	}

	/** Not relevant to subclasses. */
	public synchronized final void abort() {
		abort_ = true;
		if (ioio_ != null) {
			ioio_.disconnect();
		}
		if (connected_) {
			interrupt();
		}
	}
}

