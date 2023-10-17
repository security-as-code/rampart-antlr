/*
 * Copyright (c) 2012-2017 The ANTLR Project. All rights reserved.
 * Use of this file is governed by the BSD 3-clause license that
 * can be found in the LICENSE.txt file in the project root.
 */

package org.rampart.antlr.v4.runtime.tree;

import org.rampart.antlr.v4.runtime.Parser;
import org.rampart.antlr.v4.runtime.RuleContext;
import org.rampart.antlr.v4.runtime.Token;
import org.rampart.antlr.v4.runtime.misc.Interval;

public class TerminalNodeImpl implements TerminalNode {
	public Token symbol;
	public ParseTree parent;

	public TerminalNodeImpl(Token symbol) {	this.symbol = symbol;	}


	public ParseTree getChild(int i) {return null;}


	public Token getSymbol() {return symbol;}


	public ParseTree getParent() { return parent; }


	public void setParent(RuleContext parent) {
		this.parent = parent;
	}


	public Token getPayload() { return symbol; }


	public Interval getSourceInterval() {
		if ( symbol ==null ) return Interval.INVALID;

		int tokenIndex = symbol.getTokenIndex();
		return new Interval(tokenIndex, tokenIndex);
	}


	public int getChildCount() { return 0; }


	public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
		return visitor.visitTerminal(this);
	}


	public String getText() { return symbol.getText(); }


	public String toStringTree(Parser parser) {
		return toString();
	}


	public String toString() {
			if ( symbol.getType() == Token.EOF ) return "<EOF>";
			return symbol.getText();
	}


	public String toStringTree() {
		return toString();
	}
}
