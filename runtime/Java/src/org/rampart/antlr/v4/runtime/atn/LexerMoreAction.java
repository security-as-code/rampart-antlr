/*
 * Copyright (c) 2012-2017 The ANTLR Project. All rights reserved.
 * Use of this file is governed by the BSD 3-clause license that
 * can be found in the LICENSE.txt file in the project root.
 */

package org.rampart.antlr.v4.runtime.atn;

import org.rampart.antlr.v4.runtime.Lexer;
import org.rampart.antlr.v4.runtime.misc.MurmurHash;

/**
 * Implements the {@code more} lexer action by calling {@link Lexer#more}.
 *
 * <p>The {@code more} command does not have any parameters, so this action is
 * implemented as a singleton instance exposed by {@link #INSTANCE}.</p>
 *
 * @author Sam Harwell
 * @since 4.2
 */
public final class LexerMoreAction implements LexerAction {
	/**
	 * Provides a singleton instance of this parameterless lexer action.
	 */
	public static final LexerMoreAction INSTANCE = new LexerMoreAction();

	/**
	 * Constructs the singleton instance of the lexer {@code more} command.
	 */
	private LexerMoreAction() {
	}

	/**
	 * {@inheritDoc}
	 * @return This method returns {@link LexerActionType#MORE}.
	 */

	public LexerActionType getActionType() {
		return LexerActionType.MORE;
	}

	/**
	 * {@inheritDoc}
	 * @return This method returns {@code false}.
	 */

	public boolean isPositionDependent() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>This action is implemented by calling {@link Lexer#more}.</p>
	 */

	public void execute(Lexer lexer) {
		lexer.more();
	}


	public int hashCode() {
		int hash = MurmurHash.initialize();
		hash = MurmurHash.update(hash, getActionType().ordinal());
		return MurmurHash.finish(hash, 1);
	}


	@SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
	public boolean equals(Object obj) {
		return obj == this;
	}


	public String toString() {
		return "more";
	}
}
