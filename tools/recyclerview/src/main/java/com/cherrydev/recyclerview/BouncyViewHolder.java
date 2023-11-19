package com.cherrydev.recyclerview;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import androidx.recyclerview.widget.RecyclerView;

public class BouncyViewHolder extends RecyclerView.ViewHolder {

    public float currentVelocity = 0F;
    public SpringAnimation rotation = new SpringAnimation(itemView, SpringAnimation.ROTATION)
            .setSpring(new SpringForce()
                    .setFinalPosition(0f)
                    .setDampingRatio(SpringForce.DAMPING_RATIO_HIGH_BOUNCY)
                    .setStiffness(SpringForce.STIFFNESS_LOW)
            )
            .addUpdateListener((animation, value, velocity) ->
                    currentVelocity = velocity);


    public SpringAnimation translationY = new SpringAnimation(itemView, DynamicAnimation.TRANSLATION_Y)
            .setSpring(new SpringForce()
                    .setFinalPosition(0f)
                    .setDampingRatio(SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY)
                    .setStiffness(SpringForce.STIFFNESS_LOW)
            );

    public SpringAnimation translationX = new SpringAnimation(itemView, DynamicAnimation.TRANSLATION_X)
            .setSpring(new SpringForce()
                    .setFinalPosition(0f)
                    .setDampingRatio(SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY)
                    .setStiffness(SpringForce.STIFFNESS_LOW)
            );


    public BouncyViewHolder(@NonNull View itemView) {
        super(itemView);
    }
}
