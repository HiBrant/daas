package data.as.a.service.adaptor.condition;

import java.lang.reflect.Field;

import data.as.a.service.adaptor.exception.FailToAccessEntityFieldException;
import data.as.a.service.adaptor.exception.FieldTypeNotMatchDataModelException;
import data.as.a.service.adaptor.exception.InvalidQueryOperatorException;
import data.as.a.service.adaptor.exception.NoSuchFieldDefinedException;
import data.as.a.service.exception.SystemException;
import data.as.a.service.exception.UserException;

public class ConditionNode {

	private LexicalType type = null;
	private String str = null;

	private ConditionNode left = null;
	private ConditionNode right = null;

	public ConditionNode(LexicalType type, String str) {
		this.type = type;
		this.str = str;
	}

	public ConditionNode(LexicalType type) {
		this(type, null);
	}

	public ConditionNode(String str) {
		this(LexicalType.operand, str);
	}

	public String getString() {
		return str;
	}

	public LexicalType getType() {
		return type;
	}

	public ConditionNode getLeft() {
		return left;
	}

	public void setLeft(ConditionNode left) {
		this.left = left;
	}

	public ConditionNode getRight() {
		return right;
	}

	public void setRight(ConditionNode right) {
		this.right = right;
	}

	public boolean judge(Object entity) throws SystemException, UserException {

		if (type == LexicalType.and) {
			return left.judge(entity) && right.judge(entity);
		}
		if (type == LexicalType.or) {
			return left.judge(entity) || right.judge(entity);
		}
		if (type == LexicalType.operand) {
			return false;
		}

		Object fieldValue = null;
		Class<?> fieldType = null;
		Object targetValue = null;
		try {
			Field field = entity.getClass().getDeclaredField(left.getString());
			fieldValue = field.get(entity);
			fieldType = field.getType();

			if (fieldType == Integer.class) {
				targetValue = Integer.parseInt(right.getString());
			} else if (fieldType == Double.class) {
				targetValue = Double.parseDouble(right.getString());
			} else if (fieldType == Boolean.class) {
				targetValue = Boolean.parseBoolean(right.getString());
			} else {
				targetValue = right.getString();
			}

		} catch (NoSuchFieldException e) {
			throw new NoSuchFieldDefinedException(left.getString());
		} catch (SecurityException e) {
			throw new FailToAccessEntityFieldException(entity.getClass()
					.getName(), left.getString(), e);
		} catch (NumberFormatException e) {
			throw new FieldTypeNotMatchDataModelException(left.getString());
		} catch (IllegalArgumentException e) {
			throw new FailToAccessEntityFieldException(entity.getClass()
					.getName(), left.getString(), e);
		} catch (IllegalAccessException e) {
			throw new FailToAccessEntityFieldException(entity.getClass()
					.getName(), left.getString(), e);
		}

		if (type == LexicalType.eq) {
			return fieldValue.equals(targetValue);
		}
		if (type == LexicalType.ne) {
			return !fieldValue.equals(targetValue);
		}

		if (type == LexicalType.lt || type == LexicalType.gt
				|| type == LexicalType.le || type == LexicalType.ge) {
			if (fieldType == String.class || fieldType == Boolean.class) {
				throw new InvalidQueryOperatorException(left.getString(),
						fieldType, type);
			}
			if (fieldType == Integer.class) {
				int a = (int) fieldValue;
				int b = (int) targetValue;
				switch (type) {
				case lt:
					return a < b;
				case gt:
					return a > b;
				case le:
					return a <= b;
				case ge:
					return a >= b;
				default:
					break;
				}
			}
			if (fieldType == Double.class) {
				double a = (double) fieldValue;
				double b = (double) targetValue;
				switch (type) {
				case lt:
					return a < b;
				case gt:
					return a > b;
				case le:
					return a <= b;
				case ge:
					return a >= b;
				default:
					break;
				}
			}
		}
		
		if (type == LexicalType.sw || type == LexicalType.ew || type == LexicalType.ct) {
			if (fieldType != String.class) {
				throw new InvalidQueryOperatorException(left.getString(),
						fieldType, type);
			}
			String a = (String) fieldValue;
			String b = (String) targetValue;
			switch (type) {
			case sw:
				return a.startsWith(b);
			case ew:
				return a.endsWith(b);
			case ct:
				return a.contains(b);
			default:
				break;
			}
		}

		return false;
	}
}
