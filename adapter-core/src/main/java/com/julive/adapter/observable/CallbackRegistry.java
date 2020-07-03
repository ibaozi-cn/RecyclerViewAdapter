/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.julive.adapter.observable;

import java.util.ArrayList;
import java.util.List;

public class CallbackRegistry<C, T, A> implements Cloneable {

    private static final String TAG = "CallbackRegistry";
    private final NotifierCallback<C, T, A> mNotifier;
    private List<C> mCallbacks = new ArrayList<C>();
    private long mFirst64Removed = 0x0;
    private long[] mRemainderRemoved;
    private int mNotificationLevel;

    public CallbackRegistry(NotifierCallback<C, T, A> notifier) {
        mNotifier = notifier;
    }

    public synchronized void notifyCallbacks(T sender, int arg, A arg2) {
        mNotificationLevel++;
        notifyRecurseLocked(sender, arg, arg2);
        mNotificationLevel--;
        if (mNotificationLevel == 0) {
            if (mRemainderRemoved != null) {
                for (int i = mRemainderRemoved.length - 1; i >= 0; i--) {
                    final long removedBits = mRemainderRemoved[i];
                    if (removedBits != 0) {
                        removeRemovedCallbacks((i + 1) * Long.SIZE, removedBits);
                        mRemainderRemoved[i] = 0;
                    }
                }
            }
            if (mFirst64Removed != 0) {
                removeRemovedCallbacks(0, mFirst64Removed);
                mFirst64Removed = 0;
            }
        }
    }

    private void notifyFirst64Locked(T sender, int arg, A arg2) {
        final int maxNotified = Math.min(Long.SIZE, mCallbacks.size());
        notifyCallbacksLocked(sender, arg, arg2, 0, maxNotified, mFirst64Removed);
    }

    private void notifyRecurseLocked(T sender, int arg, A arg2) {
        final int callbackCount = mCallbacks.size();
        final int remainderIndex = mRemainderRemoved == null ? -1 : mRemainderRemoved.length - 1;

        notifyRemainderLocked(sender, arg, arg2, remainderIndex);

        final int startCallbackIndex = (remainderIndex + 2) * Long.SIZE;

        notifyCallbacksLocked(sender, arg, arg2, startCallbackIndex, callbackCount, 0);
    }

    private void notifyRemainderLocked(T sender, int arg, A arg2, int remainderIndex) {
        if (remainderIndex < 0) {
            notifyFirst64Locked(sender, arg, arg2);
        } else {
            final long bits = mRemainderRemoved[remainderIndex];
            final int startIndex = (remainderIndex + 1) * Long.SIZE;
            final int endIndex = Math.min(mCallbacks.size(), startIndex + Long.SIZE);
            notifyRemainderLocked(sender, arg, arg2, remainderIndex - 1);
            notifyCallbacksLocked(sender, arg, arg2, startIndex, endIndex, bits);
        }
    }

    private void notifyCallbacksLocked(T sender, int arg, A arg2, final int startIndex,
                                       final int endIndex, final long bits) {
        long bitMask = 1;
        for (int i = startIndex; i < endIndex; i++) {
            if ((bits & bitMask) == 0) {
                mNotifier.onNotifyCallback(mCallbacks.get(i), sender, arg, arg2);
            }
            bitMask <<= 1;
        }
    }

    public synchronized void add(C callback) {
        int index = mCallbacks.lastIndexOf(callback);
        if (index < 0 || isRemovedLocked(index)) {
            mCallbacks.add(callback);
        }
    }

