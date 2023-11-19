package com.jromans.notes.tools.recyclerview

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.EdgeEffectFactory.DIRECTION_BOTTOM
import androidx.recyclerview.widget.RecyclerView.EdgeEffectFactory.DIRECTION_LEFT
import androidx.recyclerview.widget.RecyclerView.EdgeEffectFactory.DIRECTION_RIGHT
import androidx.recyclerview.widget.RecyclerView.EdgeEffectFactory.DIRECTION_TOP
import com.cherrydev.recyclerview.BouncyViewHolder
import com.cherrydev.recyclerview.ZeEdgeEffect

//https://medium.com/mindorks/bounce-effect-in-recyclerview-6463a7f81e5
class EdgeEffectHelper {

    companion object {
        /** The magnitude of rotation while the list is scrolled. */
        private const val SCROLL_ROTATION_MAGNITUDE = 0.25f

        /** The magnitude of rotation while the list is over-scrolled. */
        private const val OVERSCROLL_ROTATION_MAGNITUDE = -10

        /** The magnitude of translation distance while the list is over-scrolled. */
        private const val OVERSCROLL_TRANSLATION_MAGNITUDE = 0.2f

        /** The magnitude of translation distance when the list reaches the edge on fling. */
        private const val FLING_TRANSLATION_MAGNITUDE = 0.5f
    }


    fun apply(recyclerView: RecyclerView) {
        apply(recyclerView, null)
    }

    var THRESHOLD_TRANSLATION_X = 1.8
    var THRESHOLD_TRANSLATION_Y = 2.5


    fun apply(recyclerView: RecyclerView, pullInterface: PullInterface?) {

        var shouldShowDialog = false

        recyclerView.edgeEffectFactory = object : RecyclerView.EdgeEffectFactory() {
            override fun createEdgeEffect(
                recyclerView: RecyclerView,
                direction: Int
            ): ZeEdgeEffect {

                val edge = object : ZeEdgeEffect(recyclerView.context) {

                    override fun onPull(deltaDistance: Float) {
                        super.onPull(deltaDistance)
                        handlePull(deltaDistance)
                    }

                    override fun onPull(deltaDistance: Float, displacement: Float) {
                        super.onPull(deltaDistance, displacement)
                        handlePull(deltaDistance)
                    }

                    private fun handlePull(deltaDistance: Float) {
                        // This is called on every touch event while the list is scrolled with a finger.
                        // We simply update the view properties without animation.
                        val sign = getSign(direction)

                        //val rotationDelta = sign * deltaDistance * OVERSCROLL_ROTATION_MAGNITUDE
                        val translationYDelta =
                            sign * recyclerView.width * deltaDistance * OVERSCROLL_TRANSLATION_MAGNITUDE
                        val translationXDelta =
                            sign * recyclerView.height * deltaDistance * OVERSCROLL_TRANSLATION_MAGNITUDE

                        recyclerView.forEachVisibleHolder { holder: BouncyViewHolder ->
                            //holder.rotation.cancel()
                            if (direction == DIRECTION_LEFT || direction == DIRECTION_RIGHT) {
                                holder.translationX.cancel()
                                holder.itemView.translationX += translationXDelta

                            } else if (direction == DIRECTION_BOTTOM || direction == DIRECTION_TOP) {
                                holder.translationY.cancel()
                                holder.itemView.translationY += translationYDelta
                            }


                        }


                        if (translationXDelta >= THRESHOLD_TRANSLATION_X) {
                            shouldShowDialog = true
                            //Timber.d("handlePull, dir " + direction + ". trX, trY: " + translationXDelta + " " + translationYDelta)
                        } else if (translationYDelta >= THRESHOLD_TRANSLATION_Y) {
                            shouldShowDialog = true
                        }
                    }

                    override fun onRelease() {
                        super.onRelease()
                        // The finger is lifted. This is when we should start the animations to bring
                        // the view property values back to their resting states.
                        recyclerView.forEachVisibleHolder { holder: BouncyViewHolder ->
                            //holder.rotation.start()
                            if (direction == DIRECTION_LEFT || direction == DIRECTION_RIGHT) {
                                holder.translationX.start()
                            } else if (direction == DIRECTION_BOTTOM || direction == DIRECTION_TOP) {
                                holder.translationY.start()
                            }
                        }

                        if (shouldShowDialog) {
                            pullInterface?.onPull()
                        }

                        shouldShowDialog = false
                        //Timber.d("release")
                    }

                    override fun onAbsorb(velocity: Int) {
                        super.onAbsorb(velocity)
                        val sign = getSign(direction)
                        // The list has reached the edge on fling.
                        val translationVelocity = sign * velocity * FLING_TRANSLATION_MAGNITUDE
                        recyclerView.forEachVisibleHolder { holder: BouncyViewHolder ->

                            if (direction == DIRECTION_LEFT || direction == DIRECTION_RIGHT) {
                                holder.translationX
                                    .setStartVelocity(translationVelocity)
                                    .start()

                            } else if (direction == DIRECTION_BOTTOM || direction == DIRECTION_TOP) {
                                holder.translationY
                                    .setStartVelocity(translationVelocity)
                                    .start()
                            }

                        }
                    }

                    override fun setColor(color: Int) {
                        //we don't want the color to appear, so we don't allow it to be changed
                        //super.setColor(color)
                    }


                }


                return edge
            }
        }


//        rvApps.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                recyclerView.forEachVisibleHolder { holder: MyViewHolder ->
////                    holder.rotation
////                            // Update the velocity.
////                            // The velocity is calculated by the vertical scroll offset.
////                            .setStartVelocity(holder.currentVelocity - dx * SCROLL_ROTATION_MAGNITUDE)
////                            // Start the animation. This does nothing if the animation is already running.
////                            .start()
//                }
//            }
//        })
    }

    private fun getSign(direction: Int): Int {
        var sign = 1
        if (direction == DIRECTION_LEFT || direction == DIRECTION_RIGHT) {
            sign = if (direction == DIRECTION_RIGHT) -1 else 1

        } else if (direction == DIRECTION_BOTTOM || direction == DIRECTION_TOP) {
            sign = if (direction == DIRECTION_BOTTOM) -1 else 1
        }
        return sign
    }

    inline fun <reified T : RecyclerView.ViewHolder> RecyclerView.forEachVisibleHolder(action: (T) -> Unit) {
        for (i in 0 until childCount) {
            action(getChildViewHolder(getChildAt(i)) as T)
        }
    }

    interface PullInterface {
        fun onPull()
    }
}