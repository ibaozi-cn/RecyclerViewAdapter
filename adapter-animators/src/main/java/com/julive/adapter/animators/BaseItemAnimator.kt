package com.julive.adapter.animators

import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.view.View
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
import androidx.core.view.ViewCompat
import androidx.core.view.ViewPropertyAnimatorCompat
import androidx.core.view.ViewPropertyAnimatorListener
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator

abstract class BaseItemAnimator : SimpleItemAnimator() {

    private val mPendingRemovals: ArrayList<RecyclerView.ViewHolder> = ArrayList()
    private val mPendingAdditions: ArrayList<RecyclerView.ViewHolder> = ArrayList()
    private val mPendingMoves: ArrayList<MoveInfo> = ArrayList()
    private val mPendingChanges: ArrayList<ChangeInfo> = ArrayList()

    private val mAdditionsList: ArrayList<ArrayList<RecyclerView.ViewHolder>> = ArrayList()
    private val mMovesList: ArrayList<ArrayList<MoveInfo>> = ArrayList()
    private val mChangesList: ArrayList<ArrayList<ChangeInfo>> = ArrayList()

    private val mAddAnimations: ArrayList<RecyclerView.ViewHolder> = ArrayList()
    private val mMoveAnimations: ArrayList<RecyclerView.ViewHolder> = ArrayList()
    private val mRemoveAnimations: ArrayList<RecyclerView.ViewHolder> = ArrayList()
    private val mChangeAnimations: ArrayList<RecyclerView.ViewHolder> = ArrayList()

    protected var mInterpolator: Interpolator = LinearInterpolator()
    private var mDefaultInterpolator: TimeInterpolator? = null

    private class MoveInfo(
        var holder: RecyclerView.ViewHolder,
        var fromX: Int = 0,
        var fromY: Int = 0,
        var toX: Int = 0,
        var toY: Int = 0
    )

    class ChangeInfo(
        var oldHolder: RecyclerView.ViewHolder?,
        var newHolder: RecyclerView.ViewHolder?,
        var fromX: Int = 0,
        var fromY: Int = 0,
        var toX: Int = 0,
        var toY: Int = 0
    )

    override fun runPendingAnimations() {
        val removalsPending = mPendingRemovals.isNotEmpty()
        val movesPending = mPendingMoves.isNotEmpty()
        val changesPending = mPendingChanges.isNotEmpty()
        val additionsPending = mPendingAdditions.isNotEmpty()
        if (!removalsPending && !movesPending && !additionsPending && !changesPending) {
            // nothing to animate
            return
        }
        // First, remove stuff
        for (holder in mPendingRemovals) {
            animateRemoveImpl(holder)
        }
        mPendingRemovals.clear()
        // Next, move stuff
        if (movesPending) {
            val moves: ArrayList<MoveInfo> = ArrayList()
            moves.addAll(mPendingMoves)
            mMovesList.add(moves)
            mPendingMoves.clear()
            val mover = Runnable {
                for (moveInfo in moves) {
                    animateMoveImpl(
                        moveInfo.holder, moveInfo.fromX, moveInfo.fromY,
                        moveInfo.toX, moveInfo.toY
                    )
                }
                moves.clear()
                mMovesList.remove(moves)
            }
            if (removalsPending) {
                val view: View = moves[0].holder.itemView
                ViewCompat.postOnAnimationDelayed(view, mover, 0)
            } else {
                mover.run()
            }
        }
        // Next, change stuff, to run in parallel with move animations
        if (changesPending) {
            val changes: ArrayList<ChangeInfo> = ArrayList()
            changes.addAll(mPendingChanges)
            mChangesList.add(changes)
            mPendingChanges.clear()
            val changer = Runnable {
                for (change in changes) {
                    animateChangeImpl(change)
                }
                changes.clear()
                mChangesList.remove(changes)
            }
            if (removalsPending) {
                val holder =
                    changes[0].oldHolder
                val moveDuration = if (movesPending) moveDuration else 0
                holder?.itemView?.let {
                    ViewCompat.postOnAnimationDelayed(
                        it,
                        changer,
                        getRemoveDelay(removeDuration, moveDuration, changeDuration)
                    )
                }
            } else {
                changer.run()
            }
        }
        // Next, add stuff
        if (additionsPending) {
            val additions: ArrayList<RecyclerView.ViewHolder> =
                ArrayList()
            additions.addAll(mPendingAdditions)
            mAdditionsList.add(additions)
            mPendingAdditions.clear()
            val adder = Runnable {
                for (holder in additions) {
                    animateAddImpl(holder)
                }
                additions.clear()
                mAdditionsList.remove(additions)
            }
            if (removalsPending || movesPending || changesPending) {
                val removeDuration = if (removalsPending) removeDuration else 0
                val moveDuration = if (movesPending) moveDuration else 0
                val changeDuration = if (changesPending) changeDuration else 0
                val view: View = additions[0].itemView
                ViewCompat.postOnAnimationDelayed(
                    view,
                    adder,
                    getAddDelay(removeDuration, moveDuration, changeDuration)
                )
            } else {
                adder.run()
            }
        }
    }

