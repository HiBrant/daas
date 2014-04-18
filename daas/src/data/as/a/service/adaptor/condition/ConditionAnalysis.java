package data.as.a.service.adaptor.condition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

import data.as.a.service.adaptor.exception.IllegalQueryExpressionException;

public class ConditionAnalysis {

	private String str;

	public Conditions execute(String expression)
			throws IllegalQueryExpressionException {
		this.str = expression;
		List<Lexis> list = this.lexicalAnalyse();
		
//		for (Lexis lex: list) {
//			System.out.println(lex);
//		}
//		System.out.println("=================================");
		
		list = this.toPostfixExpression(list);
		
//		for (Lexis lex: list) {
//			System.out.println(lex);
//		}
//		System.out.println("=================================");
		
		return this.toQueryConditions(list);
	}

	private List<Lexis> lexicalAnalyse() throws IllegalQueryExpressionException {

		int begin = 0;
		int end = begin;
		int len = str.length();
		List<Lexis> list = new ArrayList<>();

		while (begin < len) {
			while (end < len) {
				char ch = str.charAt(end);
				if (ch == '*' || ch == '|' || ch == '(' || ch == ')') {
					if (begin == end) {
						String token = str.substring(begin, end + 1);
						Lexis lex = new Lexis(token, LexicalType.fromString(token));
						list.add(lex);
						end = begin = end + 1;
					} else {
						String token = str.substring(begin, end);
						Lexis lex = new Lexis(token, LexicalType.fromString(token));
						list.add(lex);
						begin = end;
					}
				} else if (ch == '[') {
					if (begin == end) {
						end++;
					} else {
						String token = str.substring(begin, end);
						Lexis lex = new Lexis(token, LexicalType.fromString(token));
						list.add(lex);
						begin = end;
					}
				} else if (ch == ']') {
					String token = str.substring(begin, end + 1);
					Lexis lex = new Lexis(token, LexicalType.fromString(token));
					list.add(lex);
					end = begin = end + 1;
				} else {
					end++;
				}
			}
			if (begin != end) {
				String token = str.substring(begin, end);
				Lexis lex = new Lexis(token, LexicalType.fromString(token));
				list.add(lex);
				begin = end;
			}
		}
		
		if (list.size() < 3) {
			throw new IllegalQueryExpressionException(str);
		}

		return list;
	}

	private List<Lexis> toPostfixExpression(List<Lexis> list) {
		Stack<Lexis> s0 = new Stack<>();
		Stack<Lexis> s1 = new Stack<>();

		for (Lexis lex : list) {
			if (lex.getType() == LexicalType.operand) {
				s0.push(lex);
			} else if (lex.getType() == LexicalType.lb) {
				s1.push(lex);
			} else if (lex.getType() == LexicalType.rb) {
				Lexis lexInS1;
				while ((lexInS1 = s1.pop()).getType() != LexicalType.lb) {
					s0.push(lexInS1);
				}
			} else {
				boolean pass = true;
				do {
					if (s1.empty() || s1.peek().getType() == LexicalType.lb) {
						s1.push(lex);
						pass = true;
					} else if (lex.getType().priority() > s1.peek().getType()
							.priority()) {
						s1.push(lex);
						pass = true;
					} else {
						s0.push(s1.pop());
						pass = false;
					}
				} while (!pass);
			}
		}
		while (!s1.empty()) {
			s0.push(s1.pop());
		}

		list.clear();
		while (!s0.empty()) {
			list.add(s0.pop());
		}

		Collections.reverse(list);
		return list;
	}

	private Conditions toQueryConditions(List<Lexis> list)
			throws IllegalQueryExpressionException {

		Stack<ConditionNode> s = new Stack<>();
		for (Lexis lex : list) {
			if (lex.getType() == LexicalType.operand) {
				ConditionNode node = new ConditionNode(lex.getToken());
				s.push(node);
			} else {
				try {
					ConditionNode right = s.pop();
					ConditionNode left = s.pop();
					ConditionNode node = new ConditionNode(lex.getType());
					node.setLeft(left);
					node.setRight(right);
					s.push(node);
				} catch (EmptyStackException e) {
					throw new IllegalQueryExpressionException(str);
				}

			}
		}
		if (s.size() != 1) {
			throw new IllegalQueryExpressionException(str);
		}

		Conditions conditions = new Conditions(s.pop());
		return conditions;
	}
	
}
