/*
 * Copyright (c) 2012-2017 The ANTLR Project. All rights reserved.
 * Use of this file is governed by the BSD 3-clause license that
 * can be found in the LICENSE.txt file in the project root.
 */

package org.rampart.antlr.v4.runtime.tree;

import org.rampart.antlr.v4.runtime.RuleContext;

public interface RuleNode extends ParseTree {
	RuleContext getRuleContext();
}