    open fun getRemoveDelay(
        remove: Long,
        move: Long,
        change: Long
    ): Long {
        return remove + move.coerceAtLeast(change)
    }

    open fun getAddDelay(remove: Long, move: Long, change: Long): Long {
        return remove + move.coerceAtLeast(change)
    }

    override fun animateRemove(holder: RecyclerView.ViewHolder): Boolean {
        resetAnimation(holder)
        preAnimateRemove(holder)
        mPendingRemovals.add(holder)
        return true
    }

    private fun preAnimateRemove(holder: RecyclerView.ViewHolder) {
        clear(holder.itemView)
        if (holder is AnimatorViewHolder) {
            holder.preRemoveAnimation(holder)
        } else {
            preRemoveAnimation(holder)
        }
    }

    private fun animateRemoveImpl(holder: RecyclerView.ViewHolder) {
        val animation: ViewPropertyAnimatorCompat =
            if (holder is AnimatorViewHolder)
                holder.removeAnimation(holder)
            else
                removeAnimation(holder)
        mRemoveAnimations.add(holder)
        animation.setListener(object : VpaListenerAdapter() {
            override fun onAnimationStart(view: View?) {
                dispatchRemoveStarting(holder)
            }

            override fun onAnimationEnd(view: View?) {
                animation.setListener(null)
                removeAnimationCleanup(holder)
                dispatchRemoveFinished(holder)
                mRemoveAnimations.remove(holder)
                dispatchFinishedWhenDone()
            }
        }).start()
    }

    open fun preRemoveAnimation(holder: RecyclerView.ViewHolder) {}

    abstract fun removeAnimation(holder: RecyclerView.ViewHolder): ViewPropertyAnimatorCompat

    open fun removeAnimationCleanup(holder: RecyclerView.ViewHolder) {
        clear(holder.itemView)
    }

    override fun animateAdd(holder: RecyclerView.ViewHolder): Boolean {
        resetAnimation(holder)
        addAnimationPrepare(holder)
        mPendingAdditions.add(holder)
        return true
    }

    private fun animateAddImpl(holder: RecyclerView.ViewHolder) {
        val animation: ViewPropertyAnimatorCompat =
            if (holder is AnimatorViewHolder) holder.addAnimation(holder) else addAnimation(holder)
        mAddAnimations.add(holder)
        animation.setListener(object : VpaListenerAdapter() {
            override fun onAnimationStart(view: View?) {
                dispatchAddStarting(holder)
            }

            override fun onAnimationCancel(view: View?) {
                addAnimationCleanup(holder)
            }

            override fun onAnimationEnd(view: View?) {
                animation.setListener(null)
                dispatchAddFinished(holder)
                mAddAnimations.remove(holder)
                dispatchFinishedWhenDone()
                addAnimationCleanup(holder)
            }
        }).start()
    }

