package com.het.sdk.demo.utils;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;

import java.lang.ref.WeakReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * -----------------------------------------------------------------
 * Copyright (C) 2014-2017, by het, Shenzhen, All rights reserved.
 * -----------------------------------------------------------------
 * 作者: liuzh<br>
 * 版本: 2.0<br>
 * 日期: 2017-11-13<br>
 * 描述:
 **/
public class WeakHandler {
    private final Handler.Callback mCallback;
    private final WeakHandler.ExecHandler mExec;
    private Lock mLock = new ReentrantLock();
    @VisibleForTesting
    final WeakHandler.ChainedRef mRunnables;

    public WeakHandler() {
        this.mRunnables = new WeakHandler.ChainedRef(this.mLock, (Runnable) null);
        this.mCallback = null;
        this.mExec = new WeakHandler.ExecHandler();
    }

    public WeakHandler(@Nullable Handler.Callback callback) {
        this.mRunnables = new WeakHandler.ChainedRef(this.mLock, (Runnable) null);
        this.mCallback = callback;
        this.mExec = new WeakHandler.ExecHandler(new WeakReference(callback));
    }

    public WeakHandler(@NonNull Looper looper) {
        this.mRunnables = new WeakHandler.ChainedRef(this.mLock, (Runnable) null);
        this.mCallback = null;
        this.mExec = new WeakHandler.ExecHandler(looper);
    }

    public WeakHandler(@NonNull Looper looper, @NonNull Handler.Callback callback) {
        this.mRunnables = new WeakHandler.ChainedRef(this.mLock, (Runnable) null);
        this.mCallback = callback;
        this.mExec = new WeakHandler.ExecHandler(looper, new WeakReference(callback));
    }

    public final boolean post(@NonNull Runnable r) {
        return this.mExec.post(this.wrapRunnable(r));
    }

    public final boolean postAtTime(@NonNull Runnable r, long uptimeMillis) {
        return this.mExec.postAtTime(this.wrapRunnable(r), uptimeMillis);
    }

    public final boolean postAtTime(Runnable r, Object token, long uptimeMillis) {
        return this.mExec.postAtTime(this.wrapRunnable(r), token, uptimeMillis);
    }

    public final boolean postDelayed(Runnable r, long delayMillis) {
        return this.mExec.postDelayed(this.wrapRunnable(r), delayMillis);
    }

    public final boolean postAtFrontOfQueue(Runnable r) {
        return this.mExec.postAtFrontOfQueue(this.wrapRunnable(r));
    }

    public final void removeCallbacks(Runnable r) {
        WeakHandler.WeakRunnable runnable = this.mRunnables.remove(r);
        if (runnable != null) {
            this.mExec.removeCallbacks(runnable);
        }

    }

    public final void removeCallbacks(Runnable r, Object token) {
        WeakHandler.WeakRunnable runnable = this.mRunnables.remove(r);
        if (runnable != null) {
            this.mExec.removeCallbacks(runnable, token);
        }

    }

    public final boolean sendMessage(Message msg) {
        return this.mExec.sendMessage(msg);
    }

    public final boolean sendEmptyMessage(int what) {
        return this.mExec.sendEmptyMessage(what);
    }

    public final Message obtainMessage(int what) {
        return this.mExec.obtainMessage(what);
    }

    public final Message obtainMessage() {
        return this.mExec.obtainMessage();
    }

    public final Message obtainMessage(int what, int arg1, int arg2) {
        return this.mExec.obtainMessage(what, arg1, arg2);
    }

    public final Message obtainMessage(int what, Object object) {
        return this.mExec.obtainMessage(what, object);
    }

    public final Message obtainMessage(int what, int arg1, int arg2, Object object) {
        return this.mExec.obtainMessage(what, arg1, arg2, object);
    }

    public final boolean sendEmptyMessageDelayed(int what, long delayMillis) {
        return this.mExec.sendEmptyMessageDelayed(what, delayMillis);
    }

    public final boolean sendEmptyMessageAtTime(int what, long uptimeMillis) {
        return this.mExec.sendEmptyMessageAtTime(what, uptimeMillis);
    }

    public final boolean sendMessageDelayed(Message msg, long delayMillis) {
        return this.mExec.sendMessageDelayed(msg, delayMillis);
    }

