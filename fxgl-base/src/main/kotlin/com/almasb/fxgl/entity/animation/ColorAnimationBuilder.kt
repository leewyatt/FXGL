/*
 * FXGL - JavaFX Game Library. The MIT License (MIT).
 * Copyright (c) AlmasB (almaslvl@gmail.com).
 * See LICENSE for details.
 */

package com.almasb.fxgl.entity.animation

import com.almasb.fxgl.animation.AnimatedColor
import com.almasb.fxgl.animation.Animation
import com.almasb.fxgl.entity.components.ColorComponent
import com.almasb.fxgl.core.util.Consumer
import javafx.scene.paint.Color

/**
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 */
class ColorAnimationBuilder(private val animationBuilder: EntityAnimationBuilder) {

    private var startColor = Color.TRANSPARENT
    private var endColor = Color.TRANSPARENT

    fun fromColor(startColor: Color): ColorAnimationBuilder {
        this.startColor = startColor
        return this
    }

    fun toColor(endColor: Color): ColorAnimationBuilder {
        this.endColor = endColor
        return this
    }

    fun build(): Animation<*> {
        return animationBuilder.animationBuilder.build(
                AnimatedColor(startColor, endColor),
                Consumer { value -> animationBuilder.entities
                        .map { it.getComponent(ColorComponent::class.java) }
                        .forEach { it.value = value } }
        )
    }

    fun buildAndPlay(): Animation<*> {
        val anim = build()
        anim.startInPlayState()
        return anim
    }
}