    private fun addAnimationPrepare(holder: RecyclerView.ViewHolder) {
        if (holder is AnimatorViewHolder) {
            holder.preAddAnimation(holder)
        } else {
            preAddAnimation(holder)
        }
    }

    open fun preAddAnimation(holder: RecyclerView.ViewHolder) {}

    abstract fun addAnimation(holder: RecyclerView.ViewHolder): ViewPropertyAnimatorCompat

    open fun addAnimationCleanup(holder: RecyclerView.ViewHolder) {
        clear(holder.itemView)
    }

    override fun animateMove(
        holder: RecyclerView.ViewHolder, fromX: Int, fromY: Int,
        toX: Int, toY: Int
    ): Boolean {
        var x = fromX
        var y = fromY
        val view = holder.itemView
        x += ViewCompat.getTranslationX(holder.itemView).toInt()
        y += ViewCompat.getTranslationY(holder.itemView).toInt()
        resetAnimation(holder)
        val deltaX = toX - fromX
        val deltaY = toY - fromY
        if (deltaX == 0 && deltaY == 0) {
            dispatchMoveFinished(holder)
            return false
        }
        if (deltaX != 0) {
            ViewCompat.setTranslationX(view, -deltaX.toFloat())
        }
        if (deltaY != 0) {
            ViewCompat.setTranslationY(view, -deltaY.toFloat())
        }
        mPendingMoves.add(MoveInfo(holder, fromX, fromY, toX, toY))
        return true
    }

    private fun animateMoveImpl(
        holder: RecyclerView.ViewHolder,
        fromX: Int,
        fromY: Int,
        toX: Int,
        toY: Int
    ) {
        val view = holder.itemView
        val deltaX = toX - fromX
        val deltaY = toY - fromY
        if (deltaX != 0) {
            ViewCompat.animate(view).translationX(0f)
        }
        if (deltaY != 0) {
            ViewCompat.animate(view).translationY(0f)
        }
        val animation = ViewCompat.animate(view)
        mMoveAnimations.add(holder)
        animation.setDuration(moveDuration).setListener(object : VpaListenerAdapter() {
            override fun onAnimationStart(view: View?) {
                dispatchMoveStarting(holder)
            }

            override fun onAnimationCancel(view: View?) {
                if (deltaX != 0) {
                    ViewCompat.setTranslationX(view, 0f)
                }
                if (deltaY != 0) {
                    ViewCompat.setTranslationY(view, 0f)
                }
            }

            override fun onAnimationEnd(view: View?) {
                animation.setListener(null)
                dispatchMoveFinished(holder)
                mMoveAnimations.remove(holder)
                dispatchFinishedWhenDone()
            }
        }).start()
    }

    override fun animateChange(
        oldHolder: RecyclerView.ViewHolder,
        newHolder: RecyclerView.ViewHolder,
        fromX: Int,
        fromY: Int,
        toX: Int,
        toY: Int
    ): Boolean {
        if (oldHolder === newHolder) {
            return animateMove(oldHolder, fromX, fromY, toX, toY)
        }
        changeAnimation(
            oldHolder, newHolder,
            fromX, fromY, toX, toY
        )
        mPendingChanges.add(ChangeInfo(oldHolder, newHolder, fromX, fromY, toX, toY))
        return true
    }

