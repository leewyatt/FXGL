/*
 * The MIT License (MIT)
 *
 * FXGL - JavaFX Game Library
 *
 * Copyright (c) 2015-2017 AlmasB (almaslvl@gmail.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.almasb.fxgl.service.impl.display

import com.almasb.fxgl.app.DialogSubState
import com.almasb.fxgl.app.FXGL
import com.almasb.fxgl.scene.FXGLScene
import com.almasb.fxgl.service.Display
import com.almasb.fxgl.ui.FXGLButton
import com.sun.javafx.scene.traversal.Algorithm
import com.sun.javafx.scene.traversal.Direction
import com.sun.javafx.scene.traversal.ParentTraversalEngine
import com.sun.javafx.scene.traversal.TraversalContext
import javafx.beans.binding.Bindings
import javafx.geometry.Point2D
import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.control.Button
import javafx.scene.control.TextField
import javafx.scene.effect.BoxBlur
import javafx.scene.effect.Effect
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import javafx.scene.text.Text
import jfxtras.scene.control.window.Window
import java.io.PrintWriter
import java.io.StringWriter
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import java.util.function.Consumer
import java.util.function.Predicate

/**
 * In-game dialog pane.
 * The pane fills the whole scene area so that user
 * input does not pass through to the underlying nodes.
 *
 * @author Almas Baimagambetov (AlmasB) (almaslvl@gmail.com)
 */
class DialogPane
internal constructor() : Pane() {

}