    public boolean sendMessageAtTime(Message msg, long uptimeMillis) {
        return this.mExec.sendMessageAtTime(msg, uptimeMillis);
    }

    public final boolean sendMessageAtFrontOfQueue(Message msg) {
        return this.mExec.sendMessageAtFrontOfQueue(msg);
    }

    public final void removeMessages(int what) {
        this.mExec.removeMessages(what);
    }

    public final void removeMessages(int what, Object object) {
        this.mExec.removeMessages(what, object);
    }

    public final void removeCallbacksAndMessages(Object token) {
        this.mExec.removeCallbacksAndMessages(token);
    }

    public final boolean hasMessages(int what) {
        return this.mExec.hasMessages(what);
    }

    public final boolean hasMessages(int what, Object object) {
        return this.mExec.hasMessages(what, object);
    }

    public final Looper getLooper() {
        return this.mExec.getLooper();
    }

    private WeakHandler.WeakRunnable wrapRunnable(@NonNull Runnable r) {
        if (r == null) {
            throw new NullPointerException("Runnable can\'t be null");
        } else {
            WeakHandler.ChainedRef hardRef = new WeakHandler.ChainedRef(this.mLock, r);
            this.mRunnables.insertAfter(hardRef);
            return hardRef.wrapper;
        }
    }

    static class ChainedRef {
        @Nullable
        WeakHandler.ChainedRef next;
        @Nullable
        WeakHandler.ChainedRef prev;
        @NonNull
        final Runnable runnable;
        @NonNull
        final WeakHandler.WeakRunnable wrapper;
        @NonNull
        Lock lock;

        public ChainedRef(@NonNull Lock lock, @NonNull Runnable r) {
            this.runnable = r;
            this.lock = lock;
            this.wrapper = new WeakHandler.WeakRunnable(new WeakReference(r), new WeakReference(this));
        }

        public WeakHandler.WeakRunnable remove() {
            this.lock.lock();

            try {
                if (this.prev != null) {
                    this.prev.next = this.next;
                }

                if (this.next != null) {
                    this.next.prev = this.prev;
                }

                this.prev = null;
                this.next = null;
            } finally {
                this.lock.unlock();
            }

            return this.wrapper;
        }

        public void insertAfter(@NonNull WeakHandler.ChainedRef candidate) {
            this.lock.lock();

            try {
                if (this.next != null) {
                    this.next.prev = candidate;
                }

                candidate.next = this.next;
                this.next = candidate;
                candidate.prev = this;
            } finally {
                this.lock.unlock();
            }

        }

        @Nullable
        public WeakHandler.WeakRunnable remove(Runnable obj) {
            this.lock.lock();

            try {
                for (WeakHandler.ChainedRef curr = this.next; curr != null; curr = curr.next) {
                    if (curr.runnable == obj) {
                        WeakHandler.WeakRunnable var3 = curr.remove();
                        return var3;
                    }
                }

                return null;
            } finally {
                this.lock.unlock();
            }
        }
    }

    static class WeakRunnable implements Runnable {
        private final WeakReference<Runnable> mDelegate;
        private final WeakReference<WeakHandler.ChainedRef> mReference;

        WeakRunnable(WeakReference<Runnable> delegate, WeakReference<WeakHandler.ChainedRef> reference) {
            this.mDelegate = delegate;
            this.mReference = reference;
        }

        public void run() {
            Runnable delegate = (Runnable) this.mDelegate.get();
            WeakHandler.ChainedRef reference = (WeakHandler.ChainedRef) this.mReference.get();
            if (reference != null) {
                reference.remove();
            }

            if (delegate != null) {
                delegate.run();
            }

        }
    }

    private static class ExecHandler extends Handler {
        private final WeakReference<Callback> mCallback;

        ExecHandler() {
            this.mCallback = null;
        }

        ExecHandler(WeakReference<Callback> callback) {
            this.mCallback = callback;
        }

        ExecHandler(Looper looper) {
            super(looper);
            this.mCallback = null;
        }

        ExecHandler(Looper looper, WeakReference<Callback> callback) {
            super(looper);
            this.mCallback = callback;
        }

        public void handleMessage(@NonNull Message msg) {
            if (this.mCallback != null) {
                Callback callback = (Callback) this.mCallback.get();
                if (callback != null) {
                    callback.handleMessage(msg);
                }
            }
        }
    }
}