    private fun animateChangeImpl(changeInfo: ChangeInfo) {
        val holder = changeInfo.oldHolder
        val view = holder?.itemView
        val newHolder = changeInfo.newHolder
        val newView = newHolder?.itemView
        if (view != null) {
            val oldViewAnim: ViewPropertyAnimatorCompat = changeOldAnimation(holder, changeInfo)
            changeInfo.oldHolder?.let {
                mChangeAnimations.add(it)
            }
            oldViewAnim.setListener(object : VpaListenerAdapter() {
                override fun onAnimationStart(view: View?) {
                    dispatchChangeStarting(changeInfo.oldHolder, true)
                }

                override fun onAnimationEnd(view: View?) {
                    oldViewAnim.setListener(null)
                    changeAnimationCleanup(holder)
                    ViewCompat.setTranslationX(view, 0f)
                    ViewCompat.setTranslationY(view, 0f)
                    changeInfo.oldHolder?.let {
                        dispatchChangeFinished(it, true)
                        mChangeAnimations.remove(it)
                    }
                    dispatchFinishedWhenDone()
                }
            }).start()
        }
        if (newView != null) {
            val newViewAnimation: ViewPropertyAnimatorCompat =
                changeNewAnimation(newHolder)
            changeInfo.newHolder?.let {
                mChangeAnimations.add(it)
            }
            newViewAnimation.setListener(object : VpaListenerAdapter() {
                override fun onAnimationStart(view: View?) {
                    dispatchChangeStarting(changeInfo.newHolder, false)
                }

                override fun onAnimationEnd(view: View?) {
                    newViewAnimation.setListener(null)
                    changeAnimationCleanup(newHolder)
                    ViewCompat.setTranslationX(newView, 0f)
                    ViewCompat.setTranslationY(newView, 0f)
                    changeInfo.newHolder?.let {
                        dispatchChangeFinished(it, false)
                        mChangeAnimations.remove(it)
                    }
                    dispatchFinishedWhenDone()
                }
            }).start()
        }
    }

    open fun changeAnimation(
        oldHolder: RecyclerView.ViewHolder,
        newHolder: RecyclerView.ViewHolder?,
        fromX: Int,
        fromY: Int,
        toX: Int,
        toY: Int
    ) {
        val prevTranslationX =
            ViewCompat.getTranslationX(oldHolder.itemView)
        val prevTranslationY =
            ViewCompat.getTranslationY(oldHolder.itemView)
        val prevValue = ViewCompat.getAlpha(oldHolder.itemView)
        resetAnimation(oldHolder)
        val deltaX = (toX - fromX - prevTranslationX).toInt()
        val deltaY = (toY - fromY - prevTranslationY).toInt()
        // recover prev translation state after ending animation
        ViewCompat.setTranslationX(oldHolder.itemView, prevTranslationX)
        ViewCompat.setTranslationY(oldHolder.itemView, prevTranslationY)
        ViewCompat.setAlpha(oldHolder.itemView, prevValue)
        if (newHolder != null) {
            // carry over translation values
            resetAnimation(newHolder)
            ViewCompat.setTranslationX(newHolder.itemView, -deltaX.toFloat())
            ViewCompat.setTranslationY(newHolder.itemView, -deltaY.toFloat())
            ViewCompat.setAlpha(newHolder.itemView, 0f)
        }
    }

    open fun changeOldAnimation(
        holder: RecyclerView.ViewHolder,
        changeInfo: ChangeInfo
    ): ViewPropertyAnimatorCompat = ViewCompat.animate(holder.itemView)
        .setDuration(changeDuration).alpha(0f)
        .translationX(changeInfo.toX - changeInfo.fromX.toFloat())
        .translationY(changeInfo.toY - changeInfo.fromY.toFloat())
        .setInterpolator(mInterpolator)

    open fun changeNewAnimation(holder: RecyclerView.ViewHolder): ViewPropertyAnimatorCompat =
        ViewCompat.animate(holder.itemView).translationX(0f)
            .translationY(0f).setDuration(changeDuration).alpha(1f)
            .setInterpolator(mInterpolator)

    open fun changeAnimationCleanup(holder: RecyclerView.ViewHolder?) {
        holder?.itemView?.apply {
            clear(this)
        }
    }

    private fun endChangeAnimation(
        infoList: MutableList<ChangeInfo>,
        item: RecyclerView.ViewHolder
    ) {
        for (i in infoList.indices.reversed()) {
            val changeInfo =
                infoList[i]
            if (endChangeAnimationIfNecessary(changeInfo, item)) {
                if (changeInfo.oldHolder == null && changeInfo.newHolder == null) {
                    infoList.remove(changeInfo)
                }
            }
        }
    }

