/*
 * Copyright (c) 2012-2017 The ANTLR Project. All rights reserved.
 * Use of this file is governed by the BSD 3-clause license that
 * can be found in the LICENSE.txt file in the project root.
 */

package org.rampart.antlr.v4.runtime;

import org.rampart.antlr.v4.runtime.atn.ATNConfigSet;
import org.rampart.antlr.v4.runtime.atn.DecisionInfo;
import org.rampart.antlr.v4.runtime.atn.ParserATNSimulator;
import org.rampart.antlr.v4.runtime.atn.PredictionMode;
import org.rampart.antlr.v4.runtime.dfa.DFA;

import java.util.BitSet;

/** How to emit recognition errors. */
public interface ANTLRErrorListener {
	/**
	 * Upon syntax error, notify any interested parties. This is not how to
	 * recover from errors or compute error messages. {@link ANTLRErrorStrategy}
	 * specifies how to recover from syntax errors and how to compute error
	 * messages. This listener's job is simply to emit a computed message,
	 * though it has enough information to create its own message in many cases.
	 *
	 * <p>The {@link RecognitionException} is non-null for all syntax errors except
	 * when we discover mismatched token errors that we can recover from
	 * in-line, without returning from the surrounding rule (via the single
	 * token insertion and deletion mechanism).</p>
	 *
	 * @param recognizer
     *        What parser got the error. From this
	 * 		  object, you can access the context as well
	 * 		  as the input stream.
	 * @param offendingSymbol
	 *        The offending token in the input token
	 * 		  stream, unless recognizer is a lexer (then it's null). If
	 * 		  no viable alternative error, {@code e} has token at which we
	 * 		  started production for the decision.
	 * @param line
	 * 		  The line number in the input where the error occurred.
	 * @param charPositionInLine
	 * 		  The character position within that line where the error occurred.
	 * @param msg
	 * 		  The message to emit.
	 * @param e
	 *        The exception generated by the parser that led to
	 *        the reporting of an error. It is null in the case where
	 *        the parser was able to recover in line without exiting the
	 *        surrounding rule.
	 */
	public void syntaxError(Recognizer<?, ?> recognizer,
							Object offendingSymbol,
							int line,
							int charPositionInLine,
							String msg,
							RecognitionException e);

	/**
	 * This method is called by the parser when a full-context prediction
	 * results in an ambiguity.
	 *
	 * <p>Each full-context prediction which does not result in a syntax error
	 * will call either {@link #reportContextSensitivity} or
	 * {@link #reportAmbiguity}.</p>
	 *
	 * <p>When {@code ambigAlts} is not null, it contains the set of potentially
	 * viable alternatives identified by the prediction algorithm. When
	 * {@code ambigAlts} is null, use {@link ATNConfigSet#getAlts} to obtain the
	 * represented alternatives from the {@code configs} argument.</p>
	 *
	 * <p>When {@code exact} is {@code true}, <em>all</em> of the potentially
	 * viable alternatives are truly viable, i.e. this is reporting an exact
	 * ambiguity. When {@code exact} is {@code false}, <em>at least two</em> of
	 * the potentially viable alternatives are viable for the current input, but
	 * the prediction algorithm terminated as soon as it determined that at
	 * least the <em>minimum</em> potentially viable alternative is truly
	 * viable.</p>
	 *
	 * <p>When the {@link PredictionMode#LL_EXACT_AMBIG_DETECTION} prediction
	 * mode is used, the parser is required to identify exact ambiguities so
	 * {@code exact} will always be {@code true}.</p>
	 *
	 * <p>This method is not used by lexers.</p>
	 *
	 * @param recognizer the parser instance
	 * @param dfa the DFA for the current decision
	 * @param startIndex the input index where the decision started
	 * @param stopIndex the input input where the ambiguity was identified
	 * @param exact {@code true} if the ambiguity is exactly known, otherwise
	 * {@code false}. This is always {@code true} when
	 * {@link PredictionMode#LL_EXACT_AMBIG_DETECTION} is used.
	 * @param ambigAlts the potentially ambiguous alternatives, or {@code null}
	 * to indicate that the potentially ambiguous alternatives are the complete
	 * set of represented alternatives in {@code configs}
	 * @param configs the ATN configuration set where the ambiguity was
	 * identified
	 */
	void reportAmbiguity(Parser recognizer,
						 DFA dfa,
						 int startIndex,
						 int stopIndex,
						 boolean exact,
						 BitSet ambigAlts,
						 ATNConfigSet configs);

	/**
	 * This method is called when an SLL conflict occurs and the parser is about
	 * to use the full context information to make an LL decision.
	 *
	 * <p>If one or more configurations in {@code configs} contains a semantic
	 * predicate, the predicates are evaluated before this method is called. The
	 * subset of alternatives which are still viable after predicates are
	 * evaluated is reported in {@code conflictingAlts}.</p>
	 *
	 * <p>This method is not used by lexers.</p>
	 *
	 * @param recognizer the parser instance
	 * @param dfa the DFA for the current decision
	 * @param startIndex the input index where the decision started
	 * @param stopIndex the input index where the SLL conflict occurred
	 * @param conflictingAlts The specific conflicting alternatives. If this is
	 * {@code null}, the conflicting alternatives are all alternatives
	 * represented in {@code configs}. At the moment, conflictingAlts is non-null
	 * (for the reference implementation, but Sam's optimized version can see this
	 * as null).
	 * @param configs the ATN configuration set where the SLL conflict was
	 * detected
	 */
	void reportAttemptingFullContext(Parser recognizer,
									 DFA dfa,
									 int startIndex,
									 int stopIndex,
									 BitSet conflictingAlts,
									 ATNConfigSet configs);

	/**
	 * This method is called by the parser when a full-context prediction has a
	 * unique result.
	 *
	 * <p>Each full-context prediction which does not result in a syntax error
	 * will call either {@link #reportContextSensitivity} or
	 * {@link #reportAmbiguity}.</p>
	 *
	 * <p>For prediction implementations that only evaluate full-context
	 * predictions when an SLL conflict is found (including the default
	 * {@link ParserATNSimulator} implementation), this method reports cases
	 * where SLL conflicts were resolved to unique full-context predictions,
	 * i.e. the decision was context-sensitive. This report does not necessarily
	 * indicate a problem, and it may appear even in completely unambiguous
	 * grammars.</p>
	 *
	 * <p>{@code configs} may have more than one represented alternative if the
	 * full-context prediction algorithm does not evaluate predicates before
	 * beginning the full-context prediction. In all cases, the final prediction
	 * is passed as the {@code prediction} argument.</p>
	 *
	 * <p>Note that the definition of "context sensitivity" in this method
	 * differs from the concept in {@link DecisionInfo#contextSensitivities}.
	 * This method reports all instances where an SLL conflict occurred but LL
	 * parsing produced a unique result, whether or not that unique result
	 * matches the minimum alternative in the SLL conflicting set.</p>
	 *
	 * <p>This method is not used by lexers.</p>
	 *
	 * @param recognizer the parser instance
	 * @param dfa the DFA for the current decision
	 * @param startIndex the input index where the decision started
	 * @param stopIndex the input index where the context sensitivity was
	 * finally determined
	 * @param prediction the unambiguous result of the full-context prediction
	 * @param configs the ATN configuration set where the unambiguous prediction
	 * was determined
	 */
	void reportContextSensitivity(Parser recognizer,
								  DFA dfa,
								  int startIndex,
								  int stopIndex,
								  int prediction,
								  ATNConfigSet configs);
}
