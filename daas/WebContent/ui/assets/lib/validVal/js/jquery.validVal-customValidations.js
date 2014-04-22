﻿$.fn.validVal.customValidations = {

	'nl_postalcode': function( val ) {
		var v = val.split( ' ' ).join( '' );

		//	Empty value is OK, required-test will follow if clas="required".
		if ( v.length == 0 ) {
			return true;
		}

		//	NL postalcode should be formatted as: "1234AB".
		if ( v.length != 6 ||
			isNaN( v.charAt( 0 ) ) || 
			isNaN( v.charAt( 1 ) ) ||
			isNaN( v.charAt( 2 ) ) ||
			isNaN( v.charAt( 3 ) ) ||
			!isNaN( v.charAt( 4 ) ) ||
			!isNaN( v.charAt( 5 ) )
		) {
			return false;
		} else {
			return true;		
		}
	}
};