    private fun endChangeAnimationIfNecessary(changeInfo: ChangeInfo) {
        if (changeInfo.oldHolder != null) {
            endChangeAnimationIfNecessary(changeInfo, changeInfo.oldHolder)
        }
        if (changeInfo.newHolder != null) {
            endChangeAnimationIfNecessary(changeInfo, changeInfo.newHolder)
        }
    }

    private fun endChangeAnimationIfNecessary(
        changeInfo: ChangeInfo,
        viewholder: RecyclerView.ViewHolder?
    ): Boolean {
        var oldItem = false
        when {
            changeInfo.newHolder === viewholder -> {
                changeInfo.newHolder = null
            }
            changeInfo.oldHolder === viewholder -> {
                changeInfo.oldHolder = null
                oldItem = true
            }
            else -> {
                return false
            }
        }
        changeAnimationCleanup(viewholder)
        ViewCompat.setTranslationX(viewholder?.itemView, 0f)
        ViewCompat.setTranslationY(viewholder?.itemView, 0f)
        dispatchChangeFinished(viewholder, oldItem)
        return true
    }

    override fun endAnimation(item: RecyclerView.ViewHolder) {
        val view = item.itemView
        ViewCompat.animate(view).cancel()
        // TODO if some other animations are chained to end, how do we cancel them as well?
        for (i in mPendingMoves.indices.reversed()) {
            val moveInfo = mPendingMoves[i]
            if (moveInfo.holder === item) {
                ViewCompat.setTranslationY(view, 0f)
                ViewCompat.setTranslationX(view, 0f)
                dispatchMoveFinished(item)
                mPendingMoves.removeAt(i)
            }
        }
        endChangeAnimation(mPendingChanges, item)
        if (mPendingRemovals.remove(item)) {
            removeAnimationCleanup(item)
            dispatchRemoveFinished(item)
        }
        if (mPendingAdditions.remove(item)) {
            addAnimationCleanup(item)
            dispatchAddFinished(item)
        }
        for (i in mChangesList.indices.reversed()) {
            val changes =
                mChangesList[i]
            endChangeAnimation(changes, item)
            if (changes.isEmpty()) {
                mChangesList.removeAt(i)
            }
        }
        for (i in mMovesList.indices.reversed()) {
            val moves = mMovesList[i]
            for (j in moves.size - 1 downTo 0) {
                val moveInfo = moves[j]
                if (moveInfo.holder === item) {
                    ViewCompat.setTranslationY(view, 0f)
                    ViewCompat.setTranslationX(view, 0f)
                    dispatchMoveFinished(item)
                    moves.removeAt(j)
                    if (moves.isEmpty()) {
                        mMovesList.removeAt(i)
                    }
                    break
                }
            }
        }
        for (i in mAdditionsList.indices.reversed()) {
            val additions =
                mAdditionsList[i]
            if (additions.remove(item)) {
                addAnimationCleanup(item)
                dispatchAddFinished(item)
                if (additions.isEmpty()) {
                    mAdditionsList.removeAt(i)
                }
            }
        }
        dispatchFinishedWhenDone()
    }

    open fun resetAnimation(holder: RecyclerView.ViewHolder) {
        if (mDefaultInterpolator == null) {
            mDefaultInterpolator = ValueAnimator().interpolator
        }
        holder.itemView.animate().interpolator = mDefaultInterpolator
        endAnimation(holder)
    }

    override fun isRunning(): Boolean {
        return mPendingAdditions.isNotEmpty() ||
                mPendingChanges.isNotEmpty() ||
                mPendingMoves.isNotEmpty() ||
                mPendingRemovals.isNotEmpty() ||
                mMoveAnimations.isNotEmpty() ||
                mRemoveAnimations.isNotEmpty() ||
                mAddAnimations.isNotEmpty() ||
                mChangeAnimations.isNotEmpty() ||
                mMovesList.isNotEmpty() ||
                mAdditionsList.isNotEmpty() ||
                mChangesList.isNotEmpty()
    }

    private fun dispatchFinishedWhenDone() {
        if (!isRunning) {
            dispatchAnimationsFinished()
        }
    }