    private boolean isRemovedLocked(int index) {
        if (index < Long.SIZE) {
            // It is in the first 64 callbacks, just check the bit.
            final long bitMask = 1L << index;
            return (mFirst64Removed & bitMask) != 0;
        } else if (mRemainderRemoved == null) {
            // It is after the first 64 callbacks, but nothing else was marked for removal.
            return false;
        } else {
            final int maskIndex = (index / Long.SIZE) - 1;
            if (maskIndex >= mRemainderRemoved.length) {
                // There are some items in mRemainderRemoved, but nothing at the given index.
                return false;
            } else {
                // There is something marked for removal, so we have to check the bit.
                final long bits = mRemainderRemoved[maskIndex];
                final long bitMask = 1L << (index % Long.SIZE);
                return (bits & bitMask) != 0;
            }
        }
    }

    private void removeRemovedCallbacks(int startIndex, long removed) {
        // The naive approach should be fine. There may be a better bit-twiddling approach.
        final int endIndex = startIndex + Long.SIZE;

        long bitMask = 1L << (Long.SIZE - 1);
        for (int i = endIndex - 1; i >= startIndex; i--) {
            if ((removed & bitMask) != 0) {
                mCallbacks.remove(i);
            }
            bitMask >>>= 1;
        }
    }

    public synchronized void remove(C callback) {
        if (mNotificationLevel == 0) {
            mCallbacks.remove(callback);
        } else {
            int index = mCallbacks.lastIndexOf(callback);
            if (index >= 0) {
                setRemovalBitLocked(index);
            }
        }
    }

    private void setRemovalBitLocked(int index) {
        if (index < Long.SIZE) {
            // It is in the first 64 callbacks, just check the bit.
            final long bitMask = 1L << index;
            mFirst64Removed |= bitMask;
        } else {
            final int remainderIndex = (index / Long.SIZE) - 1;
            if (mRemainderRemoved == null) {
                mRemainderRemoved = new long[mCallbacks.size() / Long.SIZE];
            } else if (mRemainderRemoved.length < remainderIndex) {
                // need to make it bigger
                long[] newRemainders = new long[mCallbacks.size() / Long.SIZE];
                System.arraycopy(mRemainderRemoved, 0, newRemainders, 0, mRemainderRemoved.length);
                mRemainderRemoved = newRemainders;
            }
            final long bitMask = 1L << (index % Long.SIZE);
            mRemainderRemoved[remainderIndex] |= bitMask;
        }
    }

    public synchronized ArrayList<C> copyListeners() {
        ArrayList<C> callbacks = new ArrayList<C>(mCallbacks.size());
        int numListeners = mCallbacks.size();
        for (int i = 0; i < numListeners; i++) {
            if (!isRemovedLocked(i)) {
                callbacks.add(mCallbacks.get(i));
            }
        }
        return callbacks;
    }

    public synchronized boolean isEmpty() {
        if (mCallbacks.isEmpty()) {
            return true;
        } else if (mNotificationLevel == 0) {
            return false;
        } else {
            int numListeners = mCallbacks.size();
            for (int i = 0; i < numListeners; i++) {
                if (!isRemovedLocked(i)) {
                    return false;
                }
            }
            return true;
        }
    }


    public synchronized void clear() {
        if (mNotificationLevel == 0) {
            mCallbacks.clear();
        } else if (!mCallbacks.isEmpty()) {
            for (int i = mCallbacks.size() - 1; i >= 0; i--) {
                setRemovalBitLocked(i);
            }
        }
    }

    public synchronized CallbackRegistry<C, T, A> clone() {
        CallbackRegistry<C, T, A> clone = null;
        try {
            clone = (CallbackRegistry<C, T, A>) super.clone();
            clone.mFirst64Removed = 0;
            clone.mRemainderRemoved = null;
            clone.mNotificationLevel = 0;
            clone.mCallbacks = new ArrayList<C>();
            final int numListeners = mCallbacks.size();
            for (int i = 0; i < numListeners; i++) {
                if (!isRemovedLocked(i)) {
                    clone.mCallbacks.add(mCallbacks.get(i));
                }
            }
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return clone;
    }


    public abstract static class NotifierCallback<C, T, A> {

        public abstract void onNotifyCallback(C callback, T sender, int arg, A arg2);
    }
}
