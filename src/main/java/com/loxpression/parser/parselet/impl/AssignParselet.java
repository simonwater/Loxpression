package com.loxpression.parser.parselet.impl;

import com.loxpression.expr.AssignExpr;
import com.loxpression.expr.Expr;
import com.loxpression.expr.GetExpr;
import com.loxpression.expr.SetExpr;
import com.loxpression.parser.Parser;
import com.loxpression.parser.Token;
import com.loxpression.parser.parselet.InfixParselet;

public class AssignParselet implements InfixParselet {
	private int precedence;
	public AssignParselet(int precedence) {
		this.precedence = precedence;
	}

	@Override
	public Expr parse(Parser parser, Expr lhs, Token token) {
		// 右结合，优先级降低以为，有连续等号时先解析后面的
		Expr rhs = parser.expressionPrec(precedence - 1);
		if (lhs instanceof GetExpr) {
			GetExpr get = (GetExpr)lhs;
			return new SetExpr(get.object, get.name, rhs);
		}
		return new AssignExpr(lhs, token, rhs);
	}

	@Override
	public int getPrecedence() {
		return precedence;
	}

}