    override fun endAnimations() {
        var count = mPendingMoves.size
        for (i in count - 1 downTo 0) {
            val item = mPendingMoves[i]
            val view = item.holder.itemView
            ViewCompat.setTranslationY(view, 0f)
            ViewCompat.setTranslationX(view, 0f)
            dispatchMoveFinished(item.holder)
            mPendingMoves.removeAt(i)
        }
        count = mPendingRemovals.size
        for (i in count - 1 downTo 0) {
            val item = mPendingRemovals[i]
            dispatchRemoveFinished(item)
            mPendingRemovals.removeAt(i)
        }
        count = mPendingAdditions.size
        for (i in count - 1 downTo 0) {
            val item =
                mPendingAdditions[i]
            val view = item.itemView
            addAnimationCleanup(item)
            dispatchAddFinished(item)
            mPendingAdditions.removeAt(i)
        }
        count = mPendingChanges.size
        for (i in count - 1 downTo 0) {
            endChangeAnimationIfNecessary(mPendingChanges[i])
        }
        mPendingChanges.clear()
        if (!isRunning) {
            return
        }
        var listCount = mMovesList.size
        for (i in listCount - 1 downTo 0) {
            val moves = mMovesList[i]
            count = moves.size
            for (j in count - 1 downTo 0) {
                val moveInfo = moves[j]
                val item = moveInfo.holder
                val view = item.itemView
                ViewCompat.setTranslationY(view, 0f)
                ViewCompat.setTranslationX(view, 0f)
                dispatchMoveFinished(moveInfo.holder)
                moves.removeAt(j)
                if (moves.isEmpty()) {
                    mMovesList.remove(moves)
                }
            }
        }
        listCount = mAdditionsList.size
        for (i in listCount - 1 downTo 0) {
            val additions =
                mAdditionsList[i]
            count = additions.size
            for (j in count - 1 downTo 0) {
                val item = additions[j]
                val view = item.itemView
                addAnimationCleanup(item)
                dispatchAddFinished(item)
                additions.removeAt(j)
                if (additions.isEmpty()) {
                    mAdditionsList.remove(additions)
                }
            }
        }
        listCount = mChangesList.size
        for (i in listCount - 1 downTo 0) {
            val changes =
                mChangesList[i]
            count = changes.size
            for (j in count - 1 downTo 0) {
                endChangeAnimationIfNecessary(changes[j])
                if (changes.isEmpty()) {
                    mChangesList.remove(changes)
                }
            }
        }
        cancelAll(mRemoveAnimations)
        cancelAll(mMoveAnimations)
        cancelAll(mAddAnimations)
        cancelAll(mChangeAnimations)
        dispatchAnimationsFinished()
    }

    open fun cancelAll(viewHolders: List<RecyclerView.ViewHolder>) {
        for (i in viewHolders.indices.reversed()) {
            ViewCompat.animate(viewHolders[i].itemView).cancel()
        }
    }

    /**
     * {@inheritDoc}
     */
    override fun canReuseUpdatedViewHolder(
        viewHolder: RecyclerView.ViewHolder,
        payloads: List<Any?>
    ): Boolean {
        return payloads.isNotEmpty() || super.canReuseUpdatedViewHolder(viewHolder, payloads)
    }

    private open class VpaListenerAdapter : ViewPropertyAnimatorListener {
        override fun onAnimationStart(view: View?) {}
        override fun onAnimationEnd(view: View?) {}
        override fun onAnimationCancel(view: View?) {}
    }

    private fun clear(v: View) {
        v.alpha = 1f
        v.scaleY = 1f
        v.scaleX = 1f
        v.translationY = 0f
        v.translationX = 0f
        v.rotation = 0f
        v.rotationY = 0f
        v.rotationX = 0f
        v.pivotY = v.measuredHeight / 2.toFloat()
        v.pivotX = v.measuredWidth / 2.toFloat()
        v.animate().setInterpolator(null).startDelay = 0
    }
}