/*
 * Copyright (c) 2012-2017 The ANTLR Project. All rights reserved.
 * Use of this file is governed by the BSD 3-clause license that
 * can be found in the LICENSE.txt file in the project root.
 */

package org.antlr.v4.codegen.model;

import org.antlr.v4.codegen.OutputModelFactory;
import org.rampart.antlr.v4.runtime.misc.IntervalSet;
import org.antlr.v4.tool.ast.GrammarAST;

/** */
public class ThrowEarlyExitException extends ThrowRecognitionException {
	public ThrowEarlyExitException(OutputModelFactory factory, GrammarAST ast, IntervalSet expecting) {
		super(factory, ast, expecting);
	}
}
