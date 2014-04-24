package data.as.a.service.exception;

public abstract class DaaSException extends Exception {

	private static final long serialVersionUID = -8875713694335864187L;
	
	public DaaSException(String msg) {
		super(msg);
	}
	
	public DaaSException(String msg, Throwable e) {
		super(msg, e);
	}

	public abstract int getErrorCode();
	
	// adaptor exceptions
	protected final static int CODE_FAIL_TO_ACCESS_ENTITY_FIELD 		= 40001;
	protected final static int CODE_FAIL_TO_ASSIGN_ENTITY_FIELD 		= 40002;
	protected final static int CODE_FAIL_TO_CALL_REPOSITORY_METHOD 		= 40003;
	protected final static int CODE_FAIL_TO_INSTANTIATE_ENTITY_OBJECT 	= 40004;
	protected final static int CODE_FIELD_TYPE_NOT_MATCH_DATA_MODEL		= 40005;
	protected final static int CODE_ILLEGAL_QUERY_EXPRESSION			= 40006;
	protected final static int CODE_INVALID_QUERY_OPERATOR				= 40007;
	protected final static int CODE_NO_DATA_OBJECT_INSTANCE_REFERED		= 40008;
	protected final static int CODE_NO_SUCH_FIELD_DEFINED				= 40009;
	
	// common exceptions
	protected final static int CODE_INVALID_APP_VERIFICATION			= 40101;
	protected final static int CODE_MODEL_CLASS_MISSING					= 40102;
	protected final static int CODE_MODEL_NOT_AVAILABLE					= 40103;
	protected final static int CODE_STILL_DELELOPING					= 40104;
	protected final static int CODE_UNHANDLED							= 40105;
	
	// generator exceptions
	protected final static int CODE_FAIL_TO_LOAD_CLASS					= 40201;
	protected final static int CODE_FAIL_TO_SAVE_CLASS_FILE				= 40202;
	
	// metadata exceptions
	protected final static int CODE_MODEL_EXISTS						= 40301;
	protected final static int CODE_NO_MODEL_REFER_TO_THIS_ID			= 40302;
	protected final static int CODE_REFERENCE_MODEL_NOT_EXISTS			= 40303;
	protected final static int CODE_REPEATED_VARIABLE_NAME				= 40304;
	protected final static int CODE_SAME_NAME_WITH_DISCARD_MODEL		= 40305;
	protected final static int CODE_UNSUPPORTED_FIELD_TYPE				= 40306;
	protected final static int CODE_UNSUPPORTED_REFERENCE_TYPE			= 40307;
	protected final static int CODE_UNSUPPORTED_SEMANTICS_TYPE 			= 40308;
	
	// json exception
	public final static int CODE_JSON									= 40401;
}
